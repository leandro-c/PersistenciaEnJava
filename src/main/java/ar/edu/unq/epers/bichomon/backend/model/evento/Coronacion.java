package ar.edu.unq.epers.bichomon.backend.model.evento;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

public class Coronacion extends Evento {

    public int campeon;
    public int derrotado;

    public Coronacion(){}

    public Coronacion(Entrenador jugador, Bicho campeon, Bicho derrotado, Dojo dojo) {
        super(jugador, dojo);
        this.campeon = campeon.getId();
        this.derrotado = derrotado.getId();
    }

    @Override
    public String mensaje() {
        return "El entrenador " + jugador +
                " derroto al campeon del dojo " + derrotado +
                " con su bicho " + derrotado + " en el dojo " + ubicacion;
    }
}
