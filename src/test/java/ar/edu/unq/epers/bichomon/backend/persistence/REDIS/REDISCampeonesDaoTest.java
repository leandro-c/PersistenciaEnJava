package ar.edu.unq.epers.bichomon.backend.persistence.REDIS;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.persistence.cache.REDISCampeonesDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class REDISCampeonesDaoTest {

    private REDISCampeonesDao campeonesDao;
    private Entrenador ash;
    private Entrenador brock;
    private List<Entrenador> entrenadores;

    @Before
    public void crearModelo() {
        this.campeonesDao = new REDISCampeonesDao();
        this.ash = new Entrenador("ash");
        this.brock = new Entrenador("brock");
        this.entrenadores = new ArrayList<Entrenador>();
        this.entrenadores.add(ash);
        this.entrenadores.add(brock);
    }

    @Test
    public void cuandoLeCargoLaListaDeCampeonesALaCacheYLaPidoMEDevuelveDosCampeones(){
        this.campeonesDao.put(entrenadores);

        List<String> expectedNombres = getNombres(Arrays.asList(ash, brock));

        Assert.assertEquals(expectedNombres, getNombres(this.campeonesDao.get()));
        assertEquals(this.campeonesDao.get().size(),2);
    }

    public List<String> getNombres(List<Entrenador> entrenadores) {
        List<String> nombres = new ArrayList<>();
        for (Entrenador entrenador : entrenadores) {
            nombres.add(entrenador.getNombre());
        }
        return nombres;
    }
}
