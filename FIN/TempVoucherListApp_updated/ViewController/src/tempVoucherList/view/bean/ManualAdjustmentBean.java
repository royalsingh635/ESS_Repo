package tempVoucherList.view.bean;


import adf.utils.bean.ADFBeanUtils;
import adf.utils.model.ADFModelUtils;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

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

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;

import org.apache.myfaces.trinidad.event.SelectionEvent;

import tempVoucherList.model.services.TempVoucherAMImpl;


public class ManualAdjustmentBean {

    private RichPopup advancePopupBinding;
    private Number zero = new Number(0);
    private RichTable dtlTableBinding;
    private static final String amName = "TempVoucherAMDataControl";

    private RichSelectBooleanCheckbox entityBinding;
    private RichOutputText totalTrxAmountBinding;
    private RichPopup unpostedPopup;
    private RichPopup postedAmountBinding;
    private RichPopup unpostedDetailBinding;
    private RichPanelFormLayout formbinding;
    private RichPopup postedAdvpopupBinding;
    private Number baseAmount = new Number(0);
    private RichTable adjTableBinding;
    private RichPanelCollection panelCollectionBinding;
    private RichSelectOneChoice tvouCurrIdTrxBinding;
    private RichOutputText tvouAdjCurrIdSpBind;

    public ManualAdjustmentBean() {
        System.out.println("In Manual Adjustment bean........");
    }

    public String FitlerArapWithDaysAndType() {
        BindingContainer bc = ADFUtil.getBindings();
        OperationBinding binding = bc.getOperationBinding("fetchAdjustmentDataForManual");
        binding.execute();
        OperationBinding bindin1 = bc.getOperationBinding("filterArapShuttle");
        System.out.println("entityBinding.getValue() = " + entityBinding.getValue());
        if (entityBinding.getValue() != null) {
            if (entityBinding.getValue().toString().equalsIgnoreCase("false") ||
                entityBinding.getValue().toString().equalsIgnoreCase("N")) {
                bindin1.getParamsMap().put("Ext1", "N");
            } else {
                bindin1.getParamsMap().put("Ext1", "Y");

            }
        } else {
            bindin1.getParamsMap().put("Ext1", "N");

        }
        bindin1.execute();
        OperationBinding createOpBAddress = ADFUtil.getBindings().getOperationBinding("setAdjustmentSummaryCol");
        createOpBAddress.execute();
        return null;
    }


    public String openShuttleToFilter() {

        BindingContainer bc = ADFUtil.getBindings();
        OperationBinding binding = bc.getOperationBinding("setCheckFlagOnArap");
        binding.execute();
        ADFUtil.showPopup(advancePopupBinding, true);
        return null;
    }


    public String backAction() {
        Object res = ADFBeanUtils.callBindingsMethod("getTotalbaseInvoiceAmount", null, null);

        if (res != null)
            setBaseAmount((Number) res);
        ADFBeanUtils.callBindingsMethod("updateCostCenterAmt", new Object[] { totalTrxAmountBinding.getValue() }, new Object[] {
                                        "amountSp" });
        AdfFacesContext.getCurrentInstance().addPartialTarget(adjTableBinding);
                ViewObject lines = ADFUtil.getAM().findViewObject("TvouLinesLnk");
                if (lines != null) {
                    Row currentRow = lines.getCurrentRow();
                    if (currentRow != null) {
                        if (currentRow.getAttribute("TransIsCostCenterAlw") != null) {
                                          System.out.println("before check of updateCostCenterAmt ");
                                          if (currentRow.getAttribute("TransIsCostCenterAlw").toString().equalsIgnoreCase("Y")) {
                                              System.out.println("beofre updateCostCenterAmt call");
                                              if (currentRow.getAttribute("CcId") == null) {
                                                  currentRow.setAttribute("CcId",
                                                                  getHexDocNoFromFun((String) currentRow.getAttribute("TvouCldId"),
                                                                                     (Integer) currentRow.getAttribute("TvouSlocId"),
                                                                                     (String) currentRow.getAttribute("TvouOrgId"),
                                                                                     (Integer) currentRow.getAttribute("UsrIdCreate"),
                                                                                     Integer.parseInt(currentRow.getAttribute("TvouTypeId").toString())));
                                              }
                                              ADFBeanUtils.callBindingsMethod("updateCostCenterAmt", new Object[] { totalTrxAmountBinding.getValue() }, new Object[] {
                                                                              "amountSp" });
                                          } else {
                                              currentRow.setAttribute("CcId", null);
                                          }

                                      }
                       
                       
                       
                    }
                }
        return "back";
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

    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        TempVoucherAMImpl am = (TempVoucherAMImpl) resolvElDC(amName);
        try {
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
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public String getHexDocNoFromFun(String CldId, Integer SlocId, String OrgId, Integer UsrId, Integer typId) {

        String hexDocIdFromFun = "0";
        try {
            hexDocIdFromFun = this.callStoredFunction(Types.VARCHAR, "APP.GET_TXN_ID_CC(?,?,?,?,?,?,?)", new Object[] {
                                                      CldId, SlocId, OrgId, UsrId, 55, 84651, typId
            }).toString();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR IN CALLING FUNCTION GET_TXN_ID at BdgEoBudgetEoImpl :" + e.getMessage());
        }
        System.out.println("===========" + hexDocIdFromFun);

        return hexDocIdFromFun;
    }


    public String InsertIntoTvouAdjDtl() {
        BindingContainer bc = ADFUtil.getBindings();
        OperationBinding binding = bc.getOperationBinding("insertIntoTvouAdjAutoDtl");
        binding.execute();
        advancePopupBinding.hide();
        adjustAction();
        // unCheckAllAdvances();
        AdfFacesContext.getCurrentInstance().addPartialTarget(dtlTableBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(panelCollectionBinding);

        return null;
    }

    public String checkAllAdvances() {
        BindingContainer bc = ADFUtil.getBindings();
        OperationBinding binding = bc.getOperationBinding("checkAllAdvances");
        binding.execute();
        return null;
    }

    public String unCheckAllAdvances() {
        BindingContainer bc = ADFUtil.getBindings();
        OperationBinding binding = bc.getOperationBinding("unCheckAllAdvances");
        binding.execute();
        return null;
    }


    public String selectAllAdvanceInAdvanceTable() {
        BindingContainer bc = ADFUtil.getBindings();
        OperationBinding binding = bc.getOperationBinding("selectAllAdvanceInAdjDtl");
        binding.execute();
        return null;
    }

    public String refreshDetails() {
        OperationBinding createOpBAddress = ADFUtil.getBindings().getOperationBinding("refreshAdjustedAmount");
        createOpBAddress.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(formbinding);
        return null;
    }

    public String adjustAction() {
        OperationBinding createOpBAddress = ADFUtil.getBindings().getOperationBinding("adjustAmount");
        createOpBAddress.execute();
        Object object = createOpBAddress.getResult();
        if (object != null) {
            String msg = (String) resolvEl("#{bundle['MSG.1954']}"); //Not Enough Balance To Adjust. Please Enter Amount less than Balance Amount.
            ADFModelUtils.showFacesMessage(msg, msg, FacesMessage.SEVERITY_ERROR, null);
            /*  ADFUtils.showFacesMsg(msg, FacesMessage.SEVERITY_ERROR, null); */
        }
        return null;
    }

    public void transactionAmountValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {

            Number amount = (Number) object;
            if (!ADFUtil.isPrecisionValid(26, 6, (Number) object)) {
                String msg = resolvEl("#{bundle['MSG.1637']}");  //Invalid Precision/Scale! Please Enter valid amount.
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));

            } else if (amount.compareTo(0) < 0) {

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.1955']}"), null));  //Enter Amount more than 0

            } else {

                ViewObject TvouAdjAutoVO = ADFUtil.getAM().findViewObject("TvouAdjAutoVO");
                Row currentRow = TvouAdjAutoVO.getCurrentRow();
                if (currentRow != null) {
                    Number TvouCurrCcTrx = (Number) currentRow.getAttribute("TvouCurrCcTrx");
                    Number TvouCcCurr = (Number) currentRow.getAttribute("TvouCcOld");
                    System.out.println("tvouAdjCurrIdSpBind.getValue()=" +
                                       tvouAdjCurrIdSpBind.getValue());
                    System.out.println("currentRow.getAttribute(\"TvouCurrIdTrx\")) =" +
                                       currentRow.getAttribute("TvouCurrIdTrx"));
                    System.out.println("tvouAdjCurrIdSpBind.getValue().equals(currentRow.getAttribute(\"TvouCurrIdTrx\")) = " +
                                       tvouAdjCurrIdSpBind.getValue().equals(currentRow.getAttribute("TvouCurrIdTrx")));
                    if (tvouAdjCurrIdSpBind.getValue().equals(currentRow.getAttribute("TvouCurrIdTrx"))) {
                        // do nothing
                    } else {
                        amount = ADFUtil.roundOffAmt((amount.multiply(TvouCurrCcTrx)).divide(TvouCcCurr));
                    }
                    System.out.println(" TvouCurrCcTrx = " + TvouCurrCcTrx + "TvouCcCurr = " + TvouCcCurr +
                                       " amount = " + amount);
                    Number TvouRadjAmtAdj = (Number) currentRow.getAttribute("TvouRadjAmtAdj");
                    Number TvouAdjAmtOT = (Number) currentRow.getAttribute("TvouAdjAmtOT");
                    Number rembal = TvouAdjAmtOT.subtract(TvouRadjAmtAdj);
                    System.out.println("TvouRadjAmtAdj = " + TvouRadjAmtAdj + " TvouAdjAmtOT = " + TvouAdjAmtOT +
                                       " rembal = " + rembal);
                    if (amount.compareTo(rembal) > 0) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      resolvEl("#{bundle['MSG.1956']}"), null));  //Enter Transaction Amount less than Balance Amount

                    } else
                        AdfFacesContext.getCurrentInstance().addPartialTarget(totalTrxAmountBinding);
                }
            }
        }

    }

    public void InvoiceTrxAmtVCE(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());

        if (valueChangeEvent.getNewValue() != null) {

            BindingContainer bc = ADFUtil.getBindings();
            OperationBinding binding = bc.getOperationBinding("thisAdjAmount");
            binding.getParamsMap().put("amount", valueChangeEvent.getNewValue());
            binding.execute();


        }
    }

    public void tvouAdjSelectionListner(SelectionEvent selectionEvent) {
        ADFUtil.invokeEL("#{bindings.TvouAdjAutoVO.collectionModel.makeCurrent}", new Class[] { SelectionEvent.class }, new Object[] {
                         selectionEvent });
        BindingContainer bc = ADFUtil.getBindings();
        OperationBinding binding = bc.getOperationBinding("refreshDetailAdjustedAmountValue");
        binding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(dtlTableBinding);

    }

    public void dtlTransactionAmountValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number amount = (Number) object;

        if (object != null) {
            if (!ADFUtil.isPrecisionValid(26, 6, (Number) object)) {
                System.out.println("invalid precisoion");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.1637']}"), null));  //Invalid Precision/Scale! Please Enter valid amount.

            } else if (amount.compareTo(0) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.1955']}"), null));  //Enter Amount more than 0
            } else {
                Object tvouAdjCurrIdSp = uIComponent.getAttributes().get("tvouAdjCurrIdSp");

                Number tvouCurrCc = (Number) uIComponent.getAttributes().get("TvouCcOld");
                System.out.println("");
                Number tvouCurrCcTrx = (Number) uIComponent.getAttributes().get("TvouCurrCcTrx");
                if (tvouCurrIdTrxBinding.getValue().equals(tvouAdjCurrIdSp)) {
                    //do nothing
                } else {
                    amount = ADFUtil.roundOffAmt((amount.multiply(tvouCurrCcTrx)).divide(tvouCurrCc));
                }
                /*                   System.out.println("tvouCurrCc = "+tvouCurrCc+" tvouCurrCcTrx = "+tvouCurrCcTrx+"  amount = "+amount); */


                Number TotalAdjustedAmount = (Number) uIComponent.getAttributes().get("totalAdjustedAmount");
                Number outStandingAmount = (Number) uIComponent.getAttributes().get("outStandingAmount");
                Number adjustedAmount = (Number) uIComponent.getAttributes().get("adjustedAmount");
                Number rembal = outStandingAmount.subtract(TotalAdjustedAmount.subtract(adjustedAmount));
                /*                                 System.out.println("outStandingAmount = "+outStandingAmount+" TotalAdjustedAmount = "+TotalAdjustedAmount+" adjustedAmount = "+" rembal = "+rembal); */
                if (amount.compareTo(rembal) > 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  resolvEl("#{bundle['MSG.1957']}"), null)); //Not Enough Balance.

                }
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(formbinding);
    }


    /*  public String filterArapWithEntityWise() {
        Object object = entityBinding.getValue();
        System.out.println("Enitty  Wise ="+object);
        if(object!= null){
            String Entity = object.toString();
            BindingContainer bc=ADFUtil.getBindings();
            OperationBinding bindin1 = bc.getOperationBinding("filterArapShuttle");
            if(Entity.equalsIgnoreCase("true")){
                bindin1.getParamsMap().put("Ext1", "Y");

            }else {
                bindin1.getParamsMap().put("Ext1", "N");

            }
            bindin1.execute();

        }
        return null;
    }

     */

    public void deleteAdjDtlAction(ActionEvent actionEvent) {
        String TvouRadjDispId = (String) actionEvent.getComponent().getAttributes().get("TvouRadjDispId"); //
        BindingContainer bc = ADFUtil.getBindings();
        OperationBinding binding = bc.getOperationBinding("deleteFromDetail");
        binding.getParamsMap().put("displayId", TvouRadjDispId);
        binding.execute();

    }

    public String openUnpostedVoucherDetails() {

        BindingContainer bc = ADFUtil.getBindings();
        OperationBinding binding = bc.getOperationBinding("filterUnpostedVoucherDetail");
        binding.execute();
        ADFUtil.showPopup(unpostedPopup, true);
        return null;
    }


    public String openPostedVoucherDetail() {
        BindingContainer bc = ADFUtil.getBindings();
        OperationBinding binding = bc.getOperationBinding("filterPostedVoucherDetail");
        binding.execute();
        ADFUtil.showPopup(postedAmountBinding, true);
        return null;
    }

    public void unpostedAmountDetail(ActionEvent actionEvent) {
        BindingContainer bc = ADFUtil.getBindings();
        OperationBinding binding = bc.getOperationBinding("filterUnpostedAdvVoucherDetail");
        binding.execute();
        ADFUtil.showPopup(unpostedDetailBinding, true);
    }

    public String postedAdvDetail() {
        BindingContainer bc = ADFUtil.getBindings();
        OperationBinding binding = bc.getOperationBinding("filterPostedAdvVoucherDetail");
        binding.execute();
        ADFUtil.showPopup(postedAdvpopupBinding, true);
        return null;
    }

    public void setUnpostedPopup(RichPopup unpostedPopup) {
        this.unpostedPopup = unpostedPopup;
    }

    public RichPopup getUnpostedPopup() {
        return unpostedPopup;
    }

    public void setTotalTrxAmountBinding(RichOutputText totalTrxAmountBinding) {
        this.totalTrxAmountBinding = totalTrxAmountBinding;
    }

    public RichOutputText getTotalTrxAmountBinding() {
        return totalTrxAmountBinding;
    }

    public void setPostedAmountBinding(RichPopup postedAmountBinding) {
        this.postedAmountBinding = postedAmountBinding;
    }

    public RichPopup getPostedAmountBinding() {
        return postedAmountBinding;
    }


    public void setUnpostedDetailBinding(RichPopup unpostedDetailBinding) {
        this.unpostedDetailBinding = unpostedDetailBinding;
    }

    public RichPopup getUnpostedDetailBinding() {
        return unpostedDetailBinding;
    }


    public void setFormbinding(RichPanelFormLayout formbinding) {
        this.formbinding = formbinding;
    }

    public RichPanelFormLayout getFormbinding() {
        return formbinding;
    }

    public void setPostedAdvpopupBinding(RichPopup postedAdvpopupBinding) {
        this.postedAdvpopupBinding = postedAdvpopupBinding;
    }

    public RichPopup getPostedAdvpopupBinding() {
        return postedAdvpopupBinding;
    }

    public void setBaseAmount(Number baseAmount) {
        this.baseAmount = baseAmount;
    }

    public Number getBaseAmount() {
        return baseAmount;
    }

    public void setAdjTableBinding(RichTable adjTableBinding) {
        this.adjTableBinding = adjTableBinding;
    }

    public RichTable getAdjTableBinding() {
        return adjTableBinding;
    }

    public void setZero(Number zero) {
        this.zero = zero;
    }

    public Number getZero() {
        return zero;
    }

    public void setDtlTableBinding(RichTable dtlTableBinding) {
        this.dtlTableBinding = dtlTableBinding;
    }

    public RichTable getDtlTableBinding() {
        return dtlTableBinding;
    }

    public void setEntityBinding(RichSelectBooleanCheckbox entityBinding) {
        this.entityBinding = entityBinding;
    }

    public RichSelectBooleanCheckbox getEntityBinding() {
        return entityBinding;
    }

    public void setAdvancePopupBinding(RichPopup advancePopupBinding) {
        this.advancePopupBinding = advancePopupBinding;
    }

    public RichPopup getAdvancePopupBinding() {
        return advancePopupBinding;
    }

    public void setPanelCollectionBinding(RichPanelCollection panelCollectionBinding) {
        this.panelCollectionBinding = panelCollectionBinding;
    }

    public RichPanelCollection getPanelCollectionBinding() {
        return panelCollectionBinding;
    }

    public void projectVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...

        if (valueChangeEvent.getNewValue() != null) {
            ADFBeanUtils.callBindingsMethod("setProject", new Object[] { valueChangeEvent.getNewValue() }, new Object[] {
                                            "mode" });
        }
    }

    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String msg = "N";
        if (valueExp != null) {
            msg = valueExp.getValue(elContext).toString();
        }
        return msg;
    }

    public void setTvouCurrIdTrxBinding(RichSelectOneChoice tvouCurrIdTrxBinding) {
        this.tvouCurrIdTrxBinding = tvouCurrIdTrxBinding;
    }

    public RichSelectOneChoice getTvouCurrIdTrxBinding() {
        return tvouCurrIdTrxBinding;
    }


    public void setTvouAdjCurrIdSpBind(RichOutputText tvouAdjCurrIdSpBind) {
        this.tvouAdjCurrIdSpBind = tvouAdjCurrIdSpBind;
    }

    public RichOutputText getTvouAdjCurrIdSpBind() {
        return tvouAdjCurrIdSpBind;
    }
}
