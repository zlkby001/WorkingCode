package org.forms;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

class Myrender extends DefaultTableCellRenderer {   
    private int count0, count1;   
  
    public Myrender(int num0, int num1) {   
        this.count0 = num0;  
        this.count1 = num1;
    }  
  
    public Component getTableCellRendererComponent(JTable table,  
            Object value, boolean isSelected, boolean hasFocus,  
            int row, int column) {
    	if(row <count0) {
    		setBackground(Color.PINK);
    	}
    	else if(row < count1+count0) setBackground(Color.magenta);
    	else setBackground(Color.WHITE);
        return super.getTableCellRendererComponent(table, value,  
                isSelected, hasFocus, row, column);  
    } 

}  