package org.example.Model;

import org.example.TransactionHistoryGUI;

import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class TransactionTableModel extends AbstractTableModel {
    private ResultSet rs;
    private ResultSetMetaData rsmd;
    private int rowCount;


    public TransactionTableModel(ResultSet rs) throws SQLException {
        this.rs = rs;
        this.rsmd = rs.getMetaData();

        rs.last();
        this.rowCount = rs.getRow();
        rs.beforeFirst();

    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public int getColumnCount() {
        try {
            return rsmd.getColumnCount();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            rs.absolute(rowIndex + 1);
            return rs.getObject(columnIndex + 1);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        try {
            return rsmd.getColumnName(columnIndex + 1);  // Get column name from metadata
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
