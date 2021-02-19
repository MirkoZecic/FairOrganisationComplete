/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.repository.memory.impl;

import rs.ac.bg.fon.ps.repository.memory.MemoryRepository;
import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ps.domain.DR_I;
import rs.ac.bg.fon.ps.exceptions.CRUDException;

/**
 *
 * @author Mirko
 */
public class MemorijskiRepozitorijumDnevniRasporedIzlagac implements MemoryRepository<DR_I> {

    private List<DR_I> drii;

    public MemorijskiRepozitorijumDnevniRasporedIzlagac() {
        drii = new ArrayList<>();
        //drii.add(new DR_I(dnevniRaspored, izlagac, hala, 0))
    }

    public List<DR_I> getAll() throws Exception {
        return drii;
    }

    public void Dodaj(DR_I dri) throws CRUDException {
        if (!drii.contains(dri)) {
            drii.add(dri);
        } else {
            throw new CRUDException("DnevniRaspored vec postoji!");
        }
    }

    public void sacuvajDnevniRasporedIzlagac(DR_I dri) throws Exception {
        int index = drii.indexOf(dri);
        if (index >= 0) {
            drii.remove(index);
            drii.add(index, dri);
        } else {
            throw new Exception("Greska: DnevniRaspored ne postoji!");
        }
    }

    public void izbrisiDnevniRasporedIzlagac(DR_I dri) throws Exception {
        int index = drii.indexOf(dri);
        if (index >= 0) {
            drii.remove(index);
        } else {
            throw new Exception("Greska: DnevniRaspored ne postoji!");
        }
    }

    @Override
    public void Izmeni(DR_I param) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Obrisi(DR_I param) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void NadjiSve(DR_I param, List<DR_I> listParam) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void NadjiSveSaUslovom(DR_I param, List<DR_I> listParam) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Ucitaj(DR_I param) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
