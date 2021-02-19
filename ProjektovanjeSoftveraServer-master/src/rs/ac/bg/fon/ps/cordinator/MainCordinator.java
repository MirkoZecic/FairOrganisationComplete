/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.cordinator;

import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ps.view.controller.StartController;
import rs.ac.bg.fon.ps.domain.Admin;
import rs.ac.bg.fon.ps.exceptions.user.impl.AdminException;
import rs.ac.bg.fon.ps.view.FrmStart;

/**
 *
 * @author Mirko
 */
public class MainCordinator {

    List<Admin> ulogovaniAdmini;
    private static MainCordinator instance;

    private MainCordinator() {
        ulogovaniAdmini = new ArrayList<>();
    }

    public static MainCordinator getInstance() {
        if (instance == null) {
            instance = new MainCordinator();
        }
        return instance;
    }

    public void openStartForm() {
        StartController startController = new StartController(new FrmStart());
        startController.openForm();
    }

    public void dodajAdmina(Admin a) throws AdminException {
        if (ulogovaniAdmini.contains(a)) {
            throw new AdminException("Taj admin je vec ulogovan");
        }
        ulogovaniAdmini.add(a);
    }

    public boolean ukloniAdmina(Admin adminLogout) throws AdminException {
        if (ulogovaniAdmini.remove(adminLogout)) {
            return true;
        }
        throw new AdminException("Admin ne postoji");
    }

    public void ocistiListuAdmina(){
        ulogovaniAdmini.clear();
    }
    
    public List<Boolean> obradiAdmine(List<Admin> sviAdmini) {
        List<Boolean> ulogovani = new ArrayList<>();
        for (Admin a : sviAdmini) {
            if (ulogovaniAdmini.contains(a)) {
                ulogovani.add(true);
            } else {
                ulogovani.add(false);
            }
        }

        return ulogovani;
    }

}
