package ar.edu.unq.epers.bichomon.backend.persistence.especie;

import ar.edu.unq.epers.bichomon.backend.persistence.ConnectionManager;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCEspecieDAO implements EspecieDAO {

    ConnectionManager connectionManager = new ConnectionManager();

    public JDBCEspecieDAO() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("No se puede encontrar la clase del driver", e);
        }
    }

    public void crearEspecie(Especie especie){
        connectionManager.executeWithConnection(conn -> {
            PreparedStatement esp = conn.prepareStatement("INSERT INTO especie (nombre, altura, peso, tipo, cantidadBichos) VALUES (?,?,?,?,?)");
            esp.setString(1, especie.getNombre());
            esp.setInt(2, especie.getAltura());
            esp.setInt(3, especie.getPeso());
            esp.setString(4, especie.getTipo().toString());
            esp.setInt(5, especie.getCantidadBichos());
            esp.execute();

            if (esp.getUpdateCount() != 1) {
                throw new RuntimeException("No se inserto la especie " + especie);
            }
            esp.close();

            return null;
        });
    }

    public Especie getEspecie(String nombre) {
        return connectionManager.executeWithConnection(conn -> {
            PreparedStatement esp = conn.prepareStatement("SELECT nombre, altura, peso, tipo, cantidadBichos FROM especie WHERE nombre = ?");
            esp.setString(1, nombre);

            ResultSet resultSet = esp.executeQuery();

            Especie especie = null;
            while (resultSet.next()) {
                if (especie != null) {
                    throw new RuntimeException("Existe mas de una especie con el nombre " + nombre);
                }

                especie = instanciarEspecie(resultSet);
            }

            esp.close();
            return especie;
        });
    }

    private Especie instanciarEspecie(ResultSet resultSet) throws SQLException {
        Especie especie;
        TipoBicho tipo = crearTipoConString(resultSet.getString("tipo"));
        especie = new Especie(resultSet.getString("nombre"), tipo);
        especie.setAltura(resultSet.getInt("altura"));
        especie.setPeso(resultSet.getInt("peso"));
        especie.setCantidadBichos(resultSet.getInt("cantidadBichos"));
        return especie;
    }

    public List<Especie> getAllEspecies(){
        List<Especie> especies = new ArrayList<Especie>();
        return connectionManager.executeWithConnection(conn -> {
            PreparedStatement esp = conn.prepareStatement("SELECT nombre, altura, peso, tipo, cantidadBichos FROM especie");

            ResultSet resultSet = esp.executeQuery();

            Especie especie;
            while (resultSet.next()) {
                especie = instanciarEspecie(resultSet);
                especies.add(especie);
            }

            esp.close();
            return especies;
        });
    }

    public void agregarBicho(String nombreEspecie) {

        connectionManager.executeWithConnection(conn -> {
            PreparedStatement esp = conn.prepareStatement("UPDATE especie SET cantidadBichos = (cantidadBichos + 1) WHERE nombre = ?");
            esp.setString(1, nombreEspecie);
            esp.execute();
            return null;
        });
    }

    private TipoBicho crearTipoConString(String tipo){
        return TipoBicho.valueOf(tipo);
    }

	@Override
	public Boolean tengoEvolucion(Especie especie) {
		// TODO Auto-generated method stub
		return null;
	}
}