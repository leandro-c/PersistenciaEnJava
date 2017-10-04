package ar.edu.unq.epers.bichomon.backend.persistence;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface ConnectionBlock<T> {

    public T executeWith(Connection conn) throws SQLException;

}