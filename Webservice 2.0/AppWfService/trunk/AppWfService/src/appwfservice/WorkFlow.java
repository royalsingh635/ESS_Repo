package appwfservice;

import appwfservice.uti.WorkFlowUtility;

import com.ess.conn.DBConnection;

import com.google.gson.Gson;

import com.google.gson.JsonObject;

import com.google.gson.JsonParser;

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

@Path("workFlowWS")
public class WorkFlow {
    public WorkFlow() {
    }

    @GET
    @Path("/getDocInfoWF/{cldId}/{slocId}/{orgId}/{horgId}/{usrId}/{docId}/{docTypId}/{docTxnId}/{amount}/{wfId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getDocInfo(@PathParam("cldId") String cldId, @PathParam("slocId") int slocId,
                             @PathParam("orgId") String orgId, @PathParam("horgId") String horgId,
                             @PathParam("usrId") int usrId, @PathParam("docId") int docId,
                             @PathParam("docTypId") int docTypId, @PathParam("docTxnId") String docTxnId,
                             @PathParam("amount") String amount, @PathParam("wfId") String wfId) {
        System.out.println("getCatgDisBaseList");
        Map map = new HashMap();
        try {
            DBConnection db = DBConnection.getDSConnection("APPDS");
            Connection con = db.getConnection();
            try {
                WorkFlowUtility uty = new WorkFlowUtility(db, con);
                map = uty.getWorkFlowInfo(cldId, slocId, orgId, horgId, usrId, docId, docTypId, docTxnId, amount, wfId);
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
    @Path("/getDocActionWF/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getDocActionWF(String data) {
        System.out.println("Data Found : " + data);
        String result = "";
        try {
            JsonObject object = new JsonObject();
            JsonParser parser = new JsonParser();
            object = (JsonObject) parser.parse(data);

            DBConnection db = DBConnection.getDSConnection("APPDS");
            Connection con = db.getConnection();
            con.setAutoCommit(false);
            try {
                WorkFlowUtility uty = new WorkFlowUtility(db, con);
                result =
                    uty.docAction(object.get("cldId").getAsString(), object.get("orgId").getAsString(),
                                  object.get("slocId").getAsInt(), object.get("horgId").getAsString(),
                                  object.get("wfId").getAsString(), object.get("docId").getAsInt(),
                                  object.get("docTypId").getAsInt(), object.get("docTxnId").getAsString(),
                                  object.get("amount").getAsString(), object.get("usrId").getAsInt(),
                                  object.get("remark").getAsString(), object.get("usrLvl").getAsInt(),
                                  object.get("usrIdTo").getAsInt(), object.get("usrToLvl").getAsInt(),
                                  object.get("usrAction").getAsString(), object.get("usr").getAsString(),
                                  object.get("module").getAsString(), object.get("doc_no").getAsString(),
                                  object.get("doc_dt").getAsString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map map = new HashMap();
        map.put("status", result);
        return new Gson().toJson(map).toString();
    }
}
