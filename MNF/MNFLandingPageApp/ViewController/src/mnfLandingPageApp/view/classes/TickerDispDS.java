package mnfLandingPageApp.view.classes;

public class TickerDispDS {
    private Integer seqNo;
    private String tickerId; 
    private String tickerDesc;
    private String arrow;
    private String previousAmt;
    private String currentAmt;
    private String curr;

    /**
     * @param seuenceNo
     * @param Id
     */
    public TickerDispDS(Integer seuenceNo, String Id){
        this.seqNo = seuenceNo;
        this.tickerId = Id;
    }

    /**
     * @param tickerDesc
     */
    public void setTickerDesc(String tickerDesc) {
        this.tickerDesc = tickerDesc;
    }

    /**
     * @return
     */
    public String getTickerDesc() {
        return tickerDesc;
    }

    /**
     * @param arrow
     */
    public void setArrow(String arrow) {
        this.arrow = arrow;
    }

    /**
     * @return
     */
    public String getArrow() {
        return arrow;
    }

    /**
     * @param previousAmt
     */
    public void setPreviousAmt(String previousAmt) {
        this.previousAmt = previousAmt;
    }

    /**
     * @return
     */
    public String getPreviousAmt() {
        return previousAmt;
    }

    /**
     * @param currentAmt
     */
    public void setCurrentAmt(String currentAmt) {
        this.currentAmt = currentAmt;
    }

    /**
     * @return
     */
    public String getCurrentAmt() {
        return currentAmt;
    }

    /**
     * @param curr
     */
    public void setCurr(String curr) {
        this.curr = curr;
    }

    /**
     * @return
     */
    public String getCurr() {
        return curr;
    }

    public TickerDispDS() {
        super();
    }

    /**
     * @param seqNo
     */
    public void setSeqNo(Integer seqNo) {
        this.seqNo = seqNo;
    }

    /**
     * @return
     */
    public Integer getSeqNo() {
        return seqNo;
    }

    /**
     * @param tickerId
     */
    public void setTickerId(String tickerId) {
        this.tickerId = tickerId;
    }

    /**
     * @return
     */
    public String getTickerId() {
        return tickerId;
    }
}
