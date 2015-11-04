package slslandingapp.ds;

public class TickerRowDS {
    private Integer seqNo;
    private String tickerId; 
    private StringBuilder tickerDesc;
    private StringBuilder arrow;
    private Number previousAmt;
    private Number currentAmt;
    private StringBuilder curr;
    private Integer sequenceNo;
    
    public TickerRowDS() {
        super();
    }
    public TickerRowDS(String t , Integer s,StringBuilder d) {
        this.tickerId = t;
        this.sequenceNo = s;
        this.tickerDesc = d;
    }

    public void setTickerId(String tickerId) {
        this.tickerId = tickerId;
    }

    public String getTickerId() {
        return tickerId;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public void setTickerDesc(StringBuilder tickerDesc) {
        this.tickerDesc = tickerDesc;
    }

    public StringBuilder getTickerDesc() {
        return tickerDesc;
    }
}
