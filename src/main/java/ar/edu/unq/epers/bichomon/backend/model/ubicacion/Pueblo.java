package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.service.ServiceFactory;

import javax.persistence.*;
import java.util.*;

@Entity
@DiscriminatorValue(value="pueblo")
public class Pueblo extends Ubicacion {

    @ElementCollection
    public Map<Especie, Integer> especiesHabitantes;

    public Pueblo( String nombre ){
        super( nombre );
    }

    public Pueblo(){}

    @Override
    public Bicho buscarBichoPara(Entrenador entrenador) {
        return new Bicho(obtenerEspecieRandom());
    }

    private Especie obtenerEspecieRandom() {
        Set<Especie> especies = this.especiesHabitantes.keySet();
        Random r = new Random();
        double random = Math.random() * 100;

        for (Especie especie : especies) {
            if ((random -= this.especiesHabitantes.get(especie)) < 0) return especie;
        }
        return null;
    }

    @Override
    public int poblacion() {
        return this.getEntrenadores().size();
    }

    public void setEspeciesHabitantes(Map<Especie, Integer> especiesHabitantes) {
        this.especiesHabitantes = especiesHabitantes;
    }
}
