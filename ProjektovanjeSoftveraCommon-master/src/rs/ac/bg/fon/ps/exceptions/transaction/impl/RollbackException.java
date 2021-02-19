/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.exceptions.transaction.impl;

import rs.ac.bg.fon.ps.exceptions.TransactionException;

/**
 *
 * @author Mirko
 */
public class RollbackException extends TransactionException {

    public RollbackException() {
    }

    public RollbackException(String message) {
        super(message);
    }

    public RollbackException(Throwable cause) {
        super(cause);
    }

    public RollbackException(String message, Throwable cause) {
        super(message, cause);
    }
}
