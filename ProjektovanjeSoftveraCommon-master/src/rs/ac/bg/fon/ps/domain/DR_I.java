/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 *
 * @author Mirko
 */
public class DR_I implements GenericEntity {

    private DnevniRaspored dnevniRaspored;
    private Izlagac izlagac;
    private Hala hala;
    private int brStanda;

    public DR_I() {
    }

    public DR_I(DnevniRaspored dnevniRaspored, Izlagac izlagac, Hala hala, int brStanda) {
        this.dnevniRaspored = dnevniRaspored;
        this.izlagac = izlagac;
        this.hala = hala;
        this.brStanda = brStanda;
    }

    public int getBrStanda() {
        return brStanda;
    }

    public void setBrStanda(int brStanda) {
        this.brStanda = brStanda;
    }

    public DnevniRaspored getDnevniRaspored() {
        return dnevniRaspored;
    }

    public void setDnevniRaspored(DnevniRaspored dnevniRaspored) {
        this.dnevniRaspored = dnevniRaspored;
    }

    public Izlagac getIzlagac() {
        return izlagac;
    }

    public void setIzlagac(Izlagac izlagac) {
        this.izlagac = izlagac;
    }

    public Hala getHala() {
        return hala;
    }

    public void setHala(Hala hala) {
        this.hala = hala;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DR_I other = (DR_I) obj;
        if (!Objects.equals(this.dnevniRaspored, other.dnevniRaspored)) {
            return false;
        }
        if (!Objects.equals(this.izlagac, other.izlagac)) {
            return false;
        }
        return true;
    }

    @Override
    public String getSelect() {
        return "d.dnevnirasporedid AS 'dnevnirasporedid', \n"
                + "d.datum AS 'datum',\n"
                + "m.manifestacijaid AS 'manifestacijaid',\n"
                + "m.naziv AS 'nazivm',\n"
                + "m.datumod AS 'datumod',\n"
                + "m.datumdo AS 'datumdo',\n"
                + "tm.tipid AS 'tipid',\n"
                + "tm.nazivtipa AS 'nazivtipa',\n"
                + "i.izlagacid AS 'izlagacid',\n"
                + "i.ime AS 'imeIz',\n"
                + "i.prezime AS 'prezimeIz',\n"
                + "i.kontakttelefon AS 'telefonIz',\n"
                + "i.email AS 'emailIz',\n"
                + "k.kompanijaid AS 'kompanijaId',\n"
                + "k.naziv AS 'nazivK',\n"
                + "k.adresa AS 'adresaK',\n"
                + "k.kontakttelefon AS 'telefonK',\n"
                + "h.halaid AS 'halaId',\n"
                + "h.brStandova AS 'brStandova',\n"
                + "dri.stand AS 'stand'\n";
    }

    @Override
    public String getAtrValue() {
        return dnevniRaspored.getDnevniRasporedId() + "," + izlagac.getIzlagacId() + "," + hala.getBrojHale() + "," + brStanda;
    }

    @Override
    public String setAtrValue() {
        return "dnevnirasporedid=" + dnevniRaspored.getDnevniRasporedId() + ",izlagacid=" + izlagac.getIzlagacId() + ",halaid=" + hala.getBrojHale() + ",stand=" + brStanda;
    }

    @Override
    public String getClassName() {
        return "dr_i";
    }

    @Override
    public String getComplexClassName() {
        return "dr_i dri\n"
                + "JOIN dnevniraspored d\n"
                + "ON dri.dnevnirasporedid = d.dnevnirasporedid\n"
                + "JOIN manifestacija m\n"
                + "ON d.manifestacijaid = m.manifestacijaid\n"
                + "JOIN tipmanifestacije tm\n"
                + "ON m.tipid = tm.tipid\n"
                + "JOIN izlagac i\n"
                + "ON dri.izlagacid = i.izlagacid\n"
                + "JOIN kompanija k\n"
                + "ON i.kompanijaid = k.kompanijaid\n"
                + "JOIN hala h\n"
                + "ON h.halaid = dri.halaid\n";
    }

    @Override
    public String getWhereCondition() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return "dnevnirasporedid=" + dnevniRaspored.getDnevniRasporedId();
    }

    @Override
    public GenericEntity getNewRecord(ResultSet rs) throws SQLException {
        TipManifestacije tipM = new TipManifestacije(rs.getLong("tipid"), rs.getString("nazivtipa"));

        Manifestacija man = new Manifestacija(rs.getLong("manifestacijaid"), rs.getString("nazivm"), rs.getDate("datumod"), rs.getDate("datumdo"), tipM);

        DnevniRaspored dr = new DnevniRaspored(rs.getLong("dnevnirasporedid"), rs.getDate("datum"), man);

        Kompanija komp = new Kompanija(rs.getLong("kompanijaId"), rs.getString("nazivK"), rs.getString("adresaK"), rs.getString("telefonK"));

        Izlagac i = new Izlagac(rs.getLong("izlagacid"), rs.getString("imeIz"), rs.getString("prezimeIz"), rs.getString("telefonIz"), rs.getString("emailIz"), komp);

        Hala h = new Hala(rs.getLong("halaId"), rs.getInt("brStandova"));

        DR_I noviDri = new DR_I(dr, i, h, rs.getInt("stand"));
        return noviDri;
    }

    @Override
    public void setId(Long id) {
    }

    @Override
    public String getColumnsForInsertNames() {
        return "dnevnirasporedid,izlagacid,halaid,stand";
    }

    @Override
    public void setNewRecord(GenericEntity ge) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
