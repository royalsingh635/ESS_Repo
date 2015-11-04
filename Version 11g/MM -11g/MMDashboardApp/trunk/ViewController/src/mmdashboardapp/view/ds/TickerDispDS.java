package mmdashboardapp.view.ds;

public class TickerDispDS {
    private Integer seqNo;
    private String tickerId;
    public TickerDispDS() {
        super();
    }
    public TickerDispDS(Integer seuenceNo, String Id){
        this.seqNo = seuenceNo;
        this.tickerId = Id;
    }

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
}
