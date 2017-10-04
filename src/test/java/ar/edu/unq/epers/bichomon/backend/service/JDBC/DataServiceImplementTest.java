package ar.edu.unq.epers.bichomon.backend.service.JDBC;

import ar.edu.unq.epers.bichomon.backend.factories.EspecieFactory;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.service.data.DataService;
import ar.edu.unq.epers.bichomon.backend.service.data.DataServiceJDBC;
import ar.edu.unq.epers.bichomon.backend.service.especie.EspecieService;
import ar.edu.unq.epers.bichomon.backend.service.especie.JDBCEspecieService;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DataServiceImplementTest {

    private DataService service = new DataServiceJDBC();
    private EspecieService especieService = new JDBCEspecieService();
    EspecieFactory especieFactory = new EspecieFactory();

    @Test
    public void siCreoNuevaDataLaBaseDeDatosVaATenerNuevosDatos() {
        this.service.eliminarDatos();
        this.service.crearSetDatosIniciales();

        ArrayList<Especie> expectedArray = new ArrayList<>();
        expectedArray.add(especieFactory.tortuga());
        expectedArray.add(especieFactory.dinosaurio());
        expectedArray.add(especieFactory.dragon());

        ArrayList<Especie> resultArray = new ArrayList<>(this.especieService.getAllEspecies());

        assertTrue(todasEspeciesIguales(expectedArray, resultArray));
    }

    private boolean todasEspeciesIguales(ArrayList<Especie> expectedEspecies, ArrayList<Especie> actualEspecies) {
        boolean result = true;
        for(int i = 0; i < expectedEspecies.size(); i++){
            result = result && containsEspecie(actualEspecies, expectedEspecies.get(i));
        }
        return result;
    }

    private boolean containsEspecie(ArrayList<Especie> listaEspecies, Especie especieABuscar) {
        boolean result = false;
        for(Especie especie : listaEspecies) {
            result = result || especie.equalEspecie(especieABuscar);
        }
        return result;
    }
    @Test
    public void TraerBichoEspecie(){
 	   this.service.eliminarDatos();
 	   this.service.crearSetDatosIniciales();

 	   this.especieService.crearBicho("Dinosaurio", "Charizard");
 	   int cantBichos = this.especieService.getEspecie("Dinosaurio").getCantidadBichos();
 	   assertEquals(cantBichos,1);
    }
}
