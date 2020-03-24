
 /* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import static Core.Koneksi.conn;
import static Core.Koneksi.ps;
import static Core.Koneksi.rs;
import com.toedter.calendar.JDateChooser;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author Asus
 */
public class Pembelian_Obat {
    
    public static int id_pembelian,selectedRowIndex = -1,Id_Kategori,Id_Satuan;
    
    public static void insertToJtablePembelian(JTable table,JTextField no_batch, JTextField nama_obat, JComboBox Kategori, JComboBox Satuan, JTextField harga_beli, JDateChooser expired_date, JTextField stock){
        SimpleDateFormat dFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String date = dFormat.format(expired_date.getDate());
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        model.addRow(new Object[]{no_batch.getText(), nama_obat.getText(), Kategori.getSelectedItem().toString(), Satuan.getSelectedItem().toString(), harga_beli.getText(), date, stock.getText()});
    }
    
    public static void getDataTable(JTable table, JTextField no_batch, JTextField nama_obat, JComboBox kategori, JComboBox satuan, JTextField harga_beli, JDateChooser expired_date, JTextField quantity){
        try{
            SimpleDateFormat dFormat = new SimpleDateFormat("dd-MMM-yyyy");
            DefaultTableModel model = (DefaultTableModel)table.getModel();
            selectedRowIndex = table.getSelectedRow();
            
            no_batch.setText(model.getValueAt(selectedRowIndex, 0).toString());
            nama_obat.setText(model.getValueAt(selectedRowIndex, 1).toString());
            kategori.setSelectedItem(model.getValueAt(selectedRowIndex, 2).toString());
            satuan.setSelectedItem(model.getValueAt(selectedRowIndex, 3).toString());
            harga_beli.setText(model.getValueAt(selectedRowIndex, 4).toString());        
            expired_date.setDate(dFormat.parse((String)model.getValueAt(selectedRowIndex, 5)));
            quantity.setText(model.getValueAt(selectedRowIndex, 6).toString());
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public static void removeRowTable(JTable table){
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        int selectedRowIndex = table.getSelectedRow();
        
        model.removeRow(selectedRowIndex);
    }
    
    public static void updateRowTable(JTable table, JTextField no_batch, JTextField nama_obat, JComboBox kategori, JComboBox satuan, JTextField harga_beli, JDateChooser expired_date, JTextField quantity){
        try{
            SimpleDateFormat dFormat = new SimpleDateFormat("dd-MMM-yyyy");
            String date = dFormat.format(expired_date.getDate());
            DefaultTableModel model = (DefaultTableModel)table.getModel();
            selectedRowIndex = table.getSelectedRow();
            
            model.setValueAt(no_batch.getText(), selectedRowIndex, 0);
            model.setValueAt(nama_obat.getText(), selectedRowIndex, 1);
            model.setValueAt(kategori.getSelectedItem().toString(), selectedRowIndex, 2);
            model.setValueAt(satuan.getSelectedItem().toString(), selectedRowIndex, 3);
            model.setValueAt(harga_beli.getText(), selectedRowIndex, 4);
            model.setValueAt(date, selectedRowIndex, 5);
            model.setValueAt(quantity.getText(), selectedRowIndex, 6);
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public static void insert_pembelian(JTextField txt_nofakturpb ,JDateChooser date_transaksi ,JDateChooser date_jatuhtempo ,JComboBox cmb_supplier) throws SQLException{
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
        try{
            conn = Koneksi.conn();
            ps = conn.prepareStatement("insert into pembelian(No_Faktur,Tgl_Transaksi,Tgl_JatuhTempo,Id_Supplier,Id_Akun) Values('"+txt_nofakturpb.getText()+"', '"+ dFormat.format(date_transaksi.getDate())+"', '"+dFormat.format(date_jatuhtempo.getDate())+"', '"+ getIdSupplier(cmb_supplier) +"','" + SessionLogin.getIdAkun() + "')");
            
            ps.executeUpdate();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public static void insertObat(JTable table) throws Exception{
        String No_Batch,Nama_Obat,Expired;
        int id_kategori,id_satuan,Stock,Harga_Beli;
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        
        for(int i = 0; i < table.getRowCount(); i++){
            No_Batch = model.getValueAt(i, 0).toString();
            Nama_Obat = model.getValueAt(i, 1).toString();
            id_kategori = getIdKategori(model.getValueAt(i, 2).toString());
            id_satuan = getIdSatuan(model.getValueAt(i, 3).toString());
            Stock = Integer.parseInt(model.getValueAt(i, 4).toString());
            Harga_Beli = Integer.parseInt(model.getValueAt(i, 5).toString());
            Expired = dFormat.format(model.getValueAt(i, 6).toString());
            try{
            conn = Koneksi.conn();
            ps = conn.prepareStatement("insert into obat(No_Batch,Nama_Obat,Id_Kategori,Id_Satuan,Stock,Harga_beli,Expired) values('" + No_Batch + "' ,'" + Nama_Obat + "' ,'" + id_kategori + "' ,'" + id_satuan + "' ,'" + Stock + "' ,'" + Harga_Beli + "' ,'" + Expired + "')");
            
            ps.executeUpdate();
            
            insertDetilObat(getIdPembelian(), No_Batch, Harga_Beli, Stock);
        }catch(Exception e){
            
        }
        }
    }
    
    public static void insertDetilObat(int Id_Pembelian, String No_Batch, int Harga_Beli,int quantity) throws Exception{
        try{
            conn = Koneksi.conn();
            ps = conn.prepareStatement("insert into detail_pembelian(Id_Pembelian, No_Batch, Harga_beli, quantity) values('" + Id_Pembelian + "' ,'" + No_Batch + " ,'" + Harga_Beli + " ,'" + quantity + ")");
            
            ps.executeUpdate();
        }catch(Exception e){
            
        }
    }
    
    public static int getIdPembelian(){
        try{
            conn = Koneksi.conn();
            ps = conn.prepareStatement("select MAX(Id_Pembelian) AS Id from pembelian");
        
            rs = ps.executeQuery();
            id_pembelian = Integer.parseInt(rs.getString("Id"));
            return id_pembelian;
        }catch(Exception e){
            return 0;
        }
    }
    
    public static int getIdSupplier(JComboBox supplier){
        try{
            int Id_Supplier = 0;
            conn = Koneksi.conn();
            ps = conn.prepareStatement("select Id_Supplier from supplier where Nama_PBF = '" + supplier.getSelectedItem().toString() + "'");
            
            rs = ps.executeQuery();
            while(rs.next()){
               Id_Supplier  = Integer.parseInt(rs.getString("Id_Supplier"));
            }
            return Id_Supplier;
        }catch(Exception e){
            return 0;
        }
    }
    
    public static int getIdKategori(String kategori){
        try{
            int Id_Kategori = 0;
            conn = Koneksi.conn();
            ps = conn.prepareStatement("select Id_Kategori from kategori_obat where Kategori = '" + kategori + "'");
            
            rs = ps.executeQuery();
            while(rs.next()){
               Id_Kategori  = Integer.parseInt(rs.getString("Id_Kategori"));
            }
            return Id_Kategori;
        }catch(Exception e){
            return 0;
        }
    }
    
    public static int getIdSatuan(String satuan){
        try{
            int Id_Satuan = 0;
            conn = Koneksi.conn();
            ps = conn.prepareStatement("select Id_Satuan from satuan_obat where Satuan = '" + satuan + "'");
            
            rs = ps.executeQuery();
            while(rs.next()){
               Id_Satuan  = Integer.parseInt(rs.getString("Id_Satuan"));
            }
            return Id_Satuan;
        }catch(Exception e){
            return 0;
        }
    }

    public static void comboBox(JComboBox cmb_satuan){
        try {
            conn = Koneksi.conn();
            ps = conn.prepareStatement("Select Satuan from satuan_obat");
            rs = ps.executeQuery();
            while (rs.next()) {                
                cmb_satuan.addItem(rs.getString("Satuan"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Pembelian_Obat.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    public static void comboBoxKategori(JComboBox cmb_kategori){
        try {
            conn = Koneksi.conn();
            ps = conn.prepareStatement("Select Kategori from kategori_obat");
            rs = ps.executeQuery();
            while (rs.next()) {                
                cmb_kategori.addItem(rs.getString("Kategori"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Pembelian_Obat.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
    public static void comboBoxSupplier(JComboBox cmb_supplier){
        try {
            conn = Koneksi.conn();
            ps = conn.prepareStatement("Select Nama_PBF from supplier");
            rs = ps.executeQuery();
            while (rs.next()) {                
                Object[] combo = new Object[3];
                combo[0] = rs.getString("Nama_PBF");
                cmb_supplier.addItem(combo[0]);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Pembelian_Obat.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
}