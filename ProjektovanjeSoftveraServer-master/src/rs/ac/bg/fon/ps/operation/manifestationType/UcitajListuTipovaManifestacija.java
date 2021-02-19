/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operation.manifestationType;

import java.util.List;
import rs.ac.bg.fon.ps.domain.TipManifestacije;
import rs.ac.bg.fon.ps.exceptions.CRUDException;
import rs.ac.bg.fon.ps.operation.AbstractGenericOperation;

/**
 *
 * @author Mirko
 */
public class UcitajListuTipovaManifestacija extends AbstractGenericOperation {

    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null) {
            throw new Exception("Invalid admin data!");
        }
    }

    @Override
    protected void executeOperation(Object param) throws CRUDException {
        repository.NadjiSve(new TipManifestacije(), (List<TipManifestacije>) param);
    }
}
