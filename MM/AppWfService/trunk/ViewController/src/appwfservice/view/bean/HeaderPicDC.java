package appwfservice.view.bean;

public class HeaderPicDC {
    private String action;
    private Integer index;
    public HeaderPicDC(String a, Integer i) {
        super();
        this.action = a;
        this.index = i;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getIndex() {
        return index;
    }
}
