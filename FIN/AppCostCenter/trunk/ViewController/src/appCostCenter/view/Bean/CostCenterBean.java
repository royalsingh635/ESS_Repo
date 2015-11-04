package appCostCenter.view.Bean;

//NEW
import appCostCenter.model.Module.AppCostCenterAMImpl;
import appCostCenter.model.View.AppCcPrfVORowImpl;
import appCostCenter.model.View.AppCcStructVORowImpl;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.event.QueryEvent;
import oracle.adf.view.rich.model.QueryDescriptor;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewObject;
import oracle.jbo.uicli.binding.JUSearchBindingCustomizer;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

//COMENT
public class CostCenterBean {
    private RichPopup profilePopup;
    private RichPopup structPopup;
    private RichPopup docPopup;
    private RichPopup deleteAllPopup;
    private String Mode = "V";
    private RichTable ccProfileTab;
    private RichTable ccDocTab;
    private RichTable ccStructTab;
    private RichInputDate startDate;
    private RichInputDate endDate;
    private RichSelectBooleanCheckbox ccDepndnt;
    private RichSelectOneChoice ccEntIdParentBind;
    private RichInputText structPos;
    private RichSelectOneChoice cmpIdBind;
    private Integer onloadVal;
    private RichSelectOneChoice refColIdPgBind;
    private RichTable defaultValueTabPgBind;
    private RichSelectOneChoice dfltColIdPgBind;


    public CostCenterBean() {
    }


    AppCostCenterAMImpl am = (AppCostCenterAMImpl) resolvElDC("AppCostCenterAMDataControl");
    private long count = 0;
    private long count1 = 0;

    private static int VARCHAR = Types.VARCHAR;

    public String addProfileAction() {

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Createwithparameters");
        operationBinding.execute();
        if (operationBinding.getErrors().isEmpty()) {
            showPopup(profilePopup, true);
            Mode = "A";
        }
        return null;
    }

    public String editProfileAction() {

        ViewObject profileViewObject = am.getAppCcPrf();
        AppCcPrfVORowImpl CostCenterProfile = (AppCcPrfVORowImpl) profileViewObject.getCurrentRow();

        if (CostCenterProfile.getAttribute("CcFinalize") != null) {
            //profileViewObject.getCurrentRow();
            String finalize = CostCenterProfile.getAttribute("CcFinalize").toString();
            if (finalize.equalsIgnoreCase("Y")) {
                FacesMessage Message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.138']}").toString());
                Message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage(null, Message);

            } else {
                showPopup(profilePopup, true);
                Mode = "E";

            }

        }
        return null;
    }

    /**
     * @return
     */
    public String addStructAction() {
        /*  ViewObject srtuctViewObject = am.getAppCcStruct();

        AppCcStructVORowImpl CostCenterStruct = (AppCcStructVORowImpl)srtuctViewObject.getCurrentRow();
        ViewObject profileViewObject = am.getAppCcPrf();
        AppCcPrfVORowImpl CostCenterProfile = (AppCcPrfVORowImpl)profileViewObject.getCurrentRow();
        Integer NoofComponent = CostCenterProfile.getCcNoOfComp();

        count = srtuctViewObject.getRowCount(); */


        ViewObject profileViewObject = am.getAppCcPrf();
        AppCcPrfVORowImpl CostCenterProfile = (AppCcPrfVORowImpl) profileViewObject.getCurrentRow();

        if (CostCenterProfile != null && CostCenterProfile.getAttribute("CcFinalize") != null) {
            // profileViewObject.getCurrentRow();
            String finalize = CostCenterProfile.getAttribute("CcFinalize").toString();
            if (finalize.equalsIgnoreCase("Y")) {
                FacesMessage Message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.136']}").toString());
                Message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage(null, Message);
            } else {
                ViewObject srtuctViewObject = am.getAppCcStruct();


                Integer NoofComponent = CostCenterProfile.getCcNoOfComp();
                count = srtuctViewObject.getRowCount();


                // Mode = "A";
                if (count < NoofComponent) {

                    BindingContainer bindings = getBindings();
                    OperationBinding operationBinding = bindings.getOperationBinding("Createwithparameters1");
                    operationBinding.execute();
                    if (operationBinding.getErrors().isEmpty()) {
                        showPopup(structPopup, true);
                        Mode = "A";
                    }
                } else {
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    FacesMessage fm =
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                         resolvElDCMsg("#{bundle['MSG.137']}").toString() + NoofComponent, "");

                    ctx.addMessage(null, fm);
                }
            }
        }
        return null;

    }
    public Integer Rownum = 0;

    public String editStructAction() {
        ViewObject profileViewObject = am.getAppCcPrf();
        AppCcPrfVORowImpl CostCenterProfile = (AppCcPrfVORowImpl) profileViewObject.getCurrentRow();
        // ViewObject profileViewObject = am.getAppCcPrf();
        // AppCcPrfVORowImpl CostCenterProfile = (AppCcPrfVORowImpl)profileViewObject.getCurrentRow();
        if (CostCenterProfile.getAttribute("CcFinalize") != null) {
            profileViewObject.getCurrentRow();
            String finalize = CostCenterProfile.getAttribute("CcFinalize").toString();

            if (finalize.equalsIgnoreCase("Y")) {
                FacesMessage Message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.139']}").toString());
                Message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage(null, Message);
            } else {

                showPopup(structPopup, true);
                Mode = "E";

            }

        }
        return null;
    }

    public String addDocAction() {
        ViewObject profileViewObject = am.getAppCcPrf();
        AppCcPrfVORowImpl CostCenterProfile = (AppCcPrfVORowImpl) profileViewObject.getCurrentRow();
        if (CostCenterProfile != null && CostCenterProfile.getAttribute("CcFinalize") != null) {

            String finalize = CostCenterProfile.getAttribute("CcFinalize").toString();
            if (finalize.equalsIgnoreCase("Y")) {
                FacesMessage Message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.141']}").toString());
                Message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage(null, Message);

            } else {
                //showPopup(profilePopup, true);
                //Mode = "A";


                BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("Createwithparameters2");
                operationBinding.execute();
                if (operationBinding.getErrors().isEmpty()) {
                    showPopup(docPopup, true);
                    Mode = "A";
                }
            }
        }
        return null;
    }

    public String editDocAction() {
        ViewObject profileViewObject = am.getAppCcPrf();
        AppCcPrfVORowImpl CostCenterProfile = (AppCcPrfVORowImpl) profileViewObject.getCurrentRow();
        if (CostCenterProfile.getAttribute("CcFinalize") != null) {
            profileViewObject.getCurrentRow();
            String finalize = CostCenterProfile.getAttribute("CcFinalize").toString();

            if (finalize.equalsIgnoreCase("Y")) {
                FacesMessage Message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.140']}").toString());
                Message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage(null, Message);
            } else {
                showPopup(docPopup, true);
                Mode = "E";

            }

        }
        return null;
    }

    public String deleteAllAction() {
        showPopup(deleteAllPopup, true);
        Mode = "D";
        return null;
    }

    public String deleteAll(Integer P_PRF_ID, String P_DEL_MODE) {
        return (String) callStoredFunction(STRING, "APP.PKG_APP.FN_DEL_CC_ALL(?,?)", new Object[] {
                                           P_PRF_ID, P_DEL_MODE });
    }

    public void deleteDialogListner(DialogEvent dialogEvent) {
        AppCostCenterAMImpl am = (AppCostCenterAMImpl) resolvElDC("AppCostCenterAMDataControl");
        ViewObject v1 = am.getAppCcPrf();

        if (dialogEvent.getOutcome().name().equals("yes")) {
            Row r1 = v1.getCurrentRow();
            Integer prfID = Integer.parseInt(r1.getAttribute("CcPrfId").toString());
            BindingContainer bindings = getBindings();
            DCIteratorBinding Iter = (DCIteratorBinding) bindings.get("AppCcPrfIterator");
            Key parentKey = Iter.getCurrentRow().getKey();
            String retVal = deleteAll(prfID, "C");

            if (retVal.equalsIgnoreCase("Y")) {
                am.getDBTransaction().commit();
                v1.executeQuery();
                String delmsg = resolvElDCMsg("#{bundle['MSG.142']}").toString();
                FacesMessage msg = new FacesMessage(delmsg);
                msg.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage(null, msg);
            } else {
                am.getDBTransaction().rollback();
                v1.executeQuery();
                Iter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                String delmsg = resolvElDCMsg("#{bundle['MSG.91']}").toString();
                FacesMessage msg = new FacesMessage(delmsg);
                msg.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage(null, msg);
            }
            BindingContainer binding = getBindings();
            OperationBinding createOpBAddress = binding.getOperationBinding("Execute");
            createOpBAddress.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(ccProfileTab);
        } else if (dialogEvent.getOutcome().name().equals("no")) {
        }
    }

    public void deletePopCancelListner(PopupCanceledEvent popupCanceledEvent) {

    }

    private static int STRING = Types.VARCHAR;

    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {
            AppCostCenterAMImpl am = (AppCostCenterAMImpl) resolvElDC("AppCostCenterAMDataControl");
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

    protected Object callProcedureCostCenter(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {
            AppCostCenterAMImpl am = (AppCostCenterAMImpl) resolvElDC("AppCostCenterAMDataControl");
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


    public static Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }

    public void profileDialogLIstener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {

            BindingContainer bindings = getBindings();
            if (Mode.equalsIgnoreCase("E")) {
                System.out.println(Mode);
                ViewObject profileViewObject = am.getAppCcPrf();
                ViewObject docViewObject = am.getAppCcDoc();
                ViewObject srtuctViewObject = am.getAppCcStruct();

                AppCcPrfVORowImpl costCenterProfile = (AppCcPrfVORowImpl) profileViewObject.getCurrentRow();
                DCIteratorBinding Iter = (DCIteratorBinding) bindings.get("AppCcPrfIterator");
                count = docViewObject.getEstimatedRowCount();
                count1 = srtuctViewObject.getEstimatedRowCount();
                Integer noOfComponent = (Integer) costCenterProfile.getAttribute("CcNoOfComp");
                System.out.println(noOfComponent);
                System.out.println(count1);
                if (count1 <= noOfComponent) {

                    if (costCenterProfile.getAttribute("CcFinalize") != null) {

                        String finalize = costCenterProfile.getAttribute("CcFinalize").toString();
                        System.out.println("ccFinalize not null");
                        if (finalize.equalsIgnoreCase("Y")) {
                            /*
                        */


                            if ((count > 0) && (count1 > 0)) {

                                System.out.println("count is ok");
                                Row currentRow = Iter.getCurrentRow();
                                Integer slocId = (Integer) currentRow.getAttribute("SlocId");
                                String cldId = (String) currentRow.getAttribute("CldId");
                                String orgId = (String) currentRow.getAttribute("OrgId");
                                String hoOrgId = (String) currentRow.getAttribute("HoOrgId");
                                Integer prfId = (Integer) currentRow.getAttribute("CcPrfId");
                                System.out.println("---->" + slocId + " " + cldId + " " + orgId + " " + hoOrgId + " " +
                                                   prfId);
                                String value = callStoredProcedureCostCenter("APP.PROC_GEN_COST_CENTER(?,?,?,?,?,?)", new Object[] {
                                                                             slocId, cldId, orgId, hoOrgId, prfId
                                }).toString();
                                System.out.println("==============>" + value);
                                if (value.equalsIgnoreCase("Y")) {

                                    OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                                    operationBinding.execute();
                                    if (operationBinding.getErrors().isEmpty()) {
                                        Key parentKey = Iter.getCurrentRow().getKey();
                                        OperationBinding createOpBAddress = bindings.getOperationBinding("Execute");
                                        createOpBAddress.execute();
                                        String delmsg = resolvElDCMsg("#{bundle['MSG.92']}").toString();
                                        FacesMessage msg = new FacesMessage(delmsg);
                                        msg.setSeverity(FacesMessage.SEVERITY_INFO);
                                        FacesContext ctx = FacesContext.getCurrentInstance();
                                        ctx.addMessage(null, msg);


                                        Iter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                                    }
                                } else {
                                    Key parentKey = Iter.getCurrentRow().getKey();
                                    OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
                                    operationBinding.execute();
                                    OperationBinding createOpBAddress = bindings.getOperationBinding("Execute");
                                    createOpBAddress.execute();

                                    Iter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                                    String delmsg = resolvElDCMsg("#{bundle['MSG.143']}").toString();
                                    FacesMessage msg = new FacesMessage(delmsg);
                                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext ctx = FacesContext.getCurrentInstance();
                                    ctx.addMessage(null, msg);
                                    profilePopup.hide();
                                }
                            } else {


                                String errmsg = resolvElDCMsg("#{bundle['MSG.144']}").toString();
                                FacesMessage msg = new FacesMessage(errmsg);
                                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext ctx = FacesContext.getCurrentInstance();
                                ctx.addMessage(null, msg);
                            }
                        }

                        else {

                            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                            operationBinding.execute();
                            if (operationBinding.getErrors().isEmpty()) {
                                Key parentKey = Iter.getCurrentRow().getKey();
                                OperationBinding createOpBAddress = bindings.getOperationBinding("Execute");
                                createOpBAddress.execute();
                                String delmsg = resolvElDCMsg("#{bundle['MSG.92']}").toString();
                                FacesMessage msg = new FacesMessage(delmsg);
                                msg.setSeverity(FacesMessage.SEVERITY_INFO);
                                FacesContext ctx = FacesContext.getCurrentInstance();
                                ctx.addMessage(null, msg);


                                Iter.setCurrentRowWithKey(parentKey.toStringFormat(true));

                            }


                        }

                    }
                } else {
                    String delmsg = resolvElDCMsg("#{bundle['MSG.145']}").toString();
                    FacesMessage msg = new FacesMessage(delmsg);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    ctx.addMessage(cmpIdBind.getClientId(), msg);
                    throw new ValidatorException(msg);

                }


            } else if (Mode.equalsIgnoreCase("A")) {
                /** This code is Edited by VK on 8 Mar 2012*/

                //ViewObject profileViewObject = am.getAppCcPrf();
                // AppCcPrfVORowImpl CostCenterProfile = (AppCcPrfVORowImpl)profileViewObject.getCurrentRow();
                // if (CostCenterProfile.getAttribute("CcFinalize") != null) {
                //     profileViewObject.getCurrentRow();
                //    String finalize = CostCenterProfile.getAttribute("CcFinalize").toString();

                /* if (finalize.equalsIgnoreCase("Y")) {
                    System.out.println(finalize+"<-----Finalised in add mode");
                        FacesMessage Message = new FacesMessage("Entity profile can't be created as Finalized ");
                        Message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext ctx = FacesContext.getCurrentInstance();
                        ctx.addMessage(null, Message);
                        showPopup(profilePopup, true);

                    }  */ //else {
                //System.out.println(finalize+"<-----not Finalised in add mode");
                OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                operationBinding.execute();
                BindingContainer binding = getBindings();
                OperationBinding createOpBAddress = binding.getOperationBinding("Execute");
                createOpBAddress.execute();
                if (operationBinding.getErrors().isEmpty()) {

                    String delmsg = resolvElDCMsg("#{bundle['MSG.91']}").toString();
                    FacesMessage msg = new FacesMessage(delmsg);
                    msg.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    ctx.addMessage(null, msg);
                }

                //}


                //}
            }
        } else if (dialogEvent.getOutcome().name().equals("cancel")) {
            BindingContainer bindings = getBindings();
            if (Mode.equalsIgnoreCase("E")) {
                DCIteratorBinding Iter = (DCIteratorBinding) bindings.get("AppCcPrfIterator");
                Key parentKey = Iter.getCurrentRow().getKey();
                OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
                operationBinding.execute();
                BindingContainer binding = getBindings();
                OperationBinding createOpBAddress = binding.getOperationBinding("Execute");
                createOpBAddress.execute();
                Iter.setCurrentRowWithKey(parentKey.toStringFormat(true));

            } else if (Mode.equalsIgnoreCase("A")) {
                OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
                operationBinding.execute();
                BindingContainer binding = getBindings();
                OperationBinding createOpBAddress = binding.getOperationBinding("Execute");
                createOpBAddress.execute();

            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(ccProfileTab);

        AdfFacesContext.getCurrentInstance().addPartialTarget(ccDocTab);

        AdfFacesContext.getCurrentInstance().addPartialTarget(ccStructTab);
    }

    public void profilePopupCancelListener(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer bindings = getBindings();
        if (Mode.equalsIgnoreCase("E")) {

            DCIteratorBinding Iter = (DCIteratorBinding) bindings.get("AppCcPrfIterator");
            Key parentKey = Iter.getCurrentRow().getKey();

            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            operationBinding.execute();

            OperationBinding createOpBAddress = bindings.getOperationBinding("Execute");
            createOpBAddress.execute();

            Iter.setCurrentRowWithKey(parentKey.toStringFormat(true));

        } else if (Mode.equalsIgnoreCase("A")) {
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            operationBinding.execute();
            BindingContainer binding = getBindings();
            OperationBinding createOpBAddress = binding.getOperationBinding("Execute");
            createOpBAddress.execute();

        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(ccProfileTab);

        AdfFacesContext.getCurrentInstance().addPartialTarget(ccDocTab);

        AdfFacesContext.getCurrentInstance().addPartialTarget(ccStructTab);
    }

    public void structDialogLIstener(DialogEvent dialogEvent) {

        if (dialogEvent.getOutcome().name().equals("ok")) {

            BindingContainer bindings = getBindings();

            ViewObject srtuctViewObject = am.getAppCcStruct();

            AppCcStructVORowImpl CostCenterStruct = (AppCcStructVORowImpl) srtuctViewObject.getCurrentRow();
            Integer ccPos = (Integer) CostCenterStruct.getAttribute("CcPos");
            String ccDepndnt = (String) CostCenterStruct.getAttribute("CcDepndnt");
            Long ParentId = (Long) CostCenterStruct.getAttribute("CcEntIdParent");
            Long entityId = (Long) CostCenterStruct.getAttribute("CcEntId");
            if (Mode.equalsIgnoreCase("E")) {

                DCIteratorBinding parentIter = (DCIteratorBinding) bindings.get("AppCcPrfIterator");
                DCIteratorBinding childIter = (DCIteratorBinding) bindings.get("AppCcStructIterator");
                Key parentKey = parentIter.getCurrentRow().getKey();
                Key childKey = childIter.getCurrentRow().getKey();


                if ((ccPos > 1) && (ccDepndnt.equalsIgnoreCase("Y"))) {
                    if ((ccDepndnt.equalsIgnoreCase("Y")) && (CostCenterStruct.getAttribute("CcEntIdParent") == null)) {
                        String delmsg = resolvElDCMsg("#{bundle['MSG.146']}").toString();
                        FacesMessage msg = new FacesMessage(delmsg);
                        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext ctx = FacesContext.getCurrentInstance();
                        ctx.addMessage(null, msg);

                    } else {


                        OperationBinding createOpBStruct = bindings.getOperationBinding("Execute1");
                        createOpBStruct.execute();

                        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                        childIter.setCurrentRowWithKey(childKey.toStringFormat(true));
                        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                        operationBinding.execute();
                        if (operationBinding.getErrors().isEmpty()) {
                            String delmsg = resolvElDCMsg("#{bundle['MSG.92']}").toString();
                            FacesMessage msg = new FacesMessage(delmsg);
                            msg.setSeverity(FacesMessage.SEVERITY_INFO);
                            FacesContext ctx = FacesContext.getCurrentInstance();
                            ctx.addMessage(null, msg);
                        }
                        OperationBinding createOpBNStruct = bindings.getOperationBinding("Execute1");
                        createOpBNStruct.execute();

                    }
                } else if (((ccPos > 1) && ccDepndnt.equalsIgnoreCase("N")) ||
                           ((ccPos <= 1) && ccDepndnt.equalsIgnoreCase("N"))) {
                    OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                    operationBinding.execute();

                    OperationBinding createOpBStruct = bindings.getOperationBinding("Execute1");
                    createOpBStruct.execute();

                    parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));

                    childIter.setCurrentRowWithKey(childKey.toStringFormat(true));


                    RowSetIterator iterator = srtuctViewObject.createRowSetIterator(null);
                    // iterator.reset();
                    while (iterator.hasNext()) {
                        System.out.println(ccPos);
                        Row curr = iterator.next();
                        Object entObj = curr.getAttribute("CcEntIdParent");
                        //System.out.println("entobj---"+entObj);
                        if (entObj != null) {
                            Row[] svo = srtuctViewObject.getFilteredRows("CcEntId", (Long) entObj);
                            //System.out.println("len--"+svo.length);
                            System.out.println(ccPos);
                            if (svo.length == 0) {
                                curr.remove();

                            }

                        }

                    }
                    OperationBinding operationBindin = bindings.getOperationBinding("Commit");
                    operationBindin.execute();
                    if (operationBindin.getErrors().isEmpty()) {
                        // String delmsg = saveMessage(16);
                        String delmsg = resolvElDCMsg("#{bundle['MSG.92']}").toString();
                        FacesMessage msg = new FacesMessage(delmsg);
                        msg.setSeverity(FacesMessage.SEVERITY_INFO);
                        FacesContext ctx = FacesContext.getCurrentInstance();
                        ctx.addMessage(null, msg);
                    }
                    iterator.closeRowSetIterator();
                } else {
                    String delmsg = resolvElDCMsg("#{bundle['MSG.147']}").toString();
                    FacesMessage msg = new FacesMessage(delmsg);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    ctx.addMessage(null, msg);

                }


                /*  OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                operationBinding.execute();

                OperationBinding createOpBAddress = bindings.getOperationBinding("Execute1");
                createOpBAddress.execute();

                parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));

                childIter.setCurrentRowWithKey(childKey.toStringFormat(true));


                // String delmsg = saveMessage(16);
                String delmsg = "Record updated successfully";
                FacesMessage msg = new FacesMessage(delmsg);
                msg.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage(null, msg); */

            } else if (Mode.equalsIgnoreCase("A")) {
                if ((ccPos > 1) && (ccDepndnt.equalsIgnoreCase("Y"))) {
                    if ((ccDepndnt.equalsIgnoreCase("Y")) && (ParentId == null)) {
                        String delmsg = resolvElDCMsg("#{bundle['MSG.146']}").toString();
                        FacesMessage msg = new FacesMessage(delmsg);
                        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext ctx = FacesContext.getCurrentInstance();
                        ctx.addMessage(null, msg);

                    } else {

                        DCIteratorBinding parentIter = (DCIteratorBinding) bindings.get("AppCcPrfIterator");

                        Key parentKey = parentIter.getCurrentRow().getKey();

                        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                        operationBinding.execute();

                        OperationBinding createOpBStruct = bindings.getOperationBinding("Execute1");
                        createOpBStruct.execute();

                        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));

                        //String delmsg = saveMessage(15);
                        String delmsg = resolvElDCMsg("#{bundle['MSG.91']}").toString();
                        FacesMessage msg = new FacesMessage(delmsg);
                        msg.setSeverity(FacesMessage.SEVERITY_INFO);
                        FacesContext ctx = FacesContext.getCurrentInstance();
                        ctx.addMessage(null, msg);
                    }

                } else if (((ccPos > 1) && ccDepndnt.equalsIgnoreCase("N")) ||
                           ((ccPos <= 1) && ccDepndnt.equalsIgnoreCase("N"))) {
                    DCIteratorBinding parentIter = (DCIteratorBinding) bindings.get("AppCcPrfIterator");

                    Key parentKey = parentIter.getCurrentRow().getKey();

                    OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                    operationBinding.execute();

                    OperationBinding createOpBStruct = bindings.getOperationBinding("Execute1");
                    createOpBStruct.execute();

                    parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));

                    //String delmsg = saveMessage(15);
                    String delmsg = resolvElDCMsg("#{bundle['MSG.91']}").toString();
                    FacesMessage msg = new FacesMessage(delmsg);
                    msg.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    ctx.addMessage(null, msg);


                } else {
                    String delmsg = resolvElDCMsg("#{bundle['MSG.147']}").toString();
                    FacesMessage msg = new FacesMessage(delmsg);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    ctx.addMessage(null, msg);

                }

            }


        } else if (dialogEvent.getOutcome().name().equals("cancel")) {

            BindingContainer bindings = getBindings();
            if (Mode.equalsIgnoreCase("E")) {


                DCIteratorBinding parentIter = (DCIteratorBinding) bindings.get("AppCcPrfIterator");

                DCIteratorBinding childIter = (DCIteratorBinding) bindings.get("AppCcStructIterator");

                Key parentKey = parentIter.getCurrentRow().getKey();

                Key childKey = childIter.getCurrentRow().getKey();


                OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
                operationBinding.execute();

                OperationBinding createOpBStruct = bindings.getOperationBinding("Execute1");
                createOpBStruct.execute();

                parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));

                childIter.setCurrentRowWithKey(childKey.toStringFormat(true));

            }

            else if (Mode.equalsIgnoreCase("A")) {

                DCIteratorBinding parentIter = (DCIteratorBinding) bindings.get("AppCcPrfIterator");

                Key parentKey = parentIter.getCurrentRow().getKey();

                OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
                operationBinding.execute();

                OperationBinding createOpBStruct = bindings.getOperationBinding("Execute1");
                createOpBStruct.execute();

                parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));

            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(ccStructTab);
        AdfFacesContext.getCurrentInstance().addPartialTarget(ccProfileTab);
    }

    public void structPopupCancelListener(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer bindings = getBindings();
        if (Mode.equalsIgnoreCase("E")) {


            DCIteratorBinding parentIter = (DCIteratorBinding) bindings.get("AppCcPrfIterator");

            DCIteratorBinding childIter = (DCIteratorBinding) bindings.get("AppCcStructIterator");

            Key parentKey = parentIter.getCurrentRow().getKey();

            Key childKey = childIter.getCurrentRow().getKey();


            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            operationBinding.execute();

            OperationBinding createOpBStruct = bindings.getOperationBinding("Execute1");
            createOpBStruct.execute();

            parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));

            childIter.setCurrentRowWithKey(childKey.toStringFormat(true));


        }

        else if (Mode.equalsIgnoreCase("A")) {

            DCIteratorBinding parentIter = (DCIteratorBinding) bindings.get("AppCcPrfIterator");

            Key parentKey = parentIter.getCurrentRow().getKey();

            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            operationBinding.execute();

            OperationBinding createOpBStruct = bindings.getOperationBinding("Execute1");
            createOpBStruct.execute();

            parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));


        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(ccStructTab);
        AdfFacesContext.getCurrentInstance().addPartialTarget(ccProfileTab);
    }

    public void doctDialogLIstener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            BindingContainer bindings = getBindings();
            if (Mode.equalsIgnoreCase("E")) {


                DCIteratorBinding parentIter = (DCIteratorBinding) bindings.get("AppCcPrfIterator");

                DCIteratorBinding childIter = (DCIteratorBinding) bindings.get("AppCcDocIterator");

                Key parentKey = parentIter.getCurrentRow().getKey();
                System.out.println(parentKey);

                Key childKey = childIter.getCurrentRow().getKey();
                System.out.println(childKey);


                OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                operationBinding.execute();

                OperationBinding createOpBStruct = bindings.getOperationBinding("Execute2");
                createOpBStruct.execute();

                parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));

                childIter.setCurrentRowWithKey(childKey.toStringFormat(true));


                //String delmsg = saveMessage(16);
                String delmsg = resolvElDCMsg("#{bundle['MSG.92']}").toString();
                FacesMessage msg = new FacesMessage(delmsg);
                msg.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage(null, msg);
            }

            else if (Mode.equalsIgnoreCase("A")) {

                DCIteratorBinding parentIter = (DCIteratorBinding) bindings.get("AppCcPrfIterator");

                Key parentKey = parentIter.getCurrentRow().getKey();

                OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                operationBinding.execute();

                OperationBinding operationB = bindings.getOperationBinding("Rollback");
                operationB.execute();

                OperationBinding createOpBStruct = bindings.getOperationBinding("Execute2");
                createOpBStruct.execute();

                parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));


                //String delmsg = saveMessage(15);
                String delmsg = resolvElDCMsg("#{bundle['MSG.91']}").toString();
                FacesMessage msg = new FacesMessage(delmsg);
                msg.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage(null, msg);
            }

        } else if (dialogEvent.getOutcome().name().equals("cancel")) {
            BindingContainer bindings = getBindings();
            if (Mode.equalsIgnoreCase("E")) {


                DCIteratorBinding parentIter = (DCIteratorBinding) bindings.get("AppCcPrfIterator");

                DCIteratorBinding childIter = (DCIteratorBinding) bindings.get("AppCcDocIterator");

                Key parentKey = parentIter.getCurrentRow().getKey();

                Key childKey = childIter.getCurrentRow().getKey();


                OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
                operationBinding.execute();

                OperationBinding createOpBStruct = bindings.getOperationBinding("Execute2");
                createOpBStruct.execute();

                parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));

                childIter.setCurrentRowWithKey(childKey.toStringFormat(true));


            }

            else if (Mode.equalsIgnoreCase("A")) {

                DCIteratorBinding parentIter = (DCIteratorBinding) bindings.get("AppCcPrfIterator");

                Key parentKey = parentIter.getCurrentRow().getKey();

                OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
                operationBinding.execute();

                OperationBinding createOpBStruct = bindings.getOperationBinding("Execute2");
                createOpBStruct.execute();

                parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));


            }

        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(ccDocTab);
        AdfFacesContext.getCurrentInstance().addPartialTarget(ccProfileTab);
    }

    public void docPopupCancelListener(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer bindings = getBindings();
        if (Mode.equalsIgnoreCase("E")) {


            DCIteratorBinding parentIter = (DCIteratorBinding) bindings.get("AppCcPrfIterator");

            DCIteratorBinding childIter = (DCIteratorBinding) bindings.get("AppCcDocIterator");

            Key parentKey = parentIter.getCurrentRow().getKey();

            Key childKey = childIter.getCurrentRow().getKey();


            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            operationBinding.execute();

            OperationBinding createOpBStruct = bindings.getOperationBinding("Execute2");
            createOpBStruct.execute();

            parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));

            childIter.setCurrentRowWithKey(childKey.toStringFormat(true));

        }

        else if (Mode.equalsIgnoreCase("A")) {

            DCIteratorBinding parentIter = (DCIteratorBinding) bindings.get("AppCcPrfIterator");

            Key parentKey = parentIter.getCurrentRow().getKey();

            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            operationBinding.execute();

            OperationBinding createOpBStruct = bindings.getOperationBinding("Execute2");
            createOpBStruct.execute();

            parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));

        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(ccDocTab);
        AdfFacesContext.getCurrentInstance().addPartialTarget(ccProfileTab);
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }


    public void showPopup(RichPopup popup, boolean visible) {
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

    public void setProfilePopup(RichPopup profilePopup) {
        this.profilePopup = profilePopup;
    }

    public RichPopup getProfilePopup() {
        return profilePopup;
    }


    public void setStructPopup(RichPopup structPopup) {
        this.structPopup = structPopup;
    }

    public RichPopup getStructPopup() {
        return structPopup;
    }


    public void setDocPopup(RichPopup docPopup) {
        this.docPopup = docPopup;
    }

    public RichPopup getDocPopup() {
        return docPopup;
    }


    public void setDeleteAllPopup(RichPopup deleteAllPopup) {
        this.deleteAllPopup = deleteAllPopup;
    }

    public RichPopup getDeleteAllPopup() {
        return deleteAllPopup;
    }


    public void ccProfileNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null) {
            String nameDesc = object.toString();

            char[] xx = nameDesc.toCharArray();
            int i = xx.length;
            if (xx[xx.length - 1] == ' ' || xx[0] == ' ') {
                String msg2 = resolvElDCMsg("#{bundle['MSG.148']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
        }
        if (object != null) {
            String nameDesc = object.toString();
            int openB = 0;
            int closeB = 0;
            boolean closeFg = false;

            char[] xx = nameDesc.toCharArray();

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

            //2.if openB=0 then no. of closing and opening brackets equal
            //3.opening bracket must always come before closing brackets
            //4.closing brackets must not come before first occurrence of openning bracket
            if (openB != closeB || closeFg == true || (nameDesc.lastIndexOf("(") > nameDesc.lastIndexOf(")")) ||
                (nameDesc.indexOf(")") < nameDesc.indexOf("("))) {
                String msg2 = resolvElDCMsg("#{bundle['MSG.7']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }

            //5.check for empty brackets
            if (nameDesc.contains("()")) {
                String msg2 = resolvElDCMsg("#{bundle['MSG.49']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            //check for wrong combo
            if (nameDesc.contains("(.") || nameDesc.contains("(-") || nameDesc.contains("-)")) {
                String msg2 = resolvElDCMsg("#{bundle['MSG.149']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }

            openB = 0;
            closeB = 0;
            closeFg = false;


            String expression =
                "^(?:(?>[A-Za-z0-9 )]+)(?:\\_|\\-|\\(|\\.|\\)|\\:|\\@|\\/|\\\\(?!\\_|\\-|\\:|\\@|\\/|\\\\|$))?)*$";


            CharSequence inputStr = nameDesc;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String error = resolvElDCMsg("#{bundle['MSG.151']}").toString();

            if (matcher.matches()) {
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
            }
        }
    }

    public void setCcProfileTab(RichTable ccProfileTab) {
        this.ccProfileTab = ccProfileTab;
    }

    public RichTable getCcProfileTab() {
        return ccProfileTab;
    }

    public void setCcDocTab(RichTable ccDocTab) {
        this.ccDocTab = ccDocTab;
    }

    public RichTable getCcDocTab() {
        return ccDocTab;
    }

    public void setCcStructTab(RichTable ccStructTab) {
        this.ccStructTab = ccStructTab;
    }

    public RichTable getCcStructTab() {
        return ccStructTab;
    }

    public void CCSEntityValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String str = null;
        ViewObject srtuctViewObject = am.getAppCcStruct();
        AppCcStructVORowImpl CostCenterStruct = (AppCcStructVORowImpl) srtuctViewObject.getCurrentRow();
        try {
            String dpndnt = CostCenterStruct.getCcDepndnt();
            Integer position = CostCenterStruct.getCcPos();
            Long structEntId = CostCenterStruct.getCcEntId();
            Long parentEntId = CostCenterStruct.getCcEntIdParent();
            int ccs_EntId = (Integer) object;

            if (position == 1) {
                //Msg for this position Entity can't be dependent
                CostCenterStruct.setCcDepndnt("N");
                FacesMessage Message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.150']}").toString());
                Message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(Message);

            }
        } catch (ClassCastException cce) {
            str = resolvElDCMsg("#{bundle['MSG.152']}").toString();
        } catch (NullPointerException npe) {
            str = resolvElDCMsg("#{bundle['MSG.153']}").toString();
        } finally {
            if (str != null) {
                FacesMessage Message = new FacesMessage(str);
                Message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, Message);
            }
        }
    }

    public void CcDependentValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null && structPos.getValue() != null) {


            Boolean dpndnt = (Boolean) object;
            Integer position = (Integer) structPos.getValue();
            if (position == 1 && dpndnt.TRUE) {
                //Msg for this position Entity can't be dependent
                FacesMessage Message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.150']}").toString());
                Message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(Message);
            }
        }
    }
    //   public String isFinalize = "N";

    protected String callStoredProcedureCostCenter(String stmt, Object[] bindVars) {
        System.out.println("Procedure");
        String isFinalize = "N";
        CallableStatement st = null;
        try {
            /** 1. Create a JDBC CallabledStatement */
            System.out.println("try Block");

            st = am.getDBTransaction().createCallableStatement("begin " + stmt + ";end;", 0);
            System.out.println("registering values/parameters");
            /** 2. Register the first bind variable for the return
            value. and last 2 variable for output variable of function. */
            st.setObject(1, bindVars[0]);
            System.out.println("1.reg");
            st.setObject(2, bindVars[1]);
            System.out.println("2.reg");
            st.setObject(3, bindVars[2]);
            System.out.println("3.reg");
            st.setObject(4, bindVars[3]);
            System.out.println("4.reg");
            st.setObject(5, bindVars[4]);
            System.out.println("5.reg");
            st.registerOutParameter(6, Types.VARCHAR);
            System.out.println("6.reg");
            st.executeUpdate();
            /** 5. Set the value of user-supplied bind vars in the stmt */
            System.out.println("before null");
            if (st.getObject(6) != null) {
                isFinalize = (String) st.getObject(6);
                System.out.println("isFinalize" + isFinalize);
                //return isFinalize;
                if (isFinalize.equalsIgnoreCase("Y")) {
                    System.out.println("out--" + isFinalize);

                }

            } else {
                System.out.println("NULL returned");
            }


        } catch (Exception e) {
            e.printStackTrace();
            int end = e.getMessage().indexOf("\n");
            String Msg = resolvElDCMsg("#{bundle['MSG.154']}").toString();
            FacesMessage Message = new FacesMessage(Msg);
            Message.setSeverity(FacesMessage.SEVERITY_ERROR);
            //throw new JboException(e.getMessage());
        } finally {
            if (st != null) {
                try {
                    /** 7. Close the statement */
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return isFinalize;
    }


    public void setMode(String Mode) {
        this.Mode = Mode;
    }

    public String getMode() {
        return Mode;
    }

    public void setStartDate(RichInputDate startDate) {
        this.startDate = startDate;
    }

    public RichInputDate getStartDate() {
        return startDate;
    }

    public void setEndDate(RichInputDate endDate) {
        this.endDate = endDate;
    }

    public RichInputDate getEndDate() {
        return endDate;
    }

    public void setCcDepndnt(RichSelectBooleanCheckbox ccDepndnt) {
        this.ccDepndnt = ccDepndnt;
    }

    public RichSelectBooleanCheckbox getCcDepndnt() {
        return ccDepndnt;
    }


    public void setCcEntIdParentBind(RichSelectOneChoice ccEntIdParentBind) {
        this.ccEntIdParentBind = ccEntIdParentBind;
    }

    public RichSelectOneChoice getCcEntIdParentBind() {
        return ccEntIdParentBind;
    }

    public void setStructPos(RichInputText structPos) {
        this.structPos = structPos;
    }

    public RichInputText getStructPos() {
        return structPos;
    }

    public void ccFinalised(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Boolean val = (Boolean) object;
            if (Mode.equalsIgnoreCase("A"))
                if (val.equals(Boolean.TRUE)) {
                    FacesMessage Message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1921']}").toString());
                    Message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(Message);
                }
        }
    }

    public void setCmpIdBind(RichSelectOneChoice cmpIdBind) {
        this.cmpIdBind = cmpIdBind;
    }

    public RichSelectOneChoice getCmpIdBind() {
        return cmpIdBind;
    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    /*
     * programatically handling of query and criteria
     *
     */

    /* public void onSearch(QueryEvent queryEvent) {

        String Field1 = null;
        String Field2 = null;
        Date Field3 = null;
        Date Field4 = null;
        Boolean isValid = true;
        // Query Event is delivered when a query action is triggered
        try {
            System.out.println("try");
            QueryDescriptor qd = queryEvent.getDescriptor();

            // This line will represent group of criterion objects
            ConjunctionCriterion conCrit = qd.getConjunctionCriterion();

            //access the list of all search fields
            List<Criterion> criterionList = conCrit.getCriterionList();
          System.out.println("CRITERIA LIST--"+criterionList.size());
            for (Criterion criterion : criterionList) {
                AttributeDescriptor attrDescriptor = ((AttributeCriterion)criterion).getAttribute();
                /* Boolean A = ((AttributeCriterion)criterion).getValues().get(0)!=null;
                Boolean B = ((AttributeCriterion)criterion).getValues().get(1)!=null;
                Boolean C = ((AttributeCriterion)criterion).getValues().get(2)!=null;
                Boolean D =  ((AttributeCriterion)criterion).getValues().get(3)!=null;
                Integer s=((AttributeCriterion)criterion).getValues().size();
                System.out.println("SIZE OF CRITERIA---"+s);
                if((attrDescriptor.getName().equalsIgnoreCase("CcNoOfComp"))||(attrDescriptor.getName().equalsIgnoreCase("CcPrfDesc"))||(attrDescriptor.getName().equalsIgnoreCase("CcPrfEffStdt")) ||
                                                                                                                                          (attrDescriptor.getName().equalsIgnoreCase("CcPrfEffEndt"))){
                    if(attrDescriptor.getName().equalsIgnoreCase("CcNoOfComp")){
                     if(((AttributeCriterion)criterion).getValues().get(0)!=null){
                        Field1=((AttributeCriterion)criterion).getValues().get(0).toString();
                     }
                    }

                    if(attrDescriptor.getName().equalsIgnoreCase("CcPrfDesc")){
                     if(((AttributeCriterion)criterion).getValues().get(0)!=null){
                        Field2=((AttributeCriterion)criterion).getValues().get(0).toString();
                     }
                    }

                    if(attrDescriptor.getName().equalsIgnoreCase("CcPrfEffStdt")){
                     if(((AttributeCriterion)criterion).getValues().get(0)!=null){
                        Field3=(Date)((AttributeCriterion)criterion).getValues().get(0);
                     }
                    }

                    if(attrDescriptor.getName().equalsIgnoreCase("CcPrfEffEndt")){
                     if(((AttributeCriterion)criterion).getValues().get(0)!=null){
                        Field4=(Date)((AttributeCriterion)criterion).getValues().get(0);
                     }
                    }



                }else{


                }
            }
                System.out.println("Field1 :" + Field1);
                System.out.println("Field2 :" + Field2+"--F3---"+Field3+"---F4--"+Field4);
                if(Field1!=null || Field2 !=null || Field3!=null || Field4!=null){
                    isValid=true;
                }
                if(Field3.compareTo(Field4)==1){
                 isValid=false;
                 System.out.println("Invalid Date");
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Invalid Date","Invalid Date"));
                }

                if (isValid) {


                    DCBindingContainer bc = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
                    ViewCriteria vc = getViewCriteria(bc, qd);


                    System.out.println("View Object" + vc.getViewObject());

                    //Execute the query Listener using EL. This will execute the query component .If u see the exp , this was initially applied to QueryListener.. Later we assigned QueryListener to our custom method.

                    invokeMethodExpression("#{bindings.AppCcPrfVOCriteria1Query.processQuery}", queryEvent);
                } else {

                }

        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
    }          */


    //helper method to execute the QueryListener EL

    private void invokeMethodExpression(String expr, QueryEvent queryEvent) {
        FacesContext fctx = FacesContext.getCurrentInstance();
        ELContext elContext = fctx.getELContext();
        ExpressionFactory eFactory = fctx.getApplication().getExpressionFactory();
        MethodExpression mexpr = eFactory.createMethodExpression(elContext, expr, Object.class, new Class[] {
                                                                 QueryEvent.class });
        mexpr.invoke(elContext, new Object[] { queryEvent });
    }


    private ViewCriteria getViewCriteria(DCBindingContainer bc, QueryDescriptor qd) {

        Object execBinding = bc.findExecutableBinding("AppCcPrfVOCriteria1Query");
        // This will be seen in the page executable section as we have dropped for af:query
        ViewCriteria vc = JUSearchBindingCustomizer.getViewCriteria((DCBindingContainer) execBinding, qd.getName());
        System.out.println(vc);
        return vc;

    }

    public void setOnloadVal(Integer onloadVal) {
        this.onloadVal = onloadVal;
    }

    public Integer getOnloadVal() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("on_load_page");
        Object ret = operationBinding.execute();

        return (Integer) ret;
    }

    public void searchPrfBtnAL(ActionEvent actionEvent) {
        getBindings().getOperationBinding("searchPrf").execute();
    }

    public void resetPrfBtnAL(ActionEvent actionEvent) {
        getBindings().getOperationBinding("resetPrfSrch").execute();
    }

    public void mapColumnsDL(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equalsIgnoreCase("Ok")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();

            String delmsg = "Record saved successfully";
            FacesMessage msg = new FacesMessage(delmsg);
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, msg);
        }
    }

    public void mapColumnPopupCL(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();

    }

    public void tableNameVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(refColIdPgBind);
        }
    }

    public void setRefColIdPgBind(RichSelectOneChoice refColIdPgBind) {
        this.refColIdPgBind = refColIdPgBind;
    }

    public RichSelectOneChoice getRefColIdPgBind() {
        return refColIdPgBind;
    }

    public void defaultValDL(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equalsIgnoreCase("Ok")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();

            String delmsg = "Record saved successfully";
            FacesMessage msg = new FacesMessage(delmsg);
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, msg);
        }
    }

    public void defaultValueCL(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
    }

    public void setDefaultValueTabPgBind(RichTable defaultValueTabPgBind) {
        this.defaultValueTabPgBind = defaultValueTabPgBind;
    }

    public RichTable getDefaultValueTabPgBind() {
        return defaultValueTabPgBind;
    }

    public void columnNameVCL(ValueChangeEvent vce) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(defaultValueTabPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(dfltColIdPgBind);
    }

    public void dfltValueVCL(ValueChangeEvent vce) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(defaultValueTabPgBind);
    }

    public void setDfltColIdPgBind(RichSelectOneChoice dfltColIdPgBind) {
        this.dfltColIdPgBind = dfltColIdPgBind;
    }

    public RichSelectOneChoice getDfltColIdPgBind() {
        return dfltColIdPgBind;
    }
}


