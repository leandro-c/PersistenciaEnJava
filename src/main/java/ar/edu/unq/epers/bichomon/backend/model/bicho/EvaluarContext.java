package ar.edu.unq.epers.bichomon.backend.model.bicho;

import java.util.ArrayList;

public class EvaluarContext {
	private EvaluarStrategy strategy;
	
	//this can be set at runtime by the application preferences
	  public void setCondicionStrategy(EvaluarStrategy strategy) {
	    this.strategy = strategy;
	  }

	//use the strategy
	  public Boolean evaluarEvolucion(Bicho bicho) {
	    return strategy.evaluar(bicho);
	  }
}
