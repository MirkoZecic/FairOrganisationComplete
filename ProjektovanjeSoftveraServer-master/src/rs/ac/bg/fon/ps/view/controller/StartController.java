/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ps.controller.Controller;
import rs.ac.bg.fon.ps.cordinator.MainCordinator;
import rs.ac.bg.fon.ps.domain.Admin;
import rs.ac.bg.fon.ps.properties.DatabaseConfiguration;
import rs.ac.bg.fon.ps.server.Server;
import rs.ac.bg.fon.ps.view.FrmStart;
import rs.ac.bg.fon.ps.view.component.StatusColumnCellRenderer;
import rs.ac.bg.fon.ps.view.component.table.LoggedAdminTableModel;

/**
 *
 * @author Mirko
 */
public class StartController {

    private final FrmStart frmStart;
    Server server;

    public StartController(FrmStart startForma) {
        this.frmStart = startForma;
        prepareView();
        designComponents();
        addActionListener();
    }

    public void openForm() {
        frmStart.setVisible(true);
    }

    private void addActionListener() {
        frmStart.addStartServerActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startServer();
            }

            private void startServer() {
                server = new Server();
                server.start();
                fillTblExhibitors();
                frmStart.getBtnStart().setEnabled(false);
                frmStart.getBtnStop().setEnabled(true);
                frmStart.getTxtStatus().setText("SERVER JE POKRENUT");
                frmStart.getTxtStatus().setForeground(Color.blue);
                frmStart.getTxtUrl().setEnabled(false);
                frmStart.getTxtUsername().setEnabled(false);
                frmStart.getTxtPassword().setEnabled(false);
                MainCordinator.getInstance().ocistiListuAdmina();
                fillTblExhibitors();
            }
        });

        frmStart.addStopServerActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    stopServer();
                } catch (IOException ex) {
                    Logger.getLogger(StartController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            private void stopServer() throws IOException {
                server.stopServer();
                frmStart.getBtnStart().setEnabled(true);
                frmStart.getBtnStop().setEnabled(false);
                frmStart.getTxtStatus().setText("SERVER JE UGASEN");
                frmStart.getTxtStatus().setForeground(Color.red);
                frmStart.getTxtUrl().setEnabled(true);
                frmStart.getTxtUsername().setEnabled(true);
                frmStart.getTxtPassword().setEnabled(true);
            }
        });

        frmStart.addSaveBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = frmStart.getTxtUrl().getText().trim();
                String username = frmStart.getTxtUsername().getText().trim();
                String password = frmStart.getTxtPassword().getText().trim();
                DatabaseConfiguration.getInstance().writeConfigProperties(url, username, password);
                JOptionPane.showMessageDialog(frmStart, "Uspesno sacuvana konfiguracija!", "Uspesno cuvanje", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        frmStart.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                fillTblExhibitors();
            }
        });

    }

    private void fillTblExhibitors() {
        if (server == null) {
            LoggedAdminTableModel etm = new LoggedAdminTableModel(new ArrayList<Admin>(), new ArrayList<>());
            frmStart.getTblSviAdmini().setModel(etm);
            return;
        }
        List<Admin> sviAdmini = new ArrayList<>();
        try {
            Controller.getInstance().UcitajListuAdmina(sviAdmini);
            List<Boolean> ulogovani = MainCordinator.getInstance().obradiAdmine(sviAdmini);

            LoggedAdminTableModel etm = new LoggedAdminTableModel(sviAdmini, ulogovani);
            frmStart.getTblSviAdmini().setModel(etm);
            frmStart.getTblSviAdmini().setAutoCreateRowSorter(true);
            frmStart.getTblSviAdmini().getColumnModel().getColumn(4).setCellRenderer(new StatusColumnCellRenderer());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frmStart, "Greska pri uspostavljanju konekcije!\n" + ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(StartController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void prepareView() {
        frmStart.setLocationRelativeTo(null);
        frmStart.setTitle("Server program");
        DatabaseConfiguration.getInstance().readConfigProperties();
        frmStart.getTxtUrl().setText(DatabaseConfiguration.getInstance().getUrl());
        frmStart.getTxtUsername().setText(DatabaseConfiguration.getInstance().getUsername());
        frmStart.getTxtPassword().setText(DatabaseConfiguration.getInstance().getPassword());
    }

    private void designComponents() {
        frmStart.getBtnStart().setForeground(Color.green);
        frmStart.getBtnStop().setForeground(Color.red);
    }

}
