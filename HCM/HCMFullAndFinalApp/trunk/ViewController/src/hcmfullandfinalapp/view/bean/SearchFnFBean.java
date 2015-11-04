package hcmfullandfinalapp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;

import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.context.RequestContext;

public class SearchFnFBean {
    public SearchFnFBean() {
    }

    public void searchFnfAL(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("searchFnf").execute();
    }

    public void resetSearchAL(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("srchReset").execute();
    }

    public String clickOnEyeAction() {
        RequestContext context = RequestContext.getCurrentInstance();
        OperationBinding opchk = ADFBeanUtils.getOperationBinding("setDocId");
        opchk.execute();
        context.getPageFlowScope().put("GLBL_MODE", "V");
        return "view";
    }

    public String addNewFrmSearchPageAction() {
        RequestContext context = RequestContext.getCurrentInstance();
        ADFBeanUtils.getOperationBinding("CreateInsert").execute();
        OperationBinding operationBinding1 = ADFBeanUtils.getOperationBinding("insertIntoFNFandgGenrateDocId");
        operationBinding1.execute();
        if (operationBinding1.getErrors().isEmpty()) {
            context.getPageFlowScope().put("GLBL_MODE", "E");
            return "add";
        } else {
            System.out.println("some error during generation of doc id");
            return null;
        }

    }
}
