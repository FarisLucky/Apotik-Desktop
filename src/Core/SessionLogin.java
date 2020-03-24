/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import Form.DashboardAdmin;
import Form.DashboardGudang;
import Form.DashboardStokies;
import Form.FrmLogin;
import static java.awt.image.ImageObserver.WIDTH;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author FarisX
 */
public class SessionLogin {
   public static Connection conn;
   public static Statement st;
   public static ResultSet rs;
   public static int id_Akun;
   public static String Username,Password;
    
   
    public static void SetIdAkun(int id_akun){
        SessionLogin.id_Akun = id_akun;
    }
    public static int getIdAkun(){
        return id_Akun;
    }
    public static void setUser(String Username){
        SessionLogin.Username = Username;
    }
    public static String getUser(){
        return Username;
    }
    
    public static void loginSession(JTextField username, JTextField password){
        try{
            Username = username.getText();
            Password = password.getText();
            String sql = "select * from akun where Username = '"+Username+"' and Password = '"+Password+"'";
            
            conn = Koneksi.conn();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            
            while(rs.next()){
                setUser(rs.getString("Nama"));
                SetIdAkun(Integer.parseInt(rs.getString("Id_Akun")));
            }
            rs.last();
            if(rs.getRow() == 0){
                JOptionPane.showMessageDialog(null, "Akun Tidak Ditemukan");
            }
            else{
                if(rs.getString("Jabatan").equals("Admin")){
                    DashboardAdmin da = new DashboardAdmin();
                    da.setVisible(true);
                }
                else if(rs.getString("Jabatan").equals("Gudang")){
                    DashboardGudang dg = new DashboardGudang();
                    dg.setVisible(true);
                }
                else{
                    DashboardStokies ds = new DashboardStokies();
                    ds.setVisible(true);
                }
                FrmLogin fl = new FrmLogin();
                fl.dispose();
            }
        }catch(Exception e){
            System.out.print(e.getMessage());
        }
    }
}
