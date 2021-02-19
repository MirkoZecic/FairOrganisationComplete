/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.repository.db;

import java.sql.SQLException;
import rs.ac.bg.fon.ps.repository.Repository;

/**
 *
 * @author Mirko
 */
public interface DbRepository<T> extends Repository<T> {

    default public void connect() throws SQLException {
        DbConnectionFactory.getInstance().getConnection();
    }

    default public void disconnect() throws SQLException {
        DbConnectionFactory.getInstance().getConnection().close();
    }

    default public void commit() throws SQLException {
        DbConnectionFactory.getInstance().getConnection().commit();
    }

    default public void rollback() throws SQLException {
        DbConnectionFactory.getInstance().getConnection().rollback();
    }
}
