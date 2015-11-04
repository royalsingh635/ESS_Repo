package PatchJarWriter;

import java.io.File;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;

public class SysVarReader {
    
    public String patchFolder=null;
    public String jarFolder=null;
    public String patch=null;
    
    public SysVarReader() {
        super();
    }
    
    public void readVariable(){
        HashMap map=null;
        try {
            map = (HashMap) this.readXML();
        } catch (IOException e) {
        } catch (SAXException e) {
        } catch (ParserConfigurationException e) {
        }
        if(map.get("Patch")==null||map.get("Jar")==null||map.get("LastPatch")==null){
            System.out.println("Please define location for Patch,Jar,LastPatch");
            System.exit(0);
        }
            this.patchFolder=map.get("Patch").toString();
            this.jarFolder=map.get("Jar").toString();
            this.patch=map.get("LastPatch").toString();
    }
    
    public Map<String,String> readXML() throws ParserConfigurationException, SAXException, IOException {
        HashMap map=new HashMap<String,String>();
        
        File xml=new File("SysInfo.xml");
                
        
        DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
        
        DocumentBuilder parser=factory.newDocumentBuilder();
        
        Document doc= parser.parse(xml);
        doc.getDocumentElement().normalize();
        
        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
        NodeList list = doc.getElementsByTagName("Info");
       
        for (int temp = 0; temp < list.getLength(); temp++) {
           
            Node nNode = list.item(temp);
            if(nNode.getNodeType()==Node.ELEMENT_NODE){
                
                Element eElement = (Element)nNode;

                map.put(this.getTagValue("Name",eElement), getTagValue("Loc", eElement));
            }
        }     
        return map;
    }
    
    private  String getTagValue(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
        Node nValue = (Node)nlList.item(0);
        return nValue.getNodeValue();
    }

    
//    public void printNodeDetail(NodeList list,Integer level){
//        for (int temp = 0; temp < list.getLength(); temp++) {
//            System.out.println("");
//            Node nNode = list.item(temp);
//            
//            for(int i=0;i<level;i++){
//                System.out.print(" ");
//            }
//            
//            System.out.print(level+nNode.getNodeName());
//            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
//                printNodeDetail(nNode.getChildNodes(),level+1);
//            }
//        }
//    }
}
