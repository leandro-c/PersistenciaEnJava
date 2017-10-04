package ar.edu.unq.epers.bichomon.backend.service.HBN;

import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.data.DataServiceHBN;
import ar.edu.unq.epers.bichomon.backend.service.runner.SessionFactoryProvider;
import ar.edu.unq.epers.bichomon.backend.service.ubicacion.HBNUbicacionService;
import ar.edu.unq.epers.bichomon.backend.service.ubicacion.UbicacionService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class HBNUbicacionServiceTest {

    public Ubicacion ubicacion;
    public UbicacionService ubicacionService = new HBNUbicacionService();

    @Before
    public void crearModelo(){
        this.ubicacion = new Pueblo("Paleta");
    }
    @After
    public void tearDown() { new DataServiceHBN().clearDb(); }

    @Test
    public void cuandoCreoUnaUbicacionYlaRecuperoSonSimilares(){
        ubicacionService.nuevaUbicacion(ubicacion);
        Ubicacion ubicacionRecuperada = ubicacionService.reccuperarUbicacion(ubicacion.getNombre());
        assertTrue(ubicacion.equalUbicacion(ubicacionRecuperada));
    }

    @Test
    public void cuandoPidoTodosLosDojosMeDevuelveUnaListaConEstos() {
        ubicacionService.nuevaUbicacion(new Dojo("Gimnasio Brok"));
        ubicacionService.nuevaUbicacion(new Dojo("Gimnasio Misty"));
        ubicacionService.nuevaUbicacion(new Dojo("Gimnasio Lance"));

        List<Dojo> dojos =  ubicacionService.todosLosDojos();
        assertEquals(3, dojos.size());
    }

    @Test
    public void cuandoPidoTodosLosDojosYHayOtrasUbicacionesMeDevuelveUnaListaConSoloDojos() {
        ubicacionService.nuevaUbicacion(new Dojo("Gimnasio Brok"));
        ubicacionService.nuevaUbicacion(new Guarderia("Viejito"));
        ubicacionService.nuevaUbicacion(new Pueblo("Paleta"));

        List<Dojo> dojos =  ubicacionService.todosLosDojos();
        assertEquals(1, dojos.size());
    }

    @Test
    public void cuandoPidoTodosLosDojosYNoHayDojosMeDevuelveListaVacia() {
        ubicacionService.nuevaUbicacion(new Guarderia("Viejito"));
        ubicacionService.nuevaUbicacion(new Pueblo("Paleta"));

        List<Dojo> dojos =  ubicacionService.todosLosDojos();
        assertEquals(0, dojos.size());
    }
}
