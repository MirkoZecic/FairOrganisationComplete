/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
import rs.ac.bg.fon.ps.communication.Communication;
import rs.ac.bg.fon.ps.domain.Manifestacija;
import rs.ac.bg.fon.ps.domain.TipManifestacije;
import rs.ac.bg.fon.ps.exceptions.CommunicationException;
import rs.ac.bg.fon.ps.properties.SetProperties;
import rs.ac.bg.fon.ps.view.constant.Constants;
import rs.ac.bg.fon.ps.view.cordinator.MainCordinator;
import rs.ac.bg.fon.ps.view.form.FrmManifestation;
import rs.ac.bg.fon.ps.view.util.FormMode;

/**
 *
 * @author Mirko
 */
public class ManifestationController implements SetProperties {

    private final FrmManifestation frmManifestation;

    public ManifestationController(FrmManifestation frmManifestation) {
        this.frmManifestation = frmManifestation;
        addActionListenersTxt();
        addActionListeners();
    }

    private void addActionListeners() {
        frmManifestation.addSaveBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    if (validateForm()) {
                        save();
                    }
                } catch (Exception ex) {
                    System.out.println("Datum nije ispravan");
                }

            }

            private void save() {
                try {
                    Manifestacija manifestacija = makeManifestationFromForm();

                    Communication.getInstance().KreirajManifestaciju(manifestacija);
                    JOptionPane.showMessageDialog(frmManifestation, "Sistem je zapamtio manifestaciju", "Uspesno", JOptionPane.INFORMATION_MESSAGE);
                    frmManifestation.dispose();
                } catch (CommunicationException se) {
                    JOptionPane.showMessageDialog(frmManifestation, "Server je ugasen\nAplikacija ce se ugasiti", "Greska", JOptionPane.ERROR_MESSAGE);
                    frmManifestation.dispose();
                    System.exit(0);
                } catch (Exception ex) {
                    Logger.getLogger(ManifestationController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        frmManifestation.addEnableChangesBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setupComponents(FormMode.FORM_EDIT);
            }
        });

        frmManifestation.addCancelBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancel();
            }

            private void cancel() {
                frmManifestation.dispose();
            }
        });

        frmManifestation.addDeleteBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dialogResult = JOptionPane.showConfirmDialog(frmManifestation, "Da li ste sigurni da zelite da izbrisete manifestaciju", "Potvrda", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    delete();
                }
            }

            private void delete() {
                Manifestacija manifestacija = (Manifestacija) MainCordinator.getInstance().getParam(Constants.PARAM_MANIFESTATION);

                try {
                    Communication.getInstance().deleteManifestation(manifestacija);
                    JOptionPane.showMessageDialog(frmManifestation, "Sistem je izbrisao manifestaciju", "Brisanje manifestacije", JOptionPane.INFORMATION_MESSAGE);
                    frmManifestation.dispose();
                } catch (CommunicationException se) {
                    JOptionPane.showMessageDialog(frmManifestation, "Server je ugasen\nAplikacija ce se ugasiti", "Greska", JOptionPane.ERROR_MESSAGE);
                    frmManifestation.dispose();
                    System.exit(0);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmManifestation, "Sistem ne moze da izbrise manifestaciju", "Brisanje manifestacije", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(ManifestationController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        frmManifestation.addEditBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dialogResult = JOptionPane.showConfirmDialog(frmManifestation, "Da li ste sigurni da zelite da izmenite manifestaciju", "Potvrda", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    edit();
                }
            }

            private void edit() {
                Manifestacija manifestacija = makeManifestationFromForm();
                Manifestacija originalnaManifestacija = (Manifestacija) MainCordinator.getInstance().getParam(Constants.PARAM_MANIFESTATION);
                manifestacija.setId(originalnaManifestacija.getManifestacijaId());

                try {
                    Communication.getInstance().editManifestation(manifestacija);
                    JOptionPane.showMessageDialog(frmManifestation, "Sistem je zapamtio manifestaciju", "Izmena manifestacije", JOptionPane.INFORMATION_MESSAGE);
                    frmManifestation.dispose();
                } catch (CommunicationException se) {
                    JOptionPane.showMessageDialog(frmManifestation, "Server je ugasen\nAplikacija ce se ugasiti", "Greska", JOptionPane.ERROR_MESSAGE);
                    frmManifestation.dispose();
                    System.exit(0);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmManifestation, "Sistem ne moze da zapamti manifestaciju", "Izmena manifestacije", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(ManifestationController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public void openForm(FormMode formMode) {
        frmManifestation.setLocationRelativeTo(MainCordinator.getInstance().getMainContoller().getFrmMain());
        if (MainCordinator.getInstance().isIsEnglish()) {
            readConfigProperties();
        }
        prepareView(formMode);
        frmManifestation.setVisible(true);
    }

    private void prepareView(FormMode formMode) {
        frmManifestation.setTitle("Manifestacija");
        fillComboBox();
        designComponents(formMode);
        setupComponents(formMode);
        frmManifestation.getLblIme().requestFocus();
    }

    private boolean validateForm() throws Exception {
        boolean uspesanNaziv = validateNaziv();
        boolean uspesanDatum = validateDatum();
        return uspesanDatum && uspesanNaziv;
    }

    private void designComponents(FormMode formMode) {
        switch (formMode) {
            case FORM_ADD:
                frmManifestation.getBtnSave().setForeground(Color.GREEN);
                frmManifestation.getBtnCancel().setForeground(Color.BLUE);
                break;
            case FORM_VIEW:
                frmManifestation.getBtnCancel().setForeground(Color.BLUE);
                frmManifestation.getBtnDelete().setForeground(Color.RED);
                frmManifestation.getBtnEnableChanges().setForeground(Color.GREEN);
                break;
            case FORM_EDIT:
                frmManifestation.getBtnCancel().setForeground(Color.BLUE);
                frmManifestation.getBtnEdit().setForeground(Color.GREEN);
                break;
        }
    }

    private void setupComponents(FormMode formMode) {
        switch (formMode) {
            case FORM_ADD:
                frmManifestation.getBtnCancel().setEnabled(true);
                frmManifestation.getBtnDelete().setEnabled(false);
                frmManifestation.getBtnEdit().setEnabled(false);
                frmManifestation.getBtnEnableChanges().setEnabled(false);
                frmManifestation.getBtnSave().setEnabled(true);

                frmManifestation.getTxtNaziv().setEnabled(true);
                frmManifestation.getCbTipManifestacije().setEnabled(true);
                frmManifestation.getDatumOd().setEnabled(true);
                frmManifestation.getDatumDo().setEnabled(true);
                break;
            case FORM_VIEW:
                frmManifestation.getBtnCancel().setEnabled(true);
                frmManifestation.getBtnDelete().setEnabled(false);
                frmManifestation.getBtnEdit().setEnabled(false);
                frmManifestation.getBtnEnableChanges().setEnabled(true);
                frmManifestation.getBtnSave().setEnabled(false);

                //zabrani izmenu vrednosti
                frmManifestation.getTxtNaziv().setEnabled(false);
                frmManifestation.getCbTipManifestacije().setEnabled(false);
                frmManifestation.getDatumOd().setEnabled(false);
                frmManifestation.getDatumDo().setEnabled(false);
                //get manifestation
                Manifestacija manifestacija = (Manifestacija) MainCordinator.getInstance().getParam(Constants.PARAM_MANIFESTATION);
                frmManifestation.getTxtNaziv().setText(manifestacija.getNaziv() + "");
                frmManifestation.getCbTipManifestacije().setSelectedItem(manifestacija.getTipManifestacije());
                frmManifestation.setDateOd(manifestacija.getDatumOd());
                frmManifestation.setDateDo(manifestacija.getDatumDo());
                break;
            case FORM_EDIT:
                frmManifestation.getBtnCancel().setEnabled(true);
                frmManifestation.getBtnDelete().setEnabled(true);
                frmManifestation.getBtnEdit().setEnabled(true);
                frmManifestation.getBtnEnableChanges().setEnabled(false);
                frmManifestation.getBtnSave().setEnabled(false);

                //zabrani izmenu vrednosti
                frmManifestation.getTxtNaziv().setEnabled(true);
                frmManifestation.getCbTipManifestacije().setEnabled(true);
                frmManifestation.getDatumOd().setEnabled(true);
                frmManifestation.getDatumDo().setEnabled(true);

                //boja txt polja
                frmManifestation.getTxtNaziv().setForeground(Color.black);
                break;
        }
    }

    private void fillComboBox() {
        frmManifestation.getCbTipManifestacije().removeAllItems();
        try {
            List<TipManifestacije> manifestationTypes = Communication.getInstance().UcitajListuTipovaManifestacija();
            for (TipManifestacije m : manifestationTypes) {
                frmManifestation.getCbTipManifestacije().addItem(m);
            }

        } catch (SocketException se) {
            JOptionPane.showMessageDialog(frmManifestation, "Server je ugasen\nAplikacija ce se ugasiti", "Greska", JOptionPane.ERROR_MESSAGE);
            frmManifestation.dispose();
            System.exit(0);
        } catch (Exception ex) {
            Logger.getLogger(ManifestationController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(frmManifestation, "Greska pri pristupanju tipovima manifestacija", "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Manifestacija makeManifestationFromForm() {
        Manifestacija manifestacija = new Manifestacija();

        manifestacija.setNaziv(frmManifestation.getTxtNaziv().getText().trim());
        manifestacija.setTipManifestacije((TipManifestacije) frmManifestation.getCbTipManifestacije().getSelectedItem());
        manifestacija.setDatumOd(frmManifestation.getDatumOd().getDate());
        manifestacija.setDatumDo(frmManifestation.getDatumDo().getDate());
        return manifestacija;
    }

    private boolean validateNaziv() {
        if (MainCordinator.getInstance().isIsEnglish()) {
            return validateNazivTemplate("Enter manifestation name...", "Name musn't be empty!");
        }
        return validateNazivTemplate("Unesite naziv manifestacije...", "Naziv ne sme da bude prazan!");
    }

    private boolean validateNazivTemplate(String text1, String text2) {
        if (frmManifestation.getTxtNaziv().getText() == null || frmManifestation.getTxtNaziv().getText().trim().isEmpty() || frmManifestation.getTxtNaziv().getText().equals(text1)) {
            frmManifestation.getTxtNaziv().setText(text2);
            frmManifestation.getTxtNaziv().setForeground(Color.red);
            return false;
        }
        return true;
    }

    private boolean validateDatum() throws Exception {
        Date datumOd = frmManifestation.getDatumOd().getDate();
        Date datumDo = frmManifestation.getDatumDo().getDate();

        Calendar trenutno = Calendar.getInstance();
        trenutno.set(trenutno.get(Calendar.YEAR), trenutno.get(Calendar.MONTH), trenutno.get(Calendar.DAY_OF_MONTH), 0, 0, 0);

        if (datumOd.after(datumDo)) {
            throw new Exception("Datum zavrsetka mora biti nakon datuma pocetka!");
        }
        if (datumOd.before(trenutno.getTime())) {
            throw new Exception("Datum pocetka ne sme biti u proslosti!");
        }
        return true;
    }

    private void addActionListenersTxt() {

        frmManifestation.getTxtNaziv().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (frmManifestation.getTxtNaziv().getText().equals("Unesite naziv manifestacije...") || frmManifestation.getTxtNaziv().getText().equals("Naziv ne sme da bude prazan!")
                        || frmManifestation.getTxtNaziv().getText().equals("Enter manifestation name...") || frmManifestation.getTxtNaziv().getText().equals("Name musn't be empty!")) {
                    frmManifestation.getTxtNaziv().setText("");
                    frmManifestation.getTxtNaziv().setForeground(Color.black);
                }
            }
        });
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
            frmManifestation.setTitle(prop.getProperty("titleManifestation"));
            frmManifestation.getjPanel1().setBorder(new TitledBorder(prop.getProperty("titleManifestation")));

            frmManifestation.getLblIme().setText(prop.getProperty("lblNaziv"));
            frmManifestation.getLblTip().setText(prop.getProperty("lblManifestationType"));
            frmManifestation.getLblDatumOd().setText(prop.getProperty("lblDatumOd"));
            frmManifestation.getLblDatumDo().setText(prop.getProperty("lblDatumDo"));

            frmManifestation.getTxtNaziv().setText(prop.getProperty("txtNaziv"));

            frmManifestation.getBtnCancel().setText(prop.getProperty("btnCancel"));
            frmManifestation.getBtnDelete().setText(prop.getProperty("btnDelete"));
            frmManifestation.getBtnEdit().setText(prop.getProperty("btnEdit"));
            frmManifestation.getBtnEnableChanges().setText(prop.getProperty("btnEnableChanges"));
            frmManifestation.getBtnSave().setText(prop.getProperty("btnSave"));

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
