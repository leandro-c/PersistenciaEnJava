package ar.edu.unq.epers.bichomon.backend.persistence.N4J;

import ar.edu.unq.epers.bichomon.backend.model.camino.Camino;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.persistence.camino.N4JCaminoDAO;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static ar.edu.unq.epers.bichomon.backend.model.especie.TipoCamino.*;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class N4JCaminoDAOTest {

    private N4JCaminoDAO dao;
    private Ubicacion ubicacion1;
    private Ubicacion ubicacion2;
    private Ubicacion ubicacion3;
    private Ubicacion ubicacion4;
    private Ubicacion ubicacion5;
    private Ubicacion ubicacion6;
    private Ubicacion ubicacionAislada;
    private Camino caminoTerrestre;
    private Camino caminoMaritimo;
    private Camino caminoAereo;

    @Before
    public void prepare() {
        this.dao = new N4JCaminoDAO();
        this.ubicacion1 = new Dojo("Gimnasio de Brock");
        this.ubicacion2 = new Dojo("Gimnasio de Misty");
        this.ubicacion3 = new Guarderia("Profesor Oak");
        this.ubicacion4 = new Pueblo("Pueblo paleta");
        this.ubicacion5 = new Dojo("Gimnasio Pikachu");
        this.ubicacion6 = new Guarderia("Guarderia de Jenny");
        this.ubicacionAislada = new Pueblo( "Lalomaelor");

        this.caminoTerrestre = new Camino( TERRESTRE, 1 );
        this.caminoAereo = new Camino( AEREO, 3 );
        this.caminoMaritimo = new Camino( MARITIMO, 5 );

        this.dao.clearDb();
    }

    @Test
    public void crearUbicacion() {
        crearUbicaciones();
        String nombreUbicacionRecuperada = this.dao.getUbicacion(ubicacion1);

        assertEquals(nombreUbicacionRecuperada, this.ubicacion1.getNombre());
    }

    @Test
    public void cuandoLePidoLosConectadosAlGimnasioDeBrockMeDevuelveElGimnasioDeMisty(){
        crearUbicaciones();
        List<String> conectados = this.dao.conectados(this.ubicacion1,this.caminoTerrestre.getTipoCamino());
        assertEquals(2, conectados.size());

        this.dao.clearDb();
        assertTrue(conectados.contains("Gimnasio de Misty"));
    }

    @Test
    public void cuandoLePidoLosConectadosPorAireAlGimnasioDeBrockDevuelveUnaListaVacia(){
        crearUbicaciones();
        List<String> conectados = this.dao.conectados(this.ubicacion1,caminoAereo.getTipoCamino());
        assertEquals(0, conectados.size());
    }

    private void crearUbicaciones() {
        this.dao.crearUbicacion(this.ubicacion1);
        this.dao.crearUbicacion(this.ubicacion2);
        this.dao.crearUbicacion(this.ubicacion3);
        this.dao.crearUbicacion(this.ubicacion4);
        this.dao.crearUbicacion(this.ubicacion5);
        this.dao.crearUbicacion(this.ubicacion6);
        this.dao.crearUbicacion(this.ubicacionAislada);

        this.dao.conectarUbicaciones(this.ubicacion1,this.ubicacion2,caminoTerrestre);
        this.dao.conectarUbicaciones(this.ubicacion1,this.ubicacion6,caminoTerrestre);
        this.dao.conectarUbicaciones(this.ubicacion2,this.ubicacion3,caminoAereo);
        this.dao.conectarUbicaciones(this.ubicacion3,this.ubicacion4,caminoMaritimo);
        this.dao.conectarUbicaciones(this.ubicacion2,this.ubicacion4,caminoTerrestre);
        this.dao.conectarUbicaciones(this.ubicacion2,this.ubicacion4,caminoAereo);
    }

    @Test
    public void cuandoLePidoElCostoDirectoHaciaUnaUbicacionPorCaminoTerrestreMeDevuelve1() {
        crearUbicaciones();

        int costoObtenido = this.dao.costoDirecto(ubicacion3, ubicacion4);
        assertEquals(costoObtenido, 5);
    }


    @Test
    public void cuandoLePidoElCaminoMasCortoAUnaUbicacionMeDevuelveElMasCorto() {
        crearUbicaciones();

        int caminoMasCorto = this.dao.caminoMasCorto(ubicacion1, ubicacion4);
        assertEquals(caminoMasCorto, 4);
    }

    @Test
    public void cuandoPidoElCaminoMasBaratoAunaUbicacionMeDevuelveElMasBarato(){
        crearUbicaciones();

        int caminoMasBarato = this.dao.caminoMasBarato(ubicacion2, ubicacion4);
        assertEquals(caminoMasBarato, 1);
    }

    @Test
    public void cuandoPreguntoSiExisteCaminoAunaUbicacionAisaladaMeDevuelveFalse(){
        crearUbicaciones();

        assertFalse(this.dao.conectados(ubicacion1, ubicacionAislada));
    }

    @Test
    public void cuandoPreguntoSiExisteCaminoAunaUbicacionConectadaMeDevuelvetrue(){
        crearUbicaciones();

        assertTrue(this.dao.conectados(ubicacion1, ubicacion4));
    }
}
