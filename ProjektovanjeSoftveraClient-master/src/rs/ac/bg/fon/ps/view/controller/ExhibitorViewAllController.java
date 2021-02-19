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
import rs.ac.bg.fon.ps.domain.Izlagac;
import rs.ac.bg.fon.ps.properties.SetProperties;
import rs.ac.bg.fon.ps.view.component.table.ExhibitorTableModel;
import rs.ac.bg.fon.ps.view.constant.Constants;
import rs.ac.bg.fon.ps.view.cordinator.MainCordinator;
import rs.ac.bg.fon.ps.view.form.FrmViewAllExhibitors;

/**
 *
 * @author Mirko
 */
public class ExhibitorViewAllController implements SetProperties {

    private final FrmViewAllExhibitors frmViewAllExhibitors;
    private boolean optionPaneActive = false;

    public ExhibitorViewAllController(FrmViewAllExhibitors form) {
        this.frmViewAllExhibitors = form;
        addActionListeners();
    }

    public void openForm() {
        frmViewAllExhibitors.setLocationRelativeTo(MainCordinator.getInstance().getMainContoller().getFrmMain());
        prepareView();
        if (MainCordinator.getInstance().isIsEnglish()) {
            readConfigProperties();
        }
        frmViewAllExhibitors.setVisible(true);
    }

    private void prepareView() {
        frmViewAllExhibitors.setTitle("Svi izlagaci");
    }

    private void addActionListeners() {
        frmViewAllExhibitors.setBtnDetailsAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = frmViewAllExhibitors.getTblExhibitor().getSelectedRow();
                if (row >= 0) {
                    Izlagac izlagac = ((ExhibitorTableModel) frmViewAllExhibitors.getTblExhibitor().getModel()).getExhibitorAt(row);
                    MainCordinator.getInstance().addParam(Constants.PARAM_EXHIBITOR, izlagac);
                    MainCordinator.getInstance().openExhibitorDetailsExhibitorForm();
                } else {
                    JOptionPane.showMessageDialog(frmViewAllExhibitors, "Morate odabrati izlagaca", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frmViewAllExhibitors.setBtnCancelAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmViewAllExhibitors.dispose();
            }
        });

        frmViewAllExhibitors.setTxtPretragaDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                fillTblExhibitors(frmViewAllExhibitors.getTxtPretraga().getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                fillTblExhibitors(frmViewAllExhibitors.getTxtPretraga().getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                fillTblExhibitors(frmViewAllExhibitors.getTxtPretraga().getText());
            }

        });

        frmViewAllExhibitors.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                fillTblExhibitors(frmViewAllExhibitors.getTxtPretraga().getText());
            }
        });
    }

    private void fillTblExhibitors(String text) {
        List<Izlagac> izlagaci;
        try {
            izlagaci = Communication.getInstance().getAllExhibitors();
            izlagaci = filtriraj(izlagaci, text);

            ExhibitorTableModel etm = new ExhibitorTableModel(izlagaci);
            frmViewAllExhibitors.getTblExhibitor().setModel(etm);
            if (etm.getAllExhibitors().isEmpty()) {
                if (!optionPaneActive) {
                    JOptionPane.showMessageDialog(frmViewAllExhibitors, "Sistem ne moze da nadje izlagace po zadatoj vrednosti!", "Greska", JOptionPane.ERROR_MESSAGE);
                    optionPaneActive = true;
                }
            } else {
                optionPaneActive = false;
            }
        } catch (SocketException se) {
            JOptionPane.showMessageDialog(frmViewAllExhibitors, "Server je ugasen\nAplikacija ce se ugasiti", "Greska", JOptionPane.ERROR_MESSAGE);
            frmViewAllExhibitors.dispose();
            //MainCordinator.getInstance().getMainContoller().ugasiFormu();
            System.exit(0);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frmViewAllExhibitors, "Greska pri pristupanju izlagacima!\n" + ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ExhibitorViewAllController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Izlagac> filtriraj(List<Izlagac> izlagaci, String text) {
        if (text.isEmpty() || text.equals("*")) {
            return izlagaci;
        }

        List<Izlagac> modifikovanIzlagaci = new LinkedList<>();

        for (Izlagac iz : izlagaci) {
            if (iz.getEmail().contains(text) || iz.getIme().contains(text)
                    || iz.getKompanija().getNaziv().contains(text) || iz.getKontaktTelefon().contains(text)
                    || iz.getPrezime().contains(text)) {
                modifikovanIzlagaci.add(iz);
            }
        }
        return modifikovanIzlagaci;
    }

    public void refresh() {
        fillTblExhibitors(frmViewAllExhibitors.getTxtPretraga().getText());
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
            frmViewAllExhibitors.setTitle(prop.getProperty("titleAllExhibitors"));
            frmViewAllExhibitors.getLblPretraga().setText(prop.getProperty("lblSearchExhibitors"));
            frmViewAllExhibitors.getBtnDetails().setText(prop.getProperty("btnDetails"));
            frmViewAllExhibitors.getBtnCancel().setText(prop.getProperty("btnCancel"));

            //ExhibitorTableModel etm = (ExhibitorTableModel) frmViewAllExhibitors.getTblExhibitor().getModel();
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
