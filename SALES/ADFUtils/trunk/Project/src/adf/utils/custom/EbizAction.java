package adf.utils.custom;

public abstract class EbizAction {
    public EbizAction() {
        super();
    }

    /**
     * @return
     */
    public abstract String doAction();
    
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
