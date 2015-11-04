package barcodewebserviceapp.picklist;

import barcodewebserviceapp.utilities.BarCodeService;

import com.ess.conn.DBConnection;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MalformedObjectNameException;
import javax.management.ReflectionException;

import javax.naming.NamingException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("pickdetail")
public class PickListDetail {
    public PickListDetail() {
    }

    /**
     * @param data
     * @return
     */
    @POST
    @Path("/getPickListBarcodeWS")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getPickListBarcodeWS(String data) {

        BarCodeService pk = new BarCodeService();
        JsonObject object = new JsonObject();
        JsonParser parser = new JsonParser();
        object = (JsonObject) parser.parse(data);


        String query =
            "SELECT a.PRCS_ID,\n" + "  a.ATT_ID,\n" + "  a.COL_NAME,\n" + "  b.STRT_POS\n" +
            "FROM APP$GLBL$BC$PRCS$ATT a,\n" + "  APP$BC$PRF$ATT b\n" + "WHERE a.PRCS_ID=1\n" +
            "AND a.PRCS_ID  =b.PRCS_ID\n" + "AND a.ATT_ID   = b.ATT_ID\n AND b.CLD_ID = ?\n" + "And b.SLOC_ID= ?\n" +
            "AND b.HO_ORG_ID=?\n" + "AND b.ORG_ID=?" + "ORDER BY b.STRT_POS";


        Map<String, Map> pickList = new HashMap<String, Map>();
        Map add = new LinkedHashMap();
        add.put("status", "false");
        add.put("message", "Record Not Found !!");
        add.put("address", "");
        add.put("productDetails", "");
        add.put("customerDetails", "");

        pickList.put("pickList", add);

        try {

            String barCode = object.get("BarCode").getAsString();
            String CldId = object.get("CldId").getAsString();
            String HoOrgId = object.get("HoOrgId").getAsString();
            Integer SlocId = object.get("SlocId").getAsInt();
            String OrgId = object.get("OrgId").getAsString();

            String[] split = barCode.split("~");

            DBConnection AppDB = DBConnection.getDSConnection("APPDS");
            Connection con = AppDB.getConnection();
            try {
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, CldId);
                ps.setInt(2, SlocId);
                ps.setString(3, HoOrgId);
                ps.setString(4, OrgId);
                ResultSet rs = ps.executeQuery();

                List address = new ArrayList();
                Map<String, String> prdt = new LinkedHashMap<String, String>();

                prdt.put("id", "");
                prdt.put("name", "");
                prdt.put("serial", "");
                prdt.put("price", "");
                prdt.put("quantity", "");

                Map<String, String> custDet = new HashMap<String, String>();
                custDet.put("id", "");
                custDet.put("name", "");

                String whId = null;
                String srno = null;
                String itmNm = null;
                String serFlg = "N";
                String nameForChk = null;
                int pos = 0;
                boolean chk = false;
                int count = 0;
                boolean useSer = false;
                boolean validRecord = true;

                while (rs.next()) {
                    count++;
                    String clNm = rs.getString("COL_NAME");
                    System.out.println("clm_nm is: " + clNm);
                    nameForChk = null;
                    if (clNm.equalsIgnoreCase("ITM_ID") && split.length > pos) {
                        //    System.out.println(" " + no + " -- " + CldId + " -- " + HoOrgId + " -- " + SlocId);
                        String no = split[pos];
                        Map<String, String> itmMap = pk.getItemName(no, CldId, HoOrgId, SlocId, con);
                        String nm = itmMap.get("itemName");
                        serFlg = itmMap.get("serFlg");
                        //String nm = pk.getItemName(no, CldId, HoOrgId, SlocId, con);
                        //       System.out.println("----> " + nm);

                        if (nm != null) {
                            prdt.put("id", no);
                            prdt.put("name", nm);
                            itmNm = nm;
                            chk = true;
                            nameForChk = nm;
                        }
                        pos++;
                    } else if (clNm.equalsIgnoreCase("ITM_PRICE") && split.length > pos) {
                        String no = split[pos];
                        if (prdt.size() > 0) {
                            prdt.put("price", no);
                            chk = true;
                            nameForChk = no;
                        }
                        pos++;
                    } else if (clNm.equalsIgnoreCase("SO_ITM_QTY") && split.length > pos) {
                        String no = split[pos];
                        if (prdt.size() > 0) {
                            prdt.put("quantity", no);
                            chk = true;
                            nameForChk = no;
                        }
                        pos++;
                    } else if (clNm.equalsIgnoreCase("EO_ID") && split.length > pos) {
                        String no = split[pos];
                        String nm = pk.getEONm(Integer.parseInt(no), CldId, HoOrgId, SlocId, OrgId, con);
                        if (nm != null) {
                            custDet.put("id", (no == null ? "" : no));
                            custDet.put("name", (nm == null ? "" : nm));
                            chk = true;
                            nameForChk = nm;
                        }
                        pos++;
                    } else if (clNm.equalsIgnoreCase("WH_ID") && split.length > pos) {
                        String no = split[pos];
                        String name = pk.getWhName(no, CldId, HoOrgId, SlocId, OrgId, con);
                        if (name != null) {
                            Map m1 = new LinkedHashMap();
                            m1.put("id", no);
                            m1.put("name", "Warehouse");
                            m1.put("description", name);
                            address.add(m1);
                            chk = true;
                            nameForChk = name;
                        }
                        whId = no;
                        pos++;
                    } else if (clNm.equalsIgnoreCase("LOT_ID") && split.length > pos) {
                        String no = split[pos];
                        Map m1 = new LinkedHashMap();
                        m1.put("id", no);
                        m1.put("name", "Lot");
                        m1.put("description", no);
                        address.add(m1);
                        chk = true;
                        nameForChk = no;
                        pos++;
                    } else if (clNm.equalsIgnoreCase("BIN_ID") && split.length > pos) {
                        String no = split[pos];
                        String name = pk.getBinNm(no, whId, CldId, SlocId, OrgId, con);
                        if (name != null) {
                            Map m1 = new LinkedHashMap();
                            m1.put("id", no);
                            m1.put("name", "Bin");
                            m1.put("description", name);
                            address.add(m1);
                            chk = true;
                            nameForChk = name;
                        }
                        pos++;
                    } else if (useSer = clNm.equalsIgnoreCase("SR_NO")) {
                        if (serFlg.equals("Y") && split.length == count) {
                            String no = split[pos];
                            if (itmNm != null) {
                                Map m1 = new LinkedHashMap();
                                m1.put("id", no);
                                m1.put("name", "Serial");
                                m1.put("description", itmNm);
                                address.add(m1);
                                srno = no;
                                chk = true;
                                nameForChk = no;
                            }
                            pos++;
                        } else {
                            nameForChk = "";
                        }
                    }

                    if (nameForChk == null) {
                        validRecord = false;
                        break;
                    }
                }
                prdt.put("serial", (srno == null ? "" : srno));

                /*  if (address.size() == 0 && prdt.size() == 0 && custDet.size() == 0) {
                add.put("status", "false");
                add.put("message", "Record Not Found !!");
            } else
*/
                System.out.println("use ser :" + useSer + " -- " + split.length + " -- " + count + " -- " + serFlg);
                if (validRecord &&
                    ((!useSer && split.length == count) ||
                     (useSer && serFlg.equalsIgnoreCase("N") && split.length == (count - 1)) ||
                     (useSer && serFlg.equalsIgnoreCase("Y") && split.length == count))) {
                    if (chk) {
                        add.put("status", "true");
                        add.put("message", "Record Found !!");
                    }
                    add.put("address", address);
                    add.put("productDetails", prdt);
                    add.put("customerDetails", custDet);
                } else {
                    add.put("message", "Invalid Barcode !!");
                }
                rs.close();
                ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //   System.out.println("data is: \n" + pickList);
        return new Gson().toJson(pickList).toString();
    }
}
