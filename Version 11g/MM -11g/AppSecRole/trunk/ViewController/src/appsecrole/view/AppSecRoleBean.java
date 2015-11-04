package appsecrole.view;

import appsecrole.model.module.AppRolesAMImpl;

import appsecrole.model.views.AppMnuVWVOImpl;
import appsecrole.model.views.AppSecUsrRoleLnkVOImpl;

import appsecrole.model.views.dualVOImpl;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import oracle.jbo.domain.Number;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelTabbed;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;

import oracle.jbo.JboException;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewObject;

import oracle.jbo.domain.Date;
import oracle.jbo.server.RowFilter;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.uicli.binding.JUCtrlListBinding;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;
//role bean
public class AppSecRoleBean {
    private RichPopup createPopUp;
    private RichPopup deletePopUp;
    private RichPopup addUsrPopUp;
    private RichOutputText userRoleName;
    private List selectedUser;
    private List allUsers;
    private List selectedRolelist = new ArrayList();
    private RichInputText usrRoleName;
    private RichPopup addMnuPopUp;
    private Integer count = -1;
    private RichTable bindRoleMenuTab;
    private RichSelectBooleanCheckbox addMode;
    private RichInputListOfValues getUserRoleBindVar;
    private RichSelectBooleanCheckbox menuAddModeBinding;
    private RichSelectBooleanCheckbox isSelectedbindingVar;
    private RichSelectBooleanCheckbox editModeBindingVar;
    private RichSelectBooleanCheckbox viewBindingVar;
    private RichSelectBooleanCheckbox delModeBindingVar;
    private RichSelectOneChoice getOrgBindVar;
    private RichSelectOneChoice getUsrNameBindVar;
    private RichSelectOneChoice getOrgMenuBindVar;
    private String readMode="V";
    private String buttonMode="V";
    private RichSelectBooleanCheckbox activeBindVar;
    private String buttonReadOnlyMode="V";
    private String addOrgMenuButtonMode="V";
    private RichTable searchTableBind;
    private RichPanelTabbed panelTabBind;
    private RichPopup newAddMenuPopBinding;
    private RichInputListOfValues rolenNameBinding;
    private RichInputListOfValues pkgNameBinding;
    private RichInputListOfValues menuNameBinding;
    private RichSelectOneChoice menuTypeBinding;
    private RichSelectBooleanCheckbox viewCheckBinding;

    public AppSecRoleBean() {
        count=(Integer)getBindings().getOperationBinding("on_load_page").execute();
        System.out.println(">>>>>>>>>>>>>>count value ="+count);
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
    
    public Object resolvElDC(String data) {
    FacesContext fc = FacesContext.getCurrentInstance();
    Application app = fc.getApplication();
    ExpressionFactory elFactory = app.getExpressionFactory();
    ELContext elContext = fc.getELContext();
    ValueExpression valueExp =
    elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
    return valueExp.getValue(elContext);
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

    public void createActionListener(ActionEvent actionEvent) {
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("CreateInsert").execute();
        showPopup(createPopUp,true);
        setReadMode("V");
        setButtonMode("V");
        System.out.println("CREATE BUTTON CALLED");  
        
    }

    public void setCreatePopUp(RichPopup createPopUp) {
        this.createPopUp = createPopUp;
    }

    public RichPopup getCreatePopUp() {
        return createPopUp;
    }

    public void addPopUpDialogListener(DialogEvent dialogEvent) {
        if(DialogEvent.Outcome.ok==dialogEvent.getOutcome()){

            AppRolesAMImpl am = (AppRolesAMImpl)resolvElDC("AppRolesAMDataControl");
            ViewObjectImpl vo = am.getAppSecUsrRole1();
            String activeUsr = "";
            if (vo.getCurrentRow().getAttribute("UsrRoleActv") != null) {
                if (vo.getCurrentRow().getAttribute("UsrRoleActv").equals("N")) {
                    activeUsr = "N";
                } else if (vo.getCurrentRow().getAttribute("UsrRoleActv").equals("Y")) {
                    activeUsr = "Y";
                    System.out.println("UsrRoleActv :" + vo.getCurrentRow().getAttribute("UsrRoleActv"));
                }
            } else {
                activeUsr = "N";
            }
            String rnm = "";
            if (usrRoleName.getValue() != null) {
                rnm = usrRoleName.getValue().toString();
            }
            ViewObjectImpl vo2 = am.getAppSecUsrRole3();
            Row[] rnme = vo2.getFilteredRows("UsrRoleNm", rnm);

            System.out.println("name present = " + rnme);
            if (rnme.length > 1) {
                AdfFacesContext.getCurrentInstance().addPartialTarget(searchTableBind);
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.254']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(usrRoleName.getClientId(), message);
            } else {
                vo.getCurrentRow().setAttribute("UsrRoleActv", activeUsr);
                AdfFacesContext.getCurrentInstance().addPartialTarget(searchTableBind);
                
                //commented becoz no need to get parent iterator
               // DCIteratorBinding parentIter = (DCIteratorBinding)getBindings().get("AppSecUsrRole1Iterator");
                //  System.out.println("parentIter"+parentIter);
                // System.out.println("parentIter.getCurrentRow().getKey()"+parentIter.getCurrentRow().getKey());


                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
                //Key parentKey = parentIter.getCurrentRow().getKey();
                
                //becoz after saving record whole page refresh and refrence is go to first record
                
                //BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Execute").execute();
                //vo.executeQuery();
               // parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));

                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.227']}"));
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);

            }


        }
    }

    public void createPopUpCancelListener(PopupCanceledEvent popupCanceledEvent) {
        
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Rollback").execute();
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Execute").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabBind);
    }

    public void editActionListener(ActionEvent actionEvent) {
        showPopup(createPopUp,true);
        
        AppRolesAMImpl am = (AppRolesAMImpl)resolvElDC("AppRolesAMDataControl");
        ViewObject vo = am.getAppSecUsrRole1();
        Row row =vo.getCurrentRow();
        Integer attribute = (Integer)row.getAttribute("UsrRoleId");
        
    }

    public void setDeletePopUp(RichPopup deletePopUp) {
        this.deletePopUp = deletePopUp;
    }

    public RichPopup getDeletePopUp() {
        return deletePopUp;
    }

    public void DeleteDialogListener(DialogEvent dialogEvent) {
        if(DialogEvent.Outcome.ok==dialogEvent.getOutcome()){
            
             BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Delete").execute();
            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Execute").execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(searchTableBind);

             FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.528']}"));
             message.setSeverity(FacesMessage.SEVERITY_INFO);
             FacesContext fc = FacesContext.getCurrentInstance();
             fc.addMessage(null, message);
             
        }
    }

    public void deletePopUpCancelListenerRole(PopupCanceledEvent popupCanceledEvent) {
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Rollback").execute();
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Execute").execute();
        
                
    }

    public void deleteActionListener(ActionEvent actionEvent) {
        showPopup(deletePopUp,true);
        
    }

    public void addUsrActionListener(ActionEvent actionEvent) {
     //   showPopup(addUsrPopUp,true);
        setReadMode("A");
        setButtonMode("A");
        setButtonReadOnlyMode("A");
     BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("CreateInsert3").execute();
    
     
        
    }

    public void setAddUsrPopUp(RichPopup addUsrPopUp) {
        this.addUsrPopUp = addUsrPopUp;
    }

    public RichPopup getAddUsrPopUp() {
        return addUsrPopUp;
    }

    public void addUsrDialogListener(DialogEvent dialogEvent) {
        if(DialogEvent.Outcome.ok==dialogEvent.getOutcome()){
             
            AppRolesAMImpl am = (AppRolesAMImpl)resolvElDC("AppRolesAMDataControl");
            ViewObject roleVO = am.getAppSecUsrRole1();
            Row row = roleVO.getCurrentRow();
            Integer usrRoleId = (Integer)row.getAttribute("UsrRoleId");
            
            Date createDT = (Date)row.getAttribute("UsrIdCreateDt");
                     
            Integer glbl_usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
            
            Integer glbl_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            
            String glbl_cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
           
             
            AppSecUsrRoleLnkVOImpl roleLnkVO = (AppSecUsrRoleLnkVOImpl)am.getAppSecUsrRoleLnk1();
             //---------------- for deletion of existing role row in role link table
            RowQualifier rq =  new RowQualifier(roleLnkVO); 
            rq.setWhereClause("UsrRoleId = "+usrRoleId);
            Row[] rowr= roleLnkVO.getFilteredRows(rq);
            for(Row rw:rowr) {System.out.println("DELETE = "+rw.getAttribute("UsrId"));}
            //System.out.println("Before Deletion ==== NEW Selected list ====> "+selectedUser);
            for(Row rw:rowr)
            { 
                roleLnkVO.setCurrentRow(rw);
                roleLnkVO.removeCurrentRow();
            }
            roleLnkVO.executeQuery();
            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
            //---------------------------------------------------------------------------------------------------
            
    if(selectedUser==null){
        System.out.println("List is null");
    }else{
            for(Object s:selectedUser)
            {
                Row rw = roleLnkVO.createRow();
                rw.setAttribute("UsrId", s.toString());
               rw.setAttribute("SlocId",glbl_sloc_id);
                
               rw.setAttribute("UsrRoleId",usrRoleId);
                rw.setAttribute("UsrIdCreate",glbl_usr_id);
               rw.setAttribute("UsrIdCreateDt",createDT);
                rw.setAttribute("UsrIdMod",glbl_usr_id);
                rw.setAttribute("UsrIdModDt",createDT);
               //rw.setAttribute("UsrRoleLnkActv",RoleLnkActv);
                rw.setAttribute("UsrRoleCldId",glbl_cld_id);   
                
                roleLnkVO.insertRow(rw);  
            }
            roleLnkVO.executeQuery();
    }
            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
            roleLnkVO.executeQuery();
            
            FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.227']}"));
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
      
    }
    public BindingContainer getBindings() {
            return BindingContext.getCurrent().getCurrentBindingsEntry();
        }

    public void addUsrCancelListener(PopupCanceledEvent popupCanceledEvent) {
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Rollback").execute();
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Execute").execute();
        System.out.println("ADD USER DIALOG OR POPUP CANCEL BUTTON CALLED of ADD user popup");
    }

    public void setUserRoleName(RichOutputText userRoleName) {
        this.userRoleName = userRoleName;
    }

    public RichOutputText getUserRoleName() {
        return userRoleName;
    }

    public void setSelectedUser(List selectedUser) {
        this.selectedUser = selectedUser;
    }
    
    public List getSelectedUser() {
        List selectedList = null;
        AdfFacesContext fctx = AdfFacesContext.getCurrentInstance();
        Map<String, Object> pfs = fctx.getPageFlowScope();
        if (pfs != null) {
            if (pfs.get("selectedList") == null ){                   
                selectedList = attributeListForIterator();
            }
            else
                selectedList = (List)pfs.get("selectedList");
                //System.out.println("getSelectedUser()----in else selectedList = "+ selectedList);
        }
        return selectedList;
    }
    //Helper method to return the selected USERs for an ROLE  
    public List attributeListForIterator() {
       DCBindingContainer bc = (DCBindingContainer)getBindings();
       DCIteratorBinding iter = (DCIteratorBinding)getBindings().get("AppSecUsrRoleLnk2Iterator");
       List attributeList = new ArrayList();
        System.out.println("Before All Selected USERS SIZE === "+ attributeList.size());
       for (Row row : iter.getAllRowsInRange()) {
           attributeList.add(row.getAttribute("UsrId"));
       }
        System.out.println("After iter all selectedList = "+ attributeList);
       return attributeList;
    }

    public void setAllUsers(List allUsers) {
        this.allUsers = allUsers;
        System.out.println("All USER = "+allUsers);
    }

    public List getAllUsers() {
        if (allUsers == null) {
            allUsers = selectItemsForIterator();
        }
        return allUsers;
    }
    //helper method to return All user for allUsers
    public List<SelectItem> selectItemsForIterator() {
        DCBindingContainer bc = (DCBindingContainer)getBindings();
        DCIteratorBinding iter = bc.findIteratorBinding("AppSecUsr1Iterator");
        iter.setRangeSize(-1);
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
          System.out.println("Before All USERS SIZE === "+ selectItems.size());
        for (Row row : iter.getAllRowsInRange()) {
            selectItems.add(new SelectItem(row.getAttribute("UsrId"),
                                           (String)row.getAttribute("UsrName") ));
           // System.out.println("UserID :"+row.getAttribute("UsrId")+" Name :"+row.getAttribute("UsrName"));
            //System.out.println("selectItems == "+selectItems);
        }
        System.out.println("After iter All USERS SIZE === "+ selectItems.size());
        return selectItems;
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
    }

    public void setUsrRoleName(RichInputText usrRoleName) {
        this.usrRoleName = usrRoleName;
    }

    public RichInputText getUsrRoleName() {
        return usrRoleName;
    }

    public void setAddMnuPopUp(RichPopup addMnuPopUp) {
        this.addMnuPopUp = addMnuPopUp;
    }

    public RichPopup getAddMnuPopUp() {
        return addMnuPopUp;
    }

    public void addMnuActionListener(ActionEvent actionEvent) {
        AppRolesAMImpl am = (AppRolesAMImpl)resolvElDC("AppRolesAMDataControl");
        am.getAppSecUsrRoleMnu2().executeQuery();
       // am.getNewLovForMenuName1().executeQuery();
        showPopup(newAddMenuPopBinding,true);
      //  ViewObjectImpl menulov = am.getLovMenuName1();
        //ViewObjectImpl appSecUsrRole1 = am.getAppSecUsrRole1();
       // Row currentRow = appSecUsrRole1.getCurrentRow();
       // if(currentRow.getAttribute("UsrRoleId")!=null){
        //System.out.println("role id in table  "+currentRow.getAttribute("UsrRoleId"));
       // menulov.setNamedWhereClauseParam("BindRoleId", currentRow.getAttribute("UsrRoleId"));
       // }
       // else{
        //    menulov.setNamedWhereClauseParam("BindRoleId", null);
        //}
       // menulov.executeQuery();
       // System.out.println("row count----"+menulov.getAllRowsInRange().length);
        /*  String orgId="";
        ViewObject roleVO = am.getAppSecUsrRole1();
        ViewObjectImpl roleLinkVO = am.getAppSecUsrRoleLnk2();
        Row row = roleVO.getCurrentRow();
        Integer usrRoleId = Integer.parseInt(row.getAttribute("UsrRoleId").toString());
        if(getOrgMenuBindVar.getValue()!=null)
        orgId = getOrgMenuBindVar.getValue().toString();
        System.out.println("orgid after clicking ok button in role menu"+orgId);
        Integer glbl_usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        Integer glbl_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String glbl_cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        System.out.println(">>>>>>>>>>>>>>>>>.. usrRoleId ="+usrRoleId);

        ViewObject appMnuVWvo = am.getAppMnuVW1();
         appMnuVWvo.setWhereClause(" (MNU_ID) NOT IN( SELECT X.USR_MNU_ID " +
            "FROM APP$SEC$USR_ROLE$MNU X WHERE X.USR_ROLE_CLD_ID='"+glbl_cld_id+"' AND X.SLOC_ID="+glbl_sloc_id +" AND X.USR_ROLE_ID="+usrRoleId+" AND X.USR_ROLE_ORG_ID='"+orgId+"' )" +
            "               ");
        appMnuVWvo.executeQuery();
        showPopup(addMnuPopUp,true);
        setAddOrgMenuButtonMode("V");    old code commented*/
        
    }

    

    public void addMnuDialogListener(DialogEvent dialogEvent) {
        
       // am.getDBTransaction().postChanges();
        
        if(dialogEvent.getOutcome().name().equals("ok")){
            
            System.out.println("Add Menu ok button clicked ");
            
            AppRolesAMImpl am = (AppRolesAMImpl)resolvElDC("AppRolesAMDataControl");
            ViewObject appMnuVWvo = am.getAppMnuVW1();
            
            ViewObject roleMnuVO = am.getAppSecUsrRoleMnu2();
            
            ViewObject roleLinkVO = am.getAppSecUsrRoleLnk2();
            
            ViewObject roleVO = am.getAppSecUsrRole1();
            Integer usrRoleId=null;
            Row row = roleVO.getCurrentRow();
            if (row.getAttribute("UsrRoleId")!=null) {
                 usrRoleId = Integer.parseInt(row.getAttribute("UsrRoleId").toString());
            }
            
            
            Date createDT = (Date)row.getAttribute("UsrIdCreateDt");
            if(usrRoleId!=null){
                    System.out.println("Role ID from master table  = "+usrRoleId);
                }
            else{
                    FacesMessage message = new FacesMessage("Can not able to Add in Menu,User Role Is Missing..!!!");   
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }
            
            Integer glbl_usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
            Integer glbl_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            String glbl_cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
            String orgId=null;
            if (getOrgMenuBindVar.getValue()!=null) {
                orgId = getOrgMenuBindVar.getValue().toString();
                System.out.println("orgId in addMNUDialog---->"+orgId);
            }
        else{
                FacesMessage message = new FacesMessage("Can not able to Add in Menu, Organisation Is Missing..!!!");   
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
            if(orgId!=null){
                    System.out.println("org ID from master table  = "+orgId);
                }
            else{
                    FacesMessage message = new FacesMessage("Can not able to Add in Menu, Organisation Is Missing..!!!");   
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }
           
           
           /*  Row[] rowsUsr = roleLinkVO.getFilteredRows("UsrOrgId", orgId);
             System.out.println("Length of filtered array--->"+rowsUsr.length);
            if(rowsUsr.length>0) {
            Integer  usrid= Integer.parseInt(rowsUsr[0].getAttribute("UsrId").toString()) ;
            System.out.println("UsrId is-->"+usrid); */
           
            System.out.println("OrgId---->>>"+orgId);
            
            
            String mode ="I";
           
      /* if ( menuAddModeBinding.getValue()!=null) {
                String add_mode = menuAddModeBinding.getValue().toString();
                System.out.println("add_mode---->"+add_mode);
            }
      
        if (isSelectedbindingVar.getValue()!=null) {
                String select = isSelectedbindingVar.getValue().toString();
                System.out.println("select---->"+select); 
            }
             
      if (editModeBindingVar.getValue()!=null) {
                String edit_mode = editModeBindingVar.getValue().toString();
                System.out.println("edit_mode---->"+edit_mode); 
            }
             
       if (viewBindingVar.getValue()!=null) {
                String view_mode = viewBindingVar.getValue().toString();
                System.out.println("view_mode---->"+view_mode);
            }
            
      if (delModeBindingVar.getValue()!=null) {
                String del_mode = delModeBindingVar.getValue().toString();
                System.out.println("del_mode---->"+del_mode);
            }
             */
        
            
            
          //  Integer roleid=Integer.parseInt(usrRoleidBindingVar.getValue().toString());
        //  usrRoleId
            
           
            
            System.out.println("before create insert  "+ "glbl_usr_id = "+glbl_usr_id+"   glbl_sloc_id = "+glbl_sloc_id+" glbl_cld_id = "+glbl_cld_id);
            System.out.println("Selected"+appMnuVWvo.getCurrentRow().getAttribute("Selected"));
            Row[] rows = appMnuVWvo.getFilteredRows("Selected", "true");
            System.out.println("number of rows selected = "+rows.length);
            for(Row rr:rows){
                
                String addMode="";
                String delMode="";
                String editMode="";
                String viewMode="";
                System.out.println("Menu Id Is ---->"+rr.getAttribute("MnuId"));
                      System.out.println("rr.getAttribute(\"AddModeTrans\")----->"+rr.getAttribute("AddModeTrans"));          
                if (rr.getAttribute("AddModeTrans")!=null) {
                        if (rr.getAttribute("AddModeTrans").equals("true")) {
                            addMode = "Y";
                            System.out.println("mnuAddMode :" + rr.getAttribute("AddModeTrans"));
                        } else if (rr.getAttribute("AddModeTrans").equals("false")) {
                            addMode = "N";
                        }
                }else{
                    addMode = "N";
                }

                    if (rr.getAttribute("DelModeTrans")!=null) {
                        if (rr.getAttribute("DelModeTrans").equals("false")) {
                            delMode = "N";
                        } else if (rr.getAttribute("DelModeTrans").equals("true")) {
                            delMode = "Y";
                            System.out.println("mnuDelMode :" + rr.getAttribute("DelModeTrans"));
                        }
                    }else{
                        delMode = "N";
                    }
                if (rr.getAttribute("EditModeTrans")!=null) {
                        if (rr.getAttribute("EditModeTrans").equals("true")) {
                            editMode = "Y";
                            System.out.println("mnuEditMode :" + rr.getAttribute("EditModeTrans"));
                        } else if (rr.getAttribute("EditModeTrans").equals("false")) {
                            editMode = "N";
                        }
                }else{
                    editMode = "N";
                }
                
                if (rr.getAttribute("ViewModeTrans")!=null) {
                        if (rr.getAttribute("ViewModeTrans").equals("true")) {
                            viewMode = "Y";
                            System.out.println("mnuviewMode :" + rr.getAttribute("ViewModeTrans"));

                        } else if (rr.getAttribute("ViewModeTrans").equals("false")) {
                            viewMode = "N";
                        }

                }else{
                    viewMode = "N";
                }
                
                System.out.println(rr.getAttribute("MnuTypeId"));
                System.out.println("ROLE ID TO BE SET ="+usrRoleId);
                
                Row newRow = roleMnuVO.createRow();
               
                System.out.println("======new row created=====");
                
          //      newRow.setAttribute("UsrRoleId",usrRoleId);
                newRow.setAttribute("UsrMnuId",rr.getAttribute("MnuId"));
                newRow.setAttribute("UsrMnuAddMode",addMode);
                newRow.setAttribute("UsrMnuDelMode",delMode);
                newRow.setAttribute("UsrMnuEditMode",editMode);
                newRow.setAttribute("UsrMnuTypeId",rr.getAttribute("MnuTypeId"));
                newRow.setAttribute("UsrMnuViewMode",viewMode);
                newRow.setAttribute("SlocId",glbl_sloc_id);
                newRow.setAttribute("UsrIdCreate",glbl_usr_id);
                newRow.setAttribute("UsrIdCreateDt",createDT);
                newRow.setAttribute("UsrIdMod",glbl_usr_id);
                newRow.setAttribute("UsrIdModDt",createDT);
                newRow.setAttribute("UsrRoleCldId",glbl_cld_id);
                newRow.setAttribute("UsrRoleOrgId",orgId);
                roleMnuVO.insertRow(newRow); 
             
                
               
                
               
                DCIteratorBinding parentIter = (DCIteratorBinding)getBindings().get("AppSecUsrRole1Iterator");
                
          
                Key parentKey = parentIter.getCurrentRow().getKey();
                roleMnuVO.executeQuery();
                              
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
                System.out.println("Commit Done");
            //getOrgMenuBindVar
            AdfFacesContext.getCurrentInstance().addPartialTarget(getOrgMenuBindVar);
                AdfFacesContext.getCurrentInstance().addPartialTarget(bindRoleMenuTab);
            //code commented beoz full vo execute then    
               // BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Execute").execute();
                //appMnuVWvo.executeQuery(); 
                System.out.println("Commit Done after execute");
                parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
           
                /* else{
                              FacesMessage message = new FacesMessage("Cannot able to replicate in Menu");   
                              message.setSeverity(FacesMessage.SEVERITY_ERROR);
                              FacesContext fc = FacesContext.getCurrentInstance();
                              fc.addMessage(null, message); 
                          }  */ 
            } 
            
            ViewObject dualVO = am.getdual1();
            //dualVO.getCurrentRow().setAttribute("UserOrgTrans","");
            dualVO.executeQuery();
            AdfFacesContext.getCurrentInstance().addPartialTarget(getOrgMenuBindVar);
        }
        else if(dialogEvent.getOutcome().name().equalsIgnoreCase("cancel")){
            AppRolesAMImpl am = (AppRolesAMImpl)resolvElDC("AppRolesAMDataControl");
            ViewObject dualVO = am.getdual1();
            //dualVO.getCurrentRow().setAttribute("UserOrgTrans","");
            dualVO.executeQuery();  
        }
        /* 
        System.out.println("Add Menu ok button clicked ");
        AppRolesAMImpl am = (AppRolesAMImpl)resolvElDC("AppRolesAMDataControl");
        ViewObject appMnuVWvo = am.getAppMnuVW1();
        
        ViewObject roleMnuVO = am.getAppSecUsrRoleMnu2();
        
        ViewObject roleVO = am.getAppSecUsrRole1();
        
        Row row = roleVO.getCurrentRow();
        Integer usrRoleId = (Integer)row.getAttribute("UsrRoleId");
        Date createDT = (Date)row.getAttribute("UsrIdCreateDt");
        System.out.println("Role ID from master table  = "+usrRoleId);
                
        Integer glbl_usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        Integer glbl_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String glbl_cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        
        System.out.println("before create insert  "+ "glbl_usr_id = "+glbl_usr_id+"   glbl_sloc_id = "+glbl_sloc_id+" glbl_cld_id = "+glbl_cld_id);
        
        Row[] rows = appMnuVWvo.getFilteredRows("Selected", true);
        System.out.println("number of rows selected = "+rows.length);
        for(Row rr:rows){
            System.out.println(rr.getAttribute("MnuId"));
            String mnuAddMode=null;
            String mnuDelMode=null;
            String mnuEditMode=null;
            String mnuviewMode=null;
            
            System.out.println("mnuAddMode :"+ mnuAddMode);
            System.out.println("mnuDelMode :"+ mnuDelMode);
            System.out.println("mnuEditMode :"+ mnuEditMode);
            System.out.println("mnuviewMode :"+ mnuviewMode);
            System.out.println(rr.getAttribute("MnuTypeId"));
            System.out.println("ROLE ID TO BE SET ="+usrRoleId);
            
            Row newRow = roleMnuVO.createRow();
            roleMnuVO.insertRow(newRow);
            System.out.println("======new row created=====");
            
            newRow.setAttribute("UsrRoleId",usrRoleId);
            newRow.setAttribute("UsrMnuId",rr.getAttribute("MnuId"));
            newRow.setAttribute("UsrMnuAddMode",rr.getAttribute("UsrMnuAddMode"));
            newRow.setAttribute("UsrMnuDelMode",rr.getAttribute("UsrMnuDelMode"));
            newRow.setAttribute("UsrMnuEditMode",rr.getAttribute("UsrMnuEditMode"));
            newRow.setAttribute("UsrMnuTypeId",rr.getAttribute("MnuTypeId"));
            newRow.setAttribute("UsrMnuViewMode",rr.getAttribute("UsrMnuViewMode"));
            newRow.setAttribute("SlocId",glbl_sloc_id);
            newRow.setAttribute("UsrIdCreate",glbl_usr_id);
            newRow.setAttribute("UsrIdCreateDt",createDT);
            newRow.setAttribute("UsrIdMod",glbl_usr_id);
            newRow.setAttribute("UsrIdModDt",createDT);
            newRow.setAttribute("UsrRoleCldId",glbl_cld_id);
                        
            DCIteratorBinding parentIter = (DCIteratorBinding)getBindings().get("AppSecUsrRole1Iterator");
            
            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
            Key parentKey = parentIter.getCurrentRow().getKey();
            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Execute").execute();
            roleMnuVO.executeQuery();
            
            parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
        } */
                
        /*FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.227']}"));
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, message); */
        
    }
    
    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
            CallableStatement st = null;
            try {
                AppRolesAMImpl am = (AppRolesAMImpl)resolvElDC("AppRolesAMDataControl");
                st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
                st.registerOutParameter(1,sqlReturnType);
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
                    } catch (SQLException e) { throw new JboException(e);
                    }
                }
            }
        }

    public void addMnuCancelListener(PopupCanceledEvent popupCanceledEvent) {
        
         AppRolesAMImpl am = (AppRolesAMImpl)resolvElDC("AppRolesAMDataControl");
        ViewObject appMnuVWvo = am.getAppMnuVW1();
        appMnuVWvo.setWhereClause(null);
        appMnuVWvo.executeQuery(); 
        ViewObject dualVO = am.getdual1();
        //dualVO.getCurrentRow().setAttribute("UserOrgTrans","");
        dualVO.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(getOrgMenuBindVar);
        
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Rollback").execute();
        //BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Execute").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindRoleMenuTab);//bindRoleMenuTab
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchTableBind);
        
    }

    public void deleteMnuActionListener(ActionEvent actionEvent) {
        
        DCIteratorBinding parentIter = (DCIteratorBinding)getBindings().get("AppSecUsrRole1Iterator");
        Key parentKey = parentIter.getCurrentRow().getKey();
        
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Delete2").execute();
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Execute").execute();
                      
        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
        
         /*FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.528']}"));
         message.setSeverity(FacesMessage.SEVERITY_INFO);
         FacesContext fc = FacesContext.getCurrentInstance();
         fc.addMessage(null, message);*/
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void setBindRoleMenuTab(RichTable bindRoleMenuTab) {
        this.bindRoleMenuTab = bindRoleMenuTab;
    }

    public RichTable getBindRoleMenuTab() {
        return bindRoleMenuTab;
    }

    public void setAddMode(RichSelectBooleanCheckbox addMode) {
        this.addMode = addMode;
    }

    public RichSelectBooleanCheckbox getAddMode() {
        return addMode;
    }
    
    public void selectedMnuValueChangeListener(ValueChangeEvent valueChangeEvent) {
        
        Boolean selected = (Boolean)valueChangeEvent.getNewValue();
        //System.out.println("Selected OR NOT ? -------------> "+selected);
        
    }
    public void addMnuCBValueChangeListener(ValueChangeEvent valueChangeEvent) {
        //System.out.println("ADD CB = "+valueChangeEvent.getNewValue()); 
    }

    public void editMnuCBValueChangeListener(ValueChangeEvent valueChangeEvent) {
        //System.out.println("EDIT CB = "+valueChangeEvent.getNewValue()); 
    }

    public void viewMnuCBValueChangeListener(ValueChangeEvent valueChangeEvent) {
        //System.out.println("VIEW CB = "+valueChangeEvent.getNewValue()); 
    }

    public void deleteMnuCBValueChangeListener(ValueChangeEvent valueChangeEvent) {
        //System.out.println("DELETE CB = "+valueChangeEvent.getNewValue()); 
    }

    public void setGetUserRoleBindVar(RichInputListOfValues getUserRoleBindVar) {
        this.getUserRoleBindVar = getUserRoleBindVar;
    }

    public RichInputListOfValues getGetUserRoleBindVar() {
        return getUserRoleBindVar;
    }

    public void searchUserRoleActionListener(ActionEvent actionEvent) {
        AppRolesAMImpl am = (AppRolesAMImpl)resolvElDC("AppRolesAMDataControl");
                Row srcCurr=am.getAppSearchRole1().getCurrentRow();
                if(srcCurr!=null){
                    String RoleName="";
                if(srcCurr.getAttribute("searchRoleTrans")!=null)
                RoleName=srcCurr.getAttribute("searchRoleTrans").toString();
                
                System.out.println("current row att name"+RoleName);
                ViewObjectImpl vo = am.getAppSecUsrRole1();
                ViewCriteria vc = vo.getViewCriteria("AppSecUsrRoleVOCriteria");
                vo.applyViewCriteria(vc);
                vo.setNamedWhereClauseParam("roleNameBind", RoleName);
                vo.executeQuery();
        
                }
                
                //System.out.println(userVo.getRowCount());
                //System.out.println("getUserBindVar.getValue() : " + getUserRoleBindVar.getValue());
                //userVo.setNamedWhereClauseParam("Bind_role", getUserRoleBindVar.getValue());
                //userVo.executeQuery();
                //System.out.println(userVo.getRowCount());
                //userVo.setWhereClause("UserName");

    }

    public void resetUserSearchRoleActionListener(ActionEvent actionEvent) {
        AppRolesAMImpl am = (AppRolesAMImpl)resolvElDC("AppRolesAMDataControl");

        ViewObjectImpl userVo = am.getAppSecUsrRole1();
        ViewObjectImpl searchUserVo = am.getAppSearchRole1();
        ViewObjectImpl vo = am.getAppSecUsrRole1();
        ViewCriteria vc = vo.getViewCriteria("AppSecUsrRoleVOCriteria");
        vo.applyViewCriteria(vc);
        vo.setNamedWhereClauseParam("roleNameBind", null);
        vo.executeQuery();
        searchUserVo.getCurrentRow().setAttribute("searchRoleTrans", null);
        searchUserVo.getCurrentRow().setAttribute("searchRoleTrans", "");
        searchUserVo.executeQuery();
        System.out.println("After execute search vo");
        getUserRoleBindVar.setValue(null);
        getUserRoleBindVar.setValue("");
        AdfFacesContext.getCurrentInstance().addPartialTarget(getUserRoleBindVar);
         //userVo.setWhereClause("UserName");
    }

    public void setMenuAddModeBinding(RichSelectBooleanCheckbox menuAddModeBinding) {
        this.menuAddModeBinding = menuAddModeBinding;
    }

    public RichSelectBooleanCheckbox getMenuAddModeBinding() {
        return menuAddModeBinding;
    }

    public void setIsSelectedbindingVar(RichSelectBooleanCheckbox isSelectedbindingVar) {
        this.isSelectedbindingVar = isSelectedbindingVar;
    }

    public RichSelectBooleanCheckbox getIsSelectedbindingVar() {
        return isSelectedbindingVar;
    }

    public void setEditModeBindingVar(RichSelectBooleanCheckbox editModeBindingVar) {
        this.editModeBindingVar = editModeBindingVar;
    }

    public RichSelectBooleanCheckbox getEditModeBindingVar() {
        return editModeBindingVar;
    }

    public void setViewBindingVar(RichSelectBooleanCheckbox viewBindingVar) {
        this.viewBindingVar = viewBindingVar;
    }

    public RichSelectBooleanCheckbox getViewBindingVar() {
        return viewBindingVar;
    }

    public void setDelModeBindingVar(RichSelectBooleanCheckbox delModeBindingVar) {
        this.delModeBindingVar = delModeBindingVar;
    }

    public RichSelectBooleanCheckbox getDelModeBindingVar() {
        return delModeBindingVar;
    }

    public void setGetOrgBindVar(RichSelectOneChoice getOrgBindVar) {
        this.getOrgBindVar = getOrgBindVar;
    }

    public RichSelectOneChoice getGetOrgBindVar() {
        return getOrgBindVar;
    }

    public void setGetUsrNameBindVar(RichSelectOneChoice getUsrNameBindVar) {
        this.getUsrNameBindVar = getUsrNameBindVar;
    }

    public RichSelectOneChoice getGetUsrNameBindVar() {
        return getUsrNameBindVar;
    }

    public void saveUsrOrgLinkActionListener(ActionEvent actionEvent) {

        AppRolesAMImpl am = (AppRolesAMImpl)resolvElDC("AppRolesAMDataControl");
        ViewObject roleVO = am.getAppSecUsrRoleLnk2();
        //System.out.println("getOrgBindVar.getValue().toString()" + getOrgBindVar.getValue().toString());
        //System.out.println("getUsrNameBindVar.getValue()" + getUsrNameBindVar.getValue());
        Integer usrId = Integer.parseInt(getUsrNameBindVar.getValue().toString());
        //System.out.println("In saveUsrOrgLinkActionListener UsrId---->" + usrId);
        //System.out.println("Integer.parseInt(getUsrNameBindVar.getValue().toString())" + usrId);
        /*  roleVO.getCurrentRow().setAttribute("UsrId", usrId); */
        ViewObject roleVO1 = am.getAppSecUsrRole1();
        Row row = roleVO1.getCurrentRow();
        Integer usrRoleId = (Integer)row.getAttribute("UsrRoleId");
        //System.out.println("(Date)row.getAttribute(\"UsrIdCreateDt\")" + (Date)row.getAttribute("UsrIdCreateDt"));
        Date createDT = (Date)row.getAttribute("UsrIdCreateDt");
        Integer glbl_usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        Integer glbl_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String orgId = getOrgBindVar.getValue().toString();
        String mode = "I";
        String glbl_cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        String Flag =
            (callStoredFunction(Types.VARCHAR, "APP.FN_INS_ORG_USR_ROL_MNU(?,?,?,?,?,?,?)", new Object[] { glbl_cld_id,
                                                                                                           glbl_sloc_id,
                                                                                                           orgId,
                                                                                                           usrId,
                                                                                                           usrRoleId,
                                                                                                           glbl_usr_id,
                                                                                                           mode })).toString();
        System.out.println("Flag--->" + Flag);

        if (Flag.equalsIgnoreCase("Y")) {
            roleVO.getCurrentRow().setAttribute("UsrOrgId", getOrgBindVar.getValue().toString());
            roleVO.getCurrentRow().setAttribute("SlocId", glbl_sloc_id);
            roleVO.getCurrentRow().setAttribute("UsrId", usrId);
            roleVO.getCurrentRow().setAttribute("UsrRoleId", usrRoleId);
            roleVO.getCurrentRow().setAttribute("UsrIdCreate", glbl_usr_id);
            roleVO.getCurrentRow().setAttribute("UsrIdCreateDt", createDT);
            roleVO.getCurrentRow().setAttribute("UsrIdMod", glbl_usr_id);
            roleVO.getCurrentRow().setAttribute("UsrIdModDt", createDT);
            //rw.setAttribute("UsrRoleLnkActv",RoleLnkActv);
            roleVO.getCurrentRow().setAttribute("UsrRoleCldId", glbl_cld_id);
            roleVO.executeQuery();
            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
            String flag =
                (callStoredFunction(Types.VARCHAR, "APP.FN_INS_ORG_USR_ROL_MNU(?,?,?,?,?,?,?)", new Object[] { glbl_cld_id,
                                                                                                               glbl_sloc_id,
                                                                                                               orgId,
                                                                                                               usrId,
                                                                                                               usrRoleId,
                                                                                                               glbl_usr_id,
                                                                                                               mode })).toString();
            System.out.println("Flag--->" + flag);
            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
            setReadMode("V");
            setButtonMode("V");
            setButtonReadOnlyMode("V");
            ViewObject dualVO = am.getdual1();
            dualVO.getCurrentRow().setAttribute("UserOrgTrans", "");
            dualVO.getCurrentRow().setAttribute("userIdTrans", "");
        } else {
            String msg2 = "Can not able to Replicate in Menu...!!";
            //                   FacesMessage message2 = new FacesMessage( (msg2));
            //                   message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg2, null));
        }
    }
    public void setGetOrgMenuBindVar(RichSelectOneChoice getOrgMenuBindVar) {
        this.getOrgMenuBindVar = getOrgMenuBindVar;
    }

    public RichSelectOneChoice getGetOrgMenuBindVar() {
        return getOrgMenuBindVar;
    }

    public void setReadMode(String readMode) {
        this.readMode = readMode;
    }

    public String getReadMode() {
        return readMode;
    }

    public void editUsrOrgActionListener(ActionEvent actionEvent) {
       setReadMode("A");
    }

    public void deleteUsrOrgActionListener(ActionEvent actionEvent) {
        AppRolesAMImpl am = (AppRolesAMImpl)resolvElDC("AppRolesAMDataControl");
        ViewObject roleVO1 = am.getAppSecUsrRole1();
        ViewObjectImpl linkVO=  am.getAppSecUsrRoleLnk2();
        Row row = roleVO1.getCurrentRow();
        Integer usrRoleId = (Integer)row.getAttribute("UsrRoleId");
        Integer glbl_usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        Integer usrId = Integer.parseInt(linkVO.getCurrentRow().getAttribute("UsrId").toString());
        String orgId= linkVO.getCurrentRow().getAttribute("UsrOrgId").toString();
       /*  if (linkVO!=null) {
            if (linkVO.getCurrentRow().getAttribute("UsrId") != null &&
                linkVO.getCurrentRow().getAttribute("UsrOrgId") != null) {
                System.out.println("If BLock--->");
                usrId = 
                orgId =
                System.out.println(orgId);
                System.out.println(usrId);
            } else {
                System.out.println("In else--->");
                String msg2 = "There is no record to delete...!!";
                //                   FacesMessage message2 = new FacesMessage( (msg2));
                //                   message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg2, null));
            }
        } */
       /*  else {
                        System.out.println("In else--->");
                        String msg2 = "There is no record to delete...!!";
                        //                   FacesMessage message2 = new FacesMessage( (msg2));
                        //                   message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg2, null));
                    } */
        /* if(linkVO.getCurrentRow().getAttribute("UsrOrgId")!=null){
            orgId=linkVO.getCurrentRow().getAttribute("UsrOrgId").toString();
            }
        else{
                String  msg2 = "There is no record to delete...!!";
                //                   FacesMessage message2 = new FacesMessage( (msg2));
                //                   message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                   throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,msg2,null));
            } */
        Integer glbl_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
         
        
         String mode="D";
        String glbl_cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        String Flag =
                   (callStoredFunction(Types.VARCHAR, "APP.FN_INS_ORG_USR_ROL_MNU(?,?,?,?,?,?,?)",
               new Object[] {glbl_cld_id ,glbl_sloc_id,orgId,usrId  ,usrRoleId,glbl_usr_id,mode})).toString();
        System.out.println("Flag--->"+Flag);
        if(Flag.equalsIgnoreCase("Y")){
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Delete3").execute();
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
        setReadMode("V");
        setButtonMode("V");
        }
        else{
                String  msg2 = "Can not able to Replicate in Menu...!!";
                //                   FacesMessage message2 = new FacesMessage( (msg2));
                //                   message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                   throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,msg2,null));
            }
        
    }


    public void usrOrgCheckValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Integer usrId=0;
        String orgId="";
        if(object!=null){
               usrId = Integer.parseInt(object.toString());
        }
        if(getOrgBindVar.getValue()!=null){
        orgId =  getOrgBindVar.getValue().toString();
        }
        AppRolesAMImpl am = (AppRolesAMImpl)resolvElDC("AppRolesAMDataControl");
        ViewObjectImpl roleVO = am.getAppSecUsrRoleLnk2();
        RowQualifier rowQualifier = new RowQualifier(roleVO);
        // filtering data using method setWhereClause
           if( orgId!=null ){
                  rowQualifier.setWhereClause("UsrId='"+usrId+"'AND UsrOrgId='"+orgId+"'" );
             // use method getFilteredRows to return Row[] type after filter.
                  //System.out.println("query  "+rowQualifier.getExprStr());
             Row[] filteredRows =null;
                  try {
                 filteredRows = roleVO.getFilteredRows(rowQualifier);
             } catch (Exception e) {
                 // TODO: Add catch code
                 e.printStackTrace();
             }
                  System.out.println("filter "+filteredRows.length);
                  if(filteredRows.length >0){
                      System.out.println("set 1");
                      String  msg2 = "Duplicate Organisation exist for the same user!!!";
         //                   FacesMessage message2 = new FacesMessage( (msg2));
         //                   message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                         throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,msg2,null));
                  }
         }
        
    }

    public void cancelUsrOrgLinkActionListener(ActionEvent actionEvent) {
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Rollback").execute();
        setReadMode("V");
        setButtonMode("V");
        setButtonReadOnlyMode("V");
    }


    public void setButtonMode(String buttonMode) {
        this.buttonMode = buttonMode;
    }

    public String getButtonMode() {
        return buttonMode;
    }

    public void activeUserValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
         if( object.toString()!=null)  {
            
         }
        else{
                String  msg2 = "Active User Field is Required..!!!";
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,msg2,null));
            } 
    }
    public void setActiveBindVar(RichSelectBooleanCheckbox activeBindVar) {
        this.activeBindVar = activeBindVar;
    }

    public RichSelectBooleanCheckbox getActiveBindVar() {
        return activeBindVar;
    }

    public void setButtonReadOnlyMode(String buttonReadOnlyMode) {
        this.buttonReadOnlyMode = buttonReadOnlyMode;
    }

    public String getButtonReadOnlyMode() {
        return buttonReadOnlyMode;
    }

    public void UsrOrgValueChangeListener(ValueChangeEvent valueChangeEvent) {
        Integer glbl_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
         
         String glbl_cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");     
         AppRolesAMImpl am = (AppRolesAMImpl)resolvElDC("AppRolesAMDataControl");
       
        ViewObjectImpl UsrOrgLinkVO = am.getUserOrgLink1();
        ViewObjectImpl dualVO = am.getdual1();
        String org_id=valueChangeEvent.getNewValue().toString();
        dualVO.getCurrentRow().setAttribute("UserOrgTrans", org_id);
        dualVO.getCurrentRow().setAttribute("SlocIdTrans", glbl_sloc_id);
        dualVO.getCurrentRow().setAttribute("CldIdTrans", glbl_cld_id);
        
       UsrOrgLinkVO.setNamedWhereClauseParam("OrgIdBindVar", org_id); 
        UsrOrgLinkVO.setNamedWhereClauseParam("CldIdBindVar", glbl_cld_id); 
        UsrOrgLinkVO.setNamedWhereClauseParam("SlocIdBindVar", glbl_sloc_id); 
        UsrOrgLinkVO.executeQuery();  
        
    }

    public void menuOrgChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
      /*  if(getOrgMenuBindVar.getValue()!=null && getOrgMenuBindVar.getValue()!=""){
            System.out.println("OrgID is "+getOrgMenuBindVar.getValue());
         setAddOrgMenuButtonMode("A");
        
        }
        else{
                setAddOrgMenuButtonMode("V");
            }*/
      AppRolesAMImpl am = (AppRolesAMImpl)resolvElDC("AppRolesAMDataControl");
        dualVOImpl dual1 = am.getdual1();
        Row currentRow = dual1.getCurrentRow();
        //currentRow.setAttribute(arg0, arg1);


    }

    public void setAddOrgMenuButtonMode(String addOrgMenuButtonMode) {
        this.addOrgMenuButtonMode = addOrgMenuButtonMode;
    }

    public String getAddOrgMenuButtonMode() {
        return addOrgMenuButtonMode;
    }

    public void reserveCheckValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        AppRolesAMImpl am = (AppRolesAMImpl)resolvElDC("AppRolesAMDataControl");
                ViewObjectImpl appSecRoleVO = am.getAppSecUsrRole1();
          if (object!=null) {
                    String str=object.toString();
                 if (str.equalsIgnoreCase("true")) {
                        if (appSecRoleVO.getCurrentRow().getAttribute("UsrRoleActv") != null) {
                            String rsv = appSecRoleVO.getCurrentRow().getAttribute("UsrRoleActv").toString();
                            if (rsv.equalsIgnoreCase("Y")) {
                            } else {
                                String msg2 ="Can not able to Add Role , Active Field is Required...!!";
                                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,msg2,null));
                            }
                        } 
                        else {                  
                                String msg2 ="Can not able to Add Role , Active Field is Required...!!";
                                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,msg2,null));
                             }
                    }
                }
                
            
    }

    public void setSearchTableBind(RichTable searchTableBind) {
        this.searchTableBind = searchTableBind;
    }

    public RichTable getSearchTableBind() {
        return searchTableBind;
    }

    public void setPanelTabBind(RichPanelTabbed panelTabBind) {
        this.panelTabBind = panelTabBind;
    }

    public RichPanelTabbed getPanelTabBind() {
        return panelTabBind;
    }

    public void searchByPackageAL(ActionEvent actionEvent) {
        AppRolesAMImpl am = (AppRolesAMImpl)resolvElDC("AppRolesAMDataControl");
        ViewObjectImpl srch = am.getAppSearchRole1();
        AppMnuVWVOImpl menuvo = am.getAppMnuVW1();
        if(srch.getCurrentRow().getAttribute("PckageIdTrans")!=null){
            String pkgid = srch.getCurrentRow().getAttribute("PckageIdTrans").toString();
            System.out.println("Package Id is----"+pkgid);
            menuvo.setNamedWhereClauseParam("BindDocPkgId", pkgid);
        }
        else{
            menuvo.setNamedWhereClauseParam("BindDocPkgId", null);
        }
        menuvo.executeQuery();
    }

    public void resetPackageAL(ActionEvent actionEvent) {
        AppRolesAMImpl am = (AppRolesAMImpl)resolvElDC("AppRolesAMDataControl");
        ViewObjectImpl srch = am.getAppSearchRole1();
        AppMnuVWVOImpl menuvo = am.getAppMnuVW1();
        menuvo.setNamedWhereClauseParam("BindDocPkgId", null);
        menuvo.executeQuery();
        srch.executeQuery();
    }

    public void setNewAddMenuPopBinding(RichPopup newAddMenuPopBinding) {
        this.newAddMenuPopBinding = newAddMenuPopBinding;
    }

    public RichPopup getNewAddMenuPopBinding() {
        return newAddMenuPopBinding;
    }

  /*  public void newAddMenuDL(DialogEvent dialogEvent) {
        
        System.out.println("inside dialog listener---");
    
    
        if(dialogEvent.getOutcome().name().equals("ok"))
        {
            System.out.println("Inside ok action dialog--");
            
            AppRolesAMImpl am = (AppRolesAMImpl)resolvElDC("AppRolesAMDataControl");
            dualVOImpl dual1 = am.getdual1();
            Row currentRow = dual1.getCurrentRow();
            
            String addMode="";
            String delMode="";
            String editMode="";
            String viewMode="";
            
                 
            if (currentRow.getAttribute("AddModeTrans")!=null) {
                System.out.println("addmode curentrowvalue--"+currentRow.getAttribute("AddModeTrans"));
                    if (currentRow.getAttribute("AddModeTrans").equals("Y")) {
                        addMode = "Y";
                        System.out.println("mnuAddMode in true:" + currentRow.getAttribute("AddModeTrans"));
                    } else if (currentRow.getAttribute("AddModeTrans").equals("N")) {
                        addMode = "N";
                        System.out.println("addMode--"+addMode);
                    }
            }else{
                System.out.println("if null add mode--"+currentRow.getAttribute("AddModeTrans"));
                addMode = "N";
            }

                if (currentRow.getAttribute("DelModeTrans")!=null) {
                    if (currentRow.getAttribute("DelModeTrans").equals("N")) {
                        System.out.println("not null but  del current row"+currentRow.getAttribute("DelModeTrans"));
                        delMode = "N";
                        System.out.println("d mode--"+delMode);
                    } else if (currentRow.getAttribute("DelModeTrans").equals("Y")) {
                        delMode = "Y";
                        System.out.println("mnuDelMode :" + currentRow.getAttribute("DelModeTrans"));
                    }
                }else{
                    System.out.println("null current row"+currentRow.getAttribute("DelModeTrans"));
                    delMode = "N";
                }
            if (currentRow.getAttribute("EditModeTrans")!=null) {
                    if (currentRow.getAttribute("EditModeTrans").equals("Y")) {
                        editMode = "Y";
                        System.out.println("mnuEditMode :" + currentRow.getAttribute("EditModeTrans"));
                    } else if (currentRow.getAttribute("EditModeTrans").equals("N")) {
                        editMode = "N";
                        System.out.println("e mode--"+editMode);
                    }
            }else{
                editMode = "N";
            }
            
            if (currentRow.getAttribute("ViewModeTrans")!=null) {
                    if (currentRow.getAttribute("ViewModeTrans").equals("Y")) {
                        viewMode = "Y";
                        System.out.println("mnuviewMode :" + currentRow.getAttribute("ViewModeTrans"));

                    } else if (currentRow.getAttribute("ViewModeTrans").equals("N")) {
                        viewMode = "N";
                        System.out.println("v mode--"+viewMode);
                    }

            }else{
                viewMode = "N";
                System.out.println("in blank view--"+viewMode);
            }
            
            
            ViewObjectImpl menu = am.getAppSecUsrRoleMnu2();
            
  if(addMode=="N" && delMode=="N" && viewMode=="N" && editMode=="N")
        {
                System.out.println("Inside first check-----");
                String msg="Please Select atleast one right!";
                FacesMessage message = new FacesMessage(msg);
                message.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
                
            

        }
  
  else{
            System.out.println("inside else first-------");
            if(currentRow.getAttribute("RoleNameTrans")!=null && currentRow.getAttribute("RoleNameTrans")!="")
            {
                System.out.println("inside rolenm check--");
                if(currentRow.getAttribute("searchPckgNmTrans")!=null && currentRow.getAttribute("searchPckgNmTrans")!="") 
                {
                    System.out.println("inside pkg check--");
                if(currentRow.getAttribute("MenuNmTrans")!=null && currentRow.getAttribute("MenuNmTrans")!="")
                {
                    System.out.println("inside menu check--");
                if(currentRow.getAttribute("MenuTypIdTrans")!=null && currentRow.getAttribute("MenuTypIdTrans")!="")
                {
                    System.out.println("inside menutype check--");
                    System.out.println("add--"+addMode);
                    System.out.println("del---"+delMode);
                    System.out.println("edit--"+editMode);
                    System.out.println("view--"+viewMode);
                    Row createRow = menu.createRow();
                    createRow.setAttribute("UsrRoleId", currentRow.getAttribute("RoleIdTrans"));
                    createRow.setAttribute("UsrMnuId", currentRow.getAttribute("MenuIdTrans"));
                    createRow.setAttribute("UsrMnuTypeId", currentRow.getAttribute("MenuTypIdTrans"));
                    createRow.setAttribute("UsrMnuAddMode", addMode);
                    createRow.setAttribute("UsrMnuDelMode", delMode);
                    createRow.setAttribute("UsrMnuEditMode", editMode);
                    createRow.setAttribute("UsrMnuViewMode", viewMode);
                    menu.insertRow(createRow);
                    getBindings().getOperationBinding("Commit").execute();
                    String msg="Record saved successfully.";
                    FacesMessage message = new FacesMessage(msg);
                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                    
                }
                else
                {
                    String msg="Menu Type is mandatory!";
                    FacesMessage message = new FacesMessage(msg);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(menuTypeBinding.getClientId(), message);
                }
                    
                }
                else
                {
                    String msg="Menu is mandatory!";
                    FacesMessage message = new FacesMessage(msg);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(menuNameBinding.getClientId(), message);
                }
                
                }
                else{
                    String msg="Package is mandatory!";
                    FacesMessage message = new FacesMessage(msg);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(pkgNameBinding.getClientId(), message);
                }
                
            }
            else{
                String msg="Role is mandatory!";
                FacesMessage message = new FacesMessage(msg);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(rolenNameBinding.getClientId(), message);
            }
        }
        }
        else if(dialogEvent.getOutcome().name().equals("cancel"))
        {
            System.out.println("Inside cancel action dialog--");
            AppRolesAMImpl am = (AppRolesAMImpl)resolvElDC("AppRolesAMDataControl");
            am.getdual1().executeQuery();
        }
}*/



    public void setRolenNameBinding(RichInputListOfValues rolenNameBinding) {
        this.rolenNameBinding = rolenNameBinding;
    }

    public RichInputListOfValues getRolenNameBinding() {
        return rolenNameBinding;
    }

    public void setPkgNameBinding(RichInputListOfValues pkgNameBinding) {
        this.pkgNameBinding = pkgNameBinding;
    }

    public RichInputListOfValues getPkgNameBinding() {
        return pkgNameBinding;
    }

    public void setMenuNameBinding(RichInputListOfValues menuNameBinding) {
        this.menuNameBinding = menuNameBinding;
    }

    public RichInputListOfValues getMenuNameBinding() {
        return menuNameBinding;
    }

    public void setMenuTypeBinding(RichSelectOneChoice menuTypeBinding) {
        this.menuTypeBinding = menuTypeBinding;
    }

    public RichSelectOneChoice getMenuTypeBinding() {
        return menuTypeBinding;
    }

    public void submitBtnAL(ActionEvent actionEvent) {
        
            System.out.println("Inside submit action --");
            
            AppRolesAMImpl am = (AppRolesAMImpl)resolvElDC("AppRolesAMDataControl");
            dualVOImpl dual1 = am.getdual1();
            Row currentRow = dual1.getCurrentRow();
            
            String addMode="";
            String delMode="";
            String editMode="";
            String viewMode="";
            String selectAll="";
            
                 
            if (currentRow.getAttribute("AddModeTrans")!=null) {
                System.out.println("addmode curentrowvalue--"+currentRow.getAttribute("AddModeTrans"));
                    if (currentRow.getAttribute("AddModeTrans").equals("Y")) {
                        addMode = "Y";
                        System.out.println("mnuAddMode in true:" + currentRow.getAttribute("AddModeTrans"));
                    } else if (currentRow.getAttribute("AddModeTrans").equals("N")) {
                        addMode = "N";
                        System.out.println("addMode--"+addMode);
                    }
            }else{
                System.out.println("if null add mode--"+currentRow.getAttribute("AddModeTrans"));
                addMode = "N";
            }

                if (currentRow.getAttribute("DelModeTrans")!=null) {
                    if (currentRow.getAttribute("DelModeTrans").equals("N")) {
                        System.out.println("not null but  del current row"+currentRow.getAttribute("DelModeTrans"));
                        delMode = "N";
                        System.out.println("d mode--"+delMode);
                    } else if (currentRow.getAttribute("DelModeTrans").equals("Y")) {
                        delMode = "Y";
                        System.out.println("mnuDelMode :" + currentRow.getAttribute("DelModeTrans"));
                    }
                }else{
                    System.out.println("null current row"+currentRow.getAttribute("DelModeTrans"));
                    delMode = "N";
                }
            if (currentRow.getAttribute("EditModeTrans")!=null) {
                    if (currentRow.getAttribute("EditModeTrans").equals("Y")) {
                        editMode = "Y";
                        System.out.println("mnuEditMode :" + currentRow.getAttribute("EditModeTrans"));
                    } else if (currentRow.getAttribute("EditModeTrans").equals("N")) {
                        editMode = "N";
                        System.out.println("e mode--"+editMode);
                    }
            }else{
                editMode = "N";
            }
            
            if (currentRow.getAttribute("ViewModeTrans")!=null) {
                    if (currentRow.getAttribute("ViewModeTrans").equals("Y")) {
                        viewMode = "Y";
                        System.out.println("mnuviewMode :" + currentRow.getAttribute("ViewModeTrans"));

                    } else if (currentRow.getAttribute("ViewModeTrans").equals("N")) {
                        viewMode = "N";
                        System.out.println("v mode--"+viewMode);
                    }

            }else{
                viewMode = "N";
                System.out.println("in blank view--"+viewMode);
            }
            
            
        if (currentRow.getAttribute("SelectAllMenu")!=null) {
                if (currentRow.getAttribute("SelectAllMenu").equals("Y")) {
                    selectAll = "Y";
                    System.out.println("selectall :" + currentRow.getAttribute("SelectAllMenu"));

                } else if (currentRow.getAttribute("SelectAllMenu").equals("N")) {
                    selectAll = "N";
                    System.out.println("select all--"+selectAll);
                }

        }else{
            selectAll = "N";
            System.out.println("in blank selectall--"+selectAll);
        }
            
            
            
            
            ViewObjectImpl menu = am.getAppSecUsrRoleMnu2();
    
    
       if(selectAll.equalsIgnoreCase("Y"))
        {
                System.out.println("Inside selectall check-----");
            if(currentRow.getAttribute("searchPckgNmTrans")!=null && currentRow.getAttribute("searchPckgNmTrans")!="") 
            {
                System.out.println("inside select all and package name not null now call function---");
                Integer usr = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
                Integer sloc = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
                String org = getOrgBindVar.getValue().toString();
                String cld = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
                String pkg = currentRow.getAttribute("PckageIdTrans").toString();
                System.out.println("Pkg param--->>> "+pkg);
                Integer RoleId = Integer.parseInt(currentRow.getAttribute("RoleIdTrans").toString());
                System.out.println("role id in param--->>>  "+RoleId);
                System.out.println("user in param----> "+usr);
                System.out.println("just before function call----");
                BigDecimal rslt;
         rslt =
                    (BigDecimal)callStoredFunction(Types.NUMERIC, "FN_INS_SEC_USR_ROLE_MENU(?,?,?,?,?,?,?,?,?,?)", new Object[] { cld, sloc,
                                                                                                                 org,
                                                                                                                 RoleId,
                                                                                                                 pkg,
                                                                                                                 addMode,
                                                                                                                 editMode,
                                                                                                                 viewMode,
                                                                                                                 delMode,
                                                                                                                 usr});
           
           System.out.println("function called -------and result is ---> "+rslt);
           if(rslt.compareTo(new BigDecimal(1))==0)
           {
               System.out.println("going to commit---");
               getBindings().getOperationBinding("Commit").execute();
               String msg="Record saved successfully.";
               FacesMessage message = new FacesMessage(msg);
               message.setSeverity(FacesMessage.SEVERITY_INFO);
               FacesContext fc = FacesContext.getCurrentInstance();
               fc.addMessage(null, message);
               newAddMenuPopBinding.hide();
               menu.executeQuery(); 
               am.getNewLovForMenuName1().executeQuery();
               am.getdual1().executeQuery();
           }
                else if(rslt.compareTo(new BigDecimal(-1))==0) 
           {
               System.out.println("somthing went wrong inside function as result is ---> "+rslt);
           }
           else{
               System.out.println("error in function ----");
           }
                
                
            }
            else{
                String msg="Package is mandatory!";
                FacesMessage message = new FacesMessage(msg);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(pkgNameBinding.getClientId(), message);
            }

        }
    
        //else if(currentRow.getAttribute("SelectAllMenu").equals("N"))
        else{
            
            System.out.println("inside else first-------");
            if(currentRow.getAttribute("RoleNameTrans")!=null && currentRow.getAttribute("RoleNameTrans")!="")
            {
                System.out.println("inside rolenm check--");
                if(currentRow.getAttribute("searchPckgNmTrans")!=null && currentRow.getAttribute("searchPckgNmTrans")!="") 
                {
                    System.out.println("inside pkg check--");
                if(currentRow.getAttribute("MenuNmTrans")!=null && currentRow.getAttribute("MenuNmTrans")!="")
                {
                    System.out.println("inside menu check--");
                if(currentRow.getAttribute("MenuTypIdTrans")!=null && currentRow.getAttribute("MenuTypIdTrans")!="")
                {
                    System.out.println("inside menutype check--");
                    System.out.println("add--"+addMode);
                    System.out.println("del---"+delMode);
                    System.out.println("edit--"+editMode);
                    System.out.println("view--"+viewMode);
                    Row createRow = menu.createRow();
                    createRow.setAttribute("UsrRoleId", currentRow.getAttribute("RoleIdTrans"));
                    createRow.setAttribute("UsrMnuId", currentRow.getAttribute("MenuIdTrans"));
                    createRow.setAttribute("UsrMnuTypeId", currentRow.getAttribute("MenuTypIdTrans"));
                    createRow.setAttribute("UsrMnuAddMode", addMode);
                    createRow.setAttribute("UsrMnuDelMode", delMode);
                    createRow.setAttribute("UsrMnuEditMode", editMode);
                    createRow.setAttribute("UsrMnuViewMode", viewMode);
                    menu.insertRow(createRow);
                    getBindings().getOperationBinding("Commit").execute();
                    String msg="Record saved successfully.";
                    FacesMessage message = new FacesMessage(msg);
                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                    newAddMenuPopBinding.hide();
                    menu.executeQuery();
                    am.getNewLovForMenuName1().executeQuery();
                    am.getdual1().executeQuery();
                    
                }
                else
                {
                    String msg="Menu Type is mandatory!";
                    FacesMessage message = new FacesMessage(msg);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(menuTypeBinding.getClientId(), message);
                }
                    
                }
                else
                {
                    String msg="Menu is mandatory!";
                    FacesMessage message = new FacesMessage(msg);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(menuNameBinding.getClientId(), message);
                }
                
                }
                else{
                    String msg="Package is mandatory!";
                    FacesMessage message = new FacesMessage(msg);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(pkgNameBinding.getClientId(), message);
                }
                
            }
            else{
                String msg="Role is mandatory!";
                FacesMessage message = new FacesMessage(msg);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(rolenNameBinding.getClientId(), message);
            }
        }
    }

    public void cancelBtnAL(ActionEvent actionEvent) {
        AppRolesAMImpl am = (AppRolesAMImpl)resolvElDC("AppRolesAMDataControl");
        am.getdual1().executeQuery();
        newAddMenuPopBinding.hide();
        
    }

    public void setViewCheckBinding(RichSelectBooleanCheckbox viewCheckBinding) {
        this.viewCheckBinding = viewCheckBinding;
    }

    public RichSelectBooleanCheckbox getViewCheckBinding() {
        return viewCheckBinding;
    }

    public void selectAllVCL(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getNewValue().equals(true)){
            System.out.println("select new val--"+valueChangeEvent.getNewValue());
            menuNameBinding.setValue("");
            menuNameBinding.setValue(null);
            menuTypeBinding.setValue("");
            menuTypeBinding.setValue(null);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(menuNameBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(menuTypeBinding);
    }

    public void roleNameVCL(ValueChangeEvent valueChangeEvent) {
        
            System.out.println("role new val is---"+valueChangeEvent.getNewValue());
            pkgNameBinding.setValue("");
            menuNameBinding.setValue("");
            menuNameBinding.setValue(null);
            menuTypeBinding.setValue("");
            menuTypeBinding.setValue(null);
       
            getBindings().getOperationBinding("refreshMenu").execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(pkgNameBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(menuNameBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(menuTypeBinding);
        
    }

    public void pkgNmVCL(ValueChangeEvent valueChangeEvent) {
        menuNameBinding.setValue("");
        menuNameBinding.setValue(null);
        menuTypeBinding.setValue("");
        menuTypeBinding.setValue(null);
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
      
        
        getBindings().getOperationBinding("refreshMenu").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(menuNameBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(menuTypeBinding);
    }

    public void newPopCanelListener(PopupCanceledEvent popupCanceledEvent) {
        AppRolesAMImpl am = (AppRolesAMImpl)resolvElDC("AppRolesAMDataControl");
        am.getdual1().executeQuery();
}

    public void searchRoleMenuAL(ActionEvent actionEvent) {
        AppRolesAMImpl am = (AppRolesAMImpl)resolvElDC("AppRolesAMDataControl");
        Row currentRow = am.getdual1().getCurrentRow();
        ViewObjectImpl menu = am.getAppSecUsrRoleMnu2();
        if(currentRow.getAttribute("SearchMenuId")!=null && currentRow.getAttribute("SearchMenuId")!=""){
            System.out.println("menuid in search---"+currentRow.getAttribute("SearchMenuId"));
            menu.setNamedWhereClauseParam("BindMenuId", currentRow.getAttribute("SearchMenuId"));
        }
        else{
            menu.setNamedWhereClauseParam("BindMenuId", null);
        }
        if(currentRow.getAttribute("UserOrgTrans")!=null || currentRow.getAttribute("UserOrgTrans")!=""){
            System.out.println("orgid in search----"+currentRow.getAttribute("UserOrgTrans"));
            menu.setNamedWhereClauseParam("BindOrgId",currentRow.getAttribute("UserOrgTrans"));
        }
        else{
            menu.setNamedWhereClauseParam("BindOrgId",null);
        }
        menu.executeQuery();
        
    }

    public void resetRoleSearchAL(ActionEvent actionEvent) {
        AppRolesAMImpl am = (AppRolesAMImpl)resolvElDC("AppRolesAMDataControl");
        ViewObjectImpl menu = am.getAppSecUsrRoleMnu2();
        am.getdual1().executeQuery();
        menu.setNamedWhereClauseParam("BindOrgId",null);
        menu.setNamedWhereClauseParam("BindMenuId", null);
        menu.executeQuery();
    }
}
