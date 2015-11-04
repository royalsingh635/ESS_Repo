package slsreportapp.view.servlet;

import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DJCrosstab;
import ar.com.fdvs.dj.domain.DJGroupLabel;
import ar.com.fdvs.dj.domain.DJValueFormatter;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.ImageBanner;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.CrosstabBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.GroupBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.LabelPosition;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;


import java.awt.Color;

import java.io.IOException;
import java.io.OutputStream;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import java.text.DateFormat;

import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

public class SalesReportServletDynamic extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("enter in servlet ");

        response.setContentType("application/pdf");
        OutputStream op = response.getOutputStream();
        java.sql.Connection con = null;
        java.sql.Statement stmt = null;
        Connection pathconn = null;
        PreparedStatement pathps = null;
        ResultSet pathrs = null;
        ResultSet rst = null;
        String query = request.getParameter("Stmt");
        String grpWise = request.getParameter("grpWise");
        String cmlNmArr = request.getParameter("clmNm");
        String sumName = request.getParameter("sumName");
        String width = request.getParameter("width");
        String ReportName = request.getParameter("rptName");
        String ColSumNamewithoutGrp = request.getParameter("ColSumNamewithoutGrp");
        String fileType=request.getParameter("fileType");
        String pageFormat = request.getParameter("pageType").trim();

        System.out.println("Page format is:--" + pageFormat + "--");
        System.out.println("sum Column in Servlet are: " + sumName);

        System.out.println("All value of group wise in servlet is:  " + grpWise);
        System.out.println("Width of the columns are: " + width);
        System.out.println("===============");

        System.out.println("cmlNmArr  " + cmlNmArr);

        System.out.println("===============");

        String cmldescArr = request.getParameter("clmNmDesc");

        // this map has the name of column and their description.
        Map<String, String> map = new HashMap<String, String>();
        if (cmlNmArr.length() > 2) {
            StringTokenizer strTk = new StringTokenizer(cmlNmArr, ",");
            while (strTk.hasMoreElements()) {
                String str = ((String)strTk.nextElement()).replace("[", "").replace("]", "").trim();
                System.out.println("Column : " + str);

                String s[] = str.split("-");
                if (s.length > 1) {
                    System.out.println(s[0] + " --Split-- " + s[1]);
                    map.put(s[0], s[1]);
                }
            }
        }

        /* this is for getting the all group name which are selected..*/
        List<String> grpnames = new ArrayList<String>();
        List<AbstractColumn> groupcolumn = new ArrayList<AbstractColumn>();
        Map<String, AbstractColumn> grpcolmnPair = new HashMap<String, AbstractColumn>();

        List<String> clmAllSum = new ArrayList<String>();
        if (ColSumNamewithoutGrp.length() > 2) {
            StringTokenizer sumAllClm = new StringTokenizer(ColSumNamewithoutGrp, ",");
            while (sumAllClm.hasMoreElements()) {
                String str = ((String)sumAllClm.nextElement()).replace("[", "").replace("]", "").trim();
                clmAllSum.add(str);
            }
        }

        if (grpWise.length() > 2) {
            StringTokenizer grptoken = new StringTokenizer(grpWise, ",");
            while (grptoken.hasMoreElements()) {
                String str = ((String)grptoken.nextElement()).replace("[", "").replace("]", "").trim();
                grpnames.add(str);
            }
        }


        List<Integer> clmWidth = new ArrayList<Integer>();
        if (width.length() > 2) {
            StringTokenizer WidthToken = new StringTokenizer(width, ",");
            while (WidthToken.hasMoreElements()) {
                String str = ((String)WidthToken.nextElement()).replace("[", "").replace("]", "").trim();
                clmWidth.add(Integer.parseInt(str));
            }
        }

        List<String> columnNameSum = new ArrayList<String>();
        if (sumName.length() > 2) {
            StringTokenizer sumtoken = new StringTokenizer(sumName, ",");
            while (sumtoken.hasMoreElements()) {
                String str = ((String)sumtoken.nextElement()).replace("[", "").replace("]", "").trim();
                columnNameSum.add(str);
            }
        }
        System.out.println("Values of sum are: ");
        ListIterator<String> iterator = columnNameSum.listIterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            System.out.println("sum  name in servlet are: " + next);
        }
        System.out.println("Map Value : " + map.size());
        for (Map.Entry m : map.entrySet()) {
            System.out.println(m.getKey() + " " + m.getValue());
        }
        StringTokenizer clmDescTkr = new StringTokenizer(cmldescArr, ",");


        System.out.println("in servlet Query " + query + " GROUP wise= " + grpWise + " cmlNmArr " + cmlNmArr +
                           " cmldescArr " + cmldescArr);

        /*
         * these are the styles which designing the report.
         * */

        Style headerStyle = new Style();
        headerStyle.setBackgroundColor(new Color(193, 221, 249));
        headerStyle.setVerticalAlign(VerticalAlign.MIDDLE);
        headerStyle.setBorderBottom(Border.THIN);
        headerStyle.setBorderTop(Border.THIN);
        headerStyle.setBorderLeft(Border.THIN);
        headerStyle.setBorderRight(Border.THIN);
        headerStyle.setBorderColor(Color.black);
        headerStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        headerStyle.setTransparency(Transparency.OPAQUE);


        Style titleStyle = new Style("titleStyle");

        Style subtitleStyleParent = new Style("subtitleParent");
        subtitleStyleParent.setBackgroundColor(Color.CYAN);
        subtitleStyleParent.setTransparency(Transparency.OPAQUE);
        
        Style subtitleStyle = Style.createBlankStyle("subtitleStyle", "subtitleParent");
        subtitleStyle.setFont(Font.ARIAL_SMALL_BOLD);

        Style amountStyle = new Style();
        amountStyle.setHorizontalAlign(HorizontalAlign.RIGHT);

        Style detailStyle = new Style();
        detailStyle.setFont(new Font(8, Font._FONT_ARIAL, false, true, false));
        detailStyle.setVerticalAlign(VerticalAlign.MIDDLE);
        detailStyle.setBorderBottom(Border.THIN);
        detailStyle.setBorderTop(Border.THIN);
        detailStyle.setBorderColor(Color.black);
        detailStyle.setHorizontalAlign(HorizontalAlign.RIGHT);
        detailStyle.setPaddingRight(5);

        Style detailStyleFirst = new Style();
        detailStyleFirst.setFont(new Font(8, Font._FONT_ARIAL, false, true, false));
        detailStyleFirst.setVerticalAlign(VerticalAlign.MIDDLE);
        detailStyleFirst.setBorderBottom(Border.THIN);
        detailStyleFirst.setBorderTop(Border.THIN);
        detailStyleFirst.setBorderColor(Color.black);
        detailStyleFirst.setHorizontalAlign(HorizontalAlign.RIGHT);
        detailStyleFirst.setPaddingRight(5);
        detailStyleFirst.setBorderLeft(Border.THIN);

        Style detailStyleLast = new Style();
        detailStyleLast.setFont(new Font(8, Font._FONT_ARIAL, false, true, false));
        detailStyleLast.setVerticalAlign(VerticalAlign.MIDDLE);
        detailStyleLast.setBorderBottom(Border.THIN);
        detailStyleLast.setBorderTop(Border.THIN);
        detailStyleLast.setBorderColor(Color.black);
        detailStyleLast.setHorizontalAlign(HorizontalAlign.RIGHT);
        detailStyleLast.setBorderRight(Border.THIN);
        detailStyleLast.setPaddingRight(5);

        Style detailStyleLeft = new Style();
        detailStyleLeft.setFont(new Font(8, Font._FONT_ARIAL, false, true, false));
        detailStyleLeft.setVerticalAlign(VerticalAlign.MIDDLE);
        detailStyleLeft.setHorizontalAlign(HorizontalAlign.LEFT);
        detailStyleLeft.setBorderBottom(Border.THIN);
        detailStyleLeft.setBorderTop(Border.THIN);
        detailStyleLeft.setBorderColor(Color.black);
        detailStyleLeft.setPaddingLeft(5);


        Style detailStyleFirstLeft = new Style();
        detailStyleFirstLeft.setFont(new Font(8, Font._FONT_ARIAL, false, true, false));
        detailStyleFirstLeft.setVerticalAlign(VerticalAlign.MIDDLE);
        detailStyleFirstLeft.setHorizontalAlign(HorizontalAlign.LEFT);
        detailStyleFirstLeft.setBorderBottom(Border.THIN);
        detailStyleFirstLeft.setBorderTop(Border.THIN);
        detailStyleFirstLeft.setBorderColor(Color.black);
        detailStyleFirstLeft.setBorderLeft(Border.THIN);
        detailStyleFirstLeft.setPaddingLeft(5);

        Style detailStyleLastLeft = new Style();
        detailStyleLastLeft.setFont(new Font(8, Font._FONT_ARIAL, false, true, false));
        detailStyleLastLeft.setVerticalAlign(VerticalAlign.MIDDLE);
        detailStyleLastLeft.setHorizontalAlign(HorizontalAlign.LEFT);
        detailStyleLastLeft.setBorderBottom(Border.THIN);
        detailStyleLastLeft.setBorderTop(Border.THIN);
        detailStyleLastLeft.setBorderColor(Color.black);
        detailStyleLastLeft.setBorderRight(Border.THIN);
        detailStyleLastLeft.setPaddingLeft(5);

        Style headerVariables = new Style();
        // headerVariables.setFont(Font.ARIAL_MEDIUM_BOLD);
        headerVariables.setFont(new Font(10, Font._FONT_ARIAL, true, true, false));
        headerVariables.setBorderBottom(Border.THIN);
        headerVariables.setBorderTop(Border.THIN);
        headerVariables.setHorizontalAlign(HorizontalAlign.RIGHT);
        headerVariables.setVerticalAlign(VerticalAlign.MIDDLE);
        headerVariables.setPaddingRight(5);

        Style headerVariables1 = new Style();
        //   headerVariables1.setFont(Font.ARIAL_MEDIUM_BOLD);
        headerVariables1.setFont(new Font(10, Font._FONT_ARIAL, true, true, false));
        headerVariables1.setBorderBottom(Border.THIN);
        headerVariables1.setBorderTop(Border.THIN);
        headerVariables1.setBorderRight(Border.THIN);
        headerVariables.setHorizontalAlign(HorizontalAlign.RIGHT);
        headerVariables1.setVerticalAlign(VerticalAlign.MIDDLE);
        headerVariables1.setPaddingRight(5);

        Style myVar = new Style();
        //myVar.setFont(new Font(0, Font._FONT_ARIAL,false,false,false));
        myVar.setBackgroundColor(Color.red);
        myVar.setHorizontalAlign(HorizontalAlign.RIGHT);
        myVar.setVerticalAlign(VerticalAlign.MIDDLE);
        myVar.setTextColor(Color.white);

        myVar.setBorderBottom(Border.THIN);
        myVar.setBorderTop(Border.THIN);

        /* myVar.setBorderRight(Border.THIN);
         myVar.setBorderLeft(Border.THIN); */

        Style myVar1 = new Style();
        //myVar.setFont(new Font(0, Font._FONT_ARIAL,false,false,false));
        myVar1.setBackgroundColor(Color.red);
        myVar1.setHorizontalAlign(HorizontalAlign.RIGHT);
        myVar1.setVerticalAlign(VerticalAlign.MIDDLE);
        myVar1.setTextColor(Color.white);

        myVar1.setBorderBottom(Border.THIN);
        myVar1.setBorderTop(Border.THIN);
        myVar1.setBorderRight(Border.THIN);

        Style groupStyle = new Style("groupStyle");
        groupStyle.setFont(new Font(10, Font._FONT_VERDANA, true));
        groupStyle.setTextColor(Color.BLUE);
        groupStyle.setHorizontalAlign(HorizontalAlign.LEFT);
        groupStyle.setBorderLeft(Border.THIN);
        groupStyle.setPaddingLeft(10);
        groupStyle.setBorderRight(Border.THIN);
        groupStyle.setBorderTop(Border.THIN);
        groupStyle.setBorderBottom(Border.THIN);


        if (query != null) {
            try {
                Context pathctx = new InitialContext();
                DataSource pathds = (DataSource)pathctx.lookup("java:comp/env/jdbc/APPDS");
                pathconn = pathds.getConnection();
                System.out.println("in try");
                //con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.220:1521:deve", "sls", "ebizSLS");
                pathps = pathconn.prepareStatement("select distinct srvr_Loc_As_Rpt_Path from APP.App$Servr$Loc");
                pathrs = pathps.executeQuery();
                String path = null;
                while (pathrs.next()) {
                    path = pathrs.getString(1);
                    // System.out.println("Path is :" + rs.getString(1));
                }
                System.out.println("path is"+path);
                Context ctx = new InitialContext();
                DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/APPDS");
                con = ds.getConnection();

                stmt = con.createStatement();
                rst = stmt.executeQuery(query);
                System.out.println("conn " + con + " " + stmt + "  " + rst);

                ResultSetMetaData rsmd = rst.getMetaData();
                JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rst);
                System.out.println("  resultSetDataSource " + resultSetDataSource);
                /*****************Generating Dynamic Report Builder**************************/


                DynamicReportBuilder drb = new DynamicReportBuilder();
                /** * Adding many autotexts in the same position (header/footer and aligment) makes them to be one on top of the other */


                Style atStyle3 =
                    new StyleBuilder(true).setFont(new Font(8, Font._FONT_TIMES_NEW_ROMAN, false, true, false)).setTextColor(Color.BLACK).setBorderLeft(Border.THIN).setBorderBottom(Border.THIN).setBorderTop(Border.THIN).setHorizontalAlign(HorizontalAlign.LEFT).build();

                /*   this is to add the text on the head of the page [102,0,204] */
                Style orgStyle =
                    new StyleBuilder(true).setFont(new Font(11, Font._FONT_ARIAL, true, true, false)).setTextColor(new Color(102,
                                                                                                                             0,
                                                                                                                             204)).build();

                drb.addAutoText(ReportName, AutoText.POSITION_HEADER, AutoText.ALIGNMENT_RIGHT, 250, orgStyle);

                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                String dateFormat = df.format(new Date());

                drb.addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_FOOTER, AutoText.ALIGMENT_RIGHT, 100,
                                20);
                drb.addAutoText(dateFormat, AutoText.POSITION_FOOTER, AutoText.ALIGMENT_LEFT, 100);

                drb.addImageBanner(path+"Org-logo.png", new Integer(30), new Integer(30), ImageBanner.ALIGN_LEFT);
                drb.setDefaultStyles(titleStyle, subtitleStyle, headerStyle, null);
                // drb.setDefaultStyles(null, null, headerStyle, null);
                drb.addStyle(subtitleStyleParent);
                drb.setMargins(20, 20, 20, 20);

                int numColumns = rsmd.getColumnCount();
                AbstractColumn columnNm = null;

                List<AbstractColumn> allColumn = new ArrayList<AbstractColumn>();
                List<String> columnNameList = new ArrayList<String>();
                List<Integer> columnNameType = new ArrayList<Integer>();


                DJGroupLabel grpLabel =
                    new DJGroupLabel("Total  : ", new StyleBuilder(true).setFont(Font.ARIAL_MEDIUM_BOLD).setTextColor(Color.green).build(),
                                     LabelPosition.TOP);
                System.out.println("total no of columns are: " + numColumns);
                int counter = 1;

                if (clmAllSum.size() > 0) {
                    drb.setGrandTotalLegend("Grand TOTAL :");
                    drb.setGrandTotalLegendStyle(atStyle3);
                }


                for (int i = 1; i < numColumns + 1; i++) {

                    String columnName = rsmd.getColumnName(i);
                    columnNameList.add(columnName);
                    System.out.println("columnName " + columnName + " Map Value : " + map.get(columnName));

                    Integer type = rsmd.getColumnType(i);
                    System.out.println("type " + type);
                    columnNameType.add(type);

                    String desc = ((String)clmDescTkr.nextElement()).replace("[", "").replace("]", "");
                    boolean flag = false;

                    ListIterator<String> itr1 = grpnames.listIterator();
                    outer:
                    while (itr1.hasNext()) {
                        String next = itr1.next();
                        if ((columnName.toString()).equalsIgnoreCase(next)) {
                            flag = true;
                            break outer;
                        }
                    }

                    boolean chkflag = clmAllSum.contains(columnName);

                    if (type == 2) {
                        System.out.println("when 6");

                        if (flag == true) {
                            columnNm =
                                    ColumnBuilder.getNew().setColumnProperty(columnName.trim(), Float.class.getName()).setTitle(map.get(columnName)).setPattern("#,##0.00;-#,##0.00").setStyle(groupStyle).setWidth(clmWidth.get((i -
                                                                                                                                                                                                                                  1))).build();
                            allColumn.add(columnNm);
                            groupcolumn.add(columnNm);
                            grpcolmnPair.put(columnName, columnNm);
                            ++counter;
                        } else {
                            if (i == counter) {
                                System.out.println("Came in the first Column 1");
                                columnNm =
                                        ColumnBuilder.getNew().setColumnProperty(columnName.trim(), Float.class.getName()).setTitle(map.get(columnName)).setPattern("#,##0.00;-#,##0.00").setStyle(detailStyleFirst).setWidth(clmWidth.get((i -
                                                                                                                                                                                                                                            1))).build();
                            } else if ((counter == grpnames.size() + 1 && i == numColumns) ||
                                       (i == (numColumns - (grpnames.size() + 1 - counter)))) {
                                System.out.println("Last column type 2");
                                columnNm =
                                        ColumnBuilder.getNew().setColumnProperty(columnName.trim(), Float.class.getName()).setTitle(map.get(columnName)).setPattern("#,##0.00;-#,##0.00").setStyle(detailStyleLast).setWidth(clmWidth.get((i -
                                                                                                                                                                                                                                           1))).build();
                                if (clmAllSum.size() > 0) {
                                    if (chkflag == true) {
                                        drb.addGlobalFooterVariable(columnNm, DJCalculation.SUM,
                                                                    new StyleBuilder(true).setBorderTop(Border.THIN).setPaddingRight(5).setHorizontalAlign(HorizontalAlign.RIGHT).setBorderRight(Border.THIN).setBorderBottom(Border.THIN).build());
                                    } else {
                                        drb.addGlobalFooterVariable(columnNm, DJCalculation.SYSTEM,
                                                                    new StyleBuilder(true).setBorderRight(Border.THIN).setBorderTop(Border.THIN).setBorderBottom(Border.THIN).build());
                                    }
                                }

                            } else {
                                columnNm =
                                        ColumnBuilder.getNew().setColumnProperty(columnName.trim(), Float.class.getName()).setPattern("#,##0.00;-#,##0.00").setTitle(map.get(columnName)).setStyle(detailStyle).setWidth(clmWidth.get((i -
                                                                                                                                                                                                                                       1))).build();
                                if (clmAllSum.size() > 0) {
                                    if (chkflag == true) {
                                        drb.addGlobalFooterVariable(columnNm, DJCalculation.SUM,
                                                                    new StyleBuilder(true).setBorderTop(Border.THIN).setPaddingRight(5).setHorizontalAlign(HorizontalAlign.RIGHT).setBorderBottom(Border.THIN).build());
                                    } else {
                                        drb.addGlobalFooterVariable(columnNm, DJCalculation.SYSTEM,
                                                                    new StyleBuilder(true).setBorderTop(Border.THIN).setBorderBottom(Border.THIN).build());
                                    }
                                }

                            }
                            allColumn.add(columnNm);

                        }
                        //  drb.addGlobalHeaderVariable(columnNm, DJCalculation.SUM);
                        /* drb.setGrandTotalLegend("TOTAL :");
                        drb.setGrandTotalLegendStyle(atStyle3); */


                        /*  drb.setGlobalHeaderVariableHeight(new Integer(25));
                        drb.setGlobalFooterVariableHeight(new Integer(35)); */

                    }

                    else if (type == 12) {
                        System.out.println(" when 12");
                        if (flag == true) {
                            columnNm =
                                    ColumnBuilder.getNew().setColumnProperty(columnName.trim(), String.class.getName()).setTitle(map.get(columnName)).setStyle(groupStyle).setWidth(clmWidth.get((i -
                                                                                                                                                                                                  1))).build();
                            allColumn.add(columnNm);
                            groupcolumn.add(columnNm);
                            grpcolmnPair.put(columnName, columnNm);
                            ++counter;
                            System.out.println("Selected Column Names are: " + columnName + "   " + columnNm +
                                               "-----");
                        } else {
                            if (i == counter) {
                                System.out.println("Came in the first Column 12");
                                columnNm =
                                        ColumnBuilder.getNew().setColumnProperty(columnName.trim(), String.class.getName()).setTitle(map.get(columnName)).setStyle(detailStyleFirstLeft).setWidth(clmWidth.get((i -
                                                                                                                                                                                                                1))).build();
                            } else if ((counter == grpnames.size() + 1 && i == numColumns) ||
                                       (i == (numColumns - (grpnames.size() + 1 - counter)))) {
                                System.out.println("Last column type 12");
                                columnNm =
                                        ColumnBuilder.getNew().setColumnProperty(columnName.trim(), String.class.getName()).setTitle(map.get(columnName)).setStyle(detailStyleLastLeft).setWidth(clmWidth.get((i -
                                                                                                                                                                                                               1))).build();
                                if (clmAllSum.size() > 0) {
                                    drb.addGlobalFooterVariable(columnNm, DJCalculation.SYSTEM,
                                                                new StyleBuilder(true).setBorderRight(Border.THIN).setBorderTop(Border.THIN).setBorderBottom(Border.THIN).build());
                                }

                            } else {
                                columnNm =
                                        ColumnBuilder.getNew().setColumnProperty(columnName.trim(), String.class.getName()).setTitle(map.get(columnName)).setStyle(detailStyleLeft).setWidth(clmWidth.get((i -
                                                                                                                                                                                                           1))).build();
                                System.out.println("Last column type 12 and column is: " + columnName + "   " +
                                                   columnNm + "-----");
                                if (clmAllSum.size() > 0) {
                                    drb.addGlobalFooterVariable(columnNm, DJCalculation.SYSTEM,
                                                                new StyleBuilder(true).setBorderTop(Border.THIN).setBorderBottom(Border.THIN).build());
                                }
                            }
                            allColumn.add(columnNm);

                        }
                        //drb.addGlobalFooterVariable(columnNm, DJCalculation.SYSTEM, new StyleBuilder(true).setBorderBottom(Border.THIN).setBorderTop(Border.THIN).build());

                    } else if (type == 93) {
                        if (flag == true) {
                            System.out.println("Came in the first Column 93");
                            columnNm =
                                    ColumnBuilder.getNew().setColumnProperty(columnName.trim(), Date.class.getName()).setTitle(map.get(columnName)).setPattern("dd-MM-yyyy").setStyle(groupStyle).setWidth(clmWidth.get((i -
                                                                                                                                                                                                                         1))).build();
                            allColumn.add(columnNm);
                            groupcolumn.add(columnNm);
                            grpcolmnPair.put(columnName, columnNm);
                            ++counter;
                        } else {
                            if (i == counter) {
                                columnNm =
                                        ColumnBuilder.getNew().setColumnProperty(columnName.trim(), Date.class.getName()).setTitle(map.get(columnName)).setStyle(detailStyleFirstLeft).setPattern("dd-MM-yyyy").setWidth(clmWidth.get((i -
                                                                                                                                                                                                                                       1))).build();
                            } else if ((counter == grpnames.size() + 1 && i == numColumns) ||
                                       (i == (numColumns - (grpnames.size() + 1 - counter)))) {
                                System.out.println("Last column type 93");
                                columnNm =
                                        ColumnBuilder.getNew().setColumnProperty(columnName.trim(), Date.class.getName()).setTitle(map.get(columnName)).setPattern("dd-MM-yyyy").setStyle(detailStyleLastLeft).setWidth(clmWidth.get((i -
                                                                                                                                                                                                                                      1))).build();
                                if (clmAllSum.size() > 0) {
                                    drb.addGlobalFooterVariable(columnNm, DJCalculation.SYSTEM,
                                                                new StyleBuilder(true).setBorderTop(Border.THIN).setBorderRight(Border.THIN).setBorderBottom(Border.THIN).build());
                                }
                            } else {
                                columnNm =
                                        ColumnBuilder.getNew().setColumnProperty(columnName.trim(), Date.class.getName()).setTitle(map.get(columnName)).setPattern("dd-MM-yyyy").setStyle(detailStyleLeft).setWidth(clmWidth.get((i -
                                                                                                                                                                                                                                  1))).build();
                                if (clmAllSum.size() > 0) {
                                    drb.addGlobalFooterVariable(columnNm, DJCalculation.SYSTEM,
                                                                new StyleBuilder(true).setBorderTop(Border.THIN).setBorderBottom(Border.THIN).build());
                                }
                            }
                            allColumn.add(columnNm);
                        }

                    }
                    drb.addColumn(columnNm);
                    
                }


                System.out.println("toal size of groups:" + grpnames.size());
                for (int i = 0; i < grpnames.size(); i++) {

                    System.out.println("Group Name iS:  " + grpnames.get(i));
                    GroupBuilder gb1 = new GroupBuilder();
                    gb1.setAllowSplitting(true, true);
                    gb1.setReprintHeaderOnEachPage(false);
                    if (clmAllSum.size() > 0) {
                        gb1.setFooterLabel(new DJGroupLabel(map.get(grpnames.get(i)) + " wise Total: ",
                                                            new StyleBuilder(true).setHorizontalAlign(HorizontalAlign.LEFT).setTextColor(Color.black).setBackgroundColor(Color.red).setPaddingLeft(5).setPaddingTop(5).setBorderLeft(Border.THIN).setBorderTop(Border.THIN).setBorderBottom(Border.THIN).build(),
                                                            LabelPosition.LEFT));
                    }

                    Set<Map.Entry<String, AbstractColumn>> entrySet = grpcolmnPair.entrySet();
                    Iterator<Map.Entry<String, AbstractColumn>> iterator_2 = entrySet.iterator();

                    while (iterator_2.hasNext()) {
                        Map.Entry<String, AbstractColumn> entry = iterator_2.next();

                        if (entry.getKey().equals(grpnames.get(i))) {
                            gb1.setCriteriaColumn((PropertyColumn)entry.getValue());

                            //gb1.setGroupLayout(GroupLayout.VALUE_IN_HEADER); // tells the group how to be shown, there are many posibilities, see the GroupLayout for more.
                            gb1.setGroupLayout(GroupLayout.VALUE_IN_HEADER_WITH_HEADERS_AND_COLUMN_NAME);

                            //gb1.addColumnHeaderStyle(entry.getValue(), new StyleBuilder(true).set)
                            //grpcount++;
                        }
                    }
                    //int grpcount = 0;

                    // System.out.println("Total Grp count is:  " + grpcount);
                    for (int k = 0; k < columnNameSum.size(); k++) {
                        System.out.println("Group Name is: " + grpnames.get(i) + " count: " + i);
                        int grpLastcount = 0;
                        int grpcount = 0;
                        for (int j = 0; j < allColumn.size(); j++) {
                            System.out.println("Column Name is: " + columnNameList.get(j) + " and abstratct is: " +
                                               allColumn.get(j) + " count: " + j);

                            /* if (columnNameList.get(j).equalsIgnoreCase(grpnames.get(i)))
                            {
                                grpcount++; grp count variable is not using
                            } */
                            if (grpnames.contains(columnNameList.get(j))) {
                                grpLastcount++;
                            }

                            if ((columnNameList.get(j).equalsIgnoreCase(columnNameSum.get(k)))) {

                                System.out.println("inside group" + groupcolumn.get(i));
                                System.out.println("Column Name is:  " + grpWise);

                                if (columnNameType.get(j) == 2) {
                                    // gb1.addHeaderVariable(allColumn.get(j), DJCalculation.SUM, headerVariables);
                                    if (clmAllSum.size() > 0) {

                                        //if (j + 1 == grpcount) {
                                        if ((grpLastcount == 0 && j == 0) || j - grpLastcount == 0) {
                                            System.out.println("Came in counter If");

                                        } else if (j == (allColumn.size() - (grpnames.size() + 1 - grpLastcount))) {
                                            gb1.addFooterVariable(allColumn.get(j), DJCalculation.SUM,
                                                                  headerVariables1);
                                            System.out.println("Came in the for if for sum:  " +
                                                               columnNameList.get(j));
                                        } else {
                                            gb1.addFooterVariable(allColumn.get(j), DJCalculation.SUM,
                                                                  headerVariables);
                                        }
                                    }

                                    /* gb1.setFooterLabel(new DJGroupLabel("Total: ",
                                                                        new StyleBuilder(true).setHorizontalAlign(HorizontalAlign.LEFT).setBackgroundColor(Color.red).setPaddingLeft(5).setPaddingTop(5).setBorderLeft(Border.THIN).build(),
                                                                        LabelPosition.LEFT)); */

                                    //    gb1.setDefaultFooterVariableStyle(new StyleBuilder(true).setBackgroundColor(Color.BLUE).setBorder(Border.THIN).setHorizontalAlign(HorizontalAlign.CENTER).build());
                                }
                            } else {

                                //if (!columnNameList.get(j).equalsIgnoreCase(grpnames.get(i))) {
                                if (!grpnames.contains(columnNameList.get(j))) {
                                    System.out.println("came here for clmn :" + columnNameList.get(j) +
                                                       " and value of j : " + j + "condi " +
                                                       ((grpcount == 0 && j == 0) || j - grpcount == 0) + " val " +
                                                       grpcount + "  " + grpLastcount);
                                    if (clmAllSum.size() > 0) {

                                        System.out.println("value of coun and j is:  " + (j + 1) + "==" + grpcount);

                                        //if (j + 1 == grpcount) {
                                        //if ((grpcount == 0 && j == 0) || j - grpcount == 0) {
                                        if ((grpLastcount == 0 && j == 0) || j - grpLastcount == 0) {
                                            System.out.println("Came in counter else");
                                            System.out.println("Came in the for if for String:  " +
                                                               columnNameList.get(j));
                                        } else if (j == (allColumn.size() - (grpnames.size() + 1 - grpLastcount))) {
                                            System.out.println("Came in the for if for:  " + columnNameList.get(j));
                                            System.out.println("Value is: " + allColumn.size() + " " +
                                                               grpnames.size() + " " + grpLastcount);
                                            gb1.addFooterVariable(allColumn.get(j), DJCalculation.SYSTEM, myVar1);
                                        } else {
                                            System.out.println("Came in the for if for 1:  " + columnNameList.get(j));
                                            gb1.addFooterVariable(allColumn.get(j), DJCalculation.SYSTEM, myVar);
                                        }
                                    }
                                    // gb1.setDefaultFooterVariableStyle(new StyleBuilder(true).setBackgroundColor(Color.BLUE).setBorder(Border.DOTTED).build());


                                    /* System.out.println("The selected Colum is: "+columnNameList.get(j));
                                    gb1.addFooterVariable(allColumn.get(j), DJCalculation.SYSTEM, myVar); */
                                }
                            }
                        }
                    }
                    //gb1.setFooterHeight(30);
                    drb = drb.addGroup(gb1.build()); //This will build the group and return a DJGroup :-)
                }
                if (grpnames.size() > 0) {
                    drb.setPrintColumnNames(false);
                }
            
                //drb.setHeaderHeight(20);
                drb.setTitleStyle(new StyleBuilder(true).setHorizontalAlign(HorizontalAlign.RIGHT).build());
                drb.setTitle("Dynamic Report");
                drb.setDetailHeight(20);
                drb.setAllowDetailSplit(true);
                drb.setQuery(query, DJConstants.QUERY_LANGUAGE_SQL);
                System.out.println("drb  " + drb);
                //drb.setPrintBackgroundOnOddRows(true);
                if (pageFormat.equalsIgnoreCase("p")) {
                    System.out.println("Page Format is Portrait");
                    drb.setPageSizeAndOrientation(Page.Page_A4_Portrait());
                } else {
                    System.out.println("Page Format is LandScape");
                    drb.setPageSizeAndOrientation(Page.Page_A4_Landscape());
                }
                drb.setUseFullPageWidth(true); //we tell the report to use the full width of the page. this rezises
                //the columns width proportionally to meat the page width.

                DynamicReport dr = drb.build(); //Finally build the report!
                System.out.println(" resultSetDataSource " + resultSetDataSource);
                System.out.println("dr " + dr);

                JasperPrint jp =
                    DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), resultSetDataSource);
                System.out.println("------------------------------1");
                //generating in pdf formet
                //   JasperExportManager.exportReportToPdfStream(jp, op);
                JRExporter exporter=null;
                System.out.println("file type is "+fileType);
                if("PDF".equalsIgnoreCase(fileType)){
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + ReportName + ".pdf\"");
                exporter = new JRPdfExporter();
                exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, true);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, op);
                } else if ("xls".equalsIgnoreCase(fileType)) {
                response.setContentType("application/csv");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + ReportName + ".csv\"");

                exporter = new JRCsvExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, op); 
                }
                exporter.exportReport();

                System.out.println("report generated sucessfully ");

                rst.close();
                stmt.close();
                con.close();
                op.close();

            } catch (SQLException ex) {
                System.out.println("rx " + ex);
                ex.printStackTrace();
            } catch (ColumnBuilderException e) {
                System.out.println("rx " + e);
                e.printStackTrace();
            } catch (JRException e) {
                System.out.println("rx " + e);
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
        }
    }
}
