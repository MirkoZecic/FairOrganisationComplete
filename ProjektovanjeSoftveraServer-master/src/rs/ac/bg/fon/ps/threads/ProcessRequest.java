/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.threads;

import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fon.ps.communication.Receiver;
import rs.ac.bg.fon.ps.communication.Request;
import rs.ac.bg.fon.ps.communication.Response;
import rs.ac.bg.fon.ps.communication.Sender;
import rs.ac.bg.fon.ps.controller.Controller;
import rs.ac.bg.fon.ps.cordinator.MainCordinator;
import rs.ac.bg.fon.ps.domain.Admin;
import rs.ac.bg.fon.ps.domain.DR_I;
import rs.ac.bg.fon.ps.domain.DnevniRaspored;
import rs.ac.bg.fon.ps.domain.Hala;
import rs.ac.bg.fon.ps.domain.Izlagac;
import rs.ac.bg.fon.ps.domain.Kompanija;
import rs.ac.bg.fon.ps.domain.Manifestacija;
import rs.ac.bg.fon.ps.domain.TipManifestacije;

/**
 *
 * @author Mirko
 */
public class ProcessRequest extends Thread {

    Socket socket;
    Sender sender;
    Receiver receiver;

    public ProcessRequest(Socket socket) {
        this.socket = socket;
        sender = new Sender(socket);
        receiver = new Receiver(socket);
    }

    @Override
    public void run() {
        while (!socket.isClosed()) {
            Request request = null;
            try {
                request = (Request) receiver.receive();
            } catch (SocketException ex) {
            } catch (Exception ex) {
                Logger.getLogger(ProcessRequest.class.getName()).log(Level.SEVERE, null, ex);
            }
            Response response = new Response();
            try {
                switch (request.getOperation()) {
                    case LOGIN:
                        Admin adminLogin = (Admin) request.getArgument();
                        Controller.getInstance().PrijavljivanjeNaSistem(adminLogin);
                        MainCordinator.getInstance().dodajAdmina(adminLogin);
                        response.setResult(adminLogin);
                        break;
                    case LOGOUT:
                        Admin adminLogout = (Admin) request.getArgument();
                        MainCordinator.getInstance().ukloniAdmina(adminLogout);
                        break;
                    case GET_ALL_EXHIBITORS:
                        List<Izlagac> listaIzlagaca = new ArrayList<>();
                        Controller.getInstance().UcitajListuIzlagaca(listaIzlagaca);
                        response.setResult(listaIzlagaca);
                        break;
                    case GET_ALL_COMPANIES:
                        List<Kompanija> listaKompanija = new ArrayList<>();
                        Controller.getInstance().UcitajListuKompanija(listaKompanija);
                        response.setResult(listaKompanija);
                        break;
                    case GET_ALL_MANIFESTATIONS:
                        List<Manifestacija> listaManifestacija = new ArrayList<>();
                        Controller.getInstance().UcitajListuManifestacija(listaManifestacija);
                        response.setResult(listaManifestacija);
                        break;
                    case GET_ALL_MANIFESTATION_TYPES:
                        List<TipManifestacije> listaTipovaManifestacija = new ArrayList<>();
                        Controller.getInstance().UcitajListuTipovaManifestacija(listaTipovaManifestacija);
                        response.setResult(listaTipovaManifestacija);
                        break;
                    case GET_ALL_DAILY_SCHEDULES:
                        List<DnevniRaspored> listaDnevnihRasporeda = new ArrayList<>();
                        Controller.getInstance().UcitajListuDnevnihRasporeda(listaDnevnihRasporeda);
                        response.setResult(listaDnevnihRasporeda);
                        break;
                    case GET_ALL_DAILY_SCHEDULE_EXHIBITORS:
                        List<DR_I> listaDnevnihRasporedIzlagaca = new ArrayList<>();
                        Controller.getInstance().UcitajListuDnevnihRasporedIzlagaca(listaDnevnihRasporedIzlagaca);
                        response.setResult(listaDnevnihRasporedIzlagaca);
                        break;
                    case GET_DAILY_SCHEDULE_EXHIBITORS_WITH_CONDITION:
                        List<DR_I> listaDnevnihRasporedIzlagacaSaUslovom = new ArrayList<>();
                        listaDnevnihRasporedIzlagacaSaUslovom.add((DR_I) request.getArgument());
                        Controller.getInstance().UcitajListuDnevnihRasporedIzlagacaSaUslovom(listaDnevnihRasporedIzlagacaSaUslovom);
                        response.setResult(listaDnevnihRasporedIzlagacaSaUslovom);
                        break;
                    case GET_ALL_HALLS:
                        List<Hala> listaHala = new ArrayList<>();
                        Controller.getInstance().UcitajListuHala(listaHala);
                        response.setResult(listaHala);
                        break;
                    case ADD_EXHIBITOR:
                        Izlagac izlagacZaDodati = (Izlagac) request.getArgument();
                        Controller.getInstance().KreirajIzlagaca(izlagacZaDodati);
                        response.setResult(izlagacZaDodati);
                        break;
                    case EDIT_EXHIBITOR:
                        Izlagac izlagacZaIzmeniti = (Izlagac) request.getArgument();
                        Controller.getInstance().ZapamtiIzlagaca(izlagacZaIzmeniti);
                        break;
                    case DELETE_EXHIBITOR:
                        Izlagac izlagacZaObrisati = (Izlagac) request.getArgument();
                        Controller.getInstance().ObrisiIzlagaca(izlagacZaObrisati);
                        break;
                    case ADD_MANIFESTATION:
                        Manifestacija manifestacijaZaDodati = (Manifestacija) request.getArgument();
                        Controller.getInstance().KreirajManifestaciju(manifestacijaZaDodati);
                        response.setResult(manifestacijaZaDodati);
                        break;
                    case EDIT_MANIFESTATION:
                        Manifestacija manifestacijaZaIzmeniti = (Manifestacija) request.getArgument();
                        Controller.getInstance().ZapamtiManifestaciju(manifestacijaZaIzmeniti);
                        break;
                    case DELETE_MANIFESTATION:
                        Manifestacija manifestacijaZaObrisati = (Manifestacija) request.getArgument();
                        Controller.getInstance().ObrisiManifestaciju(manifestacijaZaObrisati);
                        break;
                    case ADD_DAILY_SCHEDULE:
                        DnevniRaspored rasporedZaDodati = (DnevniRaspored) request.getArgument();
                        Controller.getInstance().KreirajDnevniRaspored(rasporedZaDodati);
                        response.setResult(rasporedZaDodati);
                        break;
                    case EDIT_DAILY_SCHEDULE:
                        DnevniRaspored rasporedZaIzmeniti = (DnevniRaspored) request.getArgument();
                        Controller.getInstance().ZapamtiDnevniRaspored(rasporedZaIzmeniti);
                        break;
                    case GET_DAILY_SCHEDULE:
                        DnevniRaspored dnevniRasporedSaUslovom = (DnevniRaspored) request.getArgument();
                        Controller.getInstance().UcitajDnevniRaspored(dnevniRasporedSaUslovom);
                        response.setResult(dnevniRasporedSaUslovom);
                        break;
                    case DELETE_DAILY_SCHEDULE:
                        DnevniRaspored rasporedZaIzbrisati = (DnevniRaspored) request.getArgument();
                        Controller.getInstance().ObrisiDnevniRaspored(rasporedZaIzbrisati);
                        break;
                    case ADD_DAILY_SCHEDULE_EXHIBITORS:
                        List<DR_I> driZaDodati = (List<DR_I>) request.getArgument();
                        Controller.getInstance().KreirajDnevniRasporedIzlagac(driZaDodati);
                        break;
                    case EDIT_DAILY_SCHEDULE_EXHIBITORS:
                        List<DR_I> driZaIzmeniti = (List<DR_I>) request.getArgument();
                        Controller.getInstance().ZapamtiDnevniRasporedIzlagac(driZaIzmeniti);
                        break;
                    case DELETE_DAILY_SCHEDULE_EXHIBITORS:
                        DR_I driZaIzbrisati = (DR_I) request.getArgument();
                        Controller.getInstance().ObrisiDnevniRasporedIzlagac(driZaIzbrisati);
                        break;
                }
            } catch (Exception e) {
                //e.printStackTrace();
                response.setException(e);
            }
            try {
                sender.send(response);
            } catch (Exception ex) {
                //ex.printStackTrace();
                Logger.getLogger(ProcessRequest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
