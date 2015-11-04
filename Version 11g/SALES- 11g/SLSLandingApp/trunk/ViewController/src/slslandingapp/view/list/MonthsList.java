package slslandingapp.view.list;

public class MonthsList {
    private StringBuffer mon;
    private Integer monId;
    private StringBuffer enabled;
    public MonthsList() {
        super();
    }
    public MonthsList(StringBuffer m, Integer i,StringBuffer e){
        this.mon = m;
        this.monId = i;
        this.enabled = e;
    }

    public void setmonId(Integer monId) {
        this.monId = monId;
    }

    public Integer getmonId() {
        return monId;
    }

    public void setMon(StringBuffer mon) {
        this.mon = mon;
    }

    public StringBuffer getMon() {
        return mon;
    }

    public void setEnabled(StringBuffer enabled) {
        this.enabled = enabled;
    }

    public StringBuffer getEnabled() {
        return enabled;
    }
}
