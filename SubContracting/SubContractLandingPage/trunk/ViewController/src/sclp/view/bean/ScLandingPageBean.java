package sclp.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.ebiz.EbizParams;

import alertupdateservice.view.bean.AlertUpdateServiceBean;

import java.math.BigDecimal;

import javax.faces.component.UIOutput;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.OperationBinding;

public class ScLandingPageBean {
    private RichPanelGroupLayout panelGroup;
    private RichPanelFormLayout panelForm;

    public ScLandingPageBean() {
    }

    public void setPanelGroup(RichPanelGroupLayout panelGroup) {
        this.panelGroup = panelGroup;
    }

    public RichPanelGroupLayout getPanelGroup() {
        return panelGroup;
    }

    public void setPanelForm(RichPanelFormLayout panelForm) {
        this.panelForm = panelForm;
    }

    public RichPanelFormLayout getPanelForm() {
        return panelForm;
    }

    // Code For Alert and Update Service
    private AlertUpdateServiceBean bean = new AlertUpdateServiceBean();
    private int i = 0;

    public String getText() {
        if (i == 0) {
            this.printDetail();
            i++;
        }
        return "Y";
    }

    public void printDetail() {

        UIOutput o1 =
            bean.getAlert("300", "275", EbizParams.GLBL_APP_CLD_ID(), EbizParams.GLBL_APP_SERV_LOC(),
                          EbizParams.GLBL_HO_ORG_ID(), EbizParams.GLBL_APP_USR_ORG(), EbizParams.GLBL_APP_USR(), "010");
        this.panelForm.getChildren().add(o1);
        System.out.println("addind Refresh");
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.panelForm);
        AdfFacesContext.getCurrentInstance().addPartialTarget(o1);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelGroup());


    }

    public void getUpdate(ClientEvent clientEvent) {
        System.out.println("I am in bean");
        bean.getUpdate(this.getPanelForm(), EbizParams.GLBL_APP_CLD_ID(), EbizParams.GLBL_APP_SERV_LOC(),
                       EbizParams.GLBL_HO_ORG_ID(), EbizParams.GLBL_APP_USR_ORG(), EbizParams.GLBL_APP_USR(), "010");
        //    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelForm());
    }

    /**
     * Value Change Listner for Sub contracting type
     * */
    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void SubContractTypeVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            System.out.println("Printed Value ::::::::::::::::::: " + vce.getNewValue());
            OperationBinding binding = ADFBeanUtils.findOperation("setTopNCustomerServiceProvider");
            binding.getParamsMap().put("ho_org_id", EbizParams.GLBL_HO_ORG_ID());
            binding.getParamsMap().put("org_id", EbizParams.GLBL_APP_USR_ORG());
            binding.getParamsMap().put("sc_type", (Integer) vce.getNewValue());
            binding.getParamsMap().put("cld_id", EbizParams.GLBL_APP_CLD_ID());
            binding.getParamsMap().put("sloc_id", EbizParams.GLBL_APP_SERV_LOC());
            binding.getParamsMap().put("countVal", 5);
            binding.execute();

            OperationBinding binding1 = ADFBeanUtils.findOperation("setTopNProducts");
            binding1.getParamsMap().put("ho_org_id", EbizParams.GLBL_HO_ORG_ID());
            binding1.getParamsMap().put("org_id", EbizParams.GLBL_APP_USR_ORG());
            binding1.getParamsMap().put("p_inv_type", (Integer) vce.getNewValue());
            binding1.getParamsMap().put("cld_id", EbizParams.GLBL_APP_CLD_ID());
            binding1.getParamsMap().put("sloc_id", EbizParams.GLBL_APP_SERV_LOC());
            binding1.getParamsMap().put("countVal", 5);
            binding1.execute();

            OperationBinding binding2 = ADFBeanUtils.findOperation("setTopNOperation");
            binding2.getParamsMap().put("ho_org_id", EbizParams.GLBL_HO_ORG_ID());
            binding2.getParamsMap().put("org_id", EbizParams.GLBL_APP_USR_ORG());
            binding2.getParamsMap().put("p_inv_type", (Integer) vce.getNewValue());
            binding2.getParamsMap().put("cld_id", EbizParams.GLBL_APP_CLD_ID());
            binding2.getParamsMap().put("sloc_id", EbizParams.GLBL_APP_SERV_LOC());
            binding2.execute();
        }
    }

    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void TopCustomerAndSupplierVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            BigDecimal count = (BigDecimal) vce.getNewValue();
            int i = count.intValue();
            System.out.println("Printed Value ::::::::::::::::::: " + i);
            OperationBinding binding = ADFBeanUtils.findOperation("setTopNCustomerServiceProvider");
            binding.getParamsMap().put("ho_org_id", EbizParams.GLBL_HO_ORG_ID());
            binding.getParamsMap().put("org_id", EbizParams.GLBL_APP_USR_ORG());
            binding.getParamsMap().put("sc_type", 0);
            binding.getParamsMap().put("cld_id", EbizParams.GLBL_APP_CLD_ID());
            binding.getParamsMap().put("sloc_id", EbizParams.GLBL_APP_SERV_LOC());
            binding.getParamsMap().put("countVal", i);
            binding.execute();
        }
    }

    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void TopProductsVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            BigDecimal count = (BigDecimal) vce.getNewValue();
            int i = count.intValue();
            OperationBinding binding = ADFBeanUtils.findOperation("setTopNProducts");
            binding.getParamsMap().put("ho_org_id", EbizParams.GLBL_HO_ORG_ID());
            binding.getParamsMap().put("org_id", EbizParams.GLBL_APP_USR_ORG());
            binding.getParamsMap().put("p_inv_type", 0);
            binding.getParamsMap().put("cld_id", EbizParams.GLBL_APP_CLD_ID());
            binding.getParamsMap().put("sloc_id", EbizParams.GLBL_APP_SERV_LOC());
            binding.getParamsMap().put("countVal", i);
            binding.execute();
        }
    }

    /*---------------------------------Ticker Code------------------------------------*/

    public void tickerSettingACL(ActionEvent actionEvent) {
        // Add event code here...
    }
}
