package src.backhend;

import java.sql.*;

public class dbcoba {
    private static Connection koneksi;

    // === Buka koneksi ke PostgreSQL ===
    public static void bukaKoneksi() {
        if (koneksi == null) {
            try {
                String url = "jdbc:postgresql://localhost:5432/dbperpus";
                String user = "postgres";
                String password = "12345678";

                // Load driver PostgreSQL
                try {
                    Class.forName("org.postgresql.Driver");
                } catch (ClassNotFoundException ex) {
                    System.out.println("Driver PostgreSQL tidak ditemukan di classpath: " + ex.getMessage());
                    return;
                }

                koneksi = DriverManager.getConnection(url, user, password);
                System.out.println("✅ Koneksi PostgreSQL berhasil!");
            } catch (SQLException t) {
                System.out.println("❌ Error koneksi PostgreSQL: " + t.getMessage());
            }
        }
    }

    // === Tutup koneksi ===
    public static void tutupKoneksi() {
        if (koneksi != null) {
            try {
                koneksi.close();
                koneksi = null;
                System.out.println("🔒 Koneksi PostgreSQL berhasil ditutup.");
            } catch (SQLException e) {
                System.out.println("⚠️ Gagal menutup koneksi: " + e.getMessage());
            }
        }
    }

    // === Jalankan query INSERT, UPDATE, DELETE ===
    public static boolean executeQuery(String query) {
        bukaKoneksi();
        boolean result = false;
        Statement stmt = null;
        try {
            stmt = koneksi.createStatement();
            stmt.executeUpdate(query);
            result = true;
        } catch (SQLException e) {
            System.out.println("❌ Error saat menjalankan query: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    // === Jalankan SELECT query ===
    public static ResultSet selectQuery(String query) {
        bukaKoneksi();
        ResultSet rs = null;
        Statement stmt = null;
        try {
            stmt = koneksi.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("❌ Error saat menjalankan select query: " + e.getMessage());
            rs = null;
        }
        return rs;
    }

    // === Jalankan INSERT dan ambil ID yang dihasilkan ===
    public static int insertQueryGetId(String query) {
        bukaKoneksi();
        int generatedId = 0;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = koneksi.createStatement();
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error saat insertQueryGetId: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return generatedId;
    }

    // === Tutup ResultSet dan Statement ===
    public static void closeResultSet(ResultSet rs) {
        if (rs == null) return;
        try {
            Statement st = rs.getStatement();
            rs.close();
            if (st != null) st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getKoneksi() {
        return koneksi;
    }

    // === Untuk uji koneksi ===
    public static void main(String[] args) {
        bukaKoneksi();
        ResultSet rs = selectQuery("SELECT * FROM kategori");
        try {
            while (rs != null && rs.next()) {
                System.out.println("id: " + rs.getInt("idkategori") + ", nama: " + rs.getString("nama"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(rs);
            tutupKoneksi();
        }
    }
}
