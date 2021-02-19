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
public class CommitException extends TransactionException {

    public CommitException() {
    }

    public CommitException(String message) {
        super(message);
    }

    public CommitException(Throwable cause) {
        super(cause);
    }

    public CommitException(String message, Throwable cause) {
        super(message, cause);
    }
}
