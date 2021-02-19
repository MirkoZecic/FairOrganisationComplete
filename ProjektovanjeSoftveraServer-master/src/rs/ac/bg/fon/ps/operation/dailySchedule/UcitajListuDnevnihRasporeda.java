/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operation.dailySchedule;

import java.util.List;
import rs.ac.bg.fon.ps.domain.DnevniRaspored;
import rs.ac.bg.fon.ps.exceptions.CRUDException;
import rs.ac.bg.fon.ps.exceptions.user.impl.DnevniRasporedException;
import rs.ac.bg.fon.ps.operation.AbstractGenericOperation;

/**
 *
 * @author Mirko
 */
public class UcitajListuDnevnihRasporeda extends AbstractGenericOperation {

    @Override
    protected void preconditions(Object param) throws DnevniRasporedException {
        if (param == null) {
            throw new DnevniRasporedException("Nisu validni podaci za dnevni raspored!");
        }
    }

    @Override
    protected void executeOperation(Object param) throws CRUDException {
        repository.NadjiSve(new DnevniRaspored(), (List< DnevniRaspored>) param);
    }
}
