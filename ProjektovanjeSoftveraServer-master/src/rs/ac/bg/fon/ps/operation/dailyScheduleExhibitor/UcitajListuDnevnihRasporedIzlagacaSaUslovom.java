/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operation.dailyScheduleExhibitor;

import java.util.List;
import rs.ac.bg.fon.ps.domain.DR_I;
import rs.ac.bg.fon.ps.exceptions.CRUDException;
import rs.ac.bg.fon.ps.exceptions.user.impl.DnevniRasporedIzlagacException;
import rs.ac.bg.fon.ps.operation.AbstractGenericOperation;

/**
 *
 * @author Mirko
 */
public class UcitajListuDnevnihRasporedIzlagacaSaUslovom extends AbstractGenericOperation {

    @Override
    protected void preconditions(Object param) throws DnevniRasporedIzlagacException {
        if (param == null) {
            throw new DnevniRasporedIzlagacException("Nisu validni podaci za stavku dnevnog rasporeda!");
        }
    }

    @Override
    protected void executeOperation(Object param) throws CRUDException {
        List<DR_I> drii = (List<DR_I>) param;
        repository.NadjiSveSaUslovom(drii.get(0), drii);
    }
}
