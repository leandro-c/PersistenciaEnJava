package ar.edu.unq.epers.bichomon.backend.service.mapa;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.camino.Camino;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

import java.util.List;

public interface MapaService {


    void crearUbicacion( Ubicacion ubicacion );

    void conectarUbicaciones( String origen, String destino , Camino camino );

    List<Ubicacion> conectados(String ubicacion, String tipoCamino );
    List<Ubicacion> conectados(Ubicacion ubicacion);

    void moverMasCorto(String strEntrenador, String strUbicacion);
    /** Cambia al entrenador desde su ubicacion actual a la espesificada por parametro */
    void mover( String entrenador, String ubicacion ) throws Exception;

    /** Devuelve la cantidad de entrenadores de dicha localizacion */
    int cantidadEntrenadores( String ubicacion );

    /** Retorna el actual campeon del Dojo */
    Bicho campeon( String dojo );

    /** Retorna el bicho que haya sido campeon por mas tiempo en el dojo */
    Bicho campeonHitorico( String dojo );

    /** Arroje una excepcion UbicacionMuyLejana si no es posible
     * llegar desde la actual ubicación del entrenador a la nueva por medio de un camino. */

}
