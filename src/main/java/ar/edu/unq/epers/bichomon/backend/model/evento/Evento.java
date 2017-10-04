package ar.edu.unq.epers.bichomon.backend.model.evento;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.time.LocalDate;
import java.util.Date;

import java.util.Date;

@JsonTypeInfo(use= JsonTypeInfo.Id.CLASS, property="_class")
public abstract class Evento {

    @MongoId
    @MongoObjectId
    public String id;
    public String jugador;
    public Date fecha;
    public String ubicacion;

    public Evento(Entrenador jugador, Ubicacion ubicacion) {
        this.ubicacion = ubicacion.getNombre();
        this.jugador = jugador.getNombre();
        this.fecha = new Date();
    }

    public Evento() {}

    public abstract String mensaje();
}
