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
import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ps.communication.Communication;
import rs.ac.bg.fon.ps.domain.DR_I;
import rs.ac.bg.fon.ps.domain.DnevniRaspored;
import rs.ac.bg.fon.ps.domain.Hala;
import rs.ac.bg.fon.ps.domain.Izlagac;
import rs.ac.bg.fon.ps.domain.Manifestacija;
import rs.ac.bg.fon.ps.exceptions.CommunicationException;
import rs.ac.bg.fon.ps.properties.SetProperties;
import rs.ac.bg.fon.ps.view.component.table.DailyScheduleItemTableModel;
import rs.ac.bg.fon.ps.view.constant.Constants;
import rs.ac.bg.fon.ps.view.cordinator.MainCordinator;
import rs.ac.bg.fon.ps.view.form.FrmDailySchedule;
import rs.ac.bg.fon.ps.view.util.FormMode;

/**
 *
 * @author Mirko
 */
public class DailyScheduleController implements SetProperties {

    private FrmDailySchedule frmDailySchedule;
    private boolean pressedDelete = false;

    public DailyScheduleController(FrmDailySchedule frmDailySchedule) {
        this.frmDailySchedule = frmDailySchedule;
    }

    public FrmDailySchedule getFrmDailySchedule() {
        return frmDailySchedule;
    }

    public void openForm(FormMode formMode) {
        if (MainCordinator.getInstance().isIsEnglish()) {
            readConfigProperties();
        }
        fillForm();
        setupComponents(formMode);
        addActionListeners();
        prepareView();

        switch (formMode) {
            case FORM_ADD:
                fillTblDailySchedule();
                break;
            case FORM_EDIT:
                prepareDailyScheduleOnForm();
                break;
            case FORM_VIEW:
                prepareDailyScheduleOnForm();
                selektujRed();
                break;
        }
        frmDailySchedule.setVisible(true);
    }

    private void fillForm() {
        try {
            frmDailySchedule.getCbManifestacija().setModel(new DefaultComboBoxModel(Communication.getInstance().getAllManifestations().toArray()));
            frmDailySchedule.getCbIzlagac().setModel(new DefaultComboBoxModel(Communication.getInstance().getAllExhibitors().toArray()));
            frmDailySchedule.getCbHala().setModel(new DefaultComboBoxModel(Communication.getInstance().getAllHalls().toArray()));

        } catch (SocketException se) {
            JOptionPane.showMessageDialog(frmDailySchedule, "Server je ugasen\nAplikacija ce se ugasiti", "Greska", JOptionPane.ERROR_MESSAGE);
            frmDailySchedule.dispose();
            System.exit(0);
        } catch (Exception ex) {
            Logger.getLogger(DailyScheduleController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(frmDailySchedule, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void prepareView() {
        frmDailySchedule.setLocationRelativeTo(MainCordinator.getInstance().getMainContoller().getFrmMain());
        frmDailySchedule.setTitle("Dnevni raspored");
    }

    private void prepareDailyScheduleOnForm() {

        try {
            DnevniRaspored dr = (DnevniRaspored) MainCordinator.getInstance().getParam("PARAM_DAILY_SCHEDULE");
            DR_I selektovaniItem = (DR_I) MainCordinator.getInstance().getParam("PARAM_DAILY_SCHEDULE_EXHIBITOR");
            Manifestacija manifestacija = dr.getManifestacija();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            List<DR_I> listaDnevnihRasporedIzlagaca = Communication.getInstance().getAllDailySchedulesExhibitorsWithCondition(selektovaniItem);
            DailyScheduleItemTableModel dsitm = new DailyScheduleItemTableModel(listaDnevnihRasporedIzlagaca);
            frmDailySchedule.getTblIzlagaciDana().setModel(dsitm);

            frmDailySchedule.getCbManifestacija().setSelectedItem(manifestacija);
            frmDailySchedule.getTxtDatumOd().setText(sdf.format(manifestacija.getDatumOd()));
            frmDailySchedule.getTxtDatumDo().setText(sdf.format(manifestacija.getDatumDo()));
            frmDailySchedule.getCbDatum().setSelectedItem(sdf.format(dr.getDatum()));

            frmDailySchedule.getCbIzlagac().setSelectedItem(selektovaniItem.getIzlagac());
            frmDailySchedule.getCbHala().setSelectedItem(selektovaniItem.getHala());
            frmDailySchedule.getCbStand().setSelectedItem(selektovaniItem.getBrStanda());
            frmDailySchedule.getCbStand().setEnabled(false);
        } catch (CommunicationException se) {
            JOptionPane.showMessageDialog(frmDailySchedule, "Server je ugasen\nAplikacija ce se ugasiti", "Greska", JOptionPane.ERROR_MESSAGE);
            frmDailySchedule.dispose();
            System.exit(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(DailyScheduleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setupComponents(FormMode formMode) {
        switch (formMode) {
            case FORM_ADD:
                frmDailySchedule.getCbManifestacija().setSelectedIndex(-1);
                frmDailySchedule.getCbHala().setSelectedIndex(-1);
                frmDailySchedule.getCbManifestacija().setEnabled(true);
                frmDailySchedule.getCbDatum().setEnabled(false);
                frmDailySchedule.getCbIzlagac().setEnabled(true);
                frmDailySchedule.getCbHala().setEnabled(true);
                frmDailySchedule.getCbStand().setEnabled(false);
                frmDailySchedule.getBtnDodajIzlagaca().setEnabled(false);
                frmDailySchedule.getBtnSacuvaj().setEnabled(false);
                frmDailySchedule.getBtnOmoguciIzmene().setVisible(false);
                frmDailySchedule.getBtnIzmeniDnevniRaspored().setVisible(false);
                frmDailySchedule.getBtnObrisiDnevniRaspored().setVisible(false);
                break;
            case FORM_VIEW:
                frmDailySchedule.getCbManifestacija().setEnabled(false);
                frmDailySchedule.getCbDatum().setEnabled(false);
                frmDailySchedule.getCbIzlagac().setEnabled(false);
                frmDailySchedule.getCbHala().setEnabled(false);
                frmDailySchedule.getCbStand().setEnabled(false);
                frmDailySchedule.getBtnDodajIzlagaca().setVisible(false);
                frmDailySchedule.getBtnIzbrisiIzlagaca().setVisible(false);
                frmDailySchedule.getBtnOmoguciIzmene().setVisible(true);
                frmDailySchedule.getBtnIzmeniDnevniRaspored().setVisible(false);
                frmDailySchedule.getBtnObrisiDnevniRaspored().setVisible(false);
                frmDailySchedule.getBtnPonisti().setVisible(true);
                frmDailySchedule.getBtnSacuvaj().setVisible(false);
                break;
            case FORM_EDIT:
                frmDailySchedule.getCbManifestacija().setEnabled(true);
                frmDailySchedule.getCbDatum().setEnabled(true);
                frmDailySchedule.getCbIzlagac().setEnabled(true);
                frmDailySchedule.getCbHala().setEnabled(true);
                frmDailySchedule.getCbStand().setEnabled(true);
                frmDailySchedule.getBtnDodajIzlagaca().setVisible(true);
                frmDailySchedule.getBtnIzbrisiIzlagaca().setVisible(true);
                frmDailySchedule.getBtnOmoguciIzmene().setVisible(false);
                frmDailySchedule.getBtnIzmeniDnevniRaspored().setVisible(true);
                frmDailySchedule.getBtnObrisiDnevniRaspored().setVisible(true);
                frmDailySchedule.getBtnPonisti().setVisible(true);
                frmDailySchedule.getBtnSacuvaj().setVisible(false);
                break;
        }
    }

    private void addActionListeners() {
        //Dodavanje izlagaca
        frmDailySchedule.addDodajIzlagacaActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodajIzlagaca();
            }

            private void dodajIzlagaca() {
                Izlagac izlagac = (Izlagac) frmDailySchedule.getCbIzlagac().getSelectedItem();
                Hala hala = (Hala) frmDailySchedule.getCbHala().getSelectedItem();
                int brojStanda = (int) frmDailySchedule.getCbStand().getSelectedItem();
                DnevniRaspored dnevniRaspored = (DnevniRaspored) MainCordinator.getInstance().getParam("PARAM_DAILY_SCHEDULE");

                DailyScheduleItemTableModel model = (DailyScheduleItemTableModel) frmDailySchedule.getTblIzlagaciDana().getModel();
                model.addDailyScheduleItem(dnevniRaspored, izlagac, hala, brojStanda);

                frmDailySchedule.getBtnSacuvaj().setEnabled(true);
                frmDailySchedule.getCbManifestacija().setEnabled(false);
                frmDailySchedule.getCbDatum().setEnabled(false);
            }
        });

        //Brisanje izlagaca
        frmDailySchedule.addIzbrisiIzlagacaActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                izbrisiIzlagaca();
            }

            private void izbrisiIzlagaca() {

                int rowIndex = frmDailySchedule.getTblIzlagaciDana().getSelectedRow();
                DailyScheduleItemTableModel model = (DailyScheduleItemTableModel) frmDailySchedule.getTblIzlagaciDana().getModel();
                if (rowIndex >= 0) {
                    DR_I izbrisani = model.removeDailyScheduleItem(rowIndex);
                    //ubaciUCb(frmDailySchedule.getCbStand(), izbrisani.getBrStanda());

                    frmDailySchedule.getBtnSacuvaj().setEnabled(true);
                    frmDailySchedule.getCbManifestacija().setEnabled(false);
                    frmDailySchedule.getCbDatum().setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(frmDailySchedule, "Niste odabrali izlagaca!", "Greska", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        //Sacuvaj dnevni raspored
        frmDailySchedule.addSacuvajDnevniRasporedActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveDailySchedule();
            }

            private void saveDailySchedule() {
                try {
                    DailyScheduleItemTableModel model = (DailyScheduleItemTableModel) frmDailySchedule.getTblIzlagaciDana().getModel();
                    List<DR_I> listDrI = model.getListDailyScheduleItems();

                    Communication.getInstance().addDailyScheduleExhibitor(listDrI);
                    JOptionPane.showMessageDialog(frmDailySchedule, "Sistem je zapamtio dnenvi raspored", "Uspesno", JOptionPane.INFORMATION_MESSAGE);
                    frmDailySchedule.dispose();
                } catch (CommunicationException se) {
                    JOptionPane.showMessageDialog(frmDailySchedule, "Server je ugasen\nAplikacija ce se ugasiti", "Greska", JOptionPane.ERROR_MESSAGE);
                    frmDailySchedule.dispose();
                    System.exit(0);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frmDailySchedule, "Sistem ne moze da zapamti dnevni raspored", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //Ponistenje prozora za dnevni raspored
        frmDailySchedule.addPonistiActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ponisti();
            }

            private void ponisti() {
                obradiPrethodniDnevniRaspored();
                frmDailySchedule.dispose();
            }
        });

        frmDailySchedule.addOmoguciIzmeneActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setupComponents(FormMode.FORM_EDIT);
            }
        });

        frmDailySchedule.addIzmeniActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dialogResult = JOptionPane.showConfirmDialog(frmDailySchedule, "Da li ste sigurni da zelite da izmenite dnevniraspored", "Potvrda", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    editDailySchedule();
                    refresh();
                }
            }

            private void editDailySchedule() {
                try {
                    DailyScheduleItemTableModel model = (DailyScheduleItemTableModel) frmDailySchedule.getTblIzlagaciDana().getModel();
                    List<DR_I> listDrI = model.getListDailyScheduleItems();

                    Communication.getInstance().editDailyScheduleExhibitor(listDrI);
                    JOptionPane.showMessageDialog(frmDailySchedule, "Sistem je zapamtio dnevni raspored");

                    frmDailySchedule.getCbManifestacija().setEnabled(true);
                } catch (CommunicationException se) {
                    JOptionPane.showMessageDialog(frmDailySchedule, "Server je ugasen\nAplikacija ce se ugasiti", "Greska", JOptionPane.ERROR_MESSAGE);
                    frmDailySchedule.dispose();
                    System.exit(0);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frmDailySchedule, "Sistem ne moze da zapmti dnevni raspored", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        frmDailySchedule.addObrisiActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dialogResult = JOptionPane.showConfirmDialog(frmDailySchedule, "Da li ste sigurni da zelite da obrisete dnevniraspored", "Potvrda", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    deleteDailySchedule();
                    refresh();
                }
            }

            private void deleteDailySchedule() {
                try {
                    pressedDelete = true;
                    DnevniRaspored dnevniRaspored = (DnevniRaspored) MainCordinator.getInstance().getParam("PARAM_DAILY_SCHEDULE");
                    Communication.getInstance().deleteDailySchedule(dnevniRaspored);
                    JOptionPane.showMessageDialog(frmDailySchedule, "Sistem je obrisao dnevni raspored");
                    frmDailySchedule.dispose();
                } catch (CommunicationException se) {
                    JOptionPane.showMessageDialog(frmDailySchedule, "Server je ugasen\nAplikacija ce se ugasiti", "Greska", JOptionPane.ERROR_MESSAGE);
                    frmDailySchedule.dispose();
                    System.exit(0);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frmDailySchedule, "Sistem ne moze da izbrise dnevni raspored", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frmDailySchedule.addCbManifestacijaActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmDailySchedule.getCbDatum().setEnabled(true);

                Manifestacija man = (Manifestacija) frmDailySchedule.getCbManifestacija().getSelectedItem();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                frmDailySchedule.getTxtDatumOd().setText(sdf.format(man.getDatumOd()));
                frmDailySchedule.getTxtDatumDo().setText(sdf.format(man.getDatumDo()));

                try {
                    Date prviDatum = sdf.parse(frmDailySchedule.getTxtDatumOd().getText());
                    Date drugiDatum = sdf.parse(frmDailySchedule.getTxtDatumDo().getText());
                    List<LocalDate> datumiIzmedju = vratiDatumeIzmedju(prviDatum, drugiDatum);
                    frmDailySchedule.getCbDatum().removeAllItems();
                    for (LocalDate ld : datumiIzmedju) {
                        frmDailySchedule.getCbDatum().addItem(sdf.format(Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant())));
                    }
                    frmDailySchedule.getCbDatum().setSelectedIndex(-1);
                } catch (ParseException ex) {
                    Logger.getLogger(DailyScheduleController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        });

        frmDailySchedule.addCbDatumActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (frmDailySchedule.getCbDatum().getSelectedItem() != null && !String.valueOf(frmDailySchedule.getCbDatum().getSelectedItem()).isEmpty() && !pressedDelete) {
                    obradiPrethodniDnevniRaspored();
                    try {
                        Manifestacija selektovna = (Manifestacija) frmDailySchedule.getCbManifestacija().getSelectedItem();
                        DnevniRaspored tempDnevniRaspored = new DnevniRaspored();
                        tempDnevniRaspored.setManifestacija(selektovna);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        tempDnevniRaspored.setDatum(sdf.parse(frmDailySchedule.getCbDatum().getSelectedItem().toString()));
                        DnevniRaspored dnevniRaspored = Communication.getInstance().getDailySchedule(tempDnevniRaspored);
                        if (dnevniRaspored == null || dnevniRaspored.getDnevniRasporedId() == null) {
                            dnevniRaspored = new DnevniRaspored();
                            dnevniRaspored.setDatum(sdf.parse((String) frmDailySchedule.getCbDatum().getSelectedItem()));
                            dnevniRaspored.setManifestacija((Manifestacija) frmDailySchedule.getCbManifestacija().getSelectedItem());

                            Communication.getInstance().addDailySchedule(dnevniRaspored);
                            frmDailySchedule.getTblIzlagaciDana().setModel(new DailyScheduleItemTableModel(new ArrayList<DR_I>()));
                        } else {
                            DR_I tempDRI = new DR_I();
                            tempDRI.setDnevniRaspored(dnevniRaspored);
                            List<DR_I> listaDRIja = Communication.getInstance().getAllDailySchedulesExhibitorsWithCondition(tempDRI);
                            DailyScheduleItemTableModel dsitm = new DailyScheduleItemTableModel(listaDRIja);
                            frmDailySchedule.getTblIzlagaciDana().setModel(dsitm);
                        }
                        MainCordinator.getInstance().addParam(Constants.PARAM_DAILY_SCHEDULE, dnevniRaspored);
                    } catch (CommunicationException se) {
                        JOptionPane.showMessageDialog(frmDailySchedule, "Server je ugasen\nAplikacija ce se ugasiti", "Greska", JOptionPane.ERROR_MESSAGE);
                        frmDailySchedule.dispose();
                        System.exit(0);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frmDailySchedule, "Sistem ne moze da kreira dnevni raspored", "Greska", JOptionPane.ERROR_MESSAGE);
                        Logger.getLogger(DailyScheduleController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        }
        );

        frmDailySchedule.addCbHalaActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                frmDailySchedule.getCbStand().setEnabled(true);
                frmDailySchedule.getCbStand().removeAllItems();
                for (int i = 1; i <= ((Hala) frmDailySchedule.getCbHala().getSelectedItem()).getBrStandova(); i++) {
                    frmDailySchedule.getCbStand().addItem(i);
                }
            }
        }
        );
        frmDailySchedule.addCbStandActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                frmDailySchedule.getBtnDodajIzlagaca().setEnabled(true);
            }
        }
        );

        frmDailySchedule.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                obradiPrethodniDnevniRaspored();
            }
        });
    }

    private List<LocalDate> vratiDatumeIzmedju(Date datumOd, Date datumDo) {
        LocalDate ldOd = datumOd.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate ldDo = datumDo.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long numOfDaysBetween = ChronoUnit.DAYS.between(ldOd, ldDo) + 1;

        return IntStream.iterate(0, i -> i + 1).limit(numOfDaysBetween).mapToObj(i -> ldOd.plusDays(i)).collect(Collectors.toList());
    }

    public void refresh() {
        prepareDailyScheduleOnForm();
    }

    private void fillTblDailySchedule() {
        DailyScheduleItemTableModel model = new DailyScheduleItemTableModel(new LinkedList<>());
        frmDailySchedule.getTblIzlagaciDana().setModel(model);
    }

//    private void ubaciUCb(JComboBox dobijeni, int vrednost) {
//        List<Integer> vrednosti = new ArrayList<>();
//        for (int i = 0; i < dobijeni.getItemCount(); i++) {
//            vrednosti.add(Integer.valueOf(dobijeni.getItemAt(i).toString()));
//        }
//        
//        for (Integer integ : vrednosti) {
//            if (integ > vrednost) {
//                vrednosti.add(vrednosti.indexOf(integ), vrednost);
//            }
//        }
//        
//        dobijeni.removeAllItems();
//        dobijeni.addItem(vrednosti.toArray());
//        
//    }
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
            frmDailySchedule.setTitle(prop.getProperty("titleDailySchedule"));
            frmDailySchedule.getLblManifestacija().setText(prop.getProperty("lblManifestacija"));
            frmDailySchedule.getLblDatum().setText(prop.getProperty("lblDatum"));
            frmDailySchedule.getLblHala().setText(prop.getProperty("lblHala"));
            frmDailySchedule.getLblIzlagac().setText(prop.getProperty("lblIzlagac"));
            frmDailySchedule.getLblStand().setText(prop.getProperty("lblStand"));
            frmDailySchedule.getLblTrajanje().setText(prop.getProperty("lblTrajanje"));
            frmDailySchedule.getBtnDodajIzlagaca().setText(prop.getProperty("btnDodajIzlagaca"));
            frmDailySchedule.getBtnIzbrisiIzlagaca().setText(prop.getProperty("btnObrisiIzlagaca"));
            frmDailySchedule.getBtnIzmeniDnevniRaspored().setText(prop.getProperty("btnIzmeniDnevniRaspored"));
            frmDailySchedule.getBtnObrisiDnevniRaspored().setText(prop.getProperty("btnObrisiDnevniRaspored"));
            frmDailySchedule.getBtnOmoguciIzmene().setText(prop.getProperty("btnEnableChanges"));
            frmDailySchedule.getBtnSacuvaj().setText(prop.getProperty("btnSave"));
            frmDailySchedule.getBtnPonisti().setText(prop.getProperty("btnCancel"));
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

    private void selektujRed() {
        DR_I selektovaniItem = (DR_I) MainCordinator.getInstance().getParam("PARAM_DAILY_SCHEDULE_EXHIBITOR");
        DailyScheduleItemTableModel dsitm = (DailyScheduleItemTableModel) frmDailySchedule.getTblIzlagaciDana().getModel();
        int indexSelektovanogDrIa = dsitm.getIndexOfDailyScheduleItem(selektovaniItem);

        frmDailySchedule.getTblIzlagaciDana().setRowSelectionAllowed(true);
        frmDailySchedule.getTblIzlagaciDana().setRowSelectionInterval(indexSelektovanogDrIa, indexSelektovanogDrIa);
    }

    private void obradiPrethodniDnevniRaspored() {
        DnevniRaspored dnevniRaspored = (DnevniRaspored) MainCordinator.getInstance().getParam("PARAM_DAILY_SCHEDULE");
        if (dnevniRaspored == null) {
            return;
        }
        DailyScheduleItemTableModel dsitm = (DailyScheduleItemTableModel) frmDailySchedule.getTblIzlagaciDana().getModel();
        List<DR_I> drii = dsitm.getListDailyScheduleItems();
        if (drii.isEmpty()) {
            try {
                Communication.getInstance().deleteDailySchedule(dnevniRaspored);
            } catch (CommunicationException se) {
                JOptionPane.showMessageDialog(frmDailySchedule, "Server je ugasen\nAplikacija ce se ugasiti", "Greska", JOptionPane.ERROR_MESSAGE);
                frmDailySchedule.dispose();
                System.exit(0);
            } catch (Exception ex) {
                Logger.getLogger(DailyScheduleController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
