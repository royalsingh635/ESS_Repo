package externalentitysetup.view.bean;

import appEoAddress.model.service.AppEoAddressAMImpl;

import externalentitysetup.model.module.ExternalEntityAMImpl;

import externalentitysetup.model.views.AppEoMstVOImpl;
import externalentitysetup.model.views.AppEoVOImpl;
import externalentitysetup.model.views.LOVEoNmImpl;

import java.sql.CallableStatement;
import java.sql.SQLException;

import java.sql.Types;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.el.ELContext;
import javax.el.Expression;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichDialog;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.data.RichTreeTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelTabbed;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.adf.view.rich.event.RegionNavigationEvent;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Key;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.Session;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Array;
import oracle.jbo.domain.Date;
import oracle.jbo.rules.JboPrecisionScaleValidator;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;

import oracle.jbo.server.util.SQLExceptionConverter;

import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import oracle.jbo.uicli.binding.JUCtrlValueBindingRef;

import org.apache.myfaces.trinidad.component.UIXTree;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class ExternamEntityBean {
    private RichPopup eoMstPopup;
    String mstMode = "A";
    private RichTreeTable treeTableEnt;
    Key conskey = null;
    private RichPopup wantTodeletepopup;
    private RichPopup eoPopup;
    private RichPanelFormLayout eoForm1Bind;
    private RichPanelFormLayout eoFormBind2;
    private RichPopup eoEditPopup;
    private static int VARCHAR = Types.VARCHAR;
    private static int NUMBER = Types.INTEGER;
    private RichInputText searchText;
    private boolean form_disable_Bank = true;
    private RichInputText inActiveReasonBind;
    private RichInputDate inActiveDateBind;
    private boolean form_disable_pDtl = true;
    private boolean form_disable_prf = true;
    private RichInputText blRsnBind;
    private RichInputDate blFrmDtBind;
    private RichInputDate blToDtBind;
    private RichInputText ohRsnBind;
    private RichInputDate ohFrmDtBind;
    private RichInputDate ohToDtBind;
    //   private RichSelectOneChoice currOnAddBind;
    // private RichSelectOneChoice currOnEditBind;
    private RichPanelFormLayout eoMstFormBind;
    private RichSelectOneChoice eotypeBind;
    private boolean specificCurrEditFieldVisibleRequired = false;
    private boolean blacklistVis = true;
    private boolean onholdVis = true;
    private RichSelectBooleanCheckbox onholdchkbxbind;
    private RichSelectBooleanCheckbox blklistchkbxbind;
    private RichSelectBooleanCheckbox bnkinactiveChkBxBind;
    private Integer na_ID = 0;
    String LeagacyCode = "";
    private RichInputText eoLgcyCodePopBind;
    private RichPopup warningPopBind;
    private String wrningmsg = "";
    private RichPopup subEoWarnPopBind;
    private RichPopup editSubWrnPopUpBind;
    ExternalEntityAMImpl am = (ExternalEntityAMImpl)resolvElDC("ExternalEntityAMDataControl");
    private RichSelectOneChoice eoBehavAddFormBind;
    private RichSelectOneChoice eoBehvEditPopBind;

    private RichInputText bnk_nm_form_Bind;
    private RichInputText bnk_acc_no_form_Bind;
    private RichInputText eo_per_nm_form_bind;
    private RichInputText eo_per_desg_form_bind;
    private Integer on_load = (Integer)getBindings().getOperationBinding("on_load_page").execute();
    private RichSelectOneChoice catgIdBindVar;
    private RichInputText mstNameBinding;
    private RichShowDetailItem addTab;
    private RichPopup popupDeleteBinding;
    private RichTable eoPerTableBinding;
    private RichTable bankTableBinding;
    private RichPanelFormLayout bankFormBinding;
    private RichPanelFormLayout persComFormBinding;
    private RichInputText eoNameBinding;
    private RichInputListOfValues searchGroupBinding;
    private RichCommandImageLink editBankBinding;
    private RichCommandImageLink editPersBinding;
    private Integer EntityId = -1;
    private Map<String, Object> parameterMap = new HashMap<String, Object>();
    private RichInputListOfValues currIdTransBinding;
    private RichInputListOfValues currIdOnEditTransBinding;
    private RichCommandButton naBinding;
    private RichCommandButton coaBinding;
    private RichCommandButton viewBinding;
    private RichCommandImageLink delButtonBinding;
    private RichCommandImageLink deleteButtonBinding;
    private RichPanelFormLayout formBinding;
    private RichPanelFormLayout searchFormBind;
    
    // Theses two variable is used to Handle disability of component during add address.
    private String modeFromAddres=null; 
    private Integer render=1;
    
    AppEoAddressAMImpl addAM = (AppEoAddressAMImpl) resolvElDC("AppEoAddressAMDataControl");
    private RichPanelGroupLayout entityForm;
    private RichPanelGroupLayout entitySubtypeForm;
    private RichPanelTabbed pannelTab;
    private RichSelectOneChoice eoTypeEdit;
    private RichCommandImageLink editPrf;
    private RichShowDetailItem editDtl;
    private RichCommandImageLink savePrf;
    private RichShowDetailItem saveBank;
    private RichCommandImageLink saveDtl;
    private RichShowDetailItem prfTab;
    private RichCommandImageLink saveBankLink;
    private RichCommandImageLink addButton;
    
    private Boolean currDis=false;

    public ExternamEntityBean() {

        
    }

    public void setEoMstPopup(RichPopup eoMstPopup) {
        this.eoMstPopup = eoMstPopup;
    }

    public RichPopup getEoMstPopup() {
        return eoMstPopup;
    }

    public void eoMstDialogList(DialogEvent dialogEvent) {
        String is_unique = "Y";

        if (eoLgcyCodePopBind.getValue() != null) {

            am = (ExternalEntityAMImpl)resolvElDC("ExternalEntityAMDataControl");
            ViewObjectImpl appEoMst = am.getAppEoMst1();
            String cldid = resolvEl("#{pageFlowScope. GLBL_APP_CLD_ID}");
            Integer slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}".toString()));
            String HoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
            String error = resolvElDCMsg("#{bundle['MSG.197']}").toString();

            if (mstMode.equalsIgnoreCase("A")) {
                //    System.out.println("when mstMode=A -->" +
                 //                  eoLgcyCodePopBind.getValue().toString().trim().toUpperCase());
                is_unique =
                        (String)callStoredFunction(VARCHAR, "fn_is_eo_grp_legcode_unique(?,?,?,?,?)", new Object[] { cldid,
                                                                                                                     slocid,
                                                                                                                     HoOrgId,
                                                                                                                     eoLgcyCodePopBind.getValue().toString().trim().toUpperCase(),
                                                                                                                     null });

            } else if (mstMode.equalsIgnoreCase("E")) {
                //    System.out.println("when mstMode=E --> " +
                     //              eoLgcyCodePopBind.getValue().toString().trim().toUpperCase());

                Row currentRow = appEoMst.getCurrentRow();
                Integer mstId = (Integer)currentRow.getAttribute("EoMstId");
                is_unique =
                        (String)callStoredFunction(VARCHAR, "fn_is_eo_grp_legcode_unique(?,?,?,?,?)", new Object[] { cldid,
                                                                                                                     slocid,
                                                                                                                     HoOrgId,
                                                                                                                     eoLgcyCodePopBind.getValue().toString().trim().toUpperCase(),
                                                                                                                     mstId });
            } else
                System.out.println("error onin executing function");

            //    System.out.println("result from is unique_legCode function = " + is_unique);
            if (is_unique.equalsIgnoreCase("N")) {
                //    System.out.println("when output is N");
                this.showPopup(this.warningPopBind, true);

            }
            /*  int i = appEoMst.getRangeSize();
        appEoMst.setRangeSize(-1);
        Row[] allRowsInRange = appEoMst.getAllRowsInRange();
        Row currentRow = appEoMst.getCurrentRow();
        for (Row r : allRowsInRange) {
            if (r != currentRow && (r.getAttribute("EoMstLegCd") != null) &&
                (currentRow.getAttribute("EoMstLegCd") != null)) {


                if ((currentRow.getAttribute("EoMstLegCd").equals(r.getAttribute("EoMstLegCd")))) {
                    this.showPopup(this.warningPopBind, true);
                    count = 1;


                    break;
                }

            }
        }
        appEoMst.setRangeSize(i); */
        }

        if (dialogEvent.getOutcome().name().equals("ok") && is_unique.equalsIgnoreCase("Y")) {

            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();

            if (operationBinding.getErrors().isEmpty()) {
                if (mstMode.equals("A")) {

                    FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.184']}").toString());
                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                } else if (mstMode.equals("E")) {

                    FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.185']}").toString());
                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }
                am.getLOVEoNm1().executeQuery();
            } else {
                    System.out.println("error on commit");
            }

        }
    }

    public void warningLcDialogList(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();
            //  showPopup(this.eoMstPopup, false);
            eoMstPopup.hide();
            if (operationBinding.getErrors().isEmpty()) {
                if (mstMode.equals("A")) {


                    FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.184']}").toString());
                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                } else if (mstMode.equals("E")) {

                    FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.185']}").toString());
                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }

            } else {

            }
        }
    }

    public void wrningPopupCancelList(PopupCanceledEvent popupCanceledEvent) {
        showPopup(eoMstPopup, true);
    }

    public void eoMstPopupCancelList(PopupCanceledEvent popupCanceledEvent) {
        if (getMstMode().equalsIgnoreCase("A")) {

            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            operationBinding.execute();

            OperationBinding operationBindings = bindings.getOperationBinding("Execute");
            operationBindings.execute();
            DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("AppEoMst1Iterator");
            try {
                if (conskey != null) {
                    parentIter.setCurrentRowWithKey(conskey.toStringFormat(true));
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(treeTableEnt);

            } catch (Exception e) {

            }
        }
        if (getMstMode().equalsIgnoreCase("E")) {

            BindingContainer bindings = getBindings();


            DCIteratorBinding parentIter = null;
            Key parentKey = null;
            try {
                parentIter = (DCIteratorBinding)bindings.get("AppEoMst1Iterator");
                parentKey = parentIter.getCurrentRow().getKey();
            } catch (Exception e) {

            }


            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            operationBinding.execute();

            OperationBinding operationBindings = bindings.getOperationBinding("Execute");
            operationBindings.execute();

            try {
                parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
            } catch (Exception e) {

            }

        }
    }

    public void addEoMstButton(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        setMstMode("A");
        try {
            DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("AppEoMst1Iterator");
            conskey = parentIter.getCurrentRow().getKey();
        } catch (Exception e) {
            System.out.println("error onin addEoMstButton = " + e);
        }

        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();


        showPopup(eoMstPopup, true);
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

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void editEoMstButton(ActionEvent actionEvent) {
        setMstMode("E");
        showPopup(eoMstPopup, true);
    }

    public void setMstMode(String mstMode) {
        this.mstMode = mstMode;
    }

    public String getMstMode() {
        return mstMode;
    }

    public void setTreeTableEnt(RichTreeTable treeTableEnt) {
        this.treeTableEnt = treeTableEnt;
    }

    public RichTreeTable getTreeTableEnt() {
        return treeTableEnt;
    }

    public void setConskey(Key conskey) {
        this.conskey = conskey;
    }

    public Key getConskey() {
        return conskey;
    }

    public void deleteEoMst(ActionEvent actionEvent) {

        am = (ExternalEntityAMImpl)resolvElDC("ExternalEntityAMDataControl");
        ViewObjectImpl eo = am.getAppEo2();

        int i = eo.getRowCount();
        //
        if (i > 0) {
            String Rmsg = resolvElDCMsg("#{bundle['MSG.10']}").toString();
            FacesMessage message = new FacesMessage(Rmsg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            //  throw new ValidatorException(message);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);

        } else {
            showPopup(wantTodeletepopup, true);

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

    public void setWantTodeletepopup(RichPopup wantTodeletepopup) {
        this.wantTodeletepopup = wantTodeletepopup;
    }

    public RichPopup getWantTodeletepopup() {
        return wantTodeletepopup;
    }

    public void wantToDeleteDialogList(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            BindingContainer bindings = getBindings();

            OperationBinding operationBinding = bindings.getOperationBinding("Delete");
            operationBinding.execute();

            /*  OperationBinding operationBind = bindings.getOperationBinding("Execute");
            operationBind.execute(); */

            OperationBinding operationBindings = bindings.getOperationBinding("Commit");
            operationBindings.execute();

            /*
            OperationBinding operationBindingss = bindings.getOperationBinding("Rollback");
            operationBindingss.execute(); */

            AdfFacesContext.getCurrentInstance().addPartialTarget(treeTableEnt);
            AdfFacesContext.getCurrentInstance().addPartialTarget(eoMstFormBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(delButtonBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(deleteButtonBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(formBinding);

            //this.currOnAddBind.setVisible(false);formBinding
            am = (ExternalEntityAMImpl)resolvElDC("ExternalEntityAMDataControl");
            am.getLOVEoNm1().executeQuery();

        }
    }

    private String mode = "S";

    public void addEoButton(ActionEvent actionEvent) {
        mode = "A";
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert1");
        operationBinding.execute();

        System.out.println("error onin createw=" + operationBinding.getErrors());

        am = (ExternalEntityAMImpl)resolvElDC("ExternalEntityAMDataControl");
        ViewObjectImpl eoMst = am.getAppEoMst1();
        Row row = eoMst.getCurrentRow();
        Integer sloc_id = Integer.parseInt(row.getAttribute("EoMstSlocId").toString());
        String ho_org_id = row.getAttribute("EoHoOrgId").toString();
        String cld_id = row.getAttribute("EoCldId").toString();
        Integer eo_mst_id = Integer.parseInt(row.getAttribute("EoMstId").toString());
        ////    System.out.println("eo mst id =  "+eo_mst_id);
        ViewObjectImpl eoForAdd = am.getAppEo2();
        Row currRow = eoForAdd.getCurrentRow();
        //    System.out.println("Curr row=" + currRow);
        currRow.setAttribute("EoSlocId", sloc_id);
        currRow.setAttribute("EoMstId", eo_mst_id);
        currRow.setAttribute("EoHoOrgId", ho_org_id);
        currRow.setAttribute("EoCldId", cld_id);


        showPopup(eoPopup, true);
    }

    public void editEoButton(ActionEvent actionEvent) {
        mode = "E";
        this.specificCurrEditFieldVisibleRequired = false;

        am = (ExternalEntityAMImpl)resolvElDC("ExternalEntityAMDataControl");
        ViewObjectImpl eo = am.getAppEo2();
        Row row = eo.getCurrentRow();
        Integer eo_type = Integer.parseInt(row.getAttribute("EoType").toString());
        this.eoTypeEdit.setDisabled(true);
        showPopup(eoEditPopup, true);
        if (eo_type == 78 || eo_type == 79) {
            //this.currOnEditBind.setVisible(true);
            this.specificCurrEditFieldVisibleRequired = true;
        }
    }

    public void deleteEoButton(ActionEvent actionEvent) {
        ////    System.out.println("----------------delete btn clicked :-----------------");
        am = (ExternalEntityAMImpl)resolvElDC("ExternalEntityAMDataControl");
        ViewObjectImpl eo = am.getAppEo2();
        Integer eo_id = (Integer)eo.getCurrentRow().getAttribute("EoId");
        Integer EoSlocId = (Integer)eo.getCurrentRow().getAttribute("EoSlocId");
        String EoHoOrgId = eo.getCurrentRow().getAttribute("EoHoOrgId").toString();
        String EoCldId = eo.getCurrentRow().getAttribute("EoCldId").toString();
        if(eo_id==null){
            Object object = eoNameBinding.getValue();
            if(object!= null){
                String eo_nm=object.toString();
                //    System.out.println("eo_nm = "+eo_nm);
                eoEditCancelList(null);
                Row[] filteredRows = eo.getFilteredRows("EoNm", eo_nm);
                //    System.out.println("filteredRows.length = "+filteredRows.length);
                if(filteredRows.length>0){
                    //    System.out.println("");
                    Object attribute = filteredRows[0].getAttribute("EoId");
                    if(attribute!= null){
                        //    System.out.println("attribute = "+attribute);
                        eo_id = (Integer)attribute;
                    }
                }
            }
        }
        //    System.out.println("eo_id = "+eo_id+" EoSlocId = "+EoSlocId+" EoHoOrgId = "+EoHoOrgId+" EoCldId = "+EoCldId);
        
        String s ="E";
        //    System.out.println("eo_id = "+eo_id);

        try {
            s =
  callStoredFunction(VARCHAR, "fn_is_deletable_eo(?,?,?,?)", new Object[] { EoCldId, EoSlocId, EoHoOrgId, eo_id }).toString();
        } catch (Exception e) {
            e.printStackTrace();
                  eo.executeQuery();  
           
        } finally {
            //    System.out.println("fn_ is_deletable_eo(?,?,?,? )= "+s);
            if(s.equalsIgnoreCase("E")){
                    FacesMessage message = new FacesMessage("Error in Deleting Record! Please try again later");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    //  throw new ValidatorException(message);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }
           else if (s.equalsIgnoreCase("N")) {
                String Rmsg = resolvElDCMsg("#{bundle['MSG.186']}").toString();
                FacesMessage message = new FacesMessage(Rmsg);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                //  throw new ValidatorException(message);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);

            } else {

                BindingContainer bindings = getBindings();

                OperationBinding operationBinding = bindings.getOperationBinding("Delete1");
                operationBinding.execute();

                OperationBinding operationBindings = bindings.getOperationBinding("Commit");
                operationBindings.execute();

                OperationBinding operationBindingss = bindings.getOperationBinding("Execute2");
                operationBindingss.execute();
                String Rmsg = resolvElDCMsg("#{bundle['MSG.187']}").toString();
                FacesMessage message = new FacesMessage(Rmsg);
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                //  throw new ValidatorException(message);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);

            }

        }
        
    }

    public void setEoPopup(RichPopup eoPopup) {
        this.eoPopup = eoPopup;
    }

    public RichPopup getEoPopup() {
        return eoPopup;
    }

    public void eoDialogList(DialogEvent dialogEvent) {
        //    System.out.println("--------------ok btn clicked---------------");
        int count = 0;

        am = (ExternalEntityAMImpl)resolvElDC("ExternalEntityAMDataControl");
        //   ViewObjectImpl appEo2 = am.getEoVoForAdd1();
        AppEoVOImpl appEo2 = am.getAppEo2();

        Integer eo_type = (Integer)appEo2.getCurrentRow().getAttribute("EoType");
        String EoBhav = (String)appEo2.getCurrentRow().getAttribute("EoBhav");
        Integer CurrIdSp = (Integer)appEo2.getCurrentRow().getAttribute("CurrIdSp");
        Integer CatgId = (Integer)appEo2.getCurrentRow().getAttribute("CatgId");



        int i = appEo2.getRangeSize();

        appEo2.setRangeSize(-1);

        ViewObjectImpl appEo1 = am.getAppEo2();

        int j = appEo1.getRangeSize();

        appEo1.setRangeSize(-1);

        Row[] allRowsInRange = appEo1.getAllRowsInRange();
        Row currentRow = appEo2.getCurrentRow();
        for (Row r : allRowsInRange) {

            if ((currentRow.getAttribute("EoNm") != r.getAttribute("EoNm")) && (r.getAttribute("EoLegCode") != null) &&
                (currentRow.getAttribute("EoLegCode") != null)) {

                if ((currentRow.getAttribute("EoLegCode").equals(r.getAttribute("EoLegCode")))) {
                    this.showPopup(this.subEoWarnPopBind, true);
                    count = 1;
                    break;
                }
            }
        }
        appEo2.setRangeSize(i);
        appEo1.setRangeSize(j);
        //    System.out.println("BEFORE OK CONDITION ------------- value of COUNT = " + count);

        if (EoBhav == null) {
            //    System.out.println("Please select Behaviour its mandatory");
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['LBL.2371']}").toString());
            //String msg = "Please select Behaviour its mandatory";
            //FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } else {
            //    System.out.println("behav is not null");
            if (eo_type == 78 || eo_type == 79) {
                //    System.out.println("eo type=Bank acc or cash acc");
                if (CurrIdSp == null) {
                    //    System.out.println("Please select Currency its mandatory");
                    FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['LBL.2372']}").toString());
                    //String msg = "Please select Currency its mandatory";
                    //FacesMessage message = new FacesMessage(msg);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                } else {
                    if (dialogEvent.getOutcome().name().equals("ok") && (count == 0)) {
                        //    System.out.println("before commit BC");
                        BindingContainer bindings = getBindings();
                        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                        operationBinding.execute();

                      
                        //    System.out.println("after COMMIT BC .....>");
                        eoPopup.hide();
                        // showPopup(this.eoPopup, false);
                        if (operationBinding.getErrors().isEmpty()) {
 
                            
                            this.mode="S";
                             AdfFacesContext.getCurrentInstance().addPartialTarget(this.addButton);
                            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.185']}").toString());
                            message.setSeverity(FacesMessage.SEVERITY_INFO);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        } else
                                System.out.println("error on commit=" + operationBinding.getErrors());
                        // am = (ExternalEntityAMImpl)resolvElDC("ExternalEntityAMDataControl");
                        ViewObjectImpl eoForAdd = am.getAppEo2();

                        Key key = eoForAdd.getCurrentRow().getKey();

                        OperationBinding operationBindings = bindings.getOperationBinding("Execute2");
                        operationBindings.execute();


                        DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("AppEo2Iterator");
                        try {
                            if (key != null) {
                                parentIter.setCurrentRowWithKey(key.toStringFormat(true));
                            }
                            RowKeySet selectedRowKeys = new RowKeySetImpl();
                            selectedRowKeys.add(key);
                            treeTableEnt.setSelectedRowKeys(selectedRowKeys);

                        } catch (Exception e) {

                        }
                        AdfFacesContext.getCurrentInstance().addPartialTarget(treeTableEnt);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(eoForm1Bind);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(eoFormBind2);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(naBinding);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(coaBinding);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(viewBinding);


                    }
                }

            } else if (eo_type == 68) {
                //    System.out.println("customer");
                if (CatgId == null) {
                    //    System.out.println("Please select Catagory its mandatory");
                    FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['LBL.2376']}").toString());
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                } else {
                    if (dialogEvent.getOutcome().name().equals("ok") && (count == 0)) {
                        //    System.out.println("before commit");
                        BindingContainer bindings = getBindings();
                        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                        operationBinding.execute();
                        //    System.out.println("after COMMIT .....>");
                        //  showPopup(this.eoPopup, false);
                        this.eoPopup.hide();
                        if (operationBinding.getErrors().isEmpty()) {
                            
                            this.mode="S";
                             AdfFacesContext.getCurrentInstance().addPartialTarget(this.addButton);
                            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.185']}").toString());
                            message.setSeverity(FacesMessage.SEVERITY_INFO);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        } else
                            System.out.println("error onon commit=" + operationBinding.getErrors());

                        am = (ExternalEntityAMImpl)resolvElDC("ExternalEntityAMDataControl");
                        ViewObjectImpl eoForAdd = am.getAppEo2();

                        Key key = eoForAdd.getCurrentRow().getKey();

                        OperationBinding operationBindings = bindings.getOperationBinding("Execute2");
                        operationBindings.execute();


                        DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("AppEo2Iterator");
                        try {
                            if (key != null) {
                                parentIter.setCurrentRowWithKey(key.toStringFormat(true));
                            }
                            RowKeySet selectedRowKeys = new RowKeySetImpl();
                            ArrayList arr = new ArrayList();
                            selectedRowKeys.add(key);
                            treeTableEnt.setSelectedRowKeys(selectedRowKeys);

                        } catch (Exception e) {

                        }
                        AdfFacesContext.getCurrentInstance().addPartialTarget(treeTableEnt);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(eoForm1Bind);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(eoFormBind2);


                    }
                }
            } else {
                //    System.out.println("Other than bank cash customer");
                if (dialogEvent.getOutcome().name().equals("ok") && (count == 0)) {
                    //    System.out.println("before commit");
                    BindingContainer bindings = getBindings();
                    OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                    operationBinding.execute();
                    //    System.out.println("after COMMIT .....>");
                    
                    eoPopup.hide();
                    // showPopup(this.eoPopup, false);
                    if (operationBinding.getErrors().isEmpty()) {
                        
                        
                        this.mode="S";
                         AdfFacesContext.getCurrentInstance().addPartialTarget(this.addButton);
                       
                        FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.185']}").toString());
                        message.setSeverity(FacesMessage.SEVERITY_INFO);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    } else
                        System.out.println("error onon commit=" + operationBinding.getErrors());

                    am = (ExternalEntityAMImpl)resolvElDC("ExternalEntityAMDataControl");
                    ViewObjectImpl eoForAdd = am.getAppEo2();

                    Key key = eoForAdd.getCurrentRow().getKey();


                    OperationBinding operationBindingss = bindings.getOperationBinding("Execute2");
                    operationBindingss.execute();


                    DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("AppEo2Iterator");
                    try {
                        if (key != null) {
                            parentIter.setCurrentRowWithKey(key.toStringFormat(true));
                        }
                        RowKeySet selectedRowKeys = new RowKeySetImpl();
                        selectedRowKeys.add(key);
                        treeTableEnt.setSelectedRowKeys(selectedRowKeys);

                    } catch (Exception e) {

                    }
                    AdfFacesContext.getCurrentInstance().addPartialTarget(treeTableEnt);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(eoForm1Bind);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(eoFormBind2);
                    
                    //  eoPopup.addPartialTarget(FacesContext.getCurrentInstance(), null, treeTableEnt);

                }
            }

        }
        
        
       
    }

    public void sbEoAddPopCancelList(PopupCanceledEvent popupCanceledEvent) {
        showPopup(eoPopup, true);
    }

    public void eoCancelList(PopupCanceledEvent popupCanceledEvent) {
        am = (ExternalEntityAMImpl)resolvElDC("ExternalEntityAMDataControl");
        // am.getDBTransaction().cancelDMLOperations();
        BindingContainer bindings = getBindings();


        DCIteratorBinding parentIter = null;
        DCIteratorBinding Iter = null;
        Key parentKey = null;
        try {
            parentIter = (DCIteratorBinding)bindings.get("AppEoMst1Iterator");
            Iter = (DCIteratorBinding)bindings.get("AppEo2Iterator");
            parentKey = parentIter.getCurrentRow().getKey();

        } catch (Exception e) {
            System.out.println("error onin eoCancelList=" + e);
        }
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        System.out.println("error onafter rollback=" + operationBinding.getErrors().isEmpty());
        operationBinding.execute();
        this.mode="S";
        
        //  am.getDBTransaction().clearEntityCache("AppEoEO");
        //  am.getDBTransaction().closeTransaction();
        //  bindings.getOperationBinding("Commit").execute();


        /*  OperationBinding operationBindings = bindings.getOperationBinding("Execute");
        operationBindings.execute(); */


        try {
            parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
        } catch (Exception e) {
            //    System.out.println("e" + e);
        }


        AdfFacesContext.getCurrentInstance().addPartialTarget(eoForm1Bind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(eoFormBind2);


    }

    public void setEoForm1Bind(RichPanelFormLayout eoForm1Bind) {
        this.eoForm1Bind = eoForm1Bind;
    }

    public RichPanelFormLayout getEoForm1Bind() {
        return eoForm1Bind;
    }

    public void setEoFormBind2(RichPanelFormLayout eoFormBind2) {
        this.eoFormBind2 = eoFormBind2;
    }

    public RichPanelFormLayout getEoFormBind2() {
        return eoFormBind2;
    }

    public void setEoEditPopup(RichPopup eoEditPopup) {
        this.eoEditPopup = eoEditPopup;
    }

    public RichPopup getEoEditPopup() {
        return eoEditPopup;
    }

    public void editsubEoDialogList(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();
            eoEditPopup.hide();
            // showPopup(this.eoEditPopup, false);
            if (operationBinding.getErrors().isEmpty()) {
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.185']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }

        }
    }

    public void eoEditCancelList(PopupCanceledEvent popupCanceledEvent) {
        //    System.out.println("Popup cancel");
        BindingContainer bindings = getBindings();

        DCIteratorBinding parentIter = null;
        Key parentKey = null;
        DCIteratorBinding dtlIter = null;
        Key dtlKey = null;
        try {
            parentIter = (DCIteratorBinding)bindings.get("AppEoMst1Iterator");
            parentKey = parentIter.getCurrentRow().getKey();
            dtlIter = (DCIteratorBinding)bindings.get("AppEo2Iterator");
            dtlKey = dtlIter.getCurrentRow().getKey();
        } catch (Exception e) {

        }


        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();

        this.mode="S";   

        OperationBinding operationBindings = bindings.getOperationBinding("Execute2");
        operationBindings.execute();

        try {

            parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
            dtlIter.setCurrentRowWithKey(dtlKey.toStringFormat(true));
        } catch (Exception e) {

        }
    }

    public void eoEditDialogList(DialogEvent dialogEvent) {

        //    System.out.println("Listening popup");
        int count = 0;
        am = (ExternalEntityAMImpl)resolvElDC("ExternalEntityAMDataControl");
        ViewObjectImpl appEo2 = am.getAppEo2();

        int i = appEo2.getRangeSize();
        appEo2.setRangeSize(-1);

        ViewObjectImpl appEo1 = am.getAppEo2();
        int j = appEo1.getRangeSize();
        appEo1.setRangeSize(-1);


        Row[] allRowsInRange = appEo1.getAllRowsInRange();
        Row currentRow = appEo2.getCurrentRow();

        for (Row r : allRowsInRange) {
            if ((currentRow.getAttribute("EoNm") != r.getAttribute("EoNm")) && (r.getAttribute("EoLegCode") != null) &&
                (currentRow.getAttribute("EoLegCode") != null)) {

                if ((currentRow.getAttribute("EoLegCode").equals(r.getAttribute("EoLegCode")))) {
                    this.showPopup(this.editSubWrnPopUpBind, true);
                    count = 1;


                    break;
                }
            }
        }
        appEo2.setRangeSize(i);
        appEo1.setRangeSize(j);

        if (dialogEvent.getOutcome().name().equals("ok") && count == 0) {
            //    System.out.println("Ok pressed");
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();
            if (operationBinding.getErrors().isEmpty()) {
                this.mode="S";
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.185']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else
                    System.out.println("error on commit=" + operationBinding.getErrors());

        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(naBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(coaBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(viewBinding);
    }

    public void editSubentityCanclList(PopupCanceledEvent popupCanceledEvent) {
        showPopup(eoEditPopup, true);
    }

    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {
            am = (ExternalEntityAMImpl)resolvElDC("ExternalEntityAMDataControl");
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
                }
            }
        }
    }

    public void setSearchText(RichInputText searchText) {
        this.searchText = searchText;
    }

    public RichInputText getSearchText() {
        return searchText;
    }

    public void searchButton(ActionEvent actionEvent) {
        String cldid = resolvEl("#{pageFlowScope. GLBL_APP_CLD_ID}");
        Integer slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}".toString()));
        String HoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        try {
            am = (ExternalEntityAMImpl)resolvElDC("ExternalEntityAMDataControl");
            ViewObjectImpl dual = am.getSearchDual1();
            String searchStr = (String)dual.getCurrentRow().getAttribute("EntityGroup");
            AppEoMstVOImpl eoMst = (AppEoMstVOImpl)am.getAppEoMst1();
            // String searchStr = searchGroupBinding.getValue().toString();

            if (searchStr != null && searchStr.length() > 0) {
                searchStr = searchStr.toUpperCase();
                eoMst.setSlocIdBindVar(slocid);
                eoMst.setCldIdBindVar(cldid);
                eoMst.setHoOrgIdBindVar(HoOrgId);
                eoMst.setEoNmBind(searchStr.toUpperCase());
                eoMst.executeQuery();
            }
            /*  eoMst.setWhereClause("upper(eo_mst_nm) like '%" + searchStr + "%'");
                eoMst.executeQuery(); */
            AdfFacesContext.getCurrentInstance().addPartialTarget(treeTableEnt);
        } catch (Exception e) {
            //    System.out.println("e = " + e);
        }

    }

    public void resetButton(ActionEvent actionEvent) {
        String cldid = resolvEl("#{pageFlowScope. GLBL_APP_CLD_ID}");
        Integer slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}".toString()));
        String HoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        am = (ExternalEntityAMImpl)resolvElDC("ExternalEntityAMDataControl");
        AppEoMstVOImpl eoMst = (AppEoMstVOImpl)am.getAppEoMst1();
        eoMst.setSlocIdBindVar(slocid);
        eoMst.setCldIdBindVar(cldid);
        eoMst.setHoOrgIdBindVar(HoOrgId);
        eoMst.setEoNmBind(null);
        eoMst.executeQuery();
        ViewObjectImpl dual = am.getSearchDual1();
        dual.getCurrentRow().setAttribute("EntityGroup", null);
        dual.executeQuery();
        // this.searchGroupBinding.setValue("");
        this.resetValueInputItems(AdfFacesContext.getCurrentInstance(),this.searchFormBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(treeTableEnt);

    }
    
    
    

    private void resetValueInputItems(AdfFacesContext adfFacesContext, UIComponent component) {
        List<UIComponent> items = component.getChildren();
        for (UIComponent item : items) {

            resetValueInputItems(adfFacesContext, item);

            if (item instanceof RichInputText) {
                RichInputText input = (RichInputText)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            } else if (item instanceof RichInputDate) {
                RichInputDate input = (RichInputDate)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            } else if (item instanceof RichSelectOneChoice) {
                RichSelectOneChoice input = (RichSelectOneChoice)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            } else if (item instanceof RichInputListOfValues) {
                RichInputListOfValues input = (RichInputListOfValues)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            }

        }
    }


    public void generateNAButton(ActionEvent actionEvent) {
        ////    System.out.println("---------------- generate NA button clicked : -------------------");
        am = (ExternalEntityAMImpl)resolvElDC("ExternalEntityAMDataControl");
        ViewObjectImpl eo = am.getAppEo2();
        // ViewObjectImpl naVo = am.getFinAccNa1();
        //    System.out.println("after getting fin acnt na vo");
        Integer eo_id = (Integer)eo.getCurrentRow().getAttribute("EoId");
        Integer EoSlocId = (Integer)eo.getCurrentRow().getAttribute("EoSlocId");
        String EoHoOrgId = eo.getCurrentRow().getAttribute("EoHoOrgId").toString();
        String EoCldId = eo.getCurrentRow().getAttribute("EoCldId").toString();


        String s =
            callStoredFunction(VARCHAR, "app.fn_is_deletable_eo(?,?,?,?)", new Object[] { EoCldId, EoSlocId, EoHoOrgId,
                                                                                          eo_id }).toString();
       
        if (s.equalsIgnoreCase("N")) {
            String Rmsg = resolvElDCMsg("#{bundle['MSG.189']}").toString();
            FacesMessage message = new FacesMessage(Rmsg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            //  throw new ValidatorException(message);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);

        } else {

            Row row = eo.getCurrentRow();
            Integer sloc = Integer.parseInt(row.getAttribute("EoSlocId").toString());
            String eonm = row.getAttribute("EoNm").toString();
            Integer eoType = Integer.parseInt(row.getAttribute("EoType").toString());
            Integer eoId = (Integer)row.getAttribute("EoId");
            Integer eotypeId = Integer.parseInt(row.getAttribute("EoId").toString());
            Integer createId = Integer.parseInt(row.getAttribute("UsrIdCreate").toString());
            String horg_id = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
            String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
            /*
            RowQualifier rqna = new RowQualifier(naVo);
            //    System.out.println("after row qualifier");
            rqna.setWhereClause("AccCldId='" + cld_id + "' and HoOrgId='" + horg_id + "' and AccSlocId=" + sloc +
                                " and AccNm='" + eonm + "'");
            //    System.out.println("after set where clause");

            Row[] r = naVo.getFilteredRows(rqna);
            //    System.out.println("after getfilter row"); */
            if (eoNameBinding.getValue() != null) {
                String eoName = eoNameBinding.getValue().toString().trim().toUpperCase();
                String result =
                    (String)callStoredFunction(VARCHAR, "fin.fn_is_na_nm_unique(?,?,?,?,?)", new Object[] { cld_id,
                                                                                                            sloc,
                                                                                                            horg_id,
                                                                                                            eoName,
                                                                                                            null });
                //    System.out.println("is na unique = " + result);
                if (result.equalsIgnoreCase("N")) {
                    //Duplicate NA Name
                    String duplimsg = "Duplicate NA name [" + eonm + "] Exist. Please Change Entity Name.";
                    FacesMessage message = new FacesMessage(duplimsg);
                    message.setSeverity(FacesMessage.SEVERITY_WARN);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                } else {

                    try {
                        Integer tmp_na_id =
                            (Integer)callStoredFunction(Types.INTEGER, "app.fn_ins_eo_to_na(?,?,?,?,?,?,?,?)",
                                                        new Object[] { cld_id, sloc, horg_id, eonm, eoId, eoType,
                                                                       createId, null });
                        if (tmp_na_id > 0) {
                            BindingContainer bindings = getBindings();
                            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                            operationBinding.execute();
                            ////    System.out.println("successfully created NA");
                            String Rmsg = resolvElDCMsg("#{bundle['MSG.396']}").toString();
                            FacesMessage message = new FacesMessage(Rmsg);
                            message.setSeverity(FacesMessage.SEVERITY_INFO);
                            //  throw new ValidatorException(message);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        } else {
                            //String Rmsg = resolvElDCMsg("#{bundle['MSG.396']}").toString();
                            String msg = "Error in Creating Natural Account 2440";
                            ////    System.out.println(msg);
                            String Rmsg = resolvElDCMsg("#{bundle['LBL.2440']}").toString();
                            FacesMessage message = new FacesMessage(Rmsg);
                            message.setSeverity(FacesMessage.SEVERITY_INFO);
                            //  throw new ValidatorException(message);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        }

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            }

        }
        //  naVo.executeQuery();
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

    public String createCoaButton() {
        am = (ExternalEntityAMImpl)resolvElDC("ExternalEntityAMDataControl");
        ViewObjectImpl eo = am.getAppEo2();
        Integer eo_id = (Integer)eo.getCurrentRow().getAttribute("EoId");
        Integer EoSlocId = (Integer)eo.getCurrentRow().getAttribute("EoSlocId");
        Integer EoType = (Integer)eo.getCurrentRow().getAttribute("EoType");
        String EoHoOrgId = eo.getCurrentRow().getAttribute("EoHoOrgId").toString();
        String EoCldId = eo.getCurrentRow().getAttribute("EoCldId").toString();
        //    System.out.println("eo_id = "+eo_id+" EoSlocId = "+EoSlocId+" EoType = "+EoType+" EoHoOrgId = "+EoHoOrgId+" EoCldId = "+EoCldId);
        Integer cnt =
            Integer.parseInt(callStoredFunction(NUMBER, "get_coa_cnt_eo(?,?,?,?)", new Object[] { EoCldId, EoSlocId,
                                                                                                  EoHoOrgId,
                                                                                                  eo_id }).toString());
        //    System.out.println("count = "+cnt);
        String s =
            callStoredFunction(VARCHAR, "fn_is_deletable_eo(?,?,?,?)", new Object[] { EoCldId, EoSlocId, EoHoOrgId, eo_id }).toString();

//    System.out.println("s= "+s);
        if (cnt > 0) {
            String Rmsg = resolvElDCMsg("#{bundle['MSG.190']}").toString();
            FacesMessage message = new FacesMessage(Rmsg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            //  throw new ValidatorException(message);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            return null;

        } else if (s.equalsIgnoreCase("Y")) {
            String Rmsg = resolvElDCMsg("#{bundle['MSG.191']}").toString();
            FacesMessage message = new FacesMessage(Rmsg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            //  throw new ValidatorException(message);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            return null;

        }

        else {
            na_ID =
                    Integer.parseInt(callStoredFunction(NUMBER, "GET_NA_ID(?,?,?,?,?)", new Object[] { EoSlocId, EoCldId,
                                                                                                       EoHoOrgId,
                                                                                                       eo_id,
                                                                                                       EoType }).toString());
            //    System.out.println("Na id=" + na_ID);

            return "coa";


        }


    }

    public void createBank(ActionEvent actionEvent) {
        this.bnk_nm_form_Bind.setRequired(false);
        this.bnk_acc_no_form_Bind.setRequired(false);
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert4");
        operationBinding.execute();
        this.form_disable_Bank = false;
        if (this.bnkinactiveChkBxBind.getValue().equals(true)) {

            this.inActiveReasonBind.setDisabled(true);
        }
        
        this.treeTableEnt.setRowSelection("none");
        this.visibleFunction(AdfFacesContext.getCurrentInstance(), this.searchFormBind, true);
        this.visibleFunction(AdfFacesContext.getCurrentInstance(), this.entityForm, true);
        this.visibleFunction(AdfFacesContext.getCurrentInstance(),this.entitySubtypeForm, true);
        this.disablePanelTab(pannelTab, true);
    }

    public void editBank(ActionEvent actionEvent) {
        this.form_disable_Bank = false;
        this.treeTableEnt.setRowSelection("none");
        this.visibleFunction(AdfFacesContext.getCurrentInstance(), this.searchFormBind, true);
        this.visibleFunction(AdfFacesContext.getCurrentInstance(), this.entityForm, true);
        this.visibleFunction(AdfFacesContext.getCurrentInstance(),this.entitySubtypeForm, true);

        if (this.bnkinactiveChkBxBind.getValue().equals(true)) {

            this.inActiveReasonBind.setDisabled(true);
        }
        this.disablePanelTab(pannelTab, true);
    }

    public void saveBank(ActionEvent actionEvent) {
        if (this.bnk_nm_form_Bind.getValue() == null) {
            this.bnk_nm_form_Bind.setRequired(true);
        } else if (this.bnk_acc_no_form_Bind.getValue() == null) {
            this.bnk_acc_no_form_Bind.setRequired(true);
        } else {

            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();
            this.form_disable_Bank = true;
            String Rmsg = resolvElDCMsg("#{bundle['MSG.192']}").toString();
            FacesMessage msg = new FacesMessage(Rmsg);
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            this.treeTableEnt.setRowSelection("single");
            
            this.visibleFunction(AdfFacesContext.getCurrentInstance(), this.searchFormBind, false);
            this.visibleFunction(AdfFacesContext.getCurrentInstance(), this.entityForm, false);
            this.visibleFunction(AdfFacesContext.getCurrentInstance(),this.entitySubtypeForm, false);

            this.disablePanelTab(pannelTab, false);
            
            

        }
    }

    public void cancelBank(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("AppEoMst1Iterator");
        Key parentKey = parentIter.getCurrentRow().getKey();

        DCIteratorBinding dtlIter = (DCIteratorBinding)bindings.get("AppEo2Iterator");
        Key dtlKey = dtlIter.getCurrentRow().getKey();


        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        OperationBinding operationBinding1 = bindings.getOperationBinding("Execute4");
        operationBinding1.execute();


        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
        dtlIter.setCurrentRowWithKey(dtlKey.toStringFormat(true));
        this.form_disable_Bank = true;
        this.treeTableEnt.setRowSelection("single");
        this.visibleFunction(AdfFacesContext.getCurrentInstance(), this.searchFormBind, false);
        this.visibleFunction(AdfFacesContext.getCurrentInstance(), this.entityForm, false);
        this.visibleFunction(AdfFacesContext.getCurrentInstance(),this.entitySubtypeForm, false);

        this.disablePanelTab(pannelTab, false);
    }

    public void setForm_disable_Bank(boolean form_disable_Bank) {
        this.form_disable_Bank = form_disable_Bank;
    }

    public boolean isForm_disable_Bank() {
        return form_disable_Bank;
    }

    public void activeBankValueChangeList(ValueChangeEvent vce) {
        if (vce.getNewValue().equals(true)) {
            inActiveDateBind.setValue(null);
            inActiveReasonBind.setValue(null);
            inActiveReasonBind.setDisabled(true);
        } else if (vce.getNewValue().equals(false)) {
            inActiveDateBind.setValue(Date.getCurrentDate());
            inActiveReasonBind.setDisabled(false);
        }
    }

    public void setInActiveReasonBind(RichInputText inActiveReasonBind) {
        this.inActiveReasonBind = inActiveReasonBind;
    }

    public RichInputText getInActiveReasonBind() {
        return inActiveReasonBind;
    }

    public void setInActiveDateBind(RichInputDate inActiveDateBind) {
        this.inActiveDateBind = inActiveDateBind;
    }

    public RichInputDate getInActiveDateBind() {
        return inActiveDateBind;
    }

    public void createPersCom(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert5");
        operationBinding.execute();
        this.eo_per_nm_form_bind.setRequired(false);
        this.eo_per_desg_form_bind.setRequired(false);
        this.form_disable_pDtl = false;
        this.treeTableEnt.setRowSelection("none");
        this.visibleFunction(AdfFacesContext.getCurrentInstance(), this.searchFormBind, true);
        this.visibleFunction(AdfFacesContext.getCurrentInstance(), this.entityForm, true);
        this.visibleFunction(AdfFacesContext.getCurrentInstance(),this.entitySubtypeForm, true);
        
        this.disablePanelTab(pannelTab, true);
    }

    public void editPersCom(ActionEvent actionEvent) {
        this.form_disable_pDtl = false;
        this.treeTableEnt.setRowSelection("none");
        this.visibleFunction(AdfFacesContext.getCurrentInstance(), this.searchFormBind, true);
        this.visibleFunction(AdfFacesContext.getCurrentInstance(), this.entityForm, true);
        this.visibleFunction(AdfFacesContext.getCurrentInstance(),this.entitySubtypeForm, true);

        this.disablePanelTab(pannelTab, true);
    }

    public void savePersCom(ActionEvent actionEvent) {

        if (this.eo_per_nm_form_bind.getValue() == null) {

            this.eo_per_nm_form_bind.setRequired(true);
        } else if (this.eo_per_desg_form_bind.getValue() == null) {

            this.eo_per_desg_form_bind.setRequired(true);
        } else {

            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();
            if (operationBinding.getErrors().isEmpty()) {
                String Rmsg = resolvElDCMsg("#{bundle['MSG.193']}").toString();
                FacesMessage msg = new FacesMessage(Rmsg);
                msg.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext.getCurrentInstance().addMessage(null, msg);
                this.form_disable_pDtl = true;
            }
            this.treeTableEnt.setRowSelection("single");
            this.visibleFunction(AdfFacesContext.getCurrentInstance(), this.searchFormBind, false);
            this.visibleFunction(AdfFacesContext.getCurrentInstance(), this.entityForm, false);
            this.visibleFunction(AdfFacesContext.getCurrentInstance(),this.entitySubtypeForm, false);
           
            this.disablePanelTab(pannelTab, false);
        }


    }

    public void cancelPersCom(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("AppEoMst1Iterator");
        Key parentKey = parentIter.getCurrentRow().getKey();

        DCIteratorBinding dtlIter = (DCIteratorBinding)bindings.get("AppEo2Iterator");
        Key dtlKey = dtlIter.getCurrentRow().getKey();


        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        OperationBinding operationBinding1 = bindings.getOperationBinding("Execute5");
        operationBinding1.execute();


        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
        dtlIter.setCurrentRowWithKey(dtlKey.toStringFormat(true));
        this.form_disable_pDtl = true;
        this.treeTableEnt.setRowSelection("single");
        this.visibleFunction(AdfFacesContext.getCurrentInstance(), this.searchFormBind, false);
        this.visibleFunction(AdfFacesContext.getCurrentInstance(), this.entityForm, false);
        this.visibleFunction(AdfFacesContext.getCurrentInstance(),this.entitySubtypeForm, false);
    
        this.disablePanelTab(pannelTab, false);
    }

    public void setForm_disable_pDtl(boolean form_disable_pDtl) {
        this.form_disable_pDtl = form_disable_pDtl;
    }

    public boolean isForm_disable_pDtl() {
        return form_disable_pDtl;
    }

    public void addPrf(ActionEvent actionEvent) {

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert6");
        operationBinding.execute();
         
         if(am.getAppEoPrf2().getCurrentRow().getAttribute("SuppCurrId") != null)
          this.currDis=((Integer)(am.getAppEoPrf2().getCurrentRow().getAttribute("SuppCurrId"))>0) ? true : false;         
        else
            this.currDis=false;
        this.disablePanelTab(pannelTab, true);
        this.treeTableEnt.setRowSelection("none");
        this.visibleFunction(AdfFacesContext.getCurrentInstance(), this.searchFormBind, true);
        this.visibleFunction(AdfFacesContext.getCurrentInstance(), this.entityForm, true);
        this.visibleFunction(AdfFacesContext.getCurrentInstance(),this.entitySubtypeForm, true);

        
        this.form_disable_prf = false;
    
    }

    public void editPrf(ActionEvent actionEvent) {
        this.currDis=((Integer)(am.getAppEoPrf2().getCurrentRow().getAttribute("SuppCurrId"))>0) ? true : false; 
        this.form_disable_prf = false;
        this.blklistchkbxbind.getValue();

        if (this.blklistchkbxbind.getValue().equals(true)) {
            this.blacklistVis = false;

        }
        if (this.onholdchkbxbind.getValue().equals(true)) {
            this.onholdVis = false;

        }
        
       
        this.treeTableEnt.setRowSelection("none");
        this.visibleFunction(AdfFacesContext.getCurrentInstance(), this.searchFormBind, true);
        this.visibleFunction(AdfFacesContext.getCurrentInstance(), this.entityForm, true);
        this.visibleFunction(AdfFacesContext.getCurrentInstance(),this.entitySubtypeForm, true);

        
        this.disablePanelTab(pannelTab, true);
    }

    public void savePrf(ActionEvent actionEvent) {
        //    System.out.println("---------------------------Trying to commit data--------------------");
    //   System.out.println("Eo_id is -------------"+am.getAppEoPrf2().getCurrentRow().getAttribute("EoId"));
        Integer cur= (Integer) am.getAppEoPrf2().getCurrentRow().getAttribute("SuppCurrId");
        am.getAppEoPrf2().getCurrentRow().setAttribute("SuppCurrId",cur);
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        operationBinding.execute();
        
//        System.out.println("Eo_id is -------------"+am.getAppEoPrf2().getCurrentRow().getAttribute("EoId"));
//       
//        System.out.println("Currency id is --------------------"+am.getAppEoPrf2().getCurrentRow().getAttribute("SuppCurrId"));
//        


        
        
        if (operationBinding.getErrors().isEmpty()) {
           
            String Rmsg = resolvElDCMsg("#{bundle['MSG.194']}").toString();
            FacesMessage msg = new FacesMessage(Rmsg);
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            this.form_disable_prf = true;
            this.onholdVis = true;
            this.blacklistVis = true;
            this.treeTableEnt.setRowSelection("single");
            this.visibleFunction(AdfFacesContext.getCurrentInstance(), this.searchFormBind, false);
            this.visibleFunction(AdfFacesContext.getCurrentInstance(), this.entityForm, false);
            this.visibleFunction(AdfFacesContext.getCurrentInstance(),this.entitySubtypeForm, false);

            
            this.disablePanelTab(pannelTab, false);
        }


    }

    public void cancelPrf(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("AppEoMst1Iterator");
        Key parentKey = parentIter.getCurrentRow().getKey();

        DCIteratorBinding dtlIter = (DCIteratorBinding)bindings.get("AppEo2Iterator");
        Key dtlKey = dtlIter.getCurrentRow().getKey();

     
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        OperationBinding operationBinding1 = bindings.getOperationBinding("Execute6");
        operationBinding1.execute();


        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
        dtlIter.setCurrentRowWithKey(dtlKey.toStringFormat(true));
        this.form_disable_prf = true;

        this.onholdVis = true;
        this.blacklistVis = true;
        this.treeTableEnt.setRowSelection("single");
        this.visibleFunction(AdfFacesContext.getCurrentInstance(), this.searchFormBind, false);
        this.visibleFunction(AdfFacesContext.getCurrentInstance(), this.entityForm, false);
        this.visibleFunction(AdfFacesContext.getCurrentInstance(),this.entitySubtypeForm, false);

        
        this.disablePanelTab(pannelTab, false);

    }

    public void setForm_disable_prf(boolean form_disable_prf) {
        this.form_disable_prf = form_disable_prf;
    }

    public boolean isForm_disable_prf() {
        return form_disable_prf;
    }

    public void blockListedValueChangeListner(ValueChangeEvent vce) {
        if (vce.getNewValue().equals(true)) {
            blFrmDtBind.setValue(Date.getCurrentDate());
            this.blacklistVis = false;

        } else if (vce.getNewValue().equals(false)) {
            blFrmDtBind.setValue(null);
            blRsnBind.setValue(null);
            blToDtBind.setValue(null);
            this.blacklistVis = true;

        }
    }

    public void onholdValueChangeList(ValueChangeEvent vce) {
        String val = vce.getNewValue().toString();
        if (vce.getNewValue().equals(true)) {
            ohFrmDtBind.setValue(Date.getCurrentDate());
            this.onholdVis = false;
            //this.blklistchkbxbind.setVisible(false);
        } else if (vce.getNewValue().equals(false)) {
            ohFrmDtBind.setValue(null);
            ohRsnBind.setValue(null);
            ohToDtBind.setValue(null);
            this.onholdVis = true;
            //this.blklistchkbxbind.setVisible(true);
        }
    }

    public void setBlRsnBind(RichInputText blRsnBind) {
        this.blRsnBind = blRsnBind;
    }

    public RichInputText getBlRsnBind() {
        return blRsnBind;
    }

    public void setBlFrmDtBind(RichInputDate blFrmDtBind) {
        this.blFrmDtBind = blFrmDtBind;
    }

    public RichInputDate getBlFrmDtBind() {
        return blFrmDtBind;
    }

    public void setBlToDtBind(RichInputDate blToDtBind) {
        this.blToDtBind = blToDtBind;
    }

    public RichInputDate getBlToDtBind() {
        return blToDtBind;
    }

    public void setOhRsnBind(RichInputText ohRsnBind) {
        this.ohRsnBind = ohRsnBind;
    }

    public RichInputText getOhRsnBind() {
        return ohRsnBind;
    }

    public void setOhFrmDtBind(RichInputDate ohFrmDtBind) {
        this.ohFrmDtBind = ohFrmDtBind;
    }

    public RichInputDate getOhFrmDtBind() {
        return ohFrmDtBind;
    }

    public void setOhToDtBind(RichInputDate ohToDtBind) {
        this.ohToDtBind = ohToDtBind;
    }

    public RichInputDate getOhToDtBind() {
        return ohToDtBind;
    }

    public void eoNmValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String msg2 = "";
        if (object != null) {
            String langDesc = object.toString();
            int openB = 0;
            int closeB = 0;
            boolean closeFg = false;
            char[] xx = langDesc.toCharArray();
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


            //if openB=0 then no. of closing and opening brackets equal || opening bracket must always come before closing brackets
            //closing brackets must not come before first occurrence of openning bracket
            if (openB != closeB || closeFg == true || (langDesc.lastIndexOf("(") > langDesc.lastIndexOf(")")) ||
                (langDesc.indexOf(")") < langDesc.indexOf("("))) {
                msg2 = resolvElDCMsg("#{bundle['MSG.7']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (langDesc.contains("()")) {
                msg2 = resolvElDCMsg("#{bundle['MSG.170']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (langDesc.contains("(.") || langDesc.contains("(-") || langDesc.contains("(&") || langDesc.contains("-)") ) {
                msg2 = resolvElDCMsg("#{bundle['MSG.195']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }

            openB = 0;
            closeB = 0;
            closeFg = false;


            //check for valid language name.Allowed- brackets,dots,hyphen,ampersend &,also allow
            String expression = "^(?:(?>[A-Za-z0-9 \\(\\)]+)(\\.|\\-(?!\\.|\\-|$))*(\\.|\\&(?!\\.|\\&|$))*(\\.|\\:(?!\\.|\\:|$))*" +
                "(\\.|\\/(?!\\.|\\/|$))*(\\.|\\;(?!\\.|\\;|$))*(\\.|\\\\(?!\\.|\\\\|$))*(\\.|\\,(?!\\.|\\,|$))?)*$";
            //String expression = "^(?:(?>[A-Za-z0-9 ()]+)(.|-(?!.|-|$))*(.|&(?!.|&|$))*(.|:(?!.|:|$))*(.|/(?!.|/|$))*(.|;(?!.|;|$))?)*$";
            CharSequence inputStr = langDesc;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String error = resolvElDCMsg("#{bundle['MSG.1185']}").toString();

            if (matcher.matches()) {
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                              resolvElDCMsg("#{bundle['MSG.1185']}").toString()));
            }

            /** Code to find duplicate record*/
            String cldid = resolvEl("#{pageFlowScope. GLBL_APP_CLD_ID}");
            Integer slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}".toString()));
            String HoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
            String is_unique = null;
            if (mstMode.equalsIgnoreCase("A")) {
                is_unique =
                        (String)callStoredFunction(VARCHAR, "fn_is_eo_grp_unique(?,?,?,?,?)", new Object[] { cldid, slocid,
                                                                                                             HoOrgId,
                                                                                                             object.toString().trim().toUpperCase(),
                                                                                                             null });

            } else if (mstMode.equalsIgnoreCase("E")) {
                AppEoMstVOImpl appEoMst = am.getAppEoMst1();
                Row currentRow = appEoMst.getCurrentRow();
                Integer eoMstId = (Integer)currentRow.getAttribute("EoMstId");
                is_unique =
                        (String)callStoredFunction(VARCHAR, "fn_is_eo_grp_unique(?,?,?,?,?)", new Object[] { cldid, slocid,
                                                                                                             HoOrgId,
                                                                                                             object.toString().trim().toUpperCase(),
                                                                                                             eoMstId });
            } else
                System.out.println("error onin executing function");
            //    System.out.println("result from is unique_mst_name function = " + is_unique);
            if (is_unique.equalsIgnoreCase("N")) {
                //    System.out.println("when output is N");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                              "Duplicate Entity Group Name"));
            }
        }
    }

    public void Number_validator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        oracle.jbo.domain.Number value = (oracle.jbo.domain.Number)object;
        if (value == null) {
            return;
        }
        if (value.compareTo(0) == -1) {
            String error = resolvElDCMsg("#{bundle['MSG.198']}").toString();
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
        }


    }

    public void Number_validatorDecimal(FacesContext facesContext, UIComponent uIComponent, Object object) {
        oracle.jbo.domain.Number value = (oracle.jbo.domain.Number)object;
        if (value == null) {
            return;
        } else {
            Boolean is = isPrecisionValid(26, 6, value);
            if (is.toString().equalsIgnoreCase("true")) {
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision(26,6).",
                                                              null));
            }

        }
        if (value.compareTo(0) == -1) {
            String error = resolvElDCMsg("#{bundle['MSG.198']}").toString();
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
        }

    }

    public Boolean isPrecisionValid(Integer precision, Integer scale, oracle.jbo.domain.Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();

        vc.setPrecision(precision);


        vc.setScale(scale);

        return vc.validateValue(total);
    }

    public void NameValidation_Normal(FacesContext facesContext, UIComponent uIComponent, Object object) {
        /* String value = (String)object;
        if ((value == null) || value.length() == 0) {
            return;
        }


        String expression = "[A-Za-z0-9]*";
        CharSequence inputStr = value;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        String error = "Special character not allowed";
        if (matcher.matches()) {
        } else {

            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
        } */
        if (object != null) {
            String quotId = object.toString();
            int openB = 0;
            int closeB = 0;
            boolean closeFg = false;

            char[] xx = quotId.toCharArray();

            for (char c : xx) {

                if (c == '(') {

                    openB = openB + 1;

                } else if (c == ')') {

                    closeB = closeB + 1;

                }

                if (closeB > openB) {
                    closeFg =
                            true; /**1.no. of closed brackets will not be more than open brackets at any given time.*/
                }
            }

            /**2.if openB=0 then no. of closing and opening brackets equal ||
                          * 3.opening bracket must always come before closing brackets
                          * 4.closing brackets must not come before first occurrence of openning bracket
                          */
            if (openB != closeB || closeFg == true || (quotId.lastIndexOf("(") > quotId.lastIndexOf(")")) ||
                (quotId.indexOf(")") < quotId.indexOf("("))) {
                String msg2 = resolvElDCMsg("#{bundle['MSG.7']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }

            /**5.check for empty brackets
                           */
            if (quotId.contains("()")) {
                String msg2 = resolvElDCMsg("#{bundle['MSG.170']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            /**check for to dot not comes together
                           */
            /* if(quotId.contains("..")){
                                      String msg2="Consecutive dots .. value not allowed together.";
                                      FacesMessage message2 = new FacesMessage(msg2);
                                      message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                      throw new ValidatorException(message2);
                              }  */
            /**check for to dot not comes together
                           */
            /*  if(quotId.contains("--")){
                                      String msg2="Consecutive hyphens -- value not allowed together.";
                                      FacesMessage message2 = new FacesMessage(msg2);
                                      message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                      throw new ValidatorException(message2);
                              }  */
            /**  check for wrong combo
                           */
            if (quotId.contains("(.") || quotId.contains("(-") || quotId.contains("-)")) {
                String msg2 = resolvElDCMsg("#{bundle['MSG.59']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            openB = 0;
            closeB = 0;
            closeFg = false;


            /**check for valid cornm .. Starts with character. can have dots .No two dots can be adjacent.
                               *
                               */


            //check for valid country name.. Starts with character. can have dots .No two dots can be adjacent.
            String expression =
                "^(?:(?>[A-Za-z0-9 )]+)(?:\\_|\\-|\\(|\\.|\\)|\\:|\\#|\\/|\\\\(?!\\_|\\-|\\:|\\#|\\/|\\\\|$))?)*$";
            CharSequence inputStr = quotId;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String error = resolvElDCMsg("#{bundle['MSG.199']}").toString();

            if (matcher.matches()) {
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
            }

        }

    }

    public void NameValidation_ScAllow(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String msgDesc = object.toString();
            int openB = 0;
            int closeB = 0;
            boolean closeFg = false;

            char[] xx = msgDesc.toCharArray();
            for (char c : xx) {

                if (c == '(') {
                    openB = openB + 1;
                } else if (c == ')') {
                    closeB = closeB + 1;
                }

                if (closeB > openB) {
                    closeFg = true; //1.no. of closed brackets will not be more than open brackets at any given time.
                }
            }


            //if openB=0 then no. of closing and opening brackets equal || opening bracket must always come before closing brackets
            //closing brackets must not come before first occurrence of openning bracket
            if (openB != closeB || closeFg == true || (msgDesc.lastIndexOf("(") > msgDesc.lastIndexOf(")")) ||
                (msgDesc.indexOf(")") < msgDesc.indexOf("("))) {
                String msg2 = resolvElDCMsg("#{bundle['MSG.7']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            //5.check for empty brackets
            if (msgDesc.contains("()")) {
                String msg2 = resolvElDCMsg("#{bundle['MSG.170']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }

            //6. check for invalid combo
            if (msgDesc.contains("(-") || msgDesc.contains("-)")) {
                String msg2 = resolvElDCMsg("#{bundle['MSG.149']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }

            openB = 0;
            closeB = 0;
            closeFg = false;
            String expression = "^(?:(?>[A-Za-z0-9 \\(\\)]+)(?:\\-(?!\\-|$))?)*$";

            CharSequence inputStr = msgDesc;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String error = resolvElDCMsg("#{bundle['MSG.200']}").toString();

            if (matcher.matches()) {
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
            }

        }
    }


    public void phoneNoValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
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
                    closeFg = true; //closed brackets will not be more than open brackets at any given time.
                }
            }
            //if openB=0 then no. of closing and opening brackets equal || opening bracket must always come before closing brackets
            //closing brackets must not come before first occurrence of openning bracket
            if (openB != closeB || closeFg == true || (phnNo.lastIndexOf("(") > phnNo.lastIndexOf(")")) ||
                (phnNo.indexOf(")") < phnNo.indexOf("("))) {
                msg2 = resolvElDCMsg("#{bundle['MSG.7']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (phnNo.contains("()")) {
                msg2 = resolvElDCMsg("#{bundle['MSG.170']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (phnNo.contains("(.") || phnNo.contains("(-") || phnNo.contains("-)")) {
                msg2 = resolvElDCMsg("#{bundle['MSG.196']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }

            openB = 0;
            closeB = 0;
            closeFg = false;
            //check for valid language name.Allowed- brackets,dots,hyphen

            String expression = "([0-9\\-\\+\\(\\)]+)";
            CharSequence inputStr = phnNo;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String error = resolvElDCMsg("#{bundle['MSG.202']}").toString();


            if (matcher.matches()) {
                if (phnNo.contains("++") || phnNo.contains("--")) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                                  resolvElDCMsg("#{bundle['MSG.201']}").toString()));
                } else if (phnNo.lastIndexOf("+") > 1) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                                  resolvElDCMsg("#{bundle['MSG.203']}").toString()));
                } else if (phnNo.lastIndexOf("+") == 1 && phnNo.charAt(0) != '(') {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                                  resolvElDCMsg("#{bundle['MSG.203']}").toString()));
                } else if (phnNo.startsWith(" ") || phnNo.endsWith(" ")) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                                  resolvElDCMsg("#{bundle['MSG.204']}").toString()));
                }

            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                              resolvElDCMsg("#{bundle['MSG.205']}").toString()));
            }

        }
    }


    public void emailIdValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String email = object.toString();
        if (email.startsWith(" ") || email.endsWith(" ")) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          resolvElDCMsg("#{bundle['MSG.54']}").toString(),
                                                          resolvElDCMsg("#{bundle['MSG.204']}").toString()));
        }
        String expression = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        String error = resolvElDCMsg("#{bundle['MSG.54']}").toString();

        if (matcher.matches()) {
        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                          resolvElDCMsg("#{bundle['MSG.206']}").toString()));
        }
    }

    public void typeOnAddValueChangelist(ValueChangeEvent valueChangeEvent) {
        //  valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        ////    System.out.println("---------------in VCL of add EE type --------------");
        AdfFacesContext.getCurrentInstance().addPartialTarget(treeTableEnt);

        am = (ExternalEntityAMImpl)resolvElDC("ExternalEntityAMDataControl");
        if (valueChangeEvent.getNewValue() != null) {
            Integer eo_type = (Integer)valueChangeEvent.getNewValue();
            //    System.out.println("selected Type = " + eo_type);
            eoBehavAddFormBind.processUpdates(FacesContext.getCurrentInstance());
            AdfFacesContext.getCurrentInstance().addPartialTarget(eoBehavAddFormBind);

            ViewObjectImpl vo = am.getAppEo2();
            Integer EoSlocId = (Integer)vo.getCurrentRow().getAttribute("EoSlocId");

            String EoCldId = vo.getCurrentRow().getAttribute("EoCldId").toString();
            //    System.out.println("EoSlocId = " + EoSlocId + " EoCldId = " + EoCldId);
            ViewObjectImpl lovVo = am.getLovEoBehav1();
            lovVo.setNamedWhereClauseParam("Cldid_bind", EoCldId);
            lovVo.setNamedWhereClauseParam("Slocid_bind", EoSlocId);
            lovVo.setNamedWhereClauseParam("EoType_Bind", eo_type);
            Row[] r = lovVo.getAllRowsInRange();

            /** applied setWhereClause directly because changes not reflecting immediately on page*/
            /* lovVo.setWhereClause("EE_TYPE_ID =" + eo_type + " and EE_CLD_ID = '" + EoCldId + "' and EE_SLOC_ID =" +   EoSlocId);
             Row[] r  = lovVo.getAllRowsInRange(); */
            //   RowQualifier rq =  new RowQualifier("EeTypeId =" + eo_type + " and EeCldId = '" + EoCldId + "' and EeSlocId =" +   EoSlocId);
            //Row[] r = lovVo.getFilteredRows(rq);
            //    System.out.println("r.length = " + r.length);

            if (r.length > 0) {
                String EeBh = (String)r[0].getAttribute("EeBhav");
                ////    System.out.println("r[0] = "+r[0]);
                //    System.out.println("EeBhav = " + EeBh);
                vo.getCurrentRow().setAttribute("EoBhav", EeBh);
                vo.executeQuery();
                //this.eoBehavAddFormBind.resetValue();
                //this.eoBehavAddFormBind.setValue(EeBh);
                AdfFacesContext.getCurrentInstance().addPartialTarget(eoBehavAddFormBind);
            } else {
                ////    System.out.println("no value exist ;;;;;;>");
            }
            ////    System.out.println(eoBehavAddFormBind.getValue()+"---------------------->"+vo.getCurrentRow().getAttribute("EoBhav"));
            if (eo_type == 78 || eo_type == 79) {
                currIdTransBinding.setVisible(true);
                //currOnAddBind.setRequired(true);
                catgIdBindVar.setValue(null);
                catgIdBindVar.setVisible(false);

            } else {
                am = (ExternalEntityAMImpl)resolvElDC("ExternalEntityAMDataControl");
                ViewObjectImpl v = am.getAppEo2();
                Row row = v.getCurrentRow();
                row.setAttribute("CurrIdSp", null);
                currIdTransBinding.setVisible(false);
                //currOnAddBind.setRequired(false);
                catgIdBindVar.setValue(null);
                catgIdBindVar.setVisible(false);

            }


            if (this.eoBehavAddFormBind != null) {

            }

            if ((Integer)valueChangeEvent.getNewValue() == 69) {

                AdfFacesContext.getCurrentInstance().addPartialTarget(eoBehavAddFormBind);

                catgIdBindVar.setValue(null);
                catgIdBindVar.setVisible(false);

            } else if ((Integer)valueChangeEvent.getNewValue() == 68) {

                AdfFacesContext.getCurrentInstance().addPartialTarget(eoBehavAddFormBind);


                catgIdBindVar.setVisible(true);
            } else {
                this.eoBehavAddFormBind.setDisabled(false);
                catgIdBindVar.setValue(null);
                catgIdBindVar.setVisible(false);
            }
        }
        BindingContainer bc = getBindings();
        OperationBinding binding = bc.getOperationBinding("Execute2");
        binding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(treeTableEnt);

    }


    public void typeOnEditValuechangeLIst(ValueChangeEvent valueChangeEvent) {
        Integer eo_type = Integer.parseInt(valueChangeEvent.getNewValue().toString());
        if (eo_type == 78 || eo_type == 79) {
            currIdOnEditTransBinding.setVisible(true);
        } else {
            am = (ExternalEntityAMImpl)resolvElDC("ExternalEntityAMDataControl");
            ViewObjectImpl v = am.getAppEo2();
            Row row = v.getCurrentRow();
            row.setAttribute("CurrIdSp", null);
            currIdOnEditTransBinding.setVisible(false);

        }
    }


    public void setEoMstFormBind(RichPanelFormLayout eoMstFormBind) {
        this.eoMstFormBind = eoMstFormBind;
    }

    public RichPanelFormLayout getEoMstFormBind() {
        return eoMstFormBind;
    }

    public String viewCoaButton() {
        am = (ExternalEntityAMImpl)resolvElDC("ExternalEntityAMDataControl");
        ViewObjectImpl eo = am.getAppEo2();
        Integer eo_id = (Integer)eo.getCurrentRow().getAttribute("EoId");
        Integer EoSlocId = (Integer)eo.getCurrentRow().getAttribute("EoSlocId");
        String EoHoOrgId = eo.getCurrentRow().getAttribute("EoHoOrgId").toString();
        String EoCldId = eo.getCurrentRow().getAttribute("EoCldId").toString();
        Integer cnt =
            Integer.parseInt(callStoredFunction(NUMBER, "get_coa_cnt_eo(?,?,?,?)", new Object[] { EoCldId, EoSlocId,
                                                                                                  EoHoOrgId,
                                                                                                  eo_id }).toString());

        if (cnt == 0) {

            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.207']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            //  throw new ValidatorException(message);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            return null;
        } else {
            return "view";
        }
    }

    public void setEotypeBind(RichSelectOneChoice eotypeBind) {
        this.eotypeBind = eotypeBind;
    }

    public RichSelectOneChoice getEotypeBind() {
        return eotypeBind;
    }

    public void specificCurrValidtor(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (this.eotypeBind.getValue().toString().equals("79")) {
            if (object == null) {

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.208']}").toString(), null));

            }
        }
    }

    public void setSpecificCurrEditFieldVisibleRequired(boolean specificCurrEditFieldVisibleRequired) {
        this.specificCurrEditFieldVisibleRequired = specificCurrEditFieldVisibleRequired;
    }

    public boolean isSpecificCurrEditFieldVisibleRequired() {
        return specificCurrEditFieldVisibleRequired;
    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public void setBlacklistVis(boolean blacklistVis) {
        this.blacklistVis = blacklistVis;
    }

    public boolean isBlacklistVis() {
        return blacklistVis;
    }

    public void setOnholdVis(boolean onholdVis) {
        this.onholdVis = onholdVis;
    }

    public boolean isOnholdVis() {
        return onholdVis;
    }

    public void setOnholdchkbxbind(RichSelectBooleanCheckbox onholdchkbxbind) {
        this.onholdchkbxbind = onholdchkbxbind;
    }

    public RichSelectBooleanCheckbox getOnholdchkbxbind() {
        return onholdchkbxbind;
    }

    public void setBlklistchkbxbind(RichSelectBooleanCheckbox blklistchkbxbind) {
        this.blklistchkbxbind = blklistchkbxbind;
    }

    public RichSelectBooleanCheckbox getBlklistchkbxbind() {
        return blklistchkbxbind;
    }

    public void setBnkinactiveChkBxBind(RichSelectBooleanCheckbox bnkinactiveChkBxBind) {
        this.bnkinactiveChkBxBind = bnkinactiveChkBxBind;
    }

    public RichSelectBooleanCheckbox getBnkinactiveChkBxBind() {
        return bnkinactiveChkBxBind;
    }

    public void setna_ID(Integer na_ID) {
        this.na_ID = na_ID;
    }

    public Integer getna_ID() {
        return na_ID;
    }

    public void setEoLgcyCodePopBind(RichInputText eoLgcyCodePopBind) {
        this.eoLgcyCodePopBind = eoLgcyCodePopBind;
    }

    public RichInputText getEoLgcyCodePopBind() {
        return eoLgcyCodePopBind;
    }

    public void setWarningPopBind(RichPopup warningPopBind) {
        this.warningPopBind = warningPopBind;
    }

    public RichPopup getWarningPopBind() {
        return warningPopBind;
    }


    public void setWrningmsg(String wrningmsg) {
        this.wrningmsg = wrningmsg;
    }

    public String getWrningmsg() {
        return wrningmsg;
    }

    public void setSubEoWarnPopBind(RichPopup subEoWarnPopBind) {
        this.subEoWarnPopBind = subEoWarnPopBind;
    }

    public RichPopup getSubEoWarnPopBind() {
        return subEoWarnPopBind;
    }


    public void setEditSubWrnPopUpBind(RichPopup editSubWrnPopUpBind) {
        this.editSubWrnPopUpBind = editSubWrnPopUpBind;
    }

    public RichPopup getEditSubWrnPopUpBind() {
        return editSubWrnPopUpBind;
    }


    public void addsubentitywrnDialogList(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {

            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();
            // showPopup(this.eoPopup, false);
            eoPopup.hide();
            if (operationBinding.getErrors().isEmpty()) {
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.185']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }

            am = (ExternalEntityAMImpl)resolvElDC("ExternalEntityAMDataControl");
            ViewObjectImpl eoForAdd = am.getAppEo2();

            Key key = eoForAdd.getCurrentRow().getKey();


            OperationBinding operationBindingss = bindings.getOperationBinding("Execute2");
            operationBindingss.execute();

            ViewObjectImpl eo = am.getAppEo2();

            DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("AppEo2Iterator");
            try {
                if (key != null) {
                    parentIter.setCurrentRowWithKey(key.toStringFormat(true));
                }
                RowKeySet selectedRowKeys = new RowKeySetImpl();
                ArrayList arr = new ArrayList();
                selectedRowKeys.add(key);
                treeTableEnt.setSelectedRowKeys(selectedRowKeys);

            } catch (Exception e) {

            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(treeTableEnt);
            AdfFacesContext.getCurrentInstance().addPartialTarget(eoForm1Bind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(eoFormBind2);


        }


    }

    public void setEoBehavAddFormBind(RichSelectOneChoice eoBehavAddFormBind) {
        this.eoBehavAddFormBind = eoBehavAddFormBind;
    }

    public RichSelectOneChoice getEoBehavAddFormBind() {
        return eoBehavAddFormBind;
    }

    public void setEoBehvEditPopBind(RichSelectOneChoice eoBehvEditPopBind) {
        this.eoBehvEditPopBind = eoBehvEditPopBind;
    }

    public RichSelectOneChoice getEoBehvEditPopBind() {
        return eoBehvEditPopBind;
    }


    public void setBnk_nm_form_Bind(RichInputText bnk_nm_form_Bind) {
        this.bnk_nm_form_Bind = bnk_nm_form_Bind;
    }

    public RichInputText getBnk_nm_form_Bind() {
        return bnk_nm_form_Bind;
    }

    public void setBnk_acc_no_form_Bind(RichInputText bnk_acc_no_form_Bind) {
        this.bnk_acc_no_form_Bind = bnk_acc_no_form_Bind;
    }

    public RichInputText getBnk_acc_no_form_Bind() {
        return bnk_acc_no_form_Bind;
    }

    public void setEo_per_nm_form_bind(RichInputText eo_per_nm_form_bind) {
        this.eo_per_nm_form_bind = eo_per_nm_form_bind;
    }

    public RichInputText getEo_per_nm_form_bind() {
        return eo_per_nm_form_bind;
    }

    public void setEo_per_desg_form_bind(RichInputText eo_per_desg_form_bind) {
        this.eo_per_desg_form_bind = eo_per_desg_form_bind;
    }

    public RichInputText getEo_per_desg_form_bind() {
        return eo_per_desg_form_bind;
    }

    public void setOn_load(Integer on_load) {
        this.on_load = on_load;
    }

    public Integer getOn_load() {
        return on_load;
    }

    public void setCatgIdBindVar(RichSelectOneChoice catgIdBindVar) {
        this.catgIdBindVar = catgIdBindVar;
    }

    public RichSelectOneChoice getCatgIdBindVar() {
        return catgIdBindVar;
    }

    public void setMstNameBinding(RichInputText mstNameBinding) {
        this.mstNameBinding = mstNameBinding;
    }

    public RichInputText getMstNameBinding() {
        return mstNameBinding;
    }

    public void SubEntityNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String msg2 = "";
        if (object != null) {
            String langDesc = object.toString();
            int openB = 0;
            int closeB = 0;
            boolean closeFg = false;
            char[] xx = langDesc.toCharArray();
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


            //if openB=0 then no. of closing and opening brackets equal || opening bracket must always come before closing brackets
            //closing brackets must not come before first occurrence of openning bracket
            if (openB != closeB || closeFg == true || (langDesc.lastIndexOf("(") > langDesc.lastIndexOf(")")) ||
                (langDesc.indexOf(")") < langDesc.indexOf("("))) {
                msg2 = resolvElDCMsg("#{bundle['MSG.7']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (langDesc.contains("()")) {
                msg2 = resolvElDCMsg("#{bundle['MSG.170']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (langDesc.contains("(.") || langDesc.contains("(-") || langDesc.contains("-)")) {
                msg2 = resolvElDCMsg("#{bundle['MSG.195']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }

            openB = 0;
            closeB = 0;
            closeFg = false;


            //check for valid language name.Allowed- brackets,dots,hyphen
            String expression = "^(?:(?>[A-Za-z0-9 \\(\\)]+)(\\.|\\-(?!\\.|\\-|$))*(\\.|\\&(?!\\.|\\&|$))*(\\.|\\:(?!\\.|\\:|$))*" +
                "(\\.|\\/(?!\\.|\\/|$))*(\\.|\\;(?!\\.|\\;|$))*(\\.|\\\\(?!\\.|\\\\|$))*(\\.|\\,(?!\\.|\\,|$))?)*$";
                        CharSequence inputStr = langDesc;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String error = resolvElDCMsg("#{bundle['MSG.1185']}").toString();

            if (matcher.matches()) {
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                              resolvElDCMsg("#{bundle['MSG.1185']}").toString()));
            }

            Integer len = -1;
            String dupli = "0";
            Row currentRow = am.getAppEo2().getCurrentRow();
            Key currentkey = currentRow.getKey();
            //    System.out.println("Current EoId=" + currentRow.getAttribute("EoId"));
            Row Frow[] = am.getAppEo2().getFilteredRows("EoNm", object.toString().trim());
            if (Frow.length > 0)
                len = Frow.length - 1;

            while (len >= 0) {
                Row itrrow = Frow[len];
                //    System.out.println("itr eoid=" + itrrow.getAttribute("EoId").toString());
                if (!(itrrow.getAttribute("EoId").toString().equals(currentRow.getAttribute("EoId").toString()))) {
                    if (itrrow.getAttribute("EoNm").toString().trim().equalsIgnoreCase(object.toString().trim())) {
                        dupli = "1";
                        break;
                    }
                }
                len--;
            }
            //  itr.closeRowSetIterator();
            if (dupli.equals("0")) {
                //not duplicate
            } else {
                //msg for duplicate
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                              "Duplicate Entity Name."));

            }

        }
    }

    public void addSubEntityNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String msg2 = "";
        if (object != null) {
            String langDesc = object.toString();
            int openB = 0;
            int closeB = 0;
            boolean closeFg = false;
            char[] xx = langDesc.toCharArray();
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


            //if openB=0 then no. of closing and opening brackets equal || opening bracket must always come before closing brackets
            //closing brackets must not come before first occurrence of openning bracket
            if (openB != closeB || closeFg == true || (langDesc.lastIndexOf("(") > langDesc.lastIndexOf(")")) ||
                (langDesc.indexOf(")") < langDesc.indexOf("("))) {
                msg2 = resolvElDCMsg("#{bundle['MSG.7']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (langDesc.contains("()")) {
                msg2 = resolvElDCMsg("#{bundle['MSG.170']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (langDesc.contains("(.") || langDesc.contains("(-") || langDesc.contains("-)")) {
                msg2 = resolvElDCMsg("#{bundle['MSG.195']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }

            openB = 0;
            closeB = 0;
            closeFg = false;


            //check for valid language name.Allowed- brackets,dots,hyphen
            String expression = "^(?:(?>[A-Za-z0-9 \\(\\)]+)(\\.|\\-(?!\\.|\\-|$))*(\\.|\\&(?!\\.|\\&|$))*(\\.|\\:(?!\\.|\\:|$))*" +
                "(\\.|\\/(?!\\.|\\/|$))*(\\.|\\;(?!\\.|\\;|$))*(\\.|\\\\(?!\\.|\\\\|$))*(\\.|\\,(?!\\.|\\,|$))?)*$";
            CharSequence inputStr = langDesc;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String error = resolvElDCMsg("#{bundle['MSG.1185']}").toString();

            if (matcher.matches()) {
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                              null));
            }

            /*            Integer len = -1;
            String dupli = "0";
            Row currentRow = am.getAppEo2().getCurrentRow();
            Row Frow[] = am.getAppEo2().getFilteredRows("EoNm", object.toString().trim());
            if (Frow.length > 0)
                len = Frow.length - 1;

            while (len >= 0) {
                Row itrrow = Frow[len];
                //    System.out.println("itrrow=" + itrrow);


                if ((itrrow.getAttribute("EoId") == null && currentRow.getAttribute("EoId") == null)) {
                    //    System.out.println("Current row");
                } else {
                    if (itrrow.getAttribute("EoId") != null && currentRow.getAttribute("EoId") != null) {
                        if ((itrrow.getAttribute("EoId").toString().equals(currentRow.getAttribute("EoId").toString()))) {
                            //    System.out.println("Current row again");
                        } else {
                            //    System.out.println("Not a current row");
                            if (itrrow.getAttribute("EoNm").toString().trim().equalsIgnoreCase(object.toString().trim())) {
                                dupli = "1";
                                break;
                            }
                        }
                    } else {
                        //    System.out.println("Not a current row f");
                        if (itrrow.getAttribute("EoNm").toString().trim().equalsIgnoreCase(object.toString().trim())) {
                            dupli = "1";
                            break;
                        }
                    }

                }

                len--;
            }
            //  itr.closeRowSetIterator();
            if (dupli.equals("0")) {
                //not duplicate
            } else {
                //msg for duplicate
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                              "Duplicate Entity Name."));

            } */


            String cldid = resolvEl("#{pageFlowScope. GLBL_APP_CLD_ID}");
            Integer slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}".toString()));
            String HoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
            String is_unique = null;
            if (mode.equalsIgnoreCase("A")) {
                is_unique =
                        (String)callStoredFunction(VARCHAR, "fn_is_eo_nm_unique(?,?,?,?,?)", new Object[] { cldid, slocid,
                                                                                                            HoOrgId,
                                                                                                            object.toString().trim().toUpperCase(),
                                                                                                            null });

            } else if (mode.equalsIgnoreCase("E")) {
                AppEoVOImpl appEo = am.getAppEo2();
                Row currentRow = appEo.getCurrentRow();
                Integer eoMstId = (Integer)currentRow.getAttribute("EoId");
                is_unique =
                        (String)callStoredFunction(VARCHAR, "fn_is_eo_nm_unique(?,?,?,?,?)", new Object[] { cldid, slocid,
                                                                                                            HoOrgId,
                                                                                                            object.toString().trim().toUpperCase(),
                                                                                                            eoMstId });
            } else
                System.out.println("error onin executing function");

            //    System.out.println("result from is unique function_eo_name = " + is_unique);
            if (is_unique.equalsIgnoreCase("N")) {
                //    System.out.println("when output is N");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                              "Duplicate Entity Group Name"));
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(treeTableEnt);
        treeTableEnt.__updateChildrenImpl(facesContext);

        AdfFacesContext.getCurrentInstance().addPartialTarget(treeTableEnt);

        /* BindingContainer bc=getBindings();
        bc.getOperationBinding("Execute").execute(); */
        //getBindings().getOperationBinding("Execute2").execute();
    }

    public void setAddTab(RichShowDetailItem addTab) {
        this.addTab = addTab;
    }

    public RichShowDetailItem getAddTab() {
        return addTab;
    }

    public void personNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String msg2 = "";
        if (object != null) {
            String langDesc = object.toString();
            int openB = 0;
            int closeB = 0;
            boolean closeFg = false;
            char[] xx = langDesc.toCharArray();
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


            //if openB=0 then no. of closing and opening brackets equal || opening bracket must always come before closing brackets
            //closing brackets must not come before first occurrence of openning bracket
            if (openB != closeB || closeFg == true || (langDesc.lastIndexOf("(") > langDesc.lastIndexOf(")")) ||
                (langDesc.indexOf(")") < langDesc.indexOf("("))) {
                msg2 = resolvElDCMsg("#{bundle['MSG.7']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (langDesc.contains("()")) {
                msg2 = resolvElDCMsg("#{bundle['MSG.170']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (langDesc.contains("(.") || langDesc.contains("(-") || langDesc.contains("-)")) {
                msg2 = resolvElDCMsg("#{bundle['MSG.195']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }

            openB = 0;
            closeB = 0;
            closeFg = false;


            //check for valid language name.Allowed- brackets,dots,hyphen
            String expression = "^(?:(?>[A-Za-z0-9 \\(\\)]+)(\\.|\\-(?!\\.|\\-|$))?)*$";
            CharSequence inputStr = langDesc;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String error = resolvElDCMsg("#{bundle['MSG.197']}").toString();

            if (matcher.matches()) {
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                              resolvElDCMsg("#{bundle['MSG.172']}").toString()));
            }

        }
    }

    public void personaDesignationValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String msg2 = "";
        if (object != null) {
            String langDesc = object.toString();
            int openB = 0;
            int closeB = 0;
            boolean closeFg = false;
            char[] xx = langDesc.toCharArray();
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


            //if openB=0 then no. of closing and opening brackets equal || opening bracket must always come before closing brackets
            //closing brackets must not come before first occurrence of openning bracket
            if (openB != closeB || closeFg == true || (langDesc.lastIndexOf("(") > langDesc.lastIndexOf(")")) ||
                (langDesc.indexOf(")") < langDesc.indexOf("("))) {
                msg2 = resolvElDCMsg("#{bundle['MSG.7']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (langDesc.contains("()")) {
                msg2 = resolvElDCMsg("#{bundle['MSG.170']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (langDesc.contains("(.") || langDesc.contains("(-") || langDesc.contains("-)")) {
                msg2 = resolvElDCMsg("#{bundle['MSG.195']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }

            openB = 0;
            closeB = 0;
            closeFg = false;


            //check for valid language name.Allowed- brackets,dots,hyphen
            String expression = "^(?:(?>[A-Za-z0-9 \\(\\)]+)(\\.|\\-(?!\\.|\\-|$))?)*$";
            CharSequence inputStr = langDesc;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String error = resolvElDCMsg("#{bundle['MSG.197']}").toString();

            if (matcher.matches()) {
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                              resolvElDCMsg("#{bundle['MSG.172']}").toString()));
            }

        }
    }

    public void eoTypeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(treeTableEnt);
        AdfFacesContext.getCurrentInstance().addPartialTarget(naBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(coaBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(viewBinding);

    }

    private int del = 0;

    public void deletePopupDialogLisnter(DialogEvent dialogEvent) {
        BindingContainer bc = getBindings();
        OperationBinding binding = null;
        OperationBinding binding1 = null;
        if (dialogEvent.getOutcome().name().equalsIgnoreCase("ok")) {
            if (del == 2) {
                binding = bc.getOperationBinding("Delete3");
                binding.execute();
                binding = bc.getOperationBinding("Execute4");
                binding.execute();
                binding1 = bc.getOperationBinding("Commit");
                binding1.execute();
                // binding = bc.getOperationBinding("Execute4");
                //binding.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(bankTableBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(bankFormBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(editBankBinding);

            }
            if (del == 3) {
                binding = bc.getOperationBinding("Delete4");
                binding.execute();
                binding = bc.getOperationBinding("Execute5");
                binding.execute();
                binding1 = bc.getOperationBinding("Commit");
                binding1.execute();
                // binding = bc.getOperationBinding("Execute5");
                // binding.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(eoPerTableBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(persComFormBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(editPersBinding);

            }
        }

        if (binding1.getErrors().isEmpty() && binding.getErrors().isEmpty()) {
            String Rmsg = resolvElDCMsg("#{bundle['MSG.528']}").toString();
            FacesMessage message = new FacesMessage(Rmsg);
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } else {
            // String Rmsg = resolvElDCMsg("#{bundle['MSG.187']}").toString();
            FacesMessage message = new FacesMessage("Error in deleting record");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            //  throw new ValidatorException(message);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
    }

    public void setPopupDeleteBinding(RichPopup popupDeleteBinding) {
        this.popupDeleteBinding = popupDeleteBinding;
    }

    public RichPopup getPopupDeleteBinding() {
        return popupDeleteBinding;
    }


    public String deleteBank() {
        del = 2;
        showPopup(popupDeleteBinding, true);
        return null;
    }

    public String deletePersonalDetail() {
        del = 3;
        showPopup(popupDeleteBinding, true);
        return null;
    }

    public void setDel(int del) {
        this.del = del;
    }

    public int getDel() {
        return del;
    }


    public void setEoPerTableBinding(RichTable eoPerTableBinding) {
        this.eoPerTableBinding = eoPerTableBinding;
    }

    public RichTable getEoPerTableBinding() {
        return eoPerTableBinding;
    }

    public void setBankTableBinding(RichTable bankTableBinding) {
        this.bankTableBinding = bankTableBinding;
    }

    public RichTable getBankTableBinding() {
        return bankTableBinding;
    }

    public void setBankFormBinding(RichPanelFormLayout bankFormBinding) {
        this.bankFormBinding = bankFormBinding;
    }

    public RichPanelFormLayout getBankFormBinding() {
        return bankFormBinding;
    }

    public void setPersComFormBinding(RichPanelFormLayout persComFormBinding) {
        this.persComFormBinding = persComFormBinding;
    }

    public RichPanelFormLayout getPersComFormBinding() {
        return persComFormBinding;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void AvgCrDaysValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            //Integer days = Math.round((Integer)object);
            Integer days = (Integer)object;
            //    System.out.println("days=" + days);
            if (days < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Days should be greater than 0", null));
            } else {
                String expression = "([0-9])*"; //String with Number only! No deimal value allowed

                CharSequence inputStr = object.toString();
                Pattern pattern = Pattern.compile(expression);
                Matcher matcher = pattern.matcher(inputStr);
                String error =
                    resolvElDCMsg("Days must be in a Whole Number! Only Numeric Character allowed").toString();

                if (matcher.matches()) {
                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
                }

            }
        }
    }

    public void AgDlvDays(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            //Integer days = Math.round((Integer)object);
            Integer days = (Integer)object;
            //    System.out.println("days=" + days);
            if (days < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Days should be greater than 0", null));
            } else {
                String expression = "([0-9])*"; //String with Number only! No deimal value allowed

                CharSequence inputStr = object.toString();
                Pattern pattern = Pattern.compile(expression);
                Matcher matcher = pattern.matcher(inputStr);
                String error =
                    resolvElDCMsg("Days must be in a Whole Number! Only Numeric Character allowed").toString();

                if (matcher.matches()) {
                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
                }

            }
        }
    }

    public void setEoNameBinding(RichInputText eoNameBinding) {
        this.eoNameBinding = eoNameBinding;
    }

    public RichInputText getEoNameBinding() {

        return eoNameBinding;
    }

    public void setSearchGroupBinding(RichInputListOfValues searchGroupBinding) {
        this.searchGroupBinding = searchGroupBinding;
    }

    public RichInputListOfValues getSearchGroupBinding() {
        return searchGroupBinding;
    }

    public void setEditBankBinding(RichCommandImageLink editBankBinding) {
        this.editBankBinding = editBankBinding;
    }

    public RichCommandImageLink getEditBankBinding() {
        return editBankBinding;
    }

    public void setEditPersBinding(RichCommandImageLink editPersBinding) {
        this.editPersBinding = editPersBinding;
    }

    public RichCommandImageLink getEditPersBinding() {
        return editPersBinding;
    }

    public void setEntityId(Integer EntityId) {
        this.EntityId = EntityId;
    }

    public Integer getEntityId() {
        AppEoVOImpl appEo2 = am.getAppEo2();
        Row currentRow = appEo2.getCurrentRow();
        if (currentRow != null) {
            Integer eoid = (Integer)currentRow.getAttribute("EoId");
            if (eoid != null) {
                RequestContext.getCurrentInstance().getPageFlowScope().put("PARAM_EOID", eoid);
                return eoid;
            }
        }
        return EntityId;
    }

    public void setCurrIdTransBinding(RichInputListOfValues currIdTransBinding) {
        this.currIdTransBinding = currIdTransBinding;
    }

    public RichInputListOfValues getCurrIdTransBinding() {
        return currIdTransBinding;
    }

    public void setCurrIdOnEditTransBinding(RichInputListOfValues currIdOnEditTransBinding) {
        this.currIdOnEditTransBinding = currIdOnEditTransBinding;
    }

    public RichInputListOfValues getCurrIdOnEditTransBinding() {
        return currIdOnEditTransBinding;
    }


    public void setNaBinding(RichCommandButton naBinding) {
        this.naBinding = naBinding;
    }

    public RichCommandButton getNaBinding() {
        return naBinding;
    }

    public void setCoaBinding(RichCommandButton coaBinding) {
        this.coaBinding = coaBinding;
    }

    public RichCommandButton getCoaBinding() {
        return coaBinding;
    }

    public void setViewBinding(RichCommandButton viewBinding) {
        this.viewBinding = viewBinding;
    }

    public RichCommandButton getViewBinding() {
        return viewBinding;
    }

    public void behaviourValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(naBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(coaBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(viewBinding);
    }

    public void setDelButtonBinding(RichCommandImageLink delButtonBinding) {
        this.delButtonBinding = delButtonBinding;
    }

    public RichCommandImageLink getDelButtonBinding() {
        return delButtonBinding;
    }

    public void setDeleteButtonBinding(RichCommandImageLink deleteButtonBinding) {
        this.deleteButtonBinding = deleteButtonBinding;
    }

    public RichCommandImageLink getDeleteButtonBinding() {
        return deleteButtonBinding;
    }

    public void setFormBinding(RichPanelFormLayout formBinding) {
        this.formBinding = formBinding;
    }

    public RichPanelFormLayout getFormBinding() {
        return formBinding;
    }

    public void setSearchFormBind(RichPanelFormLayout searchFormBind) {
        this.searchFormBind = searchFormBind;
    }

    public RichPanelFormLayout getSearchFormBind() {
        return searchFormBind;
    }


    public void setRender(Integer render) {
        this.render = render;
    }

    public Integer getRender() {
        
       // //    System.out.println("In getter render");
        
        String mode=addAM.getModeToEntity();
        ////    System.out.println("current mode "+ mode);
        if(this.addTab.isDisclosed())
        {
                if(mode.equalsIgnoreCase("S")){

                    this.treeTableEnt.setRowSelection("single");
                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.treeTableEnt);
                    this.disablePanelTab(this.pannelTab,false);
                   
                    this.visibleFunction(AdfFacesContext.getCurrentInstance(), this.searchFormBind, false);
          //          //    System.out.println("Calling Set DisABLED fUNCTION");
                    this.visibleFunction(AdfFacesContext.getCurrentInstance(), this.entityForm, false);
                    this.visibleFunction(AdfFacesContext.getCurrentInstance(),this.entitySubtypeForm, false);
                    if((!this.form_disable_Bank) || (!this.form_disable_pDtl) || (!this.form_disable_prf))
                       this.disablePanelTab(this.pannelTab,true);
                }else{
                    this.treeTableEnt.setRowSelection("none");
                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.treeTableEnt);
                    this.disablePanelTab(this.pannelTab,true);
                    
                    this.visibleFunction(AdfFacesContext.getCurrentInstance(), this.searchFormBind, true);
                    
                    this.visibleFunction(AdfFacesContext.getCurrentInstance(), this.entityForm, true);
                    this.visibleFunction(AdfFacesContext.getCurrentInstance(),this.entitySubtypeForm, true);
                    }
         }
        else{ 
                  
                 if(this.prfTab.isDisclosed()) 
                    if(this.savePrf.isDisabled()){
            //          //    System.out.println("in save mode");
                        this.treeTableEnt.setRowSelection("single");
                        AdfFacesContext.getCurrentInstance().addPartialTarget(this.treeTableEnt);
                        this.disablePanelTab(this.pannelTab,false);
                       
                        this.visibleFunction(AdfFacesContext.getCurrentInstance(), this.searchFormBind, false);
              //          //    System.out.println("Calling Set DisABLED fUNCTION");
                        this.visibleFunction(AdfFacesContext.getCurrentInstance(), this.entityForm, false);
                        this.visibleFunction(AdfFacesContext.getCurrentInstance(),this.entitySubtypeForm, false);
                        if((!this.form_disable_Bank) || (!this.form_disable_pDtl) || (!this.form_disable_prf))
                           this.disablePanelTab(this.pannelTab,true);
                    }else{
                        //    System.out.println("edit mode");
                        this.treeTableEnt.setRowSelection("none");
                         AdfFacesContext.getCurrentInstance().addPartialTarget(this.treeTableEnt);
                        this.disablePanelTab(this.pannelTab,true);
                        
                //        //    System.out.println("after panel tab disable");
                        this.visibleFunction(AdfFacesContext.getCurrentInstance(), this.searchFormBind, true);
                        
                        
                        this.visibleFunction(AdfFacesContext.getCurrentInstance(), this.entityForm, true);
                        this.visibleFunction(AdfFacesContext.getCurrentInstance(),this.entitySubtypeForm, true);
                  //      //    System.out.println("After visible function");
                        }
            
            if(this.saveBank.isDisclosed()) 
               if(this.saveBankLink.isDisabled()){
                 ////    System.out.println("in save mode");
                   this.treeTableEnt.setRowSelection("single");
                   AdfFacesContext.getCurrentInstance().addPartialTarget(this.treeTableEnt);
                   this.disablePanelTab(this.pannelTab,false);
                  
                   this.visibleFunction(AdfFacesContext.getCurrentInstance(), this.searchFormBind, false);
                   ////    System.out.println("Calling Set DisABLED fUNCTION");
                   this.visibleFunction(AdfFacesContext.getCurrentInstance(), this.entityForm, false);
                   this.visibleFunction(AdfFacesContext.getCurrentInstance(),this.entitySubtypeForm, false);
                   if((!this.form_disable_Bank) || (!this.form_disable_pDtl) || (!this.form_disable_prf))
                      this.disablePanelTab(this.pannelTab,true);
               }else{
               //    //    System.out.println("edit mode");
                   this.treeTableEnt.setRowSelection("none");
                   AdfFacesContext.getCurrentInstance().addPartialTarget(this.treeTableEnt);
                   this.disablePanelTab(this.pannelTab,true);
                   
                   this.visibleFunction(AdfFacesContext.getCurrentInstance(), this.searchFormBind, true);
                   
                   this.visibleFunction(AdfFacesContext.getCurrentInstance(), this.entityForm, true);
                   this.visibleFunction(AdfFacesContext.getCurrentInstance(),this.entitySubtypeForm, true);
                   }
            
            if(this.editDtl.isDisclosed()) 
               if(this.saveDtl.isDisabled()){
                 //    System.out.println("in save mode");
                   this.treeTableEnt.setRowSelection("single");
                   AdfFacesContext.getCurrentInstance().addPartialTarget(this.treeTableEnt);
                   this.disablePanelTab(this.pannelTab,false);
                  
                   this.visibleFunction(AdfFacesContext.getCurrentInstance(), this.searchFormBind, false);
                   //    System.out.println("Calling Set DisABLED fUNCTION");
                   this.visibleFunction(AdfFacesContext.getCurrentInstance(), this.entityForm, false);
                   this.visibleFunction(AdfFacesContext.getCurrentInstance(),this.entitySubtypeForm, false);
                   if((!this.form_disable_Bank) || (!this.form_disable_pDtl) || (!this.form_disable_prf))
                      this.disablePanelTab(this.pannelTab,true);
               }else{
                   //    System.out.println("edit mode");
                   this.treeTableEnt.setRowSelection("none");
                   AdfFacesContext.getCurrentInstance().addPartialTarget(this.treeTableEnt);
                   this.disablePanelTab(this.pannelTab,true);
                   
                   this.visibleFunction(AdfFacesContext.getCurrentInstance(), this.searchFormBind, true);
                   
                   this.visibleFunction(AdfFacesContext.getCurrentInstance(), this.entityForm, true);
                   this.visibleFunction(AdfFacesContext.getCurrentInstance(),this.entitySubtypeForm, true);
                   }
        }
        return render;
    }
    
    
    private void visibleFunction(AdfFacesContext adfFacesContext, UIComponent component,Boolean visibilty) {
        List<UIComponent> items = component.getChildren();
        for (UIComponent item : items) {
          
        if(!(item instanceof RichPopup)){
               ////    System.out.println("in Child");
               visibleFunction(adfFacesContext, item,visibilty);
               
            }
            if (item instanceof RichInputText) {
                
                RichInputText input = (RichInputText)item;
                input.setDisabled(visibilty);
                AdfFacesContext.getCurrentInstance().addPartialTarget(input);
            
            } else if (item instanceof RichInputDate) {
                RichInputDate input = (RichInputDate)item;
                input.setDisabled(visibilty);
                AdfFacesContext.getCurrentInstance().addPartialTarget(input);
               
            } else if (item instanceof RichInputListOfValues) {
                RichInputListOfValues input = (RichInputListOfValues)item;
                          
              //    System.out.println("Disabling Inplut list of values");                 
            //Using XOR Operator
                if((input.isDisabled() ^ visibilty)){
                    input.setDisabled(visibilty);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(input);
                }
            }else if (item instanceof RichCommandImageLink){
               // //    System.out.println("RichCommandImageLink");
                RichCommandImageLink input = (RichCommandImageLink)item;
                ////    System.out.println(input.getAttributes());
                Object o=input.getAttributes().get("FattrValue");
                if(visibilty){
                    input.setDisabled(true);
                }else{
                 ////    System.out.println( " is called having f aatr value "+o);
                    if(o !=null){
                ////    System.out.println("Corresponding Class is "+o.getClass().getName().toString());
                 //      //    System.out.println("--------------------------------------------------------");
                 
                        if((Boolean)o){
                            ////    System.out.println("Disabiling");
                            input.setDisabled(true);  
                        }else{
                            input.setDisabled(false);  
                        } 
                    }else
                     input.setDisabled(visibilty);
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(input);
            }else if(item instanceof RichCommandButton){
                RichCommandButton input = (RichCommandButton)item;
//                //    System.out.println("Rich Button");
//                //    System.out.println(input.getAttributes());
                Object o=input.getAttributes().get("FattrValue");
                if(visibilty){
                    input.setDisabled(true);
                }else{
                  ////    System.out.println( " is called having f aatr value "+o);
                    if(o !=null){
                        ////    System.out.println("Corresponding Class is "+o.getClass().getName().toString());
                    ////    System.out.println("--------------------------------------------------------");
                        if((Boolean)o){
                            ////    System.out.println("Disabiling");
                             input.setDisabled(true);  
                        }else{
                            input.setDisabled(false); 
                        } 
                    }else
                     input.setDisabled(visibilty);
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(input);
            }

        }
    }

    public void setEntityForm(RichPanelGroupLayout entityForm) {
        this.entityForm = entityForm;
    }

    public RichPanelGroupLayout getEntityForm() {
        return entityForm;
    }

    public void setEntitySubtypeForm(RichPanelGroupLayout entitySubtypeForm) {
        this.entitySubtypeForm = entitySubtypeForm;
    }

    public RichPanelGroupLayout getEntitySubtypeForm() {
        return entitySubtypeForm;
    }

    public void setPannelTab(RichPanelTabbed pannelTab) {
        this.pannelTab = pannelTab;
    }

    public RichPanelTabbed getPannelTab() {
        return pannelTab;
    }
    
    public void disablePanelTab( RichPanelTabbed tab,Boolean disible){
        Iterator children=(tab.getChildren()).iterator();
        
        while(children.hasNext()){
            RichShowDetailItem child=(RichShowDetailItem) children.next();
           // //    System.out.println("Class of Child "+child.getClass().getName());
            if(disible){
                if(!child.isDisclosed()){
                    child.setDisabled(true);
                   
                } else{
        
                    child.setDisabled(false);
                   
                }
            }else{
            child.setDisabled(false); 
            
            }
        }
        
       if(this.treeTableEnt.getRowSelection().equals("single")) 
        AdfFacesContext.getCurrentInstance().addPartialTarget(tab);

    }


    public void setEoTypeEdit(RichSelectOneChoice eoTypeEdit) {
        this.eoTypeEdit = eoTypeEdit;
    }

    public RichSelectOneChoice getEoTypeEdit() {
        return eoTypeEdit;
    }

    public void setEditPrf(RichCommandImageLink editPrf) {
        this.editPrf = editPrf;
    }

    public RichCommandImageLink getEditPrf() {
        return editPrf;
    }

    public void setEditDtl(RichShowDetailItem editDtl) {
        this.editDtl = editDtl;
    }

    public RichShowDetailItem getEditDtl() {
        return editDtl;
    }

    public void setSavePrf(RichCommandImageLink savePrf) {
        this.savePrf = savePrf;
    }

    public RichCommandImageLink getSavePrf() {
        return savePrf;
    }

    public void setSaveBank(RichShowDetailItem saveBank) {
        this.saveBank = saveBank;
    }

    public RichShowDetailItem getSaveBank() {
        return saveBank;
    }

    public void setSaveDtl(RichCommandImageLink saveDtl) {
        this.saveDtl = saveDtl;
    }

    public RichCommandImageLink getSaveDtl() {
        return saveDtl;
    }

    public void setPrfTab(RichShowDetailItem prfTab) {
        this.prfTab = prfTab;
    }

    public RichShowDetailItem getPrfTab() {
        return prfTab;
    }

    public void setSaveBankLink(RichCommandImageLink saveBankLink) {
        this.saveBankLink = saveBankLink;
    }

    public RichCommandImageLink getSaveBankLink() {
        return saveBankLink;
    }

    public void setAddButton(RichCommandImageLink addButton) {
        this.addButton = addButton;
    }

    public RichCommandImageLink getAddButton() {
        return addButton;
    }

    public void setCurrDis(Boolean currDis) {
        this.currDis = currDis;
    }

    public Boolean getCurrDis() {
        return currDis;
    }
}
