package fincashflowapp.view.bean;

public class Filter {
    
    private String Filter_typ;
    private String Filter_Nm;
    private String Remove;
    private String Filter_id;
    
    public Filter() {
        super();
    }
    public Filter(String fil_typ, String fil_nm ,String rem, String fil_id ) {
        this.Filter_typ = fil_typ;
        this.Filter_Nm = fil_nm;
        this.Remove = rem;
        this.Filter_id = fil_id;
    }

    public void setFilter_typ(String Filter_typ) {
        this.Filter_typ = Filter_typ;
    }

    public String getFilter_typ() {
        return Filter_typ;
    }

    public void setFilter_Nm(String Filter_Nm) {
        this.Filter_Nm = Filter_Nm;
    }

    public String getFilter_Nm() {
        return Filter_Nm;
    }

    public void setRemove(String Remove) {
        this.Remove = Remove;
    }

    public String getRemove() {
        return Remove;
    }

    public void setFilter_id(String Filter_id) {
        this.Filter_id = Filter_id;
    }

    public String getFilter_id() {
        return Filter_id;
    }
}
