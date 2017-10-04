package ar.edu.unq.epers.bichomon.backend.service.data;

import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.nivel.Nivel;
import ar.edu.unq.epers.bichomon.backend.service.nivel.NivelService;
import ar.edu.unq.epers.bichomon.backend.service.nivel.NivelServiceHBN;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import org.hibernate.Session;

import java.util.List;

public class DataServiceHBN implements DataService {

    private NivelService nivelService = new NivelServiceHBN();

    @Override
    public void eliminarDatos() {
    }

    @Override
    public void crearSetDatosIniciales() {
    }


    @Override
    public void crearNiveles() {
        Nivel nivel1 = new Nivel(1, 99, 10);
        Nivel nivel2 = new Nivel(2, 400, 20);
        nivel1.setNivelProximo(nivel2);
        Nivel nivel3 = new Nivel(3, 1000, 30);
        nivel2.setNivelProximo(nivel3);
        Nivel nivel4 = new Nivel(4, 2000, 50);
        nivel3.setNivelProximo(nivel4);

        nivelService.guardarNivel(nivel1);
        // Guarda todos porque va en cascada.
    }

    @Override
    public Especie getEspecie() {
        return new Especie("Pikachu", TipoBicho.ELECTRICIDAD);
    }

    @Override
    public void crearEspecies() {
    }

    public void clearDb() {
        Runner.runInSession( () -> {
            Session currentSession = Runner.getCurrentSession();
            List<String> tablas = currentSession.createNativeQuery("show tables").getResultList();

            currentSession.createNativeQuery("set FOREIGN_KEY_CHECKS=0").executeUpdate();
            tablas.forEach(tabla -> {
                currentSession.createNativeQuery("truncate table " + tabla).executeUpdate();
            });

            currentSession.createNativeQuery("set FOREIGN_KEY_CHECKS=1").executeUpdate();
            return null;
        });
    }
}
