/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.repository;

import java.util.List;
import rs.ac.bg.fon.ps.exceptions.CRUDException;

/**
 *
 * @author Mirko
 */
public interface Repository<T> {

    public void Dodaj(T param) throws CRUDException;
    public void Izmeni(T param) throws CRUDException;
    public void Obrisi(T param)throws CRUDException;
    public void NadjiSve(T param, List<T> listParam) throws CRUDException;
    public void NadjiSveSaUslovom(T param, List<T> listParam) throws CRUDException;
    public void Ucitaj(T param) throws CRUDException;
}
