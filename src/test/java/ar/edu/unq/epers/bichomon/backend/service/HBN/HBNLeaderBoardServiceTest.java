package ar.edu.unq.epers.bichomon.backend.service.HBN;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.persistence.cache.REDISCampeonesDao;
import ar.edu.unq.epers.bichomon.backend.service.ServiceFactory;
import ar.edu.unq.epers.bichomon.backend.service.data.DataServiceHBN;
import ar.edu.unq.epers.bichomon.backend.service.leaderBoard.HBNLeaderBoardService;
import ar.edu.unq.epers.bichomon.backend.service.leaderBoard.LeaderBoardService;
import ar.edu.unq.epers.bichomon.backend.service.runner.SessionFactoryProvider;
import ar.edu.unq.epers.bichomon.backend.service.ubicacion.UbicacionService;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class HBNLeaderBoardServiceTest {

    private LeaderBoardService leaderBoardService = new HBNLeaderBoardService();
    UbicacionService ubicacionService = new ServiceFactory().getUbicacionService();
    private Ubicacion ubicacion;
    private Entrenador entrenador1;
    private Entrenador entrenador2;
    private Entrenador entrenador3;
    private Dojo dojo1;
    private Dojo dojo2;
    private Guarderia guarderia;
    private Especie especie1;
    private Especie especie2;

    @Before
    public void crearModelo() {
        this.ubicacion = new Pueblo("Paleta");
        this.entrenador1 = new Entrenador("Ash");
        this.entrenador2 = new Entrenador("Misty");
        this.entrenador3 = new Entrenador("Brock");
        this.dojo1 = new Dojo("Gimnasio Brok");
        this.dojo2 = new Dojo("Gimnasio Misty");
        this.guarderia = new Guarderia("Guarderia");
        especie1 = new Especie("pikachu", TipoBicho.TIERRA);
        especie2 = new Especie("pepe", TipoBicho.PLANTA);
    }
    @After
    public void tearDown() {
        new DataServiceHBN().clearDb();
        new REDISCampeonesDao().clear();
    }

    @Test
    public void cuandoPidoLosCampeonesMeDevuelveUnaListaConTodosLosEntrenadoresQueSonCampeonesActualmente() {
        Bicho bicho1 = new Bicho(especie1);
        entrenador1.agregarBicho(bicho1);
        Bicho bicho2 = new Bicho(especie2);
        entrenador2.agregarBicho(bicho2);

        dojo1.setCampeones(Arrays.asList(bicho1));
        dojo2.setCampeones(Arrays.asList(bicho2));
        guarderia.agregarEntrenador(entrenador3);

        ubicacionService.nuevaUbicacion(dojo1);
        ubicacionService.nuevaUbicacion(dojo2);
        ubicacionService.nuevaUbicacion(guarderia);

        List<String> expectedNombres = getNombres(Arrays.asList(entrenador1, entrenador2));

        assertEquals(expectedNombres, getNombres(leaderBoardService.campeones()));
        assertEquals(leaderBoardService.campeones().size(), 2);
    }

    @Test
    public void cuandoPidoLosCampeonesYNoHayNingunoMeDevuelveUnaListaVacia() {
        ubicacionService.nuevaUbicacion(dojo1);
        ubicacionService.nuevaUbicacion(dojo2);

        assertEquals(new ArrayList<>(), leaderBoardService.campeones());
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void cuandoPidoLaEspecieLiderYNoHayNingunCampeonMeDevuelveUnaListaVacia() {
        exception.expect(RuntimeException.class);
        exception.expectMessage("No hay ningun campeon");

        ubicacionService.nuevaUbicacion(dojo1);
        ubicacionService.nuevaUbicacion(dojo2);
        leaderBoardService.especieLider();
    }

    @Test
    public void cuandoPidoLaEspecieLiderMeDevuelveLaEspecieConMasCampeonesEnLosDojos() {
        Bicho bicho1 = new Bicho(especie1);
        Bicho bicho2 = new Bicho(especie2);
        Bicho bicho3 = new Bicho(especie2);

        dojo1.setCampeones(Arrays.asList(bicho1, bicho3));
        dojo2.setCampeones(Arrays.asList(bicho2));

        ubicacionService.nuevaUbicacion(dojo1);
        ubicacionService.nuevaUbicacion(dojo2);

        assertEquals(especie2.getNombre(), leaderBoardService.especieLider().getNombre());
    }

    /*
    @Test
    public void cuandoPidoLaEspecieLiderMeDevuelveLaEspecieConMasCampeonesSinRepetirBichos() {
        Bicho bicho1 = new Bicho(especie1);
        Bicho bicho2 = new Bicho(especie2);
        Bicho bicho3 = new Bicho(especie2);

        dojo1.setCampeones(Arrays.asList(bicho1, bicho3));
        dojo2.setCampeones(Arrays.asList(bicho2, bicho1, bicho1));

        ubicacionService.nuevaUbicacion(dojo1);
        ubicacionService.nuevaUbicacion(dojo2);

        assertEquals(especie2.getNombre(), leaderBoardService.especieLider().getNombre());
    }
    */

    public List<String> getNombres(List<Entrenador> entrenadores) {
        List<String> nombres = new ArrayList<>();
        for (Entrenador entrenador : entrenadores) {
            nombres.add(entrenador.getNombre());
        }
        return nombres;
    }
}
