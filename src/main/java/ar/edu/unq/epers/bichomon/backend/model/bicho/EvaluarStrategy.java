package ar.edu.unq.epers.bichomon.backend.model.bicho;

import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

import javax.persistence.*;

@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
public abstract class EvaluarStrategy {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	int id;

	@ManyToOne
	Especie especie;

	public abstract Boolean evaluar(Bicho bicho);
}
