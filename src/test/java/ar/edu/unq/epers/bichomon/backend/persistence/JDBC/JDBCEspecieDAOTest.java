package ar.edu.unq.epers.bichomon.backend.persistence.JDBC;

import ar.edu.unq.epers.bichomon.backend.persistence.especie.JDBCEspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.service.data.DataService;
import ar.edu.unq.epers.bichomon.backend.service.data.DataServiceJDBC;
import org.junit.Before;
import org.junit.Test;

import static ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho.ELECTRICIDAD;
import static org.junit.Assert.*;

public class JDBCEspecieDAOTest {

    private JDBCEspecieDAO dao = new JDBCEspecieDAO();
    private Especie especie;
    private DataService dataService = new DataServiceJDBC();

    @Before
    public void crearModelo() {
        this.dataService.eliminarDatos();
        this.especie = new Especie("Pikachu", ELECTRICIDAD);
        this.especie.setAltura(20);
        this.especie.setPeso(5);
        this.especie.setCantidadBichos(3);
    }

    @Test
    public void cuandoCreoYrecuperoUnaEspecieSonObjetosSimilares() {
        this.dao.crearEspecie(this.especie);

        Especie otraEspecie = this.dao.getEspecie(this.especie.getNombre());
        assertTrue(this.especie.equalEspecie(otraEspecie));
    }

    @Test
    public void alGuardarLeAgregoUnBichoYSuCantidadDeBichosAumenta() {
        this.especie.setCantidadBichos(1);
        this.dao.crearEspecie(this.especie);
        this.dao.agregarBicho(this.especie.getNombre());

        Especie especiePersistida = this.dao.getEspecie(this.especie.getNombre());
        assertEquals(2, especiePersistida.getCantidadBichos());
    }

}