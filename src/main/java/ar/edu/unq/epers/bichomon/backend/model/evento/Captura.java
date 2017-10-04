package ar.edu.unq.epers.bichomon.backend.model.evento;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;

public class Captura extends Evento {

    public int bicho;

    public Captura(){}

    public Captura(Entrenador jugador, Bicho bicho, Pueblo pueblo) {
        super(jugador, pueblo);
        this.bicho = bicho.getId();
    }

    @Override
    public String mensaje() {
        return "El entrenador " + jugador + " capturo el bicho " + bicho + " en el pueblo " + ubicacion;
    }
}
