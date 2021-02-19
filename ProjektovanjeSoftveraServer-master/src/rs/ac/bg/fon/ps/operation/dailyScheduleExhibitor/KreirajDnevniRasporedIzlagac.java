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
public class KreirajDnevniRasporedIzlagac extends AbstractGenericOperation {
    
    @Override
    protected void preconditions(Object param) throws DnevniRasporedIzlagacException {
        if (param == null || !(((List<DR_I>) param).get(0) instanceof DR_I)) {
            throw new DnevniRasporedIzlagacException("Nisu validni podaci za stavku dnevnog rasporeda!");
        }
    }
    
    @Override
    protected void executeOperation(Object param) throws CRUDException {
        for (DR_I dri : (List<DR_I>) param) {
            repository.Dodaj(dri);
        }
    }
    
}
