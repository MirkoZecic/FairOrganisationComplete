/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Mirko
 */
public class Manifestacija implements GenericEntity {

    private Long manifestacijaId;
    private String naziv;
    private Date datumOd;
    private Date datumDo;
    private TipManifestacije tipManifestacije;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public Manifestacija() {
    }

    public Manifestacija(Long ManifestacijaId, String Naziv, Date DatumOd, Date DatumDo, TipManifestacije tipManifestacije) {
        this.manifestacijaId = ManifestacijaId;
        this.naziv = Naziv;
        this.datumOd = DatumOd;
        this.datumDo = DatumDo;
        this.tipManifestacije = tipManifestacije;
    }

    public TipManifestacije getTipManifestacije() {
        return tipManifestacije;
    }

    public void setTipManifestacije(TipManifestacije tipManifestacije) {
        this.tipManifestacije = tipManifestacije;
    }

    public Long getManifestacijaId() {
        return manifestacijaId;
    }

    @Override
    public void setId(Long id) {
        this.manifestacijaId = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String Naziv) {
        this.naziv = Naziv;
    }

    public Date getDatumOd() {
        return datumOd;
    }

    public void setDatumOd(Date DatumOd) {
        this.datumOd = DatumOd;
    }

    public Date getDatumDo() {
        return datumDo;
    }

    public void setDatumDo(Date DatumDo) {
        this.datumDo = DatumDo;
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
        final Manifestacija other = (Manifestacija) obj;
        if (!Objects.equals(this.manifestacijaId, other.manifestacijaId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return naziv;
    }

    @Override
    public String getSelect() {
        return "m.manifestacijaid,m.naziv,m.datumod,m.datumdo,tm.tipid,tm.nazivtipa";
    }

    @Override
    public String getAtrValue() {
        return "'" + naziv + "', '" + sdf.format(datumOd) + "', '" + sdf.format(datumDo) + "', " + tipManifestacije.getTipId();
    }

    @Override
    public String setAtrValue() {
        return "naziv='" + naziv + "', datumod='" + sdf.format(datumOd) + "', datumdo='" + sdf.format(datumDo) + "', tipid=" + tipManifestacije.getTipId();
    }

    @Override
    public String getClassName() {
        return "manifestacija";
    }

    @Override
    public String getComplexClassName() {
        return "manifestacija m join tipmanifestacije tm on m.tipid=tm.tipid";
    }

    @Override
    public String getWhereCondition() {
        return "manifestacijaid=" + manifestacijaId;
    }


    @Override
    public GenericEntity getNewRecord(ResultSet rs) throws SQLException {
        TipManifestacije tm = new TipManifestacije(rs.getLong("tm.tipid"), rs.getString("tm.nazivtipa"));
        return new Manifestacija(rs.getLong("m.manifestacijaid"), rs.getString("m.naziv"), rs.getDate("m.datumod"), rs.getDate("m.datumdo"), tm);
    }

    @Override
    public String getColumnsForInsertNames() {
        return "naziv,datumod,datumdo,tipid";
    }

    @Override
    public void setNewRecord(GenericEntity ge) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
