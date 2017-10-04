package ar.edu.unq.epers.bichomon.backend.service.Mongo;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.evento.*;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.data.DataService;
import ar.edu.unq.epers.bichomon.backend.service.data.DataServiceHBN;
import ar.edu.unq.epers.bichomon.backend.service.evento.EventoService;
import ar.edu.unq.epers.bichomon.backend.service.evento.MongoEventoService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.List;

public class MongoEventoServiceTest {

    private EventoService eventoService = new MongoEventoService();
    private Entrenador entrenador1 = new Entrenador("pepe");
    private Ubicacion ubicacion1 = new Dojo("Gym Brook");
    private DataService dataService = new DataServiceHBN();
    private Especie especie = dataService.getEspecie();
    private Bicho bicho = new Bicho(especie);
    private Especie especie2 = dataService.getEspecie();
    private Bicho bichoVencido = new Bicho(especie2);
    private Guarderia guarderia = new Guarderia("villa palito");


    @Before
    public void setUp(){}

    @Test
    public void guardarUbicacionEventoTest() {
        Evento arribo = new Arribo(this.entrenador1,this.ubicacion1);
        this.eventoService.guardarEvento(arribo);
        Assert.assertEquals(this.eventoService.get(arribo.id).mensaje(), arribo.mensaje());
    }
    @Test
    public void getBichoAbandonadoEventoTest(){
        Evento abandono = new Abandono(this.entrenador1,this.bicho,this.guarderia);
        this.eventoService.guardarEvento(abandono);
        Evento getAbandono = this.eventoService.get(abandono.id);
        Assert.assertEquals(getAbandono.mensaje(),this.eventoService.get(abandono.id).mensaje());
    }
    @Test
    public void getCoronacionEventoTest(){
        Dojo dojo = new Dojo();
        Evento coronacion = new Coronacion(this.entrenador1,this.bicho,this.bichoVencido,dojo);
        this.eventoService.guardarEvento(coronacion);
        Evento getCoronacion = this.eventoService.get(coronacion.id);
        Assert.assertEquals(getCoronacion.mensaje(),this.eventoService.get(coronacion.id).mensaje());
    }
    @Test
    public void getCapturaEventoTest(){
        Pueblo pueblo = new Pueblo("Villa Palito");
        Evento captura = new Captura(this.entrenador1,this.bicho,pueblo);
        this.eventoService.guardarEvento(captura);
        Evento getCaptura = this.eventoService.get(captura.id);
        Assert.assertEquals(getCaptura.mensaje(),this.eventoService.get(captura.id).mensaje());
    }

}
