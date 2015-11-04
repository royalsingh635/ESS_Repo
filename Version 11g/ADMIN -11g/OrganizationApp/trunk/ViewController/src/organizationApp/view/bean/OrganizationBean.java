package organizationApp.view.bean;

import java.sql.CallableStatement;
import java.sql.SQLException;

import java.sql.Types;

import java.util.ArrayList;
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

import javax.faces.event.ValueChangeEvent;

import javax.faces.validator.ValidatorException;

import javax.print.Doc;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.data.RichTreeTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelTabbed;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.adf.view.rich.event.QueryEvent;
import oracle.adf.view.rich.model.FilterableQueryDescriptor;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;


import oracle.jbo.Row;
import oracle.jbo.domain.Date;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.event.AttributeChangeEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

import organizationApp.model.module.OrgAppAMImpl;

import oracle.adf.view.rich.util.ResetUtils;

import oracle.jbo.JboException;

import oracle.jbo.Key;
import oracle.jbo.NavigatableRowIterator;
import oracle.jbo.RowIterator;
import oracle.jbo.RowSet;
import oracle.jbo.RowSetIterator;

import oracle.jbo.ViewObject;
import oracle.jbo.server.RowQualifier;

import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import organizationApp.model.view.LOVCOAVOImpl;
import organizationApp.model.view.OrgVOImpl;

public class OrganizationBean {
    private String labelMode;
    private RichPopup pop_org;
    private String editAlowOp="Y";
    private boolean disableOrg = true;
    private static boolean disableProfile = true;
    private RichPopup pop_loc;
    private RichInputText orgDesc;
    private RichInputText orgAlias;
    private RichSelectOneChoice orgParent;
    private RichSelectOneChoice orgCountry;
    private RichSelectOneChoice orgLang;
    private RichSelectOneChoice orgPreLang;
    //  private RichSelectOneChoice orgCurr;
    private RichSelectOneChoice orgCurr1;
    private RichSelectOneChoice orgPrfFy;
    private RichSelectBooleanCheckbox orgPrfMst;
    private RichSelectBooleanCheckbox orgPrfREnt;
    private RichSelectBooleanCheckbox orgPrfRNa;
    private RichSelectBooleanCheckbox orgPrfRCog;
    private RichSelectBooleanCheckbox orgPrfRCc;
    private RichSelectBooleanCheckbox orgPrfCreateCc;
    private RichSelectBooleanCheckbox orgPrfCreateCog;
    private RichSelectBooleanCheckbox orgPrfCreateEnt;
    private RichSelectBooleanCheckbox orgPrfCreateNa;
    private StringBuilder warningmessage = new StringBuilder("<html><body>");
    private RichPopup pop_rgn;
    private RichPopup pop_comm;
    private String mode = "V";
    private String profilemode = "V";
    private String fydate = "E";
    private String maininfomode = "V";
    private String createprofilemode = "V";
    private String defmode = "Y";
    private RichPopup createOrgPop;
    private RichInputDate orgFyStDtBind;
    private RichPanelFormLayout orgPnlFrmLoutBind;
    private RichPanelFormLayout orgPnlFrmLout2Bind;
    private RichPanelFormLayout orgDtlPnlFrmLoutBind;
    private RichPanelFormLayout orgDtlPnlFrmLout2Bind;
    private RichPanelFormLayout orgDtlPnlFrmLout3Bind;
    private Boolean orgDtldis = true;

    private RichCommandButton saveOrgDtlBtnBind;
    private RichCommandButton cancelOrgDtlBtnBind;
    private RichCommandButton editOrgDtlBtnBind;
    private RichCommandButton delOrgBtnBind;
    private RichPopup delPopupBind;
    private RichTreeTable orgTreeBind;
    private RichOutputText nodeBind;
    private RichInputDate orgFyDtFromBind;

    private RichPopup addUsrPopUpBind;
    private RichPopup addFyPopUpBind;
    private RichPopup delFyPopBind;
    //  private RichSelectOneChoice orgCurrId;
    private String flag = "false";
    //  private RichSelectOneChoice orgIdParentBinding;
    // private RichSelectOneChoice transCoaBinding;
    private Date previousStartDate = null;
    private RichInputListOfValues orgCurrencyBinding;
    private RichInputListOfValues coaTransBinding;
    private String DocMode = "V";
    OrgAppAMImpl am = (OrgAppAMImpl)resolvElDC("OrgAppAMDataControl");
    private RichSelectOneChoice parentOrgBind;
    private RichPopup docPopupBinding;
    private RichPopup docTypePopup;
    private RichCommandImageLink editOrgDtlImgBind;
    private RichCommandImageLink saveOrgDtlImgBind;
    private RichCommandImageLink cancelOrgDtlImgBind;
    private RichPopup orgDocTypResetPopBinding;
    private RichPopup delwarningpopbind;
    private RichPopup popupforOrgDocFy;
    private RichTable orgFyBind;
    private RichSelectOneChoice docNmPopupBind;
    private RichSelectOneChoice docTypNmPopupBind;
    private RichSelectOneChoice docResetopupBind;
    private RichPopup orgDocFyDelPopBind;
    private RichTable orgDocBind;
    private RichTable orgDocTypBind;
    private RichTable docResetTypBind;
    private RichTable orgDocFyBind;
    // private String orgid=resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
    private String cldid = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
    private Integer slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
    private RichTable usrtablebind;
    private RichPopup doctypWarnpopup;
    private java.util.Date dt = new java.util.Date();
    private Integer count = 0;
    private Integer chkcount = 0;
    private RichInputText t;
    private RichInputText transAddBind;
    private RichInputText transAdd2Bind;
    private static int VARCHAR = Types.VARCHAR;
    private RichCommandImageLink editBtnBind;
    private RichCommandImageLink orgFyEditBind;
    private RichCommandImageLink othdetailEditBind;
    private RichCommandImageLink userAddBind;
    private RichShowDetailItem fyTabBind;
    private RichShowDetailItem infoTabBind;
    private RichShowDetailItem docTabBind;
    private RichPanelTabbed panelTabBind;
    private RichSelectBooleanCheckbox defResetBind;
    private RichInputText orgPrefixBind;
    private RichInputText maxLengthBind;
    private RichSelectBooleanCheckbox prefixBind;
    Integer count1 = (Integer)getBindings().getOperationBinding("on_load_page").execute();

    public OrganizationBean() {
    }


    public void edit(ActionEvent actionEvent) {
        if (orgFyDtFromBind.getValue() != null) {
            previousStartDate = (Date)orgFyDtFromBind.getValue();
        }
        this.maininfomode = "E";
        this.mode = "E";

        String PrntOrgId = null;
        Date date = new Date();
        OrgAppAMImpl am = (OrgAppAMImpl)resolvElDC("OrgAppAMDataControl");
        OrgVOImpl v2 = am.getOrg2();
        Row currentRow = v2.getCurrentRow();

        Integer UserId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        String CldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        String OrgId = currentRow.getAttribute("OrgId").toString();
        String Slocid = resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}");
        //  System.out.println("UserId = "+UserId+"     CldId = "+CldId+"   OrgId = "+OrgId+"   Slocid = "+Slocid);

        if (currentRow.getAttribute("OrgIdParent") != null) {
            System.out.println(" currentRow.getAttribute(OrgIdParent) != null");
            PrntOrgId = currentRow.getAttribute("OrgIdParent").toString();
            date = (Date)currentRow.getAttribute("OrgFyStDt");
            System.out.println("date when org in a childdd then datee>>>>" + date);
            // System.out.println("ORGPRNTID :"+PrntOrgId+"\n FYSTDATE :"+date);
        }
        System.out.println("when or is aa head office then date<<<<>>>>" + date);
        System.out.println("value of org type isss====" + currentRow.getAttribute("OrgType"));
        if (currentRow.getAttribute("OrgType").toString().equals("51")) {
            String i =
                callStoredFunction(Types.VARCHAR, "FN_CHK_UPDATE_FY (?,?,?,?,?,?,?)", new Object[] { CldId, Slocid,
                                                                                                     OrgId, PrntOrgId,
                                                                                                     null, UserId,
                                                                                                     "U" }).toString();
            System.out.println("in edit FN_CHK_UPDATE_FY (?,?,?,?,?,?,?) = " + i);
            if (i.equals("N")) {
                this.orgFyDtFromBind.setDisabled(true);
                // fydate="D";
            }
            if (i.equals("Y")) {
                this.orgFyDtFromBind.setDisabled(false);
            }
        } else {
            this.orgFyDtFromBind.setDisabled(true);
        }
        //this.delOrgBtnBind.setDisabled(true);
        this.orgTreeBind.setRowSelection("none");
        // AdfFacesContext.getCurrentInstance().addPartialTarget(orgTreeBind);

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

    public void save(ActionEvent actionEvent) {
        // System.out.println("---------------------SAVE BTN CLICKED --------------------");
        String PrntOrgId = null;
        Date date = new Date();


        OrgAppAMImpl am = (OrgAppAMImpl)resolvElDC("OrgAppAMDataControl");
        OrgVOImpl v2 = am.getOrg2();
        String CldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer Slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        Integer UserId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        //  System.out.println("CldId = "+CldId+"   Slocid = "+Slocid+"     UserId = "+UserId);

        Row currentRow = v2.getCurrentRow();
        // System.out.println("1. currentRow = "+currentRow);
        //System.out.println("2. currentRow = "+currentRow.getAttribute("OrgId"));

        if (currentRow.getAttribute("OrgIdParent") != null) {
            PrntOrgId = currentRow.getAttribute("OrgIdParent").toString();
            System.out.println("PrntOrgId = " + PrntOrgId);

        }
        date = (Date)currentRow.getAttribute("OrgFyStDt");
        System.out.println("date = " + date);
        String OrgId = currentRow.getAttribute("OrgId").toString();


        /* System.out.println("OrgId = " + OrgId + "previousStartDate = " + previousStartDate);
        System.out.println("CldId>>>>>"+CldId);
        System.out.println("Slocid>>>>>"+Slocid);
        System.out.println("PrntOrgId>>>>>"+PrntOrgId);
        System.out.println("OrgId>>>>>>"+OrgId);
        System.out.println("date>>>>>>>"+date);
        System.out.println("previousStartDate>>>>"+previousStartDate);
        System.out.println("UserId>>>>>>>"+UserId); */

        String u =
            callStoredFunction(Types.VARCHAR, "fn_org_op (?,?,?,?,?,?,?,?,?)", new Object[] { CldId, Slocid, PrntOrgId,
                                                                                              OrgId, date,
                                                                                              previousStartDate, null,
                                                                                              UserId,
                                                                                              "U" }).toString();
        System.out.println("VALUEEEEE RETURN BYYYYYY FUN ORG IN BEANNNN IS = " + u);
        if ((u.equals("Y"))) {
            // System.out.println("u = y so commit it :-) ");
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();
            FacesMessage message = new FacesMessage("Record Updated Succesfully");
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            System.out.println("commit = operationBinding.getErrors() = " + operationBinding.getErrors());
            this.maininfomode = "V";
            this.mode = "V";
            //this.delOrgBtnBind.setDisabled(false);
            this.orgTreeBind.setRowSelection("single");
            AdfFacesContext.getCurrentInstance().addPartialTarget(orgTreeBind);

        } else {
            FacesMessage message = new FacesMessage("Error in Post Organization");
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
         Key curRowKey=null;
        BindingContainer bindings = getBindings();
        DCIteratorBinding dcitr = (DCIteratorBinding)bindings.get("Org2Iterator");
        if (dcitr.getCurrentRow() != null) {
                curRowKey = dcitr.getCurrentRow().getKey();
               }
        am.getOrgVO1().executeQuery();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
       if (curRowKey != null) {
                dcitr.setCurrentRowWithKey(curRowKey.toStringFormat(true));
                }
        orgFyDtFromBind.setDisabled(true);
        am.getOrgFy2().executeQuery();
    
        
        // orgTreeBind.__updateChildrenImpl(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(orgFyBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(orgTreeBind);
    }

    public void cancel(ActionEvent actionEvent) {
        Key ky = null;
        Key key = null;
        BindingContainer bindings = getBindings();
        DCIteratorBinding dcitr1 = (DCIteratorBinding)bindings.get("LovOrgType1Iterator");
        DCIteratorBinding dcitr = (DCIteratorBinding)bindings.get("Org2Iterator");
        if (dcitr1.getCurrentRow() != null) {
            key = dcitr1.getCurrentRow().getKey();
            if (dcitr.getCurrentRow() != null) {
                ky = dcitr.getCurrentRow().getKey();
            }
        }
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        if (key != null) {
            dcitr1.setCurrentRowWithKey(key.toStringFormat(true));
            if (ky != null) {
                dcitr.setCurrentRowWithKey(ky.toStringFormat(true));
            }
        }
        this.maininfomode = "V";
        this.mode = "V";
        orgFyDtFromBind.setDisabled(true);
        this.orgTreeBind.setRowSelection("single");
        AdfFacesContext.getCurrentInstance().addPartialTarget(orgTreeBind);
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
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

    public void setPop_loc(RichPopup pop_loc) {
        this.pop_loc = pop_loc;
    }

    public RichPopup getPop_loc() {
        return pop_loc;
    }

    public void setDisableProfile(boolean disableProfile) {
        this.disableProfile = disableProfile;
    }

    public boolean isDisableProfile() {
        return disableProfile;
    }

    public void setPop_org(RichPopup pop_org) {
        this.pop_org = pop_org;
    }

    public RichPopup getPop_org() {
        return pop_org;
    }


    public void setDisableOrg(boolean disableOrg) {
        this.disableOrg = disableOrg;
    }

    public boolean isDisableOrg() {
        return disableOrg;
    }

    public void setOrgDesc(RichInputText orgDesc) {
        this.orgDesc = orgDesc;
    }

    public RichInputText getOrgDesc() {
        return orgDesc;
    }

    public void setOrgAlias(RichInputText orgAlias) {
        this.orgAlias = orgAlias;
    }

    public RichInputText getOrgAlias() {
        return orgAlias;
    }

    public void setOrgParent(RichSelectOneChoice orgParent) {
        this.orgParent = orgParent;
    }

    public RichSelectOneChoice getOrgParent() {
        return orgParent;
    }

    public void setOrgCountry(RichSelectOneChoice orgCountry) {
        this.orgCountry = orgCountry;
    }

    public RichSelectOneChoice getOrgCountry() {
        return orgCountry;
    }

    public void setOrgLang(RichSelectOneChoice orgLang) {
        this.orgLang = orgLang;
    }

    public RichSelectOneChoice getOrgLang() {
        return orgLang;
    }

    public void setOrgPreLang(RichSelectOneChoice orgPreLang) {
        this.orgPreLang = orgPreLang;
    }

    public RichSelectOneChoice getOrgPreLang() {
        return orgPreLang;
    }


    public void setOrgCurr1(RichSelectOneChoice orgCurr1) {
        this.orgCurr1 = orgCurr1;
    }

    public RichSelectOneChoice getOrgCurr1() {
        return orgCurr1;
    }

    public void setOrgPrfFy(RichSelectOneChoice orgPrfFy) {
        this.orgPrfFy = orgPrfFy;
    }

    public RichSelectOneChoice getOrgPrfFy() {
        return orgPrfFy;
    }

    public void setOrgPrfMst(RichSelectBooleanCheckbox orgPrfMst) {
        this.orgPrfMst = orgPrfMst;
    }

    public RichSelectBooleanCheckbox getOrgPrfMst() {
        return orgPrfMst;
    }

    public void setOrgPrfREnt(RichSelectBooleanCheckbox orgPrfREnt) {
        this.orgPrfREnt = orgPrfREnt;
    }

    public RichSelectBooleanCheckbox getOrgPrfREnt() {
        return orgPrfREnt;
    }

    public void setOrgPrfRNa(RichSelectBooleanCheckbox orgPrfRNa) {
        this.orgPrfRNa = orgPrfRNa;
    }

    public RichSelectBooleanCheckbox getOrgPrfRNa() {
        return orgPrfRNa;
    }

    public void setOrgPrfRCog(RichSelectBooleanCheckbox orgPrfRCog) {
        this.orgPrfRCog = orgPrfRCog;
    }

    public RichSelectBooleanCheckbox getOrgPrfRCog() {
        return orgPrfRCog;
    }

    public void setOrgPrfRCc(RichSelectBooleanCheckbox orgPrfRCc) {
        this.orgPrfRCc = orgPrfRCc;
    }

    public RichSelectBooleanCheckbox getOrgPrfRCc() {
        return orgPrfRCc;
    }

    public void setOrgPrfCreateCc(RichSelectBooleanCheckbox orgPrfCreateCc) {
        this.orgPrfCreateCc = orgPrfCreateCc;
    }

    public RichSelectBooleanCheckbox getOrgPrfCreateCc() {
        return orgPrfCreateCc;
    }

    public void setOrgPrfCreateCog(RichSelectBooleanCheckbox orgPrfCreateCog) {
        this.orgPrfCreateCog = orgPrfCreateCog;
    }

    public RichSelectBooleanCheckbox getOrgPrfCreateCog() {
        return orgPrfCreateCog;
    }

    public void setOrgPrfCreateEnt(RichSelectBooleanCheckbox orgPrfCreateEnt) {
        this.orgPrfCreateEnt = orgPrfCreateEnt;
    }

    public RichSelectBooleanCheckbox getOrgPrfCreateEnt() {
        return orgPrfCreateEnt;
    }

    public void setOrgPrfCreateNa(RichSelectBooleanCheckbox orgPrfCreateNa) {
        this.orgPrfCreateNa = orgPrfCreateNa;
    }

    public RichSelectBooleanCheckbox getOrgPrfCreateNa() {
        return orgPrfCreateNa;
    }

    public void setPop_rgn(RichPopup pop_rgn) {
        this.pop_rgn = pop_rgn;
    }

    public RichPopup getPop_rgn() {
        return pop_rgn;
    }


    public void editRegistration(ActionEvent actionEvent) {
        showPopup(pop_rgn, true);
    }

    public void setPop_comm(RichPopup pop_comm) {
        this.pop_comm = pop_comm;
    }

    public RichPopup getPop_comm() {
        return pop_comm;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setProfilemode(String profilemode) {
        this.profilemode = profilemode;
    }

    public String getProfilemode() {
        return profilemode;
    }

    public void setMaininfomode(String maininfomode) {
        this.maininfomode = maininfomode;
    }

    public String getMaininfomode() {
        return maininfomode;
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
     * Used for the creation of new Org
     * @param actionEvent
     */
    public void createOrg(ActionEvent actionEvent) {
        //  System.out.println("--------------create org btn clicked----------------");
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Createwithparameters");
        operationBinding.execute();
        OrgAppAMImpl am = (OrgAppAMImpl)resolvElDC("OrgAppAMDataControl");
        OrgVOImpl v2 = am.getOrg2();
        Row currentRow = v2.getCurrentRow();
        //  System.out.println("OrgType ================> "+currentRow.getAttribute("OrgType"));
        if (currentRow.getAttribute("OrgType") != null) {
            if (currentRow.getAttribute("OrgType").toString().equals("51")) {
                flag = "true";
                  System.out.println("flag = "+flag);
            }

        }

        this.showPopup(createOrgPop, true);
    }

    public void setCreateOrgPop(RichPopup createOrgPop) {
        this.createOrgPop = createOrgPop;
    }

    public RichPopup getCreateOrgPop() {
        return createOrgPop;
    }

    public void setOrgFyStDtBind(RichInputDate orgFyStDtBind) {
        this.orgFyStDtBind = orgFyStDtBind;
    }

    public RichInputDate getOrgFyStDtBind() {
        return orgFyStDtBind;
    }

    public void orgIdParentVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent != null) {
            String newValue = valueChangeEvent.getNewValue().toString();
            //  System.out.println("NEW VALUE IS : "+newValue);

            OrgAppAMImpl am = (OrgAppAMImpl)resolvElDC("OrgAppAMDataControl");
            ViewObjectImpl v = getAppModule().getOrg2();
            Row[] rw = v.getFilteredRows("OrgId", newValue);
            Object obj = new Object();
            //  System.out.println("ROW.LENGTH : ::::::::::::::::::::::"+rw.length);
            if (rw.length == 0) {
                System.out.println("---------------SET DISABLED");
                //this.orgFyStDtBind.setDisabled(false);
                this.orgFyStDtBind.setValue(null);
            } else {

                for (Row r : rw) {
                    obj = r.getAttribute("OrgFyStDt");
                    //  System.out.println("obj = ORG_FY_ST_DT = "+obj);
                    //    System.out.println("ORG_FY_ST_DT :"+r.getAttribute("OrgFyStDt"));
                    this.orgFyStDtBind.setValue(r.getAttribute("OrgFyStDt"));

                    this.orgFyStDtBind.setDisabled(true);
                    //  System.out.println("after set in VCL000000000 = "+orgFyStDtBind.getValue());
                }
            }


        } else {

            this.orgFyStDtBind.setDisabled(false);
            this.orgFyStDtBind.setValue(null);
        }
        System.out.println("orgidparent=" + valueChangeEvent.getNewValue().toString());
        LOVCOAVOImpl lov = getAppModule().getLOVCOAVO1();
        /*lov.setBindCldId("0000");
        lov.setBindSlocId(1);
        lov.setBindOrgIdParent((String)valueChangeEvent.getNewValue());
        lov.setBindOrgId(null); */
        AdfFacesContext.getCurrentInstance().addPartialTarget(coaTransBinding);
        lov.executeQuery();
        getBindings().getOperationBinding("Execute2").execute();
    }

    public void orgCreateDL(DialogEvent dialogEvent) {
        //  System.out.println("---------------orgCreateDL---------------");
        System.out.println("parent org value===" + parentOrgBind.getValue());
        System.out.println("dateee issss===" + orgFyStDtBind.getValue());

        try {
            if (dialogEvent.getOutcome().name().equals("ok")) {


                flag = "false";
                String PrntOrgId = null;
                Date date = new Date();
                OrgAppAMImpl am = (OrgAppAMImpl)resolvElDC("OrgAppAMDataControl");
                OrgVOImpl v2 = am.getOrg2();
                Integer orgtyp = Integer.parseInt(v2.getCurrentRow().getAttribute("OrgType").toString());
                System.out.println("value of orgtypeeeee isss=>>>>" + orgtyp);
                String CldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
                Integer Slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
                Integer UserId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));

                if (orgCurrencyBinding == null) {
                    //  System.out.println("orgCurrId = "+orgCurrId.getValue().toString());
                    FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.555']}")); //Fields Cann't be blank
                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(orgCurrencyBinding.getClientId(), message);
                } else if (orgtyp == 51 && (orgFyStDtBind.getValue() == null || orgFyStDtBind.getValue() == "")) {
                    System.out.println("---------------------------");
                    FacesMessage message =
                        new FacesMessage("Organisation Financial Start Date is Required"); //Fields Cann't be blank
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(orgFyStDtBind.getClientId(), message);
                } else {
                    //  System.out.println("orgCurrId = "+orgCurrId.getValue().toString());
                    System.out.println("before  commit");

                    BindingContainer bindings = getBindings();
                    OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                    operationBinding.execute();
                    //  System.out.println("commit " + operationBinding.getErrors());

                    if (operationBinding.getErrors().isEmpty()) {
                        FacesMessage message =
                            new FacesMessage(resolvEl("#{bundle['MSG.91']}")); //Record Saved Successfully.
                        message.setSeverity(FacesMessage.SEVERITY_INFO);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }


                    if (v2.getCurrentRow() != null) {
                        System.out.println("CAme in the current......... ");
                        Row currentRow = v2.getCurrentRow();

                        if (currentRow.getAttribute("OrgIdParent") != null) {
                            PrntOrgId = currentRow.getAttribute("OrgIdParent").toString();
                            date = null;
                        }
                        if (currentRow.getAttribute("OrgFyStDt") != null) {
                            date = (Date)currentRow.getAttribute("OrgFyStDt");
                            System.out.println("----ELSE PrntOrgId = " + PrntOrgId + "   date = " + date);
                        }
                        String OrgId =
                            (currentRow.getAttribute("OrgId") != null) ? currentRow.getAttribute("OrgId").toString() :
                            null;
                        System.out.println("New Org ID is:  ......... " + OrgId +"daTE IS "+ date +" parent Id" +PrntOrgId+"date is"+date +"user id ="+UserId);
                        //String  OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
                        System.out.println();

                        // am.getDBTransaction().postChanges();

                        /*  System.out.println("praent org id in cse of insert is=  " + PrntOrgId);
                        System.out.println("org id in case of insert is=   " + OrgId);
                        System.out.println("date in cae of insert is=  " + date); */
                        String u =
                            callStoredFunction(Types.VARCHAR, "fn_org_op (?,?,?,?,?,?,?,?,?)", new Object[] { CldId,
                                                                                                              Slocid,
                                                                                                              PrntOrgId,
                                                                                                              OrgId,
                                                                                                              date,
                                                                                                              null,
                                                                                                              null,
                                                                                                              UserId,
                                                                                                              "I" }).toString();
                        System.out.println("output of fn_org_op in insert mode= " + u);
                        if ((u.equals("Y"))) {
                            System.out.println("iam in org_op_fy whic valuee is yyyyy");
                            OperationBinding op = bindings.getOperationBinding("Commit");
                            op.execute();
                        } else {
                             System.out.println("return valuee by the function is N");
                            FacesMessage message = new FacesMessage("Error in Post Organization");
                            message.setSeverity(FacesMessage.SEVERITY_INFO);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message); 
                        }
                    }
                    ResetUtils.reset(this.orgTreeBind);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.orgTreeBind);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.orgPnlFrmLoutBind);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.orgPnlFrmLout2Bind);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(parentOrgBind);
                    fyTabBind.setDisclosed(false);
                    infoTabBind.setDisclosed(false);
                    docTabBind.setDisclosed(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabBind);
                    ViewObjectImpl vo = am.getOrgDocVO1();
                    vo.executeQuery();
                    ViewObjectImpl fyvo = am.getOrgFy2();
                    fyvo.executeQuery();
                }

                /* else if (dialogEvent.getOutcome().name().equals("cancel")) {
                                                 System.out.println("cancel btn clicked");
                                                BindingContainer bindings = getBindings();
                                                OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
                                                operationBinding.execute();
                                                // System.out.println("ROLLBACK!");
                                            } */


            }
        } catch (Exception e) {
            e.printStackTrace();

            FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.120']}"));

            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);

        }
        orgTreeBind.__updateChildrenImpl(FacesContext.getCurrentInstance());

        AdfFacesContext.getCurrentInstance().addPartialTarget(userAddBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(orgFyEditBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(usrtablebind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(orgFyBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(orgDocFyBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(orgDocBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(orgDocTypBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(docResetTypBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(othdetailEditBind);
    }

    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {
            OrgAppAMImpl am = (OrgAppAMImpl)resolvElDC("OrgAppAMDataControl");
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

    public void createOrgCnclList(PopupCanceledEvent popupCanceledEvent) {
        Key key = null;
        Key key1 = null;
        ViewObjectImpl vo = am.getLovOrgType1();
        Row rw[] = vo.getAllRowsInRange();
        chkcount = rw.length;
        System.out.println("chkcountttttt is===" + chkcount);
        flag = "false";
        DCIteratorBinding dcitr = null;
        DCIteratorBinding dcitr1 = null;
        BindingContainer bindings = getBindings();
        if (chkcount > 1) {
            dcitr = (DCIteratorBinding)bindings.get("LovOrgType1Iterator");
            if (dcitr.getCurrentRow() != null) {
                key = dcitr.getCurrentRow().getKey();

            }
        }
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        // System.out.println("ROLLBACKED!");
        BindingContainer binding = getBindings();
        OperationBinding operationBindin = bindings.getOperationBinding("Execute");
        operationBindin.execute();
        if (key != null) {
            dcitr.setCurrentRowWithKey(key.toStringFormat(true));

        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.orgPnlFrmLoutBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.orgPnlFrmLout2Bind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(orgFyEditBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(othdetailEditBind);
        chkcount = 0;
    }

    public void setOrgPnlFrmLoutBind(RichPanelFormLayout orgPnlFrmLoutBind) {
        this.orgPnlFrmLoutBind = orgPnlFrmLoutBind;
    }

    public RichPanelFormLayout getOrgPnlFrmLoutBind() {
        return orgPnlFrmLoutBind;
    }

    public void setOrgPnlFrmLout2Bind(RichPanelFormLayout orgPnlFrmLout2Bind) {
        this.orgPnlFrmLout2Bind = orgPnlFrmLout2Bind;
    }

    public RichPanelFormLayout getOrgPnlFrmLout2Bind() {
        return orgPnlFrmLout2Bind;
    }

    public void addOrgDtlsList(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();
        this.orgDtldis = false;
        this.editOrgDtlImgBind.setDisabled(true);
        this.saveOrgDtlImgBind.setDisabled(false);
        this.cancelOrgDtlImgBind.setDisabled(false);
        this.orgTreeBind.setRowSelection("none");
        AdfFacesContext.getCurrentInstance().addPartialTarget(orgTreeBind);
    }

    public void saveOrgDtlsList(ActionEvent actionEvent) {
        System.out.println("information valueee is==" + transAddBind.getValue());
        if ((transAddBind.getValue() == "" || transAddBind.getValue() == null) &&
            (transAdd2Bind.getValue() == "" || transAdd2Bind.getValue() == null)) {
            FacesMessage message = new FacesMessage("please enter the address  of organisation !");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } else {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();

            AdfFacesContext.getCurrentInstance().addPartialTarget(this.orgDtlPnlFrmLoutBind);
            // AdfFacesContext.getCurrentInstance().addPartialTarget(this.orgDtlPnlFrmLout2Bind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.orgDtlPnlFrmLout3Bind);
            OrgAppAMImpl am = (OrgAppAMImpl)resolvElDC("OrgAppAMDataControl");
            am.getOrgOthDtlVO1().executeQuery();
            this.orgDtldis = true;
            this.editOrgDtlImgBind.setDisabled(false);
            this.saveOrgDtlImgBind.setDisabled(true);
            this.cancelOrgDtlImgBind.setDisabled(true);
            this.orgTreeBind.setRowSelection("single");

            AdfFacesContext.getCurrentInstance().addPartialTarget(orgTreeBind);
        }
    }

    public void setOrgDtlPnlFrmLoutBind(RichPanelFormLayout orgDtlPnlFrmLoutBind) {
        this.orgDtlPnlFrmLoutBind = orgDtlPnlFrmLoutBind;
    }

    public RichPanelFormLayout getOrgDtlPnlFrmLoutBind() {
        return orgDtlPnlFrmLoutBind;
    }

    public void setOrgDtlPnlFrmLout2Bind(RichPanelFormLayout orgDtlPnlFrmLout2Bind) {
        this.orgDtlPnlFrmLout2Bind = orgDtlPnlFrmLout2Bind;
    }

    public RichPanelFormLayout getOrgDtlPnlFrmLout2Bind() {
        return orgDtlPnlFrmLout2Bind;
    }

    public void setOrgDtlPnlFrmLout3Bind(RichPanelFormLayout orgDtlPnlFrmLout3Bind) {
        this.orgDtlPnlFrmLout3Bind = orgDtlPnlFrmLout3Bind;
    }

    public RichPanelFormLayout getOrgDtlPnlFrmLout3Bind() {
        return orgDtlPnlFrmLout3Bind;
    }

    public void orgDtlsCnclList(ActionEvent actionEvent) {
        Key key = null;
        Key ky = null;
        BindingContainer bindings = getBindings();
        DCIteratorBinding dcitr1 = (DCIteratorBinding)bindings.get("LovOrgType1Iterator");
        DCIteratorBinding dcitr = (DCIteratorBinding)bindings.get("Org2Iterator");
        if (dcitr.getCurrentRow() != null) {
            ky = dcitr1.getCurrentRow().getKey();
            if (dcitr.getCurrentRow() != null) {
                key = dcitr.getCurrentRow().getKey();
            }
        }
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        if (ky != null) {
            dcitr1.setCurrentRowWithKey(ky.toStringFormat(true));
            if (key != null) {
                dcitr.setCurrentRowWithKey(key.toStringFormat(true));
            }
        }

        /*  AdfFacesContext.getCurrentInstance().addPartialTarget(this.orgDtlPnlFrmLoutBind);
        //  AdfFacesContext.getCurrentInstance().addPartialTarget(this.orgDtlPnlFrmLout2Bind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.orgDtlPnlFrmLout3Bind); */
        this.orgDtldis = true;

        this.saveOrgDtlImgBind.setDisabled(true);

        if (resolvEl("#{bindings.OrgOthDtlVO1Iterator.estimatedRowCount}").equals("0")) {
            this.editOrgDtlImgBind.setDisabled(true);
        } else {
            this.editOrgDtlImgBind.setDisabled(false);
        }


        this.cancelOrgDtlImgBind.setDisabled(true);
        this.orgTreeBind.setRowSelection("single");
        AdfFacesContext.getCurrentInstance().addPartialTarget(orgTreeBind);
    }


    public void setOrgDtldis(Boolean orgDtldis) {
        this.orgDtldis = orgDtldis;
    }

    public Boolean getOrgDtldis() {
        return orgDtldis;
    }

    public void orgDtlEditList(ActionEvent actionEvent) {
        this.orgDtldis = false;
        this.saveOrgDtlImgBind.setDisabled(true);
        this.saveOrgDtlImgBind.setDisabled(false);
        this.cancelOrgDtlImgBind.setDisabled(false);
        this.editOrgDtlImgBind.setDisabled(true);
        this.orgTreeBind.setRowSelection("none");
        AdfFacesContext.getCurrentInstance().addPartialTarget(orgTreeBind);

    }


    public void setSaveOrgDtlBtnBind(RichCommandButton saveOrgDtlBtnBind) {
        this.saveOrgDtlBtnBind = saveOrgDtlBtnBind;
    }

    public RichCommandButton getSaveOrgDtlBtnBind() {
        return saveOrgDtlBtnBind;
    }

    public void setCancelOrgDtlBtnBind(RichCommandButton cancelOrgDtlBtnBind) {
        this.cancelOrgDtlBtnBind = cancelOrgDtlBtnBind;
    }

    public RichCommandButton getCancelOrgDtlBtnBind() {
        return cancelOrgDtlBtnBind;
    }

    public void setEditOrgDtlBtnBind(RichCommandButton editOrgDtlBtnBind) {
        this.editOrgDtlBtnBind = editOrgDtlBtnBind;
    }

    public RichCommandButton getEditOrgDtlBtnBind() {
        return editOrgDtlBtnBind;
    }

    /*  public void phoneNoValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
      System.out.println("Inside Validator");
         String msg2 = "";
        if (object != null) {
            String phnNo = object.toString();
            int openB = 0;
            int closeB = 0;
            boolean closeFg = false;
            char[] xx = phnNo.toCharArray();
            for (char c : xx) {
                if (c == '(') {
                    openB = openB + 1;
                } else if (c == ')') {
                    closeB = closeB + 1;
                }

                if (closeB > openB) {
                    closeFg =
                            true; //closed brackets will not be more than open brackets at any given                    time.
                }
            }

            //if openB=0 then no. of closing and opening brackets equal || opening bracket must always come before closing brackets
            //closing brackets must not come before first occurrence of openning bracket

            if (openB != closeB || closeFg == true || (phnNo.lastIndexOf("(") > phnNo.lastIndexOf(")")) ||
                (phnNo.indexOf(")") < phnNo.indexOf("("))) {
                msg2 = resolvEl("#{bundle['MSG.7']}");
               // FacesMessage message2 = new FacesMessage(msg2);
                //message2.setSeverity(FacesMessage.SEVERITY_ERROR);
               // throw new ValidatorException(message2);
               throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,msg2,null));
            }

            if (phnNo.contains("()")) {
                msg2 = resolvEl("#{bundle['MSG.170']}");
                //FacesMessage message2 = new FacesMessage(msg2);
                //message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                //throw new ValidatorException(message2);
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,msg2,null));
            }
            if (phnNo.contains("(.") || phnNo.contains("(-") || phnNo.contains("-)")) {
                msg2 = resolvEl("#{bundle['MSG.196']}");
                //FacesMessage message2 = new FacesMessage(msg2);
               // message2.setSeverity(FacesMessage.SEVERITY_ERROR);
               throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,msg2,null));
            }

            openB = 0;
            closeB = 0;
            closeFg = false;
            //check for valid language name.Allowed- brackets,dots,hyphen

            String expression = "([0-9\\-\\+\\(\\)]+)";
            CharSequence inputStr = phnNo;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String error = resolvEl("#{bundle['MSG.202']}");
            // System.out.println("Index of plus is--->" + phnNo.lastIndexOf("+"));
            // System.out.println("Bracket index--->" + phnNo.charAt(0));

            if (matcher.matches()) {
                if (phnNo.contains("++") || phnNo.contains("--")) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                                  resolvEl("#{bundle['MSG.201']}")));
                } else if (phnNo.lastIndexOf("+") > 1) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                                  resolvEl("#{bundle['MSG.203']}")));
                } else if (phnNo.lastIndexOf("+") == 1 && phnNo.charAt(0) != '(') {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                                  resolvEl("#{bundle['MSG.203']}")));
                } else if (phnNo.startsWith(" ") || phnNo.endsWith(" ")) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                                  resolvEl("#{bundle['MSG.204']}")));
                }

            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                              resolvEl("#{bundle['MSG.205']}")));
            }

              }
        System.out.println("Exiting Validator");

    } */

    /*  public void emailValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String name = object.toString();
            String expression = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            CharSequence inputStr = name;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String msg = resolvEl("#{bundle['MSG.283']}");
            if (matcher.matches()) {

            } else {
                System.out.println("through email validation ");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg , null));
            }
        }
    } */

    public void delOrgList(ActionEvent actionEvent) {
        String PrntOrgId = null;
        Date date = new Date();
        OrgAppAMImpl am = (OrgAppAMImpl)resolvElDC("OrgAppAMDataControl");
        OrgVOImpl v2 = am.getOrg2();
        Row currentRow = v2.getCurrentRow();

        Integer UserId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        String CldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        String OrgId = currentRow.getAttribute("OrgId").toString();
        String Slocid = resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}");

        if (currentRow.getAttribute("OrgIdParent") != null) {
            PrntOrgId = currentRow.getAttribute("OrgIdParent").toString();
            date = (Date)currentRow.getAttribute("OrgFyStDt");
        }
        System.out.println("org id in delete action isss  " + OrgId);
        System.out.println("parent org id in delete action iss  " + PrntOrgId);
        String u =
            callStoredFunction(Types.VARCHAR, "FN_IS_ORG_DELETABLE (?,?,?)", new Object[] { CldId, OrgId, PrntOrgId }).toString();
        //System.out.println("value of  u for delete isss <<<<<<====>>>>>"+u);
        System.out.println("value return by FN_IS_ORG_DELETABLE=  " + u);
        if ((u.equals("Y"))) {

            this.showPopup(this.delPopupBind, true);
        } else {
            FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.486']}"));
            //change message or new message as "unable to delete org"
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);


        }
    }


    public void setDelOrgBtnBind(RichCommandButton delOrgBtnBind) {
        this.delOrgBtnBind = delOrgBtnBind;
    }

    public RichCommandButton getDelOrgBtnBind() {
        return delOrgBtnBind;
    }

    public void delOrgPopDialogList(DialogEvent dialogEvent) {
        //OrgAppAMImpl am = (OrgAppAMImpl)resolvElDC("OrgAppAMDataControl");
        String PrntOrgId = null;
        Date date = new Date();
        OrgAppAMImpl am = (OrgAppAMImpl)resolvElDC("OrgAppAMDataControl");
        OrgVOImpl v2 = am.getOrg2();
        Row currentRow = v2.getCurrentRow();

        String CldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer Slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        Integer UserId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        String OrgId = currentRow.getAttribute("OrgId").toString();

        if (currentRow.getAttribute("OrgIdParent") != null) {
            PrntOrgId = currentRow.getAttribute("OrgIdParent").toString();
            date = (Date)currentRow.getAttribute("OrgFyStDt");
        }
        if (dialogEvent.getOutcome().name().equals("ok")) {
            //                BindingContainer bindings = getBindings();
            //                OperationBinding operationBindin = bindings.getOperationBinding("Delete3");
            //                operationBindin.execute();
            //                BindingContainer in = getBindings();
            //                OperationBinding perationBin = in.getOperationBinding("Commit");
            //                perationBin.execute();
            //                BindingContainer bindin = getBindings();
            //                OperationBinding operationBindi = bindin.getOperationBinding("Delete2");
            //                operationBindi.execute();
            //                BindingContainer bin = getBindings();
            //                OperationBinding operationBin = bin.getOperationBinding("Commit");
            //                operationBin.execute();
            //                BindingContainer bindi = getBindings();
            //                OperationBinding operationBind = bindi.getOperationBinding("Execute7");
            //                operationBind.execute();
            //                BindingContainer b = getBindings();
            //                OperationBinding o = b.getOperationBinding("Execute5");
            //                o.execute();
            //                  commented as the function below deletes all the child tables of org, which were being deleted individually above
            //   2013-12-05 - suyash
            //op := fn_del_org(p_cld_id,p_org_id,p_org_id_parent), op values Y/N
            /* System.out.println("No of rows in org2=====" + am.getOrg2().getRowCount());
            System.out.println("No of rows in orgVo1=====" + am.getOrgVO1().getRowCount()); */

            /*  String u =
                callStoredFunction(Types.VARCHAR, "fn_del_org (?,?,?)", new Object[] { CldId, OrgId, PrntOrgId }).toString();
            */
            /*  System.out.println("CldIdCldId "+CldId);
           System.out.println("SlocidSlocid "+Slocid);
           System.out.println("PrntOrgIdPrntOrgId "+PrntOrgId);
           System.out.println("OrgIdOrgId "+OrgId);
           System.out.println("datedate "+date);
           System.out.println("UserIdUserId "+UserId); */
            
            try {
                System.out.println("ready to calll fn org op functionnnn");
                String u =
                    callStoredFunction(Types.VARCHAR, "fn_org_op (?,?,?,?,?,?,?,?,?)", new Object[] { CldId, Slocid,
                                                                                                      PrntOrgId, OrgId,
                                                                                                      date, null, null,
                                                                                                      UserId,
                                                                                                    "D" }).toString(); 
                 System.out.println("value return by fn_org_op issss===="+u); 
                if ((u.equals("Y"))) {
                    BindingContainer in = getBindings();
                    OperationBinding op = in.getOperationBinding("Delete6");
                    op.execute();
                    OperationBinding perationBin = in.getOperationBinding("Commit");
                    perationBin.execute();
                    AdfFacesContext.getCurrentInstance().addPartialTarget(nodeBind);
                    am.getLovOrgType1().executeQuery();
                    am.getOrg2().executeQuery();
                } else {
                    FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.486']}"));
                    //change message or new message as "unable to delete org"
                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);


                }
            } catch (Exception e) {
                FacesMessage message =
                    new FacesMessage("Deletion of Org not possible as Child Orgs(s) are present for this Org, Operation Cannot Proceed !!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }

            // System.out.println("Deleted!");
            /* AdfFacesContext.getCurrentInstance().addPartialTarget(this.orgDtlPnlFrmLoutBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.orgDtlPnlFrmLout2Bind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.orgDtlPnlFrmLout3Bind); */
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.orgTreeBind);
            /*  ResetUtils.reset(orgDtlPnlFrmLoutBind);
            ResetUtils.reset(orgDtlPnlFrmLout2Bind);
            ResetUtils.reset(orgDtlPnlFrmLout3Bind);  */
            ResetUtils.reset(orgTreeBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(orgTreeBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(createOrgPop);
            /* ResetUtils.reset(orgPnlFrmLout2Bind);
            ResetUtils.reset(orgPnlFrmLoutBind); */

        }
    }

    public void delOrgPopCanclDist(PopupCanceledEvent popupCanceledEvent) {
        this.showPopup(delPopupBind, false);
    }

    public void setDelPopupBind(RichPopup delPopupBind) {
        this.delPopupBind = delPopupBind;
    }

    public RichPopup getDelPopupBind() {
        return delPopupBind;
    }

    public void setOrgTreeBind(RichTreeTable orgTreeBind) {
        this.orgTreeBind = orgTreeBind;
    }

    public RichTreeTable getOrgTreeBind() {
        return orgTreeBind;
    }

    public void setNodeBind(RichOutputText nodeBind) {
        this.nodeBind = nodeBind;
    }

    public RichOutputText getNodeBind() {
        return nodeBind;
    }

    public void setOrgFyDtFromBind(RichInputDate orgFyDtFromBind) {
        this.orgFyDtFromBind = orgFyDtFromBind;
    }

    public RichInputDate getOrgFyDtFromBind() {
        return orgFyDtFromBind;
    }

    public OrgAppAMImpl getAppModule() {
        return (OrgAppAMImpl)resolvElDC("OrgAppAMDataControl");
    }

    public void addUsersToOrgACTION(ActionEvent actionEvent) {
        //System.out.println("usserr add action buttotnn-------------------");
        OrgVOImpl impl = this.getAppModule().getOrg2();
        Row currentRow = impl.getCurrentRow();
        Object attribute = currentRow.getAttribute("OrgId");
        //  System.out.println(currentRow.getAttribute("OrgId"));

        Integer glbl_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String glbl_cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");


        ViewObjectImpl addUsrCldSloc1 = this.getAppModule().getAddUsrCldSloc1();
        addUsrCldSloc1.setNamedWhereClauseParam("orgIdBind", attribute.toString());
        addUsrCldSloc1.setNamedWhereClauseParam("cldIdBind", glbl_cld_id);
        addUsrCldSloc1.setNamedWhereClauseParam("slocIdBind", glbl_sloc_id);

        addUsrCldSloc1.executeQuery();
        //System.out.println("cjeckkkkk fro popuppppppppppppp");
        this.showPopup(addUsrPopUpBind, true);

    }

    public void setAddUsrPopUpBind(RichPopup addUsrPopUpBind) {
        this.addUsrPopUpBind = addUsrPopUpBind;
    }

    public RichPopup getAddUsrPopUpBind() {
        return addUsrPopUpBind;
    }

    public void addUsrDialogList(DialogEvent dialogEvent) {

        BindingContainer bindings = getBindings();

        ViewObjectImpl orgSecUsrImpl = this.getAppModule().getOrgSecUsr1();
        Row currRow = null;

        /**
         * to get organisation id
         */
        OrgVOImpl impl = this.getAppModule().getOrg2();
        Row currentRow = impl.getCurrentRow();
        String CldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        String OrgId = currentRow.getAttribute("OrgId").toString();
        String Slocid = resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}");
        /**
         * to get selected users
         */
        ViewObjectImpl addUsrCldSloc1 = this.getAppModule().getAddUsrCldSloc1();
        RowSetIterator createRowSetIterator = addUsrCldSloc1.createRowSetIterator(null);

        while (createRowSetIterator.hasNext()) {
            Row next = createRowSetIterator.next();
            System.out.println("in while---------------");
            System.out.println("next.getAttribute(\"checkTrans\")" + next.getAttribute("checkTrans"));
            if (next.getAttribute("checkTrans") != null) {

                System.out.println("NEXT VALUE : " + next.getAttribute("checkTrans"));
                System.out.println(CldId + ":" + Slocid + ":" + OrgId + ":" + next.getAttribute("UsrId"));
                if (next.getAttribute("checkTrans").toString().equalsIgnoreCase("true")) {
                    count = count + 1;
                    System.out.println("INSIDE--------------------");
                    OperationBinding binding = bindings.getOperationBinding("Createwithparameters1");
                    binding.getParamsMap().put("UsrCldId", CldId);
                    binding.getParamsMap().put("SlocId", Slocid);
                    binding.getParamsMap().put("UsrOrgId", OrgId);
                    binding.getParamsMap().put("UsrId", next.getAttribute("UsrId"));
                    binding.execute();
                    System.out.println("after inserting row --------------------");

                }
            }


        }
        System.out.println("value of count isssss===" + count);
        if (count == 0) {
            /* FacesMessage message = new FacesMessage("please select atleast one user");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);  */
        } else {
            OperationBinding obinding = bindings.getOperationBinding("Commit");
            obinding.execute();
            createRowSetIterator.closeRowSetIterator();
            this.getAppModule().getUsrCldSloc4().executeQuery();
            if (obinding.getErrors().isEmpty()) {
                FacesMessage message = new FacesMessage("Selected users are added to the organisation.");
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else {
                FacesMessage message =
                    new FacesMessage("There was some error in adding users to organisation. Please try again.");
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(usrtablebind);
        }

    }

    public void addUsrPopCnclList(PopupCanceledEvent popupCanceledEvent) {
        // Add event code here...
    }

    public void removeUserACTION(ActionEvent actionEvent) {

        ViewObjectImpl cldSloc4 = this.getAppModule().getUsrCldSloc4();
        Row currentRow = cldSloc4.getCurrentRow();
        ViewObjectImpl impl = this.getAppModule().getOrgSecUsr1();
        RowQualifier rq = new RowQualifier(impl);
        rq.setWhereClause("UsrCldId = '" + currentRow.getAttribute("UsrCldId").toString() + "' and " + "SlocId = " +
                          Integer.parseInt(currentRow.getAttribute("SlocId").toString()) + " and " + "UsrOrgId = '" +
                          currentRow.getAttribute("UsrOrgId").toString() + "' and UsrId = " +
                          Integer.parseInt(currentRow.getAttribute("UsrId").toString()));

        System.out.println("Checking For Roles    " + rq.getExprStr());
        Integer roles = am.findroleLink();
        //Integer roles=0;
        System.out.println("no of Roles" + roles);

        if (roles.equals(new Integer(0))) {
            Row[] filteredRows = impl.getFilteredRows(rq);
            for (Row r : filteredRows) {

                try {
                    r.remove();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("row removed");
            }

            BindingContainer bindings = getBindings();
            bindings.getOperationBinding("Commit").execute();
            cldSloc4.executeQuery();
        } else {

            FacesMessage message =
                new FacesMessage(roles + "  Role has been assigned to the user for this organisation.Please Remove roles first.");
            //Financial year saved sucessfully.
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }


    }

    public void setAddFyPopUpBind(RichPopup addFyPopUpBind) {
        this.addFyPopUpBind = addFyPopUpBind;
    }

    public RichPopup getAddFyPopUpBind() {
        return addFyPopUpBind;
    }

    /**
     * add financial year dialog listener
     * @param dialogEvent
     */
    public void adddFyDialogLIST(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().toString().equalsIgnoreCase("ok")) {
            OperationBinding binding = getBindings().getOperationBinding("Commit");
            binding.execute();
            // this.getBindings().getOperationBinding("Execute1").execute();

            if (binding.getErrors().isEmpty()) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.563']}"));
                //Financial year saved sucessfully.
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else {
                FacesMessage message = new FacesMessage("Tnere was an error in creating financial year.");
                message.setSeverity(FacesMessage.SEVERITY_FATAL);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(orgFyBind);
        }
    }

    /**
     * add financial year popup cancel listener
     * @param popupCanceledEvent
     */
    public void addFyDialogCancelLIST(PopupCanceledEvent popupCanceledEvent) {
        Key key = null;
        Key key1 = null;
        Key key2 = null;
        BindingContainer bindings = getBindings();
        DCIteratorBinding dcitr2 = (DCIteratorBinding)bindings.get("LovOrgType1Iterator");
        DCIteratorBinding dcitr = (DCIteratorBinding)bindings.get("Org2Iterator");
        DCIteratorBinding dcitr1 = (DCIteratorBinding)bindings.get("OrgFy2Iterator");
        if (dcitr2.getCurrentRow() != null) {
            key2 = dcitr2.getCurrentRow().getKey();
            if (dcitr.getCurrentRow() != null) {
                key = dcitr.getCurrentRow().getKey();
                if (dcitr1.getCurrentRow() != null) {
                    key1 = dcitr1.getCurrentRow().getKey();
                }
            }
        }
        this.getBindings().getOperationBinding("Rollback").execute();
        if (key2 != null) {
            dcitr2.setCurrentRowWithKey(key2.toStringFormat(true));
            if (key != null) {
                dcitr.setCurrentRowWithKey(key.toStringFormat(true));
                if (key1 != null) {
                    dcitr1.setCurrentRowWithKey(key1.toStringFormat(true));
                }
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(orgFyBind);
    }

    /**
     *Add financial year
     * @param actionEvent
     */
    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public void addFyYearACTION(ActionEvent actionEvent) {
        this.getBindings().getOperationBinding("CreateInsert2").execute();
        labelMode = resolvElDCMsg("#{bundle['MSG.1051']}").toString();
        this.showPopup(addFyPopUpBind, true);

    }

    /**
     *Edit financial year
     * @param actionEvent
     */
    public void editFyYearACTION(ActionEvent actionEvent) {
        OperationBinding op = getBindings().getOperationBinding("opBalChkAllow");
        op.execute();
       // System.out.println("result in beaaaaaaaannnnnnn is=="+op.getResult());
        if(op.getResult()!=null)
        editAlowOp=op.getResult().toString();
        labelMode = resolvElDCMsg("#{bundle['LBL.2866']}").toString();
        this.showPopup(addFyPopUpBind, true);
    }

    /**
     *delete financial year
     * @param actionEvent
     */
    public void deleteFyYearACTION(ActionEvent actionEvent) {
        this.showPopup(delFyPopBind, true);
    }

    public void deleteFyDeleteDialogList(DialogEvent dialogEvent) {
        this.getBindings().getOperationBinding("Delete").execute();
        OperationBinding binding = getBindings().getOperationBinding("Commit");
        binding.execute();

        if (binding.getErrors().isEmpty()) {
            FacesMessage message = new FacesMessage("Financial year deleted sucessfully.");
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } else {
            FacesMessage message = new FacesMessage("Tnere was an error in deleting financial year.");
            message.setSeverity(FacesMessage.SEVERITY_FATAL);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
    }

    public void setDelFyPopBind(RichPopup delFyPopBind) {
        this.delFyPopBind = delFyPopBind;
    }

    public RichPopup getDelFyPopBind() {
        return delFyPopBind;
    }


    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }

    public void setLabelMode(String labelMode) {
        this.labelMode = labelMode;
    }

    public String getLabelMode() {
        return labelMode;
    }

    public void orgnameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String org = object.toString();
        //System.out.println("value of org objectttt=="+org);
        if (object != null) {
            ViewObjectImpl vo = am.getOrgVO1();
            Row[] rw = vo.getFilteredRows("OrgDesc", org);
            vo.executeQuery();
            //System.out.println("total no of filtered rows in org validator is==="+rw.length);
            if (rw.length > 1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "duplicate organisation name found !", null));

            }
        }

        /* OrgAppAMImpl am = (OrgAppAMImpl)resolvElDC("OrgAppAMDataControl");
    OrgVOImpl v2 = am.getOrg2();
        RowSetIterator rst = v2.createRowSetIterator(null);
        Row currentRow=am.getOrg2().getCurrentRow();

        while(rst.hasNext()){
        Row r1 = rst.next();
        // System.out.println("r1.getKey(): "+r1.getKey());
        if(r1!=currentRow){
        String stkactn=r1.getAttribute("StkActn").toString();
        String srno1=r1.getAttribute("SrNo").toString();
        if(srno1.equals(srno) && stkactn.equalsIgnoreCase(StkActn)) {
        flag="N"    }
} */
    }


    public void setPreviousStartDate(Date previousStartDate) {
        this.previousStartDate = previousStartDate;
    }

    public Date getPreviousStartDate() {
        return previousStartDate;
    }

    public void setOrgCurrencyBinding(RichInputListOfValues orgCurrencyBinding) {
        this.orgCurrencyBinding = orgCurrencyBinding;
    }

    public RichInputListOfValues getOrgCurrencyBinding() {
        return orgCurrencyBinding;
    }

    public void setCoaTransBinding(RichInputListOfValues coaTransBinding) {
        this.coaTransBinding = coaTransBinding;
    }

    public RichInputListOfValues getCoaTransBinding() {
        return coaTransBinding;
    }

    public void deleteDocNmAL(ActionEvent actionEvent) {
        ViewObjectImpl typevo = am.getOrgDocTypeVO1();
        showPopup(delwarningpopbind, true);
    }

    public void docAddAL(ActionEvent actionEvent) {
        DocMode = "A";
        String orgid = am.getOrg2().getCurrentRow().getAttribute("OrgId").toString();
        ViewObject docnm = am.getLovDocNm1();
        docnm.setNamedWhereClauseParam("bindorgId", orgid);
        docnm.setNamedWhereClauseParam("bindcldId", cldid);
        docnm.setNamedWhereClauseParam("bindslocId", slocid);
        docnm.executeQuery();
        BindingContainer bindings = getBindings();
        BindingContainer bc = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcitr = (DCIteratorBinding)bc.get("OrgDocVO1Iterator");
        if (dcitr.getCurrentRow() != null) {
            OperationBinding op = bindings.getOperationBinding("getLovDocNm1");
            op.execute();
        }
        if (docnm.getEstimatedRowCount() == 0) {
            FacesMessage message = new FacesMessage("There are no more documents available to add ! ");
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } else {
            NavigatableRowIterator navi = dcitr.getNavigatableRowIterator();
            Row newrow = navi.createRow();
            newrow.setNewRowState(Row.STATUS_INITIALIZED);
            //navi.insertRowAtRangeIndex(0, newrow);
            navi.insertRow(newrow); //insert new row before selected row
            dcitr.setCurrentRowWithKey(newrow.getKey().toStringFormat(true));
            showPopup(docPopupBinding, true);
            /* BindingContainer bindings = getBindings();
        showPopup(docPopupBinding, true);
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert1");
        operationBinding.execute();
        operationBinding = bindings.getOperationBinding("getLovDocNm1");
        operationBinding.execute(); */
        }
    }

    public void setParentOrgBind(RichSelectOneChoice parentOrgBind) {
        this.parentOrgBind = parentOrgBind;
    }

    public RichSelectOneChoice getParentOrgBind() {
        return parentOrgBind;
    }

    public void setDocPopupBinding(RichPopup docPopupBinding) {
        this.docPopupBinding = docPopupBinding;
    }

    public RichPopup getDocPopupBinding() {
        return docPopupBinding;
    }

    public void docDialogListener(DialogEvent dialogEvent) {
        Key key = null;
        ViewObjectImpl vo = am.getOrgDocVO1();
        BindingContainer bindings = getBindings();
        DCIteratorBinding dcitr = (DCIteratorBinding)bindings.get("OrgDocVO1Iterator");
        /* if (dcitr.getCurrentRow() != null) {
            key = dcitr.getCurrentRow().getKey();
        } */

        OperationBinding op = bindings.getOperationBinding("Commit");
        op.execute();    
                
        /* OperationBinding op2 = bindings.getOperationBinding("Rollback");
        op2.execute(); */
       /*  op = bindings.getOperationBinding("Execute3");
        op.execute();  */
       /*  if (key != null) {
            dcitr.setCurrentRowWithKey(key.toStringFormat(true));
        } */
        clearfilter();
        AdfFacesContext.getCurrentInstance().addPartialTarget(orgDocBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(orgDocTypBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(docResetTypBind);
    }

    public void docPopUpCL(PopupCanceledEvent popupCanceledEvent) {
        Key ky = null;
        Key key = null;
        Key key1 = null;
        BindingContainer bindings = getBindings();
        DCIteratorBinding dcitr1 = (DCIteratorBinding)bindings.get("LovOrgType1Iterator");
        DCIteratorBinding dcitr = (DCIteratorBinding)bindings.get("Org2Iterator");
        DCIteratorBinding dcitr2 = (DCIteratorBinding)bindings.get("OrgDocVO1Iterator");
        if (dcitr1.getCurrentRow() != null) {
            key = dcitr1.getCurrentRow().getKey();
            if (dcitr.getCurrentRow() != null) {
                ky = dcitr.getCurrentRow().getKey();
                if (dcitr2.getCurrentRow() != null && DocMode == "E") {
                    key1 = dcitr2.getCurrentRow().getKey();
                }
            }
        }
        this.getBindings().getOperationBinding("Rollback").execute();
        if (key != null) {
            dcitr1.setCurrentRowWithKey(key.toStringFormat(true));
            if (ky != null) {
                dcitr.setCurrentRowWithKey(ky.toStringFormat(true));
                if (key1 != null && DocMode == "E") {
                    dcitr2.setCurrentRowWithKey(key1.toStringFormat(true));
                }
            }
        }
        clearfilter();
        AdfFacesContext.getCurrentInstance().addPartialTarget(orgDocBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(orgDocTypBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(docResetTypBind);
        /* BindingContainer bindings = getBindings();
        OperationBinding op = bindings.getOperationBinding("Rollback");
        op.execute();
        op = bindings.getOperationBinding("Execute3");
        op.execute();*/
    }

    public void docEditAL(ActionEvent actionEvent) {
        DocMode = "E";
        showPopup(docPopupBinding, true);
    }

    public void setDocMode(String DocMode) {
        this.DocMode = DocMode;
    }

    public String getDocMode() {
        return DocMode;
    }

    public void docTypeAL(ActionEvent actionEvent) {
        DocMode = "A";
        BindingContainer bindings = getBindings();       
        String orgid = am.getOrg2().getCurrentRow().getAttribute("OrgId").toString();
        Integer docid = Integer.parseInt(am.getOrgDocVO1().getCurrentRow().getAttribute("OrgDocId").toString());
        ViewObject doctyp = am.getLovDocTypNmVO1();
        doctyp.setNamedWhereClauseParam("BindOrgId", orgid);
        doctyp.setNamedWhereClauseParam("BindSlocId", slocid);
        doctyp.setNamedWhereClauseParam("BindCldId", cldid);
        doctyp.setNamedWhereClauseParam("BindOrgDocId", docid);
        doctyp.setNamedWhereClauseParam("BindDocId", docid);
        doctyp.executeQuery();      
        DCIteratorBinding dcitr = (DCIteratorBinding)bindings.get("OrgDocTypeVO1Iterator");
        if (dcitr.getCurrentRow() != null) {
            OperationBinding operationBinding = bindings.getOperationBinding("getLovDocTypNmVO1");
            operationBinding.execute();
        }
        if (doctyp.getEstimatedRowCount() == 0) {
            FacesMessage message = new FacesMessage("There are no more document type available to add !");
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } else {
            showPopup(docTypePopup, true);
            OperationBinding operationBinding1 = bindings.getOperationBinding("CreateInsert3");
            operationBinding1.execute();
        }
    }

    public void setDocTypePopup(RichPopup docTypePopup) {
        this.docTypePopup = docTypePopup;
    }

    public RichPopup getDocTypePopup() {
        return docTypePopup;
    }

    public void docTypeDeleteAL(ActionEvent actionEvent) {
        showPopup(doctypWarnpopup, true);

    }

    public void editDocTypeNmAL(ActionEvent actionEvent) {
        DocMode = "E";
        showPopup(docTypePopup, true);
    }

    public void docTypPopupCL(PopupCanceledEvent popupCanceledEvent) {        
        Key key = null;
        Key key1 = null;
        Key key2 = null;
        BindingContainer bindings = getBindings();
        DCIteratorBinding dcitr2 = (DCIteratorBinding)bindings.get("LovOrgType1Iterator");
        DCIteratorBinding dcitr = (DCIteratorBinding)bindings.get("Org2Iterator");
        DCIteratorBinding dcitr1 = (DCIteratorBinding)bindings.get("OrgDocVO1Iterator");
        if (dcitr2.getCurrentRow() != null) {
            key2 = dcitr2.getCurrentRow().getKey();
            if (dcitr.getCurrentRow() != null) {
                key = dcitr.getCurrentRow().getKey();
                if (dcitr1.getCurrentRow() != null) {
                    key1 = dcitr1.getCurrentRow().getKey();
                }
            }
        }
        this.getBindings().getOperationBinding("Rollback").execute();      
        if (key2 != null) {
            dcitr2.setCurrentRowWithKey(key2.toStringFormat(true));
            if (key != null) {
                dcitr.setCurrentRowWithKey(key.toStringFormat(true));
                if (key1 != null) {
                    dcitr1.setCurrentRowWithKey(key1.toStringFormat(true));
                }
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(orgDocBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(orgDocTypBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(docResetTypBind);

        /*  BindingContainer bindings = getBindings();
        OperationBinding op = bindings.getOperationBinding("Rollback");
        op.execute();
        OperationBinding op1 = bindings.getOperationBinding("Execute4");
        op1.execute(); */
    }

    public void docTypNmDL(DialogEvent dialogEvent) {
        BindingContainer bindings = getBindings();
        Key parentKey = null;
        DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("OrgDocVO1Iterator");
        if (parentIter.getCurrentRow() != null) {
            parentKey = parentIter.getCurrentRow().getKey();
        }
        OperationBinding op = bindings.getOperationBinding("Commit");
        op.execute();
        op = bindings.getOperationBinding("Execute4");
        op.execute();
        if (parentKey != null) {
            parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(orgDocBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(orgDocTypBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(docResetTypBind);
    }


    public void setEditOrgDtlImgBind(RichCommandImageLink editOrgDtlImgBind) {
        this.editOrgDtlImgBind = editOrgDtlImgBind;
    }

    public RichCommandImageLink getEditOrgDtlImgBind() {
        return editOrgDtlImgBind;
    }

    public void setSaveOrgDtlImgBind(RichCommandImageLink saveOrgDtlImgBind) {
        this.saveOrgDtlImgBind = saveOrgDtlImgBind;
    }

    public RichCommandImageLink getSaveOrgDtlImgBind() {
        return saveOrgDtlImgBind;
    }

    public void setCancelOrgDtlImgBind(RichCommandImageLink cancelOrgDtlImgBind) {
        this.cancelOrgDtlImgBind = cancelOrgDtlImgBind;
    }

    public RichCommandImageLink getCancelOrgDtlImgBind() {
        return cancelOrgDtlImgBind;
    }

    public void orgDocTypResetAL(ActionEvent actionEvent) {
        DocMode = "A";
        BindingContainer bindings = getBindings();
        DCIteratorBinding dcitr = (DCIteratorBinding)bindings.get("OrgDocTypResetVO1Iterator");
        if (dcitr.getCurrentRow() != null) {
            OperationBinding operationBinding = bindings.getOperationBinding("getLovTranAttNm1");
            operationBinding.execute();
        }

        showPopup(orgDocTypResetPopBinding, true);
        OperationBinding op = bindings.getOperationBinding("CreateInsert4");
        op.execute();
    }

    public void setOrgDocTypResetPopBinding(RichPopup orgDocTypResetPopBinding) {
        this.orgDocTypResetPopBinding = orgDocTypResetPopBinding;
    }

    public RichPopup getOrgDocTypResetPopBinding() {
        return orgDocTypResetPopBinding;
    }

    public void docResetTypePopDL(DialogEvent dialogEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding op = bindings.getOperationBinding("Commit");
        op.execute();
        op = bindings.getOperationBinding("Execute5");
        op.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(orgDocBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(orgDocTypBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(docResetTypBind);
    }

    public void orgDocTypeRsetCL(PopupCanceledEvent popupCanceledEvent) {
        Key key = null;
        Key key1 = null;
        Key key2 = null;
        BindingContainer bindings = getBindings();
        DCIteratorBinding dcitr2 = (DCIteratorBinding)bindings.get("LovOrgType1Iterator");
        DCIteratorBinding dcitr = (DCIteratorBinding)bindings.get("Org2Iterator");
        DCIteratorBinding dcitr1 = (DCIteratorBinding)bindings.get("OrgDocVO1Iterator");
        if (dcitr2.getCurrentRow() != null) {
            key2 = dcitr2.getCurrentRow().getKey();
            if (dcitr.getCurrentRow() != null) {
                key = dcitr.getCurrentRow().getKey();
                if (dcitr1.getCurrentRow() != null) {
                    key1 = dcitr1.getCurrentRow().getKey();
                }
            }
        }
        if (defmode == "N") {
            defResetBind.resetValue();
        }
        this.getBindings().getOperationBinding("Rollback").execute();
        if (key2 != null) {
            dcitr2.setCurrentRowWithKey(key2.toStringFormat(true));
            if (key != null) {
                dcitr.setCurrentRowWithKey(key.toStringFormat(true));
                if (key1 != null)
                    dcitr1.setCurrentRowWithKey(key1.toStringFormat(true));
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(orgDocBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(orgDocTypBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(docResetTypBind);
        defmode = "Y";
    }

    public void docTypRestEditAL(ActionEvent actionEvent) {
        DocMode = "E";
        showPopup(orgDocTypResetPopBinding, true);
    }

    public void setDelwarningpopbind(RichPopup delwarningpopbind) {
        this.delwarningpopbind = delwarningpopbind;
    }

    public RichPopup getDelwarningpopbind() {
        return delwarningpopbind;
    }

    public void docNmdelDilogOkAL(DialogEvent dialogEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding op = bindings.getOperationBinding("delDocNm");
        op.execute();
        op = bindings.getOperationBinding("Commit");
        op.execute();
        op = bindings.getOperationBinding("Execute3");
        op.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(orgDocBind);

    }

    public void docNmdelDilogCancelAL(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding op = bindings.getOperationBinding("Rollback");
        op.execute();
    }

    public void orgDocFyAL(ActionEvent actionEvent) {
        DocMode = "A";
        BindingContainer bindings = getBindings();
        BindingContainer bc = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcitr = (DCIteratorBinding)bc.get("OrgDocFyVO1Iterator");
        if (dcitr.getCurrentRow() != null) {
            OperationBinding op = bindings.getOperationBinding("getLovDocNmForOrgDocFy1");
            op.execute();
            op = bindings.getOperationBinding("getLovDocTypNmForOrgDocFy1");
            op.execute();
        }
        NavigatableRowIterator navi = dcitr.getNavigatableRowIterator();
        Row newrow = navi.createRow();
        newrow.setNewRowState(Row.STATUS_INITIALIZED);
        //navi.insertRowAtRangeIndex(0, newrow);
        navi.insertRow(newrow);
        dcitr.setCurrentRowWithKey(newrow.getKey().toStringFormat(true));
        showPopup(popupforOrgDocFy, true);
        /* BindingContainer bindings=getBindings();
        OperationBinding op1=bindings.getOperationBinding("CreateInsert5");
        op1.execute() ; */
    }

    public void setPopupforOrgDocFy(RichPopup popupforOrgDocFy) {
        this.popupforOrgDocFy = popupforOrgDocFy;
    }

    public RichPopup getPopupforOrgDocFy() {
        return popupforOrgDocFy;
    }


    public void orgDocFyPopUpDL(DialogEvent dialogEvent) {
        System.out.println("----->" + docNmPopupBind.getValue());
        System.out.println("--------->" + docTypNmPopupBind.getValue());
        System.out.println("------------>" + docResetopupBind.getValue());
        if (docNmPopupBind.getValue() == null || docTypNmPopupBind.getValue() == null ||
            docResetopupBind.getValue() == null) {
            FacesMessage message = new FacesMessage("please enter document type and reset frequency !");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } else {
            BindingContainer bindings = getBindings();
            OperationBinding op = bindings.getOperationBinding("Commit");
            op.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(orgDocFyBind);
        }
    }

    public void orgDocFyPopUpCL(PopupCanceledEvent popupCanceledEvent) {
        Key orgky = null;
        Key orgfykey = null;
        Key key = null;
        BindingContainer bindings = getBindings();
        DCIteratorBinding dcitr1 = (DCIteratorBinding)bindings.get("LovOrgType1Iterator");
        DCIteratorBinding dcitr = (DCIteratorBinding)bindings.get("Org2Iterator");
        DCIteratorBinding orgfydcitr = (DCIteratorBinding)bindings.get("OrgFy2Iterator");
        if (dcitr1.getCurrentRow() != null) {
            key = dcitr1.getCurrentRow().getKey();
            if (dcitr.getCurrentRow() != null) {
                orgky = dcitr.getCurrentRow().getKey();
                if (orgfydcitr.getCurrentRow() != null) {
                    orgfykey = orgfydcitr.getCurrentRow().getKey();
                }
            }
        }
        OperationBinding op = bindings.getOperationBinding("Rollback");
        op.execute();
        op = bindings.getOperationBinding("Execute6");
        op.execute();
        if (key != null) {
            dcitr1.setCurrentRowWithKey(key.toStringFormat(true));
            if (orgky != null) {
                dcitr.setCurrentRowWithKey(orgky.toStringFormat(true));
                if (orgfykey != null)
                    orgfydcitr.setCurrentRowWithKey(orgfykey.toStringFormat(true));
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(orgDocFyBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(editBtnBind);
    }

    public void docOrgFyEditAL(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding op = bindings.getOperationBinding("chkEditorDelforOrgDocFy");
        op.execute();
        String result = op.getResult().toString();
        if (result.equalsIgnoreCase("Y")) {
            FacesMessage message =
                new FacesMessage("This document is already in use.So you can't edit this document !");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);

        } else {
            DocMode = "E";
            showPopup(popupforOrgDocFy, true);
        }
    }

    public void setOrgFyBind(RichTable orgFyBind) {
        this.orgFyBind = orgFyBind;
    }

    public RichTable getOrgFyBind() {
        AdfFacesContext.getCurrentInstance().addPartialTarget(orgFyEditBind);
        return orgFyBind;
    }

    public void docNmForOrgDocFy(ValueChangeEvent vce) {
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
        if (vce.getNewValue() != null) {
            docTypNmPopupBind.setValue("");
            docResetopupBind.setValue("");

        }
    }

    public void setDocNmPopupBind(RichSelectOneChoice docNmPopupBind) {
        this.docNmPopupBind = docNmPopupBind;
    }

    public RichSelectOneChoice getDocNmPopupBind() {
        return docNmPopupBind;
    }

    public void setDocTypNmPopupBind(RichSelectOneChoice docTypNmPopupBind) {
        this.docTypNmPopupBind = docTypNmPopupBind;
    }

    public RichSelectOneChoice getDocTypNmPopupBind() {
        return docTypNmPopupBind;
    }

    public void setDocResetopupBind(RichSelectOneChoice docResetopupBind) {
        this.docResetopupBind = docResetopupBind;
    }

    public RichSelectOneChoice getDocResetopupBind() {
        return docResetopupBind;
    }

    public void delDocForOrgDocFyAL(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding op = bindings.getOperationBinding("chkEditorDelforOrgDocFy");
        op.execute();
        String result = op.getResult().toString();
        if (result.equalsIgnoreCase("Y")) {
            FacesMessage message =
                new FacesMessage("This document is already in use.So you can not delete this document !");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);

        } else {
            warningmessage.append("<p style='color:red'><b>Are you sure you want to delete this document ?</b></p>");
            warningmessage.append("</body></html>");
            showPopup(orgDocFyDelPopBind, true);
        }
    }

    public void setWarningmessage(StringBuilder warningmessage) {
        this.warningmessage = warningmessage;
    }

    public StringBuilder getWarningmessage() {
        return warningmessage;
    }

    public void setOrgDocFyDelPopBind(RichPopup orgDocFyDelPopBind) {
        this.orgDocFyDelPopBind = orgDocFyDelPopBind;
    }

    public RichPopup getOrgDocFyDelPopBind() {
        return orgDocFyDelPopBind;
    }

    public void orgDocFyDelDL(DialogEvent dialogEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding op = bindings.getOperationBinding("Delete4");
        op.execute();
        op = bindings.getOperationBinding("Commit");
        op.execute();
        /* op=bindings.getOperationBinding("Execute6");
        op.execute(); */
        AdfFacesContext.getCurrentInstance().addPartialTarget(orgDocFyBind);
    }

    public void setOrgDocBind(RichTable orgDocBind) {
        this.orgDocBind = orgDocBind;
    }

    public RichTable getOrgDocBind() {
        return orgDocBind;
    }

    public void setOrgDocTypBind(RichTable orgDocTypBind) {
        this.orgDocTypBind = orgDocTypBind;
    }

    public RichTable getOrgDocTypBind() {
        return orgDocTypBind;
    }

    public void setDocResetTypBind(RichTable docResetTypBind) {
        this.docResetTypBind = docResetTypBind;
    }

    public RichTable getDocResetTypBind() {
        return docResetTypBind;
    }

    public void setOrgDocFyBind(RichTable orgDocFyBind) {
        this.orgDocFyBind = orgDocFyBind;
    }

    public RichTable getOrgDocFyBind() {
        AdfFacesContext.getCurrentInstance().addPartialTarget(editBtnBind);
        return orgDocFyBind;
    }


    public void docNoprefixOthValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null)
            if (object.toString().length() < 2) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              " Please enter atleast two character!", null));
            }
    }

    public void maxlengthValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String msg = "maximum length should be less than 11 or greater than 3 . ";
            Integer length = (Integer)object;
            if ((length > 11) || (length < 3)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }
    }


    public void setUsrtablebind(RichTable usrtablebind) {
        this.usrtablebind = usrtablebind;
    }

    public RichTable getUsrtablebind() {
        return usrtablebind;
    }

    public void setDoctypWarnpopup(RichPopup doctypWarnpopup) {
        this.doctypWarnpopup = doctypWarnpopup;
    }

    public RichPopup getDoctypWarnpopup() {
        return doctypWarnpopup;
    }

    public void delDocTypDL(DialogEvent dialogEvent) {
        Key key = null;
        BindingContainer bindings = getBindings();
        OperationBinding op = bindings.getOperationBinding("delDocType");
        op.execute();
        DCIteratorBinding dcitr = (DCIteratorBinding)bindings.get("OrgDocVO1Iterator");
        if (dcitr.getCurrentRow() != null) {
            key = dcitr.getCurrentRow().getKey();
            /*  System.out.println("keyyyyyyyyyy is== " + key); */
        }
        op = bindings.getOperationBinding("Commit");
        op.execute();
        op = bindings.getOperationBinding("Execute4");
        op.execute();
        if (key != null) {
            dcitr.setCurrentRowWithKey(key.toStringFormat(true));
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(orgDocTypBind);
    }

    public void setDt(java.util.Date dt) {
        this.dt = dt;
    }

    public java.util.Date getDt() {
        return dt;
    }

    public void setT(RichInputText t) {
        this.t = t;
    }

    public RichInputText getT() {
        return t;
    }

    public void setTransAddBind(RichInputText transAddBind) {
        this.transAddBind = transAddBind;
    }

    public RichInputText getTransAddBind() {

        return transAddBind;
    }

    public void setTransAdd2Bind(RichInputText transAdd2Bind) {
        this.transAdd2Bind = transAdd2Bind;
    }

    public RichInputText getTransAdd2Bind() {

        return transAdd2Bind;
    }


    public void chkDefaultValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println("value of the object is===" + object);
        String docreset = null;
        ViewObjectImpl vo = am.getOrgDocTypResetVO1();
        Row rw[] = vo.getFilteredRows("DocTypResetDef", "Y");
        if (object.toString().equalsIgnoreCase("Y") || object.toString().equalsIgnoreCase("true")) {
            if (rw.length > 1) {
                defmode = "N";
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "There should be only one Default reset frequency ",
                                                              null));
            }
        }
    }

    public void setChkcount(Integer chkcount) {
        this.chkcount = chkcount;
    }

    public Integer getChkcount() {
        return chkcount;
    }

    public void setEditBtnBind(RichCommandImageLink editBtnBind) {
        this.editBtnBind = editBtnBind;
    }

    public RichCommandImageLink getEditBtnBind() {
        return editBtnBind;
    }

    public void setOrgFyEditBind(RichCommandImageLink orgFyEditBind) {
        this.orgFyEditBind = orgFyEditBind;
    }

    public RichCommandImageLink getOrgFyEditBind() {
        return orgFyEditBind;
    }

    public void setOthdetailEditBind(RichCommandImageLink othdetailEditBind) {
        this.othdetailEditBind = othdetailEditBind;
    }

    public RichCommandImageLink getOthdetailEditBind() {
        return othdetailEditBind;
    }

    public void setUserAddBind(RichCommandImageLink userAddBind) {
        this.userAddBind = userAddBind;
    }

    public RichCommandImageLink getUserAddBind() {
        return userAddBind;
    }

    public void orgAliasValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String alias = object.toString();
        if (object != null) {
            ViewObjectImpl vo = am.getOrgVO1();
            Row[] rw = vo.getFilteredRows("OrgAlias", alias);
            vo.executeQuery();
            System.out.println("total no of filtered rows in orgalias validator is===" + rw.length);
            if (rw.length > 1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "duplicate Alias found !",
                                                              null));
            }
        }
    }

    public void orgAliasValidator1(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String alias = object.toString();
        if (object != null) {
            ViewObjectImpl vo = am.getOrgVO1();
            ViewObjectImpl orgvo = am.getOrg2();
            Row[] rw = vo.getFilteredRows("OrgAlias", alias);
            RowSetIterator itr = vo.createRowSetIterator(null);
            while (itr.hasNext()) {
                Row row = itr.next();
                String orgalias = row.getAttribute("OrgAlias").toString();
                if (orgalias.equals(alias)) {
                    if (rw[0].getAttribute("OrgId").toString().equals(row.getAttribute("OrgId").toString())) {
                    } else {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      "duplicate alias found !", null));
                    }
                }

            }
        }
    }

    public void orgnameValidator1(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String org = object.toString();
        System.out.println("value of org objectttt==" + org);
        ViewObjectImpl vo = am.getOrgVO1();
        Row[] rw = vo.getFilteredRows("OrgDesc", org);
        RowSetIterator itr = vo.createRowSetIterator(null);
        while (itr.hasNext()) {
            Row rw1 = itr.next();
            String orgname = rw1.getAttribute("OrgDesc").toString();
            if (orgname.equals(org)) {
                if (rw[0].getAttribute("OrgId").toString().equals(rw1.getAttribute("OrgId").toString())) {
                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "duplicate organisation name found !", null));
                }
            }
        }
    }

    public void setFydate(String fydate) {
        this.fydate = fydate;
    }

    public String getFydate() {
        return fydate;
    }

    public void setFyTabBind(RichShowDetailItem fyTabBind) {
        this.fyTabBind = fyTabBind;
    }

    public RichShowDetailItem getFyTabBind() {
        return fyTabBind;
    }

    public void setInfoTabBind(RichShowDetailItem infoTabBind) {
        this.infoTabBind = infoTabBind;
    }

    public RichShowDetailItem getInfoTabBind() {
        return infoTabBind;
    }

    public void setDocTabBind(RichShowDetailItem docTabBind) {
        this.docTabBind = docTabBind;
    }

    public RichShowDetailItem getDocTabBind() {
        return docTabBind;
    }

    public void setPanelTabBind(RichPanelTabbed panelTabBind) {
        this.panelTabBind = panelTabBind;
    }

    public RichPanelTabbed getPanelTabBind() {
        return panelTabBind;
    }


    public void setDefmode(String defmode) {
        this.defmode = defmode;
    }

    public String getDefmode() {
        return defmode;
    }

    public void setDefResetBind(RichSelectBooleanCheckbox defResetBind) {
        this.defResetBind = defResetBind;
    }

    public RichSelectBooleanCheckbox getDefResetBind() {
        return defResetBind;
    }

    public void popOkBtnAl(ActionEvent actionEvent) {
         System.out.println("----->"+docNmPopupBind.getValue());
        System.out.println("--------->"+docTypNmPopupBind.getValue());
        System.out.println("------------>"+docResetopupBind.getValue());
       /* if(docNmPopupBind.getValue()==null||docTypNmPopupBind.getValue()==null||docResetopupBind.getValue()==null) {
            FacesMessage message =
                new FacesMessage("please enter document type and reset frequency !");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } */
        BindingContainer bindings = getBindings();
        OperationBinding op = bindings.getOperationBinding("Commit");
        op.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(orgDocFyBind);
        popupforOrgDocFy.hide();
    }

    public void popCancelBtnAL(ActionEvent actionEvent) {
         Key orgky = null;
        Key orgfykey = null;
        Key key = null;
        BindingContainer bindings = getBindings();
        DCIteratorBinding dcitr1 = (DCIteratorBinding)bindings.get("LovOrgType1Iterator");
        DCIteratorBinding dcitr = (DCIteratorBinding)bindings.get("Org2Iterator");
        DCIteratorBinding orgfydcitr = (DCIteratorBinding)bindings.get("OrgFy2Iterator");
        if (dcitr1.getCurrentRow() != null) {
            key = dcitr1.getCurrentRow().getKey();
            if (dcitr.getCurrentRow() != null) {
                orgky = dcitr.getCurrentRow().getKey();
                if (orgfydcitr.getCurrentRow() != null) {
                    orgfykey = orgfydcitr.getCurrentRow().getKey();
                }
            }
        }
        OperationBinding op = bindings.getOperationBinding("Rollback");
        op.execute();
        op = bindings.getOperationBinding("Execute6");
        op.execute();
        if (key != null) {
            dcitr1.setCurrentRowWithKey(key.toStringFormat(true));
            if (orgky != null) {
                dcitr.setCurrentRowWithKey(orgky.toStringFormat(true));
                if (orgfykey != null)
                    orgfydcitr.setCurrentRowWithKey(orgfykey.toStringFormat(true));
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(orgDocFyBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(editBtnBind);
        popupforOrgDocFy.hide();
    }

    public void setOrgPrefixBind(RichInputText orgPrefixBind) {
        this.orgPrefixBind = orgPrefixBind;
    }

    public RichInputText getOrgPrefixBind() {
        return orgPrefixBind;
    }

    public void setMaxLengthBind(RichInputText maxLengthBind) {
        this.maxLengthBind = maxLengthBind;
    }

    public RichInputText getMaxLengthBind() {
        return maxLengthBind;
    }

    public void setPrefixBind(RichSelectBooleanCheckbox prefixBind) {
        this.prefixBind = prefixBind;
    }

    public RichSelectBooleanCheckbox getPrefixBind() {
        return prefixBind;
    }

    public void setEditAlowOp(String editAlowOp) {
        this.editAlowOp = editAlowOp;
    }

    public String getEditAlowOp() {
        return editAlowOp;
    }
    public void clearfilter() {
        FilterableQueryDescriptor qd =(FilterableQueryDescriptor)orgDocBind.getFilterModel();
              if (qd != null && qd.getFilterCriteria() != null) {
                qd.getFilterCriteria().clear();
                orgDocBind.queueEvent(new QueryEvent(orgDocBind, qd));           

             }
    }

    public void setCount1(Integer count1) {
        this.count1 = count1;
    }

    public Integer getCount1() {
        return count1;
    }
}

