package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.service.ServiceFactory;
import com.google.gson.annotations.Expose;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type",discriminatorType=DiscriminatorType.STRING)
public abstract class Ubicacion {

    @Expose
    @Id
    private String nombre;

    @Expose
    @Basic
    @Column(name="type", insertable = false, updatable = false)
    private String type;

    /**
     * una ubicacion tiene una lista de entrenadores
     */
    @Expose(serialize = false)
    @OneToMany(cascade= CascadeType.ALL)
    private List<Entrenador> entrenadores;

    public Ubicacion( String nombre ) {

        this.nombre = nombre;
        this.entrenadores = new ArrayList<>();
    }

    /** constructor sin parametros */
    protected Ubicacion(){};

    /** geters y seters */
    public String getNombre(){
        return this.nombre;
    }

    public void setNombre( String nombre ){
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return this.nombre;
    }

    public boolean equalUbicacion(Ubicacion otraUbicacion) {
        return	this.getNombre().equals(otraUbicacion.getNombre()) && this.entrenadores.size() == otraUbicacion.entrenadores.size();
    }

    public abstract Bicho buscarBichoPara(Entrenador entrenador);

    public int poblacion() {
        return 0;
    }

    public List<Entrenador> getEntrenadores() {
        return entrenadores;
    }

    public void setEntrenadores(List<Entrenador> entrenadores) {
        this.entrenadores = entrenadores;
    }

    public void agregarEntrenador( Entrenador entrenador ){
        this.entrenadores.add(entrenador);
    }

    public void removerEntrenador( Entrenador entrenador ) { this.entrenadores.remove(entrenador);}

    /**
     *Devuelvo la cantidad de entrenadores en la ubicacion
     */
    public int getCantidadEntreadores() {
        return this.entrenadores.size();
    }

    public ResultadoCombate retarADuelo(Bicho bicho) { throw new RuntimeException("No se puede luchar en esta ubicacion"); };

    public void abandonarBicho(Bicho bicho) { throw new RuntimeException("No se puede abandonar en esta ubicacion"); };
}
