/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.repository.memory;

import rs.ac.bg.fon.ps.exceptions.ConnectionException;
import rs.ac.bg.fon.ps.repository.Repository;

/**
 *
 * @author Mirko
 */
public interface MemoryRepository<T> extends Repository<T> {

    public default void connect() throws ConnectionException {

    }

    public default void disconnect() throws ConnectionException {

    }

}
