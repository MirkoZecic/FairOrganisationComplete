/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.component;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Mirko
 */
public class StatusColumnCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

        String vrednost = table.getValueAt(row, col).toString();
        if (vrednost.equals("DA")) {
            comp.setForeground(Color.green);

        } else {
            comp.setForeground(Color.red);
        }

        return (comp);
    }
}
