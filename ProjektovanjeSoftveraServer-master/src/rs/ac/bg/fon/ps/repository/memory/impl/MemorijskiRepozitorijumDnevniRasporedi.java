/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.repository.memory.impl;

import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ps.domain.DnevniRaspored;

/**
 *
 * @author Mirko
 */
public class MemorijskiRepozitorijumDnevniRasporedi {

    private List<DnevniRaspored> dnevniRasporedi;

    public MemorijskiRepozitorijumDnevniRasporedi() {
        dnevniRasporedi = new ArrayList<>();

    }

    public List<DnevniRaspored> getAll() {
        return dnevniRasporedi;
    }
}
