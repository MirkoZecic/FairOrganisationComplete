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
public class DnevniRaspored implements GenericEntity {

    private Long dnevniRasporedId;
    private Date datum;
    private Manifestacija manifestacija;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public DnevniRaspored() {
    }

    public DnevniRaspored(Long dnevniRasporedId, Date datum, Manifestacija manifestacija) {
        this.dnevniRasporedId = dnevniRasporedId;
        this.datum = datum;
        this.manifestacija = manifestacija;
    }

    public Manifestacija getManifestacija() {
        return manifestacija;
    }

    public void setManifestacija(Manifestacija manifestacija) {
        this.manifestacija = manifestacija;
    }

    public Long getDnevniRasporedId() {
        return dnevniRasporedId;
    }

    @Override
    public void setId(Long id) {
        this.dnevniRasporedId = id;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
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
        final DnevniRaspored other = (DnevniRaspored) obj;
        if (!Objects.equals(this.dnevniRasporedId, other.dnevniRasporedId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return dnevniRasporedId + " / " + datum + " / " + manifestacija.getNaziv();
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
                + "tm.nazivtipa AS 'nazivtipa'";
    }

    @Override
    public String getAtrValue() {
        return "'" + sdf.format(datum) + "', " + manifestacija.getManifestacijaId();
    }

    @Override
    public String setAtrValue() {
        return "dnevnirasporedid=" + dnevniRasporedId + ", datum='" + sdf.format(datum) + "', manifestacijaid=" + manifestacija.getManifestacijaId();
    }

    @Override
    public String getClassName() {
        return "dnevniraspored";
    }

    @Override
    public String getComplexClassName() {
        return "dnevniraspored d\n"
                + "JOIN manifestacija m\n"
                + "ON d.manifestacijaid = m.manifestacijaid\n"
                + "JOIN tipmanifestacije tm\n"
                + "ON m.tipid = tm.tipid\n";
    }

    @Override
    public String getWhereCondition() {
        return "manifestacijaid=" + manifestacija.getManifestacijaId() + " AND datum='" + sdf.format(datum) + "'";
    }

    @Override
    public GenericEntity getNewRecord(ResultSet rs) throws SQLException {
        TipManifestacije tipM = new TipManifestacije(rs.getLong("tipid"), rs.getString("nazivtipa"));

        Manifestacija man = new Manifestacija(rs.getLong("manifestacijaid"), rs.getString("nazivm"), rs.getDate("datumod"), rs.getDate("datumdo"), tipM);

        DnevniRaspored dr = new DnevniRaspored(rs.getLong("dnevnirasporedid"), rs.getDate("datum"), man);

        return dr;
    }

    @Override
    public String getColumnsForInsertNames() {
        return "datum,manifestacijaid";
    }

    @Override
    public void setNewRecord(GenericEntity ge) {
        DnevniRaspored tempDR = (DnevniRaspored) ge;
        this.dnevniRasporedId = tempDR.getDnevniRasporedId();
        this.datum = tempDR.getDatum();
        this.manifestacija = tempDR.getManifestacija();
    }

}
