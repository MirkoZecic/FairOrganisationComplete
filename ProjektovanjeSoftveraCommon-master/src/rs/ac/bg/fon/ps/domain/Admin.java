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
public class Admin implements GenericEntity {

    private Long id;
    private String email;
    private String password;
    private String ime;
    private String prezime;

    public Admin() {
    }

    public Admin(Long id, String email, String password, String ime, String prezime) {
        this.id = id;
        this.password = password;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        final Admin other = (Admin) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id+" "+ime + " " + prezime+" "+email;
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
        return "admin";
    }

    @Override
    public String getComplexClassName() {
        return getClassName();
    }

    @Override
    public String getWhereCondition() {
        return "email ='" + email + "' AND password='" + password + "'";
    }

    @Override
    public GenericEntity getNewRecord(ResultSet rs) throws SQLException {
        return new Admin(rs.getLong("id"), rs.getString("email"), rs.getString("password"), rs.getString("ime"), rs.getString("prezime"));
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
        Admin tempAdmin = (Admin) ge;
        this.id = tempAdmin.getId();
        this.password = tempAdmin.getPassword();
        this.ime = tempAdmin.getIme();
        this.prezime = tempAdmin.getPrezime();
        this.email = tempAdmin.getEmail();
    }

}
