package slssalesordapp.view.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;

import javax.servlet.*;
import javax.servlet.http.*;

import javax.sql.DataSource;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

public class ExportServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
    Connection con;
    Statement st;
    ResultSet rs;
    ServletOutputStream output = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        output = response.getOutputStream();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=ItemDetails.xls");
        response.setContentType(CONTENT_TYPE);
        //PrintWriter out = response.getWriter();
        /* out.println("<html>");
        out.println("<head><title>ExportServlet</title></head>");
        out.println("<body>");
        out.println("<p>The servlet has received a GET. This is the reply.</p>");
        out.println("</body></html>");
        out.close(); */
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            String pa = request.getParameter("path");
            //StringBuilder path = ;
            StringBuilder OrgIdBind = null;
            StringBuilder HoOrgIdBind = null;
            StringBuilder DocIdBind = null;
            StringBuilder query = new StringBuilder("SELECT \n" +
                    "  A.ITM_BASIS,\n" +
                    "  A.ITM_ID,\n" +
                    "  A.ITM_UOM,\n" +
                    "  A.ITM_RATE,\n" +
                    "  A.ITM_QTY,\n" +            
                    "  A.ITM_DISC_TYP,\n" +
                    "  A.ITM_DISC_VAL\n" +
                    "FROM \n" +
                    "  SLS$SO$ITM A\n" +
                    "WHERE\n" +
                    pa);
            /*
                "  A.SLOC_ID = "+SlocIdBind+"  AND A.CLD_ID = :" + CldIdBind.toString()+"  AND A.ORG_ID = "+OrgIdBind.toString()+" AND A.HO_ORG_ID = " +HoOrgIdBind.toString()+
                                                    "  AND A.DOC_ID = "+DocIdBind.toString()); */
            System.out.println("Final query : " + query);

            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Sheet1");
            HSSFCellStyle cellStyle = workbook.createCellStyle();

            HSSFFont font = workbook.createFont();
            font.setFontName(HSSFFont.FONT_ARIAL);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            cellStyle.setFont(font);


            HSSFRow createRow = sheet.createRow(0);
            for (int i = 0; i <= 6; i++) {
                Cell cell = createRow.createCell(i);
                // sheet.autoSizeColumn(i);
                // sheet.setColumnWidth(100+i,100);
                switch (i) {
                case 0:                  
                    cell.setCellValue("ITM_BASIS");
                    cell.setCellStyle(cellStyle);
                    break;
                case 1:
                    cell.setCellValue("ITM_ID");
                    cell.setCellStyle(cellStyle);
                    break;
                case 2:
                    cell.setCellValue("ITM_UOM");
                    cell.setCellStyle(cellStyle);
                    break;
                case 3:
                    cell.setCellValue("ITEM_PRICE");
                    cell.setCellStyle(cellStyle);
                    break;
                case 4:
                    cell.setCellValue("QUANTITY");
                    cell.setCellStyle(cellStyle);
                    break;
                case 5:
                    cell.setCellValue("DISCOUNT_TYPE");
                    cell.setCellStyle(cellStyle);
                    break;
                case 6:
                    cell.setCellValue("DISCOUNT");
                    cell.setCellStyle(cellStyle);
                    break;
                }
            }

            Context ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/SLSDS");
            con = ds.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(query.toString());
            int rownum = 1;
            while (rs.next()) {
                HSSFRow row = sheet.createRow(rownum++);
                for (int i = 1; i <= 7; i++) {
                    Cell cell = row.createCell(i - 1);
                    if(i ==4 || i ==5 || i== 7){
                        Double value = new Double(rs.getString(i));
                        cell.setCellValue(value);
                    }else{
                        cell.setCellValue(rs.getString(i));
                    }
                    
                }
            }
            workbook.write(output);
            output.flush();
            output.close();
            rs.close();
            st.close();
            con.close();


        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
