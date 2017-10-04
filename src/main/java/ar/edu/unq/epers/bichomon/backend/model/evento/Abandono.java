package ar.edu.unq.epers.bichomon.backend.model.evento;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

public class Abandono extends Evento {

    public int abandonado;

    public Abandono(){}

    public Abandono(Entrenador jugador, Bicho abandonado, Guarderia guarderia) {
        super(jugador, guarderia);
        this.abandonado = abandonado.getId();
    }

    @Override
    public String mensaje() {
        return jugador + " abandono al bicho " + abandonado +
                " en la guarderia " + ubicacion;
    }
}
