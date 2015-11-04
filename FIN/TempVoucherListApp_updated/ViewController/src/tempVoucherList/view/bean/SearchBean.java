package tempVoucherList.view.bean;

import java.math.BigDecimal;
import java.math.BigInteger;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputRangeSlider;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelBox;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.server.DBTransaction;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlValueBindingRef;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

import tempVoucherList.model.services.TempVoucherAMImpl;


public class SearchBean {

    private static final String amName = "TempVoucherAMDataControl";
    private RichPanelBox searchPanel;
    private RichSelectOneChoice bindOrgId;
    private RichSelectOneChoice bindTypeId;
    private RichSelectOneChoice bindVouId;
    private RichSelectOneChoice bindCoaId;
    private RichSelectOneChoice bindCogId;
    private RichInputDate fromDate;
    private RichInputDate toDate;
    private RichInputRangeSlider amountSlider;
    private RichSelectOneChoice vouIdBind;
    private Integer paramCount;
    private RichPopup voucherPrintPopUp;
    private Integer count = 0;
    private RichInputText callPopupBind;
    public String glDispNo = "JV000001";
    public String multiOrgFlag = "N";
    private RichPanelFormLayout searchForm;

    public SearchBean() {
    }

    /**
     * @param data
     * @return
     * function to get Application module value from a EL expression. Input will be a string value containing name of application module.
     */
    public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }


    /** Function is called from resetValues function to reset values of input fields and list of values.
     *
     */
    private void resetValueInputItems(AdfFacesContext adfFacesContext, UIComponent component) {
        /** Get list of all components from search panel */
        List<UIComponent> items = component.getChildren();
        for (UIComponent item : items) {

            resetValueInputItems(adfFacesContext, item);
            /** Check if Input text , reset its value*/
            if (item instanceof RichInputText) {
                RichInputText input = (RichInputText) item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
                /** Check if RichInputDate , reset its value*/
            } else if (item instanceof RichInputDate) {
                RichInputDate input = (RichInputDate) item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
                /** Check if SelectOneChoice , reset its value*/
            } else if (item instanceof RichSelectOneChoice) {
                RichSelectOneChoice input = (RichSelectOneChoice) item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            }

        }
    }

    /** Function is used to reset values of input fields and list of values.
     * @param actionEvent
     */
    public void resetValues(ActionEvent actionEvent) {
        /** Get TvouSearchVO view object */
        OperationBinding ob = getBindings().getOperationBinding("Rollback");
        ob.execute();
        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        ViewObject v = am.getTvouSearchVO();
        ViewObject view = am.getViewforLov();
        /**Set values of bind variables to null */
        v.setNamedWhereClauseParam("BindTvouTyp", null);
        v.setNamedWhereClauseParam("BindPrjId", null);
        //org set to null to stop row on reset
        v.setNamedWhereClauseParam("BindOrgId", -1);
        v.setNamedWhereClauseParam("BindTvouId", null);
        v.setNamedWhereClauseParam("BindCoaId", null);
        v.setNamedWhereClauseParam("BindCogId", null);
        v.setNamedWhereClauseParam("BindFrmDate", null);
        v.setNamedWhereClauseParam("BindToDate", null);
        v.setNamedWhereClauseParam("BindAmtFrm", null);
        v.setNamedWhereClauseParam("BindAmtTo", null);
        v.executeQuery();
        /** Execute view object*/
        view.executeQuery();
        AdfFacesContext fctx = AdfFacesContext.getCurrentInstance();
        /** Call function to reset input field's values */
        resetValueInputItems(fctx, searchForm);
        BindingContext bindingctx2 = BindingContext.getCurrent();
        BindingContainer bindings2 = bindingctx2.getCurrentBindingsEntry();
        OperationBinding createOpBAddress2 = bindings2.getOperationBinding("Execute");
        createOpBAddress2.execute();

    }

    /**
     * @param searchPanel
     */
    public void setSearchPanel(RichPanelBox searchPanel) {
        this.searchPanel = searchPanel;
    }

    /**
     * @return
     */
    public RichPanelBox getSearchPanel() {
        return searchPanel;
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    /**
     * @param actionEvent
     */
    public void searchAction(ActionEvent actionEvent) {
        /** Get TvouSearchVO view object */

        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        ViewObject v = am.getTvouSearchVO();
        ViewObject view = am.getViewforLov();
        System.out.println("Voudispid :" + view.first().getAttribute("Voudispid"));
        /**Set values of bind variables. these values are from bind  */
        v.setNamedWhereClauseParam("BindPrjId", view.first().getAttribute("Prjid"));
        v.setNamedWhereClauseParam("BindTvouTyp", bindTypeId.getValue());
        v.setNamedWhereClauseParam("BindOrgId", bindOrgId.getValue());
        v.setNamedWhereClauseParam("BindTvouId", view.first().getAttribute("Voudispid"));
        v.setNamedWhereClauseParam("BindCoaId", view.first().getAttribute("Coaid"));
        v.setNamedWhereClauseParam("BindCogId", view.first().getAttribute("Cogid"));
        v.setNamedWhereClauseParam("BindFrmDate", fromDate.getValue());
        v.setNamedWhereClauseParam("BindToDate", toDate.getValue());
        v.setNamedWhereClauseParam("BindTvouBillNo", view.first().getAttribute("Billno"));
        /** Execute view object */
        v.executeQuery();


    }

    /**
     * @param bindOrgId
     */
    public void setBindOrgId(RichSelectOneChoice bindOrgId) {
        this.bindOrgId = bindOrgId;
    }

    /**
     * @return
     */
    public RichSelectOneChoice getBindOrgId() {
        return bindOrgId;
    }

    /**
     * @param bindTypeId
     */
    public void setBindTypeId(RichSelectOneChoice bindTypeId) {
        this.bindTypeId = bindTypeId;
    }

    /**
     * @return
     */
    public RichSelectOneChoice getBindTypeId() {
        return bindTypeId;
    }

    /**
     * @param bindVouId
     */
    public void setBindVouId(RichSelectOneChoice bindVouId) {
        this.bindVouId = bindVouId;
    }

    /**
     * @return
     */
    public RichSelectOneChoice getBindVouId() {
        return bindVouId;
    }

    /**
     * @param bindCoaId
     */
    public void setBindCoaId(RichSelectOneChoice bindCoaId) {
        this.bindCoaId = bindCoaId;
    }

    /**
     * @return
     */
    public RichSelectOneChoice getBindCoaId() {
        return bindCoaId;
    }

    /**
     * @param bindCogId
     */
    public void setBindCogId(RichSelectOneChoice bindCogId) {
        this.bindCogId = bindCogId;
    }

    /**
     * @return
     */
    public RichSelectOneChoice getBindCogId() {
        return bindCogId;
    }

    /**
     * @param fromDate
     */
    public void setFromDate(RichInputDate fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * @return
     */
    public RichInputDate getFromDate() {
        return fromDate;
    }

    /**
     * @param toDate
     */
    public void setToDate(RichInputDate toDate) {
        this.toDate = toDate;
    }

    /**
     * @return
     */
    public RichInputDate getToDate() {
        return toDate;
    }


    /**
     * @return
     */
    public BigInteger getMaxNo() {
        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        String query =
            "SELECT max(B.TVOU_TOT_AMT_BS) FROM TVOU_LINES A ,(SELECT  SUM(NVL(TVOU_AMT_BS,0) ) TVOU_TOT_AMT_BS from TVOU_LINES GROUP BY TVOU_ID) b";
        DBTransaction dbt = am.getDBTransaction();
        BigDecimal max = new BigDecimal(0);

        try {
            ResultSet rs = dbt.createStatement(0).executeQuery(query);
            if (rs.next()) {
                max = (BigDecimal) rs.getObject(1);
            }
            rs.close();
        } catch (SQLException sqlerr) {
            throw new JboException(sqlerr);
        }
        System.out.println(max.toBigInteger());

        return max.toBigInteger();
    }

    public List coaAutoSuggest(String string) {
        //get access to the binding context and binding container at runtime
        BindingContext bctx = BindingContext.getCurrent();
        BindingContainer bindings = bctx.getCurrentBindingsEntry();
        //set the bind variable value that is used to filter the View Object
        ////query of the suggest list. The View Object instance has a View
        ////Criteria assigned
        OperationBinding setVariable = (OperationBinding) bindings.get("setBindCoaNm");
        setVariable.getParamsMap().put("value", string);
        setVariable.execute();
        //the data in the suggest list is queried by a tree binding.
        JUCtrlHierBinding hierBinding = (JUCtrlHierBinding) bindings.get("LovDistCoaVO");
        //re-query the list based on the new bind variable values
        hierBinding.executeQuery();
        //The rangeSet, the list of queries entries, is of type        JUCtrlValueBndingRef.
        List<JUCtrlValueBindingRef> displayDataList = hierBinding.getRangeSet();
        ArrayList<SelectItem> selectItems = new ArrayList<SelectItem>();
        for (JUCtrlValueBindingRef displayData : displayDataList) {
            Row rw = displayData.getRow();
            //populate the SelectItem list
            selectItems.add(new SelectItem((String) rw.getAttribute("CoaNm"), (String) rw.getAttribute("CoaNm")));
        }
        return selectItems;

    }

    public void setAmountSlider(RichInputRangeSlider amountSlider) {
        this.amountSlider = amountSlider;
    }

    public RichInputRangeSlider getAmountSlider() {
        return amountSlider;
    }

    public String viewButton() {
        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        ViewObject searchVo = am.getTvouSearchVO();
        Row curRow = searchVo.getCurrentRow();
        DCBindingContainer bc = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();

        DCIteratorBinding iterator = bc.findIteratorBinding("TvouSearchVOIterator");

        Row r = iterator.getCurrentRow();
        // System.out.println("Current Row is--->" + curRow + "and from iterator-->" + r);
        // System.out.println("Value from Binding--->" + vouIdBind.getValue());
        String vouId = "N";
        // System.out.println("VouId from Row is-->" + curRow.getAttribute("TvouId"));
        if (curRow.getAttribute("TvouId") != null) {
            vouId = curRow.getAttribute("TvouId").toString();
        } else {
            System.out.println("Id not found for this row.");
        }
        return "GoTvouEdit";
    }

    public void setVouIdBind(RichSelectOneChoice vouIdBind) {
        this.vouIdBind = vouIdBind;
    }

    public RichSelectOneChoice getVouIdBind() {
        return vouIdBind;
    }

    public void setParamCount(Integer paramCount) {
        this.paramCount = paramCount;
    }

    public Integer getParamCount() {

        OperationBinding ob = getBindings().getOperationBinding("on_load_page");
        paramCount = (Integer) ob.execute();

        return paramCount;
    }

    /**
     *Function to open popup, this function takes value of popup name and generates a java script to open popup.
     */
    private void showPopup(RichPopup pop, boolean visible) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && pop != null) {
                String popupId = pop.getClientId(context);
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

    /**
     * Function to call a database procedure "transalate_to_gl" for translating temporary voucher to GL.
     * */
    public Object resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        Object Message = valueExp.getValue(elContext);
        return Message;
    }

    public void setVoucherPrintPopUp(RichPopup voucherPrintPopUp) {
        this.voucherPrintPopUp = voucherPrintPopUp;
    }

    public RichPopup getVoucherPrintPopUp() {
        return voucherPrintPopUp;
    }

    public void setCallPopupBind(RichInputText callPopupBind) {
        this.callPopupBind = callPopupBind;
    }

    public RichInputText getCallPopupBind() {

        count = count + 1;

        System.out.println("PARAM_WF_FLAG :" + resolvEl("#{pageFlowScope.PARAM_WF_FLAG}") + " count " + count);
        if (resolvEl("#{pageFlowScope.PARAM_WF_FLAG}") != null && count.equals(1)) {

            if (resolvEl("#{pageFlowScope.PARAM_WF_FLAG}").toString().equals("A")) {

                System.out.println(" getCallPopupBind ");
                showPopup(voucherPrintPopUp, true);
            }
        }

        return callPopupBind;
    }

    public void setGlDispNo(String glDispNo) {


        this.glDispNo = glDispNo;
    }

    /**
     * Method call to set the voucher id generated incase the page is referred from WF service.
     * **/
    public String getGlDispNo() {

        // get the generated Voucher Display Id from AM via binding call.

        OperationBinding ob = getBindings().getOperationBinding("getGlDispNo");
        ob.execute();

        if (ob.getResult() != null) {

            this.glDispNo = ob.getResult().toString();
        }

        return glDispNo;
    }

    public Object getGlVouDt() {

        // get the Gl vou Date from AM via binding call.

        OperationBinding ob = getBindings().getOperationBinding("getGlVouDt");
        ob.execute();
        // System.out.println("date " + Date.valueOf(ob.getResult().toString()));

        return ob.getResult();
    }

    public Integer getGlVouTyp() {

        // get the Gl vou type from AM via binding call.

        OperationBinding ob = getBindings().getOperationBinding("getGlVouTyp");
        ob.execute();

        return Integer.parseInt(ob.getResult().toString());
    }

    public void setMultiOrgFlag(String multiOrgFlag) {
        this.multiOrgFlag = multiOrgFlag;
    }

    public String getMultiOrgFlag() {
        OperationBinding ob = getBindings().getOperationBinding("getMultiOrgFlag");
        ob.execute();
        return ob.getResult().toString();
    }

    public String setTvouIdParam() {
        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        ViewObject searchVo = am.getTvouSearchVO();
        Row curRow = searchVo.getCurrentRow();
        if (curRow != null) {
            Object TvouId = curRow.getAttribute("TvouId");
            if (TvouId != null) {
                return TvouId.toString();
            }
        }
        return null;
    }

    public void setSearchForm(RichPanelFormLayout searchForm) {
        this.searchForm = searchForm;
    }

    public RichPanelFormLayout getSearchForm() {
        return searchForm;
    }
}
