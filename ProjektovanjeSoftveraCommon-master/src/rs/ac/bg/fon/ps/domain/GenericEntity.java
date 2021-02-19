/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Mirko
 */
public interface GenericEntity extends Serializable {

    public String getSelect();
    public String getAtrValue();
    public String setAtrValue();
    public String getClassName();
    public String getComplexClassName();
    public String getColumnsForInsertNames();
    public String getWhereCondition();
    public GenericEntity getNewRecord(ResultSet rs) throws SQLException;
    public void setNewRecord(GenericEntity ge);
    public void setId(Long id);
}
