import src.backhend.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FrmBuku extends JFrame {

    private JTextField txtIdBuku, txtJudul, txtPenerbit, txtPenulis, txtCari;
    private JComboBox<Kategori> cmbKategori;
    private JButton btnSimpan, btnHapus, btnTambahBaru, btnCari;
    private JTable tblBuku;
    private JScrollPane jScrollPane1;

    public FrmBuku() {

        //tampilan UI
        UIManager.put("Button.focus", new Color(0, 0, 0, 0));   
        UIManager.put("Table.showGrid", false);                     
        UIManager.put("Table.intercellSpacing", new Dimension(8, 8));

        //inisialisasi komponen form
        JLabel lblId = new JLabel("ID Buku");
        JLabel lblKategori = new JLabel("Kategori");
        JLabel lblJudul = new JLabel("Judul");
        JLabel lblPenerbit = new JLabel("Penerbit");
        JLabel lblPenulis = new JLabel("Penulis");

        txtIdBuku = new JTextField();
        cmbKategori = new JComboBox<>();
        txtJudul = new JTextField();
        txtPenerbit = new JTextField();
        txtPenulis = new JTextField();
        txtCari = new JTextField();

        btnSimpan = makeButton("Simpan");
        btnHapus = makeButton("Hapus");
        btnTambahBaru = makeButton("Tambah Baru");
        btnCari = makeButton("Cari");

        txtIdBuku.setText("0");
        txtIdBuku.setEnabled(false);

        tblBuku = new JTable();
        jScrollPane1 = new JScrollPane(tblBuku);

        setLayout(null); // manual layout

        //font style
        Font fLabel = new Font("Segoe UI", Font.PLAIN, 14);
        Font fText = new Font("Segoe UI", Font.PLAIN, 14);
        Font fBtn = new Font("Segoe UI", Font.BOLD, 14);

        lblId.setFont(fLabel);
        lblKategori.setFont(fLabel);
        lblJudul.setFont(fLabel);
        lblPenerbit.setFont(fLabel);
        lblPenulis.setFont(fLabel);

        txtIdBuku.setFont(fText);
        cmbKategori.setFont(fText);
        txtJudul.setFont(fText);
        txtPenerbit.setFont(fText);
        txtPenulis.setFont(fText);
        txtCari.setFont(fText);

        btnSimpan.setFont(fBtn);
        btnHapus.setFont(fBtn);
        btnTambahBaru.setFont(fBtn);
        btnCari.setFont(fBtn);

        tblBuku.setFont(fText);
        tblBuku.setRowHeight(28);

        //rapikan grid - layout lebih sederhana
        int xLabel = 30;
        int xField = 120;
        int wField = 250;
        int hField = 28;
        int gapY = 35;

        lblId.setBounds(xLabel, 30, 80, 25);
        txtIdBuku.setBounds(xField, 30, 80, hField);

        lblKategori.setBounds(xLabel, 30 + gapY, 80, 25);
        cmbKategori.setBounds(xField, 30 + gapY, wField, hField);

        lblJudul.setBounds(xLabel, 30 + gapY * 2, 80, 25);
        txtJudul.setBounds(xField, 30 + gapY * 2, wField, hField);

        lblPenerbit.setBounds(xLabel, 30 + gapY * 3, 80, 25);
        txtPenerbit.setBounds(xField, 30 + gapY * 3, wField, hField);

        lblPenulis.setBounds(xLabel, 30 + gapY * 4, 80, 25);
        txtPenulis.setBounds(xField, 30 + gapY * 4, wField, hField);

        // Button di samping kanan form
        btnSimpan.setBounds(xField + wField + 20, 30, 120, 35);
        btnHapus.setBounds(xField + wField + 20, 30 + gapY, 120, 35);
        btnTambahBaru.setBounds(xField + wField + 20, 30 + gapY * 2, 120, 35);

        // Search di bawah button
        txtCari.setBounds(xField + wField + 20, 30 + gapY * 3, 120, 32);
        btnCari.setBounds(xField + wField + 20, 30 + gapY * 4, 120, 32);

        // Table di bawah semua komponen
        jScrollPane1.setBounds(30, 30 + gapY * 5 + 20, 500, 250);

        add(lblId);
        add(txtIdBuku);
        add(lblKategori);
        add(cmbKategori);
        add(lblJudul);
        add(txtJudul);
        add(lblPenerbit);
        add(txtPenerbit);
        add(lblPenulis);
        add(txtPenulis);
        add(btnSimpan);
        add(btnHapus);
        add(btnTambahBaru);
        add(txtCari);
        add(btnCari);
        add(jScrollPane1);

        // ====== EVENT ======
        btnSimpan.addActionListener(e -> simpan());
        btnHapus.addActionListener(e -> hapus());
        btnTambahBaru.addActionListener(e -> kosongkanForm());
        btnCari.addActionListener(e -> cari(txtCari.getText()));
        tblBuku.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                isiFormDariTabel();
            }
        });

        // Frame Setting
        setTitle("Form Buku");
        setSize(580, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tampilkanCmbKategori();
        tampilkanData();
        kosongkanForm();
    }

    // STYLE BUTTON (MENGHILANGKAN GARIS FOKUS, MEMBERI EFEK HALUS)
    private JButton makeButton(String text) {
        JButton b = new JButton(text);
        b.setFocusPainted(false);
        b.setBackground(new Color(200, 220, 240));
        b.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        return b;
    }

    //CRUD
    private void simpan() {
        Buku buku = new Buku();
        buku.setIdbuku(Integer.parseInt(txtIdBuku.getText()));
        buku.setKategori((Kategori) cmbKategori.getSelectedItem());
        buku.setJudul(txtJudul.getText());
        buku.setPenulis(txtPenulis.getText());
        buku.setPenerbit(txtPenerbit.getText());
        buku.save();

        txtIdBuku.setText(String.valueOf(buku.getIdbuku()));
        tampilkanData();
    }

    private void hapus() {
        int row = tblBuku.getSelectedRow();
        if (row >= 0) {
            int id = Integer.parseInt(tblBuku.getValueAt(row, 0).toString());
            Buku buku = new Buku().getById(id);
            buku.delete();
            tampilkanData();
            kosongkanForm();
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus terlebih dahulu.");
        }
    }

    private void isiFormDariTabel() {
        int row = tblBuku.getSelectedRow();
        int id = Integer.parseInt(tblBuku.getValueAt(row, 0).toString());
        Buku buku = new Buku().getById(id);
        
        txtIdBuku.setText(String.valueOf(buku.getIdbuku()));
        cmbKategori.setSelectedItem(buku.getKategori());
        txtJudul.setText(buku.getJudul());
        txtPenerbit.setText(buku.getPenerbit());
        txtPenulis.setText(buku.getPenulis());
    }

    private void kosongkanForm() {
        txtIdBuku.setText("0");
        if (cmbKategori.getItemCount() > 0) {
            cmbKategori.setSelectedIndex(0);
        }
        txtJudul.setText("");
        txtPenerbit.setText("");
        txtPenulis.setText("");
    }

    private void tampilkanData() {
        String[] kolom = {"ID", "Kategori", "Judul", "Penulis", "Penerbit"};
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, kolom);

        for (Buku b : new Buku().getAll()) {
            model.addRow(new Object[]{
                b.getIdbuku(),
                b.getKategori().getNama(),
                b.getJudul(),
                b.getPenulis(),
                b.getPenerbit()
            });
        }

        tblBuku.setModel(model);
        tblBuku.getColumnModel().getColumn(0).setPreferredWidth(40);
    }

    private void cari(String keyword) {
        String[] kolom = {"ID", "Kategori", "Judul", "Penulis", "Penerbit"};
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, kolom);

        for (Buku b : new Buku().search(keyword)) {
            model.addRow(new Object[]{
                b.getIdbuku(),
                b.getKategori().getNama(),
                b.getJudul(),
                b.getPenulis(),
                b.getPenerbit()
            });
        }

        tblBuku.setModel(model);
    }

    private void tampilkanCmbKategori() {
        ArrayList<Kategori> listKategori = new Kategori().getAll();
        for (Kategori k : listKategori) {
            cmbKategori.addItem(k);
        }
    }

    public static void main(String[] args) {
        new FrmBuku().setVisible(true);
    }
}
