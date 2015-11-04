package adf.utils.custom;

public abstract class EbizMethod {
    Object object;

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

    public EbizMethod() {
        super();
    }


    /**
     * @param obj
     * @return
     */
    public abstract Object processMethod(Object[] obj);
}