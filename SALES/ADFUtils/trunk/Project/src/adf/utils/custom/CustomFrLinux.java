package adf.utils.custom;

import adf.utils.model.ADFModelUtils;
import adf.utils.model.dbprocsupp.ProcPrams;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;

import oracle.jbo.server.ApplicationModuleImpl;

public class CustomFrLinux {
    public CustomFrLinux() {
        super();
    }

    /**
     * @param am
     * @param slocId
     * @param cldId
     * @param docId
     * @param docTypeId
     * @param action
     * @param order
     * @param jar
     * @return
     */
    public static Object getClassInstance(ApplicationModuleImpl am,Integer slocId,String cldId,Integer docId,Integer docTypeId,Integer action,Integer order,StringBuilder jar){
    Object obj=null;

    StringBuilder isCustom=null;
    StringBuilder className=null;
    StringBuilder jarLoc=null;
    StringBuilder jarAdd=null;

        try {
            ArrayList dtl=getClsDetail(am, slocId, cldId, docId, docTypeId, action, order, jar);

            System.out.println("Detail is " + dtl);
            isCustom = new StringBuilder(dtl.get(0).toString());
            

            if (isCustom.toString().equalsIgnoreCase("Y")) {
                jarAdd = new StringBuilder(dtl.get(1).toString());
                className = new StringBuilder(dtl.get(2).toString());
                
                
                URL[] classLoaderUrls = new URL[] { new URL("file:///" + jarAdd) };
            System.out.println("ClassLoder : "+classLoaderUrls+"|"+jarAdd);
                // Create a new URLClassLoader

                URLClassLoader urlClassLoader = new URLClassLoader(classLoaderUrls, Custom.class.getClassLoader());


                // Load the target class

                Class cls = urlClassLoader.loadClass(className.toString());

                obj = cls.newInstance();

            }

        } catch (IllegalAccessException iae) {
            // TODO: Add catch code
            iae.printStackTrace();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        } catch (InstantiationException ie) {
            // TODO: Add catch code
            ie.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            // TODO: Add catch code
            cnfe.printStackTrace();
        } catch (MalformedURLException murle) {
            // TODO: Add catch code
            murle.printStackTrace();
        }
    return obj;
    }


    /**
     * @param am
     * @param slocId
     * @param cldId
     * @param docId
     * @param docTypeId
     * @param action
     * @param order
     * @param jar
     * @return
     * @throws SQLException
     */
    public static ArrayList getClsDetail(ApplicationModuleImpl am,Integer slocId,String cldId,Integer docId,Integer docTypeId,Integer action,Integer order,StringBuilder jar) throws SQLException {
        ProcPrams params[]=new ProcPrams[]{
                                            new ProcPrams(1,Types.NUMERIC,ProcPrams.IN,slocId),
                                            new ProcPrams(2,Types.VARCHAR,ProcPrams.IN,cldId),
                                            new ProcPrams(3,Types.NUMERIC,ProcPrams.IN,docId),
                                            new ProcPrams(4,Types.NUMERIC,ProcPrams.IN,docTypeId),
                                            new ProcPrams(5,Types.NUMERIC,ProcPrams.IN,action),
                                            new ProcPrams(6,Types.NUMERIC,ProcPrams.IN,order),
                                            new ProcPrams(7,Types.VARCHAR,ProcPrams.OUT,null),
                                            new ProcPrams(8,Types.VARCHAR,ProcPrams.OUT,null),
                                            new ProcPrams(9,Types.VARCHAR,ProcPrams.OUT,null)
    
                                              };
        
       return ADFModelUtils.callDbProcedure(am, new StringBuilder("app.PROC_GET_CLS_DTL_FR_LIN(?,?,?,?,?,?,?,?,?)"), params);
    }
    

}
