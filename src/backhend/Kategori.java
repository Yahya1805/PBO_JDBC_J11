package src.backhend;

import java.util.ArrayList;
import java.sql.*;

public class Kategori {
    private int idkategori;
    private String nama;
    private String keterangan;

    // === Konstruktor ===
    public Kategori() {
    }

    public Kategori(String nama, String keterangan) {
        this.nama = nama;
        this.keterangan = keterangan;
    }

    // === Getter dan Setter ===
    public int getIdkategori() {
        return idkategori;
    }

    public void setIdkategori(int idkategori) {
        this.idkategori = idkategori;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    // === Method getById ===
    public Kategori getById(int id) {
        Kategori kat = new Kategori();
        ResultSet rs = dbcoba.selectQuery("SELECT * FROM kategori WHERE idkategori = " + id);

        try {
            while (rs.next()) {
                kat = new Kategori();
                kat.setIdkategori(rs.getInt("idkategori"));
                kat.setNama(rs.getString("nama"));
                kat.setKeterangan(rs.getString("keterangan"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return kat;
    }

    // === Method getAll ===
    public ArrayList<Kategori> getAll() {
        ArrayList<Kategori> ListKategori = new ArrayList<>();
        ResultSet rs = dbcoba.selectQuery("SELECT * FROM kategori");

        try {
            while (rs.next()) {
                Kategori kat = new Kategori();
                kat.setIdkategori(rs.getInt("idkategori"));
                kat.setNama(rs.getString("nama"));
                kat.setKeterangan(rs.getString("keterangan"));
                ListKategori.add(kat);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ListKategori;
    }

    // === Method search ===
    public ArrayList<Kategori> search(String keyword) {
        ArrayList<Kategori> ListKategori = new ArrayList<>();
        String sql = "SELECT * FROM kategori WHERE "
                   + "nama LIKE '%" + keyword + "%' "
                   + "OR keterangan LIKE '%" + keyword + "%'";
        ResultSet rs = dbcoba.selectQuery(sql);

        try {
            while (rs.next()) {
                Kategori kat = new Kategori();
                kat.setIdkategori(rs.getInt("idkategori"));
                kat.setNama(rs.getString("nama"));
                kat.setKeterangan(rs.getString("keterangan"));
                ListKategori.add(kat);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ListKategori;
    }

    // === Method save ===
    public void save() {
        if (getById(this.idkategori).getIdkategori() == 0) {
            // INSERT
            String SQL = "INSERT INTO kategori (nama, keterangan) VALUES ("
                    + "'" + this.nama + "', "
                    + "'" + this.keterangan + "')";

            // Panggil method insertQueryGetId() yang baru ditambahkan di dbcoba
            this.idkategori = dbcoba.insertQueryGetId(SQL);

        } else {
            // UPDATE
            String SQL = "UPDATE kategori SET "
                    + "nama = '" + this.nama + "', "
                    + "keterangan = '" + this.keterangan + "' "
                    + "WHERE idkategori = " + this.idkategori;
            dbcoba.executeQuery(SQL);
        }
    }

    // === Method delete ===
    public void delete() {
        String SQL = "DELETE FROM kategori WHERE idkategori = " + this.idkategori;
        dbcoba.executeQuery(SQL);
    }
}
