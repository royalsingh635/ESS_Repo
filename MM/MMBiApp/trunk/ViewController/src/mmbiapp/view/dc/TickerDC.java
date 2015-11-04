package mmbiapp.view.dc;

public class TickerDC {
    private StringBuffer header;
    private StringBuffer detail;
    private Integer tickerId;
    private boolean active;
    
    public TickerDC(Integer tickerId,StringBuffer header,StringBuffer detail,boolean active) {
        this.tickerId = tickerId;
        this.detail = detail;
        this.header = header;
        this.active = active;
    }

    public void setHeader(StringBuffer header) {
        this.header = header;
    }

    public StringBuffer getHeader() {
        return header;
    }

    public void setDetail(StringBuffer detail) {
        this.detail = detail;
    }

    public StringBuffer getDetail() {
        return detail;
    }

    public void setTickerId(Integer tickerId) {
        this.tickerId = tickerId;
    }

    public Integer getTickerId() {
        return tickerId;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }
}
