package ebiztokenapp;

import com.ess.conn.DBConnection;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import com.google.gson.JsonParser;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.LinkedHashMap;
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
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("tokenWS")
public class EbizToken {
    public EbizToken() {
    }

    @POST
    @Path("/authenticationWF/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getItmListWF(String data) {
        System.out.println("Data Found : " + data);

        String query =
            "select  \n" + "ws.CLIENT_WLS_IP, \n" + "ws.CLIENT_WLS_PORT \n" + "from \n" +
            "APP.APP$CLIENT$REGN reg, \n" + "APP.APP$CLIENT$WLS ws \n" + "WHERE \n" +
            "ws.CLIENT_ID = reg.CLIENT_ID \n" + "AND reg.CLIENT_TOKEN = ?";

        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("srvrId", "null");
        map.put("domain", "null");
        map.put("port", "null");
        map.put("usr", "Invalid");

        DBConnection AppDB;
        try {

            JsonObject object = new JsonObject();
            JsonParser parser = new JsonParser();
            object = (JsonObject) parser.parse(data);
            String tokenId = object.get("token").getAsString();

            AppDB = DBConnection.getDSConnection("APPDS");
            Connection con = AppDB.getConnection();
            try {
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, tokenId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String ip = rs.getString(1);
                    String port = rs.getString(2);
                    map.put("srvrId", ip);
                    map.put("domain", "null");
                    map.put("port", port);
                    map.put("cldId", "0000");
                    map.put("slocID", "1");
                    map.put("hoNm", "Eastern Software System Pvt.Ltd.");
                    map.put("usr", "Valid");
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
        return new Gson().toJson(map).toString();
    }
}

