package application.model.services;

import com.google.gson.Gson;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/ebizWS")
public class CallWS {
    @POST
    @Path("/loginWS/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String loginWS(String data){
          System.out.println("Data Found : "+data);
          JsonObject object = new JsonObject();
          JsonParser parser = new JsonParser();
          object = (JsonObject)parser.parse(data);
          ebizMobileAppAMImpl ebiz = new ebizMobileAppAMImpl();
          String output =ebiz.loginWS(object.get("cldId").getAsString(),object.get("slocId").getAsInt(),object.get("userNm").getAsString(),
                                    object.get("userPwd").getAsString(),object.get("tknId").getAsString(),object.get("osNm").getAsString(),
                                    object.get("dvcNm").getAsString(),object.get("flag").getAsInt());
          System.out.println("Login Output : "+output);
        return output;
    }

    /**
     * @param horgId
     * @param orgId
     * @param usrId
     * @param type
     * @param start
     * @param end
     * @return
     */
    @GET
    @Path("/forApprovalWS/{cldId}/{slocId}/{horgId}/{orgId}/{usrId}/{type}/{start}/{end}")
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("oracle.jdeveloper.webservice.rest.broken-resource-warning")
    public String forApprovalWS(@PathParam("cldId") String cldId,@PathParam("slocId") int slocId,
                                @PathParam("horgId") String horgId,@PathParam("orgId") String orgId,
                                @PathParam("usrId") String usrId,@PathParam("type") String type,
                                @PathParam("start") String start,@PathParam("end") String end){
        ebizMobileAppAMImpl ebiz = new ebizMobileAppAMImpl();
        String output =ebiz.approvalWS(cldId,slocId,horgId,orgId,usrId,type,start,end);
        return output;
    }

    /**
     * @param cldId
     * @param horgId
     * @param slocId
     * @param orgId
     * @param usrId
     * @param vouId
     * @return
     */
    @GET
    @Path("/detailWS/{cldId}/{horgId}/{slocId}/{orgId}/{usrId}/{vouId}")
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("oracle.jdeveloper.webservice.rest.broken-resource-warning")
    public String detailWS(@PathParam("cldId") String cldId,@PathParam("horgId") String horgId,
                           @PathParam("slocId") String slocId,@PathParam("orgId") String orgId,
                           @PathParam("usrId") String usrId,@PathParam("vouId") String vouId){
        ebizMobileAppAMImpl ebiz = new ebizMobileAppAMImpl();
        String output =ebiz.detailWS(cldId,horgId,slocId,orgId,usrId,vouId);
        return output;
    }

    /**
     * @param cldId
     * @param orgId
     * @param usrId
     * @param btkTyp
     * @return
     */
    @GET
    @Path("/getDbAgeingInfo/{cldId}/{orgId}/{usrId}/{btkTyp}")
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("oracle.jdeveloper.webservice.rest.broken-resource-warning")
    public String getDbAgeingInfo(@PathParam("cldId") String cldId,@PathParam("orgId") String orgId,
                              @PathParam("usrId") int usrId,@PathParam("btkTyp") String btkTyp){
        ebizMobileAppAMImpl ebiz = new ebizMobileAppAMImpl();
        Map output =ebiz.getDbAgeingInfo(cldId,orgId,btkTyp,usrId);
        return new Gson().toJson(output).toString();
    }

    /**
     * @param cldId
     * @param orgId
     * @param usrId
     * @param btkTyp
     * @param r1
     * @param r2
     * @param r3
     * @param r4
     * @return
     */
    @GET
    @Path("/setBtkRange/{cldId}/{orgId}/{usrId}/{btkTyp}/{r1}/{r2}/{r3}/{r4}")
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("oracle.jdeveloper.webservice.rest.broken-resource-warning")
    public String setBtkRange(@PathParam("cldId") String cldId,@PathParam("orgId") String orgId,
                                  @PathParam("usrId") int usrId,@PathParam("btkTyp") String btkTyp,
                                  @PathParam("r1") String r1,@PathParam("r2") String r2,
                                  @PathParam("r3") String r3,@PathParam("r4") String r4){
        ebizMobileAppAMImpl ebiz = new ebizMobileAppAMImpl();
        String output = null;
        try {
            output = ebiz.setBtkRange(cldId, orgId, btkTyp, usrId, r1, r2, r3, r4);
        } catch (Exception e) {
        }
        Map result = new HashMap();
        if(output.equals("Y")){
            result.put("status",output);
            result.put("msg","Data Save Successfully");
        }else{
            result.put("status",output);
            result.put("msg","Define Bucket Not Save. Please Try Again");
        }
        System.out.println(result);
        return new Gson().toJson(result).toString();
    }

    /**
     * @param cldId
     * @param slocId
     * @param horgId
     * @param orgId
     * @return
     */
    @GET
    @Path("/getCashPositionInfo/{cldId}/{slocId}/{horgId}/{orgId}")
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("oracle.jdeveloper.webservice.rest.broken-resource-warning")
    public String getCashPositionInfo(@PathParam("cldId") String cldId,@PathParam("slocId") int slocId,
                              @PathParam("horgId") String horgId,@PathParam("orgId") String orgId){
        ebizMobileAppAMImpl ebiz = new ebizMobileAppAMImpl();
        Map output =ebiz.getCashPositionInfo(cldId,slocId,horgId,orgId);
        return new Gson().toJson(output).toString();
    }
    
    @GET
    @Path("/getOrgInfoWS/{orgId}/{cldId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getOrgInfo(@PathParam("orgId")String orgId,@PathParam("cldId")String cldId){
        ebizMobileAppAMImpl ebiz = new ebizMobileAppAMImpl();
        Map map = ebiz.getOrgInfo(orgId,cldId);
        return new Gson().toJson(map).toString();
    }
    
    @POST
    @Path("/getCashPositionDetailWS/")
    @Produces(MediaType.MULTIPART_FORM_DATA)
    public String getCashPositionDetail(){
        return "";
    } 
    
    @GET
    @Path("/getNotificationWS/{cldId}/{slocId}/{horgId}/{orgId}/{usrId}/{modId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getNotification(@PathParam("cldId")String cldId,@PathParam("slocId")int slocId,
                                  @PathParam("horgId")String horgId,@PathParam("orgId")String orgId,
                                  @PathParam("usrId")int usrId,@PathParam("modId")String modId){
        ebizMobileAppAMImpl ebiz = new ebizMobileAppAMImpl();
        Map map = ebiz.getNotification(cldId,slocId,horgId,orgId,usrId,modId);
        return new Gson().toJson(map).toString();
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
          ebizMobileAppAMImpl ebiz = new ebizMobileAppAMImpl();
          Map output =ebiz.getAlertCountWS(object.get("cldId").getAsString(),object.get("slocId").getAsInt(),object.get("horgId").getAsString(),
                                    object.get("orgId").getAsString(),object.get("usrId").getAsInt());
          System.out.println("Output : "+output);
        return new Gson().toJson(output).toString();
    }
}
