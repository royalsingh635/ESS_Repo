package slsrmaapp.model.dc;

import oracle.jbo.domain.Number;

public class ItmRcpt {
    private StringBuilder itmNm;
    private Number itmRcptQty;
    private StringBuilder serialFlag;
    private Number aprovQty;
    
    
    public ItmRcpt(StringBuilder itmNm,Number itmRcptQty) {
        this.itmNm = itmNm;
        this.itmRcptQty = itmRcptQty;
    }
    public ItmRcpt(StringBuilder itmNm,Number itmRcptQty,Number apovQty){
        this.itmNm=itmNm;
        this.itmRcptQty=itmRcptQty;
        this.aprovQty=apovQty;
        
        
        }
    public ItmRcpt() {
        super();
        
    }


    public void setItmNm(StringBuilder itmNm) {
        this.itmNm = itmNm;
    }

    public StringBuilder getItmNm() {
        return itmNm;
    }

    public void setItmRcptQty(Number itmRcptQty) {
        this.itmRcptQty = itmRcptQty;
    }

    public Number getItmRcptQty() {
        return itmRcptQty;
    }

    public void setSerialFlag(StringBuilder serialFlag) {
        this.serialFlag = serialFlag;
    }

    public StringBuilder getSerialFlag() {
        return serialFlag;
    }

    public void setAprovQty(Number aprovQty) {
        this.aprovQty = aprovQty;
    }

    public Number getAprovQty() {
        return aprovQty;
    }
}
