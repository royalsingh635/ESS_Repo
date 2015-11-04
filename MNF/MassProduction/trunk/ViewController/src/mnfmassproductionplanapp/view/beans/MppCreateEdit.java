package mnfmassproductionplanapp.view.beans;

import java.util.ArrayList;

import javax.el.ELContext;
import javax.el.ValueExpression;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import javax.faces.validator.ValidatorException;

import mnfmassproductionplanapp.view.utils.ADFUtils;
import mnfmassproductionplanapp.view.utils.JSFUtils;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.output.RichOutputLabel;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;

import oracle.jbo.rules.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.component.UIXSwitcher;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;

public class MppCreateEdit {
    // value for mode V : view, C : create, E : edit
    private StringBuffer mode = new StringBuffer(JSFUtils.resolveExpressionAsString("#{pageFlowScope.MPP_MODE}"));

    //production P, operation O, Summary S
    protected StringBuffer switchMode =

        new StringBuffer(JSFUtils.resolveExpressionAsString("#{pageFlowScope.MPP_SWITCH_MODE}"));
    protected String WfId;
    private UIXSwitcher mppSwitcher;
    private RichTable inputItemTableBInd;


    private RichInputListOfValues itemNameBind;
    private String searchValue = "";
    private Key key;
    private RichOutputText itemIdBind;
    private RichInputListOfValues outputItemName;
    private RichTable outputItemTableBind;
    private RichOutputLabel outputItemIdBind;
    private RichOutputText outputItemBind;
    private RichOutputText inputItemBind;
    private RichTable inputItemTableBind;
    private RichSelectOneChoice jobRouteCardBind;
    private RichPopup jcRcGenPopBind;
    private RichSelectOneChoice gppStatusBindVar;
    private RichInputText remarkBindVar;
    private RichInputListOfValues warehouseBindVar;
    private RichInputText totalQuantityBindVar;
    private RichSelectOneChoice outputItmBindVar;
    private RichInputListOfValues operationNameBindVar;
    private RichInputText oprSrNoBindVar;
    private RichInputText balanceQuantityBinding;
    private RichOutputText tmpBalQtyBinding;
    private RichInputText chckBalQtyBinding;
    private RichSelectOneChoice gppModeBindvar;

    public void setWfId(String WfId) {
        this.WfId = WfId;
    }

    public String getWfId() {
        return WfId;
    }

    public void setMode(StringBuffer mode) {
        this.mode = mode;
    }

    public StringBuffer getMode() {
        return mode;
    }

    public void setSwitchMode(StringBuffer switchMode) {
        this.switchMode = switchMode;
    }

    public StringBuffer getSwitchMode() {
        return switchMode;
    }

    public MppCreateEdit() {
    }

    public void switchToProdModeACE(ActionEvent actionEvent) {

    }

    public void backToOperationACE(ActionEvent actionEvent) {


        OperationBinding ob = ADFUtils.findOperation("resetStatus");
        ob.getParamsMap().put("opr_doc_id", getAttrsVal("MnfMppOp1Iterator", "OpId"));
        ob.getParamsMap().put("opr_sr_no", getAttrsVal("MnfMppOp1Iterator", "OpSrNo"));
        ob.getParamsMap().put("gpp_doc_id", getAttrsVal("MnfMppOp1Iterator", "DocId"));

        ob.execute();
        setSwitchMode(new StringBuffer("O"));
        ADFUtils.findOperation("reSetPdoStatus").execute();
        ADFUtils.findOperation("operationHistory").execute();
        mppSwitcher.setFacetName("OperationSearchView");
        // ADFUtils.findOperation("getCommonOperation").execute();
        //ADFUtils.findOperation("deleteRowforSummary").execute();
        //ADFUtils.findOperation("removeItem").execute();
        //        if(mode.equals("E")){
        //            System.out.println("navigating back to operation in edit mode");
        //        }

    }

    public void backToProductionACE(ActionEvent actionEvent) {

        //ADFUtils.findOperation("clearPreviousSelectedOpr").execute();
        //ADFUtils.findOperation("clearSingleOperationDetails").execute();
        //ADFUtils.findOperation("showHistory").execute();
        setSwitchMode(new StringBuffer("P"));
        mppSwitcher.setFacetName("PdoSearchView");
    }

    public void switchToOperationACE(ActionEvent actionEvent) {
        OperationBinding binding = ADFUtils.findOperation("getPdoRowCount");
        Object execute = binding.execute();
        Integer result = (Integer) binding.getResult();
        if (result.compareTo(1) == 1) {
            setSwitchMode(new StringBuffer("O"));
            ADFUtils.findOperation("pdoHistory").execute();
            ADFUtils.findOperation("getCommonOperation").execute();
            ADFUtils.findOperation("operationHistory").execute();
            ADFUtils.findOperation("setBindValuesToView").execute();

            mppSwitcher.setFacetName("OperationSearchView");

        } else {
            JSFUtils.addFacesErrorMessage("Please select minimum 2 Production Order for further processing!");
        }

    }

    public void switchToSummaryACE(ActionEvent actionEvent) {
        OperationBinding binding1 = ADFUtils.findOperation("getOprRowCount");
        Object execute = binding1.execute();
        Integer result = (Integer) binding1.getResult();
        if (result == 1) {
            ADFUtils.findOperation("deleteRowforSummary").execute();
            ADFUtils.findOperation("removeItem").execute();
            ADFUtils.findOperation("showSummary1").execute();
            mppSwitcher.setFacetName("SummaryMppView");
            setSwitchMode(new StringBuffer("S"));
        } else {
            JSFUtils.addFacesErrorMessage("Please select at-least one Operation to create GPP!");
        }
    }

    public void createNewMppACE(ActionEvent actionEvent) {
        setSwitchMode(new StringBuffer("P"));
        setMode(new StringBuffer("C"));
        ADFUtils.findOperation("CreateWithParams").execute();
    }

    public String cancelAction() {
        ADFUtils.findOperation("Rollback").execute();
        setMode(new StringBuffer("V"));
        return "returnSearch";
    }

    public void saveMppACE(ActionEvent actionEvent) {
        //ADFUtils.findOperation("Commit").execute();

        OperationBinding ob = ADFUtils.findOperation("setStatus");
        ob.getParamsMap().put("opr_doc_id", getAttrsVal("MnfMppOp1Iterator", "OpId"));
        ob.getParamsMap().put("opr_sr_no", getAttrsVal("MnfMppOp1Iterator", "OpSrNo"));
        ob.getParamsMap().put("gpp_doc_id", getAttrsVal("MnfMppOp1Iterator", "DocId"));

        ob.execute();

        callFuncWf();
        //  ADFUtils.findOperation("setPdoStatus").execute();
        ADFUtils.findOperation("Commit").execute();
        JSFUtils.addFacesInformationMessage("Record Saved Successfully !");
        setMode(new StringBuffer("V"));

    }

    public void saveAndForwardMppACE(ActionEvent actionEvent) {
        //ADFUtils.findOperation("setPdoStatus").execute();
        OperationBinding ob = ADFUtils.findOperation("setStatus");
        ob.getParamsMap().put("opr_doc_id", getAttrsVal("MnfMppOp1Iterator", "OpId"));
        ob.getParamsMap().put("opr_sr_no", getAttrsVal("MnfMppOp1Iterator", "OpSrNo"));
        ob.getParamsMap().put("gpp_doc_id", getAttrsVal("MnfMppOp1Iterator", "DocId"));

        ob.execute();

        callFuncWf();
        ADFUtils.findOperation("Commit").execute();
        //JSFUtils.addFacesInformationMessage("Record Saved Sucessfully!");
        setMode(new StringBuffer("V"));

        //ADFUtils.findOperation("Commit").execute();
    }

    public void editMppACE(ActionEvent actionEvent) {
        Integer usr_Id = getUsrId();
        Integer pending = 0;
        OperationBinding ob = ADFUtils.findOperation("getDocUsrFromWF");
        ob.execute();
        Integer chkUsr = (Integer) ob.getResult();
        if (chkUsr != null) {
            pending = chkUsr;
        }
        if (pending.compareTo(usr_Id) == 0) {
            //ADFUtils.findOperation("removeItem").execute();
            setMode(new StringBuffer("E"));
            // setMode(new StringBuffer("S"));

        } else if (pending.compareTo(new Integer(-1)) == 0) {
            StringBuffer Msg22 = new StringBuffer("GPP Document has been Approved, You can not edit this Document");
            FacesMessage message2 = new FacesMessage(Msg22.toString());
            message2.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
        } else {
            ob = ADFUtils.findOperation("getUserName");
            ob.getParamsMap().put("u_Id", pending);
            ob.getParamsMap().put("slc_id", getSlocId());
            ob.execute();
            if (ob.getResult() != null) {
                String msg2 =
                    "<html><body> <p>This MPP is pending at other user<b><i> [ " + ob.getResult().toString() +
                    " ] </i></b>for approval, You can not edit this.</p></body></html>";
                //   String msg2 =
                //       "<html><body> <p>" + resolvEl("#{bundle['MSG.824']}").toString() + "<b><i> [ " + uNm.getResult().toString() +
                //       " ] </i></b>" + resolvEl("#{bundle['MSG.879']}").toString() + "</p></body></html>";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
            }
        }
    }

    public void SelectSingleOperationVCE(ValueChangeEvent vce) {
        ADFUtils.findOperation("diselectAll").execute();

        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
        ADFUtils.findOperation("setBindValuesToView").execute();

    }

    /*----------------------Call Function callWfFunction----------------------*/
    public void callFuncWf() {
        callWfId();
        ADFUtils.findOperation("callWfFunctions").execute();

    }

    /*---------------------Call callWfId Function------------------------------*/
    public void callWfId() {
        OperationBinding ob = ADFUtils.findOperation("getWfId");
        ob.execute();
        String wId = ob.getResult().toString();
        setWfId(wId);
    }


    public void setMppSwitcher(UIXSwitcher mppSwitcher) {
        this.mppSwitcher = mppSwitcher;
    }

    public UIXSwitcher getMppSwitcher() {
        return mppSwitcher;
    }

    public void setInputItemTableBInd(RichTable inputItemTableBInd) {
        this.inputItemTableBInd = inputItemTableBInd;
    }

    public RichTable getInputItemTableBInd() {
        return inputItemTableBInd;
    }

    public void setItemNameBind(RichInputListOfValues itemNameBind) {
        this.itemNameBind = itemNameBind;
    }

    public RichInputListOfValues getItemNameBind() {
        return itemNameBind;
    }

    public void searchOutputItemACE(ActionEvent actionEvent) {
        if (outputItemBind.getValue() != null) {
            String itmName =
                (outputItemBind.getValue().toString() == null ? null : outputItemBind.getValue().toString());
            //System.out.println("item Name " + itmName);
            if (itmName != null) {

                setSearchValue(itmName);
                DCIteratorBinding it = ADFUtils.findIterator("MnfMppOpItmOutputIterator");
                RowSetIterator rsi = it.getRowSetIterator();
                RowKeySet oldSelection = outputItemTableBind.getSelectedRowKeys();

                if (rsi.first() != null) {
                    // System.out.println("Inside of the loop");
                    Row r = rsi.first();
                    while (rsi.hasNext() && getKey() == null && (!matchFound(r, oldSelection))) {
                        r = rsi.next();
                    }
                }
            }
        }
    }

    private boolean matchFound(Row r, RowKeySet oldSelection) {
        setKey(null);
        //System.out.println("inside of the match function");
        ArrayList lst = new ArrayList(1);
        RowKeySetImpl newSelection = new RowKeySetImpl();
        Key key = null;
        String rowValue = r.getAttribute("ItmId").toString();
        if (rowValue.toString().contains(searchValue)) {
            //System.out.println("now setting key to " + key);
            key = r.getKey();
            lst.add(key);
            outputItemTableBind.setActiveRowKey(lst);
            newSelection.add(lst);
            makeCurrent(outputItemTableBind, newSelection, oldSelection);
            return true;
        } else {
        }
        return false;

    }

    private void makeCurrent(RichTable outputItemTableBind, RowKeySet newCurrentRow, RowKeySet oldCurrentRow) {
        //To make a row current, we need to create a SelectionEvent which
        //expects the following arguments: component, unselected_keys,
        //selected_keys. In our example, we don't have unselected keys and
        //therefore create an empty RowSet for this
        SelectionEvent selectionEvent = new SelectionEvent(oldCurrentRow, newCurrentRow, outputItemTableBind);
        selectionEvent.queue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(outputItemTableBind);
    }

    /**
     *Method to get data from page Flow scope
     *
     *
     * @param data
     * @return
     */
    public Object resolvEl(String data) {
        ADFContext adfCtx = ADFContext.getCurrent();
        ELContext elContext = adfCtx.getELContext();
        ValueExpression valueExp = adfCtx.getExpressionFactory().createValueExpression(elContext, data, Object.class);
        Object Message = valueExp.getValue(elContext).toString();

        return Message;
    }


    /*getting page flow scope parameter methods.*/

    public Integer getUsrId() {
        return Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
    }

    public Integer getSlocId() {
        return Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC }").toString());
    }


    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public Key getKey() {
        return key;
    }

    public void setItemIdBind(RichOutputText itemIdBind) {
        this.itemIdBind = itemIdBind;
    }

    public RichOutputText getItemIdBind() {
        return itemIdBind;
    }

    public void setOutputItemTableBind(RichTable outputItemTableBind) {
        this.outputItemTableBind = outputItemTableBind;
    }

    public RichTable getOutputItemTableBind() {
        return outputItemTableBind;
    }

    public void setOutputItemBind(RichOutputText outputItemBind) {
        this.outputItemBind = outputItemBind;
    }

    public RichOutputText getOutputItemBind() {
        return outputItemBind;
    }

    public void setInputItemBind(RichOutputText inputItemBind) {
        this.inputItemBind = inputItemBind;
    }

    public RichOutputText getInputItemBind() {
        return inputItemBind;
    }

    public void searchInputItemACE(ActionEvent actionEvent) {

        if (inputItemBind.getValue() != null) {
            String itmName = (inputItemBind.getValue().toString() == null ? null : inputItemBind.getValue().toString());
            // System.out.println("item Name " + itmName);
            if (itmName != null) {

                setSearchValue(itmName);
                DCIteratorBinding it = ADFUtils.findIterator("MnfMppOpItmInputIterator");
                RowSetIterator rsi = it.getRowSetIterator();
                RowKeySet oldSelection = inputItemTableBind.getSelectedRowKeys();

                if (rsi.first() != null) {
                    //System.out.println("Inside of the loop");
                    Row r = rsi.first();
                    while (rsi.hasNext() && getKey() == null && (!matchFoundInput(r, oldSelection))) {
                        r = rsi.next();
                    }

                }
            }
        }
    }

    private boolean matchFoundInput(Row r, RowKeySet oldSelection) {
        setKey(null);
        //System.out.println("inside of the match function");
        ArrayList lst = new ArrayList(1);
        RowKeySetImpl newSelection = new RowKeySetImpl();
        Key key = null;
        String rowValue = r.getAttribute("ItmId").toString();
        if (rowValue.toString().contains(searchValue)) {
            // System.out.println("now setting key to " + key);
            key = r.getKey();
            lst.add(key);
            inputItemTableBind.setActiveRowKey(lst);
            newSelection.add(lst);
            makeCurrentInput(inputItemTableBind, newSelection, oldSelection);
            return true;
        } else {

        }
        return false;

    }

    private void makeCurrentInput(RichTable outputItemTableBind, RowKeySet newCurrentRow, RowKeySet oldCurrentRow) {
        //To make a row current, we need to create a SelectionEvent which
        //expects the following arguments: component, unselected_keys,
        //selected_keys. In our example, we don't have unselected keys and
        //therefore create an empty RowSet for this
        SelectionEvent selectionEvent = new SelectionEvent(oldCurrentRow, newCurrentRow, outputItemTableBind);
        selectionEvent.queue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(outputItemTableBind);
    }


    public void setInputItemTableBind(RichTable inputItemTableBind) {
        this.inputItemTableBind = inputItemTableBind;
    }

    public RichTable getInputItemTableBind() {
        return inputItemTableBind;
    }

    public void createJobRouteDL(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            OperationBinding ob = null;
            Integer chk;
            Integer jc = (Integer) jobRouteCardBind.getValue();
            if (jc != null) {
                if (jc == 105) {
                    ob = ADFUtils.findOperation("generateJobCard");
                    ob.execute();
                    chk = (Integer) ob.getResult();
                    if (chk != null) {
                        if (chk == 1) {
                            ADFUtils.findOperation("Commit").execute();
                            JSFUtils.addFacesInformationMessage("Job Card successfully generated!");
                            setAttrsVal("DualForJobRouteGen1Iterator", "OutItmQty", null);
                            setAttrsVal("DualForJobRouteGen1Iterator", "OprName", null);
                            setAttrsVal("DualForJobRouteGen1Iterator", "OprDocId", null);
                            setAttrsVal("DualForJobRouteGen1Iterator", "outItmId", null);
                            setAttrsVal("DualForJobRouteGen1Iterator", "WcId", null);
                            
                            AdfFacesContext.getCurrentInstance().addPartialTarget(gppStatusBindVar);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(gppModeBindvar);
                        } else {
                            JSFUtils.addFacesErrorMessage("Job Card was not generated!");
                        }
                    }

                } else {

                    ob = ADFUtils.findOperation("generateRouteCard");
                    ob.execute();
                    chk = (Integer) ob.getResult();
                    if (chk != null) {
                        if (chk == 1) {
                            ADFUtils.findOperation("Commit").execute();
                            setAttrsVal("DualForJobRouteGen1Iterator", "OutItmQty", null);
                            setAttrsVal("DualForJobRouteGen1Iterator", "OprName", null);
                            setAttrsVal("DualForJobRouteGen1Iterator", "OprDocId", null);
                            setAttrsVal("DualForJobRouteGen1Iterator", "outItmId", null);
                            setAttrsVal("DualForJobRouteGen1Iterator", "WcId", null);
                            JSFUtils.addFacesInformationMessage("Route Card successfully generated!");
                            AdfFacesContext.getCurrentInstance().addPartialTarget(gppStatusBindVar);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(gppModeBindvar);

                        } else {
                            JSFUtils.addFacesErrorMessage("Route Card was not generated!");
                        }
                    }
                }
            }
        }
    }

    public void setJobRouteCardBind(RichSelectOneChoice jobRouteCardBind) {
        this.jobRouteCardBind = jobRouteCardBind;
    }

    public RichSelectOneChoice getJobRouteCardBind() {
        return jobRouteCardBind;
    }

    public void setWcIdACE(ActionEvent actionEvent) {
        Integer x;
        ADFUtils.findOperation("setParamForOperationSerial").execute();
        String gppId = actionEvent.getComponent().getAttributes().get("gppId").toString();

        OperationBinding a = ADFUtils.findOperation("setParamForWcId");
        a.execute();
        x = (Integer) a.getResult();
        if (x == 0) {
            ADFUtils.findOperation("chkJcRcGen").execute();

            ADFUtils.showPopup(jcRcGenPopBind, true);
        } else {
            JSFUtils.addFacesErrorMessage("JC/RC cannot be generated");
        }

    }

    protected void calculateBalanQty(Integer typId) {
        oracle.jbo.domain.Number totQty = new oracle.jbo.domain.Number(0);
        oracle.jbo.domain.Number tempQty = new oracle.jbo.domain.Number(0);
        oracle.jbo.domain.Number balanc = new oracle.jbo.domain.Number(0);
        if (typId == 105) {
            totQty = (oracle.jbo.domain.Number) getAttrsVal("MnfMppOp1Iterator", "BalOpQty");
            tempQty = (oracle.jbo.domain.Number) getAttrsVal("MnfMppOp1Iterator", "TmpBalOpQty");
            balanc = totQty.subtract(tempQty);
            
            setAttrsVal("DualForJobRouteGen1Iterator", "BalanceQty", balanc);
        } else {
            totQty = (oracle.jbo.domain.Number) getAttrsVal("MnfMpp1Iterator", "BalQty");
            tempQty = (oracle.jbo.domain.Number) getAttrsVal("MnfMpp1Iterator", "TmpBalQty");
            balanc = totQty.subtract(tempQty);
            
            setAttrsVal("DualForJobRouteGen1Iterator", "BalanceQty", balanc);
        }
        System.out.println(totQty + " Total Qty");
        System.out.println(tempQty + " temp Qty");
        System.out.println(balanc + " balance Qty");

    }

    public void resnValidCharValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (object.toString().contains("  ")) {
                FacesMessage message = new FacesMessage("Entered Remarks has useless white spaces!!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
        }
    }

    public void setJcRcGenPopBind(RichPopup jcRcGenPopBind) {
        this.jcRcGenPopBind = jcRcGenPopBind;
    }

    public RichPopup getJcRcGenPopBind() {
        return jcRcGenPopBind;
    }

    public void selectAllACE(ActionEvent actionEvent) {
        ADFUtils.findOperation("selectAllPdo").execute();
    }

    public void deselectAllACE(ActionEvent actionEvent) {
        ADFUtils.findOperation("unselectAllPdo").execute();
    }

    public void setGppStatusBindVar(RichSelectOneChoice gppStatusBindVar) {
        this.gppStatusBindVar = gppStatusBindVar;
    }

    public RichSelectOneChoice getGppStatusBindVar() {
        return gppStatusBindVar;
    }


    public void setRemarkBindVar(RichInputText remarkBindVar) {
        this.remarkBindVar = remarkBindVar;
    }

    public RichInputText getRemarkBindVar() {
        return remarkBindVar;
    }

    public void requirmntAreaVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(warehouseBindVar);
    }

    public void setWarehouseBindVar(RichInputListOfValues warehouseBindVar) {
        this.warehouseBindVar = warehouseBindVar;
    }

    public RichInputListOfValues getWarehouseBindVar() {
        return warehouseBindVar;
    }

    public void acttualQtyForJcValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number value = (oracle.jbo.domain.Number) object;
            oracle.jbo.domain.Number totalValue = (oracle.jbo.domain.Number) (totalQuantityBindVar.getValue());
            oracle.jbo.domain.Number actualQty = (oracle.jbo.domain.Number) (chckBalQtyBinding.getValue());
            System.out.println(actualQty);
            Boolean isValid = isPrecisionValueValid(26, 6, value);
            if (isValid == false) {
                FacesMessage message = new FacesMessage("Precision upto 20.6 digit only!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
            if (value.compareTo(0) == -1) {
                FacesMessage message = new FacesMessage("Value must not be negative");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
            if (actualQty.compareTo(value) == -1) {
                FacesMessage message = new FacesMessage("Actual quantity must not exceeds Balance quantity!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
            if (value.compareTo(0) == 0) {
                FacesMessage message = new FacesMessage("Cannot generate JC/RC with quantity 0");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }

        }
    }

    /**
     * Precision check method
     */
    public Boolean isPrecisionValueValid(Integer precision, Integer scale, oracle.jbo.domain.Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }

    public void setTotalQuantityBindVar(RichInputText totalQuantityBindVar) {
        this.totalQuantityBindVar = totalQuantityBindVar;
    }

    public RichInputText getTotalQuantityBindVar() {
        return totalQuantityBindVar;
    }


    /**
     * Method to set value for an attribute of current row for an Iterator
     * @param iter for iterator name
     * @param for attribute name
     * @param for attribute value
     * **/
    public void setAttrsVal(String iter, String attrs, Object val) {

        if (iter != null && attrs != null) {
            DCIteratorBinding dcIter = ADFUtils.findIterator(iter);

            if (dcIter.getCurrentRow() != null)
                dcIter.getCurrentRow().setAttribute(attrs, val);

        }
    }

    /**
     * Method to get value of attribute of current row for an Iterator
     * @param iter for iterator name
     * @param for attribute name
     * **/
    public Object getAttrsVal(String iter, String attrs) {

        if (iter != null && attrs != null) {
            DCIteratorBinding dcIter = ADFUtils.findIterator(iter);
            // System.out.println("dcIter " + dcIter.getEstimatedRowCount());
            if (dcIter.getCurrentRow() != null)
                return dcIter.getCurrentRow().getAttribute(attrs);

        }
        return null;
    }

    public void setOutputItmBindVar(RichSelectOneChoice outputItmBindVar) {
        this.outputItmBindVar = outputItmBindVar;
    }

    public RichSelectOneChoice getOutputItmBindVar() {
        return outputItmBindVar;
    }

    public void DocumentSourceVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent != null) {

            Integer jc = (Integer) valueChangeEvent.getNewValue();
            if (jc == 105) {
                calculateBalanQty(105);
                setAttrsVal("DualForJobRouteGen1Iterator", "OutItmQty", null);
                setAttrsVal("DualForJobRouteGen1Iterator", "OprName", null);
                setAttrsVal("DualForJobRouteGen1Iterator", "OprDocId", null);
                setAttrsVal("DualForJobRouteGen1Iterator", "outItmId", null);
                setAttrsVal("DualForJobRouteGen1Iterator", "WcId", getAttrsVal("MnfMppOp1Iterator", "WcId"));
                // System.out.println(getAttrsVal("MnfMppOp1Iterator", "WcId"));
                // System.out.println(getAttrsVal("DualForJobRouteGen1Iterator", "WcId"));
                // setAttrsVal("DualForJobRouteGen1Iterator", "OprDocId", getAttrsVal("MnfMppOp1Iterator", "OpId"));
                // setAttrsVal("DualForJobRouteGen1Iterator", "OprSrNo", getAttrsVal("MnfMppOp1Iterator", "OpSrNo"));
                operationNameBindVar.setVisible(true);
                oprSrNoBindVar.setVisible(true);
                AdfFacesContext.getCurrentInstance().addPartialTarget(outputItmBindVar);
                AdfFacesContext.getCurrentInstance().addPartialTarget(totalQuantityBindVar);
                AdfFacesContext.getCurrentInstance().addPartialTarget(operationNameBindVar);
                AdfFacesContext.getCurrentInstance().addPartialTarget(oprSrNoBindVar);
                // AdfFacesContext.getCurrentInstance().addPartialTarget(oprSrNoBindVar);
            } else { /*
                OperationBinding ob = ADFUtils.findOperation("ChkJCExists");
                ob.execute();
                String chJc = ob.getResult().toString();
                if (chJc.equalsIgnoreCase("Y")) {
                    FacesMessage message =
                        new FacesMessage("Route card can not be generated as an operation exists against a Job Card !!");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(jobRouteCardBind.getClientId(), message);
                }else */
                // {

                calculateBalanQty(106);
                setAttrsVal("DualForJobRouteGen1Iterator", "OutItmQty",
                            getAttrsVal("MnfMppOp1Iterator", "OutptItmQty"));
                setAttrsVal("DualForJobRouteGen1Iterator", "outItmId", getAttrsVal("MnfMppOp1Iterator", "OutptItmId"));
                setAttrsVal("DualForJobRouteGen1Iterator", "WcId", getAttrsVal("MnfMppOp1Iterator", "WcId"));

                setAttrsVal("DualForJobRouteGen1Iterator", "OprDocId", null);
                setAttrsVal("DualForJobRouteGen1Iterator", "OprSrNo", null);
                // System.out.println(getAttrsVal("MnfMppOp1Iterator", "WcId"));
                // System.out.println(getAttrsVal("DualForJobRouteGen1Iterator", "WcId"));
                operationNameBindVar.setVisible(false);
                oprSrNoBindVar.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(outputItmBindVar);
                AdfFacesContext.getCurrentInstance().addPartialTarget(totalQuantityBindVar);
                //}
            }
        }


    }

    public void setOperationNameBindVar(RichInputListOfValues operationNameBindVar) {
        this.operationNameBindVar = operationNameBindVar;
    }

    public RichInputListOfValues getOperationNameBindVar() {
        return operationNameBindVar;
    }

    public void setOprSrNoBindVar(RichInputText oprSrNoBindVar) {
        this.oprSrNoBindVar = oprSrNoBindVar;
    }

    public RichInputText getOprSrNoBindVar() {
        return oprSrNoBindVar;
    }

    public void setBalanceQuantityBinding(RichInputText balanceQuantityBinding) {
        this.balanceQuantityBinding = balanceQuantityBinding;
    }

    public RichInputText getBalanceQuantityBinding() {
        return balanceQuantityBinding;
    }

    public void setTmpBalQtyBinding(RichOutputText tmpBalQtyBinding) {
        this.tmpBalQtyBinding = tmpBalQtyBinding;
    }

    public RichOutputText getTmpBalQtyBinding() {
        return tmpBalQtyBinding;
    }

    public void setChckBalQtyBinding(RichInputText chckBalQtyBinding) {
        this.chckBalQtyBinding = chckBalQtyBinding;
    }

    public RichInputText getChckBalQtyBinding() {
        return chckBalQtyBinding;
    }

    public void setGppModeBindvar(RichSelectOneChoice gppModeBindvar) {
        this.gppModeBindvar = gppModeBindvar;
    }

    public RichSelectOneChoice getGppModeBindvar() {
        return gppModeBindvar;
    }
}
