/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gnp;

/**
 *
 * @author test
 */
import java.awt.Dimension;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Grid {
    JTable table;  
    JLabel label;
    
    public void Grid(String[][] data,int attributeamount)  
    {  
        String[] headers = new String[attributeamount]; 
        for(int i=0;i<attributeamount;i++){
            int k=i+1;
            headers[i] = ""+k;
        }
        table = new JTable(data, headers);  
        ListSelectionModel selectionModel = table.getSelectionModel();  
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  
        selectionModel.addListSelectionListener(new RowListener(this));  
        label = new JLabel();  
        label.setHorizontalAlignment(JLabel.CENTER);  
        label.setBorder(BorderFactory.createTitledBorder("selected row values"));  
        Dimension d = label.getPreferredSize();  
        d.height = 45;  
        label.setPreferredSize(d);  
        JFrame f = new JFrame();  
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        f.getContentPane().add(new JScrollPane(table));  
        f.getContentPane().add(label, "South");  
        f.setSize(800,800);
        //f.setLocation(200,200);  
        f.setVisible(true);
    }
}
class RowListener implements ListSelectionListener  
{  
    Grid readRow;  
    JTable table;  
   
    public RowListener(Grid rar)  
    {  
        readRow = rar;  
        table = readRow.table;  
    }  
   
    @Override
    public void valueChanged(ListSelectionEvent e)  
    {  
        if(!e.getValueIsAdjusting())  
        {  
            ListSelectionModel model = table.getSelectionModel();  
            int lead = model.getLeadSelectionIndex();  
            displayRowValues(lead);  
        }  
    }  
   
    private void displayRowValues(int rowIndex)  
    {  
        int columns = table.getColumnCount();  
        String s = "";  
        for(int col = 0; col < columns; col++)  
        {  
            Object o = table.getValueAt(rowIndex, col);  
            s += o.toString();  
            if(col < columns - 1)  
                s += ", ";  
        }  
        readRow.label.setText(s);  
    }  
}