package mnfRoutingApp.view.bean;

import oracle.adf.view.rich.component.rich.input.RichInputDate;

public class MNFRoutingSearchBean {


    private RichInputDate createDtToBind;
    private RichInputDate createDtFrm;

    public MNFRoutingSearchBean() {
    }


    public void setCreateDtToBind(RichInputDate createDtToBind) {
        this.createDtToBind = createDtToBind;
    }

    public RichInputDate getCreateDtToBind() {
        return createDtToBind;
    }

    public void setCreateDtFrm(RichInputDate createDtFrm) {
        this.createDtFrm = createDtFrm;
    }

    public RichInputDate getCreateDtFrm() {
        return createDtFrm;
    }
}
