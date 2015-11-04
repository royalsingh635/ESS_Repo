package finauthoritypayapp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import java.util.Map;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.context.RequestContext;

public class SearchBean {
    private RichInputText docIdBind;

    public SearchBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String viewPaymentDetialsAction() {
        RequestContext context = RequestContext.getCurrentInstance();
        OperationBinding opchk = ADFBeanUtils.getOperationBinding("setDocId");
        opchk.getParamsMap().put("documentId", docIdBind.getValue());
        opchk.execute();
        RequestContext.getCurrentInstance().getPageFlowScope().put("ADD_EDIT_MODE", "V");
        return "viewPayDetail";
    }

    public void searchPayAL(ActionEvent actionEvent) {
        getBindings().getOperationBinding("searchPayDetail").execute();
    }

    public void resetPayAL(ActionEvent actionEvent) {
        getBindings().getOperationBinding("resetSearch").execute();
    }

    public String goToAddPaymentAction() {
        getBindings().getOperationBinding("CreateInsert").execute();
        RequestContext.getCurrentInstance().getPageFlowScope().put("ADD_EDIT_MODE", "A");
        return "add";
    }

    public void setDocIdBind(RichInputText docIdBind) {
        this.docIdBind = docIdBind;
    }

    public RichInputText getDocIdBind() {
        return docIdBind;
    }
}
