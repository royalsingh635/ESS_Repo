package custommandtrychkapp.model.data;

public class A {
    private StringBuffer attNm;
    private StringBuffer attDesc;
    public A(StringBuffer attNm,StringBuffer attDesc) {
        super();
        this.attDesc = attDesc;
        this.attNm = attNm;
    }

    public void setAttNm(StringBuffer attNm) {
        this.attNm = attNm;
    }

    public StringBuffer getAttNm() {
        return attNm;
    }

    public void setAttDesc(StringBuffer attDesc) {
        this.attDesc = attDesc;
    }

    public StringBuffer getAttDesc() {
        return attDesc;
    }
}
