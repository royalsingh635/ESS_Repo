package ebizresources.resource.bundle;

import adf.utils.model.ADFModelUtils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.ListResourceBundle;
import java.util.Map;
import java.util.ResourceBundle;

import java.util.Set;

import sun.util.ResourceBundleEnumeration;

public class Resource extends ResourceBundle {
    private Map[] lookupArray = new Map[20];
    private static Object[][] contents = EbizLangResource.initContents();

    public Resource() {
        System.out.println("Resource ________________");
        for (int i = 0; i < 15000; i++) {
            contents[i][0] = "";
            contents[i][1] = "";
        }
    }

    public Object[][] getContents() {
        /**
         * GLBL_RES_PATH -- GLBL Parameter for getting Resource path define app$sevr$loc
         * GLBL_RES_FILE -- GLBL Parameter for getting Resource File Selected in Main application.
         */
        // First it picks file name and path from session scope when it runs in main applicaiton
        // else when running in a local application it picks name and path from pageFlowScope
        Object filePathLangO = ADFModelUtils.resolvEl("#{sessionScope.lang_path}");
        Object fileNameLangO = ADFModelUtils.resolvEl("#{sessionScope.lang_file}");
        Object filePathResO = null;
        Object fileNameResO = null;
        if(filePathLangO == null || fileNameLangO == null){
            filePathResO = ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_RES_PATH}");
            fileNameResO = ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_RES_FILE}");
        }else{
            filePathResO = filePathLangO;
            fileNameResO = fileNameLangO;
        }
        String filePath = (filePathResO == null ? "D:\\Resource\\" : filePathResO.toString());
        String fileName = (fileNameResO == null ? "Resource_EN.xml" : fileNameResO.toString());
        
        return EbizLangResource.getContents(contents, filePath, fileName);
    }

    // Implements java.util.ResourceBundle.handleGetObject; inherits javadoc specification.
    public final Object handleGetObject(String key) {
        // lazily load the lookup hashtable.
        Object langIdO = ADFModelUtils.resolvEl("#{sessionScope.lang_id}");
        Integer i = (langIdO == null ?  1 : (Integer) langIdO);
        try {
            if (lookupArray[i] == null) {
                loadLookup(i);
            }
        } catch (IndexOutOfBoundsException e) {
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
        Object langIdO = ADFModelUtils.resolvEl("#{sessionScope.lang_id}");
        Integer i = (Integer) langIdO;
        try {
            if (lookupArray[i] == null) {
                loadLookup(i);
            }
        } catch (IndexOutOfBoundsException e) {
            loadLookup(i);
        }

        ResourceBundle parent = this.parent;
        return new ResourceBundleEnumeration(lookupArray[i].keySet(), (parent != null) ? parent.getKeys() : null);
    }

    /**
     * Returns a <code>Set</code> of the keys contained
     * <em>only</em> in this <code>ResourceBundle</code>.
     * @return a <code>Set</code> of the keys contained only in this
     *         <code>ResourceBundle</code>
     * @since 1.6
     * @see #keySet()
     */
    protected Set<String> handleKeySet() {
        Object langIdO = ADFModelUtils.resolvEl("#{sessionScope.lang_id}");
        Integer i = (Integer) langIdO;
        try {
            if (lookupArray[i] == null) {
                loadLookup(i);
            }
        } catch (IndexOutOfBoundsException e) {
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
     * @return an array of an <code>Object</code> array representing a
     * key-value pair.
     */


    /**
     * We lazily load the lookup hashtable.  This function does the
     * loading.
     */
    private synchronized void loadLookup(Integer index) {
        Object[][] contents = getContents();
        HashMap<String, Object> temp = new HashMap<String, Object>(contents.length);
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
        lookupArray[index] = temp;
    }
}
