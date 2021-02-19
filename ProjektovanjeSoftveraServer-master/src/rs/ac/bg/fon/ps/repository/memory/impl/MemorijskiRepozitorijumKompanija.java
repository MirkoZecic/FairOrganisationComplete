/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.repository.memory.impl;

import java.util.List;
import rs.ac.bg.fon.ps.domain.Kompanija;
import rs.ac.bg.fon.ps.exceptions.CRUDException;
import rs.ac.bg.fon.ps.repository.memory.MemoryRepository;

/**
 *
 * @author Mirko
 */
public class MemorijskiRepozitorijumKompanija implements MemoryRepository<Kompanija> {

    private List<Kompanija> kompanije;

    public MemorijskiRepozitorijumKompanija(List<Kompanija> kompanije) {
        this.kompanije = kompanije;
        kompanije.add(new Kompanija(1l, "ABC", "Mateje Prokica", "062 2135123"));
        kompanije.add(new Kompanija(2l, "Prvi Partizan", "Boska Kovacevica 42", "011 134312"));
        kompanije.add(new Kompanija(3l, "Elektra", "Teodora Ruzvelta 10", "061 9732194"));
    }

    public List<Kompanija> getAll() throws Exception {
        return kompanije;
    }

    public void Dodaj(Kompanija kompanija) throws CRUDException {
        if (!kompanije.contains(kompanija)) {
            kompanije.add(kompanija);
        } else {
            throw new CRUDException("Kompanija vec postoji!");
        }
    }

    public void sacuvajKompaniju(Kompanija kompanija) throws Exception {
        int index = kompanije.indexOf(kompanija);
        if (index >= 0) {
            kompanije.remove(index);
            kompanije.add(index, kompanija);
        } else {
            throw new Exception("Greska: Kompanija ne postoji!");
        }
    }

    @Override
    public void Obrisi(Kompanija param) throws CRUDException {
        int index = kompanije.indexOf(param);
        if (index >= 0) {
            kompanije.remove(index);
        } else {
            throw new CRUDException("Greska: Kompanija ne postoji!");
        }
    }

    @Override
    public void Izmeni(Kompanija param) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void NadjiSve(Kompanija param, List<Kompanija> listParam) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void NadjiSveSaUslovom(Kompanija param, List<Kompanija> listParam) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Ucitaj(Kompanija param) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
