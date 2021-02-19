/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.component.table;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import rs.ac.bg.fon.ps.domain.DR_I;
import rs.ac.bg.fon.ps.domain.DnevniRaspored;
import rs.ac.bg.fon.ps.domain.Hala;
import rs.ac.bg.fon.ps.domain.Izlagac;
import rs.ac.bg.fon.ps.view.cordinator.MainCordinator;

/**
 *
 * @author Mirko
 */
public class DailyScheduleItemTableModel extends AbstractTableModel {

    private List<DR_I> dr_ii;
    private String[] columnNames = new String[]{"Izlagac", "Hala", "Stand"};
    private Class[] columnClasses = new Class[]{Izlagac.class, Hala.class, Integer.class};

    public DailyScheduleItemTableModel(List<DR_I> dr_ii) {
        this.dr_ii = dr_ii;
        if (MainCordinator.getInstance().isIsEnglish()) {
            columnNames = new String[]{"Exhibitor", "Hall", "Stand"};
        }
    }

    public int getRowCount() {
        return dr_ii.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
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
        DR_I dr_i = dr_ii.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return dr_i.getIzlagac();
            case 1:
                return dr_i.getHala();
            case 2:
                return dr_i.getBrStanda();
            default:
                return "n/a";
        }
    }

    public void addDailyScheduleItem(DnevniRaspored dnevniRaspored, Izlagac izlagac, Hala hala, int brStanda) {
        DR_I dri = new DR_I(dnevniRaspored, izlagac, hala, brStanda);
        dr_ii.add(dri);

        fireTableRowsInserted(dr_ii.size() - 1, dr_ii.size() - 1);
    }

    public DR_I removeDailyScheduleItem(int rowIndex) {
        DR_I drI = dr_ii.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
        return drI;
    }

    public int getIndexOfDailyScheduleItem(DR_I dri) {
        return dr_ii.indexOf(dri);
    }

    public List<DR_I> getListDailyScheduleItems() {
        return dr_ii;
    }

}
