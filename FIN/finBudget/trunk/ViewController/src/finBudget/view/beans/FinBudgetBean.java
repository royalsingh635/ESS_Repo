package finBudget.view.beans;

import finBudget.model.services.FinBudgetAMImpl;
import finBudget.model.views.OrgBudgetCcVOImpl;
import finBudget.model.views.OrgBudgetVOImpl;

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
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.faces.bi.component.graph.UIGraph;
import oracle.adf.view.faces.bi.event.ClickEvent;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.dss.dataView.ComponentHandle;
import oracle.dss.dataView.DataComponentHandle;

import oracle.jbo.JboException;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


// ---------------------------------------------------------------------
// ---    File Added By Priyank Khare.
// ---    Thu Apr 05 14:10:16 IST 2013
// ---    Custom code have being added for the controlled functionality.
// ---    Warning: Do not modify method unnecessarily.
// ---------------------------------------------------------------------

public class FinBudgetBean {

    private RichPopup costCenterPopUp;
    private String modeFlag = "";
    private boolean removeFlag = false;
    private RichInputListOfValues chartofAC;
    private RichSelectOneChoice fyId;
    private static int INTEGER = Types.INTEGER;
    private static int VARCHAR = Types.VARCHAR;
    private static int BOOLEAN = Types.BOOLEAN;
    private RichTable costCenterTable;
    private RichInputText budgetAmt;
    private RichInputText actBudgetAmt;
    private RichInputText actBudgetTyp;
    private RichSelectOneChoice monId;
    private Number cc_act_bgt_amt=new Number(0);
    private String cc_act_bgt_typ;
    private UIGraph pieForAssignedBgt;
    private UIGraph pieForActualBgt;
    private UIGraph barForActualAndAssignedBgt;
    private RichSelectOneChoice graphTypeBind;
    private RichPopup fyBgtDtlPopUp;
    Integer count =(Integer)getBindings().getOperationBinding("on_load_page").execute();
    private RichShowDetailItem graphDetailItemBinding;

    public FinBudgetBean() {
    }

    public String allocateBudgetActionButton() {

        OperationBinding createOpBAddress = getBindings().getOperationBinding("Createwithparameters");
        createOpBAddress.execute();
        setModeFlag("C");
        return null;
    }

    public String editButtonAction() {

        setModeFlag("E");
        return null;
    }
    
    public Object resolvElDCMsg(String data) {
               FacesContext fc = FacesContext.getCurrentInstance();
               Application app = fc.getApplication();
               ExpressionFactory elFactory = app.getExpressionFactory();
               ELContext elContext = fc.getELContext();
               ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
               return valueExp.getValue(elContext);
           }
    
    public String saveButtonAction() {

        OrgBudgetVOImpl orgBudgetVO = getAm().getOrgBudget();

        if (orgBudgetVO.getEstimatedRowCount() != 0) {

            Row orgBudgetRow = orgBudgetVO.getCurrentRow();

            OrgBudgetCcVOImpl orgBudgetCcVO = getAm().getOrgBudgetCc();
            orgBudgetCcVO.executeQuery();

            if (orgBudgetRow.getAttribute("CoaId") == null) {
                System.out.println("coa-------->"+orgBudgetRow.getAttribute("CoaId"));
    System.out.println("Coa reqd");
                FacesMessage errMsg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.343']}").toString()); //Chart of Account is required
                errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(chartofAC.getClientId(), errMsg);

            } else if (orgBudgetRow.getAttribute("FyId") == null) {

                FacesMessage errMsg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.350']}").toString());
                errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(fyId.getClientId(), errMsg);

            } else if (orgBudgetRow.getAttribute("MonId") == null) {

                FacesMessage errMsg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.351']}").toString());
                errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(monId.getClientId(), errMsg);

            }

            else if (getCcBudgetAmtTyp().equals(getCoaBudgetTyp()) && getCcBudget().compareTo(getCoaBudget()) == 1) {

                FacesMessage errMsg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.352']}").toString());
                errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(budgetAmt.getClientId(), errMsg);
            } else {

                getActualCoaBudget();
                AdfFacesContext.getCurrentInstance().addPartialTarget(actBudgetAmt);
                AdfFacesContext.getCurrentInstance().addPartialTarget(actBudgetTyp);
                getAm().getDBTransaction().commit();
                setModeFlag("V");

                Integer coa_id;
                Integer fy_id;
                Integer mon_id;
                coa_id = Integer.parseInt(orgBudgetRow.getAttribute("CoaId").toString());
                fy_id = Integer.parseInt(orgBudgetRow.getAttribute("FyId").toString());
                mon_id = Integer.parseInt(orgBudgetRow.getAttribute("MonId").toString());
                RequestContext.getCurrentInstance().getPageFlowScope().put("CoaIdParameter", coa_id);
                RequestContext.getCurrentInstance().getPageFlowScope().put("FyIdparameter", fy_id);
                RequestContext.getCurrentInstance().getPageFlowScope().put("MonIdParameter", mon_id);

                System.out.println("CoaIdParameter----------->" + resolvEl("#{pageFlowScope.CoaIdParameter}"));
                System.out.println("FyIdparameter----------->" + resolvEl("#{pageFlowScope.FyIdparameter}"));
                System.out.println("MonIdParameter----------->" + resolvEl("#{pageFlowScope.MonIdParameter}"));
                return "save";
            }
        }

      return null;
    }

    public String cancelButtonAction() {

        getAm().getDBTransaction().rollback();
        getAm().getOrgBudget().executeQuery();
        setModeFlag("");
        return "back";
    }

    public String addCostCenterButtonAction() {
        setModeFlag("E");
        setRemoveFlag(true);
        OrgBudgetVOImpl orgBudgetVO = getAm().getOrgBudget();

        if (orgBudgetVO.getEstimatedRowCount() != 0) {
            //System.out.println("Row Count--------->"+orgBudgetVO.getEstimatedRowCount());
            Row orgBudgetRow = orgBudgetVO.getCurrentRow();
            

            // getCostCenterActBudget();

            OrgBudgetCcVOImpl orgBudgetCcVO = getAm().getOrgBudgetCc();
            orgBudgetCcVO.executeQuery();

            if (orgBudgetRow.getAttribute("CoaId") == null) {

                FacesMessage errMsg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.343']}").toString());//Chart of Account is required
                errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(chartofAC.getClientId(), errMsg);

            } else if (orgBudgetRow.getAttribute("FyId") == null) {

                FacesMessage errMsg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.350']}").toString());//Financial Year is required
                errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(fyId.getClientId(), errMsg);

            } else if (orgBudgetRow.getAttribute("MonId") == null) {

                FacesMessage errMsg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.351']}").toString());
                errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(monId.getClientId(), errMsg);

            } else {

                OperationBinding createOpBAddress = getBindings().getOperationBinding("CreateInsert");
                createOpBAddress.execute();

            }

            showPopup(costCenterPopUp, true);
        }
        return null;
    }

    public void costCenterPopupDialogListener(DialogEvent dialogEvent) {

        if (dialogEvent.getOutcome().name().equals("ok")) {

            System.out.println("-----------After ok in popup dialog listener---------------");
            getCostCenterActBudget();
            AdfFacesContext.getCurrentInstance().addPartialTarget(costCenterTable);

        } else if (dialogEvent.getOutcome().name().equals("cancel")) {

            /** Remove the row created by create insert operation on cancel  **/

            if (removeFlag == true) {

                OperationBinding createOpBAddress = getBindings().getOperationBinding("Rollback");
                createOpBAddress.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(costCenterTable);

            }
        }
    }

    public void costCenterCancelListener(PopupCanceledEvent popupCanceledEvent) {


        if (removeFlag == true) {

            //OperationBinding createOpBAddress = getBindings().getOperationBinding("Delete");
            OperationBinding createOpBAddress = getBindings().getOperationBinding("Rollback");
            createOpBAddress.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(costCenterTable);

        }
    }

    public String editCostCenter() {
        if (modeFlag.equals("E")) {
            setRemoveFlag(false);
            showPopup(costCenterPopUp, true);
        }
        return null;
    }

    public void ccPieGraphClickListener(ClickEvent clickEvent) {

        if (modeFlag.equals("E")) {
            ComponentHandle handle = clickEvent.getComponentHandle();
            if (handle instanceof DataComponentHandle) {
                DataComponentHandle dhandle = (DataComponentHandle)handle;
                Key key = (Key)dhandle.getValue(DataComponentHandle.ROW_KEY);
                ViewObjectImpl orgBudgetCcVo = getAm().getOrgBudgetCc();
                //orgBudgetCcVo.getRow(key);

                BindingContainer bindings = getBindings();
                DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("OrgBudgetCcIterator");
                parentIter.setCurrentRowWithKey(key.toStringFormat(true));

                setRemoveFlag(false);
                showPopup(costCenterPopUp, true);
            }
        }
    }

    public String removeCostCenterAction() {

        OperationBinding createOpBAddress = getBindings().getOperationBinding("Delete");
        createOpBAddress.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(costCenterTable);

        return null;
    }
   

    public void getActualCoaBudget() {

        OrgBudgetVOImpl orgBudgetVO = getAm().getOrgBudget();
        String hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer slocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String org_Id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Integer coa_id;
        Integer fy_id;
        Integer mon_id;

       /*  try {
            hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
            cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
            slocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
            org_Id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        } catch (NumberFormatException nfe) {

            nfe.printStackTrace();
        } */


        if (orgBudgetVO.getRowCount() > 0) {
            Row orgBudgetRow = orgBudgetVO.getCurrentRow();
            if (orgBudgetRow.getAttribute("CoaId") != null && orgBudgetRow.getAttribute("FyId") != null) {
                coa_id = Integer.parseInt(orgBudgetRow.getAttribute("CoaId").toString());
                fy_id = Integer.parseInt(orgBudgetRow.getAttribute("FyId").toString());
                mon_id = Integer.parseInt(orgBudgetRow.getAttribute("MonId").toString());
                System.out.println("hoOrgId " + hoOrgId + "" + " org_Id " + org_Id + " slocId " + slocId + " cldId " +
                                   cldId + " coa_id " + coa_id + " fy_id " + fy_id + " mon_id" + mon_id);
                try {
                    callStoredProcedure("FIN.GET_COA_ACTUAL_BUDGET(?,?,?,?,?,?,?,?,?)",
                                        new Object[] { slocId, hoOrgId, cldId, org_Id, coa_id, fy_id, mon_id });
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
                orgBudgetRow.setAttribute("CoaActBudget", actBudgetAmt.getValue());
                orgBudgetRow.setAttribute("CoaActBudgetType", actBudgetTyp.getValue());
            }
        }
    }

    public Void getCostCenterActBudget() {

        OrgBudgetCcVOImpl orgBudgetCcVO = getAm().getOrgBudgetCc();
        String hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");;
        String cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");;
        Integer slocId =  Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());;
        String org_Id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Integer coa_id;
        Integer fy_id;
        Integer mon_id;
        Integer doc_id;
        Integer ent_id;
        Integer ent_comp_id;
        String col_id;
        Integer return_val;
/* 
        try {
            hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
            cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
            slocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
            org_Id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        } catch (NumberFormatException nfe) {

            nfe.printStackTrace();
        } */


        if (orgBudgetCcVO.getRowCount() > 0) {

            Row orgBudgetCcRow = orgBudgetCcVO.getCurrentRow();

            if (orgBudgetCcRow.getAttribute("CoaId") != null && orgBudgetCcRow.getAttribute("FyId") != null) {

                coa_id = Integer.parseInt(orgBudgetCcRow.getAttribute("CoaId").toString());
                fy_id = Integer.parseInt(orgBudgetCcRow.getAttribute("FyId").toString());
                ent_id = Integer.parseInt(orgBudgetCcRow.getAttribute("CcEntId").toString());
                ent_comp_id = Integer.parseInt(orgBudgetCcRow.getAttribute("CcEntCompId").toString());
                col_id = (orgBudgetCcRow.getAttribute("CcColId").toString());
                mon_id = Integer.parseInt(orgBudgetCcRow.getAttribute("MonId").toString());

                System.out.println("hoOrgId " + hoOrgId + "" + " org_Id " + org_Id + " slocId " + slocId + " cldId " +
                                   cldId + " coa_id " + coa_id + " fy_id " + fy_id + " mon_id " + mon_id + " ent_id " +
                                   ent_id + " ent_comp_id " + ent_comp_id + " col_id " + col_id);
                try {

                    return_val =
                            Integer.parseInt((callStoredProcedure1("FIN.PROC_CC_ACTUAL(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                                   new Object[] { hoOrgId, org_Id, slocId, cldId, 56,
                                                                                  coa_id, fy_id, mon_id, ent_id,
                                                                                  ent_comp_id, col_id })).toString());
                } catch (Exception sqle) {
                    sqle.printStackTrace();
                }
                orgBudgetCcRow.setAttribute("CoaCcActBgt", cc_act_bgt_amt);
                orgBudgetCcRow.setAttribute("CoaCcActBgtType", cc_act_bgt_typ);
            }
        }
        return null;
    }

    public Number getCoaBudget() {

        Number budgetAmt = new Number(0);
        Row orgBudgetRow = getAm().getOrgBudget().getCurrentRow();
        budgetAmt = (Number)(orgBudgetRow.getAttribute("CoaBudget"));
        System.out.println("Budget for Coa is--------->" + budgetAmt);
        return budgetAmt;
    }

    public String getCoaBudgetTyp() {

        String budgetTyp = "Dr";
        Row orgBudgetRow = getAm().getOrgBudget().getCurrentRow();
        budgetTyp = orgBudgetRow.getAttribute("CoaBudgetType").toString();


        System.out.println("Budget typ for Coa is--------->" + budgetTyp);
        return budgetTyp;

    }

    public Number getCcBudget() {

        Number budgetAmt = new Number(0);
        Number budgetAmtDr = new Number(0);

        ViewObject orgBudgetCcVO = getAm().getOrgBudgetCc();
        RowSetIterator rit = orgBudgetCcVO.createRowSetIterator(null);

        if (rit.first() != null && rit.first().getAttribute("CoaCcBudget") != null) {
            if (rit.first().getAttribute("CoaCcBudgetType").equals("Cr")) {

                budgetAmt = (Number)rit.first().getAttribute("CoaCcBudget");

            }
        }
        while (rit.hasNext()) {
            Row ritRow = rit.next();
            if (ritRow.getAttribute("CoaCcBudget") != null && ritRow.getAttribute("CoaCcBudgetType").equals("Cr"))
                budgetAmt = (Number)ritRow.getAttribute("CoaCcBudget");

        }
        rit.closeRowSetIterator();

        RowSetIterator rit1 = orgBudgetCcVO.createRowSetIterator(null);

        if (rit1.first() != null && rit1.first().getAttribute("CoaCcBudget") != null) {
            if (rit1.first().getAttribute("CoaCcBudgetType").equals("Dr")) {

                budgetAmtDr = (Number)rit1.first().getAttribute("CoaCcBudget");

            }
        }
        while (rit1.hasNext()) {
            Row ritRow = rit1.next();
            if (ritRow.getAttribute("CoaCcBudget") != null && ritRow.getAttribute("CoaCcBudgetType").equals("Dr"))
                budgetAmtDr = (Number)ritRow.getAttribute("CoaCcBudget");

        }
        rit1.closeRowSetIterator();


        if (budgetAmtDr.compareTo(budgetAmt) == 0)
            budgetAmt = new Number(0);
        else if (budgetAmtDr.compareTo(budgetAmt) == 1)
            budgetAmt = (Number)budgetAmtDr.minus(budgetAmt);
        else
            budgetAmt = (Number)budgetAmt.minus(budgetAmtDr);

        System.out.println("Cost center budget---------------->" + budgetAmt);
        return budgetAmt;
    }


    public String getCcBudgetAmtTyp() {

        String budgetAmtTyp = "Dr";
        Number budgetAmt = new Number(0);
        Number budgetAmtDr = new Number(0);

        ViewObject orgBudgetCcVO = getAm().getOrgBudgetCc();
        RowSetIterator rit = orgBudgetCcVO.createRowSetIterator(null);

        if (rit.first() != null && rit.first().getAttribute("CoaCcBudget") != null) {
            if (rit.first().getAttribute("CoaCcBudgetType").equals("Cr")) {

                budgetAmt = (Number)rit.first().getAttribute("CoaCcBudget");

            }
        }
        while (rit.hasNext()) {
            Row ritRow = rit.next();
            if (ritRow.getAttribute("CoaCcBudget") != null && ritRow.getAttribute("CoaCcBudgetType").equals("Cr"))
                budgetAmt = (Number)ritRow.getAttribute("CoaCcBudget");

        }
        rit.closeRowSetIterator();

        RowSetIterator rit1 = orgBudgetCcVO.createRowSetIterator(null);

        if (rit1.first() != null && rit1.first().getAttribute("CoaCcBudget") != null) {
            if (rit1.first().getAttribute("CoaCcBudgetType").equals("Dr")) {

                budgetAmtDr = (Number)rit1.first().getAttribute("CoaCcBudget");

            }
        }
        while (rit1.hasNext()) {
            Row ritRow = rit1.next();
            if (ritRow.getAttribute("CoaCcBudget") != null && ritRow.getAttribute("CoaCcBudgetType").equals("Dr"))
                budgetAmtDr = (Number)ritRow.getAttribute("CoaCcBudget");

        }
        rit1.closeRowSetIterator();

        if (budgetAmtDr.compareTo(budgetAmt) == 0)
            budgetAmtTyp = "Dr";
        else if (budgetAmtDr.compareTo(budgetAmt) == 1)
            budgetAmtTyp = "Dr";
        else
            budgetAmtTyp = "Cr";

        System.out.println("Cost center budget type---------------->" + budgetAmtTyp);
        return budgetAmtTyp;

    }


    public void budgetAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number budgetAmt = new Number(0);

        try {
            budgetAmt = (Number)object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (budgetAmt.isZero() || budgetAmt.getValue() < 0) {
            String msg = resolvElDCMsg("#{bundle['MSG.353']}").toString();
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }

    }

    public String refreshAction() {
        getActualCoaBudget();
        AdfFacesContext.getCurrentInstance().addPartialTarget(actBudgetAmt);
        AdfFacesContext.getCurrentInstance().addPartialTarget(actBudgetTyp);
        getAm().getDBTransaction().commit();
        return null;
    }

    public String viewBgtForCurrFY() {
        showPopup(fyBgtDtlPopUp, true);
        return null;
    }

    public void graphTypValueChangeListener(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue().equals("pie")) {
            pieForAssignedBgt.setRendered(true);
            pieForActualBgt.setRendered(true);
            barForActualAndAssignedBgt.setRendered(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pieForAssignedBgt);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pieForActualBgt);
            AdfFacesContext.getCurrentInstance().addPartialTarget(barForActualAndAssignedBgt);

        } else if (valueChangeEvent.getNewValue().equals("bar")) {
            pieForAssignedBgt.setRendered(false);
            pieForActualBgt.setRendered(false);
            barForActualAndAssignedBgt.setRendered(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pieForAssignedBgt);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pieForActualBgt);
            AdfFacesContext.getCurrentInstance().addPartialTarget(barForActualAndAssignedBgt);
        } else {
            pieForAssignedBgt.setRendered(false);
            pieForActualBgt.setRendered(false);
            barForActualAndAssignedBgt.setRendered(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pieForAssignedBgt);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pieForActualBgt);
            AdfFacesContext.getCurrentInstance().addPartialTarget(barForActualAndAssignedBgt);
        }
    }

    public String fyMonthBgt() {
      
        ViewObjectImpl finBgtSearchDtl = getAm().getFinBudgetSearchDtl();
        Row finBgtSearchDtlRow = finBgtSearchDtl.getCurrentRow();
        Integer coa_id;
        Integer fy_id;
        Integer mon_id;
        coa_id = Integer.parseInt(finBgtSearchDtlRow.getAttribute("CoaId").toString());
        fy_id = Integer.parseInt(finBgtSearchDtlRow.getAttribute("FyId").toString());
        mon_id = Integer.parseInt(finBgtSearchDtlRow.getAttribute("MonId").toString());
        RequestContext.getCurrentInstance().getPageFlowScope().put("CoaIdParameter", coa_id);
        RequestContext.getCurrentInstance().getPageFlowScope().put("FyIdparameter", fy_id);
        RequestContext.getCurrentInstance().getPageFlowScope().put("MonIdParameter", mon_id);
       
        System.out.println("CoaIdParameter----------->" + resolvEl("#{pageFlowScope.CoaIdParameter}"));
        System.out.println("FyIdparameter----------->" + resolvEl("#{pageFlowScope.FyIdparameter}"));
        System.out.println("MonIdParameter----------->" + resolvEl("#{pageFlowScope.MonIdParameter}"));
        
        fyBgtDtlPopUp.hide();
        return "edit";
    }

    /** Manual row selection listener for yearly budget Distribution **/
   // Currently not in use
    
    public void fyBgtSelectionListener(SelectionEvent selectionEvent) {
        ADFUtil.invokeEL("#{bindings.FinBudgetSearchDtl.collectionModel.makeCurrent}", new Class[] { SelectionEvent.class },
                         new Object[] { selectionEvent });     
        fyBgtDtlPopUp.hide();
        
    }

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

    public FinBudgetAMImpl getAm() {
        FinBudgetAMImpl am = (FinBudgetAMImpl)resolvElDC("FinBudgetAMDataControl");
        return am;
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    protected Object callStoredProcedure1(String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {
            // 1. Create a JDBC CallableStatement
            st = getAm().getDBTransaction().createCallableStatement("begin " + stmt + "; end ;", 0);
            // 2. Register the first bind variable for the return value
            st.setObject(1, bindVars[0]);
            st.setObject(2, bindVars[1]);
            st.setObject(3, bindVars[2]);
            st.setObject(4, bindVars[3]);
            st.setObject(5, bindVars[4]);
            st.setObject(6, bindVars[5]);
            st.setObject(7, bindVars[6]);
            st.setObject(8, bindVars[7]);
            st.setObject(9, bindVars[8]);
            st.setObject(10, bindVars[9]);
            st.setObject(11, bindVars[10]);
            st.registerOutParameter(12, Types.NUMERIC);
            st.registerOutParameter(13, Types.VARCHAR);
            st.registerOutParameter(14, Types.NUMERIC);
            // 5. Set the value of user-supplied bind vars in the stmt

            st.executeUpdate();
            try {
                if(st.getObject(13)!=null){
                cc_act_bgt_typ = (st.getObject(13)).toString();
                }
                if(st.getObject(12)!=null){
                cc_act_bgt_amt = new Number(st.getObject(12));
                }
               

                System.out.println("amt---------------->" + st.getObject(12));
                System.out.println("amt typ------------>" + (st.getObject(13)));

            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }

            return st.getObject(14);
        } catch (SQLException e) {
            int end = e.getMessage().indexOf("\n");
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    // 7. Close the statement
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();

                }
            }
        }
    }

    public void callStoredProcedure(String stmt, Object[] bindVars) throws SQLException {

        CallableStatement st = null;
        try {
            // 1. Create a JDBC CallableStatement
            st = getAm().getDBTransaction().createCallableStatement("begin " + stmt + "; end ;", 0);
            // 2. Register the first bind variable for the return value
            st.setObject(1, bindVars[0]);
            st.setObject(2, bindVars[1]);
            st.setObject(3, bindVars[2]);
            st.setObject(4, bindVars[3]);
            st.setObject(5, bindVars[4]);
            st.setObject(6, bindVars[5]);
            st.setObject(7, bindVars[6]);
            st.registerOutParameter(8, Types.NUMERIC);
            st.registerOutParameter(9, Types.VARCHAR);
            // 5. Set the value of user-supplied bind vars in the stmt

            st.executeUpdate();
            try {

                Number bgtAmt;
                String bgtTyp;
                bgtAmt = new Number(st.getObject(8));
                bgtTyp = (st.getObject(9)).toString();
                actBudgetAmt.setValue(bgtAmt);
                actBudgetTyp.setValue(bgtTyp);
                System.out.println("amt---------------->" + st.getObject(8));
                System.out.println("amt typ------------>" + (st.getObject(9).toString()));

            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            }

        } catch (SQLException e) {
            int end = e.getMessage().indexOf("\n");
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    // 7. Close the statement
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();

                }
            }
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


    public void setCostCenterPopUp(RichPopup costCenterPopUp) {
        this.costCenterPopUp = costCenterPopUp;
    }

    public RichPopup getCostCenterPopUp() {
        return costCenterPopUp;
    }


    public void setModeFlag(String modeFlag) {
        this.modeFlag = modeFlag;
    }

    public String getModeFlag() {
        if (modeFlag.equals("")) {

            return resolvEl("#{pageFlowScope.callingFormMode}");
        } else
            return modeFlag;
    }

    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String msg = valueExp.getValue(elContext).toString();
        return msg;
    }

    public void setChartofAC(RichInputListOfValues chartofAC) {
        this.chartofAC = chartofAC;
    }

    public RichInputListOfValues getChartofAC() {
        return chartofAC;
    }

    public void setFyId(RichSelectOneChoice fyId) {
        this.fyId = fyId;
    }

    public RichSelectOneChoice getFyId() {
        return fyId;
    }


    public void setCostCenterTable(RichTable costCenterTable) {
        this.costCenterTable = costCenterTable;
    }

    public RichTable getCostCenterTable() {
        return costCenterTable;
    }

    public void setRemoveFlag(boolean removeFlag) {
        this.removeFlag = removeFlag;
    }

    public boolean isRemoveFlag() {
        return removeFlag;
    }


    public void setBudgetAmt(RichInputText budgetAmt) {
        this.budgetAmt = budgetAmt;
    }

    public RichInputText getBudgetAmt() {
        return budgetAmt;
    }

    public void setActBudgetAmt(RichInputText actBudgetAmt) {
        this.actBudgetAmt = actBudgetAmt;
    }

    public RichInputText getActBudgetAmt() {
        return actBudgetAmt;
    }

    public void setActBudgetTyp(RichInputText actBudgetTyp) {
        this.actBudgetTyp = actBudgetTyp;
    }

    public RichInputText getActBudgetTyp() {
        return actBudgetTyp;
    }


    public void setMonId(RichSelectOneChoice monId) {
        this.monId = monId;
    }

    public RichSelectOneChoice getMonId() {
        return monId;
    }

    public void setCc_act_bgt_amt(Number cc_act_bgt_amt) {
        this.cc_act_bgt_amt = cc_act_bgt_amt;
    }

    public Number getCc_act_bgt_amt() {
        return cc_act_bgt_amt;
    }

    public void setCc_act_bgt_typ(String cc_act_bgt_typ) {
        this.cc_act_bgt_typ = cc_act_bgt_typ;
    }

    public String getCc_act_bgt_typ() {
        return cc_act_bgt_typ;
    }

    public void setPieForAssignedBgt(UIGraph pieForAssignedBgt) {
        this.pieForAssignedBgt = pieForAssignedBgt;
    }

    public UIGraph getPieForAssignedBgt() {
        return pieForAssignedBgt;
    }


    public void setPieForActualBgt(UIGraph pieForActualBgt) {
        this.pieForActualBgt = pieForActualBgt;
    }

    public UIGraph getPieForActualBgt() {
        return pieForActualBgt;
    }

    public void setBarForActualAndAssignedBgt(UIGraph barForActualAndAssignedBgt) {
        this.barForActualAndAssignedBgt = barForActualAndAssignedBgt;
    }

    public UIGraph getBarForActualAndAssignedBgt() {
        return barForActualAndAssignedBgt;
    }

    public void setGraphTypeBind(RichSelectOneChoice graphTypeBind) {
        this.graphTypeBind = graphTypeBind;
    }

    public RichSelectOneChoice getGraphTypeBind() {
        return graphTypeBind;
    }

    public void setfyBgtDtlPopUp(RichPopup fyBgtDtlPopUp) {
        this.fyBgtDtlPopUp = fyBgtDtlPopUp;
    }

    public RichPopup getfyBgtDtlPopUp() {
        return fyBgtDtlPopUp;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void setGraphDetailItemBinding(RichShowDetailItem graphDetailItemBinding) {
        this.graphDetailItemBinding = graphDetailItemBinding;
    }

    public RichShowDetailItem getGraphDetailItemBinding() {
        return graphDetailItemBinding;
    }
}
