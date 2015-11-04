package appwfservdisp.view.bean;


public class HeaderImage {
    String action;
    Integer index;
    //    Integer j = 0;

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


    public HeaderImage(String a, Integer i) {
        super();
        this.action = a;
        this.index = i;


    }
}
