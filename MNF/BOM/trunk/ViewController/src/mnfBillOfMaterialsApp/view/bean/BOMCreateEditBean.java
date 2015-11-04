
package mnfBillOfMaterialsApp.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.model.ADFModelUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import mnfBillOfMaterialsApp.view.utils.ADFUtils;
import mnfBillOfMaterialsApp.view.utils.JSFUtils;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelTabbed;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.rules.JboPrecisionScaleValidator;

import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;
import org.apache.myfaces.trinidad.model.UploadedFile;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

public class BOMCreateEditBean {

    // value for mode V : view, C : create, E : edit
    private StringBuffer mode = new StringBuffer(JSFUtils.resolveExpressionAsString("#{pageFlowScope.MNF_BOM_MOD_ID}"));
    private StringBuffer modeActive = new StringBuffer("Y");
    private StringBuffer modePrice = new StringBuffer("Y");
    private StringBuffer chckLastItem = new StringBuffer("A");


    private List<UploadedFile> uploadedFile;
    private RichLink addQueueBind;
    private RichInputListOfValues transQcParamDescBind;
    private RichSelectOneChoice bomUsageBind;

    public void setChckLastItem(StringBuffer chckLastItem) {
        this.chckLastItem = chckLastItem;
    }

    public StringBuffer getChckLastItem() {
        return chckLastItem;
    }
    private StringBuffer hdrFreeze = new StringBuffer("N");
    protected oracle.binding.OperationBinding ob = null;
    private RichPanelFormLayout opItemDtlFormBinding;
    private RichLink refreshItmDtlBinding;
    private String workFlowId;
    private RichSelectOneChoice itmUom1Binding;
    private RichOutputText allItmTotAmtBinding;
    private RichOutputText allItmPriceBinding;
    private RichLink copyReferenceLnk;
    private RichPanelGroupLayout routeHdrBinding;
    private RichPanelTabbed dtlTabBinding;
    private RichInputDate vldEndDtBinding;
    private RichInputDate vldFrmDtBinding;
    private RichPanelFormLayout hdrFormBinding;
    private RichTable allItemTableBinding;
    private Key key;
    private Number totAmt = new Number(0);
    private RichPanelFormLayout opDtlFormBinding;
    private RichTable bomOpTableBinding;
    private RichTable outputItmTableBinding;
    private RichTable inputItmTableBinding;
    private String imagePath = null;
    private RichSelectBooleanCheckbox returnQtyFlagBindVar;
    private RichInputText retrnQtyBindVar;
    private RichSelectOneChoice itmReturnTypBindVar;
    private RichSelectOneChoice itmTypeBindVar;
    private RichInputText itmQtyBindVar;
    private RichSelectOneChoice itmBasisBindVar;
    private RichTable paramTableBinding;
    private RichLink addParamBinding;
    private RichInputText paramValueBinding;
    private RichSelectBooleanCheckbox defaultBomBindVar;
    private RichLink saveBomLinkBinding;
    private RichInputText itmTotAmtBindVar;
    private RichLink iamntBinding;
    private RichInputText itmPriceBindVar;
    private RichInputDate usrCreatDtBindVar;
    private RichSelectBooleanCheckbox isValuePercBinding;
    private RichInputText minTolBindVar;
    private RichInputText maxTolBindVar;
    private RichInputText minValBindVar;
    private RichInputText maxValBindVar;
    private RichTable qcParamTable;
    private RichInputText paramValueBindVar;
    private RichSelectOneChoice valueTypeBindVar;
    private RichSelectBooleanCheckbox principleItemBinding;
    private RichInputListOfValues refrencedBomBinding;

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {

        if (imagePath == null) {
            ob = ADFUtils.findOperation("getImagePathFromServer");
            ob.execute();

            if (ob.getResult() != null) {
                imagePath = ob.getResult().toString();
                //System.out.println(" path in bean " + imagePath);
            }
        }
        return imagePath;
    }


    public void setKey(Key key) {
        this.key = key;
    }

    public Key getKey() {
        return key;
    }

    public void setWorkFlowId(String workFlowId) {
        this.workFlowId = workFlowId;
    }

    public String getWorkFlowId() {
        return workFlowId;
    }

    public void setHdrFreeze(StringBuffer hdrFreeze) {
        this.hdrFreeze = hdrFreeze;
    }

    public StringBuffer getHdrFreeze() {
        return hdrFreeze;
    }

    public void setUploadedFile(List<UploadedFile> uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public List<UploadedFile> getUploadedFile() {
        return uploadedFile;
    }

    /**
     * @param mode
     */
    public void setMode(StringBuffer mode) {
        this.mode = mode;
    }

    /**
     * @return
     */
    public StringBuffer getMode() {
        return mode;
    }

    public void createBomAction(ActionEvent actionEvent) {


        ADFUtils.findOperation("CreateNewBom").execute();
        setMode(new StringBuffer("C"));
        setHdrFreeze(new StringBuffer("N"));

        //set the bind var for header VO
        ADFUtils.findOperation("setBomParam").execute();
        ADFUtils.findOperation("resetOverhead").execute();
    }

    public void editBomAction(ActionEvent actionEvent) {
        Integer pending = (Integer) ADFUtils.callBindingsMethod("getDocUsrFromWF", null, null);

        System.out.println(pending + "--------" + getUserId());

        if (!pending.equals(getUserId())) {
            ob = ADFUtils.findOperation("getUserName");
            ob.getParamsMap().put("UsrId", pending);
            ob.execute();
            String usrName = ob.getResult().toString();
System.out.println("--------"+ usrName);
            // StringBuffer usrName = new StringBuffer(getUserNm(pending));
            StringBuilder saveMsg = new StringBuilder();
            if ("0".equals(usrName)) {
                String msg2 =
                    "This BOM is not editable for this organization / Work Flow is not Defined / Bom is Pending at other User. ";
                saveMsg = new StringBuilder("<html><body><p><b>" + msg2 + "</b></p>");
                // saveMsg.append("Pending at - <b> " + usrName + "</b>");
                saveMsg.append("</body></html>");

            } else {

                String msg2 = "This BOM is pending at other user for approval, You can not edit it. ";
                saveMsg = new StringBuilder("<html><body><p><b>" + msg2 + "</b></p>");
                saveMsg.append("Pending at - <b> " + usrName + "</b>");
                saveMsg.append("</body></html>");

            }
            // StringBuffer usrName = new StringBuffer(getUserId().toString());
            // use the helper method to show faces messages.
            //  System.out.println(" --------------------  " + usrName);

            //This BOM is pending at other user for approval, You can not edit this.*
            //create multi line faces message using StringBuilder

            //String msg2 = JSFUtils.resolveExpression("#{bundle['MSG.506']}").toString();
            ADFUtils.showFacesMsg(saveMsg.toString(), null, FacesMessage.SEVERITY_INFO, null);

        } else {

            setMode(new StringBuffer("E"));

            setHdrFreeze(new StringBuffer("Y"));
        }

        /* ob = ADFBeanUtils.findOperation("chkDefaultWsVal");
        String wcid = getAttrsVal("MnfBomVOIterator", "OutptItmId").toString();
        ob.getParamsMap().put("WcId", wcid);
        ob.execute();
         Object result = ob.getResult();
         System.out.println("returned from impl " + result);
        if (result != null && result.toString().equalsIgnoreCase("true")) {
            defaultBomBindVar.setDisabled(true);
        } */
    }

    public String saveBomAction() {

        //validate before save
        if ("Y".compareTo(draftValidation()) == 0) {

            ADFUtils.findOperation("refreshAllItem").execute();
            outItemAmount();
            refreshOutItmPrice();

            setAttrsVal("MnfBomVOIterator", "TotOutptItmAmt", getTotAmt());
            ADFUtils.findOperation("setConsmtnFlg").execute();

            ADFUtils.findOperation("beforeSave").execute();

            //ADFUtils.findOperation("Commit").execute();
            setMode(new StringBuffer("V"));

            workFlowCall();

            ADFUtils.findOperation("Commit").execute();

            setModeActive(new StringBuffer("Y"));

            JSFUtils.addFacesInformationMessage("Record Saved Successfully");
            return "Y";
        } else
            //return N if validation fails
            return "N";
    }

    public String saveAndForwardAction() {

        if ("Y".compareTo(validateAll()) == 0) {

            ADFUtils.findOperation("refreshAllItem").execute();
            outItemAmount();
            refreshOutItmPrice();
            setAttrsVal("MnfBomVOIterator", "TotOutptItmAmt", getTotAmt());
            ADFUtils.findOperation("setConsmtnFlg").execute();

            workFlowCall();

            ADFUtils.findOperation("beforeSave").execute();

            ADFUtils.findOperation("Commit").execute();

            ob = ADFUtils.findOperation("getWfId");
            ob.execute();
            setModeActive(new StringBuffer("Y"));
            setMode(new StringBuffer("V"));

            //  System.out.println("ob.getResult() " + ob.getResult());
            if (ob.getResult() != null && !(ob.getResult().toString().equalsIgnoreCase("0"))) {
                setWorkFlowId(ob.getResult().toString());
                return "wfCall";
            } else {

                ADFModelUtils.showFormattedFacesMessage("Record Saved Successfully", "", FacesMessage.SEVERITY_INFO);

                ADFModelUtils.showFormattedFacesMessage("Error in getting Work Flow Id", "",
                                                        FacesMessage.SEVERITY_ERROR);

                return null;
            }
        }

        return null;
    }

    /**
     * Method to check validation before draft save
     * return Y if validation are okay otherwise N
     * **/
    public String draftValidation() {

        //validate for Bom name
        if (getAttrsVal("MnfBomVOIterator", "BomDesc") == null) {
            StringBuilder msg = new StringBuilder("");
            msg.append("<br/><br/>Please provide BOM Name");
            ADFModelUtils.showFormattedFacesMessage("Unique BOM Name is Required.", msg.toString(),
                                                    FacesMessage.SEVERITY_ERROR);


            // ADFUtils.showFacesMsg("BOM Name is Required", "Please enter BOM Name", FacesMessage.SEVERITY_ERROR, null);
            return "N";
        }


        ob = ADFUtils.findOperation("validateBomNm");
        ob.execute();

        if ("Y".equals(ob.getResult())) {
            StringBuilder msg = new StringBuilder("");
            msg.append(getAttrsVal("MnfBomVOIterator", "BomDesc"));
            msg.append("is used by another BOM.");
            msg.append("<br/><br/>Please provide another BOM Name");
            ADFModelUtils.showFormattedFacesMessage("Unique BOM Name is Required.", msg.toString(),
                                                    FacesMessage.SEVERITY_ERROR);
            /* ADFUtils.showFacesMsg("Unique BOM Name is Required. " + getAttrsVal("MnfBomVOIterator", "BomDesc") +
                                  " is used by another BOM.",
                                  "Unique BOM Name is Required. " + getAttrsVal("MnfBomVOIterator", "BomDesc") +
                                  " is used by another BOM.", FacesMessage.SEVERITY_ERROR, null); */

            return "N";
        }
        //validate for output Item in BOM
        if (getAttrsVal("MnfBomVOIterator", "BomItmDesc") == null) {

            StringBuilder msg = new StringBuilder("");
            msg.append("<br/><br/>Please enter Output Item");
            ADFModelUtils.showFormattedFacesMessage("BOM Output Item is Required.", msg.toString(),
                                                    FacesMessage.SEVERITY_ERROR);


            //ADFUtils.showFacesMsg("BOM Output Item is Required", "Please enter Output Item. ",
            //                      FacesMessage.SEVERITY_ERROR, null);
            return "N";
        }

        //validate for operation output item in every operation

        /*
        if (validateOutputItm().compareTo(0) == 1) {
            StringBuilder msg = new StringBuilder("");
            msg.append("<br/><br/>Please provide Operation Output Item !");
            ADFModelUtils.showFormattedFacesMessage("Operation Output Item for all Operation are required.",
                                                    msg.toString(), FacesMessage.SEVERITY_ERROR);
            return "N";
        } */

        //validate for input item in every operation

        /*    if (validateInputItm().compareTo(0) == 1) {

            StringBuilder msg = new StringBuilder("");
            msg.append("<br/><br/>Please provide Input Item !");
            ADFModelUtils.showFormattedFacesMessage("Input Item for all Operation are required.", msg.toString(),
                                                    FacesMessage.SEVERITY_ERROR);
           return "N";
        } */

        

        //validate for route RtId
        if (getAttrsVal("MnfBomVOIterator", "RtId") == null) {
            StringBuilder msg = new StringBuilder("");
            msg.append("<br/><br/>Please enter Route.");
            ADFModelUtils.showFormattedFacesMessage("Route for BOM is Required.", msg.toString(),
                                                    FacesMessage.SEVERITY_ERROR);


            // ADFUtils.showFacesMsg("Route for BOM is Required", "Please enter Route. ", FacesMessage.SEVERITY_ERROR,
            //                       null);
            return "N";
        }

        //if bom not active then inactive reason is required
        if (getAttrsVal("MnfBomVOIterator", "BomActv") != null &&
            (getAttrsVal("MnfBomVOIterator", "BomActv").toString()).compareToIgnoreCase("N") == 0 &&
            getAttrsVal("MnfBomVOIterator", "BomInactvReason") == null) {

            ADFModelUtils.showFormattedFacesMessage("Inactive Reason is Required.", "", FacesMessage.SEVERITY_ERROR);


            //ADFUtils.showFacesMsg("Inactive Reason is Required. ", "Inactive Reason is Required.",
            //                      FacesMessage.SEVERITY_ERROR, null);
            return "N";
        }

        if ("Y".equals(getRetVal())) {
            ADFUtils.showFacesMsg("Organization!", "Operation Item/Items are not replicated in an Organization",
                                  FacesMessage.SEVERITY_ERROR, null);
            return "N";
        }

        return "Y";
    }

    /**
     * Method to check all validation before save
     * return Y if validation are okay otherwise N
     * **/
    public String validateAll() {

        if (getAttrsVal("MnfBomVOIterator", "TotOutptItmAmt") == null) {

            StringBuilder msg = new StringBuilder("");
            msg.append("<br/><br/>Total Quantity not set");
            ADFModelUtils.showFormattedFacesMessage("BOM operation doesn't have total quantity.", msg.toString(),
                                                    FacesMessage.SEVERITY_ERROR);

            return "N";
        }

        //validate for Bom name
        if (getAttrsVal("MnfBomVOIterator", "BomDesc") == null) {

            StringBuilder msg = new StringBuilder("");
            msg.append("<br/><br/>Please provide BOM Name");
            ADFModelUtils.showFormattedFacesMessage("Unique BOM Name is Required.", msg.toString(),
                                                    FacesMessage.SEVERITY_ERROR);


            //  ADFUtils.showFacesMsg("BOM Name is Required", "Please enter BOM Name", FacesMessage.SEVERITY_ERROR, null);
            return "N";
        }


        ob = ADFUtils.findOperation("validateBomNm");
        ob.execute();

        if ("Y".equals(ob.getResult())) {

            StringBuilder msg = new StringBuilder("");
            msg.append(getAttrsVal("MnfBomVOIterator", "BomDesc"));
            msg.append("is used by another BOM.");
            msg.append("<br/><br/>Please provide another BOM Name");
            ADFModelUtils.showFormattedFacesMessage("Unique BOM Name is Required.", msg.toString(),
                                                    FacesMessage.SEVERITY_ERROR);


            /* ADFUtils.showFacesMsg("Unique BOM Name is Required. " + getAttrsVal("MnfBomVOIterator", "BomDesc") +
                                  " is used by another BOM.",
                                  "Unique BOM Name is Required. " + getAttrsVal("MnfBomVOIterator", "BomDesc") +
                                  " is used by another BOM.", FacesMessage.SEVERITY_ERROR, null); */

            return "N";
        }
        //validate for output Item in BOM
        if (getAttrsVal("MnfBomVOIterator", "BomItmDesc") == null) {

            StringBuilder msg = new StringBuilder("");
            msg.append("<br/><br/>Please enter Output Item");
            ADFModelUtils.showFormattedFacesMessage("BOM Output Item is Required.", msg.toString(),
                                                    FacesMessage.SEVERITY_ERROR);

            // ADFUtils.showFacesMsg("BOM Output Item is Required", "Please enter Output Item. ",
            //                     FacesMessage.SEVERITY_ERROR, null);
            return "N";
        }

        //validate for operation output item in every operation

        if (validateOutputItm().compareTo(0) == 1) {

            StringBuilder msg = new StringBuilder("");
            msg.append("<br/><br/>Please provide Operation Output Item !");
            ADFModelUtils.showFormattedFacesMessage("Operation Output Item for all Operation are required.",
                                                    msg.toString(), FacesMessage.SEVERITY_ERROR);

            /* ADFUtils.showFacesMsg("Operation Output Item for all Operation are required",
                                  "Operation Output Item for all Operation are required. ", FacesMessage.SEVERITY_ERROR,
                                  null); */

            return "N";
        }

        //validate for input item in every operation

        if (validateInputItm().compareTo(0) == 1) {

            StringBuilder msg = new StringBuilder("");
            msg.append("<br/><br/>Please provide Input Item !");
            ADFModelUtils.showFormattedFacesMessage("Input Item for all Operation are required.", msg.toString(),
                                                    FacesMessage.SEVERITY_ERROR);

            /*  ADFUtils.showFacesMsg("Input Item for all Operation are required",
                                  "Input Item for all Operation are required. ", FacesMessage.SEVERITY_ERROR, null); */

            return "N";
        }

        //validate for route RtId
        if (getAttrsVal("MnfBomVOIterator", "RtId") == null) {

            StringBuilder msg = new StringBuilder("");
            msg.append("<br/><br/>Please enter Route.");
            ADFModelUtils.showFormattedFacesMessage("Route for BOM is Required.", msg.toString(),
                                                    FacesMessage.SEVERITY_ERROR);

            // ADFUtils.showFacesMsg("Route for BOM is Required", "Please enter Route. ", FacesMessage.SEVERITY_ERROR,
            //                     null);
            return "N";
        }

        ob = ADFUtils.findOperation("validateAllItems");
        ob.execute();
        if (ob.getResult() != null) {
            String errMsg = ob.getResult().toString(); //validateAllItems

            if (errMsg.length() > 1) {

                // StringBuilder msg = new StringBuilder(errMsg);
                //msg.append("<br/><br/>Please enter Route.");
                ADFModelUtils.showFormattedFacesMessage(errMsg, "", FacesMessage.SEVERITY_ERROR);

                // ADFUtils.showFacesMsg(errMsg, errMsg, FacesMessage.SEVERITY_ERROR, null);

                return "N";
            }
        }
        //if bom not active then inactive reason is required
        if (getAttrsVal("MnfBomVOIterator", "BomActv") != null &&
            (getAttrsVal("MnfBomVOIterator", "BomActv").toString()).compareToIgnoreCase("N") == 0 &&
            getAttrsVal("MnfBomVOIterator", "BomInactvReason") == null) {

            ADFModelUtils.showFormattedFacesMessage("Inactive Reason is Required.", "", FacesMessage.SEVERITY_ERROR);


            // ADFUtils.showFacesMsg("Inactive Reason is Required. ", "Inactive Reason is Required.",
            //                     FacesMessage.SEVERITY_ERROR, null);
            return "N";
        }

        if (getAttrsVal("MnfBomOpItmVOIterator", "ItmRtrnFlg") != null &&
            (getAttrsVal("MnfBomOpItmVOIterator", "ItmRtrnFlg").toString()).compareToIgnoreCase("Y") == 0) {
            Number a = (Number) getAttrsVal("MnfBomOpItmVOIterator", "ItmRtrnQty");
            if (a.compareTo(0) == 0) {

                ADFModelUtils.showFormattedFacesMessage("Return Quantity is Required.", "",
                                                        FacesMessage.SEVERITY_ERROR);
                return "N";
            }
        }

        if ("Y".equals(getRetVal())) {
            ADFUtils.showFacesMsg("Organization!", "Operation Item/Items are not exists in an Organization",
                                  FacesMessage.SEVERITY_ERROR, null);
            return "N";
        }

        return "Y";
    }

    public String cancelBomAction() {

        ADFUtils.findOperation("Rollback").execute();
        setMode(new StringBuffer("V"));
        return "back";
    }


    public void addOperationAction(ActionEvent actionEvent) {

        //check for output Item in BOM
        if (getAttrsVal("MnfBomVOIterator", "BomItmDesc") == null) {

            ADFUtils.showFacesMsg("BOM Output Item is Required", "Please enter Output Item. ",
                                  FacesMessage.SEVERITY_WARN, null);
        } else if (getAttrsVal("MnfBomVOIterator", "RtId") == null) {

            ADFUtils.showFacesMsg("Select a Route. ", "Select a Route. ", FacesMessage.SEVERITY_WARN, null);

        } else if (("54").equals(getAttrsVal("MnfBomVOIterator", "BomUsage").toString()) &&
                   getAttrsVal("MnfBomVOIterator", "TransRefBomDesc") == null) {

            ADFUtils.showFacesMsg("Reference BOM Required for Variant type. ",
                                  "Reference BOM Required for Variant type ", FacesMessage.SEVERITY_WARN, null);

        }else if (("136").equals(getAttrsVal("MnfBomVOIterator", "BomUsage").toString()) &&
                   getAttrsVal("MnfBomVOIterator", "TransRefBomDesc") == null) {

            ADFUtils.showFacesMsg("Reference BOM Required for Copy Previous type. ",
                                  "Reference BOM Required for Copy Previous type ", FacesMessage.SEVERITY_WARN, null);

        } else {
            //System.out.println(getAttrsVal("MnfBomVOIterator", "RtId") + "  ========================= ");
            if (getAttrsVal("MnfBomVOIterator", "RtId") != null) {
                ob = ADFUtils.findOperation("createBomOperations");
                ob.getParamsMap().put("docId", getAttrsVal("MnfBomVOIterator", "RtId"));
                ob.execute();
                //docId
                setHdrFreeze(new StringBuffer("Y"));

                AdfFacesContext.getCurrentInstance().addPartialTarget(dtlTabBinding);
            } else {
                JSFUtils.addFacesErrorMessage("Route cannot be added to this BOM!");
            }
        }
    }

    public String addItemAction() {
        DCIteratorBinding dcIter = ADFUtils.findIterator("MnfBomInputItmVOIterator");
        dcIter.executeQuery();

        dcIter = ADFUtils.findIterator("MnfBomOpItmAllVOIterator");
        dcIter.executeQuery();

        dcIter = ADFUtils.findIterator("MnfBomOutputItmVOIterator");
        dcIter.executeQuery();

        dcIter = ADFUtils.findIterator("MnfBomCoProductItmVOIterator");
        dcIter.executeQuery();

        ADFUtils.findOperation("createItemRow").execute();
        return null;
    }

    public void operationSelectEvent(ActionEvent actionEvent) {

        ADFUtils.findIterator("MnfBomOpItmVOIterator").executeQuery();

    }

    /**
     * Method to set current row in Iterator
     * **/

    protected void setCurrentRow(String iterNm, Object attrsVal, String attrsNm) {

        RowSetIterator iterator = ADFUtils.findIterator(iterNm).getRowSetIterator();

        Row[] r = iterator.getFilteredRows(attrsNm, attrsVal);
        // System.out.println("rows " + r.length + " r ");

        if (r.length > 0)
            iterator.setCurrentRow(r[0]);

    }

    /**
     * Method to set current row in Iterator
     * **/

    protected void setCurrentItmRow(Integer itmTyp, String itmId) {

        ViewObject vo = ADFUtils.findIterator("MnfBomOpItmVOIterator").getViewObject();
        RowSetIterator rsetIter = vo.createRowSetIterator(null);
        Row r = null;

        while (rsetIter.hasNext()) {
            r = rsetIter.next();
            // System.out.println(" itm type >" + r.getAttribute("e") + " itm id >" + r.getAttribute("ItmId"));
            if (r.getAttribute("ItmType").equals(itmTyp) && r.getAttribute("ItmId").equals(itmId)) {

                ADFUtils.findIterator("MnfBomOpItmVOIterator").getRowSetIterator().setCurrentRow(r);
                break;
            }
        }
        rsetIter.closeRowSetIterator();
    }

    public void outputItemSelectAction(ActionEvent actionEvent) {

        //get key from f:attribute attched to the link
        String itmId = actionEvent.getComponent().getAttributes().get("opOutputItem").toString(); //opOutputItem
        Integer itmTyp = Integer.parseInt(actionEvent.getComponent().getAttributes().get("itmTyp").toString());

        if (itmTyp != null && itmId != null)
            setCurrentItmRow(itmTyp, itmId);

        AdfFacesContext.getCurrentInstance().addPartialTarget(opItemDtlFormBinding);

    }

    public void inputItemSelectAction(ActionEvent actionEvent) {

        String itmId = actionEvent.getComponent().getAttributes().get("opInputItem").toString();
        Integer itmTyp = Integer.parseInt(actionEvent.getComponent().getAttributes().get("itmTyp").toString());

        if (itmTyp != null && itmId != null)
            setCurrentItmRow(itmTyp, itmId);

        AdfFacesContext.getCurrentInstance().addPartialTarget(opItemDtlFormBinding);
    }

    /**
     * Method to validate the submited rows  before commit;
     * This method will Iterator through all rows in Page and will return Y if attribute value is found
     * null. Default return value is N.
     * **/

    private String checkNullData(String iter, String attrsNm) {

        DCIteratorBinding dcIter = ADFUtils.findIterator(iter);
        ViewObject vo = dcIter.getViewObject();
        RowSetIterator rSetIter = vo.createRowSetIterator(null);

        Row r = null;
        Integer countNullVal = 0;

        while (rSetIter.hasNext()) {
            r = rSetIter.next();
            // System.out.println("getAttribute" + attrsNm + " " + r.getAttribute(attrsNm));
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

        DCIteratorBinding dcIter = ADFUtils.findIterator(iter);
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

    /**
     * Method to check duplicate value for Item and Item type
     * **/
    private boolean duplicateItmAndItmType(String iter, Object itmNm, Object itmTyp) {

        DCIteratorBinding dcIter = ADFUtils.findIterator(iter);
        ViewObject vo = dcIter.getViewObject();
        RowSetIterator rSetIter = vo.createRowSetIterator(null);

        Row r = null;
        Integer countVal = 0;

        //check the null values
        if (itmNm != null && itmTyp != null) {

            while (rSetIter.hasNext()) {
                r = rSetIter.next();
                //  System.out.println("attrsNm "+r.getAttribute("ItmDesc"));
                // System.out.println(" att type   "+ r.getAttribute("ItmType"));

                if (itmNm.equals(r.getAttribute("ItmDesc")) && itmTyp.equals(r.getAttribute("ItmType"))) {
                    countVal = countVal + 1;
                }
            }
            rSetIter.closeRowSetIterator();

            //exclude count from current row
            Row currentRow = dcIter.getCurrentRow();

            if (itmNm.equals(currentRow.getAttribute("ItmDesc")) && itmTyp.equals(currentRow.getAttribute("ItmType"))) {
                countVal = countVal - 1;
            }
        }

        return countVal == 1 ? true : false;
    }

    /**
     * Method to check duplicate value for Item in Operation
     * **/
    private boolean duplicateItmInOperation(String iter, Object itmNm, Object oprSr) {

        DCIteratorBinding dcIter = ADFUtils.findIterator(iter);
        ViewObject vo = dcIter.getViewObject();
        RowSetIterator rSetIter = vo.createRowSetIterator(null);

        Row r = null;
        Integer countVal = 0;

        //check the null values
        if (itmNm != null && oprSr != null) {

            while (rSetIter.hasNext()) {
                r = rSetIter.next();
                // System.out.println("attrsNm "+r.getAttribute(attrsNm));

                if (itmNm.equals(r.getAttribute("ItmDesc")) && oprSr.equals(r.getAttribute("OpSrNo"))) {
                    countVal = countVal + 1;
                }
            }
            rSetIter.closeRowSetIterator();

            //exclude count from current row
            Row currentRow = dcIter.getCurrentRow();

            if (itmNm.equals(currentRow.getAttribute("ItmDesc")) && oprSr.equals(currentRow.getAttribute("OpSrNo"))) {
                countVal = countVal - 1;
            }
        }

        return countVal == 1 ? true : false;
    }

    /**
     * Method to check multiple operation output item in single operation
     * **/
    private boolean multpleOutitemInOpr(String iter, Integer itmTyp, Object oprSrl) {

        DCIteratorBinding dcIter = ADFUtils.findIterator(iter);
        ViewObject vo = dcIter.getViewObject();
        RowSetIterator rSetIter = vo.createRowSetIterator(null);

        Row r = null;
        Integer countVal = 0;

        //check the null values
        if (itmTyp != null) {

            while (rSetIter.hasNext()) {
                r = rSetIter.next();
                //System.out.println("itmTyp +++++++++++++++++++++ "+itmTyp);
                //System.out.println(" att type  ++++++++++++++++++++ " + r.getAttribute("ItmType"));

                if (itmTyp.compareTo((Integer) r.getAttribute("ItmType")) == 0 &&
                    oprSrl.equals(r.getAttribute("OpSrNo"))) {
                    countVal = countVal + 1;
                }
            }
            rSetIter.closeRowSetIterator();

            //exclude count from current row
            Row currentRow = dcIter.getCurrentRow();

            if (itmTyp.compareTo((Integer) currentRow.getAttribute("ItmType")) == 0 &&
                oprSrl.equals(currentRow.getAttribute("OpSrNo"))) {
                countVal = countVal - 1;
            }
        }

        return countVal == 1 ? true : false;
    }

    public void setOpItemDtlFormBinding(RichPanelFormLayout opItemDtlFormBinding) {
        this.opItemDtlFormBinding = opItemDtlFormBinding;
    }

    public RichPanelFormLayout getOpItemDtlFormBinding() {
        return opItemDtlFormBinding;
    }


    public void itmValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // System.out.println("bean itmValidator");
        if (object != null) {

            if (duplicateItmAndItmType("MnfBomOpItmVOIterator", object,
                                       getAttrsVal("MnfBomOpItmVOIterator", "ItmType"))) {

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Selected Item and Its type already present in this operation ",
                                                              null));
            }
            if (duplicateItmInOperation("MnfBomOpItmVOIterator", object,
                                        getAttrsVal("MnfBomOpItmVOIterator", "OpSrNo"))) {

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Selected Item is already present in this operation ",
                                                              null));
            }
            Integer tmpType = (Integer) itmTypeBindVar.getValue();
            if (tmpType.compareTo(67) == 0) {
                if (multpleOutitemInOpr("MnfBomOpItmVOIterator", tmpType,
                                        getAttrsVal("MnfBomOpItmVOIterator", "OpSrNo"))) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Single Operation Output Item is allowed per operation. ",
                                                                  null));
                }
            }


        }
    }


    public void itmRefreshAction(ActionEvent actionEvent) {

        if (getAttrsVal("MnfBomOpItmVOIterator", "ItmDesc") != null) {

            if (getAttrsVal("MnfBomOpItmVOIterator", "itm_Serilized") != null &&
                getAttrsVal("MnfBomOpItmVOIterator", "itm_Serilized").toString().equalsIgnoreCase("Y")) {

                Number sr_qty =
                    getAttrsVal("MnfBomOpItmVOIterator", "itm_Serilized") != null ?
                    (Number) getAttrsVal("MnfBomOpItmVOIterator", "ItmQty") : new Number(1);
                setAttrsVal("MnfBomOpItmVOIterator", "ItmQty", sr_qty.round(0));
            }

            ADFUtils.findOperation("refreshAllItem").execute();
            outItemAmount();
            refreshOutItmPrice();


            setAttrsVal("MnfBomVOIterator", "TotOutptItmAmt", getTotAmt());

            ADFUtils.findIterator("MnfBomInputItmVOIterator").executeQuery();
            //            ADFUtils.findIterator("MnfBomOpItmAllVOIterator").executeQuery();
            ADFUtils.findIterator("MnfBomOutputItmVOIterator").executeQuery();
            ADFUtils.findIterator("MnfBomCoProductItmVOIterator").executeQuery();


            AdfFacesContext.getCurrentInstance().addPartialTarget(outputItmTableBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(inputItmTableBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dtlTabBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(opDtlFormBinding);
        }
    }

    /**
     * Method to validate Number field for negative value and given precision
     * returns 2 if number is negative
     * returns 1 if number is out of precision
     * returns 0 if no error
     * **/

    protected Integer validateNumber(Integer precision, Integer scale, Object num) {

        if (num != null && precision != null && scale != null) {

            Number val = (Number) num;

            if (!isPrecisionValid(precision, scale, val)) {
                return 1;
            }
            if (val.compareTo(0) == -1) {
                return 2;
            }
            if (val.compareTo(0) == 0) {
                return 3;
            }
        }
        return 0;
    }

    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {

        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);

        return vc.validateValue(total);
    }

    public void bomOutputQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        Integer errCode = validateNumber(26, 6, object);

        if (errCode.compareTo(1) == 0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          "Invalid Number. Precision Should be (26,6) ", null));
        }
        if (errCode.compareTo(2) == 0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Value cannot be Negative",
                                                          null));
        }
        if (errCode.compareTo(3) == 0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          "Value should be greater than 0 (Zero)", null));
        }
    }

    public void itmPriceValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Integer errCode = validateNumber(26, 6, object);

        if (errCode.compareTo(1) == 0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          "Invalid Number. Precision Should be (26,6) ", null));
        }
        if (errCode.compareTo(2) == 0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Value cannot be Negative",
                                                          null));
        }
    }

    public void itmQuantityValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        Integer errQty = 0;
        Integer errCode = validateNumber(26, 6, object);
        Integer errVal = validateNumber(26, 0, object);
        if (getAttrsVal("MnfBomOpItmVOIterator", "ItmType") == 62) {
            ob = ADFUtils.findOperation("chckQuantityValidator");
            ob.getParamsMap().put("opr_sr", getAttrsVal("MnfBomOpItmVOIterator", "OpSrNo"));
            ob.getParamsMap().put("itm_id", getAttrsVal("MnfBomOpItmVOIterator", "ItmId"));
            ob.getParamsMap().put("qty", (Number) object);

            ob.execute();
            errQty = (Integer) ob.getResult();
        }

        String isSeril =
            getAttrsVal("MnfBomOpItmVOIterator", "itm_Serilized") != null ?
            getAttrsVal("MnfBomOpItmVOIterator", "itm_Serilized").toString() : "N";
        if (isSeril.equalsIgnoreCase("Y")) {
            if (errVal.compareTo(1) == 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Invalid Number. Decimal Digits not allowed for serialized Item (26,0) ",
                                                              null));
            }
        }

        if (errCode.compareTo(1) == 0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          "Invalid Number. Precision Should be (26,6) ", null));
        }
        if (errCode.compareTo(2) == 0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Value cannot be Negative",
                                                          null));
        }
        if (errCode.compareTo(3) == 0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          "Value should be greater than 0 (Zero)", null));
        }
        if (errQty.compareTo(1) == 0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          "Value must match the quantity defined in the output item",
                                                          null));
        }

    }

    public void itmRtnQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Integer errCode = validateNumber(26, 6, object);
        Number retn_Qty = (Number) object;

        if (returnQtyFlagBindVar.isSelected()) {

            if (errCode.compareTo(1) == 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Invalid Number. Precision Should be (26,6) ", null));
            }
            if (errCode.compareTo(2) == 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Value cannot be Negative",
                                                              null));
            }
            if (getAttrsVal("MnfBomOpItmVOIterator", "ItmQty") != null) {
                Number itm_Qty = (Number) getAttrsVal("MnfBomOpItmVOIterator", "ItmQty");

                if (itm_Qty.compareTo(retn_Qty) == -1) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Return Quantity cannot be more than Item Quantity",
                                                                  null));
                }
            }
        }

    }

    public void scrapPrcntValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Integer errCode = validateNumber(26, 6, object);

        if (errCode.compareTo(1) == 0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          "Invalid Number. Precision Should be (26,6) ", null));
        }
        if (errCode.compareTo(2) == 0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Value cannot be Negative",
                                                          null));
        }

        if (object != null && ((Number) object).compareTo(100) == 1) {

            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          "Scrap% cannot be greater than 100", null));

        }
    }

    public void itmAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Integer errCode = validateNumber(26, 6, object);

        if (errCode.compareTo(1) == 0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          "Invalid Number. Precision Should be (26,6) ", null));
        }
        if (errCode.compareTo(2) == 0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Value cannot be Negative",
                                                          null));
        }
    }

    /**
     * Method to set value for an attribute of current row for an Iterator
     * @param iter for iterator name
     * @param for attribute name
     * @param for attribute value
     * **/
    public void setAttrsVal(String iter, String attrs, Object val) {

        if (iter != null && attrs != null) {
            DCIteratorBinding dcIter = ADFUtils.findIterator(iter);

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
            DCIteratorBinding dcIter = ADFUtils.findIterator(iter);
            // System.out.println("dcIter " + dcIter.getEstimatedRowCount());
            if (dcIter.getCurrentRow() != null)
                return dcIter.getCurrentRow().getAttribute(attrs);

        }
        return null;
    }

    public void bomActiveVCE(ValueChangeEvent valueChangeEvent) {


        if (valueChangeEvent.getNewValue() != null) {

            setModeActive(new StringBuffer("N"));
            //System.out.println(modeActive+"  ==============================  ");

            if ((valueChangeEvent.getNewValue().equals("N") || valueChangeEvent.getNewValue().equals(false))) {
                setAttrsVal("MnfBomVOIterator", "BomInacttvDt", new Timestamp(System.currentTimeMillis()));

            } else {
                setAttrsVal("MnfBomVOIterator", "BomInacttvDt", null);
                setAttrsVal("MnfBomVOIterator", "BomInactvReason", null);
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(valueChangeEvent.getComponent().getParent());
            AdfFacesContext.getCurrentInstance().addPartialTarget(hdrFormBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(saveBomLinkBinding);
        }
    }

    /**Method to update Total amount for an Item**/
    public void updateItmAmt(Number qty, Number price) {

        //Integer roudValue = Integer.parseInt(JSFUtils.resolveExpression("#{pageFlowScope.GLBL_AMT_DIGIT}").toString());
        if (qty != null && price != null) {
            setAttrsVal("MnfBomOpItmVOIterator", "ItmTotAmt", qty.multiply(price).round(getAmtRoundDigit()));
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmTotAmtBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(outputItmTableBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(inputItmTableBinding);
    }

    public void updateItmPrice(Number itm_qty, Number itm_amt) {
        if (itm_qty != null && itm_qty.compareTo(0) == 1 && itm_amt != null) {
            Number itm_price = new Number(itm_amt.divide(itm_qty));

            Number number = new Number(ADFBeanUtils.roundOff(itm_price));
            setAttrsVal("MnfBomOpItmVOIterator", "ItmPrice", number);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmPriceBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(outputItmTableBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(inputItmTableBinding);
        }
    }

    public void itmPriceVCE(ValueChangeEvent valueChangeEvent) {

        if (valueChangeEvent.getNewValue() != null && getAttrsVal("MnfBomOpItmVOIterator", "ItmQty") != null) {
            updateItmAmt((Number) getAttrsVal("MnfBomOpItmVOIterator", "ItmQty"),
                         (Number) valueChangeEvent.getNewValue());
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmTotAmtBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(outputItmTableBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(inputItmTableBinding);
    }

    public void serializedItemQty(Number itm_Qty, Object is_sril) {

        if (itm_Qty.compareTo(0) == -1 && is_sril != null && is_sril.toString().equalsIgnoreCase("Y")) {
            setAttrsVal("MnfBomOpItmVOIterator", "ItmQty", itm_Qty.round(0));
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmQtyBindVar);
        }

    }

    public void itmQtyVCE(ValueChangeEvent valueChangeEvent) {
        Integer tmpType = (Integer) itmTypeBindVar.getValue();
        String mp = "Y";

        if (valueChangeEvent.getNewValue() != null && getAttrsVal("MnfBomOpItmVOIterator", "ItmPrice") != null &&
            tmpType != 67 && mp.contentEquals(getModePrice())) {
            updateItmAmt((Number) valueChangeEvent.getNewValue(),
                         (Number) getAttrsVal("MnfBomOpItmVOIterator", "ItmPrice"));
        } else {
            outItemAmount();
            updateItmPrice((Number) valueChangeEvent.getNewValue(),
                           (Number) getAttrsVal("MnfBomOpItmVOIterator", "ItmTotAmt"));
        }

        if (tmpType == 63) {
            //System.out.println("putting qty for tools & Equ");
            Number qty = (Number) valueChangeEvent.getNewValue();
            // System.out.println("  -----------------  " + qty);
            setAttrsVal("MnfBomOpItmVOIterator", "ItmRtrnQty", qty);
            AdfFacesContext.getCurrentInstance().addPartialTarget(retrnQtyBindVar);

        }
        serializedItemQty((Number) valueChangeEvent.getNewValue(),
                          getAttrsVal("MnfBomOpItmVOIterator", "itm_Serilized"));

    }

    /**
     * Method to refresh output Item Price
     */
    public void refreshOutItmPrice() {
        Number opSrNo = (Number) getAttrsVal("MnfBomOpItmVOIterator", "OpSrNo");
        //System.out.println( "============== opr sr " + opSrNo);
        ob = ADFUtils.findOperation("refreshItmPrice");
        ob.getParamsMap().put("currOprSr", opSrNo);
        ob.execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(itmPriceBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(outputItmTableBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(inputItmTableBinding);
    }

    /**
     * Method to add output item amount
     */
    public void outItemAmount() {
        Number opSrNo = (Number) getAttrsVal("MnfBomOpItmVOIterator", "OpSrNo");
        ob = ADFUtils.findOperation("calcTotalOuputAmount");
        ob.getParamsMap().put("currOprSr", opSrNo);
        ob.execute();

        //String outItmId = null;

        DCIteratorBinding dcIter = ADFUtils.findIterator("MnfBomOpItmVOIterator");
        ViewObject vo = dcIter.getViewObject();
        RowSetIterator rSetIter = vo.createRowSetIterator(null);

        Row r = null;
        Number newAmount = (Number) ob.getResult();

        while (rSetIter.hasNext()) {
            r = rSetIter.next();
            // System.out.println("attrsNm "+r.getAttribute("ItmDesc"));
            //System.out.println(" att type   " + r.getAttribute("ItmType"));

            if (r.getAttribute("ItmType") != null && (Integer) r.getAttribute("ItmType") == 67 &&
                r.getAttribute("ItmId") != null) {

                // r.setAttribute("" + "ItmTotAmt", newAmount);
                r.setAttribute("ItmTotAmt", newAmount);
            }
        }
        rSetIter.closeRowSetIterator();

        //AdfFacesContext.getCurrentInstance().addPartialTarget(itmPriceBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmTotAmtBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(outputItmTableBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(inputItmTableBinding);
    }

    public void seriItmQtyRefresh() {

        DCIteratorBinding dcIter = ADFUtils.findIterator("MnfBomOpItmAllVOIterator");
        ViewObject vo = dcIter.getViewObject();
        RowSetIterator rSetIter = vo.createRowSetIterator(null);

        Row r = null;

        while (rSetIter.hasNext()) {
            r = rSetIter.next();
            // System.out.println("attrsNm "+r.getAttribute("ItmDesc"));
            //System.out.println(" att type   " + r.getAttribute("ItmType"));

            if (r.getAttribute("ItmType") != null && (Integer) r.getAttribute("ItmType") == 67 &&
                r.getAttribute("ItmId") != null) {

            }
        }
        rSetIter.closeRowSetIterator();


        AdfFacesContext.getCurrentInstance().addPartialTarget(itmQtyBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(outputItmTableBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(inputItmTableBinding);
    }

    public void setRefreshItmDtlBinding(RichLink refreshItmDtlBinding) {
        this.refreshItmDtlBinding = refreshItmDtlBinding;
    }

    public RichLink getRefreshItmDtlBinding() {
        return refreshItmDtlBinding;
    }

    protected void resetAttribute() {
        setAttrsVal("MnfBomOpItmVOIterator", "PrncplInpt", "N");
        setAttrsVal("MnfBomOpItmVOIterator", "ItmId", null);
        setAttrsVal("MnfBomOpItmVOIterator", "ItmPrice", 0);
        setAttrsVal("MnfBomOpItmVOIterator", "ItmQty", 0);
        setAttrsVal("MnfBomOpItmVOIterator", "ItmTotAmt", 0);
        setAttrsVal("MnfBomOpItmVOIterator", "ItmDesc", null);
        setAttrsVal("MnfBomOpItmVOIterator", "ItmScrapPer", 0);
        setAttrsVal("MnfBomOpItmVOIterator", "ItmRtrnQty", 0);

    }

    public void itmTypeVCE(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        Integer itmTyp = 0;
        // if (getAttrsVal("MnfBomOpItmVOIterator", "ItmDesc") != null) {
        //queueRefreshAction();
        // }
        resetAttribute();

        if (itmTypeBindVar.getValue() != null) {
            itmTyp = (Integer) itmTypeBindVar.getValue();
        }
        // System.out.println(" item type is  ++++ " + itmTyp);
        if (itmTyp.compareTo(63) == 0) {
            oracle.jbo.domain.Number tmpQty = new oracle.jbo.domain.Number(0);
            if (itmQtyBindVar.getValue() != null) {
                tmpQty = (Number) itmQtyBindVar.getValue();
            }
            // System.out.println(" ====================  return true
            // ");
            // returnQtyFlagBindVar.setValue(true);
            setAttrsVal("MnfBomOpItmVOIterator", "ItmRtrnFlg", "Y");
            setAttrsVal("MnfBomOpItmVOIterator", "ItmRtrnType", 89);
            setAttrsVal("MnfBomOpItmVOIterator", "ItmRtrnQty", tmpQty);
        }
        if (itmTyp.compareTo(62) != 0) {
            setAttrsVal("MnfBomOpItmVOIterator", "ItmBasis", 55);
            // setAttrsVal("MnfBomOpItmVOIterator", "ConsmptnFlg", "N");
        } /*  else if (itmTyp.compareTo(62) == 0) {
            setAttrsVal("MnfBomOpItmVOIterator", "ConsmptnFlg", "Y");
        } */
        // }
        if (itmTyp.compareTo(67) == 0) {

            setAttrsVal("MnfBomOpItmVOIterator", "IssuType", 25);
            setAttrsVal("MnfBomOpItmVOIterator", "ItmRtrnFlg", "N");
            setAttrsVal("MnfBomOpItmVOIterator", "ItmRtrnQty", 0.00);
            // setAttrsVal("MnfBomOpItmVOIterator", "ConsmptnFlg", "N");
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmBasisBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(returnQtyFlagBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmReturnTypBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(retrnQtyBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(opItemDtlFormBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(refreshItmDtlBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(bomOpTableBinding);


    }

    /**
     * Method to save uploaded files in file system and DB tables
     * **/
    public String uploadFiles() {

        String path = "";
        String extn = "";
        List<UploadedFile> files = this.getUploadedFile();
        if (files != null) {

            for (int i = 0; i < files.size(); i++) {

                try {

                    //get file extension
                    extn = files.get(i).getFilename().substring(files.get(i).getFilename().lastIndexOf("."));

                    //Add files to the Table
                    ob = ADFUtils.findOperation("createAttchmntRow");
                    ob.getParamsMap().put("fileNm", files.get(i).getFilename());
                    ob.getParamsMap().put("contentTyp", files.get(i).getContentType());
                    ob.getParamsMap().put("extn", extn);
                    ob.execute();

                    if (ob.getResult() != null) {
                        path = ob.getResult().toString();
                    }


                    // System.out.println("extn " + extn);
                    //write files in the file system.

                    InputStream in = files.get(i).getInputStream();
                    //System.out.println(files.get(i).getInputStream());

                    //System.out.println("write file at " + path + extn);
                    FileOutputStream out = new FileOutputStream(path + extn);
                    byte[] buffer = new byte[8192];
                    int bytesRead = 0;

                    while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                    in.close();
                    out.close();

                    //call commit after checking all validations

                    ADFUtils.findOperation("Commit").execute();
                } catch (IOException ioe) {
                    // TODO: Add catch code
                    ioe.printStackTrace();
                }
            }
        }
        ADFUtils.findOperation("Commit").execute();
        //remove the files to prepare for new uploads
        setUploadedFile(null);

        //freeze the header
        setHdrFreeze(new StringBuffer("N"));

        return "null";
    }

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

    public void attachmentDeleteACE(ActionEvent actionEvent) {
        Object filePath = actionEvent.getComponent().getAttributes().get("pathWithName");
        String rowKey = actionEvent.getComponent().getAttributes().get("rowKey").toString();
        System.out.println("Key Value : " + rowKey);
        if (filePath !=
            null) {
            //System.out.println("File Path : "+filePath.toString());
            OperationBinding binding =
    BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("deleteAttachFileRow");
            binding.getParamsMap().put("path", filePath.toString());
            binding.execute();
            ADFUtils.findOperation("Commit").execute();
        }
        //        String path = null;
        //
        //        Object pathObj = actionEvent.getComponent().getAttributes().get("pathWithName");
        //        if (pathObj != null)
        //            path = actionEvent.getComponent().getAttributes().get("pathWithName").toString();
        //
        //        // System.out.println("File path in AMimpl" + path);
        //        File f = new File(path);
        //        try {
        //            boolean success = f.delete();
        //            //  System.out.println("File Deleted");
        //        } catch (Exception x) {
        //
        //            System.err.format("%s: no such" + " file or directory%n", path);
        //        }
        //
        //        ADFUtils.findOperation("DeleteAttach").execute();
        //        ADFUtils.findOperation("Commit").execute();
    }

    /**
     *Method to get user Id.
     * **/
    public Integer getUserId() {

        if (JSFUtils.resolveExpression("#{pageFlowScope.GLBL_APP_USR}") != null) {

            return Integer.parseInt(JSFUtils.resolveExpression("#{pageFlowScope.GLBL_APP_USR}").toString());

        } else {
            JSFUtils.addFacesErrorMessage("Error in getting User Id");
        }

        return 0;
    }

    /**
     * Method to get User Name from user Id
     * **/

    public String getUserNm(Integer usr_id) {

        DCIteratorBinding iter = ADFUtils.findIterator("LOVUserVOIterator");
        ViewObject object = iter.getViewObject();
        object.setNamedWhereClauseParam("BindUsrId", usr_id);
        object.setNamedWhereClauseParam("BindSlcId",
                                        Integer.parseInt(JSFUtils.resolveExpression("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString()));
        object.executeQuery();
        RowSetIterator rit = object.createRowSetIterator(null);

        System.out.println("row count " + rit.getRowCount() + "---" + usr_id);

        if (rit.hasNext()) {
            System.out.println(" has next" + rit.first().getAttribute("UsrName").toString());
            return rit.first().getAttribute("UsrName").toString();
        }

        return "0";
    }

    public void workFlowCall() {

        ADFUtils.findOperation("callWfFunctions").execute();

    }

    public void ItmRtrnFlgVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            //System.out.println("VCE " + valueChangeEvent.getNewValue());
            if ((valueChangeEvent.getNewValue().equals("N") || valueChangeEvent.getNewValue().equals(false))) {
                setAttrsVal("MnfBomOpItmVOIterator", "ItmRtrnQty", new Number(0));

            } else if (valueChangeEvent.getNewValue().equals("Y") || valueChangeEvent.getNewValue().equals(true)) {
                setAttrsVal("MnfBomOpItmVOIterator", "ItmRtrnQty", new Number(1));
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(retrnQtyBindVar);
        }
        /* if (returnQtyFlagBindVar.isSelected()) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(retrnQtyBindVar);
            this.retrnQtyBindVar.setRequired(true);
            this.retrnQtyBindVar.setValue(1);
            this.retrnQtyBindVar.setVisible(true);


        } else {
            AdfFacesContext.getCurrentInstance().addPartialTarget(retrnQtyBindVar);
            this.retrnQtyBindVar.setRequired(false);
            this.retrnQtyBindVar.setValue(0);
            this.retrnQtyBindVar.setVisible(false);

        } */
    }

    public void itmDescVCE(ValueChangeEvent valueChangeEvent) {
        //System.out.println("itmDescVCE ");
        setAttrsVal("MnfBomOpItmVOIterator", "ItmQty", 0);
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        setModePrice(new StringBuffer("Y"));

        if (getAttrsVal("MnfBomOpItmVOIterator", "ItmType") != null) {
            queueRefreshAction();
        }

        /* if (ob.getResult() == true) {
            ADFUtils.showFacesMsg("Multiple Operation Output Item !",
                                  "Single operation output item per operation is allowed. ",
                                  FacesMessage.SEVERITY_ERROR, valueChangeEvent.getComponent().getClientId());
        } */

        if (duplicateItmAndItmType("MnfBomOpItmVOIterator", valueChangeEvent.getNewValue(),
                                   getAttrsVal("MnfBomOpItmVOIterator", "ItmType"))) {

            ADFUtils.showFacesMsg("Duplicate Value !", "Selected Item and Its type already present in this operation. ",
                                  FacesMessage.SEVERITY_ERROR, valueChangeEvent.getComponent().getClientId());

        }
        if (duplicateItmInOperation("MnfBomOpItmVOIterator", valueChangeEvent.getNewValue(),
                                    getAttrsVal("MnfBomOpItmVOIterator", "OpSrNo"))) {

            ADFUtils.showFacesMsg("Duplicate Value !", "Selected Item is already present in this operation. ",
                                  FacesMessage.SEVERITY_ERROR, valueChangeEvent.getComponent().getClientId());

        }

        Number currOprSr = (Number) getAttrsVal("MnfBomOpItmVOIterator", "OpSrNo");
        if (currOprSr.compareTo(1) == 1) {
            callInShadow();
        }

        Integer tmpType = (Integer) itmTypeBindVar.getValue();
        if (tmpType == 67) {
            outItemAmount();
        }


        AdfFacesContext.getCurrentInstance().addPartialTarget(itmUom1Binding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(opItemDtlFormBinding); //refreshItmDtlBinding
        AdfFacesContext.getCurrentInstance().addPartialTarget(refreshItmDtlBinding);
    }

    public void queueRefreshAction() {

        ActionEvent ae = new ActionEvent(refreshItmDtlBinding);
        ae.queue();
    }

    public void prvItmAmountACE(ActionEvent actionEvent) {
        callInShadow();
    }

    public void callInShadow() {
        String currItmId = null;
        Number currOprSr = new Number(0);

        currItmId =
            getAttrsVal("MnfBomOpItmVOIterator", "ItmId") != null ?
            getAttrsVal("MnfBomOpItmVOIterator", "ItmId").toString() : "";
        currOprSr = (Number) getAttrsVal("MnfBomOpItmVOIterator", "OpSrNo");

        // System.out.println("current itm ID ============== " + currItmId);
        //  System.out.println("current opr sr no ============== " + currOprSr);

        Number prvAmnt = setAmntInCurrItem(currItmId, currOprSr);
        // System.out.println(" previous out Item amount =========== " + prvAmnt);
        if (prvAmnt.compareTo(0) != 0) {
            setAttrsVal("MnfBomOpItmVOIterator", "ItmTotAmt", prvAmnt);

        }
    }

    public Number setAmntInCurrItem(String curItm, Number curOpr) {

        DCIteratorBinding dcIter = ADFUtils.findIterator("MnfBomOpItmAllVOIterator");
        ViewObject vo = dcIter.getViewObject();
        RowSetIterator rSetIter = vo.createRowSetIterator(null);

        Number prvAmnt = new Number(0);
        Integer itmTy = 0;
        String prvItmId = "";
        Number pOprSr = new Number(0);

        Row r = null;
        while (rSetIter.hasNext()) {
            r = rSetIter.next();

            itmTy = r.getAttribute("ItmType") != null ? (Integer) r.getAttribute("ItmType") : 0;
            prvItmId = r.getAttribute("ItmId") != null ? r.getAttribute("ItmId").toString() : "";
            pOprSr = r.getAttribute("OpSrNo") != null ? (Number) r.getAttribute("OpSrNo") : new Number(0);
            //System.out.println("Conditions Returning : "+itmTy.compareTo(67) +" "+prvItmId.equalsIgnoreCase(curItm)+" "+pOprSr.compareTo(curOpr)+"");
            if (itmTy.compareTo(67) == 0 && prvItmId.equalsIgnoreCase(curItm) && pOprSr.compareTo(curOpr) != 0) {
                prvAmnt = (Number) r.getAttribute("ItmTotAmt");
                // System.out.println(" amount for out item is  =========== " + prvAmnt);
                setModePrice(new StringBuffer("N"));
            }
        }

        rSetIter.closeRowSetIterator();
        return prvAmnt;
    }

    String orgId = null;

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void orgIdValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (duplicateValue("OrgMnfBomVOIterator", "OrgId", object)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          "Duplicate Organization Selected", null));
        }

        setOrgId(object.toString());
    }

    String retVal = "N";

    public void setRetVal(String retVal) {
        this.retVal = retVal;
    }

    public String getRetVal() {
        return retVal;
    }

    public void addQueueACE(ActionEvent actionEvent) {
        System.out.println("Org id is " + getOrgId());
        ob = ADFUtils.findOperation("chkOrgWiseItemRep");
        // ob.getParamsMap().put("orgId",getAttrsVal("OrgMnfBomVOIterator", "OrgId").toString());
        ob.getParamsMap().put("orgId", getOrgId());
        ob.execute();
        if (ob.getResult() == -1) {
            /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          "Operation Item/Items are not exists in an Organization", null));
             */
            setRetVal("Y");
            ADFUtils.showFacesMsg("Organization!", "Operation Item/Items are not replicated in an Organization",
                                  FacesMessage.SEVERITY_ERROR, null);
        }


    }


    public void OrgBomActvVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {

            if ((valueChangeEvent.getNewValue().equals("N") || valueChangeEvent.getNewValue().equals(false))) {
                setAttrsVal("OrgMnfBomVOIterator", "InactvDt", new Timestamp(System.currentTimeMillis()));

            } else {
                setAttrsVal("OrgMnfBomVOIterator", "InactvDt", null);
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(valueChangeEvent.getComponent().getParent());
        }
    }

    public void setItmUom1Binding(RichSelectOneChoice itmUom1Binding) {
        this.itmUom1Binding = itmUom1Binding;
    }

    public RichSelectOneChoice getItmUom1Binding() {
        return itmUom1Binding;
    }

    public Integer validateOutputItm() {

        DCIteratorBinding dcIter = ADFUtils.findIterator("MnfBomOpIterator");
        RowSetIterator rSetIter = dcIter.getRowSetIterator();
        Row r = null;
        Integer outputItmMiss = 0;

        while (rSetIter.hasNext()) {
            r = rSetIter.next();
            //System.out.println("validateOutputItm " + r.getAttribute("TransOpOutItmPresent"));
            if (r.getAttribute("TransOpOutItmPresent").equals(0)) {
                outputItmMiss = outputItmMiss + 1;
            }
        }
        return outputItmMiss;
    }

    public Integer validateInputItm() {
        //System.out.println("Inside input Item Validator  ++++++++++++++ ");
        DCIteratorBinding dcIter = ADFUtils.findIterator("MnfBomOpIterator");
        RowSetIterator rSetIter = dcIter.getRowSetIterator();
        Row r = null;
        Integer inputItmMiss = 0;

        while (rSetIter.hasNext()) {
            r = rSetIter.next();
            // System.out.println("validateInputItm ++++++++++ " + r.getAttribute("TransInputItmPresent"));
            if (r.getAttribute("TransInputItmPresent").equals(0)) {
                inputItmMiss = inputItmMiss + 1;
            }
        }
        return inputItmMiss;
    }


    public void allItmQtyVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null && allItmPriceBinding.getValue() != null) {

            //allItmTotAmtBinding.setValue(((Number)valueChangeEvent.getNewValue()).multiply((Number)allItmPriceBinding.getValue()).round(getAmtRoundDigit()));
            Integer itmTp = (Integer) itmTypeBindVar.getValue();
            Number totalAmount = new Number(0);
            if ((itmTp.compareTo(67) == 0)) {

                setAttrsVal("MnfBomOpItmAllVOIterator", "ItmTotAmt", totalAmount);
                //  (((Number) valueChangeEvent.getNewValue()).multiply((Number) allItmPriceBinding.getValue())).round(getAmtRoundDigit()));
            } else {
                setAttrsVal("MnfBomOpItmAllVOIterator", "ItmTotAmt",
                            (((Number) valueChangeEvent.getNewValue()).multiply((Number) allItmPriceBinding.getValue())).round(getAmtRoundDigit()));
            }

        }


    }

    public void setAllItmTotAmtBinding(RichOutputText allItmTotAmtBinding) {
        this.allItmTotAmtBinding = allItmTotAmtBinding;
    }

    public RichOutputText getAllItmTotAmtBinding() {
        return allItmTotAmtBinding;
    }

    public void setAllItmPriceBinding(RichOutputText allItmPriceBinding) {
        this.allItmPriceBinding = allItmPriceBinding;
    }

    public RichOutputText getAllItmPriceBinding() {
        return allItmPriceBinding;
    }

    public Integer getAmtRoundDigit() {

        if (JSFUtils.resolveExpression("#{pageFlowScope.GLBL_AMT_DIGIT}") != null)
            return Integer.parseInt(JSFUtils.resolveExpression("#{pageFlowScope.GLBL_AMT_DIGIT}").toString());

        return 2;
    }

    public void allOpItmdeleteACE(ActionEvent actionEvent) {

        ADFUtils.findOperation("DeleteAllOpItm").execute();

        queueRefreshAction();

        AdfFacesContext.getCurrentInstance().addPartialTarget(opItemDtlFormBinding);
    }

    public void operationSelectionListener(SelectionEvent selectionEvent) {

        ADFUtils.invokeEL("#{bindings.MnfBomOp.collectionModel.makeCurrent}", new Class[] { SelectionEvent.class }, new Object[] {
                          selectionEvent });

        ADFUtils.findIterator("MnfBomOpItmVOIterator").executeQuery();

        AdfFacesContext.getCurrentInstance().addPartialTarget(opDtlFormBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(opItemDtlFormBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(bomOpTableBinding);

        queueRefreshAction();
    }

    public void TransRefBomVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {

            //queue copy event after value change event.
            ActionEvent ace = new ActionEvent(copyReferenceLnk);
            ace.queue();
        }
    }

    public void copyReferenceBom(ActionEvent actionEvent) {

        //enable reserve mode for AM before calling a function with DML operation
        ADFUtils.findOperation("enableReservedMode").execute();

        //call the AM method to copy selected BOM
        ADFUtils.findOperation("copyReferencedBom").execute();

        if (("54").equals(getAttrsVal("MnfBomVOIterator", "BomUsage").toString())) {
            setHdrFreeze(new StringBuffer("Y"));
        } else
            setHdrFreeze(new StringBuffer("CC"));
        //refresh all components
        AdfFacesContext.getCurrentInstance().addPartialTarget(actionEvent.getComponent().getParent());
        AdfFacesContext.getCurrentInstance().addPartialTarget(hdrFormBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(routeHdrBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(dtlTabBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(opItemDtlFormBinding);

    }

    public void setCopyReferenceLnk(RichLink copyReferenceLnk) {
        this.copyReferenceLnk = copyReferenceLnk;
    }

    public RichLink getCopyReferenceLnk() {
        return copyReferenceLnk;
    }

    public void allItemDisclosureListener(DisclosureEvent disclosureEvent) {

        if (disclosureEvent.isExpanded()) {

            ADFUtils.findIterator("MnfBomOpItmVOIterator").executeQuery();

            ADFUtils.findIterator("MnfBomOpItmAllVOIterator").executeQuery();

            checkNullData("MnfBomOpItmAllVOIterator", "ItmId");

            queueRefreshAction();
        }
    }

    public void setRouteHdrBinding(RichPanelGroupLayout routeHdrBinding) {
        this.routeHdrBinding = routeHdrBinding;
    }

    public RichPanelGroupLayout getRouteHdrBinding() {
        return routeHdrBinding;
    }

    public void setDtlTabBinding(RichPanelTabbed dtlTabBinding) {
        this.dtlTabBinding = dtlTabBinding;
    }

    public RichPanelTabbed getDtlTabBinding() {
        return dtlTabBinding;
    }

    public void itmTypeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Integer tmpType = Integer.parseInt(object.toString());
        if (object != null && tmpType.compareTo(67) == 0) {
            if (multpleOutitemInOpr("MnfBomOpItmVOIterator", tmpType, getAttrsVal("MnfBomOpItmVOIterator", "OpSrNo"))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Single Operation Output Item is allowed per operation. ",
                                                              null));
            }
        }
        if (object != null) {
            if (duplicateItmAndItmType("MnfBomOpItmVOIterator", getAttrsVal("MnfBomOpItmVOIterator", "ItmDesc"),
                                       object)) {

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Selected Item and Its type already present in this operation. ",
                                                              null));
            }
        }

    }

    public void setVldEndDtBinding(RichInputDate vldEndDtBinding) {
        this.vldEndDtBinding = vldEndDtBinding;
    }

    public RichInputDate getVldEndDtBinding() {
        return vldEndDtBinding;
    }

    public void setVldFrmDtBinding(RichInputDate vldFrmDtBinding) {
        this.vldFrmDtBinding = vldFrmDtBinding;
    }

    public RichInputDate getVldFrmDtBinding() {
        return vldFrmDtBinding;
    }

    public void reviseBomACE(ActionEvent actionEvent) {
        //enable reserve mode for AM before calling a function with DML operation
        ADFUtils.findOperation("enableReservedMode").execute();

        //call the AM method to copy selected BOM
        ADFUtils.findOperation("reviseBom").execute();

        setHdrFreeze(new StringBuffer("Y"));
        setMode(new StringBuffer("E"));

        //refresh all components
        AdfFacesContext.getCurrentInstance().addPartialTarget(paramTableBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(actionEvent.getComponent().getParent());
        AdfFacesContext.getCurrentInstance().addPartialTarget(routeHdrBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(dtlTabBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(opItemDtlFormBinding);

        /* ob = ADFBeanUtils.findOperation("chkDefaultWsVal");
        String wcid = getAttrsVal("MnfBomVOIterator", "OutptItmId").toString();
        ob.getParamsMap().put("WcId", wcid);
        ob.execute();
         Object result = ob.getResult();
         System.out.println("returned from impl " + result);
        if (result != null && result.toString().equalsIgnoreCase("true")) {
            defaultBomBindVar.setDisabled(true);
        } */


    }

    public void bomUsageVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(hdrFormBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(refrencedBomBinding);
    }

    public void setHdrFormBinding(RichPanelFormLayout hdrFormBinding) {
        this.hdrFormBinding = hdrFormBinding;
    }

    public RichPanelFormLayout getHdrFormBinding() {
        return hdrFormBinding;
    }

    public void replicateOrgACE(ActionEvent actionEvent) {


        ADFUtils.findOperation("enableReservedMode").execute();

        ADFUtils.findOperation("replicateAllOrg").execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(actionEvent.getComponent().getParent());

    }

    /**
     * Method to make current row in all item tab with selected item type or item or both
     * **/
    private boolean matchFound(Row r, RowKeySet oldSelection) {
        setKey(null);
        ArrayList lst = new ArrayList(1);
        RowKeySetImpl newSelection = new RowKeySetImpl();
        Key key = null;
        String rowValue = (String) r.getAttribute("ItmId");
        if (rowValue.toString().equals(getAttrsVal("DualAllItemSearchIterator", "ItemId").toString())) {
            //  System.out.println("now setting key to " + key);
            key = r.getKey();
            lst.add(key);
            allItemTableBinding.setActiveRowKey(lst);
            newSelection.add(lst);
            makeCurrent(allItemTableBinding, newSelection, oldSelection);
            return true;
        }
        return false;
    }

    public void gotoPressed(ActionEvent actionEvent) {

        if (getAttrsVal("DualAllItemSearchIterator", "ItemId") != null) {
            DCIteratorBinding it = ADFUtils.findIterator("MnfBomOpItmAllVOIterator");
            RowSetIterator rsi = it.getRowSetIterator();
            RowKeySet oldSelection = allItemTableBinding.getSelectedRowKeys();

            if (rsi.first() != null) {
                Row r = rsi.first();
                while (rsi.hasNext() && getKey() == null && (!matchFound(r, oldSelection))) {
                    r = rsi.next();
                }
            }
        }
    }

    private void makeCurrent(RichTable itmTable, RowKeySet newCurrentRow, RowKeySet oldCurrentRow) {
        //To make a row current, we need to create a SelectionEvent which
        //expects the following arguments: component, unselected_keys,
        //selected_keys.
        SelectionEvent selectionEvent = new SelectionEvent(oldCurrentRow, newCurrentRow, itmTable);
        selectionEvent.queue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmTable);
    }


    public void setAllItemTableBinding(RichTable allItemTableBinding) {
        this.allItemTableBinding = allItemTableBinding;
    }

    public RichTable getAllItemTableBinding() {
        return allItemTableBinding;
    }

    public void deleteOpItmACE(ActionEvent actionEvent) {

        ADFUtils.findOperation("DeleteOpItm").execute();

        //outItemAmount();

        ADFUtils.findIterator("MnfBomInputItmVOIterator").executeQuery();
        ADFUtils.findIterator("MnfBomOpItmAllVOIterator").executeQuery();
        ADFUtils.findIterator("MnfBomOutputItmVOIterator").executeQuery();
        ADFUtils.findIterator("MnfBomCoProductItmVOIterator").executeQuery();

        AdfFacesContext.getCurrentInstance().addPartialTarget(outputItmTableBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(inputItmTableBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(dtlTabBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(opDtlFormBinding);
    }

    public void setTotAmt(Number totAmt) {
        this.totAmt = totAmt;
    }

    public Number getTotAmt() {

        /*   ob = ADFUtils.findOperation("getTotAmt");
        ob.execute();
        //System.out.println("ob.getResult() " + ob.getResult());
        if (ob.getResult() != null)
            return (Number) ob.getResult();
         */

        ob = ADFUtils.findOperation("getlastOprTotalAmount");
        ob.execute();
        Number totAmnt = ob.getResult() != null ? (Number) ob.getResult() : new Number(0);
        if (totAmnt.compareTo(0) == 1) {
            return totAmnt;
        }

        return new Number(0);
    }

    public void setOpDtlFormBinding(RichPanelFormLayout opDtlFormBinding) {
        this.opDtlFormBinding = opDtlFormBinding;
    }

    public RichPanelFormLayout getOpDtlFormBinding() {
        return opDtlFormBinding;
    }

    public void setBomOpTableBinding(RichTable bomOpTableBinding) {
        this.bomOpTableBinding = bomOpTableBinding;
    }

    public RichTable getBomOpTableBinding() {
        return bomOpTableBinding;
    }

    public void setOutputItmTableBinding(RichTable outputItmTableBinding) {
        this.outputItmTableBinding = outputItmTableBinding;
    }

    public RichTable getOutputItmTableBinding() {
        return outputItmTableBinding;
    }

    public void setInputItmTableBinding(RichTable inputItmTableBinding) {
        this.inputItmTableBinding = inputItmTableBinding;
    }

    public RichTable getInputItmTableBinding() {
        return inputItmTableBinding;
    }

    public void removeRow(String iterName, Key key) {

        if (iterName != null && key != null) {
            ViewObject vo = ADFUtils.findIterator(iterName).getViewObject();
            Row row = vo.getRow(key);

            if (row != null)
                row.remove();
        }
    }

    public void orgDeleteACE(ActionEvent actionEvent) {

        //get the key value from f:attribute attached to the calling command link
        Key key = (oracle.jbo.Key) actionEvent.getComponent().getAttributes().get("orgKey");
        // System.out.println(" key------" + key);

        removeRow("OrgMnfBomVOIterator", key);

        AdfFacesContext.getCurrentInstance().addPartialTarget(dtlTabBinding);
        setRetVal("N");
    }

    public void setReturnQtyFlagBindVar(RichSelectBooleanCheckbox returnQtyFlagBindVar) {
        this.returnQtyFlagBindVar = returnQtyFlagBindVar;
    }

    public RichSelectBooleanCheckbox getReturnQtyFlagBindVar() {
        return returnQtyFlagBindVar;
    }

    public void setRetrnQtyBindVar(RichInputText retrnQtyBindVar) {
        this.retrnQtyBindVar = retrnQtyBindVar;
    }

    public RichInputText getRetrnQtyBindVar() {
        return retrnQtyBindVar;
    }

    public void setItmReturnTypBindVar(RichSelectOneChoice itmReturnTypBindVar) {
        this.itmReturnTypBindVar = itmReturnTypBindVar;
    }

    public RichSelectOneChoice getItmReturnTypBindVar() {
        return itmReturnTypBindVar;
    }

    public void setItmTypeBindVar(RichSelectOneChoice itmTypeBindVar) {
        this.itmTypeBindVar = itmTypeBindVar;
    }

    public RichSelectOneChoice getItmTypeBindVar() {
        return itmTypeBindVar;
    }

    public void setItmQtyBindVar(RichInputText itmQtyBindVar) {
        this.itmQtyBindVar = itmQtyBindVar;
    }

    public RichInputText getItmQtyBindVar() {
        return itmQtyBindVar;
    }

    public void setModeActive(StringBuffer modeActive) {
        this.modeActive = modeActive;
    }

    public StringBuffer getModeActive() {
        return modeActive;
    }

    public void itemBasisVCE(ValueChangeEvent valueChangeEvent) {
        Integer itmBas = (Integer) itmBasisBindVar.getValue();
        if (itmBas.compareTo(58) == 0) {
            //System.out.println("Pointer was here ===================== ");
            //setAttrsVal("MnfBomOpItmVOIterator", "ItmType", 62);
            setAttrsVal("MnfBomOpItmVOIterator", "ItmId", "0");
            setAttrsVal("MnfBomOpItmVOIterator", "ItmDesc", "0");
            setAttrsVal("MnfBomOpItmVOIterator", "ItmQty", 0);
            setAttrsVal("MnfBomOpItmVOIterator", "ItmUom", "0");
            setAttrsVal("MnfBomOpItmVOIterator", "ItmTotAmt", 0);
            setAttrsVal("MnfBomOpItmVOIterator", "ItmPrice", 0);
            //setAttrsVal("MnfBomOpItmVOIterator", "ItmScrapPer", 0);
            setAttrsVal("MnfBomOpItmVOIterator", "InptBomId", 0);
            //setAttrsVal("MnfBomOpItmVOIterator", "ItmRtrnQty", 0);
            // setAttrsVal("MnfBomOpItmVOIterator", "ItmRtrnFlg", "N");
            //  setAttrsVal("MnfBomOpItmVOIterator", "ItmRtrnType", 89);


            // System.out.println("Pointer was here +++++++++++++++++++++++++++++++ ");
        }
        // queueRefreshAction();
        AdfFacesContext.getCurrentInstance().addPartialTarget(opItemDtlFormBinding); //refreshItmDtlBinding
        AdfFacesContext.getCurrentInstance().addPartialTarget(refreshItmDtlBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(outputItmTableBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(inputItmTableBinding);


    }

    public void setItmBasisBindVar(RichSelectOneChoice itmBasisBindVar) {
        this.itmBasisBindVar = itmBasisBindVar;
    }

    public RichSelectOneChoice getItmBasisBindVar() {
        return itmBasisBindVar;
    }

    public void itemRemarksVCE(ValueChangeEvent valueChangeEvent) {
        Integer itmBas = (Integer) itmBasisBindVar.getValue();

        if (itmBas == 58) {
            //queueRefreshAction();
            AdfFacesContext.getCurrentInstance().addPartialTarget(opItemDtlFormBinding); //refreshItmDtlBinding
            AdfFacesContext.getCurrentInstance().addPartialTarget(refreshItmDtlBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(outputItmTableBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(inputItmTableBinding);
        }
    }

    public void paramValueValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number value = (oracle.jbo.domain.Number) object;
            Boolean percValid = isPrecisionValueValid(5, 2, value);
            if (value.compareTo(0) < 0 || value.compareTo(100) > 0) {
                FacesMessage message = new FacesMessage("Value range must be between 0 and 100");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }

            if (percValid == false) {
                FacesMessage message = new FacesMessage("Precision upto 2.2 digit only!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
        }
    }

    public void setParamTableBinding(RichTable paramTableBinding) {
        this.paramTableBinding = paramTableBinding;
    }

    public RichTable getParamTableBinding() {
        return paramTableBinding;
    }

    public void paramNameVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(paramTableBinding);
            //AdfFacesContext.getCurrentInstance().addPartialTarget(paramValueBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(addParamBinding);
        }
    }

    public void paramValueVCE(ValueChangeEvent valueChangeEvent) {
        // if (valueChangeEvent.getNewValue() != null)
        //    AdfFacesContext.getCurrentInstance().addPartialTarget(paramTableBinding);
    }

    /**
     * Precision check method
     */
    public Boolean isPrecisionValueValid(Integer precision, Integer scale, oracle.jbo.domain.Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }

    public void rowDelete(Key key, String iterName) {
        if (iterName != null && key != null) {
            ViewObject vo = ADFUtils.findIterator(iterName).getViewObject();
            Row row = vo.getRow(key);

            if (row != null)
                row.remove();
        }

    }

    public void deleteParamACE(ActionEvent actionEvent) {

        Key key = (oracle.jbo.Key) actionEvent.getComponent().getAttributes().get("deleteParam");
        rowDelete(key, "MnfBomParamIterator");
    }

    public void setAddParamBinding(RichLink addParamBinding) {
        this.addParamBinding = addParamBinding;
    }

    public RichLink getAddParamBinding() {
        return addParamBinding;
    }

    public void setParamValueBinding(RichInputText paramValueBinding) {
        this.paramValueBinding = paramValueBinding;
    }

    public RichInputText getParamValueBinding() {
        return paramValueBinding;
    }

    public void createParamACE(ActionEvent actionEvent) {
        ADFUtils.findOperation("CreateInsertParam").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(paramTableBinding);

    }

    public void defaultBomValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null && object.toString().equalsIgnoreCase("true")) {

            if ((getAttrsVal("MnfBomVOIterator", "BomItmDesc") == null)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Select BOM output Item",
                                                              null));
            }

            if (getAttrsVal("MnfBomVOIterator", "BomItmDesc") != null) {

                String itmId = getAttrsVal("MnfBomVOIterator", "OutptItmId").toString();
                //  ob = ADFUtils.findOperation("chkDefaultBom");
                ob = ADFUtils.findOperation("chkDefaultWsVal");
                ob.getParamsMap().put("wcId", itmId);
                ob.execute();

                /* if (ob.getResult() {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Default BOM already exist for this Item", null));
                } */
            }
        }
    }

    public void setDefaultBomBindVar(RichSelectBooleanCheckbox defaultBomBindVar) {
        this.defaultBomBindVar = defaultBomBindVar;
    }

    public RichSelectBooleanCheckbox getDefaultBomBindVar() {
        return defaultBomBindVar;
    }

    public void bomItmDescVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(defaultBomBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(refrencedBomBinding);
    }

    public void setSaveBomLinkBinding(RichLink saveBomLinkBinding) {
        this.saveBomLinkBinding = saveBomLinkBinding;
    }

    public RichLink getSaveBomLinkBinding() {
        return saveBomLinkBinding;
    }

    public void leadTimeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Integer errCode = validateNumber(5, 0, object);

        if (errCode.compareTo(1) == 0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          "Invalid Number. Precision Should be (5) ", null));
        }
        if (errCode.compareTo(2) == 0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Value cannot be Negative",
                                                          null));
        }
        if (errCode.compareTo(3) == 0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          "Value must be greated than 0 (Zero)", null));
        }

    }

    public void setItmTotAmtBindVar(RichInputText itmTotAmtBindVar) {
        this.itmTotAmtBindVar = itmTotAmtBindVar;
    }

    public RichInputText getItmTotAmtBindVar() {
        return itmTotAmtBindVar;
    }


    public void setIamntBinding(RichLink iamntBinding) {
        this.iamntBinding = iamntBinding;
    }

    public RichLink getIamntBinding() {
        return iamntBinding;
    }

    public void setModePrice(StringBuffer modePrice) {
        this.modePrice = modePrice;
    }

    public StringBuffer getModePrice() {
        return modePrice;
    }

    public void paramSetValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

    }

    public void paramSetVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(paramTableBinding);
        }
    }

    public void addOverheadToOperACE(ActionEvent actionEvent) {

        if (getAttrsVal("DualOverheadVOIterator", "ParamSetId") == null) {
            ADFUtils.showFacesMsg("Parameter Set is Required!", "Please enter Parameter Set Name",
                                  FacesMessage.SEVERITY_ERROR, null);
        } else if (getAttrsVal("DualOverheadVOIterator", "ParamId") == null) {
            ADFUtils.showFacesMsg("Parameter is Required!", "Please enter Parameter Name", FacesMessage.SEVERITY_ERROR,
                                  null);
        } else if (getAttrsVal("DualOverheadVOIterator", "OperationId") == null) {

            ADFUtils.showFacesMsg("Operation is Required", "Please Select Operation Name", FacesMessage.SEVERITY_ERROR,
                                  null);

        } else {
            ADFUtils.findOperation("attachOverhead").execute();
        }
    }

    public void validFromDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            try {
                Timestamp t2 = (Timestamp) object;
                Timestamp t3 = (Timestamp) usrCreatDtBindVar.getValue();
                Date d2 = t2.dateValue();
                Date d3 = t3.dateValue();

                if (vldEndDtBinding.getValue() != null) {
                    Timestamp t1 = (Timestamp) vldEndDtBinding.getValue();
                    Date d1 = t1.dateValue();
                    // System.out.println("Document Date Validator is " + t2.compareTo(t1) + !d1.toString().equals(d2.toString()));
                    if (t2.compareTo(t1) > 0 && !d1.toString().equals(d2.toString())) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      "Valid From Date Should be lesser than Upto Date.",
                                                                      null));
                    }
                }

                if (t2.compareTo(t3) < 0 && !d3.toString().equals(d2.toString())) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Valid From Date should not be lesser than Create Date.",
                                                                  null));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void vldEndDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            try {
                Timestamp t1 = (Timestamp) vldFrmDtBinding.getValue();
                Timestamp t2 = (Timestamp) object;

                Date d1 = t1.dateValue();
                Date d2 = t2.dateValue();

                // System.out.println("Document Date Validator is " + t2.compareTo(t1) + !d1.toString().equals(d2.toString()));
                if (t2.compareTo(t1) < 0 && !d1.toString().equals(d2.toString())) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Valid upto Date Should be greater than From Date.",
                                                                  null));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void setItmPriceBindVar(RichInputText itmPriceBindVar) {
        this.itmPriceBindVar = itmPriceBindVar;
    }

    public RichInputText getItmPriceBindVar() {
        return itmPriceBindVar;
    }

    public void setUsrCreatDtBindVar(RichInputDate usrCreatDtBindVar) {
        this.usrCreatDtBindVar = usrCreatDtBindVar;
    }

    public RichInputDate getUsrCreatDtBindVar() {
        return usrCreatDtBindVar;
    }

    public void removeQcParamACE(ActionEvent actionEvent) {
        // Add event code here...
        Key key = (oracle.jbo.Key) actionEvent.getComponent().getAttributes().get("QcParmKey");
        // System.out.println("Key is : " + key);
        //DelShiftKey
        rowDelete(key, "MnfBomQcParamIterator");

    }

    public void standardValueValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number stdNum = (oracle.jbo.domain.Number) object;
            if (ADFBeanUtils.isNumberNegative(stdNum)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Negative Value not allowed.", null));
            }
            if (!ADFBeanUtils.isPrecisionValid(stdNum)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision.", null));
            }
        }

    }

    public void lowTolerValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number value = (Number) (object);

            if (isValuePercBinding.isSelected()) {
                if (ADFBeanUtils.isPercentValueValid(value) != 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Please provide Percentage Value.", null));
                }
                if (ADFBeanUtils.isNumberNegative(value)) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Negative Value not allowed.", null));
                }
            } else {
                if (!ADFBeanUtils.isPrecisionValid(value)) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision.",
                                                                  null));
                }
                if (ADFBeanUtils.isNumberNegative(value)) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Negative Value not allowed.", null));
                }
            }
        }

    }

    public void uprTolerValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number value = (Number) (object);

            if (isValuePercBinding.isSelected()) {
                if (ADFBeanUtils.isPercentValueValid(value) != 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Please provide Percentage Value.", null));
                }
                if (ADFBeanUtils.isNumberNegative(value)) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Negative Value not allowed.", null));
                }
            } else {
                if (!ADFBeanUtils.isPrecisionValid(value)) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision.",
                                                                  null));
                }
                if (ADFBeanUtils.isNumberNegative(value)) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Negative Value not allowed.", null));
                }
            }
            Number minV = new Number(0);
            if (minTolBindVar.getValue() != null) {
                minV = (Number) minTolBindVar.getValue();
            }

            if (value.compareTo(minV) == -1) {
                FacesMessage message = new FacesMessage("Upper Tolerance must be greater than Lower Tolerance!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
        }


    }

    public void setIsValuePercBinding(RichSelectBooleanCheckbox isValuePercBinding) {
        this.isValuePercBinding = isValuePercBinding;
    }

    public RichSelectBooleanCheckbox getIsValuePercBinding() {
        return isValuePercBinding;
    }

    public void lowerTolVCE(ValueChangeEvent valueChangeEvent) {
        if (paramValueBindVar.getValue() != null && valueChangeEvent.getNewValue() != null) {

            Integer c = (Integer) valueTypeBindVar.getValue();

            if (c != null && c == 274) {

                Number a = (Number) paramValueBindVar.getValue();
                Number b = (Number) minTolBindVar.getValue();
                if (a != null && b != null) {
                    if (isValuePercBinding.isSelected()) {
                        b = a.multiply(b.divide(new Number(100)));
                        // System.out.println(b + "  dfd");
                    }
                    Number f = a.subtract(b);

                    setAttrsVal("MnfBomQcParamIterator", "LowerLimit", f);
                }

                AdfFacesContext.getCurrentInstance().addPartialTarget(minValBindVar);

            }

        }
        if (paramValueBindVar.getValue() != null && valueChangeEvent.getNewValue() == null) {
            Number a = (Number) paramValueBindVar.getValue();
            Number f = a.subtract(0);
            setAttrsVal("MnfBomQcParamIterator", "LowerLimit", f);
            AdfFacesContext.getCurrentInstance().addPartialTarget(minValBindVar);
        }
    }

    public void uprTolVCE(ValueChangeEvent valueChangeEvent) {
        if (paramValueBindVar.getValue() != null && valueChangeEvent.getNewValue() != null) {
            Integer c = (Integer) valueTypeBindVar.getValue();
            if (c != null && c == 274) {
                Number a = (Number) paramValueBindVar.getValue();
                Number b = (Number) maxTolBindVar.getValue();
                if (a != null && b != null) {
                    if (isValuePercBinding.isSelected()) {
                        b = a.multiply(b.divide(new Number(100)));
                        //System.out.println(b + "  dcf");
                    }
                    Number f = a.add(b);
                    setAttrsVal("MnfBomQcParamIterator", "UpperLimit", f);
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(maxTolBindVar);
            }
        }
        if (paramValueBindVar.getValue() != null && valueChangeEvent.getNewValue() == null) {
            Number a = (Number) paramValueBindVar.getValue();
            Number f = a.add(0);
            setAttrsVal("MnfBomQcParamIterator", "UpperLimit", f);
            AdfFacesContext.getCurrentInstance().addPartialTarget(maxTolBindVar);
        }
    }

    public void stdValueVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueTypeBindVar.getValue() != null) {
                Integer x = (valueTypeBindVar.getValue() != null ? (Integer) valueTypeBindVar.getValue() : 0);
                if (x == 274) {

                    Number a = new Number(0);
                    Number e = new Number(0);
                    Number b = new Number(0);
                    Number d = new Number(0);

                    a = (Number) paramValueBindVar.getValue();
                    e = (Number) paramValueBindVar.getValue();
                    if (minTolBindVar.getValue() != null) {
                        b = (Number) minTolBindVar.getValue();
                    }
                    if (maxTolBindVar.getValue() != null) {
                        d = (Number) maxTolBindVar.getValue();
                    }
                    if (a != null && b != null && d != null) {
                        if (isValuePercBinding.isSelected()) {
                            b = a.multiply(b.divide(new Number(100)));
                            d = a.multiply(d.divide(new Number(100)));
                        }
                        Number f = a.subtract(b);
                        Number g = e.add(d);

                        setAttrsVal("MnfBomQcParamIterator", "UpperLimit", g);
                        setAttrsVal("MnfBomQcParamIterator", "LowerLimit", f);
                    }
                    AdfFacesContext.getCurrentInstance().addPartialTarget(minValBindVar);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(maxValBindVar);
                }
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(qcParamTable);
        } else {
            setAttrsVal("MnfBomQcParamIterator", "TlrncLower", 0);
            setAttrsVal("MnfBomQcParamIterator", "TlrncUpper", 0);
            setAttrsVal("MnfBomQcParamIterator", "UpperLimit", 0);
            setAttrsVal("MnfBomQcParamIterator", "LowerLimit", 0);
            AdfFacesContext.getCurrentInstance().addPartialTarget(minTolBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(maxTolBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(minValBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(maxValBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(qcParamTable);
        }
    }

    public void setMinTolBindVar(RichInputText minTolBindVar) {
        this.minTolBindVar = minTolBindVar;
    }

    public RichInputText getMinTolBindVar() {
        return minTolBindVar;
    }

    public void chkPercentAmountVCE(ValueChangeEvent valueChangeEvent) {

        if (paramValueBindVar.getValue() != null) {
            Integer c = (valueTypeBindVar.getValue() != null ? (Integer) valueTypeBindVar.getValue() : null);
            if (c != null && c == 274) {

                Number a = (Number) paramValueBindVar.getValue();
                Number e = (Number) paramValueBindVar.getValue();

                Number b = (Number) minTolBindVar.getValue();
                Number d = (Number) maxTolBindVar.getValue();

                if (a != null && b != null && d != null) {
                    if (isValuePercBinding.isSelected()) {
                        b = a.multiply(b.divide(new Number(100)));
                        d = a.multiply(d.divide(new Number(100)));

                    }
                    Number f = a.subtract(b);
                    Number g = e.add(d);

                    setAttrsVal("MnfBomQcParamIterator", "UpperLimit", g);
                    setAttrsVal("MnfBomQcParamIterator", "LowerLimit", f);
                }
            }
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(minValBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(maxValBindVar);
    }

    public void setMaxTolBindVar(RichInputText maxTolBindVar) {
        this.maxTolBindVar = maxTolBindVar;
    }

    public RichInputText getMaxTolBindVar() {
        return maxTolBindVar;
    }

    public void setMinValBindVar(RichInputText minValBindVar) {
        this.minValBindVar = minValBindVar;
    }

    public RichInputText getMinValBindVar() {
        return minValBindVar;
    }

    public void setMaxValBindVar(RichInputText maxValBindVar) {
        this.maxValBindVar = maxValBindVar;
    }

    public RichInputText getMaxValBindVar() {
        return maxValBindVar;
    }

    public void setQcParamTable(RichTable qcParamTable) {
        this.qcParamTable = qcParamTable;
    }

    public RichTable getQcParamTable() {
        return qcParamTable;
    }

    public void setParamValueBindVar(RichInputText paramValueBindVar) {
        this.paramValueBindVar = paramValueBindVar;
    }

    public RichInputText getParamValueBindVar() {
        return paramValueBindVar;
    }

    public void setValueTypeBindVar(RichSelectOneChoice valueTypeBindVar) {
        this.valueTypeBindVar = valueTypeBindVar;
    }

    public RichSelectOneChoice getValueTypeBindVar() {
        return valueTypeBindVar;
    }

    public void qcParamDescVCE(ValueChangeEvent valueChangeEvent) {
        setAttrsVal("MnfBomQcParamIterator", "TlrncLower", 0);
        setAttrsVal("MnfBomQcParamIterator", "TlrncUpper", 0);
        setAttrsVal("MnfBomQcParamIterator", "UpperLimit", 0);
        setAttrsVal("MnfBomQcParamIterator", "LowerLimit", 0);
        setAttrsVal("MnfBomQcParamIterator", "StdVal", 0);
        AdfFacesContext.getCurrentInstance().addPartialTarget(qcParamTable);

    }

    public void qcParamValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (duplicateQcParam("MnfBomQcParamIterator", object, getAttrsVal("MnfBomQcParamIterator", "OpId"))) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate Parameter Selected",
                                                          null));
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(qcParamTable);

    }

    /**
     * Method to check duplicate QC parameter for each Operation
     * **/
    private boolean duplicateQcParam(String iter, Object paramId, Object oprId) {

        DCIteratorBinding dcIter = ADFUtils.findIterator(iter);
        ViewObject vo = dcIter.getViewObject();
        RowSetIterator rSetIter = vo.createRowSetIterator(null);

        Row r = null;
        Integer countVal = 0;

        //check the null values
        if (paramId != null && oprId != null) {

            while (rSetIter.hasNext()) {
                r = rSetIter.next();
                //  System.out.println("attrsNm "+r.getAttribute("ItmDesc"));
                // System.out.println(" att type   "+ r.getAttribute("ItmType"));

                if (paramId.equals(r.getAttribute("TransQcParamDesc")) && oprId.equals(r.getAttribute("OpId"))) {
                    countVal = countVal + 1;
                }
            }
            rSetIter.closeRowSetIterator();

            //exclude count from current row
            Row currentRow = dcIter.getCurrentRow();

            if (paramId.equals(currentRow.getAttribute("TransQcParamDesc")) &&
                oprId.equals(currentRow.getAttribute("OpId"))) {
                countVal = countVal - 1;
            }
        }

        return countVal == 1 ? true : false;
    }

    public void setPrincipleItemBinding(RichSelectBooleanCheckbox principleItemBinding) {
        this.principleItemBinding = principleItemBinding;
    }

    public RichSelectBooleanCheckbox getPrincipleItemBinding() {
        return principleItemBinding;
    }

    public void principleItemValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().equalsIgnoreCase("true")) {

            if ((getAttrsVal("MnfBomOpItmVOIterator", "ItmId") == null)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Select Operation Input Item", null));
            }

            if (getAttrsVal("MnfBomOpItmVOIterator", "ItmId") != null) {

                String docId = getAttrsVal("MnfBomOpItmVOIterator", "DocId").toString();
                System.out.println(docId + " dubble data in doc ID");
                ob = ADFUtils.findOperation("chkDoublePrincpleItem");
                ob.getParamsMap().put("itmId", docId);
                ob.execute();

                if (ob.getResult().equals(true)) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Principle Item already exist for this Output Item",
                                                                  null));
                }
            }
        }

        //MnfBomOpItmVOIterator", "PrncplInpt", "N");
        // setAttrsVal("MnfBomOpItmVOIterator", "ItmId", null);
    }

    public void setRefrencedBomBinding(RichInputListOfValues refrencedBomBinding) {
        this.refrencedBomBinding = refrencedBomBinding;
    }

    public RichInputListOfValues getRefrencedBomBinding() {
        return refrencedBomBinding;
    }

    public boolean getChelIt() {
        if (getAttrsVal("MnfBomOpItmVOIterator", "ItmId") != null &&
            getAttrsVal("MnfBomVOIterator", "OutptItmId") != null &&
            getAttrsVal("MnfBomOpItmVOIterator",
                        "ItmId").toString().equalsIgnoreCase(getAttrsVal("MnfBomVOIterator",
                                                                         "OutptItmId").toString())) {

            return true;
        }

        return false;
    }

    public boolean getLastOpr() {

        return false;
    }

    public void opDescVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(qcParamTable);
    }

    public void setAddQueueBind(RichLink addQueueBind) {
        this.addQueueBind = addQueueBind;
    }

    public RichLink getAddQueueBind() {
        return addQueueBind;
    }

    public void orgVCE(ValueChangeEvent valueChangeEvent) {

        System.out.println("helloooooooooo");
        //System.out.println("--- " +  getAttrsVal("OrgMnfBomVOIterator", "OrgId").toString());
        ActionEvent acc = new ActionEvent(addQueueBind);
        acc.queue();
        addQueueACE(acc);
    }

    public void orgTabDL(DisclosureEvent disclosureEvent) {
        if (disclosureEvent.isExpanded()) {
            ob = ADFUtils.findOperation("dataBeforeCommit");
            ob.execute();
        } else {

        }
    }

    public void orgActiveVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println("Org id is " + getOrgId());
        if (object != null && object.toString().equalsIgnoreCase("true")) {
            ob = ADFUtils.findOperation("chkOrgWiseItemRep");
            // ob.getParamsMap().put("orgId",getAttrsVal("OrgMnfBomVOIterator", "OrgId").toString());
            ob.getParamsMap().put("orgId", getOrgId());
            ob.execute();
            if (ob.getResult() == -1) {
                setRetVal("Y");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Operation Item/Items are not replicated in an Organization ",
                                                              null));
            }
        }

    }

    public void opDescVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(qcParamTable);
    }

    public void qcOrgIdBindVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(transQcParamDescBind);

    }

    public void setTransQcParamDescBind(RichInputListOfValues transQcParamDescBind) {
        this.transQcParamDescBind = transQcParamDescBind;
    }

    public RichInputListOfValues getTransQcParamDescBind() {
        return transQcParamDescBind;
    }

    public void itemPriceTypeVCE(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            Number latestPrice = new Number();
            System.out.println("Item Price Type is : " + vce.getNewValue());
            // if (Integer.parseInt(vce.getNewValue().toString())==69){

            ob = ADFUtils.findOperation("bomLatestPrice");
            ob.getParamsMap().put("itm_id", getAttrsVal("MnfBomOpItmVOIterator", "ItmId"));
            ob.getParamsMap().put("itm_typ", Integer.parseInt(vce.getNewValue().toString()));
            ob.execute();
            latestPrice = (Number) ob.getResult();
            //  }
            System.out.println("Price is : " + latestPrice);
            Number number = new Number(ADFBeanUtils.roundOff(latestPrice));
            System.out.println("Price is : " + number);
            setAttrsVal("MnfBomOpItmVOIterator", "ItmPrice", number);
            updateItmAmt((Number) getAttrsVal("MnfBomOpItmVOIterator", "ItmQty"), number);
            AdfFacesContext.getCurrentInstance().addPartialTarget(outputItmTableBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(inputItmTableBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmPriceBindVar);
        }
    }

    public void opYieldPercVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Integer errCode = validateNumber(26, 6, object);

        if (errCode.compareTo(1) == 0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          "Invalid Number. Precision Should be (26,6) ", null));
        }
        if (errCode.compareTo(2) == 0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Value cannot be Negative",
                                                          null));
        }

        if (object != null && ((Number) object).compareTo(100) == 1) {

            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          "Yield % cannot be greater than 100", null));

        }

    }

    public static BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    /**
     * To export bom item on  XL file
     * */
    public void exportAction(FacesContext facesContext, OutputStream outputStream) {

        System.out.println("Exporting File: ");
        /*     DCBindingContainer bindings2 = (DCBindingContainer) getBindings();
            JUCtrlHierBinding obj = (JUCtrlHierBinding) bindings2.findCtrlBinding("MnfBomOpItmAllVO");
            ViewObject vo = obj.getViewObject();

     DCIteratorBinding dcIter = ADFUtils.findIterator("MnfBomOpItmAllVOIterator"); */

        ViewObject vo = ADFUtils.findIterator("MnfBomOpItmAllVOIterator").getViewObject();

        RowSetIterator itr = vo.createRowSetIterator(null);
        System.out.println("Rows are: " + itr.getRowCount());

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sheet1");
        HSSFCellStyle cellStyle = workbook.createCellStyle();

        HSSFFont font = workbook.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        cellStyle.setFont(font);

        HSSFRow createRow = sheet.createRow(0);
        for (int i = 0; i < 10; i++) {
            Cell cell = createRow.createCell(i);
            // sheet.autoSizeColumn(i);
            // sheet.setColumnWidth(100+i,100);
            switch (i) {
            case 0:
                cell.setCellValue("OPERATION_DESC");
                cell.setCellStyle(cellStyle);
                break;
            case 1:
                cell.setCellValue("OPR_SR_No");
                cell.setCellStyle(cellStyle);
                break;
            case 2:
                cell.setCellValue("ITM_DESC");
                cell.setCellStyle(cellStyle);
                break;
            case 3:
                cell.setCellValue("QUANTITY");
                cell.setCellStyle(cellStyle);
                break;
            case 4:
                cell.setCellValue("ITM_TYPE");
                cell.setCellStyle(cellStyle);
                break;
            case 5:
                cell.setCellValue("ITM_PRICE");
                cell.setCellStyle(cellStyle);
                break;
            case 6:
                cell.setCellValue("ITM_AMOUNT");
                cell.setCellStyle(cellStyle);
                break;
            case 7:
                cell.setCellValue("OPERATION_ID");
                cell.setCellStyle(cellStyle);
                break;
            case 8:
                cell.setCellValue("ITM_ID");
                cell.setCellStyle(cellStyle);
                break;
            case 9:
                cell.setCellValue("RT_ID");
                cell.setCellStyle(cellStyle);
                break;

            }
        }
        int rownum = 1;
        while (itr.hasNext()) {
            Row next = itr.next();
            HSSFRow row = sheet.createRow(rownum++);
            for (int i = 0; i <= 10; i++) {
                Cell cell = row.createCell(i);
                switch (i) {
                case 0:
                    if (next.getAttribute("OpDescAll") != null) {
                        cell.setCellValue(next.getAttribute("OpDescAll").toString());
                    }
                    break;
                case 1:
                    if (next.getAttribute("OpSrNo") != null) {
                        cell.setCellValue(next.getAttribute("OpSrNo").toString());
                    }
                    break;
                case 2:
                    if (next.getAttribute("ItmDescAll") != null) {
                        cell.setCellValue(next.getAttribute("ItmDescAll").toString());
                    }
                    break;
                case 3:
                    if (next.getAttribute("ItmQty") != null) {
                        Double val1 = new Double(next.getAttribute("ItmQty").toString());
                        cell.setCellValue(val1);
                    }
                    break;
                case 4:
                    if (next.getAttribute("ItmType") == null) {
                    }
                    //  cell.setCellValue(null);
                    else {
                        Double val2 = new Double(next.getAttribute("ItmType").toString());
                        cell.setCellValue(val2);
                    }
                    break;
                case 5:
                    if (next.getAttribute("ItmPrice") != null) {
                        Double val3 = new Double(next.getAttribute("ItmPrice").toString());
                        cell.setCellValue(val3);
                    }
                    break;
                case 6:
                    if (next.getAttribute("ItmTotAmt") != null) {
                        Double val4 = new Double(next.getAttribute("ItmTotAmt").toString());
                        cell.setCellValue(val4);
                    }
                    break;
                case 7:
                    if (next.getAttribute("OpId") != null) {
                        cell.setCellValue(next.getAttribute("OpId").toString());

                    }
                    break;
                case 8:
                    if (next.getAttribute("ItmId") != null) {
                        cell.setCellValue(next.getAttribute("ItmId").toString());

                    }
                    break;
                case 9:
                    if (getAttrsVal("MnfBomVOIterator", "RtId") != null) {
                        cell.setCellValue(getAttrsVal("MnfBomVOIterator", "RtId").toString());
                    }
                    break;

                }
            }
        }
        try {
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            itr.closeRowSetIterator();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exportBOMOperationAction(FacesContext facesContext, OutputStream outputStream) {

        System.out.println("Exporting File: ");

        ViewObject vo = ADFUtils.findIterator("MnfBomOpIterator").getViewObject();

        RowSetIterator itr = vo.createRowSetIterator(null);
        System.out.println("Rows are: " + itr.getRowCount());

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sheet1");
        HSSFCellStyle cellStyle = workbook.createCellStyle();

        HSSFFont font = workbook.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        cellStyle.setFont(font);

        HSSFRow createRow = sheet.createRow(0);
        for (int i = 0; i < 10; i++) {
            Cell cell = createRow.createCell(i);
            // sheet.autoSizeColumn(i);
            // sheet.setColumnWidth(100+i,100);
            switch (i) {
            case 0:
                cell.setCellValue("OPERATION_DESC");
                cell.setCellStyle(cellStyle);
                break;
            case 1:
                cell.setCellValue("OPR_SR_No");
                cell.setCellStyle(cellStyle);
                break;
            case 2:
                cell.setCellValue("OPERATION_ID");
                cell.setCellStyle(cellStyle);
                break;
            case 3:
                cell.setCellValue("RT_ID");
                cell.setCellStyle(cellStyle);
                break;
            case 4:
                cell.setCellValue("ITM_DESC");
                cell.setCellStyle(cellStyle);
                break;
            case 5:
                cell.setCellValue("QUANTITY");
                cell.setCellStyle(cellStyle);
                break;
            case 6:
                cell.setCellValue("ITM_TYPE");
                cell.setCellStyle(cellStyle);
                break;
            case 7:
                cell.setCellValue("ITM_PRICE");
                cell.setCellStyle(cellStyle);
                break;
            case 8:
                cell.setCellValue("ITM_AMOUNT");
                cell.setCellStyle(cellStyle);
                break;
            case 9:
                cell.setCellValue("ITM_ID");
                cell.setCellStyle(cellStyle);
                break;

            }
        }
        int rownum = 1;
        while (itr.hasNext()) {
            Row next = itr.next();
            HSSFRow row = sheet.createRow(rownum++);
            for (int i = 0; i <= 9; i++) {
                Cell cell = row.createCell(i);
                switch (i) {
                case 0:
                    if (next.getAttribute("OpDesc") != null) {
                        cell.setCellValue(next.getAttribute("OpDesc").toString());
                    }
                    break;
                case 1:
                    if (next.getAttribute("OpSrNo") != null) {
                        Double val1 = new Double(next.getAttribute("OpSrNo").toString());
                        cell.setCellValue(val1);
                    }
                    break;
                case 2:
                    if (next.getAttribute("OpId") != null) {
                        cell.setCellValue(next.getAttribute("OpId").toString());
                    }
                    break;
                case 3:
                    if (getAttrsVal("MnfBomVOIterator", "RtId") != null) {
                        cell.setCellValue(getAttrsVal("MnfBomVOIterator", "RtId").toString());
                    }
                    break;
                case 7:
                    cell.setCellValue(0.0);
                    break;
                case 8:
                    cell.setCellValue(0.0);
                    break;


                }
            }
        }
        try {
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            itr.closeRowSetIterator();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setBomUsageBind(RichSelectOneChoice bomUsageBind) {
        this.bomUsageBind = bomUsageBind;
    }

    public RichSelectOneChoice getBomUsageBind() {
        return bomUsageBind;
    }

    public void bomUsageVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {

        AdfFacesContext.getCurrentInstance().addPartialTarget(hdrFormBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(refrencedBomBinding);
    }
}
