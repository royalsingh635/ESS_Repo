package vendorpricelevel.view.bean;

import java.math.BigDecimal;

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
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelLabelAndMessage;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;

import vendorpricelevel.model.services.vendorpriceAMImpl;
import vendorpricelevel.model.views.MmEoItmVORowImpl;

public class VendorpricelevelBean {
    private RichInputText minQtybind;

    private RichSelectOneChoice tolerenceType;
    private RichInputDate inActiveDt;
    private RichInputText inActiveReason;
    private RichSelectOneChoice itmUomBind;
    private RichTable tableBind;
    private RichInputText itemPriceBind;
    private RichInputText discValueBind;
    Number zero = new Number(0);
    private static ADFLogger adfLog = (ADFLogger) ADFLogger.createADFLogger(VendorpricelevelBean.class);
    private RichPanelLabelAndMessage panelLabelDiscBind;
    private RichInputListOfValues itmDescBinding;
    private RichInputText itmIdBind;
    private RichSelectOneChoice itmUomBsBinding;
    private RichInputListOfValues suppCurrbinding;

    public VendorpricelevelBean() {
    }
    private boolean form_readonly = true;
    private boolean createbutton = false;
    private boolean saveButton = true;
    private boolean editButton = false;

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void createButton(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Createwithparameters");
        operationBinding.execute();
        vendorpriceAMImpl am = (vendorpriceAMImpl) resolvElDC("vendorpriceAMDataControl");
        Integer curr = (Integer) am.getAppEoPrf().getCurrentRow().getAttribute("SuppCurrId");
        am.getMmEoItm().getCurrentRow().setAttribute("CurrIdSp", curr);
        this.form_readonly = false;
        this.createbutton = true;
        this.saveButton = false;
        this.editButton = true;
        tableBind.setRowSelection(RichTable.ROW_SELECTION_NONE);

    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public void saveButton(ActionEvent actionEvent) {
        System.out.println("save action");
        vendorpriceAMImpl am = (vendorpriceAMImpl) resolvElDC("vendorpriceAMDataControl");
        ViewObject v1 = am.getMmEoItm();
        Row row = v1.getCurrentRow();
        if (row.getAttribute("CurrIdSp") != null) {
            Number currconvfctr = new Number(0);
            if (row.getAttribute("TransCurrConvFctr") != null) {
                currconvfctr = (Number) row.getAttribute("TransCurrConvFctr");
            }
            if (currconvfctr.compareTo(new Number(0)) > 0) {
                if (row.getAttribute("ItmUom") == null) {
                    System.out.println("itm uom-------------");
                    FacesContext fc = FacesContext.getCurrentInstance();
                    FacesMessage msg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.379']}").toString());
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    fc.addMessage(itmUomBind.getClientId(), msg);

                } else {
                    System.out.println("save---------");
                    //if any changes made then insert into history
                    /* 2-Modified
             0-New
             1-Unmodified
            -1-Initialized */
                    MmEoItmVORowImpl rowimpl = (MmEoItmVORowImpl) row;

                    Integer state = new Integer(rowimpl.getEntity(0).getEntityState());
                    System.out.println("state=" + state);
                    if (state.equals(new Integer(2))) {


                        Integer ret = (Integer) (callStoredFunction(Types.INTEGER, "MM.MM_INS_EO_ITM_HIST(?,?,?,?,?)", new Object[] {
                                                                    rowimpl.getCldId(), rowimpl.getSlocId(),
                                                                    rowimpl.getOrgId(), rowimpl.getEoId(),
                                                                    rowimpl.getItmId()
                        }));

                    }

                    // Integer tempDflt=new Integer(0);
                    String dfltSupp = rowimpl.getDfltSuppFlg(); //DfltSuppFlg
                    if ("Y".equalsIgnoreCase(dfltSupp)) {
                        Integer tempDflt =
                            (Integer) (callStoredFunction(Types.INTEGER, "MM.MM_CHK_ITM_DFLT_EO(?,?,?,?,?)", new Object[] {
                                                          rowimpl.getCldId(), rowimpl.getSlocId(), rowimpl.getOrgId(),
                                                          rowimpl.getEoId(), rowimpl.getItmId()
                        }));
                    }
                    BindingContainer bindings = getBindings();
                    OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                    operationBinding.execute();
                    FacesContext fc = FacesContext.getCurrentInstance();
                    FacesMessage msg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.91']}").toString());
                    msg.setSeverity(FacesMessage.SEVERITY_INFO);
                    fc.addMessage(null, msg);
                    if (operationBinding.getErrors().isEmpty()) {
                        System.out.println("222222222222save---------");
                        this.form_readonly = true;
                        this.createbutton = false;
                        this.saveButton = true;
                        this.editButton = false;
                    }
                    tableBind.setRowSelection(RichTable.ROW_SELECTION_SINGLE);
                }
            } else {
                //String msg2="Currency not valid or Conversion not define for this Currency.";
                String msg1 = resolvElDCMsg("#{bundle['MSG.2236']}").toString();
                FacesContext fc = FacesContext.getCurrentInstance();
                FacesMessage msg = new FacesMessage(msg1);
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                fc.addMessage(suppCurrbinding.getClientId(), msg);
            }
        } else {
            //String msg2="Please select Supplier's Currency";
            String msg1 = resolvElDCMsg("#{bundle['MSG.2237']}").toString();
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage msg = new FacesMessage(msg1);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(suppCurrbinding.getClientId(), msg);

        }
    }


    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {
            vendorpriceAMImpl am = (vendorpriceAMImpl) resolvElDC("vendorpriceAMDataControl");
            st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 2, bindVars[z]);
                    // System.out.println(bindVars[z] + "z");
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
                }
            }
        }
    }


    public void cancelButton(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        vendorpriceAMImpl am = (vendorpriceAMImpl) resolvElDC("vendorpriceAMDataControl");

        DCIteratorBinding parentIter = (DCIteratorBinding) bindings.get("AppEoPrfIterator");
        Key parentKey = null;
        //am.getAppEoPrf().setRangeSize(-1);
        int index = 0;
        // Row r = am.getAppEoPrf().getCurrentRow();

        if (parentIter.getCurrentRow() != null) {
            parentKey = parentIter.getCurrentRow().getKey();
            index = parentIter.getCurrentRowIndexInRange();
        }
        System.out.println("Index  " + index + "==KEY--" + parentKey);


        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        if (parentKey != null) {

            parentIter.setCurrentRowIndexInRange(index);
            /*  if(parentIter.getRowSetIterator().getRow(parentKey)!=null){  //check condition else gives exception in add mode.
            parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
             } */
        }

        this.form_readonly = true;
        this.createbutton = false;
        this.saveButton = true;
        this.editButton = false;
        tableBind.setRowSelection(RichTable.ROW_SELECTION_SINGLE);
    }


    public void discountValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        //   AdfFacesContext.getCurrentInstance().addPartialTarget(discType);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemPriceBind);
        Number itmPrice = (Number) itemPriceBind.getValue();
        Number zero = new Number(0);

        if (itmPrice.compareTo(zero) == 0) {
            oracle.jbo.domain.Number val = (oracle.jbo.domain.Number) object;
            if (val.compareTo(zero) != 0) {

                String msg = resolvElDCMsg("#{bundle['MSG.380']}").toString();

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }


        } else {


            if (object != null) {
                oracle.jbo.domain.Number val = (oracle.jbo.domain.Number) object;
                oracle.jbo.domain.Number zeroVal = new oracle.jbo.domain.Number(0);
                oracle.jbo.domain.Number hundredVal = new oracle.jbo.domain.Number(100);
                AdfFacesContext.getCurrentInstance().addPartialTarget(itemPriceBind);


                System.out.println("itmPrice" + itmPrice);

                if (val.compareTo(zeroVal) == -1) {
                    String msg = resolvElDCMsg("#{bundle['MSG.383']}").toString();

                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                } else if (val.compareTo(hundredVal) == 1) {
                    String msg = resolvElDCMsg("#{bundle['MSG.263']}").toString();

                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }


            }

        }
    }

    public void QtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number Qty = (oracle.jbo.domain.Number) object;
            oracle.jbo.domain.Number zeroVal = new oracle.jbo.domain.Number(0);
            if (Qty.compareTo(zeroVal) == -1) {
                String msg = resolvElDCMsg("#{bundle['MSG.337']}").toString();

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }
    }


    public void setForm_readonly(boolean form_readonly) {
        this.form_readonly = form_readonly;
    }

    public boolean isForm_readonly() {
        return form_readonly;
    }

    public void setCreatebutton(boolean createbutton) {
        this.createbutton = createbutton;
    }

    public boolean isCreatebutton() {
        return createbutton;
    }

    public void setSaveButton(boolean saveButton) {
        this.saveButton = saveButton;
    }

    public boolean isSaveButton() {
        return saveButton;
    }

    public void editButton(ActionEvent actionEvent) {
        vendorpriceAMImpl am = (vendorpriceAMImpl) resolvElDC("vendorpriceAMDataControl");
        ViewObject v1 = am.getMmEoItm();
        Row row = v1.getCurrentRow();
        System.out.println("Curr Key=" + row.getKey());
        this.form_readonly = false;
        this.createbutton = true;
        this.saveButton = false;
        this.editButton = true;
        tableBind.setRowSelection(RichTable.ROW_SELECTION_NONE);
    }

    public void setMinQtybind(RichInputText minQtybind) {
        this.minQtybind = minQtybind;
    }

    public RichInputText getMinQtybind() {
        return minQtybind;
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

    public void activeVC(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            String oldval = vce.getOldValue().toString();

            String newVal = vce.getNewValue().toString();

            vendorpriceAMImpl am = (vendorpriceAMImpl) resolvElDC("vendorpriceAMDataControl");
            ViewObject v1 = am.getMmEoItm();
            Row row = v1.getCurrentRow();
            if (newVal.equalsIgnoreCase("true")) {
                row.setAttribute("InactvDt", null);
                row.setAttribute("InactvResn", null);
                inActiveReason.setValue("");
                inActiveReason.setValue(null);
                AdfFacesContext.getCurrentInstance().addPartialTarget(inActiveDt);
                AdfFacesContext.getCurrentInstance().addPartialTarget(inActiveReason);


            } else if (newVal.equalsIgnoreCase("false")) {
                Date dt = (Date) Date.getCurrentDate();
                row.setAttribute("InactvDt", dt);
                inActiveDt.setValue(dt);
                AdfFacesContext.getCurrentInstance().addPartialTarget(inActiveDt);
                AdfFacesContext.getCurrentInstance().addPartialTarget(inActiveReason);
            }

        }
    }


    public void tlrncValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (tolerenceType.getValue() == null) {
            String msg = resolvElDCMsg("#{bundle['MSG.384']}").toString();

            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
        } else {
            if (object != null) {
                oracle.jbo.domain.Number val = (oracle.jbo.domain.Number) object;
                oracle.jbo.domain.Number zeroVal = new oracle.jbo.domain.Number(0);
                oracle.jbo.domain.Number hundredVal = new oracle.jbo.domain.Number(100);
                if (tolerenceType.getValue().equals("A")) {
                    if (val.compareTo(zeroVal) == -1) {
                        String msg = resolvElDCMsg("#{bundle['MSG.382']}").toString();

                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                    }
                } else if (tolerenceType.getValue().equals("P")) {
                    if (val.compareTo(zeroVal) == -1) {
                        String msg = resolvElDCMsg("#{bundle['MSG.383']}").toString();

                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                    } else if (val.compareTo(hundredVal) == 1) {
                        String msg = resolvElDCMsg("#{bundle['MSG.263']}").toString();

                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                    }
                }


            }
        }
    }

    public void setTolerenceType(RichSelectOneChoice tolerenceType) {
        this.tolerenceType = tolerenceType;
    }

    public RichSelectOneChoice getTolerenceType() {
        return tolerenceType;
    }

    public void leadTimeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Integer Qty = (Integer) object;

            if (Qty < 0) {
                String msg = resolvElDCMsg("#{bundle['MSG.337']}").toString();

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }
    }

    public void setInActiveDt(RichInputDate inActiveDt) {
        this.inActiveDt = inActiveDt;
    }

    public RichInputDate getInActiveDt() {
        return inActiveDt;
    }

    public void setInActiveReason(RichInputText inActiveReason) {
        this.inActiveReason = inActiveReason;
    }

    public RichInputText getInActiveReason() {
        return inActiveReason;
    }

    public void setItmUomBind(RichSelectOneChoice itmUomBind) {
        this.itmUomBind = itmUomBind;
    }

    public RichSelectOneChoice getItmUomBind() {
        return itmUomBind;
    }

    public void setEditButton(boolean editButton) {
        this.editButton = editButton;
    }

    public boolean isEditButton() {
        return editButton;
    }

    public void setTableBind(RichTable tableBind) {
        this.tableBind = tableBind;
    }

    public RichTable getTableBind() {
        return tableBind;
    }

    public void setItemPriceBind(RichInputText itemPriceBind) {
        this.itemPriceBind = itemPriceBind;
    }

    public RichInputText getItemPriceBind() {
        return itemPriceBind;
    }


    public void setDiscValueBind(RichInputText discValueBind) {
        this.discValueBind = discValueBind;
    }

    public RichInputText getDiscValueBind() {
        return discValueBind;
    }


    public void setPanelLabelDiscBind(RichPanelLabelAndMessage panelLabelDiscBind) {
        this.panelLabelDiscBind = panelLabelDiscBind;
    }

    public RichPanelLabelAndMessage getPanelLabelDiscBind() {
        return panelLabelDiscBind;
    }

    public void priceValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number Qty = (oracle.jbo.domain.Number) object;
            oracle.jbo.domain.Number zeroVal = new oracle.jbo.domain.Number(0);
            if (Qty.compareTo(zeroVal) == -1) {
                String msg = resolvElDCMsg("#{bundle['MSG.335']}").toString();

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
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

    public void searchSupVCE(ValueChangeEvent valueChangeEvent) {
        //        // String hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        //        vendorpriceAMImpl am = (vendorpriceAMImpl) resolvElDC("vendorpriceAMDataControl");
        //        am.getAppEoPrf().setNamedWhereClauseParam("SupNameBind", valueChangeEvent.getNewValue());
        //        //  am.getAppEoPrf().setWhereClause("upper(EO_NM) like upper('%" + valueChangeEvent.getNewValue() + "%')");
        //        am.getAppEoPrf().executeQuery();
    }

    public void itmIdValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            vendorpriceAMImpl am = (vendorpriceAMImpl) resolvElDC("vendorpriceAMDataControl");
            System.out.println("Currnt in validator=" + am.getMmEoItm().getCurrentRow().getKey());
            ViewObjectImpl eoItm = am.getMmEoItm();
            Row curr = eoItm.getCurrentRow();

            ViewObjectImpl itmvo12 = am.getLovItmDescTune();
            itmvo12.setNamedWhereClauseParam("slocIdBind", curr.getAttribute("SlocId"));
            itmvo12.setNamedWhereClauseParam("cldIdBind", curr.getAttribute("CldId"));
            itmvo12.setNamedWhereClauseParam("hoOrgIdBind", curr.getAttribute("TransHoOrgId"));
            itmvo12.setNamedWhereClauseParam("ItmDescBind", object);
            itmvo12.executeQuery();
            adfLog.info("validator  executes");

            /*        RowQualifier rq = new RowQualifier(am.getItmIdLOV1());
            rq.setWhereClause("CldId='" + curr.getAttribute("CldId") + "' and SlocId=" + curr.getAttribute("SlocId") +
                              " and OrgId='" + curr.getAttribute("OrgId") + "' and HoOrgId='" +
                              curr.getAttribute("TransHoOrgId") + "' and ItmDesc='" + object + "'") */;
            Row[] fr = itmvo12.getFilteredRows("ItmDesc", object);

            Object itmId = null;
            if (fr.length > 0)
                itmId = fr[0].getAttribute("ItmId");
            if (itmId != null) {
                ViewObjectImpl itmvo = am.getLovItmIdTune();
                itmvo.setNamedWhereClauseParam("slocIdBind", curr.getAttribute("SlocId"));
                itmvo.setNamedWhereClauseParam("cldIdBind", curr.getAttribute("CldId"));
                itmvo.setNamedWhereClauseParam("hoOrgIdBind", curr.getAttribute("TransHoOrgId"));
                itmvo.setNamedWhereClauseParam("itmIdBind", itmId);
                itmvo.executeQuery();

                RowSetIterator rs = eoItm.createRowSetIterator(null);
                while (rs.hasNext()) {
                    Row rr1 = rs.next();
                    if (rr1 != curr) {
                        adfLog.info("itmid of bith attributes is " + rr1.getAttribute("ItmId") + "   " + itmId);
                        if (((String) rr1.getAttribute("ItmId")).equalsIgnoreCase(itmId.toString())) {
                            //String msg1="Item already exist.";
                            String msg1 = resolvElDCMsg("#{bundle['MSG.2261']}").toString();
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, null));
                        }
                    }
                }

                /*   Row r[] = am.getMmEoItm().getFilteredRows("ItmId", itmId);
                System.out.println("No of rows of this item=" + r.length);
                if (r.length > 0)
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Item already exist.",
                                                                  null));
               */ /*  for(Row row:r)
                    {
                        System.out.println();
                        System.out.println("duplicate rowkey="+row.getKey()+" and curent rowkey="+am.getMmEoItm().getCurrentRow().getKey());
                        if(!row.getKey().equals(am.getMmEoItm().getCurrentRow().getKey()))
                        {


                            }
                        }  */

            } else {
                //String msg2="Select valid Item.";
                String msg1 = resolvElDCMsg("#{bundle['MSG.2238']}").toString();
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, null));
            }

        }
    }

    public void itmIdVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            String hoOrg_id = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
            String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
            String org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            vendorpriceAMImpl am = (vendorpriceAMImpl) resolvElDC("vendorpriceAMDataControl");
            Row curr = am.getMmEoItm().getCurrentRow();
            ViewObjectImpl itmvo12 = am.getLovItmDescTune();
            itmvo12.setNamedWhereClauseParam("slocIdBind", curr.getAttribute("SlocId"));
            itmvo12.setNamedWhereClauseParam("cldIdBind", curr.getAttribute("CldId"));
            itmvo12.setNamedWhereClauseParam("hoOrgIdBind", curr.getAttribute("TransHoOrgId"));
            itmvo12.setNamedWhereClauseParam("ItmDescBind", valueChangeEvent.getNewValue());
            itmvo12.executeQuery();


            /*  RowQualifier rq = new RowQualifier(am.getItmIdLOV1());
            rq.setWhereClause("CldId='" + curr.getAttribute("CldId") + "' and SlocId=" + curr.getAttribute("SlocId") +
                              " and OrgId='" + curr.getAttribute("OrgId") + "' and HoOrgId='" +
                              curr.getAttribute("TransHoOrgId") + "' and ItmDesc='" + valueChangeEvent.getNewValue() +
                              "'");
            */Row[] fr = itmvo12.getFilteredRows("ItmDesc", valueChangeEvent.getNewValue());
            // adfLog.info(fr.length + " FIRST      " + rq.getExprStr());
            Object itmId = null;
            if (fr.length > 0) {
                itmId = fr[0].getAttribute("ItmId");
            }
            ViewObjectImpl itmvo = am.getLovItmIdTune();
            itmvo.setNamedWhereClauseParam("slocIdBind", curr.getAttribute("SlocId"));
            itmvo.setNamedWhereClauseParam("cldIdBind", curr.getAttribute("CldId"));
            itmvo.setNamedWhereClauseParam("hoOrgIdBind", curr.getAttribute("TransHoOrgId"));
            itmvo.setNamedWhereClauseParam("itmIdBind", itmId);
            itmvo.executeQuery();

            Row[] r = itmvo.getFilteredRows("ItmId", itmId);
            String uompur = null;
            String uombs = null;
            if (r.length > 0) {
                uompur = (String) r[0].getAttribute("UomPur");
                uombs = (String) r[0].getAttribute("UomBasic");
            }
            Number uomCnvr = zero;
            if (uompur != null && uombs != null) {

                BigDecimal ret =
                    (BigDecimal) (callStoredFunction(Types.NUMERIC, "APP.FN_GET_UOM_CONV_FCTR(?,?,?,?,?,?)", new Object[] {
                                                     sloc_id, cld_id, org_id, itmId, uompur, uombs
                }));
                adfLog.info(" 1convirsoin in bean " + ret);
                if (ret != null) {
                    Number retV = zero;
                    try {
                        retV = new Number(ret);
                    } catch (SQLException e) {
                    }
                    adfLog.info(" 2convirsoin in bean " + retV);
                    if ((zero.compareTo(retV) == 1) || (zero.compareTo(retV) == 0)) {
                        uomCnvr = zero;
                    } else {
                        uomCnvr = retV;
                    }

                }
            }
            adfLog.info(" 3convirsoin in bean " + uomCnvr);
            am.getMmEoItm().getCurrentRow().setAttribute("UomConvFctr", uomCnvr);
            /* adfLog.info(" total no of rows  "+am.getuomLOV1().getRowCount());
                RowQualifier rquom=new RowQualifier(am.getuomLOV1());
                    rquom.setWhereClause("CldId = '"+cld_id+"' and SlocId = "+sloc_id+" and HoOrgId = '"+hoOrg_id+"' and OrgId = '"+org_id+"' and ItmId='"+itmId+"' and UomId='"+uompur+"' and UomIdSrc='"+uombs+"'");
                    Row[] row=am.getuomLOV1().getFilteredRows(rquom);
                   adfLog.info(" rquom  "+rquom.getExprStr()+"  lenth "+row.length);
                    if(row.length>0)
                    {
                       am.getMmEoItm().getCurrentRow().setAttribute("UomConvFctr",  row[0].getAttribute("ConvFctr"));
                        } */
            // }
            adfLog.info("itm value change listner executes");
            Number price = new Number(0);
            if (am.getMmEoItm().getCurrentRow().getAttribute("ItmPrice") != null)
                price = (Number) am.getMmEoItm().getCurrentRow().getAttribute("ItmPrice");
            Number uomconv = new Number(1);
            if (am.getMmEoItm().getCurrentRow().getAttribute("UomConvFctr") != null)
                uomconv = (Number) am.getMmEoItm().getCurrentRow().getAttribute("UomConvFctr");
            am.getMmEoItm().getCurrentRow().setAttribute("ItmPriceUomBs", price.multiply(uomconv));
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmUomBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmUomBsBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(tableBind);
        }
        //
        //  AdfFacesContext.getCurrentInstance().addPartialTarget(itmIdBind);

    }

    public void itmUomVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            String hoOrg_id = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
            String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
            String org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            vendorpriceAMImpl am = (vendorpriceAMImpl) resolvElDC("vendorpriceAMDataControl");
            RowQualifier rquom = new RowQualifier(am.getuomLOV1());
            rquom.setWhereClause("CldId = '" + cld_id + "' and SlocId = " + sloc_id + " and HoOrgId = '" + hoOrg_id +
                                 "' and OrgId = '" + org_id + "' and ItmId='" +
                                 am.getMmEoItm().getCurrentRow().getAttribute("ItmId") + "' and UomId='" +
                                 valueChangeEvent.getNewValue() + "' and UomIdSrc='" +
                                 am.getMmEoItm().getCurrentRow().getAttribute("ItmUomBs") + "'");
            Row[] row = am.getuomLOV1().getFilteredRows(rquom);
            System.out.println(" rquom1  " + rquom.getExprStr() + "  lenth1 " + row.length);
            if (row.length > 0) {
                am.getMmEoItm().getCurrentRow().setAttribute("UomConvFctr", row[0].getAttribute("ConvFctr"));
            }
            Number price = new Number(0);
            if (am.getMmEoItm().getCurrentRow().getAttribute("ItmPrice") != null)
                price = (Number) am.getMmEoItm().getCurrentRow().getAttribute("ItmPrice");
            Number uomconv = new Number(1);
            if (am.getMmEoItm().getCurrentRow().getAttribute("UomConvFctr") != null) {
                Number uomC = (Number) am.getMmEoItm().getCurrentRow().getAttribute("UomConvFctr");
                if (uomC.compareTo(new Number(0)) == 1) {
                    uomconv = (Number) am.getMmEoItm().getCurrentRow().getAttribute("UomConvFctr");
                }
            }
            //am.getMmEoItm().getCurrentRow().setAttribute("ItmPriceUomBs", price.multiply(uomconv));
            am.getMmEoItm().getCurrentRow().setAttribute("ItmPriceUomBs", price.divide(uomconv));
        }
    }

    public void priceVCE(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void setItmDescBinding(RichInputListOfValues itmDescBinding) {
        this.itmDescBinding = itmDescBinding;
    }

    public RichInputListOfValues getItmDescBinding() {
        return itmDescBinding;
    }

    public void setItmIdBind(RichInputText itmIdBind) {
        this.itmIdBind = itmIdBind;
    }

    public RichInputText getItmIdBind() {
        return itmIdBind;
    }

    public void setItmUomBsBinding(RichSelectOneChoice itmUomBsBinding) {
        this.itmUomBsBinding = itmUomBsBinding;
    }

    public RichSelectOneChoice getItmUomBsBinding() {
        return itmUomBsBinding;
    }

    public void setSuppCurrbinding(RichInputListOfValues suppCurrbinding) {
        this.suppCurrbinding = suppCurrbinding;
    }

    public RichInputListOfValues getSuppCurrbinding() {
        return suppCurrbinding;
    }

    public List getSuggestions(String input) {

        // create a new list to hold matching items
        List<SelectItem> items = new ArrayList<SelectItem>();
        OperationBinding binding = getBindings().getOperationBinding("getSuggestedItemDesc");
        binding.getParamsMap().put("itmStr", input);
        binding.execute();
        Object object = binding.getResult();
        System.out.println("Item Query Executed");
        if (object != null) {
            for (String item : (ArrayList<String>) object) {
                items.add(new SelectItem(item));
            }
        }
        return items;
    }

}
