package ebizresources.resource.bundle;


import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class EbizLangResource {
    /**
     * Method to initialize the inital contents of the content variable.
     * @return
     */
    public static Object[][] initContents() {
        return new Object[15000][2];
    }

    /**
     * Method to get the contents of the resource.
     * @param contents
     * @param filePath
     * @param fileName
     * @return
     */
    public static Object[][] getContents(Object[][] contents,String filePath,String fileName) {
        for (int i = 0; i < 15000; i++) {
            contents[i][0] = "";
            contents[i][1] = "";
        }
        try {
            contents = getResourceKeyValuePairs(contents, filePath, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contents;
    }
    /**
     * Method to get tag value from the tag
    */
    private static String getTagValue(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
        Node nValue = nlList.item(0);
        return nValue.getNodeValue();
    }
    
    /**
     * Method to fetch Key Value Pair from resource file
     * @param contents
     * @param filePath
     * @param fileName
     * @return
     */
    private static Object[][] getResourceKeyValuePairs(Object[][] contents, String filePath, String fileName) {
        File file = new File("/" + filePath + fileName);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        NodeList nList = null;
        try {
            db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            nList = doc.getElementsByTagName("label");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    contents[temp][0] = getTagValue("key", eElement);
                    contents[temp][1] = getTagValue("value", eElement);
                }
            }
        } catch (Exception e) {
        }
        return contents;
    }
}

