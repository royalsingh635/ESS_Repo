package application.model.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/tokenWS")
public class callWS {
    
    @POST
    @Path("/authenticationWF/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getItmListWF(String data) {
          System.out.println("Data Found : "+data);
          tokenAppAMImpl ebiz = new tokenAppAMImpl();
          JsonObject object = new JsonObject();
          JsonParser parser = new JsonParser();
          object = (JsonObject)parser.parse(data);
          Map map = ebiz.getInfo(object.get("token").getAsString());
          return new Gson().toJson(map).toString();
      }
    
    @GET
    @Path("/systemWS/")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSystemWS() {
          tokenAppAMImpl ebiz = new tokenAppAMImpl();
          Map map = ebiz.getSysInfo();
          return new Gson().toJson(map).toString();
      }
    @GET
    @Path("/bannerWS/")
    @Produces(MediaType.APPLICATION_JSON)
    public String getBannerWS() {
          tokenAppAMImpl ebiz = new tokenAppAMImpl();
          Map map = ebiz.getBannerInfo();
          return new Gson().toJson(map).toString();
      }
    
}
