/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.controller;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import rs.ac.bg.fon.ps.communication.Communication;
import rs.ac.bg.fon.ps.domain.Admin;
import rs.ac.bg.fon.ps.exceptions.CommunicationException;
import rs.ac.bg.fon.ps.properties.SetProperties;
import rs.ac.bg.fon.ps.view.constant.Constants;
import rs.ac.bg.fon.ps.view.cordinator.MainCordinator;
import rs.ac.bg.fon.ps.view.form.FrmLogin;

/**
 *
 * @author Mirko
 */
public class LoginController implements SetProperties {

    private final FrmLogin frmLogin;

    public LoginController(FrmLogin frmLogin) {
        this.frmLogin = frmLogin;
        addActionListenerTxt();
        prepareView();
        designComponents();
        addActionListener();
    }

    public void openForm() {
        readConfigProperties();
        frmLogin.setVisible(true);
    }

    private void addActionListener() {
        frmLogin.loginAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginUser(e);
            }

            private void loginUser(ActionEvent e) {
                //resetovanje forme

                try {
                    String email = frmLogin.getTxtEmail().getText().trim();
                    String password = String.copyValueOf(frmLogin.getTxtSifra().getPassword());
                    //validacija forme
                    if (validateForm()) {
                        Admin admin = new Admin();
                        admin.setEmail(email);
                        admin.setPassword(password);
                        admin = Communication.getInstance().PrijavljivanjeNaSistem(admin);
                        JOptionPane.showMessageDialog(
                                frmLogin,
                                "Uspesno prijavljivanje na sistem",
                                "Prijavljivanje", JOptionPane.INFORMATION_MESSAGE
                        );
                        frmLogin.dispose();
                        MainCordinator.getInstance().addParam(Constants.CURRENT_USER, admin);
                        MainCordinator.getInstance().openMainForm();
                    }

                } catch (CommunicationException se) {
                    JOptionPane.showMessageDialog(frmLogin, "Server je ugasen", "Greska", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmLogin, "Neuspesno prijavljivanje na sistem", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void prepareView() {
        frmLogin.setTitle("Dobrodosli / Welcome");
        frmLogin.setLocationRelativeTo(null);
        frmLogin.getTxtSifra().setEchoChar((char) 0);
        frmLogin.getLblEmail().requestFocus();
        Border blackline = BorderFactory.createLineBorder(Color.black);

        frmLogin.getLblSrpskaZastava().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("../../images/srb.png")).getImage().getScaledInstance(140, 60, Image.SCALE_SMOOTH)));
        frmLogin.getLblSrpskaZastava().setBorder(blackline);

        frmLogin.getLblEngleskaZastava().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("../../images/uk.png")).getImage().getScaledInstance(140, 60, Image.SCALE_SMOOTH)));
    }

    private void designComponents() {
        frmLogin.getBtnPrijava().setForeground(Color.GREEN);
    }

    private boolean validateForm() {
        boolean uspesanEmail = validateEmail();
        boolean uspesnaSifra = validatePassword();
        return uspesanEmail && uspesnaSifra;
    }

    private boolean validateEmail() {
        if (MainCordinator.getInstance().isIsEnglish()) {
            return validateEmailTemplate("Enter your email...", "You must enter email!");
        } else {
            return validateEmailTemplate("Unesite email...", "Morate uneti email!");

        }
    }

    private boolean validateEmailTemplate(String text1, String text2) {
        if (frmLogin.getTxtEmail().getText().trim().isEmpty() || frmLogin.getTxtEmail().getText().equals(text1) || frmLogin.getTxtEmail().getText().equals("text2")) {
            frmLogin.getTxtEmail().setText(text2);
            frmLogin.getTxtEmail().setForeground(Color.red);
            return false;
        }
        return true;
    }

    private boolean validatePassword() {
        if (MainCordinator.getInstance().isIsEnglish()) {
            return validatePasswordTemplate("Enter your password...", "You must enter password!");
        } else {
            return validatePasswordTemplate("Unesite sifru...", "Morate uneti sifru!");
        }
    }

    private boolean validatePasswordTemplate(String text1, String text2) {
        if (String.valueOf(frmLogin.getTxtSifra().getPassword()).isEmpty() || String.valueOf(frmLogin.getTxtSifra().getPassword()).equals(text1) || String.valueOf(frmLogin.getTxtSifra().getPassword()).equals(text2)) {
            frmLogin.getTxtSifra().setText(text2);
            frmLogin.getTxtSifra().setForeground(Color.red);
            frmLogin.getTxtSifra().setEchoChar((char) 0);
            frmLogin.getCbSifra().setEnabled(false);
            return false;
        }
        return true;
    }

    private void addActionListenerTxt() {
        frmLogin.addTxtEmailFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (frmLogin.getTxtEmail().getText().equals("Morate uneti email!") || frmLogin.getTxtEmail().getText().equals("Unesite email...")
                        || frmLogin.getTxtEmail().getText().equals("You must enter email!") || frmLogin.getTxtEmail().getText().equals("Enter your email...")) {
                    frmLogin.getTxtEmail().setText("");
                    frmLogin.getTxtEmail().setForeground(Color.black);
                }
            }
        });

        frmLogin.addTxtPasswordFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(frmLogin.getTxtSifra().getPassword()).equals("Morate uneti sifru!") || String.valueOf(frmLogin.getTxtSifra().getPassword()).equals("Unesite sifru...")
                        || String.valueOf(frmLogin.getTxtSifra().getPassword()).equals("You must enter password!") || String.valueOf(frmLogin.getTxtSifra().getPassword()).equals("Enter your password...")) {
                    frmLogin.getTxtSifra().setForeground(Color.black);
                    frmLogin.getTxtSifra().setText("");
                    frmLogin.getCbSifra().setEnabled(true);
                }
                if (frmLogin.getCbSifra().isSelected()) {
                    frmLogin.getTxtSifra().setEchoChar('\u0000');
                } else {
                    frmLogin.getTxtSifra().setEchoChar('•');
                }
            }

        });

        frmLogin.addCbPasswordMouseClikedListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (frmLogin.getCbSifra().isSelected()) {
                    frmLogin.getTxtSifra().setEchoChar('\u0000');
                }
                if (!frmLogin.getCbSifra().isSelected() && !String.valueOf(frmLogin.getTxtSifra().getPassword()).equals("Unesite sifru...") && !String.valueOf(frmLogin.getTxtSifra().getPassword()).equals("Enter your password...")) {
                    frmLogin.getTxtSifra().setEchoChar('•');
                }
            }
        });

        frmLogin.addLblSrpskiMouseClickedListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Border blackline = BorderFactory.createLineBorder(Color.black);
                frmLogin.getLblSrpskaZastava().setBorder(blackline);
                frmLogin.getLblEngleskaZastava().setBorder(null);
                MainCordinator.getInstance().setIsEnglish(false);
                readConfigProperties();
            }
        });

        frmLogin.addLblEngleskiMouseClickedListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Border blackline = BorderFactory.createLineBorder(Color.black);
                frmLogin.getLblEngleskaZastava().setBorder(blackline);
                frmLogin.getLblSrpskaZastava().setBorder(null);
                MainCordinator.getInstance().setIsEnglish(true);
                readConfigProperties();
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
            frmLogin.getLblEmail().setText(prop.getProperty("lblEmail"));
            frmLogin.getLblSifra().setText(prop.getProperty("lblPassword"));
            frmLogin.getTxtEmail().setText(prop.getProperty("txtEmail"));
            frmLogin.getTxtSifra().setText(prop.getProperty("txtSifra"));
            frmLogin.getBtnPrijava().setText(prop.getProperty("btnPrijava"));
            frmLogin.getCbSifra().setText(prop.getProperty("cbSifra"));

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
