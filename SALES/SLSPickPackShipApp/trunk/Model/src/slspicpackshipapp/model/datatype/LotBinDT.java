package slspicpackshipapp.model.datatype;

import oracle.jbo.domain.Number;

public class LotBinDT {
    private String key;
    private Number value;

    public LotBinDT(String key, Number value) {
        this.key = key;
        this.value = value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setValue(Number value) {
        this.value = value;
    }

    public Number getValue() {
        return value;
    }
}
