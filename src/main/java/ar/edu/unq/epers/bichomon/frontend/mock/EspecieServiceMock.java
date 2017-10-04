package ar.edu.unq.epers.bichomon.frontend.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.service.especie.EspecieService;

/**
 * Esta es una implementacion mock de {@link EspecieService}
 * 
 * Esto es codigo de frontend, no deberian tocar nada de aca.
 */
public class EspecieServiceMock implements EspecieService {

	private static Map<String, Especie> DATA = new HashMap<>();
	
	static {
		Especie red = new Especie("Rojomon", TipoBicho.FUEGO);
		red.setAltura(180);
		red.setPeso(75);
		red.setEnergiaIncial(100);
		red.setUrlFoto("http://fsb.zedge.net/scale.php?img=MS83LzIvNS8xLTEwMTM5MDgwLTE3MjUxMzMuanBn&ctype=1&v=4&q=71&xs=300&ys=266&sig=f646cac1f807dad3d23cb9133120724f950c0178");
		DATA.put(red.getNombre(), red);
		
		Especie amarillo = new Especie("Amarillomon", TipoBicho.ELECTRICIDAD);
		amarillo.setAltura(170);
		amarillo.setPeso(69);
		amarillo.setEnergiaIncial(300);
		amarillo.setUrlFoto("http://hdphonewallpapers.com/content/q8yCyv47vICodrvsVLHUp4rEysK76b9BilLxZ3Ej8AYuij5PX4tLJYawPuNGl7BU.jpg");
		DATA.put(amarillo.getNombre(), amarillo);
		
		Especie green = new Especie("Verdemon", TipoBicho.PLANTA);
		green.setAltura(150);
		green.setPeso(55);
		green.setEnergiaIncial(5000);
		green.setUrlFoto("http://previews.123rf.com/images/albertzig/albertzig1210/albertzig121001480/15810570-3d-cartoon-cute-monster-Stock-Photo-monster.jpg");
		DATA.put(green.getNombre(), green);
		
		Especie turronmon = new Especie("Tierramon", TipoBicho.TIERRA);
		turronmon.setAltura(1050);
		turronmon.setPeso(99);
		turronmon.setEnergiaIncial(5000);
		turronmon.setUrlFoto("http://image.shutterstock.com/display_pic_with_logo/498883/498883,1328091644,5/stock-photo-furry-cute-monster-94065856.jpg");
		DATA.put(turronmon.getNombre(), turronmon);
		
		Especie fantasmon = new Especie("Fantasmon", TipoBicho.AIRE);
		fantasmon.setAltura(1050);
		fantasmon.setPeso(99);
		fantasmon.setEnergiaIncial(5000);
		fantasmon.setUrlFoto("http://www.komarketingassociates.com/images/2014/12/cute-monster.jpg");
		DATA.put(fantasmon.getNombre(), fantasmon);
		
		Especie vampiron = new Especie("Vanpiron", TipoBicho.AIRE);
		vampiron.setAltura(1050);
		vampiron.setPeso(99);
		vampiron.setEnergiaIncial(5000);
		vampiron.setUrlFoto("http://previews.123rf.com/images/albertzig/albertzig1210/albertzig121000360/15625240-3d-cartoon-cute-monster-Stock-Photo-troll.jpg");
		DATA.put(vampiron.getNombre(), vampiron);
		
		Especie fortmon = new Especie("Fortmon", TipoBicho.CHOCOLATE);
		fortmon.setAltura(1050);
		fortmon.setPeso(99);
		fortmon.setEnergiaIncial(5000);
		fortmon.setUrlFoto("http://ar.cdn01.mundotkm.com/2013/11/fort-1-e1385400469164.jpg");
		DATA.put(fortmon.getNombre(), fortmon);
		
		Especie dientemon = new Especie("Dientemon", TipoBicho.AGUA);
		dientemon.setAltura(1050);
		dientemon.setPeso(99);
		dientemon.setEnergiaIncial(5000);
		dientemon.setUrlFoto("https://www.colourbox.com/preview/5448367-blue-cute-monster.jpg");
		DATA.put(dientemon.getNombre(), dientemon);
	}
	
	@Override
	public void crearEspecie(Especie especie) {
		DATA.put(especie.getNombre(), especie);
	}

	@Override
	public Especie getEspecie(String nombreEspecie) {
		return DATA.get(nombreEspecie);
	}

	@Override
	public List<Especie> getAllEspecies() {
		return new ArrayList<>(DATA.values());
	}

	@Override
	public Bicho crearBicho(String nombreEspecie, String nombreBicho) {
		Especie especie = DATA.get(nombreEspecie);
		especie.setCantidadBichos(especie.getCantidadBichos()+1);
		return new Bicho(especie);
	}

	@Override
	public List<Especie> populares() {
		return null;
	}

}
