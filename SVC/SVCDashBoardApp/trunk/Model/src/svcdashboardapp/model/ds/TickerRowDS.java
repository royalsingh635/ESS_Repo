package svcdashboardapp.model.ds;

public class TickerRowDS {
    private String tickerId;
    private Integer sequenceNo;
    private StringBuffer tickerDesc;
    
    public TickerRowDS() {
        super();
    }
    public TickerRowDS(String t , Integer s,StringBuffer d) {
        this.tickerId = t;
        this.sequenceNo = s;
        this.tickerDesc = d;
        
    }

    

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }
///-----gfg bn hjbg 
    public Integer getSequenceNo() {
        
        
        //jedrr
        return sequenceNo;
    }

    public void setTickerDesc(StringBuffer tickerDesc) {
        this.tickerDesc = tickerDesc;
    }

    public StringBuffer getTickerDesc() {
        return tickerDesc;
    }

    public void setTickerId(String tickerId) {
        this.tickerId = tickerId;
    }

    public String getTickerId() {
        return tickerId;
    }
}
