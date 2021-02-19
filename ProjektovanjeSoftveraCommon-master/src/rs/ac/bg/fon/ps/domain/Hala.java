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
public class Hala implements GenericEntity {

    private Long brojHale;
    private int brStandova;

    public Hala() {
    }

    public Hala(Long brojHale, int brStandova) {
        this.brojHale = brojHale;
        this.brStandova = brStandova;
    }

    public int getBrStandova() {
        return brStandova;
    }

    public void setBrStandova(int brStandova) {
        this.brStandova = brStandova;
    }

    public Long getBrojHale() {
        return brojHale;
    }

    @Override
    public void setId(Long brojHale) {
        this.brojHale = brojHale;
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
        final Hala other = (Hala) obj;
        if (!Objects.equals(this.brojHale, other.brojHale)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(brojHale);
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
        return "hala";
    }

    @Override
    public String getComplexClassName() {
        return getClassName();
    }

    @Override
    public String getWhereCondition() {
        return "brojHale + " + brojHale;
    }


    @Override
    public GenericEntity getNewRecord(ResultSet rs) throws SQLException {
        return new Hala(rs.getLong("halaid"), rs.getInt("brStandova"));
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
