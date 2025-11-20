import src.backhend.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FrmAnggota extends JFrame {

    private JTextField txtIdAnggota, txtNama, txtAlamat, txtTelepon, txtCari;
    private JButton btnSimpan, btnHapus, btnTambahBaru, btnCari;
    private JTable tblAnggota;
    private JScrollPane jScrollPane1;

    public FrmAnggota() {

        //tampilan UI
        UIManager.put("Button.focus", new Color(0, 0, 0, 0));   
        UIManager.put("Table.showGrid", false);                     
        UIManager.put("Table.intercellSpacing", new Dimension(8, 8));

        //inisialisasi komponen form
        JLabel lblId = new JLabel("ID Anggota");
        JLabel lblNama = new JLabel("Nama");
        JLabel lblAlamat = new JLabel("Alamat");
        JLabel lblTelepon = new JLabel("Telepon");

        txtIdAnggota = new JTextField();
        txtNama = new JTextField();
        txtAlamat = new JTextField();
        txtTelepon = new JTextField();
        txtCari = new JTextField();

        btnSimpan = makeButton("Simpan");
        btnHapus = makeButton("Hapus");
        btnTambahBaru = makeButton("Tambah Baru");
        btnCari = makeButton("Cari");

        txtIdAnggota.setText("0");
        txtIdAnggota.setEnabled(false);

        tblAnggota = new JTable();
        jScrollPane1 = new JScrollPane(tblAnggota);

        setLayout(null); // manual layout

        //font style
        Font fLabel = new Font("Segoe UI", Font.PLAIN, 14);
        Font fText = new Font("Segoe UI", Font.PLAIN, 14);
        Font fBtn = new Font("Segoe UI", Font.BOLD, 14);

        lblId.setFont(fLabel);
        lblNama.setFont(fLabel);
        lblAlamat.setFont(fLabel);
        lblTelepon.setFont(fLabel);

        txtIdAnggota.setFont(fText);
        txtNama.setFont(fText);
        txtAlamat.setFont(fText);
        txtTelepon.setFont(fText);
        txtCari.setFont(fText);

        btnSimpan.setFont(fBtn);
        btnHapus.setFont(fBtn);
        btnTambahBaru.setFont(fBtn);
        btnCari.setFont(fBtn);

        tblAnggota.setFont(fText);
        tblAnggota.setRowHeight(28);

        //rapikan grid
        int xLabel = 30;
        int xField = 160;
        int wField = 360;
        int hField = 28;
        int gapY = 40;

        lblId.setBounds(xLabel, 30, 120, 25);
        txtIdAnggota.setBounds(xField, 30, 120, hField);

        lblNama.setBounds(xLabel, 30 + gapY, 120, 25);
        txtNama.setBounds(xField, 30 + gapY, wField, hField);

        lblAlamat.setBounds(xLabel, 30 + gapY * 2, 120, 25);
        txtAlamat.setBounds(xField, 30 + gapY * 2, wField, hField);

        lblTelepon.setBounds(xLabel, 30 + gapY * 3, 120, 25);
        txtTelepon.setBounds(xField, 30 + gapY * 3, wField, hField);

        btnSimpan.setBounds(xLabel, 30 + gapY * 4, 110, 35);
        btnHapus.setBounds(xLabel + 120, 30 + gapY * 4, 110, 35);
        btnTambahBaru.setBounds(xLabel + 240, 30 + gapY * 4, 140, 35);

        txtCari.setBounds(xLabel + 390, 30 + gapY * 4, 150, 32);
        btnCari.setBounds(xLabel + 545, 30 + gapY * 4, 70, 32);

        jScrollPane1.setBounds(30, 30 + gapY * 5 + 10, 640, 300);

        add(lblId);
        add(txtIdAnggota);
        add(lblNama);
        add(txtNama);
        add(lblAlamat);
        add(txtAlamat);
        add(lblTelepon);
        add(txtTelepon);
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
        tblAnggota.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                isiFormDariTabel();
            }
        });

        // Frame Setting
        setTitle("Form Anggota");
        setSize(720, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tampilkanData();
        kosongkanForm();
    }

    //   STYLE BUTTON (MENGHILANGKAN GARIS FOKUS, MEMBERI EFEK HALUS)
    private JButton makeButton(String text) {
        JButton b = new JButton(text);
        b.setFocusPainted(false);
        b.setBackground(new Color(200, 220, 240));
        b.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        return b;
    }

    //CRUD
    private void simpan() {
        Anggota ang = new Anggota();
        ang.setIdanggota(Integer.parseInt(txtIdAnggota.getText()));
        ang.setNama(txtNama.getText());
        ang.setAlamat(txtAlamat.getText());
        ang.setTelepon(txtTelepon.getText());
        ang.save();

        txtIdAnggota.setText(String.valueOf(ang.getIdanggota()));
        tampilkanData();
    }

    private void hapus() {
        int row = tblAnggota.getSelectedRow();
        if (row >= 0) {
            int id = Integer.parseInt(tblAnggota.getValueAt(row, 0).toString());
            Anggota ang = new Anggota().getById(id);
            ang.delete();
            tampilkanData();
            kosongkanForm();
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus terlebih dahulu.");
        }
    }

    private void isiFormDariTabel() {
        int row = tblAnggota.getSelectedRow();
        txtIdAnggota.setText(tblAnggota.getValueAt(row, 0).toString());
        txtNama.setText(tblAnggota.getValueAt(row, 1).toString());
        txtAlamat.setText(tblAnggota.getValueAt(row, 2).toString());
        txtTelepon.setText(tblAnggota.getValueAt(row, 3).toString());
    }

    private void kosongkanForm() {
        txtIdAnggota.setText("0");
        txtNama.setText("");
        txtAlamat.setText("");
        txtTelepon.setText("");
    }

    private void tampilkanData() {
        String[] kolom = {"ID", "Nama", "Alamat", "Telepon"};
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, kolom);

        for (Anggota a : new Anggota().getAll()) {
            model.addRow(new Object[]{
                a.getIdanggota(),
                a.getNama(),
                a.getAlamat(),
                a.getTelepon()
            });
        }

        tblAnggota.setModel(model);
        tblAnggota.getColumnModel().getColumn(0).setPreferredWidth(40);
    }

    private void cari(String keyword) {
        String[] kolom = {"ID", "Nama", "Alamat", "Telepon"};
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, kolom);

        for (Anggota a : new Anggota().search(keyword)) {
            model.addRow(new Object[]{
                a.getIdanggota(),
                a.getNama(),
                a.getAlamat(),
                a.getTelepon()
            });
        }

        tblAnggota.setModel(model);
    }

    public static void main(String[] args) {
        new FrmAnggota().setVisible(true);
    }
}
