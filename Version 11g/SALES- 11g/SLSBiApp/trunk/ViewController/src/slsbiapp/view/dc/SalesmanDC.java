package slsbiapp.view.dc;

public class SalesmanDC {
    private Integer eoId;
    private StringBuffer name;
    private StringBuffer location;
    private StringBuffer designation;
    private StringBuffer amount;
    
    public SalesmanDC(Integer eoId, StringBuffer name,StringBuffer location,StringBuffer designation, StringBuffer amount) {
        this.name = name;
        this.eoId = eoId;
        this.location = location;
        this.designation = designation;
        this.amount = amount;
    }
    public SalesmanDC(){
        
    }

    public void setEoId(Integer eoId) {
        this.eoId = eoId;
    }

    public Integer getEoId() {
        return eoId;
    }

    public void setName(StringBuffer name) {
        this.name = name;
    }

    public StringBuffer getName() {
        return name;
    }

    public void setLocation(StringBuffer location) {
        this.location = location;
    }

    public StringBuffer getLocation() {
        return location;
    }

    public void setDesignation(StringBuffer designation) {
        this.designation = designation;
    }

    public StringBuffer getDesignation() {
        return designation;
    }

    public void setAmount(StringBuffer amount) {
        this.amount = amount;
    }

    public StringBuffer getAmount() {
        return amount;
    }
}
