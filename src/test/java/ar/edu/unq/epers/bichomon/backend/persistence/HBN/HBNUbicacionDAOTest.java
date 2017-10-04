package ar.edu.unq.epers.bichomon.backend.persistence.HBN;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.persistence.Ubicacion.UbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.persistence.Ubicacion.HBNUbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.service.data.DataServiceHBN;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import ar.edu.unq.epers.bichomon.backend.service.runner.SessionFactoryProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.mock;

public class HBNUbicacionDAOTest {

    private UbicacionDAO ubicacionDAO = new HBNUbicacionDAO();
    private Ubicacion ubicacion;
    private Entrenador entrenador;
    private Dojo dojo1;
    private Dojo dojo2;

    @Before
    public void crearModelo() {

        this.ubicacion = new Pueblo("Paleta");
        this.entrenador = new Entrenador("Ash");
        this.dojo1 = new Dojo("Gimnasio Brok");
        this.dojo2 = new Dojo("Gimnasio Misty");
    }
    @After
    public void tearDown() { new DataServiceHBN().clearDb(); }

    @Test
    public void cuandoCreoYrecuperoUnaUbicacionSonObjetosSimilares() {
        Runner.runInSession(() -> {
            ubicacionDAO.nuevaUbicacion(ubicacion);
            Ubicacion ubicacionRecuperado = ubicacionDAO.recuperar(ubicacion.getNombre());
            assertTrue(ubicacion.equalUbicacion(ubicacionRecuperado));
            return null;
        });
    }
    @Test
    public void cuandoActualizoUnaUbicacionYlaRecuperoSePersisitio() {
        Runner.runInSession(() -> {
            ubicacionDAO.nuevaUbicacion(ubicacion);
            Ubicacion ubicacionRecuperado = ubicacionDAO.recuperar(ubicacion.getNombre());
            ubicacionRecuperado.agregarEntrenador(entrenador);
            ubicacionDAO.actualizarUbicacion(ubicacionRecuperado);
            Ubicacion ubicacionActualizada = ubicacionDAO.recuperar(ubicacion.getNombre());
            assertTrue(ubicacionActualizada.equalUbicacion(ubicacionRecuperado));
            return null;
        });
    }

    @Test
    public void cuandoPidoTodosLosDojosMeDevuelveUnaListaConEstos() {
        Runner.runInSession(() -> {
            ubicacionDAO.nuevaUbicacion(dojo1);
            ubicacionDAO.nuevaUbicacion(dojo2);
            ubicacionDAO.nuevaUbicacion(new Dojo("Gimnasio Lance"));

            List<Dojo> dojos = ubicacionDAO.todosLosDojos();
            assertEquals(3, dojos.size());
            return null;
        });
    }

    @Test
    public void cuandoPidoTodosLosDojosYHayOtrasUbicacionesMeDevuelveUnaListaConSoloDojos() {
        Runner.runInSession(() -> {
            ubicacionDAO.nuevaUbicacion(dojo1);
            ubicacionDAO.nuevaUbicacion(new Guarderia("Viejito"));
            ubicacionDAO.nuevaUbicacion(new Pueblo("Paleta"));

            List<Dojo> dojos =  ubicacionDAO.todosLosDojos();
            assertEquals(1, dojos.size());
            return null;
        });
    }

    @Test
    public void cuandoPidoTodosLosDojosYNoHayDojosMeDevuelveListaVacia() {
        Runner.runInSession(() -> {
            ubicacionDAO.nuevaUbicacion(new Guarderia("Viejito"));
            ubicacionDAO.nuevaUbicacion(new Pueblo("Paleta"));

            List<Dojo> dojos =  ubicacionDAO.todosLosDojos();
            assertEquals(0, dojos.size());
            return null;
        });
    }

    @Test
    public void cuandoPidoTodosLosCampeonesMeDevuelveLosCampeonesDeCadaDojo() {
        Runner.runInSession(() -> {
            Bicho bicho1 = getBichoMock();
            Bicho bicho2 = getBichoMock();
            Bicho bicho3 = getBichoMock();

            dojo1.setCampeones(Arrays.asList(bicho1, bicho2));
            dojo2.setCampeones(Arrays.asList(bicho3));

            ubicacionDAO.nuevaUbicacion(dojo1);
            ubicacionDAO.nuevaUbicacion(dojo2);

            List<Bicho> bichos = ubicacionDAO.todosLosCampeones();
            assertEquals(Arrays.asList(bicho1, bicho2, bicho3), bichos);
            return null;
        });
    }

    @Test
    public void cuandoPidoTodosLosCampeonesYEstanRepetidosMeDevuelveLosCampeonesSinRepetidos() {
        Runner.runInSession(() -> {
            Bicho bicho1 = getBichoMock();
            Bicho bicho2 = getBichoMock();
            Bicho bicho3 = getBichoMock();

            dojo1.setCampeones(Arrays.asList(bicho1, bicho2));
            dojo2.setCampeones(Arrays.asList(bicho2, bicho3));

            ubicacionDAO.nuevaUbicacion(dojo1);
            ubicacionDAO.nuevaUbicacion(dojo2);

            List<Bicho> bichos = ubicacionDAO.todosLosCampeones();
            assertEquals(Arrays.asList(bicho1, bicho2, bicho3), bichos);
            return null;
        });
    }

    private Bicho getBichoMock() {
        return mock(Bicho.class);
    }
}
