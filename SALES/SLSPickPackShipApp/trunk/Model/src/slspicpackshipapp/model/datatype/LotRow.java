package slspicpackshipapp.model.datatype;

import oracle.jbo.domain.Number;

public class LotRow {
    private String lotId;
    private String binId;
    private String srNo;
    private Number okQty;
    private Number itmPrice;

    public void setItmPrice(Number itmPrice) {
        this.itmPrice = itmPrice;
    }

    public Number getItmPrice() {
        return itmPrice;
    }

    public void setBinId(String binId) {
        this.binId = binId;
    }

    public String getBinId() {
        return binId;
    }

    public void setSrNo(String srNo) {
        this.srNo = srNo;
    }

    public String getSrNo() {
        return srNo;
    }
    
    
    public LotRow(String lotId,String binId,String srNo,Number okQty,Number itmPrice) {
        this.lotId = lotId;
        this.okQty = okQty;
        this.binId = binId;
        this.srNo = srNo;
        this.itmPrice = itmPrice;
    }

    public LotRow() {
        this.okQty = new Number(0);
    }

    public void setLotId(String lotId) {
        this.lotId = lotId;
    }

    public String getLotId() {
        return lotId;
    }

    public void setOkQty(Number okQty) {
        this.okQty = okQty;
    }

    public Number getOkQty() {
        return okQty;
    }

    
    public String toString(){
        return lotId+" | "+binId+" | "+srNo+" | "+okQty+"\n";
    }
}