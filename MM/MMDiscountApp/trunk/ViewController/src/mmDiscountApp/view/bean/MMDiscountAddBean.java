package mmDiscountApp.view.bean;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import java.text.SimpleDateFormat;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import mmDiscountApp.model.service.MMDiscountAppAMImpl;
import mmDiscountApp.model.views.MmSchmFreeItmVOImpl;
import mmDiscountApp.model.views.MmSchmItmVOImpl;
import mmDiscountApp.model.views.MmSchmVOImpl;

import oracle.adf.model.BindingContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelLabelAndMessage;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.JboPrecisionScaleValidator;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


public class MMDiscountAddBean {
    private RichInputDate inActiveBindVar;
    private RichInputText inActiveReason;
    private RichPopup itemPopup;
    private String ModeItem = "V";
    private RichTable itemTable;
    private RichSelectOneChoice discType;
    private RichPopup deleteItemPopup;
    private String ModeFreeItem = "V";
    private RichPopup freeItemPopup;
    private RichPopup deleteFreeItemPopup;
    private RichTable freeItemTable;
    private RichSelectOneChoice freeitmDiscType;
    private RichInputDate validFrmDtBind;
    private RichInputDate validToDtBind;
    private RichInputText itmPriceBind;
    private RichInputText qtyBind;
    private RichInputText freeValueBind;
    private RichPopup valuePopUp;
    private RichInputText freeQtyBind;
    private RichInputText discvalueBind;
    private RichPanelLabelAndMessage freeValuepanelBind;
    private RichPanelFormLayout formBind;
    private RichOutputText paidProductValueBind;
    private RichOutputText freeProductValueBind;
    private RichPanelGroupLayout paidProductpanelBind;
    private RichPanelGroupLayout freeProductPanelBind;
    private RichSelectOneChoice schmTypBind;
    private RichPopup delmsgpopup;


    public MMDiscountAddBean() {
    }
    private String Mode = getModeVal();


    public String CreateAction() {
        this.setMode("A");
        return "Create";
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String EditAction() {
        setMode("E");
        return null;
    }

    public String SaveAction() {
        offmode = "N";
        OperationBinding createOB = executeBinding("Commit");
        createOB.execute();
        if (createOB.getErrors().isEmpty()) {
            if (getMode().equals("A")) {
                String Defaultmsg = resolvEl("#{bundle['MSG.89']}");
                String valMsg = Defaultmsg.format(Defaultmsg, "", "", "", "", "");
                FacesMessage msg = new FacesMessage(valMsg);
                msg.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage(null, msg);
            } else if (getMode().equals("E")) {
                String Defaultmsg = resolvEl("#{bundle['MSG.90']}");
                String valMsg = Defaultmsg.format(Defaultmsg, "", "", "", "", "");
                FacesMessage msg = new FacesMessage(valMsg);
                msg.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage(null, msg);
            }
            this.setMode("V");
        }

        return null;
    }

    public String CancelAction() {
        offmode = "N";
        MMDiscountAppAMImpl am = (MMDiscountAppAMImpl)resolvElDC("MMDiscountAppAMDataControl");

       BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        BindingContainer bindings1 = getBindings();
        OperationBinding operationBinding1 = bindings1.getOperationBinding("Execute2");
        operationBinding1.execute();
        am.getMMSchemeSearchView().executeQuery();
        RequestContext context = RequestContext.getCurrentInstance();
        context.getPageFlowScope().put("SCHEME_ID", null);
        setMode("");

        /*   FilterableQueryDescriptor queryDescriptor = (FilterableQueryDescriptor)getItemTable().getFilterModel();

                if (queryDescriptor != null && queryDescriptor.getFilterCriteria() != null) {
                  queryDescriptor.getFilterCriteria().clear();
                  //getItemTable().queueEvent(new QueryEvent(getItemTable(), queryDescriptor));
                  QueryEvent queryEvent = new QueryEvent(getItemTable(),queryDescriptor );
                  queryEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                  getItemTable().queueEvent(queryEvent);
                 }
                  AdfFacesContext.getCurrentInstance().addPartialTarget(itemTable);

        operationBinding1.execute(); */
        /*   FilterableQueryDescriptor queryDescripto = (FilterableQueryDescriptor)getFreeItemTable().getFilterModel();
         if (queryDescripto != null && queryDescripto.getFilterCriteria() != null) {
         queryDescripto.getFilterCriteria().clear();
         getFreeItemTable().queueEvent(new QueryEvent(getFreeItemTable(), queryDescripto));
         } */
        return "Back";
    }

    public void ActiveChangeListner(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            String value = vce.getNewValue().toString();

            if (value.equals("false")) {
                Date dt = (Date)Date.getCurrentDate();
                inActiveBindVar.setValue(dt);


                AdfFacesContext.getCurrentInstance().addPartialTarget(inActiveReason);
                AdfFacesContext.getCurrentInstance().addPartialTarget(inActiveBindVar);
            } else if (value.equals("true")) {

                inActiveBindVar.setValue(null);
                inActiveReason.setValue(null);
                AdfFacesContext.getCurrentInstance().addPartialTarget(inActiveReason);
                AdfFacesContext.getCurrentInstance().addPartialTarget(inActiveBindVar);
            }
        }
    }

    public String EditSchItemAction() {
        showPopup(itemPopup, true);
        ModeItem = "E";
        return null;
    }

    public String CreateScmItemAction() {

        OperationBinding createBinding = executeBinding("CreateInsert");
        createBinding.execute();
        if (createBinding.getErrors().isEmpty()) {
            ModeItem = "A";
            showPopup(itemPopup, true);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(paidProductValueBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(paidProductpanelBind);
        return null;
    }

    public void ItemCancelListner(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer bindings = getBindings();

        DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("MmSchmIterator");
        if (parentIter.getCurrentRow().getKey() != null) {
            Key parentKey = parentIter.getCurrentRow().getKey();
            parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));

        }

        OperationBinding createOpBAddress = bindings.getOperationBinding("Rollback");
        createOpBAddress.execute();


        OperationBinding createOpBAddress1 = bindings.getOperationBinding("Execute");
        createOpBAddress1.execute();
        /*   if(parentKey != null){
        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
        }   */
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemTable);


    }

    public void ItemDialogListner(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            OperationBinding commBinding = executeBinding("Commit");
            commBinding.execute();
            if (commBinding.getErrors().isEmpty()) {
                if (ModeItem.equals("A")) {
                    String Defaultmsg = resolvEl("#{bundle['MSG.89']}");
                    String valMsg = Defaultmsg.format(Defaultmsg, "", "", "", "", "");
                    FacesMessage msg = new FacesMessage(valMsg);
                    msg.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    ctx.addMessage(null, msg);
                } else if (ModeItem.equals("E")) {
                    String Defaultmsg = resolvEl("#{bundle['MSG.90']}");
                    String valMsg = Defaultmsg.format(Defaultmsg, "", "", "", "", "");
                    FacesMessage msg = new FacesMessage(valMsg);
                    msg.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    ctx.addMessage(null, msg);
                }
                OperationBinding exeBinding = executeBinding("Execute");
                exeBinding.execute();
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemTable);
            AdfFacesContext.getCurrentInstance().addPartialTarget(paidProductValueBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(paidProductpanelBind);

        } else if (dialogEvent.getOutcome().name().equals("cancel")) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(paidProductValueBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(paidProductpanelBind);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(paidProductValueBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(paidProductpanelBind);
    }

    public String DeleteItemAction() {

        MMDiscountAppAMImpl am = (MMDiscountAppAMImpl)resolvElDC("MMDiscountAppAMDataControl");

        MmSchmItmVOImpl v3 = am.getMmSchmItm1();
        MmSchmFreeItmVOImpl v4 = am.getMmSchmFreeItm1();

        Number paidItemTotSum = new Number(0);

        Number freeItemTotSum = new Number(0);


        RowSetIterator paidItm_RSI = v3.createRowSetIterator(null);
        while (paidItm_RSI.hasNext()) {
            Row rw = paidItm_RSI.next();


            paidItemTotSum =
                    paidItemTotSum.add(((Number)rw.getAttribute("ItmPrice")).multiply(((Number)rw.getAttribute("ItmQty"))));

        }
        paidItm_RSI.closeRowSetIterator();


        RowSetIterator freeItm_RSI = v4.createRowSetIterator(null);
        while (freeItm_RSI.hasNext()) {
            Row rw = freeItm_RSI.next();


            freeItemTotSum =
                    freeItemTotSum.add(((Number)rw.getAttribute("ItmPrice")).multiply(((Number)rw.getAttribute("ItmQty"))));

        }
        freeItm_RSI.closeRowSetIterator();


        //   System.out.println("paidItemTotSum---->" + paidItemTotSum);
        //   System.out.println("freeItemTotSum---->" + freeItemTotSum);
        if (paidItemTotSum.compareTo(freeItemTotSum) == -1) {

            String valMsg = resolvEl("#{bundle['MSG.260']}");
            FacesMessage msg = new FacesMessage(valMsg);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, msg);
        } else {
            showPopup(deleteItemPopup, true);
        }
        return null;
    }

    public void DeleteItemDialogListner(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("yes")) {

            MMDiscountAppAMImpl am = (MMDiscountAppAMImpl)resolvElDC("MMDiscountAppAMDataControl");

            MmSchmItmVOImpl v3 = am.getMmSchmItm1();
            Row paidCurrRw = v3.getCurrentRow();
            MmSchmFreeItmVOImpl v4 = am.getMmSchmFreeItm1();

            Number paidItemTotSum = new Number(0);

            Number freeItemTotSum = new Number(0);


            RowSetIterator paidItm_RSI = v3.createRowSetIterator(null);
            while (paidItm_RSI.hasNext()) {
                Row rw = paidItm_RSI.next();

                if (rw != paidCurrRw) {
                    paidItemTotSum =
                            paidItemTotSum.add(((Number)rw.getAttribute("ItmPrice")).multiply(((Number)rw.getAttribute("ItmQty"))));
                }


            }
            paidItm_RSI.closeRowSetIterator();


            RowSetIterator freeItm_RSI = v4.createRowSetIterator(null);
            while (freeItm_RSI.hasNext()) {
                Row rw = freeItm_RSI.next();


                freeItemTotSum =
                        freeItemTotSum.add(((Number)rw.getAttribute("ItmPrice")).multiply(((Number)rw.getAttribute("ItmQty"))));

            }
            freeItm_RSI.closeRowSetIterator();


            //  System.out.println("paidItemTotSum---->" + paidItemTotSum);
            //  System.out.println("freeItemTotSum---->" + freeItemTotSum);
            if (paidItemTotSum.compareTo(freeItemTotSum) == -1) {

                String valMsg = resolvEl("#{bundle['MSG.260']}");
                FacesMessage msg = new FacesMessage(valMsg);
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage(null, msg);
            } else {
                OperationBinding deleteBinding = executeBinding("Delete");
                deleteBinding.execute();
                OperationBinding commBinding = executeBinding("Commit");
                commBinding.execute();
                OperationBinding exeBinding = executeBinding("Execute");
                exeBinding.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(itemTable);
            }

        }

    }

    private String getModeVal() {
        String callingVal = resolvEl("#{pageFlowScope.CallingValue}");
        return callingVal;
    }

    public OperationBinding executeBinding(String var) {
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding(var);
        return operationBinding;
    }

    public void ItemDiscValValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        /* if (discType.getValue() == null) {
            String msg = "Please select Discount Type first.";

            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
        }  else {*/
        System.out.println(discType.getSubmittedValue() + "type-----: " + discType.getValue());

        if (object != null) {
            Number val = (Number)object;
            System.out.println("value------------:" + val);
            Number zeroVal = new Number(0);
            Number hundredVal = new Number(100);
            Number total = ((Number)itmPriceBind.getValue()).multiply(((Number)qtyBind.getValue()));
            if (val.compareTo(zeroVal) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.261']}"), null));
            }

            if (discType.getValue().equals("A")) {
                if (val.compareTo(total) >= 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  resolvEl("#{bundle['MSG.262']}") + total, null));
                }
            }
            if (discType.getValue().equals("P")) {
                if (val.compareTo(hundredVal) >= 0) {
                    String msg = resolvEl("#{bundle['MSG.263']}");
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }


        }
    }

    public String CreateFreeItem() {

        OperationBinding createBinding = executeBinding("CreateInsert1");
        createBinding.execute();
        if (createBinding.getErrors().isEmpty()) {
            ModeFreeItem = "A";
            showPopup(freeItemPopup, true);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(freeProductPanelBind);
        return null;
    }

    public String EditFreeItem() {
        showPopup(freeItemPopup, true);
        ModeFreeItem = "E";
        return null;
    }

    public String DeleteFreeItem() {
        showPopup(deleteFreeItemPopup, true);
        return null;
    }

    public void FreeItemCancelListner(PopupCanceledEvent popupCanceledEvent) {

        BindingContainer bindings = getBindings();

        DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("MmSchmIterator");
        Key parentKey = parentIter.getCurrentRow().getKey();


        OperationBinding createOpBAddress = bindings.getOperationBinding("Rollback");
        createOpBAddress.execute();


        OperationBinding createOpBAddress1 = bindings.getOperationBinding("Execute1");
        createOpBAddress1.execute();
        if (parentKey != null) {
            parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(freeItemTable);


    }

    //  public void FreeItemDialogListner(DialogEvent dialogEvent) {
    // if (dialogEvent.getOutcome().name().equals("ok")) {
    /*  MMDiscountAppAMImpl am = (MMDiscountAppAMImpl)resolvElDC("MMDiscountAppAMDataControl");
             MmSchmItmVOImpl v3 = am.getMmSchmItm1();
            Row CurrRwItm=v3.getCurrentRow();
            MmSchmFreeItmVOImpl v4 = am.getMmSchmFreeItm1();
            Row CurrRwFree=v4.getCurrentRow();
            Number itmPricePaid = (Number)CurrRwItm.getAttribute("ItmPrice");
            Number itmQtyPaid = (Number)CurrRwItm.getAttribute("ItmQty");
            Number itmPriceFree = (Number)CurrRwFree.getAttribute("ItmPrice");
            Number itmQtyFree = (Number)CurrRwFree.getAttribute("ItmQty");
            Number totalPaidValue = itmPricePaid.multiply(itmQtyPaid);
            Number totalFreeValue = itmPriceFree.multiply(itmQtyFree);
            System.out.println("-----paid value "+totalPaidValue+"free value-----------"+totalFreeValue);
           // Number totalPaidValue = ((Number)itmPriceBind.getValue()).multiply((Number)qtyBind.getValue());
            if(totalPaidValue.compareTo(totalFreeValue) == -1){
                showPopup(valuePopUp, true);
            }else{
            OperationBinding commBinding = executeBinding("Commit");
            commBinding.execute();
            if(commBinding.getErrors().isEmpty()){

            String FocFlg="N";

            MmSchmVOImpl v1 = am.getMmSchm();
            Row CurrRw=v1.getCurrentRow();

            if(CurrRw.getAttribute("SchmType")==null){
                FocFlg="N";
            }
            else if((Integer)CurrRw.getAttribute("SchmType")==178){
                FocFlg="Y";
            }
            else{
                FocFlg="N";
            }




            MmSchmFreeItmVOImpl v2 = am.getMmSchmFreeItm1();
            Row cRw=v2.getCurrentRow();
            cRw.setAttribute("FocFlg", FocFlg);
            commBinding.execute();
            OperationBinding exeBinding = executeBinding("Execute1");
            exeBinding.execute();

            if( ModeFreeItem.equals("A")){
                String Defaultmsg = resolvEl("#{bundle['APP.Message.Save']}");
                String valMsg = Defaultmsg.format(Defaultmsg, "", "", "", "", "");
                FacesMessage msg = new FacesMessage(valMsg);
                msg.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage(null, msg);
            }
            else if(ModeFreeItem.equals("E")){
                String Defaultmsg = resolvEl("#{bundle['APP.Message.Update']}");
                String valMsg = Defaultmsg.format(Defaultmsg, "", "", "", "", "");
                FacesMessage msg = new FacesMessage(valMsg);
                msg.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage(null, msg);
            }
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(freeItemTable);


        } */
    // }
    // }

    public void DeleteFreeItemDialogListner(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("yes")) {

            OperationBinding deleteBinding = executeBinding("Delete1");
            deleteBinding.execute();
            OperationBinding commBinding = executeBinding("Commit");
            commBinding.execute();
            OperationBinding exeBinding = executeBinding("Execute1");
            exeBinding.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(freeItemTable);


        }
    }

    public void FreeItemDiscVal(FacesContext facesContext, UIComponent uIComponent, Object object) {
        MMDiscountAppAMImpl am = (MMDiscountAppAMImpl)resolvElDC("MMDiscountAppAMDataControl");
        MmSchmFreeItmVOImpl v4 = am.getMmSchmFreeItm1();
        Row CurrRwFree = v4.getCurrentRow();
        Number itmPriceFree = (Number)CurrRwFree.getAttribute("ItmPrice");
        Number itmQtyFree = (Number)CurrRwFree.getAttribute("ItmQty");
        Number totalFreeValue = itmPriceFree.multiply(itmQtyFree);

        if (freeitmDiscType.getValue() == null) {
            String msg = resolvEl("#{bundle['MSG.264']}");

            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
        } else {
            if (object != null) {
                Number val = (Number)object;
                Number zeroVal = new Number(0);
                Number hundredVal = new Number(100);
                if (freeitmDiscType.getValue().equals("A")) {
                    if (val.compareTo(zeroVal) == -1) {
                        String msg = resolvEl("#{bundle['MSG.265']}");

                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                    } else if (val.compareTo(totalFreeValue) == 1) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      resolvEl("#{bundle['MSG.262']}") +
                                                                      totalFreeValue, null));
                    }
                } else if (freeitmDiscType.getValue().equals("P")) {
                    if (val.compareTo(zeroVal) == -1) {
                        String msg = resolvEl("#{bundle['MSG.266']}");

                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                    } else if (val.compareTo(hundredVal) == 1) {
                        String msg = resolvEl("#{bundle['MSG.263']}");

                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                    }
                }


            }
        }


    }

    private void showPopup(RichPopup popup, boolean visible) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && popup != null) {
                String popupId = popup.getClientId(context);
                if (popupId != null) {
                    StringBuilder script = new StringBuilder();
                    script.append("var popup = AdfPage.PAGE.findComponent('").append(popupId).append("'); ");
                    if (visible) {
                        script.append("if (!popup.isPopupVisible()) { ").append("popup.show();}");
                    } else {
                        script.append("if (popup.isPopupVisible()) { ").append("popup.hide();}");
                    }
                    ExtendedRenderKitService erks =
                        Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);
                    erks.addScript(context, script.toString());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }


    public void setMode(String Mode) {
        this.Mode = Mode;
    }

    public String getMode() {
        if (Mode == "") {
            return getModeVal();
        } else {
            return Mode;
        }

    }


    public void setInActiveBindVar(RichInputDate inActiveBindVar) {
        this.inActiveBindVar = inActiveBindVar;
    }

    public RichInputDate getInActiveBindVar() {
        return inActiveBindVar;
    }

    public void setInActiveReason(RichInputText inActiveReason) {
        this.inActiveReason = inActiveReason;
    }

    public RichInputText getInActiveReason() {
        return inActiveReason;
    }


    public void setItemPopup(RichPopup itemPopup) {
        this.itemPopup = itemPopup;
    }

    public RichPopup getItemPopup() {
        return itemPopup;
    }


    public void setModeItem(String ModeItem) {
        this.ModeItem = ModeItem;
    }

    public String getModeItem() {
        return ModeItem;
    }

    public void setItemTable(RichTable itemTable) {
        this.itemTable = itemTable;
    }

    public RichTable getItemTable() {
        return itemTable;
    }

    public void setDiscType(RichSelectOneChoice discType) {
        this.discType = discType;
    }

    public RichSelectOneChoice getDiscType() {
        return discType;
    }


    public void setDeleteItemPopup(RichPopup deleteItemPopup) {
        this.deleteItemPopup = deleteItemPopup;
    }

    public RichPopup getDeleteItemPopup() {
        return deleteItemPopup;
    }


    public void setModeFreeItem(String ModeFreeItem) {
        this.ModeFreeItem = ModeFreeItem;
    }

    public String getModeFreeItem() {
        return ModeFreeItem;
    }

    public void setFreeItemPopup(RichPopup freeItemPopup) {
        this.freeItemPopup = freeItemPopup;
    }

    public RichPopup getFreeItemPopup() {
        return freeItemPopup;
    }


    public void setDeleteFreeItemPopup(RichPopup deleteFreeItemPopup) {
        this.deleteFreeItemPopup = deleteFreeItemPopup;
    }

    public RichPopup getDeleteFreeItemPopup() {
        return deleteFreeItemPopup;
    }


    public void setFreeItemTable(RichTable freeItemTable) {
        this.freeItemTable = freeItemTable;
    }

    public RichTable getFreeItemTable() {
        return freeItemTable;
    }


    public void setFreeitmDiscType(RichSelectOneChoice freeitmDiscType) {
        this.freeitmDiscType = freeitmDiscType;
    }

    public RichSelectOneChoice getFreeitmDiscType() {
        return freeitmDiscType;
    }


    public void setValidFrmDtBind(RichInputDate validFrmDtBind) {
        this.validFrmDtBind = validFrmDtBind;
    }

    public RichInputDate getValidFrmDtBind() {
        return validFrmDtBind;
    }

    public void setValidToDtBind(RichInputDate validToDtBind) {
        this.validToDtBind = validToDtBind;
    }

    public RichInputDate getValidToDtBind() {
        return validToDtBind;
    }

    public void validFrmDtVCL(ValueChangeEvent vce) {
        //System.out.println("new value-------" + vce.getNewValue().toString());
        AdfFacesContext.getCurrentInstance().addPartialTarget(validToDtBind);
        //  validToDtBind.validate(FacesContext.getCurrentInstance());

        //  System.out.println("value-----------:"+validToDtBind.getValue());

    }


    public Boolean isPrecisionValid(Integer precision, Integer scale, oracle.jbo.domain.Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();

        vc.setPrecision(precision);


        vc.setScale(scale);

        return vc.validateValue(total);
    }


    public void quantityValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number n = (Number)object;
        Number zero = new Number(0);
        if (n.compareTo(zero) == 0 || n.compareTo(zero) == -1) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          resolvEl("#{bundle['LBL.1270']}"), null));
        }


        if (isPrecisionValid(26, 6, n)) {

        } else {


            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvEl("#{bundle['MSG.57']}"),
                                                          null));
        }
    }

    public void setItmPriceBind(RichInputText itmPriceBind) {
        this.itmPriceBind = itmPriceBind;
    }

    public RichInputText getItmPriceBind() {
        return itmPriceBind;
    }

    public void setQtyBind(RichInputText qtyBind) {
        this.qtyBind = qtyBind;
    }

    public RichInputText getQtyBind() {
        return qtyBind;
    }
    String offmode = "N";

    public void offerTypeChangeListener(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            offmode = "Y";
            // String schmidNew= vce.getNewValue().toString();
          //  BindingContainer bindings = getBindings();
            //DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("MmSchmItm1Iterator");
            // Key parentKey = parentIter.getCurrentRow().getKey();
            MMDiscountAppAMImpl am = (MMDiscountAppAMImpl)resolvElDC("MMDiscountAppAMDataControl");
            ViewObject schm = am.getMmSchm();
            ViewObject schmItm = am.getMmSchmItm();
            ViewObject schmFree = am.getMmSchmFreeItm1();
            Row rr = schm.getCurrentRow();
            String schmId = rr.getAttribute("SchmId").toString();
            System.out.println("sch in inside cl--" + schmId);
            schmItm.setWhereClause("SCHM_ID = '" + schmId + "'");
            schmFree.setWhereClause("SCHM_ID = '" + schmId + "'");
            schmItm.executeQuery();
            schmFree.executeQuery();
            RowSetIterator rsi1 = schmItm.createRowSetIterator(null);
            RowSetIterator rsi2 = schmFree.createRowSetIterator(null);
            while (rsi1.hasNext()) {
                Row nxt = rsi1.next();
                nxt.remove();
            }
            rsi1.closeRowSetIterator();
            while (rsi2.hasNext()) {
                Row nxt1 = rsi2.next();
                nxt1.remove();
            }
            rsi2.closeRowSetIterator();
            OperationBinding operationBinding1 = getBindings().getOperationBinding("Execute");
            operationBinding1.execute();
            OperationBinding operationBinding2 = getBindings().getOperationBinding("Execute1");
            operationBinding2.execute();

            am.getMmSchmItm().executeQuery();
            am.getMmSchmFreeItm1().executeQuery();
        }

    }

    public void setFreeValueBind(RichInputText freeValueBind) {
        this.freeValueBind = freeValueBind;
    }

    public RichInputText getFreeValueBind() {
        return freeValueBind;
    }

    public void freeDiscTypeVCL(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(freeValueBind);
        Number zero = new Number(0);
        MMDiscountAppAMImpl am = (MMDiscountAppAMImpl)resolvElDC("MMDiscountAppAMDataControl");
        ViewObjectImpl vo = am.getMmSchmFreeItm1();
        Row rr = vo.getCurrentRow();
        rr.setAttribute("DiscVal", zero);
        freeValueBind.setValue(new Number(0));
        AdfFacesContext.getCurrentInstance().addPartialTarget(formBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(freeValueBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(freeValuepanelBind);

    }

    /*  public void qtyValueChange(ValueChangeEvent vce) {
        Number newQty = (Number)vce.getNewValue();
        MMDiscountAppAMImpl am = (MMDiscountAppAMImpl)resolvElDC("MMDiscountAppAMDataControl");
        MmSchmItmVOImpl v3 = am.getMmSchmItm1();
        Row CurrRwItm=v3.getCurrentRow();
        MmSchmFreeItmVOImpl v4 = am.getMmSchmFreeItm1();
        Row CurrRwFree=v4.getCurrentRow();
        Number itmPricePaid = (Number)CurrRwItm.getAttribute("ItmPrice");
        Number itmQtyPaid = (Number)CurrRwItm.getAttribute("ItmQty");
        Number itmPriceFree = (Number)CurrRwFree.getAttribute("ItmPrice");
        Number itmQtyFree = (Number)CurrRwFree.getAttribute("ItmQty");
        Number totalPaidValue = itmPricePaid.multiply(itmQtyPaid);
        //Number totalFreeValue = itmPriceFree.multiply(itmQtyFree);
        Number totalFreeValue = itmPriceFree.multiply(newQty);
        if(totalPaidValue.compareTo(totalFreeValue) == -1){
            showPopup(valuePopUp, true);
        }
    } */

    public void setValuePopUp(RichPopup valuePopUp) {
        this.valuePopUp = valuePopUp;
    }

    public RichPopup getValuePopUp() {
        return valuePopUp;
    }

    public void freeItmValuevalidDialogListener(DialogEvent dialogEvent) {
        MMDiscountAppAMImpl am = (MMDiscountAppAMImpl)resolvElDC("MMDiscountAppAMDataControl");
        if (dialogEvent.getOutcome().name().equals("yes")) {
            OperationBinding commBinding = executeBinding("Commit");
            commBinding.execute();
            if (commBinding.getErrors().isEmpty()) {

                String FocFlg = "N";

                MmSchmVOImpl v1 = am.getMmSchm();
                Row CurrRw = v1.getCurrentRow();

                if (CurrRw.getAttribute("SchmType") == null) {
                    FocFlg = "N";
                } else if ((Integer)CurrRw.getAttribute("SchmType") == 178) {
                    FocFlg = "Y";
                } else {
                    FocFlg = "N";
                }
                MmSchmFreeItmVOImpl v2 = am.getMmSchmFreeItm1();
                Row cRw = v2.getCurrentRow();
                cRw.setAttribute("FocFlg", FocFlg);
                commBinding.execute();
                OperationBinding exeBinding = executeBinding("Execute1");
                exeBinding.execute();

                if (ModeFreeItem.equals("A")) {
                    String Defaultmsg = resolvEl("#{bundle['MSG.89']}");
                    String valMsg = Defaultmsg.format(Defaultmsg, "", "", "", "", "");
                    FacesMessage msg = new FacesMessage(valMsg);
                    msg.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    ctx.addMessage(null, msg);
                } else if (ModeFreeItem.equals("E")) {
                    String Defaultmsg = resolvEl("#{bundle['MSG.90']}");
                    String valMsg = Defaultmsg.format(Defaultmsg, "", "", "", "", "");
                    FacesMessage msg = new FacesMessage(valMsg);
                    msg.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    ctx.addMessage(null, msg);
                }
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(freeItemTable);
            freeItemPopup.hide();

        } else if (dialogEvent.getOutcome().name().equals("no")) {
            /* FacesMessage message =
                new FacesMessage("");
            message.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(freeQtyBind.getClientId(), message); */
        }
    }

    public void setFreeQtyBind(RichInputText freeQtyBind) {
        this.freeQtyBind = freeQtyBind;
    }

    public RichInputText getFreeQtyBind() {
        return freeQtyBind;
    }

    /*  public void FreeItemDialogListner(ActionEvent actionEvent) {
        MMDiscountAppAMImpl am = (MMDiscountAppAMImpl)resolvElDC("MMDiscountAppAMDataControl");
        MmSchmVOImpl v1 = am.getMmSchm();
        Row CurrRw=v1.getCurrentRow();
        MmSchmItmVOImpl v3 = am.getMmSchmItm1();
        Row CurrRwItm=v3.getCurrentRow();
        MmSchmFreeItmVOImpl v4 = am.getMmSchmFreeItm1();
        Row CurrRwFree=v4.getCurrentRow();
        Number itmPricePaid = (Number)CurrRwItm.getAttribute("ItmPrice");
        Number itmQtyPaid = (Number)CurrRwItm.getAttribute("ItmQty");
        Number itmPriceFree = (Number)CurrRwFree.getAttribute("ItmPrice");
        Number itmQtyFree = (Number)CurrRwFree.getAttribute("ItmQty");
        Number totalPaidValue = itmPricePaid.multiply(itmQtyPaid);
        Number totalFreeValue = itmPriceFree.multiply(itmQtyFree);
        System.out.println("-----paid value "+totalPaidValue+"free value-----------"+totalFreeValue);
        // Number totalPaidValue = ((Number)itmPriceBind.getValue()).multiply((Number)qtyBind.getValue());
        if(totalPaidValue.compareTo(totalFreeValue) == -1 && (Integer)CurrRw.getAttribute("SchmType")==178){
            showPopup(valuePopUp, true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(freeProductPanelBind);
        }else{
        OperationBinding commBinding = executeBinding("Commit");
        commBinding.execute();
        if(commBinding.getErrors().isEmpty()){

        String FocFlg="N";
        if(CurrRw.getAttribute("SchmType")==null){
            FocFlg="N";
        }
        else if((Integer)CurrRw.getAttribute("SchmType")==178){
            FocFlg="Y";
        }
        else{
            FocFlg="N";
        }




        MmSchmFreeItmVOImpl v2 = am.getMmSchmFreeItm1();
        Row cRw=v2.getCurrentRow();
        cRw.setAttribute("FocFlg", FocFlg);
        commBinding.execute();
        OperationBinding exeBinding = executeBinding("Execute1");
        exeBinding.execute();

        if( ModeFreeItem.equals("A")){
            String Defaultmsg = resolvEl("#{bundle['APP.Message.Save']}");
            String valMsg = Defaultmsg.format(Defaultmsg, "", "", "", "", "");
            FacesMessage msg = new FacesMessage(valMsg);
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, msg);
        }
        else if(ModeFreeItem.equals("E")){
            String Defaultmsg = resolvEl("#{bundle['APP.Message.Update']}");
            String valMsg = Defaultmsg.format(Defaultmsg, "", "", "", "", "");
            FacesMessage msg = new FacesMessage(valMsg);
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, msg);
        }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(freeItemTable);
        AdfFacesContext.getCurrentInstance().addPartialTarget(freeProductPanelBind);
            freeItemPopup.hide();

        }

    } */


    public void FreeItemDialogListner(ActionEvent actionEvent) {
        MMDiscountAppAMImpl am = (MMDiscountAppAMImpl)resolvElDC("MMDiscountAppAMDataControl");
        MmSchmVOImpl v1 = am.getMmSchm();
        Row CurrRw = v1.getCurrentRow();
        MmSchmItmVOImpl v3 = am.getMmSchmItm1();
        MmSchmFreeItmVOImpl v4 = am.getMmSchmFreeItm1();

        Number paidItemTotSum = new Number(0);

        Number freeItemTotSum = new Number(0);


        RowSetIterator paidItm_RSI = v3.createRowSetIterator(null);
        while (paidItm_RSI.hasNext()) {
            Row rw = paidItm_RSI.next();


            paidItemTotSum =
                    paidItemTotSum.add(((Number)rw.getAttribute("ItmPrice")).multiply(((Number)rw.getAttribute("ItmQty"))));

        }
        paidItm_RSI.closeRowSetIterator();


        RowSetIterator freeItm_RSI = v4.createRowSetIterator(null);
        while (freeItm_RSI.hasNext()) {
            Row rw = freeItm_RSI.next();


            freeItemTotSum =
                    freeItemTotSum.add(((Number)rw.getAttribute("ItmPrice")).multiply(((Number)rw.getAttribute("ItmQty"))));

        }
        freeItm_RSI.closeRowSetIterator();


        //Row CurrRwFree=v4.getCurrentRow();
        //  Number itmPricePaid = (Number)CurrRwItm.getAttribute("ItmPrice");
        //  Number itmQtyPaid = (Number)CurrRwItm.getAttribute("ItmQty");
        //Number itmPriceFree = (Number)CurrRwFree.getAttribute("ItmPrice");
        //Number itmQtyFree = (Number)CurrRwFree.getAttribute("ItmQty");
        //Number totalPaidValue = itmPricePaid.multiply(itmQtyPaid);
        //Number totalFreeValue = itmPriceFree.multiply(itmQtyFree);
        //System.out.println("-----paid value "+totalPaidValue+"free value-----------"+totalFreeValue);
        //Number totalPaidValue = ((Number)itmPriceBind.getValue()).multiply((Number)qtyBind.getValue());

        System.out.println("paidItemTotSum---->" + paidItemTotSum);
        System.out.println("freeItemTotSum---->" + freeItemTotSum);
        if (paidItemTotSum.compareTo(freeItemTotSum) == -1 && (Integer)CurrRw.getAttribute("SchmType") == 178) {
            showPopup(valuePopUp, true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(freeProductPanelBind);
        } else {
            OperationBinding commBinding = executeBinding("Commit");
            commBinding.execute();
            if (commBinding.getErrors().isEmpty()) {

                String FocFlg = "N";
                if (CurrRw.getAttribute("SchmType") == null) {
                    FocFlg = "N";
                } else if ((Integer)CurrRw.getAttribute("SchmType") == 178) {
                    FocFlg = "Y";
                } else {
                    FocFlg = "N";
                }


                MmSchmFreeItmVOImpl v2 = am.getMmSchmFreeItm1();
                Row cRw = v2.getCurrentRow();
                cRw.setAttribute("FocFlg", FocFlg);
                commBinding.execute();
                OperationBinding exeBinding = executeBinding("Execute1");
                exeBinding.execute();

                if (ModeFreeItem.equals("A")) {
                    String Defaultmsg = resolvEl("#{bundle['MSG.89']}");
                    String valMsg = Defaultmsg.format(Defaultmsg, "", "", "", "", "");
                    FacesMessage msg = new FacesMessage(valMsg);
                    msg.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    ctx.addMessage(null, msg);
                } else if (ModeFreeItem.equals("E")) {
                    String Defaultmsg = resolvEl("#{bundle['MSG.90']}");
                    String valMsg = Defaultmsg.format(Defaultmsg, "", "", "", "", "");
                    FacesMessage msg = new FacesMessage(valMsg);
                    msg.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    ctx.addMessage(null, msg);
                }
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(freeItemTable);
            AdfFacesContext.getCurrentInstance().addPartialTarget(freeProductPanelBind);
            freeItemPopup.hide();

        }

    }


    public void cancelFreeitmPopup(ActionEvent actionEvent) {


        BindingContainer bindings = getBindings();

        DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("MmSchmIterator");
        Key parentKey = parentIter.getCurrentRow().getKey();

        OperationBinding deleteBinding = executeBinding("Rollback");
        deleteBinding.execute();
        if (parentKey != null) {
            parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(freeItemTable);
        freeItemPopup.hide();
    }

    public void discTypeVCL(ValueChangeEvent vce) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(discvalueBind);
        System.out.println("new value----: " + vce.getNewValue());
        System.out.println("bind new value----: " + discType.getValue());
        MMDiscountAppAMImpl am = (MMDiscountAppAMImpl)resolvElDC("MMDiscountAppAMDataControl");
        ViewObjectImpl vo = am.getMmSchmItm1();
        Number zero = new Number(0);
        Row rr = vo.getCurrentRow();
        rr.setAttribute("DiscVal", zero);
        discvalueBind.setValue(zero);
        AdfFacesContext.getCurrentInstance().addPartialTarget(discvalueBind);

    }

    public void setDiscvalueBind(RichInputText discvalueBind) {
        this.discvalueBind = discvalueBind;
    }

    public RichInputText getDiscvalueBind() {
        return discvalueBind;
    }

    public void setFreeValuepanelBind(RichPanelLabelAndMessage freeValuepanelBind) {
        this.freeValuepanelBind = freeValuepanelBind;
    }

    public RichPanelLabelAndMessage getFreeValuepanelBind() {
        return freeValuepanelBind;
    }

    public void setFormBind(RichPanelFormLayout formBind) {
        this.formBind = formBind;
    }

    public RichPanelFormLayout getFormBind() {
        return formBind;
    }

    public void setPaidProductValueBind(RichOutputText paidProductValueBind) {
        this.paidProductValueBind = paidProductValueBind;
    }

    public RichOutputText getPaidProductValueBind() {
        return paidProductValueBind;
    }

    public void setFreeProductValueBind(RichOutputText freeProductValueBind) {
        this.freeProductValueBind = freeProductValueBind;
    }

    public RichOutputText getFreeProductValueBind() {
        return freeProductValueBind;
    }

    public void setPaidProductpanelBind(RichPanelGroupLayout paidProductpanelBind) {
        this.paidProductpanelBind = paidProductpanelBind;
    }

    public RichPanelGroupLayout getPaidProductpanelBind() {
        return paidProductpanelBind;
    }

    public void setFreeProductPanelBind(RichPanelGroupLayout freeProductPanelBind) {
        this.freeProductPanelBind = freeProductPanelBind;
    }

    public RichPanelGroupLayout getFreeProductPanelBind() {
        return freeProductPanelBind;
    }

    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {
            MMDiscountAppAMImpl am = (MMDiscountAppAMImpl)resolvElDC("MMDiscountAppAMDataControl");
            st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 2, bindVars[z]);
                }
            }
            st.executeUpdate();

            return st.getObject(1);
        } catch (SQLException e) {
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*  public void fromDateDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
          Date currDt= (Date)Date.getCurrentDate();
          Date poDate=(Date)object;
          String pOrgId = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");

         java.sql.Date s=(java.sql.Date)callStoredFunction(Types.DATE,"APP.PKG_APP.GET_ORG_FY_START_DATE(?,?,?)", new Object[]{currDt,pOrgId,"FY"});
         Date strtDate=new Date(s);
         System.out.println("Start Date of FY:"+strtDate );
         if(strtDate!=null){
          if(poDate.compareTo(strtDate)==-1){
             String msg2 = resolvEl("#{bundle['MSG.267']}");
             FacesMessage message2 = new FacesMessage(msg2);
             message2.setDetail(resolvEl("#{bundle['MSG.268']}"));
             message2.setSeverity(FacesMessage.SEVERITY_ERROR);
             throw new ValidatorException(message2);
          }
         }
      }
     */

    public void fromDateDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Date currDt = (Date)Date.getCurrentDate();
        Date formDate = (Date)object;
        String pOrgId = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String pSloc = (String)resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}");
        Object oDt =
            callStoredFunction(Types.VARCHAR, "APP.PKG_APP.GET_FY_STAT_FOR_TXN(?,?,?)", new Object[] { pSloc, pOrgId,
                                                                                                       formDate });
        if (oDt == null) {
            String msg2 = "Financial Year is not defined.";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message2);
        } else if ("Y".equalsIgnoreCase(oDt.toString())) {
            //  java.sql.Date s =(java.sql.Date)oDt;
            //  Date strtDate = new Date(s);
            //System.out.println(s + "Start Date of FY:" + (Date)strtDate);
            SimpleDateFormat dt = new SimpleDateFormat("dd-mm-yyyy");

            /*    if (strtDate != null) {
                if (poDate.compareTo(strtDate) == -1) {
          */String msg2 = resolvEl("#{bundle['MSG.267']}");
            FacesMessage message2 = new FacesMessage(msg2);
            // message2.setDetail(resolvEl("#{bundle['MSG.463']}") + s);
            message2.setDetail(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message2);
        }
    }

    public void setOffmode(String offmode) {
        this.offmode = offmode;
    }

    public String getOffmode() {
        return offmode;
    }

    public String ItemDeleteAction() {
        MMDiscountAppAMImpl am = (MMDiscountAppAMImpl)resolvElDC("MMDiscountAppAMDataControl");
        ViewObjectImpl schmitmvo=am.getMmSchmItm1();
        ViewObjectImpl schmfreeitmvo=am.getMmSchmFreeItm1();
        System.out.println("-----"+schmitmvo.getEstimatedRowCount());
        System.out.println("-----"+schmfreeitmvo.getEstimatedRowCount());
        System.out.println("value  of schm typ bind is==="+schmTypBind.getValue());
        if((Integer)schmTypBind.getValue()!=177)
        { 
            if(schmitmvo.getEstimatedRowCount()==1&&schmfreeitmvo.getEstimatedRowCount()>0) {
                showPopup(delmsgpopup, true);
            }
            else
            {
            BindingContainer binding=getBindings();
        OperationBinding delbinding = executeBinding("Delete");
        delbinding.execute();
        OperationBinding commitbinding = executeBinding("Commit");
        commitbinding.execute();
        }
        
    }
        else {
            OperationBinding delbinding = executeBinding("Delete");
            delbinding.execute();
            OperationBinding commitbinding = executeBinding("Commit");
            commitbinding.execute();
        }
        return null;
    }

    public String freeItemDeleteAction() {
        OperationBinding delbinding = executeBinding("Delete1");
        delbinding.execute();
        OperationBinding commitbinding = executeBinding("Commit");
        commitbinding.execute();
        return null;
    }

    public void setSchmTypBind(RichSelectOneChoice schmTypBind) {
        this.schmTypBind = schmTypBind;
    }

    public RichSelectOneChoice getSchmTypBind() {
        return schmTypBind;
    }

    public void setDelmsgpopup(RichPopup delmsgpopup) {
        this.delmsgpopup = delmsgpopup;
    }

    public RichPopup getDelmsgpopup() {
        return delmsgpopup;
    }

    public void delmsgDilogoklistener(DialogEvent dialogEvent) {
        System.out.println("i am in dailog ok listemner----");
          BindingContainer bindings = getBindings();
        OperationBinding operationBinding =bindings.getOperationBinding("chkdeletecondition");
        operationBinding.execute(); 
        AdfFacesContext.getCurrentInstance().addPartialTarget(freeItemTable);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemTable);
           }

    public void delmsgpopupCancelList(PopupCanceledEvent popupCanceledEvent) {
    /*     BindingContainer bindings = getBindings();
        OperationBinding operationBinding =bindings.getOperationBinding("Rollback");
        operationBinding.execute(); */



       /*  OperationBinding rolback = executeBinding("Rollback");
        rolback.execute(); */
    }
}
