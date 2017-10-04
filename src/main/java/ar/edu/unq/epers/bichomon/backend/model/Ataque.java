package ar.edu.unq.epers.bichomon.backend.model;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

import javax.persistence.*;
import java.util.Random;

@Entity
public class Ataque {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @ManyToOne
    private Bicho atacante;

    @ManyToOne
    private Bicho atacado;

    private double danio;

    public Ataque(){}

    public Ataque(Bicho atacante, Bicho atacado) {
        this.atacante = atacante;
        this.atacado = atacado;
    }

    public void realizar() {
        double random = new Random().nextDouble() * (1 - 0.5) + 0.5;
        this.danio = atacante.getEnergia() * random;
        if (atacante.estaVivo()) this.atacado.restarEnergia(this.danio);
    }

    public double getDanio() {
        return danio;
    }
}
