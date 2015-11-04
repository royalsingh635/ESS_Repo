package mnfBillOfMaterialsApp.view.bean;

import oracle.adf.view.rich.component.rich.input.RichInputDate;

public class BOMSearchBean {
    private RichInputDate createDtFrmBinding;
    private RichInputDate createDtToBinding;

    public BOMSearchBean() {
    }

    public void setCreateDtFrmBinding(RichInputDate createDtFrmBinding) {
        this.createDtFrmBinding = createDtFrmBinding;
    }

    public RichInputDate getCreateDtFrmBinding() {
        return createDtFrmBinding;
    }

    public void setCreateDtToBinding(RichInputDate createDtToBinding) {
        this.createDtToBinding = createDtToBinding;
    }

    public RichInputDate getCreateDtToBinding() {
        return createDtToBinding;
    }

    
}
