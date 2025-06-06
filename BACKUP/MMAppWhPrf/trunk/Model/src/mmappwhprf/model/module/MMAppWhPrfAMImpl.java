package mmappwhprf.model.module;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import mmappwhprf.model.module.common.MMAppWhPrfAM;

import oracle.adf.share.ADFContext;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Apr 14 11:46:33 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MMAppWhPrfAMImpl extends ApplicationModuleImpl implements MMAppWhPrfAM {
    /**
     * This is the default constructor (do not remove).
     */
    public MMAppWhPrfAMImpl() {
    }

    /**
     * Container's getter for AppWhOrg1.
     * @return AppWhOrg1
     */
    public ViewObjectImpl getAppWhOrg1() {
        return (ViewObjectImpl)findViewObject("AppWhOrg1");
    }

    /**
     * Container's getter for WarehouseSearch1.
     * @return WarehouseSearch1
     */
    public ViewObjectImpl getWarehouseSearch1() {
        return (ViewObjectImpl)findViewObject("WarehouseSearch1");
    }
    public void searchWarehouse(){
        ViewCriteria vc= this.getAppWhOrg1().getViewCriteria("AppWhOrgVOCriteria");
        String WId=null;
        ViewObjectImpl srch = this.getWarehouseSearch1();
        Row currentRow = srch.getCurrentRow();
        if(currentRow.getAttribute("WhId")!=null){
           WId= currentRow.getAttribute("WhId").toString();
            this.getAppWhOrg1().setNamedWhereClauseParam("BINDWHID", WId);
        }
        else{
             this.getAppWhOrg1().setNamedWhereClauseParam("BINDWHID", WId);
        }
        this.getAppWhOrg1().applyViewCriteria(vc);
            this.getAppWhOrg1().executeQuery();
    }
    
    public void resetWarehouse(){
        ViewCriteria vc= this.getAppWhOrg1().getViewCriteria("AppWhOrgVOCriteria");
        this.getWarehouseSearch1().getCurrentRow().setAttribute("WhId", null);
        this.getWarehouseSearch1().executeQuery();
        this.getAppWhOrg1().setNamedWhereClauseParam("BINDWHID", null);
        this.getAppWhOrg1().applyViewCriteria(vc);
        this.getAppWhOrg1().executeQuery();
    }
    
    public Key getCurrentRowKey() {
        return this.getAppWhOrg1().getCurrentRow().getKey();
    }

    public void setCurrentRow(Key key) {
        this.getAppWhOrg1().executeQuery();
        Row r = this.getAppWhOrg1().getRow(key);
        System.out.println("row get=" + r);
        if (r != null)
            System.out.println("Key of this row=" + r.getKey());
        this.getAppWhOrg1().setCurrentRow(r);
    }
    public String resolvEl(String data) {
        ADFContext adfCtx = ADFContext.getCurrent();
                ELContext elContext = adfCtx.getELContext();
                ValueExpression valueExp = adfCtx.getExpressionFactory().createValueExpression(elContext, data, Object.class);
                Object Message = valueExp.getValue(elContext).toString();
                return Message.toString();
    } 
    public String CheckDuplicateName(String whNm)
    {
        String flag="N";
        String orgId=resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        RowSetIterator whiterator = this.getAppWhOrg1().createRowSetIterator(null);
                  Row currentRow=this.getAppWhOrg1().getCurrentRow();
                      while(whiterator.hasNext()){
                           Row r1 = whiterator.next();
                          // System.out.println("r1.getKey(): "+r1.getKey());  
                          if(r1!=currentRow){
                              String whNm1=r1.getAttribute("WhNm").toString();
                              String orgId1=r1.getAttribute("OrgId").toString();
                                  if(orgId.equals(orgId1) && whNm.equalsIgnoreCase(whNm1)) {
                                      flag="Y";
                                  }
                           }
                       }
                whiterator.closeRowSetIterator();
                System.out.println("function serialize"+flag);
              //  this.getMmKitProdStk2().closeRowSetIterator();

        //duplicate name in same org
       /*  this.getAppWhOrg2().executeQuery();
        ViewObjectImpl appWhOrg2 = this.getAppWhOrg2();
       RowQualifier rq=new RowQualifier(appWhOrg2);
       Row curr= this.getAppWhOrg1().getCurrentRow();
       rq.setWhereClause("CldId='"+curr.getAttribute("CldId")+"' and OrgId='"+curr.getAttribute("OrgId")+"' and SlocId="+curr.getAttribute("SlocId")+" and HoOrgId='"+curr.getAttribute("HoOrgId")+"' and WhNm='"+whNm.trim()+"'");
            Row[] fr=appWhOrg2.getFilteredRows(rq);
            System.out.println("No of same rows="+fr.length);
            if(fr.length>0)
            {
                for(Row r:fr)
                {
                if(curr.getAttribute("WhId").equals(r.getAttribute("WhId")) && curr.getAttribute("CldId").equals(r.getAttribute("CldId")) && curr.getAttribute("SlocId").equals(r.getAttribute("SlocId")) && curr.getAttribute("OrgId").equals(r.getAttribute("OrgId")) && curr.getAttribute("HoOrgId").equals(r.getAttribute("HoOrgId")))
                {}
                else
                    return "Y";
                }
            }
            return "N"; */
                return flag;
    }
    
    public String ChkAdds()
    {
        System.out.println("Inside");
        String chk="N";
        if(this.getAppWhOrg1().getCurrentRow()!=null){
            System.out.println("Current row is not null");
            if(this.getAppWhOrg1().getCurrentRow().getAttribute("AddsId")!=null)
            { 
                chk="Y";
                System.out.println("add is not null");
            }
        }
        System.out.println("chkexit="+chk);
        return chk;
        }
    
    public void executeCriteriaForWhOrg(String cld,String hoOrg,String org,Integer sloc)
    {
        ViewCriteria vc= this.getAppWhOrg1().getViewCriteria("AppWhOrgVOCriteria");
            this.getAppWhOrg1().setNamedWhereClauseParam("BINDCLDID", cld);
            this.getAppWhOrg1().setNamedWhereClauseParam("BINDSLOCID", sloc);
            this.getAppWhOrg1().setNamedWhereClauseParam("BINDORGID", org);
            this.getAppWhOrg1().setNamedWhereClauseParam("BINDHOORGID", hoOrg);
            this.getAppWhOrg1().applyViewCriteria(vc);
            this.getAppWhOrg1().executeQuery();
        }

    /**
     * Container's getter for AppWhOrg2.
     * @return AppWhOrg2
     */
    public ViewObjectImpl getAppWhOrg2() {
        return (ViewObjectImpl)findViewObject("AppWhOrg2");
    }

    /**
     * Container's getter for lovAddsTune.
     * @return lovAddsTune
     */
    public ViewObjectImpl getlovAddsTune() {
        return (ViewObjectImpl)findViewObject("lovAddsTune");
    }
}
