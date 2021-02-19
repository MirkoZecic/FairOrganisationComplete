/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.repository.memory.impl;

import rs.ac.bg.fon.ps.repository.memory.MemoryRepository;
import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ps.domain.TipManifestacije;
import rs.ac.bg.fon.ps.exceptions.CRUDException;

/**
 *
 * @author Mirko
 */
public class MemorijskiRepozitorijumTipManifestacije implements MemoryRepository<TipManifestacije> {

    private List<TipManifestacije> tipoviManifestacija;

    public MemorijskiRepozitorijumTipManifestacije() {
        tipoviManifestacija = new ArrayList<>();
        tipoviManifestacija.add(new TipManifestacije(1l, "Tehnika"));
        tipoviManifestacija.add(new TipManifestacije(2l, "Kultura"));
        tipoviManifestacija.add(new TipManifestacije(3l, "Vozila"));
    }

    @Override
    public void Dodaj(TipManifestacije param) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Izmeni(TipManifestacije param) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Obrisi(TipManifestacije param) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void NadjiSve(TipManifestacije param, List<TipManifestacije> listParam) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void NadjiSveSaUslovom(TipManifestacije param, List<TipManifestacije> listParam) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Ucitaj(TipManifestacije param) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
