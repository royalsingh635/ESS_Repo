package slsbiapp.view.dc;

public class SalesOrderAmtDC {
    private StringBuffer odrType;
    private StringBuffer odrDesc;
    private StringBuffer odrAmt;
    
    public SalesOrderAmtDC(StringBuffer odrType,StringBuffer odrDesc, StringBuffer odrAmt) {
        this.odrType = odrType;
        this.odrDesc = odrDesc;
        this.odrAmt = odrAmt;
    }
    public SalesOrderAmtDC(){
        
    }

    public void setOdrType(StringBuffer odrType) {
        this.odrType = odrType;
    }

    public StringBuffer getOdrType() {
        return odrType;
    }

    public void setOdrDesc(StringBuffer odrDesc) {
        this.odrDesc = odrDesc;
    }

    public StringBuffer getOdrDesc() {
        return odrDesc;
    }

    public void setOdrAmt(StringBuffer odrAmt) {
        this.odrAmt = odrAmt;
    }

    public StringBuffer getOdrAmt() {
        return odrAmt;
    }
}
