/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operation.hall;

import java.util.List;
import rs.ac.bg.fon.ps.domain.Hala;
import rs.ac.bg.fon.ps.exceptions.CRUDException;
import rs.ac.bg.fon.ps.exceptions.user.impl.HalaException;
import rs.ac.bg.fon.ps.operation.AbstractGenericOperation;

/**
 *
 * @author Mirko
 */
public class UcitajListuHala extends AbstractGenericOperation {

    @Override
    protected void preconditions(Object param) throws HalaException {
        if (param == null) {
            throw new HalaException("Nisu validni podaci za halu!");
        }
    }

    @Override
    protected void executeOperation(Object param) throws CRUDException {
        repository.NadjiSve(new Hala(), (List<Hala>) param);
    }
}
