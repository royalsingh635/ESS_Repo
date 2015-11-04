/* Author of this code is Mayank Shukla.This is different from normal Resource Java Class.So Dont temper
 * this without your manager's permission. 
*/

package ebizframemainapplication.view.bundle;

import java.io.File;


import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ListResourceBundle;
import java.util.Map;

import java.util.ResourceBundle;
import java.util.Set;

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

import sun.util.ResourceBundleEnumeration;

import utils.system;

public class Resource extends ResourceBundle  {
    
   // private static Map<String,Object> lookup = null;

    public Resource() {
        for (int i = 0; i < 20000; i++) {
            contents[i][0] = "";
            contents[i][1] = "";

        }
       // System.out.println("resource bundle in Main Application");
    }
    

 private  Object[][] contents = new Object[20000][2];


    public Object[][] getContents() {
        if (resolvEl("#{sessionScope.lang_path}") != null)
        {
       /*  System.out.println( resolvEl("#{sessionScope.lang_path}").toString());
           if(resolvEl("#{sessionScope.lang_file}") != null)
         System.out.println(resolvEl("#{sessionScope.lang_file}").toString()); */
          
         }
       
       
       
       
        try {
            String path = "D:\\Resource\\";
            String res_file = "Resource_EN.xml";
            
             if (resolvEl("#{sessionScope.lang_path}") != null)
            { 
                path = resolvEl("#{sessionScope.lang_path}").toString();
                if(resolvEl("#{sessionScope.lang_file}") != null)
                res_file = resolvEl("#{sessionScope.lang_file}").toString();
               
              }
           // System.out.println("resource from global app"+path+res_file);
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
    
    

    // Implements java.util.ResourceBundle.handleGetObject; inherits javadoc specification.
    public final Object handleGetObject(String key) {
        // lazily load the lookup hashtable.
        
       
        Integer i=Integer.parseInt(resolvEl("#{sessionScope.lang_id}").toString());
        //System.out.println("In handle Get Object" +i);
        try{
            if (lookupArray[i]==null) {
              loadLookup(i);
            }
        }
        catch(IndexOutOfBoundsException e){
            e.printStackTrace();
            loadLookup(1);
        }
        if (key == null) {
            throw new NullPointerException();
        }
        return lookupArray[i].get(key); // this class ignores locales
    }

    /**
     * Returns an <code>Enumeration</code> of the keys contained in
     * this <code>ResourceBundle</code> and its parent bundles.
     *
     * @return an <code>Enumeration</code> of the keys contained in
     *         this <code>ResourceBundle</code> and its parent bundles.
     * @see #keySet()
     */
    public Enumeration<String> getKeys() {
        // lazily load the lookup hashtable.
        Integer i=Integer.parseInt(resolvEl("#{sessionScope.lang_id}").toString());
        try{
            if (lookupArray[i]==null) {
              loadLookup(i);
            }
        }
        catch(IndexOutOfBoundsException e){
            loadLookup(i);
        }
        
        ResourceBundle parent = this.parent;
        
        System.out.println("Getting Parent");
        
        return new ResourceBundleEnumeration(lookupArray[i].keySet(),
                (parent != null) ? parent.getKeys() : null);
    }

    /**
     * Returns a <code>Set</code> of the keys contained
     * <em>only</em> in this <code>ResourceBundle</code>.
     *
     * @return a <code>Set</code> of the keys contained only in this
     *         <code>ResourceBundle</code>
     * @since 1.6
     * @see #keySet()
     */
    protected Set<String> handleKeySet() {
        
        
        Integer i=Integer.parseInt(resolvEl("#{sessionScope.lang_id}").toString());
        //System.out.println("in handle Key Set" + i);
        try{
            if (lookupArray[i]==null) {
              loadLookup(i);
            }
        }
        catch(IndexOutOfBoundsException e){
            loadLookup(i);
        }
        return (lookupArray[i]).keySet();
    }

    /**
     * Returns an array in which each item is a pair of objects in an
     * <code>Object</code> array. The first element of each pair is
     * the key, which must be a <code>String</code>, and the second
     * element is the value associated with that key.  See the class
     * description for details.
     *
     * @return an array of an <code>Object</code> array representing a
     * key-value pair.
     */


    /**
     * We lazily load the lookup hashtable.  This function does the
     * loading.
     */
    private synchronized void loadLookup(Integer index) {
        
        Object[][] contents = getContents();
        HashMap<String,Object> temp = new HashMap<String,Object>(contents.length);
        for (int i = 0; i < contents.length; ++i) {
            // key must be non-null String, value must be non-null
            String key = (String) contents[i][0];
            Object value = contents[i][1];
            if (key == null || value == null) {
                throw new NullPointerException();
            }
            temp.put(key, value);
        }
        //lookup = temp;
        lookupArray[index]=temp; 
    }

    private Map<String,Object> lookup = null;
    
    private Map[] lookupArray=new Map[20];
    
    
    
}

