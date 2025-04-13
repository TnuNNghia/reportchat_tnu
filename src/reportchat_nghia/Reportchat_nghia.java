
package reportchat_nghia;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;

public class Reportchat_nghia {

        

    public static void main(String[] args) {
        try {
            String reportPath = "D:\\MonHoc\\Do_an_tot_Nghiep\\Example\\reportchat_nghia\\src\\print\\report_phieumuon.jrxml";
            JasperReport report = JasperCompileManager.compileReport(reportPath);

            if (report != null) {
                System.out.println("✅ Compile báo cáo thành công!");
            } else {
                System.out.println("❌ Compile thất bại, báo cáo NULL.");
            }
        } catch (JRException e) {
            e.printStackTrace();
            System.out.println("❌ Lỗi compile báo cáo!");
        }
    }

}
