package ar.edu.unq.epers.bichomon.backend.service.especie;

/**
 * Situación excepcional en que una especie buscada no es
 * encontrada.
 */
public class EspecieNoExistente extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EspecieNoExistente(String especie) {
		super("No se encuentra la especie [" + especie + "]");
	}
	
}
