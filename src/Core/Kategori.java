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
public class Kategori {
    
    private int id_kategori;
    private String Kategori;
    
    
    public Kategori(int id_kategori,String Kategori){
        this.id_kategori = id_kategori;
        this.Kategori = Kategori;
    }
    
    public int getidKategori(){
        return id_kategori;
    }
    
    public String getKategori(){
        return Kategori;
    }
    
    public static ArrayList<Kategori> getDataKategori() throws SQLException{
        conn = Koneksi.conn();
        ps = conn.prepareStatement("select * from kategori_obat");
        
        rs = ps.executeQuery();
        ArrayList<Kategori> array = new ArrayList<Kategori>();
        Kategori kat;
        while(rs.next()){
            kat = new Kategori(rs.getInt("Id_Kategori"), rs.getString("Kategori"));
            array.add(kat);
        }
        return array;
    }
    
    public static void show_kategori(JTable tabel) throws SQLException{
        ArrayList<Kategori> array = getDataKategori();
        DefaultTableModel model = (DefaultTableModel)tabel.getModel();
        for(int i = model.getRowCount() - 1; i >= 0; i--){
            model.removeRow(i);
        }
        Object[] row = new Object[6];
        for(int i = 0; i < array.size(); i++){
            row[0] = array.get(i).getKategori();
            model.addRow(row);
        }
    }
    
    public static void insert_akun(JTextField txt_kategoriobat) throws SQLException{
        try{
            conn = Koneksi.conn();
            ps = conn.prepareStatement("insert into kategori_obat(Kategori) Values('"+txt_kategoriobat.getText()+"')");
            
            ps.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public static void getDataTable(JTable tabel,JTextField txt_kategoriobat) throws SQLException{
        try{
            DefaultTableModel model = (DefaultTableModel)tabel.getModel();
            int selectedRowIndex = tabel.getSelectedRow();
            
            txt_kategoriobat.setText(model.getValueAt(selectedRowIndex, 0).toString());
        }catch(Exception e){
            System.out.println(e);
        }
        
    }
    
    public static int getidKategori(JTextField txt_kategoriobat){
        try{
            int id_kategori = 0;
            conn = Koneksi.conn();
            ps = conn.prepareStatement("select Id_Kategori from kategori_obat where Kategori = '"+txt_kategoriobat.getText()+"'");
            
            rs = ps.executeQuery();
            while(rs.next()){
               id_kategori  = Integer.parseInt(rs.getString("Id_Kategori"));
            }
            return id_kategori;
        }catch(Exception e){
            return 0;
        }
    }
    
    public static void updateAkun(JTextField txt_kategoriobat,int id_kategori){
        try{
            conn = Koneksi.conn();
            ps = conn.prepareStatement("update kategori_obat set Kategori = '" + txt_kategoriobat.getText()+ "' where Id_Kategori = '"+ id_kategori +"'");
            
            ps.executeUpdate();
        }catch(Exception e){
            System.out.print(e);
        }
    }
    
    public static void hapusAkun(int Id_kategori){
        try{
            conn = Koneksi.conn();
            ps = conn.prepareStatement("delete from kategori_obat where Id_Kategori = '" + Id_kategori + "'");
        
            ps.executeUpdate();
        }catch(Exception e){
            System.out.print(e);
        }
    }
}
