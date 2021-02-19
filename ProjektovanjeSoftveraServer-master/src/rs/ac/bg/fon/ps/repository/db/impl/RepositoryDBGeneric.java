/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.repository.db.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import rs.ac.bg.fon.ps.domain.GenericEntity;
import rs.ac.bg.fon.ps.exceptions.CRUDException;
import rs.ac.bg.fon.ps.exceptions.crud.impl.AddException;
import rs.ac.bg.fon.ps.exceptions.crud.impl.DeleteException;
import rs.ac.bg.fon.ps.exceptions.crud.impl.EditException;
import rs.ac.bg.fon.ps.exceptions.crud.impl.GetException;
import rs.ac.bg.fon.ps.repository.db.DbConnectionFactory;
import rs.ac.bg.fon.ps.repository.db.DbRepository;

/**
 *
 * @author Mirko
 */
public class RepositoryDBGeneric implements DbRepository<GenericEntity> {

    @Override
    public void Dodaj(GenericEntity entity) throws CRUDException {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            String upit = "INSERT INTO " + entity.getClassName() + " (" + entity.getColumnsForInsertNames() + ")" + " VALUES (" + entity.getAtrValue() + ")";

            System.out.println(upit);
            Statement statement = connection.createStatement();
            statement.executeUpdate(upit, Statement.RETURN_GENERATED_KEYS);
            ResultSet rsKey = statement.getGeneratedKeys();
            if (rsKey.next()) {
                Long id = rsKey.getLong(1);
                entity.setId(id);
            }
            statement.close();
            rsKey.close();
        } catch (SQLException ex) {
            throw new AddException("Greska prilikom ubacivanja " + entity.getClassName() + " u bazu!", ex);
        }
    }

    @Override
    public void NadjiSve(GenericEntity entity, List<GenericEntity> list) throws CRUDException {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            String upit = "SELECT " + entity.getSelect() + " FROM " + entity.getComplexClassName();
            System.out.println(upit);

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(upit);
            while (rs.next()) {
                list.add(entity.getNewRecord(rs));
            }
            statement.close();
        } catch (SQLException ex) {
            throw new GetException("Greska prilikom citanja " + entity.getClassName() + " u bazu!", ex);
        }
    }

    @Override
    public void NadjiSveSaUslovom(GenericEntity entity, List<GenericEntity> list) throws CRUDException {
        list.clear();
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            String upit = "SELECT " + entity.getSelect() + " FROM " + entity.getComplexClassName() + " HAVING " + entity.getWhereCondition();
            System.out.println(upit);

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(upit);
            while (rs.next()) {
                list.add(entity.getNewRecord(rs));
            }
            statement.close();
        } catch (SQLException ex) {
            throw new GetException("Greska prilikom citanja " + entity.getClassName() + " u bazu!", ex);
        }
    }

    @Override
    public void Izmeni(GenericEntity param) throws CRUDException {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            String upit = "UPDATE " + param.getClassName() + " SET " + param.setAtrValue() + " WHERE " + param.getWhereCondition();

            System.out.println(upit);
            Statement statement = connection.createStatement();
            statement.executeUpdate(upit);
            statement.close();
        } catch (SQLException ex) {
            throw new EditException("Greska prilikom izmene " + param.getClassName() + " u bazu!", ex);
        }
    }

    @Override
    public void Obrisi(GenericEntity param) throws CRUDException {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            String upit = "DELETE FROM " + param.getClassName() + " WHERE " + param.getWhereCondition();

            System.out.println(upit);
            Statement statement = connection.createStatement();
            statement.executeUpdate(upit);
            statement.close();
        } catch (SQLException ex) {
            throw new DeleteException("Greska prilikom brisanja " + param.getClassName() + " u bazu!", ex);
        }
    }

    @Override
    public void Ucitaj(GenericEntity entity) throws CRUDException {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            String upit = "SELECT " + entity.getSelect() + " FROM " + entity.getComplexClassName() + " HAVING " + entity.getWhereCondition();
            System.out.println(upit);

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(upit);
            if (rs.next()) {
                entity.setNewRecord(entity.getNewRecord(rs));
            }
            statement.close();
        } catch (SQLException ex) {
            throw new GetException("Greska prilikom citanja " + entity.getClassName() + " u bazu!", ex);
        }
    }

}
