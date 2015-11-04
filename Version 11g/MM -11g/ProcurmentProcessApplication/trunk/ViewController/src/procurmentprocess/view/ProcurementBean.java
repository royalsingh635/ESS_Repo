package procurmentprocess.view;

import java.sql.SQLException;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;

import oracle.adf.view.rich.component.rich.layout.RichPanelLabelAndMessage;

import oracle.adf.view.rich.component.rich.output.RichOutputText;

import procurmentprocess.model.module.ProcurementAMImpl;

import procurmentprocess.model.view.DrftPOVOImpl;
import procurmentprocess.model.view.DualVOImpl;

import procurmentprocess.model.view.QuotationSrchVOImpl;
import procurmentprocess.model.view.RFQSrchVOImpl;


import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.component.rich.nav.RichGoLink;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.client.Configuration;
import oracle.jbo.domain.Date;

import org.apache.myfaces.trinidad.component.UIXSwitcher;

public class ProcurementBean {
    
    private RichInputText eoId;
    private RichSelectOneChoice poStatus;
    private RichTable tableBind;
    private RichInputDate fromDate;
    private RichInputDate toDate;
    private oracle.jbo.domain.Date time = (oracle.jbo.domain.Date)oracle.jbo.domain.Date.getCurrentDate();
    private RichInputListOfValues supplierName;
    private RichInputText fileType;
    private RichInputText procurementType;
    private RichGoLink poLink;
    private Boolean poFlag;
    private RichPanelFormLayout poform;
    private RichInputText rfqId;
    private RichSelectOneChoice rfqStatus;
    private RichInputDate rfqFromDate;
    private RichInputDate rfqToDate;
    private RichInputText rfqFileType;
    private RichGoLink rfqLink;
    private RichGoLink allrfqSupplier;
    private Boolean allSupplierFlag;
    private Boolean selSupplierFlag;
    private RichPanelFormLayout rfqForm;
    
    private RichSelectOneChoice quotStatus;
    private RichInputListOfValues quotSupName;
    private RichInputText quotSupId;
    private RichInputDate quotFromDate;
    private RichInputDate quotToDate;
    private RichInputText quotFileType;
    private RichGoLink quotLink;
    private RichPanelFormLayout quotForm;
    private Boolean quotFlag;
    private UIXSwitcher switcherBind;
    private String facetValue;
    private RichSelectBooleanCheckbox quotationAnalysisChkBinding;
    private String quotationAnalysisRpt="false";
    private RichGoLink quotAnaRptLinkBinding;
    private RichSelectOneChoice fileTypeBinding;
    private RichSelectBooleanCheckbox cashPurchaseOrderChekBinding;
    private String cashPurchaseOrderRpt="false";
    private RichGoLink cashpurchaseOrderLinkBinding;
    private RichSelectBooleanCheckbox suggestOrderRptCheckBinding;
    private RichSelectBooleanCheckbox suggestorderSupplierwiseChekBinding;
    private String suggestedOrder="false";
    private String suggestedOrderSupplierwise="false";
    private RichGoLink suggestedOrderLinkBinding;
    private RichGoLink suggestedOrderSupwiseLinkBinding;
    private RichSelectBooleanCheckbox poStatusWiseChekBinding;
    private RichSelectBooleanCheckbox poSummaryChekBinding;
    private String PoStatusWise="flase";
    private String PoSummary="flase";
    private String PoSingle="false";
    private String DeliverySchedule="false";
    private String OpenOrder="false";
    private String OpenOrderItemWise="false";
    private RichSelectBooleanCheckbox purchaseOrderChkBinding;
    private RichGoLink poStatusLinkBinding;
    private RichGoLink poSummarylinkBinding;
    private RichSelectBooleanCheckbox cpoSummaryCheckBinding;
    private String cpoSummary="false";
    
    private RichGoLink cpoSummarylinkBinding;
    private RichSelectBooleanCheckbox deliveryScheduleChkBinding;
    private RichGoLink deliveryScheduleLinkBinding;
    private RichSelectBooleanCheckbox listOfQuotCheckBinding;
    private RichGoLink listOfQuotLinkBinding;
    private RichSelectBooleanCheckbox quotAnaConsolidateChkBinding;
    private RichGoLink quotAnaConsolidateLinkBinding;
    private RichSelectBooleanCheckbox rateContractPOCheckBinding;
    private RichSelectBooleanCheckbox rateContractPOItemwiseChkBinding;
    private RichGoLink rateContractPolnkBinding;
    private RichGoLink rateContractPoItemWiseLnkBinding;
    private RichSelectBooleanCheckbox openOrderChkBinding;
    private RichSelectBooleanCheckbox openOrderItmWiseChkBinding;
    private RichGoLink openOrderItemWiselnkBinding;
    private RichGoLink openOrderLnkBinding;
    private RichPanelLabelAndMessage poTypeBinding;
    private RichPanelLabelAndMessage poBasisBinding;
    private RichOutputText potype;
    private RichOutputText poBasis;

    public ProcurementBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String SearchAction() {
        // Add event code here...
        ProcurementAMImpl am = (ProcurementAMImpl)resolvElDC("ProcurementAMDataControl");
        DrftPOVOImpl drftPoVo = (DrftPOVOImpl)am.getDrftPO1();

        drftPoVo.setNamedWhereClauseParam("bindPOId",am.getDual1().getCurrentRow().getAttribute("PoDocId"));
        drftPoVo.setNamedWhereClauseParam("bindPOMode", poStatus.getValue());
        drftPoVo.setNamedWhereClauseParam("bindEoId", eoId.getValue());
        drftPoVo.setNamedWhereClauseParam("bindFromDate", am.getDual1().getCurrentRow().getAttribute("FrmDate"));
        drftPoVo.setNamedWhereClauseParam("bindToDate", am.getDual1().getCurrentRow().getAttribute("ToDate"));


        /* drftPoVo.setWhereClause(null);
        drftPoVo.executeQuery();
        StringBuilder sb = new StringBuilder();
        boolean flag=true;
        if(poNo.getValue()!=null && poNo.getValue().toString().length()>0)
        {  if(flag)
           {sb.append(" upper(PO_ID) like upper(q'["+poNo.getValue()+"%]')"); flag=false;}
           else
           {sb.append(" AND upper(PO_ID) like upper(q'["+poNo.getValue()+"%]')");}
        }

        if(poStatus.getValue()!=null && poStatus.getValue().toString().length()>0)
        {  if(flag)
           {sb.append(" PO_MODE = "+poStatus.getValue()); flag=false;}
           else
           {sb.append(" AND PO_MODE = "+poStatus.getValue());}
         }

        if(eoId.getValue()!=null && eoId.getValue().toString().length()>0)
        {  if(flag)
           {sb.append(" EO_ID = "+eoId.getValue()); flag=false;}
           else
           {sb.append(" AND EO_ID = "+eoId.getValue());}
        }

        java.sql.Date fromSqlDate=null;
        java.sql.Date toSqlDate=null;
        Date frm=null;
        Date to=null;

            try{
               if(fromDate.getValue()!=null)
              {
                if(fromDate.getValue().toString().length()>0)
               {
                   java.util.Date fromDateUtil = (java.util.Date)fromDate.getValue();
                   fromSqlDate = new java.sql.Date(fromDateUtil.getTime());
                   frm = new Date(fromSqlDate);
               }
              }

                    if(toDate.getValue()!=null)
                    {
                if(toDate.getValue().toString().length()>0)
              {
                java.util.Date toDateUtil = (java.util.Date)toDate.getValue();
                toSqlDate = new java.sql.Date(toDateUtil.getTime());
                to = new Date(toSqlDate);
              }
                }}
               catch(Exception ex){}


            if(fromDate.getValue()==null && toDate.getValue()==null)
            {
                 if(flag)
                   {sb.append(" (PO_DT BETWEEN NVL(to_date("+frm+",'yyyy/mm/dd'),PO_DT) AND NVL(to_date("+to+",'yyyy/mm/dd'),PO_DT))"); flag=false;}
                else
                   {sb.append(" AND (PO_DT BETWEEN NVL(to_date("+frm+",'yyyy/mm/dd'),PO_DT) AND NVL(to_date("+to+",'yyyy/mm/dd'),PO_DT))");}
            }

            else if(fromDate.getValue()!=null && toDate.getValue()==null)
            {
                 if(flag)
                   {sb.append(" (PO_DT BETWEEN NVL(to_date('"+frm+"','yyyy/mm/dd'),PO_DT) AND NVL(to_date("+to+",'yyyy/mm/dd'),PO_DT))"); flag=false;}
                else
                   {sb.append(" AND (PO_DT BETWEEN NVL(to_date('"+frm+"','yyyy/mm/dd'),PO_DT) AND NVL(to_date("+to+",'yyyy/mm/dd'),PO_DT))");}
            }

           else if(fromDate.getValue()==null && toDate.getValue()!=null)
            {
                 if(flag)
                   {sb.append(" (PO_DT BETWEEN NVL(to_date("+frm+",'yyyy/mm/dd'),PO_DT) AND NVL(to_date('"+to+"','yyyy/mm/dd'),PO_DT))"); flag=false;}
                else
                   {sb.append(" AND (PO_DT BETWEEN NVL(to_date("+frm+",'yyyy/mm/dd'),PO_DT) AND NVL(to_date('"+to+"','yyyy/mm/dd'),PO_DT))");}
            }

            else{
                  if(flag)
                    {sb.append(" (PO_DT BETWEEN NVL(to_date('"+frm+"','yyyy/mm/dd'),PO_DT) AND NVL(to_date('"+to+"','yyyy/mm/dd'),PO_DT))"); flag=false;}
                  else
                    {sb.append(" AND (PO_DT BETWEEN NVL(to_date('"+frm+"','yyyy/mm/dd'),PO_DT) AND NVL(to_date('"+to+"','yyyy/mm/dd'),PO_DT))");}
                }


        drftPoVo.setWhereClause(sb.toString());
        drftPoVo.executeQuery();*/
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("ExecuteWithParams");
        operationBinding.execute();
        drftPoVo.executeQuery();
        return null;
    }


    

    public void setEoId(RichInputText eoId) {
        this.eoId = eoId;
    }

    public RichInputText getEoId() {
        return eoId;
    }

    public void setPoStatus(RichSelectOneChoice poStatus) {
        this.poStatus = poStatus;
    }

    public RichSelectOneChoice getPoStatus() {
        return poStatus;
    }

    public void setTableBind(RichTable tableBind) {
        this.tableBind = tableBind;
    }

    public RichTable getTableBind() {
        return tableBind;
    }

    public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }

    

    public void setTime(oracle.jbo.domain.Date time) {
        this.time = time;
    }

    public oracle.jbo.domain.Date getTime() {
        return time;
    }

    public void resetAction(ActionEvent actionEvent) {
        // Add event code here...
        ProcurementAMImpl am = (ProcurementAMImpl)resolvElDC("ProcurementAMDataControl");
        DrftPOVOImpl drftPoVo = (DrftPOVOImpl)am.getDrftPO1();
        drftPoVo.setNamedWhereClauseParam("bindEoId", Integer.valueOf(-1));
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("ExecuteWithParams");
        operationBinding.execute();
        drftPoVo.executeQuery();
        am.getDual1().getCurrentRow().setAttribute("PoDocId",null);
        am.getDual1().getCurrentRow().setAttribute("ToDate",null);
        am.getDual1().getCurrentRow().setAttribute("FrmDate",null);
        poStatus.setValue("");
        supplierName.setValue("");
        am.getDual1().executeQuery();
    }

    public void setSupplierName(RichInputListOfValues supplierName) {
        this.supplierName = supplierName;
    }

    public RichInputListOfValues getSupplierName() {
        return supplierName;
    }

    public void fileTypeListenerAction(ValueChangeEvent valueChangeEvent) {
        // Add event code here...

        String file_Type = (String)valueChangeEvent.getNewValue();
        fileType.setValue(file_Type);
       // poLink.setVisible(false);
        rfqFileType.setValue(file_Type);
        quotFileType.setValue(file_Type);
        AdfFacesContext.getCurrentInstance().addPartialTarget(poLink);
        AdfFacesContext.getCurrentInstance().addPartialTarget(fileType);
        AdfFacesContext.getCurrentInstance().addPartialTarget(rfqFileType);
        AdfFacesContext.getCurrentInstance().addPartialTarget(quotFileType);
        quotLink.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(quotLink);

    }

    public void setFileType(RichInputText fileType) {
        this.fileType = fileType;
    }

    public RichInputText getFileType() {
        return fileType;
    }

    public void setProcurementType(RichInputText procurementType) {
        this.procurementType = procurementType;
    }

    public RichInputText getProcurementType() {
        return procurementType;
    }

    public void ProcurementChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        facetValue = (String)valueChangeEvent.getNewValue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(switcherBind);

    }

    public void setPoLink(RichGoLink poLink) {
        this.poLink = poLink;
    }

    public RichGoLink getPoLink() {
        return poLink;
    }

    public void checkPurchaseOrderListener(ValueChangeEvent valueChangeEvent) {
       /* poFlag = (Boolean)valueChangeEvent.getNewValue();
        if (poFlag) {
         //   poLink.setVisible(true);
        } else {
          //  poLink.setVisible(false);
        }*/
    }

    public void generateReportAction(ActionEvent actionEvent) {
        // Add event code here...
       /* if (poFlag) {
            poLink.setVisible(true);
        } else {
            poLink.setVisible(false);
        }*/
       // System.out.println("value:"+openOrderChkBinding.getValue());
        if(openOrderChkBinding.getValue()!=null && openOrderChkBinding.getValue()!=""){
        if(openOrderChkBinding.getValue().equals(true)) {
            //if(((Integer)potype.getValue()==491)||((Integer)potype.getValue()==171&&(Integer)poBasis.getValue()==490)){
                   this.OpenOrder="true";
            }
            else {
                this.OpenOrder="false";
            }
        }
        else {
            this.OpenOrder="false";
        }
        //System.out.println("value itemwise"+openOrderItmWiseChkBinding.getValue());
        if(openOrderItmWiseChkBinding.getValue()!=null && openOrderItmWiseChkBinding.getValue()!=""){
        if(openOrderItmWiseChkBinding.getValue().equals(true)) {
             //if(((Integer)potype.getValue()==491)||((Integer)potype.getValue()==171&&(Integer)poBasis.getValue()==490)){
                      this.OpenOrderItemWise="true";
               }
            
        else {
            this.OpenOrderItemWise="false";
            }
        }
        else {
            this.OpenOrderItemWise="false";
            }
        if(rateContractPOCheckBinding.getValue()!=null && rateContractPOCheckBinding.getValue()!=""){
        if(rateContractPOCheckBinding.getValue().equals(true)) {
          //if(((Integer)potype.getValue()==173)||((Integer)potype.getValue()==170&&(Integer)potype.getValue()==172&&(Integer)poBasis.getValue()==175)){
       this.rateContractPolnkBinding.setVisible(true);
        }
        else{
        this.rateContractPolnkBinding.setVisible(false);
        }
        }
        else{
        this.rateContractPolnkBinding.setVisible(false);
        }
        if(rateContractPOItemwiseChkBinding.getValue()!=null && rateContractPOItemwiseChkBinding.getValue()!=""){
        if(rateContractPOItemwiseChkBinding.getValue().equals(true)) {
            //if(((Integer)potype.getValue()==173)||((Integer)potype.getValue()==170&&(Integer)potype.getValue()==172&&(Integer)poBasis.getValue()==175)){
            this.rateContractPoItemWiseLnkBinding.setVisible(true);
            }
       else
        {
        this.rateContractPoItemWiseLnkBinding.setVisible(false);
        }}       
        else
        {
        this.rateContractPoItemWiseLnkBinding.setVisible(false);
        }
       
        
        if(purchaseOrderChkBinding.getValue().equals(true)){
            PoSingle="true";
        }
        else{
            PoSingle="false";
        }
        if(poStatusWiseChekBinding.getValue().equals(true)){
            PoStatusWise="true";
        }
        else{
            PoStatusWise="false";
        }
        if(poSummaryChekBinding.getValue().equals(true)){
            PoSummary="true";
        }
        else{
            PoSummary="false";
        }
        
        if(deliveryScheduleChkBinding.getValue().equals(true)){
            DeliverySchedule="true";
        }
        else{
            DeliverySchedule="false";
        }
    }


    public void setPoFlag(Boolean poFlag) {
        this.poFlag = poFlag;
    }

    public Boolean getPoFlag() {
        return poFlag;
    }

    public void setPoform(RichPanelFormLayout poform) {
        this.poform = poform;
    }

    public RichPanelFormLayout getPoform() {
        return poform;
    }

    public void searchRFQAction(ActionEvent actionEvent) {
        // Add event code here...

        ProcurementAMImpl am = (ProcurementAMImpl)resolvElDC("ProcurementAMDataControl");
        RFQSrchVOImpl rfqSrchVo = (RFQSrchVOImpl)am.getRFQSrch1();

        rfqSrchVo.setNamedWhereClauseParam("bindRFQId", rfqId.getValue());
        rfqSrchVo.setNamedWhereClauseParam("bindRFQStatus", rfqStatus.getValue());
        rfqSrchVo.setNamedWhereClauseParam("bindFromDate", rfqFromDate.getValue());
        rfqSrchVo.setNamedWhereClauseParam("bindToDate", rfqToDate.getValue());
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("ExecuteWithParams1");
        operationBinding.execute();
        rfqSrchVo.executeQuery();


        /*     rfqSrchVo.setWhereClause(null);
        rfqSrchVo.executeQuery();
        StringBuilder sb = new StringBuilder();
        boolean flag=true;

        if(rfqId.getValue()!=null && rfqId.getValue().toString().length()>0)
        {  if(flag)
           {sb.append(" upper(RFQ_ID) like upper(q'["+rfqId.getValue()+"%]')"); flag=false;}
           else
           {sb.append(" AND upper(RFQ_ID) like upper(q'["+rfqId.getValue()+"%]')");}
        }

        if(rfqStatus.getValue()!=null && rfqStatus.getValue().toString().length()>0)
        {  if(flag)
           {sb.append(" RFQ_STATUS = "+rfqStatus.getValue()); flag=false;}
           else
           {sb.append(" AND RFQ_STATUS = "+rfqStatus.getValue());}
         }


        java.sql.Date fromSqlDate=null;
        java.sql.Date toSqlDate=null;
        Date frm=null;
        Date to=null;

            try{
               if(rfqFromDate.getValue()!=null)
              {
                if(rfqFromDate.getValue().toString().length()>0)
               {
                   java.util.Date fromDateUtil = (java.util.Date)rfqFromDate.getValue();
                   fromSqlDate = new java.sql.Date(fromDateUtil.getTime());
                   frm = new Date(fromSqlDate);
               }
              }

                    if(rfqToDate.getValue()!=null)
                    {
                if(rfqToDate.getValue().toString().length()>0)
              {
                java.util.Date toDateUtil = (java.util.Date)rfqToDate.getValue();
                toSqlDate = new java.sql.Date(toDateUtil.getTime());
                to = new Date(toSqlDate);
              }
                }}
               catch(Exception ex){}


            if(rfqFromDate.getValue()==null && rfqToDate.getValue()==null)
            {
                 if(flag)
                   {sb.append(" (DT BETWEEN NVL(to_date("+frm+",'yyyy/mm/dd'),DT) AND NVL(to_date("+to+",'yyyy/mm/dd'),DT))"); flag=false;}
                else
                   {sb.append(" AND (DT BETWEEN NVL(to_date("+frm+",'yyyy/mm/dd'),DT) AND NVL(to_date("+to+",'yyyy/mm/dd'),DT))");}
            }

            else if(rfqFromDate.getValue()!=null && rfqToDate.getValue()==null)
            {
                 if(flag)
                   {sb.append(" (DT BETWEEN NVL(to_date('"+frm+"','yyyy/mm/dd'),DT) AND NVL(to_date("+to+",'yyyy/mm/dd'),DT))"); flag=false;}
                else
                   {sb.append(" AND (DT BETWEEN NVL(to_date('"+frm+"','yyyy/mm/dd'),DT) AND NVL(to_date("+to+",'yyyy/mm/dd'),DT))");}
            }

           else if(rfqFromDate.getValue()==null && rfqToDate.getValue()!=null)
            {
                 if(flag)
                   {sb.append(" (DT BETWEEN NVL(to_date("+frm+",'yyyy/mm/dd'),DT) AND NVL(to_date('"+to+"','yyyy/mm/dd'),DT))"); flag=false;}
                else
                   {sb.append(" AND (DT BETWEEN NVL(to_date("+frm+",'yyyy/mm/dd'),DT) AND NVL(to_date('"+to+"','yyyy/mm/dd'),DT))");}
            }

            else{
                  if(flag)
                    {sb.append(" (DT BETWEEN NVL(to_date('"+frm+"','yyyy/mm/dd'),DT) AND NVL(to_date('"+to+"','yyyy/mm/dd'),DT))"); flag=false;}
                  else
                    {sb.append(" AND (DT BETWEEN NVL(to_date('"+frm+"','yyyy/mm/dd'),DT) AND NVL(to_date('"+to+"','yyyy/mm/dd'),DT))");}
                }

        rfqSrchVo.setWhereClause(sb.toString());
        rfqSrchVo.executeQuery();*/


    }

    public void setRfqId(RichInputText rfqId) {
        this.rfqId = rfqId;
    }

    public RichInputText getRfqId() {
        return rfqId;
    }

    public void setRfqStatus(RichSelectOneChoice rfqStatus) {
        this.rfqStatus = rfqStatus;
    }

    public RichSelectOneChoice getRfqStatus() {
        return rfqStatus;
    }

    public void setRfqFromDate(RichInputDate rfqFromDate) {
        this.rfqFromDate = rfqFromDate;
    }

    public RichInputDate getRfqFromDate() {
        return rfqFromDate;
    }

    public void setRfqToDate(RichInputDate rfqToDate) {
        this.rfqToDate = rfqToDate;
    }

    public RichInputDate getRfqToDate() {
        return rfqToDate;
    }

    public void setRfqFileType(RichInputText rfqFileType) {
        this.rfqFileType = rfqFileType;
    }

    public RichInputText getRfqFileType() {
        return rfqFileType;
    }

    public void setRfqLink(RichGoLink rfqLink) {
        this.rfqLink = rfqLink;
    }

    public RichGoLink getRfqLink() {
        return rfqLink;
    }

    public void setAllrfqSupplier(RichGoLink allrfqSupplier) {
        this.allrfqSupplier = allrfqSupplier;
    }

    public RichGoLink getAllrfqSupplier() {
        return allrfqSupplier;
    }

    public void selectedSupplierListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        selSupplierFlag = (Boolean)valueChangeEvent.getNewValue();
        if (!selSupplierFlag) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(rfqLink);
            rfqLink.setVisible(false);
        }

    }

    public void allSupplierListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        allSupplierFlag = (Boolean)valueChangeEvent.getNewValue();
        if (!allSupplierFlag) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(allrfqSupplier);
            allrfqSupplier.setVisible(false);
        }
    }


    public void setAllSupplierFlag(Boolean allSupplierFlag) {
        this.allSupplierFlag = allSupplierFlag;
    }

    public Boolean getAllSupplierFlag() {
        return allSupplierFlag;
    }

    public void setSelSupplierFlag(Boolean selSupplierFlag) {
        this.selSupplierFlag = selSupplierFlag;
    }

    public Boolean getSelSupplierFlag() {
        return selSupplierFlag;
    }

    public void rfqAction(ActionEvent actionEvent) {
        // Add event code here...


        if (selSupplierFlag) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(rfqLink);
            rfqLink.setVisible(true);
        }
        if (allSupplierFlag) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(allrfqSupplier);
            allrfqSupplier.setVisible(true);
        }

    }

    public void setRfqForm(RichPanelFormLayout rfqForm) {
        this.rfqForm = rfqForm;
    }

    public RichPanelFormLayout getRfqForm() {
        return rfqForm;
    }

    public void resetRFQAction(ActionEvent actionEvent) {
        // Add event code here...
        ProcurementAMImpl am = (ProcurementAMImpl)resolvElDC("ProcurementAMDataControl");
        RFQSrchVOImpl srchVo = (RFQSrchVOImpl)am.getRFQSrch1();
        srchVo.setNamedWhereClauseParam("bindRFQStatus", Integer.valueOf(-1));
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("ExecuteWithParams1");
        operationBinding.execute();
        am.getRFQtrns1().getCurrentRow().setAttribute("Rfqno", null);
        am.getRFQtrns1().executeQuery();
        rfqId.setValue("");
        rfqFromDate.setValue("");
        rfqFromDate.setValue("");
        rfqStatus.setValue("");
        rfqToDate.setValue("");
    }

   

    

    public void setQuotStatus(RichSelectOneChoice quotStatus) {
        this.quotStatus = quotStatus;
    }

    public RichSelectOneChoice getQuotStatus() {
        return quotStatus;
    }

    public void setQuotSupName(RichInputListOfValues quotSupName) {
        this.quotSupName = quotSupName;
    }

    public RichInputListOfValues getQuotSupName() {
        return quotSupName;
    }

    public void setQuotSupId(RichInputText quotSupId) {
        this.quotSupId = quotSupId;
    }

    public RichInputText getQuotSupId() {
        return quotSupId;
    }

    public void setQuotFromDate(RichInputDate quotFromDate) {
        this.quotFromDate = quotFromDate;
    }

    public RichInputDate getQuotFromDate() {
        return quotFromDate;
    }

    public void setQuotToDate(RichInputDate quotToDate) {
        this.quotToDate = quotToDate;
    }

    public RichInputDate getQuotToDate() {
        return quotToDate;
    }

    public void searchQuotionAction(ActionEvent actionEvent) {
        // Add event code here...
        ProcurementAMImpl am = (ProcurementAMImpl)resolvElDC("ProcurementAMDataControl");
        QuotationSrchVOImpl srchVo = (QuotationSrchVOImpl)am.getQuotationSrch1();
        Row currentRow = am.getQuotTrns1().getCurrentRow();
        String docid=null;
        if(currentRow.getAttribute("QuotDocId")!=null){
             docid=currentRow.getAttribute("QuotDocId").toString();
        }
        else{
            docid=null;
        }

        srchVo.setNamedWhereClauseParam("bindQuotId", docid);
        srchVo.setNamedWhereClauseParam("bindQuotStatus", quotStatus.getValue());
        srchVo.setNamedWhereClauseParam("bindQuotEOId", quotSupId.getValue());
        srchVo.setNamedWhereClauseParam("bindQtFrmDate", quotFromDate.getValue());
        srchVo.setNamedWhereClauseParam("bindQtToDate", quotToDate.getValue());
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("ExecuteWithParams2");
        operationBinding.execute();
        srchVo.executeQuery();

        /*    srchVo.setWhereClause(null);
        srchVo.executeQuery();
        StringBuilder sb = new StringBuilder();
        boolean flag=true;

        if(quotId.getValue()!=null && quotId.getValue().toString().length()>0)
        {  if(flag)
           {sb.append(" upper(QUOT_ID) like upper(q'["+quotId.getValue()+"%]')"); flag=false;}
           else
           {sb.append(" AND upper(QUOT_ID) like upper(q'["+quotId.getValue()+"%]')");}
        }

        if(quotStatus.getValue()!=null && quotStatus.getValue().toString().length()>0)
        {  if(flag)
           {sb.append(" QUOT_STATUS = "+quotStatus.getValue()); flag=false;}
           else
           {sb.append(" AND QUOT_STATUS = "+quotStatus.getValue());}
         }


        if(quotSupId.getValue()!=null && quotSupId.getValue().toString().length()>0)
        {  if(flag)
           {sb.append(" EO_ID = "+quotSupId.getValue()); flag=false;}
           else
           {sb.append(" AND EO_ID = "+quotSupId.getValue());}
         }


        java.sql.Date fromSqlDate=null;
        java.sql.Date toSqlDate=null;
        Date frm=null;
        Date to=null;

            try{
               if(quotFromDate.getValue()!=null)
              {
                if(quotFromDate.getValue().toString().length()>0)
               {
                   java.util.Date fromDateUtil = (java.util.Date)quotFromDate.getValue();
                   fromSqlDate = new java.sql.Date(fromDateUtil.getTime());
                   frm = new Date(fromSqlDate);
               }
              }

                    if(quotToDate.getValue()!=null)
                    {
                if(quotToDate.getValue().toString().length()>0)
              {
                java.util.Date toDateUtil = (java.util.Date)quotToDate.getValue();
                toSqlDate = new java.sql.Date(toDateUtil.getTime());
                to = new Date(toSqlDate);
              }
                }}
               catch(Exception ex){}


            if(quotFromDate.getValue()==null && quotToDate.getValue()==null)
            {
                 if(flag)
                   {sb.append(" (QUOT_DT BETWEEN NVL(to_date("+frm+",'yyyy/mm/dd'),QUOT_DT) AND NVL(to_date("+to+",'yyyy/mm/dd'),QUOT_DT))"); flag=false;}
                else
                   {sb.append(" AND (QUOT_DT BETWEEN NVL(to_date("+frm+",'yyyy/mm/dd'),QUOT_DT) AND NVL(to_date("+to+",'yyyy/mm/dd'),QUOT_DT))");}
            }

            else if(quotFromDate.getValue()!=null && quotToDate.getValue()==null)
            {
                 if(flag)
                   {sb.append(" (QUOT_DT BETWEEN NVL(to_date('"+frm+"','yyyy/mm/dd'),QUOT_DT) AND NVL(to_date("+to+",'yyyy/mm/dd'),QUOT_DT))"); flag=false;}
                else
                   {sb.append(" AND (QUOT_DT BETWEEN NVL(to_date('"+frm+"','yyyy/mm/QUOT_DT'),DT) AND NVL(to_date("+to+",'yyyy/mm/dd'),QUOT_DT))");}
            }

           else if(quotFromDate.getValue()==null && quotToDate.getValue()!=null)
            {
                 if(flag)
                   {sb.append(" (QUOT_DT BETWEEN NVL(to_date("+frm+",'yyyy/mm/dd'),QUOT_DT) AND NVL(to_date('"+to+"','yyyy/mm/dd'),QUOT_DT))"); flag=false;}
                else
                   {sb.append(" AND (QUOT_DT BETWEEN NVL(to_date("+frm+",'yyyy/mm/dd'),QUOT_DT) AND NVL(to_date('"+to+"','yyyy/mm/dd'),QUOT_DT))");}
            }

            else{
                  if(flag)
                    {sb.append(" (QUOT_DT BETWEEN NVL(to_date('"+frm+"','yyyy/mm/dd'),QUOT_DT) AND NVL(to_date('"+to+"','yyyy/mm/dd'),QUOT_DT))"); flag=false;}
                  else
                    {sb.append(" AND (QUOT_DT BETWEEN NVL(to_date('"+frm+"','yyyy/mm/dd'),QUOT_DT) AND NVL(to_date('"+to+"','yyyy/mm/dd'),QUOT_DT))");}
                }

        System.out.println(sb.toString());
        srchVo.setWhereClause(sb.toString());
        srchVo.executeQuery();
        */

    }

    public void resetQuotAction(ActionEvent actionEvent) {
        // Add event code here...
        ProcurementAMImpl am = (ProcurementAMImpl)resolvElDC("ProcurementAMDataControl");
        QuotationSrchVOImpl srchVo = (QuotationSrchVOImpl)am.getQuotationSrch1();
        Row currentRow = am.getQuotTrns1().getCurrentRow();
        srchVo.setNamedWhereClauseParam("bindQuotEOId", Integer.valueOf(-1));
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("ExecuteWithParams2");
        operationBinding.execute();
        currentRow.setAttribute("QuotDocId",null);
        quotStatus.setValue("");
        quotSupName.setValue("");
        quotFromDate.setValue("");
        quotToDate.setValue("");
        am.getQuotTrns1().executeQuery();
    }

    public void setQuotFileType(RichInputText quotFileType) {
        this.quotFileType = quotFileType;
    }

    public RichInputText getQuotFileType() {
        return quotFileType;
    }

    public void setQuotLink(RichGoLink quotLink) {
        this.quotLink = quotLink;
    }

    public RichGoLink getQuotLink() {
        return quotLink;
    }

    public void setQuotForm(RichPanelFormLayout quotForm) {
        this.quotForm = quotForm;
    }

    public RichPanelFormLayout getQuotForm() {
        return quotForm;
    }


    public void checkQuotationListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        quotFlag = (Boolean)valueChangeEvent.getNewValue();
        if (!quotFlag) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotLink);
            quotLink.setVisible(false);
        }
    }

    public void setQuotFlag(Boolean quotFlag) {
        this.quotFlag = quotFlag;
    }

    public Boolean getQuotFlag() {
        return quotFlag;
    }

    public void QuotationAction(ActionEvent actionEvent) {
        // Add event code here...
        if (quotFlag) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotLink);
            quotLink.setVisible(true);
        }
        if(listOfQuotCheckBinding.getValue().equals(true)){
            listOfQuotLinkBinding.setVisible(true);
        }
        else{
            listOfQuotLinkBinding.setVisible(false);
        }
    }


    public void setSwitcherBind(UIXSwitcher switcherBind) {
        this.switcherBind = switcherBind;
    }

    public UIXSwitcher getSwitcherBind() {
        return switcherBind;
    }


    public void setFacetValue(String facetValue) {
        this.facetValue = facetValue;
    }

    public String getFacetValue() {
        return facetValue;
    }

    public void searchQuotationAnalysisAL(ActionEvent actionEvent) {
        getBindings().getOperationBinding("searchQuotationAnalysis").execute();
    }

    public void resetQuotationAnalysisAL(ActionEvent actionEvent) {
        getBindings().getOperationBinding("resetQuotationAnalysis").execute();
    }

    public void setQuotationAnalysisChkBinding(RichSelectBooleanCheckbox quotationAnalysisChkBinding) {
        this.quotationAnalysisChkBinding = quotationAnalysisChkBinding;
    }

    public RichSelectBooleanCheckbox getQuotationAnalysisChkBinding() {
        return quotationAnalysisChkBinding;
    }

    public void genrateRptQuotationAnalysisAL(ActionEvent actionEvent) {
        if((Boolean)quotationAnalysisChkBinding.getValue().equals(true)){
            this.quotationAnalysisRpt="true";
        }
        else{
            this.quotationAnalysisRpt="false";
        }
        
        if(quotAnaConsolidateChkBinding.getValue().equals(true)){
            this.quotAnaConsolidateLinkBinding.setVisible(true);
        }
        else{
            this.quotAnaConsolidateLinkBinding.setVisible(false);
        }
    }

    public void setQuotationAnalysisRpt(String quotationAnalysisRpt) {
        this.quotationAnalysisRpt = quotationAnalysisRpt;
    }

    public String getQuotationAnalysisRpt() {
        return quotationAnalysisRpt;
    }

    public void setQuotAnaRptLinkBinding(RichGoLink quotAnaRptLinkBinding) {
        this.quotAnaRptLinkBinding = quotAnaRptLinkBinding;
    }

    public RichGoLink getQuotAnaRptLinkBinding() {
        return quotAnaRptLinkBinding;
    }

    public void setFileTypeBinding(RichSelectOneChoice fileTypeBinding) {
        this.fileTypeBinding = fileTypeBinding;
    }

    public RichSelectOneChoice getFileTypeBinding() {
        return fileTypeBinding;
    }

    public void searchCpoAL(ActionEvent actionEvent) {
        getBindings().getOperationBinding("searchCashPurchaseOrder").execute();
    }

    public void resetCpoAL(ActionEvent actionEvent) {
        getBindings().getOperationBinding("resetCashpurchaseOrder").execute();
    }

    public void setCashPurchaseOrderChekBinding(RichSelectBooleanCheckbox cashPurchaseOrderChekBinding) {
        this.cashPurchaseOrderChekBinding = cashPurchaseOrderChekBinding;
    }

    public RichSelectBooleanCheckbox getCashPurchaseOrderChekBinding() {
        return cashPurchaseOrderChekBinding;
    }

    public void genrateRptCashPurOrderAL(ActionEvent actionEvent) {
        if((Boolean)cashPurchaseOrderChekBinding.getValue().equals(true)){
            this.cashPurchaseOrderRpt="true";
        }
        else{
            this.cashPurchaseOrderRpt="false";
        }
        if(cpoSummaryCheckBinding.getValue().equals(true)){
            this.cpoSummary="true";
        }
        else{
            this.cpoSummary="false";
        }
    }

    public void setCashPurchaseOrderRpt(String cashPurchaseOrderRpt) {
        this.cashPurchaseOrderRpt = cashPurchaseOrderRpt;
    }

    public String getCashPurchaseOrderRpt() {
        return cashPurchaseOrderRpt;
    }

    public void setCashpurchaseOrderLinkBinding(RichGoLink cashpurchaseOrderLinkBinding) {
        this.cashpurchaseOrderLinkBinding = cashpurchaseOrderLinkBinding;
    }

    public RichGoLink getCashpurchaseOrderLinkBinding() {
        return cashpurchaseOrderLinkBinding;
    }

    public void searchForSuggestedOrderAL(ActionEvent actionEvent) {
        getBindings().getOperationBinding("searchSuggestedOreder").execute();
    }

    public void resetForSugestedOrderAL(ActionEvent actionEvent) {
        getBindings().getOperationBinding("resetSuggestedOrder").execute();
    }

    public void genReportSuggestOrderAL(ActionEvent actionEvent) {
        if((Boolean)suggestOrderRptCheckBinding.getValue().equals(true)){
            suggestedOrder="true";
        }
        else{
            suggestedOrder="false";
        }
        if((Boolean)suggestorderSupplierwiseChekBinding.getValue().equals(true)){
            suggestedOrderSupplierwise="true";
        }
        else{
            suggestedOrderSupplierwise="false";
        }
    }

    public void setSuggestOrderRptCheckBinding(RichSelectBooleanCheckbox suggestOrderRptCheckBinding) {
        this.suggestOrderRptCheckBinding = suggestOrderRptCheckBinding;
    }

    public RichSelectBooleanCheckbox getSuggestOrderRptCheckBinding() {
        return suggestOrderRptCheckBinding;
    }

    public void setSuggestorderSupplierwiseChekBinding(RichSelectBooleanCheckbox suggestorderSupplierwiseChekBinding) {
        this.suggestorderSupplierwiseChekBinding = suggestorderSupplierwiseChekBinding;
    }

    public RichSelectBooleanCheckbox getSuggestorderSupplierwiseChekBinding() {
        return suggestorderSupplierwiseChekBinding;
    }

    public void setSuggestedOrder(String suggestedOrder) {
        this.suggestedOrder = suggestedOrder;
    }

    public String getSuggestedOrder() {
        return suggestedOrder;
    }

    public void setSuggestedOrderSupplierwise(String suggestedOrderSupplierwise) {
        this.suggestedOrderSupplierwise = suggestedOrderSupplierwise;
    }

    public String getSuggestedOrderSupplierwise() {
        return suggestedOrderSupplierwise;
    }

    public void setSuggestedOrderLinkBinding(RichGoLink suggestedOrderLinkBinding) {
        this.suggestedOrderLinkBinding = suggestedOrderLinkBinding;
    }

    public RichGoLink getSuggestedOrderLinkBinding() {
        return suggestedOrderLinkBinding;
    }

    public void setSuggestedOrderSupwiseLinkBinding(RichGoLink suggestedOrderSupwiseLinkBinding) {
        this.suggestedOrderSupwiseLinkBinding = suggestedOrderSupwiseLinkBinding;
    }

    public RichGoLink getSuggestedOrderSupwiseLinkBinding() {
        return suggestedOrderSupwiseLinkBinding;
    }

    public void setPoStatusWiseChekBinding(RichSelectBooleanCheckbox poStatusWiseChekBinding) {
        this.poStatusWiseChekBinding = poStatusWiseChekBinding;
    }

    public RichSelectBooleanCheckbox getPoStatusWiseChekBinding() {
        return poStatusWiseChekBinding;
    }

    public void setPoSummaryChekBinding(RichSelectBooleanCheckbox poSummaryChekBinding) {
        this.poSummaryChekBinding = poSummaryChekBinding;
    }

    public RichSelectBooleanCheckbox getPoSummaryChekBinding() {
        return poSummaryChekBinding;
    }

    public void setPoStatusWise(String PoStatusWise) {
        this.PoStatusWise = PoStatusWise;
    }

    public String getPoStatusWise() {
        return PoStatusWise;
    }

    public void setPoSummary(String PoSummary) {
        this.PoSummary = PoSummary;
    }

    public String getPoSummary() {
        return PoSummary;
    }

    public void setPoSingle(String PoSingle) {
        this.PoSingle = PoSingle;
    }

    public String getPoSingle() {
        return PoSingle;
    }

    public void setPurchaseOrderChkBinding(RichSelectBooleanCheckbox purchaseOrderChkBinding) {
        this.purchaseOrderChkBinding = purchaseOrderChkBinding;
    }

    public RichSelectBooleanCheckbox getPurchaseOrderChkBinding() {
        return purchaseOrderChkBinding;
    }

    public void setPoStatusLinkBinding(RichGoLink poStatusLinkBinding) {
        this.poStatusLinkBinding = poStatusLinkBinding;
    }

    public RichGoLink getPoStatusLinkBinding() {
        return poStatusLinkBinding;
    }

    public void setPoSummarylinkBinding(RichGoLink poSummarylinkBinding) {
        this.poSummarylinkBinding = poSummarylinkBinding;
    }

    public RichGoLink getPoSummarylinkBinding() {
        return poSummarylinkBinding;
    }

    public void setCpoSummaryCheckBinding(RichSelectBooleanCheckbox cpoSummaryCheckBinding) {
        this.cpoSummaryCheckBinding = cpoSummaryCheckBinding;
    }

    public RichSelectBooleanCheckbox getCpoSummaryCheckBinding() {
        return cpoSummaryCheckBinding;
    }

    public void setCpoSummary(String cpoSummary) {
        this.cpoSummary = cpoSummary;
    }

    public String getCpoSummary() {
        return cpoSummary;
    }

    public void setCpoSummarylinkBinding(RichGoLink cpoSummarylinkBinding) {
        this.cpoSummarylinkBinding = cpoSummarylinkBinding;
    }

    public RichGoLink getCpoSummarylinkBinding() {
        return cpoSummarylinkBinding;
    }

    public void setDeliveryScheduleChkBinding(RichSelectBooleanCheckbox deliveryScheduleChkBinding) {
        this.deliveryScheduleChkBinding = deliveryScheduleChkBinding;
    }

    public RichSelectBooleanCheckbox getDeliveryScheduleChkBinding() {
        return deliveryScheduleChkBinding;
    }

    public void setDeliveryScheduleLinkBinding(RichGoLink deliveryScheduleLinkBinding) {
        this.deliveryScheduleLinkBinding = deliveryScheduleLinkBinding;
    }

    public RichGoLink getDeliveryScheduleLinkBinding() {
        return deliveryScheduleLinkBinding;
    }

    public void setDeliverySchedule(String DeliverySchedule) {
        this.DeliverySchedule = DeliverySchedule;
    }

    public String getDeliverySchedule() {
        return DeliverySchedule;
    }

    

   

    public void setListOfQuotCheckBinding(RichSelectBooleanCheckbox listOfQuotCheckBinding) {
        this.listOfQuotCheckBinding = listOfQuotCheckBinding;
    }

    public RichSelectBooleanCheckbox getListOfQuotCheckBinding() {
        return listOfQuotCheckBinding;
    }

    public void setListOfQuotLinkBinding(RichGoLink listOfQuotLinkBinding) {
        this.listOfQuotLinkBinding = listOfQuotLinkBinding;
    }

    public RichGoLink getListOfQuotLinkBinding() {
        return listOfQuotLinkBinding;
    }

    public void setQuotAnaConsolidateChkBinding(RichSelectBooleanCheckbox quotAnaConsolidateChkBinding) {
        this.quotAnaConsolidateChkBinding = quotAnaConsolidateChkBinding;
    }

    public RichSelectBooleanCheckbox getQuotAnaConsolidateChkBinding() {
        return quotAnaConsolidateChkBinding;
    }

    public void setQuotAnaConsolidateLinkBinding(RichGoLink quotAnaConsolidateLinkBinding) {
        this.quotAnaConsolidateLinkBinding = quotAnaConsolidateLinkBinding;
    }

    public RichGoLink getQuotAnaConsolidateLinkBinding() {
        return quotAnaConsolidateLinkBinding;
    }

    public void setRateContractPOCheckBinding(RichSelectBooleanCheckbox rateContractPOCheckBinding) {
        this.rateContractPOCheckBinding = rateContractPOCheckBinding;
    }

    public RichSelectBooleanCheckbox getRateContractPOCheckBinding() {
        return rateContractPOCheckBinding;
    }

    public void setRateContractPOItemwiseChkBinding(RichSelectBooleanCheckbox rateContractPOItemwiseChkBinding) {
        this.rateContractPOItemwiseChkBinding = rateContractPOItemwiseChkBinding;
    }

    public RichSelectBooleanCheckbox getRateContractPOItemwiseChkBinding() {
        return rateContractPOItemwiseChkBinding;
    }

    public void setRateContractPolnkBinding(RichGoLink rateContractPolnkBinding) {
        this.rateContractPolnkBinding = rateContractPolnkBinding;
    }

    public RichGoLink getRateContractPolnkBinding() {
        return rateContractPolnkBinding;
    }

    public void setRateContractPoItemWiseLnkBinding(RichGoLink rateContractPoItemWiseLnkBinding) {
        this.rateContractPoItemWiseLnkBinding = rateContractPoItemWiseLnkBinding;
    }

    public RichGoLink getRateContractPoItemWiseLnkBinding() {
        return rateContractPoItemWiseLnkBinding;
    }

    public void setOpenOrderChkBinding(RichSelectBooleanCheckbox openOrderChkBinding) {
        this.openOrderChkBinding = openOrderChkBinding;
    }

    public RichSelectBooleanCheckbox getOpenOrderChkBinding() {
        return openOrderChkBinding;
    }

    public void setOpenOrderItmWiseChkBinding(RichSelectBooleanCheckbox openOrderItmWiseChkBinding) {
        this.openOrderItmWiseChkBinding = openOrderItmWiseChkBinding;
    }

    public RichSelectBooleanCheckbox getOpenOrderItmWiseChkBinding() {
        return openOrderItmWiseChkBinding;
    }

    public void setOpenOrderItemWiselnkBinding(RichGoLink openOrderItemWiselnkBinding) {
        this.openOrderItemWiselnkBinding = openOrderItemWiselnkBinding;
    }

    public RichGoLink getOpenOrderItemWiselnkBinding() {
        return openOrderItemWiselnkBinding;
    }

    public void setOpenOrderLnkBinding(RichGoLink openOrderLnkBinding) {
        this.openOrderLnkBinding = openOrderLnkBinding;
    }

    public RichGoLink getOpenOrderLnkBinding() {
        return openOrderLnkBinding;
    }

    public void setOpenOrder(String OpenOrder) {
        this.OpenOrder = OpenOrder;
    }

    public String getOpenOrder() {
        return OpenOrder;
    }

    public void setOpenOrderItemWise(String OpenOrderItemWise) {
        this.OpenOrderItemWise = OpenOrderItemWise;
    }

    public String getOpenOrderItemWise() {
        return OpenOrderItemWise;
    }

    public void setPoTypeBinding(RichPanelLabelAndMessage poTypeBinding) {
        this.poTypeBinding = poTypeBinding;
    }

    public RichPanelLabelAndMessage getPoTypeBinding() {
        return poTypeBinding;
    }

    public void setPoBasisBinding(RichPanelLabelAndMessage poBasisBinding) {
        this.poBasisBinding = poBasisBinding;
    }

    public RichPanelLabelAndMessage getPoBasisBinding() {
        return poBasisBinding;
    }

    public void setPotype(RichOutputText potype) {
        this.potype = potype;
    }

    public RichOutputText getPotype() {
        return potype;
    }

    public void setPoBasis(RichOutputText poBasis) {
        this.poBasis = poBasis;
    }

    public RichOutputText getPoBasis() {
        return poBasis;
    }

    public void poValueChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        rateContractPOCheckBinding.setValue(false);
        rateContractPOItemwiseChkBinding.setValue(false);
        openOrderChkBinding.setValue(false);
        openOrderItmWiseChkBinding.setValue(false);
        
    }
}
