package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import javax.persistence.*;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Entity
@DiscriminatorValue(value="guarderia")
public class Guarderia extends Ubicacion {

    /**
     * una guarderia tiene una lista de bichos abandonados
     */
    @OneToMany(mappedBy="guarderia", cascade= CascadeType.ALL)
    private List<Bicho> bichosAbandonados;

    public Guarderia(String nombre ){
        super(nombre);
    }

    public Guarderia(){}

    @Override
    public Bicho buscarBichoPara(Entrenador entrenador) {
        List<Bicho> bichosPosibles = bichosAbandonados.stream()
                .filter(p -> !p.pertenecioA(entrenador)).collect(Collectors.toList());
        if (bichosPosibles.size() > 0) return getRandom(bichosPosibles);
        return null;
    }

    private Bicho getRandom(List<Bicho> bichos) {
        return bichos.get(new Random().nextInt(bichos.size()));
    }

    protected Bicho elegirOSeguirBuscando(Bicho bicho, Entrenador entrenador) {
        if (bicho.pertenecioA(entrenador)) return buscarBichoPara(entrenador);
        else return bicho;
    }

    /**
     *Un bicho puede ser abandonado solo en la guarderia
     */

    public void abandonarBicho( Bicho bicho ){
        this.bichosAbandonados.add( bicho );
    }

    public void setAbandonados(List<Bicho> abandonados) {
        this.bichosAbandonados = abandonados;
    }
}
