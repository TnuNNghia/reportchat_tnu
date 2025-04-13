/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package reportchat_nghia;

import connection.DatabaseConnection;
import java.util.ArrayList;
import java.util.List;
import print_model.FieldReportMuon;
import print_model.ParameterReportMuon;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import print.ReportManager;
import print_model.FieldReportTra;
import print_model.ParameterReportTra;

/**
 *
 * @author ACER
 */
public class test extends javax.swing.JFrame {

    /**
     * Creates new form test
     */
    public test() {
        initComponents();
        try {
            DatabaseConnection.getInstance().connectToDatabase();
            ReportManager.getInstance().compileReport();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ParameterReportMuon getDataPrintMuon(String maphieu) throws SQLException {
        String sql = "SELECT pm.MaPhieuMuon, pm.MaDocGia, dg.HoTen, pm.NgayMuon, pm.HanTra, pm.TrangThai FROM phieumuon pm INNER JOIN docgia dg ON pm.MaDocGia = dg.MaDocGia WHERE pm.MaPhieuMuon = ? ORDER BY pm.MaPhieuMuon";
        PreparedStatement p = DatabaseConnection.getInstance().getConnection().prepareStatement(sql);
        p.setString(1, maphieu);

        ResultSet r = p.executeQuery();

        ParameterReportMuon param = null;

        if (r.next()) {
            String madocgia = r.getString("MaDocGia");
            String hoten = r.getString("HoTen");
            Date ngayMuon = r.getDate("NgayMuon");  // Thêm lấy Ngày Mượn
            Date hanTra = r.getDate("HanTra");  // Thêm lấy Hạn Trả
            String trangthai = r.getString("TrangThai");
            // Lấy chi tiết phiếu mượn
            String sqlDetail = "SELECT MaPhieuMuon, MaSach, TenSach, SoLuong FROM chitietmuon WHERE MaPhieuMuon = ?";
            PreparedStatement p2 = DatabaseConnection.getInstance().getConnection().prepareStatement(sqlDetail);
            p2.setString(1, maphieu);
            ResultSet r2 = p2.executeQuery();

            List<FieldReportMuon> ds = new ArrayList<>();
            while (r2.next()) {
                String mpm = r2.getString("MaPhieuMuon");
                String ms = r2.getString("MaSach");
                String ten = r2.getString("TenSach");
                int soluong = r2.getInt("SoLuong");

                ds.add(new FieldReportMuon(mpm, ms, ten, soluong));
            }

            r2.close();
            p2.close();
// Thêm Ngày Mượn và Hạn Trả vào trong constructor của ParameterReportMuon
            param = new ParameterReportMuon(maphieu, madocgia, hoten, ngayMuon, hanTra, trangthai, ds);
        }

        r.close();
        p.close();

        return param;
    }

    public ParameterReportTra getDataPrintTra(String maphieutra) throws SQLException {
        String sql = "SELECT  pt.MaPhieuTra, dg.MaDocGia, dg.HoTen,pt.NgayTra FROM  phieutra pt JOIN phieumuon pm ON pt.MaPhieuMuon = pm.MaPhieuMuon JOIN  docgia dg ON pm.MaDocGia = dg.MaDocGia WHERE pt.MaPhieuTra = ? ORDER BY pt.MaPhieuTra";
        PreparedStatement p = DatabaseConnection.getInstance().getConnection().prepareStatement(sql);
        p.setString(1, maphieutra);

        ResultSet r = p.executeQuery();

        ParameterReportTra param = null;

        if (r.next()) {
            String madocgia = r.getString("MaDocGia");
            String hoten = r.getString("HoTen");
            Date ngayTra = r.getDate("NgayTra");  // Thêm lấy Ngày Trả

            // Lấy chi tiết phiếu trả
            String sqlDetail = "SELECT MaPhieuTra, MaSach, TenSach, SoLuong, TinhTrangSach FROM chitiettra WHERE MaPhieuTra = ?";
            PreparedStatement p2 = DatabaseConnection.getInstance().getConnection().prepareStatement(sqlDetail);
            p2.setString(1, maphieutra);
            ResultSet r2 = p2.executeQuery();

            List<FieldReportTra> ds = new ArrayList<>();
            while (r2.next()) {
                String mpt = r2.getString("MaPhieuTra");
                String ms = r2.getString("MaSach");
                String ten = r2.getString("TenSach");
                int soluong = r2.getInt("SoLuong");
                String tinhtrangsach = r2.getString("TinhTrangSach");
                ds.add(new FieldReportTra(mpt, ms, ten, soluong, tinhtrangsach));
            }

            r2.close();
            p2.close();
// Thêm Ngày Mượn và Hạn Trả vào trong constructor của ParameterReportMuon
            param = new ParameterReportTra(maphieutra, madocgia, hoten, ngayTra, ds);
        }

        r.close();
        p.close();

        return param;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_print = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txt_maphieumuon = new javax.swing.JTextField();
        btn_phieutra = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txt_maphieutra = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btn_print.setText("print");
        btn_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_printActionPerformed(evt);
            }
        });

        jLabel1.setText("Mã phiếu mượn");

        btn_phieutra.setText("print1");
        btn_phieutra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_phieutraActionPerformed(evt);
            }
        });

        jLabel2.setText("Mã phiếu trả");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_maphieumuon, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_maphieutra, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_phieutra)
                    .addComponent(btn_print))
                .addContainerGap(133, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_maphieumuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_print))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_phieutra)
                    .addComponent(jLabel2)
                    .addComponent(txt_maphieutra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(207, 207, 207))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed
        String phieumuon = txt_maphieumuon.getText().trim();

        if (phieumuon.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập mã phiếu mượn!");
            return;
        }

        try {
            // Gọi hàm có truyền tham số mã độc giả
            ParameterReportMuon data = getDataPrintMuon(phieumuon);

            if (data == null) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy phiếu mượn : " + phieumuon);
                return;
            }

            // In báo cáo thông qua ReportManager
            ReportManager.getInstance().printReportMuon(data);

        } catch (SQLException | JRException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi in phiếu: " + ex.getMessage());
        }
    }//GEN-LAST:event_btn_printActionPerformed

    private void btn_phieutraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_phieutraActionPerformed
        String phieutra = txt_maphieutra.getText().trim();

        if (phieutra.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập mã phiếu trả!");
            return;
        }

        try {
            // Gọi hàm có truyền tham số mã độc giả
            ParameterReportTra data = getDataPrintTra(phieutra);

            if (data == null) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy phiếu trả : " + phieutra);
                return;
            }

            // In báo cáo thông qua ReportManager
            ReportManager.getInstance().printReportTra(data);

        } catch (SQLException | JRException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi in phiếu: " + ex.getMessage());
        }
    }//GEN-LAST:event_btn_phieutraActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(test.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(test.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(test.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(test.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new test().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_phieutra;
    private javax.swing.JButton btn_print;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField txt_maphieumuon;
    private javax.swing.JTextField txt_maphieutra;
    // End of variables declaration//GEN-END:variables
}
