package ebizmobileappwebservice;

import com.ess.conn.DBConnection;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import com.google.gson.JsonParser;

import ebizmobileappwebservice.utilities.EbizUtilities;

import java.sql.Connection;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
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
import javax.ws.rs.core.MediaType;

@Path("ebizWS")
public class EbizLogin {
    public EbizLogin() {
    }

    @POST
    @Path("/loginWS/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String loginWS(String data) {
        System.out.println("Data Found : " + data);
        JsonObject object = new JsonObject();
        JsonParser parser = new JsonParser();
        Map map = new HashMap();
        map.put("status", "N");
        map.put("msg", "No Data Found.");
        try {
            object = (JsonObject) parser.parse(data);
            String cldId = object.get("cldId").getAsString();
            Integer slocId = object.get("slocId").getAsInt();
            String usrNm = object.get("userNm").getAsString();
            String password = object.get("userPwd").getAsString();

            String token = object.get("tknId").getAsString();
            String osNm = object.get("osNm").getAsString();
            String dvcNm = object.get("dvcNm").getAsString();
            Integer flg = object.get("flag").getAsInt();

            DBConnection bConnection = DBConnection.getDSConnection("APPDS");
            Connection con = bConnection.getConnection();
            con.setAutoCommit(false);
            try {
                EbizUtilities uty = new EbizUtilities(bConnection, con);
                String loginWS = uty.loginWS(cldId, slocId, usrNm, password, token, osNm, dvcNm, flg);
                System.out.println("----> " + loginWS);
                if (!loginWS.equalsIgnoreCase("N")) {
                    con.commit();
                    con.close();
                    return loginWS;
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

    @GET
    @Path("/forApprovalWS/{cldId}/{slocId}/{horgId}/{orgId}/{usrId}/{type}/{start}/{end}")
    @Produces(MediaType.APPLICATION_JSON)
    public String forApprovalWS(@PathParam("cldId") String cldId, @PathParam("slocId") int slocId,
                                @PathParam("horgId") String horgId, @PathParam("orgId") String orgId,
                                @PathParam("usrId") String usrId, @PathParam("type") String type,
                                @PathParam("start") String start, @PathParam("end") String end) {
        Map map = new HashMap();
        map.put("status", "N");
        map.put("msg", "No Data Found.");
        map.put("docList", new ArrayList());
        DBConnection bConnection;
        try {
            bConnection = DBConnection.getDSConnection("APPDS");
            Connection con = bConnection.getConnection();
            try {
                EbizUtilities uty = new EbizUtilities(bConnection, con);
                String approvalWS = uty.approvalWS(cldId, slocId, horgId, orgId, usrId, type, start, end);
                if (approvalWS != null && (!approvalWS.equalsIgnoreCase("N"))) {
                    con.close();
                    return approvalWS;
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


    @GET
    @Path("/detailWS/{cldId}/{horgId}/{slocId}/{orgId}/{usrId}/{vouId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String detailWS(@PathParam("cldId") String cldId, @PathParam("horgId") String horgId,
                           @PathParam("slocId") String slocId, @PathParam("orgId") String orgId,
                           @PathParam("usrId") String usrId, @PathParam("vouId") String vouId) {

        String output = "{\"status\":\"N\",\"vouDTL\":[],\"vouLines\":[],\"msg\":\"No Data Found.\"}";
        DBConnection bConnection;
        try {
            bConnection = DBConnection.getDSConnection("APPDS");
            Connection con = bConnection.getConnection();
            try {
                EbizUtilities uty = new EbizUtilities(bConnection, con);
                output = uty.detailWS(cldId, horgId, slocId, orgId, usrId, vouId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

    @GET
    @Path("/getDbAgeingInfo/{cldId}/{orgId}/{usrId}/{btkTyp}")
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("oracle.jdeveloper.webservice.rest.broken-resource-warning")
    public String getDbAgeingInfo(@PathParam("cldId") String cldId, @PathParam("orgId") String orgId,
                                  @PathParam("usrId") int usrId, @PathParam("btkTyp") String btkTyp) {

        Map output = new HashMap();
        DBConnection bConnection;
        try {
            bConnection = DBConnection.getDSConnection("APPDS");
            Connection con = bConnection.getConnection();
            try {
                EbizUtilities uty = new EbizUtilities(bConnection, con);
                output = uty.getDbAgeingInfo(cldId, orgId, btkTyp, usrId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Gson().toJson(output).toString();
    }

    @GET
    @Path("/setBtkRange/{cldId}/{orgId}/{usrId}/{btkTyp}/{r1}/{r2}/{r3}/{r4}")
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("oracle.jdeveloper.webservice.rest.broken-resource-warning")
    public String setBtkRange(@PathParam("cldId") String cldId, @PathParam("orgId") String orgId,
                              @PathParam("usrId") int usrId, @PathParam("btkTyp") String btkTyp,
                              @PathParam("r1") String r1, @PathParam("r2") String r2, @PathParam("r3") String r3,
                              @PathParam("r4") String r4) {
        //   ebizMobileAppAMImpl ebiz = new ebizMobileAppAMImpl();
        String output = null;
        DBConnection bConnection;
        try {
            bConnection = DBConnection.getDSConnection("APPDS");
            Connection con = bConnection.getConnection();
            con.setAutoCommit(false);
            try {
                EbizUtilities uty = new EbizUtilities(bConnection, con);
                output = uty.setBtkRange(cldId, orgId, btkTyp, usrId, r1, r2, r3, r4);
            } catch (Exception e) {
                e.printStackTrace();
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map result = new HashMap();
        if (output.equals("Y")) {
            result.put("status", output);
            result.put("msg", "Data Save Successfully");
        } else {
            result.put("status", output);
            result.put("msg", "Define Bucket Not Save. Please Try Again");
        }
        System.out.println(result);
        return new Gson().toJson(result).toString();
    }


    @GET
    @Path("/getCashPositionInfo/{cldId}/{slocId}/{horgId}/{orgId}")
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("oracle.jdeveloper.webservice.rest.broken-resource-warning")
    public String getCashPositionInfo(@PathParam("cldId") String cldId, @PathParam("slocId") int slocId,
                                      @PathParam("horgId") String horgId, @PathParam("orgId") String orgId) {

        DBConnection bConnection;
        Map output = new HashMap();
        try {
            bConnection = DBConnection.getDSConnection("APPDS");
            Connection con = bConnection.getConnection();
            try {
                EbizUtilities uty = new EbizUtilities(bConnection, con);
                output = uty.getCashPositionInfo(cldId, slocId, horgId, orgId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Gson().toJson(output).toString();
    }


    @GET
    @Path("/getOrgInfoWS/{orgId}/{cldId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getOrgInfo(@PathParam("orgId") String orgId, @PathParam("cldId") String cldId) {

        DBConnection bConnection;
        Map map = new HashMap();
        try {
            bConnection = DBConnection.getDSConnection("APPDS");
            Connection con = bConnection.getConnection();
            try {
                EbizUtilities uty = new EbizUtilities(bConnection, con);
                map = uty.getOrgInfo(orgId, cldId);
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
    @Path("/getCashPositionDetailWS/")
    @Produces(MediaType.MULTIPART_FORM_DATA)
    public String getCashPositionDetail() {
        return "";
    }

    @GET
    @Path("/getNotificationWS/{cldId}/{slocId}/{horgId}/{orgId}/{usrId}/{modId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getNotification(@PathParam("cldId") String cldId, @PathParam("slocId") int slocId,
                                  @PathParam("horgId") String horgId, @PathParam("orgId") String orgId,
                                  @PathParam("usrId") int usrId, @PathParam("modId") String modId) {

        DBConnection bConnection;
        Map map = new HashMap();
        try {
            bConnection = DBConnection.getDSConnection("APPDS");
            Connection con = bConnection.getConnection();
            try {
                EbizUtilities uty = new EbizUtilities(bConnection, con);
                System.out.println("Mod id: isL " + modId);
                map = uty.getNotification(cldId, slocId, horgId, orgId, usrId, modId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String p = new Gson().toJson(map).toString();
        //  System.out.println(p);

        p = p.replace("\\u0026", "&");
        p = p.replace("\\u003d", "=");
        // System.out.println("\n\n\n" + p);
        return p;
        //  return new Gson().toJson(map).toString();
    }


    @POST
    @Path("/alertCountWS/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String alertCountWS(String data) {
        System.out.println("Data Found : " + data);

        DBConnection bConnection;
        Map output = new HashMap();
        try {
            JsonObject object = new JsonObject();
            JsonParser parser = new JsonParser();
            object = (JsonObject) parser.parse(data);

            bConnection = DBConnection.getDSConnection("APPDS");
            Connection con = bConnection.getConnection();
            try {
                EbizUtilities uty = new EbizUtilities(bConnection, con);
                output =
                    uty.getAlertCountWS(object.get("cldId").getAsString(), object.get("slocId").getAsInt(),
                                        object.get("horgId").getAsString(), object.get("orgId").getAsString(),
                                        object.get("usrId").getAsInt());
            } catch (Exception e) {
                e.printStackTrace();
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Output : " + output);
        return new Gson().toJson(output).toString();
    }
}
