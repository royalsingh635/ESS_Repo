package application.model.services;

import com.google.gson.Gson;

import com.google.gson.JsonObject;

import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/slsWS")
public class CallWS {
    public CallWS() {
        super();
    }


    /**
     * Method that returns a HashMap containg Eo Category list with
     * CatgId as Key
     * CatgNm as Value.
     * @return
     */
    @GET
    @Path("/getCatgDisBaseList")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCatgDisBaseList() {
        System.out.println("getCatgDisBaseList");
        EbizSlsMobileWSAMImpl a = new EbizSlsMobileWSAMImpl();
        Map catgList = a.getCatgDisBaseList();
        return new Gson().toJson(catgList).toString();
    }


    /**
     * @param CldId
     * @param OrgId
     * @param HoOrgId
     * @param SlocId
     * @param ItmNmStr
     * @param GrpNmStr
     * @param eoCatg
     * @param EoNmStr
     * @param SalesExecNmStr
     * @param DiscBasis
     * @param RowsLowerLimit
     * @param RowsUpperLimit
     * @return
     */
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
        EbizSlsMobileWSAMImpl a = new EbizSlsMobileWSAMImpl();
        Map catgList =
            a.getDiscPolicyList(CldId, OrgId, HoOrgId, Integer.parseInt(SlocId),
                                (ItmNmStr.equals("null") ? null : ItmNmStr),
                                (GrpNmStr.equals("null") ? null : GrpNmStr),
                                (eoCatg.equals("null") ? null : Integer.parseInt(eoCatg)),
                                (EoNmStr.equals("null") ? null : EoNmStr),
                                (SalesExecNmStr.equals("null") ? null : SalesExecNmStr),
                                (DiscBasis.equals("null") ? null : Integer.parseInt(DiscBasis)),
                                (RowsLowerLimit.equals("null") ? null : Integer.parseInt(RowsLowerLimit)),
                                (RowsUpperLimit.equals("null") ? null : Integer.parseInt(RowsUpperLimit)));
        return new Gson().toJson(catgList).toString();
    }

    /**
     * @param SlocId
     * @param CldId
     * @param OrgId
     * @param HoOrgId
     * @param UsrId
     * @return
     */
    @GET
    @Path("/getApprovalType/{SlocId}/{CldId}/{OrgId}/{HoOrgId}/{UsrId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getApprovalType(@PathParam("SlocId") Integer SlocId, @PathParam("CldId") String CldId,
                                  @PathParam("OrgId") String OrgId, @PathParam("HoOrgId") String HoOrgId,
                                  @PathParam("UsrId") Integer UsrId) {
        EbizSlsMobileWSAMImpl a = new EbizSlsMobileWSAMImpl();
        Map catgList = a.getPendingDocumentForCountMyApproval(SlocId, CldId, OrgId, HoOrgId, UsrId);
        return new Gson().toJson(catgList).toString();
    }


    /**
     * Method to fetch Documents of the particular
     * @param SlocId
     * @param CldId
     * @param OrgId
     * @param HoOrgId
     * @param UsrId
     * @param wfTyp
     * @param DocTypeId
     * @param RowsLowerLimit
     * @param RowsUpperLimit
     * @return
     */
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
        EbizSlsMobileWSAMImpl a = new EbizSlsMobileWSAMImpl();
        Map catgList =
            a.getPendingDocumentList(Integer.parseInt(SlocId), CldId, OrgId, HoOrgId,
                                     (UsrId.equals("null") ? null : Integer.parseInt(UsrId)),
                                     (wfTyp.equals("null") ? null : wfTyp),
                                     (DocTypeId.equals("null") ? null : Integer.parseInt(DocTypeId)),
                                     (RowsLowerLimit.equals("null") ? null : Integer.parseInt(RowsLowerLimit)),
                                     (RowsUpperLimit.equals("null") ? null : Integer.parseInt(RowsUpperLimit)));
        return new Gson().toJson(catgList).toString();
    }

    /**
     * Method to fetch Sales Order Details
     * @param CldId *
     * @param HoOrgId *
     * @param SlocId *
     * @param OrgId *
     * @param usrId
     * @param docId *
     * @return
     */
    @GET
   // {cldId}/{horgId}/{slocId}/{orgId}/{usrId}/{vouId}
    @Path("/detailWS/{CldId}/{HoOrgId}/{SlocId}/{OrgId}/{usrId}/{docId}")
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("oracle.jdeveloper.webservice.rest.broken-resource-warning")
    public String getSalesOrderDetails(@PathParam("CldId") String CldId, @PathParam("HoOrgId") String HoOrgId, 
                                       @PathParam("SlocId") String SlocId,@PathParam("OrgId") String OrgId,
                                       @PathParam("usrId") String usrId,@PathParam("docId") String docId) {
        EbizSlsMobileWSAMImpl a = new EbizSlsMobileWSAMImpl();
        Map catgList =
            a.getSalesOrderDetail(Integer.parseInt(SlocId), CldId, OrgId, HoOrgId,docId,null);
        return new Gson().toJson(catgList).toString();
    }

    /**
     * Method to update and fetch new sales order details
     * @param SlocId
     * @param CldId
     * @param OrgId
     * @param HoOrgId
     * @param docId
     * @param itmId
     * @param qty
     * @param dicVal
     * @return
     */
    
    @GET
    @Path("/updateSalesOrderDetails/{SlocId}/{CldId}/{OrgId}/{HoOrgId}/{docId}/{itmId}/{qty}/{dicVal}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("oracle.jdeveloper.webservice.rest.broken-resource-warning")
    public String updateSalesOrderDetails(@PathParam("SlocId") String SlocId, @PathParam("CldId") String CldId,
                                       @PathParam("OrgId") String OrgId, @PathParam("HoOrgId") String HoOrgId,
                                       @PathParam("docId") String docId,@PathParam("itmId")String itmId,
                                          @PathParam("qty")String qty,@PathParam("dicVal") String dicVal) {
        EbizSlsMobileWSAMImpl a = new EbizSlsMobileWSAMImpl();
        System.out.println("Update");
        Map catgList = a.updateSalesOrderData(Integer.parseInt(SlocId), CldId, OrgId, HoOrgId,docId,itmId,qty,dicVal);
        return new Gson().toJson(catgList).toString();
    }
    @POST
        @Path("/alertCountWS/")
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public String alertCountWS(String data){
              System.out.println("Data Found : "+data);
              JsonObject object = new JsonObject();
              JsonParser parser = new JsonParser();
              object = (JsonObject)parser.parse(data);
              EbizSlsMobileWSAMImpl ebiz = new EbizSlsMobileWSAMImpl();
              Map output =ebiz.getAlertCountWS(object.get("cldId").getAsString(),object.get("slocId").getAsInt(),object.get("horgId").getAsString(),
                                        object.get("orgId").getAsString(),object.get("usrId").getAsInt());
              System.out.println("Login Output : "+output);
            return new Gson().toJson(output).toString();
        }
}