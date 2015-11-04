package appwfservice.view.bean;

import adfmailinterface.view.bean.utill.MailSender;

import appwfservice.model.module.WfServAMImpl;

import appwfservice.model.views.UserMailVOImpl;

import appwfservice.view.bean.util.Encrypt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

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


import javax.print.attribute.standard.Severity;

import javax.servlet.http.HttpServletRequest;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputFile;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;

import oracle.adf.view.rich.component.rich.nav.RichGoLink;
import oracle.adf.view.rich.context.AdfFacesContext;


import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.domain.Date;
import oracle.jbo.server.ViewObjectImpl;

import oracle.jbo.server.util.SQLExceptionConverter;

import oracle.security.xml.ws.addressing.bindings.To;

import org.apache.myfaces.trinidad.component.UIXIterator;
import org.apache.myfaces.trinidad.model.UploadedFile;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class WfServBean {
    private RichSelectOneRadio rdoAction;
    private RichInputText inputRmrk;
    private RichSelectBooleanCheckbox rejectCheck;
    private RichSelectBooleanCheckbox revertCheck;
    private RichSelectOneChoice userLovBind;
    String actionT = "I";
    String tabDisb = "N";
    private RichSelectBooleanCheckbox skipCheck;
    private static int NUMBER = Types.INTEGER;
    private static int VARCHAR = Types.VARCHAR;
    private RichInputText remarkDown;
    private RichTable tabBind;
    private RichPopup popupForTop;
    private RichPopup poupForFI;
    String text1 = null;
    String text2 = null;
    private UploadedFile file;
    StringBuffer _DirPath = null;
    StringBuffer _FileName = new StringBuffer();
    ArrayList<WfAttchmentDC> _FileList = new ArrayList();
    private String _FileDisplayName = "";
    private RichInputFile inputFileBinding;
    private File inputFile;
    private InputStream _inputStream;
    private RichInputFile fileinputfieldBind;
    private RichInputText displaynameBind;
    
    ArrayList<HeaderPicDC> _headerPic = new ArrayList<HeaderPicDC>(10);
    private UIXIterator iteratorImg;
    private Integer maxpic;
    private UIXIterator attachIterator;
    private RichGoLink displayNmBind;
    private RichInputText dispNameInputBind;
    private RichSelectBooleanCheckbox approveCbBind;
    private RichSelectOneChoice rvrtToBinding;
    private RichSelectOneChoice rvertUsrBinding;


    public WfServBean() {
        Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        String org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String wf_id = resolvEl("#{pageFlowScope.WF_ID}").toString();
        Integer doc_id = Integer.parseInt(resolvEl("#{pageFlowScope.Doc_id}").toString());
        String doc_txn_id = resolvEl("#{pageFlowScope.Doc_txn_id}").toString();
        //   Integer doc_type_id=Integer.parseInt(resolvEl("#{pageFlowScope.Doc_type_id}").toString());
        Integer doc_type_id = new Integer(0);
        if (resolvEl("#{pageFlowScope.Doc_type_id}") != null)
            doc_type_id = Integer.parseInt(resolvEl("#{pageFlowScope.Doc_type_id}").toString());
        oracle.jbo.domain.Number amount = new oracle.jbo.domain.Number(0);

        //#{pageFlowScope.Amount}
        String lvl = "";
        //FUNCTION CAN_INT_USR_APRV (p_slocid number , p_orgid varchar2, p_cldid varchar2, P_DOCID NUMBER, P_USRID number, P_DOC_AMT NUMBER)
        try {
            amount = new oracle.jbo.domain.Number(resolvEl("#{pageFlowScope.Amount}").toString());
        } catch (SQLException e) {
            System.out.println("Error on amount=" + e);
        }
        System.out.println("Amount :" + amount);
        //        lvl = callStoredFunction(Types.VARCHAR, "APP.WF_CAN_INT_USR_APRV(?,?,?,?,?,?,?)", new Object[] {
        //                                 sloc_id, org_id, cld_id, doc_id, usr_id, amount, doc_type_id
        //        }).toString();

        System.out.println("level is : " + lvl);
        
         String loc=(String)this.callStoredFunction(Types.VARCHAR, "app.fn_get_app_doc_attach_path(?)", new Object[]{sloc_id});
        
        this._DirPath=new StringBuffer(loc);
        File dir=new File(loc);
        if(dir.exists()){
            System.out.println("A folder with name 'new folder' is already exist in the path");
        }else{
            dir.mkdir();
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

    public Object resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
       // System.out.println("In resolvel="+valueExp.getValue(elContext));
        Object msg =  valueExp.getValue(elContext);
        return msg;
    }

    public String okButton() throws SQLException {
        String action = "A";
        WfServAMImpl am = (WfServAMImpl)resolvElDC("WfServAMDataControl");
        String remark = null;
        Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
      
        String org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String wf_id = resolvEl("#{pageFlowScope.WF_ID}").toString();
        Integer doc_id = Integer.parseInt(resolvEl("#{pageFlowScope.Doc_id}").toString());
        Integer doc_type_id =new Integer(0);
        if(resolvEl("#{pageFlowScope.Doc_type_id}")!=null)
            doc_type_id=Integer.parseInt(resolvEl("#{pageFlowScope.Doc_type_id}").toString());
        String doc_txn_id = resolvEl("#{pageFlowScope.Doc_txn_id}").toString();
        oracle.jbo.domain.Number amount =new  oracle.jbo.domain.Number(resolvEl("#{pageFlowScope.Amount}").toString());
        System.out.println(rdoAction.getValue().toString() + "-----radio action");
        Integer level =
            Integer.parseInt(callStoredFunction(NUMBER, "APP.WF_GET_USR_LEVEL(?,?,?,?,?,?,?)", new Object[] { sloc_id,
                                                                                                                    cld_id,
                                                                                                                    org_id,
                                                                                                                    usr_id,
                                                                                                                    wf_id,
                                                                                                                    doc_id, doc_type_id }).toString());
        Integer usr_id_to=usr_id;
        Integer lvl_to=level;
        String valid="Y";
        if (inputRmrk.getValue() != null) {
            remark = inputRmrk.getValue().toString();
        }
        if (rdoAction.getValue().toString().equalsIgnoreCase("Approve")) {
            text1 = "Document approved and translated.";
            if(_FileList.size() > 0){
                text1 = "Document approved and translated.  And the documents have been added.";
            }
            action = "A";
            actionT = "A";
        
        
        } else if (rdoAction.getValue().toString().equalsIgnoreCase("Reject")) {
            text1 = "Document has been rejected.";
            if(_FileList.size() > 0){
                text1 = "Document has been rejected.  And the documents have been added.";
            }
            
            
            action = "R";
            actionT = "R";
        } else if (rdoAction.getValue().toString().equalsIgnoreCase("Revert")) {
            text1 = "Document has been reverted.";
            if(_FileList.size() > 0){
                text1 = "Document has been reverted.  And the documents have been added.";
            }
            
            action = "V";
            actionT = "V";
            if(am.getDualUser1().getCurrentRow().getAttribute("RvrtUsrLast")!=null)
            {
                    usr_id_to = (Integer)am.getDualUser1().getCurrentRow().getAttribute("RvrtUsrLast");
                    lvl_to = (Integer)am.getDualUser1().getCurrentRow().getAttribute("RvrtLvlTo");
                }
            else
            {
                    FacesMessage Message = new FacesMessage("Please select user.");
                    Message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    ctx.addMessage(rvertUsrBinding.getClientId(), Message);
                return null;
                }
        }

          
        System.out.println(action + "-----4444--");
        String post = "P";
        String res =
            (callStoredFunction(Types.VARCHAR, "APP.WF_INS_TXN(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                new Object[] { sloc_id, cld_id, org_id, doc_id,doc_type_id, wf_id, doc_txn_id,
                                                               usr_id, usr_id_to, level, lvl_to, action, remark, amount,
                                                               post }).toString());
        
        
        //FUNCTION GET_RECENT_TXN_ID(P_SLOC_ID NUMBER , P_CLD_ID VARCHAR2 , P_ORG_ID VARCHAR2 , P_DOCID VARCHAR2, P_TXN_DOCID VARCHAR2)
       System.out.println("RES : "+res);
        Integer creator=((Number)  this.callStoredFunction(Types.NUMERIC, "app.WF_GET_INT_USR(?,?,?,?,?,?)", new Object[]{
                                                                                                                sloc_id,
                                                                                                                cld_id,
                                                                                                                org_id,
                                                                                                                doc_id,
                                                                                                              doc_txn_id,
                                                                                                                doc_type_id
                                                                                                             })).intValue();
        
        
        if (!res.equals("-1")) {
           
            ViewObjectImpl appWfAttchVO1 = am.getAppWfAttchVO1();
            
            Integer userId=null;
            StringBuilder encrLink=null;
            if(action.equalsIgnoreCase("R"))
                userId=creator;
            else   if(action.equalsIgnoreCase("V"))
                userId=usr_id_to;
           
           
            if((!action.equalsIgnoreCase("A"))&& userId != null){
                
               if ( this.getPassword(userId) != null) {
                    try {
                        String encPwd = callStoredFunction(Types.VARCHAR, "PKG_SEC.DCRYPT(?)", new Object[] {
                                                           this.getPassword(userId) }).toString();
                        String link;
                        if (encPwd != null) {
                            link =
                                "cld_id=" + cld_id + "&org_id=" + org_id + "&sloc_id=" + sloc_id + "&wf_id=" + wf_id +
                                "&doc_id=" + doc_id + "&doc_txn_id=" + doc_txn_id + "&doc_typ_id=" + doc_type_id +
                                "&amount=" + amount + "&usr_id=" + userId + "&usr_pwd=" + encPwd;

                            encrLink = Encrypt.encrypt(link);
                        } else
                            link = null;
                        this.sendMail(creator, usr_id, userId, action, encrLink);
                    } catch (Exception e) {
                        // TODO: Add catch code
                        e.printStackTrace();
                    }
               }else{
                   this.sendMail(creator,usr_id,userId, action, null);
               }
            } 
            
            
           
           System.out.println("Action For mail is "+action);
            if(action.equalsIgnoreCase("A"))
                this.sendMail(creator,usr_id,creator, action,null);


            for (WfAttchmentDC list : _FileList) {
                getBindings().getOperationBinding("CreateInsert").execute();
                Row currentRow = appWfAttchVO1.getCurrentRow();
                String nm = "";
                try {//GET_TABLE_NAME                
                    nm =
 callStoredFunction(Types.VARCHAR, "pkg_app_gen.generate_id(?,?,?,?,?)", new Object[] { sloc_id, cld_id, null, org_id,"APP$WF$ATTCH" }).toString();
                    currentRow.setAttribute("AttchFlNm", nm);
                } catch (Exception e) {
                    System.out.println("ERROR IN CALLING FUNCTION :" + e.getMessage());
                }

                currentRow.setAttribute("CldId", cld_id);
                currentRow.setAttribute("SlocId", sloc_id);
                currentRow.setAttribute("OrgId", org_id);
                try {
                    //FUNCTION GET_RECENT_TXN_ID(P_SLOC_ID NUMBER , P_CLD_ID VARCHAR2 , P_ORG_ID VARCHAR2 , P_DOCID VARCHAR2, P_TXN_DOCID VARCHAR2)
                    String re =
                        (callStoredFunction(Types.VARCHAR, "APP.WF_GET_RECENT_TXN_ID(?,?,?,?,?,?)",
                                                            new Object[] { sloc_id, cld_id, org_id, doc_id, doc_txn_id, doc_type_id}).toString());
                    currentRow.setAttribute("TxnId", re);
                } catch (Exception e) {
                    System.out.println("ERROR IN CALLING FUNCTION : "+e.getMessage());
                }
                
                
                currentRow.setAttribute("TxnDt", (Date)Date.getCurrentDate());
                currentRow.setAttribute("DocId", doc_id);
                currentRow.setAttribute("TxnDocId", doc_txn_id);
                //currentRow.setAttribute("AttachFl",doc_txn_id);
                currentRow.setAttribute("AttchFlExtn", list.getAttchedFileExtension());
                System.out.println("FILE P :" + list.getAttchedFilePath() + nm + list.getFileExt());
                currentRow.setAttribute("AttchFlPath", list.getAttchedFilePath() + nm + list.getFileExt());
                currentRow.setAttribute("DispFlNm", list.getAttchedFileDispName());
                currentRow.setAttribute("UsrIdBy", usr_id);
                
                


                OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
                operationBinding.execute();
                if (operationBinding.getErrors().isEmpty()) {

                    if (list.getFileInputStream() != null) {
                        System.out.println("FILE_NAME :" + nm + list.getFileExt());
                        System.out.println("FILE_TYPE :" + list.getAttchedFileExtension());
                        //System.out.println(_FileName);

                        try {
                            System.out.println(list.getFileInputStream());
                            InputStream in = list.getFileInputStream();
                            System.out.println("FILE PATH : " + list.getAttchedFilePath()+nm +list.getFileExt());
                            FileOutputStream out =
                                new FileOutputStream(list.getAttchedFilePath()  + nm + list.getFileExt());
                            byte[] buffer = new byte[8192];
                            int bytesRead = 0;

                            while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
                                out.write(buffer, 0, bytesRead);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println(e.getMessage());
                        }


                    }


                    getBindings().getOperationBinding("Commit").execute();
                    

    

                }
            }
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(popupForTop);
        showPopup(popupForTop, true);
        
        return null;
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

    public void setRdoAction(RichSelectOneRadio rdoAction) {
        this.rdoAction = rdoAction;
    }

    public RichSelectOneRadio getRdoAction() {
        return rdoAction;
    }

    public void setInputRmrk(RichInputText inputRmrk) {
        this.inputRmrk = inputRmrk;
    }

    public RichInputText getInputRmrk() {
        return inputRmrk;
    }

    public void setRejectCheck(RichSelectBooleanCheckbox rejectCheck) {
        this.rejectCheck = rejectCheck;
    }

    public RichSelectBooleanCheckbox getRejectCheck() {
        return rejectCheck;
    }

    public void setRevertCheck(RichSelectBooleanCheckbox revertCheck) {
        this.revertCheck = revertCheck;
    }

    public RichSelectBooleanCheckbox getRevertCheck() {
        return revertCheck;
    }

    public String okButtonDwn() throws SQLException {
        WfServAMImpl am = (WfServAMImpl)resolvElDC("WfServAMDataControl");
        ViewObjectImpl user = am.getDualUser1();
        ViewObjectImpl selectUser = am.getLvlUserSelect1();
        Integer one = 1;
        Row rw[] = selectUser.getFilteredRows("UsrId", one);
        Integer length = rw.length;


        String action = "F";
        String remark = null;
        Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        
        System.out.println("User id :"+usr_id);
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();

        String org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String wf_id = resolvEl("#{pageFlowScope.WF_ID}").toString();
        Integer doc_id = Integer.parseInt(resolvEl("#{pageFlowScope.Doc_id}").toString());
        String doc_txn_id = resolvEl("#{pageFlowScope.Doc_txn_id}").toString();
     //   Integer doc_type_id=Integer.parseInt(resolvEl("#{pageFlowScope.Doc_type_id}").toString());
     Integer doc_type_id =new Integer(0);
     if(resolvEl("#{pageFlowScope.Doc_type_id}")!=null)
         doc_type_id=Integer.parseInt(resolvEl("#{pageFlowScope.Doc_type_id}").toString());
        oracle.jbo.domain.Number amount =new  oracle.jbo.domain.Number(resolvEl("#{pageFlowScope.Amount}").toString());


        String post = "P";
        if (revertCheck.getValue().toString().equalsIgnoreCase("true")) {
            text2 = "Document has been reverted.";
            if(_FileList.size() > 0){
                text2 = "Document has been reverted.  And the documents have been added.";
            }
        
            
            action = "V";
            actionT = "V";
        } else if (rejectCheck.getValue().toString().equalsIgnoreCase("true")) {
            text2 = "Document has been rejected.";
            if(_FileList.size() > 0){
                text2 = "Document has been rejected.  And the documents have been added.";
            }
            
            action = "R";
            actionT = "R";
        }else if(approveCbBind.getValue().toString().equalsIgnoreCase("true")){
                text2 = "Document has been Approved.";
                if(_FileList.size() > 0){
                    text2 = "Document has been Approved.  And the documents have been added.";
                }
                
                action = "A";
                actionT = "A";
            }
        else {
            action = "F";
            actionT = "F";
            text2 = "Document has been forwarded.";
            if(_FileList.size() > 0){
                text2 = "Document has been forwarded.  And the documents have been added.";
            }
            
        }
        System.out.println(text2 + "------");
        if (remarkDown.getValue() != null) {
            remark = remarkDown.getValue().toString();
        }
        Row row = user.getCurrentRow();
        Integer usr_id_to = Integer.parseInt(row.getAttribute("J1").toString());
        System.out.println(action + "--------------action--");
        System.out.println(row.getAttribute("J1") + "-----------------user selected.");
        System.out.println(length + "------------length");
        if (action.equalsIgnoreCase("F") && usr_id_to == 1 && length == 0) {
            FacesMessage Message = new FacesMessage("Please select user.");
            Message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(userLovBind.getClientId(), Message);
          return null;
        }
        else if(action.equalsIgnoreCase("V") && am.getDualUser1().getCurrentRow().getAttribute("RevertUsr")==null){
                FacesMessage Message = new FacesMessage("Please select user.");
                Message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage(rvrtToBinding.getClientId(), Message);
                return null;

            }

        Integer level =
            Integer.parseInt(callStoredFunction(NUMBER, "APP.WF_GET_USR_LEVEL(?,?,?,?,?,?,?)", new Object[] { sloc_id,
                                                                                                                    cld_id,
                                                                                                                    org_id,
                                                                                                                    usr_id,
                                                                                                                    wf_id,
                                                                                                                doc_id ,doc_type_id}).toString());
        Integer level_to = 1;
        if(!action.equalsIgnoreCase("A") && !action.equalsIgnoreCase("V")){
            level_to =
                Integer.parseInt(callStoredFunction(NUMBER, "APP.WF_GET_USR_LEVEL(?,?,?,?,?,?,?)", new Object[] { sloc_id,
                                                                                                                        cld_id,
                                                                                                                        org_id,
                                                                                                                        usr_id_to,
                                                                                                                        wf_id,
                                                                                                                        doc_id, doc_type_id }).toString());
            System.out.println(sloc_id + "----" + cld_id + "-----" + org_id + "-----" + doc_id + "---" + wf_id + "----" +
                               doc_txn_id + "---" + usr_id + "----" + usr_id_to + "----" + level + "----" + level_to +
                               "----" + action + "-----" + remark);
    
        }else
            if(action.equalsIgnoreCase("V"))
        {
                usr_id_to=(Integer)am.getDualUser1().getCurrentRow().getAttribute("RevertUsr");
                level_to=(Integer)am.getDualUser1().getCurrentRow().getAttribute("RvrtLvlTo");
            }
        
        String res = "";
        Integer creator=((Number)  this.callStoredFunction(Types.NUMERIC, "app.WF_GET_INT_USR(?,?,?,?,?,?)", new Object[]{
                                                                                                                sloc_id,
                                                                                                                cld_id,
                                                                                                                org_id,
                                                                                                                doc_id,
                                                                                                                doc_txn_id,
                                                                                                                doc_type_id
                                                                                                             })).intValue();
        
        System.out.println("Document to user is "+usr_id_to);
        if(action.equalsIgnoreCase("A")){
            
            res =
                (callStoredFunction(Types.VARCHAR, "APP.WF_INS_TXN(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                    new Object[] { sloc_id, cld_id, org_id, doc_id, doc_type_id,wf_id, doc_txn_id,
                                                                   usr_id, usr_id, level, level, action, remark, amount,
                                                                   post }).toString());
            
          this.sendMail(creator,creator,usr_id, "A",null);
            System.out.println("Approved !");    
            
        }else{
            System.out.println("From user to "+usr_id+" to user "+usr_id_to);
            System.out.println(sloc_id + "----" + cld_id + "-----" + org_id + "-----" + doc_id + "---" + wf_id + "----" +
                               doc_txn_id + "---" + usr_id + "----" + usr_id_to + "----" + level + "----" + level_to +
                               "----" + action + "-----" + remark);
            res =
                (callStoredFunction(Types.VARCHAR, "APP.WF_INS_TXN(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                    new Object[] { sloc_id, cld_id, org_id, doc_id,doc_type_id, wf_id, doc_txn_id,
                                                                   usr_id, usr_id_to, level, level_to, action, remark, amount,
                                                                   post }).toString());
                System.out.println("Not in approved block");
                System.out.println("Action is "+action + " value is "+action.equalsIgnoreCase("F"));
               System.out.println("Lov value is "+this.getUserLovBind().getValue() +" value is "+this.getUserLovBind().getValue()!= null);
            if( usr_id_to!= null ){

                if ( this.getPassword(usr_id_to) != null ) {
                    try {
                        String encPwd = callStoredFunction(Types.VARCHAR, "PKG_SEC.DCRYPT(?)", new Object[] {
                                                           this.getPassword(usr_id_to) }).toString();
                        String link;
                        StringBuilder encrLink = null;
                        if (encPwd != null) {
                            link =
                                "cld_id=" + cld_id + "&org_id=" + org_id + "&sloc_id=" + sloc_id + "&wf_id=" + wf_id +
                                "&doc_id=" + doc_id + "&doc_txn_id=" + doc_txn_id + "&doc_typ_id=" + doc_type_id +
                                "&amount=" + amount + "&usr_id=" + usr_id_to + "&usr_pwd=" + encPwd;

                            encrLink = Encrypt.encrypt(link);
                        } else
                            link = null;
                        this.sendMail(creator, usr_id, usr_id_to, action, encrLink);
                    } catch (Exception e) {
                        // TODO: Add catch code
                        e.printStackTrace();
                    }
                }else{
                    this.sendMail(creator,usr_id,usr_id_to, action, null);
                }
            }
        }
        System.out.println(res+"----------------------");
        
        if (!res.equals("-1")) {
            am = (WfServAMImpl)resolvElDC("WfServAMDataControl");
            ViewObjectImpl appWfAttchVO1 = am.getAppWfAttchVO1();
            System.out.println("Action is "+action.equalsIgnoreCase("F"));
            System.out.println("User id is "+this.getUserLovBind().getValue());
            

            for (WfAttchmentDC list : _FileList) {
                getBindings().getOperationBinding("CreateInsert").execute();
                Row currentRow = appWfAttchVO1.getCurrentRow();
                String nm = "";
                try {
                    nm =
        callStoredFunction(Types.VARCHAR, "pkg_app_gen.generate_id(?,?,?,?,?)", new Object[] { sloc_id, cld_id, null, org_id,
                                                                                        "APP$WF$ATTCH" }).toString();
                    currentRow.setAttribute("AttchFlNm", nm);
                } catch (Exception e) {
                    System.out.println("ERROR IN CALLING FUNCTION :" + e.getMessage());
                }

                currentRow.setAttribute("CldId", cld_id);
                currentRow.setAttribute("SlocId", sloc_id);
                currentRow.setAttribute("OrgId", org_id);
                try {
                    //FUNCTION GET_RECENT_TXN_ID(P_SLOC_ID NUMBER , P_CLD_ID VARCHAR2 , P_ORG_ID VARCHAR2 , P_DOCID VARCHAR2, P_TXN_DOCID VARCHAR2)
                    String re =
                        (callStoredFunction(Types.VARCHAR, "APP.WF_GET_RECENT_TXN_ID(?,?,?,?,?,?)",
                                                            new Object[] { sloc_id, cld_id, org_id, doc_id, doc_txn_id, doc_type_id}).toString());
                    currentRow.setAttribute("TxnId", re);
                } catch (Exception e) {
                    System.out.println("ERROR IN CALLING FUNCTION : "+e.getMessage());
                }
                currentRow.setAttribute("TxnDt", (Date)Date.getCurrentDate());
                currentRow.setAttribute("DocId", doc_id);
                currentRow.setAttribute("TxnDocId", doc_txn_id);
                //currentRow.setAttribute("AttachFl",doc_txn_id);
                currentRow.setAttribute("AttchFlExtn", list.getAttchedFileExtension());
                System.out.println("FILE P :" + list.getAttchedFilePath() + nm + list.getFileExt());
                currentRow.setAttribute("AttchFlPath", list.getAttchedFilePath() + nm + list.getFileExt());
                currentRow.setAttribute("DispFlNm", list.getAttchedFileDispName());
                currentRow.setAttribute("UsrIdBy", usr_id);
                


                OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
                operationBinding.execute();
                if (operationBinding.getErrors().isEmpty()) {

                    if (list.getFileInputStream() != null) {
                        System.out.println("FILE_NAME :" + nm + list.getFileExt());
                        System.out.println("FILE_TYPE :" + list.getAttchedFileExtension());
                        //System.out.println(_FileName);

                        try {
                            System.out.println(list.getFileInputStream());
                            InputStream in = list.getFileInputStream();
                            System.out.println("FILE PATH : " + list.getAttchedFilePath()+nm +list.getFileExt());
                            FileOutputStream out =
                                new FileOutputStream(list.getAttchedFilePath()  + nm + list.getFileExt());
                            byte[] buffer = new byte[8192];
                            int bytesRead = 0;

                            while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
                                out.write(buffer, 0, bytesRead);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println(e.getMessage());
                        }


                    }


                    getBindings().getOperationBinding("Commit").execute();


                    //System.out.println(file.getFilename() + " : " + list.getAttchedFileExtension() + " : " +
                     //                  list.getAttchedFilePath() + " : " + list.getAttchedFileDispName());

                }
            }
        }
        
        
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(poupForFI);
        showPopup(poupForFI, true);
        return null;
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void skipValueChange(ValueChangeEvent valueChangeEvent) {
        BindingContainer bindings = getBindings();
        DCIteratorBinding iter = (DCIteratorBinding)bindings.get("AppWfLvl1Iterator");
        List rowKey = (List)tabBind.getRowKey();
        Key key = (Key)rowKey.get(0);
        Row row1 = iter.findRowByKeyString(key.toStringFormat(true));
        Integer levelhit = Integer.parseInt(row1.getAttribute("WfLvl").toString());
        System.out.println(row1.getAttribute("WfLvl").toString() + "---work  flow level-----");
        Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        String org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String wf_id = resolvEl("#{pageFlowScope.WF_ID}").toString();
        Integer doc_id = Integer.parseInt(resolvEl("#{pageFlowScope.Doc_id}").toString());
        String doc_txn_id = resolvEl("#{pageFlowScope.Doc_txn_id}").toString();
       // Integer doc_type_id=Integer.parseInt(resolvEl("#{pageFlowScope.Doc_type_id}").toString());
       Integer doc_type_id =new Integer(0);
       if(resolvEl("#{pageFlowScope.Doc_type_id}")!=null)
           doc_type_id=Integer.parseInt(resolvEl("#{pageFlowScope.Doc_type_id}").toString());
        if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
            System.out.println("in the true----");
            WfServAMImpl am = (WfServAMImpl)resolvElDC("WfServAMDataControl");
            ViewObjectImpl v = am.getAppWfLvl1();
            Row curr = v.getCurrentRow();

            RowSetIterator rit = v.createRowSetIterator(null);
            String lvl = curr.getAttribute("WfLvl").toString();
            Integer maxlvl = 0;
            while (rit.hasNext()) {
                Row row = rit.next();
                maxlvl = Integer.parseInt(row.getAttribute("WfLvl").toString());
                lvl = lvl.concat(",").concat(row.getAttribute("WfLvl").toString());
            }
            Integer n = maxlvl + 1;
            lvl = lvl.concat(",").concat(n.toString());
            v.setWhereClause(null);
            v.executeQuery();
            v.setWhereClause("WF_ID = '" + wf_id + "' and org_id = '" + org_id + "' and cld_id = '" + cld_id +
                             "' and WF_LVL in (" + lvl + ")");
            v.executeQuery();

            System.out.println(v.getRowCount() + "-----rowcount");


            ViewObjectImpl usr = am.getLvlUserSelect1();
            usr.setWhereClause(null);
            usr.executeQuery();
            usr.setWhereClause("WF_ID = '" + wf_id + "' and WF_LVL = " + n + " and doc_id = " + doc_id +
                               " and cld_id = '" + cld_id + "' and org_id = '" + org_id + "' and doc_type_id="+doc_type_id);

            usr.executeQuery();

            AdfFacesContext.getCurrentInstance().addPartialTarget(userLovBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(tabBind);
        } else {

            WfServAMImpl am = (WfServAMImpl)resolvElDC("WfServAMDataControl");
            ViewObjectImpl v = am.getAppWfLvl1();
            Row curr = v.getCurrentRow();
            Integer lvl = Integer.parseInt(curr.getAttribute("WfLvl").toString());

            System.out.println(lvl + "-------lvl");
            RowSetIterator rit = v.createRowSetIterator(null);
            String n = "N";
            String maxlvl = curr.getAttribute("WfLvl").toString();
            while (rit.hasNext()) {
                Row row = rit.next();
                maxlvl = maxlvl.concat(",").concat(row.getAttribute("WfLvl").toString());
                Integer num = Integer.parseInt(row.getAttribute("WfLvl").toString());
                if (num > levelhit) {
                    row.setAttribute("Check1", n);

                }
            }

            v.setWhereClause(null);
            v.executeQuery();


            v.setWhereClause("org_id = '" + org_id + "' and cld_id = '" + cld_id + "' and WF_ID = '" + wf_id +
                             "' and WF_LVL <= " + levelhit + " and WF_LVL in (" + maxlvl + ")");
            v.executeQuery();

            System.out.println(v.getRowCount() + "--------rowcount3");
            ViewObjectImpl usr = am.getLvlUserSelect1();
            usr.setWhereClause(null);
            usr.executeQuery();
            usr.setWhereClause("WF_ID = '" + wf_id + "' and WF_LVL = " + levelhit + " and doc_id = " + doc_id +
                               " and cld_id = '" + cld_id + "' and org_id = '" + org_id + "' and doc_type_id="+doc_type_id);
            usr.executeQuery();
            AdfFacesContext.getCurrentInstance().addPartialTarget(userLovBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(tabBind);


        }
    }

    public void rejectValuechangeList(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
            revertCheck.setValue(false);
            approveCbBind.setValue(false);
           // userLovBind.setDisabled(true);
            setTabDisb("Y");

        } else {
            setTabDisb("N");
           // userLovBind.setDisabled(false);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(skipCheck);
        AdfFacesContext.getCurrentInstance().addPartialTarget(userLovBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(revertCheck);
    }

    public void revertvaluechangeList(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
            rejectCheck.setValue(false);
            approveCbBind.setValue(false);
           // userLovBind.setDisabled(true);  
            
            setTabDisb("Y");
        } else {
            setTabDisb("N");
           // userLovBind.setDisabled(false);
           
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(skipCheck);
        AdfFacesContext.getCurrentInstance().addPartialTarget(userLovBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(rejectCheck);
    }

    public void setUserLovBind(RichSelectOneChoice userLovBind) {
        this.userLovBind = userLovBind;
    }

    public RichSelectOneChoice getUserLovBind() {
        return userLovBind;
    }

    public void setTabDisb(String tabDisb) {
        this.tabDisb = tabDisb;
    }

    public String getTabDisb() {
        return tabDisb;
    }

    public void setSkipCheck(RichSelectBooleanCheckbox skipCheck) {
        this.skipCheck = skipCheck;
    }

    public RichSelectBooleanCheckbox getSkipCheck() {
        return skipCheck;
    }

    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        WfServAMImpl am = (WfServAMImpl)resolvElDC("WfServAMDataControl");

        try {
           // System.out.println("Am is "+am);
            //System.out.println("Transatcion is "+am.getDBTransaction());
            st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 2, bindVars[z]);
                    //System.out.println(bindVars[z] + "zzzzzzzzzzzzzzzzzzzzz");
                }
            }
            st.executeUpdate();
            //System.out.println("aaaaaaaaaaaaaaaaaaa");
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

    public void setRemarkDown(RichInputText remarkDown) {
        this.remarkDown = remarkDown;
    }

    public RichInputText getRemarkDown() {
        return remarkDown;
    }

    public void setTabBind(RichTable tabBind) {
        this.tabBind = tabBind;
    }

    public RichTable getTabBind() {
        return tabBind;
    }

    public void remarkValidatorUp(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String remark = object.toString();
        if (remark.contains("--") || remark.contains("'") || remark.contains(";") || remark.contains("\\*") ||
            remark.contains("*/")) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not a valid Expression",
                                                          "-- , ' , ; , \\* , */ not allowed in remark."));
        }
    }

    public void remarkValidatorDwn(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String remark = object.toString();
        if (remark.contains("--") || remark.contains("'") || remark.contains(";") || remark.contains("\\*") ||
            remark.contains("*/")) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Not a valid Expression",
                                                          "-- , ' , ; , \\* , */ not allowed in remark."));
        }
    }

    public void setPopupForTop(RichPopup popupForTop) {
        this.popupForTop = popupForTop;
    }

    public RichPopup getPopupForTop() {
        return popupForTop;
    }

    public String okOnToPPopup() {
        System.out.println(actionT + "----------------");
        return "back";
    }

    public void setPoupForFI(RichPopup poupForFI) {
        this.poupForFI = poupForFI;
    }

    public RichPopup getPoupForFI() {
        return poupForFI;
    }

    public String onOnFiPopup() {
        System.out.println(actionT + "----------------111");
        return "back";
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText1() {
        return text1;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public String getText2() {
        return text2;
    }

    public void setActionT(String actionT) {
        this.actionT = actionT;
    }

    public String getActionT() {
        return actionT;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFileDisplayName(String _FileDisplayName) {
        this._FileDisplayName = _FileDisplayName;
    }

    public String getFileDisplayName() {
        return _FileDisplayName;
    }

    public void setFileList(ArrayList<WfAttchmentDC> _FileList) {
        this._FileList = _FileList;
    }

    public ArrayList<WfAttchmentDC> getFileList() {
        return _FileList;
    }

    public void uploadDocumentListener(ActionEvent actionEvent) {
        System.out.println(_FileDisplayName);
        System.out.println(file);
        if(_FileDisplayName.equals("")){
            FacesMessage Message = new FacesMessage("Please enter Display name.");
            Message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(displaynameBind.getClientId(), Message);
        }
        else if (file != null) {

            //_FileName = new StringBuffer(file.getFilename().toString());
            System.out.println("FILE_NAME :" + file.getFilename());
            System.out.println("FILE_TYPE :" + file.getContentType());
             System.out.println(_FileName);
   
            try {
               _inputStream =  file.getInputStream();
            } catch (Exception e) {
                System.out.println("ERROR IN GETTING FILEINPUTSTREAM : "+e.getMessage());
            }
            _FileList.add(new WfAttchmentDC(file.getFilename(),
                                            file.getContentType().toString(),
                                            _DirPath + "\\" + _FileName,
                                            _FileDisplayName + file.getFilename().substring(file.getFilename().lastIndexOf(".")),
                                            _inputStream,
                                            file.getFilename().substring(file.getFilename().lastIndexOf("."))));
            for (WfAttchmentDC list : _FileList) {
                System.out.println(file.getFilename() + " : " + list.getAttchedFileExtension() + " : " +
                                   list.getAttchedFilePath() + " : " + list.getAttchedFileDispName()+""+list.getFileExt()+ " "+list.getFileInputStream() );

            }
            _FileDisplayName = "";
            file.dispose();
            file = null;
        }else{
            FacesMessage Message = new FacesMessage("Please select a file.");
            Message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(fileinputfieldBind.getClientId(),Message);
        }

    }


    public void setFileinputfieldBind(RichInputFile fileinputfieldBind) {
        this.fileinputfieldBind = fileinputfieldBind;
    }

    public RichInputFile getFileinputfieldBind() {
        return fileinputfieldBind;
    }

    public void setDisplaynameBind(RichInputText displaynameBind) {
        this.displaynameBind = displaynameBind;
    }

    public RichInputText getDisplaynameBind() {
        return displaynameBind;
    }


    public ArrayList<HeaderPicDC> getHeaderPic() {
        Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        String org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String wf_id = resolvEl("#{pageFlowScope.WF_ID}").toString();
        Integer doc_id = Integer.parseInt(resolvEl("#{pageFlowScope.Doc_id}").toString());
        String doc_txn_id = resolvEl("#{pageFlowScope.Doc_txn_id}").toString();
       // Integer doc_type_id=Integer.parseInt(resolvEl("#{pageFlowScope.Doc_type_id}").toString());
       Integer doc_type_id =new Integer(0);
       if(resolvEl("#{pageFlowScope.Doc_type_id}")!=null)
           doc_type_id=Integer.parseInt(resolvEl("#{pageFlowScope.Doc_type_id}").toString());
        Integer level =
            Integer.parseInt(callStoredFunction(NUMBER, "APP.WF_GET_TOP_LVL(?,?,?,?)", new Object[] { sloc_id,
                                                                                                                    cld_id,
                                                                                                                  org_id,
                                                                                                                wf_id}).toString());
        //FUNCTION GET_DOC_WF_CUR_LVL(P_SLOCID NUMBER, P_CLDID VARCHAR2, P_ORGID VARCHAR2,P_DOCID NUMBER,  P_TXN_DOCID VARCHAR2) RETURN NUMBER;
        Integer currLevel =
            Integer.parseInt(callStoredFunction(NUMBER, "APP.WF_GET_CUR_LVL(?,?,?,?,?,?)", new Object[] { sloc_id,
                                                                                                                    cld_id,
                                                                                                                  org_id,
                                                                                                                     doc_id,
                                                                                                                doc_txn_id, doc_type_id}).toString());
        _headerPic.clear();
        System.out.println("LEVEL : "+level);
        System.out.println("CURR LEVEL :"+currLevel);
        if(level >0){
            for (int i = 1; i <= level; i++) {
                if (i <= currLevel) {//i <= currLevel
                try {
                        //get_lvl_action(p_sloc_id number , p_org_id varchar2 , p_cld_id varchar2 , P_txn_doc_id varchar2 , p_lvl number)
                        String s = callStoredFunction(Types.VARCHAR, "APP.WF_get_lvl_action(?,?,?,?,?,?,?)", new Object[] { sloc_id,org_id,cld_id,doc_id,doc_txn_id,i,doc_type_id}).toString();
                       _headerPic.add(new HeaderPicDC(s,i));   
                      // System.out.println("LEVEL ACTION : "+s);
                    } catch (Exception e) {
                       //System.out.println("ERROR IN GETTING LEVEL ACTION : "+e.getMessage());
                       e.printStackTrace();
                    }
                    
                } else {
                    _headerPic.add(new HeaderPicDC("Z",i));
                }

            }
        }
        /* for(ArrayList<String> h:_headerPic){
            System.out.println("ACTION IS : "+h);
        } */
        
        
        //System.out.println("I : "+_headerPic.size());
        return _headerPic;
    }

    public void setIteratorImg(UIXIterator iteratorImg) {
    
        this.iteratorImg = iteratorImg;
    }

    public UIXIterator getIteratorImg() {
        return iteratorImg;
    }
    public Integer getmaxpic(){
        maxpic = _headerPic.size();
        //System.out.println("maxpic : "+maxpic);
        return maxpic; 
    }

    public void DelAttchedAttchmentListnor(ActionEvent actionEvent) {
        WfAttchmentDC f = new WfAttchmentDC();
        System.out.println("NAME : "+dispNameInputBind.getValue());
        if(!(dispNameInputBind.getValue() == null)){
            for(WfAttchmentDC w: getFileList()){
                if(w.getAttchedFileDispName().equals(dispNameInputBind.getValue())){
                    f = w;
                    System.out.println("Removed!"+dispNameInputBind.getValue());
                }
            }
            if(f != null){    
        _FileList.remove(f);
                System.out.println("Removed!"+dispNameInputBind.getValue());
            }
        
        }
        
        
        
    }

    public void setAttachIterator(UIXIterator attachIterator) {
        this.attachIterator = attachIterator;
    }

    public UIXIterator getAttachIterator() {
        return attachIterator;
    }

    public void setDisplayNmBind(RichGoLink displayNmBind) {
        this.displayNmBind = displayNmBind;
    }

    public RichGoLink getDisplayNmBind() {
        return displayNmBind;
    }

    public void setDispNameInputBind(RichInputText dispNameInputBind) {
        this.dispNameInputBind = dispNameInputBind;
    }

    public RichInputText getDispNameInputBind() {
        return dispNameInputBind;
    }

    public void setApproveCbBind(RichSelectBooleanCheckbox approveCbBind) {
        this.approveCbBind = approveCbBind;
    }

    public RichSelectBooleanCheckbox getApproveCbBind() {
        return approveCbBind;
    }

    public void approveCbVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
            rejectCheck.setValue(false);
            revertCheck.setValue(false);
          //  userLovBind.setDisabled(true);
            setTabDisb("Y");
        } else {
            setTabDisb("N");
        //    userLovBind.setDisabled(false);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(skipCheck);
        AdfFacesContext.getCurrentInstance().addPartialTarget(userLovBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(rejectCheck);
    }
    
    
    public boolean getApproveMode(){
        OperationBinding binding = this.getBindings().getOperationBinding("checkIfUserIsAuthorisedToApprove");
        binding.execute();
        if(binding.getResult().equals((Object)true)){
            return true;
        }else{
            return false;    
        }
        
        
    }

    public void actionVCE(ValueChangeEvent valueChangeEvent) {
       /*  if(valueChangeEvent.getNewValue()!=null && valueChangeEvent.getNewValue().toString().equals("Revert")){
       
        } */
    }

    public void setRvrtToBinding(RichSelectOneChoice rvrtToBinding) {
        this.rvrtToBinding = rvrtToBinding;
    }

    public RichSelectOneChoice getRvrtToBinding() {
        return rvrtToBinding;
    }

    public void setRvertUsrBinding(RichSelectOneChoice rvertUsrBinding) {
        this.rvertUsrBinding = rvertUsrBinding;
    }

    public RichSelectOneChoice getRvertUsrBinding() {
        return rvertUsrBinding;
    }
    
    
    public void sendMail(Integer creator,Integer from,Integer recTo,String mode,StringBuilder msg){
        try {
            if (isSendingValid(mode,creator,from,recTo)) {
                System.out.println("Trying to send Mail" + recTo);
                String message = "Text";
                String subject = "Subject";
                Map<String, Object> map = this.getMailDetail(recTo);
                System.out.println("Map is " + map);
                Map<String, Object> info = null;
                try {
                    info = this.getMailInfo();
                    System.out.println("Ifo is " + info);
                } catch (SQLException e) {
                }
                if ((!map.containsValue(null)) && info != null) {
                    if (mode.equals("A")) {
                        message =
                            "Document has been approved " + "<br> Document Name :" + info.get("DocName") +
                            "<br> Document Amount :" + info.get("Amount") + "<br> Approved By :" + info.get("Sender");
                        subject = "Approve";


                    } else if (mode.equals("F")) {
                        if (msg == null) {
                            message =
                                "Document has been Forwarded to You" + "<br> Document Name :" + info.get("DocName") +
                                "<br> Document Amount :" + info.get("Amount") + "<br> Forwarded By :" +
                                info.get("Sender");
                        } else {
                            System.out.println("Link is " + msg);
                            message =
                                "Document is pending at You." + "<br> Document Name :" + info.get("DocName") +
                                "<br> Document Amount :" + info.get("Amount") + "<br> Forwarded By :" +
                                info.get("Sender") +
                                "<br> To Verify the Document Please Click on link .  <br><a href=\"" +
                                this.getURL(msg) + "\">Click Here</a> ";
                        }
                        subject = "Forward";
                    } else if(mode.equalsIgnoreCase("R")){
                        
                        message =
                            "Document is rejected." + "<br> Document Name :" + info.get("DocName") +
                            "<br> Document Amount :" + info.get("Amount") + "<br> Rejected By :" +
                            info.get("Sender") +
                            "<br> To Verify the Document Please Click on link .  <br><a href=\"" +
                            this.getURL(msg) + "\">Click Here</a> ";
                        
                        subject="Reject";
                    }else if(mode.equalsIgnoreCase("V")){
                        message =
                            "Document is reverted." + "<br> Document Name :" + info.get("DocName") +
                            "<br> Document Amount :" + info.get("Amount") + "<br> Reverted By :" +
                            info.get("Sender") +
                            "<br> To Verify the Document Please Click on link .  <br><a href=\"" +
                            this.getURL(msg) + "\">Click Here</a> ";
                        
                        subject="Revert";
                    }

                    map.put("To", this.getToId(recTo));
                    System.out.println("Map is " + map);

                    MailSender sender =
                        new MailSender(map.get("Server").toString(), map.get("Security").toString(),
                                       Integer.parseInt(map.get("Port").toString()), new String[] {
                                       map.get("To").toString() }, null, null, subject, message, null);

                    sender.send(map.get("From").toString(), map.get("Password").toString());
                }
            }
            
            
          
      
        } catch (Exception nfe) {
            nfe.printStackTrace();
        }
        
    }
    
    
    public Map<String,Object> getMailDetail(Integer recTo) {
          CallableStatement st = null;
          try {// wf_get_creator
          WfServAMImpl am = (WfServAMImpl)resolvElDC("WfServAMDataControl");
               st = am.getDBTransaction().createCallableStatement("begin  app.PR_ALRT_GET_MAIL_CFG(?,?,?,?,?,?) ;end;", 0);
               st.setObject(1, null);
               st.registerOutParameter(2,Types.VARCHAR);
               st.registerOutParameter(3,Types.VARCHAR);
               st.registerOutParameter(4,Types.VARCHAR);
               st.registerOutParameter(5,Types.VARCHAR);
               st.registerOutParameter(6,Types.VARCHAR);
               st.executeUpdate();
              
              HashMap<String,Object> map=new HashMap<String,Object>();
              map.put("Server",st.getObject(2).toString());
              map.put("Port",st.getObject(3).toString());
              map.put("Security",st.getObject(4).toString());
              map.put("From",st.getObject(5).toString());
              map.put("Password",st.getObject(6).toString());
               
              map.put("To", this.getToId(recTo));
              

              return map;
          } catch (SQLException e) {
              e.printStackTrace();
//             int end = e.getMessage().indexOf("\n");
//              //  throw new JboException(e.getMessage().substring(11, end));
//              String msg = e.getMessage();
//              FacesMessage ermsg = new FacesMessage(msg);
//              ermsg.setSeverity(FacesMessage.SEVERITY_ERROR);
//              FacesContext.getCurrentInstance().addMessage(null, ermsg);
             throw new JboException(e);
          } finally {
              if (st != null) {
                  try {
                      st.close();
                  } catch (SQLException e) {
                        e.printStackTrace();}
              }
          }
      }
    
    public String getToId(Integer recTo){
          WfServAMImpl am = (WfServAMImpl)resolvElDC("WfServAMDataControl");  
          Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
          ((UserMailVOImpl)am.getUserMail1()).setBindSlocId(sloc_id);
          ((UserMailVOImpl)am.getUserMail1()).setBindUserId(recTo);
           am.getUserMail1().executeQuery();
           if( am.getUserMail1().getRowCount()>0){
               return am.getUserMail1().first().getAttribute("UsrMailId").toString();
           }
          return null;
      }
    
    public String getPassword(Integer recTo){
           System.out.println("Getting password"); 
            
          WfServAMImpl am = (WfServAMImpl)resolvElDC("WfServAMDataControl");  
          Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
          ((UserMailVOImpl)am.getUserMail1()).setBindSlocId(sloc_id);
          ((UserMailVOImpl)am.getUserMail1()).setBindUserId(recTo);
           am.getUserMail1().executeQuery();
           if( am.getUserMail1().getRowCount()>0){
               return am.getUserMail1().first().getAttribute("UsrPwd").toString();
           }
          return null;
      }
    
     public String getSenderName(Integer recTo){
                  WfServAMImpl am = (WfServAMImpl)resolvElDC("WfServAMDataControl");  
                  Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
                  ((UserMailVOImpl)am.getUserMail1()).setBindSlocId(sloc_id);
                  ((UserMailVOImpl)am.getUserMail1()).setBindUserId(recTo);
                   am.getUserMail1().executeQuery();
                   if( am.getUserMail1().getRowCount()>0){
                       return am.getUserMail1().first().getAttribute("UsrName").toString();
                   }
                  return null;
              }
    
    public String getURL(StringBuilder msg){
        String url=((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURL().toString();
        String[] path=url.split("faces");
        return path[0]+"faces/UserWfAction.jspx?aqnx="+msg;
    }
    
    public Map<String,Object> getMailInfo() throws SQLException {
        Map<String,Object> map=new HashMap<String,Object>();
        oracle.jbo.domain.Number amount =new  oracle.jbo.domain.Number(resolvEl("#{pageFlowScope.Amount}").toString());
        String docNm = (String)callStoredFunction(VARCHAR,"App_Get_Glbl_Doc_Nm(?)" , new Object[]{  Integer.parseInt(resolvEl("#{pageFlowScope.Doc_id}").toString())});
        String sender=this.getSenderName( Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString()));
        map.put("Sender", sender);
        map.put("DocName",docNm);
        map.put("Amount", amount);
        return map;
    }
    
    
    public Boolean isSendingValid(String type,Integer creator,Integer from,Integer to){
        String result="N";
       
        String cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        String orgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        Integer slocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        
        Integer docId = Integer.parseInt(resolvEl("#{pageFlowScope.Doc_id}").toString());
        Integer docTypeId =new Integer(0);
        if(resolvEl("#{pageFlowScope.Doc_type_id}")!=null)
            docTypeId=Integer.parseInt(resolvEl("#{pageFlowScope.Doc_type_id}").toString());
        String docTxnId = resolvEl("#{pageFlowScope.Doc_txn_id}").toString();
        
     // System.out.println("Type is "+type + " user id "+to +"message is "+this.getMessage(type,from,to));  
        oracle.jbo.domain.Number amount;
        try {
            amount = new oracle.jbo.domain.Number(resolvEl("#{pageFlowScope.Amount}").toString());
  
        result=(String)this.callStoredFunction(Types.VARCHAR, "FN_CHECK_ACTION_AND_INS_ALRT(?,?,?,?,?,?,?,?,?,?,?,?)",new Object[]{slocId,
                                                                                                                              cldId,
                                                                                                                              orgId,
                                                                                                                              docId,
                                                                                                                              docTypeId,
                                                                                                                              docTxnId,
                                                                                                                              type,
                                                                                                                              creator,
                                                                                                                                from,
                                                                                                                                 to,
                                                                                                                                 amount,
                                                                                                                                this.getMessage(type,from,to)
                                                                                                                                });
   
        
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Rsult is "+result);
        if(result.equalsIgnoreCase("Y"))
        return true;
        else
        return false;
    
    }
    
    public String getMessage(String type,Integer userId,Integer to){
        String result="Y";
        
        String cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        String orgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String hoOrgId = null;
        Integer slocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        
        Integer docId = Integer.parseInt(resolvEl("#{pageFlowScope.Doc_id}").toString());
        Integer docTypeId =new Integer(0);
        if(resolvEl("#{pageFlowScope.Doc_type_id}")!=null)
            docTypeId=Integer.parseInt(resolvEl("#{pageFlowScope.Doc_type_id}").toString());
        String docTxnId = resolvEl("#{pageFlowScope.Doc_txn_id}").toString();
        
        
        
        
            String mode=type;
            Map<String, Object> info = null;
            try {
                info = this.getMailInfo();
                System.out.println("Ifo is " + info);
            } catch (SQLException e) {
            }
            String message="None";
            
            
            if (mode.equals("A")) {
                message =
                    "Document has been approved " + "<br> Document Name :" + info.get("DocName") +
                    "<br> Document Amount :" + info.get("Amount") + "<br> Approved By :" + info.get("Sender");
               

            } else if (mode.equals("F")) {
                
                    message =
                        "Document has been Forwarded to "+this.getSenderName(to) + "<br> Document Name :" + info.get("DocName") +
                        "<br> Document Amount :" + info.get("Amount") + "<br> Forwarded By :" +
                        info.get("Sender");
                
            } else if(mode.equalsIgnoreCase("R")){
                
                message =
                    "Document is rejected." + "<br> Document Name :" + info.get("DocName") +
                    "<br> Document Amount :" + info.get("Amount") + "<br> Rejected By :" +
                    info.get("Sender");
                
               
            }else if(mode.equalsIgnoreCase("V")){
                message =
                    "Document is reverted." + "<br> Document Name :" + info.get("DocName") +
                    "<br> Document Amount :" + info.get("Amount") + "<br> Reverted By :" +
                    info.get("Sender") ;
            }
         
         return message;
    }
    
    public void updateResult (){
       
        try{
           this.actionT="A";
            
        }catch(Exception e){
            e.printStackTrace();
           
        }
        
        return;
    }

}
