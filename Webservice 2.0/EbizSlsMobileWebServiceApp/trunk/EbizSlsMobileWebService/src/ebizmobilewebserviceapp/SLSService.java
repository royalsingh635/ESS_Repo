package ebizmobilewebserviceapp;

import com.ess.conn.DBConnection;

import com.google.gson.Gson;

import com.google.gson.JsonObject;

import com.google.gson.JsonParser;

import ebizmobilewebserviceapp.utilities.SlsUtilities;

import java.sql.Connection;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("slsWS")
public class SLSService {
    public SLSService() {
    }

    @GET
    @Path("/getCatgDisBaseList")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCatgDisBaseList() {
        System.out.println("getCatgDisBaseList");
        Map catgList = new HashMap();
        try {
            DBConnection db = DBConnection.getDSConnection("APPDS");
            Connection con = db.getConnection();
            try {
                SlsUtilities su = new SlsUtilities(db, con);
                catgList = su.getCatgDisBaseList();
            } catch (Exception e) {
                e.printStackTrace();
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Gson().toJson(catgList).toString();
    }


    @GET
    @Path("/getDiscPolicyList/{CldId}/{OrgId}/{HoOrgId}/{SlocId}/{ItmNmStr}/{GrpNmStr}/{eoCatg}/{EoNmStr}/{SalesExecNmStr}/{DiscBasis}/{RowsLowerLimit}/{RowsUpperLimit}")
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("oracle.jdeveloper.webservice.rest.broken-resource-warning")
    public String getDiscPolicyList(@PathParam("CldId") String CldId, @PathParam("OrgId") String OrgId,
                                    @PathParam("HoOrgId") String HoOrgId, @PathParam("SlocId") String SlocId,
                                    @PathParam("ItmNmStr") String ItmNmStr, @PathParam("GrpNmStr") String GrpNmStr,
                                    @PathParam("eoCatg") String eoCatg, @PathParam("EoNmStr") String EoNmStr,
                                    @PathParam("SalesExecNmStr") String SalesExecNmStr,
                                    @PathParam("DiscBasis") String DiscBasis,
                                    @PathParam("RowsLowerLimit") String RowsLowerLimit,
                                    @PathParam("RowsUpperLimit") String RowsUpperLimit) {
        System.out.println("getDiscPolicyList");
        Map catgList = new HashMap();
        try {
            DBConnection db = DBConnection.getDSConnection("SLSDS");
            Connection con = db.getConnection();
            try {
                SlsUtilities su = new SlsUtilities(db, con);
                catgList =
                    su.getDiscPolicyList(CldId, OrgId, HoOrgId, Integer.parseInt(SlocId),
                                         (ItmNmStr.equals("null") ? null : ItmNmStr),
                                         (GrpNmStr.equals("null") ? null : GrpNmStr),
                                         (eoCatg.equals("null") ? null : Integer.parseInt(eoCatg)),
                                         (EoNmStr.equals("null") ? null : EoNmStr),
                                         (SalesExecNmStr.equals("null") ? null : SalesExecNmStr),
                                         (DiscBasis.equals("null") ? null : Integer.parseInt(DiscBasis)),
                                         (RowsLowerLimit.equals("null") ? null : Integer.parseInt(RowsLowerLimit)),
                                         (RowsUpperLimit.equals("null") ? null : Integer.parseInt(RowsUpperLimit)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Gson().toJson(catgList).toString();
    }


    @GET
    @Path("/getApprovalType/{SlocId}/{CldId}/{OrgId}/{HoOrgId}/{UsrId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getApprovalType(@PathParam("SlocId") Integer SlocId, @PathParam("CldId") String CldId,
                                  @PathParam("OrgId") String OrgId, @PathParam("HoOrgId") String HoOrgId,
                                  @PathParam("UsrId") Integer UsrId) {
        Map catgList = new HashMap();
        try {
            DBConnection db = DBConnection.getDSConnection("SLSDS");
            Connection con = db.getConnection();
            try {
                SlsUtilities su = new SlsUtilities(db, con);
                catgList = su.getPendingDocumentForCountMyApproval(SlocId, CldId, OrgId, HoOrgId, UsrId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Gson().toJson(catgList).toString();
    }

    @GET
    @Path("/getDocApprovalList/{SlocId}/{CldId}/{OrgId}/{HoOrgId}/{UsrId}/{wfTyp}/{DocTypeId}/{RowsUpperLimit}/{RowsLowerLimit}")
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("oracle.jdeveloper.webservice.rest.broken-resource-warning")
    public String getDocApprovalList(@PathParam("SlocId") String SlocId, @PathParam("CldId") String CldId,
                                     @PathParam("OrgId") String OrgId, @PathParam("HoOrgId") String HoOrgId,
                                     @PathParam("UsrId") String UsrId, @PathParam("wfTyp") String wfTyp,
                                     @PathParam("DocTypeId") String DocTypeId,
                                     @PathParam("RowsUpperLimit") String RowsUpperLimit,
                                     @PathParam("RowsLowerLimit") String RowsLowerLimit) {
        Map catgList = new HashMap();

        try {
            DBConnection db = DBConnection.getDSConnection("SLSDS");
            Connection con = db.getConnection();
            try {
                SlsUtilities su = new SlsUtilities(db, con);
                catgList =
                    su.getPendingDocumentList(Integer.parseInt(SlocId), CldId, OrgId, HoOrgId,
                                              (UsrId.equals("null") ? null : Integer.parseInt(UsrId)),
                                              (wfTyp.equals("null") ? null : wfTyp),
                                              (DocTypeId.equals("null") ? null : Integer.parseInt(DocTypeId)),
                                              (RowsLowerLimit.equals("null") ? null : Integer.parseInt(RowsLowerLimit)),
                                              (RowsUpperLimit.equals("null") ? null :
                                               Integer.parseInt(RowsUpperLimit)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Gson().toJson(catgList).toString();
    }

    @GET
    // {cldId}/{horgId}/{slocId}/{orgId}/{usrId}/{vouId}
    //  @Path("/detailWS/{CldId}/{HoOrgId}/{SlocId}/{OrgId}/{usrId}/{doctxnId}/{glblDocId}")
    @Path("/detailWS/{cldId}/{slocId}/{horgId}/{orgId}/{usrId}/{glblDocId}/{doctxnId}/")
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("oracle.jdeveloper.webservice.rest.broken-resource-warning")
    public String getPendingDocumentDetails(@PathParam("cldId") String cldId, @PathParam("slocId") int slocId,
                                            @PathParam("horgId") String horgId, @PathParam("orgId") String orgId,
                                            @PathParam("usrId") String usrId, @PathParam("glblDocId") String glblDocId,
                                            @PathParam("doctxnId") String docTxnId) {
        Map catgList = new HashMap();
        catgList.put("status", false);
        catgList.put("message", "Record not found !!");
        try {
            DBConnection db = DBConnection.getDSConnection("SLSDS");
            Connection con = db.getConnection();
            try {
                SlsUtilities su = new SlsUtilities(db, con);
                catgList = su.getDocumentDetails(slocId, cldId, orgId, horgId, docTxnId, null, glblDocId, usrId);
            } catch (Exception e) {

                e.printStackTrace();
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Gson().toJson(catgList).toString();
    }


    @GET
    @Path("/updateSalesOrderDetails/{SlocId}/{CldId}/{OrgId}/{HoOrgId}/{docId}/{itmId}/{qty}/{dicVal}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("oracle.jdeveloper.webservice.rest.broken-resource-warning")
    public String updateSalesOrderDetails(@PathParam("SlocId") String SlocId, @PathParam("CldId") String CldId,
                                          @PathParam("OrgId") String OrgId, @PathParam("HoOrgId") String HoOrgId,
                                          @PathParam("docId") String docId, @PathParam("itmId") String itmId,
                                          @PathParam("qty") String qty, @PathParam("dicVal") String dicVal) {

        Map catgList = new HashMap();
        try {
            DBConnection db = DBConnection.getDSConnection("SLSDS");
            Connection con = db.getConnection();
            con.setAutoCommit(false);
            try {
                SlsUtilities su = new SlsUtilities(db, con);
                System.out.println("Update");
                catgList =
                    su.updateSalesOrderData(Integer.parseInt(SlocId), CldId, OrgId, HoOrgId, docId, itmId, qty, dicVal);
            } catch (Exception e) {
                e.printStackTrace();
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Gson().toJson(catgList).toString();
    }

    @POST
    @Path("/alertCountWS/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String alertCountWS(String data) {
        System.out.println("Data Found : " + data);

        Map output = new HashMap();
        try {
            JsonObject object = new JsonObject();
            JsonParser parser = new JsonParser();
            object = (JsonObject) parser.parse(data);

            DBConnection db = DBConnection.getDSConnection("SLSDS");
            Connection con = db.getConnection();
            con.setAutoCommit(false);
            try {
                SlsUtilities su = new SlsUtilities(db, con);
                output =
                    su.getAlertCountWS(object.get("cldId").getAsString(), object.get("slocId").getAsInt(),
                                       object.get("horgId").getAsString(), object.get("orgId").getAsString(),
                                       object.get("usrId").getAsInt());
            } catch (Exception e) {
                e.printStackTrace();
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Login Output : " + output);
        return new Gson().toJson(output).toString();
    }
}
