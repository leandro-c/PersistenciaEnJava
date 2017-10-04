package ar.edu.unq.epers.bichomon.backend.persistence.HBN;

import static ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho.ELECTRICIDAD;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import ar.edu.unq.epers.bichomon.backend.persistence.bicho.HBNBichoDAO;
import ar.edu.unq.epers.bichomon.backend.service.data.DataServiceHBN;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.persistence.HBNGenericDAO;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import ar.edu.unq.epers.bichomon.backend.service.runner.SessionFactoryProvider;


import static org.mockito.Mockito.*;

public class HBNBichoDAOTest {
	
	private HBNBichoDAO daoBicho;
	private Especie especie;
	private Bicho bicho;
	@Before
    public void setUp() {
        this.especie = new Especie("squartle", ELECTRICIDAD);
        this.especie.setAltura(20);
        this.especie.setPeso(5);
        this.especie.setCantidadBichos(3);

        this.bicho = new Bicho(especie);
        this.bicho.setCantVictorias(5);
        this.bicho.setEdad(2);
        this.bicho.setEnergia(10);
        this.bicho.setNivel(10);
        this.daoBicho = new HBNBichoDAO();
    }
	@After
	public void cleanup() {
        new DataServiceHBN().clearDb();
	}
	@Test
	public void puedeEvolucionarTest(){
		this.especie = mock(Especie.class);
		this.bicho.setEspecie(especie);
		Especie evolucion = mock(Especie.class);
		when(this.especie.puedoEvolucionar(this.bicho)).thenReturn(true);
		when(bicho.getEspecie().getEvolucion()).thenReturn(evolucion);
		bicho.evolucionar();
		assertEquals(evolucion, bicho.getEspecie());
	}
	@Test
	public void noPuedeEvolucionarTest(){
		this.especie = mock(Especie.class);
		this.bicho.setEspecie(especie);
		when(this.especie.puedoEvolucionar(this.bicho)).thenReturn(false);
		bicho.evolucionar();
		assertEquals(this.especie, bicho.getEspecie());
	}
	@Test
	public void crearBichoTest(){
		Runner.runInSession(() -> {
			int cantidadBichos = this.daoBicho.getAll().size();
			this.daoBicho.guardarBicho(this.bicho);
			assertEquals(cantidadBichos +1, this.daoBicho.getAll().size());
			return null;
		});

	}
	@Test
	public void modificarBichoTest(){
		Runner.runInSession(() -> {
			this.bicho.setEdad(0);
			this.daoBicho.guardarBicho(bicho);

			this.bicho.setEdad(1);
			this.daoBicho.update(this.bicho);
			assertEquals(this.bicho.getEdad(),1);
			return null;
		});

	}
	@Test
	public void eleminarBichoTest(){

	}

	public void tieneEvolucionTest() {
	   Bicho bicho = new Bicho(this.especie);
	   Especie especieMock = mock(Especie.class);
	   this.especie.setEvolucion(especieMock);

	   bicho.evolucionar();
	   assertEquals(this.especie.getEvolucion(), bicho.getEspecie());
	}
}
