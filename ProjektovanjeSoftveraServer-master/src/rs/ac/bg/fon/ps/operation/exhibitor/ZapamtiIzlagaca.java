/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operation.exhibitor;

import rs.ac.bg.fon.ps.domain.Izlagac;
import rs.ac.bg.fon.ps.exceptions.CRUDException;
import rs.ac.bg.fon.ps.exceptions.user.impl.IzlagacException;
import rs.ac.bg.fon.ps.operation.AbstractGenericOperation;

/**
 *
 * @author Mirko
 */
public class ZapamtiIzlagaca extends AbstractGenericOperation {
    
    @Override
    protected void preconditions(Object param) throws IzlagacException {
        if (param == null || !(param instanceof Izlagac)) {
            throw new IzlagacException("Nisu validni podaci za izlagaca!");
        }
    }
    
    @Override
    protected void executeOperation(Object param) throws CRUDException {
        repository.Izmeni((Izlagac) param);
    }
    
}
