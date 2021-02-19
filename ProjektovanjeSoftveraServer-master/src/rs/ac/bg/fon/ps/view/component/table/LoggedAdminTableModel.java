/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.component.table;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import rs.ac.bg.fon.ps.domain.Admin;

/**
 *
 * @author Mirko
 */
public class LoggedAdminTableModel extends AbstractTableModel {

    List<Admin> admini;
    List<Boolean> ulogovani;
    private String[] columnNames = new String[]{"Id", "Email", "Ime", "Prezime", "Aktivan"};
    private Class[] columnClasses = new Class[]{Integer.class, String.class, String.class, String.class, String.class};

    public LoggedAdminTableModel(List<Admin> admini, List<Boolean> ulogovani) {
        this.admini = admini;
        this.ulogovani = ulogovani;
    }

    @Override
    public int getRowCount() {
        return admini.size();
    }

    @Override
    public int getColumnCount() {
        return columnClasses.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClasses[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Admin admin = admini.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return admin.getId();
            case 1:
                return admin.getEmail();
            case 2:
                return admin.getIme();
            case 3:
                return admin.getPrezime();
            case 4:
                if (ulogovani.get(rowIndex)) {
                    return "DA";
                } else {
                    return "NE";
                }
            default:
                return "n/a";
        }
    }

    public void refreshTable() {
        fireTableDataChanged();
    }

}
