package ar.edu.unq.epers.bichomon.backend.persistence.evento;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.evento.Evento;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.persistence.GenericMongoDAO;

import java.util.ArrayList;
import java.util.List;

public class EventoDAO<T> extends GenericMongoDAO{

    private Class<T> entityType;

    public EventoDAO(Class<T> entityType){
        super( entityType );
    }

    public List<Evento> getByEntrenador(Entrenador entrenador ){
        return find( "{ jugador: # }", entrenador.getNombre() );
    }

    public List<Evento> getByUbicacion( Ubicacion ubicacion ){
        return find( "{ ubicacion: # }", ubicacion.getNombre() );
    }

    public void saveEvento( Evento evento ){
        save( evento );
    }

    public Evento getEvento( String evento ){
        return (Evento) get( evento );
    }
    public List<Evento> getByUbicaciones(List<Ubicacion> conexiones){
    	List<Evento> res = new ArrayList<>();
    	for (Ubicacion conexion : conexiones) {
    		 
    	}
		return res;
    }

}
