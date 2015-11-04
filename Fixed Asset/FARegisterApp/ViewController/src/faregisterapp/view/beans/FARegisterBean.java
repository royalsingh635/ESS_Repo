package faregisterapp.view.beans;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.model.ADFModelUtils;

import java.io.IOException;
import java.io.OutputStream;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class FARegisterBean {
    private RichSelectOneChoice docTypeBinding;
    private RichPopup lineDetailBinding;
    private RichTable itemTableBinding;
    private RichInputText addtnlDeprBinding;
    private RichInputText addtnlAccDepBinding;
    private RichSelectOneChoice statusBinding;
     private RichInputText regNoBinding;

    public FARegisterBean() {
    }
    private String mode = "V";
    private String dis = "V";

    public void setDis(String dis) {
        this.dis = dis;
    }

    public String getDis() {
        return dis;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public String CreateSourceAction() {
        ADFBeanUtils.callBindingsMethod("CreateInsert", null, null);
        mode = "A";
        return null;
    }

    public String LoadInvoiceAction() {
        ADFBeanUtils.callBindingsMethod("CreateInsert1", null, null);
        ADFBeanUtils.callBindingsMethod("loadInvoiceDetails", null, null);

        return null;
    }

    public String saveAction() {
        Object bindingsMethod = ADFBeanUtils.callBindingsMethod("validateItem", new Object[] { "V" }, new Object[] {
                                                                "mode" });
        System.out.println("bindingsMethod = " + bindingsMethod);
        if (bindingsMethod != null) {
            if (bindingsMethod.toString().equalsIgnoreCase("Y")) {
                mode = "V";
                ADFBeanUtils.callBindingsMethod("Commit", null, null);
                ADFModelUtils.showFacesMessage("Record Saved Succesfully", "Record Saved Succesfully!",
                                               FacesMessage.SEVERITY_INFO, null);
            } else {
                ADFModelUtils.showFacesMessage("Invalid data imported", "Invalid data imported!",
                                               FacesMessage.SEVERITY_ERROR, null);
            }
        }
        return null;
    }

    public String cancelAction() {
        mode = "V";
        ADFBeanUtils.callBindingsMethod("Rollback", null, null);
        return "back";
    }

    public String editAction() {

        if (docTypeBinding != null) {
            if (docTypeBinding.getValue().equals(5507)) {
                Object Editopening = ADFBeanUtils.callBindingsMethod("EditOpeningBalancemethod", null, null);
                if (Editopening == "Y") {
                    String msg = "Can not edit Opening Balance";
                    ADFModelUtils.showFacesMessage(msg, msg, FacesMessage.SEVERITY_ERROR, null);
                } else {
                    mode = "E";
                }
            } else
                mode = "E";
        }
        return null;
    }


    public String loadItems() {
        if (docTypeBinding.getValue().equals(18521)) {
            ADFBeanUtils.callBindingsMethod("loadItemDetails", null, null);
            //  dis="E";
        } else if (docTypeBinding.getValue().equals(5507)) {
            ADFBeanUtils.callBindingsMethod("CreateInsert2", null, null);
            dis = "A";
            //  ADFBeanUtils.showPopup(lineDetailBinding, true);
        }
        return null;
    }

    public void invoiceDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Object res = ADFBeanUtils.callBindingsMethod("allowDate", null, null);
            if (res != null) {
                //                SimpleDateFormat sd=new SimpleDateFormat("dd-MON-yy");
                //                object = sd.format(object);

                //                System.out.println("res.toString().compareTo(object.toString())<0) = "+(res.toString().compareTo(object.toString())<0)+" res= "+res+"object = "+object);
                //                System.out.println("docTypeBinding.getValue().equals(18521) = "+(docTypeBinding.getValue().equals(18521))+" docTypeBinding.getValue() = "+docTypeBinding.getValue());
                if ((res.toString().compareTo(object.toString()) <= 0) && !(docTypeBinding.getValue().equals(18521))) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Date cannot be greater than " + res +
                                                                  "for opening Balance", null));
                } else if ((res.toString().compareTo(object.toString()) >= 0) &&
                           (docTypeBinding.getValue().equals(18521))) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Date cannot be smaller than " + res + " for Invoice",
                                                                  null));

                }
            }

        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(docTypeBinding);
    }

    public void setDocTypeBinding(RichSelectOneChoice docTypeBinding) {
        this.docTypeBinding = docTypeBinding;
    }

    public RichSelectOneChoice getDocTypeBinding() {
        return docTypeBinding;
    }

    public String editItemDetails() {
        Object res = ADFBeanUtils.callBindingsMethod("editAllow", null, null);
        if (res != null) {
            if (res.toString().equalsIgnoreCase("Y")) {
                ADFModelUtils.showFacesMessage("You cannot edit the record!", "You cannot edit the record!",
                                               FacesMessage.SEVERITY_ERROR, null);
            } else {
                //ADFBeanUtils.showPopup(lineDetailBinding, true);
                dis = "E";
            }
        }
        return null;
    }

    public void setLineDetailBinding(RichPopup lineDetailBinding) {
        this.lineDetailBinding = lineDetailBinding;
    }

    public RichPopup getLineDetailBinding() {
        return lineDetailBinding;
    }

    public String submitLineValue() {
        Object bindingsMethod = ADFBeanUtils.callBindingsMethod("validateItem", new Object[] { dis }, new Object[] {
                                                                "mode" });
        System.out.println("bindingsMethod = " + bindingsMethod);
        if (bindingsMethod != null) {

            if (bindingsMethod.toString().equalsIgnoreCase("Y")) {
                ADFBeanUtils.callBindingsMethod("updateItemLine", null, null);
                dis = "V";
                AdfFacesContext.getCurrentInstance().addPartialTarget(itemTableBinding);
            } else
                ADFModelUtils.showFacesMessage(bindingsMethod.toString(), bindingsMethod.toString(),
                                               FacesMessage.SEVERITY_ERROR, null);
        }
        return null;
    }

    public void setItemTableBinding(RichTable itemTableBinding) {
        this.itemTableBinding = itemTableBinding;
    }

    public RichTable getItemTableBinding() {
        return itemTableBinding;
    }

    public String createOnpageLoad() {
        if (ADFModelUtils.resolvEl("#{pageFlowScope.PARAM_APP_MODE}").toString().equalsIgnoreCase("A")) {
            System.out.println("before setting mod=v");
            mode = "A";
            //   ADFBeanUtils.callBindingsMethod("CreateInsert", null, null);

        } else if (ADFModelUtils.resolvEl("#{pageFlowScope.PARAM_APP_MODE}").toString().equalsIgnoreCase("V")) {
            System.out.println("before setting mod=A");

            mode = "V";
            //   ADFBeanUtils.callBindingsMethod("filterVoOnPageLoad", null, null);

        }
        return "abc";
    }


    public void depPRcntCOValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (!ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_LAW_TYP}").toString().equalsIgnoreCase("IT")) {
            if (object != null) {
                Number per = (Number) object;
                Boolean b = ADFBeanUtils.isPrecisionValid(9, 6, per);
                if (!b) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Invalid Precision/Scale", null));

                }
            }
        }
    }

    public void dprcntPrcntgItValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (!ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_LAW_TYP}").toString().equalsIgnoreCase("CO")) {
            // Add event code here...
            // Add event code here...
            if (object != null) {
                Number per = (Number) object;
                Boolean b = ADFBeanUtils.isPrecisionValid(9, 6, per);
                if (!b) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Invalid Precision/Scale", null));

                }
            }
        }
    }

    public void addcostBsValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number per = (Number) object;
            Boolean b = ADFBeanUtils.isPrecisionValid(26, 6, per);
            if (!b) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision/Scale",
                                                              null));

            }

        }
    }

    public void totalcostCOValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (!ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_LAW_TYP}").toString().equalsIgnoreCase("IT")) {
            if (object != null) {
                Number per = (Number) object;
                Boolean b = ADFBeanUtils.isPrecisionValid(26, 6, per);
                if (!b) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Invalid Precision/Scale", null));

                }

            }
        }
    }

    public void totalCostITValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (!ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_LAW_TYP}").toString().equalsIgnoreCase("CO")) {
            if (object != null) {
                Number per = (Number) object;
                Boolean b = ADFBeanUtils.isPrecisionValid(26, 6, per);
                if (!b) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Invalid Precision/Scale", null));

                }

            }
        }
    }

    public void accDprctnCO(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (!ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_LAW_TYP}").toString().equalsIgnoreCase("IT")) {
            if (object != null) {
                Number per = (Number) object;
                Boolean b = ADFBeanUtils.isPrecisionValid(26, 6, per);
                if (!b) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Invalid Precision/Scale", null));

                }

            }
        }

    }

    public void addDepITValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (!ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_LAW_TYP}").toString().equalsIgnoreCase("CO")) {
            if (object != null) {
                Number per = (Number) object;
                Boolean b = ADFBeanUtils.isPrecisionValid(26, 6, per);
                if (!b) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Invalid Precision/Scale", null));

                }
            }
        }
    }


    public void labelIdValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            Object bindingsMethod = ADFBeanUtils.callBindingsMethod("validateLabelId", new Object[] { object }, new Object[] {
                                                                    "label" });
            if (bindingsMethod != null) {
                if (!bindingsMethod.toString().equalsIgnoreCase("Y")) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  bindingsMethod.toString(), null));

                }
            }
        }
    }

    public String transferItem() {
        Object bindingsMethod = ADFBeanUtils.callBindingsMethod("moveItemToOrg", null, null);
        if (bindingsMethod != null) {
            if (bindingsMethod.toString().equalsIgnoreCase("Y")) {
                ADFModelUtils.showFacesMessage("Item Transfered Successfuly", "Item Transfered Successfuly",
                                               FacesMessage.SEVERITY_INFO, null);
                lineDetailBinding.hide();

            } else {
                ADFModelUtils.showFacesMessage(bindingsMethod.toString(), bindingsMethod.toString(),
                                               FacesMessage.SEVERITY_ERROR, null);
            }
        }
        return null;
    }

    public void exportData(FacesContext facesContext, OutputStream outputStream) {
        // Add event code here...
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Item_Register");
        HSSFCellStyle cellStyle = workbook.createCellStyle();

        HSSFFont font = workbook.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        cellStyle.setFont(font);

        HSSFRow row = sheet.createRow(0);
        System.out.println("after creating first row");
        int j = 0;
        for (int i = 0; i <= 25; i++) {
            HSSFCell cell = null;
            System.out.println("after creating first coloumn" + i);
            switch (i) {
            case 0:
                cell = row.createCell(j);
                j++;
                cell.setCellValue("Group");
                cell.setCellStyle(cellStyle);
                break;
            case 1:
                cell = row.createCell(j);
                j++;
                cell.setCellValue("Item");
                cell.setCellStyle(cellStyle);
                break;
            case 2:
                cell = row.createCell(j);
                j++;
                cell.setCellValue("Label");
                cell.setCellStyle(cellStyle);
                break;
            case 3:
                cell = row.createCell(j);
                j++;
                cell.setCellValue("Salvage Value");
                cell.setCellStyle(cellStyle);
                break;
            case 4:
                cell = row.createCell(j);
                j++;
                cell.setCellValue("Additional Cost");
                cell.setCellStyle(cellStyle);
                break;
            case 5:
                if (ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_ADDTNL_DEP_ALW}").toString().equalsIgnoreCase("Y") &&
                    !ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_LAW_TYP}").toString().equalsIgnoreCase("CO")) {
                    cell = row.createCell(j);
                    j++;
                    cell.setCellValue("Additional Depreciation Allow");
                    cell.setCellStyle(cellStyle);
                }
                break;
            case 6:
                if (ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_ADDTNL_DEP_ALW}").toString().equalsIgnoreCase("Y") &&
                    !ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_LAW_TYP}").toString().equalsIgnoreCase("CO")) {
                    cell = row.createCell(j);
                    j++;
                    cell.setCellValue("Additional Depreciation Percentage");
                    cell.setCellStyle(cellStyle);
                }
                break;
            case 7:
                if (ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_ADDTNL_DEP_ALW}").toString().equalsIgnoreCase("Y") &&
                    !ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_LAW_TYP}").toString().equalsIgnoreCase("CO")) {
                    cell = row.createCell(j);
                    j++;
                    cell.setCellValue("Additional Accumulated Depreciation");
                    cell.setCellStyle(cellStyle);
                }
                break;
            case 8:
                cell = row.createCell(j);
                j++;
                cell.setCellValue("Remarks");
                cell.setCellStyle(cellStyle);
                break;
            case 9:
                if (!ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_LAW_TYP}").toString().equalsIgnoreCase("IT")) {
                    cell = row.createCell(j);
                    j++;
                    cell.setCellValue("Total Cost(CO)");
                    cell.setCellStyle(cellStyle);
                }
                break;
            case 10:
                if (!ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_LAW_TYP}").toString().equalsIgnoreCase("IT")) {
                    cell = row.createCell(j);
                    j++;
                    cell.setCellValue("Depreciation Percentage(CO)");
                    cell.setCellStyle(cellStyle);
                }
                break;
            case 11:
                if (!ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_LAW_TYP}").toString().equalsIgnoreCase("IT")) {
                    cell = row.createCell(j);
                    j++;
                    cell.setCellValue("Accumulated Depriciation(CO)");
                    cell.setCellStyle(cellStyle);
                }
                break;
            case 12:
                if (!ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_LAW_TYP}").toString().equalsIgnoreCase("IT")) {
                    cell = row.createCell(j);
                    j++;
                    cell.setCellValue("Start Date(CO)");
                    cell.setCellStyle(cellStyle);
                }
                break;
            case 13:
                if (!ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_LAW_TYP}").toString().equalsIgnoreCase("IT") &&
                    ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_SHIFT_ALW}").toString().equalsIgnoreCase("Y")) {
                    cell = row.createCell(j);
                    j++;
                    cell.setCellValue("Shift");
                    cell.setCellStyle(cellStyle);
                }
                break;
            case 14:
                if (!ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_LAW_TYP}").toString().equalsIgnoreCase("CO")) {
                    cell = row.createCell(j);
                    j++;
                    cell.setCellValue("Total Cost(IT)");
                    cell.setCellStyle(cellStyle);
                }
                break;
            case 15:
                if (!ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_LAW_TYP}").toString().equalsIgnoreCase("CO")) {
                    cell = row.createCell(j);
                    j++;
                    cell.setCellValue("Depreciation Percentage(IT)");
                    cell.setCellStyle(cellStyle);
                }
                break;
            case 16:
                if (!ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_LAW_TYP}").toString().equalsIgnoreCase("CO")) {
                    cell = row.createCell(j);
                    j++;
                    cell.setCellValue("Accumulated Depriciation(IT)");
                    cell.setCellStyle(cellStyle);
                }
                break;
            case 17:
                if (!ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_LAW_TYP}").toString().equalsIgnoreCase("CO")) {
                    cell = row.createCell(j);
                    j++;
                    cell.setCellValue("Start Date(IT)");
                    cell.setCellStyle(cellStyle);
                }
                break;
            case 18:
                cell = row.createCell(j);
                j++;
                cell.setCellValue("Instance Id");
                cell.setCellStyle(cellStyle);
                break;
            case 19:
                cell = row.createCell(j);
                j++;
                cell.setCellValue("Sequence Id");
                cell.setCellStyle(cellStyle);
                break;
            case 20:
                cell = row.createCell(j);
                j++;
                cell.setCellValue("User Id");
                cell.setCellStyle(cellStyle);
                break;
            case 21:
                cell = row.createCell(j);
                j++;
                cell.setCellValue("Created On");
                cell.setCellStyle(cellStyle);
                break;
            case 22:
                cell = row.createCell(j);
                j++;
                cell.setCellValue("Source");
                cell.setCellStyle(cellStyle);
                break;
            case 23:
                cell = row.createCell(j);
                j++;
                cell.setCellValue("Active");
                cell.setCellStyle(cellStyle);
                break;
            case 24:
                cell = row.createCell(j);
                j++;
                cell.setCellValue("Warranty");
                cell.setCellStyle(cellStyle);
                break;
            case 25:
                cell = row.createCell(j);
                j++;
                cell.setCellValue("Insurance");
                cell.setCellStyle(cellStyle);
                break;
            default:
                break;

            }

        }

        // Load Data present in table
        DCBindingContainer dcBindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcItr = (DCIteratorBinding) dcBindings.get("MmFaItmLn1Iterator");
        ViewObjectImpl vo = (ViewObjectImpl) dcItr.getViewObject();
        RowSetIterator rsi = vo.createRowSetIterator(null);
        int rownum = 1;
        while (rsi.hasNext()) {
            j = 0; // Reset the value of coloumn id

            Row next = rsi.next();
            if (next != null) {
                //rownum++;
                System.out.println("rownum = " + rownum);
                Object ItmGrp = next.getAttribute("ItmGrp");
                StringBuilder ItmGrpId =
                    (ItmGrp == null ? new StringBuilder("") : new StringBuilder(ItmGrp.toString()));

                Object ItmId = next.getAttribute("ItmId");
                StringBuilder ItemId = (ItmId == null ? new StringBuilder("") : new StringBuilder(ItmId.toString()));

                Object ItmLblId = next.getAttribute("ItmLblId");
                StringBuilder label =
                    (ItmLblId == null ? new StringBuilder("") : new StringBuilder(ItmLblId.toString()));

                Object ItmSelvgValue = next.getAttribute("ItmSelvgValue");
                StringBuilder slvg =
                    (ItmSelvgValue == null ? new StringBuilder("") : new StringBuilder(ItmSelvgValue.toString()));

                Object AddlCostBs = next.getAttribute("AddlCostBs");
                StringBuilder AddlCost =
                    (AddlCostBs == null ? new StringBuilder("") : new StringBuilder(AddlCostBs.toString()));

                Object AddlAccDep = next.getAttribute("AddlAccDep");
                StringBuilder AddlDep =
                    (AddlAccDep == null ? new StringBuilder("") : new StringBuilder(AddlAccDep.toString()));

                Object Remark = next.getAttribute("Remark");
                System.out.println("Remark = " + Remark);
                StringBuilder Remarks = (Remark == null ? new StringBuilder("") : new StringBuilder(Remark.toString()));

                Object ItmTotCostCo = next.getAttribute("ItmTotCostCo");
                StringBuilder TotCostCo =
                    (ItmTotCostCo == null ? new StringBuilder("") : new StringBuilder(ItmTotCostCo.toString()));

                Object DepPerCoLaw = next.getAttribute("DepPerCoLaw");
                StringBuilder DepPerCo =
                    (DepPerCoLaw == null ? new StringBuilder("") : new StringBuilder(DepPerCoLaw.toString()));

                Object DepEffStrtDtCo = next.getAttribute("DepEffStrtDtCo");
                System.out.println("DepEffStrtDtCo = " + DepEffStrtDtCo);
                StringBuilder DepStrtDtCo =
                    (DepEffStrtDtCo == null ? new StringBuilder("") : new StringBuilder(DepEffStrtDtCo.toString()));
                System.out.println("DepStrtDtCo = " + DepStrtDtCo);
                Object DepShftCoLaw = next.getAttribute("DepShftCoLaw");
                StringBuilder DepShftCo =
                    (DepShftCoLaw == null ? new StringBuilder("") : new StringBuilder(DepShftCoLaw.toString()));

                Object AccDepCo = next.getAttribute("AccDepCo");
                StringBuilder AcDepCo =
                    (AccDepCo == null ? new StringBuilder("") : new StringBuilder(AccDepCo.toString()));

                Object ItmTotCostIt = next.getAttribute("ItmTotCostIt");
                StringBuilder TotCostIt =
                    (ItmTotCostIt == null ? new StringBuilder("") : new StringBuilder(ItmTotCostIt.toString()));

                Object DepPerItLaw = next.getAttribute("DepPerItLaw");
                StringBuilder DepPerIt =
                    (DepPerItLaw == null ? new StringBuilder("") : new StringBuilder(DepPerItLaw.toString()));

                Object DepEffStrtDtIt = next.getAttribute("DepEffStrtDtIt");
                StringBuilder DepStrtDtIt =
                    (DepEffStrtDtIt == null ? new StringBuilder("") : new StringBuilder(DepEffStrtDtIt.toString()));

                Object AccDepIt = next.getAttribute("AccDepIt");
                StringBuilder AcDepIt =
                    (AccDepIt == null ? new StringBuilder("") : new StringBuilder(AccDepIt.toString()));

                Object CreateId = next.getAttribute("CreateId");
                StringBuilder CreateBy =
                    (CreateId == null ? new StringBuilder("") : new StringBuilder(CreateId.toString()));

                Object CreateDt = next.getAttribute("CreateDt");
                StringBuilder Createon =
                    (CreateDt == null ? new StringBuilder("") : new StringBuilder(CreateDt.toString()));

                Object AddlDepFlg = next.getAttribute("AddlDepFlg");
                StringBuilder addDepFlag =
                    (AddlDepFlg == null ? new StringBuilder("") : new StringBuilder(AddlDepFlg.toString()));

                Object AddlDepPer = next.getAttribute("AddlDepPer");
                StringBuilder addPer =
                    (AddlDepPer == null ? new StringBuilder("") : new StringBuilder(AddlDepPer.toString()));

                HSSFRow rw = sheet.createRow(rownum++);
                for (int i = 0; i <= 25; i++) {
                    HSSFCell cell = null;

                    switch (i) {
                    case 0:
                        cell = rw.createCell(j);
                        j++;
                        cell.setCellValue(ItmGrpId.toString());
                        break;
                    case 1:
                        cell = rw.createCell(j);
                        j++;
                        cell.setCellValue(ItemId.toString());
                        break;
                    case 2:
                        cell = rw.createCell(j);
                        j++;
                        cell.setCellValue(label.toString());
                        break;
                    case 3:
                        cell = rw.createCell(j);
                        j++;
                        if (slvg.toString().length() > 0)
                            cell.setCellValue(new Double(slvg.toString()));
                        else
                            cell.setCellValue(slvg.toString());
                        break;
                    case 4:
                        cell = rw.createCell(j);
                        j++;
                        if (AddlCost.toString().length() > 0)
                            cell.setCellValue(new Double(AddlCost.toString()));
                        else
                            cell.setCellValue(AddlCost.toString());
                        break;
                    case 5:
                        if (ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_ADDTNL_DEP_ALW}").toString().equalsIgnoreCase("Y") &&
                            !ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_LAW_TYP}").toString().equalsIgnoreCase("CO")) {
                            cell = rw.createCell(j);
                            j++;
                            cell.setCellValue(addDepFlag.toString());
                        }
                        break;
                    case 6:
                        if (ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_ADDTNL_DEP_ALW}").toString().equalsIgnoreCase("Y") &&
                            !ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_LAW_TYP}").toString().equalsIgnoreCase("CO")) {
                            cell = rw.createCell(j);
                            j++;
                            if (addPer.toString().length() > 0) {
                                cell.setCellValue(new Double(addPer.toString()));
                            } else
                                cell.setCellValue(addPer.toString());
                        }
                        break;
                    case 7:
                        if (ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_ADDTNL_DEP_ALW}").toString().equalsIgnoreCase("Y") &&
                            !ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_LAW_TYP}").toString().equalsIgnoreCase("CO")) {
                            cell = rw.createCell(j);
                            j++;
                            if (AddlDep.toString().length() > 0)
                                cell.setCellValue(new Double(AddlDep.toString()));
                            else
                                cell.setCellValue(AddlDep.toString());
                        }
                        break;
                    case 8:
                        cell = rw.createCell(j);
                        j++;
                        cell.setCellValue(Remarks.toString());
                        break;
                    case 9:
                        if (!ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_LAW_TYP}").toString().equalsIgnoreCase("IT")) {
                            cell = rw.createCell(j);
                            j++;
                            if (TotCostCo.toString().length() > 0) {
                                cell.setCellValue(new Double(TotCostCo.toString()));
                            } else
                                cell.setCellValue(TotCostCo.toString());
                        }
                        break;
                    case 10:
                        if (!ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_LAW_TYP}").toString().equalsIgnoreCase("IT")) {
                            cell = rw.createCell(j);
                            j++;
                            if (DepPerCo.toString().length() > 0) {
                                cell.setCellValue(new Double(DepPerCo.toString()));
                            } else
                                cell.setCellValue(DepPerCo.toString());
                        }
                        break;
                    case 11:
                        if (!ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_LAW_TYP}").toString().equalsIgnoreCase("IT")) {
                            cell = rw.createCell(j);
                            j++;
                            if (AcDepCo.toString().length() > 0) {
                                cell.setCellValue(new Double(AcDepCo.toString()));
                            } else
                                cell.setCellValue(AcDepCo.toString());
                        }
                        break;

                    case 12:
                        if (!ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_LAW_TYP}").toString().equalsIgnoreCase("IT")) {
                            cell = rw.createCell(j);
                            j++;
                            cell.setCellValue(getConvertTimeStampToStr(DepStrtDtCo.toString()));
                        }
                        break;

                    case 13:
                        if (!ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_LAW_TYP}").toString().equalsIgnoreCase("IT") &&
                            ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_SHIFT_ALW}").toString().equalsIgnoreCase("Y")) {
                            cell = rw.createCell(j);
                            j++;
                            if (DepShftCo.toString().length() > 0) {
                                cell.setCellValue(new Double(DepShftCo.toString()));
                            } else
                                cell.setCellValue(DepShftCo.toString());
                        }
                        break;

                    case 14:
                        if (!ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_LAW_TYP}").toString().equalsIgnoreCase("CO")) {
                            cell = rw.createCell(j);
                            j++;
                            if (TotCostIt.toString().length() > 0) {
                                cell.setCellValue(new Double(TotCostIt.toString()));
                            } else
                                cell.setCellValue(TotCostIt.toString());
                        }
                        break;
                    case 15:
                        if (!ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_LAW_TYP}").toString().equalsIgnoreCase("CO")) {
                            cell = rw.createCell(j);
                            j++;
                            if (DepPerIt.toString().length() > 0) {
                                cell.setCellValue(new Double(DepPerIt.toString()));
                            } else
                                cell.setCellValue(DepPerIt.toString());
                        }
                        break;
                    case 16:
                        if (!ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_LAW_TYP}").toString().equalsIgnoreCase("CO")) {
                            cell = rw.createCell(j);
                            j++;
                            if (AcDepIt.toString().length() > 0) {
                                cell.setCellValue(new Double(AcDepIt.toString()));
                            } else
                                cell.setCellValue(AcDepIt.toString());
                        }
                        break;

                    case 17:
                        if (!ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_LAW_TYP}").toString().equalsIgnoreCase("CO")) {
                            cell = rw.createCell(j);
                            j++;
                            cell.setCellValue(getConvertTimeStampToStr(DepStrtDtIt.toString()));
                        }
                        break;
                    case 18:
                        cell = rw.createCell(j);
                        j++;
                        cell.setCellValue(1);
                        break;
                    case 19:
                        cell = rw.createCell(j);
                        j++;
                        cell.setCellValue(1);
                        break;
                    case 20:
                        cell = rw.createCell(j);
                        j++;
                        if (CreateBy.toString().length() > 0) {
                            cell.setCellValue(new Double(CreateBy.toString()));
                        } else
                            cell.setCellValue(CreateBy.toString());
                        break;
                    case 21:
                        cell = rw.createCell(j);
                        j++;
                        cell.setCellValue(getConvertTimeStampToStr(Createon.toString()));
                        break;
                    case 22:
                        cell = rw.createCell(j);
                        j++;
                        cell.setCellValue("O");
                        break;
                    case 23:
                        cell = rw.createCell(j);
                        j++;
                        cell.setCellValue("Y");
                        break;
                    case 24:
                        cell = rw.createCell(j);
                        j++;
                        cell.setCellValue("N");
                        break;
                    case 25:
                        cell = rw.createCell(j);
                        j++;
                        cell.setCellValue("N");
                        break;
                    default:
                        break;

                    }
                    sheet.autoSizeColumn(i);
                }


            }
        }
        try {
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getConvertTimeStampToStr(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        //Format to match actual String to parse
        Date dt = null;
        try {
            dt = format.parse(str);
        } catch (ParseException e) {
            System.out.println("Exception Caught=" + e.getStackTrace());
        }
        SimpleDateFormat newFormat = new SimpleDateFormat("dd-MMM-YYY");
        // System.out.println(newFormat.format(dt));
        return newFormat.format(dt);
    }


    public void addtnlAccDeprctnValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (!ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_LAW_TYP}").toString().equalsIgnoreCase("CO")) {
            if (object != null) {
                Number per = (Number) object;
                Boolean b = ADFBeanUtils.isPrecisionValid(26, 6, per);
                if (!b) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Invalid Precision/Scale", null));

                }
            }
        }
    }

    public void addtnlDeprctnVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("N") ||
                valueChangeEvent.getNewValue().toString().equalsIgnoreCase("false")) {
                addtnlDeprBinding.setValue(new Number(0));
                addtnlAccDepBinding.setValue(new Number(0));
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(addtnlDeprBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(addtnlAccDepBinding);
    }

    public void setAddtnlDeprBinding(RichInputText addtnlDeprBinding) {
        this.addtnlDeprBinding = addtnlDeprBinding;
    }

    public RichInputText getAddtnlDeprBinding() {
        return addtnlDeprBinding;
    }

    public void setAddtnlAccDepBinding(RichInputText addtnlAccDepBinding) {
        this.addtnlAccDepBinding = addtnlAccDepBinding;
    }

    public RichInputText getAddtnlAccDepBinding() {
        return addtnlAccDepBinding;
    }


    public String cancelPopupAction() {
        lineDetailBinding.hide();
        return null;
    }

    public void InnvoiceVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        AdfFacesContext.getCurrentInstance().addPartialTarget(docTypeBinding);
    }

    public String adjustment() {
        ADFBeanUtils.callBindingsMethod("adjustment", null, null);
        dis = "E";
        return null;
    }

    public void effctvDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && statusBinding.getValue() != null) {
            if (statusBinding.getValue().toString().equalsIgnoreCase("A")) {
                Timestamp ts = new Timestamp(System.currentTimeMillis());
                System.out.println("ts = " + ts + "object =  " + object);
                System.out.println("ts st=" + ts.toString().substring(0, 10) + " object st =- " +
                                   object.toString().substring(0, 10));
                if (object.toString().substring(0, 10).compareTo(ts.toString().substring(0, 10)) > 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Date cannot be greater than current date in case of addition!",
                                                                  null));
                }
            }
        }
    }

    public void setStatusBinding(RichSelectOneChoice statusBinding) {
        this.statusBinding = statusBinding;
    }

    public RichSelectOneChoice getStatusBinding() {
        return statusBinding;
    }

    public void DocSourceValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (object.equals(5507)) {
                Object Check = ADFBeanUtils.callBindingsMethod("CheckInvoiceExecute", null, null);
                System.out.println("Check==" + Check);
                if (Check == "Y") {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Opening Balance is not Allowed after processing",
                                                                  null));
                }
            }

        }

    }

    public void catageoryVCL(ValueChangeEvent valueChangeEvent) {
        regNoBinding.setValue(null);
        regNoBinding.setValue("");
        AdfFacesContext.getCurrentInstance().addPartialTarget(regNoBinding);
    }

        public void setRegNoBinding(RichInputText regNoBinding) {
        this.regNoBinding = regNoBinding;
    }

    public RichInputText getRegNoBinding() {
        return regNoBinding;
    }
}

