/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.component.table;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import rs.ac.bg.fon.ps.domain.Manifestacija;
import rs.ac.bg.fon.ps.domain.TipManifestacije;
import rs.ac.bg.fon.ps.view.cordinator.MainCordinator;

/**
 *
 * @author Mirko
 */
public class ManifestationTableModel extends AbstractTableModel {

    private List<Manifestacija> manifestacije;
    private String[] columnNames = new String[]{"Naziv", "Datum Od", "Datum Do", "Tip Manifestacije"};
    private Class[] columnClasses = new Class[]{String.class, String.class, String.class, TipManifestacije.class};

    public ManifestationTableModel(List<Manifestacija> manifestacije) {
        this.manifestacije = manifestacije;
         if(MainCordinator.getInstance().isIsEnglish()){
             columnNames = new String[]{"Name", "Date From", "Date To", "Manifestation Type"};
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
        if (manifestacije == null) {
            return 0;
        }
        return manifestacije.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Manifestacija manifestacija = manifestacije.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return manifestacija.getNaziv();
            case 1:
                return manifestacija.getDatumOd();
            case 2:
                return manifestacija.getDatumDo();
            case 3:
                return manifestacija.getTipManifestacije();
            default:
                return "n/a";

        }
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Manifestacija manifestacija = manifestacije.get(rowIndex);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        switch (columnIndex) {
            case 0:
                manifestacija.setNaziv(value.toString());
                break;
            case 1: {
                try {
                    manifestacija.setDatumOd(sdf.parse(value.toString()));
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
            break;
            case 2: {
                try {
                    manifestacija.setDatumDo(sdf.parse(value.toString()));
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
            break;
            case 3:
                manifestacija.setTipManifestacije((TipManifestacije) value);
                break;
        }
    }

    public void addManifestation(Manifestacija manifestacija) {
        this.manifestacije.add(manifestacija);

        fireTableRowsInserted(manifestacije.size() - 1, manifestacije.size() - 1);
    }

    public Manifestacija deleteManifestation(int row) {
        Manifestacija izbrisanaManifestacija = this.manifestacije.remove(row);
        this.fireTableRowsDeleted(row, row);
        return izbrisanaManifestacija;
    }

    public Manifestacija getManifestationAt(int rowIndex) {
        return manifestacije.get(rowIndex);
    }

    public List<Manifestacija> getAllManifestations() {
        return manifestacije;
    }

    public void refreshManifestations() {
        fireTableDataChanged();
    }
}
