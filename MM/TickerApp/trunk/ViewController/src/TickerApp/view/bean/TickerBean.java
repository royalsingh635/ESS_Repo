package TickerApp.view.bean;

import TickerApp.model.module.TickerAppAMImpl;

import adf.utils.model.ADFModelUtils;

import java.util.List;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.context.RequestContext;

public class TickerBean {
    private String mode = "V";
    private RichSelectOneChoice userNmBinding;
    private RichPanelGroupLayout searchPannel;
    private RichTable searchTable;
    private RichInputText tickrNmBind;
    private RichSelectOneChoice slectTckerBind;
    public String keymode = "V";
    private RichInputText tickernameBinding;
    private RichSelectOneChoice dataRangeBind;
    private RichInputText daysBind;
    private RichInputText tickerIdBinding;

    public TickerBean() {
        Map paramMap = RequestContext.getCurrentInstance().getPageFlowScope();
        paramMap.put("GLBL_LOVSWITCH_PARAM", "V");
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

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void addTickerAL(ActionEvent actionEvent) {
        keymode = "A";
        BindingContainer bindings = getBindings();
        /*  DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("MmTkr1Iterator");
        if(parentIter.getCurrentRow()!=null)
           key = parentIter.getCurrentRow().getKey().toString(); */
        getBindings().getOperationBinding("CreateInsert1").execute();
        Map paramMap = RequestContext.getCurrentInstance().getPageFlowScope();
        paramMap.put("GLBL_LOVSWITCH_PARAM", "A");
        System.out.println("add btn value" + resolvEl("#{pageFlowScope.GLBL_LOVSWITCH_PARAM}"));

        this.mode = "A";

    }

    public void editTickerBtnAL(ActionEvent actionEvent) {
        keymode = "E";
        /* BindingContainer bindings=getBindings();
        DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("MmTkr1Iterator");
        if(parentIter.getCurrentRow()!=null)
           key = parentIter.getCurrentRow().getKey().toString(); */
        Map paramMap = RequestContext.getCurrentInstance().getPageFlowScope();
        paramMap.put("GLBL_LOVSWITCH_PARAM", "E");
        System.out.println("edit btn value--" + resolvEl("#{pageFlowScope.GLBL_LOVSWITCH_PARAM}"));
        this.mode = "E";
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setValueInTkrAL(ActionEvent actionEvent) {
        System.out.println("inside OK btn ");
        System.out.println("value of sletc tivkker bnd is===" + slectTckerBind.getValue());
        if (slectTckerBind.getValue() == null || slectTckerBind.getValue() == "") {
            System.out.println("-------------------------");
//            Please select Ticker
            String s=ADFModelUtils.resolvRsrc("MSG.2324");
            FacesMessage message = new FacesMessage(s);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(slectTckerBind.getClientId(), message);

        }

        else {
            if (mode == "A") {
                System.out.println("while click on add");
                getBindings().getOperationBinding("setValuesInMmTkr").execute();
                this.mode = "set";
            }
            if (mode == "E") {
                System.out.println("while click on edit");
                getBindings().getOperationBinding("setTkronEdit").execute();
                this.mode = "set";
            }
        }
    }

    /**
     *      Method to show validation message(I,E,W)
     *      mesg:Message to display
     *      sev:Severity(I,E,W)
     *      chk:true=if resource bundle is used
     *      typFlg: 'F' for FM , 'V' for VE
     *      clientId : client id for UI component
     * */
    public void showFacesMessage(String mesg, String sev, Boolean chk, String typFlg, String clientId) {
        System.out.println("messge is" +mesg);
    
        FacesMessage message = new FacesMessage(mesg);
        if (chk == true) {
            String s=ADFModelUtils.resolvRsrc(mesg);
             message = new FacesMessage(s);
            
            String msg = resolvEl("#{bundle['" + mesg + "']}");
            message = new FacesMessage(msg);
        }
        if (sev.equalsIgnoreCase("E")) {
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
        } else if (sev.equalsIgnoreCase("W")) {
            message.setSeverity(FacesMessage.SEVERITY_WARN);
        } else if (sev.equalsIgnoreCase("I")) {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
        } else {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
        }
        if (typFlg.equals("F")) {
            FacesContext.getCurrentInstance().addMessage(clientId, message);
        } else if (typFlg.equals("V")) {
            throw new ValidatorException(message);
        }
    }


    public void saveBtnAL(ActionEvent actionEvent) {
        userNmBinding.setValue(null);
        userNmBinding.setValue("");
        slectTckerBind.setValue(null);
        slectTckerBind.setValue("");
         OperationBinding op=  getBindings().getOperationBinding("chkTkrId");
        op.execute(); 
        
        if(op.getResult()!=null){
            if((Integer)op.getResult()==1) {
                
            }else{
//                Ticker Id is required
                String s=ADFModelUtils.resolvRsrc("MSG.2325");
                FacesMessage message = new FacesMessage(s);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
                return;
            }
        }
        
        if (tickernameBinding.getValue() == null || tickernameBinding.getValue() == "") {
//            Please Enter Ticker Name!
            String s=ADFModelUtils.resolvRsrc("MSG.2324");
            FacesMessage message = new FacesMessage(s);
//            FacesMessage message = new FacesMessage("Please Enter Ticker Name!");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(tickernameBinding.getClientId(), message);
        } else {

            BindingContainer bindings = getBindings();
            DCIteratorBinding parentIter = (DCIteratorBinding) bindings.get("MmTkr1Iterator");
            System.out.println("Parent Iterator=" + parentIter.getCurrentRow());


            if (parentIter.getCurrentRow() != null) {
                Key parentKey = parentIter.getCurrentRow().getKey();
                System.out.println("Parent Key=" + parentKey);
                Object obj = parentIter.getCurrentRow().getAttribute("DataRangeType");
                Object obj1 = parentIter.getCurrentRow().getAttribute("DataRangeDays");


                System.out.println("Object value::" + obj);
                if (obj == null) {
//                    Data Range is required 
                    showFacesMessage("MSG.2327 ", "E", true, "F", dataRangeBind.getClientId());
                    return;
                }
                Integer r1 = Integer.parseInt(obj.toString());

                if (r1 == 505) {
                    if (obj1 == null) {
//                        No.of Days is required
                        showFacesMessage("MSG.2328", "E", false, "F", daysBind.getClientId());
                        return;
                    }


                }

                AdfFacesContext.getCurrentInstance().addPartialTarget(userNmBinding);
                OperationBinding opr = getBindings().getOperationBinding("Commit");
                opr.execute();
                if (opr.getErrors() != null)
                    getBindings().getOperationBinding("Rollback").execute();
                getBindings().getOperationBinding("refreshOnSave").execute();
                this.mode = "V";
                System.out.println(opr.getErrors().size());
                String s=ADFModelUtils.resolvRsrc("MSG.818");
                FacesMessage message = new FacesMessage(s); //"Record Saved Successfully!"
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
                System.out.println("Parent Interator value" + parentIter.getEstimatedRowCount());
                if (parentIter.getEstimatedRowCount() > 1) {
                    //System.out.println();
                    parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                }


            }
        }

    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public void deleteUserTblRowAL(ActionEvent actionEvent) {
        getBindings().getOperationBinding("deleteUserRow").execute();

    }

    public void addUserToTkrAL(ActionEvent actionEvent) {
        System.out.println("inside add user btn");
        if (userNmBinding.getValue() != null && userNmBinding.getValue() != "") {
            String s=ADFModelUtils.resolvRsrc("MSG.1236");
//            Duplicate UserName Not Allowed, Please Select Unique.
//            String msg = resolvElDCMsg("#{bundle['MSG.1236']}").toString();
            System.out.println("usernam value--" + userNmBinding.getValue());
            Integer id = Integer.parseInt(userNmBinding.getValue().toString());
            OperationBinding op = getBindings().getOperationBinding("usrNmValidator");
            op.getParamsMap().put("Id", id);
            op.execute();
            if (op.getErrors().isEmpty() && op.getResult() != null) {
                String rslt = op.getResult().toString();
                if (rslt.equalsIgnoreCase("Y")) {
                    FacesMessage message = new FacesMessage(s);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(userNmBinding.getClientId(), message);
                } else {
                    getBindings().getOperationBinding("addUserToTkr").execute();
                }
            }
        }

        this.mode = "E";
    }


    public void cancelBtnAL(ActionEvent actionEvent) {
        BindingContainer binding = getBindings();
        DCIteratorBinding parentIter = (DCIteratorBinding) binding.get("MmTkr1Iterator");
        if (parentIter.getCurrentRow() != null && keymode != "A") {
            Key parentKey = parentIter.getCurrentRow().getKey();
            getBindings().getOperationBinding("Rollback").execute();
            getBindings().getOperationBinding("refreshOnCancel").execute();
            parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
        } else {
            getBindings().getOperationBinding("Rollback").execute();
            getBindings().getOperationBinding("refreshOnCancel").execute();
        }
        this.mode = "V";
    }

    public void setUserNmBinding(RichSelectOneChoice userNmBinding) {
        this.userNmBinding = userNmBinding;
    }

    public RichSelectOneChoice getUserNmBinding() {
        return userNmBinding;
    }

    public void tckrSearchAction(ActionEvent actionEvent) {
        TickerAppAMImpl am = (TickerAppAMImpl) resolvElDC("TickerAppAMDataControl");
        ViewObjectImpl vo = am.getMmTkr1();
        vo.setNamedWhereClauseParam("BINDCLDID", resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}"));
        vo.setNamedWhereClauseParam("BINDORGID", resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}"));
        vo.setNamedWhereClauseParam("BINDSLOCID", Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}")));
        vo.setNamedWhereClauseParam("BindTckrNm", tickrNmBind.getValue());
        vo.executeQuery();
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

    public void ResetAction(ActionEvent actionEvent) {


        AdfFacesContext fctx = AdfFacesContext.getCurrentInstance();
        resetValueInputItems(fctx, searchPannel);
        TickerAppAMImpl am = (TickerAppAMImpl) resolvElDC("TickerAppAMDataControl");
        ViewObjectImpl vo = am.getMmTkr1();
        vo.setNamedWhereClauseParam("BINDCLDID", resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}"));
        vo.setNamedWhereClauseParam("BINDORGID", resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}"));
        vo.setNamedWhereClauseParam("BINDSLOCID", Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}")));
        vo.setNamedWhereClauseParam("BindTckrNm", null);
        vo.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchTable);
    }

    private void resetValueInputItems(AdfFacesContext adfFacesContext, UIComponent component) {
        List<UIComponent> items = component.getChildren();
        for (UIComponent item : items) {

            resetValueInputItems(adfFacesContext, item);

            if (item instanceof RichInputText) {
                RichInputText input = (RichInputText) item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            } else if (item instanceof RichInputDate) {
                RichInputDate input = (RichInputDate) item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            } else if (item instanceof RichSelectOneChoice) {
                RichSelectOneChoice input = (RichSelectOneChoice) item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            } else if (item instanceof RichInputListOfValues) {
                RichInputListOfValues input = (RichInputListOfValues) item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            }

        }
    }

    public void setSearchPannel(RichPanelGroupLayout searchPannel) {
        this.searchPannel = searchPannel;
    }

    public RichPanelGroupLayout getSearchPannel() {
        return searchPannel;
    }

    public void setSearchTable(RichTable searchTable) {
        this.searchTable = searchTable;
    }

    public RichTable getSearchTable() {
        return searchTable;
    }

    public void setTickrNmBind(RichInputText tickrNmBind) {
        this.tickrNmBind = tickrNmBind;
    }

    public RichInputText getTickrNmBind() {
        return tickrNmBind;
    }

    public void setSlectTckerBind(RichSelectOneChoice slectTckerBind) {
        this.slectTckerBind = slectTckerBind;
    }

    public RichSelectOneChoice getSlectTckerBind() {
        return slectTckerBind;
    }

    public void setKeymode(String keymode) {
        this.keymode = keymode;
    }

    public String getKeymode() {
        return keymode;
    }

    public void setTickernameBinding(RichInputText tickernameBinding) {
        this.tickernameBinding = tickernameBinding;
    }

    public RichInputText getTickernameBinding() {
        return tickernameBinding;
    }

    public void setDataRangeBind(RichSelectOneChoice dataRangeBind) {
        this.dataRangeBind = dataRangeBind;
    }

    public RichSelectOneChoice getDataRangeBind() {
        return dataRangeBind;
    }

    public void setDaysBind(RichInputText daysBind) {
        this.daysBind = daysBind;
    }

    public RichInputText getDaysBind() {
        return daysBind;
    }

    public void daysValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        Integer value = (Integer) object;


        if (value != null) {
            if (value.compareTo(0) < 0) {
//             Days cannot be negative."
                String s=ADFModelUtils.resolvRsrc("MSG.2326");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, s,
                                                              null));
            }
        }

    }

    public void setTickerIdBinding(RichInputText tickerIdBinding) {
        this.tickerIdBinding = tickerIdBinding;
    }

    public RichInputText getTickerIdBinding() {
        return tickerIdBinding;
    }

    public void tickerIdValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        String value = (String) object;
        if (value == null) {
            this.setMode("TV");

            //            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
            //                                                          "Please click on Tick Mark got Ticker Id", null));
            //        }


        }
    }
}
