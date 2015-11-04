package appitemprofile.view.bean;

import appitemprofile.model.services.ItemProfileAMImpl;
import appitemprofile.model.views.AppAltItmVORowImpl;
import appitemprofile.model.views.AppItmOrgVOImpl;
import appitemprofile.model.views.AppItmPrfVORowImpl;
import appitemprofile.model.views.ViewItemGrpLOVImpl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import java.sql.Types;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.data.RichTreeTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectManyCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelLabelAndMessage;
import oracle.adf.view.rich.component.rich.layout.RichPanelTabbed;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.LaunchPopupEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.rules.JboPrecisionScaleValidator;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlValueBindingRef;

import org.apache.myfaces.trinidad.component.UIXIterator;
import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


public class ItemProfile {
    private String amName = "ItemProfileAMDataControl";
    private RichInputDate inactiveDate;
    private RichInputText inactiveReason;
    private Date today;
    private int INTEGER = Types.INTEGER;
    private RichTreeTable orgWhTree;
    private String mode = modeGet();
    private RichPanelFormLayout orgWhForm;
    private RichSelectOneChoice orgList;
    private RichSelectManyCheckbox whList;
    private RichInputText itemCOA;
    private RichPanelTabbed panelTab;
    private RichInputText minStock;
    private RichInputText safeStock;
    private RichSelectBooleanCheckbox stockableFlg;
    private RichSelectBooleanCheckbox serviceFlg;
    private RichInputText groupName;
    private RichSelectBooleanCheckbox slsItmFlg;
    private RichSelectBooleanCheckbox purItmFlg;
    private RichSelectBooleanCheckbox wipItmFlg;
    private RichSelectBooleanCheckbox capitalFlg;
    private RichShowDetailItem basicDtlTab;
    private String orgBtn_Disable = "M";
    private String altItmBtn_Disable = "O";
    private RichInputText altItmInactiveReason;
    private RichSelectBooleanCheckbox itmActvBind;
    private RichInputText maxStock;
    private RichPopup groupPopUp;
    private RichPanelGroupLayout maxStkPglBind;
    private RichShowDetailItem altItmTabBind;
    private String tabSelect = "S";
    private boolean tabSelectF = true;
    private String WareHouseMode = "V";
    private RichShowDetailItem orgTab;
    private RichInputListOfValues purUomBind;
    private RichInputListOfValues saleUomBind;
    private RichInputText purPriceBind;
    private RichInputText salePriceBind;
    private RichPanelLabelAndMessage purPricePLAMBind;
    private RichPanelLabelAndMessage salePricePLAMBind;
    private RichSelectBooleanCheckbox cashPurFlg;
    private Integer count = -1;
    private UIXIterator coaIter;
    private RichInputListOfValues tranCoaNameBinding;


    private boolean disableItemCode = false;

    private String ItemCode = null;
    private RichInputText bindItemCode;
    private RichSelectBooleanCheckbox qcFlag;
    private RichSelectBooleanCheckbox exptTaxFlg;
    private RichSelectBooleanCheckbox consumableFlg;
    private RichSelectBooleanCheckbox serializedFlg;
    private RichCommandImageLink partialAddBtn;


    String hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
    String cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
    String orgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
    Integer slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
    private boolean disablePartial = false;
    private RichSelectOneChoice pickOrderBindVar;
    private RichPanelGroupLayout bindAltItmVar1;
    private RichPanelGroupLayout bindAltItmVar2;
    private RichOutputText outputMsgVar;
    private RichPopup attributePopUp;
    /*  private RichSelectOneChoice itmAttTypeVar;
    private RichSelectOneChoice itmAttValVar; */
    private RichInputText itemNameVar;

    private String itemDescBeforeEdit;
    private RichTable bindCoaTbl;
    private RichSelectOneChoice transItmAttTypeVar;
    private RichSelectOneChoice transItmAttValVar;
    private RichTable attTblVar;

    private Integer countAtt;
    private static ADFLogger adfLog = (ADFLogger) ADFLogger.createADFLogger(ItemProfile.class);
    private RichInputText fileBindName;
    private RichPopup orgReplicatePopup;
    private RichSelectOneChoice valuationBindVar;
    private RichSelectBooleanCheckbox applyTaxRl;
    private RichSelectBooleanCheckbox applyTaxRlBind;
    private RichInputListOfValues taxRlBind;
    private RichInputListOfValues itmAttTypeVar;
    private RichInputListOfValues itmAttValVar;
    private RichInputText imagePathBind;

    public ItemProfile() {

    }

    public static Object resolveElExp(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        Object msg = valueExp.getValue(elContext);
        return msg;
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
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

    public String addNewItem() {
        setMode("A");
        basicDtlTab.setDisclosed(true);
        AdfFacesContext.getCurrentInstance().getPageFlowScope().put("APP_ITEM_ID", null);
        return "Add";

    }

    public void umoBasicValueChange(ValueChangeEvent valueChangeEvent) {
        ItemProfileAMImpl am = (ItemProfileAMImpl) resolvElDC(amName);
        ViewObjectImpl itemPrf = am.getAppItmPrf();
        AppItmPrfVORowImpl rowImpl = (AppItmPrfVORowImpl) itemPrf.getCurrentRow();
        //System.out.println(rowImpl.getUomBasicId(valueChangeEvent.getNewValue().toString()));
        rowImpl.setUomBasic(rowImpl.getUomBasicId(valueChangeEvent.getNewValue().toString()));

    }

    public void uomPurValueChange(ValueChangeEvent valueChangeEvent) {
        ItemProfileAMImpl am = (ItemProfileAMImpl) resolvElDC(amName);
        ViewObjectImpl itemPrf = am.getAppItmPrf();
        AppItmPrfVORowImpl rowImpl = (AppItmPrfVORowImpl) itemPrf.getCurrentRow();
        //System.out.println(rowImpl.getUomBasicId(valueChangeEvent.getNewValue().toString()));
        rowImpl.setUomPur(rowImpl.getUomBasicId(valueChangeEvent.getNewValue().toString()));
    }

    public void uomSaleValueChange(ValueChangeEvent valueChangeEvent) {
        ItemProfileAMImpl am = (ItemProfileAMImpl) resolvElDC(amName);
        ViewObjectImpl itemPrf = am.getAppItmPrf();
        AppItmPrfVORowImpl rowImpl = (AppItmPrfVORowImpl) itemPrf.getCurrentRow();
        //System.out.println(rowImpl.getUomBasicId(valueChangeEvent.getNewValue().toString()));
        rowImpl.setUomSls(rowImpl.getUomBasicId(valueChangeEvent.getNewValue().toString()));
    }

    public void editItem(ActionEvent actionEvent) {
        /*     String OrgId=resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
               Integer SlocId=Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
               String CldId= resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
               String currUser = resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString();

        OperationBinding chkUsr = getBindings().getOperationBinding("pendingCheck");
        chkUsr.getParamsMap().put("SlocId", SlocId);
        chkUsr.getParamsMap().put("CldId", CldId);
        chkUsr.getParamsMap().put("OrgId", OrgId);
        chkUsr.getParamsMap().put("DocNo", 2028);
        chkUsr.execute();
        Object pendUser= (Object)chkUsr.getResult();

             System.out.println("PEND USR : "+pendUser);

        if(pendUser != null)
           {
               if(currUser.equals(pendUser.toString()))
                  {         setMode("E");
                            orgList.setDisabled(false);
                            ItemProfileAMImpl am = (ItemProfileAMImpl)resolvElDC(amName);
                            ViewObjectImpl itemPrf = am.getAppItmPrf();
                            setItemDescBeforeEdit(itemPrf.getCurrentRow().getAttribute("ItmDesc").toString());
                }
             else
               {
                           String msg2 = resolvEl("#{bundle['MSG.612']}").toString();
                           FacesMessage message2 = new FacesMessage(msg2);
                           message2.setSeverity(FacesMessage.SEVERITY_INFO);
                           FacesContext fc = FacesContext.getCurrentInstance();
                           fc.addMessage(null, message2);
                }
           } */

        setMode("E");
        orgList.setDisabled(false);
        ItemProfileAMImpl am = (ItemProfileAMImpl) resolvElDC(amName);
        ViewObjectImpl itemPrf = am.getAppItmPrf();
        setItemDescBeforeEdit(itemPrf.getCurrentRow().getAttribute("ItmDesc").toString());

    }

    public String cancelItem() {
        // System.out.println("Cancel Called");
        setMode("");
        setOrgBtn_Disable("M");
        setAltItmBtn_Disable("O");
        basicDtlTab.setDisclosed(true);
        orgList.setDisabled(true);
        //orgList.setDisabled(false);
        WareHouseMode = "V";
        return "backToSearch";
    }

    public String backTosearch() {
        setMode("");
        setOrgBtn_Disable("M");
        setAltItmBtn_Disable("O");
        basicDtlTab.setDisclosed(true);
        orgList.setDisabled(true);
        //orgList.setDisabled(false);
        WareHouseMode = "V";
        return "backToSearch";
    }

    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        // System.out.println("current facescontext="+fc);
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        // System.out.println("elContext="+elContext);
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        // System.out.println("value of "+data+" = "+valueExp.getValue(elContext));
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }


    public void saveItem(ActionEvent actionEvent) {
        // System.out.println("SAVE ITEM PROFILE : "+itemNameVar.getValue());
        //-------------------------Validation  of Pick Order  -----------------
        if (valuationBindVar.getValue() != null && pickOrderBindVar.getValue() != null) {
            System.out.println("in the Validation Block");
            Integer objVal = Integer.parseInt(valuationBindVar.getValue().toString());
            System.out.println("Valuation:" + objVal);
            Integer objPick = Integer.parseInt(pickOrderBindVar.getValue().toString());
            //  String pickClientId=pickOrderBind.getClientId();
            System.out.println("pick:" + objPick);


            if (objVal == 303 && objPick != 305) {
                System.out.println("in the LIFO Block..");
                System.out.println("pick Client Id:" + pickOrderBindVar.getClientId());
                FacesMessage message =
                    new FacesMessage(" If Valuation method is LIFO type then Pick Order must also be LIFO type ");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(pickOrderBindVar.getClientId(), message);
                return;
            }
            if (objVal == 304 && objPick != 306) {
                System.out.println("in the FIFO Block....");
                System.out.println("pick Client Id:" + pickOrderBindVar.getClientId());
                FacesMessage message =
                    new FacesMessage(" If Valuation method is FIFO type then Pick Order must also be FIFO type");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(pickOrderBindVar.getClientId(), message);
                return;
            }
            //                if(objVal!=303&& objPick ==305) {
            //                    System.out.println("in the LIFO Block..");
            //                    System.out.println("pick Client Id:"+pickOrderBindVar.getClientId());
            //                FacesMessage message = new FacesMessage(" If Pick Order is LIFO type then Valuation Order must also be LIFO type ");
            //                message.setSeverity(FacesMessage.SEVERITY_ERROR);
            //                FacesContext fc = FacesContext.getCurrentInstance();
            //                fc.addMessage(valuationBindVar.getClientId(), message);
            //                return;
            //                }
            //
            //            if(objVal!=304 && objPick ==306) {
            //                System.out.println("in the FIFO Block....");
            //                System.out.println("pick Client Id:"+pickOrderBindVar.getClientId());
            //                FacesMessage message = new FacesMessage(" If Pick order is FIFO type then Valuation method must also be FIFO type");
            //                message.setSeverity(FacesMessage.SEVERITY_ERROR);
            //                FacesContext fc = FacesContext.getCurrentInstance();
            //                fc.addMessage(valuationBindVar.getClientId(), message);
            //                return;
            //            }
        }
        //------------------------------END--------------------------------------

        ItemProfileAMImpl am = (ItemProfileAMImpl) resolvElDC(amName);
        int c = (int) am.getViewItemAccLinkLOV().getEstimatedRowCount();

        int count = 0;

        ViewObjectImpl itmPrf = am.getAppItmPrf();
        //     ViewObjectImpl itemCoa = am.getAppItmCoaVO();
        //     Row currRow = itemCoa.getCurrentRow();
        Row curritmprf = itmPrf.getCurrentRow();
        String uomId = curritmprf.getAttribute("UomBasic").toString();
        String uomBasicNm = curritmprf.getAttribute("UOMBasicTrans").toString();
        if (curritmprf.getAttribute("UomPur") == null) {
            curritmprf.setAttribute("UomPur", uomId);
            curritmprf.setAttribute("UOMPurTrans", uomBasicNm);
        }
        if (curritmprf.getAttribute("UomSls") == null) {
            curritmprf.setAttribute("UomSls", uomId);
            curritmprf.setAttribute("UOMSaleTrans", uomBasicNm);
        }
        String itmId = curritmprf.getAttribute("ItmId").toString();
        Integer P_SLOCID = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String P_ORGID = (String) resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String P_HO_ORGID = (String) resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String P_CLDID = (String) resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");


        if (itemNameVar.getValue() != null) {
            String msg3 = resolvEl("#{bundle['MSG.48']}").toString();
            OperationBinding itmAtt = getBindings().getOperationBinding("isItemNameDuplicate");
            itmAtt.getParamsMap().put("itemName", itemNameVar.getValue().toString());
            itmAtt.getParamsMap().put("previousVal", getItemDescBeforeEdit());
            itmAtt.execute();
            Boolean flg = (Boolean) itmAtt.getResult();
            if (flg) {
                FacesMessage message2 = new FacesMessage(msg3);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
                return;
            }
        }

        /*  Integer coaId = null;
        try {
            if (currRow.getAttribute("CoaId") != null) {
                coaId = Integer.parseInt(currRow.getAttribute("CoaId").toString());
            }
        } catch (NullPointerException NPE) {

        }
        if (coaId != null) {
            itemCoa.setRangeSize(-1);
            itemCoa.getAllRowsInRange();
            RowQualifier rowQualifier = new RowQualifier(itemCoa);
            // rowQualifier.setWhereClause(" OrgId = '"+P_ORGID+"' and SlocId="+P_SLOCID+" and CoaId="+coaId+" and ItmId= '"+itmId+"'" );
            rowQualifier.setWhereClause(" OrgId = '" + P_ORGID + "' and SlocId=" + P_SLOCID + " and ItmId= '" + itmId +"' and HoOrgId= '"+P_HO_ORGID+"' and CldId ='"+P_CLDID+"'");
            Row[] rows = itemCoa.getFilteredRows(rowQualifier);

        //    System.out.println("EXPRESSION -------" + rowQualifier.getExprStr());

      //      System.out.println("itemCoa  :: " +rows.length);


            if (rows.length > 0) {
                for (Row r : rows) {
                    Integer coa_Id = Integer.parseInt(r.getAttribute("CoaId").toString());
                    for (Row r1 : rows) {
                        Integer coa_Id1 = Integer.parseInt(r1.getAttribute("CoaId").toString());
                        if (coa_Id1.equals(coa_Id)) {
                            count = count + 1;
                        }
                    }
                }
            }


            if (count > c) {
                //count =0;
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resolveElExp("#{bundle['MSG.364']}").toString(), null);
                FacesContext.getCurrentInstance().addMessage(null, msg);
                altItmTabBind.setDisclosed(true);
            }

            rowQualifier.setWhereClause(null);
            ///change bl for coa duplicate
        }
        */ // if (count == c || count == 0) {

        String msg2 = resolvEl("#{bundle['MSG.1186']}").toString();

        OperationBinding op = getBindings().getOperationBinding("isPropertyCombinationValid");
        op.execute();
        Object obj = op.getResult();
        if (obj != null) {
            Boolean valid = (Boolean) obj;
            if (!valid) {
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
                return;
            }
        }


        Integer UsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String CldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        //get Wf Id

        String action = "I";
        String remark = "A";
        String WfNum = null;
        Integer level = 0;
        Integer res = -1;

        OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
        WfnoOp.getParamsMap().put("SlocId", SlocId);
        WfnoOp.getParamsMap().put("CldId", CldId);
        WfnoOp.getParamsMap().put("OrgId", OrgId);
        WfnoOp.getParamsMap().put("DocNo", 2028);
        WfnoOp.execute();

        //System.out.println("Bean Object : "+WfnoOp.getResult());

        if (WfnoOp.getErrors().size() == 0 && WfnoOp.getResult() != null) {
            WfNum = WfnoOp.getResult().toString();
        }

        if (WfNum != null) {
            //get user level

            OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
            usrLevelOp.getParamsMap().put("SlocId", SlocId);
            usrLevelOp.getParamsMap().put("CldId", CldId);
            usrLevelOp.getParamsMap().put("OrgId", OrgId);
            usrLevelOp.getParamsMap().put("UsrId", UsrId);
            usrLevelOp.getParamsMap().put("WfNo", WfNum);
            usrLevelOp.getParamsMap().put("DocNo", 2028);
            usrLevelOp.execute();
            level = -1;
            if (usrLevelOp.getErrors().size() == 0 && usrLevelOp.getResult() != null) {
                level = Integer.parseInt(usrLevelOp.getResult().toString());
            }

            if (level >= 0) {
                //insert into txn
                OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
                insTxnOp.getParamsMap().put("SlocId", SlocId);
                insTxnOp.getParamsMap().put("CldId", CldId);
                insTxnOp.getParamsMap().put("OrgId", OrgId);
                insTxnOp.getParamsMap().put("DocNo", 2028);
                insTxnOp.getParamsMap().put("WfNo", WfNum);
                insTxnOp.getParamsMap().put("usr_idFrm", UsrId);
                insTxnOp.getParamsMap().put("usr_idTo", UsrId);
                insTxnOp.getParamsMap().put("levelFrm", level);
                insTxnOp.getParamsMap().put("levelTo", level);
                insTxnOp.getParamsMap().put("action", action);
                insTxnOp.getParamsMap().put("remark", remark);
                insTxnOp.getParamsMap().put("amount", 0);
                insTxnOp.execute();

                if (insTxnOp.getResult() != null) {
                    res = Integer.parseInt(insTxnOp.getResult().toString());
                }


                BindingContext bc = BindingContext.getCurrent();
                BindingContainer bindings = bc.getCurrentBindingsEntry();
                OperationBinding save = bindings.getOperationBinding("Commit");
                save.execute();
                if (save.getErrors().size() == 0) {
                    setMode("V");
                    String massege = resolveElExp("#{bundle['MSG.89']}").toString();
                    String mass = massege.format(massege, "", "", "", "", "");
                    FacesMessage msg = new FacesMessage(mass);
                    msg.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext fctx = FacesContext.getCurrentInstance();
                    fctx.addMessage(null, msg);
                    orgList.setDisabled(true);
                    basicDtlTab.setDisclosed(true);
                    WareHouseMode = "V";
                }
            } else {
                FacesMessage message =
                    new FacesMessage("Something went wrong (User level in Workflow).Please Contact to ESS!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
        } else {
            FacesMessage message = new FacesMessage("Workflow not Created for Item Profile.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }


        /*     setMode("V");
            BindingContext bc = BindingContext.getCurrent();
            BindingContainer bindings = bc.getCurrentBindingsEntry();
            OperationBinding save = bindings.getOperationBinding("Commit");
            save.execute();
            String massege = resolveElExp("#{bundle['MSG.89']}").toString();
            String mass = massege.format(massege, "", "", "", "", "");
            FacesMessage msg = new FacesMessage(mass);
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fctx = FacesContext.getCurrentInstance();
            fctx.addMessage(null, msg);
            orgList.setDisabled(true);
            basicDtlTab.setDisclosed(true);
            WareHouseMode="V"; */
        //  }


    }

    public String saveAndForwardItmAction() {
        //-------------------------Validation  of Pick Order  -----------------
        System.out.println("in the Validation Block");
        Integer objVal = Integer.parseInt(valuationBindVar.getValue().toString());
        System.out.println("Valuation:" + objVal);
        Integer objPick = Integer.parseInt(pickOrderBindVar.getValue().toString());
        //  String pickClientId=pickOrderBind.getClientId();
        System.out.println("pick:" + objPick);


        if (objVal == 303 && objPick != 305) {
            System.out.println("in the LIFO Block..");
            System.out.println("pick Client Id:" + pickOrderBindVar.getClientId());
            FacesMessage message =
                new FacesMessage(" If Valuation method is LIFO type then Pick Order must also be LIFO type ");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(pickOrderBindVar.getClientId(), message);
            return null;
        }
        if (objVal == 304 && objPick != 306) {
            System.out.println("in the FIFO Block....");
            System.out.println("pick Client Id:" + pickOrderBindVar.getClientId());
            FacesMessage message =
                new FacesMessage(" If Valuation method is FIFO type then Pick Order must also be FIFO type");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(pickOrderBindVar.getClientId(), message);
            return null;
        }
        //------------------------------END--------------------------------------
        Integer UsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String CldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        ItemProfileAMImpl am = (ItemProfileAMImpl) resolvElDC(amName);
        ViewObjectImpl itmPrf = am.getAppItmPrf();
        Row curritmprf = itmPrf.getCurrentRow();
        String uomId = curritmprf.getAttribute("UomBasic").toString();
        String uomBasicNm = curritmprf.getAttribute("UOMBasicTrans").toString();
        if (curritmprf.getAttribute("UomPur") == null) {
            curritmprf.setAttribute("UomPur", uomId);
            curritmprf.setAttribute("UOMPurTrans", uomBasicNm);
        }
        if (curritmprf.getAttribute("UomSls") == null) {
            curritmprf.setAttribute("UomSls", uomId);
            curritmprf.setAttribute("UOMSaleTrans", uomBasicNm);
        }
        String msg2 = resolvEl("#{bundle['MSG.1186']}").toString();

        OperationBinding op = getBindings().getOperationBinding("isPropertyCombinationValid");
        op.execute();
        Object obj = op.getResult();
        if (obj != null) {
            Boolean valid = (Boolean) obj;
            if (!valid) {
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
                return null;
                // some comment
            }
        }


        String action = "I";
        String remark = "A";
        String WfNum = null;
        Integer level = 0;
        Integer res = -1;


        Integer pending = 0;
        OperationBinding chkUsr = getBindings().getOperationBinding("pendingCheck");
        chkUsr.getParamsMap().put("SlocId", SlocId);
        chkUsr.getParamsMap().put("CldId", CldId);
        chkUsr.getParamsMap().put("OrgId", OrgId);
        chkUsr.getParamsMap().put("DocNo", 2028);
        chkUsr.execute();


        //System.out.println(chkUsr.getResult() + " Check User");

        if (chkUsr.getResult() != null) {
            pending = Integer.parseInt(chkUsr.getResult().toString());
        }

        if (pending.compareTo(new Integer(-1)) == 0 || pending.compareTo(UsrId) == 0) {

            OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
            WfnoOp.getParamsMap().put("SlocId", SlocId);
            WfnoOp.getParamsMap().put("CldId", CldId);
            WfnoOp.getParamsMap().put("OrgId", OrgId);
            WfnoOp.getParamsMap().put("DocNo", 2028);
            WfnoOp.execute();
            if (WfnoOp.getErrors().size() == 0 && WfnoOp.getResult() != null) {
                WfNum = WfnoOp.getResult().toString();
            }
            if (WfNum != null) {

                //get user level

                OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
                usrLevelOp.getParamsMap().put("SlocId", SlocId);
                usrLevelOp.getParamsMap().put("CldId", CldId);
                usrLevelOp.getParamsMap().put("OrgId", OrgId);
                usrLevelOp.getParamsMap().put("UsrId", UsrId);
                usrLevelOp.getParamsMap().put("WfNo", WfNum);
                usrLevelOp.getParamsMap().put("DocNo", 2028);
                usrLevelOp.execute();
                level = -1;
                if (usrLevelOp.getErrors().size() == 0 && usrLevelOp.getResult() != null) {
                    level = Integer.parseInt(usrLevelOp.getResult().toString());
                }

                if (level >= 0) {
                    //insert into txn
                    OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
                    insTxnOp.getParamsMap().put("SlocId", SlocId);
                    insTxnOp.getParamsMap().put("CldId", CldId);
                    insTxnOp.getParamsMap().put("OrgId", OrgId);
                    insTxnOp.getParamsMap().put("DocNo", 2028);
                    insTxnOp.getParamsMap().put("WfNo", WfNum);
                    insTxnOp.getParamsMap().put("usr_idFrm", UsrId);
                    insTxnOp.getParamsMap().put("usr_idTo", UsrId);
                    insTxnOp.getParamsMap().put("levelFrm", level);
                    insTxnOp.getParamsMap().put("levelTo", level);
                    insTxnOp.getParamsMap().put("action", action);
                    insTxnOp.getParamsMap().put("remark", remark);
                    insTxnOp.getParamsMap().put("amount", 0);
                    insTxnOp.execute();

                    if (insTxnOp.getResult() != null) {
                        res = Integer.parseInt(insTxnOp.getResult().toString());
                    }


                    BindingContext bc = BindingContext.getCurrent();
                    BindingContainer bindings = bc.getCurrentBindingsEntry();
                    OperationBinding save = bindings.getOperationBinding("Commit");
                    save.execute();
                    orgList.setDisabled(true);
                    basicDtlTab.setDisclosed(true);
                    WareHouseMode = "V";
                    return "toWf";

                }
                //Check Pending
                else {
                    //    Something went wrong (User level in Workflow). Please Contact to ESS!

                    FacesMessage message =
                        new FacesMessage("Something went wrong (User level in Workflow). Please Contact to ESS!");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                    return null;


                    /*  String name="Anonymous";
                OperationBinding opusr=getBindings().getOperationBinding("getUserName");
                opusr.getParamsMap().put("usrId",pending);
                opusr.execute();
                if(opusr.getErrors().size()==0 && opusr.getResult()!=null)
                    name = (String)opusr.getResult();
                String msg="Document is Pending at ["+name+"]. you can't Forward this.";
                 FacesMessage message = new FacesMessage(msg);
                 message.setSeverity(FacesMessage.SEVERITY_ERROR);
                 FacesContext fc = FacesContext.getCurrentInstance();
                 fc.addMessage(null, message);
                return null;   */
                }
            } else {
                //  work flow not created for item profile.
                FacesMessage message = new FacesMessage("Workflow not Created for Item Profile.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
                return null;


                /*  FacesMessage message = new FacesMessage("Something went wrong (User level in Workflow). Please Contact to ESS!");
                  message.setSeverity(FacesMessage.SEVERITY_ERROR);
                  FacesContext fc = FacesContext.getCurrentInstance();
                  fc.addMessage(null, message);
                  return null; */
            }
        } else {

            String name = "Anonymous";
            OperationBinding opusr = getBindings().getOperationBinding("getUserName");
            opusr.getParamsMap().put("usrId", pending);
            opusr.execute();
            if (opusr.getErrors().size() == 0 && opusr.getResult() != null)
                name = (String) opusr.getResult();
            String msg = "Document is Pending at [" + name + "]. you can't Forward this.";
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            return null;
            /* FacesMessage message = new FacesMessage("Workflow not Created for Item Profile.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            return null; */
        }
    }


    public boolean isDocPending() {
        String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String CldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        Integer UsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());

        OperationBinding chkUsr = getBindings().getOperationBinding("pendingCheck");
        chkUsr.getParamsMap().put("SlocId", SlocId);
        chkUsr.getParamsMap().put("CldId", CldId);
        chkUsr.getParamsMap().put("OrgId", OrgId);
        chkUsr.getParamsMap().put("DocNo", 18529);
        chkUsr.execute();
        Integer pending = 0;
        if (chkUsr.getResult() != null) {
            pending = Integer.parseInt(chkUsr.getResult().toString());
        }

        if (pending == UsrId) {
            return true;
        }

        else {
            return false;
        }
    }

    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }

    public void actInactiveChange(ValueChangeEvent valueChangeEvent) {
        ItemProfileAMImpl am = (ItemProfileAMImpl) resolvElDC(amName);
        ViewObject itemPrf = am.getAppItmPrf();
        Row itemCurr = itemPrf.getCurrentRow();
        /* String itmId = (String)itemCurr.getAttribute("ItmId");
        System.out.println("---------"+itmId);
        BindingContext bc = BindingContext.getCurrent();
        BindingContainer bindings = bc.getCurrentBindingsEntry();
        OperationBinding check = bindings.getOperationBinding("isItemDeletable");
        check.getParamsMap().put("itemId", itmId); */
        /*  String isDeletable = "N";
        try {
          isDeletable = (String)check.execute();
        } catch (NullPointerException e) {
            isDeletable ="N";
            e.printStackTrace();
        } */
        //String isDeletable = (String)check.execute();
        //System.out.println("--------act --- "+isDeletable);
        // if (isDeletable.equals("Y")) {
        if (valueChangeEvent.getNewValue().equals(true)) {
            itemCurr.setAttribute("InactvDt", null);
            itemCurr.setAttribute("InactvResn", "");
        } else {
            itemCurr.setAttribute("InactvDt", getCurrentDate());
        }
        /*  }else{
        String msg = "Item can not be inactive";
        FacesMessage messg = new FacesMessage(msg);
        messg.setSeverity(messg.SEVERITY_ERROR);
        FacesContext.getCurrentInstance().addMessage(itmActvBind.getClientId(), messg);
    } */
    }

    public void setInactiveDate(RichInputDate inactiveDate) {
        this.inactiveDate = inactiveDate;
    }

    public RichInputDate getInactiveDate() {
        return inactiveDate;
    }

    public void setInactiveReason(RichInputText inactiveReason) {
        this.inactiveReason = inactiveReason;
    }

    public RichInputText getInactiveReason() {
        return inactiveReason;
    }

    public Date getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        java.util.Date date = new java.util.Date();
        String dateStr = dateFormat.format(date);
        try {
            java.util.Date date2 = dateFormat.parse(dateStr);
            java.sql.Date sqldate = new java.sql.Date(date2.getTime());
            today = new oracle.jbo.domain.Date(sqldate);
            // System.out.println("Current Date Time : jbo " + today);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return today;
    }

    public void altItemValueChange(ValueChangeEvent valueChangeEvent) {
        ItemProfileAMImpl am = (ItemProfileAMImpl) resolvElDC(amName);
        ViewObjectImpl itemAlt = am.getAppAltItmVO();
        AppAltItmVORowImpl rowAltImpl = (AppAltItmVORowImpl) itemAlt.getCurrentRow();
        if (rowAltImpl.getItemId(valueChangeEvent.getNewValue().toString()) != null) {
            //System.out.println(rowAltImpl.getItemId(valueChangeEvent.getNewValue().toString()));
            rowAltImpl.setAltItmId(rowAltImpl.getItemId(valueChangeEvent.getNewValue().toString()));
        }
    }

    public void activeAltItem(ValueChangeEvent valueChangeEvent) {
        //System.out.println(valueChangeEvent.getNewValue());
        ItemProfileAMImpl am = (ItemProfileAMImpl) resolvElDC(amName);
        ViewObject itemAlt = am.getAppAltItmVO();
        Row rowAltImpl = itemAlt.getCurrentRow();
        if (valueChangeEvent.getNewValue().equals(true)) {
            //    altInactDate.setValue("");
            //    altInactReason.setValue("");
            rowAltImpl.setAttribute("InactvDt", null);
            rowAltImpl.setAttribute("InactvResn", null);
            altItmInactiveReason.setValue("");

        } else {
            // altInactDate.setValue(getCurrentDate());
            rowAltImpl.setAttribute("InactvDt", getCurrentDate());
        }
    }


    public void addAltItem(ActionEvent actionEvent) {
        ItemProfileAMImpl am = (ItemProfileAMImpl) resolvElDC(amName);
        /*     Integer priority = 0;

        String query = "select nvl(max(alt_itm_prty),0) from app$alt$itm";
        DBTransaction dbt = am.getDBTransaction();


        try {
            ResultSet rs = dbt.createStatement(0).executeQuery(query);
            if (rs.next()) {
                priority = Integer.parseInt(rs.getObject(1).toString())+1;
            }
            rs.close();
        } catch (SQLException sqlerr) {
            throw new JboException(sqlerr);
        }
        System.out.println(priority);*/
        Integer P_SLOCID = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String P_ORGID = (String) resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String P_HO_ORGID = (String) resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String P_CLDID = (String) resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");

        BindingContext bindingctx = BindingContext.getCurrent();
        BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
        OperationBinding createOpBAddress = bindings.getOperationBinding("CreateInsert");
        createOpBAddress.execute();
        ViewObject itemAlt = am.getAppAltItmVO();
        Row rowAltImpl = itemAlt.getCurrentRow();
        rowAltImpl.setAttribute("SlocId", P_SLOCID);
        rowAltImpl.setAttribute("OrgId", P_ORGID);
        rowAltImpl.setAttribute("CldId", P_CLDID);
        rowAltImpl.setAttribute("HoOrgId", P_HO_ORGID);
        rowAltImpl.setAttribute("ItmId", am.getAppItmPrf().getCurrentRow().getAttribute("ItmId").toString());
        rowAltImpl.setAttribute("AltItmPrty", getMaxNo());
        this.setAltItmBtn_Disable("P");
    }

    public Integer getMaxNo() {
        /** Create iterator for tvou lines */
        ItemProfileAMImpl am = (ItemProfileAMImpl) resolvElDC(amName);
        ViewObject vw = am.getAppAltItmVO();
        RowSetIterator rSetIter = vw.createRowSetIterator(null);
        int max = 0;
        Row cRow = null;
        //System.out.println(vw.getRowCount());
        /** Run loop for all rows of altItem and set incremented value to variable max */

        while (rSetIter.hasNext()) {
            cRow = rSetIter.next();
            //    System.out.println("row " + cRow.getAttribute("AltItmPrty"));
            if (Integer.parseInt(cRow.getAttribute("AltItmPrty").toString()) >= max) {
                max = Integer.parseInt(cRow.getAttribute("AltItmPrty").toString()) + 1;
            }
        }
        rSetIter.closeRowSetIterator();
        /** Return incremented slNo value */
        return max;
    }

    public void saveAltItm(ActionEvent actionEvent) {

        /*  BindingContext bindingctx = BindingContext.getCurrent();
        BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        operationBinding.execute();
        */
        this.setAltItmBtn_Disable("O");
    }

    public void altItemlovopen(LaunchPopupEvent launchPopupEvent) {
        ItemProfileAMImpl am = (ItemProfileAMImpl) resolvElDC(amName);
        ViewObjectImpl itemAlt = am.getAppAltItmVO();
        AppAltItmVORowImpl rowAltImpl = (AppAltItmVORowImpl) itemAlt.getCurrentRow();
        //rowAltImpl.reload();

    }

    public void addOrgWh(ActionEvent actionEvent) {
        ItemProfileAMImpl am = (ItemProfileAMImpl) resolvElDC(amName);
        this.setOrgBtn_Disable("N");
        orgList.setDisabled(false);
        //  whList.setDisabled(false);
        /*  am.getViewWHLov().setWhereClause("WH_ID not in (select wh_id from app$org$itm where itm_id = '" +
                                         am.getAppItmPrf().getCurrentRow().getAttribute("ItmId").toString() + "')");
        */WareHouseMode = "A";
    }

    public void addWarehouse(ActionEvent actionEvent) { //save button
        /*   int count = 0;
        int countSave = 0;
        int countDup = 0;
        try {
            ItemProfileAMImpl am = (ItemProfileAMImpl)resolvElDC(amName);
            ViewObject appOrgItm = am.getAppOrgItm();
            ViewObject orgLOV = am.getViewOrgLov();
            RowSetIterator rit = appOrgItm.createRowSetIterator(null); //treetable
            BindingContainer bc = BindingContext.getCurrent().getCurrentBindingsEntry();
            JUCtrlListBinding listBind = (JUCtrlListBinding)bc.get("ViewWHLov");

            Object[] str = listBind.getSelectedValues();
            System.out.println("list bind" + str.length);
            if (!listBind.isEmpty()) { //if any value selected
                for (int i = 0; i < str.length; i++) { // iterate the array of selected vals
                    System.out.println("for loop " + i + " " + str[i]);

                    while (rit.hasNext()) { //iterate the tree
                        Row thisRow = rit.next();
                        System.out.println(thisRow.getAttribute("OrgId") + "---while loop" +
                                           orgLOV.getCurrentRow().getAttribute("OrgTemp"));

                        if (thisRow.getAttribute("OrgId").equals(orgLOV.getCurrentRow().getAttribute("OrgTemp")) &&
                            thisRow.getAttribute("WhId").equals(str[i])) {
                            System.out.println(" if");
                            String msg2 = "Some of the Warehouses are already added";
                            FacesMessage message2 = new FacesMessage("Unique", msg2);
                            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                            //throw new ValidatorException(message2);

                            FacesContext.getCurrentInstance().addMessage("", message2);
                            count++;
                            countDup = 1;
                        }
                    }
                }
                rit.closeRowSetIterator();
                if (count == 0 && countDup == 0) {
                    for (int i = 0; i < str.length; i++) {
                        countSave = 1;
                        System.out.println(" after if " + i + " " + str[i]);
                        Row newRow = appOrgItm.createRow();
                        newRow.setAttribute("OrgId", orgLOV.getCurrentRow().getAttribute("OrgTemp"));
                        newRow.setAttribute("WhId", str[i]);
                        appOrgItm.insertRow(newRow);
                        //  listBind.clearSelectedIndices();
                    }
                }
            }

            System.out.println("count-----------" + count);
            System.out.println("countSave     " + countSave);
            orgLOV.getCurrentRow().setAttribute("OrgTemp", "");
            orgLOV.executeQuery();
            listBind.clearSelectedIndices();
            if (countSave == 1) {
                BindingContext bindingctx = BindingContext.getCurrent();
                BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
                OperationBinding createOpBAddress = bindings.getOperationBinding("Commit");
                createOpBAddress.execute();

                am.getViewOrgItem().executeQuery();
                am.getViewOrgWh().executeQuery();
                appOrgItm.executeQuery();
            }

            AdfFacesContext.getCurrentInstance().addPartialTarget(orgWhTree);

            orgList.setDisabled(true);
            whList.setDisabled(true);
            this.setOrgBtn_Disable("M");
            WareHouseMode = "V";
        } catch (NullPointerException NPE) {

        } */
        ItemProfileAMImpl am = (ItemProfileAMImpl) resolvElDC(amName);
        ViewObjectImpl itmOrgVo = am.getAppItmOrgVO1();
        ViewObject appItmPrf = am.getAppItmPrf();
        Row curItm = appItmPrf.getCurrentRow();
        ViewObject orgLOV = am.getViewOrgLov();

        String P_ORGID = (String) resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String P_HOORGID = (String) resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String P_CLDID = (String) resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer P_SLOCID = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));


        ViewObject accLinkLOV = am.getViewItemAccLinkLOV();
        ViewObjectImpl appItmCoa = am.getAppItmCoaVO();
        ViewObjectImpl grpCoaVo = am.getGrpCoa1();

        ViewObjectImpl trnsOrgVo = am.gettransOrg1();

        if (curItm != null && trnsOrgVo.getCurrentRow().getAttribute("OrgId") != null &&
            trnsOrgVo.getCurrentRow().getAttribute("OrgId").toString().length() > 0) {
            Row rows[] = null;
            RowQualifier rq = new RowQualifier(itmOrgVo);
            rq.setWhereClause("SlocId = " + P_SLOCID + " AND CldId ='" + P_CLDID + "' AND HoOrgId ='" + P_HOORGID +
                              "' AND OrgId = '" + trnsOrgVo.getCurrentRow().getAttribute("OrgId").toString() +
                              "' AND ItmId='" + curItm.getAttribute("ItmId") + "'");
            rows = itmOrgVo.getFilteredRows(rq);

            adfLog.info("String data is " + rows.length + " expression is " + rq.getExprStr());
            if (rows != null && rows.length > 0) {
                String msg = resolveElExp("#{bundle['LBL.2299']}").toString();
                FacesMessage message = new FacesMessage(msg);
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else { //TaxRuleId
                Row newRow = itmOrgVo.createRow();
                newRow.setAttribute("OrgId", trnsOrgVo.getCurrentRow().getAttribute("OrgId"));
                newRow.setAttribute("TaxRuleId", trnsOrgVo.getCurrentRow().getAttribute("TransTaxRule"));
                itmOrgVo.insertRow(newRow);
                // orgList.setDisabled(true);


                // Add COA Against Organisation
                //Date : 17-Apr-2014
                /*     if(curItm.getAttribute("GrpId") != null)
               {
                Row tolRws[] = accLinkLOV.getFilteredRows("AttTypeId", Integer.valueOf(31));
                for(Row or:tolRws)
                   {  //orgLOV.getCurrentRow().getAttribute("OrgTemp").toString();

                       RowQualifier rwq = new RowQualifier(grpCoaVo);
                       rwq.setWhereClause("GrpId = '"+curItm.getAttribute("GrpId")+"' AND CoaFor = "+or.getAttribute("AttId")+" AND SlocId = "+curItm.getAttribute("SlocId")+" AND CldId = '"+curItm.getAttribute("CldId")+"' AND HoOrgId = '"+curItm.getAttribute("HoOrgId")+"'");
                       Row r[]=grpCoaVo.getFilteredRows(rwq);
                       Row nwRow = appItmCoa.createRow();
                       nwRow.setAttribute("SlocId",P_SLOCID);
                       nwRow.setAttribute("CldId",P_CLDID);
                       nwRow.setAttribute("HoOrgId",P_HOORGID);
                       nwRow.setAttribute("ItmId", curItm.getAttribute("ItmId"));
                       nwRow.setAttribute("CoaFor",or.getAttribute("AttId"));
                       nwRow.setAttribute("OrgId",trnsOrgVo.getCurrentRow().getAttribute("OrgId"));
                     if(r != null && r.length == 1)
                         {nwRow.setAttribute("CoaId",r[0].getAttribute("CoaId"));}

                       appItmCoa.insertRow(nwRow);
                    }
               } */
                am.getDBTransaction().postChanges();
                String mass = resolveElExp("#{bundle['LBL.2947']}").toString(); //"Organisation COA Added Successfully";
                FacesMessage msg = new FacesMessage(mass);
                msg.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fctx = FacesContext.getCurrentInstance();
                fctx.addMessage(null, msg);

                orgList.setDisabled(false);
                this.setOrgBtn_Disable("M");
                itmOrgVo.executeQuery();
                WareHouseMode = "V";
            }

        } else {
            adfLog.info("current organization is null");
        }
        // BindingContext bindingctx = BindingContext.getCurrent();
        //  BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
        // OperationBinding createOpBAddress = bindings.getOperationBinding("Commit");
        // createOpBAddress.execute();
    }


    public void setOrgWhTree(RichTreeTable orgWhTree) {
        this.orgWhTree = orgWhTree;
    }

    public RichTreeTable getOrgWhTree() {
        return orgWhTree;
    }


    public void organisationChange(ValueChangeEvent valueChangeEvent) {
        BindingContainer bc = BindingContext.getCurrent().getCurrentBindingsEntry();
        adfLog.info("value change event value is " + valueChangeEvent.getNewValue());
        //   JUCtrlListBinding listBind = (JUCtrlListBinding)bc.get("ViewWHLov");

        //  listBind.clearSelectedIndices();
    }

    public void addItemCoa(ActionEvent actionEvent) {
        BindingContext bindingctx = BindingContext.getCurrent();
        BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
        OperationBinding createOpBAddress = bindings.getOperationBinding("Createwithparameters");
        ItemProfileAMImpl am = (ItemProfileAMImpl) resolvElDC(amName);
        ViewObject appItmCoa = am.getAppItmCoaVO();
        ViewObject accLinkLOV = am.getViewItemAccLinkLOV();

        ViewObject ItmPrf = am.getAppItmPrf();
        Row currItmRow = ItmPrf.getCurrentRow();
        String P_ORGID = (String) resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        AppItmOrgVOImpl ItmOrgVo = am.getAppItmOrgVO1();
        RowQualifier rq = new RowQualifier(ItmOrgVo);

        rq.setWhereClause("SlocId = " + currItmRow.getAttribute("SlocId") + " AND CldId = '" +
                          currItmRow.getAttribute("CldId") + "' AND HoOrgId = '" + currItmRow.getAttribute("HoOrgId") +
                          "' AND ItmId = '" + currItmRow.getAttribute("ItmId") + "' AND OrgId = '" + P_ORGID + "'");
        Row rows[] = ItmOrgVo.getFilteredRows(rq);


        if (rows != null && rows.length <= 0) {
            Row r = ItmOrgVo.createRow();
            r.setAttribute("OrgId", P_ORGID);
            ItmOrgVo.executeQuery();
        }


        RowSetIterator accLinkIterator = accLinkLOV.createRowSetIterator(null);
        int i = 0;

        while (accLinkIterator.hasNext()) {
            Row linkRow = accLinkIterator.next();
            createOpBAddress.execute();

            Row currentRow = appItmCoa.getCurrentRow();
            //System.out.println("CurrentRow="+currentRow);
            //System.out.println("Link row="+linkRow);
            //System.out.println("Link row att="+linkRow.getAttribute("AttId"));
            currentRow.setAttribute("CoaFor", linkRow.getAttribute("AttId"));
            currentRow.setAttribute("SlocId", currItmRow.getAttribute("SlocId"));
            currentRow.setAttribute("CldId", currItmRow.getAttribute("CldId"));
            currentRow.setAttribute("HoOrgId", currItmRow.getAttribute("HoOrgId"));
            currentRow.setAttribute("ItmId", currItmRow.getAttribute("ItmId"));
            /*  Row newRow = appItmCoa.createRow();
            newRow.setAttribute("SlocId", currItmRow.getAttribute("SlocId"));
            newRow.setAttribute("CldId", currItmRow.getAttribute("CldId"));
            newRow.setAttribute("HoOrgId", currItmRow.getAttribute("HoOrgId"));
            newRow.setAttribute("ItmId", currItmRow.getAttribute("ItmId"));
            newRow.setAttribute("CoaFor",linkRow.getAttribute("AttId"));
            appItmCoa.insertRow(newRow);

            System.out.println("Insert New Row");
          */
        }
        accLinkIterator.closeRowSetIterator();
        appItmCoa.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindCoaTbl);
    }

    public void saveCOAItem(ActionEvent actionEvent) {
        //itemCOA.setReadOnly(true);
        /* BindingContext bc = BindingContext.getCurrent();
        BindingContainer bindings = bc.getCurrentBindingsEntry();
        OperationBinding save = bindings.getOperationBinding("Commit");
        save.execute(); */
        ItemProfileAMImpl am = (ItemProfileAMImpl) resolvElDC(amName);
        ViewObjectImpl coaVo = am.getAppItmCoaVO();
        Row[] rr = coaVo.getAllRowsInRange();
        for (Row r : rr) {
            r.remove();
        }
    }

    public List grpAutoSuggest(String string) {
        //get access to the binding context and binding container at runtime
        BindingContext bctx = BindingContext.getCurrent();
        BindingContainer bindings = bctx.getCurrentBindingsEntry();
        //set the bind variable value that is used to filter the View Object
        ////query of the suggest list. The View Object instance has a View
        ////Criteria assigned
        OperationBinding setVariable = (OperationBinding) bindings.get("setSuggestNmBind");
        setVariable.getParamsMap().put("value", string);
        setVariable.execute();
        //the data in the suggest list is queried by a tree binding.
        JUCtrlHierBinding hierBinding = (JUCtrlHierBinding) bindings.get("ViewItemGrpLOV");
        //re-query the list based on the new bind variable values
        hierBinding.executeQuery();
        //The rangeSet, the list of queries entries, is of type        JUCtrlValueBndingRef.
        List<JUCtrlValueBindingRef> displayDataList = hierBinding.getRangeSet();
        ArrayList<SelectItem> selectItems = new ArrayList<SelectItem>();
        for (JUCtrlValueBindingRef displayData : displayDataList) {
            Row rw = displayData.getRow();
            //populate the SelectItem list
            selectItems.add(new SelectItem((String) rw.getAttribute("GrpNm"), (String) rw.getAttribute("GrpNm")));
        }
        return selectItems;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String modeGet() {
        return (String) resolveElExp("#{pageFlowScope.PARAM_ITM_MODE}");
    }

    public String getMode() {
        if (mode.equals("")) {
            return modeGet();
        } else {
            return mode;
        }
    }

    public void setOrgList(RichSelectOneChoice orgList) {
        this.orgList = orgList;
    }

    public RichSelectOneChoice getOrgList() {
        return orgList;
    }

    public void setWhList(RichSelectManyCheckbox whList) {
        this.whList = whList;
    }

    public RichSelectManyCheckbox getWhList() {
        return whList;
    }

    public void setItemCOA(RichInputText itemCOA) {
        this.itemCOA = itemCOA;
    }

    public RichInputText getItemCOA() {
        return itemCOA;
    }


    public void setPanelTab(RichPanelTabbed panelTab) {
        this.panelTab = panelTab;
    }

    public RichPanelTabbed getPanelTab() {
        return panelTab;
    }

    public void itemValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && !this.mode.equals("E")) {
            OperationBinding binding = getBindings().getOperationBinding("isDuplicateItmId");
            binding.getParamsMap().put("name", object.toString());
            Object ob = binding.execute();
            if (ob != null && ob.equals(true)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate Item Code !!",
                                                              null));
            }
        }
    }

    public void srveItmFlgChange(ValueChangeEvent valueChangeEvent) {
        /*  if (valueChangeEvent.getNewValue().equals(true)) {
            stockableFlg.setValue(false);
        }
        if (valueChangeEvent.getNewValue().equals(false)) {
            stockableFlg.setValue(true);
        } */
        // System.out.println("Srv Itm :" + valueChangeEvent.getNewValue());
        if (valueChangeEvent.getNewValue().equals(true)) {
            stockableFlg.setValue(false);
            consumableFlg.setValue(false);
        }
        /*   if (valueChangeEvent.getNewValue().equals(false)) {
                   stockableFlg.setValue(false);
               }  */
    }

    public void stockableFlgChange(ValueChangeEvent valueChangeEvent) {
        // System.out.println("Stock Itm :" + valueChangeEvent.getNewValue());
        if (valueChangeEvent.getNewValue().equals(true)) {
            serviceFlg.setValue(false);
            //consumableFlg.setValue(true);

        }
        /*  if (valueChangeEvent.getNewValue().equals(false)) {
            serviceFlg.setValue(true);
        } */
    }

    public void setMinStock(RichInputText minStock) {
        this.minStock = minStock;
    }

    public RichInputText getMinStock() {
        return minStock;
    }

    public void setSafeStock(RichInputText safeStock) {
        this.safeStock = safeStock;
    }

    public RichInputText getSafeStock() {
        return safeStock;
    }

    public void minStockValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String msg = resolveElExp("#{bundle['MSG.70']}").toString();
            FacesMessage message2 = new FacesMessage("Value", msg);
            message2.setSeverity(FacesMessage.SEVERITY_WARN);
            Number objectNew = (Number) object;
            Number zero = new Number(0);
            if (objectNew.compareTo(zero) == -1) {
                throw new ValidatorException(message2);
            }
        }
    }

    public void maxStockValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String msg = resolveElExp("#{bundle['MSG.68']}").toString();
            FacesMessage message2 = new FacesMessage("Value", msg);
            message2.setSeverity(FacesMessage.SEVERITY_WARN);
            Number objectNew = (Number) object;
            Number minnStock = (Number) minStock.getValue();
            //  System.out.println("min " + minnStock);
            /* if (Integer.parseInt(object.toString()) <= Integer.parseInt(minStock.getValue().toString())) {
            throw new ValidatorException(message2);
        } */
            if (minnStock != null) {
                if (objectNew.compareTo(minnStock) == -1 || objectNew.compareTo(minnStock) == 0) {
                    throw new ValidatorException(message2);
                }
            }
            Number zero = new Number(0);
            if (objectNew.compareTo(zero) == -1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolveElExp("#{bundle['MSG.357']}").toString(), null));
            }
        }
    }

    public void safeStockValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String msg = resolveElExp("#{bundle['LBL.1397']}").toString();
            FacesMessage message2 = new FacesMessage("Value", msg);
            message2.setSeverity(FacesMessage.SEVERITY_WARN);
            Number objectNew = (Number) object;
            Number minnStock = (Number) minStock.getValue();
            Number maxxStock = (Number) maxStock.getValue();
            //System.out.println("min " + minnStock);
            /*  if (Integer.parseInt(object.toString()) <= Integer.parseInt(minStock.getValue().toString())) {
            throw new ValidatorException(message2);
        } */
            if (minnStock != null) {
                if (objectNew.compareTo(minnStock) < 0) {
                    throw new ValidatorException(message2);
                }
            }
            if (maxxStock != null) {
                if (objectNew.compareTo(maxxStock) >= 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  resolveElExp("#{bundle['MSG.356']}").toString(),
                                                                  null));
                }
            }
            Number zero = new Number(0);
            if (objectNew.compareTo(zero) == -1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolveElExp("#{bundle['MSG.357']}").toString(), null));
            }
        }
    }

    public void reorderLvlValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null) {
            String msg = resolveElExp("#{bundle['MSG.355']}").toString();
            FacesMessage message2 = new FacesMessage("Value", msg);
            message2.setSeverity(FacesMessage.SEVERITY_WARN);
            Number objectNew = (Number) object;
            Number minnStock = (Number) minStock.getValue();
            Number safeStocks = (Number) safeStock.getValue();
            Number maxStocks = (Number) maxStock.getValue();
            //   System.out.println("min " + minnStock + "safe " + safeStocks + "maxStocks " + maxStocks);
            /*  if (Integer.parseInt(object.toString()) <= Integer.parseInt(minStock.getValue().toString()) ||
            Integer.parseInt(object.toString()) <= Integer.parseInt(safeStock.getValue().toString())) {
            throw new ValidatorException(message2);
        } */
            if (minnStock != null && safeStocks != null) {
                if (objectNew.compareTo(safeStocks) == -1) { //
                    throw new ValidatorException(message2);
                }
            }
            if (safeStocks != null && minnStock == null) {
                if (objectNew.compareTo(safeStocks) == -1) {
                    throw new ValidatorException(message2);
                }
            }
            if (safeStocks == null && minnStock != null) {
                if (objectNew.compareTo(minnStock) == -1) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  resolveElExp("#{bundle['MSG.358']}").toString(),
                                                                  null));
                }
            }
            if (maxStocks != null) {
                if (objectNew.compareTo(maxStocks) >= 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  resolveElExp("#{bundle['MSG.359']}").toString(),
                                                                  null));
                }
            }
            Number zero = new Number(0);
            if (objectNew.compareTo(zero) == -1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolveElExp("#{bundle['MSG.357']}").toString(), null));
            }
        }
    }

    public void setStockableFlg(RichSelectBooleanCheckbox stockableFlg) {
        this.stockableFlg = stockableFlg;
    }

    public RichSelectBooleanCheckbox getStockableFlg() {
        return stockableFlg;
    }

    public void setServiceFlg(RichSelectBooleanCheckbox serviceFlg) {
        this.serviceFlg = serviceFlg;
    }

    public RichSelectBooleanCheckbox getServiceFlg() {
        return serviceFlg;
    }

    public void basicPriceValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String msg = resolveElExp("#{bundle['MSG.6']}").toString();
        FacesMessage message2 = new FacesMessage("Value", msg);
        message2.setSeverity(FacesMessage.SEVERITY_WARN);
        Number objectNew = (Number) object;
        Number zero = new Number(0);
        // System.out.println(objectNew);
        if (objectNew == null) {
            throw new ValidatorException(message2);
        } else if (objectNew.compareTo(zero) == -1) {
            throw new ValidatorException(message2);
        }

        FacesMessage message = new FacesMessage("Value", resolveElExp("#{bundle['MSG.57']}").toString());
        message.setSeverity(FacesMessage.SEVERITY_WARN);

        boolean valid = isPrecisionValid(26, 6, objectNew);
        if (!valid) {
            throw new ValidatorException(message);
        }

    }

    public void setGrpvalue(String selected) {
        //System.out.println("Setting group value");
        ItemProfileAMImpl am = (ItemProfileAMImpl) resolvElDC(amName);
        ViewObject appItmPrf = am.getAppItmPrf();
        ViewItemGrpLOVImpl grpLOV = am.getViewItemGrpLOV();
        Integer slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        String cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        String orgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        grpLOV.setWhereClause("GRP_ID = '" + selected + "' and CLD_ID='" + cldId + "' and SLOC_ID=" + slocid +
                              " and HO_ORG_ID='" + hoOrgId + "'");
        grpLOV.executeQuery();
        // System.out.println("Estimated rows in grp="+grpLOV.getEstimatedRowCount());
        if (grpLOV.getRowCount() > 0) {
            Row currentRow = appItmPrf.getCurrentRow();
            currentRow.setAttribute("GrpId", selected);
            AdfFacesContext.getCurrentInstance().addPartialTarget(groupName);

        } else {
            String msg = resolveElExp("#{bundle['MSG.369']}").toString();
            FacesMessage messg = new FacesMessage(msg);
            messg.setSeverity(messg.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, messg);
        }
        grpLOV.setWhereClause(" ");
        grpLOV.executeQuery();
    }


    public void setGroupName(RichInputText groupName) {
        this.groupName = groupName;
    }

    public RichInputText getGroupName() {
        return groupName;
    }

    public String getGrpPath() {
        ItemProfileAMImpl am = (ItemProfileAMImpl) resolvElDC(amName);
        ViewObject appItmPrf = am.getAppItmPrf();
        Row currentRow = appItmPrf.getCurrentRow();
        Integer slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        String cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();

        adfLog.info("gro------" + currentRow.getAttribute("GrpId"));
        if (currentRow != null)
            if (currentRow.getAttribute("GrpId") != null) {
                Object attribute = currentRow.getAttribute("GrpId");
                BindingContext bc = BindingContext.getCurrent();
                BindingContainer bindings = bc.getCurrentBindingsEntry();
                OperationBinding getValue = bindings.getOperationBinding("getGroupPath");
                getValue.getParamsMap().put("SlocId", slocid);
                getValue.getParamsMap().put("CldId", cldId);
                getValue.getParamsMap().put("hoOrgId", hoOrgId);
                getValue.getParamsMap().put("groupId", attribute);

                String path = (String) getValue.execute();
                adfLog.info("grp path  " + path);
                return path;
            }
        return " Group Not Selected ";
    }

    public String getAttributePath() {
        ItemProfileAMImpl am = (ItemProfileAMImpl) resolvElDC(amName);
        ViewObject appItmPrf = am.getAppItmPrf();
        Row currentRow = appItmPrf.getCurrentRow();
        Integer slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        String cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();

        //  System.out.println("gro------" + currentRow.getAttribute("GrpId"));
        if (currentRow != null)
            if (currentRow.getAttribute("ItmId") != null) {
                Object attribute = currentRow.getAttribute("ItmId");
                OperationBinding getValue = getBindings().getOperationBinding("getAttributePath");
                getValue.getParamsMap().put("SlocId", slocid);
                getValue.getParamsMap().put("CldId", cldId);
                getValue.getParamsMap().put("hoOrgId", hoOrgId);
                getValue.getParamsMap().put("itmId", attribute);
                getValue.execute();
                if (getValue.getResult() != null) {
                    return getValue.getResult().toString();
                }
                // String path = (String)getValue.execute();

            }
        return " None ";
    }


    public void uomBasicChange(ValueChangeEvent valueChangeEvent) {
        ItemProfileAMImpl am = (ItemProfileAMImpl) resolvElDC(amName);
        ViewObject itemPrf = am.getAppItmPrf();
        AppItmPrfVORowImpl row = (AppItmPrfVORowImpl) itemPrf.getCurrentRow();
        String uom = valueChangeEvent.getNewValue().toString();
        String basicId = row.getUomBasicId(uom);
        //System.out.println(uom + " " + basicId);
        row.setAttribute("UOMPurTrans", uom);
        row.setAttribute("UOMSaleTrans", uom);
        row.setAttribute("UomPur", basicId);
        row.setAttribute("UomSls", basicId);
        //  System.out.println("pur---" + row.getAttribute("UomPur"));
        AdfFacesContext.getCurrentInstance().addPartialTarget(purUomBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(saleUomBind);
    }

    public void basicPriceChange(ValueChangeEvent valueChangeEvent) {
        ItemProfileAMImpl am = (ItemProfileAMImpl) resolvElDC(amName);
        ViewObject itemPrf = am.getAppItmPrf();
        Row row = itemPrf.getCurrentRow();
        Number price = new Number(0);
        Number zero = new Number(0);
        if (valueChangeEvent.getNewValue() != null) {
            price = (Number) valueChangeEvent.getNewValue();
        }
        // System.out.println(price);
        //
        row.setAttribute("PricePur", price);
        row.setAttribute("PriceSls", price);
        AdfFacesContext.getCurrentInstance().addPartialTarget(purPriceBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(salePriceBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(purPricePLAMBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(salePricePLAMBind);
    }

    public void capitalGlFlgVCE(ValueChangeEvent valueChangeEvent) {

        if (valueChangeEvent.getNewValue().equals(true)) {
            slsItmFlg.setValue(false);
            purItmFlg.setValue(false);
            wipItmFlg.setValue(false);
            cashPurFlg.setValue(false);
            ItemProfileAMImpl am = (ItemProfileAMImpl) resolvElDC(amName);
            ViewObjectImpl coaVo = am.getAppItmCoaVO();
            Row[] r = coaVo.getFilteredRows("CoaFor", 144);
            if (r.length > 0)
                r[0].setAttribute("CoaId", null);
            Row[] r2 = coaVo.getFilteredRows("CoaFor", 145);
            if (r2.length > 0)
                r2[0].setAttribute("CoaId", null);
            coaVo.executeQuery();
            bindCoaTbl.processUpdates(FacesContext.getCurrentInstance());
            AdfFacesContext.getCurrentInstance().addPartialTarget(bindCoaTbl);
        }
    }

    public void setSlsItmFlg(RichSelectBooleanCheckbox slsItmFlg) {
        this.slsItmFlg = slsItmFlg;
    }

    public RichSelectBooleanCheckbox getSlsItmFlg() {
        return slsItmFlg;
    }

    public void setPurItmFlg(RichSelectBooleanCheckbox purItmFlg) {
        this.purItmFlg = purItmFlg;
    }

    public RichSelectBooleanCheckbox getPurItmFlg() {
        return purItmFlg;
    }

    public void setWipItmFlg(RichSelectBooleanCheckbox wipItmFlg) {
        this.wipItmFlg = wipItmFlg;
    }

    public RichSelectBooleanCheckbox getWipItmFlg() {
        return wipItmFlg;
    }

    public void setCapitalFlg(RichSelectBooleanCheckbox capitalFlg) {
        this.capitalFlg = capitalFlg;
    }

    public RichSelectBooleanCheckbox getCapitalFlg() {
        return capitalFlg;
    }

    public void wipItmFlgValueChange(ValueChangeEvent valueChangeEvent) {

        if (valueChangeEvent.getNewValue().equals(true)) {
            capitalFlg.setValue(false);
        }

    }

    public void purItmFlgValueChange(ValueChangeEvent valueChangeEvent) {

        //        if (valueChangeEvent.getNewValue().equals(true)) {
        //            capitalFlg.setValue(false);
        //        }


        ItemProfileAMImpl am = (ItemProfileAMImpl) resolvElDC(amName);
        ViewObjectImpl coaVo = am.getAppItmCoaVO();
        Row[] r = coaVo.getFilteredRows("CoaFor", 144);
        if (r.length > 0)
            r[0].setAttribute("CoaId", null);
        coaVo.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindCoaTbl);
        bindCoaTbl.processUpdates(FacesContext.getCurrentInstance());

    }

    public void sisItmFlgValueChange(ValueChangeEvent valueChangeEvent) {

        //        if (valueChangeEvent.getNewValue().equals(true)) {
        //            capitalFlg.setValue(false);
        //        }


        ItemProfileAMImpl am = (ItemProfileAMImpl) resolvElDC(amName);
        ViewObjectImpl coaVo = am.getAppItmCoaVO();
        Row[] r = coaVo.getFilteredRows("CoaFor", 145);
        if (r.length > 0)
            r[0].setAttribute("CoaId", null);
        coaVo.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindCoaTbl);
        bindCoaTbl.processUpdates(FacesContext.getCurrentInstance());

    }

    public void setBasicDtlTab(RichShowDetailItem basicDtlTab) {
        this.basicDtlTab = basicDtlTab;
    }

    public RichShowDetailItem getBasicDtlTab() {
        return basicDtlTab;
    }

    public void setOrgBtn_Disable(String orgBtn_Disable) {
        this.orgBtn_Disable = orgBtn_Disable;
    }

    public String getOrgBtn_Disable() {
        return orgBtn_Disable;
    }

    public void setAltItmBtn_Disable(String altItmBtn_Disable) {
        this.altItmBtn_Disable = altItmBtn_Disable;
    }

    public String getAltItmBtn_Disable() {
        return altItmBtn_Disable;
    }

    public void setAltItmInactiveReason(RichInputText altItmInactiveReason) {
        this.altItmInactiveReason = altItmInactiveReason;
    }

    public RichInputText getAltItmInactiveReason() {
        return altItmInactiveReason;
    }


    public void setItmActvBind(RichSelectBooleanCheckbox itmActvBind) {
        this.itmActvBind = itmActvBind;
    }

    public RichSelectBooleanCheckbox getItmActvBind() {
        return itmActvBind;
    }

    public void activeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Boolean obj = (Boolean) object;
        ItemProfileAMImpl am = (ItemProfileAMImpl) resolvElDC(amName);
        ViewObject itemPrf = am.getAppItmPrf();
        Row itemCurr = itemPrf.getCurrentRow();
        String itmId = (String) itemCurr.getAttribute("ItmId");
        BindingContext bc = BindingContext.getCurrent();
        BindingContainer bindings = bc.getCurrentBindingsEntry();

        OperationBinding check = bindings.getOperationBinding("isItemDeletable");
        check.getParamsMap().put("SlocId", slocid);
        check.getParamsMap().put("CldId", cldId);
        check.getParamsMap().put("hoOrgId", hoOrgId);
        check.getParamsMap().put("OrgId", orgId);
        check.getParamsMap().put("itemId", itmId);
        String isDeletable = "N";
        try {
            isDeletable = (String) check.execute();
        } catch (NullPointerException e) {
            isDeletable = "N";
            e.printStackTrace();
        }
        if ("N".equalsIgnoreCase(isDeletable)) {
            if (obj.equals(false)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolveElExp("#{bundle['MSG.361']}").toString(), null));
            }
        }
    }

    public void setMaxStock(RichInputText maxStock) {
        this.maxStock = maxStock;
    }

    public RichInputText getMaxStock() {
        return maxStock;
    }

    public void coaIdValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String newObj = (String) object;
        ItemProfileAMImpl am = (ItemProfileAMImpl) resolvElDC(amName);
        // Row[] filteredRows = am.getViewFinCoaLOV().getFilteredRows("CoaNm", newObj);
        // String itmId = null;
        Row[] filteredRows = am.getCoaVo().getFilteredRows("CoaNm", newObj);

        //  System.out.println("length coa " + filteredRows.length);
        if (filteredRows.length > 0) {
            //itmId = filteredRows[0].getAttribute("ItmId").toString();










        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          resolveElExp("#{bundle['MSG.362']}").toString(), null));
        }
        // for check duplicate coa name
        /* ViewObjectImpl coaVo = am.getAppItmCoa();
        String inputItm=(String)object;
        String itmDesc=null;
         int totalCount=coaVo.getRowCount();  //get ALL rows
         int rangeSize=coaVo.getRangeSize(); //all in range
         coaVo.setRangeSize(totalCount);
         Row[] rArray=coaVo.getAllRowsInRange();
         Row cRow=coaVo.getCurrentRow();
         int count=0;
         String itemIdCur="";
         for(Row r:rArray){
             if(!r.equals(cRow)){
                 try{
                         itemIdCur=r.getAttribute("CoaId").toString();
                    }
                 catch(NullPointerException npe){
                    itemIdCur="";
                    }
                 if(itemIdCur!=null){
                  Row[] xx = am.getViewFinCoaLOV().getFilteredRows("CoaNm", newObj);
                        if(xx.length>0){
                        itmDesc= xx[0].getAttribute("CoaNm").toString();
                     }
                        System.out.println("inputItm "+inputItm+"itmDesc "+itmDesc);
                     if(inputItm.equalsIgnoreCase(itmDesc)){
                         count=count+1;
                  }
              }

          }

        }
            System.out.println("count ---------"+count);
        coaVo.setRangeSize(rangeSize);  //set to original range
                 if(count>0){
                        String msg2="Duplicate Record found";
                        FacesMessage message2 = new FacesMessage(msg2);
                        message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                        throw new ValidatorException(message2);
                  } */
        /* int count=0;
        ViewObjectImpl itmPrf = am.getAppItmPrf();
        ViewObjectImpl itemCoa = am.getAppItmCoa();
            Row curritmprf = itmPrf.getCurrentRow();
            String itmId = curritmprf.getAttribute("ItmId").toString();
            Integer P_SLOCID = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
            String P_ORGID = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG1}");
        itemCoa.setRangeSize(-1);
        itemCoa.getAllRowsInRange();
        RowQualifier rowQualifier =new RowQualifier(itemCoa);
        // rowQualifier.setWhereClause(" OrgId = '"+P_ORGID+"' and SlocId="+P_SLOCID+" and CoaId="+coaId+" and ItmId= '"+itmId+"'" );
        rowQualifier.setWhereClause(" OrgId = '"+P_ORGID+"' and SlocId="+P_SLOCID+" and ItmId= '"+itmId+"'" );
        Row[] rows=itemCoa.getFilteredRows(rowQualifier);
        System.out.println("-----------"+rowQualifier.getExprStr()+"Total row after filtering --->"+rows.length);
            if(rows.length>0){
                for(Row r :rows){
                    Integer coa_Id=0;
                    if(r.getAttribute("CoaId")!=null){
                     coa_Id =Integer.parseInt(r.getAttribute("CoaId").toString());
                     System.out.println("First Loop---CoaId"+coa_Id);
                    }
                    System.out.println("First Loop---CoaId"+coa_Id);
                    for(Row r1 : rows){
                        Integer coa_Id1=-1;
                        if(r1.getAttribute("CoaId")!=null){
                         coa_Id1 =Integer.parseInt(r1.getAttribute("CoaId").toString());
                        }
                        System.out.println("Second Loop---CoaId"+coa_Id1);
                        if(coa_Id1.equals(coa_Id)){
                            count =count+1;
                        }
                    }
                }
            }
            System.out.println("Count is--->"+count); */
    }


    public void openingBalDiscloserListener(DisclosureEvent disclosureEvent) {
        int count = 0;
        ItemProfileAMImpl am = (ItemProfileAMImpl) resolvElDC(amName);
        ViewObjectImpl itmPrf = am.getAppItmPrf();
        ViewObjectImpl itemCoa = am.getAppItmCoaVO();
        Row currRow = itemCoa.getCurrentRow();
        Row curritmprf = itmPrf.getCurrentRow();
        if (disclosureEvent.isExpanded()) {
            String itmId = curritmprf.getAttribute("ItmId").toString();
            Integer P_SLOCID = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            String P_ORGID = (String) resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            Integer coaId = null;
            try {
                if (currRow.getAttribute("CoaId") != null) {
                    coaId = Integer.parseInt(currRow.getAttribute("CoaId").toString());
                }
            } catch (NullPointerException NPE) {

            }
            if (coaId != null) {
                itemCoa.setRangeSize(-1);
                itemCoa.getAllRowsInRange();
                RowQualifier rowQualifier = new RowQualifier(itemCoa);
                // rowQualifier.setWhereClause(" OrgId = '"+P_ORGID+"' and SlocId="+P_SLOCID+" and CoaId="+coaId+" and ItmId= '"+itmId+"'" );
                rowQualifier.setWhereClause(" OrgId = '" + P_ORGID + "' and SlocId=" + P_SLOCID + " and ItmId= '" +
                                            itmId + "'");
                Row[] rows = itemCoa.getFilteredRows(rowQualifier);
                //System.out.println("-----------" + rowQualifier.getExprStr());
                if (rows.length > 0) {
                    for (Row r : rows) {
                        Integer coa_Id = Integer.parseInt(r.getAttribute("CoaId").toString());
                        for (Row r1 : rows) {
                            Integer coa_Id1 = Integer.parseInt(r1.getAttribute("CoaId").toString());
                            if (coa_Id1.equals(coa_Id)) {
                                count = count + 1;
                            }
                        }
                    }
                }
                int c = (int) am.getViewItemAccLinkLOV().getEstimatedRowCount();
                if (count >
                    c) {
                    //count =0;
                    FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_ERROR, resolveElExp("#{bundle['LBL.393']}").toString(), null);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    altItmTabBind.setDisclosed(true);
                    RichShowDetailItem dtl = (RichShowDetailItem) disclosureEvent.getComponent();
                    dtl.setDisclosed(false);
                } else {
                    //  Row current=am.getAppItmPrf().getCurrentRow();
                    // String itmId=null;
                    if (curritmprf.getAttribute("ItmId") != null) {
                        itmId = curritmprf.getAttribute("ItmId").toString();
                    }
                    AdfFacesContext.getCurrentInstance().getPageFlowScope().put("APP_ITEM_ID", itmId);
                    //  rowQualifier.setWhereClause(null);
                }
            } else {
                //ItemProfileAMImpl am = (ItemProfileAMImpl)resolvElDC(amName);
                // Row current=am.getAppItmPrf().getCurrentRow();
                // String itmId=null;
                if (curritmprf.getAttribute("ItmId") != null) {
                    itmId = curritmprf.getAttribute("ItmId").toString();
                }
                AdfFacesContext.getCurrentInstance().getPageFlowScope().put("APP_ITEM_ID", itmId);
            }
        }
    }

    public void groupOkAction(ActionEvent actionEvent) {
        ItemProfileAMImpl am = (ItemProfileAMImpl) resolvElDC(amName);
        Row current = am.getAppGrpViewLov().getCurrentRow();
        if (current != null) {
            String childGrp = current.getAttribute("ChildGrp").toString();
            String grpPat = current.getAttribute("GrpPath").toString();
            //  System.out.println("Grpname=="+childGrp+"---grpPath==="+grpPat);
            am.getAppItmPrf().getCurrentRow().setAttribute("GrpId", current.getAttribute("GrpId").toString());

            ViewItemGrpLOVImpl grpLOV = am.getViewItemGrpLOV();
            Integer slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            String hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
            String cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
            String orgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
            grpLOV.setWhereClause("GRP_ID = '" + current.getAttribute("GrpId").toString() + "' and CLD_ID='" + cldId +
                                  "' and SLOC_ID=" + slocid + " and HO_ORG_ID='" + hoOrgId + "'");
            grpLOV.executeQuery();
            Row[] r = grpLOV.getAllRowsInRange();
            am.getAppItmPrf().getCurrentRow().setAttribute("SrvcItmFlg", r[0].getAttribute("SrvcItmFlg"));
            am.getAppItmPrf().getCurrentRow().setAttribute("StockableFlg", r[0].getAttribute("StockableFlg"));
            am.getAppItmPrf().getCurrentRow().setAttribute("SlsItmFlg", r[0].getAttribute("SlsItmFlg"));
            am.getAppItmPrf().getCurrentRow().setAttribute("PurItmFlg", r[0].getAttribute("PurItmFlg"));
            am.getAppItmPrf().getCurrentRow().setAttribute("WipItmFlg", r[0].getAttribute("WipItmFlg"));
            am.getAppItmPrf().getCurrentRow().setAttribute("TaxExmptFlg", r[0].getAttribute("TaxExmptFlg"));
            am.getAppItmPrf().getCurrentRow().setAttribute("CapitalGdFlg", r[0].getAttribute("CapitalGdFlg"));
            am.getAppItmPrf().getCurrentRow().setAttribute("QcReqdFlg", r[0].getAttribute("QcReqdFlg"));
            am.getAppItmPrf().getCurrentRow().setAttribute("CashPurFlg", r[0].getAttribute("CashPurFlg"));
            am.getAppItmPrf().getCurrentRow().setAttribute("ConsumableFlg", r[0].getAttribute("ConsumableFlg"));
            am.getAppItmPrf().getCurrentRow().setAttribute("SerializedFlg", r[0].getAttribute("SerializedFlg"));
            am.getAppItmPrf().getCurrentRow().setAttribute("PickOrder", r[0].getAttribute("PickOrder"));
            am.getAppItmPrf().getCurrentRow().setAttribute("CostMthd", r[0].getAttribute("CostMthd"));
            grpLOV.setWhereClause(" ");
            grpLOV.executeQuery();

        }
        getGroupPopUp().hide();
    }

    public void setGroupPopUp(RichPopup groupPopUp) {
        this.groupPopUp = groupPopUp;
    }

    public RichPopup getGroupPopUp() {
        return groupPopUp;
    }

    public void minStkVCL(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(maxStock);
        AdfFacesContext.getCurrentInstance().addPartialTarget(maxStkPglBind);
    }

    public void setMaxStkPglBind(RichPanelGroupLayout maxStkPglBind) {
        this.maxStkPglBind = maxStkPglBind;
    }

    public RichPanelGroupLayout getMaxStkPglBind() {
        return maxStkPglBind;
    }

    public void altItemTabDL(DisclosureEvent disclosureEvent) {
        ItemProfileAMImpl am = (ItemProfileAMImpl) resolvElDC(amName);

        //   am.getAppItmOrgVO().executeQuery();
        if (getWareHouseMode().equals("A")) {
            BindingContainer bc = BindingContext.getCurrent().getCurrentBindingsEntry();
            String mass = resolveElExp("#{bundle['MSG.363']}").toString();
            FacesMessage msg = new FacesMessage(mass);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fctx = FacesContext.getCurrentInstance();
            fctx.addMessage(null, msg);
            orgTab.setDisclosed(true);
            RichShowDetailItem dtl = (RichShowDetailItem) disclosureEvent.getComponent();
            dtl.setDisclosed(false);

        } /* else {
        int count = 0;
        ViewObjectImpl itmPrf = am.getAppItmPrf();
        ViewObjectImpl itemCoa = am.getAppItmCoaVO();
        Row currRow = itemCoa.getCurrentRow();
        Row curritmprf = itmPrf.getCurrentRow();
        if (disclosureEvent.isExpanded()) {
            String itmId = curritmprf.getAttribute("ItmId").toString();
            Integer P_SLOCID = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
            String P_ORGID = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG1}");
            String P_HO_ORGID=(String)resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID1}");
            String P_CLDID=(String)resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}");
            Integer coaId = null;
            try {
                if (currRow.getAttribute("CoaId") != null) {
                    coaId = Integer.parseInt(currRow.getAttribute("CoaId").toString());
                }
            } catch (NullPointerException NPE) {

            }
            if (coaId != null) {
                itemCoa.setRangeSize(-1);
                itemCoa.getAllRowsInRange();
                RowQualifier rowQualifier = new RowQualifier(itemCoa);
                // rowQualifier.setWhereClause(" OrgId = '"+P_ORGID+"' and SlocId="+P_SLOCID+" and CoaId="+coaId+" and ItmId= '"+itmId+"'" );
                rowQualifier.setWhereClause(" OrgId = '" + P_ORGID + "' and SlocId=" + P_SLOCID + " and ItmId= '" +itmId +"' and HoOrgId= '"+P_HO_ORGID+"' and CldId ='"+P_CLDID+"'");
                Row[] rows = itemCoa.getFilteredRows(rowQualifier);
                System.out.println("-----------" + rowQualifier.getExprStr());
                if (rows.length > 0) {
                    for (Row r : rows) {
                        Integer coa_Id = Integer.parseInt(r.getAttribute("CoaId").toString());
                        for (Row r1 : rows) {
                            Integer coa_Id1 = Integer.parseInt(r1.getAttribute("CoaId").toString());
                            if (coa_Id1.equals(coa_Id)) {
                                count = count + 1;
                            }
                        }
                    }
                }
                int c = (int)am.getViewItemAccLinkLOV().getEstimatedRowCount();
                if (count > c) {
                    //count =0;
                   FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resolveElExp("#{bundle['MSG.364']}").toString(), null);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    altItmTabBind.setDisclosed(true);
                    RichShowDetailItem dtl = (RichShowDetailItem)disclosureEvent.getComponent();
                    dtl.setDisclosed(false);
                }

                rowQualifier.setWhereClause(null);
            }
        }
        }*/


        if (disclosureEvent.isExpanded()) {
            ViewObjectImpl itmPrf = am.getAppItmPrf();
            Row curritmprf = itmPrf.getCurrentRow();
            String itmId = curritmprf.getAttribute("ItmId").toString();
            String P_ORGID = (String) resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            String P_HOORGID = (String) resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
            String P_CLDID = (String) resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
            Integer P_SLOCID = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            am.getAppItmOrgVO().executeQuery();
            Key key = new Key(new Object[] { P_SLOCID, P_CLDID, P_HOORGID, P_ORGID, itmId });
            Row row = am.getAppItmOrgVO().getRow(key);
            if (row != null) {
                row = am.getAppItmOrgVO().getRow(key);
                am.getAppItmOrgVO().setCurrentRow(row);

                bindAltItmVar1.setVisible(true);
                bindAltItmVar2.setVisible(false);
            } else {
                Row orgRow = am.getViewOrgLov().getRow(new Key(new Object[] { P_ORGID, P_CLDID }));
                Object obj = orgRow.getAttribute("OrgDesc");
                String str = resolveElExp("#{bundle['LBL.2863']}").toString();
                if (obj != null) {
                    outputMsgVar.setValue(str + " " + obj);
                } else {
                    outputMsgVar.setValue(str);
                }

                bindAltItmVar1.setVisible(false);
                bindAltItmVar2.setVisible(true);

            }
        }

    }

    public void setAltItmTabBind(RichShowDetailItem altItmTabBind) {
        this.altItmTabBind = altItmTabBind;
    }

    public RichShowDetailItem getAltItmTabBind() {
        return altItmTabBind;
    }

    public void orgTabDL(DisclosureEvent disclosureEvent) {
        /*  int count = 0;
        ItemProfileAMImpl am = (ItemProfileAMImpl)resolvElDC(amName);
        ViewObjectImpl itmPrf = am.getAppItmPrf();
        ViewObjectImpl itemCoa = am.getAppItmCoaVO();
        Row currRow = itemCoa.getCurrentRow();
        Row curritmprf = itmPrf.getCurrentRow();
        if (disclosureEvent.isExpanded()) {
            String itmId = curritmprf.getAttribute("ItmId").toString();
            Integer P_SLOCID = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
            String P_ORGID = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG1}");
            String P_HO_ORGID=(String)resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID1}");
            String P_CLDID=(String)resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}");

            Integer coaId = null;
            try {
                if (currRow.getAttribute("CoaId") != null) {
                    coaId = Integer.parseInt(currRow.getAttribute("CoaId").toString());
                }
            } catch (NullPointerException NPE) {

            }
            if (coaId != null) {
                itemCoa.setRangeSize(-1);
                itemCoa.getAllRowsInRange();
                RowQualifier rowQualifier = new RowQualifier(itemCoa);
                // rowQualifier.setWhereClause(" OrgId = '"+P_ORGID+"' and SlocId="+P_SLOCID+" and CoaId="+coaId+" and ItmId= '"+itmId+"'" );
                rowQualifier.setWhereClause(" OrgId = '" + P_ORGID + "' and SlocId=" + P_SLOCID + " and ItmId= '" +itmId +"' and HoOrgId= '"+P_HO_ORGID+"' and CldId ='"+P_CLDID+"'");
                 Row[] rows = itemCoa.getFilteredRows(rowQualifier);
                System.out.println("-----------" + rowQualifier.getExprStr());
                if (rows.length > 0) {
                    for (Row r : rows) {
                        Integer coa_Id = Integer.parseInt(r.getAttribute("CoaId").toString());
                        for (Row r1 : rows) {
                            Integer coa_Id1 = Integer.parseInt(r1.getAttribute("CoaId").toString());
                            if (coa_Id1.equals(coa_Id)) {
                                count = count + 1;
                            }
                        }
                    }
                }
                int c = (int)am.getViewItemAccLinkLOV().getEstimatedRowCount();
                if (count > c) {
                    //count =0;
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resolveElExp("#{bundle['MSG.364']}").toString(), null);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    altItmTabBind.setDisclosed(true);
                    RichShowDetailItem dtl = (RichShowDetailItem)disclosureEvent.getComponent();
                    dtl.setDisclosed(false);
                }

                rowQualifier.setWhereClause(null);
            }
        }*/
        // am.getAppItmOrgVO1().executeQuery();

        //       BindingContext bindingctx = BindingContext.getCurrent();
        //       BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
        //       OperationBinding createOpBAddress = bindings.getOperationBinding("executeViewOrg");
        //       createOpBAddress.execute();
    }

    public void stockTabDL(DisclosureEvent disclosureEvent) {
        ItemProfileAMImpl am = (ItemProfileAMImpl) resolvElDC(amName);
        am.getAppItmOrgVO().executeQuery();
        if (getWareHouseMode().equals("A")) {
            BindingContainer bc = BindingContext.getCurrent().getCurrentBindingsEntry();
            String mass = resolveElExp("#{bundle['MSG.363']}").toString();
            FacesMessage msg = new FacesMessage(mass);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fctx = FacesContext.getCurrentInstance();
            fctx.addMessage(null, msg);
            orgTab.setDisclosed(true);
            RichShowDetailItem dtl = (RichShowDetailItem) disclosureEvent.getComponent();
            dtl.setDisclosed(false);

        } /* else {
        int count = 0;
       ViewObjectImpl itmPrf = am.getAppItmPrf();
        ViewObjectImpl itemCoa = am.getAppItmCoaVO();
        Row currRow = itemCoa.getCurrentRow();
        Row curritmprf = itmPrf.getCurrentRow();
        if (disclosureEvent.isExpanded()) {
            String itmId = curritmprf.getAttribute("ItmId").toString();
            Integer P_SLOCID = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
            String P_ORGID = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG1}");
            String P_HO_ORGID=(String)resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID1}");
            String P_CLDID=(String)resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}");

            Integer coaId = null;
            try {
                if (currRow.getAttribute("CoaId") != null) {
                    coaId = Integer.parseInt(currRow.getAttribute("CoaId").toString());
                }
            } catch (NullPointerException NPE) {

            }
            if (coaId != null) {
                itemCoa.setRangeSize(-1);
                itemCoa.getAllRowsInRange();
                RowQualifier rowQualifier = new RowQualifier(itemCoa);
                // rowQualifier.setWhereClause(" OrgId = '"+P_ORGID+"' and SlocId="+P_SLOCID+" and CoaId="+coaId+" and ItmId= '"+itmId+"'" );
                rowQualifier.setWhereClause(" OrgId = '" + P_ORGID + "' and SlocId=" + P_SLOCID + " and ItmId= '" +itmId +"' and HoOrgId= '"+P_HO_ORGID+"' and CldId ='"+P_CLDID+"'");
                  Row[] rows = itemCoa.getFilteredRows(rowQualifier);
                System.out.println("-----------" + rowQualifier.getExprStr());
                if (rows.length > 0) {
                    for (Row r : rows) {
                        Integer coa_Id = Integer.parseInt(r.getAttribute("CoaId").toString());
                        for (Row r1 : rows) {
                            Integer coa_Id1 = Integer.parseInt(r1.getAttribute("CoaId").toString());
                            if (coa_Id1.equals(coa_Id)) {
                                count = count + 1;
                            }
                        }
                    }
                }
                int c = (int)am.getViewItemAccLinkLOV().getEstimatedRowCount();
                if (count > c) {
                    //count =0;
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resolveElExp("#{bundle['MSG.364']}").toString(), null);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    altItmTabBind.setDisclosed(true);
                    RichShowDetailItem dtl = (RichShowDetailItem)disclosureEvent.getComponent();
                    dtl.setDisclosed(false);
                }

                rowQualifier.setWhereClause(null);
            }
        }
        }*/

    }

    /*     public void imagetabDL(DisclosureEvent disclosureEvent) {
        int count = 0;
        ItemProfileAMImpl am = (ItemProfileAMImpl)resolvElDC(amName);
        ViewObjectImpl itmPrf = am.getAppItmPrf();
        ViewObjectImpl itemCoa = am.getAppItmCoa();
        Row currRow = itemCoa.getCurrentRow();
        Row curritmprf = itmPrf.getCurrentRow();
        if (disclosureEvent.isExpanded()) {
            String itmId = curritmprf.getAttribute("ItmId").toString();
            Integer P_SLOCID = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
            String P_ORGID = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG1}");
            Integer coaId = null;
            try{
            if(currRow.getAttribute("CoaId")!=null){
             coaId = Integer.parseInt(currRow.getAttribute("CoaId").toString());
            }
            }catch(NullPointerException NPE){

            }
            if(coaId != null){
            itemCoa.setRangeSize(-1);
            itemCoa.getAllRowsInRange();
            RowQualifier rowQualifier =new RowQualifier(itemCoa);
            // rowQualifier.setWhereClause(" OrgId = '"+P_ORGID+"' and SlocId="+P_SLOCID+" and CoaId="+coaId+" and ItmId= '"+itmId+"'" );
            rowQualifier.setWhereClause(" OrgId = '"+P_ORGID+"' and SlocId="+P_SLOCID+" and ItmId= '"+itmId+"'" );
            Row[] rows=itemCoa.getFilteredRows(rowQualifier);
            System.out.println("-----------"+rowQualifier.getExprStr());
                if(rows.length>0){
                    for(Row r :rows){
                        Integer coa_Id =Integer.parseInt(r.getAttribute("CoaId").toString());
                        for(Row r1 : rows){
                            Integer coa_Id1 =Integer.parseInt(r1.getAttribute("CoaId").toString());
                            if(coa_Id1.equals(coa_Id)){
                                count =count+1;
                            }
                        }
                    }
                }
            if(count>4){
                //count =0;
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate COA found.", null);
                FacesContext.getCurrentInstance().addMessage(null, msg);
                altItmTabBind.setDisclosed(true);
               RichShowDetailItem dtl= (RichShowDetailItem)disclosureEvent.getComponent();
               dtl.setDisclosed(false);
            }

            rowQualifier.setWhereClause(null);
        }
        }
    } */
    /*   public String setItmTab(){
        basicDtlTab.setDisclosed(true);
        return "goToItm";
    } */

    public void setTabSelect(String tabSelect) {
        this.tabSelect = tabSelect;
    }

    public String getTabSelect() {
        return tabSelect;
    }

    public void callToSearchBean() {
        setTabSelectF(true);
        setTabSelect("S");
        //  System.out.println("----------" + getTabSelect());
        // System.out.println("----------" + isTabSelectF());
    }

    public void setTabSelectF(boolean tabSelectF) {
        this.tabSelectF = tabSelectF;
    }

    public boolean isTabSelectF() {
        return tabSelectF;
    }

    public void setWareHouseMode(String WareHouseMode) {
        this.WareHouseMode = WareHouseMode;
    }

    public String getWareHouseMode() {
        return WareHouseMode;
    }

    public void basicDtlTabDL(DisclosureEvent disclosureEvent) {
        ItemProfileAMImpl am = (ItemProfileAMImpl) resolvElDC(amName);

        am.getAppItmOrgVO().executeQuery();
        if (getWareHouseMode().equals("A")) {
            BindingContainer bc = BindingContext.getCurrent().getCurrentBindingsEntry();
            String mass = resolveElExp("#{bundle['MSG.363']}").toString();
            FacesMessage msg = new FacesMessage(mass);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fctx = FacesContext.getCurrentInstance();
            fctx.addMessage(null, msg);
            orgTab.setDisclosed(true);
            RichShowDetailItem dtl = (RichShowDetailItem) disclosureEvent.getComponent();
            dtl.setDisclosed(false);

        } /*else {
        int count = 0;
            ViewObjectImpl itmPrf = am.getAppItmPrf();
        ViewObjectImpl itemCoa = am.getAppItmCoaVO();
        Row currRow = itemCoa.getCurrentRow();
        Row curritmprf = itmPrf.getCurrentRow();
        if (disclosureEvent.isExpanded()) {
            String itmId = curritmprf.getAttribute("ItmId").toString();
            Integer P_SLOCID = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
            String P_ORGID = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG1}");
            String P_HO_ORGID=(String)resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID1}");
            String P_CLDID=(String)resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID1}");

            Integer coaId = null;
            try {
                if (currRow.getAttribute("CoaId") != null) {
                    coaId = Integer.parseInt(currRow.getAttribute("CoaId").toString());
                }
            } catch (NullPointerException NPE) {

            }
            if (coaId != null) {
                itemCoa.setRangeSize(-1);
                itemCoa.getAllRowsInRange();
                RowQualifier rowQualifier = new RowQualifier(itemCoa);
                // rowQualifier.setWhereClause(" OrgId = '"+P_ORGID+"' and SlocId="+P_SLOCID+" and CoaId="+coaId+" and ItmId= '"+itmId+"'" );
                rowQualifier.setWhereClause(" OrgId = '" + P_ORGID + "' and SlocId=" + P_SLOCID + " and ItmId= '" +itmId +"' and HoOrgId= '"+P_HO_ORGID+"' and CldId ='"+P_CLDID+"'");
                 Row[] rows = itemCoa.getFilteredRows(rowQualifier);
                System.out.println("-----------" + rowQualifier.getExprStr());
                if (rows.length > 0) {
                    for (Row r : rows) {
                        Integer coa_Id = Integer.parseInt(r.getAttribute("CoaId").toString());
                        for (Row r1 : rows) {
                            Integer coa_Id1 = Integer.parseInt(r1.getAttribute("CoaId").toString());
                            if (coa_Id1.equals(coa_Id)) {
                                count = count + 1;
                            }
                        }
                    }
                }
                int c = (int)am.getViewItemAccLinkLOV().getEstimatedRowCount();
                if (count > c) {
                    //count =0;
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,resolveElExp("#{bundle['MSG.364']}").toString(), null);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    altItmTabBind.setDisclosed(true);
                    RichShowDetailItem dtl = (RichShowDetailItem)disclosureEvent.getComponent();
                    dtl.setDisclosed(false);
                }

                rowQualifier.setWhereClause(null);
            }
        }
        }*/

    }

    public void coaDtlTabDL(DisclosureEvent disclosureEvent) {
        ItemProfileAMImpl am = (ItemProfileAMImpl) resolvElDC(amName);
        // am.getAppItmOrgVO().executeQuery();
        String P_ORGID = (String) resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String P_HOORGID = (String) resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String P_CLDID = (String) resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer P_SLOCID = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String ItmId = (String) am.getAppItmPrf().getCurrentRow().getAttribute("ItmId");
        // am.getAppItmCoaVO().setWhereClause("Org_Id='"+P_ORGID+"'");
        // am.getAppItmCoaVO().executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindCoaTbl);

        AdfFacesContext.getCurrentInstance().addPartialTarget(bindCoaTbl);
        if (getWareHouseMode().equals("A")) {
            BindingContainer bc = BindingContext.getCurrent().getCurrentBindingsEntry();
            String mass = resolveElExp("#{bundle['MSG.363']}").toString();
            FacesMessage msg = new FacesMessage(mass);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fctx = FacesContext.getCurrentInstance();
            fctx.addMessage(null, msg);
            orgTab.setDisclosed(true);
            RichShowDetailItem dtl = (RichShowDetailItem) disclosureEvent.getComponent();
            dtl.setDisclosed(false);

        }

        am.getAppItmOrgVO().executeQuery();
        Key key = new Key(new Object[] { P_SLOCID, P_CLDID, P_HOORGID, P_ORGID, ItmId });
        ViewObjectImpl vo = am.getAppItmCoaVO();
        Row row = am.getAppItmOrgVO().getRow(key);

        //    System.out.println(key +" : Key");

        //     System.out.println(row +" : row");


        /*
     * Date : 17-Apr-2014
     * if(row != null)
        {
        row = am.getAppItmOrgVO().getRow(key);
        am.getAppItmOrgVO().setCurrentRow(row);
         }
      else
      {
          Row OrgRow=am.getOrg1().getRow(new Key(new Object[]{P_ORGID,P_CLDID}));
          am.getOrg1().setCurrentRow(OrgRow);
          vo.setWhereClause(null);
          vo.executeQuery();
          vo.setWhereClause("ORG_ID = '"+P_ORGID+"'");
          vo.executeQuery();
      }


        vo.setWhereClause(null);
        vo.executeQuery();
        vo.setWhereClause("HO_ORG_ID = '"+P_HOORGID+"' AND SLOC_ID = "+P_SLOCID+" AND CLD_ID = '"+P_CLDID+"'");
        vo.executeQuery();
    */

        //vo.setWhereClause(null);
        //vo.executeQuery();
        //System.out.println("No. of rows in coavo before where="+vo.getEstimatedRowCount()+" "+P_ORGID);
        //vo.setWhereClause("ORG_ID = '"+P_ORGID+"'");
        //   System.out.println(vo.getQuery());

        AdfFacesContext.getCurrentInstance().addPartialTarget(bindCoaTbl);
        // vo.setWhereClause("ORG_ID = '"+P_ORGID+"'");
        //  vo.executeQuery();

        //    System.out.println("No. of rows in coavo after where="+vo.getEstimatedRowCount()+" "+P_ORGID);
        /*  else {
        int count = 0;
        ItemProfileAMImpl am = (ItemProfileAMImpl)resolvElDC(amName);
        ViewObjectImpl itmPrf = am.getAppItmPrf();
        ViewObjectImpl itemCoa = am.getAppItmCoa();
        Row currRow = itemCoa.getCurrentRow();
        Row curritmprf = itmPrf.getCurrentRow();
        if (disclosureEvent.isExpanded()) {
            String itmId = curritmprf.getAttribute("ItmId").toString();
            Integer P_SLOCID = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
            String P_ORGID = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG1}");
            Integer coaId = null;
            try {
                if (currRow.getAttribute("CoaId") != null) {
                    coaId = Integer.parseInt(currRow.getAttribute("CoaId").toString());
                }
            } catch (NullPointerException NPE) {

            }
            if (coaId != null) {
                itemCoa.setRangeSize(-1);
                itemCoa.getAllRowsInRange();
                RowQualifier rowQualifier = new RowQualifier(itemCoa);
                // rowQualifier.setWhereClause(" OrgId = '"+P_ORGID+"' and SlocId="+P_SLOCID+" and CoaId="+coaId+" and ItmId= '"+itmId+"'" );
                rowQualifier.setWhereClause(" OrgId = '" + P_ORGID + "' and SlocId=" + P_SLOCID + " and ItmId= '" +
                                            itmId + "'");
                Row[] rows = itemCoa.getFilteredRows(rowQualifier);
                System.out.println("-----------" + rowQualifier.getExprStr());
                if (rows.length > 0) {
                    for (Row r : rows) {
                        Integer coa_Id = Integer.parseInt(r.getAttribute("CoaId").toString());
                        for (Row r1 : rows) {
                            Integer coa_Id1 = Integer.parseInt(r1.getAttribute("CoaId").toString());
                            if (coa_Id1.equals(coa_Id)) {
                                count = count + 1;
                            }
                        }
                    }
                }
                if (count > 4) {
                    //count =0;
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate COA found.", null);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    altItmTabBind.setDisclosed(true);
                    RichShowDetailItem dtl = (RichShowDetailItem)disclosureEvent.getComponent();
                    dtl.setDisclosed(false);
                }

                rowQualifier.setWhereClause(null);
            }
        }
        } */

    }

    public void setOrgTab(RichShowDetailItem orgTab) {
        this.orgTab = orgTab;
    }

    public RichShowDetailItem getOrgTab() {
        return orgTab;
    }

    public void setPurUomBind(RichInputListOfValues purUomBind) {
        this.purUomBind = purUomBind;
    }

    public RichInputListOfValues getPurUomBind() {
        return purUomBind;
    }

    public void setSaleUomBind(RichInputListOfValues saleUomBind) {
        this.saleUomBind = saleUomBind;
    }

    public RichInputListOfValues getSaleUomBind() {
        return saleUomBind;
    }

    public void setPurPriceBind(RichInputText purPriceBind) {
        this.purPriceBind = purPriceBind;
    }

    public RichInputText getPurPriceBind() {
        return purPriceBind;
    }

    public void setSalePriceBind(RichInputText salePriceBind) {
        this.salePriceBind = salePriceBind;
    }

    public RichInputText getSalePriceBind() {
        return salePriceBind;
    }

    public void setPurPricePLAMBind(RichPanelLabelAndMessage purPricePLAMBind) {
        this.purPricePLAMBind = purPricePLAMBind;
    }

    public RichPanelLabelAndMessage getPurPricePLAMBind() {
        return purPricePLAMBind;
    }

    public void setSalePricePLAMBind(RichPanelLabelAndMessage salePricePLAMBind) {
        this.salePricePLAMBind = salePricePLAMBind;
    }

    public RichPanelLabelAndMessage getSalePricePLAMBind() {
        return salePricePLAMBind;
    }

    public void setCashPurFlg(RichSelectBooleanCheckbox cashPurFlg) {
        this.cashPurFlg = cashPurFlg;
    }

    public RichSelectBooleanCheckbox getCashPurFlg() {
        return cashPurFlg;
    }

    public void cashPurFlgValueChange(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue().equals(true)) {
            capitalFlg.setValue(false);
        }
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        BindingContext bindingctx = BindingContext.getCurrent();
        BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
        count = (Integer) bindings.getOperationBinding("on_load_page_1").execute();
        return count;
    }

    /*   public void setCoaIter(UIXIterator coaIter) {
        this.coaIter = coaIter;
    }

    public UIXIterator getCoaIter() {
        return coaIter;
    } */

    public void setTranCoaNameBinding(RichInputListOfValues tranCoaNameBinding) {
        this.tranCoaNameBinding = tranCoaNameBinding;
    }

    public RichInputListOfValues getTranCoaNameBinding() {
        return tranCoaNameBinding;
    }

    public void setDisableItemCode(boolean disableItemCode) {
        this.disableItemCode = disableItemCode;
    }

    public boolean isDisableItemCode() {
        return disableItemCode;
    }

    public void setItemCode(String ItemCode) {
        this.ItemCode = ItemCode;
    }

    public String getItemCode() {
        return ItemCode;
    }


    public void setBindItemCode(RichInputText bindItemCode) {
        this.bindItemCode = bindItemCode;
    }

    public RichInputText getBindItemCode() {
        BindingContext bindingctx = BindingContext.getCurrent();
        BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
        OperationBinding op = bindings.getOperationBinding("isOrgUseAutoItemId");
        op.execute();
        Boolean flag = (Boolean) op.getResult();
        if (flag) {
            disableItemCode = true;
        } else {
            disableItemCode = false;
        }
        return bindItemCode;
    }

    public String getVisbleItemId() {
        BindingContext bindingctx = BindingContext.getCurrent();
        BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
        OperationBinding op = bindings.getOperationBinding("isOrgUseAutoItemId");
        op.execute();
        Boolean flag = (Boolean) op.getResult();
        if (flag) {
            return "Y";
        } else {
            return "N";
        }

    }


    public void deleteOrgAction(ActionEvent actionEvent) {
        // Add event code here...
        ItemProfileAMImpl am = (ItemProfileAMImpl) resolvElDC(amName);
        ViewObjectImpl itmOrgVo = am.getAppItmOrgVO1();
        Row newRow = itmOrgVo.getCurrentRow();
        if (newRow != null) {
            newRow.remove();
        }
        itmOrgVo.executeQuery();
    }

    public void GroupChngListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...


        ItemProfileAMImpl am = (ItemProfileAMImpl) resolvElDC(amName);
        ViewObjectImpl itemPrf = am.getAppItmPrf();
        ViewObjectImpl itemGrp = am.getViewItemGrpLOV();
        ViewObjectImpl orgVo = am.getAppItmOrgVO1();
        String value = (String) valueChangeEvent.getNewValue();
        Row rows[] = itemGrp.getFilteredRows("GrpNm", value);
        Row currRow = itemPrf.getCurrentRow();

        if (rows != null && rows.length >= 1) {
            currRow.setAttribute("StockableFlg", rows[0].getAttribute("StockableFlg"));
            currRow.setAttribute("SrvcItmFlg", rows[0].getAttribute("SrvcItmFlg"));
            currRow.setAttribute("TaxExmptFlg", rows[0].getAttribute("TaxExmptFlg"));
            currRow.setAttribute("ConsumableFlg", rows[0].getAttribute("ConsumableFlg"));
            currRow.setAttribute("SerializedFlg", rows[0].getAttribute("SerializedFlg"));
            currRow.setAttribute("CashPurFlg", rows[0].getAttribute("CashPurFlg"));
            currRow.setAttribute("SlsItmFlg", rows[0].getAttribute("SlsItmFlg"));
            currRow.setAttribute("PurItmFlg", rows[0].getAttribute("PurItmFlg"));
            currRow.setAttribute("WipItmFlg", rows[0].getAttribute("WipItmFlg"));
            currRow.setAttribute("CapitalGdFlg", rows[0].getAttribute("CapitalGdFlg"));
            currRow.setAttribute("QcReqdFlg", rows[0].getAttribute("QcReqdFlg"));
            currRow.setAttribute("PickOrder", rows[0].getAttribute("PickOrder")); //Pick Order
            currRow.setAttribute("CostMthd", rows[0].getAttribute("CostMthd"));
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(slsItmFlg);
        AdfFacesContext.getCurrentInstance().addPartialTarget(purItmFlg);
        AdfFacesContext.getCurrentInstance().addPartialTarget(wipItmFlg);
        AdfFacesContext.getCurrentInstance().addPartialTarget(serviceFlg);
        AdfFacesContext.getCurrentInstance().addPartialTarget(stockableFlg);
        AdfFacesContext.getCurrentInstance().addPartialTarget(cashPurFlg);
        AdfFacesContext.getCurrentInstance().addPartialTarget(capitalFlg);
        AdfFacesContext.getCurrentInstance().addPartialTarget(qcFlag);
        AdfFacesContext.getCurrentInstance().addPartialTarget(exptTaxFlg);
        AdfFacesContext.getCurrentInstance().addPartialTarget(consumableFlg);
        AdfFacesContext.getCurrentInstance().addPartialTarget(serializedFlg);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pickOrderBindVar);

    }

    public void setQcFlag(RichSelectBooleanCheckbox qcFlag) {
        this.qcFlag = qcFlag;
    }

    public RichSelectBooleanCheckbox getQcFlag() {
        return qcFlag;
    }

    public void setExptTaxFlg(RichSelectBooleanCheckbox exptTaxFlg) {
        this.exptTaxFlg = exptTaxFlg;
    }

    public RichSelectBooleanCheckbox getExptTaxFlg() {
        return exptTaxFlg;
    }

    public void setConsumableFlg(RichSelectBooleanCheckbox consumableFlg) {
        this.consumableFlg = consumableFlg;
    }

    public RichSelectBooleanCheckbox getConsumableFlg() {
        return consumableFlg;
    }

    public void setSerializedFlg(RichSelectBooleanCheckbox serializedFlg) {
        this.serializedFlg = serializedFlg;
    }

    public RichSelectBooleanCheckbox getSerializedFlg() {
        return serializedFlg;
    }

    public void partialSaveAction(ActionEvent actionEvent) {

        String P_ORGID = (String) resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String P_HOORGID = (String) resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String P_CLDID = (String) resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer P_SLOCID = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        ItemProfileAMImpl am = (ItemProfileAMImpl) resolvElDC(amName);
        ViewObjectImpl itmOrgVo = am.getAppItmOrgVO1();
        ViewObject appItmPrf = am.getAppItmPrf();
        Row curItm = appItmPrf.getCurrentRow();
        RowQualifier rqorg = new RowQualifier(itmOrgVo);
        String itm = (String) curItm.getAttribute("ItmId");
        rqorg.setWhereClause("HoOrgId='" + P_HOORGID + "' and CldId='" + P_CLDID + "' and SlocId=" + P_SLOCID +
                             " and ItmId='" + itm + "'");
        Row[] orgrow = itmOrgVo.getFilteredRows(rqorg);
        if (orgrow.length >= 1) {
            // System.out.println("query is="+rqorg.getExprStr());
            FacesMessage msg = new FacesMessage("Item already Saved");
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fctx = FacesContext.getCurrentInstance();
            fctx.addMessage(null, msg);
        }

        else {
            /*  ViewObject appItmPrf = am.getAppItmPrf();
              Row curItm = appItmPrf.getCurrentRow(); */
            ViewObject orgLOV = am.getViewOrgLov();
            // ViewObjectImpl itemGrp = am.getViewItemGrpLOV();
            ViewObject accLinkLOV = am.getViewItemAccLinkLOV();
            ViewObjectImpl appItmCoa = am.getAppItmCoaVO2();
            ViewObjectImpl grpCoaVo = am.getGrpCoa1();

            //Add Current Org in Itm$org$prf
            RowQualifier rq = new RowQualifier(itmOrgVo);
            rq.setWhereClause("SlocId = " + curItm.getAttribute("SlocId") + " AND CldId ='" +
                              curItm.getAttribute("CldId") + "' AND HoOrgId ='" + curItm.getAttribute("HoOrgId") +
                              "' AND OrgId = '" + P_ORGID + "' AND ItmId='" + curItm.getAttribute("ItmId") + "'");
            Row rows[] = itmOrgVo.getFilteredRows(rq);

            if (rows != null && rows.length > 0) {
                String msg = resolveElExp("#{bundle['LBL.2299']}").toString();
                FacesMessage message = new FacesMessage(msg);
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else {
                Row orgRow = itmOrgVo.createRow();
                //orgRow.setAttribute("SlocId",P_SLOCID);
                // orgRow.setAttribute("CldId", P_CLDID);
                // orgRow.setAttribute("HoOrgId",P_HOORGID);
                orgRow.setAttribute("OrgId", P_ORGID);
                // orgRow.setAttribute("ItmId", curItm.getAttribute("ItmId"));
                itmOrgVo.insertRow(orgRow);
            }

            if (curItm.getAttribute("GrpId") != null) {

                Row tolRws[] = accLinkLOV.getFilteredRows("AttTypeId", Integer.valueOf(31));
                for (Row or : tolRws) {

                    RowQualifier rwq = new RowQualifier(grpCoaVo);
                    rwq.setWhereClause("GrpId = '" + curItm.getAttribute("GrpId") + "' AND CoaFor = " +
                                       or.getAttribute("AttId") + " AND SlocId = " + curItm.getAttribute("SlocId") +
                                       " AND CldId = '" + curItm.getAttribute("CldId") + "' AND HoOrgId = '" +
                                       curItm.getAttribute("HoOrgId") + "'");
                    //   rwq.setWhereClause("GrpId = '"+curItm.getAttribute("GrpId")+"' AND CoaFor = "+or.getAttribute("AttId")+" AND OrgId = '"+P_ORGID+"'")
                    Row r[] = grpCoaVo.getFilteredRows(rwq);

                    Row newRow = appItmCoa.createRow();
                    newRow.setAttribute("SlocId", P_SLOCID);
                    newRow.setAttribute("CldId", P_CLDID);
                    newRow.setAttribute("HoOrgId", P_HOORGID);
                    newRow.setAttribute("ItmId", curItm.getAttribute("ItmId"));
                    newRow.setAttribute("CoaFor", or.getAttribute("AttId"));
                    newRow.setAttribute("OrgId", P_ORGID);
                    if (r != null && r.length == 1) {
                        newRow.setAttribute("CoaId", r[0].getAttribute("CoaId"));
                    }
                    appItmCoa.insertRow(newRow);
                }
            }


            BindingContext bindingctx = BindingContext.getCurrent();
            BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
            OperationBinding createOpBAddress = bindings.getOperationBinding("Commit");
            createOpBAddress.execute();
            disablePartial = false;
            mode = "V";
            String mass = resolveElExp("#{bundle['MSG.91']}").toString();
            FacesMessage msg = new FacesMessage(mass);
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fctx = FacesContext.getCurrentInstance();
            fctx.addMessage(null, msg);
            // itmOrgVo.executeQuery();

        }
    }

    public void setPartialAddBtn(RichCommandImageLink partialAddBtn) {
        this.partialAddBtn = partialAddBtn;
    }

    public RichCommandImageLink getPartialAddBtn() {
        return partialAddBtn;
    }

    public void setDisablePartial(boolean disablePartial) {
        this.disablePartial = disablePartial;
    }

    public boolean isDisablePartial() {
        String str = mode;
        if (str.trim().length() <= 0) {
            str = modeGet();

            if (str.equals("A")) {
                disablePartial = true;
            } else {
                disablePartial = false;
            }
        }

        else if (str.equals("A")) {
            disablePartial = true;
        } else {
            disablePartial = false;
        }
        return disablePartial;
    }


    public void srlFlgChangeListner(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public String getisDeletable() {
        String flag = "";
        //Boolean obj = (Boolean)object;
        ItemProfileAMImpl am = (ItemProfileAMImpl) resolvElDC(amName);
        ViewObject itemPrf = am.getAppItmPrf();
        Row itemCurr = itemPrf.getCurrentRow();
        String itmId = (String) itemCurr.getAttribute("ItmId");
        BindingContext bc = BindingContext.getCurrent();
        BindingContainer bindings = bc.getCurrentBindingsEntry();

        OperationBinding check = bindings.getOperationBinding("isItemDeletable");
        check.getParamsMap().put("SlocId", slocid);
        check.getParamsMap().put("CldId", cldId);
        check.getParamsMap().put("hoOrgId", hoOrgId);
        check.getParamsMap().put("OrgId", orgId);
        check.getParamsMap().put("itemId", itmId);
        String isDeletable = "N";
        try {
            isDeletable = (String) check.execute();
        } catch (NullPointerException e) {
            isDeletable = "N";
            e.printStackTrace();
        }
        // System.out.println("function is delete table is : "+flag);
        if ("N".equalsIgnoreCase(isDeletable)) {
            flag = isDeletable;
        }

        return flag;
    }

    public void setPickOrderBindVar(RichSelectOneChoice pickOrderBindVar) {
        this.pickOrderBindVar = pickOrderBindVar;
    }

    public RichSelectOneChoice getPickOrderBindVar() {
        return pickOrderBindVar;
    }

    public void setBindAltItmVar1(RichPanelGroupLayout bindAltItmVar1) {
        this.bindAltItmVar1 = bindAltItmVar1;
    }

    public RichPanelGroupLayout getBindAltItmVar1() {
        return bindAltItmVar1;
    }

    public void setBindAltItmVar2(RichPanelGroupLayout bindAltItmVar2) {
        this.bindAltItmVar2 = bindAltItmVar2;
    }

    public RichPanelGroupLayout getBindAltItmVar2() {
        return bindAltItmVar2;
    }

    public void setOutputMsgVar(RichOutputText outputMsgVar) {
        this.outputMsgVar = outputMsgVar;
    }

    public RichOutputText getOutputMsgVar() {
        return outputMsgVar;
    }

    public void setAttributePopUp(RichPopup attributePopUp) {
        this.attributePopUp = attributePopUp;
    }

    public RichPopup getAttributePopUp() {
        return attributePopUp;
    }

    public String addNewAttribute() {
        Object type = itmAttTypeVar.getValue();
        Object val = itmAttValVar.getValue();

        System.out.println("Type : " + type);
        System.out.println("Value : " + val);
        // System.out.println("length inside add att "+type.toString().length());
        //System.out.println("length inside add att "+val.toString().length());
        if (type == null) { //|| type.toString().length()==0

            FacesMessage message = new FacesMessage("Attribute Type is required.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } else if (val == null) { //|| val.toString().length()==0
            adfLog.info("1 ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
            FacesMessage message = new FacesMessage("Attribute is required.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } else if (type != null && type.toString().length() == 0) {

            adfLog.info("3 ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
            FacesMessage message = new FacesMessage("Attribute Type is required.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } else if (val != null && val.toString().length() == 0) {
            adfLog.info("5 ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
            FacesMessage message = new FacesMessage("Attribute is required.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);


        } else {
            adfLog.info("6 ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
            String retval = "N";
            OperationBinding itmAttV = getBindings().getOperationBinding("isAttributeValid");
            itmAttV.getParamsMap().put("itmAttType", type.toString());
            itmAttV.getParamsMap().put("itmAttValue", val.toString());
            itmAttV.execute();

            if (itmAttV.getResult() != null) {
                retval = itmAttV.getResult().toString();
            }
            if ("N".equalsIgnoreCase(retval)) {

                OperationBinding itmAtt = getBindings().getOperationBinding("addItemAttribute");
                itmAtt.getParamsMap().put("itmAttType", type.toString());
                itmAtt.getParamsMap().put("itmAttValue", val.toString());
                itmAtt.execute();

                /*  OperationBinding itmAtt = getBindings().getOperationBinding("addItemAttribute1");
                itmAtt.execute(); */

            } else if ("Y".equalsIgnoreCase(retval)) {
                FacesMessage message = new FacesMessage("Can't Add More Than One Same Attribute Type In Same Item.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
        }
        /* if(type !=null && val !=null && type.toString().length() > 0 && val.toString().length() >0){

            OperationBinding itmAtt = getBindings().getOperationBinding("addItemAttribute");
            itmAtt.getParamsMap().put("itmAttType", type.toString());
            itmAtt.getParamsMap().put("itmAttValue", val.toString());
            itmAtt.execute();
        } */

        return null;
    }

    /*  public void setItmAttTypeVar(RichSelectOneChoice itmAttTypeVar) {
        this.itmAttTypeVar = itmAttTypeVar;
    }

    public RichSelectOneChoice getItmAttTypeVar() {
        return itmAttTypeVar;
    } */

    /*  public void setItmAttValVar(RichSelectOneChoice itmAttValVar) {
        this.itmAttValVar = itmAttValVar;
    }

    public RichSelectOneChoice getItmAttValVar() {
        return itmAttValVar;
    } */

    public String getDisablebeforeApproved() {
        ItemProfileAMImpl am = (ItemProfileAMImpl) resolvElDC(amName);
        ViewObject itemPrf = am.getAppItmPrf();
        Row itemCurr = itemPrf.getCurrentRow();
        String actVal = "N";
        if (itemCurr != null) {
            if (itemCurr.getAttribute("Actv") != null) {
                String actv = itemCurr.getAttribute("Actv").toString();
                if (actv.equalsIgnoreCase("Y") || actv.equalsIgnoreCase("N")) {
                    actVal = "Y";
                }
            }

        }
        return actVal;
    }

    public String addItmAttributeAction() {

        OperationBinding countOpr = getBindings().getOperationBinding("getRowCount");
        countOpr.execute();
        Integer i = -1;
        Object obj = countOpr.getResult();
        if (obj != null) {
            i = (Integer) obj;
        }

        countAtt = i;

        showPopup(attributePopUp, true);
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

    public void AttributeDialogListener(DialogEvent dialogEvent) {
        ItemProfileAMImpl am = (ItemProfileAMImpl) resolvElDC(amName);
        am.getTransAttTypVal1().executeQuery();
        if (dialogEvent.getOutcome().name().equals("ok")) {
            if (!(attTblVar.getEstimatedRowCount() == 0 && countAtt == 0)) {
                am.getDBTransaction().postChanges();
            }
        }
        itmAttTypeVar.setValue(null);
        itmAttValVar.setValue(null);
    }

    public String removeAttributeAction() {
        ItemProfileAMImpl am = (ItemProfileAMImpl) resolvElDC(amName);
        ViewObjectImpl itmAtt = am.getAppItmAtt1();
        Row currR = itmAtt.getCurrentRow();
        currR.remove();
        return null;
    }

    public void duplicateNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {

            String itemNm = (String) object;
            OperationBinding itmAtt = getBindings().getOperationBinding("isItemNameDuplicate");
            itmAtt.getParamsMap().put("itemName", itemNm);
            itmAtt.getParamsMap().put("previousVal", getItemDescBeforeEdit());
            itmAtt.execute();
            Boolean flg = (Boolean) itmAtt.getResult();

            if (flg) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.763']}").toString(), null));
            }
        }


    }

    public void setItemNameVar(RichInputText itemNameVar) {
        this.itemNameVar = itemNameVar;
    }

    public RichInputText getItemNameVar() {
        return itemNameVar;
    }

    public void setItemDescBeforeEdit(String itemDescBeforeEdit) {
        this.itemDescBeforeEdit = itemDescBeforeEdit;
    }

    public String getItemDescBeforeEdit() {
        return itemDescBeforeEdit;
    }

    public void setBindCoaTbl(RichTable bindCoaTbl) {
        this.bindCoaTbl = bindCoaTbl;
    }

    public RichTable getBindCoaTbl() {
        return bindCoaTbl;
    }

    public void setTransItmAttTypeVar(RichSelectOneChoice transItmAttTypeVar) {
        this.transItmAttTypeVar = transItmAttTypeVar;
    }

    public RichSelectOneChoice getTransItmAttTypeVar() {
        return transItmAttTypeVar;
    }

    public void setTransItmAttValVar(RichSelectOneChoice transItmAttValVar) {
        this.transItmAttValVar = transItmAttValVar;
    }

    public RichSelectOneChoice getTransItmAttValVar() {
        return transItmAttValVar;
    }

    public void setAttTblVar(RichTable attTblVar) {
        this.attTblVar = attTblVar;
    }

    public RichTable getAttTblVar() {
        return attTblVar;
    }

    public void setCountAtt(Integer countAtt) {
        this.countAtt = countAtt;
    }

    public Integer getCountAtt() {
        return countAtt;
    }

    public void sampleItemPopulateVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            ItemProfileAMImpl am = (ItemProfileAMImpl) resolvElDC(amName);
            adfLog.info("value ::::::: " + vce.getNewValue().toString());
            OperationBinding itmAtt = getBindings().getOperationBinding("populateItmPropertyFrmSampleItem");
            itmAtt.getParamsMap().put("value", vce.getNewValue().toString());
            itmAtt.execute();
            OperationBinding countOpr = getBindings().getOperationBinding("getRowCount");
            countOpr.execute();
            /* Integer i=-1;
            Object obj = countOpr.getResult();
            if(obj != null)
            {i=(Integer)obj;}

            countAtt=i;
            if(!(attTblVar.getEstimatedRowCount()==0 && countAtt ==0))
                {
                adfLog.info("Post changes   "); */
            am.getDBTransaction().postChanges();
            //  }
        }
    }

    public void RemarksDisclosure(DisclosureEvent disclosureEvent) {
        // Add event code here...
        System.out.println("in the RemarksDisclosure methods");
        getBindings().getOperationBinding("Execute").execute();
        getBindings().getOperationBinding("Execute1").execute();
        getBindings().getOperationBinding("filterImage").execute();

    }

    public void fileDownloadAction(FacesContext facesContext, OutputStream outputStream) {
        // Add event code here...
        RichInputText bind = this.getFileBindName();
        if (bind != null) {
            System.out.println("come ----- 1");
            String path = bind.getValue().toString();

            System.out.println("path is:  " + path);
            try {
                FileInputStream in = new FileInputStream(path);
                System.out.println("Available bytes are:  " + in.available());
                if (in.available() <= 0) {
                    System.out.println("came in unavailable!");
                    FacesMessage msg = new FacesMessage("There is no data in the File !!");
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return;
                }
                byte b[] = new byte[in.available()];
                int i = in.read(b);
                outputStream.write(b);

                outputStream.flush();
                outputStream.close();
                in.close();
                facesContext.getCurrentInstance().responseComplete();
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
                FacesMessage msg = new FacesMessage("File Not Found in the Directory!!");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (IOException e) {
                System.out.println("IO Exception occur------");
                FacesMessage msg = new FacesMessage("The File is corrupted!!");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }


    public void setFileBindName(RichInputText fileBindName) {
        this.fileBindName = fileBindName;
    }

    public RichInputText getFileBindName() {
        return fileBindName;
    }

    public String replicateAllOrg() {
        adfLog.info("now the value of item is " + applyTaxRlBind.getValue() + "tax rule value is " +
                    taxRlBind.getValue());
        if (applyTaxRlBind.getValue() != null && applyTaxRlBind.getValue() != "" &&
            ((Boolean) applyTaxRlBind.getValue())) {

            if (taxRlBind.getValue() == null || taxRlBind.getValue() == "") {
                adfLog.info("tax rule value is " + taxRlBind.getValue());
                FacesMessage msg = new FacesMessage("Select Tax Rule ");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(taxRlBind.getClientId(), msg);
            } else {
                adfLog.info("tax rule value is in else part " + taxRlBind.getValue());
                showPopup(orgReplicatePopup, true);
            }
        } else {
            showPopup(orgReplicatePopup, true);
        }
        return null;
    }

    public void setOrgReplicatePopup(RichPopup orgReplicatePopup) {
        this.orgReplicatePopup = orgReplicatePopup;
    }

    public RichPopup getOrgReplicatePopup() {
        return orgReplicatePopup;
    }

    public void replicateAllOrgDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equalsIgnoreCase("yes")) {
            adfLog.info("apply tax rule value is " + applyTaxRlBind.getValue());


            Integer retV = 0;
            OperationBinding countOpr = getBindings().getOperationBinding("replicateAllOrgFoItm");
            countOpr.execute();
            if (countOpr.getResult() != null) {
                retV = Integer.parseInt(countOpr.getResult().toString());
            }
            adfLog.info("  return  value    " + retV);
            if (retV == 1) {
                adfLog.info("1 :::::::");
                FacesMessage msg = new FacesMessage("All Organisation added sussessfully.");
                msg.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext.getCurrentInstance().addMessage(null, msg);

            } else {
                adfLog.info("some error in replication data for all org");
            }
        }
    }

    public void setValuationBindVar(RichSelectOneChoice valuationBindVar) {
        this.valuationBindVar = valuationBindVar;
    }

    public RichSelectOneChoice getValuationBindVar() {
        return valuationBindVar;
    }


    public void setApplyTaxRlBind(RichSelectBooleanCheckbox applyTaxRlBind) {
        this.applyTaxRlBind = applyTaxRlBind;
    }

    public RichSelectBooleanCheckbox getApplyTaxRlBind() {
        return applyTaxRlBind;
    }

    public void setTaxRlBind(RichInputListOfValues taxRlBind) {
        this.taxRlBind = taxRlBind;
    }

    public RichInputListOfValues getTaxRlBind() {
        return taxRlBind;
    }

    public void ConsumableFlg(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if (valueChangeEvent.getNewValue().equals(true)) {
            serviceFlg.setValue(false);
            //  stockableFlg.setValue(true);
        }
    }

    public void setItmAttTypeVar(RichInputListOfValues itmAttTypeVar) {
        this.itmAttTypeVar = itmAttTypeVar;
    }

    public RichInputListOfValues getItmAttTypeVar() {
        return itmAttTypeVar;
    }

    public void setItmAttValVar(RichInputListOfValues itmAttValVar) {
        this.itmAttValVar = itmAttValVar;
    }

    public RichInputListOfValues getItmAttValVar() {
        return itmAttValVar;
    }

    public void ImageDownloadAction(FacesContext facesContext, OutputStream outputStream) {
        // Add event code here...
        adfLog.info("Value of Image Path:::::" + getImagePathBind());
        RichInputText bind = this.getImagePathBind();
        if (bind != null) {
            System.out.println("come ----- 1");
            String path = bind.getValue().toString();

            System.out.println("path is:  " + path);
            try {
                FileInputStream in = new FileInputStream(path);
                System.out.println("Available bytes are:  " + in.available());
                if (in.available() <= 0) {
                    System.out.println("came in unavailable!");
                    FacesMessage msg = new FacesMessage("There is no data in the File !!");
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return;
                }
                byte b[] = new byte[in.available()];
                int i = in.read(b);
                outputStream.write(b);

                outputStream.flush();
                outputStream.close();
                in.close();
                facesContext.getCurrentInstance().responseComplete();
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
                FacesMessage msg = new FacesMessage("File Not Found in the Directory!!");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (IOException e) {
                System.out.println("IO Exception occur------");
                FacesMessage msg = new FacesMessage("The File is corrupted!!");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }

    }

    public void setImagePathBind(RichInputText imagePathBind) {
        this.imagePathBind = imagePathBind;
    }

    public RichInputText getImagePathBind() {
        return imagePathBind;
    }

    public void ItmAttTypeVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...\
        getBindings().getOperationBinding("resetItmAttVal").execute();


    }

    public void selfLifeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        
        if(object!=null){
            if(((Integer)object).compareTo(new Integer(0)) < 0){
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Value must be positive.",
                                                              null));
            }
        }

    }
}
