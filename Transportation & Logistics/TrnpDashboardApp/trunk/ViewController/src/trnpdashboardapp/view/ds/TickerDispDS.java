package trnpdashboardapp.view.ds;

public class TickerDispDS {

    private Integer seqNo;
    private String tickerId;
    private String tickerDesc;
    private String arrow;
    private String previousAmt;
    private String currentAmt;
    private String curr;

    public void setSeqNo(Integer seqNo) {
        this.seqNo = seqNo;
    }

    public Integer getSeqNo() {
        return seqNo;
    }

    public void setTickerId(String tickerId) {
        this.tickerId = tickerId;
    }

    public String getTickerId() {
        return tickerId;
    }

    public void setTickerDesc(String tickerDesc) {
        this.tickerDesc = tickerDesc;
    }

    public String getTickerDesc() {
        return tickerDesc;
    }

    public void setArrow(String arrow) {
        this.arrow = arrow;
    }

    public String getArrow() {
        return arrow;
    }

    public void setPreviousAmt(String previousAmt) {
        this.previousAmt = previousAmt;
    }

    public String getPreviousAmt() {
        return previousAmt;
    }

    public void setCurrentAmt(String currentAmt) {
        this.currentAmt = currentAmt;
    }

    public String getCurrentAmt() {
        return currentAmt;
    }

    public void setCurr(String curr) {
        this.curr = curr;
    }

    public String getCurr() {
        return curr;
    }

    public TickerDispDS() {
        super();
    }
}
