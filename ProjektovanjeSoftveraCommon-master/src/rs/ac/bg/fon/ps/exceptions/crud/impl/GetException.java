/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.exceptions.crud.impl;

import rs.ac.bg.fon.ps.exceptions.CRUDException;

/**
 *
 * @author Mirko
 */
public class GetException extends CRUDException {

    public GetException() {
    }

    public GetException(String message) {
        super(message);
    }

    public GetException(Throwable cause) {
        super(cause);
    }

    public GetException(String message, Throwable cause) {
        super(message, cause);
    }

}
