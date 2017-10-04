package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Entity
@DiscriminatorValue(value="dojo")
public class Dojo extends Ubicacion {

    @ManyToMany
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    public List<Bicho> campeones;

    public Dojo(String nombre) {
        super(nombre);
    }

    public Dojo() {
    }

    public Bicho buscarBichoPara(Entrenador entrenador) {
        if (campeones != null) {
            Especie especieParaCrear = this.campeonActual().getEspecie().primeraEvolucion();
            return new Bicho(especieParaCrear);
        }
        return null;
    }

    public Bicho campeonActual() {
        if (this.campeones.size() > 0) return this.campeones.get(campeones.size() - 1);
        else throw new RuntimeException("No hay ningun campeon en el dojo.");
    }

    public void setCampeones(List<Bicho> campeones) {

        this.campeones = campeones;
        campeones.forEach(( c ) -> c.setDojo(this) );
    }


    public List<Bicho> getCampeones() {
        return campeones;
    }

    @Override
    public ResultadoCombate retarADuelo(Bicho retador) {
        // Podría haber tenido un objeto que sea de clase "Duelo" para simplificar.
        ResultadoCombate resultado = new ResultadoCombate();
        Bicho enemigo = this.campeonActual();
        Integer turno = 0;

        while(retador.estaVivo() && enemigo.estaVivo() || turno == 10) {
            resultado.agregarAtaque(retador.atacarA(enemigo));
            resultado.agregarAtaque(enemigo.atacarA(retador));
            turno++;
        }

        Bicho ganador = (enemigo.estaVivo() ? enemigo : retador);
        Bicho perdedor = (enemigo.estaVivo() ? retador : enemigo);
        retador.ganarEnergia(randomGanarEnergia());
        enemigo.ganarEnergia(randomGanarEnergia());
        return ganarDuelo(resultado, ganador, perdedor);
    }

    private ResultadoCombate ganarDuelo(ResultadoCombate resultado, Bicho ganador, Bicho perdedor) {
        this.nuevoCampeon(ganador);
        return resultado.conGanadorYPerdedor(ganador, perdedor);
    }

    private void nuevoCampeon(Bicho ganador) {
        this.campeones.add(ganador);
    }

    private Integer randomGanarEnergia() {
        return new Random().nextInt(5-1) + 1;
    }
}