package ar.edu.unq.epers.bichomon.backend.persistence.especie;

import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import java.util.List;

public interface EspecieDAO {

    public void crearEspecie(Especie especie);
    public Especie getEspecie(String nombre);
    public List<Especie> getAllEspecies();
    public void agregarBicho(String nombreEspecie);
    public Boolean tengoEvolucion(Especie especie);

}
