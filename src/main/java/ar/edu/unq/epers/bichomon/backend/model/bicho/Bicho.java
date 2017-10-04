package ar.edu.unq.epers.bichomon.backend.model.bicho;

import ar.edu.unq.epers.bichomon.backend.model.Ataque;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.List;
import java.util.Random;

/**
 * Un {@link Bicho} existente en el sistema, el mismo tiene un nombre
 * y pertenece a una {@link Especie} en particular.
 * 
 * @author Charly Backend
 */
@Entity
public class Bicho {

	@Expose
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Expose
	@ManyToOne(cascade= CascadeType.ALL)
	private Especie especie;
	@Expose
	private int energia;
	@Expose
	private int edad;
	@Expose
	private int nivel;
	@Expose
	private int cantVictorias;

	@Expose(serialize = false)
	@ManyToOne(cascade= CascadeType.ALL, fetch = FetchType.LAZY)
	private Entrenador entrenador;

	@Expose(serialize = false)
	@ManyToOne(cascade= CascadeType.ALL)
    private Entrenador abandonador;

	@Expose(serialize = false)
	@ManyToOne(cascade= CascadeType.ALL)
	private Guarderia guarderia;

	@Expose(serialize = false)
	@ManyToOne(cascade= CascadeType.ALL)
	private Dojo dojo;

	@Expose(serialize = false)
	@OneToMany(cascade= CascadeType.ALL)
	List<Entrenador> entrenadoresALosQuePertenecio;

	public Bicho(Especie especie) {
		this.especie = especie;
		this.edad = 0;
		this.energia = 100;
		this.nivel = 1;
	}

	public int getId() {
		return this.id;
	}

	public Bicho(){}

	public Especie getEspecie() {
		return this.especie;
	}
	
	/**
	 * @return la cantidad de puntos de energia de este bicho en
	 * particular. Dicha cantidad crecerá (o decrecerá) conforme
	 * a este bicho participe en combates contra otros bichomones.
	 */
	public int getEnergia() {
		return this.energia;
	}

	public void setEnergia(int energia) {
		this.energia = energia;
	}

	public void evolucionar(){
		if(this.especie.puedoEvolucionar(this)){
			this.especie = this.especie.getEvolucion();
		}
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public int getNivel() {
		return nivel;
	}

	public boolean pertenecioA(Entrenador entrenador) {
		return entrenadoresALosQuePertenecio.contains(entrenador);
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public Entrenador getEntrenador(){
	    return this.entrenador;
    }

	public void setEntrenador( Entrenador entrenador){
	    this.entrenador = entrenador;
    }

	public int getCantVictorias() {
		return cantVictorias;
	}

	public void setCantVictorias(int cantVictorias) {
		this.cantVictorias = cantVictorias;
	}

    public Entrenador getAbandonador(){
        return this.abandonador;
    }

    public void setAbandonador( Entrenador abandonador ){
        this.abandonador = abandonador;
    }

	/**
	 * Al abandonar un bicho rompo la relacion de entrenador y agrego una relacion de abandono
	 */
	public void serAbandonado(Guarderia guarderia) {
		setAbandonador(getEntrenador());
		setEntrenador(null);
		setGuarderia(guarderia);
	}

	public void setEspecie(Especie especie) {
		this.especie = especie;
	}

	public void setEntrenadoresALosQuePertenecio(List<Entrenador> yaPertenecio) {
		this.entrenadoresALosQuePertenecio = yaPertenecio;
	}

	public Ataque atacarA(Bicho enemigo) {
    	Ataque ataque = new Ataque(this, enemigo);
    	ataque.realizar();
    	return ataque;
	}

	public void restarEnergia(double energiaARestar) {
		this.energia -= energiaARestar;
	}

	public boolean estaVivo() {
		return this.energia > 0;
	}

	public boolean estaMuerto() {
		return ! this.estaVivo();
	}

	public void ganarEnergia(Integer energia) {
		this.energia += energia;
	}

	public void setGuarderia(Guarderia guarderia) {
		this.guarderia = guarderia;
	}

	public void setDojo(Dojo dojo) {
		this.dojo = dojo;
	}
}
