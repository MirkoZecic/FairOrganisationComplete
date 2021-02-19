/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.repository.memory.impl;

import rs.ac.bg.fon.ps.repository.memory.MemoryRepository;
import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ps.domain.Izlagac;
import rs.ac.bg.fon.ps.exceptions.CRUDException;

/**
 *
 * @author Mirko
 */
public class MemorijskiRepozitorijumIzlagac implements MemoryRepository<Izlagac> {

    private List<Izlagac> izlagaci;

    public MemorijskiRepozitorijumIzlagac() {
        izlagaci = new ArrayList<>();
        //izlagaci.add(new Izlagac(1,"Janko", "Ivanovic", "0644212342", "janko@gmail.com", Controller.getInstance().getCompany(1)));        
        //izlagaci.add(new Izlagac(1,"Janko", "Ivanovic", "0644212342", "janko@gmail.com", Controller.getInstance().getCompany(2))));        
        //izlagaci.add(new Izlagac(1,"Janko", "Ivanovic", "0644212342", "janko@gmail.com", Controller.getInstance().getCompany(3))));        
    }

    public List<Izlagac> getAll() throws Exception {
        return izlagaci;
    }

    public void Dodaj(Izlagac izlagac) throws CRUDException {
        if (!izlagaci.contains(izlagac)) {
            izlagaci.add(izlagac);
        } else {
            throw new CRUDException("Izlagac vec postoji!");
        }
    }

    public void sacuvajIzlagaca(Izlagac izlagac) throws Exception {
        int index = izlagaci.indexOf(izlagac);
        if (index >= 0) {
            izlagaci.remove(index);
            izlagaci.add(index, izlagac);
        } else {
            throw new Exception("Greska: Izlagac ne postoji!");
        }
    }

    public void izbrisiIzlagaca(Izlagac izlagac) throws Exception {
        int index = izlagaci.indexOf(izlagac);
        if (index >= 0) {
            izlagaci.remove(index);
        } else {
            throw new Exception("Greska: Izlagac ne postoji!");
        }
    }

    @Override
    public void Izmeni(Izlagac param) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Obrisi(Izlagac param) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void NadjiSve(Izlagac param, List<Izlagac> listParam) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void NadjiSveSaUslovom(Izlagac param, List<Izlagac> listParam) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Ucitaj(Izlagac param) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
