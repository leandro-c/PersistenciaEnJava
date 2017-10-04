package ar.edu.unq.epers.bichomon.backend.model.nivel;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import javax.persistence.*;
import java.util.List;

@Entity
public class Nivel {

    @Id
    private int numero;

    private int experienciaMaxima;
    private int bichosMaximos;

    @OneToOne(cascade = CascadeType.ALL)
    private Nivel nivelProximo;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Entrenador> entrenadores;

    protected Nivel(){}

    public Nivel(int numero, int experienciaMaxima, int bichosMaximos){
        this.numero = numero;
        this.experienciaMaxima = experienciaMaxima;
        this.bichosMaximos = bichosMaximos;
    }

    public void setExperienciaMaxima(int experienciaMaxima) {
        this.experienciaMaxima = experienciaMaxima;
    }

    public int getNumero() {
        return numero;
    }

    public void setNivelProximo(Nivel nivelProximo) {
        this.nivelProximo = nivelProximo;
    }

    public Nivel getNivelProximo() {
        return nivelProximo;
    }

    public List<Entrenador> getEntrenadores() {
        return entrenadores;
    }

    public void setEntrenadores(List<Entrenador> entrenadores) {
        this.entrenadores = entrenadores;
    }

    public int getExperienciaMaxima() {
        return experienciaMaxima;
    }

    public Nivel conExperiencia(int experiencia) throws Exception {
        if (experiencia > this.experienciaMaxima)
            try {
                return this.nivelProximo.conExperiencia(experiencia);
            } catch (NullPointerException e) {
                throw new Exception("No tiene mas niveles para subir");
            }
        return this;
    }
}
