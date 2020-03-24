/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import javax.swing.JTable;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Asus
 */
public class Koneksi {
    public static DefaultTableModel model;
    public static Connection conn;
    public static Statement st;
    public static PreparedStatement ps;
    public static ResultSet rs;
    
    public static Connection conn(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/inven","root","");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    public static int getRowCount(String sql1){
        try{
            int count = 0;
            ps = conn.prepareStatement(sql1);
        
            rs = ps.executeQuery();
            if(rs.next()){
                count = Integer.parseInt(rs.getString("count(*)"));
            }
            System.out.print(count);
            return count;
        }catch(Exception e){
            return 0;
        }
    }
    public static void main(String[] args) {
        getRowCount("Select * from dataakun");
    }
}
