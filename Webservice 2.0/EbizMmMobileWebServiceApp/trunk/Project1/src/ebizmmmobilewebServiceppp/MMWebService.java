package ebizmmmobilewebServiceppp;

import com.ess.conn.DBConnection;

import com.ess.view.ViewObject;

import com.google.gson.Gson;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import ebizmmmobilewebServiceppp.utilities.MMUtilities;

import java.io.File;

import java.io.FileInputStream;

import java.io.InputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Scanner;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

@Path("mmWS")
public class MMWebService {
    public MMWebService() {
    }

    @GET
    @Path("/getGrpListWS/{cldId}/{slocId}/{horgId}/{orgId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getGrpListWS(@PathParam("cldId") String cldId, @PathParam("slocId") int slocId,
                               @PathParam("horgId") String horgId, @PathParam("orgId") String orgId) {

        DBConnection AppDB;
        Map output = new HashMap();
        try {
            AppDB = DBConnection.getDSConnection("APPDS");
            Connection con = AppDB.getConnection();
            try {
                MMUtilities mu = new MMUtilities(AppDB, con);
                output = mu.getGrpList(cldId, slocId, horgId, orgId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Gson().toJson(output).toString();
    }

    @POST
    @Path("/getItmListWF/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getItmListWF(String data) {
        System.out.println("Data Found : " + data);

        DBConnection AppDB;
        Map map = new HashMap();
        try {
            JsonObject object = new JsonObject();
            JsonParser parser = new JsonParser();
            object = (JsonObject) parser.parse(data);

            AppDB = DBConnection.getDSConnection("APPDS");
            Connection con = AppDB.getConnection();
            try {
                MMUtilities mu = new MMUtilities(AppDB, con);
                //  map = new HashMap();
                map =
                    mu.getItmList(object.get("cldId").getAsString(), object.get("slocId").getAsInt(),
                                  object.get("horgId").getAsString(), object.get("orgId").getAsString(),
                                  object.get("lowerLimit").getAsInt(), object.get("upperLimit").getAsInt(),
                                  object.get("itmPrfTyp").getAsString(), object.get("typeId").getAsString());
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
    @Path("/getItmGnrlInfo/{cldId}/{slocId}/{horgId}/{itmId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getItmGnrlInfoWS(@PathParam("cldId") String cldId, @PathParam("slocId") int slocId,
                                   @PathParam("horgId") String horgId, @PathParam("itmId") String itmId) {
        DBConnection AppDB;
        Map output = new HashMap();
        try {
            AppDB = DBConnection.getDSConnection("APPDS");
            Connection con = AppDB.getConnection();
            try {
                MMUtilities mu = new MMUtilities(AppDB, con);
                output = mu.getItmGnrlInfo(cldId, slocId, horgId, itmId);
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
    @Path("/getItmStockInfo/{cldId}/{slocId}/{horgId}/{itmId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getItmStockInfoWS(@PathParam("cldId") String cldId, @PathParam("slocId") int slocId,
                                    @PathParam("horgId") String horgId, @PathParam("itmId") String itmId) {
        DBConnection AppDB;
        Map output = new HashMap();
        try {
            AppDB = DBConnection.getDSConnection("APPDS");
            Connection con = AppDB.getConnection();
            try {
                MMUtilities mu = new MMUtilities(AppDB, con);
                output = mu.getItmStockInfo(cldId, slocId, horgId, itmId);
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
    @Path("/getItmCoaInfo/{cldId}/{slocId}/{orgId}/{itmId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getItmCoaInfoWS(@PathParam("cldId") String cldId, @PathParam("slocId") int slocId,
                                  @PathParam("orgId") String orgId, @PathParam("itmId") String itmId) {
        DBConnection AppDB;
        Map output = new HashMap();
        try {
            AppDB = DBConnection.getDSConnection("APPDS");
            Connection con = AppDB.getConnection();
            try {
                MMUtilities mu = new MMUtilities(AppDB, con);
                output = mu.getItmCoaInfo(cldId, slocId, orgId, itmId);
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
    @Path("/getpurchaseHistoryWS/{cldId}/{slocId}/{orgId}/{grpId}/{itmDesc}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getpurchaseHistoryWS(@PathParam("cldId") String cldId, @PathParam("slocId") int slocId,
                                       @PathParam("orgId") String orgId, @PathParam("grpId") String grpId,
                                       @PathParam("itmDesc") String itmDesc) {
        DBConnection AppDB;
        Map output = new HashMap();
        try {
            AppDB = DBConnection.getDSConnection("APPDS");
            Connection con = AppDB.getConnection();
            try {
                MMUtilities mu = new MMUtilities(AppDB, con);
                output = mu.getPurchaseHistory(cldId, slocId, orgId, grpId, itmDesc);
            } catch (Exception e) {
                e.printStackTrace();
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Gson().toJson(output).toString();
    }

    //2028 Item Profile
    @GET
    @Path("/getApprovalDocList/{cldId}/{slocId}/{horgId}/{orgId}/{usrId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getApprovalDocListWS(@PathParam("cldId") String cldId, @PathParam("slocId") Integer slocId,
                                       @PathParam("horgId") String horgId, @PathParam("orgId") String orgId,
                                       @PathParam("usrId") Integer usrId) {
        DBConnection AppDB;
        Map docList = new HashMap();
        try {
            AppDB = DBConnection.getDSConnection("MMDS");
            Connection con = AppDB.getConnection();
            try {
                MMUtilities mu = new MMUtilities(AppDB, con);
                docList = mu.getApprovalDocList(cldId, slocId, horgId, orgId, usrId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Gson().toJson(docList).toString();
    }

    @GET
    @Path("/getDocApprovalList/{cldId}/{slocId}/{horgId}/{orgId}/{usrId}/{approvalTyp}/{docId}/{lowerLimit}/{upperLimit}")
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("oracle.jdeveloper.webservice.rest.broken-resource-warning")
    public String getDocApprovalList(@PathParam("cldId") String cldId, @PathParam("slocId") int slocId,
                                     @PathParam("horgId") String horgId, @PathParam("orgId") String orgId,
                                     @PathParam("usrId") int usrId, @PathParam("approvalTyp") String approvalTyp,
                                     @PathParam("docId") int docId, @PathParam("lowerLimit") int lowerLimit,
                                     @PathParam("upperLimit") int upperLimit) {

        DBConnection AppDB;
        Map catgList = new HashMap();

        try {
            AppDB = DBConnection.getDSConnection("MMDS");
            Connection con = AppDB.getConnection();
            try {
                MMUtilities mu = new MMUtilities(AppDB, con);
                catgList =
                    mu.getPendingDocumentList(cldId, slocId, horgId, orgId, usrId, approvalTyp, docId, lowerLimit,
                                              upperLimit);
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
    //   @Path("/detailWS/{cldId}/{slocId}/{orgId}/{horgId}/{usrId}/{doctxnId}/{glblDocId}")
    @Path("/detailWS/{cldId}/{slocId}/{horgId}/{orgId}/{usrId}/{glblDocId}/{doctxnId}/")
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("oracle.jdeveloper.webservice.rest.broken-resource-warning")
    public String getPurchaseOrderDetails(@PathParam("cldId") String cldId, @PathParam("slocId") int slocId,
                                          @PathParam("horgId") String horgId, @PathParam("orgId") String orgId,
                                          @PathParam("usrId") String usrId, @PathParam("glblDocId") String glblDocId,
                                          @PathParam("doctxnId") String docTxnId) {

        DBConnection AppDB;
        Map catgList = new HashMap();
        catgList.put("status", false);
        catgList.put("message", "Record not found !!");
        try {
            AppDB = DBConnection.getDSConnection("MMDS");
            Connection con = AppDB.getConnection();
            try {
                MMUtilities mu = new MMUtilities(AppDB, con);
                catgList = mu.getPurchaseOrderDetail(cldId, slocId, horgId, orgId, docTxnId, glblDocId, usrId);
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
    @Path("getImg/{cldId}/{slocId}/{hoId}/{itmId}/image")
    @Produces("image/png")
    public Response getFileInJPEGFormat(@PathParam("cldId") String cldId, @PathParam("slocId") int slocId,
                                        @PathParam("hoId") String hoId, @PathParam("itmId") String itmId) {
        System.out.println("File requested is : " + itmId);

        DBConnection AppDB;
        String imgPath = "";
        try {
            AppDB = DBConnection.getDSConnection("MMDS");
            Connection con = AppDB.getConnection();
            try {
                MMUtilities mu = new MMUtilities(AppDB, con);
                imgPath = mu.getImgPath(cldId, slocId, hoId, itmId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Put some validations here such as invalid file name or missing file name
        if (itmId == null || itmId.isEmpty()) {
            System.out.println("Inner");
            ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
            return builder.build();
        }
        File file = null;
        //Prepare a file object with file to return
        if (imgPath.equals("N")) {
            file = new File("C:\\DEPLOYMENT\\Images\\logo.png");
        } else {
            file = new File(imgPath);
        }
        ResponseBuilder response = Response.ok(file);
        response.header("Content-Disposition", "attachment; filename=\"" + imgPath + ".png\"");
        return response.build();
    }

    @POST
    @Path("/alertCountWS/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String alertCountWS(String data) {
        System.out.println("Data Found : " + data);

        DBConnection AppDB;
        Map output = new HashMap();
        try {
            JsonObject object = new JsonObject();
            JsonParser parser = new JsonParser();
            object = (JsonObject) parser.parse(data);

            AppDB = DBConnection.getDSConnection("MMDS");
            Connection con = AppDB.getConnection();
            try {
                MMUtilities mu = new MMUtilities(AppDB, con);
                output =
                    mu.getAlertCountWS(object.get("cldId").getAsString(), object.get("slocId").getAsInt(),
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


    @GET
    @Path("/getTypeListWS/{cldId}/{slocId}/{horgId}/{orgId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTypeListWS(@PathParam("cldId") String cldId, @PathParam("slocId") int slocId,
                                @PathParam("horgId") String horgId, @PathParam("orgId") String orgId) {

        List l1 = new ArrayList();

        Map m1 = new HashMap();
        m1.put("typeid", "1");
        m1.put("typename", "Raw Material");

        Map m2 = new HashMap();
        m2.put("typeid", "2");
        m2.put("typename", "Finished Good");

        Map m3 = new HashMap();
        m3.put("typeid", "3");
        m3.put("typename", "Capital Good");

        l1.add(m1);
        l1.add(m2);
        l1.add(m3);

        Map map = new HashMap();
        map.put("status", "true");
        map.put("message", "Success");
        map.put("type", l1);
        return new Gson().toJson(map).toString();
    }

    @GET
    @Path("/getItmDetails/{cldId}/{slocId}/{horgId}/{orgId}/{itmId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getItmDetails(@PathParam("cldId") String cldId, @PathParam("slocId") int slocId,
                                @PathParam("horgId") String horgId, @PathParam("orgId") String orgId,
                                @PathParam("itmId") String itmId) {
        DBConnection AppDB;
        Map map = new HashMap();
        System.out.println("done .. ");

        try {
            AppDB = DBConnection.getDSConnection("MMDS");
            Connection con = AppDB.getConnection();
            try {
                System.out.println("done ..1 ");
                MMUtilities mu = new MMUtilities(AppDB, con);
                map = mu.getItemInformation(cldId, slocId, horgId, orgId, itmId);
                //map = mu.callProcedureOfItemDetail(cldId, slocId, horgId, orgId, itmId);

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
