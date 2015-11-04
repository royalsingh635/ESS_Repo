package slsrmaapp.model.dc;

import oracle.jbo.domain.Number;

public class LotRow {
    private String lotId;
    private String binId;
    private String srNo;
    private Number okQty;
    private Number rejQty;
    private Number rwkQty;

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


    public LotRow(String lotId, String binId, Number okQty, Number rejQty, Number rwkQty) {
        this.lotId = lotId;
        this.okQty = okQty;
        this.rejQty = rejQty;
        this.rwkQty = rwkQty;
        this.binId = binId;
    }

    public LotRow() {
        this.okQty = new Number(0);
        this.rejQty = new Number(0);
        this.rwkQty = new Number(0);
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

    public void setRejQty(Number rejQty) {
        this.rejQty = rejQty;
    }

    public Number getRejQty() {
        return rejQty;
    }

    public void setRwkQty(Number rwkQty) {
        this.rwkQty = rwkQty;
    }

    public Number getRwkQty() {
        return rwkQty;
    }

    public String toString() {
        return lotId + " | " + okQty + " | " + rejQty + " | " + rwkQty + "\n";
    }
}
