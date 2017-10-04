package ar.edu.unq.epers.bichomon.backend.persistence.camino;

import ar.edu.unq.epers.bichomon.backend.model.camino.Camino;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoCamino;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import org.neo4j.driver.v1.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ar.edu.unq.epers.bichomon.backend.model.especie.TipoCamino.*;

public class N4JCaminoDAO implements CaminoDAO{

    private Driver driver;
    private List<Camino> caminos;

    public void setCostos() {
        caminos = new ArrayList<>();
        caminos.add(new Camino(TERRESTRE, 1));
        caminos.add(new Camino(MARITIMO, 3));
        caminos.add(new Camino(AEREO, 5));
    }

    public void clearDb() {
        Session session = this.driver.session();

        try {
            String query = "MATCH (n) DETACH DELETE n";
            session.run(query);
        } finally {
            session.close();
        }
    }

    public N4JCaminoDAO() {
        this.driver = GraphDatabase.driver( "bolt://localhost", AuthTokens.basic( "neo4j", "root" ) );
    }

    public void crearUbicacion(Ubicacion ubicacion) {
        Session session = this.driver.session();

        try {
            String query = "MERGE (n:Ubicacion {nombre: {elNombre}})";
            session.run(query, Values.parameters("elNombre", ubicacion.getNombre()));
        } finally {
            session.close();
        }
    }

    public void conectarUbicaciones(Ubicacion origen, Ubicacion destino, Camino camino){
        Session session = this.driver.session();

        try {
            String query = "MATCH (origen:Ubicacion {nombre: {nombreOrigen}}) " +
                    "MATCH (destino:Ubicacion {nombre: {nombreDestino}}) " +
                    "MERGE (origen)-[:viajeA" +
                    "{tipoCamino: {tipoConexion}, costo: {costoConexion}}]" +
                    "->(destino) ";
            session.run(query, Values.parameters("nombreOrigen", origen.getNombre(),
                    "nombreDestino", destino.getNombre(),"tipoConexion",camino.getTipoCamino().toString(), "costoConexion", camino.getCosto()));

        } finally {
            session.close();
        }
    }

    public Integer costoDirecto(Ubicacion origen, Ubicacion destino) {
        Session session = this.driver.session();

        try {
            String query = "MATCH (origen:Ubicacion {nombre: {ubicacionActual}}) " +
                    "MATCH (destino:Ubicacion {nombre: {nombreDestino}})" +
                    "MATCH(origen)-[viaje:viajeA]-(destino)" +
                    "RETURN viaje";
            StatementResult result = session.run(query, Values.parameters("nombreDestino", destino.getNombre(), "ubicacionActual", origen.getNombre()));

            return result.list(record -> {
                Value viaje = record.get(0);
                Integer costo = viaje.get("costo").asInt();
                return costo;
            }).get(0);

        } finally {
            session.close();
        }
    }

    public boolean conectados( Ubicacion origen, Ubicacion destino ) {
        Session session = this.driver.session();

        try {

            String query = "MATCH (origen:Ubicacion {nombre: {ubicacionActual}})"+
                    "MATCH (destino:Ubicacion {nombre: {nombreDestino}})"+
                    "RETURN EXISTS((origen)-[:viajeA*]-(destino)) as existe";
            StatementResult result = session.run(query, Values.parameters("nombreDestino", destino.getNombre(), "ubicacionActual", origen.getNombre()));

            return result.list(record -> record.get("existe").asBoolean()).get(0);

        } finally {
            session.close();
        }
    }

    public List<String> conectados(Ubicacion ubicacion, TipoCamino tipoCamino) {
        Session session = this.driver.session();

        try {
            String query = "MATCH (origen: Ubicacion {nombre: {elNombreUbicacion}}), " +
                    "path = (origen)-[viaje:viajeA]->(destino) " +
                    "WHERE viaje.tipoCamino = {tipoCamino}"+
                    "RETURN extract( p IN nodes(path) | p.nombre ) as resultado";
            StatementResult result = session.run(query, Values.parameters("elNombreUbicacion", ubicacion.getNombre(), "tipoCamino", tipoCamino.toString() ));

            //Similar a list.stream().map(...)
            return result.list(record -> {
                Value destino = record.get(0);
                String nombre = destino.get(1).asString();
                return nombre;
            });


        } finally {
            session.close();
        }
    }

    public String getUbicacion(Ubicacion ubicacion) {
        Session session = this.driver.session();

        try {
            String query = "MATCH (ubicacion:Ubicacion {nombre: {elNombreUbicacion}}) " +
                    "RETURN ubicacion";
            StatementResult result = session.run(query, Values.parameters("elNombreUbicacion", ubicacion.getNombre()));

            return result.list(record -> {
                Value ubic = record.get(0);
                String nombre = ubic.get("nombre").asString();
                return nombre;
            }).get(0);

            } finally {
            session.close();
        }
    }

    public Integer caminoMasCorto(Ubicacion origen, Ubicacion destino) {
        Session session = this.driver.session();

        try {
            String query = "MATCH (origen:Ubicacion {nombre: {ubicacionActual}}) " +
                    "MATCH (destino:Ubicacion {nombre: {nombreDestino}})," +
                    "shortestPath((origen)-[camino:viajeA*]-(destino))" +
                    "RETURN REDUCE (memo = 0, viaje IN camino | memo + viaje.costo) AS costoTotal";
            StatementResult result = session.run(query, Values.parameters("nombreDestino", destino.getNombre(), "ubicacionActual", origen.getNombre()));

            return result.list(record -> record.get("costoTotal").asInt() ).get(0);

        } finally {
            session.close();
        }
    }
    public Integer caminoMasBarato(Ubicacion origen, Ubicacion destino){
        Session session = this.driver.session();

        try {

            String query = "MATCH (origen:Ubicacion {nombre: {ubicacionActual}})"+
                "MATCH (destino:Ubicacion {nombre: {nombreDestino}}),"+
                "ps = (origen)-[:viajeA]-(destino)" +
                "RETURN EXTRACT( p IN relationships(ps) | p.costo ) as costo "+
                "ORDER BY costo ASC";
            StatementResult result = session.run(query, Values.parameters("nombreDestino", destino.getNombre(), "ubicacionActual", origen.getNombre()));

            return result.list(record -> record.get("costo").get(0).asInt()).get(0);

        } finally {
            session.close();
        }
    }
}
