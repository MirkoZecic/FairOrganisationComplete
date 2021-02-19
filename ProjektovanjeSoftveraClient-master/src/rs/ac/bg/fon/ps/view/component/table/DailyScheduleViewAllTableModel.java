/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.component.table;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import rs.ac.bg.fon.ps.domain.DR_I;
import rs.ac.bg.fon.ps.domain.Hala;
import rs.ac.bg.fon.ps.domain.Izlagac;
import rs.ac.bg.fon.ps.domain.Manifestacija;
import rs.ac.bg.fon.ps.view.cordinator.MainCordinator;

/**
 *
 * @author Mirko
 */
public class DailyScheduleViewAllTableModel extends AbstractTableModel {

    private List<DR_I> drii;

    String[] columnNames = new String[]{"Manifestacija", "Datum", "Izlagac", "Hala", "Stand"};
    Class[] classNames = new Class[]{Manifestacija.class, String.class, Izlagac.class, Hala.class, Integer.class};

    public DailyScheduleViewAllTableModel(List<DR_I> dailySchedules) {
        this.drii = dailySchedules;
         if(MainCordinator.getInstance().isIsEnglish()){
            columnNames= new String[]{"Manifestation", "Date", "Exhibitor", "Hall", "Stand"};
        }
    }

    @Override
    public int getRowCount() {
        return drii.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        DR_I dri = drii.get(rowIndex);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        switch (columnIndex) {
            case 0:
                return dri.getDnevniRaspored().getManifestacija();
            case 1:
                return sdf.format(dri.getDnevniRaspored().getDatum());
            case 2:
                return dri.getIzlagac();
            case 3:
                return dri.getHala();
            case 4:
                return dri.getBrStanda();
            default:
                return "n/a";
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return classNames[columnIndex];
    }

    public DR_I getDailyScheduleItemAt(int rowIndex) {
        return drii.get(rowIndex);
    }

    public List<DR_I> getAllDailySchedules() {
        return drii;
    }
}
