/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operation.company;

import java.util.List;
import rs.ac.bg.fon.ps.domain.Kompanija;
import rs.ac.bg.fon.ps.exceptions.CRUDException;
import rs.ac.bg.fon.ps.exceptions.user.impl.KompanijaException;
import rs.ac.bg.fon.ps.operation.AbstractGenericOperation;

/**
 *
 * @author Mirko
 */
public class UcitajListuKompanija extends AbstractGenericOperation {

    @Override
    protected void preconditions(Object param) throws KompanijaException {
        if (param == null) {
            throw new KompanijaException("Nisu validni podaci za kompaniju!");
        }
    }

    @Override
    protected void executeOperation(Object param) throws CRUDException {
        repository.NadjiSve(new Kompanija(), (List<Kompanija>) param);
    }
}
