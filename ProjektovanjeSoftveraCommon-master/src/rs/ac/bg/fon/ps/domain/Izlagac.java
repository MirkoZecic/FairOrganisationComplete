/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 *
 * @author Mirko
 */
public class Izlagac implements GenericEntity {

    private Long izlagacId;
    private String ime;
    private String prezime;
    private String kontaktTelefon;
    private String email;
    private Kompanija kompanija;

    public Izlagac() {
    }

    public Izlagac(Long izlagacId, String ime, String prezime, String kontaktTelefon, String email, Kompanija kompanija) {
        this.izlagacId = izlagacId;
        this.ime = ime;
        this.prezime = prezime;
        this.kontaktTelefon = kontaktTelefon;
        this.email = email;
        this.kompanija = kompanija;
    }

    public Kompanija getKompanija() {
        return kompanija;
    }

    public void setKompanija(Kompanija kompanija) {
        this.kompanija = kompanija;
    }

    public Long getIzlagacId() {
        return izlagacId;
    }

    @Override
    public void setId(Long izlagacId) {
        this.izlagacId = izlagacId;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getKontaktTelefon() {
        return kontaktTelefon;
    }

    public void setKontaktTelefon(String kontaktTelefon) {
        this.kontaktTelefon = kontaktTelefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        final Izlagac other = (Izlagac) obj;
        if (!Objects.equals(this.izlagacId, other.izlagacId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ime + " " + prezime;
    }

    @Override
    public String getAtrValue() {
        return "'" + ime + "' ,'" + prezime + "' ,'" + kontaktTelefon + "' ,'" + email + "' ," + kompanija.getKompanijaId();
    }

    @Override
    public String setAtrValue() {
        return "ime='" + ime + "',prezime='" + prezime + "',kontakttelefon='" + kontaktTelefon + "',email='" + email + "',kompanijaid=" + kompanija.getKompanijaId();
    }

    @Override
    public String getClassName() {
        return "izlagac";
    }

    @Override
    public String getComplexClassName() {
        return "izlagac i join kompanija k on i.kompanijaid=k.kompanijaid";
    }

    @Override
    public String getWhereCondition() {
        return "izlagacid=" + izlagacId;
    }


    @Override
    public GenericEntity getNewRecord(ResultSet rs) throws SQLException {
        Kompanija k = new Kompanija(rs.getLong("k.kompanijaid"), rs.getString("k.naziv"), rs.getString("k.adresa"), rs.getString("k.kontakttelefon"));
        return new Izlagac(rs.getLong("i.izlagacid"), rs.getString("i.ime"), rs.getString("i.prezime"), rs.getString("i.kontakttelefon"), rs.getString("i.email"), k);
    }

    @Override
    public String getSelect() {
        return "i.izlagacid,i.ime,i.prezime,i.kontakttelefon,i.email, k.kompanijaid,k.naziv,k.adresa, k.kontakttelefon";
    }

    @Override
    public String getColumnsForInsertNames() {
        return "ime,prezime,kontakttelefon,email,kompanijaid";
    }

    @Override
    public void setNewRecord(GenericEntity ge) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
