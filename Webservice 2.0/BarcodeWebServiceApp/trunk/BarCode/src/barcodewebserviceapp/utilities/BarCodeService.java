package barcodewebserviceapp.utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BarCodeService {
    public BarCodeService() {
        super();
    }

    public Map<String, String> getItemName(String ItmId, String CldId, String HoOrgId, Integer SlocId, Connection con) {
        String itmName = null;
        String serFlg = "N";
        String uom = null;
        Map<String, String> map = new LinkedHashMap<String, String>();
        String query =
            "SELECT ITM_ID,\n" + "  ITM_DESC,\n" + " UOM_BASIC,\n" + "  SERIALIZED_FLG\n" + "FROM APP$ITM$PRF\n" +
            "WHERE CLD_ID  =?\n" + "AND SLOC_ID   =?\n" + "AND HO_ORG_ID =?\n" + "AND ITM_ID    =?\n" +
            "AND ACTV      ='Y'";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, CldId);
            ps.setInt(2, SlocId);
            ps.setString(3, HoOrgId);
            ps.setString(4, ItmId);
            ResultSet executeQuery = ps.executeQuery();

            if (executeQuery.next()) {
                itmName = executeQuery.getString("ITM_DESC");
                serFlg = executeQuery.getString("SERIALIZED_FLG");
                uom = executeQuery.getString("UOM_BASIC");
            }
            executeQuery.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("itemName", itmName);
        map.put("serFlg", serFlg);
        map.put("unit", uom);

        return map;
    }


    public String getWhName(String WhId, String CldId, String HoOrgId, Integer SlocId, String OrgId, Connection con) {
        String query =
            "SELECT WH_ID, WH_NM\n" + "FROM APP$WH$ORG\n" + "WHERE CLD_ID =?\n" + "AND SLOC_ID  =?\n" +
            "AND HO_ORG_ID=?\n" + "AND ORG_ID   =?\n" + "AND WH_ID  =?";
        String whNm = null;
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, CldId);
            ps.setInt(2, SlocId);
            ps.setString(3, HoOrgId);
            ps.setString(4, OrgId);
            ps.setString(5, WhId);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                whNm = res.getString("WH_NM");
            }
            res.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return whNm;
    }

    public String getEONm(Integer EoId, String CldId, String HoOrgId, Integer SlocId, String OrgId, Connection con) {
        String query =
            "SELECT EO_ID, EO_NM\n" + "FROM APP$EO\n" + "WHERE EO_SLOC_ID =?\n" + "AND EO_ID        =?\n" +
            "AND EO_CLD_ID    =?\n" + "AND EO_HO_ORG_ID =?\n" + "AND EO_ORG_ID    =?";
        String name = null;
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, SlocId);
            ps.setInt(2, EoId);
            ps.setString(3, CldId);
            ps.setString(4, HoOrgId);
            ps.setString(5, OrgId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                name = rs.getString("EO_NM");
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }

    public String getBinNm(String binId, String WhId, String CldId, Integer SlocId, String OrgId, Connection con) {
        String query =
            "SELECT BIN_ID ,\n" + "  BIN_NM\n" + "FROM APP$BIN\n" + "WHERE CLD_ID=?\n" + "AND SLOC_ID =?\n" +
            "AND ORG_ID  =?\n" + "AND WH_ID   =?\n" + "AND BIN_ID  =?\n";
        String name = null;
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, CldId);
            ps.setInt(2, SlocId);
            ps.setString(3, OrgId);
            ps.setString(4, WhId);
            ps.setString(5, binId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                name = rs.getString("BIN_NM");
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }

    public boolean isOrgUseBinMM(String CldId, int SlocId, String OrgId, Connection con) {
        String query =
            "SELECT USE_BIN\n" + "FROM ORG$MM$PRF\n" + "WHERE CLD_ID=?\n" + "AND SLOC_ID =?\n" + "AND ORG_ID  =?";
        String name = null;
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, CldId);
            ps.setInt(2, SlocId);
            ps.setString(3, OrgId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                name = rs.getString("USE_BIN");
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (name == null || name.equalsIgnoreCase("n")) {
            return false;
        }
        return true;
    }

    public List<Map> getStkTakeNo(String CldId, int SlocId, String HoOrgId, String OrgId, Connection con) {
        String query =
            "SELECT DOC_ID, STK_TAKE_NO\n" + "FROM MM$STK$TAKE\n" + "WHERE CLD_ID     =?\n" + "AND sloc_id      =?\n" +
            "AND org_id       =?\n" + "AND AUTH_STAT    ='N'\n" + "AND STK_TAKE_STAT=418\n" +
            "AND FY_ID        =MM_GET_CUR_FY_ID(CLD_ID,sloc_id,org_id, SYSDATE)\n" + "order by STK_TAKE_NO";
        List<Map> list = new ArrayList<Map>();

        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, CldId);
            ps.setInt(2, SlocId);
            ps.setString(3, OrgId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, String> map = new LinkedHashMap<String, String>();
                map.put("StkTakeNo", rs.getString("STK_TAKE_NO"));
                map.put("DocId", rs.getString("DOC_ID"));
                list.add(map);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int updateMMStkTakeBc(Connection con, String CldId, int SlocId, String HoOrgId, String OrgId, String whId,
                                 String docId, String lotId, String binId, String itemId, String srNo, String unit,
                                 double phyQty, double scrapQty, double rwblQty) throws SQLException {

        String insert =
            "INSERT INTO MM$STK$TAKE$BC (CLD_ID, SLOC_ID, ORG_ID, WH_ID, DOC_ID, LOT_ID, BIN_ID, SR_NO, ITM_ID," +
            "ITM_UOM, PHY_QTY, RWK_QTY, SCRP_QTY) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

        String chkRowExists =
            "select nvl(PHY_QTY,0), nvl(RWK_QTY,0) , nvl(SCRP_QTY,0) from MM.MM$STK$TAKE$BC \n" +
            "WHERE CLD_ID=? and\n" + "SLOC_ID =? and\n" + "ORG_ID=? and\n" + "WH_ID=? and \n" + "DOC_ID=? and\n" +
            "LOT_ID=? and\n" + "BIN_ID=? and\n" + "ITM_ID=? and\n" + "SR_NO =?";

        String updateQuery =
            "UPDATE MM.MM$STK$TAKE$BC\n" + "SET PHY_QTY =?,\n" + "  RWK_QTY   =?,\n" + "  SCRP_QTY  =?\n" +
            "WHERE CLD_ID=?\n" + "AND SLOC_ID =?\n" + "AND ORG_ID  =?\n" + "AND WH_ID   =?\n" + "AND DOC_ID  =?\n" +
            "AND LOT_ID  =?\n" + "AND BIN_ID  =?\n" + "AND ITM_ID  =?\n" + "AND SR_NO   =?";

        PreparedStatement ps;
        boolean exists = false;
        double pqty = 0;
        double rQty = 0;
        double SQty = 0;
        int flg = 0;
        /*  try { */
        PreparedStatement ps1 = con.prepareStatement(chkRowExists);
        ps1.setString(1, CldId);
        ps1.setInt(2, SlocId);
        ps1.setString(3, OrgId);
        ps1.setString(4, whId);
        ps1.setString(5, docId);
        ps1.setString(6, lotId);
        ps1.setString(7, binId);
        ps1.setString(8, itemId);
        ps1.setString(9, srNo);

        ResultSet rs = ps1.executeQuery();
        if (exists = rs.next()) {
            pqty = rs.getDouble(1);
            rQty = rs.getDouble(2);
            SQty = rs.getDouble(3);
        }
        rs.close();
        ps1.close();
        //   System.out.println("exists: " + exists + "-- >" + (!srNo.equalsIgnoreCase("0")));

        //if (((!exists) && srNo.equalsIgnoreCase("0")) || (!(exists && srNo.equalsIgnoreCase("0")))) {
        if (!exists) {
            // insert
            System.out.println("come in insert");
            ps = con.prepareStatement(insert);
            ps.setString(1, CldId);
            ps.setInt(2, SlocId);
            ps.setString(3, OrgId);
            ps.setString(4, whId);
            ps.setString(5, docId);
            ps.setString(6, lotId);
            ps.setString(7, binId);
            ps.setString(8, srNo);
            ps.setString(9, itemId);
            ps.setString(10, unit);
            ps.setDouble(11, phyQty);
            ps.setDouble(12, rwblQty);
            ps.setDouble(13, scrapQty);
            flg = ps.executeUpdate();
            ps.close();
        } else if ((exists)) {
            // update
            // srNo.equalsIgnoreCase("0") &&
            System.out.println("come in update");
            PreparedStatement upd = con.prepareStatement(updateQuery);
            if (srNo.equalsIgnoreCase("0")) {
                upd.setDouble(1, phyQty + pqty);
                upd.setDouble(2, rwblQty + rQty);
                upd.setDouble(3, scrapQty + SQty);
            } else {
                upd.setDouble(1, phyQty);
                upd.setDouble(2, rwblQty);
                upd.setDouble(3, scrapQty);
            }
            upd.setString(4, CldId);
            upd.setInt(5, SlocId);
            upd.setString(6, OrgId);
            upd.setString(7, whId);
            upd.setString(8, docId);
            upd.setString(9, lotId);
            upd.setString(10, binId);
            upd.setString(11, itemId);
            upd.setString(12, srNo);
            flg = upd.executeUpdate();
            upd.close();
        }
        /*  } catch (SQLException e) {
            e.printStackTrace();
        } */
        return flg;
    }
}
