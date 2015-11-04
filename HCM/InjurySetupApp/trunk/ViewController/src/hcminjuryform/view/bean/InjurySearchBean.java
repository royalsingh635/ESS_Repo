package hcminjuryform.view.bean;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.output.RichOutputText;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.context.RequestContext;

public class InjurySearchBean {
    private RichOutputText docIdBind;

    public InjurySearchBean() {
    }
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void searchMethod(ActionEvent actionEvent) {
   OperationBinding _searchInjury = getBindings().getOperationBinding("searchInjuryForm");
        _searchInjury.execute();
    }

    public void resetMethod(ActionEvent actionEvent) {
        OperationBinding _resetInjury = getBindings().getOperationBinding("resetInjury");
             _resetInjury.execute();
    }

    public String ViewDocIdAction() {
        String docIdVar=docIdBind.getValue().toString();
        System.out.println("docIdVar ::: "+docIdVar);
        RequestContext.getCurrentInstance().getPageFlowScope().put("#{pageFlowScope.DOC_ID}", docIdVar);
        return "view";
    }

    public void setDocIdBind(RichOutputText docIdBind) {
        this.docIdBind = docIdBind;
    }

    public RichOutputText getDocIdBind() {
        return docIdBind;
    }

    public String createAction() {
        System.out.println("In Create.....");
        return "create";
    }
}
