package barcodewebserviceapp.sotcktake;

import barcodewebserviceapp.utilities.BarCodeService;

import com.ess.conn.DBConnection;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;

import java.sql.Connection;

import java.sql.DatabaseMetaData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

@Path("stockTake")
public class StockTakeDetail {
    public StockTakeDetail() {
    }

    @POST
    @Path("/stockTakebarcodeInfoWS")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String stockTakebarcodeInfoWS(String data) {
        System.out.println("Come");
        Map map = new LinkedHashMap();
        map.put("status", "false");
        map.put("message", "Record not Found !!");
        map.put("id", "");
        map.put("name", "");
        map.put("description", "");

        BarCodeService pk = new BarCodeService();
        JsonObject object = new JsonObject();
        JsonParser parser = new JsonParser();


        String name = null;
        boolean useBin = false;
        try {
            object = (JsonObject) parser.parse(data);

            String type = object.get("type").getAsString();
            String barCode = object.get("BarCode").getAsString();
            String CldId = object.get("CldId").getAsString();
            String HoOrgId = object.get("HoOrgId").getAsString();
            Integer SlocId = object.get("SlocId").getAsInt();
            String OrgId = object.get("OrgId").getAsString();
            DBConnection AppDB = DBConnection.getDSConnection("APPDS");
            Connection con = AppDB.getConnection();
            try {
                if (type.equalsIgnoreCase("warehouse")) {
                    List l1 = new ArrayList();
                    Map lot = new LinkedHashMap();
                    lot.put("item", "Lot");
                    l1.add(lot);

                    map.put("address", l1);
                    name = pk.getWhName(barCode, CldId, HoOrgId, SlocId, OrgId, con);
                    useBin = pk.isOrgUseBinMM(CldId, SlocId, OrgId, con);
                    if (name != null) {
                        map.put("id", barCode);
                        map.put("name", "Warehouse");
                        map.put("description", name);
                        if (useBin) {
                            Map bin = new HashMap();
                            bin.put("item", "Bin");
                            l1.add(bin);
                        }
                    }
                } else if (type.equalsIgnoreCase("item")) {
                    Map itemMap = pk.getItemName(barCode, CldId, HoOrgId, SlocId, con);
                    if (itemMap.get("itemName") != null) {

                        name = itemMap.get("itemName").toString();
                        String serFlg = itemMap.get("serFlg").toString();

                        map.put("id", barCode);
                        map.put("name", "Item");
                        map.put("description", name);
                        map.put("unit", (itemMap.get("unit") != null ? itemMap.get("unit").toString() : ""));
                        if (serFlg != null) {
                            map.put("serial", serFlg);
                        } else {
                            map.put("serial", "N");
                        }
                    }
                } else if (type.equalsIgnoreCase("bin")) {
                    String whId = object.get("warehouseId").getAsString();
                    name = pk.getBinNm(barCode, whId, CldId, SlocId, OrgId, con);
                    if (name != null) {
                        map.put("id", barCode);
                        map.put("name", "Bin");
                        map.put("description", name);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (name != null) {
            map.put("status", "true");
            map.put("message", "Record Found !!");
        }
        //return name;
        // System.out.println("Data: " + map);
        return new Gson().toJson(map).toString();
    }

    /**
     * @param data
     * @return
     */
    @POST
    @Path("getStkTakeNoWS")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getStkTakeNoWS(String data) {
        BarCodeService pk = new BarCodeService();
        JsonObject object = new JsonObject();
        JsonParser parser = new JsonParser();
        object = (JsonObject) parser.parse(data);

        Map map = new LinkedHashMap();
        map.put("status", "false");
        map.put("message", "Record not found !!");
        map.put("stkTake", "");

        try {
            DBConnection AppDB = DBConnection.getDSConnection("MMDS");
            Connection con = AppDB.getConnection();
            try {
                String CldId = object.get("CldId").getAsString();
                String HoOrgId = object.get("HoOrgId").getAsString();
                Integer SlocId = object.get("SlocId").getAsInt();

                String OrgId = object.get("OrgId").getAsString();
                List<Map> list = pk.getStkTakeNo(CldId, SlocId, HoOrgId, OrgId, con);
                //System.out.println("list: " + list);
                if (list.size() > 0) {
                    map.put("status", "true");
                    map.put("message", "Record found !!");
                    map.put("stkTake", list);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Gson().toJson(map).toString();
    }

    @POST
    @Path("updateStkTakeDataWS")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String updateData(String data) {
        System.out.println("Data Found : " + data);
        BarCodeService pk = new BarCodeService();
        JsonObject object = new JsonObject();
        JsonParser parser = new JsonParser();
        object = (JsonObject) parser.parse(data);
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("status", "false");
        map.put("message", "Invalid Record !!");
        try {

            String CldId = object.get("CldId").getAsString();
            String HoOrgId = object.get("HoOrgId").getAsString();
            Integer SlocId = object.get("SlocId").getAsInt();
            String OrgId = object.get("OrgId").getAsString();
            JsonArray array = object.get("product item").getAsJsonArray();
            Iterator<JsonElement> itr = array.iterator();
            String whId = null;
            String docId = null;

            while (itr.hasNext()) {
                JsonObject obj = itr.next().getAsJsonObject();
                String str = obj.get("type").getAsString();
                String str1 = obj.get("id").getAsString();
                if (str.equalsIgnoreCase("warehouseId")) {
                    whId = str1;
                } else if (str.equalsIgnoreCase("docId")) {
                    docId = str1;
                }
                System.out.println("--> " + str + " -- " + str1);
            }


            DBConnection AppDB = DBConnection.getDSConnection("MMDS");
            Connection con = AppDB.getConnection();
            con.setAutoCommit(false);
            try {
                JsonArray array1 = object.get("product").getAsJsonArray();
                Iterator<JsonElement> itr1 = array1.iterator();
                while (itr1.hasNext()) {
                    JsonObject obj = itr1.next().getAsJsonObject();
                    //String lotId = obj.get("Lot").getAsString();
                    //String binId = obj.get("Bin").getAsString();

                    String lotId = "0";
                    String binId = "0";

                    JsonArray asJsonArray = obj.get("inventory").getAsJsonArray();
                    Iterator<JsonElement> iterator = asJsonArray.iterator();
                    while (iterator.hasNext()) {
                        JsonObject asJsonObject = iterator.next().getAsJsonObject();
                        String type = asJsonObject.get("type").getAsString();
                        if (type.equalsIgnoreCase("Lot")) {
                            lotId = asJsonObject.get("id").getAsString();
                        } else if (type.equalsIgnoreCase("Bin")) {
                            binId = asJsonObject.get("id").getAsString();
                        }
                    }
                    String itemId = obj.get("item").getAsString();
                    String srNo = obj.get("serial").getAsString();
                    String unit = obj.get("unit").getAsString();
                    double phyQty = obj.get("phyQty").getAsDouble();
                    double scrapQty = obj.get("scrpQty").getAsDouble();
                    double rwblQty = obj.get("rwkQty").getAsDouble();

                    /*  System.out.println("--> " + str1 + " -- " + str2 + " -- " + str3 + " -- " + str4 + " -- " + str5 +
                                   "  -- " + str6 + " -- " + str7 + " -- " + str8); */

                    System.out.println("-->> " + lotId + " -- " + binId + " -- " + itemId + " -- " + srNo + " -- " +
                                       unit + " -- " + phyQty + " -- " + scrapQty + " -- " + rwblQty);
                    int flg =
                        pk.updateMMStkTakeBc(con, CldId, SlocId, HoOrgId, OrgId, whId, docId, lotId, binId, itemId,
                                             (srNo.length() > 0 ? srNo : "0"), unit, phyQty, scrapQty, rwblQty);
                    System.out.println("update  :: " + flg);
                }
            } catch (Exception e) {
                e.printStackTrace();
                con.rollback();
                con.close();
                return new Gson().toJson(map).toString();
            }
            con.commit();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("status", "true");
        map.put("message", "Record Saved Successfully !!");
        return new Gson().toJson(map).toString();
    }
}
