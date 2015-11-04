package appOpeningBalance.view.bean;

import appOpeningBalance.model.service.AppOpeningBalanceAMImpl;
import appOpeningBalance.model.views.CoaLOVImpl;
import appOpeningBalance.model.views.GlLinesVOImpl;
import appOpeningBalance.model.views.GlLinesVORowImpl;

import appOpeningBalance.model.views.SearchFYVOImpl;

import java.io.Serializable;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.SQLException;

import java.sql.Types;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.component.rich.nav.RichCommandLink;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.adf.view.rich.event.QueryEvent;
import oracle.adf.view.rich.model.FilterableQueryDescriptor;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.ValidationException;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.rules.JboPrecisionScaleValidator;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class OpeningBalBean implements Serializable {
    private boolean required = false;
    private RichPopup glPopup;
    private String Mode = "V";
    private RichTable glTable;
    // private RichSelectOneChoice coaIdSearch;
    private Date maxDate = (Date)Date.getCurrentDate();
    private String RetValue = resolvEl("#{bundle['MSG.158']}").toString(); //	Initialised


    private Integer Sloc_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());

    private String Org_Id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
    private String Cld_Id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
    private String HoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
    private RichPopup costCenterPopup;
    private String cost_center_src = "L";
    private BigDecimal cost_center_amount = new BigDecimal(0);
    private Number balance = new Number(0);
    private String balanceTyp = null;
    private Integer GlBlsl_No = 1;
    private Integer GlBlDocId = 64;
    private Integer GlBlFyYr = 3;
    private String SearchNResetMode = "R";
    private RichInputText balanceOp;
    private RichInputDate vouDtGlBinding;
    private RichSelectOneChoice typeIdGlBinding;
    private RichInputListOfValues ccGlBinding;
    private RichSelectOneChoice amtTypGlBinding;
    private RichSelectOneChoice coaIdGlBinding;
    private RichInputText glCcRateBIndVar;
    private RichInputText glBaseAmtBindVar;
    private Number AmountBs = new Number(0);
    Number orgOpBalGl = new Number(0);
    String orgOpbalGlTyp = "Dr";
    private RichCommandLink costCenterLink;
    private Boolean isCostCenter;
    private RichSelectOneChoice fyIdSearch;
    private Integer onloadVal;
    private RichInputListOfValues coaBinding;
    private RichCommandImageLink buttonBinding;
    private RichInputText coaIdtransBinding;
    private RichCommandImageLink searchButtonBindVal;
    private RichInputText invNoPgBind;
    private RichInputDate invDtPgBind;
    private RichInputDate dueDtPgBind;

    public OpeningBalBean() {

    }


    protected Object callStoredFUNC(String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {
            AppOpeningBalanceAMImpl am = (AppOpeningBalanceAMImpl)resolvElDC("AppOpeningBalanceAMDataControl");
            st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);

            st.registerOutParameter(1, Types.INTEGER);
            st.setObject(2, bindVars[0]);
            st.setObject(3, bindVars[1]);
            st.setObject(4, bindVars[2]);
            st.setObject(5, bindVars[3]);
            st.registerOutParameter(6, Types.DATE);
            st.registerOutParameter(7, Types.VARCHAR);

            st.executeUpdate();
            if (st.getObject(6) != null) {
                maxDate = new Date(st.getObject(6));
            } else {
                maxDate = (Date)Date.getCurrentDate();
            }


            return maxDate;

        } catch (SQLException e) {
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    throw new JboException(e);
                }
            }
        }
    }
    // private String allowAddEdit;
    // private String BCStatus;

    public String CreateAction() {

        AppOpeningBalanceAMImpl am1 = (AppOpeningBalanceAMImpl)resolvElDC("AppOpeningBalanceAMDataControl");
        Object fyobj = fyIdSearch.getValue();
        Object coaObj = coaBinding.getValue();
        Object coaIdObj = coaIdtransBinding.getValue();
        Integer coaIdParam = null;
        Integer fyIdParam = null;
        if (resolvEl("#{pageFlowScope.COA_ID}") == null || resolvEl("#{pageFlowScope.FY_ID}") == null) {

            if (fyobj == null || fyobj.toString().length() <= 0 || coaObj == null || coaObj.toString().length() <= 0) {
                String msg = resolvEl("#{bundle['LBL.2946']}").toString(); //Fill the Mandatory Fields.
                FacesMessage fm = new FacesMessage(msg);
                fm.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, fm);
                return null;
            }

        }

        if (resolvEl("#{pageFlowScope.COA_ID}") != null) {
            coaIdParam = Integer.parseInt(resolvEl("#{pageFlowScope.COA_ID}").toString());
        } else {
            coaIdParam = Integer.parseInt(coaIdObj.toString());
        }
        if (resolvEl("#{pageFlowScope.FY_ID}") != null) {
            fyIdParam = Integer.parseInt(resolvEl("#{pageFlowScope.FY_ID}").toString());
        } else {
            fyIdParam = Integer.parseInt(fyobj.toString());
        }

        System.out.println("Cld_Id = " + Cld_Id + "Sloc_Id = " + Sloc_Id + " HoOrgId = " + HoOrgId + " Org_Id = " +
                           Org_Id + " coaId = " + coaIdParam + " fyId = " + fyIdParam);
        Object allow =
            callStoredFunction2(Types.VARCHAR, "fin.fn_check_ob_bal_allow(?,?,?,?,?,?,?)", new Object[] { Cld_Id,
                                                                                                          Sloc_Id,
                                                                                                          HoOrgId,
                                                                                                          Org_Id,
                                                                                                          coaIdParam,
                                                                                                          fyIdParam,
                                                                                                          "V" });
        System.out.println("Allow  = " + allow + "--------------->>>>");
        String BCStatus = "";
        String allowAddEdit = "";


        // called function when FY_ORG_ID isn't check in Organization setup.
        Object allowForOrg =
            callStoredFunction2(Types.VARCHAR, "fin.fn_CHK_OP_BAL_ALL_ORG(?,?,?)", new Object[] { Cld_Id, Org_Id,
                                                                                                  fyIdParam });
        System.out.println("allowForOrg--------------------------------" + allowForOrg);


        SearchFYVOImpl fYVO = am1.getSearchFYVO1();
        if (fYVO != null) {
            fYVO.setNamedWhereClauseParam("OrgIdBindVar", Org_Id);
            fYVO.setNamedWhereClauseParam("BindFyId", fyIdParam);
            fYVO.executeQuery();
            Row[] allRowsInRange = fYVO.getAllRowsInRange();
            System.out.println("allRowsInRange = " + allRowsInRange.length);
            if (allRowsInRange.length > 0) {
                Object attribute = allRowsInRange[0].getAttribute("OrgFyBcStat");
                if (attribute != null) {
                    BCStatus = (String)attribute;
                    System.out.println("BCStatus = " + BCStatus);
                }
            }
        }

        if (allow != null && allowForOrg != null && BCStatus != null) {
         //   allow="Y";
            allowAddEdit = allow.toString();
            if (allowAddEdit.equalsIgnoreCase("N")) {
                FacesMessage fm =
                    new FacesMessage("Opening Balance Summary already exists for this COA. You cannot enter Opening Balance Vouchers!");
                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, fm);
            } else if (BCStatus.equalsIgnoreCase("Y") || BCStatus.length() == 0) {
                FacesMessage fm =
                    new FacesMessage("Entry for Opening Balance are not allowed for this Financial Year! ");
                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, fm);
            } else if (allowForOrg.toString().equalsIgnoreCase("N")) {
                FacesMessage fm = new FacesMessage("Opening Balance is not allowed for this Organizations...!!!");
                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, fm);
            } else {
                // System.out.println("value of ret vlueeeee iss=====" + RetValue);
                Date d = new Date();
                d =
  (Date)callStoredFUNC("FN_OB_VOU_CHK_DATE(?,?,?,?,?,?)", new Object[] { Cld_Id, Sloc_Id, Org_Id, fyIdSearch.getValue() });
//System.out.println("valueeeeeee of sourcrrrr issssssss=="+RetValue);
             //RetValue="Initilised";
                if (RetValue.equalsIgnoreCase("Initialised")) {
                    //  System.out.println("value of ret AFTER =====" + RetValue);
                    //   System.out.println("1243243546587689879870798");
                    OperationBinding operationBinding =
                        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Createwithparameters");
                    if (resolvEl("#{pageFlowScope.COA_ID}") == null) {
                        operationBinding.getParamsMap().put("GlCoaId", coaIdObj);
                    }
                    if (resolvEl("#{pageFlowScope.FY_ID}") == null) {
                        operationBinding.getParamsMap().put("GlFyId", fyobj);
                    }
                    operationBinding.execute();


                    ViewObjectImpl v = am1.getGlLines();
                    Row currRw = v.getCurrentRow();
                    if (currRw != null) {
                        currRw.setAttribute("GlVouDt", d);
                        currRw.setAttribute("GlCoaId", coaIdParam);
                        currRw.setAttribute("GlFyId", fyIdParam);
                    }

                    showPopup(glPopup, true);
                    Mode = "A";


                } else {
                    //   System.out.println("in edit methodddddddddddddddddddddddddddddddddd");
                    FacesMessage fm = new FacesMessage(RetValue);
                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, fm);
                }
            }
        }
        return null;
    }

    public String EditAction() {

        // callStoredFUNC("FIN.FN_OB_VOU_CHK_DATE(?,?,?,?,?)", new Object[] { Org_Id, Sloc_Id,Cld_Id });
        // commented on 28 april by amandeep

        if (RetValue.equalsIgnoreCase("Initialised")) {
            AppOpeningBalanceAMImpl am = (AppOpeningBalanceAMImpl)resolvElDC("AppOpeningBalanceAMDataControl");
            ViewObjectImpl glLinesVOImpl = am.getGlLines();
            Row curr = glLinesVOImpl.getCurrentRow();
            if (curr != null) {
                AmountBs = ((Number)curr.getAttribute("GlAmtBs"));

                showPopup(glPopup, true);
            } else {
                FacesMessage fm = new FacesMessage(resolvEl("#{bundle['MSG.120']}").toString());
                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, fm);
            }
            Mode = "E";
        } else {
            FacesMessage fm = new FacesMessage(RetValue);
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        }

        return null;
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

    public void setGlPopup(RichPopup glPopup) {
        this.glPopup = glPopup;
    }

    public RichPopup getGlPopup() {
        return glPopup;
    }

    protected Object callStoredFunction2(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        AppOpeningBalanceAMImpl am = (AppOpeningBalanceAMImpl)resolvElDC("AppOpeningBalanceAMDataControl");
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
                    throw new JboException(e);
                }
            }
        }
    }


    public void popupCancelListner(PopupCanceledEvent popupCanceledEvent) {
        Object fy = null;
        Object coa = null;
        coa = coaBinding.getValue();
        fy = fyIdSearch.getValue();
        Object coaid = coaIdtransBinding.getValue();


        /*  if (resolvEl("#{pageFlowScope.COA_ID}") == null) {
            coa= coaBinding.getValue();
            if (resolvEl("#{pageFlowScope.FY_ID}") == null) {
                fy = fyIdSearch.getValue();
            }

        } */
        OperationBinding operationBinding =
            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Rollback");
        operationBinding.execute();


        OperationBinding operationBindingexe =
            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Execute");
        operationBindingexe.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(glTable);
        Mode = "V";
        /*    if (resolvEl("#{pageFlowScope.COA_ID}") == null) {
            coaBinding.setValue(coa);
            if (resolvEl("#{pageFlowScope.FY_ID}") == null)  {
                fyIdSearch.setValue(fy);
            }
        } */
        coaBinding.setValue(coa);
        fyIdSearch.setValue(fy);
        coaIdtransBinding.setValue(coaid);
    }

    public void dialogListner(DialogEvent de) {

        if (de.getOutcome().name().equals("ok")) {
            //System.out.println("---------------------OK BUTTON CLICKED---------------------");
            AppOpeningBalanceAMImpl am = (AppOpeningBalanceAMImpl)resolvElDC("AppOpeningBalanceAMDataControl");
            ViewObjectImpl gl_lines = am.getGlLines();
            SearchFYVOImpl fYVO = am.getSearchFYVO1();
            ViewObjectImpl gl = am.getGl();
            Row currRw = gl_lines.getCurrentRow();
            GlLinesVORowImpl cuurImpl = (GlLinesVORowImpl)currRw;

            Integer coaId = (Integer)currRw.getAttribute("GlCoaId");
            Integer fyId = (Integer)currRw.getAttribute("GlFyId");
           // System.out.println("coaId = "+coaId + "     fyId = "+ fyId);
           // currRw.setAttribute("GlApplInstId", 1);
            Date vouDtGl = (Date)currRw.getAttribute("GlVouDt");
            Integer typeIdGl = (Integer)currRw.getAttribute("GlTypeId");
            Number amtSpGl = (Number)currRw.getAttribute("GlAmtSp");
            Number ccGl = (Number)currRw.getAttribute("GlCc");
            String amtTypGl = currRw.getAttribute("GlAmtTyp").toString();
            Date fyEndDate=null;
            if (coaId != null) {

                if (vouDtGl != null) {

                    if (typeIdGl != null) {

                        if (amtSpGl != null) {

                            if (ccGl != null) {

                                if (amtTypGl != null) {

                                    if (Mode.equals("A")) {
                                        // System.out.println("---   mode = A -------");
                                        String CldId = (String)currRw.getAttribute("GlCldId");
                                        Integer SlocId = (Integer)currRw.getAttribute("GlSlocId");
                                        String OrgId = (String)currRw.getAttribute("GlOrgId");
                                        //  System.out.println("----------call function fin.fn_validate_coa_op_vou_date(?,?,?,?)");
                                        //  System.out.println("CldId = "+CldId+"   SlocId = "+SlocId+"  OrgId = "+OrgId+"      vouDtGl = "+vouDtGl);

                                        /* System.out.println("CldId : " + CldId);
                                        System.out.println("SlocId :" + SlocId);
                                        System.out.println("OrgId : " + OrgId);
                                        System.out.println("vouDtGl : " + vouDtGl); */

                                        String s =
                                            callStoredFunction2(Types.VARCHAR, "fin.fn_validate_coa_op_vou_date(?,?,?,?)",
                                                                new Object[] { CldId, SlocId, OrgId,
                                                                               vouDtGl }).toString();
                                        // System.out.println("s = " + s);
                                        if (s.equals("Y")) {
                                            //    System.out.println("- commited ");
                                            OperationBinding operationBinding =
                                                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit");
                                            OperationBinding operationBindingexe =
                                                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Execute");


                                            Integer GlSlocId = (Integer)currRw.getAttribute("GlSlocId");
                                            Integer GlApplInstId = (Integer)currRw.getAttribute("GlApplInstId");
                                            Integer GlCoaId = (Integer)currRw.getAttribute("GlCoaId");
                                            String GlOrgId = (String)currRw.getAttribute("GlOrgId");
                                            String GlHoOrgId = (String)currRw.getAttribute("GlHoOrgId");
                                            String GlCldId = (String)currRw.getAttribute("GlCldId");
                                            String GlVouId = (String)currRw.getAttribute("GlVouId");
                                            Integer GlTypeId = (Integer)currRw.getAttribute("GlTypeId");
                                            Date GlVouDt = (Date)currRw.getAttribute("GlVouDt");
                                            Date GlVouDueDt = (Date)currRw.getAttribute("GlVouDueDt");
                                            Number GlTotAmtBs = (Number)currRw.getAttribute("GlAmtBs");
                                            Number GlAmtSp = (Number)currRw.getAttribute("GlAmtSp");
                                            Number GlCc = (Number)currRw.getAttribute("GlCc");
                                            String GlStat = (String)currRw.getAttribute("GlStat");
                                            String GlTypMig = (String)currRw.getAttribute("GlTxnTypMig");
                                            Integer GlCurrIdBs = (Integer)currRw.getAttribute("GlCurrIdBs");
                                            Integer GlCurrIdSp = (Integer)currRw.getAttribute("GlCurrIdSp");
                                            Integer UsrIdCreate = (Integer)currRw.getAttribute("UsrIdCreate");
                                            Integer GlFyId = (Integer)currRw.getAttribute("GlFyId");
                                            // Number opBal = (Number)currRw.getAttribute("OrgOpBal");\
                                            // System.out.println("coaId = "+coaId + "    fyId = "+ fyId);
                                            //    System.out.println("GlApplInstId = "+GlApplInstId);


                                            Row newRow = gl.createRow();
                                            newRow.setAttribute("GlSlocId", GlSlocId);
                                            newRow.setAttribute("GlApplInstId", GlApplInstId);
                                            newRow.setAttribute("GlCoaId", GlCoaId);
                                            newRow.setAttribute("GlOrgId", GlOrgId);
                                            newRow.setAttribute("GlHoOrgId", GlHoOrgId);
                                            newRow.setAttribute("GlCldId", GlCldId);
                                            newRow.setAttribute("GlVouId", GlVouId);
                                            newRow.setAttribute("GlVouDt", GlVouDt);
                                            newRow.setAttribute("GlTotAmtBs", GlTotAmtBs);
                                            newRow.setAttribute("GlAmtSp", GlAmtSp);
                                            newRow.setAttribute("GlCc", GlCc);
                                            newRow.setAttribute("GlStat", GlStat);
                                            newRow.setAttribute("GlTypMig", GlTypMig);
                                            newRow.setAttribute("GlCurrIdBs", GlCurrIdBs);
                                            newRow.setAttribute("GlCurrIdSp", GlCurrIdSp);
                                            newRow.setAttribute("UsrIdCreate", UsrIdCreate);
                                            newRow.setAttribute("GlTypeId", GlTypeId);
                                            newRow.setAttribute("GlVouDueDt", GlVouDueDt);
                                            //newRow.setAttribute("GlFyId", GlFyId);
                                            // System.out.println(coaId + "   ");


                                            gl.insertRow(newRow);

                                            operationBinding.execute();
                                            if (operationBinding.getErrors().isEmpty()) {
                                                //    System.out.println("----------parm send to app.GET_TXN_ID ==================== ");
                                                //   System.out.println("   GlCldId = "+GlCldId+"   GlSlocId = "+GlSlocId+"     GlOrgId = "+GlOrgId+"   UsrIdCreate = "+UsrIdCreate+"   56 "+"  GlTypeId = "+GlTypeId);
                                                //                                    String s = (String)callStoredFunction2(Types.VARCHAR, "APP.GET_TXN_ID (?,?,?,?,?,?)",
                                                //                                                            new Object[] { GlCldId,GlSlocId,GlOrgId,UsrIdCreate,56,GlTypeId });
                                                //                                        System.out.println("function APP.GET_TXN_ID return s = "+s);


                                                /*  System.out.println("-------------parm send to APP.INS_DISP_DOC_TAB ================= ");
                                                System.out.println("GlCldId = " + GlCldId + "   GlSlocId = " +
                                                                   GlSlocId + " 1 " + "   GlHoOrgId = " + GlHoOrgId +
                                                                   "   GlOrgId = " + GlOrgId + "   GlVouId = " +
                                                                   GlVouId + "   56 " + "  GlTypeId = " + GlTypeId +
                                                                   " UsrIdCreate = " + UsrIdCreate + "   date = " +
                                                                   Date.getCurrentDate()); */
                                                Date vouDate=(Date)Date.getCurrentDate();                                               
                                                fYVO.setNamedWhereClauseParam("OrgIdBindVar", GlOrgId);
                                                fYVO.setNamedWhereClauseParam("BindFyId", fyIdSearch.getValue());
                                                fYVO.executeQuery();
                                                Row []row=fYVO.getAllRowsInRange();
                                                if(row.length>0) {
                                                     fyEndDate=(Date)row[0].getAttribute("FyEnd");
                                                }
                                                if(fyEndDate.dateValue().toString().compareTo(vouDate.dateValue().toString())<0) {
                                                    vouDate=fyEndDate;
                                                }
                                                callStoredFunction2(Types.VARCHAR,
                                                                    "APP.INS_DISP_DOC_TAB (?,?,?,?,?,?,?,?,?,?)",
                                                                    new Object[] { GlCldId, GlSlocId, 1, GlHoOrgId,
                                                                                   GlOrgId, GlVouId, 56, GlTypeId,
                                                                                   UsrIdCreate,
                                                                                   vouDate });

                                                //callStoredFunction2(Types.VARCHAR, "APP.PKG_APP.INS_DISP_DOC_TAB (?,?,?)",
                                                //                    new Object[] { GlVouId, UsrIdCreate, GlVouDt });

                                                operationBinding.execute();


                                                if (operationBinding.getErrors().isEmpty()) {
                                                    //      System.out.println("---   mode = V -------");
                                                    Mode = "V";
                                                    operationBindingexe.execute();
                                                    cuurImpl.getVoucherDispLOV1().executeQuery();
                                                    String msg = resolvEl("#{bundle['MSG.121']}").toString();
                                                    FacesMessage message = new FacesMessage(msg);
                                                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                                                    FacesContext fc = FacesContext.getCurrentInstance();
                                                    fc.addMessage(null, message);

                                                } else {
                                                    String msg = resolvEl("#{bundle['MSG.120']}").toString();
                                                    FacesMessage message = new FacesMessage(msg);
                                                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                                    FacesContext fc = FacesContext.getCurrentInstance();
                                                    fc.addMessage(null, message);
                                                }

                                            } else {
                                                String msg = resolvEl("#{bundle['MSG.120']}").toString();
                                                FacesMessage message = new FacesMessage(msg);
                                                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                                FacesContext fc = FacesContext.getCurrentInstance();
                                                fc.addMessage(null, message);
                                            }
                                        } else {
                                            //          System.out.println("not commited");
                                            String msg = resolvEl("#{bundle['MSG.521']}").toString();
                                            FacesMessage message = new FacesMessage(msg);
                                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                            FacesContext fc = FacesContext.getCurrentInstance();
                                            fc.addMessage(null, message);
                                        }


                                    } else if (Mode == "E") {
                                        //        System.out.println("---   mode = E -------");
                                        OperationBinding operationBinding =
                                            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit");
                                        OperationBinding operationBindingexe =
                                            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Execute");
                                        Number GlTotAmtBs = (Number)currRw.getAttribute("GlAmtBs");
                                        Number GlCc = (Number)currRw.getAttribute("GlCc");
                                        Integer GlCurrIdBs = (Integer)currRw.getAttribute("GlCurrIdBs");
                                        Integer GlCurrIdSp = (Integer)currRw.getAttribute("GlCurrIdSp");
                                        Integer GlSlocId = (Integer)currRw.getAttribute("GlSlocId");
                                        Integer GlCoaId = (Integer)currRw.getAttribute("GlCoaId");
                                        String GlOrgId = (String)currRw.getAttribute("GlOrgId");
                                        String GlHoOrgId = (String)currRw.getAttribute("GlHoOrgId");
                                        String GlCldId = (String)currRw.getAttribute("GlCldId");
                                        String GlVouId = (String)currRw.getAttribute("GlVouId");
                                        Date GlVouDueDt = (Date)currRw.getAttribute("GlVouDueDt");
                                        Number GlAmtSp = (Number)currRw.getAttribute("GlAmtSp");


                                        gl.setWhereClause("GL_ORG_ID = '" + GlOrgId + "' AND GL_COA_ID = " + GlCoaId +
                                                          " AND GL_TYP_MIG = 'O'" + " AND GL_CLD_ID='" + GlCldId +
                                                          "' AND GL_HO_ORG_ID='" + GlHoOrgId + "' AND GL_SLOC_ID=" +
                                                          GlSlocId + " AND GL_VOU_ID='" + GlVouId + "'");
                                        gl.executeQuery();
                                        if (gl.getRowCount() == 1) {

                                            Row[] rowGl = gl.getAllRowsInRange();
                                            if (rowGl.length > 0) {
                                                rowGl[0].setAttribute("GlTotAmtBs", GlTotAmtBs);
                                                rowGl[0].setAttribute("GlAmtSp", GlAmtSp);
                                                rowGl[0].setAttribute("GlCc", GlCc);
                                                rowGl[0].setAttribute("GlCurrIdBs", GlCurrIdBs);
                                                rowGl[0].setAttribute("GlCurrIdSp", GlCurrIdSp);
                                                rowGl[0].setAttribute("GlVouDueDt", GlVouDueDt);

                                                //rowGl[0].setAttribute("GlAmtTyp", amtTypNew);
                                            }
                                        }

                                        operationBinding.execute();

                                        if (operationBinding.getErrors().isEmpty()) {


                                            operationBindingexe.execute();
                                            String msg = resolvEl("#{bundle['MSG.121']}").toString();
                                            FacesMessage message = new FacesMessage(msg);
                                            message.setSeverity(FacesMessage.SEVERITY_INFO);
                                            FacesContext fc = FacesContext.getCurrentInstance();
                                            fc.addMessage(null, message);
                                            Mode = "V";

                                        } else {
                                            String msg = resolvEl("#{bundle['MSG.120']}").toString();
                                            FacesMessage message = new FacesMessage(msg);
                                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                            FacesContext fc = FacesContext.getCurrentInstance();
                                            fc.addMessage(null, message);
                                        }

                                    }

                                } //end of if amtTyp condition
                                else {
                                    String msg = resolvEl("#{bundle['MSG.122']}").toString();
                                    FacesMessage message = new FacesMessage(msg);
                                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fc = FacesContext.getCurrentInstance();
                                    fc.addMessage(amtTypGlBinding.getClientId(), message);
                                }
                            } //end of if ccGL condition
                            else {
                                String msg = resolvEl("#{bundle['MSG.21']}").toString();
                                FacesMessage message = new FacesMessage(msg);
                                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(ccGlBinding.getClientId(), message);
                            }
                        } //end of if AmtSp condition
                        else {
                            String msg = resolvEl("#{bundle['MSG.123']}").toString();
                            FacesMessage message = new FacesMessage(msg);
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(balanceOp.getClientId(), message);
                        }
                    } //end of if vouType condition
                    else {
                        String msg = resolvEl("#{bundle['MSG.124']}").toString();
                        FacesMessage message = new FacesMessage(msg);
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(typeIdGlBinding.getClientId(), message);
                    }
                } //end of if vouDt condition
                else {
                    String msg = resolvEl("#{bundle['MSG.125']}").toString();
                    FacesMessage message = new FacesMessage(msg);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(vouDtGlBinding.getClientId(), message);
                }
            } //end of if coaId condition
            else {
                String msg = resolvEl("#{bundle['MSG.126']}").toString();
                FacesMessage message = new FacesMessage(msg);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(coaIdGlBinding.getClientId(), message);
            }

            AdfFacesContext.getCurrentInstance().addPartialTarget(costCenterLink);
            getIsCostCenter();
            AdfFacesContext.getCurrentInstance().addPartialTarget(glTable);

        } // end of ok

        AdfFacesContext.getCurrentInstance().addPartialTarget(buttonBinding);

    } //end of dialog


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

    public void setGlTable(RichTable glTable) {
        this.glTable = glTable;
    }

    public RichTable getGlTable() {
        return glTable;
    }

    public void specificAmtChangeListner(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            Number GlAmtSp = new Number(0);
            Number GlCc = new Number(0);
            Number GlAmtBs = new Number(0);
            GlAmtSp = (Number)valueChangeEvent.getNewValue();
            GlCc = (Number)glCcRateBIndVar.getValue();

            //AppOpeningBalanceAMImpl am = (AppOpeningBalanceAMImpl)resolvElDC("AppOpeningBalanceAMDataControl");
            //ViewObjectImpl gl_lines = am.getGlLines();
            //Row rw = gl_lines.getCurrentRow();
            // if (rw.getAttribute("GlAmtSp") != null && rw.getAttribute("GlCc") != null) {
            //  System.out.println("#{pageFlowScope.GLBL_AMT_DIGIT}");
            if (resolvEl("#{pageFlowScope.GLBL_AMT_DIGIT}").toString() != null) {
                //     System.out.println("Entered into val chng listnr");
                GlAmtBs = GlAmtSp.multiply(GlCc);
                //    System.out.println(GlAmtBs);
                Integer i = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_AMT_DIGIT}").toString());
                glBaseAmtBindVar.setValue(GlAmtBs.round(i));
                //     System.out.println("changed");
            } else {
                //  System.out.println("else");
            }

        }
    }

    public String SearchAction() {

        Object obj = coaBinding.getValue();
        Object obj1 = fyIdSearch.getValue();

        if (obj == null || obj.toString().length() <= 0 || obj1 == null || obj1.toString().length() <= 0) {
            String msg = resolvEl("#{bundle['LBL.2946']}").toString(); //Fill the Mandatory Fields.
            FacesMessage fm = new FacesMessage(msg);
            fm.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
            return null;
        }

        OperationBinding operationBinding =
            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("ExecuteWithParams");
        operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(glTable);
        /* operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(glTable); */
        SearchNResetMode = "S";
        return null;
    }


    public String ResetAction() {

        /* coaBinding.setValue("");
        fyIdSearch.setValue(0); */
        AppOpeningBalanceAMImpl am = (AppOpeningBalanceAMImpl)resolvElDC("AppOpeningBalanceAMDataControl");
        ViewObjectImpl coaFyDual1 = am.getCoaFyDual1();
        coaFyDual1.executeQuery();


        OperationBinding operationBinding =
            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("ExecuteWithParams");
        operationBinding.execute();

        /* coaBinding.setValue("");
        fyIdSearch.setValue(0); */
        AdfFacesContext.getCurrentInstance().addPartialTarget(coaBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(fyIdSearch);
        AdfFacesContext.getCurrentInstance().addPartialTarget(glTable);
        SearchNResetMode = "R";
        return "reExecute";
    }

    /*  public void setCoaIdSearch(RichSelectOneChoice coaIdSearch) {
        this.coaIdSearch = coaIdSearch;
    }

    public RichSelectOneChoice getCoaIdSearch() {
        return coaIdSearch;
    } */

    public void setMode(String Mode) {
        this.Mode = Mode;
    }

    public String getMode() {
        return Mode;
    }

    public Object resolvEl(Object data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, (String)data, Object.class);
        Object Message = valueExp.getValue(elContext);
        return Message;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();

        vc.setPrecision(precision);


        vc.setScale(scale);

        return vc.validateValue(total);
    }

    protected void callStoredProcedureBalance(String stmt, Object[] bindVars) {

        CallableStatement st = null;
        Number returnVal = new Number(0);
        try {
            /** 1. Create a JDBC CallabledStatement */

            AppOpeningBalanceAMImpl am = (AppOpeningBalanceAMImpl)resolvElDC("AppOpeningBalanceAMDataControl");
            st = am.getDBTransaction().createCallableStatement("begin " + stmt + ";end;", 0);
            /** 2. Register the first bind variable for the return value. and last 2 variable for output variable of function. */
            st.setObject(1, bindVars[0]);
            st.setObject(2, bindVars[1]);
            st.setObject(3, bindVars[2]);
            st.setObject(4, bindVars[3]);
            st.setObject(5, bindVars[4]);
            st.setObject(6, bindVars[5]);
            st.setObject(7, bindVars[6]);
            st.registerOutParameter(8, Types.NUMERIC);
            st.registerOutParameter(9, Types.VARCHAR);
            st.registerOutParameter(10, Types.NUMERIC);
            st.registerOutParameter(11, Types.VARCHAR);

            /** 5. Set the value of user-supplied bind vars in the stmt */

            st.executeUpdate();
            try {

                BigDecimal num = (BigDecimal)st.getObject(10);
                orgOpBalGl = new Number(num);
                orgOpbalGlTyp = (st.getObject(11).toString());
            } catch (NullPointerException e) {
                //    System.out.println(e.getMessage());
                e.printStackTrace();
            }

        } catch (SQLException e) {
            //      System.out.println(e.getMessage());
            int end = e.getMessage().indexOf("\n");

            throw new JboException(e.getMessage());
        } finally {
            if (st != null) {
                try {
                    /** 7. Close the statement */
                    st.close();
                } catch (SQLException e) {
                    //          System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    public void AmountSpValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {


        if (typeIdGlBinding.getValue() != null) {
            if (object != null) {

                AppOpeningBalanceAMImpl am = (AppOpeningBalanceAMImpl)resolvElDC("AppOpeningBalanceAMDataControl");
                ViewObjectImpl gl_lines = am.getGlLines();
                Row rw = gl_lines.getCurrentRow();
                Integer GlSlocId = (Integer)rw.getAttribute("GlSlocId");

                Integer GlCoaId = coaIdGlBinding.getValue() == null ? 0 : (Integer)coaIdGlBinding.getValue();
                String GlOrgId = (String)rw.getAttribute("GlOrgId");
                String GlHoOrgId = (String)rw.getAttribute("GlHoOrgId");
                String GlCldId = (String)rw.getAttribute("GlCldId");

                Number ob = (Number)object;

                Number zero = new Number(0);
                Number qty = (Number)glCcRateBIndVar.getValue();
                //ob = (Number)balanceOp.getValue();

                callStoredProcedureBalance("FIN.PROC_COA_OPEN_BAL(?,?,?,?,?,?,?,?,?,?,?)",
                                           new Object[] { GlHoOrgId, GlOrgId, GlSlocId, GlCldId, 64, GlCoaId, 2 });

                // Number amtBsGl = (Number)glBaseAmtBindVar.getValue();
                //Number orgOpBalGl = (Number)rw.getAttribute("OpenBal");
                //String orgOpbalGlTyp = rw.getAttribute("OpenBalType").toString();
                String amtTypGlNew = rw.getAttribute("GlAmtTyp").toString();

                Number tot = new Number(0);

                if (qty.compareTo(zero) == 1) {
                    tot = ob.multiply(qty);
                } else {
                    tot = ob;
                }

                Boolean totFlag = isPrecisionValid(20, 6, tot);
                Number newAmountOp = new Number(0);
                if (totFlag == true) {
                    if (Mode.equalsIgnoreCase("A")) {
                        if (orgOpbalGlTyp.equalsIgnoreCase(amtTypGlNew)) {
                            newAmountOp = orgOpBalGl.add(tot);
                        } else {
                            newAmountOp = orgOpBalGl.subtract(tot);
                        }
                    } else {
                        orgOpBalGl = orgOpBalGl.subtract(AmountBs);
                        if (orgOpbalGlTyp.equalsIgnoreCase(amtTypGlNew)) {
                            newAmountOp = orgOpBalGl.add(tot);
                        } else {
                            newAmountOp = orgOpBalGl.subtract(tot);
                        }
                    }

                } else {
                    String msg = resolvEl("#{bundle['MSG.127']}").toString();
                    FacesMessage message = new FacesMessage(msg);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                }

                if ((this.isPrecisionValid(20, 6, newAmountOp) == false)) {

                    String msg = resolvEl("#{bundle['MSG.128']}").toString();
                    FacesMessage message = new FacesMessage(msg);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);


                } else if (totFlag == false) {

                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  resolvEl("#{bundle['MSG.128']}").toString(), null));

                } else if ((ob.compareTo(zero) == -1) || (ob.compareTo(zero) == 0)) {


                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  resolvEl("#{bundle['MSG.130']}").toString(), null));

                }
            }
        }
    }


    public void currencyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null) {

            Integer ob = (Integer)object;

            Number obN;
            try {
                obN = new Number(ob);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Number zero = new Number(0);
            AppOpeningBalanceAMImpl am = (AppOpeningBalanceAMImpl)resolvElDC("AppOpeningBalanceAMDataControl");
            ViewObjectImpl gl_lines = am.getGlLines();
            Row rw = gl_lines.getCurrentRow();
            Number qty = (Number)rw.getAttribute("GlCc");
            Number AmtSp = (Number)rw.getAttribute("GlAmtSp");

            obN = AmtSp.multiply(qty);
            Boolean totFlag = isPrecisionValid(26, 6, obN);

            if (totFlag == false) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.128']}").toString(), null));

            } else if ((obN.compareTo(zero) == -1) || (obN.compareTo(zero) == 0)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.130']}").toString(), null));

            }


        }


        /* ViewObjectImpl po = getAm().getMmDrftPo();
            Row row = po.getCurrentRow();

                Number currFactor=new Number(1);
                Number qty=new Number(0);

            if(row.getAttribute("CurrConvFctr")!=null){
                currFactor = (Number)row.getAttribute("CurrConvFctr");
            }
            if(itmQtyBind.getValue()!=null){
                qty=(Number)itmQtyBind.getValue();
            }
            Number tot = ob.multiply(qty).multiply(currFactor);
            Boolean totFlag = isPrecisionValid(26, 6, tot);

            if (totFlag.equals(false)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Results in Invalid Precision for Calculated fields(26,6).",null));
            }

            if (ob.compareTo(zero) != 1 && itmIdBind.getValue()!=null) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Item Price must be greater than Zero.", null));

                }
            } else if(object != null && itmIdBind.getValue()!=null){
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Item Price must be greater than Zero.", null));

            } */

    }


    public void selectBrandLinkAction(ActionEvent actionEvent) {

        AppOpeningBalanceAMImpl am = (AppOpeningBalanceAMImpl)resolvElDC("AppOpeningBalanceAMDataControl");
        ViewObjectImpl glLinesVO = am.getGlLines();
        Row glLinesRow = glLinesVO.getCurrentRow();


        Integer slocId = Integer.parseInt(glLinesRow.getAttribute("GlSlocId").toString());
        String orgId = glLinesRow.getAttribute("GlOrgId").toString();
        String HoOrgId = glLinesRow.getAttribute("GlHoOrgId").toString();
        String CldId = glLinesRow.getAttribute("GlCldId").toString();
        Integer val = 0;

        try {

            val = isCostCenterPresent(slocId, CldId, orgId, HoOrgId);
            if (val > 1) {

                FacesMessage Message = new FacesMessage(resolvEl("#{bundle['MSG.159']}").toString());
                Message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage(null, Message);
            } else if (val == 0) {

                FacesMessage Message = new FacesMessage(resolvEl("#{bundle['MSG.132']}").toString());
                Message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage(null, Message);
            } else {


                String cost_center_src = "L";
                if (glLinesRow.getAttribute("OpenBal") != null) {
                    Number amount_temp = (Number)glLinesRow.getAttribute("OpenBal");
                    if (amount_temp.compareTo(new Number(0)) == 1) {
                        cost_center_amount = amount_temp.bigDecimalValue();
                    }

                    showPopup(costCenterPopup, true);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Integer isCostCenterPresent(Integer P_SLOC_ID, String P_CLD_ID, String P_ORG_ID,
                                        String P_HO_ORG_ID) throws SQLException {
        Integer retVal =
            (Integer)callStoredFunction2(Types.INTEGER, "FIN.FN_IS_COST_CENTER_PRESENT(?,?,?,?,?)", new Object[] { 64,
                                                                                                                   P_SLOC_ID,
                                                                                                                   P_CLD_ID,
                                                                                                                   P_ORG_ID,
                                                                                                                   P_HO_ORG_ID });

        return retVal;
    }

    public String setToTmplSaveCC(Integer P_TEMP_SLOC_ID, String P_TEMP_CLD_ID, String P_TEMP_HO_ORG_ID,
                                  String P_TEMP_ORG_ID, Integer P_TEMP_DOC_ID, String P_TEMP_ID,
                                  Integer P_TEMP_SL_NO) throws SQLException {

        String ret = null;

        ret =
(String)callStoredFunction2(Types.VARCHAR, "FIN.FN_SAVE_COST_CENTER(?,?,?,?,?,?,?)", new Object[] { P_TEMP_CLD_ID,
                                                                                                    P_TEMP_SLOC_ID,
                                                                                                    P_TEMP_HO_ORG_ID,
                                                                                                    P_TEMP_ORG_ID,
                                                                                                    P_TEMP_DOC_ID,
                                                                                                    P_TEMP_ID,
                                                                                                    P_TEMP_SL_NO });


        return ret;
    }


    public void setCostCenterPopup(RichPopup costCenterPopup) {
        this.costCenterPopup = costCenterPopup;
    }

    public RichPopup getCostCenterPopup() {
        return costCenterPopup;
    }

    public void setCost_center_src(String cost_center_src) {
        this.cost_center_src = cost_center_src;
    }

    public String getCost_center_src() {
        return cost_center_src;
    }

    public void setCost_center_amount(BigDecimal cost_center_amount) {
        this.cost_center_amount = cost_center_amount;
    }

    public BigDecimal getCost_center_amount() {
        return cost_center_amount;
    }

    public void ccServiceDialogListener(DialogEvent dialogEvent) {
        // Add event code here...

        if (dialogEvent.getOutcome().name().equals("ok")) {
            try {
                AppOpeningBalanceAMImpl amNew = (AppOpeningBalanceAMImpl)resolvElDC("AppOpeningBalanceAMDataControl");
                ViewObjectImpl glLinesVO = amNew.getGlLines();
                Row glLinesRow = glLinesVO.getCurrentRow();
                Integer SLOC_ID = Integer.parseInt(glLinesRow.getAttribute("GlSlocId").toString());
                String ORG_ID = glLinesRow.getAttribute("GlOrgId").toString();
                String HO_ORG_ID = glLinesRow.getAttribute("GlHoOrgId").toString();
                String CLD_ID = glLinesRow.getAttribute("GlCldId").toString();
                String GL_COA_ID = glLinesRow.getAttribute("GlCoaId").toString();
                Integer DOC_ID = this.getGlBlDocId();
                String val = setToTmplSaveCC(SLOC_ID, CLD_ID, HO_ORG_ID, ORG_ID, DOC_ID, GL_COA_ID, 0);

            } catch (SQLException sqe) {
                // TODO: Add catch code
                //     System.out.println(sqe);
                sqe.printStackTrace();
            }
        } else {
            try {
                AppOpeningBalanceAMImpl amNew = (AppOpeningBalanceAMImpl)resolvElDC("AppOpeningBalanceAMDataControl");
                ViewObjectImpl glLinesVO = amNew.getGlLines();
                Row glLinesRow = glLinesVO.getCurrentRow();
                Integer SLOC_ID = Integer.parseInt(glLinesRow.getAttribute("GlSlocId").toString());
                String ORG_ID = glLinesRow.getAttribute("GlOrgId").toString();
                String HO_ORG_ID = glLinesRow.getAttribute("GlHoOrgId").toString();
                String CLD_ID = glLinesRow.getAttribute("GlCldId").toString();
                String GL_COA_ID = glLinesRow.getAttribute("GlCoaId").toString();
                Integer DOC_ID = this.getGlBlDocId();
                String val = setToTmplCancelCC(SLOC_ID, CLD_ID, HO_ORG_ID, ORG_ID, DOC_ID, GL_COA_ID, 0, "N");


            } catch (SQLException sqle) {
                // TODO: Add catch code
                sqle.printStackTrace();
            } catch (NumberFormatException nfe) {
                // TODO: Add catch code
                nfe.printStackTrace();
            }
        }

    }

    public String setToTmplCancelCC(Integer P_TEMP_SLOC_ID, String P_TEMP_CLD_ID, String P_TEMP_HO_ORG_ID,
                                    String P_TEMP_ORG_ID, Integer P_TEMP_DOC_ID, String P_TEMP_ID,
                                    Integer P_TEMP_SL_NO, String Mode) throws SQLException {

        String ret = null;
        ret =
(String)callStoredFunction2(Types.VARCHAR, "FIN.FN_CANCEL_COST_CENTER_TEMPLATE(?,?,?,?,?,?,?,?)", new Object[] { P_TEMP_CLD_ID,
                                                                                                                 P_TEMP_SLOC_ID,
                                                                                                                 P_TEMP_HO_ORG_ID,
                                                                                                                 P_TEMP_ORG_ID,
                                                                                                                 P_TEMP_DOC_ID,
                                                                                                                 P_TEMP_ID,
                                                                                                                 P_TEMP_SL_NO,
                                                                                                                 Mode });

        return ret;
    }


    public void setBalance(Number balance) {
        this.balance = balance;
    }

    public Number getBalance() {
        return balance;
    }

    public void setBalanceTyp(String balanceTyp) {
        this.balanceTyp = balanceTyp;
    }

    public String getBalanceTyp() {
        return balanceTyp;
    }


    public void setGlBlsl_No(Integer GlBlsl_No) {
        this.GlBlsl_No = GlBlsl_No;
    }

    public Integer getGlBlsl_No() {
        return GlBlsl_No;
    }

    public void setGlBlDocId(Integer GlBlDocId) {
        this.GlBlDocId = GlBlDocId;
    }

    public Integer getGlBlDocId() {
        return GlBlDocId;
    }

    public void setGlBlFyYr(Integer GlBlFyYr) {
        this.GlBlFyYr = GlBlFyYr;
    }

    public Integer getGlBlFyYr() {
        return GlBlFyYr;
    }

    public void setBalanceOp(RichInputText balanceOp) {
        this.balanceOp = balanceOp;
    }

    public RichInputText getBalanceOp() {

        return balanceOp;
    }


    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isRequired() {
        return required;
    }

    public void setVouDtGlBinding(RichInputDate vouDtGlBinding) {
        this.vouDtGlBinding = vouDtGlBinding;
    }

    public RichInputDate getVouDtGlBinding() {
        return vouDtGlBinding;
    }

    public void setTypeIdGlBinding(RichSelectOneChoice typeIdGlBinding) {
        this.typeIdGlBinding = typeIdGlBinding;
    }

    public RichSelectOneChoice getTypeIdGlBinding() {
        return typeIdGlBinding;
    }

    public void setCcGlBinding(RichInputListOfValues ccGlBinding) {
        this.ccGlBinding = ccGlBinding;
    }

    public RichInputListOfValues getCcGlBinding() {
        return ccGlBinding;
    }

    public void setAmtTypGlBinding(RichSelectOneChoice amtTypGlBinding) {
        this.amtTypGlBinding = amtTypGlBinding;
    }

    public RichSelectOneChoice getAmtTypGlBinding() {
        return amtTypGlBinding;
    }

    public void setCoaIdGlBinding(RichSelectOneChoice coaIdGlBinding) {
        this.coaIdGlBinding = coaIdGlBinding;
    }

    public RichSelectOneChoice getCoaIdGlBinding() {
        return coaIdGlBinding;
    }

    public void setGlCcRateBIndVar(RichInputText glCcRateBIndVar) {
        this.glCcRateBIndVar = glCcRateBIndVar;
    }

    public RichInputText getGlCcRateBIndVar() {
        return glCcRateBIndVar;
    }

    public void setGlBaseAmtBindVar(RichInputText glBaseAmtBindVar) {
        this.glBaseAmtBindVar = glBaseAmtBindVar;
    }

    public RichInputText getGlBaseAmtBindVar() {
        return glBaseAmtBindVar;
    }

    public void glCcChangeListner(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            Number GlAmtSp = new Number(0);
            Number GlCc = new Number(0);
            Number GlAmtBs = new Number(0);

            GlAmtSp = (Number)balanceOp.getValue();
            GlCc = (Number)valueChangeEvent.getNewValue();

            //AppOpeningBalanceAMImpl am = (AppOpeningBalanceAMImpl)resolvElDC("AppOpeningBalanceAMDataControl");
            //ViewObjectImpl gl_lines = am.getGlLines();
            //Row rw = gl_lines.getCurrentRow();
            // if (rw.getAttribute("GlAmtSp") != null && rw.getAttribute("GlCc") != null) {


            GlAmtBs = GlAmtSp.multiply(GlCc);

            glBaseAmtBindVar.setValue(GlAmtBs.round(2));

            // }


        }
    }

    public void currencyChangeListner(ValueChangeEvent valueChangeEvent) {

        if (valueChangeEvent.getNewValue() != null) {

            Number GlAmtSp = new Number(0);
            Number GlCc = new Number(0);
            Number GlAmtBs = new Number(0);
            AppOpeningBalanceAMImpl am = (AppOpeningBalanceAMImpl)resolvElDC("AppOpeningBalanceAMDataControl");
            ViewObjectImpl gl_lines = am.getGlLines();
            ViewObjectImpl curr = am.getCurrencyLov1();

            Row rw = gl_lines.getCurrentRow();
            Integer GlCurrIdBs = (Integer)rw.getAttribute("GlCurrIdBs");
            String orgId = (String)rw.getAttribute("GlHoOrgId");
            // Integer GlCurrIdSp = (Integer)valueChangeEvent.getNewValue();
            String currIdSpDesc = (String)valueChangeEvent.getNewValue();
            Integer GlCurrIdSp = null;
            if (currIdSpDesc != null) {
                curr.setNamedWhereClauseParam("BindOrgId", orgId);
                curr.setNamedWhereClauseParam("BindCurrId", GlCurrIdBs);
                Row[] xx = curr.getFilteredRows("CurrDescTxn", currIdSpDesc);
                // Row [] xx=am.getLovInputItmId1().getFilteredRowsInRange("ItmId", inputitm);
                if (xx.length > 0) {
                    GlCurrIdSp = Integer.parseInt(xx[0].getAttribute("CcCurrIdTxn").toString());

                }
            } else {

            }
            RowQualifier rowQualifier = new RowQualifier(curr);
            rowQualifier.setWhereClause("CcCurrId=" + GlCurrIdBs + " AND CcCurrIdTxn=" + GlCurrIdSp);
            Row[] filteredRows = curr.getFilteredRows(rowQualifier);

            if (filteredRows.length == 1) {

                GlCc = (Number)filteredRows[0].getAttribute("CcBuy");
            } else {
                GlCc = (Number)glCcRateBIndVar.getValue();
            }


            GlAmtSp = (Number)balanceOp.getValue();


            GlAmtBs = GlAmtSp.multiply(GlCc);

            glBaseAmtBindVar.setValue(GlAmtBs.round(2));


        }
    }

    public void ccValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number zero = new Number(0);
            Number ccval = (Number)object;
            if (ccval.compareTo(zero) == -1 || ccval.compareTo(zero) == 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.133']}").toString(), null));
            }
        }
    }

    public void setSearchNResetMode(String SearchNResetMode) {
        this.SearchNResetMode = SearchNResetMode;
    }

    public String getSearchNResetMode() {
        return SearchNResetMode;
    }

    public String backAction() {
        RequestContext.getCurrentInstance().getPageFlowScope().put("COA_ID", 0);
        return "Back";
    }

    public String resetFilterAction() {
        FilterableQueryDescriptor queryDescriptor = (FilterableQueryDescriptor)getGlTable().getFilterModel();
        if (queryDescriptor != null && queryDescriptor.getFilterCriteria() != null) {
            queryDescriptor.getFilterCriteria().clear();
            getGlTable().queueEvent(new QueryEvent(getGlTable(), queryDescriptor));

            AdfFacesContext.getCurrentInstance().addPartialTarget(glTable);
        }
        return null;
    }

    public void setCostCenterLink(RichCommandLink costCenterLink) {
        this.costCenterLink = costCenterLink;
    }

    public RichCommandLink getCostCenterLink() {
        return costCenterLink;
    }

    public void setIsCostCenter(Boolean isCostCenter) {
        this.isCostCenter = isCostCenter;
    }

    public Boolean getIsCostCenter() {
        AppOpeningBalanceAMImpl am = (AppOpeningBalanceAMImpl)resolvElDC("AppOpeningBalanceAMDataControl");
        ViewObjectImpl gl_lines = am.getGlLines();
        Row rw = gl_lines.getCurrentRow();
        if (rw != null) {
            if (rw.getAttribute("OpenBal") != null) {
                Number OpBal = (Number)rw.getAttribute("OpenBal");
                if ((OpBal.compareTo(balance) == 0) && ((this.Mode == "A") || (this.Mode == "E"))) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return isCostCenter;
            }
        } else {
            return isCostCenter;
        }
    }

    public void setFyIdSearch(RichSelectOneChoice fyIdSearch) {
        this.fyIdSearch = fyIdSearch;
    }

    public RichSelectOneChoice getFyIdSearch() {
        return fyIdSearch;
    }

    public void setOnloadVal(Integer onloadVal) {
        this.onloadVal = onloadVal;
    }

    public Integer getOnloadVal() {
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding("on_load_page");
        Object ret = operationBinding.execute();

        return (Integer)ret;
    }

    public void setCoaBinding(RichInputListOfValues coaBinding) {
        this.coaBinding = coaBinding;
    }

    public RichInputListOfValues getCoaBinding() {
        return coaBinding;
    }

    public void setButtonBinding(RichCommandImageLink buttonBinding) {
        this.buttonBinding = buttonBinding;
    }

    public RichCommandImageLink getButtonBinding() {
        return buttonBinding;
    }

    public void setCoaIdtransBinding(RichInputText coaIdtransBinding) {
        this.coaIdtransBinding = coaIdtransBinding;
    }

    public RichInputText getCoaIdtransBinding() {
        return coaIdtransBinding;
    }


    public void setHoOrgId(String HoOrgId) {
        this.HoOrgId = HoOrgId;
    }

    public String getHoOrgId() {
        return HoOrgId;
    }


    public void setSearchButtonBindVal(RichCommandImageLink searchButtonBindVal) {
        this.searchButtonBindVal = searchButtonBindVal;
    }

    public RichCommandImageLink getSearchButtonBindVal() {
        return searchButtonBindVal;
    }

    public void setRetValue(String RetValue) {
        this.RetValue = RetValue;
    }

    public String getRetValue() {
        return RetValue;
    }

    public void chkDoumentNo(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String docNo = object.toString();
            System.out.println("chkDocumentNo_______________________");
            OperationBinding binding =
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("chkDocumentNo");
            binding.getParamsMap().put("docNo", docNo);
            binding.execute();

            Boolean isError = (Boolean)binding.getResult();
            System.out.println("**************" + isError);
            if (isError) {
                System.out.println("Error caught BINGO..." + isError);
                FacesMessage message = new FacesMessage("Duplicate Document No. found.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }


        }

    }

    public void coaValueChangeListener(ValueChangeEvent valueChangeEvent) {
        Object coaObj = coaBinding.getValue();
        Object fyObj = fyIdSearch.getValue();
        if (valueChangeEvent.getNewValue() != null) {

            // check both search field is empty or not . If it is empty then Disable the add button. else call method
            if (coaObj != null && fyObj != null) {
                if (fyObj.toString().length() > 0 && coaObj.toString().length() > 0) {

                    /* System.out.println("New Value-------------->"+valueChangeEvent.getNewValue());
                        System.out.println("coa ----------->"+coaObj);
                        System.out.println("FY----------->"+fyObj); */

                    FacesContext facesContext = FacesContext.getCurrentInstance();
                    UIViewRoot root = facesContext.getViewRoot();

                    ActionEvent actionEvent = new ActionEvent(searchButtonBindVal);
                    actionEvent.queue();


                    valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
                    OperationBinding binding =
                        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Execute");
                    binding.execute();

                    SearchNResetMode = "S";
                }
            }

        }


    }

    public void fyValueChangeListener(ValueChangeEvent valueChangeEvent) {
        Object coaObj = coaBinding.getValue();
        Object fyObj = fyIdSearch.getValue();

        if (valueChangeEvent.getNewValue() != null) {
            if (coaObj != null && fyObj != null){
                if(fyObj.toString().length() > 0 && coaObj.toString().length() > 0) {

                /*       System.out.println("COA----------->"+coaObj);
                            System.out.println("fY ---------->"+fyObj); */

                FacesContext facesContext = FacesContext.getCurrentInstance();

                UIViewRoot root = facesContext.getViewRoot();
                ActionEvent actionEvent = new ActionEvent(searchButtonBindVal);
                actionEvent.queue();

                OperationBinding binding =
                    BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Execute");
                binding.execute();
                valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());

                SearchNResetMode = "S";
            }
            }

        }
    }

    public void setInvNoPgBind(RichInputText invNoPgBind) {
        this.invNoPgBind = invNoPgBind;
    }

    public RichInputText getInvNoPgBind() {
        return invNoPgBind;
    }

    public void setInvDtPgBind(RichInputDate invDtPgBind) {
        this.invDtPgBind = invDtPgBind;
    }

    public RichInputDate getInvDtPgBind() {
        return invDtPgBind;
    }

    public void setDueDtPgBind(RichInputDate dueDtPgBind) {
        this.dueDtPgBind = dueDtPgBind;
    }

    public RichInputDate getDueDtPgBind() {
        return dueDtPgBind;
    }

    public void extDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if(object!=null){
            if(vouDtGlBinding.getValue().toString().compareTo(object.toString())<0){
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Date must be equal to or less than Voucher date", null));
            }
        }
    }

    public void vouDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
               if(object!=null){
    if(maxDate.compareTo(object.toString())<0){
                  throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Date must be equal to or less than "+maxDate, null));
            }
        }
    }
}
