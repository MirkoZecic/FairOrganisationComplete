/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.exceptions;

import java.sql.SQLException;

/**
 *
 * @author Mirko
 */
public class CRUDException extends SQLException {

    public CRUDException() {
    }

    public CRUDException(String message) {
        super(message);
    }

    public CRUDException(Throwable cause) {
        super(cause);
    }

    public CRUDException(String message, Throwable cause) {
        super(message, cause);
    }
}
