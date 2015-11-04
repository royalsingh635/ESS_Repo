package ebizframemainapplication.view.bundle;

import java.io.File;


import java.util.ListResourceBundle;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import oracle.javatools.resourcebundle.ResourceBundleManagerRT;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NodeList;

import utils.system;

public class Resource extends ListResourceBundle {
    

    public Resource() {
        for (int i = 0; i < 15000; i++) {
            contents[i][0] = "";
            contents[i][1] = "";

        }
        System.out.println("resource bundle in Main Application");
    }
    private  Object[][] contents = new Object[15000][2];


    public Object[][] getContents() {
        try {
            String path = "D:\\Resource\\";
            String res_file = "Resource_EN.xml";
            
             if (resolvEl("#{sessionScope.lang_path}") != null)
            { 
                path = resolvEl("#{sessionScope.lang_path}").toString();
                if(resolvEl("#{sessionScope.lang_file}") != null)
                res_file = resolvEl("#{sessionScope.lang_file}").toString();
               
              }
            System.out.println("resource from global app"+path+res_file);
            File file = new File("/"+path+res_file);
            // File file = new File("D:\\Resource\\Resource_EN.xml");
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

