/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.exceptions.connection.impl;

import rs.ac.bg.fon.ps.exceptions.ConnectionException;

/**
 *
 * @author Mirko
 */
public class ConnectException extends ConnectionException {

    public ConnectException() {
    }

    public ConnectException(String message) {
        super(message);
    }

    public ConnectException(Throwable cause) {
        super(cause);
    }

    public ConnectException(String message, Throwable cause) {
        super(message, cause);
    }
}
