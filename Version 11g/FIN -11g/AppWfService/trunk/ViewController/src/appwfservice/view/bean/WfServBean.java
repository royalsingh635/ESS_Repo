package appwfservice.view.bean;

import appwfservice.model.module.WfServAMImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

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
    StringBuffer _DirPath = new StringBuffer("D:\\DOCS");
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


    public WfServBean() {
        File dir=new File("D:/DOCS");
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

    public String okButton() throws SQLException {
        String action = "A";
        String remark = null;
        Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        String org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String wf_id = resolvEl("#{pageFlowScope.WF_ID}").toString();
        Integer doc_id = Integer.parseInt(resolvEl("#{pageFlowScope.Doc_id}").toString());
        String doc_txn_id = resolvEl("#{pageFlowScope.Doc_txn_id}").toString();
        oracle.jbo.domain.Number amount = new oracle.jbo.domain.Number(resolvEl("#{pageFlowScope.Amount}"));
        System.out.println(rdoAction.getValue().toString() + "-----radio action");
        Integer level =
            Integer.parseInt(callStoredFunction(NUMBER, "APP.PKG_APP_WF.GET_USR_LEVEL(?,?,?,?,?,?)", new Object[] { sloc_id,
                                                                                                                    cld_id,
                                                                                                                    org_id,
                                                                                                                    usr_id,
                                                                                                                    wf_id,
                                                                                                                    doc_id }).toString());
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

        }


        System.out.println(action + "-----4444--");
        String post = "P";
        String res =
            (callStoredFunction(Types.VARCHAR, "APP.PKG_APP_WF.INS_TXN(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                new Object[] { sloc_id, cld_id, org_id, doc_id, wf_id, doc_txn_id,
                                                               usr_id, usr_id, level, level, action, remark, amount,
                                                               post }).toString());
        
        
        //FUNCTION GET_RECENT_TXN_ID(P_SLOC_ID NUMBER , P_CLD_ID VARCHAR2 , P_ORG_ID VARCHAR2 , P_DOCID VARCHAR2, P_TXN_DOCID VARCHAR2)
       System.out.println("RES : "+res);
        if (!res.equals("-1")) {
            WfServAMImpl am = (WfServAMImpl)resolvElDC("WfServAMDataControl");
            ViewObjectImpl appWfAttchVO1 = am.getAppWfAttchVO1();


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
                        (callStoredFunction(Types.VARCHAR, "APP.PKG_APP_WF.GET_RECENT_TXN_ID(?,?,?,?,?)",
                                                            new Object[] { sloc_id, cld_id, org_id, doc_id, doc_txn_id}).toString());
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

        oracle.jbo.domain.Number amount = new oracle.jbo.domain.Number(resolvEl("#{pageFlowScope.Amount}"));


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

        }

        Integer level =
            Integer.parseInt(callStoredFunction(NUMBER, "APP.PKG_APP_WF.GET_USR_LEVEL(?,?,?,?,?,?)", new Object[] { sloc_id,
                                                                                                                    cld_id,
                                                                                                                    org_id,
                                                                                                                    usr_id,
                                                                                                                    wf_id,
                                                                                                                doc_id }).toString());
        Integer level_to = 1;
        if(!action.equalsIgnoreCase("A")){
            level_to =
                Integer.parseInt(callStoredFunction(NUMBER, "APP.PKG_APP_WF.GET_USR_LEVEL(?,?,?,?,?,?)", new Object[] { sloc_id,
                                                                                                                        cld_id,
                                                                                                                        org_id,
                                                                                                                        usr_id_to,
                                                                                                                        wf_id,
                                                                                                                        doc_id }).toString());
            System.out.println(sloc_id + "----" + cld_id + "-----" + org_id + "-----" + doc_id + "---" + wf_id + "----" +
                               doc_txn_id + "---" + usr_id + "----" + usr_id_to + "----" + level + "----" + level_to +
                               "----" + action + "-----" + remark);
    
        }
        String res = "";
        if(action.equalsIgnoreCase("A")){
            
            res =
                (callStoredFunction(Types.VARCHAR, "APP.PKG_APP_WF.INS_TXN(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                    new Object[] { sloc_id, cld_id, org_id, doc_id, wf_id, doc_txn_id,
                                                                   usr_id, usr_id, level, level, action, remark, amount,
                                                                   post }).toString());
            System.out.println("Approved !");    
        }else{
            res =
                (callStoredFunction(Types.VARCHAR, "APP.PKG_APP_WF.INS_TXN(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                                    new Object[] { sloc_id, cld_id, org_id, doc_id, wf_id, doc_txn_id,
                                                                   usr_id, usr_id_to, level, level_to, action, remark, amount,
                                                                   post }).toString());
                System.out.println("Not in approved block");
        }
        System.out.println(res+"----------------------");
        
        if (!res.equals("-1")) {
            am = (WfServAMImpl)resolvElDC("WfServAMDataControl");
            ViewObjectImpl appWfAttchVO1 = am.getAppWfAttchVO1();


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
                        (callStoredFunction(Types.VARCHAR, "APP.PKG_APP_WF.GET_RECENT_TXN_ID(?,?,?,?,?)",
                                                            new Object[] { sloc_id, cld_id, org_id, doc_id, doc_txn_id}).toString());
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
                               " and cld_id = '" + cld_id + "' and org_id = '" + org_id + "'");

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
                               " and cld_id = '" + cld_id + "' and org_id = '" + org_id + "'");
            usr.executeQuery();
            AdfFacesContext.getCurrentInstance().addPartialTarget(userLovBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(tabBind);


        }
    }

    public void rejectValuechangeList(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
            revertCheck.setValue(false);
            approveCbBind.setValue(false);
            userLovBind.setDisabled(true);
            setTabDisb("Y");

        } else {
            setTabDisb("N");
            userLovBind.setDisabled(false);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(skipCheck);
        AdfFacesContext.getCurrentInstance().addPartialTarget(userLovBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(revertCheck);
    }

    public void revertvaluechangeList(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
            rejectCheck.setValue(false);
            approveCbBind.setValue(false);
            userLovBind.setDisabled(true);
            setTabDisb("Y");
        } else {
            setTabDisb("N");
            userLovBind.setDisabled(false);
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
        Integer level =
            Integer.parseInt(callStoredFunction(NUMBER, "APP.PKG_APP_WF.GET_DOC_WF_TOP_LVL(?,?,?,?)", new Object[] { sloc_id,
                                                                                                                    cld_id,
                                                                                                                  org_id,
                                                                                                                wf_id}).toString());
        //FUNCTION GET_DOC_WF_CUR_LVL(P_SLOCID NUMBER, P_CLDID VARCHAR2, P_ORGID VARCHAR2,P_DOCID NUMBER,  P_TXN_DOCID VARCHAR2) RETURN NUMBER;
        Integer currLevel =
            Integer.parseInt(callStoredFunction(NUMBER, "APP.PKG_APP_WF.GET_DOC_WF_CUR_LVL(?,?,?,?,?)", new Object[] { sloc_id,
                                                                                                                    cld_id,
                                                                                                                  org_id,
                                                                                                                     doc_id,
                                                                                                                doc_txn_id}).toString());
        _headerPic.clear();
        System.out.println("LEVEL : "+level);
        System.out.println("CURR LEVEL :"+currLevel);
        if(level >0){
            for (int i = 1; i <= level; i++) {
                if (i <= currLevel) {//i <= currLevel
                try {
                        //get_lvl_action(p_sloc_id number , p_org_id varchar2 , p_cld_id varchar2 , P_txn_doc_id varchar2 , p_lvl number)
                        String s = callStoredFunction(Types.VARCHAR, "APP.PKG_APP_WF.get_lvl_action(?,?,?,?,?,?)", new Object[] { sloc_id,org_id,cld_id,doc_id,doc_txn_id,i}).toString();
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
            userLovBind.setDisabled(true);
            setTabDisb("Y");
        } else {
            setTabDisb("N");
            userLovBind.setDisabled(false);
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
}
