package ar.edu.unq.epers.bichomon.backend.service.HBN;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.service.ServiceFactory;
import ar.edu.unq.epers.bichomon.backend.service.data.DataService;
import ar.edu.unq.epers.bichomon.backend.service.data.DataServiceHBN;
import ar.edu.unq.epers.bichomon.backend.service.entrenador.EntrenadorService;
import ar.edu.unq.epers.bichomon.backend.service.especie.EspecieService;
import ar.edu.unq.epers.bichomon.backend.service.especie.HBNEspecieService;
import ar.edu.unq.epers.bichomon.backend.service.runner.SessionFactoryProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho.ELECTRICIDAD;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HBNEspecieServiceTest {

    private Especie especie;
    private EspecieService especieService = new HBNEspecieService();
    private DataService dataService = new DataServiceHBN();
    Especie pikachu;
    Especie charmander;
    Especie bulbasaur ;
    Especie pidgey;

    @Before
    public void crearModelo() {
        this.especie = new Especie("Pikachu", ELECTRICIDAD);
        this.especie.setAltura(20);
        this.especie.setPeso(5);
        this.especie.setCantidadBichos(3);
    }
    @After
    public void tearDown() { new DataServiceHBN().clearDb(); }

    @Test
    public void al_guardar_y_luego_recuperar_se_obtiene_objetos_similares() {
        this.especieService.crearEspecie(this.especie);

        Especie otraEspecie = this.especieService.getEspecie(this.especie.getNombre());
        assertTrue(this.especie.equalEspecie(otraEspecie));
    }

    @Test
    public void al_guardar_le_agrego_un_bicho_y_su_cantidad_de_bichos_aumenta() {
        this.especie.setCantidadBichos(1);
        this.especieService.crearEspecie(this.especie);
        this.especieService.crearBicho(this.especie.getNombre(), "Pepe");

        Especie especiePersistida = this.especieService.getEspecie(this.especie.getNombre());
        assertEquals(2, especiePersistida.getCantidadBichos());
    }

    @Test
    public void SiPidoLasEspeciesPopularesMeDevuelveUnaListaConLasQueTienenMasBichos(){
        this.crearEspecies();
        List<Especie> especies = new ArrayList<>();
        especies.add(pidgey);
        especies.add(bulbasaur);
        especies.add(charmander);
        especies.add(pikachu);

        assertEquals(especies.size(), especieService.populares().size());
    }

    public void crearEspecies() {
        Especie pikachu = new Especie("Pikachu", TipoBicho.ELECTRICIDAD);
        Especie charmander = new Especie("Charmander", TipoBicho.FUEGO);
        Especie bulbasaur = new Especie("Bulbasaur", TipoBicho.PLANTA);
        Especie pidgey = new Especie("Pidgey", TipoBicho.AIRE);

        Bicho pikabichu = new Bicho(pikachu);
        Bicho charmanchu = new Bicho(charmander);
        Bicho bulbachu = new Bicho(bulbasaur);
        Bicho pichu = new Bicho(pidgey); //engañoso

        Entrenador entrenador1 = new Entrenador("Sabado");
        Entrenador entrenador2 = new Entrenador("Domingo");
        Entrenador entrenador3 = new Entrenador("Lunes");
        Entrenador entrenador4 = new Entrenador("Martes");
        Entrenador entrenador5 = new Entrenador("Miercoles");
        Entrenador entrenador6 = new Entrenador("Jueves");
        Entrenador entrenador7 = new Entrenador("Viernes");

        entrenador1.agregarBicho(pichu);
        entrenador2.agregarBicho(pichu);
        entrenador3.agregarBicho(pichu);
        entrenador4.agregarBicho(bulbachu);
        entrenador5.agregarBicho(bulbachu);
        entrenador6.agregarBicho(charmanchu);
        entrenador7.agregarBicho(pikabichu);

        /*EntrenadorService entrenadorService = new ServiceFactory().getEntrenadorService();
        entrenadorService.crearEntrenador(entrenador1);
        entrenadorService.crearEntrenador(entrenador2);
        entrenadorService.crearEntrenador(entrenador3);
        entrenadorService.crearEntrenador(entrenador4);
        entrenadorService.crearEntrenador(entrenador5);
        entrenadorService.crearEntrenador(entrenador6);
        entrenadorService.crearEntrenador(entrenador7);*/

        especieService.crearEspecie(pikachu);
        especieService.crearEspecie(bulbasaur);
        especieService.crearEspecie(charmander);
        especieService.crearEspecie(pidgey);
    }
}

