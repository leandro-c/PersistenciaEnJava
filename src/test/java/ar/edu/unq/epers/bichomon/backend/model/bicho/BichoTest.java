package ar.edu.unq.epers.bichomon.backend.model.bicho;

import ar.edu.unq.epers.bichomon.backend.factories.EspecieFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BichoTest {


    @Before
    public void setup() {
    }

    @Test
    public void cuandoLeDigoAUnBichoQueAtaqueAOtroLeRestaEnergia(){
        Bicho retador = crearBicho();
        Bicho enemigo = crearBicho();
        retador.setEnergia(100);
        enemigo.setEnergia(50);

        retador.atacarA(enemigo);

        assertTrue(enemigo.getEnergia() < 50);
    }

    @Test
    public void unBichoCon1DeEnergiaEstaVivo() {
        Bicho bicho = crearBicho();
        bicho.setEnergia(1);

        assertTrue(bicho.estaVivo());
    }


    @Test
    public void unBichoCon0DeEnergiaNOEstaVivo() {
        Bicho bicho = crearBicho();
        bicho.setEnergia(0);

        assertFalse(bicho.estaVivo());
    }

    private Bicho crearBicho() { return new Bicho(new EspecieFactory().dinosaurio()); }
}
