/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
import rs.ac.bg.fon.ps.communication.Communication;
import rs.ac.bg.fon.ps.domain.Izlagac;
import rs.ac.bg.fon.ps.domain.Kompanija;
import rs.ac.bg.fon.ps.properties.SetProperties;
import rs.ac.bg.fon.ps.view.constant.Constants;
import rs.ac.bg.fon.ps.view.cordinator.MainCordinator;
import rs.ac.bg.fon.ps.view.form.FrmExhibitor;
import rs.ac.bg.fon.ps.view.util.FormMode;

/**
 *
 * @author Mirko
 */
public class ExhibitorController implements SetProperties {

    private final FrmExhibitor frmExhibitor;

    public ExhibitorController(FrmExhibitor frmExhibitor) {
        this.frmExhibitor = frmExhibitor;
        addActionListenersTxt();
        addActionListeners();
    }

    private void addActionListeners() {
        frmExhibitor.addSaveBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateForm()) {
                    save();
                }
            }

            private void save() {
                try {
                    Izlagac izlagac = makeExhibitorFromForm();

                    Communication.getInstance().addExhibitor(izlagac);
                    JOptionPane.showMessageDialog(frmExhibitor, "Sistem je zapamtio izlagaca", "Uspesno", JOptionPane.INFORMATION_MESSAGE);
                    frmExhibitor.dispose();
                } catch (SocketException se) {
                    JOptionPane.showMessageDialog(frmExhibitor, "Server je ugasen\nAplikacija ce se ugasiti", "Greska", JOptionPane.ERROR_MESSAGE);
                    frmExhibitor.dispose();
                    System.exit(0);
                } catch (Exception ex) {
                    Logger.getLogger(FrmExhibitor.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(frmExhibitor, "Sistem ne moze da zapamti izlagaca", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frmExhibitor.addEnableChangesBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setupComponents(FormMode.FORM_EDIT);
            }
        });

        frmExhibitor.addCancelBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancel();
            }

            private void cancel() {
                frmExhibitor.dispose();
            }
        });

        frmExhibitor.addDeleteBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dialogResult = JOptionPane.showConfirmDialog(frmExhibitor, "Da li ste sigurni da zelite da izbrisete izlagaca", "Potvrda", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    delete();
                }
            }

            private void delete() {
                Izlagac izlagac = (Izlagac) MainCordinator.getInstance().getParam(Constants.PARAM_EXHIBITOR);

                try {
                    Communication.getInstance().deleteExhibitor(izlagac);
                    JOptionPane.showMessageDialog(frmExhibitor, "Izlagac uspesno izbrisan", "Brisanje izlagaca", JOptionPane.INFORMATION_MESSAGE);
                    frmExhibitor.dispose();
                } catch (SocketException se) {
                    JOptionPane.showMessageDialog(frmExhibitor, "Server je ugasen\nAplikacija ce se ugasiti", "Greska", JOptionPane.ERROR_MESSAGE);
                    frmExhibitor.dispose();
                    System.exit(0);
                    //MainCordinator.getInstance().getMainContoller().ugasiFormu();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmExhibitor, "Greska pri brisanju izlagaca!\n" + ex.getMessage(), "Brisanje izlagaca", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(ExhibitorController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        frmExhibitor.addEditBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dialogResult = JOptionPane.showConfirmDialog(frmExhibitor, "Da li ste sigurni da zelite da izmenite izlagaca", "Potvrda", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    edit();
                }
            }

            private void edit() {
                Izlagac izlagac = makeExhibitorFromForm();
                Izlagac originalniIzlagac = (Izlagac) MainCordinator.getInstance().getParam(Constants.PARAM_EXHIBITOR);
                izlagac.setId(originalniIzlagac.getIzlagacId());

                try {
                    Communication.getInstance().editExhibitor(izlagac);
                    JOptionPane.showMessageDialog(frmExhibitor, "Sistem je zapamtio izlagaca.", "Izmena izlagaca", JOptionPane.INFORMATION_MESSAGE);
                    frmExhibitor.dispose();
                } catch (SocketException se) {
                    JOptionPane.showMessageDialog(frmExhibitor, "Server je ugasen\nAplikacija ce se ugasiti", "Greska", JOptionPane.ERROR_MESSAGE);
                    frmExhibitor.dispose();
                    //MainCordinator.getInstance().getMainContoller().ugasiFormu();
                    System.exit(0);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmExhibitor, "Sistem ne moze da zapamti izlagaca", "Izmena izlagaca", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(ExhibitorController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public void openForm(FormMode formMode) {
        frmExhibitor.setLocationRelativeTo(MainCordinator.getInstance().getMainContoller().getFrmMain());
        if (MainCordinator.getInstance().isIsEnglish()) {
            readConfigProperties();
        }
        prepareView(formMode);
        frmExhibitor.setVisible(true);
    }

    private void prepareView(FormMode formMode) {
        frmExhibitor.setTitle("Izlagac");
        fillComboBox();
        designComponents(formMode);
        setupComponents(formMode);
        frmExhibitor.getLblIme().requestFocus();
    }

    private boolean validateForm() {
        boolean uspesnoIme = validateFirstName();
        boolean uspesnoPrezime = validateLastName();
        boolean uspesanTelefon = validateTelephone();
        boolean uspesanEmail = validateEmail();

        return uspesanEmail && uspesanTelefon && uspesnoIme && uspesnoPrezime;
    }

    private void designComponents(FormMode formMode) {
        switch (formMode) {
            case FORM_ADD:
                frmExhibitor.getBtnSave().setForeground(Color.GREEN);
                frmExhibitor.getBtnCancel().setForeground(Color.BLUE);
                break;
            case FORM_VIEW:
                frmExhibitor.getBtnCancel().setForeground(Color.BLUE);
                frmExhibitor.getBtnDelete().setForeground(Color.RED);
                frmExhibitor.getBtnEnableChanges().setForeground(Color.GREEN);
                break;
            case FORM_EDIT:
                frmExhibitor.getBtnCancel().setForeground(Color.BLUE);
                frmExhibitor.getBtnEdit().setForeground(Color.GREEN);
                break;
        }
    }

    private void setupComponents(FormMode formMode) {
        switch (formMode) {
            case FORM_ADD:
                frmExhibitor.getBtnCancel().setEnabled(true);
                frmExhibitor.getBtnDelete().setEnabled(false);
                frmExhibitor.getBtnEdit().setEnabled(false);
                frmExhibitor.getBtnEnableChanges().setEnabled(false);
                frmExhibitor.getBtnSave().setEnabled(true);

                frmExhibitor.getTxtIme().setEnabled(true);
                frmExhibitor.getTxtPrezime().setEnabled(true);
                frmExhibitor.getTxtEmail().setEnabled(true);
                frmExhibitor.getTxtTelefon().setEnabled(true);
                break;
            case FORM_VIEW:
                frmExhibitor.getBtnCancel().setEnabled(true);
                frmExhibitor.getBtnDelete().setEnabled(false);
                frmExhibitor.getBtnEdit().setEnabled(false);
                frmExhibitor.getBtnEnableChanges().setEnabled(true);
                frmExhibitor.getBtnSave().setEnabled(false);

                //zabrani izmenu vrednosti
                frmExhibitor.getTxtIme().setEnabled(false);
                frmExhibitor.getTxtPrezime().setEnabled(false);
                frmExhibitor.getTxtEmail().setEnabled(false);
                frmExhibitor.getTxtTelefon().setEnabled(false);
                frmExhibitor.getCbKompanija().setEnabled(false);

                //get exhibitor
                Izlagac izlagac = (Izlagac) MainCordinator.getInstance().getParam(Constants.PARAM_EXHIBITOR);
                frmExhibitor.getTxtIme().setText(izlagac.getIme() + "");
                frmExhibitor.getTxtPrezime().setText(izlagac.getPrezime());
                frmExhibitor.getTxtEmail().setText(izlagac.getEmail());
                frmExhibitor.getTxtTelefon().setText(izlagac.getKontaktTelefon());
                frmExhibitor.getCbKompanija().setSelectedItem(izlagac.getKompanija());
                break;
            case FORM_EDIT:
                frmExhibitor.getBtnCancel().setEnabled(true);
                frmExhibitor.getBtnDelete().setEnabled(true);
                frmExhibitor.getBtnEdit().setEnabled(true);
                frmExhibitor.getBtnEnableChanges().setEnabled(false);
                frmExhibitor.getBtnSave().setEnabled(false);

                //zabrani izmenu vrednosti
                frmExhibitor.getTxtIme().setEnabled(true);
                frmExhibitor.getTxtPrezime().setEnabled(true);
                frmExhibitor.getTxtEmail().setEnabled(true);
                frmExhibitor.getTxtTelefon().setEnabled(true);
                frmExhibitor.getCbKompanija().setEnabled(true);

                //oboji txt polja u crno
                frmExhibitor.getTxtEmail().setForeground(Color.black);
                frmExhibitor.getTxtIme().setForeground(Color.black);
                frmExhibitor.getTxtPrezime().setForeground(Color.black);
                frmExhibitor.getTxtTelefon().setForeground(Color.black);
                break;
        }
    }

    private void fillComboBox() {
        frmExhibitor.getCbKompanija().removeAllItems();
        try {
            List<Kompanija> companies = Communication.getInstance().getAllCompanies();
            for (Kompanija k : companies) {
                frmExhibitor.getCbKompanija().addItem(k);
            }

        } catch (SocketException se) {
            JOptionPane.showMessageDialog(frmExhibitor, "Server je ugasen\nAplikacija ce se ugasiti", "Greska", JOptionPane.ERROR_MESSAGE);
            frmExhibitor.dispose();
            System.exit(0);
            //MainCordinator.getInstance().getMainContoller().ugasiFormu();
        } catch (Exception ex) {
            Logger.getLogger(ExhibitorController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(frmExhibitor, "Greska pri pristupanju kompanijama!", "Pristupanje kompanijama", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Izlagac makeExhibitorFromForm() {
        Izlagac izlagac = new Izlagac();

        izlagac.setIme(frmExhibitor.getTxtIme().getText().trim());
        izlagac.setPrezime(frmExhibitor.getTxtPrezime().getText().trim());
        izlagac.setEmail(frmExhibitor.getTxtEmail().getText().trim());
        izlagac.setKontaktTelefon(frmExhibitor.getTxtTelefon().getText().trim());
        izlagac.setKompanija((Kompanija) frmExhibitor.getCbKompanija().getSelectedItem());
        return izlagac;
    }

    private boolean validateFirstName() {
        if (MainCordinator.getInstance().isIsEnglish()) {
            return validateFirstNameTemplate("Enter your firstname...", "Name can't be empty!", "Name must contain only letters!");
        }
        return validateFirstNameTemplate("Unesite ime...", "Ime ne sme da bude prazno!", "Ime sme da sadrzi samo slova!");
    }

    private boolean validateFirstNameTemplate(String text1, String text2, String text3) {
        if (frmExhibitor.getTxtIme().getText() == null || frmExhibitor.getTxtIme().getText().trim().isEmpty() || frmExhibitor.getTxtIme().getText().equals(text1)) {
            frmExhibitor.getTxtIme().setText(text2);
            frmExhibitor.getTxtIme().setForeground(Color.red);
            return false;
        } else {
            Pattern p = Pattern.compile("[^A-Za-z]");
            Matcher m;
            if (!frmExhibitor.getTxtIme().getText().equals(text2)) {
                m = p.matcher(frmExhibitor.getTxtIme().getText().trim());
                boolean b = m.find();
                if (b) {
                    frmExhibitor.getTxtIme().setText(text3);
                    frmExhibitor.getTxtIme().setForeground(Color.red);
                    return false;
                }
                return true;
            }
            return false;
        }
    }

    private boolean validateLastName() {
        if (MainCordinator.getInstance().isIsEnglish()) {
            return validateLastNameTemplate("Enter your lastname...", "Lastname can't be empty!", "Lastname must contain only letters!");
        }
        return validateLastNameTemplate("Unesite prezime...", "Prezime ne sme da bude prazno!", "Prezime sme da sadrzi samo slova!");
    }

    private boolean validateLastNameTemplate(String text1, String text2, String text3) {
        if (frmExhibitor.getTxtPrezime() == null || frmExhibitor.getTxtPrezime().getText().trim().isEmpty() || frmExhibitor.getTxtPrezime().getText().equals(text1)) {
            frmExhibitor.getTxtPrezime().setText(text2);
            frmExhibitor.getTxtPrezime().setForeground(Color.red);
            return false;
        } else {
            Pattern p = Pattern.compile("[^A-Za-z]");
            Matcher m;
            if (!frmExhibitor.getTxtPrezime().getText().equals(text2)) {
                m = p.matcher(frmExhibitor.getTxtPrezime().getText().trim());
                boolean b = m.find();
                if (b) {
                    frmExhibitor.getTxtPrezime().setText(text3);
                    frmExhibitor.getTxtPrezime().setForeground(Color.red);
                    return false;
                }
                return true;
            }
            return false;
        }
    }

    private boolean validateTelephone() {
        if (MainCordinator.getInstance().isIsEnglish()) {
            return validateTelephoneTemplate("Enter your telephone...", "Telephone can't be empty", "Telephone must contain only numbers!");
        }
        return validateTelephoneTemplate("Unesite telefon...", "Telefon ne sme da bude prazan!", "Telefon ne sme da sadrzi slova!");
    }

    private boolean validateTelephoneTemplate(String text1, String text2, String text3) {
        if (frmExhibitor.getTxtTelefon() == null || frmExhibitor.getTxtTelefon().getText().trim().isEmpty() || frmExhibitor.getTxtTelefon().getText().equals(text1)) {
            frmExhibitor.getTxtTelefon().setText(text2);
            frmExhibitor.getTxtTelefon().setForeground(Color.red);
            return false;
        } else {
            Pattern p = Pattern.compile("[^0-9]");
            Matcher m;
            if (!frmExhibitor.getTxtTelefon().getText().equals(text2)) {
                m = p.matcher(frmExhibitor.getTxtTelefon().getText().trim());
                boolean b = m.find();
                if (b) {
                    frmExhibitor.getTxtTelefon().setText(text3);
                    frmExhibitor.getTxtTelefon().setForeground(Color.red);
                    return false;
                }
                return true;
            }
            return false;
        }
    }

    private boolean validateEmail() {
        if (MainCordinator.getInstance().isIsEnglish()) {
            return validateEmail("Enter your email...", "Email can't be empty", "Email must be in valid format!");
        }
        return validateEmail("Unesite email...", "Email ne sme da bude prazan!", "Email mora da bude u validnom obliku!");
    }

    private boolean validateEmail(String text1, String text2, String text3) {
        if (frmExhibitor.getTxtEmail() == null || frmExhibitor.getTxtEmail().getText().trim().isEmpty() || frmExhibitor.getTxtEmail().getText().equals(text1)) {
            frmExhibitor.getTxtEmail().setText(text2);
            frmExhibitor.getTxtEmail().setForeground(Color.red);
            return false;
        } else {
            Pattern p = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
            Matcher m;
            if (!frmExhibitor.getTxtEmail().getText().equals(text2)) {
                m = p.matcher(frmExhibitor.getTxtEmail().getText().trim());
                boolean b = m.find();
                if (!b) {
                    frmExhibitor.getTxtEmail().setText(text3);
                    frmExhibitor.getTxtEmail().setForeground(Color.red);
                    return false;
                }
                return true;
            }
            return false;
        }
    }

    private void addActionListenersTxt() {

        frmExhibitor.addTxtImeFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (frmExhibitor.getTxtIme().getText().equals("Ime sme da sadrzi samo slova!") || frmExhibitor.getTxtIme().getText().equals("Unesite ime...") || frmExhibitor.getTxtIme().getText().equals("Ime ne sme da bude prazno!")
                        || frmExhibitor.getTxtIme().getText().equals("Name must contain only letters!") || frmExhibitor.getTxtIme().getText().equals("Enter your firstname...") || frmExhibitor.getTxtIme().getText().equals("Name can't be empty!")) {
                    frmExhibitor.getTxtIme().setText("");
                    frmExhibitor.getTxtIme().setForeground(Color.black);
                }
            }
        });

        frmExhibitor.addTxtPrezimeFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (frmExhibitor.getTxtPrezime().getText().equals("Prezime sme da sadrzi samo slova!") || frmExhibitor.getTxtPrezime().getText().equals("Unesite prezime...") || frmExhibitor.getTxtPrezime().getText().equals("Prezime ne sme da bude prazno!")
                        || frmExhibitor.getTxtPrezime().getText().equals("Lastname must contain only letters!") || frmExhibitor.getTxtPrezime().getText().equals("Enter your lastname...") || frmExhibitor.getTxtPrezime().getText().equals("Lastname can't be empty!")) {
                    frmExhibitor.getTxtPrezime().setText("");
                    frmExhibitor.getTxtPrezime().setForeground(Color.black);
                }
            }
        });

        frmExhibitor.addTxtEmailFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (frmExhibitor.getTxtEmail().getText().equals("Email mora da bude u validnom obliku!") || frmExhibitor.getTxtEmail().getText().equals("Unesite email...") || frmExhibitor.getTxtEmail().getText().equals("Email ne sme da bude prazan!")
                        || frmExhibitor.getTxtEmail().getText().equals("Email must be in valid format!") || frmExhibitor.getTxtEmail().getText().equals("Enter your email...") || frmExhibitor.getTxtEmail().getText().equals("Email can't be empty")) {
                    frmExhibitor.getTxtEmail().setText("");
                    frmExhibitor.getTxtEmail().setForeground(Color.black);
                }
            }
        });

        frmExhibitor.addTxtTelefonFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (frmExhibitor.getTxtTelefon().getText().equals("Telefon ne sme da sadrzi slova!") || frmExhibitor.getTxtTelefon().getText().equals("Unesite telefon...") || frmExhibitor.getTxtTelefon().getText().equals("Telefon ne sme da bude prazan!")
                        || frmExhibitor.getTxtTelefon().getText().equals("Telephone must contain only numbers!") || frmExhibitor.getTxtTelefon().getText().equals("Enter your telephone...") || frmExhibitor.getTxtTelefon().getText().equals("Telephone can't be empty")) {
                    frmExhibitor.getTxtTelefon().setText("");
                    frmExhibitor.getTxtTelefon().setForeground(Color.black);
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
            frmExhibitor.setTitle(prop.getProperty("titleExhibitor"));
            frmExhibitor.getjPanel1().setBorder(new TitledBorder(prop.getProperty("titleExhibitor")));

            frmExhibitor.getTxtIme().setText(prop.getProperty("txtIme"));
            frmExhibitor.getLblIme().setText(prop.getProperty("lblIme"));

            frmExhibitor.getTxtPrezime().setText(prop.getProperty("txtPrezime"));
            frmExhibitor.getLblPrezime().setText(prop.getProperty("lblPrezime"));

            frmExhibitor.getTxtTelefon().setText(prop.getProperty("txtTelephone"));
            frmExhibitor.getLblTelefon().setText(prop.getProperty("lblTelefon"));

            frmExhibitor.getTxtEmail().setText(prop.getProperty("txtEmail"));
            frmExhibitor.getLblEmail().setText(prop.getProperty("lblEmail"));

            frmExhibitor.getLblKompanija().setText(prop.getProperty("lblKompanija"));

            frmExhibitor.getBtnCancel().setText(prop.getProperty("btnCancel"));
            frmExhibitor.getBtnDelete().setText(prop.getProperty("btnDelete"));
            frmExhibitor.getBtnEdit().setText(prop.getProperty("btnEdit"));
            frmExhibitor.getBtnEnableChanges().setText(prop.getProperty("btnEnableChanges"));
            frmExhibitor.getBtnSave().setText(prop.getProperty("btnSave"));

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
