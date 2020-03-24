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
public class Supplier {
    
    private int id_supplier;
    private String nama_pbf,alamat,fax,email,nopbf,telp;
    int id;
    
    public Supplier(int id_supplier,String nopbf, String nama_pbf, String alamat, String telp, String fax, String email){
        this.id_supplier = id_supplier;
        this.nopbf = nopbf;
        this.nama_pbf = nama_pbf;
        this.alamat = alamat;
        this.telp = telp;
        this.fax = fax;
        this.email = email;
    }
    
    public int getidSupplier(){
        return id_supplier;
    }
    
    public String getnoPbf(){
        return nopbf;
    }
    
    public String getnamaPbf(){
        return nama_pbf;
    }
    
    public String getAlamat(){
        return alamat;
    }
    
    public String getTelp(){
        return telp;
    }
    
    public String getFax(){
        return fax;
    }
    
    public String getEmail(){
        return email;
    }
    
    public static ArrayList<Supplier> getDataSupplier() throws SQLException{
        conn = Koneksi.conn();
        ps = conn.prepareStatement("select * from supplier");
        
        rs = ps.executeQuery();
        ArrayList<Supplier> array = new ArrayList<Supplier>();
        Supplier supplier;
        while(rs.next()){
            supplier = new Supplier(rs.getInt("Id_Supplier"), rs.getString("No_PBF"), rs.getString("Nama_PBF"), rs.getString("Alamat"), rs.getString("Telp"), rs.getString("Fax"), rs.getString("Email"));
            array.add(supplier);
        }
        return array;
    }
    
    public static void show_supplier(JTable table) throws SQLException{
        ArrayList<Supplier> array = getDataSupplier();
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        for(int i = model.getRowCount() - 1; i >= 0; i--){
            model.removeRow(i);
        }
        Object[] row = new Object[6];
        for(int i = 0; i < array.size(); i++){
            row[0] = array.get(i).getnoPbf();
            row[1] = array.get(i).getnamaPbf();
            row[2] = array.get(i).getAlamat();
            row[3] = array.get(i).getTelp();
            row[4] = array.get(i).getFax();
            row[5] = array.get(i).getEmail();
            model.addRow(row);
        }
    }
    
    public static void insert_supplier(JTextField no_pbf ,JTextField nama_pbf ,JTextField alamat_pbf ,JTextField telp_pbf ,JTextField fax_pbf ,JTextField email_pbf) throws SQLException{
        try{
            conn = Koneksi.conn();
            ps = conn.prepareStatement("insert into supplier(No_PBF,Nama_PBF,Alamat,Telp,Fax,Email) Values('"+no_pbf.getText()+"', '"+nama_pbf.getText()+"', '"+alamat_pbf.getText()+"', '"+telp_pbf.getText()+"', '"+fax_pbf.getText()+"', '"+email_pbf.getText()+"')");
            
            ps.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public static void getDataTable(JTable table,JTextField no_pbf ,JTextField nama_pbf ,JTextField alamat_pbf ,JTextField telp_pbf ,JTextField fax_pbf ,JTextField email_pbf) throws SQLException{
        try{
            DefaultTableModel model = (DefaultTableModel)table.getModel();
            int selectedRowIndex = table.getSelectedRow();
            
            no_pbf.setText(model.getValueAt(selectedRowIndex, 0).toString());
            nama_pbf.setText(model.getValueAt(selectedRowIndex, 1).toString());
            alamat_pbf.setText(model.getValueAt(selectedRowIndex, 2).toString());
            telp_pbf.setText(model.getValueAt(selectedRowIndex, 3).toString());
            fax_pbf.setText(model.getValueAt(selectedRowIndex, 4).toString());
            email_pbf.setText(model.getValueAt(selectedRowIndex, 5).toString());
        }catch(Exception e){
            System.out.println(e);
        }
        
    }
    
    public static int getIdSupplier(JTextField no_pbf ,JTextField nama_pbf){
        try{
            int Id_Supplier = 0;
            conn = Koneksi.conn();
            ps = conn.prepareStatement("select Id_Supplier from supplier where No_PBF = '"+no_pbf.getText()+"' and Nama_PBF = '" + nama_pbf.getText() + "'");
            
            rs = ps.executeQuery();
            while(rs.next()){
               Id_Supplier  = Integer.parseInt(rs.getString("Id_Supplier"));
            }
            return Id_Supplier;
        }catch(Exception e){
            return 0;
        }
    }
    
    public static void updateSupplier(JTextField no_pbf ,JTextField nama_pbf ,JTextField alamat_pbf ,JTextField telp_pbf ,JTextField fax_pbf ,JTextField email_pbf, int Id_Supplier){
        try{
            conn = Koneksi.conn();
            ps = conn.prepareStatement("update supplier set No_PBF = '" + no_pbf.getText()+ "', Nama_PBF = '" + nama_pbf.getText()+ "', Alamat = '" + alamat_pbf.getText() + "', Telp = '" + telp_pbf.getText() + "', Fax = '" + fax_pbf.getText() + "', Email = '" + email_pbf.getText() + "' where Id_Supplier = '"+ Id_Supplier +"'");
            
            ps.executeUpdate();
        }catch(Exception e){
            System.out.print(e);
        }
    }
    
    public static void hapusSupplier(int Id_Supplier){
        try{
            conn = Koneksi.conn();
            ps = conn.prepareStatement("delete from supplier where Id_Supplier = '" + Id_Supplier + "'");
        
            ps.executeUpdate();
        }catch(Exception e){
            System.out.print(e);
        }
    }
}
