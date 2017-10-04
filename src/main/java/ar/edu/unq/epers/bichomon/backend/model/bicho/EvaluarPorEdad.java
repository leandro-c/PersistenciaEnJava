package ar.edu.unq.epers.bichomon.backend.model.bicho;

public class EvaluarPorEdad extends EvaluarStrategy{

	private Integer edad;
	@Override
	public Boolean evaluar(Bicho bicho) {
		// TODO Auto-generated method stub
		return bicho.getEdad() > edad;
	}

}
