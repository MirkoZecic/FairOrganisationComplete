/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.exceptions.user.impl;

import rs.ac.bg.fon.ps.exceptions.UserException;

/**
 *
 * @author Mirko
 */
public class ManifestacijaException extends UserException {

    public ManifestacijaException() {
    }

    public ManifestacijaException(String message) {
        super(message);
    }

    public ManifestacijaException(Throwable cause) {
        super(cause);
    }

    public ManifestacijaException(String message, Throwable cause) {
        super(message, cause);
    }
}
