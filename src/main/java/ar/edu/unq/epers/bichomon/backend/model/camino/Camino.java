package ar.edu.unq.epers.bichomon.backend.model.camino;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoCamino;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

/**
 * Created by whish on 14/06/17.
 */
public class Camino {

    private TipoCamino tipo;
    private int costo;


    public Camino(TipoCamino tipo, int costo ){
        this.tipo = tipo;
        this.costo = costo;
    }

    public TipoCamino getTipoCamino() {
        return this.tipo;
    }

    public int getCosto(){
        return this.costo;
    }

}
