package svcdefectsapp.view.beans;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class SvcDefectsBean {
    String mode = "V";
    String modeDefect = "V";
    private static ADFLogger adflog = ADFLogger.createADFLogger(SvcDefectsBean.class);
    private RichPanelGroupLayout searchPanelR;
    private RichTable tableBind;
    private RichInputText dfctTypeNmBind;
    private RichInputText dfctNmBind;

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setModeDefect(String modeDefect) {
        this.modeDefect = modeDefect;
    }

    public String getModeDefect() {
        return modeDefect;
    }


    public SvcDefectsBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
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

    /**
     *      Method to show validation message(I,E,W)
     *      mesg:Message to display
     *      sev:Severity(I,E,W)
     *      chk:true=if resource bundle is used
     *      typFlg: 'F' for FM , 'V' for VE
     *      clientId : client id for UI component
     * */
    public void showFacesMessage(String mesg, String sev, Boolean chk, String typFlg, String clientId) {
        FacesMessage message = new FacesMessage(mesg);
        if (chk == true) {
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

    public void create(ActionEvent actionEvent) {
        OperationBinding createOb = getBindings().getOperationBinding("CreateInsert");
        createOb.execute();
        setMode("A");
    }

    public void edit(ActionEvent actionEvent) {
        setMode("E");
        // Add event code here...
    }


    public String cancelAction() {
        OperationBinding rollbackOb = getBindings().getOperationBinding("Rollback");
        rollbackOb.execute();
        setMode("V");
        setModeDefect("V");
        return null;
    }

    public void createDefectAction(ActionEvent actionEvent) {
        if (dfctTypeNmBind.getValue() != null) {
            OperationBinding defct = getBindings().getOperationBinding("duplicateDfectType");
            defct.getParamsMap().put("nm", dfctTypeNmBind.getValue().toString());
            defct.execute();
            adflog.info("result:" + defct.getResult());
            if (defct.getResult() != null) {
                if ("Y".equalsIgnoreCase(defct.getResult().toString())) {
                    showFacesMessage("MSG.46", "E", true, "F", dfctTypeNmBind.getClientId());
                    return;
                } else {
                    adflog.info("not dupplicate");
                }
            }
        }
        OperationBinding createDOb = getBindings().getOperationBinding("CreateInsert1");
        createDOb.execute();
        setModeDefect("A");
    }

    public void editDefectAction(ActionEvent actionEvent) {
        setModeDefect("E");
    }

    public void deleteTypeAction(ActionEvent actionEvent) {
        OperationBinding deleteOb = getBindings().getOperationBinding("Delete");
        deleteOb.execute();
        OperationBinding saveOb = getBindings().getOperationBinding("Commit");
        saveOb.execute();
    }

    public String deleteDefectAction() {
        OperationBinding deleteDOb = getBindings().getOperationBinding("Delete1");
        deleteDOb.execute();
        return null;
    }


    public String searchAction() {
        OperationBinding searchBind = getBindings().getOperationBinding("searchDfctAction");
        searchBind.execute();
        return null;
    }

    public String resetAction() {
        AdfFacesContext fctx = AdfFacesContext.getCurrentInstance();
        resetValueInputItems(fctx, searchPanelR);
        OperationBinding searchBind = getBindings().getOperationBinding("resetAction");
        searchBind.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tableBind);
        return null;
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

    public void setSearchPanelR(RichPanelGroupLayout searchPanelR) {
        this.searchPanelR = searchPanelR;
    }

    public RichPanelGroupLayout getSearchPanelR() {
        return searchPanelR;
    }

    public void setTableBind(RichTable tableBind) {
        this.tableBind = tableBind;
    }

    public RichTable getTableBind() {
        return tableBind;
    }

    public void setDfctTypeNmBind(RichInputText dfctTypeNmBind) {
        this.dfctTypeNmBind = dfctTypeNmBind;
    }

    public RichInputText getDfctTypeNmBind() {
        return dfctTypeNmBind;
    }


    public void saveActionNew(ActionEvent actionEvent) {
        if (dfctTypeNmBind.getValue() != null) {
            OperationBinding defct = getBindings().getOperationBinding("duplicateDfectType");
            defct.getParamsMap().put("nm", dfctTypeNmBind.getValue().toString());
            defct.execute();
            adflog.info("result:" + defct.getResult());
            if (defct.getResult() != null) {
                if ("Y".equalsIgnoreCase(defct.getResult().toString())) {
                    showFacesMessage("MSG.46", "E", true, "F", dfctTypeNmBind.getClientId());
                    return;
                } else {
                    adflog.info("not dupplicate");
                }
            }
        }
        if (dfctNmBind.getValue() != null) {
            OperationBinding defct1 = getBindings().getOperationBinding("duplicateDfectNm");
            defct1.getParamsMap().put("nm", dfctNmBind.getValue().toString());
            defct1.execute();
            adflog.info("result:" + defct1.getResult());
            if (defct1.getResult() != null) {
                if ("Y".equalsIgnoreCase(defct1.getResult().toString())) {
                    showFacesMessage("MSG.46", "E", true, "F", dfctNmBind.getClientId());
                    return;
                } else {
                    adflog.info("not dupplicate");
                }
            }
        }

        OperationBinding saveOb = getBindings().getOperationBinding("Commit");
        saveOb.execute();
        showFacesMessage("MSG.1599", "S", true, "I", null);
        setMode("V");
        setModeDefect("V");
    }

    public void dfcttypeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        String msg2 = "";
        String BinNm = null;
        if (object != null) {
            BinNm = object.toString();
            if (BinNm.startsWith(" ") || BinNm.endsWith(" ")) {
                showFacesMessage("MSG.480", "E", true, "V", dfctTypeNmBind.getClientId());
                //                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                //                                                              resolvElDCMsg("#{bundle['At start and end Space not allowed']}").toString(), null));
            }

            int openB = 0;
            int closeB = 0;
            boolean closeFg = false;
            char[] xx = BinNm.toCharArray();
            for (char c : xx) {
                if (c == '(') {
                    openB = openB + 1;
                } else if (c == ')') {
                    closeB = closeB + 1;
                }

                if (closeB > openB) {
                    closeFg = true; //closed brackets will not be more than open brackets at any given time.
                }
            }


            /**if openB=0 then no. of closing and opening brackets equal || opening bracket must always come before closing brackets
            /*closing brackets must not come before first occurrence of openning bracket
            **/
            if (openB != closeB || closeFg == true || (BinNm.lastIndexOf("(") > BinNm.lastIndexOf(")")) ||
                (BinNm.indexOf(")") < BinNm.indexOf("("))) {

                showFacesMessage("MSG.1600", "E", true, "V", dfctTypeNmBind.getClientId());
                //                msg2 = resolvElDCMsg("#{bundle['Delivery Note Quantity cannot be less than zero.']}").toString();
                //                FacesMessage message2 = new FacesMessage(msg2);
                //                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                //                throw new ValidatorException(message2);
            }
            if (BinNm.contains("()")) {
                showFacesMessage("MSG.170", "E", true, "V", dfctTypeNmBind.getClientId());
                //                msg2 = resolvElDCMsg("#{bundle['Empty Brackets are not allowed.']}").toString();
                //                FacesMessage message2 = new FacesMessage(msg2);
                //                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                //                throw new ValidatorException(message2);
            }
            if (BinNm.contains("(.") || BinNm.contains("(-") || BinNm.contains("-)")) {
                showFacesMessage("MSG.1601", "E", true, "V", dfctTypeNmBind.getClientId());
                //                msg2 = resolvElDCMsg("#{bundle['Invalid Bin name. Check content inside brackets.']}").toString();
                //                FacesMessage message2 = new FacesMessage(msg2);
                //                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                //                throw new ValidatorException(message2);
            }

            openB = 0;
            closeB = 0;
            closeFg = false;


            /**check for valid Bin name.Allowed- brackets,dots,hyphen
             * */
            String expression =
                "^(?:(?>[A-Za-z0-9 \\(\\)]+)(\\.|\\-(?!\\.|\\-|$))*(\\.|\\:(?!\\.|\\:|$))*" +
                "(\\.|\\/(?!\\.|\\/|$))*(\\.|\\;(?!\\.|\\;|$))*(\\.|\\\\(?!\\.|\\\\|$))*(\\.|\\,(?!\\.|\\,|$))?)*$";

            //  String expression = "^(?:(?>[A-Za-z0-9 )]+)(?:\\_|\\-|\\(|\\.|\\)|\\:|\\#|\\/|\\\\(?!\\_|\\-|\\:|\\#|\\/|\\\\|$))?)*$";
            //  String expression = "^(?:(?>[A-Za-z \\(\\)]+)(\\.|\\-(?!\\.|\\-|$))?)*$";
            CharSequence inputStr = BinNm;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            // String error = resolvElDCMsg("#{bundle['Invalid Bin name. Check content inside brackets.']}").toString();

            if (matcher.matches()) {
            } else {
                showFacesMessage("MSG.199", "E", true, "V", dfctTypeNmBind.getClientId());
                //                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                //                                                              resolvElDCMsg("#{bundle['Only Alphanumeric value and / - _ # : . ( ) and two consecutive special not allowed']}").toString()));
            }


        }


        if (dfctTypeNmBind.getValue() != null) {
            OperationBinding defct = getBindings().getOperationBinding("duplicateDfectType");
            defct.getParamsMap().put("nm", dfctTypeNmBind.getValue().toString());
            defct.execute();
            adflog.info("result:" + defct.getResult());
            if (defct.getResult() != null) {
                if ("Y".equalsIgnoreCase(defct.getResult().toString())) {
                    showFacesMessage("MSG.46", "E", true, "F", dfctTypeNmBind.getClientId());
                    return;
                } else {
                    adflog.info("not dupplicate");
                }
            }
        }
    }

    public void dfctNmValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...

        String msg2 = "";
        String BinNm = null;
        if (object != null) {
            BinNm = object.toString();
            if (BinNm.startsWith(" ") || BinNm.endsWith(" ")) {
                showFacesMessage("MSG.480", "E", true, "V", dfctNmBind.getClientId());
                //                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                //                                                              resolvElDCMsg("#{bundle['At start and end Space not allowed']}").toString(), null));
            }

            int openB = 0;
            int closeB = 0;
            boolean closeFg = false;
            char[] xx = BinNm.toCharArray();
            for (char c : xx) {
                if (c == '(') {
                    openB = openB + 1;
                } else if (c == ')') {
                    closeB = closeB + 1;
                }

                if (closeB > openB) {
                    closeFg = true; //closed brackets will not be more than open brackets at any given time.
                }
            }


            /**if openB=0 then no. of closing and opening brackets equal || opening bracket must always come before closing brackets
            /*closing brackets must not come before first occurrence of openning bracket
            **/
            if (openB != closeB || closeFg == true || (BinNm.lastIndexOf("(") > BinNm.lastIndexOf(")")) ||
                (BinNm.indexOf(")") < BinNm.indexOf("("))) {

                showFacesMessage("MSG.1602", "E", true, "V", dfctNmBind.getClientId());
                //                msg2 = resolvElDCMsg("#{bundle['Delivery Note Quantity cannot be less than zero.']}").toString();
                //                FacesMessage message2 = new FacesMessage(msg2);
                //                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                //                throw new ValidatorException(message2);
            }
            if (BinNm.contains("()")) {
                showFacesMessage("MSG.170", "E", true, "V", dfctNmBind.getClientId());
                //                msg2 = resolvElDCMsg("#{bundle['Empty Brackets are not allowed.']}").toString();
                //                FacesMessage message2 = new FacesMessage(msg2);
                //                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                //                throw new ValidatorException(message2);
            }
            if (BinNm.contains("(.") || BinNm.contains("(-") || BinNm.contains("-)")) {
                showFacesMessage("MSG.1601", "E", true, "V", dfctNmBind.getClientId());
                //                msg2 = resolvElDCMsg("#{bundle['Invalid Bin name. Check content inside brackets.']}").toString();
                //                FacesMessage message2 = new FacesMessage(msg2);
                //                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                //                throw new ValidatorException(message2);
            }

            openB = 0;
            closeB = 0;
            closeFg = false;


            /**check for valid Bin name.Allowed- brackets,dots,hyphen
             * */
            String expression =
                "^(?:(?>[A-Za-z0-9 \\(\\)]+)(\\.|\\-(?!\\.|\\-|$))*(\\.|\\:(?!\\.|\\:|$))*" +
                "(\\.|\\/(?!\\.|\\/|$))*(\\.|\\;(?!\\.|\\;|$))*(\\.|\\\\(?!\\.|\\\\|$))*(\\.|\\,(?!\\.|\\,|$))?)*$";

            //  String expression = "^(?:(?>[A-Za-z0-9 )]+)(?:\\_|\\-|\\(|\\.|\\)|\\:|\\#|\\/|\\\\(?!\\_|\\-|\\:|\\#|\\/|\\\\|$))?)*$";
            //  String expression = "^(?:(?>[A-Za-z \\(\\)]+)(\\.|\\-(?!\\.|\\-|$))?)*$";
            CharSequence inputStr = BinNm;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            // String error = resolvElDCMsg("#{bundle['Invalid Bin name. Check content inside brackets.']}").toString();

            if (matcher.matches()) {
            } else {
                showFacesMessage("MSG.199", "E", true, "V", dfctNmBind.getClientId());
                //                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                //                                                              resolvElDCMsg("#{bundle['Only Alphanumeric value and / - _ # : . ( ) and two consecutive special not allowed']}").toString()));
            }


        }

    }

    public void setDfctNmBind(RichInputText dfctNmBind) {
        this.dfctNmBind = dfctNmBind;
    }

    public RichInputText getDfctNmBind() {
        return dfctNmBind;
    }

    public void saveDefect(ActionEvent actionEvent) {
        if (dfctTypeNmBind.getValue() != null) {
            OperationBinding defct = getBindings().getOperationBinding("duplicateDfectType");
            defct.getParamsMap().put("nm", dfctTypeNmBind.getValue().toString());
            defct.execute();
            adflog.info("result:" + defct.getResult());
            if (defct.getResult() != null) {
                if ("Y".equalsIgnoreCase(defct.getResult().toString())) {
                    showFacesMessage("MSG.46", "E", true, "F", dfctTypeNmBind.getClientId());
                    return;
                } else {
                    adflog.info("not dupplicate");
                }
            }
        }
        if (dfctNmBind.getValue() != null) {
            OperationBinding defct1 = getBindings().getOperationBinding("duplicateDfectNm");
            defct1.getParamsMap().put("nm", dfctNmBind.getValue().toString());
            defct1.execute();
            adflog.info("result:" + defct1.getResult());
            if (defct1.getResult() != null) {
                if ("Y".equalsIgnoreCase(defct1.getResult().toString())) {
                    showFacesMessage("MSG.46", "E", true, "F", dfctNmBind.getClientId());
                    return;
                } else {
                    adflog.info("not dupplicate");
                }
            }
        }
        OperationBinding saveOb = getBindings().getOperationBinding("Commit");
        saveOb.execute();
        showFacesMessage("MSG.1599", "S", true, "I", null);
        setMode("V");
        setModeDefect("V");
    }
}
