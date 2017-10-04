package ar.edu.unq.epers.bichomon.backend.persistence.HBN;

import static ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho.ELECTRICIDAD;
import static org.junit.Assert.assertTrue;

import ar.edu.unq.epers.bichomon.backend.persistence.especie.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.service.data.DataServiceHBN;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.persistence.especie.HBNEspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.persistence.HBNGenericDAO;
import ar.edu.unq.epers.bichomon.backend.service.runner.SessionFactoryProvider;

public class HBNEspecieDAOTest {

	private EspecieDAO daoEspecie = new HBNEspecieDAO();
	private HBNGenericDAO testService;
	private Especie especie;
	@Before
    public void setUp() {
        this.especie = new Especie("squartle", ELECTRICIDAD);
        this.especie.setAltura(20);
        this.especie.setPeso(5);
        this.especie.setCantidadBichos(3);
    }
	@After
	public void cleanup() {
        new DataServiceHBN().clearDb();
	}

	@Test
    public void tieneEvolucionTest() {
        assertTrue(this.daoEspecie.tengoEvolucion(this.especie));
    }

}
