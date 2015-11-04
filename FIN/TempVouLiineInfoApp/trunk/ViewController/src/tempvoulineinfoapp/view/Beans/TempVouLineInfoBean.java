package tempvoulineinfoapp.view.Beans;

import java.sql.SQLException;

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

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;

import tempvoulineinfoapp.model.service.TempVouLineInfoAppImpl;

public class TempVouLineInfoBean {
    private RichInputListOfValues tvouIsbnNoBind;
    private RichPanelFormLayout formBind;
    private RichInputListOfValues isbnNoBind;
    private RichInputText tvouPkts;
    private RichInputText tvouPrintNo;
    private RichInputText tvouQty;
    private RichInputText tvouRems;
    private RichInputText tvouSheets;
    private RichInputText tvouGross;
    private RichInputText tvouAmtSp;
    private RichTable tableBinding;
    private int count=0;

    public TempVouLineInfoBean() {
    }
    private static final String amName = "TempVouLineInfoAppDataControl";
    Integer amt_digit = Integer.parseInt(resolvElAmt("#{pageFlowScope.GLBL_AMT_DIGIT}").toString());

    public Object resolvElAmt(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        Object digit = 2;
        if (valueExp != null) {
            digit = valueExp.getValue(elContext);
        }
        if (digit == null) {
            digit = 2;
        }

        return digit;
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

    public TempVouLineInfoAppImpl getAm() {
        TempVouLineInfoAppImpl am = (TempVouLineInfoAppImpl)resolvElDC(amName);
        return am;
    }

    public Object resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        Object Message = valueExp.getValue(elContext);
        return Message;
    }

    public void createTvouLineInfo(ActionEvent actionEvent) {


        // getting the key of current row in tvou and tvouline to reset the row when cancel is presssed in tvoulineinfo page


        BindingContainer bindings = getBindings();
     //   DCIteratorBinding iterator = (DCIteratorBinding)bindings.get("TvouLinesInfoIterator");

        // method to get max serial no and set new serial no as max+1

        OperationBinding binding = bindings.getOperationBinding("CreateInsert");
        binding.execute();
        count++;

        Integer Srno = 0;
        Integer max = 0;
        ViewObjectImpl vo = getAm().getTvouLinesInfo();
        Row row[] = vo.getAllRowsInRange();
        for (Row r : row) {
            try {
                Srno = Integer.parseInt(r.getAttribute("TvouSlNo").toString());
            } catch (NullPointerException e) {
                Srno = 0;

            }

            if (Srno > max) {
                max = Srno;
            }

        }

        max = max + 1;
        System.out.println("max------>" + max);

        // setting attribute TvouSlno
        vo.getCurrentRow().setAttribute("TvouSlNo", max);


        Integer currSp = Integer.parseInt(resolvEl("#{pageFlowScope.PARAM_CURR_ID_SP}").toString());
        if (currSp != null) {
            System.out.println("in if when not equal to null");
            vo.getCurrentRow().setAttribute("TvouCurrIdSp", currSp);

            // createRow.setAttribute("TvouAmtBs", baseAmt);
            System.out.println("after setting attribute");
        }

        //    rowSetIterator.closeRowSetIterator();
        System.out.println("after close row set iterator");
        AdfFacesContext.getCurrentInstance().addPartialTarget(formBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(isbnNoBind); 

    }


    public void AmountSpValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number amount = (Number)object;
            if (amount.compareTo(0) == -1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.489']}").toString(),
                                                              resolvElDCMsg("#{bundle['MSG.490']}").toString()));
            }

        }
    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public void specificAmountValueChangeListner(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            Number rate = new Number(1);
            ViewObjectImpl vo = getAm().getTvouLinesInfo();
            Row info_cur_row = vo.getCurrentRow();
            if (info_cur_row.getAttribute("TvouCc") != null) {
                rate = (Number)info_cur_row.getAttribute("TvouCc");
            }

            System.out.println("rate = " + rate);
            // method to calculate base amount by multiplying spacific amount and currency rate
            Number spAmount = (Number)valueChangeEvent.getNewValue();
            Number base = spAmount.multiply(rate);

            Number baseAmt = (Number)base.round(amt_digit);

            // setting base amount to the tvoulineinfo table in current row
            info_cur_row.setAttribute("TvouAmtBs", baseAmt);

        }
    }

    public String deleteInfo() {

        BindingContainer bindings = getBindings();

        OperationBinding ob = bindings.getOperationBinding("Delete");
        ob.execute();
        count--;

        return null;
    }


    public void setTvouIsbnNoBind(RichInputListOfValues tvouIsbnNoBind) {
        this.tvouIsbnNoBind = tvouIsbnNoBind;
    }

    public RichInputListOfValues getTvouIsbnNoBind() {
        return tvouIsbnNoBind;
    }

    public void setFormBind(RichPanelFormLayout formBind) {
        this.formBind = formBind;
    }

    public RichPanelFormLayout getFormBind() {
        return formBind;
    }

    public void setIsbnNoBind(RichInputListOfValues isbnNoBind) {
        this.isbnNoBind = isbnNoBind;
    }

    public RichInputListOfValues getIsbnNoBind() {
        return isbnNoBind;
    }

    public void TvouIsbnNoValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null) {
            String isbn = object.toString();
            System.out.println("isbn=" + isbn);
            ViewObjectImpl vo = getAm().getTvouLinesInfo();
            Integer coaid = null;
            String orgid = null;
            Integer slocid = null;
            String hoid = null;
            String cldid = null;
            String vouid=null;
            if (vo != null) {
                if (resolvEl("#{pageFlowScope. GLBL_APP_CLD_ID}") != null) {
                    cldid = (String)resolvEl("#{pageFlowScope. GLBL_APP_CLD_ID}");
                }
                if (resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}") != null) {
                    hoid = (String)resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
                }
                if (resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}") != null) {
                    slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
                }
                if (resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}") != null) {
                    orgid = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
                }
                if (resolvEl("#{pageFlowScope.PARAM_COA_ID}") != null) {
                    coaid = Integer.parseInt(resolvEl("#{pageFlowScope.PARAM_COA_ID}").toString());
                }
               
                if(resolvEl("#{pageFlowScope.PARAM_VOU_ID}")!= null){
                    vouid = (String)resolvEl("#{pageFlowScope.PARAM_VOU_ID}");
                }
                RowQualifier rq = new RowQualifier(vo);
                rq.setWhereClause("TvouCldId='"+cldid+"' and TvouSlocId="+slocid+" and TvouHoOrgId='"+hoid+"' and TvouOrgId ='"+orgid+"' and TvouId='"+vouid+"' and TvouCoaId="+coaid+" and TvouIsbnNo ='"+isbn+"' ");
                Row[] filteredRows = vo.getFilteredRows(rq);
                System.out.println("query----"+rq.getExprStr());
                System.out.println("filteredRows.length = " + filteredRows.length);

                if (filteredRows.length > 0 && filteredRows[0]!=vo.getCurrentRow()) {

                    System.out.println("Error ");
                    String msg2 = "Duplicate  Entry exist !!!!";

                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg2, null));
                }
            }
        }
        else
           // throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selection Required.", null));

        AdfFacesContext.getCurrentInstance().addPartialTarget(tableBinding);
    }


    public void negativeValueValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null&& tvouPkts.getValue()!=null) {
            Number pkts = (Number)tvouPkts.getValue();
            if (pkts.compareTo(0) >= 0) {

            } else {
               // pkts = 0;
                String msg2 = "Negative Value can not Entered!!!!";

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg2, null));
            }
        }

    }

    public void setTvouPkts(RichInputText tvouPkts) {
        this.tvouPkts = tvouPkts;
    }

    public RichInputText getTvouPkts() {
        return tvouPkts;
    }

    public void setTvouPrintNo(RichInputText tvouPrintNo) {
        this.tvouPrintNo = tvouPrintNo;

    }

    public RichInputText getTvouPrintNo() {
        return tvouPrintNo;
    }

    public void tvouPrintNoValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && tvouPrintNo.getValue()!=null) {
            String print = tvouPrintNo.getValue().toString();
            if (print.length() > 0) {

            } else {
                print = "0";
                String msg2 = "Negative Value can not Entered!!!!";

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg2, null));
            }
        }

    }

    public void setTvouQty(RichInputText tvouQty) {
        this.tvouQty = tvouQty;
    }

    public RichInputText getTvouQty() {
        return tvouQty;
    }

    public void TvouQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && tvouQty.getValue()!=null) {
            Number qty = (Number)tvouQty.getValue();
            //Number qty=(Number)tvouQty.getValue();
            if (qty.compareTo(0) >=0){
              System.out.println("Im am in validator of quantity");
            } else {
             //   qty = 0;
                String msg2 = "Negative Value can not Entered!!!!";

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg2, null));
            }
        }

    }

    public void setTvouRems(RichInputText tvouRems) {
        this.tvouRems = tvouRems;
    }

    public RichInputText getTvouRems() {
        return tvouRems;
    }

    public void TvouRemsValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && tvouRems.getValue()!=null) {
            Number rems = (Number)tvouRems.getValue();
            if (rems.compareTo(0) >= 0) {

            } else {
             //   rems = 0;
                String msg2 = "Negative Value can not Entered!!!!";

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg2, null));
            }
        }

    }

    public void setTvouSheets(RichInputText tvouSheets) {
        this.tvouSheets = tvouSheets;
    }

    public RichInputText getTvouSheets() {
        return tvouSheets;
    }

    public void tvouSheetsValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && tvouSheets.getValue()!=null) {
            Number Sheets = (Number)tvouSheets.getValue();
            if (Sheets.compareTo(0) >= 0) {

            } else {
             //   Sheets = 0;
                String msg2 = "Negative Value can not Entered!!!!";

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg2, null));
            }
        }

    }

    public void setTvouGross(RichInputText tvouGross) {
        this.tvouGross = tvouGross;
    }

    public RichInputText getTvouGross() {
        return tvouGross;
    }

    public void TvouGrossValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && tvouGross.getValue()!=null) {
            Number gross = (Number)tvouGross.getValue();
            if (gross.compareTo(0) >= 0) {

            } else {
               // gross = 0;
                String msg2 = "Negative Value Can not Entered!!!!";

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg2, null));
            }
        }

    }

    public void setTvouAmtSp(RichInputText tvouAmtSp) {
        this.tvouAmtSp = tvouAmtSp;
    }

    public RichInputText getTvouAmtSp() {
        return tvouAmtSp;
    }

    public void TvouAmtSpValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && tvouAmtSp.getValue()!=null) {
            Number AmtSp = (Number)tvouAmtSp.getValue();
            if (AmtSp.compareTo(0)>=0) {

            } else {
               //AmtSp = new Number(0);
                String msg2 = "Negative Value can not Entered!!!!";

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg2, null));
            }

        }
    }


    public void setTableBinding(RichTable tableBinding) {
        this.tableBinding = tableBinding;
    }

    public RichTable getTableBinding() {
        return tableBinding;
    }

    public String backAction() {
        System.out.println("count= "+count);
        if (count>0) {
            if (isbnNoBind.getValue() != null) {
                System.out.println("in back");
                return "back";

            } else {
                System.out.println("in else");
                FacesMessage message = new FacesMessage("Selection Required");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(isbnNoBind.getClientId(), message);
                return null;

            }
           
        }
        return "back";

    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
