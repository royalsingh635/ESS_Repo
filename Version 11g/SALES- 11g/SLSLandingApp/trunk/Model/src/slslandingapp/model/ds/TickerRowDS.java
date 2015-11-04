package slslandingapp.model.ds;

public class TickerRowDS {
    private Integer tickerId;
    private Integer sequenceNo;
    private StringBuffer tickerDesc;
    
    public TickerRowDS() {
        super();
    }
    public TickerRowDS(Integer t , Integer s,StringBuffer d) {
        this.tickerId = t;
        this.sequenceNo = s;
        this.tickerDesc = d;
    }

    public void setTickerId(Integer tickerId) {
        this.tickerId = tickerId;
    }

    public Integer getTickerId() {
        return tickerId;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public void setTickerDesc(StringBuffer tickerDesc) {
        this.tickerDesc = tickerDesc;
    }

    public StringBuffer getTickerDesc() {
        return tickerDesc;
    }
}
