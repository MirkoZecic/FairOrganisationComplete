/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ps.communication.Communication;
import rs.ac.bg.fon.ps.domain.Admin;
import rs.ac.bg.fon.ps.properties.SetProperties;
import rs.ac.bg.fon.ps.view.constant.Constants;
import rs.ac.bg.fon.ps.view.cordinator.MainCordinator;
import rs.ac.bg.fon.ps.view.form.FrmMain;

/**
 *
 * @author Mirko
 */
public class MainController implements SetProperties {

    private final FrmMain frmMain;

    public MainController(FrmMain frmMain) {
        this.frmMain = frmMain;
        addActionListener();
    }

    public void openForm() {
        Admin admin;
        admin = (Admin) MainCordinator.getInstance().getParam(Constants.CURRENT_USER);
        frmMain.getMenuCurrentUser().setText(admin.getIme() + " " + admin.getPrezime());
        prepareView();
        frmMain.setVisible(true);
    }

    private void addActionListener() {
        // LOGOUT
        frmMain.addBtnLogoutActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });

        // IZLAGAC
        frmMain.miExhibitorNewAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                miExhibitorNewActionPerformed(e);
            }

            private void miExhibitorNewActionPerformed(ActionEvent e) {
                MainCordinator.getInstance().openAddNewExhibitorForm();
            }
        });

        frmMain.miExhibitorShowAllActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                miExhibitorShowAllActionPerformed(e);
            }

            private void miExhibitorShowAllActionPerformed(ActionEvent e) {
                MainCordinator.getInstance().openViewAllExhibitorsForm();
            }
        });
        // MANIFESTACIJA
        frmMain.miManifestationNewAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                miManifestationNewActionPerformed(e);
            }

            private void miManifestationNewActionPerformed(ActionEvent e) {
                MainCordinator.getInstance().openAddNewManifestationForm();
            }
        });

        frmMain.miManifestationShowAllActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                miManifestationShowAllActionPerformed(e);
            }

            private void miManifestationShowAllActionPerformed(ActionEvent e) {
                MainCordinator.getInstance().openViewAllManifestationsForm();
            }
        });

        // DNEVNI RASPORED
        frmMain.miDailyScheduleAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCordinator.getInstance().openDailyScheduleForm();
            }
        });

        frmMain.miDailyScheduleViewAllActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCordinator.getInstance().openViewAllDailySchedulesForm();
            }
        });

        frmMain.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                logout();
            }
        });
    }

    public FrmMain getFrmMain() {
        return frmMain;
    }

    private void logout() {
        try {
            Admin admin = (Admin) MainCordinator.getInstance().getParam(Constants.CURRENT_USER);
            JOptionPane.showMessageDialog(
                    frmMain,
                    "Dovidjenja " + admin.getIme() + " " + admin.getPrezime(),
                    "Odjava", JOptionPane.INFORMATION_MESSAGE
            );
            frmMain.dispose();
            Communication.getInstance().logout(admin);
        } catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void prepareView() {
        frmMain.setLocationRelativeTo(null);
        if (MainCordinator.getInstance().isIsEnglish()) {
            readConfigProperties();
        }
    }

    @Override
    public void readConfigProperties() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            if (MainCordinator.getInstance().isIsEnglish()) {
                input = new FileInputStream("config_english.properties");
            } else {
                input = new FileInputStream("config_serbian.properties");
            }
            prop.load(input);
            frmMain.getMenuIzlagac().setText(prop.getProperty("menuIzlagac"));
            frmMain.getMiNoviIzlagac().setText(prop.getProperty("miNoviIzlagac"));
            frmMain.getMiPrikazIzlagaca().setText(prop.getProperty("miPrikaziIzlagace"));

            frmMain.getMenuManifestacija().setText(prop.getProperty("menuManifestacija"));
            frmMain.getMiNovaManifestacija().setText(prop.getProperty("miNovaManifestacija"));
            frmMain.getMiPrikaziManifestacije().setText(prop.getProperty("miPrikaziManifestacije"));

            frmMain.getMenuDnevniRaspored().setText(prop.getProperty("menuDnevniRaspored"));
            frmMain.getMiNoviDnevniRaspored().setText(prop.getProperty("miNoviDnevniRaspored"));
            frmMain.getMiPrikaziDnevneRasporede().setText(prop.getProperty("miPrikaziDnevneRasporede"));

            frmMain.getBtnLogOut().setText(prop.getProperty("btnLogOut"));
        } catch (IOException ex) {
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
