package ar.edu.unq.epers.bichomon.backend.service.bicho;

import ar.edu.unq.epers.bichomon.backend.model.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.evento.Abandono;
import ar.edu.unq.epers.bichomon.backend.model.evento.Coronacion;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.persistence.HBNGenericDAO;
import ar.edu.unq.epers.bichomon.backend.persistence.bicho.BichoDAO;
import ar.edu.unq.epers.bichomon.backend.persistence.bicho.HBNBichoDAO;
import ar.edu.unq.epers.bichomon.backend.persistence.entrenador.impl.HBNEntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.service.entrenador.EntrenadorService;
import ar.edu.unq.epers.bichomon.backend.service.entrenador.HBNEntrenadorService;
import ar.edu.unq.epers.bichomon.backend.service.evento.EventoService;
import ar.edu.unq.epers.bichomon.backend.service.evento.MongoEventoService;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import org.hibernate.Session;

public class HBNBichoService implements BichoService {

    BichoDAO bichoDAO = new HBNBichoDAO();
    EntrenadorService entrenadorService = new HBNEntrenadorService();
    HBNEntrenadorDAO entrenadorDAO = new HBNEntrenadorDAO();
    HBNGenericDAO genericDAO = new HBNGenericDAO();
    EventoService eventoService = new MongoEventoService();

    @Override
    public Bicho recuperarBicho( int bicho ) {
        return bichoDAO.obtenerBicho( bicho );
    }

    @Override
    public void guardarBicho( Bicho bicho ) {
        bichoDAO.guardarBicho( bicho );
    }

    @Override
    public void abandonar(String entrenador, int bicho){
        Runner.runInSession(() -> {
            Entrenador entrenadorRecuperado = entrenadorDAO.getEntrenador(entrenador);
            Bicho bichoRecuperado = bichoDAO.obtenerBicho(bicho);
            entrenadorRecuperado.abandonarBicho(bichoRecuperado);
            entrenadorDAO.actualizarEntrenador(entrenadorRecuperado);
            Guarderia ubicacion = (Guarderia) entrenadorRecuperado.getUbicacion();
            eventoService.guardarEvento(new Abandono(entrenadorRecuperado, bichoRecuperado, ubicacion));
            return null;
        });
    }

    @Override
    public Bicho buscar(String entrenador) throws Exception {
        Entrenador parsedEntrenador = entrenadorService.getEntrenador(entrenador);

        Bicho bichoCapturado = parsedEntrenador.capturarBicho();
        if (present(bichoCapturado)) {
            genericDAO.actualizarEntidad(entrenador);
        }
        return bichoCapturado;
    }

    private boolean present(Bicho bichoCapturado) {
        return bichoCapturado != null;
    }

    @Override
    public Bicho obtenerBichoRandom() {
        return Runner.runInSession( () -> bichoDAO.obtenerRandom());
    }

    @Override
    public boolean puedeEvolucionar(String entrenador, int bicho) {
        return false;
    }

    @Override
    public Bicho evolucionar(int bicho) {
        return Runner.runInSession( () -> {
        	Bicho bichoEvolucionado = this.bichoDAO.obtenerBicho(bicho);
        	bichoEvolucionado.evolucionar();
        	this.bichoDAO.evolucionar(bichoEvolucionado);
        	return bichoEvolucionado;
        });

    }

    @Override
    public ResultadoCombate duelo(String entrenadorId, int bichoId) throws Exception {
        Bicho bichoRecuperado = Runner.runInSession( () -> this.bichoDAO.obtenerBicho(bichoId));
        Entrenador entrenadorRecuperado = this.entrenadorService.getEntrenador(entrenadorId);
        ResultadoCombate resultado = entrenadorRecuperado.retarADuelo(bichoRecuperado);

        Dojo dojo = (Dojo) entrenadorRecuperado.getUbicacion();
        Coronacion coronacion = new Coronacion(entrenadorRecuperado, resultado.getGanador(), resultado.getPerdedor(), dojo);
        eventoService.guardarEvento(coronacion);
        return resultado;
    }

}
