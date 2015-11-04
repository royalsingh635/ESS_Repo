package application.model.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.sun.jersey.core.spi.factory.ResponseBuilderHeaders;

import com.sun.jersey.core.spi.factory.ResponseBuilderImpl;

import java.io.File;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

@Path("/mmWS")
public class CallWS {
   
    @GET
    @Path("/getGrpListWS/{cldId}/{slocId}/{horgId}/{orgId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getGrpListWS(@PathParam("cldId") String cldId,@PathParam("slocId") int slocId,
                          @PathParam("horgId") String horgId,@PathParam("orgId") String orgId){
        EbizMmMobileWSAMImpl ebiz = new EbizMmMobileWSAMImpl();
        Map output =ebiz.getGrpList(cldId, slocId, horgId, orgId);
        return new Gson().toJson(output).toString();
    }
      
   
    @POST
    @Path("/getItmListWF/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getItmListWF(String data) {
          System.out.println("Data Found : "+data);
          EbizMmMobileWSAMImpl a = new EbizMmMobileWSAMImpl();
          JsonObject object = new JsonObject();
          JsonParser parser = new JsonParser();
          object = (JsonObject)parser.parse(data);
         Map map = a.getItmList(object.get("cldId").getAsString(),object.get("slocId").getAsInt(),object.get("horgId").getAsString(),
                                object.get("orgId").getAsString(),object.get("capitalItmFlg").getAsString(),object.get("srvsItmFlg").getAsString(),
                                object.get("stockableItmFlg").getAsString(),object.get("grpId").getAsString(),object.get("itmDesc").getAsString(),
                                object.get("lowerLimit").getAsInt(),object.get("upperLimit").getAsInt());
          return new Gson().toJson(map).toString();
          
      }
    
    @GET
    @Path("/getItmGnrlInfo/{cldId}/{slocId}/{horgId}/{itmId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getItmGnrlInfoWS(@PathParam("cldId") String cldId,@PathParam("slocId") int slocId,
                          @PathParam("horgId") String horgId,@PathParam("itmId") String itmId){
        EbizMmMobileWSAMImpl ebiz = new EbizMmMobileWSAMImpl();
        Map output =ebiz.getItmGnrlInfo(cldId, slocId, horgId, itmId);
        return new Gson().toJson(output).toString();
    }
    @GET
    @Path("/getItmStockInfo/{cldId}/{slocId}/{horgId}/{itmId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getItmStockInfoWS(@PathParam("cldId") String cldId,@PathParam("slocId") int slocId,
                          @PathParam("horgId") String horgId,@PathParam("itmId") String itmId){
        EbizMmMobileWSAMImpl ebiz = new EbizMmMobileWSAMImpl();
        Map output =ebiz.getItmStockInfo(cldId, slocId, horgId, itmId);
        return new Gson().toJson(output).toString();
    }
    
    @GET
    @Path("/getItmCoaInfo/{cldId}/{slocId}/{orgId}/{itmId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getItmCoaInfoWS(@PathParam("cldId") String cldId,@PathParam("slocId") int slocId,
                          @PathParam("orgId") String orgId,@PathParam("itmId") String itmId){
        EbizMmMobileWSAMImpl ebiz = new EbizMmMobileWSAMImpl();
        Map output =ebiz.getItmCoaInfo(cldId, slocId, orgId, itmId);
        return new Gson().toJson(output).toString();
    }
    
    @GET
    @Path("/getpurchaseHistoryWS/{cldId}/{slocId}/{orgId}/{grpId}/{itmDesc}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getpurchaseHistoryWS(@PathParam("cldId") String cldId,@PathParam("slocId") int slocId,
                          @PathParam("orgId") String orgId,@PathParam("grpId") String grpId,@PathParam("itmDesc") String itmDesc){
        EbizMmMobileWSAMImpl ebiz = new EbizMmMobileWSAMImpl();
        Map output =ebiz.getPurchaseHistory(cldId, slocId, orgId, grpId, itmDesc);
        return new Gson().toJson(output).toString();
    }
    //2028 Item Profile
    @GET
    @Path("/getApprovalDocList/{cldId}/{slocId}/{horgId}/{orgId}/{usrId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getApprovalDocListWS(@PathParam("cldId") String cldId, @PathParam("slocId") Integer slocId,
                                  @PathParam("horgId") String horgId, @PathParam("orgId") String orgId,
                                  @PathParam("usrId") Integer usrId) {
        EbizMmMobileWSAMImpl a = new EbizMmMobileWSAMImpl();
        Map docList = a.getApprovalDocList(cldId, slocId, horgId, orgId, usrId);
        return new Gson().toJson(docList).toString();
    }
    
    @GET
    @Path("/getDocApprovalList/{cldId}/{slocId}/{horgId}/{orgId}/{usrId}/{approvalTyp}/{docId}/{lowerLimit}/{upperLimit}")
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("oracle.jdeveloper.webservice.rest.broken-resource-warning")
    public String getDocApprovalList(@PathParam("cldId") String cldId, @PathParam("slocId") int slocId,@PathParam("horgId") String horgId,
                                     @PathParam("orgId") String orgId,@PathParam("usrId") int usrId, @PathParam("approvalTyp") String approvalTyp,
                                     @PathParam("docId") int docId,@PathParam("lowerLimit") int lowerLimit,@PathParam("upperLimit") int upperLimit) {
        EbizMmMobileWSAMImpl a = new EbizMmMobileWSAMImpl();
        Map catgList =
            a.getPendingDocumentList(cldId,slocId,horgId,orgId,usrId,approvalTyp,docId,lowerLimit,upperLimit);
        return new Gson().toJson(catgList).toString();
    }
    
    @GET
    @Path("/detailWS/{cldId}/{slocId}/{orgId}/{horgId}/{usrId}/{docId}")
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("oracle.jdeveloper.webservice.rest.broken-resource-warning")
    public String getPurchaseOrderDetails(@PathParam("cldId") String cldId, @PathParam("slocId") int slocId, 
                                       @PathParam("orgId") String orgId,@PathParam("horgId") String horgId,
                                       @PathParam("usrId") String usrId,@PathParam("docId") String docId) {
        EbizMmMobileWSAMImpl a = new EbizMmMobileWSAMImpl();
        Map catgList =a.getPurchaseOrderDetail(cldId,slocId ,orgId,docId);
        return new Gson().toJson(catgList).toString();
    }
    
    @GET
        @Path("getImg/{cldId}/{slocId}/{hoId}/{itmId}/image")
        @Produces("image/png")
        public Response getFileInJPEGFormat(@PathParam("cldId") String cldId,@PathParam("slocId") int slocId,
                                            @PathParam("hoId") String hoId,@PathParam("itmId") String itmId)
        {
            System.out.println("File requested is : " + itmId);
            EbizMmMobileWSAMImpl a = new EbizMmMobileWSAMImpl();
            String imgPath =a.getImgPath(cldId,slocId ,hoId,itmId);
            //Put some validations here such as invalid file name or missing file name
            if(itmId == null || itmId.isEmpty())
            {
            System.out.println("Inner");
            ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
            return builder.build();
            }
            File file = null;
            //Prepare a file object with file to return
            if(imgPath.equals("N")){
            file = new File("C:\\DEPLOYMENT\\Images\\logo.png");
            }else{
                file = new File(imgPath);
            }
            ResponseBuilder response = Response.ok(file);
            response.header("Content-Disposition", "attachment; filename=\""+imgPath+".png\"");
            return response.build();
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
              EbizMmMobileWSAMImpl ebiz = new EbizMmMobileWSAMImpl();
              Map output =ebiz.getAlertCountWS(object.get("cldId").getAsString(),object.get("slocId").getAsInt(),object.get("horgId").getAsString(),
                                        object.get("orgId").getAsString(),object.get("usrId").getAsInt());
              System.out.println("Login Output : "+output);
            return new Gson().toJson(output).toString();
        }
    
}
