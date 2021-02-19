/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operation.admin;

import rs.ac.bg.fon.ps.domain.Admin;
import rs.ac.bg.fon.ps.exceptions.user.impl.AdminException;
import rs.ac.bg.fon.ps.exceptions.CRUDException;
import rs.ac.bg.fon.ps.operation.AbstractGenericOperation;

/**
 *
 * @author Mirko
 */
public class UcitajAdmina extends AbstractGenericOperation {

    @Override
    protected void preconditions(Object param) throws AdminException {
        if (param == null || !(param instanceof Admin)) {
            throw new AdminException("Nisu validni podaci za admina!");
        }
    }

    @Override
    protected void executeOperation(Object param) throws CRUDException {
        repository.Ucitaj((Admin) param);
        if (((Admin) param).getId() == null) {
            throw new CRUDException("Taj admin ne postoji");
        }
    }

}
