package ar.edu.unq.epers.bichomon.backend.model.evento;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

public class Arribo extends Evento {

    public Arribo(){ super(); }

    public Arribo(Entrenador jugador, Ubicacion ubicacion) {
        super(jugador, ubicacion);
    }

    @Override
    public String mensaje() {
        return "El jugador " + jugador + " arribo a la ubicacion " + ubicacion;
    }
}
