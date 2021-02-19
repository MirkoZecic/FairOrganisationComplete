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
public class TipManifestacije implements GenericEntity {

    private Long tipId;
    private String nazivTipa;

    public TipManifestacije() {
    }

    public TipManifestacije(Long TipId, String NazivTipa) {
        this.tipId = TipId;
        this.nazivTipa = NazivTipa;
    }

    public String getNazivTipa() {
        return nazivTipa;
    }

    public void setNazivTipa(String NazivTipa) {
        this.nazivTipa = NazivTipa;
    }

    public Long getTipId() {
        return tipId;
    }

    @Override
    public void setId(Long TipId) {
        this.tipId = TipId;
    }

    @Override
    public String toString() {
        return nazivTipa;
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
        final TipManifestacije other = (TipManifestacije) obj;
        if (!Objects.equals(this.tipId, other.tipId)) {
            return false;
        }
        return true;
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
        return "tipmanifestacije";
    }

    @Override
    public String getComplexClassName() {
        return getClassName();
    }

    @Override
    public String getWhereCondition() {
        return "where tipid=" + tipId;
    }


    @Override
    public GenericEntity getNewRecord(ResultSet rs) throws SQLException {
        return new TipManifestacije(rs.getLong("tipid"), rs.getString("nazivtipa"));
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
