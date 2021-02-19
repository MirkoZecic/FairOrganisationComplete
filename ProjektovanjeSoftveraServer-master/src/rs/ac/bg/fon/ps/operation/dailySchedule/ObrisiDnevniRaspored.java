/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operation.dailySchedule;

import rs.ac.bg.fon.ps.domain.DR_I;
import rs.ac.bg.fon.ps.domain.DnevniRaspored;
import rs.ac.bg.fon.ps.exceptions.CRUDException;
import rs.ac.bg.fon.ps.exceptions.user.impl.DnevniRasporedException;
import rs.ac.bg.fon.ps.operation.AbstractGenericOperation;

/**
 *
 * @author Mirko
 */
public class ObrisiDnevniRaspored extends AbstractGenericOperation {

    @Override
    protected void preconditions(Object param) throws DnevniRasporedException {
        if (param == null || !(param instanceof DnevniRaspored)) {
            throw new DnevniRasporedException("Nisu validni podaci za dnevni raspored!");
        }
    }

    @Override
    protected void executeOperation(Object param) throws CRUDException {
        DR_I temp = new DR_I();
        temp.setDnevniRaspored((DnevniRaspored) param);
        repository.Obrisi(temp);
        repository.Obrisi((DnevniRaspored) param);
    }

}
