package slspricevssalesanalysisapp.model.helper;

public class ItmDataDS {
    private String itmId;
    private String itmDesc;
    public ItmDataDS(String itmId,String itmDesc) {
        super();
        this.itmId = itmId;
        this.itmDesc = itmDesc;
    }

    public void setItmId(String itmId) {
        this.itmId = itmId;
    }

    public String getItmId() {
        return itmId;
    }

    public void setItmDesc(String itmDesc) {
        this.itmDesc = itmDesc;
    }

    public String getItmDesc() {
        return itmDesc;
    }
}
