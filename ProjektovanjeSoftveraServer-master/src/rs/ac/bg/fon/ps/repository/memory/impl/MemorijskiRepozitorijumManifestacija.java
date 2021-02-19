/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.repository.memory.impl;

import rs.ac.bg.fon.ps.repository.memory.MemoryRepository;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import rs.ac.bg.fon.ps.domain.Manifestacija;
import rs.ac.bg.fon.ps.exceptions.CRUDException;

/**
 *
 * @author Mirko
 */
public class MemorijskiRepozitorijumManifestacija implements MemoryRepository<Manifestacija> {

    private List<Manifestacija> manifestacije;

    public MemorijskiRepozitorijumManifestacija() {
        manifestacije = new ArrayList<>();
        //manifestacije.add(new Manifestacija(1, "Sajam knjiga", vratiDatum(2020, 11, 11), vratiDatum(2020, 11, 15), Controller.getInstance().getManifestationType(1)));
        //manifestacije.add(new Manifestacija(2, "Sajam vozila", vratiDatum(2020, 5, 5), vratiDatum(2020, 5, 9), Controller.getInstance().getManifestationType(2)));
        //manifestacije.add(new Manifestacija(3, "Sajam nauke", vratiDatum(2020, 1, 2), vratiDatum(2020, 1, 7), Controller.getInstance().getManifestationType(3)));
    }

    private Date vratiDatum(int god, int mesec, int dan) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, god);
        cal.set(Calendar.MONTH, mesec);
        cal.set(Calendar.DAY_OF_MONTH, dan);
        return cal.getTime();
    }

    @Override
    public void Dodaj(Manifestacija param) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Izmeni(Manifestacija param) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Obrisi(Manifestacija param) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void NadjiSve(Manifestacija param, List<Manifestacija> listParam) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void NadjiSveSaUslovom(Manifestacija param, List<Manifestacija> listParam) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Ucitaj(Manifestacija param) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
