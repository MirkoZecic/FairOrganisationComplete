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
public class Kompanija implements GenericEntity {

    private Long kompanijaId;
    private String naziv;
    private String adresa;
    private String kontaktTelefon;

    public Kompanija() {
    }

    public Kompanija(Long kompanijaId, String naziv, String adresa, String kontaktTelefon) {
        this.kompanijaId = kompanijaId;
        this.naziv = naziv;
        this.adresa = adresa;
        this.kontaktTelefon = kontaktTelefon;
    }

    public String getKontaktTelefon() {
        return kontaktTelefon;
    }

    public void setKontaktTelefon(String kontaktTelefon) {
        this.kontaktTelefon = kontaktTelefon;
    }

    public Long getKompanijaId() {
        return kompanijaId;
    }

    @Override
    public void setId(Long id) {
        kompanijaId = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
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
        final Kompanija other = (Kompanija) obj;
        if (!Objects.equals(this.kompanijaId, other.kompanijaId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return naziv;
    }

    @Override
    public String getAtrValue() {
        return "";
    }

    @Override
    public String setAtrValue() {
        return "";
    }

    @Override
    public String getClassName() {
        return "kompanija";
    }

    @Override
    public String getComplexClassName() {
        return getClassName();
    }

    @Override
    public String getWhereCondition() {
        return "";
    }


    @Override
    public GenericEntity getNewRecord(ResultSet rs) throws SQLException {
        return new Kompanija(rs.getLong("kompanijaid"), rs.getString("naziv"), rs.getString("adresa"), rs.getString("kontakttelefon"));
    }

    @Override
    public String getSelect() {
        return "*";
    }

    @Override
    public String getColumnsForInsertNames() {
        return "";
    }

    @Override
    public void setNewRecord(GenericEntity ge) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
