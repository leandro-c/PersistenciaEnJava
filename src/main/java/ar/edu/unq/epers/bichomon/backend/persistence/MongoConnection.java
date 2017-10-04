package ar.edu.unq.epers.bichomon.backend.persistence;

import com.mongodb.MongoClient;
import org.jongo.Jongo;

public class MongoConnection {

    private static MongoConnection INSTANCE;

    private MongoClient client;
    private Jongo jongo;

    public static synchronized MongoConnection getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MongoConnection();
        }
        return INSTANCE;
    }

    @SuppressWarnings("deprecation")
    private MongoConnection() {
        this.client = new MongoClient("localhost", 27017);
        this.jongo = new Jongo(this.client.getDB("epersMongo"));
    }

    public Jongo getJongo() {
        return this.jongo;
    }

}