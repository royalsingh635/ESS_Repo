package fincashflowapp.view.bundle;

import java.util.ListResourceBundle;
import java.io.File;
import java.util.ListResourceBundle;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
public class Resource extends ListResourceBundle {
   public  Resource(){
               for (int i = 0; i < 9000; i++) {
                       contents[i][0] = "";
                       contents[i][1] = "";
                   }
               }
           
     private static final Object[][] contents = new Object[9000][2];
       public Object[][] getContents() {
           try {
               File file = new File("D:\\Resource\\Resource.xml");
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
}