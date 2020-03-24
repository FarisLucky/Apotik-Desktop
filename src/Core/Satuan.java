/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import static Core.Koneksi.conn;

import static Core.Koneksi.ps;
import static Core.Koneksi.rs;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Asus
 */
public class Satuan {
    
    private int id_satuan;
    private String Satuan;
    
    
    public Satuan(int id_satuan,String Satuan){
        this.id_satuan = id_satuan;
        this.Satuan = Satuan;
    }
    
    public int getidSatuan(){
        return id_satuan;
    }
    
    public String getSatuan(){
        return Satuan;
    }
    
    public static ArrayList<Satuan> getDataSatuan() throws SQLException{
        conn = Koneksi.conn();
        ps = conn.prepareStatement("select * from satuan_obat");
        
        rs = ps.executeQuery();
        ArrayList<Satuan> array = new ArrayList<Satuan>();
        Satuan sat;
        while(rs.next()){
            sat = new Satuan(rs.getInt("Id_Satuan"), rs.getString("Satuan"));
            array.add(sat);
        }
        return array;
    }
    
    public static void show_satuan(JTable tabel) throws SQLException{
        ArrayList<Satuan> array = getDataSatuan();
        DefaultTableModel model = (DefaultTableModel)tabel.getModel();
        for(int i = model.getRowCount() - 1; i >= 0; i--){
            model.removeRow(i);
        }
        Object[] row = new Object[6];
        for(int i = 0; i < array.size(); i++){
            row[0] = array.get(i).getSatuan();
            model.addRow(row);
        }
    }
    
    public static void insert_akun(JTextField txt_satuanobat) throws SQLException{
        try{
            conn = Koneksi.conn();
            ps = conn.prepareStatement("insert into satuan_obat(Satuan) Values('"+txt_satuanobat.getText()+"')");
            
            ps.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public static void getDataTable(JTable tabel,JTextField txt_satuanobat) throws SQLException{
        try{
            DefaultTableModel model = (DefaultTableModel)tabel.getModel();
            int selectedRowIndex = tabel.getSelectedRow();
            
            txt_satuanobat.setText(model.getValueAt(selectedRowIndex, 0).toString());
        }catch(Exception e){
            System.out.println(e);
        }
        
    }
    
    public static int getidSatuan(JTextField txt_satuanobat){
        try{
            int id_satuan = 0;
            conn = Koneksi.conn();
            ps = conn.prepareStatement("select Id_Satuan from satuan_obat where Satuan = '"+txt_satuanobat.getText()+"'");
            
            rs = ps.executeQuery();
            while(rs.next()){
               id_satuan  = Integer.parseInt(rs.getString("Id_Satuan"));
            }
            return id_satuan;
        }catch(Exception e){
            return 0;
        }
    }
    
    public static void updateAkun(JTextField txt_satuanobat,int id_satuan){
        try{
            conn = Koneksi.conn();
            ps = conn.prepareStatement("update satuan_obat set Satuan = '" + txt_satuanobat.getText()+ "' where Id_Satuan = '"+ id_satuan +"'");
            
            ps.executeUpdate();
        }catch(Exception e){
            System.out.print(e);
        }
    }
    
    public static void hapusAkun(int id_satuan){
        try{
            conn = Koneksi.conn();
            ps = conn.prepareStatement("delete from satuan_obat where Id_Satuan = '" + id_satuan + "'");
        
            ps.executeUpdate();
        }catch(Exception e){
            System.out.print(e);
        }
    }
}
