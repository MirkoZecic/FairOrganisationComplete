/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operation;

import java.sql.SQLException;
import rs.ac.bg.fon.ps.repository.Repository;
import rs.ac.bg.fon.ps.repository.db.DbRepository;
import rs.ac.bg.fon.ps.repository.db.impl.RepositoryDBGeneric;

/**
 *
 * @author Mirko
 */
public abstract class AbstractGenericOperation {

    protected final Repository repository;

    public AbstractGenericOperation() {
        this.repository = new RepositoryDBGeneric();
    }

    public final void execute(Object param) throws Exception {
        try {
            startTransaction();
            preconditions(param);
            executeOperation(param);
            commitTransaction();
        } catch (Exception ex) {
            ex.printStackTrace();
            rollbackTransaction();
            throw ex;
        } finally {
            disconnect();
        }
    }

    private void startTransaction() throws SQLException {
        ((DbRepository) repository).connect();
    }

    // Operation-specific method
    protected abstract void preconditions(Object param) throws Exception;

    // Operation-specific method
    protected abstract void executeOperation(Object param) throws SQLException;

    private void commitTransaction() throws SQLException {
        ((DbRepository) repository).commit();
    }

    private void rollbackTransaction() throws SQLException {
        ((DbRepository) repository).rollback();
    }

    private void disconnect() throws SQLException {
        ((DbRepository) repository).disconnect();
    }

}
