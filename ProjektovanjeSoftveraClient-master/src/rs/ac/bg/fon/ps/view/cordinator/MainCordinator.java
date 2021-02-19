/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.cordinator;

import rs.ac.bg.fon.ps.view.controller.ManifestationViewAllController;
import java.util.HashMap;
import java.util.Map;
import rs.ac.bg.fon.ps.view.controller.DailyScheduleController;
import rs.ac.bg.fon.ps.view.controller.DailyScheduleViewAllController;
import rs.ac.bg.fon.ps.view.controller.LoginController;
import rs.ac.bg.fon.ps.view.controller.MainController;
import rs.ac.bg.fon.ps.view.controller.ExhibitorController;
import rs.ac.bg.fon.ps.view.controller.ManifestationController;
import rs.ac.bg.fon.ps.view.controller.ExhibitorViewAllController;
import rs.ac.bg.fon.ps.view.form.FrmDailySchedule;
import rs.ac.bg.fon.ps.view.form.FrmLogin;
import rs.ac.bg.fon.ps.view.form.FrmMain;
import rs.ac.bg.fon.ps.view.form.FrmExhibitor;
import rs.ac.bg.fon.ps.view.form.FrmManifestation;
import rs.ac.bg.fon.ps.view.form.FrmViewAllDailySchedules;
import rs.ac.bg.fon.ps.view.form.FrmViewAllExhibitors;
import rs.ac.bg.fon.ps.view.form.FrmViewAllManifestations;
import rs.ac.bg.fon.ps.view.util.FormMode;

/**
 *
 * @author Mirko
 */
public class MainCordinator {

    private static MainCordinator instance;
    private boolean isEnglish = false;

    public boolean isIsEnglish() {
        return isEnglish;
    }

    public void setIsEnglish(boolean isEnglish) {
        this.isEnglish = isEnglish;
    }

    private final MainController mainContoller;

    private final Map<String, Object> params;

    private MainCordinator() {
        mainContoller = new MainController(new FrmMain());
        params = new HashMap<>();
    }

    public static MainCordinator getInstance() {
        if (instance == null) {
            instance = new MainCordinator();
        }
        return instance;
    }

    public void openLoginForm() {
        LoginController loginController = new LoginController(new FrmLogin());
        loginController.openForm();
    }

    public void addParam(String name, Object key) {
        params.put(name, key);
    }

    public Object getParam(String name) {
        return params.get(name);
    }

    public void openMainForm() {
        mainContoller.openForm();
    }

    // IZLAGAC
    public void openAddNewExhibitorForm() {
        ExhibitorController exhibitorController = new ExhibitorController(new FrmExhibitor(mainContoller.getFrmMain(), true));
        exhibitorController.openForm(FormMode.FORM_ADD);
    }

    public void openExhibitorDetailsExhibitorForm() {
        FrmExhibitor exhibitorDetails = new FrmExhibitor(mainContoller.getFrmMain(), true);
        ExhibitorController exhibitorController = new ExhibitorController(exhibitorDetails);
        exhibitorController.openForm(FormMode.FORM_VIEW);
    }

    public void openViewAllExhibitorsForm() {
        FrmViewAllExhibitors form = new FrmViewAllExhibitors(mainContoller.getFrmMain(), true);
        ExhibitorViewAllController exhibitorViewAllController = new ExhibitorViewAllController(form);
        exhibitorViewAllController.openForm();
    }
// MANIFESTACIJA

    public void openAddNewManifestationForm() {
        ManifestationController manifestationController = new ManifestationController(new FrmManifestation(mainContoller.getFrmMain(), true));
        manifestationController.openForm(FormMode.FORM_ADD);
    }

    public void openManifestationDetailsManifestationForm() {
        FrmManifestation manifestationDetails = new FrmManifestation(mainContoller.getFrmMain(), true);
        ManifestationController manifestationController = new ManifestationController(manifestationDetails);
        manifestationController.openForm(FormMode.FORM_VIEW);
    }

    public void openViewAllManifestationsForm() {
        FrmViewAllManifestations form = new FrmViewAllManifestations(mainContoller.getFrmMain(), true);
        ManifestationViewAllController manifestationViewAllController = new ManifestationViewAllController(form);
        manifestationViewAllController.openForm();
    }

    // DNEVNI RASPORED
    public void openDailyScheduleForm() {
        DailyScheduleController dailyScheduleController = new DailyScheduleController(new FrmDailySchedule(mainContoller.getFrmMain(), true));
        dailyScheduleController.openForm(FormMode.FORM_ADD);
    }

    public void openDailyScheduleDetailsDailyScheduleForm() {
        FrmDailySchedule dailyScheduleDetails = new FrmDailySchedule(mainContoller.getFrmMain(), true);
        DailyScheduleController dailyScheduleController = new DailyScheduleController(dailyScheduleDetails);
        dailyScheduleController.openForm(FormMode.FORM_VIEW);
    }

    public void openViewAllDailySchedulesForm() {
        FrmViewAllDailySchedules frmViewAllDailySchedules = new FrmViewAllDailySchedules(mainContoller.getFrmMain(), true);
        DailyScheduleViewAllController dailyScheduleViewAllController = new DailyScheduleViewAllController(frmViewAllDailySchedules);
        dailyScheduleViewAllController.openForm();
    }

    public MainController getMainContoller() {
        return mainContoller;
    }
}
