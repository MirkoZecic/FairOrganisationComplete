/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.component.table;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import rs.ac.bg.fon.ps.domain.Izlagac;
import rs.ac.bg.fon.ps.domain.Kompanija;
import rs.ac.bg.fon.ps.view.cordinator.MainCordinator;

/**
 *
 * @author nikollace
 */
public class ExhibitorTableModel extends AbstractTableModel {

    private List<Izlagac> izlagaci;

    private String[] columnNames = new String[]{"Ime", "Prezime", "Telefon", "Email", "Kompanija"};
    private Class[] columnClasses = new Class[]{String.class, String.class, String.class, String.class, Kompanija.class};

    public ExhibitorTableModel(List<Izlagac> izlagaci) {
        this.izlagaci = izlagaci;
        if (MainCordinator.getInstance().isIsEnglish()) {
            columnNames = new String[]{"Name", "Surname", "Telephone", "Email", "Company"};
        }
    }

    @Override
    public String getColumnName(int column) {
        if (column > columnNames.length) {
            return "n/a";
        }
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex > columnClasses.length) {
            return Object.class;
        }
        return columnClasses[columnIndex];
    }

    @Override
    public int getRowCount() {
        if (izlagaci == null) {
            return 0;
        }
        return izlagaci.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Izlagac izlagac = izlagaci.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return izlagac.getIme();
            case 1:
                return izlagac.getPrezime();
            case 2:
                return izlagac.getKontaktTelefon();
            case 3:
                return izlagac.getEmail();
            case 4:
                return izlagac.getKompanija();
            default:
                return "n/a";

        }
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Izlagac izlagac = izlagaci.get(rowIndex);
        switch (columnIndex) {
            case 0:
                izlagac.setIme(value.toString());
                break;
            case 1:
                izlagac.setPrezime(value.toString());
                break;
            case 2:
                izlagac.setKontaktTelefon(value.toString());
                break;
            case 3:
                izlagac.setEmail(value.toString());
                break;
            case 4:
                izlagac.setKompanija((Kompanija) value);
        }
    }

    public void addExhibitor(Izlagac izlagac) {
        this.izlagaci.add(izlagac);

        fireTableRowsInserted(izlagaci.size() - 1, izlagaci.size() - 1);
    }

    public Izlagac deleteExhibitor(int row) {
        Izlagac izbrisaniIzlagac = this.izlagaci.remove(row);
        this.fireTableRowsDeleted(row, row);
        return izbrisaniIzlagac;
    }

    public Izlagac getExhibitorAt(int rowIndex) {
        return izlagaci.get(rowIndex);
    }

    public List<Izlagac> getAllExhibitors() {
        return izlagaci;
    }

    public void refreshExhibitors() {
        fireTableDataChanged();
    }

}
