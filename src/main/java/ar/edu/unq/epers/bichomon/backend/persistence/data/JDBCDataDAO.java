package ar.edu.unq.epers.bichomon.backend.persistence.data;

import ar.edu.unq.epers.bichomon.backend.persistence.ConnectionManager;

import java.sql.PreparedStatement;

public class JDBCDataDAO {

    ConnectionManager connectionManager = new ConnectionManager();

    public void eliminarDatos(){
        connectionManager.executeWithConnection(conn -> {
            PreparedStatement esp = conn.prepareStatement("TRUNCATE TABLE especie");
            esp.execute();
            return null;
        });
    }
}
