package revenueprd.view.bean;

import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.domain.Number;
import oracle.jbo.server.ViewObjectImpl;

import revenueprd.model.module.RevenuePrdAMImpl;

public class RevenuePrd {
    private oracle.jbo.domain.Number tot = new oracle.jbo.domain.Number(0);
    private  oracle.jbo.domain.Number mstBstot = new oracle.jbo.domain.Number(0);
    private oracle.jbo.domain.Number prdBstot = new oracle.jbo.domain.Number(0);
    private String Dcmode="Dr";
    private String prdDcmode="Dr";
    private RichInputText bindCoaNm;
    private RichInputDate bindStrDt;
    private RichInputDate bindEndDt;
    private RichPanelFormLayout searchForm;
    private RichInputText vouBindId;
    private RichInputDate transStrDtBind;
    private RichInputDate transEndDtBind;
    private RichInputText transVouNoBind;
    private RichTable prdTableBind;
    private RichTable revMstTablebind;


    public RevenuePrd() {
    }

    public void setTot(oracle.jbo.domain.Number tot) {
        this.tot = tot;
    }

    public oracle.jbo.domain.Number getTot() {
        RevenuePrdAMImpl am = (RevenuePrdAMImpl)resolvElDC("RevenuePrdAMDataControl");
        ViewObjectImpl v = am.getRevenuePrd1();
        RowSetIterator rit = v.createRowSetIterator(null);
        oracle.jbo.domain.Number n = new oracle.jbo.domain.Number(0);
        while (rit.hasNext()) {
            Row row = rit.next();
            oracle.jbo.domain.Number m = (Number)row.getAttribute("GlAmtBs");
            n = n.add(m);

        }
        return n;
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
              String msg = valueExp.getValue(elContext).toString();
              return msg;
          }
    public String resetRevenueSearchAction() {
        RevenuePrdAMImpl am = (RevenuePrdAMImpl)resolvElDC("RevenuePrdAMDataControl");
        ViewObjectImpl revenueMstVo = am.getRevenueMstVO();
         bindCoaNm.setValue(null);
        bindCoaNm.setValue("");
        bindStrDt.setValue(null);
        bindStrDt.setValue("");
        bindEndDt.setValue(null);
        bindEndDt.setValue("");
        revenueMstVo.setNamedWhereClauseParam("BindCldId", resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}"));
       revenueMstVo.setNamedWhereClauseParam("BindSlocId", Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}")));
        revenueMstVo.setNamedWhereClauseParam("BindOrgId", resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}"));
        revenueMstVo.setNamedWhereClauseParam("BindHoOrgId", resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}"));
        revenueMstVo.setNamedWhereClauseParam("BindCoaNm", null);
        revenueMstVo.setNamedWhereClauseParam("BindStrDt", null);
        revenueMstVo.setNamedWhereClauseParam("BindEndDt", null);
       revenueMstVo.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindCoaNm);
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindStrDt);
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindEndDt);
        AdfFacesContext.getCurrentInstance().addPartialTarget(revMstTablebind);
        
       // resetValueInputItems(AdfFacesContext.getCurrentInstance(), searchForm);    
      return "reset";
    }
    public String resetRevenueDtlSearchAction() {   
        RevenuePrdAMImpl am = (RevenuePrdAMImpl)resolvElDC("RevenuePrdAMDataControl");
        ViewObjectImpl revenuePrdVo = am.getRevenuePrd1();
        revenuePrdVo.setNamedWhereClauseParam("BindStrDt", null);
        revenuePrdVo.setNamedWhereClauseParam("BindEndDt", null);
        revenuePrdVo.setNamedWhereClauseParam("VouIdBindVar", null);
        revenuePrdVo.executeQuery(); 
        transStrDtBind.setValue(null);
        transEndDtBind.setValue(null);
        transVouNoBind.setValue(null);
        am.getSearchVO1().executeQuery();
       /*  bindStrDt.setValue(null);
        bindStrDt.setValue("");
        bindEndDt.setValue(null);
        bindEndDt.setValue("");
        vouBindId.setValue(null);
        vouBindId.setValue(""); */
        AdfFacesContext.getCurrentInstance().addPartialTarget(prdTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(transStrDtBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(transEndDtBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(transVouNoBind);
        resetValueInputItems(AdfFacesContext.getCurrentInstance(), searchForm);
         return null;
    }

    private void resetValueInputItems(AdfFacesContext adfFacesContext, UIComponent component) {
        /** Get list of all components from search panel */
        List<UIComponent> items = component.getChildren();
        for (UIComponent item : items) {

            resetValueInputItems(adfFacesContext, item);
            /** Check if Input text , reset its value*/
            if (item instanceof RichInputText) {
                RichInputText input = (RichInputText)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
                /** Check if RichInputDate , reset its value*/
            } else if (item instanceof RichInputDate) {
                RichInputDate input = (RichInputDate)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }

                /* Check if SelectOneChoice , reset its value*/
            } else if (item instanceof RichSelectOneChoice) {
                RichSelectOneChoice input = (RichSelectOneChoice)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }

            }
        }
    }

    public void setBindCoaNm(RichInputText bindCoaNm) {
        this.bindCoaNm = bindCoaNm;
    }

    public RichInputText getBindCoaNm() {
        return bindCoaNm;
    }

    public void setBindStrDt(RichInputDate bindStrDt) {
        this.bindStrDt = bindStrDt;
    }

    public RichInputDate getBindStrDt() {
        return bindStrDt;
    }

    public void setBindEndDt(RichInputDate bindEndDt) {
        this.bindEndDt = bindEndDt;
    }

    public RichInputDate getBindEndDt() {
        return bindEndDt;
    }

    public void setSearchForm(RichPanelFormLayout searchForm) {
        this.searchForm = searchForm;
    }

    public RichPanelFormLayout getSearchForm() {
        return searchForm;
    }


    public void setVouBindId(RichInputText vouBindId) {
        this.vouBindId = vouBindId;
    }

    public RichInputText getVouBindId() {
        return vouBindId;
    }

    public void revenuePrdSearchAction(ActionEvent actionEvent) {
        System.out.println("value of trans str dt="+transStrDtBind.getValue());
        System.out.println("value of trans end dt="+transEndDtBind.getValue());
        System.out.println("value of trans vou no="+transVouNoBind.getValue());
        RevenuePrdAMImpl am = (RevenuePrdAMImpl)resolvElDC("RevenuePrdAMDataControl");
        ViewObjectImpl revenuePrdVo = am.getRevenuePrd1();
        revenuePrdVo.setNamedWhereClauseParam("BindStrDt", transStrDtBind.getValue());
        revenuePrdVo.setNamedWhereClauseParam("BindEndDt", transEndDtBind.getValue());
        revenuePrdVo.setNamedWhereClauseParam("VouIdBindVar", transVouNoBind.getValue());
        revenuePrdVo.setNamedWhereClauseParam("BindCldId", resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}"));
        revenuePrdVo.setNamedWhereClauseParam("BindSlocId", Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}")));
        revenuePrdVo.setNamedWhereClauseParam("BindHoOrgId", resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}"));
        revenuePrdVo.executeQuery(); 
        AdfFacesContext.getCurrentInstance().addPartialTarget(prdTableBind);
    }

    public void setTransStrDtBind(RichInputDate transStrDtBind) {
        this.transStrDtBind = transStrDtBind;
    }

    public RichInputDate getTransStrDtBind() {
        return transStrDtBind;
    }

    public void setTransEndDtBind(RichInputDate transEndDtBind) {
        this.transEndDtBind = transEndDtBind;
    }

    public RichInputDate getTransEndDtBind() {
        return transEndDtBind;
    }

    public void setTransVouNoBind(RichInputText transVouNoBind) {
        this.transVouNoBind = transVouNoBind;
    }

    public RichInputText getTransVouNoBind() {
        return transVouNoBind;
    }

    public void setPrdTableBind(RichTable prdTableBind) {
        this.prdTableBind = prdTableBind;
    }

    public RichTable getPrdTableBind() {
        return prdTableBind;
    }

    public void setMstBstot(Number mstBstot) {
        this.mstBstot = mstBstot;
    }

    public Number getMstBstot() {
        RevenuePrdAMImpl am = (RevenuePrdAMImpl)resolvElDC("RevenuePrdAMDataControl");
                ViewObjectImpl vo = am.getRevenueMstVO();
                RowSetIterator rit = vo.createRowSetIterator(null);
                //oracle.jbo.domain.Number n = new oracle.jbo.domain.Number(0);
                //oracle.jbo.domain.Number m = new oracle.jbo.domain.Number(0);
                oracle.jbo.domain.Number sum = new oracle.jbo.domain.Number(0);
                while (rit.hasNext()) {
                    Row row = rit.next();
                    sum = sum.add((Number)row.getAttribute("GlAmtBs1"));
                    }                               
                  if(sum.compareTo(0)==1||sum.compareTo(0)==0) {
                      Dcmode="Dr";
                       }
                  if(sum.compareTo(0)==-1) {
                      Dcmode="Cr";
                      sum=sum.multiply(new Number(-1));
                  }
                  return sum;
    }

    public void setDcmode(String Dcmode) {
        this.Dcmode = Dcmode;
    }

    public String getDcmode() {
        return Dcmode;
    }

    public void setPrdBstot(Number prdBstot) {
        this.prdBstot = prdBstot;
    }

    public Number getPrdBstot() {
        RevenuePrdAMImpl am = (RevenuePrdAMImpl)resolvElDC("RevenuePrdAMDataControl");
                ViewObjectImpl vo = am.getRevenuePrd1();
                RowSetIterator rit = vo.createRowSetIterator(null);
                oracle.jbo.domain.Number n = new oracle.jbo.domain.Number(0);
                oracle.jbo.domain.Number m = new oracle.jbo.domain.Number(0);
                oracle.jbo.domain.Number sum = new oracle.jbo.domain.Number(0);
                while (rit.hasNext()) {
                    Row row = rit.next();
                    if(row.getAttribute("GlAmtTyp")!=null)
                    {
                    if (row.getAttribute("GlAmtTyp").toString().equals("Cr")) {
                        n = n.add((Number)row.getAttribute("GlAmtBs"));
                    } else {
                        m = m.add((Number)row.getAttribute("GlAmtBs"));
                    }
                     }
                }
                  sum = m.subtract(n);
                  if(sum.compareTo(0)==1||sum.compareTo(0)==0) {
                      prdDcmode="Dr";
                       }
                  if(sum.compareTo(0)==-1) {
                      prdDcmode="Cr";
                      sum=sum.multiply(new Number(-1));
                  }
                  return sum;
    }

    public void setPrdDcmode(String prdDcmode) {
        this.prdDcmode = prdDcmode;
    }

    public String getPrdDcmode() {
        return prdDcmode;
    }

    public void setRevMstTablebind(RichTable revMstTablebind) {
        this.revMstTablebind = revMstTablebind;
    }

    public RichTable getRevMstTablebind() {
        return revMstTablebind;
    }
}
