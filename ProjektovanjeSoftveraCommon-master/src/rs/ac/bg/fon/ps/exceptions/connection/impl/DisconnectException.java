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
public class DisconnectException extends ConnectionException {

    public DisconnectException() {
    }

    public DisconnectException(String message) {
        super(message);
    }

    public DisconnectException(Throwable cause) {
        super(cause);
    }

    public DisconnectException(String message, Throwable cause) {
        super(message, cause);
    }
}
