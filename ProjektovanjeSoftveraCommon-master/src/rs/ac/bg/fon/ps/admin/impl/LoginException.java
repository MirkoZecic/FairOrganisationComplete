/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.admin.impl;

import rs.ac.bg.fon.ps.exceptions.user.impl.AdminException;

/**
 *
 * @author Mirko
 */
public class LoginException extends AdminException {

    public LoginException() {
    }

    public LoginException(String message) {
        super(message);
    }

    public LoginException(Throwable cause) {
        super(cause);
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
