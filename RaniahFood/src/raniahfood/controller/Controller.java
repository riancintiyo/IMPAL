/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raniahfood.controller;
import raniahfood.database.Database;
import raniahfood.view.VLogin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import raniahfood.model.*; 
import raniahfood.view.*; 
import java.awt.HeadlessException; 
import raniahfood.view.VKoki;

import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.sql.SQLException; 
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane; 
import javax.swing.JPanel; 
import javax.swing.JPasswordField;



/**
 *
 * @author Asus
 */
public class Controller implements ActionListener{
    private Database db;
    private raniahfood model;
    private String currentView;
    private JPanel mainPanel;
    private VLogin viewlogin;
    private VKoki viewkoki;
    private VAdmin viewadmin;
    
    public Controller(raniahfood model,Database db) throws SQLException {
        this.model = model; 
        this.db = db;
        
        viewlogin = new VLogin();
        viewlogin.setVisible(true);
        viewlogin.pack();
        viewlogin.setLocationRelativeTo(null);
        viewlogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewkoki = new VKoki();
        viewadmin = new VAdmin();
        viewlogin.addActionListener(this);
        viewkoki.addActionListener(this);
        viewadmin.addActionListener(this);
//        mainPanel = view.getMainPanel();
//        mainPanel.add(viewlogin,"0");
//        mainPanel.add(viewkoki,"1");
//        
//        currentView = "0";
//        view.getCardLayout().show(mainPanel, currentView); 
//        view.setVisible(true); 
//        view.addListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object a = e.getSource();
        if (a == viewlogin.getjButton1()) { //login
            String user = viewlogin.getjTextField1();
            String pass = viewlogin.getjTextField2();
            //System.out.println(user + pass);
            
            try {
                String role = model.cekLogin(user, pass); //cek username password
                if (role!="") {
                    if (role.equals("koki")) {
                        viewkoki.setVisible(true);
                        viewkoki.setLocationRelativeTo(null);
                        viewlogin.setVisible(false);
                    }
                    if (role.equals("admin")) {
                        viewadmin.setVisible(true);
                        viewadmin.setLocationRelativeTo(null);
                        viewlogin.setVisible(false);
                    }
//                    viewkoki = new VKoki();

                }else {
                    JOptionPane.showConfirmDialog(viewlogin, "Anda belum terdaftar", "Login Gagal", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception ee) {
                ee.printStackTrace();//penting
                JOptionPane.showConfirmDialog(viewlogin, ""+ee.getMessage(), ""+ee.getMessage(), JOptionPane.WARNING_MESSAGE);
            }
        } 
        if (a == viewkoki.getjButton2()){ //insertbahan
            if (viewkoki.getjTextField1().isEmpty() || viewkoki.getjTextField2().isEmpty() || viewkoki.getjTextField7().isEmpty()){
                JOptionPane.showConfirmDialog(null, "Pastikan Form Sudah di isi semua","Input Error",JOptionPane.DEFAULT_OPTION);
            } else {
                try { 
                // checking valid integer using parseInt() method 
                    String nama = viewkoki.getjTextField1();
                    String stock = viewkoki.getjTextField2();
                    String keterangan = viewkoki.getjTextField7();
                    int angka = Integer.parseInt(stock);
                    model.addBahan(nama, angka, keterangan);
                } catch (NumberFormatException ne) { 
                    JOptionPane.showConfirmDialog(null, "Pastikan Stock dalam bentuk angka","Input Error",JOptionPane.DEFAULT_OPTION);
                }
            }
        }
        if (a == viewkoki.getjButton1()){ //cektabelbahan
            try{
                List<Bahan> bahan=new ArrayList<>();
                bahan = model.listBahan();
                if(bahan==null) {
                    viewkoki.setDataBahan(bahan);
                    JOptionPane.showConfirmDialog(null, "Data Bahan Kosong","Refresh",JOptionPane.DEFAULT_OPTION);
                } else {
                    viewkoki.setDataBahan(bahan);
                    JOptionPane.showConfirmDialog(null, "Data Sudah Di Update","Refresh",JOptionPane.DEFAULT_OPTION);
                }
            } catch (Exception ee) {
                ee.printStackTrace();//penting
                JOptionPane.showConfirmDialog(viewlogin, ""+ee.getMessage(), ""+ee.getMessage(), JOptionPane.WARNING_MESSAGE);
            }
        }
        if (a == viewkoki.getjButton3()){ //deletebahan
            if (viewkoki.getjTextField3().isEmpty()){
                JOptionPane.showConfirmDialog(null, "Pastikan Form Sudah di isi semua","Delete Error",JOptionPane.DEFAULT_OPTION);
            } else {
                try { 
                    String nama = viewkoki.getjTextField3();
                    model.deleteBahan(nama);
                } catch (NumberFormatException ne) { 
                    JOptionPane.showConfirmDialog(null, "Pastikan Stock dalam bentuk angka","Input Error",JOptionPane.DEFAULT_OPTION);
                }
            }
        }
        if (a == viewkoki.getjButton4()){ //logout
            try {
                viewlogin.setVisible(true);
                viewlogin.setLocationRelativeTo(null);
                viewkoki.setVisible(false);
            } catch (Exception ee) {
                ee.printStackTrace();//penting
                JOptionPane.showConfirmDialog(viewkoki, ""+ee.getMessage(), ""+ee.getMessage(), JOptionPane.WARNING_MESSAGE);
            }
        }
        if (a == viewkoki.getjButton5()){ //updatebahan
            if (viewkoki.getjTextField5().isEmpty() || viewkoki.getjTextField6().isEmpty() || viewkoki.getjTextField8().isEmpty()){
                JOptionPane.showConfirmDialog(null, "Pastikan Form Sudah di isi semua","Update Error",JOptionPane.DEFAULT_OPTION);
            } else {
                try { 
                    String nama = viewkoki.getjTextField5();
                    String stok = viewkoki.getjTextField6();
                    String keterangan = viewkoki.getjTextField8();
                    Bahan bahan = model.cekBahan(nama);
                    if (bahan == null){
                        JOptionPane.showConfirmDialog(null, "Bahan Tidak Ditemukan","Update Error",JOptionPane.DEFAULT_OPTION);
                    } else {
                        model.updateBahan(nama, Integer.parseInt(stok),keterangan);
                    }
                } catch (NumberFormatException ne) { 
                    JOptionPane.showConfirmDialog(null, "Pastikan Stock dalam bentuk angka","Update Error",JOptionPane.DEFAULT_OPTION);
                }
            }
        }
        if (a == viewkoki.getjButton6()){ //cekupdatebahan
            if (viewkoki.getjTextField5().isEmpty()){
                JOptionPane.showConfirmDialog(null, "Pastikan Form Sudah di isi semua","Update Error",JOptionPane.DEFAULT_OPTION);
            } else {
                try { 
                    String nama = viewkoki.getjTextField5();
                    Bahan bahan = model.cekBahan(nama);
                    if (bahan == null){
                        JOptionPane.showConfirmDialog(null, "Bahan Tidak Ditemukan","Update Error",JOptionPane.DEFAULT_OPTION);
                    } else {
                        viewkoki.setjTextField5(bahan.getNama_bahan());
                        viewkoki.setjTextField6(Integer.toString(bahan.getStok_bahan()));
                        viewkoki.setjTextField8(bahan.getKeterangan());
                    }
                } catch (NumberFormatException ne) { 
                    JOptionPane.showConfirmDialog(null, "Pastikan Stock dalam bentuk angka","Update Error",JOptionPane.DEFAULT_OPTION);
                }
            }
        }        
        //sini kebawah produk
        if (a == viewkoki.getjButton10()){ //insertproduk
            if (viewkoki.getjTextField12().isEmpty() || viewkoki.getjTextField13().isEmpty()){
                JOptionPane.showConfirmDialog(null, "Pastikan Form Sudah di isi semua","Input Error",JOptionPane.DEFAULT_OPTION);
            } else {
                try { 
                // checking valid integer using parseInt() method 
                    String nama = viewkoki.getjTextField12();
                    String stock = viewkoki.getjTextField13();
                    int angka = Integer.parseInt(stock);
                    model.addProduk(nama, angka);
                } catch (NumberFormatException ne) { 
                    JOptionPane.showConfirmDialog(null, "Pastikan Stock dalam bentuk angka","Input Error",JOptionPane.DEFAULT_OPTION);
                }
            }
        } 
        if (a == viewkoki.getjButton11()){ //cektabelproduk
            try{
                List<Produk> produk=new ArrayList<>();
                produk = model.listProduk();
                if(produk==null) {
                    viewkoki.setDataProduk(produk);
                    JOptionPane.showConfirmDialog(null, "Data Produk Kosong","Refresh",JOptionPane.DEFAULT_OPTION);
                } else {
                    viewkoki.setDataProduk(produk);
                    JOptionPane.showConfirmDialog(null, "Data Sudah Di Update","Refresh",JOptionPane.DEFAULT_OPTION);
                }
            } catch (Exception ee) {
                ee.printStackTrace();//penting
                JOptionPane.showConfirmDialog(viewlogin, ""+ee.getMessage(), ""+ee.getMessage(), JOptionPane.WARNING_MESSAGE);
            }
        }
        if (a == viewkoki.getjButton9()){ //deleteproduk
            if (viewkoki.getjTextField4().isEmpty()){
                JOptionPane.showConfirmDialog(null, "Pastikan Form Sudah di isi semua","Delete Error",JOptionPane.DEFAULT_OPTION);
            } else {
                try { 
                    String nama = viewkoki.getjTextField4();
                    model.deleteProduk(nama);
                } catch (NumberFormatException ne) { 
                    JOptionPane.showConfirmDialog(null, "Pastikan Stock dalam bentuk angka","Input Error",JOptionPane.DEFAULT_OPTION);
                }
            }
        }
        if (a == viewkoki.getjButton7()){ //updateproduk
            if (viewkoki.getjTextField9().isEmpty() || viewkoki.getjTextField10().isEmpty()){
                JOptionPane.showConfirmDialog(null, "Pastikan Form Sudah di isi semua","Update Error",JOptionPane.DEFAULT_OPTION);
            } else {
                try { 
                    String nama = viewkoki.getjTextField9();
                    String stok = viewkoki.getjTextField10();
                    Produk produk = model.cekProduk(nama);
                    if (produk == null){
                        JOptionPane.showConfirmDialog(null, "Produk Tidak Ditemukan","Update Error",JOptionPane.DEFAULT_OPTION);
                    } else {
                        model.updateProduk(nama, Integer.parseInt(stok));
                    }
                } catch (NumberFormatException ne) { 
                    JOptionPane.showConfirmDialog(null, "Pastikan Stock dalam bentuk angka","Update Error",JOptionPane.DEFAULT_OPTION);
                }
            }
        }
        if (a == viewkoki.getjButton8()){ //cekupdateproduk
            if (viewkoki.getjTextField9().isEmpty()){
                JOptionPane.showConfirmDialog(null, "Pastikan Form Sudah di isi semua","Update Error",JOptionPane.DEFAULT_OPTION);
            } else {
                try { 
                    String nama = viewkoki.getjTextField9();
                    Produk produk = model.cekProduk(nama);
                    if (produk == null){
                        JOptionPane.showConfirmDialog(null, "Produk Tidak Ditemukan","Update Error",JOptionPane.DEFAULT_OPTION);
                    } else {
                        viewkoki.setjTextField9(produk.getNama_produk());
                        viewkoki.setjTextField10(Integer.toString(produk.getStok_produk()));
                    }
                } catch (NumberFormatException ne) { 
                    JOptionPane.showConfirmDialog(null, "Pastikan Stock dalam bentuk angka","Update Error",JOptionPane.DEFAULT_OPTION);
                }
            }
        }
        //Sini kebawah karyawan
        if (a == viewadmin.getjButton1()){ //logout
            try {
                viewlogin.setVisible(true);
                viewlogin.setLocationRelativeTo(null);
                viewadmin.setVisible(false);
            } catch (Exception ee) {
                ee.printStackTrace();//penting
                JOptionPane.showConfirmDialog(viewadmin, ""+ee.getMessage(), ""+ee.getMessage(), JOptionPane.WARNING_MESSAGE);
            }
        }
        if (a == viewadmin.getjButton2()){ //insertkaryawan
            if (viewadmin.getjTextField1().isEmpty() || viewadmin.getjTextField2().isEmpty() || viewadmin.getjPasswordField1().isEmpty()){
                JOptionPane.showConfirmDialog(null, "Pastikan Form Sudah di isi semua","Input Error",JOptionPane.DEFAULT_OPTION);
            } else {
                try { 
                // checking valid integer using parseInt() method 
                    String username = viewadmin.getjTextField1();
                    String nama = viewadmin.getjTextField2();
                    String password = viewadmin.getjPasswordField1();
                    String role = viewadmin.getjComboBox1();
                    model.addKaryawan(username,nama, password,role);
                } catch (Exception ee) { 
                    JOptionPane.showConfirmDialog(null, e,"Input Error",JOptionPane.DEFAULT_OPTION);
                }
            }
        } 
        if (a == viewadmin.getjButton6()){ //cektabelkaryawan
            try{
                List<MKaryawan> karyawan=new ArrayList<>();
                karyawan = model.listKaryawan();
                if(karyawan==null) {
                    viewadmin.setDataKaryawan(karyawan);
                    JOptionPane.showConfirmDialog(null, "Data Produk Kosong","Refresh",JOptionPane.DEFAULT_OPTION);
                } else {
                    viewadmin.setDataKaryawan(karyawan);
                    JOptionPane.showConfirmDialog(null, "Data Sudah Di Update","Refresh",JOptionPane.DEFAULT_OPTION);
                }
            } catch (Exception ee) {
                ee.printStackTrace();//penting
                JOptionPane.showConfirmDialog(viewlogin, ""+ee.getMessage(), ""+ee.getMessage(), JOptionPane.WARNING_MESSAGE);
            }
        }
        if (a == viewadmin.getjButton5()){ //deletekaryawan
            if (viewadmin.getjTextField5().isEmpty()){
                JOptionPane.showConfirmDialog(null, "Pastikan Form Sudah di isi semua","Delete Error",JOptionPane.DEFAULT_OPTION);
            } else {
                try { 
                    String username = viewadmin.getjTextField5();
                    model.deleteKaryawan(username);
                } catch (Exception ee) { 
                    JOptionPane.showConfirmDialog(null, ee,"Input Error",JOptionPane.DEFAULT_OPTION);
                }
            }
        }
        if (a == viewadmin.getjButton3()){ //updateproduk
            if (viewadmin.getjTextField3().isEmpty() || viewadmin.getjTextField4().isEmpty() || viewadmin.getjPasswordField2().isEmpty()){
                JOptionPane.showConfirmDialog(null, "Pastikan Form Sudah di isi semua","Update Error",JOptionPane.DEFAULT_OPTION);
            } else {
                try { 
                    String username = viewadmin.getjTextField3();
                    String nama = viewadmin.getjTextField4();
                    String password = viewadmin.getjPasswordField2();
                    String role = viewadmin.getjComboBox2();
                    MKaryawan karyawan = model.cekKaryawan(username);
                    if (karyawan == null){
                        JOptionPane.showConfirmDialog(null, "Karyawan Tidak Ditemukan","Update Error",JOptionPane.DEFAULT_OPTION);
                    } else {
                        model.updateKaryawan(username,nama, password,role);
                    }
                } catch (Exception ee) { 
                    JOptionPane.showConfirmDialog(null, e,"Update Error",JOptionPane.DEFAULT_OPTION);
                }
            }
        }
        if (a == viewadmin.getjButton4()){ //cekupdateproduk
            if (viewadmin.getjTextField3().isEmpty()){
                JOptionPane.showConfirmDialog(null, "Pastikan Form Sudah di isi semua","Update Error",JOptionPane.DEFAULT_OPTION);
            } else {
                try { 
                    String username = viewadmin.getjTextField3();
                    MKaryawan karyawan = model.cekKaryawan(username);
                    if (karyawan == null){
                        JOptionPane.showConfirmDialog(null, "Karyawan Tidak Ditemukan","Update Error",JOptionPane.DEFAULT_OPTION);
                    } else {
                        viewadmin.setjTextField3(karyawan.getUsername());
                        viewadmin.setjTextField4(karyawan.getNama());
                        viewadmin.setjPasswordField2(karyawan.getPassword());
                        viewadmin.setjComboBox2(karyawan.getRole());
                    }
                } catch (Exception ee) { 
                    JOptionPane.showConfirmDialog(null, ee,"Update Error",JOptionPane.DEFAULT_OPTION);
                }
            }
        }
    }
    
}
