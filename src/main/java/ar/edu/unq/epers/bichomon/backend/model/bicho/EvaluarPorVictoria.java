package ar.edu.unq.epers.bichomon.backend.model.bicho;

public class EvaluarPorVictoria extends EvaluarStrategy{

	@Override
	public Boolean evaluar(Bicho bicho) {
		return bicho.getCantVictorias() > 10 ;
	}
	

}
