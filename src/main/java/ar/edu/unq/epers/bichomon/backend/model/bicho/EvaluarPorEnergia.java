package ar.edu.unq.epers.bichomon.backend.model.bicho;

public class EvaluarPorEnergia extends EvaluarStrategy {
	private Integer energy;

	@Override
	public Boolean evaluar(Bicho bicho) {
		// TODO Auto-generated method stub		
		return bicho.getEnergia()>energy;
		
	}


}
