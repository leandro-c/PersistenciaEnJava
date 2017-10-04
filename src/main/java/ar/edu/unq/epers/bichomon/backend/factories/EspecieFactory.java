package ar.edu.unq.epers.bichomon.backend.factories;

import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

import static ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho.*;

public class EspecieFactory {

        public Especie tortuga() {
            return new Especie("Tortuga", AGUA);
        }

        public Especie dinosaurio() {
            return new Especie("Dinosaurio", PLANTA);
        }

        public Especie dragon() {
            return new Especie("Dragon", FUEGO);
        }

}
