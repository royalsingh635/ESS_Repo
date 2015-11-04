package mmsoctktakeapp.model.services;

import oracle.jbo.domain.Number;

public class BarCodeData implements Comparable<BarCodeData> {
    private String whId;
    private String whNm;
    private String lotId;
    private String binId;
    private String binNm;


    private String srNo;
    private String itmId;
    private String itmNm;
    private String itmUom;
    private Number phyQty;
    private Number rwkQty;
    private Number scrpQty;
    private String serFlg = "N";


    public BarCodeData(String whId, String whNm, String itmId, String itmNm, String binId, String binNm, String lotId,
                       String itmUom, String serFlg, String srNo, Number phyQty, Number rwkQty, Number scrpQty) {
        this.whId = whId;
        this.whNm = whNm;
        this.itmId = itmId;
        this.itmNm = itmNm;
        this.binId = binId;
        this.binNm = binNm;
        this.lotId = lotId;
        this.itmUom = itmUom;
        // this.serFlg = serFlg;
        this.phyQty = phyQty;
        this.rwkQty = rwkQty;
        this.scrpQty = scrpQty;
        this.srNo = srNo;
        if (!srNo.equalsIgnoreCase("0")) {
            this.serFlg = "Y";
        }
    }

    public void setBinNm(String binNm) {
        this.binNm = binNm;
    }

    public String getBinNm() {
        return binNm;
    }

    public void setSerFlg(String serFlg) {
        this.serFlg = serFlg;
    }

    public String getSerFlg() {
        return serFlg;
    }


    public void setItmNm(String itmNm) {
        this.itmNm = itmNm;
    }

    public String getItmNm() {
        return itmNm;
    }

    public void setWhId(String whId) {
        this.whId = whId;
    }

    public String getWhId() {
        return whId;
    }

    public void setWhNm(String whNm) {
        this.whNm = whNm;
    }

    public String getWhNm() {
        return whNm;
    }

    public void setLotId(String lotId) {
        this.lotId = lotId;
    }

    public String getLotId() {
        return lotId;
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

    public void setItmId(String itmId) {
        this.itmId = itmId;
    }

    public String getItmId() {
        return itmId;
    }

    public void setItmUom(String itmUom) {
        this.itmUom = itmUom;
    }

    public String getItmUom() {
        return itmUom;
    }

    public void setPhyQty(Number phyQty) {
        this.phyQty = phyQty;
    }

    public Number getPhyQty() {
        return phyQty;
    }

    public void setRwkQty(Number rwkQty) {
        this.rwkQty = rwkQty;
    }

    public Number getRwkQty() {
        return rwkQty;
    }

    public void setScrpQty(Number scrpQty) {
        this.scrpQty = scrpQty;
    }

    public Number getScrpQty() {
        return scrpQty;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BarCodeData) {
            BarCodeData code = (BarCodeData) obj;
            if (code.getSerFlg().equalsIgnoreCase("y")) {
                if (code.whId.equalsIgnoreCase(this.whId) && code.itmId.equalsIgnoreCase(this.itmId) &&
                    code.lotId.equalsIgnoreCase(this.lotId) && code.binId.equalsIgnoreCase(this.binId) &&
                    code.getSrNo().equalsIgnoreCase(this.srNo)) {
                    return true;
                }
            } else if (code.whId.equalsIgnoreCase(this.whId) && code.itmId.equalsIgnoreCase(this.itmId) &&
                       code.lotId.equalsIgnoreCase(this.lotId) && code.binId.equalsIgnoreCase(this.binId)) {
                return true;
            }
        } else {
            super.equals(obj);
        }
        return false;
    }

    @Override
    public int compareTo(BarCodeData o) {
        int x =
            (this.whId.compareTo(o.whId)) + (this.itmId.compareTo(o.itmId)) + (this.lotId.compareTo(o.lotId)) +
            (this.binId.compareTo(o.binId) + this.srNo.compareTo(o.srNo));
        return x;
    }
}
