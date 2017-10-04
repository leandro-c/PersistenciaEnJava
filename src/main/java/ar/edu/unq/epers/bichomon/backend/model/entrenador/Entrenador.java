package ar.edu.unq.epers.bichomon.backend.model.entrenador;

import ar.edu.unq.epers.bichomon.backend.model.AlgoritmoBusqueda;
import ar.edu.unq.epers.bichomon.backend.model.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.nivel.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.ServiceFactory;
import ar.edu.unq.epers.bichomon.backend.service.nivel.NivelServiceHBN;
import com.google.gson.annotations.Expose;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Entrenador {

    @Expose
    @Id
    private String nombre;
    @Expose
    private int experiencia;
    @Expose
    private int monedas;

    @Expose
    @ManyToOne(cascade= CascadeType.ALL)
    private Nivel nivel;

    @Expose
    @OneToMany(cascade= CascadeType.ALL)
    private List<Bicho> bichos;

    @Expose
    @OneToMany(mappedBy="abandonador", cascade= CascadeType.ALL)
    private List<Bicho> bichosAbandonados;

    @Expose
    @ManyToOne(cascade= CascadeType.ALL)
    private Ubicacion ubicacion;

    @Expose
    private Date ultimaVezQueVioBicho;

    /** constructor sin parametros */
    protected Entrenador(){}

    /** constructor */
    public Entrenador(String nombre) {
        this.nombre = nombre;
        this.experiencia = 0;
        this.bichos = new ArrayList<>();
        this.monedas = 0;
    }

    @Override
    public String toString(){
        return this.nombre;
    }


    public void cobrarMonedas( int monedas ){
        this.monedas += monedas;
    }

    public void pagarMonedas(int monedas) throws Exception {
        if (this.monedas > monedas) {
            this.monedas -= monedas;
        } else {
            throw new Exception("El entrenador no tiene monedas suficientes.");
        }
    }

    /** este metodo explicita la relacion de la persistencia */
    public void setUbicacion( Ubicacion ubicacion) {
        if (this.ubicacion != null) {
            this.ubicacion.removerEntrenador(this);
        }
        this.ubicacion = ubicacion;
        ubicacion.agregarEntrenador(this);
    }

    public Ubicacion getUbicacion(){
        return this.ubicacion;
    }

    public void subirDeNivel(){

    }

    /** geters y seters */
    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public Nivel getNivel(){
        return this.nivel;
    }

    public void setNivel(Nivel nivel){
        this.nivel = nivel;
    }

    public int getExperiencia(){
        return this.experiencia;
    }

    public void setExperiencia(int experiencia){
        this.experiencia = experiencia;
    }

    public boolean equalEntrenador(Entrenador otroEntrenador) {
        return	this.getNombre().equals(otroEntrenador.getNombre()) &&
                this.getNivel() == otroEntrenador.getNivel() &&
                this.getExperiencia() == otroEntrenador.getExperiencia();
    }

    public Bicho capturarBicho() throws Exception {
        boolean busquedaExitosa = new AlgoritmoBusqueda().factorBusqueda(this.ultimaVezQueVioBicho, this.nivel,
                this.ubicacion.poblacion());
        if (busquedaExitosa) {
            this.ganarExperiencia(10);
            Bicho bichoCapturado = this.ubicacion.buscarBichoPara(this);
            this.agregarBicho(bichoCapturado);
            return bichoCapturado;
        }
        return null;
    }

    public List<Bicho> getBichos() {
        return bichos;
    }

    public boolean ultimoBicho(){
        return this.bichos.size() < 2;
    }
    public void abandonarBicho( Bicho bicho ) {
        if (!ultimoBicho()){
            this.ubicacion.abandonarBicho(bicho);
            this.bichos.remove(bicho);
            this.bichosAbandonados.add(bicho);
            bicho.serAbandonado( (Guarderia)this.ubicacion );
            }
        else
            {
            throw new RuntimeException("Hasta las manos");
            }
    }

    public void ganarExperiencia(int puntosDeExperiencia) throws Exception {
        this.experiencia += puntosDeExperiencia;
        this.nivel = nivel.conExperiencia(this.experiencia);
    }

    public void agregarBicho(Bicho bicho) {
        this.bichos.add(bicho);
        bicho.setEntrenador(this);
    }

    public int getMonedas() {
        return monedas;
    }

    public ResultadoCombate retarADuelo(Bicho bicho) throws Exception {
        ResultadoCombate resultadoCombate = this.ubicacion.retarADuelo(bicho);
        this.ganarExperiencia(10);
        return resultadoCombate;
    }
}