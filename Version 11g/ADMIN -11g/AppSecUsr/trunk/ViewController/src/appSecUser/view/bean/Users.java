package appSecUser.view.bean;

import appSecUser.model.module.AppUsersAMImpl;

import appSecUser.view.bean.ADFUtil;

import appSecUser.model.view.AppSecUsrVOImpl;

import appSecUser.model.view.AppSecUsrVORowImpl;

import appSecUser.model.view.WarehouseVOImpl;

import appSecUser.view.utill.WeblogicUser;

import java.io.IOException;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.component.rich.output.RichMessage;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.event.AttributeChangeEvent;
import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.util.Service;

import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCControlBinding;
import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.event.DialogListener;

import oracle.jbo.JboException;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;

public class Users {
    private RichPopup createPopup;
    private RichTable mailIdtable;
    private RichPanelFormLayout panelFormAdd;
    /* private static boolean create_button = false;
    private static boolean edit_button = false;
    */
    private static boolean save_button = true;
    private static boolean cancel_button = true;
    private static boolean create_detail = false;
    private static boolean delete_detail = false;
    private static boolean form_readonly = true;
    private static boolean form_readonlyRole = true;
    private static boolean edit_form_prf = false;
    private static boolean create_button_org = false;
    private static boolean edit_button_org = false;
    private static boolean save_button_org = true;
    private static boolean cancel_button_org = true;
    private static boolean create_detail_org = false;
    private static boolean delete_detail_org = false;
    private static boolean form_readonly_org = true;
    private static boolean form_readonlyRole_org = true;
    private static boolean save_form_prf = true;
    private static boolean create_form_prf = true;
    private static boolean form_readonlyRoleUsrId = true;
    private RichPopup deletePopUpMailId;
    private RichPopup deletePopUpGrPLink;
    private RichPopup deletePopUpRole;
    private static Integer count;
    private Boolean isRenderPage;
    private RichMessage messageRender;
    private RichPanelGroupLayout pageRender;

    private RichInputText usrIdBinding;
    private String mode = "O";
    private String flag = "f";
    private RichInputText newPassBindVar;
    private RichInputText usrName;
    private RichInputText getUserNameTrans;
    private RichCommandButton getUserTrans;
    private RichInputListOfValues getUserBindVar;
    private String userMode = "V";
    private RichCommandImageLink childSaveLinkBVar;
    private RichCommandImageLink childCancelLinkBVar;
    private RichInputListOfValues getUserOrgBindVar;
    private RichInputText getUsrOrgNameBindVar;
    private RichSelectOneChoice usrOrgBinding;
    private RichSelectOneChoice usrOrgRoleBindingVar;
    private RichSelectOneChoice usrRoleidBindingVar;
    private RichSelectOneChoice usrPrfBindingVar;
    private RichSelectBooleanCheckbox activeUsr;
    private RichCommandImageLink editButtonBind;

    private boolean cancel_prf = true;
    private RichPanelFormLayout panelFormPrfAdd;
    private RichTable searchTableBind;
    private RichCommandImageLink addPrfBindVar;
    private RichShowDetailItem mailIdBind;
    private RichShowDetailItem organisationBindVar;
    private RichShowDetailItem roleBindVar;

    Integer validateFalag = 0;

    // this variable is used to disble all other fields except one on which add is clicked.For top add button addId=1,Middle Add Button addID=2 and for last one addID=0
    private int addId = 0;
    AppUsersAMImpl am = (AppUsersAMImpl)resolvElDC("AppUsersAMDataControl");
    private RichSelectBooleanCheckbox defaultEmail;
    private RichInputText emailId;

    ArrayList<String> al = new ArrayList<String>();
    private RichInputText whId;
    private RichSelectBooleanCheckbox checkBox;
    private RichPopup whPop;
    private RichInputText searchWarehouse;
    private RichTable warehouseTb;

    private String usrNameEdit = "";
    private String usrAlias = "";
    private RichInputText userAlias;
    private RichInputText userName;
    private RichInputText userNameAlias;
    private RichTable warehouseTableBind;
    private Boolean isWeblogicConfigured = false;

    public Users() {
        //count=(Integer)getBindings().getOperationBinding("on_load_page").execute();

        try {
            this.createConnection();
            isWeblogicConfigured = true;
            this.closeConnection();

        } catch (WeblogicStart e) {
            FacesContext.getCurrentInstance().addMessage(null,
                                                         new FacesMessage(FacesMessage.SEVERITY_ERROR, "Start Your Admin Server",
                                                                          null));
            isWeblogicConfigured = false;

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                                                         new FacesMessage(FacesMessage.SEVERITY_ERROR, "Somthing Went Wrong !! Contact ESS",
                                                                          null));
            isWeblogicConfigured = false;
        }


    }


    static {
        count = (Integer)getBindings().getOperationBinding("on_load_page").execute();
        //count=1;
        //System.out.println(count);
        //System.out.println("static block");
    }

    public static void setCount(Integer count) {
        Users.count = count;
    }

    public static Integer getCount() {
        return count;
    }

    public void setIsRenderPage(Boolean isRenderPage) {
        this.isRenderPage = isRenderPage;
    }

    public Boolean getIsRenderPage() {
        if (count == 1) {
            return false;
        } else {
            return true;
        }
    }

    public void setCreatePopup(RichPopup createPopup) {
        this.createPopup = createPopup;
    }

    public RichPopup getCreatePopup() {
        return createPopup;
    }

    public void createPopupCancelListener(PopupCanceledEvent popupCanceledEvent) {

        //this.create_form_prf=true;


        flag = "f";

        this.edit_form_prf = false;
        this.create_form_prf = true;
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();

        //        OperationBinding operationBindings = bindings.getOperationBinding("Execute");
        //        operationBindings.execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(panelFormAdd);
        AdfFacesContext.getCurrentInstance().addPartialTarget(panelFormPrfAdd);
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(editButtonBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(mailIdBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(organisationBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(roleBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(addPrfBindVar);
        searchTableBind.processUpdates(FacesContext.getCurrentInstance());
        this.edit_form_prf = false;


        //You can add your operation code here, i have used simple Cancel operation with Rollback and    Execute


        // this.cancel_prf=true;

    }

    public void createDialogLIstener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {

            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();

            if (operationBinding.getErrors().isEmpty()) {


                BindingContainer bindings1 = getBindings();
                OperationBinding operationBinding1 = bindings1.getOperationBinding("Execute");
                operationBinding1.execute();

                FacesMessage message = new FacesMessage("Record Saved successfully.");
                message.setSeverity(FacesMessage.SEVERITY_INFO);

            }


        }


    }

    public String createButton() {

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert6");
        operationBinding.execute();

        flag = "f";
        this.edit_form_prf = true;
        this.save_form_prf = true;


        showPopup(createPopup, true);

        return null;
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


    /**
     *
     **/
    public static BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String editUserButton() {
        flag = "t";
        //System.out.println("edit btnm clicked = "+flag);

        this.usrNameEdit = (String)this.getUserName().getValue();
        this.usrAlias = (String)this.userAlias.getValue();


        showPopup(createPopup, true);
        return null;


    }

    public void addMailid(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert1");
        operationBinding.execute();
        //this.create_button = true;
        this.create_detail = true;
        this.save_form_prf = true;
        // this.edit_button = true;
        this.delete_detail = true;
        this.save_button = false;
        this.cancel_button = true;
        this.cancel_prf = false;
        this.form_readonlyRole_org = true;
        this.save_button_org = true;
        this.create_detail_org = false;
        this.create_button_org = false;
        this.edit_button_org = false;
        this.delete_detail_org = false;
        this.cancel_button_org = false;
        this.validateFalag = 0;
    }

    public void saveMailId(ActionEvent actionEvent) {
        try {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();
            BindingContainer bindings1 = getBindings();
            OperationBinding operationBinding1 = bindings1.getOperationBinding("Execute1");
            operationBinding1.execute();
            this.save_button = true;
            this.create_detail = false;
            // this.create_button = false;
            //  this.edit_button = false;
            this.delete_detail = false;
            this.cancel_button = true;
            this.cancel_prf = false;
            this.save_form_prf = true;
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println(e);
        }


    }

    public void cancelMailId(ActionEvent actionEvent) {
        flag = "f";
        this.mode = "A";
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        // this.create_button = false;
        this.cancel_button = true;
        this.create_detail = false;
        //this.edit_button = false;
        this.save_button = true;
        this.delete_detail = false;
        this.form_readonly = true;
        this.form_readonlyRole = true;
        this.form_readonlyRoleUsrId = true;

        this.save_form_prf = true;
        this.form_readonlyRole_org = true;
        this.save_button_org = true;
        this.create_detail_org = false;
        this.create_button_org = true;
        this.edit_button_org = false;
        this.delete_detail_org = false;
        this.cancel_button_org = true;


        this.addId = 0;
        AdfFacesContext.getCurrentInstance().addPartialTarget((this.searchTableBind));
        AdfFacesContext.getCurrentInstance().addPartialTarget((this.getUserBindVar));


    }

    public void addGrpLink(ActionEvent actionEvent) {
        //        BindingContainer bindings = getBindings();
        //        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert2");
        //        operationBinding.execute();
        this.form_readonly = false;
        //  this.create_button = true;
        this.create_detail = true;
        //  this.edit_button = true;
        this.delete_detail = true;
        this.cancel_button = false;
    }

    public void saveGrpLink(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        operationBinding.execute();
        if (operationBinding.getErrors().isEmpty()) {
            this.form_readonly = true;
            this.save_button = true;
            this.create_detail = false;
            //   this.create_button = false;
            //  this.edit_button = false;
            this.delete_detail = false;
            this.cancel_button = true;
        }

    }

    /* public void cancelGrpLink(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();


    } */

    public void addRoleLink(ActionEvent actionEvent) {

        this.addId = 3;

        //
        //        ViewObjectImpl userOrgRuleVo = am.getOrgUsrRole1();
        //        userOrgRuleVo.executeQuery();


        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert3");
        operationBinding.execute();
        this.form_readonlyRole = false;
        this.form_readonlyRoleUsrId = false;
        //    this.create_button = true;
        this.create_detail = true;
        //    this.edit_button = true;
        this.delete_detail = true;
        this.save_button = false;
        this.cancel_button = true;
        this.cancel_prf = false;
        this.save_form_prf = true;

        AdfFacesContext.getCurrentInstance().addPartialTarget((this.searchTableBind));
        AdfFacesContext.getCurrentInstance().addPartialTarget((this.getUserBindVar));
    }

    public void saveRoleLink(ActionEvent actionEvent) {

        AppUsersAMImpl am = (AppUsersAMImpl)resolvElDC("AppUsersAMDataControl");
        ViewObjectImpl roleLinkVO = am.getAppSecUsrRoleLnk2();
        Integer slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        System.out.println("slocid---->" + slocid);
        String cldid = (resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}"));
        System.out.println("cldid---->" + cldid);
        Integer usrid = (Integer)(roleLinkVO.getCurrentRow().getAttribute("UsrId"));
        System.out.println("usrid---->" + usrid);
        Integer roleid = Integer.parseInt(usrRoleidBindingVar.getValue().toString());
        System.out.println("roleid---->" + roleid);
        String orgid = (usrOrgRoleBindingVar.getValue().toString());
        System.out.println("orgid---->" + orgid);
        //String mode ="I";
        Integer glbl_usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        Integer UsercreateId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        System.out.println("UsercreateId" + UsercreateId);
        /* String Flag =
            (String)(callStoredFunction(Types.VARCHAR, "APP.FN_INS_ORG_USR_ROL_MNU(?,?,?,?,?,?,?)",
         new Object[] {cldid ,slocid,orgid, usrid,roleid,UsercreateId,mode}));
       System.out.println("Flag------>>"+Flag); */

        System.out.println("Inside the Flag....!!!");
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        operationBinding.execute();
        String flag =
            (String)(callStoredFunction(Types.VARCHAR, "APP.FN_INS_ORG_USR_ROL_MNU(?,?,?,?,?,?,?)", new Object[] { cldid,
                                                                                                                   slocid,
                                                                                                                   orgid,
                                                                                                                   usrid,
                                                                                                                   roleid,
                                                                                                                   UsercreateId,
                                                                                                                   "I" }));
        System.out.println("Flag------>>" + flag);
        System.out.println("Inside the Flag....!!!");
        if (flag.equalsIgnoreCase("Y")) {

            roleLinkVO.getCurrentRow().setAttribute("UsrRoleCldId", cldid);
            BindingContainer bindings1 = getBindings();
            OperationBinding operationBinding1 = bindings1.getOperationBinding("Commit");
            operationBinding.execute();
        }
        System.out.println("After The Commit in flag ....!!!" + operationBinding.getErrors().isEmpty());
        if (operationBinding.getErrors().isEmpty()) {
            this.form_readonlyRole = true;
            this.form_readonlyRoleUsrId = true;
            this.save_button = true;
            this.create_detail = false;
            //  this.create_button = false;
            //  this.edit_button = false;
            this.delete_detail = false;
            this.cancel_button = true;
            this.cancel_prf = false;
            this.save_form_prf = true;
            //this.save_button=
        }


        this.addId = 0;
        AdfFacesContext.getCurrentInstance().addPartialTarget((this.searchTableBind));
        AdfFacesContext.getCurrentInstance().addPartialTarget((this.getUserBindVar));

    }

    public void cancelRoleLink(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        this.addId = 0;


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


    public String resetButton() {
        // Add event code here...
        return null;
    }

    public void setMailIdtable(RichTable mailIdtable) {
        this.mailIdtable = mailIdtable;
    }

    public RichTable getMailIdtable() {
        return mailIdtable;
    }

    public void setPanelFormAdd(RichPanelFormLayout panelFormAdd) {
        this.panelFormAdd = panelFormAdd;
    }

    public RichPanelFormLayout getPanelFormAdd() {
        return panelFormAdd;
    }

    public void DialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            this.edit_form_prf = true;
            this.create_form_prf = false;


            AdfFacesContext.getCurrentInstance().addPartialTarget(addPrfBindVar);
            OperationBinding op = getBindings().getOperationBinding("Commit");
            op.execute();
            Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            AppUsersAMImpl am = (AppUsersAMImpl)resolvElDC("AppUsersAMDataControl");
            ViewObjectImpl v1 = am.getAppSecUsr1();
            AppSecUsrVORowImpl row1 = (AppSecUsrVORowImpl)v1.getCurrentRow();
            Integer usrid = (Integer)row1.getAttribute("UsrId");
            if ((this.flag.equals("f"))) {
                System.out.println("Trying to add weblogic user");
                try {

                    this.createConnection();

                } catch (Exception e) {

                    FacesMessage message =
                        new FacesMessage("There is error in creating connection.Please Try Again Later");
                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext.getCurrentInstance().addMessage(null, message);

                }

                boolean isUserExist = true;
                try {
                    isUserExist = WeblogicUser.isUserExists(usrid.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (isUserExist) {
                    BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Delete5").execute();
                    BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
                    BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Rollback").execute();

                    this.closeConnection();

                    FacesMessage message = new FacesMessage("User Already Exist in weblogic,You can not Create Users");
                    message.setSeverity(FacesMessage.SEVERITY_WARN);
                    FacesContext.getCurrentInstance().addMessage(null, message);


                } else {

                    if ((this.flag.equals("f")) && newPassBindVar.getValue() != null &&
                        newPassBindVar.getValue().toString().length() != 0) {
                        String newpass = newPassBindVar.getValue().toString();
                        //System.out.println("new pass = "+newpass);

                        String setpwd =
                            (String)(callStoredFunction(Types.VARCHAR, "APP.PKG_SEC.FN_SET_USR_PWD(?,?,?)", new Object[] { SlocId,
                                                                                                                           usrid,
                                                                                                                           newpass }));
                    }

                    DCIteratorBinding parentIter = (DCIteratorBinding)getBindings().get("AppSecUsr1Iterator");
                    Key parentKey = parentIter.getCurrentRow().getKey();

                    //  am.beforeAdd();
                    BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();

                    //                    BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Execute").execute();
                    //                    v1.executeQuery();
                    //

                    // parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));


                    Boolean b =
                        WeblogicUser.addUser(usrid.toString(), this.newPassBindVar.getValue().toString(), this.userNameAlias.getValue().toString());


                    WeblogicUser.closeConnection();

                    if (!b) {

                        FacesMessage message = new FacesMessage("Something went wrong u cant login with this user");
                        message.setSeverity(FacesMessage.SEVERITY_INFO);
                        FacesContext.getCurrentInstance().addMessage(null, message);

                    }

                    FacesMessage message = new FacesMessage("Record Saved successfully.");
                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }


            }

            AdfFacesContext.getCurrentInstance().addPartialTarget((this.searchTableBind));

        }
    }

    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {
            AppUsersAMImpl am = (AppUsersAMImpl)resolvElDC("AppUsersAMDataControl");
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


    public void emailValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (this.validateFalag.equals(new Integer(0))) {
            String value = (String)object;
            // System.out.println(value);
            if ((value == null) || value.length() == 0) {
                return;
            }

            String expression =
                "^[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{2,4})$";
            CharSequence inputStr = value;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);

            // String error = resolvEl("#{bundle['usergroups.UserEmailID.Message']}");
            String error = "Invalid User Email ID";

            if (matcher.matches()) {
            } else {

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
            }

            /*
             * For Add popup
             * */
            int addco = 0;
            AppUsersAMImpl am = (AppUsersAMImpl)resolvElDC("AppUsersAMDataControl");
            ViewObjectImpl v2 = am.getAppSecUsrMailId1();
            //System.out.println("Total Rowcount is------?>" + v2.getRowCount());
            String mail = "";
            RowSetIterator r = v2.createRowSetIterator(null);
            while (r.hasNext()) {
                try {
                    Row row = r.next();
                    mail = row.getAttribute("UsrMailId").toString();
                } catch (NullPointerException npe) {
                    //System.out.println("NPE" + npe);
                    mail = "";
                }
                //System.out.println("Email:" + mail);
                if (value.equalsIgnoreCase(mail)) {
                    addco = addco + 1;
                }

            }

            //System.out.println("Count is------>" + addco);
            if (addco > 0) {
                String msg = "Duplicate Email Id exists.";
                FacesMessage message2 = new FacesMessage(msg);

                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            } else {

                AdfFacesContext.getCurrentInstance().addPartialTarget((this.defaultEmail));
                this.validateFalag = 1;
            }


        }
    }

    /*  public void setCreate_button(boolean create_button) {
        this.create_button = create_button;
    }

    public boolean isCreate_button() {
        return create_button;
    }

    public void setEdit_button(boolean edit_button) {
        this.edit_button = edit_button;
    }

    public boolean isEdit_button() {
        return edit_button;
    }





    */

    public void setSave_button(boolean save_button) {
        this.save_button = save_button;
    }

    public boolean isSave_button() {
        return save_button;
    }

    public boolean isCancel_button() {
        return cancel_button;
    }

    public void setCancel_button(boolean cancel_button) {
        this.cancel_button = cancel_button;
    }

    public void setCreate_detail(boolean create_detail) {
        this.create_detail = create_detail;
    }

    public boolean isCreate_detail() {
        return create_detail;
    }

    public void setDelete_detail(boolean delete_detail) {
        this.delete_detail = delete_detail;
    }

    public boolean isDelete_detail() {
        return delete_detail;
    }

    public void setForm_readonly(boolean form_readonly) {
        this.form_readonly = form_readonly;
    }

    public boolean isForm_readonly() {
        return form_readonly;
    }

    public void setDeletePopUpMailId(RichPopup deletePopUpMailId) {
        this.deletePopUpMailId = deletePopUpMailId;
    }

    public RichPopup getDeletePopUpMailId() {
        return deletePopUpMailId;
    }

    public void DeleteDialogListenerMailId(DialogEvent dialogEvent) {


        if (dialogEvent.getOutcome().name().equals("yes")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Delete2");
            operationBinding.execute();

            BindingContainer bindingsa = getBindings();
            OperationBinding operationBindings = bindingsa.getOperationBinding("Commit");
            operationBindings.execute();


            if (operationBinding.getErrors().isEmpty()) {
                FacesMessage message = new FacesMessage("Record Deleted Successfully!");
                message.setSeverity(FacesMessage.SEVERITY_INFO);

                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);

            }

        } else if (dialogEvent.getOutcome().name().equals("no")) {


        }
    }


    public void DeleteDialogListenerGrpLink(DialogEvent dialogEvent) {


        if (dialogEvent.getOutcome().name().equals("yes")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Delete1");
            operationBinding.execute();

            BindingContainer bindingsa = getBindings();
            OperationBinding operationBindings = bindingsa.getOperationBinding("Commit");
            operationBindings.execute();

            BindingContainer binding = getBindings();
            OperationBinding createOpBAddress = binding.getOperationBinding("Execute2");
            createOpBAddress.execute();


            if (operationBinding.getErrors().isEmpty()) {
                FacesMessage message = new FacesMessage("Record Deleted Successfully!");
                message.setSeverity(FacesMessage.SEVERITY_INFO);

                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);

            }

        } else if (dialogEvent.getOutcome().name().equals("no")) {


        }
    }

    public void DeleteDialogListenerRole(DialogEvent dialogEvent) {


        if (dialogEvent.getOutcome().name().equals("yes")) {

            AppUsersAMImpl am = (AppUsersAMImpl)resolvElDC("AppUsersAMDataControl");
            Integer slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            String cldid = (resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}"));
            // Integer usrid = Integer.parseInt(usrIdBinding.getValue().toString());
            //Integer roleid=Integer.parseInt(usrRoleidBindingVar.getValue().toString());

            ViewObjectImpl vo = am.getAppSecUsrRoleLnk2();
            Integer roleid = Integer.parseInt(vo.getCurrentRow().getAttribute("UsrRoleId").toString());
            Integer usrid = (Integer)vo.getCurrentRow().getAttribute("UsrId");
            String orgid = vo.getCurrentRow().getAttribute("UsrOrgId").toString();
            Integer UsercreateId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
            String mode = "D";
            String Flag = null;


            Flag =
(String)(callStoredFunction(Types.VARCHAR, "APP.FN_INS_ORG_USR_ROL_MNU(?,?,?,?,?,?,?)", new Object[] { cldid, slocid,
                                                                                                       orgid, usrid,
                                                                                                       roleid,
                                                                                                       UsercreateId,
                                                                                                       mode }));


            if (Flag.equals("Y")) {
                BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("Delete3");
                operationBinding.execute();

                BindingContainer bindingsa = getBindings();
                OperationBinding operationBindings = bindingsa.getOperationBinding("Commit");
                operationBindings.execute();

                BindingContainer binding = getBindings();
                OperationBinding createOpBAddress = binding.getOperationBinding("Execute3");
                createOpBAddress.execute();


                if (operationBinding.getErrors().isEmpty()) {
                    FacesMessage message = new FacesMessage("Record Deleted Successfully!");
                    message.setSeverity(FacesMessage.SEVERITY_INFO);

                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);

                }


            } else {
                FacesMessage message = new FacesMessage("Cannot able to replicate in Menu");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }

            AdfFacesContext.getCurrentInstance().addPartialTarget((this.mailIdtable));


        } else if (dialogEvent.getOutcome().name().equals("no")) {


        }
    }

    public void detailPopUpCancelListenerMail(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();


        /*  BindingContainer bindings1 = getBindings();
        OperationBinding operationBinding1 = bindings1.getOperationBinding("Execute1");
        operationBinding1.execute(); */


    }

    public void detailPopUpCancelListenerGrp(PopupCanceledEvent popupCanceledEvent) {


        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();


        /*  BindingContainer bindings1 = getBindings();
        OperationBinding operationBinding1 = bindings1.getOperationBinding("Execute1");
        operationBinding1.execute(); */


    }

    public void detailPopUpCancelListenerRole(PopupCanceledEvent popupCanceledEvent) {


        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Delete3");
        operationBinding.execute();


        /*  BindingContainer bindings1 = getBindings();
        OperationBinding operationBinding1 = bindings1.getOperationBinding("Execute1");
        operationBinding1.execute(); */

    }


    public void deleteMailId(ActionEvent actionEvent) {
        showPopup(deletePopUpMailId, true);
        this.cancel_prf = true;
        this.save_form_prf = true;
    }

    public void setDeletePopUpGrPLink(RichPopup deletePopUpGrPLink) {
        this.deletePopUpGrPLink = deletePopUpGrPLink;
    }

    public RichPopup getDeletePopUpGrPLink() {
        return deletePopUpGrPLink;
    }

    public void deleteGrpLink(ActionEvent actionEvent) {
        showPopup(deletePopUpGrPLink, true);
    }

    public void deleteRole(ActionEvent actionEvent) {
        showPopup(deletePopUpRole, true);
        this.addId = 0;
        AdfFacesContext.getCurrentInstance().addPartialTarget((this.searchTableBind));
        AdfFacesContext.getCurrentInstance().addPartialTarget((this.getUserBindVar));


    }

    public void setDeletePopUpRole(RichPopup deletePopUpRole) {
        this.deletePopUpRole = deletePopUpRole;
    }

    public RichPopup getDeletePopUpRole() {
        return deletePopUpRole;
    }

    public void setForm_readonlyRole(boolean form_readonlyRole) {
        this.form_readonlyRole = form_readonlyRole;
    }

    public boolean isForm_readonlyRole() {
        return form_readonlyRole;
    }

    public void setMessageRender(RichMessage messageRender) {
        this.messageRender = messageRender;
    }

    public RichMessage getMessageRender() {
        return messageRender;
    }

    public void setPageRender(RichPanelGroupLayout pageRender) {
        this.pageRender = pageRender;
    }

    public RichPanelGroupLayout getPageRender() {
        return pageRender;
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

    public void addPrfForUsr(ActionEvent actionEvent) {
        // Add event code here...
        //this.setMode("I");
        // this.cancel_prf=true;
        /* this.save_form_prf=true;
        this.edit_form_prf=false;
        this.create_form_prf=false; */


        //System.out.println("add btn clicked  = mode "+mode);
    }

    public void saveProfileActionListener(ActionEvent actionEvent) {
        // Add event code here...
        this.addId = 0;
        this.userMode = "A";
        Integer slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String cldid = (resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}"));
        //Integer usrid = Integer.parseInt(usrIdBinding.getValue().toString());
        //System.out.println("userId  : "+usrid);
        System.out.println("sloc id :  " + slocid);
        System.out.println("cld id :" + cldid);


        AppUsersAMImpl am = (AppUsersAMImpl)resolvElDC("AppUsersAMDataControl");

        ViewObjectImpl vo = am.getAppSecUsrCld1_1();

        Long usrid = (Long)vo.getCurrentRow().getAttribute("UsrId");

        if ((mode.equalsIgnoreCase("I")) || (mode.equalsIgnoreCase("U"))) {

            System.out.println("Commiting inside save ");

            String mode = this.getMode();
            vo.getCurrentRow().setAttribute("UsrId", usrid);

            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();
            this.setMode("O");
            this.edit_form_prf = false;
            this.save_form_prf = true;

        }
    }

    public void editProfileActionListener(ActionEvent actionEvent) {

        this.setMode("U");
        this.edit_form_prf = true;
        this.save_form_prf = false;
        this.create_form_prf = true;

    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void searchTableSelectionListener(SelectionEvent selectionEvent) {
        // Add event code here...

        ADFUtil.invokeEL("#{bindings.AppSecUsr1.collectionModel.makeCurrent}", new Class[] { SelectionEvent.class },
                         new Object[] { selectionEvent });
        // get the selected row , by this you can get any attribute of that row
        Row selectedRow =
            (Row)ADFUtil.evaluateEL("#{bindings.AppSecUsr1Iterator.currentRow}"); // get the current selected row
        //to show popup, you can write your logic here , what you wanna do
        Integer usrIdTable = Integer.parseInt(selectedRow.getAttribute("UsrId").toString());
        //System.out.println(usrIdTable+"  :usrIdTable");
        Integer usrid = Integer.parseInt(usrIdBinding.getValue().toString());
        //System.out.println(usrid+"  :usrid");
        OperationBinding opNew = getBindings().getOperationBinding("callFunctionOnCreate");
        opNew.getParamsMap().put("usrId", usrid);
        //System.out.println(opNew.getResult());


    }

    public void cancelProfileActionListener(ActionEvent actionEvent) {
        // Add event code here...
        this.addId = 0;
        this.setMode("T");
        //this.userMode="A";
        //System.out.println(" cancelPrf btn clicked = mode "+mode);
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();

        this.form_readonlyRole_org = true;
        this.save_form_prf = true;
        this.edit_form_prf = false;
        this.cancel_prf = true;

        this.form_readonlyRoleUsrId = true;


        this.form_readonlyRole_org = true;
        this.save_button_org = true;
        this.create_detail_org = false;
        this.create_button_org = true;
        this.edit_button_org = false;
        this.delete_detail_org = false;
        this.cancel_button_org = true;
        this.cancel_button = true;
        this.save_button = true;
        /* this.create_button = false;

        this.create_detail = false;
        this.edit_button = false;
          */
        this.delete_detail = false;
        this.form_readonly = true;
        this.form_readonlyRole = true;
        this.form_readonlyRoleUsrId = true;

    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }

    public void addPrfForUsrActionListener(ActionEvent actionEvent) {

        this.setMode("I");

        this.save_form_prf = false;
        this.edit_form_prf = true;

        this.create_form_prf = true;
        OperationBinding op = getBindings().getOperationBinding("CreateInsert2");
        op.execute();


    }

    public void setNewPassBindVar(RichInputText newPassBindVar) {
        this.newPassBindVar = newPassBindVar;
    }

    public RichInputText getNewPassBindVar() {
        return newPassBindVar;
    }

    public void nameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
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
                msg2 = "#{bundle['MSG.7']}";
                FacesMessage message2 = new FacesMessage(resolvEl(msg2));
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (langDesc.contains("()")) {
                msg2 = "#{bundle['MSG.170']}";
                FacesMessage message2 = new FacesMessage(resolvEl(msg2));
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (langDesc.contains("(.") || langDesc.contains("(-") || langDesc.contains("-)")) {
                msg2 = "#{bundle['MSG.171']}";
                FacesMessage message2 = new FacesMessage(resolvEl(msg2));
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }

            openB = 0;
            closeB = 0;
            closeFg = false;


            //check for valid language name.Allowed- brackets,dots,hyphen
            String expression =
                "^(?:(?>[A-Za-z0-9 % $)]+)(?:\\_|\\&|\\-|\\(|\\.|\\)|\\:|\\@|\\/|\\\\(?!\\_|\\&|\\%|\\-|\\:|\\@|\\/|\\\\|$))?)*$";
            CharSequence inputStr = langDesc;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String error = "#{bundle['MSG.171']}";

            if (matcher.matches()) {
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvEl(error),
                                                              resolvEl("#{bundle['MSG.172']}")));
            }
        }

        if (object != null) {
            String isInValid = null;
            System.out.println("valu eof a flag:::::::::" + this.flag);
            if (this.flag.equals("f")) {
                isInValid = am.getDuplicay(object.toString());
                System.out.println("Resut is       " + isInValid);
                if (isInValid.equals("Y")) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Duplicate Name Exist,Please Use another Name",
                                                                  null));


                }
            } else {
                if (!((String)object).equals(this.usrNameEdit)) {
                    isInValid = am.getDuplicay(object.toString());
                    if (isInValid.equals("Y")) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      "Duplicate Name Exist,Please Use another Name",
                                                                      null));
                    }
                }
            }

        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Enter User Name", null));
        }
    }

    public void setUsrName(RichInputText usrName) {
        this.usrName = usrName;
    }

    public RichInputText getUsrName() {
        return usrName;
    }

    public void setGetUserNameTrans(RichInputText getUserNameTrans) {
        this.getUserNameTrans = getUserNameTrans;
    }

    public RichInputText getGetUserNameTrans() {
        return getUserNameTrans;
    }

    public void setGetUserTrans(RichCommandButton getUserTrans) {
        this.getUserTrans = getUserTrans;
    }

    public RichCommandButton getGetUserTrans() {
        return getUserTrans;
    }

    public void serachUserName(ActionEvent actionEvent) {
        AppUsersAMImpl am = (AppUsersAMImpl)resolvElDC("AppUsersAMDataControl");

        ViewObjectImpl userVo = am.getAppSecUsr1();
        System.out.println(userVo.getRowCount());
        System.out.println("getUserBindVar.getValue() : " + getUserBindVar.getValue());
        userVo.setNamedWhereClauseParam("Bind_usr_name", getUserBindVar.getValue());
        userVo.executeQuery();
        System.out.println(userVo.getRowCount());
        //userVo.setWhereClause("UserName");

    }

    public void setGetUserBindVar(RichInputListOfValues getUserBindVar) {
        this.getUserBindVar = getUserBindVar;
    }

    public RichInputListOfValues getGetUserBindVar() {
        return getUserBindVar;
    }

    public void resetUserNameActionListner(ActionEvent actionEvent) {
        AppUsersAMImpl am = (AppUsersAMImpl)resolvElDC("AppUsersAMDataControl");
        ViewObjectImpl userVo = am.getAppSecUsr1();
        ViewObjectImpl userLOVVo = am.getSearchTrans1();
        System.out.println(userVo.getRowCount());
        System.out.println("getUserBindVar.getValue() : " + getUserBindVar.getValue());
        userVo.setNamedWhereClauseParam("Bind_usr_name", "");
        userVo.executeQuery();
        userLOVVo.executeQuery();
        System.out.println(userVo.getRowCount());
        //userVo.setWhereClause("UserName");

    }


    public void setUserMode(String userMode) {
        this.userMode = userMode;
    }

    public String getUserMode() {
        return userMode;
    }

    public void setChildSaveLinkBVar(RichCommandImageLink childSaveLinkBVar) {
        this.childSaveLinkBVar = childSaveLinkBVar;
    }

    public RichCommandImageLink getChildSaveLinkBVar() {
        return childSaveLinkBVar;
    }

    public void setChildCancelLinkBVar(RichCommandImageLink childCancelLinkBVar) {
        this.childCancelLinkBVar = childCancelLinkBVar;
    }

    public RichCommandImageLink getChildCancelLinkBVar() {
        return childCancelLinkBVar;
    }

    public void addUserOrgActionListener(ActionEvent actionEvent) {
        AppUsersAMImpl am = (AppUsersAMImpl)resolvElDC("AppUsersAMDataControl");


        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert5");
        operationBinding.execute();

        am.assignOrg();

        this.form_readonlyRole_org = false;
        this.create_button_org = true;
        this.create_detail_org = true;
        this.edit_button_org = true;
        this.delete_detail_org = true;
        this.save_button_org = false;
        this.cancel_button = true;
        this.cancel_prf = false;
        this.save_form_prf = true;
    }

    public void deleteUserOrgActionListener(ActionEvent actionEvent) {

        Integer slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String cldid = (resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}"));
        //Integer usrid = Integer.parseInt(usrIdBinding.getValue().toString());
        //Integer roleid=Integer.parseInt(usrRoleidBindingVar.getValue().toString());

        ViewObjectImpl vo = am.getOrgSecUsr3();

        //Integer roleid = Integer.parseInt(vo.getCurrentRow().getAttribute("UsrRoleId").toString());
        String orgid = vo.getCurrentRow().getAttribute("UsrOrgId").toString();
        Integer usrid = (Integer)vo.getCurrentRow().getAttribute("UsrId");


        String res =
            (String)this.callStoredFunction(Types.VARCHAR, "fn_chk_role_usr_link(?,?,?,?,?)", new Object[] { cldid,
                                                                                                             slocid,
                                                                                                             orgid,
                                                                                                             usrid,
                                                                                                             null });

        if (res != null) {
            if (res.equals("N")) {
                am.deleteWarehouse();

                BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("Delete1");
                operationBinding.execute();


                operationBinding = bindings.getOperationBinding("Commit");
                operationBinding.execute();
                this.cancel_prf = false;
                this.save_form_prf = true;
                FacesMessage message = new FacesMessage("Record Deleted Succesfully");
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);

            } else if (res.equals("Y")) {
                FacesMessage message = new FacesMessage(this.resolvEl("#{bundle['MSG.10']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else {

                FacesMessage message = new FacesMessage("Some Error Occured During  function fn_chk_role_usr_link ");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);


            }
        }
    }

    public void setGetUserOrgBindVar(RichInputListOfValues getUserOrgBindVar) {
        this.getUserOrgBindVar = getUserOrgBindVar;
    }

    public RichInputListOfValues getGetUserOrgBindVar() {
        return getUserOrgBindVar;
    }

    public void tabChangeListener(AttributeChangeEvent attributeChangeEvent) {
        System.out.println("tabChangeListener");
    }

    public void setUserIdDisclosureListener(DisclosureEvent disclosureEvent) {


        AppUsersAMImpl am = (AppUsersAMImpl)resolvElDC("AppUsersAMDataControl");
        am.filterOrgSecUSer();


    }

    public void saveUsrOrgActionListener(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding;
        if (usrPrfBindingVar.getValue() != null) {
            String ho_OrgId = usrOrgBinding.getValue().toString();
            AppUsersAMImpl am = (AppUsersAMImpl)resolvElDC("AppUsersAMDataControl");
            ViewObjectImpl userOrgVo = am.getOrgSecUsr3();

            if (ho_OrgId != null) {

                userOrgVo.getCurrentRow().setAttribute("UsrOrgId", (ho_OrgId));


                operationBinding = bindings.getOperationBinding("Commit");
                operationBinding.execute();
                userOrgVo.executeQuery();

                if (operationBinding.getErrors().isEmpty()) {
                    this.form_readonlyRole_org = true;
                    this.save_button_org = true;
                    this.create_detail_org = false;
                    this.create_button_org = false;
                    this.edit_button_org = false;
                    this.delete_detail_org = false;
                    this.cancel_button = true;
                    this.cancel_prf = false;
                    this.save_form_prf = true;
                    this.save_button = true;
                    this.create_detail = false;
                    this.delete_detail = false;
                }

            }
        }

        else {
        }
    }

    public void setGetUsrOrgNameBindVar(RichInputText getUsrOrgNameBindVar) {
        this.getUsrOrgNameBindVar = getUsrOrgNameBindVar;
    }

    public RichInputText getGetUsrOrgNameBindVar() {
        return getUsrOrgNameBindVar;
    }

    public void setUsrOrgBinding(RichSelectOneChoice usrOrgBinding) {
        this.usrOrgBinding = usrOrgBinding;
    }

    public RichSelectOneChoice getUsrOrgBinding() {
        return usrOrgBinding;
    }


    public void setCreate_button_org(boolean create_button_org) {
        this.create_button_org = create_button_org;
    }

    public boolean isCreate_button_org() {
        return create_button_org;
    }

    public void setEdit_button_org(boolean edit_button_org) {
        this.edit_button_org = edit_button_org;
    }

    public boolean isEdit_button_org() {
        return edit_button_org;
    }

    public void setSave_button_org(boolean save_button_org) {
        this.save_button_org = save_button_org;
    }

    public boolean isSave_button_org() {
        return save_button_org;
    }

    public void setCancel_button_org(boolean cancel_button_org) {
        this.cancel_button_org = cancel_button_org;
    }

    public boolean isCancel_button_org() {
        return cancel_button_org;
    }

    public void setCreate_detail_org(boolean create_detail_org) {
        this.create_detail_org = create_detail_org;
    }

    public boolean isCreate_detail_org() {
        return create_detail_org;
    }

    public void setDelete_detail_org(boolean delete_detail_org) {
        this.delete_detail_org = delete_detail_org;
    }

    public boolean isDelete_detail_org() {
        return delete_detail_org;
    }

    public void setForm_readonly_org(boolean form_readonly_org) {
        this.form_readonly_org = form_readonly_org;
    }

    public boolean isForm_readonly_org() {
        return form_readonly_org;
    }

    public void setForm_readonlyRole_org(boolean form_readonlyRole_org) {
        this.form_readonlyRole_org = form_readonlyRole_org;
    }

    public boolean isForm_readonlyRole_org() {
        return form_readonlyRole_org;
    }

    public void setForm_readonlyRoleUsrId(boolean form_readonlyRoleUsrId) {
        this.form_readonlyRoleUsrId = form_readonlyRoleUsrId;
    }

    public boolean isForm_readonlyRoleUsrId() {
        return form_readonlyRoleUsrId;
    }

    public void setUsrOrgRoleBindingVar(RichSelectOneChoice usrOrgRoleBindingVar) {
        this.usrOrgRoleBindingVar = usrOrgRoleBindingVar;
    }

    public RichSelectOneChoice getUsrOrgRoleBindingVar() {
        return usrOrgRoleBindingVar;
    }

    public void setUsrRoleidBindingVar(RichSelectOneChoice usrRoleidBindingVar) {
        this.usrRoleidBindingVar = usrRoleidBindingVar;
    }

    public RichSelectOneChoice getUsrRoleidBindingVar() {
        return usrRoleidBindingVar;
    }

    public void duplicateRoleCheckValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null) {
            if (this.usrOrgRoleBindingVar.getValue() == null ||
                this.usrOrgRoleBindingVar.getValue().toString().equals("")) {
                return;
            } else {
                String result =
                    am.validateRole(Integer.parseInt(object.toString()), usrOrgRoleBindingVar.getValue().toString(),
                                    "A");
                if (result.equalsIgnoreCase("Y")) {
                    System.out.println("Validate");
                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate Record",
                                                                  null));
                }
            }
        } else {
            return;
        }

    }

    public void duplicateRoleOrgCheckValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null) {
            System.out.println("usrRoleidBindingVar = " + usrRoleidBindingVar.getValue());
            if (this.usrRoleidBindingVar.getValue() == null ||
                this.usrRoleidBindingVar.getValue().toString().equals("")) {
                return;
            } else {
                String result =
                    am.validateRole(Integer.parseInt(usrRoleidBindingVar.getValue().toString()), object.toString(),
                                    "A");
                System.out.println("value of result in baean is== " + result);
                if (result.equalsIgnoreCase("Y")) {
                    System.out.println("Validate");
                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate Record",
                                                                  null));
                }
            }
        } else {
            return;
        }

    }

    public void userOrgIdValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String ho_OrgId = object.toString();
        AppUsersAMImpl am = (AppUsersAMImpl)resolvElDC("AppUsersAMDataControl");
        ViewObjectImpl userOrgVo = am.getOrgSecUsr3();
        RowQualifier rowQualifier = new RowQualifier(userOrgVo);
        // filtering data using method setWhereClause
        if (ho_OrgId != null) {
            rowQualifier.setWhereClause("UsrOrgId='" + ho_OrgId + "'");
            // use method getFilteredRows to return Row[] type after filter.
            System.out.println("query  " + rowQualifier.getExprStr());
            Row[] filteredRows = null;
            try {
                filteredRows = userOrgVo.getFilteredRows(rowQualifier);
            } catch (Exception e) {
                // TODO: Add catch code
                e.printStackTrace();
            }
            if (filteredRows.length > 0) {

                System.out.println("set 2");
                String msg2 = "Duplicate Organisation exist !!!!";
                //                   FacesMessage message2 = new FacesMessage( (msg2));
                //                   message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg2, null));
            }
        }
    }

    public void setUsrPrfBindingVar(RichSelectOneChoice usrPrfBindingVar) {
        this.usrPrfBindingVar = usrPrfBindingVar;
    }

    public RichSelectOneChoice getUsrPrfBindingVar() {
        return usrPrfBindingVar;
    }

    public void setEdit_form_prf(boolean edit_form_prf) {
        this.edit_form_prf = edit_form_prf;
    }

    public boolean isEdit_form_prf() {
        return edit_form_prf;
    }

    public void setSave_form_prf(boolean save_form_prf) {
        this.save_form_prf = save_form_prf;
    }

    public boolean isSave_form_prf() {
        return save_form_prf;
    }


    public void reserveValueChangeListener(ValueChangeEvent valueChangeEvent) {
        /*  AppUsersAMImpl am = (AppUsersAMImpl)resolvElDC("AppUsersAMDataControl");
        ViewObjectImpl appSecUsrVO = am.getAppSecUsr1();

        if (valueChangeEvent.getNewValue()!=null) {
            String str=valueChangeEvent.getNewValue().toString();
            System.out.println("STR----> Reserve Value--->"+str);
            if (str.equalsIgnoreCase("true")) {
                if (appSecUsrVO.getCurrentRow().getAttribute("UsrActv") != null) {

                    String rsv = appSecUsrVO.getCurrentRow().getAttribute("UsrActv").toString();
                    System.out.println("rsv---->" + rsv);
                    if (rsv.equalsIgnoreCase("true")) {
                        System.out.println("if----->" + rsv);
                    } else {
                        System.out.println("else---->" + rsv);
                        FacesMessage message =
                            new FacesMessage("Can not able to Add user , Active Field is Required...!!");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }
                } else {
                        System.out.println("else null---->");
                        FacesMessage message =
                            new FacesMessage("Can not able to Add user , Active Field is Required...!!");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }
            }
        }
         */
    }

    public void reservCheckValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        AppUsersAMImpl am = (AppUsersAMImpl)resolvElDC("AppUsersAMDataControl");
        ViewObjectImpl appSecUsrVO = am.getAppSecUsr1();

        if (object != null) {
            String str = object.toString();
            System.out.println("STR----> Reserve Value--->" + str);
            if (str.equalsIgnoreCase("true")) {
                if (appSecUsrVO.getCurrentRow().getAttribute("UsrActv") != null) {

                    String rsv = appSecUsrVO.getCurrentRow().getAttribute("UsrActv").toString();
                    System.out.println("rsv---->" + rsv);
                    if (rsv.equalsIgnoreCase("Y")) {
                        System.out.println("if----->" + rsv);
                    } else {
                        System.out.println("else---->" + rsv);
                        String msg2 = "Can not able to Add user , Active Field is Required...!!";
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg2, null));
                    }
                }
            }
        }

    }

    public void setActiveUsr(RichSelectBooleanCheckbox activeUsr) {
        this.activeUsr = activeUsr;
    }

    public RichSelectBooleanCheckbox getActiveUsr() {
        return activeUsr;
    }

    public void setEditButtonBind(RichCommandImageLink editButtonBind) {
        this.editButtonBind = editButtonBind;
    }

    public RichCommandImageLink getEditButtonBind() {
        return editButtonBind;
    }


    public void setCancel_prf(boolean cancel_prf) {
        this.cancel_prf = cancel_prf;
    }

    public boolean isCancel_prf() {
        return cancel_prf;
    }

    public void setCreate_form_prf(boolean create_form_prf) {
        this.create_form_prf = create_form_prf;
    }

    public boolean isCreate_form_prf() {
        return create_form_prf;
    }

    public void setPanelFormPrfAdd(RichPanelFormLayout panelFormPrfAdd) {
        this.panelFormPrfAdd = panelFormPrfAdd;
    }

    public RichPanelFormLayout getPanelFormPrfAdd() {
        return panelFormPrfAdd;
    }

    public void setSearchTableBind(RichTable searchTableBind) {
        this.searchTableBind = searchTableBind;
    }

    public RichTable getSearchTableBind() {
        return searchTableBind;
    }

    public void setAddPrfBindVar(RichCommandImageLink addPrfBindVar) {
        this.addPrfBindVar = addPrfBindVar;
    }

    public RichCommandImageLink getAddPrfBindVar() {
        return addPrfBindVar;
    }

    public void setMailIdBind(RichShowDetailItem mailIdBind) {
        this.mailIdBind = mailIdBind;
    }

    public RichShowDetailItem getMailIdBind() {
        return mailIdBind;
    }

    public void setOrganisationBindVar(RichShowDetailItem organisationBindVar) {
        this.organisationBindVar = organisationBindVar;
    }

    public RichShowDetailItem getOrganisationBindVar() {
        return organisationBindVar;
    }

    public void setRoleBindVar(RichShowDetailItem roleBindVar) {
        this.roleBindVar = roleBindVar;
    }

    public RichShowDetailItem getRoleBindVar() {
        return roleBindVar;
    }

    public void setAddId(int addId) {
        this.addId = addId;
    }

    public int getAddId() {
        return addId;
    }

    public void validateAlis(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {

            String isInValid;

            if (this.flag.equals("f")) {
                isInValid = am.getDuplicayAlias(object.toString());
                if (isInValid.equals("Y")) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Duplicate Alis Exist,Please Use another Alias",
                                                                  null));
                }
            }

            else {
                if (!object.toString().equals(this.usrAlias)) {
                    isInValid = am.getDuplicayAlias(object.toString());
                    if (isInValid.equals("Y")) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      "Duplicate Alis Exist,Please Use another Alias",
                                                                      null));
                    }
                }
            }

        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Enter User Alias", null));
        }
    }

    public void orgDiscloserListener(DisclosureEvent disclosureEvent) {
        if (am != null) {
            if (am.getAppSecUsrRoleLnk2() != null) {
                am.getAppSecUsrRoleLnk2().executeQuery();
            }
        }
    }

    public void roleChangeListener(ValueChangeEvent valueChangeEvent) {
    }

    public void validateDefault(ValueChangeEvent valueChangeEvent) {

    }

    public void setDefaultEmail(RichSelectBooleanCheckbox defaultEmail) {
        this.defaultEmail = defaultEmail;
    }

    public RichSelectBooleanCheckbox getDefaultEmail() {
        return defaultEmail;
    }

    public void validateDefault(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println("classs of object is:" + object.getClass().getName());

        Boolean cond = false;
        if ((object.getClass().getName()).equals("java.lang.String")) {
            // System.out.println("default email is selected:"+((String)object));
            if (((String)object).equals("Y"))
                cond = true;

        }
        if ((object.getClass().getName()).equals("java.lang.Boolean")) {
            System.out.println("default email is selected:" + (Boolean)object);
            cond = (Boolean)object;
        }

        System.out.println("default email is selected:" + cond);
        if (cond) {
            String result = am.validateDefaultEmail();
            if (result.equals("N")) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Another Email is selected as default", null));
            }
        }
    }

    public void setEmailId(RichInputText emailId) {
        this.emailId = emailId;
    }

    public RichInputText getEmailId() {
        return emailId;
    }

    public void showWarehouse(ActionEvent actionEvent) {
        am.fiterWarehouse();

        this.showPopup(this.whPop, true);
    }

    public void addWarehouse(DialogEvent dialogEvent) {
        int i = 0;
        Iterator it = this.al.iterator();

        while (it.hasNext()) {
            it.next();
            System.out.println(al.get(i));
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Createwithparameters");
            operationBinding.getParamsMap().put("WhId", al.get(i));
            operationBinding.execute();

            i++;


            // BindingContainer bindings = getBindings();
            operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();

        }
        this.al.clear();

        AdfFacesContext.getCurrentInstance().addPartialTarget(this.warehouseTableBind);


    }

    public void checkBox(ValueChangeEvent valueChangeEvent) {
        //System.out.println(valueChangeEvent.getNewValue().getClass());
        System.out.println(this.whId.getValue());
        if (this.whId != null) {
            if ((Boolean)valueChangeEvent.getNewValue()) {
                if (al.indexOf(this.whId.getValue()) == -1) {
                    al.add((String)this.whId.getValue());
                }
            } else {
                if (al.indexOf(this.whId.getValue()) != -1) {
                    al.remove((String)this.whId.getValue());
                }
            }
        }
    }

    public void setWhId(RichInputText whId) {
        this.whId = whId;
    }

    public RichInputText getWhId() {
        return whId;
    }

    public void setCheckBox(RichSelectBooleanCheckbox checkBox) {
        this.checkBox = checkBox;
    }

    public RichSelectBooleanCheckbox getCheckBox() {
        return checkBox;
    }

    public void setWhPop(RichPopup whPop) {
        this.whPop = whPop;
    }

    public RichPopup getWhPop() {
        return whPop;
    }

    public void setSearchWarehouse(RichInputText searchWarehouse) {
        this.searchWarehouse = searchWarehouse;
    }

    public RichInputText getSearchWarehouse() {
        return searchWarehouse;
    }

    public void searchWarehouse(ActionEvent actionEvent) {
        Object warehouseName = this.searchWarehouse.getValue();
        if (warehouseName != null) {


            am.warehouseSerch(warehouseName.toString());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.warehouseTb);

        }

    }

    public void resetSearchWarehouse(ActionEvent actionEvent) {
        am.warehouseSerch(null);
        this.al.clear();
        this.searchWarehouse.setValue("");
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.searchWarehouse);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.warehouseTb);

    }

    public void setWarehouseTb(RichTable warehouseTb) {
        this.warehouseTb = warehouseTb;
    }

    public RichTable getWarehouseTb() {
        return warehouseTb;
    }

    public Map getChecked() {
        return new HashMap() {


            @Override
            public Object get(Object whId) {
                if (whId != null)
                    if (al.contains(whId.toString()))
                        return true;
                    else
                        return false;
                else
                    return false;
            }
        };
    }

    public String cancelAction() {

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        BindingContainer bindings1 = getBindings();
        OperationBinding operationBinding1 = bindings1.getOperationBinding("Execute");
        operationBinding1.execute();
        createPopup.hide();


        return null;
    }

    public void setUsrAlias(String usrAlias) {
        this.usrAlias = usrAlias;
    }

    public String getUsrAlias() {
        return usrAlias;
    }

    public void setUserAlias(RichInputText userAlias) {
        this.userAlias = userAlias;
    }

    public RichInputText getUserAlias() {
        return userAlias;
    }

    public void setUserName(RichInputText userName) {
        this.userName = userName;
    }

    public RichInputText getUserName() {
        return userName;
    }

    public void roleDisClouserLine(DisclosureEvent disclosureEvent) {
        // Add event code here...
    }

    public void setUserNameAlias(RichInputText userNameAlias) {
        this.userNameAlias = userNameAlias;
    }

    public RichInputText getUserNameAlias() {
        return userNameAlias;
    }

    public void validatePassword(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null) {
            String password = (String)object;

            if (password.length() > 7) {


                String expression = ".*\\d+(.)*";
                CharSequence inputStr = password;
                Pattern pattern = Pattern.compile(expression);
                Matcher matcher = pattern.matcher(inputStr);

                // String error = resolvEl("#{bundle['usergroups.UserEmailID.Message']}");
                String error = "Password should have minimum 1 digit ";

                if (matcher.matches()) {
                } else {

                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
                }


            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "password's length should be more than 7 character",
                                                              null));
            }
        }
    }

    public void setWarehouseTableBind(RichTable warehouseTableBind) {
        this.warehouseTableBind = warehouseTableBind;
    }

    public RichTable getWarehouseTableBind() {
        return warehouseTableBind;
    }

    public void createConnection() throws WeblogicStart {
        WeblogicUser.createConnection();
    }

    public void closeConnection() {
        WeblogicUser.closeConnection();
    }

    public void setIsWeblogicConfigured(Boolean isWeblogicConfigured) {
        this.isWeblogicConfigured = isWeblogicConfigured;
    }

    public Boolean getIsWeblogicConfigured() {
        return isWeblogicConfigured;
    }
}
