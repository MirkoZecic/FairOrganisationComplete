/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.controller;

import java.util.List;
import rs.ac.bg.fon.ps.cordinator.MainCordinator;
import rs.ac.bg.fon.ps.domain.Admin;
import rs.ac.bg.fon.ps.domain.DR_I;
import rs.ac.bg.fon.ps.domain.DnevniRaspored;
import rs.ac.bg.fon.ps.domain.Hala;
import rs.ac.bg.fon.ps.domain.Izlagac;
import rs.ac.bg.fon.ps.domain.Kompanija;
import rs.ac.bg.fon.ps.domain.Manifestacija;
import rs.ac.bg.fon.ps.domain.TipManifestacije;
import rs.ac.bg.fon.ps.exceptions.user.impl.AdminException;
import rs.ac.bg.fon.ps.operation.AbstractGenericOperation;
import rs.ac.bg.fon.ps.operation.admin.UcitajAdmina;
import rs.ac.bg.fon.ps.operation.admin.UcitajListuAdmina;
import rs.ac.bg.fon.ps.operation.company.UcitajListuKompanija;
import rs.ac.bg.fon.ps.operation.dailySchedule.KreirajDnevniRaspored;
import rs.ac.bg.fon.ps.operation.dailySchedule.ObrisiDnevniRaspored;
import rs.ac.bg.fon.ps.operation.dailySchedule.ZapamtiDnevniRaspored;
import rs.ac.bg.fon.ps.operation.dailySchedule.UcitajListuDnevnihRasporeda;
import rs.ac.bg.fon.ps.operation.dailySchedule.UcitajDnevniRaspored;
import rs.ac.bg.fon.ps.operation.dailyScheduleExhibitor.KreirajDnevniRasporedIzlagac;
import rs.ac.bg.fon.ps.operation.dailyScheduleExhibitor.ObrisiDnevniRasporedIzlagac;
import rs.ac.bg.fon.ps.operation.dailyScheduleExhibitor.ZapamtiDnevniRasporedIzlagac;
import rs.ac.bg.fon.ps.operation.dailyScheduleExhibitor.UcitajListuDnevnihRasporedIzlagaca;
import rs.ac.bg.fon.ps.operation.dailyScheduleExhibitor.UcitajListuDnevnihRasporedIzlagacaSaUslovom;
import rs.ac.bg.fon.ps.operation.exhibitor.KreirajIzlagaca;
import rs.ac.bg.fon.ps.operation.exhibitor.ObrisiIzlagaca;
import rs.ac.bg.fon.ps.operation.exhibitor.ZapamtiIzlagaca;
import rs.ac.bg.fon.ps.operation.exhibitor.UcitajListuIzlagaca;
import rs.ac.bg.fon.ps.operation.hall.UcitajListuHala;
import rs.ac.bg.fon.ps.operation.manifestation.KreirajManifestaciju;
import rs.ac.bg.fon.ps.operation.manifestation.ObrisiManifestaciju;
import rs.ac.bg.fon.ps.operation.manifestation.ZapamtiManifestaciju;
import rs.ac.bg.fon.ps.operation.manifestation.UcitajListuManifestacija;
import rs.ac.bg.fon.ps.operation.manifestationType.UcitajListuTipovaManifestacija;

/**
 *
 * @author Mirko
 */
public class Controller {

    private static Controller instance;

    private Controller() {
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public void PrijavljivanjeNaSistem(Admin admin) throws Exception {
        AbstractGenericOperation operation = new UcitajAdmina();
        operation.execute(admin);
    }

    // Geteri za sve klase
    public void UcitajListuAdmina(List<Admin> admini) throws Exception {
        AbstractGenericOperation operation = new UcitajListuAdmina();
        operation.execute(admini);
    }

    public void UcitajListuIzlagaca(List<Izlagac> izlagaci) throws Exception {
        AbstractGenericOperation operation = new UcitajListuIzlagaca();
        operation.execute(izlagaci);
    }

    public void UcitajListuManifestacija(List<Manifestacija> manifestacije) throws Exception {
        AbstractGenericOperation operation = new UcitajListuManifestacija();
        operation.execute(manifestacije);
    }

    public void UcitajListuDnevnihRasporeda(List<DnevniRaspored> dnevniRasporedi) throws Exception {
        AbstractGenericOperation operation = new UcitajListuDnevnihRasporeda();
        operation.execute(dnevniRasporedi);
    }

    public void UcitajDnevniRaspored(DnevniRaspored dr) throws Exception {
        AbstractGenericOperation operation = new UcitajDnevniRaspored();
        operation.execute(dr);
    }

    public void UcitajListuDnevnihRasporedIzlagaca(List<DR_I> drii) throws Exception {
        AbstractGenericOperation operation = new UcitajListuDnevnihRasporedIzlagaca();
        operation.execute(drii);
    }

    public void UcitajListuDnevnihRasporedIzlagacaSaUslovom(List<DR_I> drii) throws Exception {
        AbstractGenericOperation operation = new UcitajListuDnevnihRasporedIzlagacaSaUslovom();
        operation.execute(drii);
    }

    public void UcitajListuKompanija(List<Kompanija> kompanije) throws Exception {
        AbstractGenericOperation operation = new UcitajListuKompanija();
        operation.execute(kompanije);
    }

    public void UcitajListuTipovaManifestacija(List<TipManifestacije> tipManifest) throws Exception {
        AbstractGenericOperation operation = new UcitajListuTipovaManifestacija();
        operation.execute(tipManifest);
    }

    public void UcitajListuHala(List<Hala> hale) throws Exception {
        AbstractGenericOperation operation = new UcitajListuHala();
        operation.execute(hale);
    }

    // Izlagac
    public void KreirajIzlagaca(Izlagac izlagac) throws Exception {
        AbstractGenericOperation operation = new KreirajIzlagaca();
        operation.execute(izlagac);
    }

    public void ZapamtiIzlagaca(Izlagac izlagac) throws Exception {
        AbstractGenericOperation operation = new ZapamtiIzlagaca();
        operation.execute(izlagac);
    }

    public void ObrisiIzlagaca(Izlagac izlagac) throws Exception {
        AbstractGenericOperation operation = new ObrisiIzlagaca();
        operation.execute(izlagac);
    }

    // Manifestacija
    public void KreirajManifestaciju(Manifestacija manifest) throws Exception {
        AbstractGenericOperation operation = new KreirajManifestaciju();
        operation.execute(manifest);
    }

    public void ZapamtiManifestaciju(Manifestacija manifest) throws Exception {
        AbstractGenericOperation operation = new ZapamtiManifestaciju();
        operation.execute(manifest);
    }

    public void ObrisiManifestaciju(Manifestacija manifest) throws Exception {
        AbstractGenericOperation operation = new ObrisiManifestaciju();
        operation.execute(manifest);
    }

    // Dnevni raspored
    public void KreirajDnevniRaspored(DnevniRaspored dr) throws Exception {
        AbstractGenericOperation operation = new KreirajDnevniRaspored();
        operation.execute(dr);
    }

    public void ZapamtiDnevniRaspored(DnevniRaspored dr) throws Exception {
        AbstractGenericOperation operation = new ZapamtiDnevniRaspored();
        operation.execute(dr);
    }

    public void ObrisiDnevniRaspored(DnevniRaspored dr) throws Exception {
        AbstractGenericOperation operation = new ObrisiDnevniRaspored();
        operation.execute(dr);
    }

    // Dnevni raspored izlagac
    public void KreirajDnevniRasporedIzlagac(List<DR_I> dri) throws Exception {
        AbstractGenericOperation operation = new KreirajDnevniRasporedIzlagac();
        operation.execute(dri);
    }

    public void ZapamtiDnevniRasporedIzlagac(List<DR_I> dri) throws Exception {
        AbstractGenericOperation operation = new ZapamtiDnevniRasporedIzlagac();
        operation.execute(dri);
    }

    public void ObrisiDnevniRasporedIzlagac(DR_I dri) throws Exception {
        AbstractGenericOperation operation = new ObrisiDnevniRasporedIzlagac();
        operation.execute(dri);
    }

}
