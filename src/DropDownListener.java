import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import CarService.DatabaseConnectionService;

public class DropDownListener implements ActionListener {
    DatabaseConnectionService dbService;
    JComboBox dbList;
    JPanel dataPanel;
    

    public DropDownListener(DatabaseConnectionService dbService, JComboBox dbList, JPanel dataPanel) {
        this.dbService = dbService;
        this.dbList = dbList;
        this.dataPanel = dataPanel;
       
        
        
    }


	@Override
	public void actionPerformed(ActionEvent event) {
		JComboBox<String> combo = (JComboBox<String>) event.getSource();
        String tableName = (String) combo.getSelectedItem();
        System.out.println(tableName);
        
        PreparedStatement psmt = null;
		try {
			psmt = this.dbService.getConnection().prepareStatement("SELECT * FROM [" +  tableName + "]");
		} catch (SQLException exception) {
			// TODO Auto-generated catch-block stub.
			exception.printStackTrace();
		}
		ResultSet rs = null;
		try {
			rs = psmt.executeQuery();
		} catch (SQLException exception) {
			// TODO Auto-generated catch-block stub.
			exception.printStackTrace();
		}

		// get column names
		int len = 0;
		try {
			len = rs.getMetaData().getColumnCount();
		} catch (SQLException exception) {
			// TODO Auto-generated catch-block stub.
			exception.printStackTrace();
		}
		Vector cols= new Vector(len);
		for(int i=1; i<=len; i++)
			try {
				cols.add(rs.getMetaData().getColumnName(i));
			} catch (SQLException exception) {
				// TODO Auto-generated catch-block stub.
				exception.printStackTrace();
			}


		// Add Data
		Vector data = new Vector();
		try {
			while(rs.next())
			{
			    Vector row = new Vector(len);
			    for(int i=1; i<=len; i++)
			    {
			        row.add(rs.getString(i));
			    }
			    data.add(row);
			}
		} catch (SQLException exception) {
			// TODO Auto-generated catch-block stub.
			exception.printStackTrace();
		}

		// Now create the table
		JTable table = new JTable(data, cols);
		table.setFillsViewportHeight(true);
		JScrollPane scrollTable = new JScrollPane(table);
		scrollTable.setSize(1000, 750);
		
		dataPanel.removeAll();
		dataPanel.add(scrollTable, BorderLayout.CENTER);
		dataPanel.revalidate();
		dataPanel.repaint();
        
      
    }

		
}
