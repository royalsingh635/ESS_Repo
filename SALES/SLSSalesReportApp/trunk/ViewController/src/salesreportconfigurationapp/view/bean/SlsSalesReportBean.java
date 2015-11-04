package salesreportconfigurationapp.view.bean;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.Timestamp;

public class SlsSalesReportBean {

    /**
     * For passing the reportId in the servlet url.
     * Please refer values given below:
     *
     * 1. Sales Report
     * 2. Opportunity
     */

    /**
     * GlobalVariables
     */
    Integer slocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
    Integer userId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
    StringBuffer cldId = new StringBuffer(resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}"));
    StringBuffer orgId = new StringBuffer(resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}"));
    StringBuffer hoOrgId = new StringBuffer(resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}"));


    private String reportURL;

    private RichInputListOfValues slsInvoiceDocIdBind;
    private RichSelectOneRadio fileTypeBind;
    private RichInputListOfValues oppIdBinding;
    private RichInputDate fromDateBinding;
    private RichInputDate toDateBinding;
    private RichInputListOfValues salesOrderStatusBinding;
    private RichInputListOfValues intmnNoBinding;
    private RichSelectOneChoice catgIdBinding;
    private RichSelectOneChoice entityIdBinding;
    private RichSelectOneChoice pickIdBinding;
    private RichSelectOneChoice cancelOrderIdBinding;
    private RichSelectOneChoice invoiceIdBinding;

    public SlsSalesReportBean() {
    }

    /**
     * Method for resolving the El
     */
    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }
    
    
    public Object resolvElDCMsg(String data) {
           FacesContext fc = FacesContext.getCurrentInstance();
           Application app = fc.getApplication();
           ExpressionFactory elFactory = app.getExpressionFactory();
           ELContext elContext = fc.getELContext();
           ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
           return valueExp.getValue(elContext);
       } 



    
    /**
     * Generates the URL for  calling 1.Sales Invoice Report
     */
    public void generateUrlForSalesInvoiceReport() {
        if (this.getSlsInvoiceDocIdBind().getValue() != null) {
            if (this.getFileTypeBind().getValue() != null) {
                OperationBinding binding = this.getBindings().getOperationBinding("SalesInvoiceDocIdFromDispId");
                binding.getParamsMap().put("dispId", new StringBuffer(getSlsInvoiceDocIdBind().getValue().toString()));
                binding.execute();
                if (binding.getResult() != null) {
                    StringBuffer docId = (StringBuffer)binding.getResult();
                    String url =
                        "/salesreportserv?" + "reportId=1" + "&docId=" + docId.toString() + "&fileType=" + this.getFileTypeBind().getValue().toString() +
                        "&cldId=" + cldId.toString() + "&orgId=" + orgId.toString() + "&hoOrgId=" +
                        hoOrgId.toString() + "&slocId=" + slocId + "&fileType=" +
                        getFileTypeBind().getValue().toString();
                    this.reportURL = url;
                    System.out.println("Final callback url is :" + reportURL);
                } else {
                    System.out.println("DocId not found for the given display id.");
                }
            } else {
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.900']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext.getCurrentInstance().addMessage(getFileTypeBind().getClientId(), message);
            }

        } else {
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.902']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(getSlsInvoiceDocIdBind().getClientId(), message);
        }
    }


    /**
     * Generates the URL for  calling 2.Sales Opportunity Report
     */
    public void generateUrlForSalesOpportunityReport() {
        String dispdocId=null;
        Timestamp fromDt = (oracle.jbo.domain.Timestamp)fromDateBinding.getValue();
        oracle.jbo.domain.Timestamp toDt = (oracle.jbo.domain.Timestamp)toDateBinding.getValue();
        System.out.println(fromDt+"   "+toDt);
        if (this.getOppIdBinding().getValue() != null) {
            if (this.getFileTypeBind().getValue() != null) {
                dispdocId = "01"+oppIdBinding.getValue().toString();
                String url =
                            "/salesreportserv?" + 
                            "reportId=2" + 
                            "&oppId=" +dispdocId+ 
                            "&FromDate=" + fromDt + 
                            "&ToDate=" + toDt+
                            "&fileType=" + this.getFileTypeBind().getValue().toString() +
                            "&cldId=" + cldId.toString() + "&orgId=" + orgId.toString() + "&hoOrgId=" +
                            hoOrgId.toString() + "&slocId=" + slocId + "&fileType=" +
                            getFileTypeBind().getValue().toString();
                        this.reportURL = url;
                        System.out.println("Final callback url is :" + reportURL);
            } else {
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.900']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext.getCurrentInstance().addMessage(getFileTypeBind().getClientId(), message);
            }

        } else {
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.902']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(getSlsInvoiceDocIdBind().getClientId(), message);
        }
    }


    /**
     * Generates the URL for  calling 3.Sales Order Status Report
     */
    public void generateUrlForSalesOrderStatusReport() {
        String dispSoId=null;
        System.out.println("dispSoId" + dispSoId);
        if (this.getSalesOrderStatusBinding().getValue() != null) {
            if (this.getFileTypeBind().getValue() != null) {
                dispSoId = "01" + salesOrderStatusBinding.getValue().toString();
                System.out.println("dispSoId : "+dispSoId);
                String url =
                            "/salesreportserv?" + 
                            "reportId=3" + 
                            "&soId=" +dispSoId+ 
                            //"&FromDate" + fromDt + 
                            //"&ToDate" + toDt+
                            "&fileType=" + this.getFileTypeBind().getValue().toString() +
                            "&cldId=" + cldId.toString() + "&orgId=" + orgId.toString() + "&hoOrgId=" +
                            hoOrgId.toString() + "&slocId=" + slocId + "&fileType=" +
                            getFileTypeBind().getValue().toString();
                        this.reportURL = url;
                        System.out.println("Final callback url is :" + reportURL);
            } else {
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.900']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext.getCurrentInstance().addMessage(getFileTypeBind().getClientId(), message);
            }

        } else {
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.902']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(getSlsInvoiceDocIdBind().getClientId(), message);
        }
    }


    /**
     * Generates the URL for  calling 4.Sales Intimation Report
     */
    public void generateUrlForSalesIntmSlipReport() {
        String dispSoId=null;
        System.out.println("dispSoId" + dispSoId);
        if (this.getIntmnNoBinding().getValue() != null) {
            if (this.getFileTypeBind().getValue() != null) {
                dispSoId = "01" + intmnNoBinding.getValue().toString();
                System.out.println("dispSoId : "+dispSoId);
                String url =
                            "/salesreportserv?" + 
                            "reportId=4" + 
                            "&intmId=" +dispSoId+ 
                            //"&FromDate" + fromDt + 
                            //"&ToDate" + toDt+
                            "&fileType=" + this.getFileTypeBind().getValue().toString() +
                            "&cldId=" + cldId.toString() + "&orgId=" + orgId.toString() + "&hoOrgId=" +
                            hoOrgId.toString() + "&slocId=" + slocId + "&fileType=" +
                            getFileTypeBind().getValue().toString();
                        this.reportURL = url;
                        System.out.println("Final callback url is :" + reportURL);
            } else {
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.900']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext.getCurrentInstance().addMessage(getFileTypeBind().getClientId(), message);
            }

        } else {
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.902']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(getSlsInvoiceDocIdBind().getClientId(), message);
        }
    }
    
    /**
     * Generates the URL for  calling 5.Sales Intimation Report
     */
    public void generateUrlForPriceMasterReport() {
        Integer categoryId=null;
        Integer entityId=null;
        System.out.println("categoryId" + categoryId);
        if ((getCatgIdBinding().getValue() != null && getCatgIdBinding().getValue()=="")||((this.getEntityIdBinding().getValue()!=null)&&(this.getEntityIdBinding().getValue()!=""))) {
            if (this.getFileTypeBind().getValue() != null) {
                categoryId = Integer.parseInt(catgIdBinding.getValue().toString());
                entityId = Integer.parseInt(entityIdBinding.getValue().toString());
                System.out.println("categoryId : " + categoryId);
                String url =
                            "/salesreportserv?" + 
                            "reportId=5" + 
                            "&catgId=" +categoryId+ 
                            //"&FromDate" + fromDt + 
                            //"&ToDate" + toDt+
                            "&eoId=" +entityId+
                            "&fileType=" + this.getFileTypeBind().getValue().toString() +
                            "&cldId=" + cldId.toString() + "&orgId=" + orgId.toString() + "&hoOrgId=" +
                            hoOrgId.toString() + "&slocId=" + slocId + "&fileType=" +
                            getFileTypeBind().getValue().toString();
                        this.reportURL = url;
                        System.out.println("Final callback url is :" + reportURL);
            } else {
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.900']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext.getCurrentInstance().addMessage(getFileTypeBind().getClientId(), message);
            }

        } else {
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.902']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(getSlsInvoiceDocIdBind().getClientId(), message);
        }
    }


    /**
     * Generates the URL for  calling 6.Pick List Report
     */
    public void generateUrlForPickListReport() {
        String pickId=null;
        System.out.println("pickId : " + pickId);
        if (this.pickIdBinding.getValue() != null) {
            System.out.println("pickId : " + pickId);
            if (this.getFileTypeBind().getValue() != null) {
                pickId =pickIdBinding.getValue().toString();
                System.out.println("pickId aftr toString : "+pickId);
                String url =
                            "/salesreportserv?" + 
                            "reportId=6" + 
                            "&pickId=" + pickId + 
                            //"&FromDate" + fromDt + 
                            //"&ToDate" + toDt+
                            "&fileType=" + this.getFileTypeBind().getValue().toString() +
                            "&cldId=" + cldId.toString() + "&orgId=" + orgId.toString() + "&hoOrgId=" +
                            hoOrgId.toString() + "&slocId=" + slocId + "&fileType=" +
                            getFileTypeBind().getValue().toString();
                        this.reportURL = url;
                        System.out.println("Final callback url is :" + reportURL);
            } else {
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.900']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext.getCurrentInstance().addMessage(getFileTypeBind().getClientId(), message);
            }

        } else {
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.907']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(getSlsInvoiceDocIdBind().getClientId(), message);
        }
    }

   
    /**
     * Generates the URL for  calling 7.Cancel Order Report
     */
    public void generateUrlForCanceledOrderReport() {
        String cancelOrder=null;
        System.out.println("cancelOrder" + cancelOrder);
        if (cancelOrderIdBinding.getValue()!=null) {
            System.out.println("cancelOrder" + cancelOrder);
            if (this.getFileTypeBind().getValue() != null) {
                cancelOrder=cancelOrderIdBinding.getValue().toString();
                System.out.println("cancelOrder aftr toString: " + cancelOrder);
                String url =
                            "/salesreportserv?" + 
                            "reportId=7" + 
                            "&docId=" + cancelOrder + 
                            //"&FromDate" + fromDt + 
                            //"&ToDate" + toDt+
                            //"&eoId=" +entityId+
                            "&fileType=" + this.getFileTypeBind().getValue().toString() +
                            "&cldId=" + cldId.toString() + "&orgId=" + orgId.toString() + "&hoOrgId=" +
                            hoOrgId.toString() + "&slocId=" + slocId + "&fileType=" +
                            getFileTypeBind().getValue().toString();
                        this.reportURL = url;
                        System.out.println("Final callback url is :" + reportURL);
            } else {
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.900']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext.getCurrentInstance().addMessage(getFileTypeBind().getClientId(), message);
            }

        } else {
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.902']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(getSlsInvoiceDocIdBind().getClientId(), message);
        }
    }
    
    /**
     * Generates the URL for  calling 8.Invoice Register
     */
    public void generateUrlForInvoiceRegisterReport() {
        String invoiceId=null;
        System.out.println("invoiceId : " + invoiceId);
        if (this.invoiceIdBinding.getValue() != null) {
            System.out.println("invoiceId : " + invoiceId);
            if (this.getFileTypeBind().getValue() != null) {
                invoiceId =invoiceIdBinding.getValue().toString();
                System.out.println("invoiceId Aftr toString : "+invoiceId);
                String url =
                            "/salesreportserv?" + 
                            "reportId=6" + 
                            "&invId=" +invoiceId+ 
                            //"&FromDate" + fromDt + 
                            //"&ToDate" + toDt+
                            "&fileType=" + this.getFileTypeBind().getValue().toString() +
                            "&cldId=" + cldId.toString() + "&orgId=" + orgId.toString() + "&slocId=" + slocId + "&fileType=" +
                            getFileTypeBind().getValue().toString();
                        this.reportURL = url;
                        System.out.println("Final callback url is :" + reportURL);
            } else {
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.900']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext.getCurrentInstance().addMessage(getFileTypeBind().getClientId(), message);
            }

        } else {
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.907']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(getSlsInvoiceDocIdBind().getClientId(), message);
        }
    }



    /**
     *  method to get Bindings
     */
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }


    /**
     * Getters and Setters for defined params
     * 
     */

    public void setSlsInvoiceDocIdBind(RichInputListOfValues slsInvoiceDocIdBind) {
        this.slsInvoiceDocIdBind = slsInvoiceDocIdBind;
    }

    public RichInputListOfValues getSlsInvoiceDocIdBind() {
        return slsInvoiceDocIdBind;
    }

    public void setFileTypeBind(RichSelectOneRadio fileTypeBind) {
        this.fileTypeBind = fileTypeBind;
    }

    public RichSelectOneRadio getFileTypeBind() {
        return fileTypeBind;
    }

    public void setReportURL(String reportURL) {
        this.reportURL = reportURL;
    }

    public String getReportURL() {
        return reportURL;
    }

    /**
     * Value Change Listner to generate URL
     * @param valueChangeEvent
     */
    public void salesInvoiceNoVCL(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getNewValue()!=null){
            this.generateUrlForSalesInvoiceReport();
        }
        
    }

    /**
     * Value Change Listner to generate URL
     * @param valueChangeEvent
     */
    public void fileTypeVCL(ValueChangeEvent valueChangeEvent) {
        
        if (valueChangeEvent.getNewValue()!=null) {
            this.generateUrlForSalesInvoiceReport();

        }
    }

    public void setOppIdBinding(RichInputListOfValues oppIdBinding) {
        this.oppIdBinding = oppIdBinding;
    }

    public RichInputListOfValues getOppIdBinding() {
        return oppIdBinding;
    }

    public void setFromDateBinding(RichInputDate fromDateBinding) {
        this.fromDateBinding = fromDateBinding;
    }

    public RichInputDate getFromDateBinding() {
        return fromDateBinding;
    }

    public void setToDateBinding(RichInputDate toDateBinding) {
        this.toDateBinding = toDateBinding;
    }

    public RichInputDate getToDateBinding() {
        return toDateBinding;
    }

    public void opportunityValueChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if (valueChangeEvent.getNewValue() != null) {
            generateUrlForSalesOpportunityReport();
        }
    }

        public void setSalesOrderStatusBinding(RichInputListOfValues salesOrderStatusBinding) {
        this.salesOrderStatusBinding = salesOrderStatusBinding;
    }

    public RichInputListOfValues getSalesOrderStatusBinding() {
        return salesOrderStatusBinding;
    }

    public void soStatusValueChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...      
        if (valueChangeEvent.getNewValue() != null) {
            System.out.println("vlc" +valueChangeEvent.getNewValue());
            generateUrlForSalesOrderStatusReport();
        }
    }

    public void intimationSlipValueChngListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if (valueChangeEvent.getNewValue()!=null) {
            generateUrlForSalesIntmSlipReport();
        }
    }

    public void setIntmnNoBinding(RichInputListOfValues intmnNoBinding) {
        this.intmnNoBinding = intmnNoBinding;
    }

    public RichInputListOfValues getIntmnNoBinding() {
        return intmnNoBinding;
    }

    public void setCatgIdBinding(RichSelectOneChoice catgIdBinding) {
        this.catgIdBinding = catgIdBinding;
    }

    public RichSelectOneChoice getCatgIdBinding() {
        return catgIdBinding;
    }

    public void catgIdValueChngListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if(valueChangeEvent.getNewValue()!=null){//||((this.getEntityIdBinding().getValue()!=null)&&(this.getEntityIdBinding().getValue()==""))){
            generateUrlForPriceMasterReport();
        }
    }

    public void setEntityIdBinding(RichSelectOneChoice entityIdBinding) {
        this.entityIdBinding = entityIdBinding;
    }

    public RichSelectOneChoice getEntityIdBinding() {
        return entityIdBinding;
    }

    public void entityIdValueChngListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if(valueChangeEvent.getNewValue()!=null){//||((this.getCatgIdBinding().getValue()!=null)&&(this.getCatgIdBinding().getValue()==""))){
            generateUrlForPriceMasterReport();
        }
    }

    public void selectFormatValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if(object!=null){
            System.out.println(object);
        }else{
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.900']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(getFileTypeBind().getClientId(), message);
        }
    }

    public void setPickIdBinding(RichSelectOneChoice pickIdBinding) {
        this.pickIdBinding = pickIdBinding;
    }

    public RichSelectOneChoice getPickIdBinding() {
        return pickIdBinding;
    }

    public void setCancelOrderIdBinding(RichSelectOneChoice cancelOrderIdBinding) {
        this.cancelOrderIdBinding = cancelOrderIdBinding;
    }

    public RichSelectOneChoice getCancelOrderIdBinding() {
        return cancelOrderIdBinding;
    }

    public void setInvoiceIdBinding(RichSelectOneChoice invoiceIdBinding) {
        this.invoiceIdBinding = invoiceIdBinding;
    }

    public RichSelectOneChoice getInvoiceIdBinding() {
        return invoiceIdBinding;
    }

    public void pickNoValueChngLIstener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println(""+valueChangeEvent.getNewValue());
        if(valueChangeEvent.getNewValue()!=null){
            System.out.println(""+valueChangeEvent.getNewValue());
            generateUrlForPickListReport();
        }
        
    }

    public void cancelOrderValueChngListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println(""+valueChangeEvent.getNewValue());
        if(valueChangeEvent.getNewValue()!=null){
            System.out.println(""+valueChangeEvent.getNewValue());
            generateUrlForCanceledOrderReport();
        }
    }

    public void invoiceNoValueChngListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println(""+valueChangeEvent.getNewValue());
        if(valueChangeEvent.getNewValue()!=null){
            System.out.println(""+valueChangeEvent.getNewValue());
            generateUrlForInvoiceRegisterReport();
        }
    }
}
