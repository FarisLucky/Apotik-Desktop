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
public class Akun {
    
    private int id_Akun;
    private String Username,Password,Nama,Jabatan;
    int id;
    
    public Akun(int id_Akun,String Username, String Password, String Nama, String Jabatan){
        this.id_Akun = id_Akun;
        this.Username = Username;
        this.Password = Password;
        this.Nama = Nama;
        this.Jabatan = Jabatan;
    }

    
    public int getidAkun(){
        return id_Akun;
    }
    public String getUsername(){
        return Username;
    }
    
    public String getPassword(){
        return Password;
    }
    
    public String getNama(){
        return Nama;
    }
    
    public String getJabatan(){
        return Jabatan;
    }
    public static ArrayList<Akun> getDataAkun() throws SQLException{
        conn = Koneksi.conn();
        ps = conn.prepareStatement("select * from akun");
        
        rs = ps.executeQuery();
        ArrayList<Akun> array = new ArrayList<Akun>();
        Akun akun;
        while(rs.next()){
            akun = new Akun(rs.getInt("Id_Akun"), rs.getString("Username"), rs.getString("Password"), rs.getString("Nama"), rs.getString("Jabatan"));
            array.add(akun);
        }
        return array;
    }
    
    public static void show_akun(JTable tabel) throws SQLException{
        ArrayList<Akun> array = getDataAkun();
        DefaultTableModel model = (DefaultTableModel)tabel.getModel();
        for(int i = model.getRowCount() - 1; i >= 0; i--){
            model.removeRow(i);
        }
        Object[] row = new Object[6];
        for(int i = 0; i < array.size(); i++){
            row[0] = array.get(i).getUsername();
            row[1] = array.get(i).getPassword();
            row[2] = array.get(i).getNama();
            row[3] = array.get(i).getJabatan();
            model.addRow(row);
        }
    }
    
    public static void insert_akun(JTextField txt_usernameda ,JTextField txt_passwordda ,JTextField txt_namada ,JTextField txt_jabatan) throws SQLException{
        try{
            conn = Koneksi.conn();
            ps = conn.prepareStatement("insert into akun(Username,Password,Nama,Jabatan) Values('"+txt_usernameda.getText()+"','"+txt_passwordda.getText()+"','"+txt_namada.getText()+"', '"+txt_jabatan.getText()+"')");
            
            ps.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public static void getDataTable(JTable tabel,JTextField txt_usernameda ,JTextField txt_passwordda ,JTextField txt_namada ,JTextField txt_jabatanda) throws SQLException{
        try{
            DefaultTableModel model = (DefaultTableModel)tabel.getModel();
            int selectedRowIndex = tabel.getSelectedRow();
            
            txt_usernameda.setText(model.getValueAt(selectedRowIndex, 0).toString());
            txt_passwordda.setText(model.getValueAt(selectedRowIndex, 1).toString());
            txt_namada.setText(model.getValueAt(selectedRowIndex, 2).toString());
            txt_jabatanda.setText(model.getValueAt(selectedRowIndex, 3).toString());
        }catch(Exception e){
            System.out.println(e);
        }
        
    }
    
    public static int getidAkun(JTextField txt_usernameda ,JTextField txt_passwordda){
        try{
            int Id_Akun = 0;
            conn = Koneksi.conn();
            ps = conn.prepareStatement("select Id_Akun from akun where Username = '"+txt_usernameda.getText()+"' and Password = '" + txt_passwordda.getText() + "'");
            
            rs = ps.executeQuery();
            while(rs.next()){
               Id_Akun  = Integer.parseInt(rs.getString("Id_Akun"));
            }
            return Id_Akun;
        }catch(Exception e){
            return 0;
        }
    }
    
    public static void updateAkun(JTextField txt_usernameda ,JTextField txt_passwordda ,JTextField txt_namada ,JTextField txt_jabatanda ,int Id_Akun){
        try{
            conn = Koneksi.conn();
            ps = conn.prepareStatement("update akun set Username = '" + txt_usernameda.getText()+ "', Password = '" + txt_passwordda.getText()+ "', Nama = '" + txt_namada.getText() + "', Jabatan = '" + txt_jabatanda.getText() + "' where Id_Akun = '"+ Id_Akun +"'");
            
            ps.executeUpdate();
        }catch(Exception e){
            System.out.print(e);
        }
    }
    
    public static void hapusAkun(int Id_Akun){
        try{
            conn = Koneksi.conn();
            ps = conn.prepareStatement("delete from akun where Id_Akun = '" + Id_Akun + "'");
        
            ps.executeUpdate();
        }catch(Exception e){
            System.out.print(e);
        }
    }
}
