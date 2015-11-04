package slsbiapp.view.dc;

public class TimeSelectorDC {
    private StringBuffer linkDesc;
    private String linkId;
    private boolean active;
    public TimeSelectorDC(String linkId,StringBuffer linkDesc,boolean actv) {
       this.linkId = linkId;
       this.linkDesc = linkDesc;
       this.active = actv;
    }

    public void setLinkDesc(StringBuffer linkDesc) {
        this.linkDesc = linkDesc;
    }

    public StringBuffer getLinkDesc() {
        return linkDesc;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }
}
