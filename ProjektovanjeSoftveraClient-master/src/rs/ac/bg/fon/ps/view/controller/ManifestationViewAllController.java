/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import rs.ac.bg.fon.ps.communication.Communication;
import rs.ac.bg.fon.ps.domain.Manifestacija;
import rs.ac.bg.fon.ps.properties.SetProperties;
import rs.ac.bg.fon.ps.view.component.table.ManifestationTableModel;
import rs.ac.bg.fon.ps.view.constant.Constants;
import rs.ac.bg.fon.ps.view.cordinator.MainCordinator;
import rs.ac.bg.fon.ps.view.form.FrmViewAllManifestations;

/**
 *
 * @author Mirko
 */
public class ManifestationViewAllController implements SetProperties {

    private final FrmViewAllManifestations frmViewAllManifestations;
    private boolean optionPaneActive = false;

    public ManifestationViewAllController(FrmViewAllManifestations form) {
        this.frmViewAllManifestations = form;
        addActionListeners();
    }

    public void openForm() {
        frmViewAllManifestations.setLocationRelativeTo(MainCordinator.getInstance().getMainContoller().getFrmMain());
        prepareView();
        if (MainCordinator.getInstance().isIsEnglish()) {
            readConfigProperties();
        }
        frmViewAllManifestations.setVisible(true);
    }

    private void prepareView() {
        frmViewAllManifestations.setTitle("Sve manifestacije");
    }

    private void addActionListeners() {
        frmViewAllManifestations.setBtnDetailsAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = frmViewAllManifestations.getTblManifestation().getSelectedRow();
                if (row >= 0) {
                    Manifestacija manifestacija = ((ManifestationTableModel) frmViewAllManifestations.getTblManifestation().getModel()).getManifestationAt(row);
                    MainCordinator.getInstance().addParam(Constants.PARAM_MANIFESTATION, manifestacija);
                    MainCordinator.getInstance().openManifestationDetailsManifestationForm();
                } else {
                    JOptionPane.showMessageDialog(frmViewAllManifestations, "Morate odabrati manifestaciju!", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        frmViewAllManifestations.setBtnCancelAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmViewAllManifestations.dispose();
            }
        });

        frmViewAllManifestations.setTxtPretragaDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                fillTblManifestation(frmViewAllManifestations.getTxtPretraga().getText().trim());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                fillTblManifestation(frmViewAllManifestations.getTxtPretraga().getText().trim());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                fillTblManifestation(frmViewAllManifestations.getTxtPretraga().getText().trim());
            }

        });

        frmViewAllManifestations.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                fillTblManifestation(frmViewAllManifestations.getTxtPretraga().getText().trim());
            }
        });
    }

    private void fillTblManifestation(String text) {
        List<Manifestacija> manifestacije;
        try {
            manifestacije = Communication.getInstance().getAllManifestations();
            manifestacije = filtriraj(manifestacije, text);

            ManifestationTableModel ptm = new ManifestationTableModel(manifestacije);
            frmViewAllManifestations.getTblManifestation().setModel(ptm);
            if (ptm.getAllManifestations().isEmpty()) {
                if (!optionPaneActive) {
                    JOptionPane.showMessageDialog(frmViewAllManifestations, "Sistem ne moze da nadje manifestacije po zadatoj vrednosti!", "Greska", JOptionPane.ERROR_MESSAGE);
                    optionPaneActive = true;
                }
            } else {
                optionPaneActive = false;
            }
        } catch (SocketException se) {
            JOptionPane.showMessageDialog(frmViewAllManifestations, "Server je ugasen\nAplikacija ce se ugasiti", "Greska", JOptionPane.ERROR_MESSAGE);
            frmViewAllManifestations.dispose();
            System.exit(0);

//            MainCordinator.getInstance().getMainContoller().ugasiFormu();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frmViewAllManifestations, "Greska prilikom pristupanja manifestacijama!", "Greska", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ManifestationTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Manifestacija> filtriraj(List<Manifestacija> manifestacije, String text) {
        if (text.isEmpty() || text.equals("*")) {
            return manifestacije;
        }

        List<Manifestacija> modifikovaneManifestacije = new LinkedList<>();

        for (Manifestacija man : manifestacije) {
            if (man.getNaziv().contains(text) || man.getTipManifestacije().getNazivTipa().contains(text)
                    || String.valueOf(man.getDatumOd()).contains(text) || String.valueOf(man.getDatumDo()).contains(text)) {
                modifikovaneManifestacije.add(man);
            }
        }

        return modifikovaneManifestacije;
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
            frmViewAllManifestations.setTitle(prop.getProperty("titleAllManifestations"));
            frmViewAllManifestations.getLblPretraga().setText(prop.getProperty("lblSearchManifestations"));
            frmViewAllManifestations.getBtnDetails().setText(prop.getProperty("btnDetails"));
            frmViewAllManifestations.getBtnCancel().setText(prop.getProperty("btnCancel"));

            //ManifestationTableModel etm = (ManifestationTableModel) frmViewAllManifestations.getTblExhibitor().getModel();
//            etm.changeColumnNames(prop.getProperty("tblIme"), prop.getProperty("tblPrezime"), prop.getProperty("tblTelefon"),
//                    prop.getProperty("tblEmail"), prop.getProperty("tblKompanija"));
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
