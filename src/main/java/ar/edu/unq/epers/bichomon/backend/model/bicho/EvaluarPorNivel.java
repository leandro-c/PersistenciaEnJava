package ar.edu.unq.epers.bichomon.backend.model.bicho;

public class EvaluarPorNivel extends EvaluarStrategy {
	private Integer nivel;

	@Override
	public Boolean evaluar(Bicho bicho) {
		
		return bicho.getNivel() >  nivel;
	}

}
