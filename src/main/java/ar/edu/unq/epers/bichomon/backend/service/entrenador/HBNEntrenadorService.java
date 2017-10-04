package ar.edu.unq.epers.bichomon.backend.service.entrenador;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.evento.Abandono;
import ar.edu.unq.epers.bichomon.backend.model.evento.Captura;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;
import ar.edu.unq.epers.bichomon.backend.persistence.HBNGenericDAO;
import ar.edu.unq.epers.bichomon.backend.persistence.bicho.BichoDAO;
import ar.edu.unq.epers.bichomon.backend.persistence.bicho.HBNBichoDAO;
import ar.edu.unq.epers.bichomon.backend.persistence.entrenador.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.persistence.entrenador.impl.HBNEntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.service.bicho.HBNBichoService;
import ar.edu.unq.epers.bichomon.backend.service.evento.EventoService;
import ar.edu.unq.epers.bichomon.backend.service.evento.MongoEventoService;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

import java.util.List;

public class HBNEntrenadorService implements EntrenadorService {

    EntrenadorDAO entrenadorDAO = new HBNEntrenadorDAO();
    HBNGenericDAO genericDAO = new HBNGenericDAO();
    BichoDAO bichoDAO = new HBNBichoDAO();
    EventoService eventoService = new MongoEventoService();

    @Override
    public void crearEntrenador(Entrenador entrenador) {
        genericDAO.crearEntidad(entrenador);
    }

    @Override
    public Entrenador getEntrenador(String nombre) {

        return genericDAO.recuperarEntidad(Entrenador.class, nombre);
    }

    @Override
    public void capturarBicho(String entrenador, Bicho bichoCapturado) {
        Runner.runInSession(() -> {
            Entrenador parsedEntrenador = this.entrenadorDAO.getEntrenador(entrenador);
            parsedEntrenador.getBichos().add(bichoCapturado);
            this.entrenadorDAO.actualizarEntrenador(parsedEntrenador);
            Pueblo pueblo = (Pueblo) parsedEntrenador.getUbicacion();
            eventoService.guardarEvento(new Captura(parsedEntrenador, bichoCapturado, pueblo));
            return null;
        });
    }

    @Override
    public List<Entrenador> entrenadoresConEspecie(Especie especie) {
        return Runner.runInSession(() -> this.entrenadorDAO.entrenadoresConEspecie(especie));
    }

    @Override
    public void actualizarEntrenador(Entrenador entrenador) {
        Runner.runInSession(() -> {
            this.entrenadorDAO.actualizarEntrenador(entrenador);
            return null;
        });
    }

    /*@Override
    public void abandonarBicho(String entrenador, int bicho) {
        Runner.runInSession(() -> {
            Entrenador entrenadorRecuperado = entrenadorDAO.getEntrenador(entrenador);
            Bicho bichoRecuperado = bichoDAO.obtenerBicho(bicho);
            entrenadorRecuperado.abandonarBicho(bichoRecuperado);
            entrenadorDAO.actualizarEntrenador(entrenadorRecuperado);
            Guarderia ubicacion = (Guarderia) entrenadorRecuperado.getUbicacion();
            eventoService.guardarEvento(new Abandono(entrenadorRecuperado, bichoRecuperado, ubicacion));
            return null;
        });
    }*/
}
