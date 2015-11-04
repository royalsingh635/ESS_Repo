package slspicpackshipapp.model.datatype;

import adf.utils.ebiz.EbizParams;
import adf.utils.ebiz.EbizParamsAPPUtils;
import adf.utils.model.ADFModelUtils;

import java.sql.Types;

import slspicpackshipapp.model.service.pickPackShipAMImpl;

public class PickDtlsDS {
    private String pickDocId;
    private String pickId;
    private String whId;
    private String whNm;
    private Integer eoId;
    private String eoNm;
    private Integer currIdSp;
    private String prjId;
    private String shipAdds;

    
    /// COstm center probs in AMImppl
    public PickDtlsDS(String pickDocId, String pickId, String whId, Integer eoId, String eoNm, Integer currIdSp,
                      String prjId, String shipAdds) {
        super();
        this.pickDocId = pickDocId;
        this.pickId = pickId;
        this.whId = whId;
        /**
         * P_SLOC_ID   NUMBER,
    P_CLD_ID    VARCHAR2,
    P_HO_ORG_ID VARCHAR2,
    P_ORG_ID    VARCHAR2,
    P_WH_ID
         */
        pickPackShipAMImpl am =
            (pickPackShipAMImpl) ADFModelUtils.resolvEl("#{data.pickPackShipAMDataControl.dataProvider}");
        String descFrmWhId = EbizParamsAPPUtils.getWhDescFrmWhId(am, whId);
        // System.out.println(whName + " For the wh ID " + whId);
        this.whNm = descFrmWhId;
        this.eoId = eoId;
        this.eoNm = eoNm;
        this.currIdSp = currIdSp;
        this.prjId = prjId;
        this.shipAdds = shipAdds;
    }

    public void setPickDocId(String pickDocId) {
        this.pickDocId = pickDocId;
    }

    public String getPickDocId() {
        return pickDocId;
    }

    public void setPickId(String pickId) {
        this.pickId = pickId;
    }

    public String getPickId() {
        return pickId;
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

    public void setEoId(Integer eoId) {
        this.eoId = eoId;
    }

    public Integer getEoId() {
        return eoId;
    }

    public void setEoNm(String eoNm) {
        this.eoNm = eoNm;
    }

    public String getEoNm() {
        return eoNm;
    }

    public String toString() {
        return eoId + "_" + whId;
    }
    public void setShipAdds(String shipAdds) {
        this.shipAdds = shipAdds;
    }

    public String getShipAdds() {
        return shipAdds;
    }

    public void setPrjId(String prjId) {
        this.prjId = prjId;
    }

    public String getPrjId() {
        return prjId;
    }

    public void setCurrIdSp(Integer currIdSp) {
        this.currIdSp = currIdSp;
    }

    public Integer getCurrIdSp() {
        return currIdSp;
    }
}
