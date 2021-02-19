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
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import rs.ac.bg.fon.ps.communication.Communication;
import rs.ac.bg.fon.ps.domain.DR_I;
import rs.ac.bg.fon.ps.exceptions.CommunicationException;
import rs.ac.bg.fon.ps.properties.SetProperties;
import rs.ac.bg.fon.ps.view.component.table.DailyScheduleViewAllTableModel;
import rs.ac.bg.fon.ps.view.constant.Constants;
import rs.ac.bg.fon.ps.view.cordinator.MainCordinator;
import rs.ac.bg.fon.ps.view.form.FrmViewAllDailySchedules;

/**
 *
 * @author Mirko
 */
public class DailyScheduleViewAllController implements SetProperties {

    private final FrmViewAllDailySchedules frmViewAllDailySchedules;
    private boolean optionPaneActive = false;

    public DailyScheduleViewAllController(FrmViewAllDailySchedules frmViewAllDailySchedules) {
        this.frmViewAllDailySchedules = frmViewAllDailySchedules;
        addActionListeners();
    }

    public void openForm() {
        frmViewAllDailySchedules.setLocationRelativeTo(MainCordinator.getInstance().getMainContoller().getFrmMain());
        prepareView();
        if (MainCordinator.getInstance().isIsEnglish()) {
            readConfigProperties();
        }
        frmViewAllDailySchedules.setVisible(true);
    }

    private void prepareView() {
        frmViewAllDailySchedules.setTitle("Svi dnevni rasporedi");
    }

    private void addActionListeners() {
        frmViewAllDailySchedules.btnDetailsAddAcionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = frmViewAllDailySchedules.getTblDailySchedules().getSelectedRow();
                if (selectedRow >= 0) {
                    DR_I dri = ((DailyScheduleViewAllTableModel) frmViewAllDailySchedules.getTblDailySchedules().
                            getModel()).getDailyScheduleItemAt(selectedRow);

                    MainCordinator.getInstance().addParam(Constants.PARAM_DAILY_SCHEDULE, dri.getDnevniRaspored());
                    MainCordinator.getInstance().addParam(Constants.PARAM_DAILY_SCHEDULE_EXHIBITOR, dri);
                    MainCordinator.getInstance().openDailyScheduleDetailsDailyScheduleForm();
                } else {
                    JOptionPane.showMessageDialog(frmViewAllDailySchedules, "Morate izabrati dnevni raspored", "Detalji dnevnog rasporeda", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frmViewAllDailySchedules.btnCancelAddAcionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmViewAllDailySchedules.dispose();
            }
        });

        frmViewAllDailySchedules.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                fillTblViewAllDailyScheduleExhibitors(frmViewAllDailySchedules.getTxtPretraga().getText());
            }
        });

        frmViewAllDailySchedules.setTxtPretragaDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                fillTblViewAllDailyScheduleExhibitors(frmViewAllDailySchedules.getTxtPretraga().getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                fillTblViewAllDailyScheduleExhibitors(frmViewAllDailySchedules.getTxtPretraga().getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                fillTblViewAllDailyScheduleExhibitors(frmViewAllDailySchedules.getTxtPretraga().getText());
            }

        });
    }

    private void fillTblViewAllDailyScheduleExhibitors(String text) {
        List<DR_I> drii;

        try {
            drii = Communication.getInstance().getAllDailySchedulesExhibitors();
            drii = filtriraj(drii, text);

            DailyScheduleViewAllTableModel dailySchedulesTableModel = new DailyScheduleViewAllTableModel(drii);
            frmViewAllDailySchedules.getTblDailySchedules().setModel(dailySchedulesTableModel);
            if (dailySchedulesTableModel.getAllDailySchedules().isEmpty()) {
                if (!optionPaneActive) {
                    JOptionPane.showMessageDialog(frmViewAllDailySchedules, "Sistem ne moze da nadje dnevne rasporede po zadatoj vrednosti!", "Greska", JOptionPane.ERROR_MESSAGE);
                    optionPaneActive = true;
                }
            } else {
                optionPaneActive = false;
            }
        } catch (CommunicationException se) {
            JOptionPane.showMessageDialog(frmViewAllDailySchedules, "Server je ugasen\nAplikacija ce se ugasiti", "Greska", JOptionPane.ERROR_MESSAGE);
            frmViewAllDailySchedules.dispose();
            System.exit(0);
        } catch (Exception ex) {
            Logger.getLogger(DailyScheduleViewAllController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(frmViewAllDailySchedules, ex.getMessage(), "Greska pri nalazenju dnevnih rasporeda", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<DR_I> filtriraj(List<DR_I> drii, String text) {
        if (text.isEmpty() || text.equals("*")) {
            return drii;
        }

        List<DR_I> modifikovaniDrii = new LinkedList<>();

        for (DR_I dri : drii) {
            if (String.valueOf(dri.getBrStanda()).contains(text) || String.valueOf(dri.getHala().getBrojHale()).contains(text)
                    || dri.getIzlagac().toString().contains(text) || dri.getDnevniRaspored().getManifestacija().getNaziv().contains(text)
                    || String.valueOf(dri.getDnevniRaspored().getDatum()).contains(text)) {
                modifikovaniDrii.add(dri);
            }
        }
        return modifikovaniDrii;
    }

    public void refresh() {
        fillTblViewAllDailyScheduleExhibitors(frmViewAllDailySchedules.getTxtPretraga().getText());
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
            frmViewAllDailySchedules.setTitle(prop.getProperty("titleAllDailySchedules"));
            frmViewAllDailySchedules.getLblPretraga().setText(prop.getProperty("lblSearchDailySchedules"));
            frmViewAllDailySchedules.getBtnDetails().setText(prop.getProperty("btnDetails"));
            frmViewAllDailySchedules.getBtnCancel().setText(prop.getProperty("btnCancel"));

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
