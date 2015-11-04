package mmbiapp.view.dc;

public class TagDC {
    private StringBuffer idFirst;
    private StringBuffer valueFirst;
    private StringBuffer descFirst;
    private StringBuffer idSecond;
    private StringBuffer valueSecond;
    private StringBuffer descSecond;
 
    public TagDC(StringBuffer idFirst,StringBuffer valueFirst,StringBuffer descFirst) {
        this.idFirst = idFirst;
        this.valueFirst = valueFirst;
        this.descFirst = descFirst;
    }
    
    public TagDC(StringBuffer idFirst,StringBuffer valueFirst,StringBuffer descFirst,StringBuffer idSecond,StringBuffer valueSecond,StringBuffer descSecond) {
        this.idFirst = idFirst;
        this.valueFirst = valueFirst;
        this.descFirst = descFirst;
        this.idSecond = idSecond;
        this.valueSecond = valueSecond;
        this.descSecond = descSecond;
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

    public void setDescFirst(StringBuffer descFirst) {
        this.descFirst = descFirst;
    }

    public StringBuffer getDescFirst() {
        return descFirst;
    }

    public void setDescSecond(StringBuffer descSecond) {
        this.descSecond = descSecond;
    }

    public StringBuffer getDescSecond() {
        return descSecond;
    }
}
