package adf.utils.custom;

import javax.faces.event.ActionListener;

public abstract class EbizActionListener implements ActionListener {
    public EbizActionListener() {
        super();
    }
    
    /**
     * This bean Object will hold the reference of current bean .So that we can directly call
     * setter and geeters of bean after casting to the bean
     * 
     * */
    Object object=null;

    /**
     * @param object
     */
    public void setObject(Object object) {
        this.object = object;
    }

    /**
     * @return
     */
    public Object getObject() {
        return object;
    }


}
