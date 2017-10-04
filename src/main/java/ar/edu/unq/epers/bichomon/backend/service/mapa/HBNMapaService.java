package ar.edu.unq.epers.bichomon.backend.service.mapa;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.camino.Camino;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoCamino;
import ar.edu.unq.epers.bichomon.backend.model.evento.Arribo;
import ar.edu.unq.epers.bichomon.backend.model.evento.Evento;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.persistence.Ubicacion.HBNUbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.persistence.cache.REDISCantidadEntrenadoresDao;
import ar.edu.unq.epers.bichomon.backend.persistence.cache.REDISDao;
import ar.edu.unq.epers.bichomon.backend.persistence.camino.N4JCaminoDAO;
import ar.edu.unq.epers.bichomon.backend.persistence.entrenador.impl.HBNEntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.service.evento.EventoService;
import ar.edu.unq.epers.bichomon.backend.service.evento.MongoEventoService;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import ar.edu.unq.epers.bichomon.backend.service.ubicacion.HBNUbicacionService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.not;

public class HBNMapaService implements MapaService{

    HBNEntrenadorDAO entrenadorDao = new HBNEntrenadorDAO();
    HBNUbicacionDAO ubicacionDao = new HBNUbicacionDAO();
    N4JCaminoDAO ubicacionRelacionDao = new N4JCaminoDAO();
    EventoService eventoService = new MongoEventoService();
    REDISCantidadEntrenadoresDao cantidadEntrenadoresDao = new REDISCantidadEntrenadoresDao();

    @Override
    public void crearUbicacion(Ubicacion ubicacion) {
        Runner.runInSession(() -> {
            ubicacionDao.nuevaUbicacion( ubicacion );
            ubicacionRelacionDao.crearUbicacion( ubicacion );
            return null;
        });
    }

    @Override
    public void conectarUbicaciones(String origen, String destino, Camino camino) {
        Runner.runInSession(() -> {
           Ubicacion origenRecuperado = ubicacionDao.recuperar(origen);
           Ubicacion destinoRecuperado = ubicacionDao.recuperar(destino);
           ubicacionRelacionDao.conectarUbicaciones( origenRecuperado, destinoRecuperado, camino );
           return null;
        });
    }

    @Override
    public List<Ubicacion> conectados(String ubicacion, String tipoCamino) {
        return Runner.runInSession(() -> {
           Ubicacion ubicacionRecuperada = ubicacionDao.recuperar(ubicacion);
           List<String> listaUbicacionesStr = ubicacionRelacionDao.conectados( ubicacionRecuperada, TipoCamino.valueOf(tipoCamino));
           return parsearUbicaciones(listaUbicacionesStr);
        });
    }

    private List<Ubicacion> parsearUbicaciones(List<String> listaUbicacionesStr) {
        List<Ubicacion> listaUbicaciones = new ArrayList<>();
        for ( String ubicacionStr : listaUbicacionesStr ){
            listaUbicaciones.add( ubicacionDao.recuperar( ubicacionStr ));
        }
        return listaUbicaciones;
    }

    public List<Ubicacion> conectados(Ubicacion ubicacion) {
        return Runner.runInSession(() -> {
            List<Ubicacion> res = new ArrayList<>();
            for (TipoCamino tipo : TipoCamino.values()) {
                res.addAll(parsearUbicaciones(ubicacionRelacionDao.conectados(ubicacion, tipo)));
            }
            return res;
        });
    }

    @Override
    /**
     * cuando muevo un entrenador lo agrego a la lista de entrenadores
     * y al entrenador le cambio el atributo ubicacion
     */
    public void moverMasCorto(String strEntrenador, String strUbicacion) {
        Runner.runInSession(() -> {

            Entrenador entrenadorRecuperado = this.entrenadorDao.getEntrenador(strEntrenador);
            Ubicacion ubicacionRecuperada = this.ubicacionDao.recuperar(strUbicacion);
            Ubicacion ubicacionActual = entrenadorRecuperado.getUbicacion();


            if ( ubicacionRelacionDao.conectados( ubicacionActual, ubicacionRecuperada ) ){

                try {
                    entrenadorRecuperado.pagarMonedas(ubicacionRelacionDao.caminoMasCorto(ubicacionActual,
                            ubicacionRecuperada));
                } catch (Exception e) {
                    throw new RuntimeException("El camino es muy costoso");
                }

                entrenadorRecuperado.setUbicacion(ubicacionRecuperada);
                ubicacionDao.actualizarUbicacion(ubicacionActual);
                entrenadorDao.actualizarEntrenador(entrenadorRecuperado);
            }
            else {
                throw new RuntimeException("Ubicacion muy lejana");
            }

            return null;
        });


    }
    public void mover(String strEntrenador, String strUbicacion) throws Exception {

            Runner.runInSession(() -> {

                int costoViaje;
                Entrenador entrenadorRecuperado = this.entrenadorDao.getEntrenador(strEntrenador);
                Ubicacion ubicacionRecuperada = this.ubicacionDao.recuperar(strUbicacion);
                Ubicacion ubicacionActual = entrenadorRecuperado.getUbicacion();

                try {
                    costoViaje = ubicacionRelacionDao.caminoMasBarato(ubicacionActual, ubicacionRecuperada);
                }
                catch (Exception e) {
                    throw new RuntimeException("Ubicacion muy lejana");
                }


                try {
                    entrenadorRecuperado.pagarMonedas(costoViaje);
                } catch (Exception e) {
                    throw new RuntimeException("El camino es muy costoso");
                }

                entrenadorRecuperado.setUbicacion(ubicacionRecuperada);
                ubicacionDao.actualizarUbicacion(ubicacionActual);
                entrenadorDao.actualizarEntrenador(entrenadorRecuperado);
                eventoService.guardarEvento(new Arribo(entrenadorRecuperado, ubicacionRecuperada));
                guardarEnCache(ubicacionActual);
                guardarEnCache(ubicacionRecuperada);

                return null;
            });

    }

    private void guardarEnCache(Ubicacion ubicacion) {
        String nombreUbicacion = ubicacion.getNombre();
        cantidadEntrenadoresDao.put(nombreUbicacion, cantidadEntrenadores(nombreUbicacion));
    }

    @Override
    public int cantidadEntrenadores(String ubicacion) {
        if (cantidadEntrenadoresDao.exists(ubicacion)) { return cantidadEntrenadoresDao.get(ubicacion); }

        HBNUbicacionService ubicacionSevice = new HBNUbicacionService();
        Ubicacion ubicacionRecuperada = ubicacionSevice.reccuperarUbicacion(ubicacion);
        Integer cantidadDeEntrenadores = ubicacionRecuperada.getCantidadEntreadores();
        cantidadEntrenadoresDao.put(ubicacion, cantidadDeEntrenadores);
        return cantidadDeEntrenadores;
    }

    @Override
    public Bicho campeon(String dojo) {
        return Runner.runInSession(() -> {
            Dojo dojoRecuperado = (Dojo) this.ubicacionDao.recuperar(dojo);
            return dojoRecuperado.campeonActual();
        });
    }

    @Override
    public Bicho campeonHitorico(String dojo) {
        return null;
    }

    private Evento generarEvento(Entrenador entrenador, Ubicacion ubicacion) {
        return new Arribo(entrenador, ubicacion);
    }

}
