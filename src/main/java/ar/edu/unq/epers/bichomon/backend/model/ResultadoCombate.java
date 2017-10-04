package ar.edu.unq.epers.bichomon.backend.model;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ResultadoCombate {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @ManyToOne
    private Dojo dojo;

    @ManyToOne(cascade= CascadeType.ALL)
    private Bicho ganador;

    @OneToMany
    private List<Ataque> ataques;

    @ManyToOne
    private Bicho perdedor;

    public ResultadoCombate(){
        this.ataques = new ArrayList<>();
    }

    public List<Ataque> getAtaques() {
        return ataques;
    }

    public void setAtaques(List<Ataque> ataques) {
        this.ataques = ataques;
    }

    public void agregarAtaque(Ataque ataque) {
        this.ataques.add(ataque);
    }

    public ResultadoCombate conGanadorYPerdedor(Bicho ganador, Bicho perdedor) {
        this.ganador = ganador;
        this.perdedor = perdedor;
        return this;
    }

    public Bicho getGanador() {
        return ganador;
    }

    public Bicho getPerdedor() {
        return this.perdedor;
    }
}
