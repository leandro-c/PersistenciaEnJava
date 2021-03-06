package ar.edu.unq.epers.bichomon.backend.model.especie;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.bicho.EvaluarPorNivel;
import ar.edu.unq.epers.bichomon.backend.model.bicho.EvaluarStrategy;

import javax.persistence.*;
import java.util.List;

/**
 * Representa una {@link Especie} de bicho.
 * 
 * @author Charly Backend
 */
@Entity
public class Especie {

	@Id
	private String nombre;
	private int altura;
	private int peso;
	private TipoBicho tipo;
	private int energiaInicial;
	private String urlFoto;
	private int cantidadBichos;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Bicho> bichos;

	@OneToOne(cascade= CascadeType.ALL)
	Especie evolucion;

    @OneToOne(mappedBy="evolucion", cascade= CascadeType.ALL)
    Especie prevolucion;

	@OneToMany(cascade = CascadeType.ALL)
    private List<EvaluarStrategy> condiciones;

	public Especie(){}

	public Especie getEvolucion() {
		return evolucion;
	}

	public void setEvolucion(Especie evolucion) {
		this.evolucion = evolucion;
	}

	public Especie(String nombre, TipoBicho tipo) {
		this.nombre = nombre;
		this.tipo = tipo;
	}
	
	/**
	 * @return el nombre de la especie (por ejemplo: Perromon)
	 */
	public String getNombre() {
		return this.nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * @return la altura de todos los bichos de esta especie
	 */
	public int getAltura() {
		return this.altura;
	}
	public void setAltura(int altura) {
		this.altura = altura;
	}
	
	/**
	 * @return el peso de todos los bichos de esta especie
	 */
	public int getPeso() {
		return this.peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	
	/**
	 * @return una url que apunta al un recurso imagen el cual será
	 * utilizado para mostrar un thumbnail del bichomon por el frontend.
	 */
	public String getUrlFoto() {
		return this.urlFoto;
	}
	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}
	
	/**
	 * @return la cantidad de energia de poder iniciales para los bichos
	 * de esta especie.
	 */
	public int getEnergiaInicial() {
		return this.energiaInicial;
	}
	public void setEnergiaIncial(int energiaInicial) {
		this.energiaInicial = energiaInicial;
	}

	/**
	 * @return el tipo de todos los bichos de esta especie
	 */
	public TipoBicho getTipo() {
		return this.tipo;
	}
	public void setTipo(TipoBicho tipo) {
		this.tipo = tipo;
	}
	
	/**
	 * @return la cantidad de bichos que se han creado para esta
	 * especie.
	 */
	public int getCantidadBichos() {
		return this.cantidadBichos;
	}
	public void setCantidadBichos(int i) {
		this.cantidadBichos = i;
	}

	public boolean equalEspecie(Especie otraEspecie) {
		return	this.getNombre().equals(otraEspecie.getNombre()) &&
				this.getPeso() == otraEspecie.getPeso() &&
				this.getAltura() == otraEspecie.getAltura();
	}

	public Especie getPrevolucion() {
		return prevolucion;
	}

	public Especie primeraEvolucion() {
		Especie primeraEvolucion = this;
		while (primeraEvolucion.getPrevolucion() != null) {
			primeraEvolucion = primeraEvolucion.getPrevolucion();
		}
		return primeraEvolucion;
	}
	/**
	 * @return itera poor la lista de las condiciones para ver si puede evolucionar por una de ellas.
	 */
	public Boolean puedoEvolucionar(Bicho bicho){
		return condiciones.stream().allMatch(condicion ->  condicion.evaluar(bicho));
	}

	public boolean tengoEvolucion() {
		return this.evolucion != null;
	}
}
