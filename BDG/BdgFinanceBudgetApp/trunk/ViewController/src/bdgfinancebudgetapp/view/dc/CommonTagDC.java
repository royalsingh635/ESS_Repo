package bdgfinancebudgetapp.view.dc;


public class CommonTagDC {
    private StringBuffer structIdFirst;
    private StringBuffer structValFirst;
    private StringBuffer structValNmFirst;
    private StringBuffer structValNmDtlFirst;
    private StringBuffer structIdSecond;
    private StringBuffer structValSecond;
    private StringBuffer structValNmSecond;
    private StringBuffer structValNmDtlSecond;

    public CommonTagDC(StringBuffer structIdFirst, StringBuffer structValFirst, StringBuffer structValNmFirst,
                       StringBuffer structValNmDtlFirst) {
        this.structIdFirst = structIdFirst;
        this.structValFirst = structValFirst;
        this.structValNmFirst = structValNmFirst;
        this.structValNmDtlFirst = structValNmDtlFirst;
    }

    public CommonTagDC(StringBuffer structIdFirst, StringBuffer structValFirst, StringBuffer structValNmFirst,
                       StringBuffer structValNmDtlFirst, StringBuffer structIdSecond, StringBuffer structValSecond,
                       StringBuffer structValNmSecond, StringBuffer structValNmDtlSecond) {
        this.structIdFirst = structIdFirst;
        this.structValFirst = structValFirst;
        this.structValNmFirst = structValNmFirst;
        this.structValNmDtlFirst = structValNmDtlFirst;
        this.structIdSecond = structIdSecond;
        this.structValSecond = structValSecond;
        this.structValNmSecond = structValNmSecond;
        this.structValNmDtlSecond = structValNmDtlSecond;
    }

    public void setStructIdFirst(StringBuffer structIdFirst) {
        this.structIdFirst = structIdFirst;
    }

    public StringBuffer getStructIdFirst() {
        return structIdFirst;
    }

    public void setStructValFirst(StringBuffer structValFirst) {
        this.structValFirst = structValFirst;
    }

    public StringBuffer getStructValFirst() {
        return structValFirst;
    }

    public void setStructValNmFirst(StringBuffer structValNmFirst) {
        this.structValNmFirst = structValNmFirst;
    }

    public StringBuffer getStructValNmFirst() {
        return structValNmFirst;
    }

    public void setStructValNmDtlFirst(StringBuffer structValNmDtlFirst) {
        this.structValNmDtlFirst = structValNmDtlFirst;
    }

    public StringBuffer getStructValNmDtlFirst() {
        return structValNmDtlFirst;
    }

    public void setStructIdSecond(StringBuffer structIdSecond) {
        this.structIdSecond = structIdSecond;
    }

    public StringBuffer getStructIdSecond() {
        return structIdSecond;
    }

    public void setStructValSecond(StringBuffer structValSecond) {
        this.structValSecond = structValSecond;
    }

    public StringBuffer getStructValSecond() {
        return structValSecond;
    }

    public void setStructValNmSecond(StringBuffer structValNmSecond) {
        this.structValNmSecond = structValNmSecond;
    }

    public StringBuffer getStructValNmSecond() {
        return structValNmSecond;
    }

    public void setStructValNmDtlSecond(StringBuffer structValNmDtlSecond) {
        this.structValNmDtlSecond = structValNmDtlSecond;
    }

    public StringBuffer getStructValNmDtlSecond() {
        return structValNmDtlSecond;
    }
}
