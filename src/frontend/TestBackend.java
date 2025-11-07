package src.frontend;

import src.backhend.*;
import java.util.ArrayList;

public class TestBackend {
    public static void main(String[] args) {
        // Buat koneksi database dulu
        dbcoba.bukaKoneksi();
        
        try {
            // Buat objek Kategori
            Kategori kat1 = new Kategori("Novel", "Koleksi buku novel");
            Kategori kat2 = new Kategori("Referensi", "Buku referensi ilmiah");
            Kategori kat3 = new Kategori("Komik", "Komik anak-anak");
            
            // test insert
            kat1.save();
            kat2.save();
            kat3.save();
            
            // test update
            kat2.setKeterangan("Koleksi buku referensi ilmiah");
            kat2.save();
            
            // test delete
            kat3.delete();
            
            // test select all
            System.out.println("\n=== TEST SELECT ALL ===");
            for(Kategori k : new Kategori().getAll()) {
                System.out.println("Nama: " + k.getNama() + ", Ket: " + k.getKeterangan());
            }
            
            // test search
            System.out.println("\n=== TEST SEARCH (keyword: ilmiah) ===");
            for(Kategori k : new Kategori().search("ilmiah")) {
                System.out.println("Nama: " + k.getNama() + ", Ket: " + k.getKeterangan());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Tutup koneksi di akhir
            dbcoba.tutupKoneksi();
        }
    }
}