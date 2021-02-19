/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.communication;

import java.net.Socket;
import java.util.List;
import rs.ac.bg.fon.ps.domain.Izlagac;
import rs.ac.bg.fon.ps.domain.Manifestacija;
import rs.ac.bg.fon.ps.domain.Admin;
import rs.ac.bg.fon.ps.domain.DR_I;
import rs.ac.bg.fon.ps.domain.DnevniRaspored;
import rs.ac.bg.fon.ps.domain.Hala;
import rs.ac.bg.fon.ps.domain.Kompanija;
import rs.ac.bg.fon.ps.domain.TipManifestacije;
import rs.ac.bg.fon.ps.view.cordinator.MainCordinator;

/**
 *
 * @author Mirko
 */
public class Communication {

    private final Socket socket;
    private final Sender sender;
    private final Receiver receiver;
    private static Communication instance;

    private Communication() throws Exception {
        socket = new Socket("localhost", 7868);
        sender = new Sender(socket);
        receiver = new Receiver(socket);
    }

    public static Communication getInstance() throws Exception {
        if (instance == null) {
            instance = new Communication();
        }
        return instance;
    }

    public Admin PrijavljivanjeNaSistem(Admin admin) throws Exception {
        Request request = new Request(Operations.LOGIN, admin);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (Admin) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public void logout(Admin admin) throws Exception {
        Request request = new Request(Operations.LOGOUT, admin);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            MainCordinator.getInstance().openLoginForm();
        } else {
            throw response.getException();
        }
    }

    public List<Izlagac> getAllExhibitors() throws Exception {
        Request request = new Request(Operations.GET_ALL_EXHIBITORS, null);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (List<Izlagac>) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public List<Kompanija> getAllCompanies() throws Exception {
        Request request = new Request(Operations.GET_ALL_COMPANIES, null);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (List<Kompanija>) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public List<Manifestacija> getAllManifestations() throws Exception {
        Request request = new Request(Operations.GET_ALL_MANIFESTATIONS, null);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (List<Manifestacija>) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public List<TipManifestacije> UcitajListuTipovaManifestacija() throws Exception {
        Request request = new Request(Operations.GET_ALL_MANIFESTATION_TYPES, null);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (List<TipManifestacije>) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public List<DnevniRaspored> getAllDailySchedules() throws Exception {
        Request request = new Request(Operations.GET_ALL_DAILY_SCHEDULES, null);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (List<DnevniRaspored>) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public DnevniRaspored getDailySchedule(DnevniRaspored dnevniRaspored) throws Exception {
        Request request = new Request(Operations.GET_DAILY_SCHEDULE, dnevniRaspored);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (DnevniRaspored) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public List<DR_I> getAllDailySchedulesExhibitors() throws Exception {
        Request request = new Request(Operations.GET_ALL_DAILY_SCHEDULE_EXHIBITORS, null);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (List<DR_I>) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public List<DR_I> getAllDailySchedulesExhibitorsWithCondition(DR_I dri) throws Exception {
        Request request = new Request(Operations.GET_DAILY_SCHEDULE_EXHIBITORS_WITH_CONDITION, dri);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (List<DR_I>) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public List<Hala> getAllHalls() throws Exception {
        Request request = new Request(Operations.GET_ALL_HALLS, null);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            return (List<Hala>) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public void addExhibitor(Izlagac izlagac) throws Exception {
        Request request = new Request(Operations.ADD_EXHIBITOR, izlagac);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            Izlagac noviIzlagac = (Izlagac) response.getResult();
            izlagac.setId(noviIzlagac.getIzlagacId());
        } else {
            throw response.getException();
        }
    }

    public void editExhibitor(Izlagac izlagac) throws Exception {
        Request request = new Request(Operations.EDIT_EXHIBITOR, izlagac);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {

        } else {
            throw response.getException();
        }
    }

    public void deleteExhibitor(Izlagac izlagac) throws Exception {
        Request request = new Request(Operations.DELETE_EXHIBITOR, izlagac);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {

        } else {
            throw response.getException();
        }
    }

    public void KreirajManifestaciju(Manifestacija manifestacija) throws Exception {
        Request request = new Request(Operations.ADD_MANIFESTATION, manifestacija);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            Manifestacija novaManifestacija = (Manifestacija) response.getResult();
            manifestacija.setId(novaManifestacija.getManifestacijaId());
        } else {
            throw response.getException();
        }
    }

    public void editManifestation(Manifestacija manifestacija) throws Exception {
        Request request = new Request(Operations.EDIT_MANIFESTATION, manifestacija);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {

        } else {
            throw response.getException();
        }
    }

    public void deleteManifestation(Manifestacija manifestacija) throws Exception {
        Request request = new Request(Operations.DELETE_MANIFESTATION, manifestacija);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {

        } else {
            throw response.getException();
        }
    }

    public void addDailySchedule(DnevniRaspored dnevniRaspored) throws Exception {
        Request request = new Request(Operations.ADD_DAILY_SCHEDULE, dnevniRaspored);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            DnevniRaspored noviDnevniRaspored = (DnevniRaspored) response.getResult();
            dnevniRaspored.setId(noviDnevniRaspored.getDnevniRasporedId());
        } else {
            throw response.getException();
        }
    }

    public void editDailySchedule(DnevniRaspored dnevniRaspored) throws Exception {
        Request request = new Request(Operations.EDIT_DAILY_SCHEDULE, dnevniRaspored);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            //DnevniRaspored noviDnevniRaspored = (DnevniRaspored) response.getResult();
            //dnevniRaspored.setDnevniRasporedId(noviDnevniRaspored.getDnevniRasporedId());
        } else {
            throw response.getException();
        }
    }

    public void deleteDailySchedule(DnevniRaspored dnevniRaspored) throws Exception {
        Request request = new Request(Operations.DELETE_DAILY_SCHEDULE, dnevniRaspored);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            //DnevniRaspored noviDnevniRaspored = (DnevniRaspored) response.getResult();
            //dnevniRaspored.setDnevniRasporedId(noviDnevniRaspored.getDnevniRasporedId());
        } else {
            throw response.getException();
        }
    }

    public void addDailyScheduleExhibitor(List<DR_I> dnevniRasporedi) throws Exception {
        Request request = new Request(Operations.ADD_DAILY_SCHEDULE_EXHIBITORS, dnevniRasporedi);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            //DnevniRaspored noviDnevniRaspored = (DnevniRaspored) response.getResult();
            //dnevniRaspored.setDnevniRasporedId(noviDnevniRaspored.getDnevniRasporedId());
        } else {
            throw response.getException();
        }
    }

    public void editDailyScheduleExhibitor(List<DR_I> dnevniRasporedi) throws Exception {
        Request request = new Request(Operations.EDIT_DAILY_SCHEDULE_EXHIBITORS, dnevniRasporedi);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            //DnevniRaspored noviDnevniRaspored = (DnevniRaspored) response.getResult();
            //dnevniRaspored.setDnevniRasporedId(noviDnevniRaspored.getDnevniRasporedId());
        } else {
            throw response.getException();
        }
    }

    public void deleteDailyScheduleExhibitor(List<DR_I> dnevniRasporedi) throws Exception {
        Request request = new Request(Operations.DELETE_DAILY_SCHEDULE_EXHIBITORS, dnevniRasporedi);
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getException() == null) {
            //DnevniRaspored noviDnevniRaspored = (DnevniRaspored) response.getResult();
            //dnevniRaspored.setDnevniRasporedId(noviDnevniRaspored.getDnevniRasporedId());
        } else {
            throw response.getException();
        }
    }

}
