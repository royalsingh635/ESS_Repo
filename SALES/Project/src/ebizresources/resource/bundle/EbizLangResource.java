package ebizresources.resource.bundle;

import adf.utils.model.ADFModelUtils;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class EbizLangResource {
    /**
     * Method to initialize the inital contents of the content variable
     * @return
     */
    public static Object[][] initContents(){
        return new Object[15000][2];
    }
    /**
     * Method to get the contents of the resource
     * @param contents
     * @return
     */
    public static Object[][] getContents(Object[][] contents) {
        for (int i = 0; i < 15000; i++) {
            contents[i][0] = "";
            contents[i][1] = "";
        }
        try {
            /**
             * GLBL_RES_PATH -- GLBL Parameter for getting Resource path define app$sevr$loc
             * GLBL_RES_FILE -- GLBL Parameter for getting Resource File Selected in Main application.
             */
            Object filePathO = ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_RES_PATH}");
            Object fileNameO = ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_RES_FILE}");
            String filePath = (filePathO == null ? "D:\\Resource\\" : filePathO.toString());
            String fileName = (fileNameO == null ? "Resource_EN.xml" : fileNameO.toString());

            File file = new File("/" + filePath + fileName);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("label");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
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
        Node nValue = nlList.item(0);
        return nValue.getNodeValue();
    }
}

