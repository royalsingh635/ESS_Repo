package hcmdepartsetupapp.view.bundle;


import java.io.File;
import java.sql.Types;

import java.util.ListResourceBundle;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import oracle.adf.share.logging.ADFLogger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Resource extends ListResourceBundle {
    private static ADFLogger adflog = ADFLogger.createADFLogger(Resource.class);
    private static int STRING = Types.VARCHAR;
   
    public  Resource(){
        for(int i=0;i<15000;i++){
                                     contents[i][0]="";                                          
                                     contents[i][1]="";
                                   
                                   }
        
        }
    private static final Object[][] contents = new Object[15000][2];
    
    public Object[][] getContents() {
        try {
            /**
             * GLBL_RES_PATH -- GLBL Parameter for getting Resource path define app$sevr$loc
             * GLBL_RES_FILE -- GLBL Parameter for getting Resource File Selected in Main application.
             */
            String p_path=null;
            String p_file=null;
              if (resolvEl("#{pageFlowScope.GLBL_RES_PATH}")!=null ) {
                p_path = resolvEl("#{pageFlowScope.GLBL_RES_PATH}").toString();
            }
            if (resolvEl("#{pageFlowScope.GLBL_RES_FILE}")!=null) {
                p_file = resolvEl("#{pageFlowScope.GLBL_RES_FILE}").toString();
            }
           adflog.info("     "+p_path+"    "+p_file);
            String path = "D:\\Resource\\";
            String res_file = "Resource_EN.xml";
                if(p_path!=null&&(!p_path.equals("")))
                path=p_path;
                if(p_file!=null&&(!p_file.equals("")))
                    res_file=p_file;
            adflog.info(res_file+" res path "+path);
          //  File file = new File("D:\\Resource\\Resource.xml");
             File file = new File("/"+path+res_file);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("label");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element)nNode;

                    contents[temp][0] = getTagValue("key", eElement);
                    contents[temp][1] = getTagValue("value", eElement);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contents;
    }
    
    
    private static String getTagValue(String sTag, Element eElement) {
    NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
    Node nValue = (Node)nlList.item(0);
    return nValue.getNodeValue();
    }
    
    
    public Object resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        Object Message = valueExp.getValue(elContext);
        return Message;
    } 
}
