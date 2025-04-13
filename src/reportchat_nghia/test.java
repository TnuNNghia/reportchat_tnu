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
            param = new ParameterReportMuon(maphieu, madocgia,hoten, ngayMuon, hanTra,trangthai, ds);
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btn_print.setText("print");
        btn_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_printActionPerformed(evt);
            }
        });

        jLabel1.setText("Mã phiếu mượn");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(jLabel1)
                        .addGap(30, 30, 30)
                        .addComponent(txt_maphieumuon, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(136, 136, 136)
                        .addComponent(btn_print)))
                .addContainerGap(129, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(78, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_maphieumuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addComponent(btn_print)
                .addGap(127, 127, 127))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed
        String phieumuon = txt_maphieumuon.getText().trim();

        if (phieumuon.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập mã độc giả!");
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
    private javax.swing.JButton btn_print;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField txt_maphieumuon;
    // End of variables declaration//GEN-END:variables
}
