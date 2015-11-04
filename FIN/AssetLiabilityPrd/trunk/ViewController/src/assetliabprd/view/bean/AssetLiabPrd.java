package assetliabprd.view.bean;

import assetliabprd.model.module.AssetLiabilityPrdAMImpl;


import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.ViewObjectImpl;


public class AssetLiabPrd {
    oracle.jbo.domain.Number tot = new oracle.jbo.domain.Number(0);
    oracle.jbo.domain.Number totLiab = new oracle.jbo.domain.Number(0);
    oracle.jbo.domain.Number baseAmt = new oracle.jbo.domain.Number(0);
    oracle.jbo.domain.Number LiabilityBaseAmt = new oracle.jbo.domain.Number(0); 
    oracle.jbo.domain.Number baseAmtprd = new oracle.jbo.domain.Number(0);
    private RichPanelFormLayout searchForm;
    private String asslibMode="A";    
    //private AssetLiabilityPrdAMImpl am = (AssetLiabilityPrdAMImpl)resolvElDC("AssetLiabilityPrdAMDataControl");
    private RichTable assetMStTableBind;
    private String org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
    private String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
    private Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
    private String ho_orgid = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
    private RichInputText vouNoBinding;
    private RichInputText coaIdBind;
    private RichTable assetlibPrdBind;
    private RichCommandButton exportBtnBind;
    private String mode = "Y";
    private String Dcmode="Dr";
    private String libDcmode="Dr";  
    private String Dcmode1="Dr";
    private String Dcmode2="Dr";
    private String Dcmode3="Dr";
    private RichInputDate startDtBind;
    private RichInputDate endDtBind;
    private RichInputDate transStartBind;
    private RichInputDate transEndBind;
    private RichTable liabilityMStTableBind;

    public AssetLiabPrd() {
    }

    public void setAsslibMode(String asslibMode) {
        this.asslibMode = asslibMode;
    }

    public String getAsslibMode() {
        return asslibMode;
    }
    public void setTot(Number tot) {
        this.tot = tot;
    }
    public void setLibDcmode(String libDcmode) {
        this.libDcmode = libDcmode;
    }

    public String getLibDcmode() {
        return libDcmode;
    }
    public Number getTot() {
        AssetLiabilityPrdAMImpl am = (AssetLiabilityPrdAMImpl)resolvElDC("AssetLiabilityPrdAMDataControl");
        ViewObjectImpl v = am.getAssetLiabilityPrdVO1();
        RowSetIterator rit = v.createRowSetIterator(null);
        oracle.jbo.domain.Number n = new oracle.jbo.domain.Number(0);
        oracle.jbo.domain.Number m = new oracle.jbo.domain.Number(0);
        oracle.jbo.domain.Number sum = new oracle.jbo.domain.Number(0);
        while (rit.hasNext()) {
            Row row = rit.next();           
            if (row.getAttribute("Group1").toString().equalsIgnoreCase("1")) {
                if(row.getAttribute("GlAmtTyp")!=null)
                {
                if(row.getAttribute("GlAmtTyp").toString().equals("Cr")) {
                    n=n.add((Number)row.getAttribute("GlAmtBs"));
                }
                else {
                      m=m.add((Number)row.getAttribute("GlAmtBs")); 
                    }
                }
            }
        }
        sum=m.subtract(n);
        if(sum.compareTo(0)==1||sum.compareTo(0)==0) {
            Dcmode1="Dr";
          //System.out.println("sum is positive");
              }
        if(sum.compareTo(0)==-1) {
            Dcmode1="Cr";
            sum=sum.multiply(new Number(-1));
        }
        System.out.println("total sum in detail page is for tot is "+sum);
        System.out.println("valuve of dc mode1 in get tot is===="+Dcmode1);
        return sum;
    }

    public void setTotLiab(Number totLiab) {
        this.totLiab = totLiab;
    }

    public Number getTotLiab() {
        AssetLiabilityPrdAMImpl am = (AssetLiabilityPrdAMImpl)resolvElDC("AssetLiabilityPrdAMDataControl");
        ViewObjectImpl v = am.getAssetLiabilityPrdVO1();
        RowSetIterator rit = v.createRowSetIterator(null);
        oracle.jbo.domain.Number n = new oracle.jbo.domain.Number(0);
        oracle.jbo.domain.Number m = new oracle.jbo.domain.Number(0);
        oracle.jbo.domain.Number sum = new oracle.jbo.domain.Number(0);
        while (rit.hasNext()) {
            Row row = rit.next();
            if (row.getAttribute("Group1").toString().equalsIgnoreCase("2")) {
                if(row.getAttribute("GlAmtTyp")!=null)
                {
                if(row.getAttribute("GlAmtTyp").toString().equals("Cr")) {
                    n=n.add((Number)row.getAttribute("GlAmtBs"));
                }
                else {
                      m=m.add((Number)row.getAttribute("GlAmtBs")); 
                    }
                }
                }
              }
        sum =m.subtract(n);
        if(sum.compareTo(0)==1||sum.compareTo(0)==0) {
            Dcmode2="Dr";
             }
        if(sum.compareTo(0)==-1) {
            Dcmode2="Cr";
            sum=sum.multiply(new Number(-1));
        }
        //System.out.println("total sum in detail page is "+sum);
        return sum;
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

    public String resetAssetLiabMstAction() {
        AssetLiabilityPrdAMImpl am = (AssetLiabilityPrdAMImpl)resolvElDC("AssetLiabilityPrdAMDataControl");
        ViewObjectImpl vo = am.getSearchVO1();
        ViewObjectImpl vo1 = am.getAssetLiabilityMstVO1();
        ViewObjectImpl vo2 = am.getAssetLiabilityMstVO2();
        vo.getCurrentRow().setAttribute("CoaNameTrans", null);
        vo.getCurrentRow().setAttribute("StartDtTrans", null);
        vo.getCurrentRow().setAttribute("EndDttrans", null);
        vo.executeQuery();
        vo1.setNamedWhereClauseParam("BindCldId", -1);
         vo1.executeQuery();
        vo2.setNamedWhereClauseParam("BindCldId", -1);
         vo2.executeQuery();
         AdfFacesContext.getCurrentInstance().addPartialTarget(assetMStTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(liabilityMStTableBind);
        // resetValueInputItems(AdfFacesContext.getCurrentInstance(), searchForm);
        return "null";
    }

    public String resetAssetLiabDtlAction() {
        resetValueInputItems(AdfFacesContext.getCurrentInstance(), searchForm);
        return "reset";
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

    public void setSearchForm(RichPanelFormLayout searchForm) {
        this.searchForm = searchForm;
    }

    public RichPanelFormLayout getSearchForm() {
        return searchForm;
    }

    public void searchAction(ActionEvent actionEvent) {
        AssetLiabilityPrdAMImpl am1 = (AssetLiabilityPrdAMImpl)resolvElDC("AssetLiabilityPrdAMDataControl");
        Integer coaId = null;
        Date startdt = null;
        Date enddt = null;
        ViewObjectImpl searchvo = am1.getSearchVO1();
         ViewObjectImpl vo1 = am1.getAssetLiabilityMstVO1();
        ViewObjectImpl vo2 = am1.getAssetLiabilityMstVO2();
         if (coaIdBind.getValue() != null) {
            coaId = Integer.parseInt(coaIdBind.getValue().toString());
        }
        if (startDtBind.getValue() != null) {
            startdt = (Date)startDtBind.getValue();
        }
        if (endDtBind.getValue() != null) {
            enddt = (Date)endDtBind.getValue();
        }
        vo1.setNamedWhereClauseParam("BindCldId", cld_id);
        vo1.setNamedWhereClauseParam("BindSlocId", sloc_id);
        vo1.setNamedWhereClauseParam("BindHoOrgId", ho_orgid);
        vo1.setNamedWhereClauseParam("BindOrgId", resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}"));
        vo1.setNamedWhereClauseParam("BindCoaId", coaId);
        vo1.setNamedWhereClauseParam("BindStrDt", startdt);
        vo1.setNamedWhereClauseParam("BindEndDt", enddt);
        vo1.setNamedWhereClauseParam("BindglCoaCogid", 1);
        vo1.executeQuery();
        vo2.setNamedWhereClauseParam("BindCldId", cld_id);
        vo2.setNamedWhereClauseParam("BindSlocId", sloc_id);
        vo2.setNamedWhereClauseParam("BindHoOrgId", ho_orgid);
        vo2.setNamedWhereClauseParam("BindOrgId", resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}"));
        vo2.setNamedWhereClauseParam("BindCoaId", coaId);
        vo2.setNamedWhereClauseParam("BindStrDt", startdt);
        vo2.setNamedWhereClauseParam("BindEndDt", enddt);
        vo2.setNamedWhereClauseParam("BindglCoaCogid", 2);
        vo2.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(assetMStTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(liabilityMStTableBind);

    }

    public void setAssetMStTableBind(RichTable assetMStTableBind) {
        this.assetMStTableBind = assetMStTableBind;
    }

    public RichTable getAssetMStTableBind() {
        return assetMStTableBind;
    }

   public Object resolvEl(String data) {

        ADFContext adfCtx = ADFContext.getCurrent();
        ELContext elContext = adfCtx.getELContext();
        ValueExpression valueExp = adfCtx.getExpressionFactory().createValueExpression(elContext, data, Object.class);
        Object Message = valueExp.getValue(elContext).toString();

        return Message;
    }

    public void assetLibSearchAction(ActionEvent actionEvent) {
        AssetLiabilityPrdAMImpl am = (AssetLiabilityPrdAMImpl)resolvElDC("AssetLiabilityPrdAMDataControl");
        String vouid = null;
        Date strtdt = null;
        Date enddt = null;
        ViewObjectImpl assetLiabilityPrd1 = am.getAssetLiabilityPrdVO1();
        ViewObjectImpl searchvo = am.getSearchPrdVO1();
        if (vouNoBinding.getValue() != null) {
            vouid = vouNoBinding.getValue().toString();
        }
        if (transStartBind.getValue() != null) {
            strtdt = (Date)transStartBind.getValue();
        }
        if (transEndBind.getValue() != null) {
            enddt = (Date)transEndBind.getValue();
        }
        System.out.println("---------" + strtdt + "\n" +  enddt);
        assetLiabilityPrd1.setNamedWhereClauseParam("BindStrDt", strtdt);
        assetLiabilityPrd1.setNamedWhereClauseParam("BindEndDt", enddt);
        assetLiabilityPrd1.setNamedWhereClauseParam("BindVouId", vouid);
        assetLiabilityPrd1.executeQuery();
    }

    public void setVouNoBinding(RichInputText vouNoBinding) {
        this.vouNoBinding = vouNoBinding;
    }

    public RichInputText getVouNoBinding() {
        return vouNoBinding;
    }


    public void setCoaIdBind(RichInputText coaIdBind) {
        this.coaIdBind = coaIdBind;
    }

    public RichInputText getCoaIdBind() {
        return coaIdBind;
    }

    public void assetPrdResetAL(ActionEvent actionEvent) {
        AssetLiabilityPrdAMImpl am = (AssetLiabilityPrdAMImpl)resolvElDC("AssetLiabilityPrdAMDataControl");
          ViewObjectImpl vo = am.getSearchPrdVO1();
        vo.getCurrentRow().setAttribute("TranVouNo", null);
        vo.getCurrentRow().setAttribute("TransStrtDt", null);
        vo.getCurrentRow().setAttribute("TransEndDt", null);
        vo.executeQuery();
        ViewObjectImpl assetLiabilityPrd1 = am.getAssetLiabilityPrdVO1();
        assetLiabilityPrd1.setNamedWhereClauseParam("BindStrDt", null);
        assetLiabilityPrd1.setNamedWhereClauseParam("BindEndDt", null);
        assetLiabilityPrd1.setNamedWhereClauseParam("BindVouId", null);
        assetLiabilityPrd1.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(assetlibPrdBind);
    }

    public void setAssetlibPrdBind(RichTable assetlibPrdBind) {
        this.assetlibPrdBind = assetlibPrdBind;
    }

    public RichTable getAssetlibPrdBind() {
        return assetlibPrdBind;
    }


    public void setExportBtnBind(RichCommandButton exportBtnBind) {
        this.exportBtnBind = exportBtnBind;
    }

    public RichCommandButton getExportBtnBind() {
        return exportBtnBind;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setStartDtBind(RichInputDate startDtBind) {
        this.startDtBind = startDtBind;
    }

    public RichInputDate getStartDtBind() {
        return startDtBind;
    }

    public void setEndDtBind(RichInputDate endDtBind) {
        this.endDtBind = endDtBind;
    }

    public RichInputDate getEndDtBind() {
        return endDtBind;
    }

    public void setTransStartBind(RichInputDate transStartBind) {
        this.transStartBind = transStartBind;
    }

    public RichInputDate getTransStartBind() {
        return transStartBind;
    }

    public void setTransEndBind(RichInputDate transEndBind) {
        this.transEndBind = transEndBind;
    }

    public RichInputDate getTransEndBind() {
        return transEndBind;
    }

    public String backAction() {
        AssetLiabilityPrdAMImpl am = (AssetLiabilityPrdAMImpl)resolvElDC("AssetLiabilityPrdAMDataControl");
        System.out.println("voucher number is =" + vouNoBinding.getValue());
        ViewObjectImpl vo = am.getSearchPrdVO1();
        vo.getCurrentRow().setAttribute("TranVouNo", null);
        vo.getCurrentRow().setAttribute("TransStrtDt", null);
        vo.getCurrentRow().setAttribute("TransEndDt", null);
        vo.executeQuery();
        ViewObjectImpl assetLiabilityPrd1 = am.getAssetLiabilityPrdVO1();
        assetLiabilityPrd1.setNamedWhereClauseParam("BindStrDt", null);
        assetLiabilityPrd1.setNamedWhereClauseParam("BindEndDt", null);
        assetLiabilityPrd1.setNamedWhereClauseParam("BindVouId", null);
        assetLiabilityPrd1.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(assetlibPrdBind);
        return "back1";
    }

    public void setBaseAmt(Number baseAmt) {
        this.baseAmt = baseAmt;
    }

    public Number getBaseAmt() {
        AssetLiabilityPrdAMImpl am = (AssetLiabilityPrdAMImpl)resolvElDC("AssetLiabilityPrdAMDataControl");
        ViewObjectImpl vo = am.getAssetLiabilityMstVO1();
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
      if(sum.compareTo(0)>=0) {
              Dcmode="Dr";
               }
          if(sum.compareTo(0)<0) {
              Dcmode="Cr";
              sum=sum.multiply(new Number(-1));
          }
          return sum;
       
    }
    public void setLiabilityBaseAmt(Number LiabilityBaseAmt) {
        this.LiabilityBaseAmt = LiabilityBaseAmt;
    }

    public Number getLiabilityBaseAmt() {
        AssetLiabilityPrdAMImpl am = (AssetLiabilityPrdAMImpl)resolvElDC("AssetLiabilityPrdAMDataControl");
        ViewObjectImpl vo = am.getAssetLiabilityMstVO2();
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
          if(sum.compareTo(0)>=0) {
             libDcmode="Dr";
               }
          if(sum.compareTo(0)<0) {
              libDcmode="Cr";
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

    public void setDcmode1(String Dcmode1) {
        this.Dcmode1 = Dcmode1;
    }

    public String getDcmode1() {
        return Dcmode1;
    }

    public void setDcmode2(String Dcmode2) {
        this.Dcmode2 = Dcmode2;
    }

    public String getDcmode2() {
        return Dcmode2;
    }

    public void setBaseAmtprd(Number baseAmtprd) {
        this.baseAmtprd = baseAmtprd;
    }

    public Number getBaseAmtprd() {
        AssetLiabilityPrdAMImpl am = (AssetLiabilityPrdAMImpl)resolvElDC("AssetLiabilityPrdAMDataControl");
        ViewObjectImpl v = am.getAssetLiabilityPrdVO1();
        RowSetIterator rit = v.createRowSetIterator(null);
        oracle.jbo.domain.Number n = new oracle.jbo.domain.Number(0);
        oracle.jbo.domain.Number m = new oracle.jbo.domain.Number(0);
        oracle.jbo.domain.Number sum = new oracle.jbo.domain.Number(0);
        while (rit.hasNext()) {
            Row row = rit.next();           
            if(row.getAttribute("GlAmtTyp")!=null)
                {
                if(row.getAttribute("GlAmtTyp").toString().equals("Cr")) {
                    n=n.add((Number)row.getAttribute("GlAmtBs"));
                }
                else {
                      m=m.add((Number)row.getAttribute("GlAmtBs")); 
                    }
                }
            
        }
        sum=m.subtract(n);
        if(sum.compareTo(0)==1||sum.compareTo(0)==0) {
            Dcmode3="Dr";
               }
        if(sum.compareTo(0)==-1) {
            Dcmode3="Cr";
            sum=sum.multiply(new Number(-1));
        }
        System.out.println("total sum in detail page is for tot is "+sum);
        System.out.println("valuve of dc mode1 in get tot is===="+Dcmode1);
        return sum;
    }

    public void setDcmode3(String Dcmode3) {
        this.Dcmode3 = Dcmode3;
    }

    public String getDcmode3() {
        return Dcmode3;
    }

    public String assetsViewAction() {
        asslibMode="A";
        AssetLiabilityPrdAMImpl am1 = (AssetLiabilityPrdAMImpl)resolvElDC("AssetLiabilityPrdAMDataControl");
           Integer coaId = null;
        ViewObjectImpl vo1 = am1.getAssetLiabilityMstVO1();
        Row currrow=vo1.getCurrentRow();
        if(vo1.getCurrentRow().getAttribute("GlCoaId")!=null)
        coaId=Integer.parseInt(vo1.getCurrentRow().getAttribute("GlCoaId").toString());
        System.out.println("coaiddd of current roww is="+coaId);
        //ViewObjectImpl vo = am1.getAssetLiabilityMstVO();
       
        ViewObjectImpl assetLiabilityPrdVO1 = am1.getAssetLiabilityPrdVO1();
        assetLiabilityPrdVO1.setNamedWhereClauseParam("BindCldId", cld_id);
        assetLiabilityPrdVO1.setNamedWhereClauseParam("BindSlocId", sloc_id);
        assetLiabilityPrdVO1.setNamedWhereClauseParam("BindOrgId", resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}"));
        assetLiabilityPrdVO1.setNamedWhereClauseParam("BindhoOrgId", ho_orgid);
        assetLiabilityPrdVO1.setNamedWhereClauseParam("BindCoaId", coaId);
        assetLiabilityPrdVO1.executeQuery();
        
       /* Row[]row= vo.getFilteredRows("GlCoaId", coaId);
       vo.setCurrentRow(currrow);
        System.out.println("total number of rows areee iss="+row.length); */
               return "details";
    }

    public String assetsLiabilityAction() {
        asslibMode="L";
        AssetLiabilityPrdAMImpl am1 = (AssetLiabilityPrdAMImpl)resolvElDC("AssetLiabilityPrdAMDataControl");
           Integer coaId = null;
        ViewObjectImpl vo1 = am1.getAssetLiabilityMstVO2();
        Row currrow=vo1.getCurrentRow();
        if(vo1.getCurrentRow().getAttribute("GlCoaId")!=null)
        coaId=Integer.parseInt(vo1.getCurrentRow().getAttribute("GlCoaId").toString());
        System.out.println("coaiddd of current roww is="+coaId);
        //ViewObjectImpl vo = am1.getAssetLiabilityMstVO();
        
        ViewObjectImpl assetLiabilityPrdVO1 = am1.getAssetLiabilityPrdVO1();
        assetLiabilityPrdVO1.setNamedWhereClauseParam("BindCldId", cld_id);
        assetLiabilityPrdVO1.setNamedWhereClauseParam("BindSlocId", sloc_id);
        assetLiabilityPrdVO1.setNamedWhereClauseParam("BindOrgId", resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}"));
        assetLiabilityPrdVO1.setNamedWhereClauseParam("BindhoOrgId", ho_orgid);
        assetLiabilityPrdVO1.setNamedWhereClauseParam("BindCoaId", coaId);
        assetLiabilityPrdVO1.executeQuery();
        return "details";
    }

    public void setLiabilityMStTableBind(RichTable liabilityMStTableBind) {
        this.liabilityMStTableBind = liabilityMStTableBind;
    }

    public RichTable getLiabilityMStTableBind() {
        return liabilityMStTableBind;
    }
}
