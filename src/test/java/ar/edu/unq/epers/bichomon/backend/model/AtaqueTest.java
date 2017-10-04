package ar.edu.unq.epers.bichomon.backend.model;

import ar.edu.unq.epers.bichomon.backend.factories.EspecieFactory;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AtaqueTest {

    private Bicho atacante = new Bicho(new EspecieFactory().dinosaurio());
    private Bicho atacado = new Bicho(new EspecieFactory().dinosaurio());

    @Test
    public void cuandoLeDigoAUnAtaqueQueSeRealiceElDanioEsUnValorEntreLaMitadYElTotalDeLaEnergiaActualDelAtacante() throws Exception {
        setDueloData(100,50);

        Ataque ataque = new Ataque(atacante, atacado);

        ataque.realizar();
        Integer energiaAtacante = atacante.getEnergia();
        assertTrue(ataque.getDanio() > energiaAtacante / 2 && ataque.getDanio() <= energiaAtacante );
    }

    private void setDueloData(Integer energiaAtacante, Integer energiaAtacado) {
        atacante.setEnergia(energiaAtacante);
        atacado.setEnergia(energiaAtacado);
    }
}
