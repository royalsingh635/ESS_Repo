package appwfservice.model.module;

import com.google.gson.Gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/workFlowWS")
public class CallWS {
    
    @GET
    @Path("/getDocInfoWF/{cldId}/{slocId}/{orgId}/{horgId}/{usrId}/{docId}/{docTypId}/{docTxnId}/{amount}/{wfId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getDocInfo(@PathParam("cldId")String cldId,@PathParam("slocId")int slocId,@PathParam("orgId")String orgId,
                             @PathParam("horgId")String horgId,@PathParam("usrId")int usrId,@PathParam("docId")int docId,
                             @PathParam("docTypId")int docTypId,@PathParam("docTxnId")String docTxnId,@PathParam("amount")String amount,
                             @PathParam("wfId")String wfId) {
        System.out.println("getCatgDisBaseList");
        WfServAMImpl a = new WfServAMImpl();
        //Map map = a.getWorkFlowInfo("0000",1,"01","01",39,18513,0,"0001.01.01.4851.0000.2CB8E4409F61E96",0,"W040");
        Map map = a.getWorkFlowInfo(cldId,slocId,orgId,horgId,usrId,docId,docTypId,docTxnId,amount,wfId);
        return new Gson().toJson(map).toString();
    }      
    
    //Finance,Sales,MM
    
  @POST
    @Path("/getDocActionWF/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getDocActionWF(String data) {
        System.out.println("Data Found : "+data);
        WfServAMImpl a = new WfServAMImpl();
        JsonObject object = new JsonObject();
        JsonParser parser = new JsonParser();
        object = (JsonObject)parser.parse(data);
        String result = a.docAction(object.get("cldId").getAsString(),object.get("orgId").getAsString(),object.get("slocId").getAsInt(),
                                    object.get("horgId").getAsString(),object.get("wfId").getAsString(),object.get("docId").getAsInt(),
                                    object.get("docTypId").getAsInt(),object.get("docTxnId").getAsString(),object.get("amount").getAsString(),
                                    object.get("usrId").getAsInt(),object.get("remark").getAsString(),object.get("usrLvl").getAsInt(),
                                    object.get("usrIdTo").getAsInt(),object.get("usrToLvl").getAsInt(),object.get("usrAction").getAsString(),
                                    object.get("usr").getAsString(),object.get("module").getAsString());
        Map map = new HashMap();
        map.put("status",result);
        return new Gson().toJson(map).toString();
        
    }      
}