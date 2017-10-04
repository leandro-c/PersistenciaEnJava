package ar.edu.unq.epers.bichomon.backend.model.bicho;

public class EvaluarPorEvolucionFinal extends EvaluarStrategy {
	//evalua que no tenga mas evolucion
	@Override
	public Boolean evaluar(Bicho bicho) {
		return false;
	}

}
