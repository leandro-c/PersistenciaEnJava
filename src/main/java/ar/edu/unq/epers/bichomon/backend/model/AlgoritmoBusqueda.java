package ar.edu.unq.epers.bichomon.backend.model;

import ar.edu.unq.epers.bichomon.backend.model.nivel.Nivel;

import java.util.Date;

public class AlgoritmoBusqueda {
    public boolean factorBusqueda(Date ultimaVezQueVioBicho, Nivel nivel, int poblacion) {
        return factorTiempo(ultimaVezQueVioBicho) * factorNivel(nivel) /
                factorPoblacion(poblacion) * Math.random() > 0.5;
    }

    private double factorPoblacion(int poblacion) {
        return poblacion * 0.5;
    }

    private int factorNivel(Nivel nivel) {
        return nivel.getNumero() * 2;
    }

    private int factorTiempo(Date ultimaVezQueVioBicho) {
        long difference = new Date().getTime() - ultimaVezQueVioBicho.getTime();
        int minutes = (int)difference / 1000 / 60;
        return minutes * 5;
    }
}
