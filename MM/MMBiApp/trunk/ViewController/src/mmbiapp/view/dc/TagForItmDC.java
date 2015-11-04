package mmbiapp.view.dc;

public class TagForItmDC {
    private StringBuffer idFirst;
    private StringBuffer valueFirst;
    private StringBuffer idSecond;
    private StringBuffer valueSecond;
 
    public TagForItmDC(StringBuffer idFirst,StringBuffer valueFirst) {
        this.idFirst = idFirst;
        this.valueFirst = valueFirst;
    }
    
    public TagForItmDC(StringBuffer idFirst,StringBuffer valueFirst,StringBuffer idSecond,StringBuffer valueSecond) {
        this.idFirst = idFirst;
        this.valueFirst = valueFirst;
        this.idSecond = idSecond;
        this.valueSecond = valueSecond;
    }

    public void setIdFirst(StringBuffer idFirst) {
        this.idFirst = idFirst;
    }

    public StringBuffer getIdFirst() {
        return idFirst;
    }

    public void setValueFirst(StringBuffer valueFirst) {
        this.valueFirst = valueFirst;
    }

    public StringBuffer getValueFirst() {
        return valueFirst;
    }

    public void setIdSecond(StringBuffer idSecond) {
        this.idSecond = idSecond;
    }

    public StringBuffer getIdSecond() {
        return idSecond;
    }

    public void setValueSecond(StringBuffer valueSecond) {
        this.valueSecond = valueSecond;
    }

    public StringBuffer getValueSecond() {
        return valueSecond;
    }
}
