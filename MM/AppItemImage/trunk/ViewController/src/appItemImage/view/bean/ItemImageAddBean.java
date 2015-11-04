package appItemImage.view.bean;

import appItemImage.model.service.AppItemImageAMImpl;

import appItemImage.model.views.AppItmImgAddVORowImpl;

import java.awt.image.BufferedImage;

import java.util.*;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;


import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

//import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import javax.faces.event.ValueChangeEvent;

import javax.faces.validator.ValidatorException;

import javax.imageio.ImageIO;


import oracle.adf.model.BindingContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.context.AdfFacesContext;


import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.BlobDomain;
import oracle.jbo.domain.Date;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;

//import org.apache.myfaces.trinidad.model.UploadedFile;
import org.apache.myfaces.trinidad.model.UploadedFile;

public class ItemImageAddBean implements Serializable {

    private RichInputText fileTypeBindVar;
    private RichInputText inactiveReason;
    private RichInputDate inactiveDate;
    private RichInputText imageNmtxt;
    private RichInputText imagepathbinding;
    private Integer sloc_id=Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
    private String cld_id=resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
    private String org_id=resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
    private String ho_org_id=resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();

    public ItemImageAddBean() {
    }

    private static ADFLogger adf = (ADFLogger)ADFLogger.createADFLogger(ItemImageAddBean.class);

    private UploadedFile _file;
    private String Mode = getModeValue();
    private String Mode1 = "D";
    private String Mode2 = "D";
    private String Mode3 = "D";
    private String Mode4 = "D";
    private String type = null;
    private String TypeVal = null;

    private String getModeValue() {
        String callingVal = resolvEl("#{pageFlowScope.CallingValue}");
        return callingVal;
    }

    public String CreateAction() {
        this.setMode("A");
        this.Mode2 = "D";
        this.Mode3="D";
        this.Mode4="D";
        return "Create";
    }

    public OperationBinding executeBinding(String var) {
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding(var);
        return operationBinding;
    }

    public String SaveAction() {


        OperationBinding binding =BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("CheckDuplicateImage");
        binding.getParamsMap().put("ImgNm", null);
        binding.getParamsMap().put("ItmId",null);
        //binding.getParamsMap().put("ftype",filetype);
        binding.execute();
        Object o = binding.getResult();
        System.out.println("Result:" + o);
        if ((Boolean)o == Boolean.TRUE) {
                             FacesMessage message = new FacesMessage("Duplicate Image File Name Found");   
                             message.setSeverity(FacesMessage.SEVERITY_ERROR);   
                             FacesContext fc = FacesContext.getCurrentInstance();   
                             fc.addMessage(null, message);
} 
        else
        {
        AppItemImageAMImpl am = (AppItemImageAMImpl)resolvElDC("AppItemImageAMDataControl");
        ViewObjectImpl v = am.getAppItmImgAdd();
        Row rw = v.getCurrentRow();
        if (rw.getAttribute("ImgFileType") == null) {
        String msg1="Image is not Uploaded Properly.";
            //String valMsg = resolvEl("#{bundle['MSG.242']}").toString();
            FacesMessage msg = new FacesMessage(msg1);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, msg);
        } 
        else {
            if ("Y".equalsIgnoreCase(rw.getAttribute("ImgDflt").toString())) {
                RowSetIterator rsi = v.createRowSetIterator(null);
                while (rsi.hasNext()) {
                    Row nxt = rsi.next();
                    //Code Change Here..
                    if (nxt != rw && (rw.getAttribute("ItmId").equals(nxt.getAttribute("ItmId")))) {
                        nxt.setAttribute("ImgDflt", "N");
                    }
                }
                rsi.closeRowSetIterator();
            }
            OperationBinding createOB = executeBinding("Commit");
            createOB.execute();
            if("N".equalsIgnoreCase(rw.getAttribute("ImgDflt").toString())){
            String itemid=resolvEl("#{pageFlowScope.ITEM_ID}").toString();
            System.out.println("default image name");
            if(!(getFileTypeBindVar().getValue().equals("PDF"))){
                adf.info("in the pdf block");
            OperationBinding opbinding= BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("defaultImage");
            opbinding.getParamsMap().put("itmId",itemid);
            opbinding.execute();
            }
           //System.out.println("default Image Commit");
            OperationBinding opb= BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit");
            opb.execute();
            }
            if (createOB.getErrors().isEmpty()) {
                if ("A".equals(getMode())) {
                    String Defaultmsg = resolvEl("#{bundle['MSG.89']}");
                    String valMsg = Defaultmsg.format(Defaultmsg, "", "", "", "", "");
                    FacesMessage msg = new FacesMessage(valMsg);
                    msg.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    ctx.addMessage(null, msg);
                } else if ("E".equals(getMode())) {
                    String Defaultmsg = resolvEl("#{bundle['MSG.90']}");
                    String valMsg = Defaultmsg.format(Defaultmsg, "", "", "", "", "");
                    FacesMessage msg = new FacesMessage(valMsg);
                    msg.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    ctx.addMessage(null, msg);
                }
                this.setMode("V");
            }

        }
        this.setFile(null);
        return null;
        }
         return "1";
}

    public String CancelAction() {
        OperationBinding createOB = executeBinding("Rollback");
        createOB.execute();
        this.setFile(null);
        setMode("");
        return "Back";
    }

    private String getVal() {

        return (String)callStoredFunction(STRING, "APP.fn_get_app_img_path()", new Object[] { });
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
    private static int STRING = Types.VARCHAR;

    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {

            // 1. Create a JDBC CallabledStatement
            AppItemImageAMImpl am = (AppItemImageAMImpl)resolvElDC("AppItemImageAMDataControl");
            st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            // 2. Register the first bind variable for the return value
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                // 3. Loop over values for the bind variables passed in, if any
                for (int z = 0; z < bindVars.length; z++) {
                    // 4. Set the value of user-supplied bind vars in the stmt
                    st.setObject(z + 2, bindVars[z]);
                }
            }
            // 5. Set the value of user-supplied bind vars in the stmt
            st.executeUpdate();
            // 6. Return the value of the first bind variable
            return st.getObject(1);
        } catch (SQLException e) {
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    // 7. Close the statement
                    st.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public void setFile(UploadedFile _file) {
        this._file = _file;


    }

    public UploadedFile getFile() {

        return _file;
    }


    public String UploadImageAction() {
    //("InSide UIA");
        adf.info("InSide UIA");
        UploadedFile myfile = this.getFile();

        if (myfile == null) {
            String valMsg = resolvEl("#{bundle['MSG.476']}").toString();
            FacesMessage msg = new FacesMessage(valMsg);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, msg);
        } else {
            adf.info("t1");
            if( !(myfile.getContentType().equalsIgnoreCase("image/jpeg") ||
                myfile.getContentType().equalsIgnoreCase("image/png") ||
                myfile.getContentType().equalsIgnoreCase("image/bmp") ||
                myfile.getContentType().equalsIgnoreCase("image/gif"))) {
            System.out.println("in the pdf block");
             adf.info(myfile.toString());
             try{
                uploadPdfFile(myfile) ;
             }
             catch(Exception e ) {
                 System.out.println("Eception Occured in UploadpdfFile::::"+e);
                 e.printStackTrace();
             }
            
        }
            if (myfile.getContentType().equalsIgnoreCase("image/jpeg") ||
                myfile.getContentType().equalsIgnoreCase("image/png") ||
                myfile.getContentType().equalsIgnoreCase("image/bmp") ||
                myfile.getContentType().equalsIgnoreCase("image/gif")
               ) {

                String path = getVal();
                adf.info(path);
               
                if (myfile.getContentType().equalsIgnoreCase("image/jpeg")) {
                    type = "JPEG";
                    TypeVal = ".jpeg";
                } else if (myfile.getContentType().equalsIgnoreCase("image/png")) {
                    type = "PNG";
                    TypeVal = ".png";
                } else if (myfile.getContentType().equalsIgnoreCase("image/bmp")) {
                    type = "BMP";
                    TypeVal = ".bmp";
                } else if (myfile.getContentType().equalsIgnoreCase("image/gif")) {
                    type = "GIF";
                    TypeVal = ".gif";
                }
//                  else if (myfile.getContentType().equalsIgnoreCase("application/pdf")) {
//                                       type = "PDF";
//                                       TypeVal = ".pdf";
//                                   }
                adf.info("t2");
                AppItemImageAMImpl am = (AppItemImageAMImpl)resolvElDC("AppItemImageAMDataControl");
                ViewObjectImpl v = am.getAppItmImgAdd();
                AppItmImgAddVORowImpl currRow = (AppItmImgAddVORowImpl)v.getCurrentRow();
                
//                String imgId =
//                          (String)callStoredFunction(STRING, "APP.FN_APP_GEN_ID(?,?,?,?,?,?)", new Object[] {sloc_id,cld_id,ho_org_id,null, "APP$ITM$IMG" ,"IMG"});
                String ImgId = (String)callStoredFunction(STRING, "APP.FN_APP_GEN_ID(?,?,?,?,?,?)", new Object[] {sloc_id,cld_id,ho_org_id,null, "APP$ITM$IMG" ,"IMG"});
                adf.info("img id>>" + ImgId);

                try {

                    adf.info("t3");
                    InputStream inputStream = myfile.getInputStream();
                    BufferedImage input = ImageIO.read(inputStream);

                    //to save image in another directory..............
                    adf.info(path + ImgId + TypeVal);
                    File outputFile = new File(path + ImgId + TypeVal);
                    adf.info("File:" + outputFile);
                    ImageIO.write(input, type, outputFile);
                    String Serverpath = outputFile.getAbsolutePath();
                    adf.info("serverpath:" + Serverpath);
                    currRow.setAttribute("ImgId",ImgId);
                    currRow.setImgFileType(type);
                    currRow.setImgPath(Serverpath);
                    currRow.setImgBlob(createBlobDomain(getFile()));

                    inputStream.close();
                    if (getMode().equals("E") || getMode().equals("A")) {
                        //Mode2="D";
                        refreshPage();
                    }
                    

                } catch (Exception ex) {
                    // handle exception
                    String valMsg = resolvEl("#{bundle['MSG.477']}").toString();
                    FacesMessage msg = new FacesMessage(valMsg);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    ctx.addMessage(null, msg);
                }
            } else {

//                String valMsg = resolvEl("#{bundle['MSG.478']}").toString();
//                FacesMessage msg = new FacesMessage(valMsg);
//                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
//                FacesContext ctx = FacesContext.getCurrentInstance();
//                ctx.addMessage(null, msg);
            }
        }
        //this.Mode2="D";
        this.Mode4="E";
        adf.info("t4");
        setFile(null);
        
        if(imagepathbinding.getValue()!=null&& type != "PDF") {
            System.out.println("Image Path is:"+imagepathbinding.getValue());
            this.Mode3="E";
            this.Mode4="E";
            
        }
    
    return null;
    }
    private void uploadPdfFile(UploadedFile file)throws Exception {
//        adf.info("in the uploadPdfFile method");
//        System.out.println(file.getFilename()+" File Name.");
//            InputStream in = null;
//          BlobDomain blobDomain = null;
//               OutputStream out = null;
//                String path=getVal();
//               type = "PDF";
//               TypeVal = ".pdf";
//               AppItemImageAMImpl am = (AppItemImageAMImpl)resolvElDC("AppItemImageAMDataControl");
//               ViewObjectImpl v = am.getAppItmImgAdd();
//               AppItmImgAddVORowImpl currRow = (AppItmImgAddVORowImpl)v.getCurrentRow();
//              
//               try {
//                   in = file.getInputStream();
//                   System.out.println("File Total length : "+in.available());
//                   blobDomain = new BlobDomain();
//                   out = blobDomain.getBinaryOutputStream();
//                   byte[] buffer = new byte[5120];
//                   DataInputStream bout=new DataInputStream(in);
//                   bout.read(buffer);
//                   adf.info(sloc_id+" "+cld_id+" "+org_id );
//                   //String FileName=callStoredFunction(Types.VARCHAR, "pkg_app_gen.generate_id(?,?,?,?,?)", new Object[] { sloc_id, cld_id, null, org_id,"APP$WF$ATTCH" }).toString();
//                   String FileName= (String)callStoredFunction(STRING, "APP.FN_APP_GEN_ID(?,?,?,?,?,?)", new Object[] {sloc_id,cld_id,ho_org_id,null, "APP$ITM$IMG" ,"DOC"});
//                   currRow.setAttribute("ImgId",FileName);
//                   adf.info("FileName:"+FileName);
//                   String data = file.getFilename();
//            String[] split = data.split("\\.");
//            System.out.println(" Length : "+split.length);
//                   System.out.println(file.getFilename());
//            FileOutputStream fw=new FileOutputStream(path+FileName+"."+split[1]);
//                   
//                   fw.write(buffer);
//                   fw.close();
//                   in.close();
//                   out.close();
//                   bout.close();
//                   //buffer=null;
//                   
//                   
//                   String Serverpath=path+FileName+"."+split[1];
//                  
//                   currRow.setImgFileType(split[1].toUpperCase());
//                   currRow.setImgPath(Serverpath);
//                   
//                   //currRow.setImgBlob(createBlobDomain(getFile()));
//                   
//               } catch (IOException e) {
//                   e.printStackTrace();
//               } catch (SQLException e) {
//                   e.fillInStackTrace();
//               }
//               if (getMode().equals("E") || getMode().equals("A")) {
//                   //Mode2="D";
//                   adf.info("refresh Code Run");
//                   refreshPage();
//               }
        
        
        
        String path = getVal();
        String extn = "";
       UploadedFile files = file;
        InputStream in = null;
        FileOutputStream out = null;
        type = "PDF";
        TypeVal = ".pdf";
        if (files != null) {
           // for (int i = 0; i < files.size(); i++) {
                try {
                    
                    

                    //get file extension
                    //extn = files.get().getFilename().substring(files.get().getFilename().lastIndexOf("."));
                    AppItemImageAMImpl am = (AppItemImageAMImpl)resolvElDC("AppItemImageAMDataControl");
                    ViewObjectImpl v = am.getAppItmImgAdd();
                    AppItmImgAddVORowImpl currRow = (AppItmImgAddVORowImpl)v.getCurrentRow();

                    //Add files to the Table
                    //OperationBinding op = getBindings().getOperationBinding("createAttchmntRow");
                    //  op = ADFUtils.findOperation("createAttchmntRow");
                    //op.getParamsMap().put("fileNm", files.get(i).getFilename());
                    //op.getParamsMap().put("contentTyp", files.get(i).getContentType());
                    //op.getParamsMap().put("extn", extn);
                    //op.execute();
                    String FileName= (String)callStoredFunction(STRING, "APP.FN_APP_GEN_ID(?,?,?,?,?,?)", new Object[] {sloc_id,cld_id,ho_org_id,null, "APP$ITM$IMG" ,"DOC"});
                    currRow.setAttribute("ImgId",FileName);
//                    if (op.getResult() != null) {
//                        path = op.getResult().toString();
//                    }
                    //System.out.println("extn " + extn);
                    //write files in the file system.

                    in = files.getInputStream();
                    System.out.println(files.getInputStream());

                    //System.out.println("write file at " + path + extn);
                    String data = file.getFilename();
                          String[] split = data.split("\\.");
                    out = new FileOutputStream(path +FileName+"."+split[1]);
                    byte[] buffer = new byte[8192];
                    int bytesRead = 0;

                    while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
                        out.write(buffer, 0, bytesRead);
                       // out.flush();
                    }
//                    String data = file.getFilename();
//                    String[] split = data.split("\\.");
                    System.out.println(" Length : "+split.length);
                    System.out.println(file.getFilename());
                    
                    
                    path.replace("//","\\");
                    String Serverpath=path+FileName+"."+split[1];
                    currRow.setImgFileType(split[1].toUpperCase());
                    currRow.setImgPath(Serverpath);
                    out.flush();

                    //call commit after checking all validations
//                    OperationBinding obCommit = getBindings().getOperationBinding("Commit");
//                    obCommit.execute();
                    ///   ADFUtils.findOperation("Commit").execute();
                } catch (IOException ioe) {
                    // TODO: Add catch code
                    ioe.printStackTrace();
                } finally {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                }
            //}
              
           }
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

    private BlobDomain createBlobDomain(UploadedFile file) {

        InputStream in = null;
        BlobDomain blobDomain = null;
        OutputStream out = null;

        try {
            in = file.getInputStream();

            blobDomain = new BlobDomain();
            out = blobDomain.getBinaryOutputStream();
            byte[] buffer = new byte[8192];
            int bytesRead = 0;

            while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.fillInStackTrace();
        }

        return blobDomain;
    }

    protected void refreshPage() {
        FacesContext fc = FacesContext.getCurrentInstance();
        String refreshpage = fc.getViewRoot().getViewId();
        ViewHandler ViewH = fc.getApplication().getViewHandler();
        UIViewRoot UIV = ViewH.createView(fc, refreshpage);
        UIV.setViewId(refreshpage);
        fc.setViewRoot(UIV);
    }

    public void setFileTypeBindVar(RichInputText fileTypeBindVar) {
        this.fileTypeBindVar = fileTypeBindVar;
    }

    public RichInputText getFileTypeBindVar() {
        return fileTypeBindVar;
    }

    public void setMode(String Mode) {
        this.Mode = Mode;
    }

    public String getMode() {
        if (Mode == "") {
            return getModeValue();
        } else {
            return Mode;
        }

    }

    public void activeChangeListner(ValueChangeEvent vce) {
        AppItemImageAMImpl am = (AppItemImageAMImpl)resolvElDC("AppItemImageAMDataControl");
        ViewObjectImpl v = am.getAppItmImg();
        if (vce.getNewValue() != null) {
            String value = vce.getNewValue().toString();

            if (value.equals("false")) {
                Date dt = (Date)Date.getCurrentDate();

                Row re = v.getCurrentRow();

                inactiveDate.setValue(dt);

                AdfFacesContext.getCurrentInstance().addPartialTarget(inactiveReason);
                AdfFacesContext.getCurrentInstance().addPartialTarget(inactiveDate);
            } else if (value.equals("true")) {

                Row re = v.getCurrentRow();
                //    re.setAttribute("InactvDt", null);
                //   re.setAttribute("InactvResn", "");
                inactiveReason.setValue("");
                inactiveDate.setValue(null);

                AdfFacesContext.getCurrentInstance().addPartialTarget(inactiveReason);
                AdfFacesContext.getCurrentInstance().addPartialTarget(inactiveDate);
            }
        }
    }

    public void setInactiveReason(RichInputText inactiveReason) {
        this.inactiveReason = inactiveReason;
    }

    public RichInputText getInactiveReason() {
        return inactiveReason;
    }

    public void setInactiveDate(RichInputDate inactiveDate) {
        this.inactiveDate = inactiveDate;
    }

    public RichInputDate getInactiveDate() {
        return inactiveDate;
    }

    public String EditAction() {
        /*  AppItemImageAMImpl am = (AppItemImageAMImpl)resolvElDC("AppItemImageAMDataControl");
        ViewObjectImpl v = am.getAppItmImgAdd();
        Row re = v.getCurrentRow();
        String activeVal = re.getAttribute("Actv").toString();
        if (activeVal.equals("Y")) {
            inactiveReason.setVisible(false);
            inactiveDate.setVisible(false);
        } else {
            inactiveReason.setVisible(false);
            inactiveDate.setVisible(false);
        } */
        AdfFacesContext.getCurrentInstance().addPartialTarget(inactiveReason);
        AdfFacesContext.getCurrentInstance().addPartialTarget(inactiveDate);
        setMode("E");
        this.Mode3="E";
        this.Mode4="E";
        this.Mode2="E";
        return null;
    }


    public void GroupDefValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Boolean val = (Boolean)object;

        String value = "";

        if (val == true) {
            value = "Y";
        } else if (val == false) {
            value = "N";
        }
        if (val == true) {
            AppItemImageAMImpl am = (AppItemImageAMImpl)resolvElDC("AppItemImageAMDataControl");
            String ItemId = resolvEl("#{pageFlowScope.ITEM_ID}");

            String SlocId = resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}");

            ViewObjectImpl v2 = am.getAppItmImg(); //used in img page for viewung
            ViewObjectImpl v3 = am.getAppItmImgAdd(); //used in form
            Row currRow = v3.getCurrentRow();
            String img = v3.getCurrentRow().getAttribute("ImgId").toString();
            v2.setRangeSize(-1);
            Row[] rows = v2.getAllRowsInRange();
            for (Row r : rows) {
                if (!r.getAttribute("ImgId").toString().equalsIgnoreCase(img))
                    r.setAttribute("ImgDflt", "N");
            }
        }
    }

    public void imageNameValidation(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String itemid=resolvEl("#{pageFlowScope.ITEM_ID}").toString();
        System.out.println(itemid);
        //String filetype=fileTypeBindVar.getValue().toString();
        if (object != null && itemid!=null) {
            //Check For Duplicate Existence of Image name..
          //  System.out.println("Image Name:" + object.toString());

            /* OperationBinding binding =
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("CheckDuplicateImage");
            binding.getParamsMap().put("ImgNm", object.toString());
          binding.getParamsMap().put("ItmId",itemid);
            //binding.getParamsMap().put("ftype",filetype);
            binding.execute(); */
           // Object o = binding.getResult();
            //System.out.println("Result:" + o);
          /*   if ((Boolean)o == Boolean.TRUE) {
                String s1 = "Duplicate Image Name Exists";
                FacesMessage message2 = new FacesMessage(s1);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            } else */
                Mode2 = "E";

            //-------------------------------------------------------------------------------------------
            String whId = object.toString();
            int openB = 0;
            int closeB = 0;
            boolean closeFg = false;

            char[] xx = whId.toCharArray();

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
            if (openB != closeB || closeFg == true || (whId.lastIndexOf("(") > whId.lastIndexOf(")")) ||
                (whId.indexOf(")") < whId.indexOf("("))) {
                String msg2 = resolvElDC("#{bundle['MSG.7']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }

            /**5.check for empty brackets
                         */
            if (whId.contains("()")) {
                String msg2 = resolvEl("#{bundle['MSG.170']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            /**check for to dot not comes together
                         */
            if (whId.contains("..")) {
                String msg2 = resolvEl("#{bundle['MSG.276']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            /**check for to dot not comes together
                         */
            if (whId.contains("--")) {
                String msg2 = resolvEl("#{bundle['MSG.277']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            /**  check for wrong combo
                         */
            if (whId.contains("(.") || whId.contains("(-") || whId.contains("-)")) {
                String msg2 = resolvEl("#{bundle['MSG.59']}").toString();
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
                "^(?:(?>[A-Za-z0-9 )]+)(?:\\_|\\-|\\(|\\.|\\)|\\:|\\/|\\\\(?!\\_|\\-|\\:|\\/|\\\\|$))?)*$";
            CharSequence inputStr = whId;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String error = resolvEl("#{bundle['']}").toString();

            if (matcher.matches()) {
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
            }

        }
    }

    public void setImageNmtxt(RichInputText imageNmtxt) {
        this.imageNmtxt = imageNmtxt;
    }

    public RichInputText getImageNmtxt() {
        return imageNmtxt;
    }

    public String getMode1() {
        return Mode;
    }

    public void setMode1(String Mode1) {
        this.Mode1 = Mode1;
    }

    //    public String getMode1() {
    //        return Mode1;
    //    }

    public static void setSTRING(int STRING) {
        ItemImageAddBean.STRING = STRING;
    }

    public static int getSTRING() {
        return STRING;
    }

    public void setMode2(String Mode2) {
        this.Mode2 = Mode2;
    }

    public String getMode2() {
        return Mode2;
    }

    public void setImagepathbinding(RichInputText imagepathbinding) {
        this.imagepathbinding = imagepathbinding;
    }

    public RichInputText getImagepathbinding() {
        return imagepathbinding;
    }

    public void setMode3(String Mode3) {
        this.Mode3 = Mode3;
    }

    public String getMode3() {
        return Mode3;
    }

    public void setMode4(String Mode4) {
        this.Mode4 = Mode4;
    }

    public String getMode4() {
        return Mode4;
    }
}

