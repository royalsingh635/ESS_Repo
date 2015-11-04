package slslandingapp.ds;

public class TickerDispDS {
    private Integer seqNo;
    private Integer tickerId;
    public TickerDispDS() {
        super();
    }
    public TickerDispDS(Integer seuenceNo, Integer Id){
        this.seqNo = seuenceNo;
        this.tickerId = Id;
    }

    public void setSeqNo(Integer seqNo) {
        this.seqNo = seqNo;
    }

    public Integer getSeqNo() {
        return seqNo;
    }

    public void setTickerId(Integer tickerId) {
        this.tickerId = tickerId;
    }

    public Integer getTickerId() {
        return tickerId;
    }
}
