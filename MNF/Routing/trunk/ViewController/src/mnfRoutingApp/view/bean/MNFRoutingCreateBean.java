
package mnfRoutingApp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.InputStream;

import java.io.OutputStream;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.util.ListIterator;

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

import mnfRoutingApp.model.services.MNFRoutingAMImpl;

import mnfRoutingApp.model.views.LOVUserVOImpl;

import mnfRoutingApp.view.utils.ADFUtils;
import mnfRoutingApp.view.utils.JSFUtils;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputFile;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelTabbed;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.component.rich.output.RichOutputFormatted;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.datatransfer.DataFlavor;
import oracle.adf.view.rich.datatransfer.Transferable;
import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DropEvent;

import oracle.binding.OperationBinding;

import oracle.jbo.ApplicationModule;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.SortCriterion;
import org.apache.myfaces.trinidad.model.UploadedFile;

public class MNFRoutingCreateBean {
    private static ADFLogger adfLog = (ADFLogger) ADFLogger.createADFLogger(MNFRoutingCreateBean.class);
  
    // value for mode V : view, C : create, E : edit
    private StringBuffer mode = new StringBuffer(JSFUtils.resolveExpressionAsString("#{pageFlowScope.MNF_RT_MOD}"));
    private StringBuffer hdrFreeze = new StringBuffer("N");
    private RichPopup createRoutePopup;
    private RichTable opDtlBinding;
    protected Integer dragRowSrNo;
    protected Integer dropRowSrNo;
    private RichLink reOrderLnkBinding;
    public String workFlowId;
    private List<UploadedFile> uploadedFile;
    public OperationBinding ob = null;
    private RichSelectBooleanCheckbox selectedOpChkBox;
    private RichButton copyActionBind;
    private RichOutputFormatted wcDescBind;
    private RichPanelFormLayout rtHdrForm;
    private RichPanelTabbed dtlTabBinding;
    private RichPanelCollection opCollectionBinding;
    private RichInputFile attachFileBinding;
    private RichPanelFormLayout orgFormBinding;
    private RichInputListOfValues wcNmeBind;
    private RichInputText opParSrNoBind;
    private RichInputListOfValues bindPredOpName;
    private String mode1="V";
    private String flag="N";
    private RichSelectBooleanCheckbox bindfreezeVar;
    private RichInputText bindPerUsed;
    private RichTable operationtblBind;
    private RichInputListOfValues bindCopyPrevious;

    public void setMode1(String mode1) {
        this.mode1 = mode1;
    }

    public String getMode1() {
        return mode1;
    }


    private MNFRoutingAMImpl getAM() {
        return (MNFRoutingAMImpl) ADFBeanUtils.getApplicationModuleForDataControl("MNFRoutingAMDataControl");
    }

    public void setUploadedFile(List<UploadedFile> uploadedFile) {

        this.uploadedFile = uploadedFile;
    }

    public List<UploadedFile> getUploadedFile() {
        return uploadedFile;
    }

    public void setWorkFlowId(String workFlowId) {
        this.workFlowId = workFlowId;
    }

    /**
     * Method to get work flow Id.
     * The Id returned by this method will be set in WF task flow call for Work flow.
     * **/
    public String getWorkFlowId() {
        return this.workFlowId;
    }

    public void setDragRowSrNo(Integer dragRowSrNo) {
        this.dragRowSrNo = dragRowSrNo;
    }

    public Integer getDragRowSrNo() {
        return dragRowSrNo;
    }

    public void setDropRowSrNo(Integer dropRowSrNo) {
        this.dropRowSrNo = dropRowSrNo;
    }

    public Integer getDropRowSrNo() {
        return dropRowSrNo;
    }


    public void setMode(StringBuffer mode) {
        this.mode = mode;
    }

    public StringBuffer getMode() {
        return mode;
    }

    public MNFRoutingCreateBean() {
    }

    public void RouteCreateACE(ActionEvent actionEvent) {

        ADFBeanUtils.findOperation("createNewRoute").execute();

        //create default organization profile
        // ADFUtils.findOperation("createOrgMnfRt").execute();

        setMode(new StringBuffer("C"));

        setHdrFreeze(new StringBuffer("N"));
    }
   

    /**
     *Method to get user Id.
     * **/
    public Integer getUserId() {
      
        if (JSFUtils.resolveExpression("#{pageFlowScope.GLBL_APP_USR}") != null) {

            return Integer.parseInt(JSFUtils.resolveExpression("#{pageFlowScope.GLBL_APP_USR}").toString());

        } else {
            JSFUtils.addFacesErrorMessage("Error in getting User Id from task flow. ");
        }

        return 0;
    }

    /**
     * Method to get User Name from user Id
     * **/

    public String getUserNm(Integer usr_id) {

       // DCIteratorBinding iter = ADFUtils.findIterator("LOVUserVOIterator");
        //ViewObject object = iter.getViewObject();
        String uname="";
        LOVUserVOImpl object = getAM().getLOVUserVO();
        object.setNamedWhereClauseParam("BindUsrId", usr_id);
        object.setNamedWhereClauseParam("BindSlcId", Integer.parseInt(
        JSFUtils.resolveExpression("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString()));
        object.executeQuery();
        /* RowSetIterator rit = object.createRowSetIterator(null);
        System.out.println("row count " + rit.getRowCount());
        if (rit.hasNext()) {
            System.out.println(" has next");
            return rit.first().getAttribute("UsrName").toString();
        }
return "0"; */
        Row[] allRowsInRange = object.getAllRowsInRange();
        if (allRowsInRange.length > 0) {
            Object a = allRowsInRange[0].getAttribute("UsrName");
            String s = (a == null ? null : a.toString());
            uname = s;
        }
        return uname;
    }

    public void RouteEditACE(ActionEvent actionEvent) {

        
        Integer pending = (Integer) ADFBeanUtils.callBindingsMethod("getDocUsrFromWF", null, null);

        System.out.println(pending + "--------" + getUserId());

        if (!pending.equals(getUserId())) {

            StringBuffer usrName = new StringBuffer(getUserNm(pending));

            //This Route is pending at other user for approval, You can not edit this.*
            //create multi line faces message using StringBuilder

            //String msg2 = JSFUtils.resolveExpression("#{bundle['MSG.506']}").toString();
            String msg2 = "This Route is pending at other user for approval, You can not edit this. ";
            StringBuilder saveMsg = new StringBuilder("<html><body><p><b>" + msg2 + "</b></p>");
            saveMsg.append("Pending at- <b> " + usrName + "</b>");
            saveMsg.append("</body></html>");

            // use the helper method to show faces messages.
            ADFUtils.showFacesMsg(saveMsg.toString(), null, FacesMessage.SEVERITY_INFO, null);

        } else {

            setMode(new StringBuffer("E"));

            setHdrFreeze(new StringBuffer("Y"));
            setMode1("A");
        }
    
    }

    public String saveAction() {
        adfLog.info("validateAllforsave::"+validateAllForSave());
        if ("Y".equals(validateAllForSave())) {

            //call commit after checking all validations
//            adfLog.info("Value of RtMode::"+getAttrsVal("MnfRtVOIterator", "RtMode"));
//            adfLog.info("Value of RtActv:::"+getAttrsVal("MnfRtVOIterator", "RtActv"));
//            String actv=(String)getAttrsVal("MnfRtVOIterator", "RtActv");
//            Integer mode=(Integer)getAttrsVal("MnfRtVOIterator", "RtMode");
//            
//            if (mode.compareTo(new Integer(43))==0 && actv.equalsIgnoreCase("N")){
//                adfLog.info("call");
//                ob = ADFBeanUtils.findOperation("checkRouteUsage");
//                ob.execute();
//                adfLog.info("Value of result:::"+ob.getResult());
//                if(ob.getResult()!=null){
//                Integer r1=(Integer)ob.getResult();
//                if(r1.compareTo(new Integer(0))==-1 || r1.compareTo(new Integer(0))==1 ){
//                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
//                                                                  "You Can not Inactive Route as it is used in BOM.", null));
//                    
//                    ADFUtils.showFacesMsg("Error Message", "You Can not Inactive Route as it is used in BOM.",
//                                          FacesMessage.SEVERITY_INFO, null);
//                   
//                    
//                }
//            }
//            }

            ADFBeanUtils.findOperation("Commit").execute();

            workFlowCall();

            ADFBeanUtils.findOperation("Commit").execute();

            setMode(new StringBuffer("V"));

            //JSFUtils.addFacesInformationMessage("Record saved successfully");
            // ADFUtils.showFacesMsg("Save Message", "Record saved successfully", FacesMessage.SEVERITY_INFO, null);
            ADFUtils.showFacesMsg("Save Message", JSFUtils.resolveExpressionAsString("#{bundle['MSG.1262']}"),
                                  FacesMessage.SEVERITY_INFO, null);
         adfLog.info("Value of bindfreezeVar:::"+bindfreezeVar.isSelected());            
            if( bindfreezeVar.isSelected()){
                ADFContext.getCurrent().getPageFlowScope().put("FREEZE_FLG","Y");
            }
            

            return "Y";

        } else

            return "N";

    }
   

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }

    private String validateAllForSave() {


        //validate for route name
        if (getAttrsVal("MnfRtVOIterator", "RtDesc") == null) {

            /* ADFUtils.showFacesMsg("Route Name is Required", "Please enter Name for the Route",
                                  FacesMessage.SEVERITY_ERROR, null); */
            ADFUtils.showFacesMsg("Route Name is Required", "Please enter Name for the Route.",
                                  FacesMessage.SEVERITY_ERROR, null);
            return "N";
        }

        //Validate for unique route name

        ob = ADFBeanUtils.findOperation("validateRtNm");
        ob.execute();

        if ("Y".equals(ob.getResult())) {

            ADFUtils.showFacesMsg("Unique Route Name is Required. ",
                                  "Unique Route Name is Required. " + getAttrsVal("MnfRtVOIterator", "RtDesc") +
                                  "is used by another Route. ", FacesMessage.SEVERITY_ERROR, null);

            return "N";
        }
        System.out.println("operation count validate");
        //check for operation count. A route cannot be saved without any operation
        if ("N".equals(dataExists("MnfRtOpIterator"))) {

            System.out.println("data exists " + dataExists("MnfRtOpIterator"));
            ADFUtils.showFacesMsg("Selection Required ", "Route cannot be saved without an Operation. ",
                                  FacesMessage.SEVERITY_ERROR, null);

            return "N";
        }

        //validate for null values of Work Center in Operation details
        if (checkNullData("MnfRtOpIterator", "OpId").compareTo("Y") == 0) {

            ADFUtils.showFacesMsg("Selection Required ", "Please select Operations for all rows.",
                                  FacesMessage.SEVERITY_ERROR, null);
            return "N";
        }

        //validate for null values of Operations in Operation details
        if (checkNullData("MnfRtOpIterator", "WcId").compareTo("Y") == 0) {

            ADFUtils.showFacesMsg("Selection Required ", "Please select Work Centers for all Operations.",
                                  FacesMessage.SEVERITY_ERROR, null);
            return "N";
        }

      //  System.out.println("REASEON" + checkNullData("MnfRtVOIterator", "RtInactvReason").compareTo("Y"));
     //   System.out.println("ACTIV" +getAttrsVal("MnfRtVOIterator", "RtActv").toString().compareToIgnoreCase("N"));
     //   System.out.println("ACTV VAL" +getAttrsVal("MnfRtVOIterator", "RtActv"));
        //if route not active then inactive reason is required
        if (getAttrsVal("MnfRtVOIterator", "RtActv") != null &&
            (getAttrsVal("MnfRtVOIterator", "RtActv").toString()).compareToIgnoreCase("N") == 0 &&
            checkNullData("MnfRtVOIterator", "RtInactvReason").compareTo("Y") == 0) {

            ADFUtils.showFacesMsg("Inactive Reason is Required. ", "Inactive Reason is Required.",
                                  FacesMessage.SEVERITY_ERROR, null);
            return "N";
        }

        //check default profile for current HO
        ADFBeanUtils.findOperation("beforeSave").execute();

        return "Y";
    }

    public String cancelAction() {

        ADFBeanUtils.findOperation("Rollback").execute();

        setMode(new StringBuffer("V"));
        return "back";
    }

    public void setCreateRoutePopup(RichPopup createRoutePopup) {
        this.createRoutePopup = createRoutePopup;
    }

    public RichPopup getCreateRoutePopup() {
        return createRoutePopup;
    }

    /**
     * Call method from AM to set the check box for operations which already exist in details.
     * **/
    public void operationDetailDE(DisclosureEvent disclosureEvent) {

        System.out.println("DisclosureEvent ---" + disclosureEvent.getComponent().getParent());

        //set the checkbox value for Opeartion lists
        if (disclosureEvent.isExpanded()) {
            ADFBeanUtils.findOperation("setSelectedOperation").execute();

        }
        //ppr the drawer component
        AdfFacesContext.getCurrentInstance().addPartialTarget(disclosureEvent.getComponent().getParent());
        AdfFacesContext.getCurrentInstance().addPartialTarget(disclosureEvent.getComponent());
        AdfFacesContext.getCurrentInstance().addPartialTarget(selectedOpChkBox);
    }

    /**
     * Method to validate the submited rows  before commit;
     * This method will Iterator through all rows in Page and will return Y if attribute value is found
     * null. Default return value is N.
     * **/

    private String checkNullData(String iter, String attrsNm) {

        DCIteratorBinding dcIter = ADFBeanUtils.findIterator(iter);
        ViewObject vo = dcIter.getViewObject();
        RowSetIterator rSetIter = vo.createRowSetIterator(null);

        Row r = null;
        Integer countNullVal = 0;
        //System.out.println("rSetIter " + rSetIter.getRowCount());

        while (rSetIter.hasNext()) {
            r = rSetIter.next();

            if (r.getAttribute(attrsNm) == null) {
                countNullVal = countNullVal + 1;
            }
        }
        rSetIter.closeRowSetIterator();

        return countNullVal == 0 ? "N" : "Y";
    }

    /**
     * Method to check duplicate value for rows within an iterator
     * **/
    private boolean duplicateValue(String iter, String attrsNm, Object val) {

        DCIteratorBinding dcIter = ADFBeanUtils.findIterator(iter);
        ViewObject vo = dcIter.getViewObject();
        RowSetIterator rSetIter = vo.createRowSetIterator(null);

        Row r = null;
        Integer countVal = 0;

        while (rSetIter.hasNext()) {
            r = rSetIter.next();
            // System.out.println("attrsNm "+r.getAttribute(attrsNm));

            if (r.getAttribute(attrsNm) != null && val.equals(r.getAttribute(attrsNm))) {
                countVal = countVal + 1;
            }
        }
        rSetIter.closeRowSetIterator();

        //exclude count from current row
        Row currentRow = dcIter.getCurrentRow();

        if (currentRow.getAttribute(attrsNm) != null && val.equals(currentRow.getAttribute(attrsNm))) {
            countVal = countVal - 1;
        }

        return countVal == 1 ? true : false;
    }

    private String dataExists(String iter) {

        DCIteratorBinding dcIter = ADFBeanUtils.findIterator(iter);
        ViewObject vo = dcIter.getViewObject();
        RowSetIterator rSetIter = vo.createRowSetIterator(null);

        Row r = null;
        Integer countVal = 0;

        while (rSetIter.hasNext()) {
            r = rSetIter.next();

            countVal = countVal + 1;

        }
        rSetIter.closeRowSetIterator();

        //System.out.println("countVal " + countVal);
        return countVal == 0 ? "N" : "Y";
    }

    /**
     * Method to handle drop event for Operation detali table
     * **/
    public DnDAction operationRowDrop(DropEvent dropEvent) {
        //get the table instance. This information is later used
        //to determine the tree binding and the iterator binding
        RichTable table = (RichTable) dropEvent.getDragComponent();

        List dropRowKey = (List) dropEvent.getDropSite();
        //if no dropsite then drop area was not a data area
        if (dropRowKey == null) {
            return DnDAction.NONE;
        }
        //The transferable is the payload that contains the dragged row's
        //row key that we use to access the dragged row handle in the ADF iterator binding
        Transferable t = dropEvent.getTransferable();

        //get the row key set of the dragged row. The "rowmove" string is the
        //discriminant defined on the drag source and the collectionDrop target.

        DataFlavor<RowKeySet> df = DataFlavor.getDataFlavor(RowKeySet.class, "rowmove");
        RowKeySet rks = t.getData(df);
        Iterator iter = rks.iterator();

        //for this use case the re-order of rows is one-by-one, which means
        //that the rowKeySet should only contain a single entry. If it
        //contains more then still we only look at a singe (first) row key entry

        List draggedRowKey = (List) iter.next();

        //get access to the oracle.jbo.Row instance represneting this table row

        Object objdragg = table.getRowData(draggedRowKey);
        Row dragRow = (Row) objdragg;

        Object objdrop = table.getRowData(dropRowKey);
        Row dropRow = (Row) objdrop;

        //get the table's ADF JUCtrlHierBinding

        CollectionModel collectionModel = (CollectionModel) table.getValue();
        JUCtrlHierBinding treeBinding = (JUCtrlHierBinding) collectionModel.getWrappedData();

        //get access to the ADF iterator binding used by the table and the
        //underlying RowSetIterator. The RowSetIterator allows us to remove and re-instert the dragged row

        DCIteratorBinding departmentsIterator = treeBinding.getDCIteratorBinding();
        RowSetIterator rsi = departmentsIterator.getRowSetIterator();

        //getSelectedRow("MnfRtOpIterator", "OpId");

        int indexOfDropRow = rsi.getRangeIndexOf(dropRow);
        //remove dragged row from collection so it can be added back
        dragRow.removeAndRetain();


        rsi.insertRowAtRangeIndex(indexOfDropRow, dragRow);

        //make row current in ADF iterator.
        departmentsIterator.setCurrentRowIndexInRange(indexOfDropRow);

        setDragRowSrNo((Integer) dragRow.getAttribute("OpSrNo"));
        setDropRowSrNo((Integer) dropRow.getAttribute("OpSrNo"));

        OperationBinding op = ADFBeanUtils.findOperation("reorderSrNo");
        op.getParamsMap().put("dragSrNo", getDragRowSrNo());
        op.getParamsMap().put("dropSrNo", getDropRowSrNo());
        op.execute();

        //ppr the table
        AdfFacesContext adfctx = AdfFacesContext.getCurrentInstance();
        //note that the refresh of the table didn't work when refreshing the
        //table needed to refresh the container component (af:panelStretchLayout).


        adfctx.addPartialTarget(table.getParent());

        return DnDAction.MOVE;
    }

    public void setOpDtlBinding(RichTable opDtlBinding) {
        this.opDtlBinding = opDtlBinding;
    }

    public RichTable getOpDtlBinding() {
        return opDtlBinding;
    }

    public void setReOrderLnkBinding(RichLink reOrderLnkBinding) {
        this.reOrderLnkBinding = reOrderLnkBinding;
    }

    public RichLink getReOrderLnkBinding() {
        return reOrderLnkBinding;
    }

    public String operationReorderEvent() {

        return "";
    }

    public void workFlowCall() {

        ADFBeanUtils.findOperation("callWfFunctions").execute();

    }

    public String saveAndForwardAction() {

        if ("Y".compareTo(saveAction()) == 0) {


            OperationBinding ob = ADFBeanUtils.findOperation("getWfId");
            ob.execute();

            System.out.println("ob.getResult() " + ob.getResult());
            if (ob.getResult() != null) {
                setWorkFlowId(ob.getResult().toString());
            } else {

                JSFUtils.addFacesErrorMessage("Error in getting Work Flow Id");
                return null;
            }

            return "wfCall";
        }

        return null;
    }

    public void fileUploadListener(ValueChangeEvent valueChangeEvent) {
        System.out.println("fileUploadListener " + valueChangeEvent.getNewValue());
        
       }
    
    /**
     * Method to save uploaded files in file system and DB tables
     * **/
    public String uploadFiles() {
        OperationBinding op = null;
        String path = "";
        String extn = "";
        List<UploadedFile> files = this.getUploadedFile();
        
        System.out.println("Max size of file is : " + attachFileBinding.getMaximumFiles() ) ;
        if (files == null)
            JSFUtils.addFacesErrorMessage("No Files have been Uploaded yet!");
        else if (files != null) {
           for (int i = 0; i < files.size(); i++) {
                try {
                    //get file extension
                    extn = files.get(i).getFilename().substring(files.get(i).getFilename().lastIndexOf("."));
                    //Add files to the Table
                    op = ADFBeanUtils.findOperation("createAttchmntRow");
                    op.getParamsMap().put("fileNm", files.get(i).getFilename());
                    op.getParamsMap().put("contentTyp", files.get(i).getContentType());
                    op.getParamsMap().put("extn", extn);
                    op.execute();
                  if (op.getResult() != null) {
                        path = op.getResult().toString();
                    }
                    System.out.println("extn " + extn);
                    //write files in the file system.
                    InputStream in = files.get(i).getInputStream();
                    System.out.println(files.get(i).getInputStream());
                    System.out.println("write file at " + path + extn);
                    FileOutputStream out = new FileOutputStream(path + extn);
                    byte[] buffer = new byte[8192];
                    int bytesRead = 0;
                    while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                    in.close();
                    out.close();
                    //call commit after checking all validations
                    ADFBeanUtils.findOperation("Commit").execute();
                } catch (IOException ioe) {
                    // TODO: Add catch code
                    ioe.printStackTrace();
                }
            }
            
            //ADFUtils.findOperation("Commit").execute();
            
            //remove the files to prepare for new uploads
            setUploadedFile(null);
            //attachFileBinding.resetValue();
           // attachFileBinding.setValue(null);
           // attachFileBinding.setSubmittedValue(null);

            //freeze the header
            setHdrFreeze(new StringBuffer("N"));
        }
            
        /* else{
           JSFUtils.addFacesErrorMessage("Error in Uploading Files, Attach Only " + attachFileBinding.getMaximumFiles() + "Files !");        
            uploadedFile.clear();
                   setUploadedFile(null);
            attachFileBinding.resetValue();
            attachFileBinding.setValue(null);
            attachFileBinding.setSubmittedValue(null);

        } */
        return "null";
       
    }
       
        
   // }

    /**Method to download file from actual path
     * @param facesContext
     * @param outputStream
     */
    public void downloadFileListener(FacesContext facesContext, OutputStream outputStream) throws IOException {
        //Read file from particular path, path bind is binding of table field that contains path
        String path = JSFUtils.resolveExpressionAsString("#{row.AttchFlPath}");


        File file = new File(path);

        FileInputStream fis;
        byte[] b;
        try {
            fis = new FileInputStream(file);

            int n;
            while ((n = fis.available()) > 0) {
                b = new byte[n];
                int result = fis.read(b);
                outputStream.write(b, 0, b.length);
                if (result == -1)
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        outputStream.flush();
    }

    public String copyRouteACE() {

        setMode(new StringBuffer("C"));

        AdfFacesContext.getCurrentInstance().addPartialTarget(rtHdrForm);
        AdfFacesContext.getCurrentInstance().addPartialTarget(dtlTabBinding);

        return "copyRt";
    }

    public String RouteDeleteACE() {

        ob = ADFBeanUtils.findOperation("deleteRoute");
        ob.execute();

        if (ob.getResult() != null && ob.getResult().toString().compareTo("Y") == 0) {

            //show message for successful deletion of this route
            JSFUtils.addFacesInformationMessage("Successfully deleted one Route");

            return "back";
        }

        return "null";
    }

    public void setSelectedOpChkBox(RichSelectBooleanCheckbox selectedOpChkBox) {
        this.selectedOpChkBox = selectedOpChkBox;
    }

    public RichSelectBooleanCheckbox getSelectedOpChkBox() {
        return selectedOpChkBox;
    }

    public void orgValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {

            if (duplicateValue("OrgMnfRtVOIterator", "OrgId", object)) {

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Duplicate Organization Selected.", null));
           
           
           
            }
        }
    }

    public void setCopyActionBind(RichButton copyActionBind) {
        this.copyActionBind = copyActionBind;
    }

    public RichButton getCopyActionBind() {
        return copyActionBind;
    }

    public void previousRtVCE(ValueChangeEvent valueChangeEvent) {

        setHdrFreeze(new StringBuffer("Y"));

        ADFBeanUtils.findOperation("enableReservedMode").execute();

        ActionEvent copyAc = new ActionEvent(copyActionBind); //
        copyAc.queue();

        AdfFacesContext.getCurrentInstance().addPartialTarget(valueChangeEvent.getComponent());
        AdfFacesContext.getCurrentInstance().addPartialTarget(valueChangeEvent.getComponent().getParent());
    }

    public void workCenterVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(wcDescBind);
    }

    public void setWcDescBind(RichOutputFormatted wcDescBind) {
        this.wcDescBind = wcDescBind;
    }

    public RichOutputFormatted getWcDescBind() {
        return wcDescBind;
    }

    public void setHdrFreeze(StringBuffer hdrFreeze) {
        this.hdrFreeze = hdrFreeze;
    }

    public StringBuffer getHdrFreeze() {
        return hdrFreeze;
    }

    public String syncOperations() {

        ADFBeanUtils.findOperation("checkOperations").execute();

        setHdrFreeze(new StringBuffer("Y"));

        return null;
    }

    public String createOrgPrf() {

        ADFBeanUtils.findOperation("CreateOrgMnfProfile").execute();

        setHdrFreeze(new StringBuffer("Y"));

        return null;
    }

    public void attachmentDeleteACE(ActionEvent actionEvent) {
        System.out.println("attachmentDeleteACE--------------------");
        String path = actionEvent.getComponent().getAttributes().get("pathWithName").toString();
        System.out.println("File Path : "+path);
        String rowKey = actionEvent.getComponent().getAttributes().get("rowKey").toString();
        System.out.println("Key Value : "+rowKey);
        try{ 
               if(path != null){
               System.out.println("File Path : "+path.toString());
               ob = ADFBeanUtils.findOperation("deleteAttachFileRow");
               ob.getParamsMap().put("path", path);
               ob.execute();
                //ADFUtils.findOperation("DeleteAttach").execute();
                 ADFBeanUtils.findOperation("Commit").execute();
               //ADFUtils.findOperation("ExecuteAttach").execute();
               }
            
        
        /*   String path = null;

        Object pathObj = actionEvent.getComponent().getAttributes().get("pathWithName");
        if (pathObj != null)
            path = actionEvent.getComponent().getAttributes().get("pathWithName").toString();

        System.out.println("File path in AMimpl" + path);
        File f = new File(path);
        JSFUtils.addFacesInformationMessage("Delete file at path "+path);
        try {
            JSFUtils.addFacesInformationMessage("Delete file at path "+path);
            boolean success = f.delete();
            System.out.println("File Delete d"); */
        } catch (Exception x) {
//            JSFUtils.addFacesInformationMessage("Failed to  file at path "+path+" exception "+x);
//            x.printStackTrace();
//            System.err.format("%s: no such" + " file or directory%n", path);
        }

       // ADFUtils.findOperation("DeleteAttach").execute();
        //ADFUtils.findOperation("Commit").execute();
    }

    public void createOperationACE(ActionEvent actionEvent) {
        // ADFUtils.findOperation("createOperation").execute();
        Integer mode = (Integer) getAttrsVal("MnfRtVOIterator", "RtBasis");
        if (mode == 15) {
            String preVal = null;
            
            
            
            System.out.println(" Pre Val 2::" +preVal + "    " + bindCopyPrevious.getValue());
            if (bindCopyPrevious.getValue() == null) {
                System.out.println("test... ");
                showFacesMessage("Refrenced Doc is required.", "E", false, "F", bindCopyPrevious.getClientId());
                return;
            }
        }
        //generate next serial no.
        ob = ADFBeanUtils.findOperation("getMnfRtSerialNo");
        ob.execute();

        Integer srNo = Integer.parseInt(ob.getResult().toString());
        System.out.println("srNo " + srNo);
        onRowCreate();

        setAttrsVal("MnfRtOpIterator", "OpSrNo", srNo);

        setHdrFreeze(new StringBuffer("Y"));
    }

    public String onRowCreate() {

        DCIteratorBinding dciter = ADFBeanUtils.findIterator("MnfRtOpIterator");
        //access the underlying RowSetIterator
        RowSetIterator rsi = dciter.getRowSetIterator();
        //get handle to the last row
        Row lastRow = rsi.last();
        //obtain the index of the last row
        int lastRowIndex = rsi.getRangeIndexOf(lastRow);
        //create a new row
        Row newRow = rsi.createRow();
        //initialize the row
        newRow.setNewRowState(Row.STATUS_INITIALIZED);
        //add row to last index + 1 so it becomes last in the range set
        rsi.insertRowAtRangeIndex(lastRowIndex + 1, newRow);
        //make row the current row so it is displayed correctly
        rsi.setCurrentRow(newRow);
        return null;
    }

    /**
     * Method to set value for an attribute of current row for an Iterator
     * @param iter for iterator name
     * @param for attribute name
     * @param for attribute value
     * **/
    public void setAttrsVal(String iter, String attrs, Object val) {

        if (iter != null && attrs != null) {
            DCIteratorBinding dcIter = ADFBeanUtils.findIterator(iter);

            if (dcIter.getCurrentRow() != null)
                dcIter.getCurrentRow().setAttribute(attrs, val);

        }
    }

    /**
     * Method to get value of attribute of current row for an Iterator
     * @param iter for iterator name
     * @param for attribute name
     * **/
    public Object getAttrsVal(String iter, String attrs) {

        if (iter != null && attrs != null) {
            DCIteratorBinding dcIter = ADFBeanUtils.findIterator(iter);

            if (dcIter.getCurrentRow() != null)
                return dcIter.getCurrentRow().getAttribute(attrs);

        }
        return null;
    }


    public void RtActvVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {

            if ((valueChangeEvent.getNewValue().equals("N") || valueChangeEvent.getNewValue().equals(false))) {
                setAttrsVal("MnfRtVOIterator", "RtInacttvDt", new Timestamp(System.currentTimeMillis()));

            } else {
                System.out.println("remove data ");
                setAttrsVal("MnfRtVOIterator", "RtInacttvDt", null);
                setAttrsVal("MnfRtVOIterator", "RtInactvReason", null);
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(valueChangeEvent.getComponent().getParent());
        }
    }


    public void opDelete(ActionEvent actionEvent) {

        Integer srNo = (Integer) actionEvent.getComponent().getAttributes().get("srNo");

        // ADFUtils.findOperation("DeleteOperation").execute();

        //get the key value from f:attribute attached to the calling command link
        Key key = (oracle.jbo.Key) actionEvent.getComponent().getAttributes().get("transKey");
        System.out.println(" key------" + key);
        removeRow("MnfRtOpIterator", key);

        System.out.println("srNo " + srNo);
        reorderSrNo(srNo);
    }

    protected void reorderSrNo(Integer Sr_No) {

        DCIteratorBinding iter = ADFBeanUtils.findIterator("MnfRtOpIterator");
        ViewObject mnfRtOpVO = iter.getViewObject();
        RowSetIterator rit = mnfRtOpVO.createRowSetIterator(null);
        Row mnfRtRow = null;
        Integer currSrNO = 0;
        while (rit.hasNext()) {
            mnfRtRow = rit.next();
            currSrNO = (Integer) mnfRtRow.getAttribute("OpSrNo");

            if (currSrNO.compareTo(Sr_No) == 1) {
                System.out.println("set " + (currSrNO - 1));
                mnfRtRow.setAttribute("OpSrNo", currSrNO - 1);
            }

        }
        rit.closeRowSetIterator();
    }

    public String reviseAction() {


        ADFBeanUtils.findOperation("enableReservedMode").execute();

        ADFBeanUtils.findOperation("reviseRoute").execute();

        setMode(new StringBuffer("E"));

        setHdrFreeze(new StringBuffer("Y"));
        ADFContext.getCurrent().getPageFlowScope().put("FREEZE_FLG","N");

        return null;
    }

    public void setRtHdrForm(RichPanelFormLayout rtHdrForm) {
        this.rtHdrForm = rtHdrForm;
    }

    public RichPanelFormLayout getRtHdrForm() {
        return rtHdrForm;
    }

    public void setDtlTabBinding(RichPanelTabbed dtlTabBinding) {
        this.dtlTabBinding = dtlTabBinding;
    }

    public RichPanelTabbed getDtlTabBinding() {
        return dtlTabBinding;
    }

    public void replicateOrgACE(ActionEvent actionEvent) {


        ADFBeanUtils.findOperation("enableReservedMode").execute();

        ADFBeanUtils.findOperation("replicateAllOrg").execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(actionEvent.getComponent().getParent());

    }

    public void setOpCollectionBinding(RichPanelCollection opCollectionBinding) {
        this.opCollectionBinding = opCollectionBinding;
    }

    public RichPanelCollection getOpCollectionBinding() {
        return opCollectionBinding;
    }

    public void opNmVCE(ValueChangeEvent vce) {
      vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(opCollectionBinding);
       AdfFacesContext.getCurrentInstance().addPartialTarget(wcNmeBind);
  }

    public void wcNmVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(opCollectionBinding);
    }

    public void setAttachFileBinding(RichInputFile attachFileBinding) {
        this.attachFileBinding = attachFileBinding;
    }

    public RichInputFile getAttachFileBinding() {
        return attachFileBinding;
    }

    public void orgActvVCE(ValueChangeEvent valueChangeEvent) {
        // Add event code here...InactvDt
        if (valueChangeEvent.getNewValue() != null) {

            if ((valueChangeEvent.getNewValue().equals("N") || valueChangeEvent.getNewValue().equals(false))) {
                setAttrsVal("OrgMnfRtVOIterator", "InactvDt", new Timestamp(System.currentTimeMillis()));

            } else {
                System.out.println("remove data ");
                setAttrsVal("OrgMnfRtVOIterator", "InactvDt", null);
                setAttrsVal("OrgMnfRtVOIterator", "InactvReason", null);
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(valueChangeEvent.getComponent().getParent());
        }
    }

    public void deleteOrgACE(ActionEvent actionEvent) {

        //get the key value from f:attribute attached to the calling command link
        Key key = (oracle.jbo.Key) actionEvent.getComponent().getAttributes().get("orgKey");
        // System.out.println(" key------" + key);

        removeRow("OrgMnfRtVOIterator", key);

        AdfFacesContext.getCurrentInstance().addPartialTarget(actionEvent.getComponent());
        AdfFacesContext.getCurrentInstance().addPartialTarget(orgFormBinding);
    }

    public void setOrgFormBinding(RichPanelFormLayout orgFormBinding) {
        this.orgFormBinding = orgFormBinding;
    }

    public RichPanelFormLayout getOrgFormBinding() {
        return orgFormBinding;
    }

    public void removeRow(String iterName, Key key) {

        if (iterName != null && key != null) {
            ViewObject vo = ADFUtils.findIterator(iterName).getViewObject();
            Row row = vo.getRow(key);

            if (row != null)
                row.remove();
        }
    }

    public void setWcNmeBind(RichInputListOfValues wcNmeBind) {
        this.wcNmeBind = wcNmeBind;
    }

    public RichInputListOfValues getWcNmeBind() {
        return wcNmeBind;
    }

    public void uploadFileVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        /* List<UploadedFile> files = this.getUploadedFile();
        
        System.out.println("Max size is at VAL: " + attachFileBinding.getMaximumFiles() ) ;
       
        int i=0;
        Iterator<UploadedFile> iterator = files.iterator();
        while(iterator.hasNext())
        {
            //iterator.next();
            i++;        
            
        }

        System.out.println("Total Uploaded File is : " + i  );
        
        if(attachFileBinding.getMaximumFiles() == this.getUploadedFile().size())
        {}
        else{
            JSFUtils.addFacesErrorMessage("Error in Uploading Files, Attach Only " + attachFileBinding.getMaximumFiles() + "Files !");        
            uploadedFile.clear();
            attachFileBinding.resetValue();
        } */

    }

    public void deleteAttachACE(ActionEvent actionEvent) {
        System.out.println("attachmentDeleteACE--------------------");
        String path = actionEvent.getComponent().getAttributes().get("pathWithName").toString();
        System.out.println("File Path : "+path);
        String rowKey = actionEvent.getComponent().getAttributes().get("rowKey").toString();
        System.out.println("Key Value : "+rowKey);
        try{ 
               if(path != null){
               System.out.println("File Path : "+path.toString());
               ob = ADFBeanUtils.findOperation("deleteAttachFileRow");
               ob.getParamsMap().put("path", path);
               ob.execute();
                 ADFBeanUtils.findOperation("Commit").execute();
               }            
        } catch (Exception x) {
        System.out.println(x);
        }
    }

    public void preSrNoVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        DCIteratorBinding iter = ADFBeanUtils.findIterator("MnfRtOpIterator");
        ViewObject mnfRtOpVO = iter.getViewObject();
        RowSetIterator rit = mnfRtOpVO.createRowSetIterator(null);
        Integer rowCnt = rit.getRowCount();
        System.out.println("totalrowCount " + rowCnt +"  obj " + object);
        if(object != null){
        if(Integer.parseInt(object.toString()) > rowCnt )
        {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          "Operation Sr. No does not exist.", null));
            
        }}
        

    }

    public void executionTypeVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {

            if ((valueChangeEvent.getNewValue()==142)) {
                opParSrNoBind.setRequired(false);
            }
            }
    }

    public void setOpParSrNoBind(RichInputText opParSrNoBind) {
        this.opParSrNoBind = opParSrNoBind;
    }

    public RichInputText getOpParSrNoBind() {
        return opParSrNoBind;
    }

    public void addPredecessorOptionACE(ActionEvent actionEvent) {
        // Add event code here...
       // ADFBeanUtils.findOperation("createMnfPredesorRow").execute();
      
        oracle.jbo.domain.Number perUsed=(oracle.jbo.domain.Number)bindPerUsed.getValue();
        OperationBinding op=ADFBeanUtils.findOperation("checkOperationSrNo");
        op.execute();
        adfLog.info("Value of resultttt:::"+op.getResult());
        if(op.getResult()!=null && op.getResult().toString().equals("Y")){
            DCIteratorBinding iter = ADFBeanUtils.findIterator("MnfRtOpPredVO1Iterator");
            ViewObject mnfRtPredOpVO = iter.getViewObject();
            RowSetIterator rit = mnfRtPredOpVO.createRowSetIterator(null);
            Integer rowCnt = rit.getRowCount();
            adfLog.info("Number of Rows in Predesscor Vo::"+rowCnt);
            if(rowCnt>0)
            {
                if(bindPredOpName.getValue()==null){
                showFacesMessage("Operation Name is required.", "E", false, "F", bindPredOpName.getClientId());
                return;
            }
                if(bindPerUsed.getValue()==null){
                    showFacesMessage("Percentage is required.", "E", false, "F", bindPerUsed.getClientId());
                    return;
                }
                
                if(perUsed.compareTo(new oracle.jbo.domain.Number(0))==-1){
                    
                    showFacesMessage("Percentage is required.", "E", false, "F", bindPerUsed.getClientId());
                    return;
                }
                OperationBinding op1=ADFBeanUtils.findOperation("checkOpPercentage");
                op1.getParamsMap().put("per",perUsed);
                op.execute();
                adfLog.info("Value of op::"+op.getResult());
                if(op.getResult()!=null && op.getResult().toString().equals("N")){
                               showFacesMessage("100 Percentage Usage of this Operation", "E",false, "F", bindPerUsed.getClientId());
                               return;
                }
                adfLog.info("---------------------------"+rowCnt);        
            ADFBeanUtils.findOperation("CreateInsert").execute();
            }
            else {
                
                ADFBeanUtils.findOperation("CreateInsert").execute();
            }
        }
        else
        ADFUtils.showFacesMsg("No predecessor for first Operation.", null,FacesMessage.SEVERITY_ERROR, null);
        
        
        
    }

    public void predOperationValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        adfLog.info("Validator is called");
        if(object!=null)
        {
            adfLog.info("Validator is called in if loop");
        OperationBinding op=ADFBeanUtils.findOperation("duplicatePredOperation");
        op.getParamsMap().put("predOp",object.toString());
        op.execute();
        adfLog.info("Value of result::"+op.getResult());
        String result=op.getResult().toString();
        if(op.getResult()!=null){
            if( result.equals("Y"))
                showFacesMessage("Duplicate Operation Exists.", "E", false, "V", bindPredOpName.getClientId());
            
            if( result.equals("X"))
                showFacesMessage("Same Operation can not be Predesscor itself.", "E", false, "V", bindPredOpName.getClientId());
            
           
        }
        }
        else {
            showFacesMessage("Operation Name is required.", "E", false, "V", bindPredOpName.getClientId());  
        }
        
    }

    public void setBindPredOpName(RichInputListOfValues bindPredOpName) {
        this.bindPredOpName = bindPredOpName;
    }

    public RichInputListOfValues getBindPredOpName() {
        return bindPredOpName;
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

    public String deletePredOpNmAction() {
        // Add event code here...
        return null;
    }

    public void deletePredOperationNmAL(ActionEvent actionEvent) {
        // Add event code here...
        adfLog.info("deletePredOperationNmAL");
        Key key = (oracle.jbo.Key) actionEvent.getComponent().getAttributes().get("transKey");
        System.out.println(" key------" + key);
        removeRow("MnfRtOpPredVO1Iterator", key);
    }
    
    
    /**
     *      Method to show validation message(I,E,W)
     *      mesg:Message to display
     *      sev:Severity(I,E,W)
     *      chk:true=if resource bundle is used
     *      typFlg: 'F' for FM , 'V' for VE
     *      clientId : client id for UI component
     * */
    public void showFacesMessage(String mesg, String sev, Boolean chk, String typFlg, String clientId) {
        FacesMessage message = new FacesMessage(mesg);
        if (chk == true) {
            String msg = resolvEl("#{bundle['" + mesg + "']}");
            message = new FacesMessage(msg);
        }
        if (sev.equalsIgnoreCase("E")) {
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
        } else if (sev.equalsIgnoreCase("W")) {
            message.setSeverity(FacesMessage.SEVERITY_WARN);
        } else if (sev.equalsIgnoreCase("I")) {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
        } else {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
        }
        if (typFlg.equals("F")) {
            FacesContext.getCurrentInstance().addMessage(clientId, message);
        } else if (typFlg.equals("V")) {
            throw new ValidatorException(message);
        }
    }

    public void precentageUsedValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        //uIComponent.processUpdates(FacesContext.getCurrentInstance());
        // Add event code here...
        //checkOpPercentage
        if(object !=null) {
            oracle.jbo.domain.Number val=(oracle.jbo.domain.Number)object;
            oracle.jbo.domain.Number value=(oracle.jbo.domain.Number)object;
            if(value.compareTo(new oracle.jbo.domain.Number(0))==-1)
            showFacesMessage("Percentage for Operation can not be negative", "E",false, "V", null);  
            if(value.compareTo(new oracle.jbo.domain.Number(100))==1)
            showFacesMessage("Percentage must be less than 100", "E",false, "V", null);
            OperationBinding op=ADFBeanUtils.findOperation("checkOpPercentage");
            op.getParamsMap().put("per",val);
            op.execute();
            adfLog.info("Value of op::"+op.getResult());
            if(op.getResult()!=null && op.getResult().toString().equals("N"))
                showFacesMessage("100 Percentage Usage of this Operation", "E",false, "V", null);
        }
        else
        {
        showFacesMessage("Percentage is required.", "E",false, "V", null);
        }
    }

    public void setBindfreezeVar(RichSelectBooleanCheckbox bindfreezeVar) {
        this.bindfreezeVar = bindfreezeVar;
    }

    public RichSelectBooleanCheckbox getBindfreezeVar() {
        return bindfreezeVar;
    }

    public void perUsageVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        oracle.jbo.domain.Number val=(oracle.jbo.domain.Number)valueChangeEvent.getNewValue();
        adfLog.info("perUsage VCL is called");
        OperationBinding op=ADFBeanUtils.findOperation("checkOpPercentage");
        op.getParamsMap().put("per",val);
        op.execute();
        adfLog.info("Value of op::"+op.getResult());
        if(op.getResult()!=null && op.getResult().toString().equals("N"))
            showFacesMessage("100 Percentage Usage of this Operation", "E",false, "V", null);
    }

    public void setBindPerUsed(RichInputText bindPerUsed) {
        this.bindPerUsed = bindPerUsed;
    }

    public RichInputText getBindPerUsed() {
        return bindPerUsed;
    }

    public void RtActvValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        //adfLog.info("Value of object::"+object);
        String obj=(object.toString());
        if(obj.equalsIgnoreCase("false")){
        ob = ADFBeanUtils.findOperation("checkRouteUsage");
        ob.execute();
        //adfLog.info("Value of result:::"+ob.getResult());
        if(ob.getResult()!=null){
        Integer r1=(Integer)ob.getResult();
        if(r1.compareTo(new Integer(1))!=0 ){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          "You can not inactive route as it is being used in  active BOM.", null));
        }

    }
}
 }

    public void moveUpActionListener(ActionEvent actionEvent) {
        // Add event code here...
        ob = ADFBeanUtils.findOperation("moveUpOperation");
                ob.execute();
                //AdfFacesContext.getCurrentInstance().addPartialTarget(operationtblBind);
                //boolean val=operationtblBind.isSortable("true");
                List sortList=new ArrayList();
                SortCriterion sc1=new SortCriterion("OpSrNo",true);
                adfLog.info("value of column to sort:::"+sc1.getProperty());
                sortList.add(sc1);
                operationtblBind.setSortCriteria(sortList);
                AdfFacesContext.getCurrentInstance().addPartialTarget(operationtblBind);
                adfLog.info("end of moveUpAction");
    }

    public void moveDownActionListener(ActionEvent actionEvent) {
        // Add event code here...
        
                ob = ADFBeanUtils.findOperation("moveDownOperation");
                ob.execute();
                List sortList=new ArrayList();
                SortCriterion sc1=new SortCriterion("OpSrNo",true);
                adfLog.info("value of to sort:::"+sc1.getProperty());
                sortList.add(sc1);
                operationtblBind.setSortCriteria(sortList);
                AdfFacesContext.getCurrentInstance().addPartialTarget(operationtblBind);
                adfLog.info("end of moveDownAction");
    }

    public void setOperationtblBind(RichTable operationtblBind) {
        this.operationtblBind = operationtblBind;
    }

    public RichTable getOperationtblBind() {
        return operationtblBind;
    }

    public void setBindCopyPrevious(RichInputListOfValues bindCopyPrevious) {
        this.bindCopyPrevious = bindCopyPrevious;
    }

    public RichInputListOfValues getBindCopyPrevious() {
        return bindCopyPrevious;
    }
}
