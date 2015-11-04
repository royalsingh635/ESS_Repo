package slssecondarysaleapp.view.bean;

import adf.utils.model.ADFModelUtils;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.component.rich.nav.RichLink;

import oracle.adf.view.rich.component.rich.nav.RichGoLink;

import oracle.jbo.domain.Number;

import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;

import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;

import oracle.jbo.RowSetIterator;
import oracle.jbo.server.JboPrecisionScaleValidator;
import oracle.jbo.server.ViewObjectImpl;

import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.model.binding.DCIteratorBindingDef;

import oracle.binding.BindingContainer;

import oracle.jbo.Key;
import oracle.jbo.ViewObject;

import slssecondarysaleapp.model.module.SlSSecondarySaleAMImpl;
import slssecondarysaleapp.model.module.common.SlSSecondarySaleAM;

//import slssecondarysaleapp.model.module.SlSSecondarySaleAMImpl;

public class SecondarySaleBean {
    /**
     * V : View
     * E : Edit
     * A : Add
     */
    private static StringBuffer printreport= new StringBuffer("D");
    private static StringBuffer headerMode = new StringBuffer("V");
    private RichInputListOfValues itemIdBind;
    private RichInputText custNmBind;
    private RichInputText itemQntyBind;
    private RichInputText itemAmntBSbind;
    private RichInputText itmPriceBind;
    private RichTable ssIdNoTableBindVar;
    private RichInputListOfValues custNMBind;
    private RichInputListOfValues searchCustNmBind;
    private RichInputListOfValues searchShipBind;
    private RichInputListOfValues shipIdBind;
    private RichLink detailAddBind;
    private RichTable itemDetailTableBind;
    private RichGoLink printReportBind;

    public SecondarySaleBean() {
    }

    /**
     * create new secondary Sale
     * @param actionEvent
     */
    public void addButtonACTION(ActionEvent actionEvent) {
        //  this.ssIdNoTableBindVar.setRowSelection(this.ssIdNoTableBindVar.ROW_SELECTION_NONE);
        this.getBindings().getOperationBinding("CreateInsert").execute(); //SearchAction

        headerMode = new StringBuffer("A");
        searchCustNmBind.setValue("");
        searchShipBind.setValue("");
      
        AdfFacesContext.getCurrentInstance().addPartialTarget(ssIdNoTableBindVar);
    }

    /**
     * edit new secondary Sale
     * @param actionEvent
     */

    public void editButtonACTION(ActionEvent actionEvent) {
        // this.ssIdNoTableBindVar.setRowSelection(this.ssIdNoTableBindVar.ROW_SELECTION_NONE);
        // ssIdNoTableBindVar.setRowSelection(null);
        headerMode = new StringBuffer("E");
       
        AdfFacesContext.getCurrentInstance().addPartialTarget(ssIdNoTableBindVar);
    }

    /**
     * save new secondary Sale
     * @param actionEvent
     */
    public void saveButtonACTION(ActionEvent actionEvent) {
        
        if (itemIdBind.getValue() == null) {
            FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.310"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(itemIdBind.getClientId(), message);
            // fc.addMessage(null, message);
            return;
        }
        if (custNmBind.getValue() == null || custNmBind.getValue().toString().equals("")) {
            FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1280"));      //MSG.1280
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(custNmBind.getClientId(), message);
            // fc.addMessage(null, message);
            return;
        }
        if (itmPriceBind.getValue() == null || (!(Float.valueOf(itmPriceBind.getValue().toString()) > 0))) {
            FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1281"));   //MSG.1281
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(itmPriceBind.getClientId(), message);
            //   fc.addMessage(null, message);
            return;
        }
        if (itemQntyBind.getValue() == null || (!(Float.valueOf(itemQntyBind.getValue().toString()) > 0))) {
            FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1283"));     //MSG.1283
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(itemQntyBind.getClientId(), message);
            
            //   fc.addMessage(null, message);
            return;
        }
        OperationBinding chkQTyBinding =
            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("chkForShipmentQty");
        Object Qtybind = chkQTyBinding.execute();
        if(Qtybind == null || (Boolean)chkQTyBinding.getResult()== false)
        {
            FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2432"));//Please Adjust the Item Shipment Quantity in the Right Order!!!
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            return;
        }
        OperationBinding binding =
            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("ChkTableValidation");
        Object execute = binding.execute();
        System.out.println("Result is:  "+execute+"------");
        if(execute == null || (Boolean)binding.getResult()== false)
        {
            FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1296"));//please complete the details;MSG.1296
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            return;
        }
        
        if (headerMode.toString().equalsIgnoreCase("A")) {
            System.out.println("into function chk-------add");

            this.getBindings().getOperationBinding("generateAndSetSSId").execute();
        }
        System.out.println("into function chk-------edit");

        DCIteratorBinding parentitr = (DCIteratorBinding)getBindings().get("SlsSs1Iterator");
        Key parentKey = parentitr.getCurrentRow().getKey();
        OperationBinding commit = this.getBindings().getOperationBinding("Commit");
        commit.execute();
        this.getBindings().getOperationBinding("Execute").execute();
        this.getBindings().getOperationBinding("Execute2").execute();
        System.out.println("Row count is:  " + ssIdNoTableBindVar.getEstimatedRowCount());
        if (ssIdNoTableBindVar.getEstimatedRowCount() > 1) {
            try {
                parentitr.setCurrentRowWithKey(parentKey.toStringFormat(true));
            } catch (Exception e) {
                System.out.println("Row not found.....");
            }
        }
        // this.getBindings().getOperationBinding("Execute1").execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getSsIdNoTableBindVar());
        if (commit.getErrors().isEmpty()) {
            FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.614"));     //MSG.614
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            headerMode = new StringBuffer("V");
            printreport=new StringBuffer("V");
            AdfFacesContext.getCurrentInstance().addPartialTarget(printReportBind);
        } else {
            FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1284"));          //MSG.1284
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
        //   this.ssIdNoTableBindVar.setRowSelection(this.ssIdNoTableBindVar.ROW_SELECTION_SINGLE);
        AdfFacesContext.getCurrentInstance().addPartialTarget(ssIdNoTableBindVar);
        
    }

    /**
     * cancel new secondary Sale
     * @param actionEvent
     */
    public void cancelButtonACTION(ActionEvent actionEvent) {

        this.getBindings().getOperationBinding("Rollback").execute();
        this.getBindings().getOperationBinding("Execute2").execute();
        headerMode = new StringBuffer("V");
        //  this.ssIdNoTableBindVar.setRowSelection(this.ssIdNoTableBindVar.ROW_SELECTION_SINGLE);
        AdfFacesContext.getCurrentInstance().addPartialTarget(ssIdNoTableBindVar);
      
    }

    /**
     * to create Bindings
     * @param actionEvent
     */
    public static BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    /**
     * Add new Item
     * @param actionEvent
     */

    public void addItemACTION(ActionEvent actionEvent) {
        //if(shipIdBind.
        if ((custNmBind.getValue() != null && itemIdBind.getValue() != null &&
             Integer.parseInt(itmPriceBind.getValue().toString()) != 0 &&
             Integer.parseInt(itemQntyBind.getValue().toString()) != 0) ||
            itemDetailTableBind.getEstimatedRowCount() == 0) {
            this.getBindings().getOperationBinding("CreateInsert1").execute();
            this.getBindings().getOperationBinding("setItemSrNO").execute();
        } else {
            FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1296"));      //MSG.1296
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
        // OperationBinding binding=BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("ChkCurrentTableRow");
        //  binding.execute();
    }

    public void deleteItemACTON(ActionEvent actionEvent) {
        this.getBindings().getOperationBinding("Delete").execute();
    }

    public void setItemIdBind(RichInputListOfValues itemIdBind) {
        this.itemIdBind = itemIdBind;
    }

    public RichInputListOfValues getItemIdBind() {
        return itemIdBind;
    }

    public void setCustNmBind(RichInputText custNmBind) {
        this.custNmBind = custNmBind;
    }

    public RichInputText getCustNmBind() {
        return custNmBind;
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

    public void setItemQntyBind(RichInputText itemQntyBind) {
        this.itemQntyBind = itemQntyBind;
    }

    public RichInputText getItemQntyBind() {
        return itemQntyBind;
    }

    public void setItemAmntBSbind(RichInputText itemAmntBSbind) {
        this.itemAmntBSbind = itemAmntBSbind;
    }

    public RichInputText getItemAmntBSbind() {
        return itemAmntBSbind;
    }

    public void setHeaderMode(StringBuffer headerMode) {
        this.headerMode = headerMode;
    }

    public StringBuffer getHeaderMode() {
        return headerMode;
    }

    public void SearchAL(ActionEvent actionEvent) {
        this.getBindings().getOperationBinding("SearchAction").execute();
    }

    public void resetAL(ActionEvent actionEvent) {
        this.getBindings().getOperationBinding("ResetAction").execute();
    }

    public void itemPriceValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number n = (Number)object;
            Number prc = new Number(0);
            prc = (Number)itmPriceBind.getValue();
            if (n.compareTo(Number.zero()) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,ADFModelUtils.resolvRsrc("MSG.1285"),    //MSG.1285
                                                              null));
            } else {
                boolean b = isPrecisionValid(26, 6, n);
                if (!b) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  ADFModelUtils.resolvRsrc("MSG.1266"),                //MSG.1266
                                                                  null));
                }
            }

        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,ADFModelUtils.resolvRsrc("MSG.1279"), null));   //MSG.1279

        }

    }

    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }

    public void setItmPriceBind(RichInputText itmPriceBind) {
        this.itmPriceBind = itmPriceBind;
    }

    public RichInputText getItmPriceBind() {
        return itmPriceBind;
    }

    public void QntyVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number n = (Number)object;
            Number prc = new Number(0);
            prc = (Number)itemQntyBind.getValue();
            if (n.compareTo(Number.zero()) < 0) {
                String msg = ADFModelUtils.resolvRsrc("MSG.1282");                   //MSG.1282
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));

            } else {
                boolean b = isPrecisionValid(26, 6, n);
                if (!b) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,ADFModelUtils.resolvRsrc("MSG.1266"),   //MSG.1266
                                                                  null));

                }
            }

        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,ADFModelUtils.resolvRsrc("MSG.1279"), null));   //MSG.1279

        }
    }

    public void setSsIdNoTableBindVar(RichTable ssIdNoTableBindVar) {
        this.ssIdNoTableBindVar = ssIdNoTableBindVar;
    }

    public RichTable getSsIdNoTableBindVar() {
        return ssIdNoTableBindVar;
    }

    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }

    public void setCustNMBind(RichInputListOfValues custNMBind) {
        this.custNMBind = custNMBind;
    }

    public RichInputListOfValues getCustNMBind() {
        return custNMBind;
    }

    public void setSearchCustNmBind(RichInputListOfValues searchCustNmBind) {
        this.searchCustNmBind = searchCustNmBind;
    }

    public RichInputListOfValues getSearchCustNmBind() {
        return searchCustNmBind;
    }

    public void setSearchShipBind(RichInputListOfValues searchShipBind) {
        this.searchShipBind = searchShipBind;
    }

    public RichInputListOfValues getSearchShipBind() {
        return searchShipBind;
    }

    public void setShipIdBind(RichInputListOfValues shipIdBind) {
        this.shipIdBind = shipIdBind;
    }

    public RichInputListOfValues getShipIdBind() {
        return shipIdBind;
    }

    public void shiptransChangeAction(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(detailAddBind);
    }

    public void setDetailAddBind(RichLink detailAddBind) {
        this.detailAddBind = detailAddBind;
    }

    public RichLink getDetailAddBind() {
        return detailAddBind;
    }

    public void CustTransChangeAction(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        shipIdBind.setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(shipIdBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(detailAddBind);

    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public void setItemDetailTableBind(RichTable itemDetailTableBind) {
        this.itemDetailTableBind = itemDetailTableBind;
    }

    public RichTable getItemDetailTableBind() {
        return itemDetailTableBind;
    }

    public void setPrintreport(StringBuffer printreport) {
        this.printreport = printreport;
    }

    public StringBuffer getPrintreport() {
        return printreport;
    }

    public void setPrintReportBind(RichGoLink printReportBind) {
        this.printReportBind = printReportBind;
    }

    public RichGoLink getPrintReportBind() {
        return printReportBind;
    }
}
