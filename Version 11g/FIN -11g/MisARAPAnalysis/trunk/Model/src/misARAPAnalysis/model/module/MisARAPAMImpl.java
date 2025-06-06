package misARAPAnalysis.model.module;

import java.sql.CallableStatement;
import java.sql.SQLException;


import java.sql.Types;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

import java.util.TimeZone;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import misARAPAnalysis.model.module.common.MisARAPAM;


import misARAPAnalysis.model.view.CoaDetailVOImpl;

import misARAPAnalysis.model.view.TimeSelectorVOImpl;

import oracle.jbo.JboException;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;
import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Oct 15 11:47:53 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MisARAPAMImpl extends ApplicationModuleImpl implements MisARAPAM {

    private static int NUMERIC = Types.NUMERIC;
    private Integer maxFyId;
    private java.sql.Date strtDt;
    private java.sql.Date endDt;
    private Integer fyId;

    /**
     * This is the default constructor (do not remove).
     */
    public MisARAPAMImpl() {
    }

    public void setOrgUsrARAgeingBktParameters(String p_cld_id, String p_org_id, Integer p_usr_id) {

       
       
        ViewObjectImpl orgUsrARAgeingBkt = this.getOrgUsrARAgeingBktVO();
       System.out.println("p_cld_id "+p_cld_id);
        orgUsrARAgeingBkt.setNamedWhereClauseParam("BindCldId", p_cld_id);
       System.out.println("p_org_id "+p_org_id);
        orgUsrARAgeingBkt.setNamedWhereClauseParam("BindOrgId", p_org_id);
      System.out.println("p_usr_id "+ p_usr_id);
        orgUsrARAgeingBkt.setNamedWhereClauseParam("BindUsrId", p_usr_id);
        orgUsrARAgeingBkt.setNamedWhereClauseParam("BindBktTyp", "AR");
        orgUsrARAgeingBkt.executeQuery();
        
        System.out.println("orgUsrARAgeingBkt row count "+orgUsrARAgeingBkt.getRowCount());

    }
    public void setOrgUsrAPAgeingBktParameters(String p_cld_id, String p_org_id, Integer p_usr_id) {

        ViewObjectImpl orgUsrAPAgeingBkt = this.getOrgUsrAPAgeingBktVO();
        System.out.println("p_cld_id "+p_cld_id);
        orgUsrAPAgeingBkt.setNamedWhereClauseParam("BindCldId", p_cld_id);
        System.out.println("p_org_id "+ p_org_id);
        orgUsrAPAgeingBkt.setNamedWhereClauseParam("BindOrgId", p_org_id);
        System.out.println("p_usr_id "+p_usr_id);
        orgUsrAPAgeingBkt.setNamedWhereClauseParam("BindUsrId", p_usr_id);
        orgUsrAPAgeingBkt.setNamedWhereClauseParam("BindBktTyp", "AP");
        orgUsrAPAgeingBkt.executeQuery();
        
        System.out.println("Row count  :"+orgUsrAPAgeingBkt.getRowCount());

    }
    public void setCurrencyWiseOutstandingParameters(String p_cld_id, String p_org_id, Integer p_fy_id,
                                                     Integer p_coa_id, Integer p_usr_id, String p1) {

        ViewObjectImpl currWiseOst = this.getCurrencyWiseOutstandingVO();
        currWiseOst.setNamedWhereClauseParam("BindCldId", p_cld_id);
        currWiseOst.setNamedWhereClauseParam("BindOrgId", p_org_id);
        currWiseOst.setNamedWhereClauseParam("BindCoaId", p_coa_id);
        currWiseOst.setNamedWhereClauseParam("BindUsrId", p_usr_id);
        currWiseOst.executeQuery();
    }

    public void setCoaVsOutstandingParameters(String p_cld_id, String p_org_id, Integer p_fy_id, Integer p_coa_id,
                                              Integer p_usr_id, String p1) {

        ViewObjectImpl coaVsOst = this.getCoaVsOutstanding1();
        coaVsOst.setNamedWhereClauseParam("BindCldId", p_cld_id);
        coaVsOst.setNamedWhereClauseParam("BindOrgId", p_org_id);
        coaVsOst.setNamedWhereClauseParam("BindCoaId", p_coa_id);
        coaVsOst.setNamedWhereClauseParam("BindUsrId", p_usr_id);
        coaVsOst.executeQuery();
    }

    public void setMainTableParameters(String p_cld_id, String p_org_id, Integer p_coa_id,
                                       Integer p_usr_id, String p1) {

        ViewObjectImpl mainTable = this.getMainTable1();
        mainTable.setNamedWhereClauseParam("BindCldId", p_cld_id);
        mainTable.setNamedWhereClauseParam("BindOrgId", p_org_id);
        mainTable.setNamedWhereClauseParam("BindCoaId", p_coa_id);
        mainTable.setNamedWhereClauseParam("BindUsrId", p_usr_id);
        mainTable.executeQuery();
    }

    public void setARCurrencyWiseOutstandingParameters(String p_cld_id, String p_org_id, Integer p_fy_id,
                                                       Integer p_coa_id, Integer p_usr_id, String p1) {

        ViewObjectImpl aRcurrWiseOst = this.getARCurrencyWiseOutstandingVO();
        aRcurrWiseOst.setNamedWhereClauseParam("BindCldId", p_cld_id);
        aRcurrWiseOst.setNamedWhereClauseParam("BindOrgId", p_org_id);
        aRcurrWiseOst.setNamedWhereClauseParam("BindCoaId", p_coa_id);
        aRcurrWiseOst.setNamedWhereClauseParam("BindUsrId", p_usr_id);
       aRcurrWiseOst.executeQuery();
    }

    public void setARCoaVsOutstandingParameters(String p_cld_id, String p_org_id, Integer p_fy_id, Integer p_coa_id,
                                                Integer p_usr_id, String p1) {

        ViewObjectImpl aRcoaVsOst = this.getARCoaVsOutstandingVO();
        aRcoaVsOst.setNamedWhereClauseParam("BindCldId", p_cld_id);
        aRcoaVsOst.setNamedWhereClauseParam("BindOrgId", p_org_id);
        aRcoaVsOst.setNamedWhereClauseParam("BindCoaId", p_coa_id);
        aRcoaVsOst.setNamedWhereClauseParam("BindUsrId", p_usr_id);
        aRcoaVsOst.executeQuery();
    }

    public void setARDetailParameters(String p_cld_id, String p_org_id, Integer p_fy_id, Integer p_coa_id,
                                      Integer p_usr_id, String p1) {

        ViewObjectImpl aRDetails = this.getARDetailsVO();
        aRDetails.setNamedWhereClauseParam("BindCldId", p_cld_id);
        aRDetails.setNamedWhereClauseParam("BindOrgId", p_org_id);
        aRDetails.setNamedWhereClauseParam("BindCoaId", p_coa_id);
        aRDetails.setNamedWhereClauseParam("BindUsrId", p_usr_id);
        aRDetails.executeQuery();

    }

    public void setCoaDetailParameters(String cldId,Integer slocId,String hoOrgId,String orgId,Integer coaId,Number startRange,Number endRange) {

              
              
              System.out.println("Executing in AM"); 
              
              System.out.println( "Parameters are  coa Id "+coaId + "start range "+startRange +" end Range "+endRange);
              
              CoaDetailVOImpl coaDetails = this.getCoaDetail1();
          
             coaDetails.setBindCldId(cldId);
             coaDetails.setBindHoOrgId(hoOrgId);
             coaDetails.setBindOrgId(orgId);
             coaDetails.setBindSlocId(slocId);
             coaDetails.setBindCoaId(coaId);
             coaDetails.setBindStartRange(startRange);
             coaDetails.setBindEndRange(endRange);
            // Coa Id is HardCoded,U have to Change.  
             coaDetails.setBindUserId(this.getCurrentUsr());
             coaDetails.setBindStartDate(null);
             coaDetails.setBindEndDate(null);
             //coaDetails.executeQuery();
             this.initilizedDatefilter();
                 
               TimeSelectorVOImpl graphVo= (TimeSelectorVOImpl) this.getTimeSelector1();
               graphVo.setBindCldId(cldId);
               graphVo.setBindHoOrgId(hoOrgId);
               graphVo.setBindOrgId(orgId);
               graphVo.setBindSlocId(slocId);
               graphVo.setBindCoaId(coaId);
               graphVo.setBindStartRange(startRange);
               graphVo.setBindEndRange(endRange);
               // Coa Id is HardCoded,U have to Change.
               graphVo.setBindUserId(this.getCurrentUsr());
               graphVo.executeQuery();

    }

    /**
     * This method is used to set the parameters for apRangeView when ever slider value are changed.
     * **/

    public void setApRangeParameters(String p_cld_id, String p_org_id, Number p_range1, Number p_range2) {

       System.out.println("in AM Starting Reange "+p_range1 + "End Ramge "+p_range2);
        
        
        
        ViewObjectImpl apRange = this.getapRange1();
        apRange.setNamedWhereClauseParam("BindCldId", p_cld_id);
        apRange.setNamedWhereClauseParam("BindOrgId", p_org_id);
        apRange.setNamedWhereClauseParam("BindRange1", p_range1);
        apRange.setNamedWhereClauseParam("BindRange2", p_range2);
        apRange.executeQuery();

    }
    /**
     * This method is used to set the parameters for arRangeView when ever slider value are changed.
     * **/

    public void setArRangeParameters(String p_cld_id, String p_org_id, Number p_range1, Number p_range2) {

        System.out.println("in AM Starting Reange "+p_range1 + "End Ramge "+p_range2);
       
        ViewObjectImpl arAdhocRange = this.getarAdhocRangeVO();
        arAdhocRange.setNamedWhereClauseParam("BindCldId", p_cld_id);
        arAdhocRange.setNamedWhereClauseParam("BindOrgId", p_org_id);
        arAdhocRange.setNamedWhereClauseParam("BindRange1", p_range1);
        arAdhocRange.setNamedWhereClauseParam("BindRange2", p_range2);
        arAdhocRange.executeQuery(); 
        System.out.println("this.getarAdhocRangeVO() :"+this.getarAdhocRangeVO().getRowCount());
    }
    /**
     * This method is used to set the parameters for timeSelector.
     * **/

    public void setTimeSelectorParameters(String p_cld_id, String p_org_id, Integer p_coa_id) {


//        ViewObjectImpl timeSelector = this.getTimeSelectorVO();
//        timeSelector.setNamedWhereClauseParam("BindCldId", p_cld_id);
//        timeSelector.setNamedWhereClauseParam("BindOrgId", p_org_id);
//        timeSelector.setNamedWhereClauseParam("BindCoaId", p_coa_id);
//        timeSelector.executeQuery();

    }

    /**@ 24/10/2013
     * @param p_org_id for organization identity
     * @param p_cld_id for cloud identity
     * @param p_fy_id for financial identity
     * @param p_coa_id for chart of account identity
     * @param p_usr_id for user identity
     * @param p1 has been kept for future use
     * This method has been called as first activity in the task flow to set the global parameters
     * for view object which are called within it.
     * **/
    public void setGlobalParameter(String p_org_id, String p_cld_id, Integer p_fy_id, Integer p_coa_id,
                                   Integer p_usr_id, String p1) {

    
        System.out.println("Executing in amimpl");
          
          fyId = getMaxFyId(p_org_id, p_cld_id, p_fy_id);

          this.setCurrencyWiseOutstandingParameters(p_cld_id, p_org_id, fyId, p_coa_id, p_usr_id, p1);
       
          this.setMainTableParameters(p_cld_id, p_org_id, p_coa_id, p_usr_id, p1);
     
       // this.setARCurrencyWiseOutstandingParameters(p_cld_id, p_org_id, fyId, p_coa_id, p_usr_id, p1);

          this.setCoaVsOutstandingParameters(p_cld_id, p_org_id, fyId, p_coa_id, p_usr_id, p1);
        //this.setARCoaVsOutstandingParameters(p_cld_id, p_org_id, fyId, p_coa_id, p_usr_id, p1);

        
        
        //this.setARDetailParameters(p_cld_id, p_org_id, fyId, p_coa_id, p_usr_id, p1);

        //this.setApRangeParameters(p_cld_id, p_org_id, null, null);
        
        //this.setArRangeParameters(p_cld_id, p_org_id, null, null);
        //this.setTimeSelectorParameters(p_cld_id, p_org_id, p_coa_id);

        //this.setCoaDetailParameters(p_cld_id, p_org_id, fyId, p_coa_id, p_usr_id, null, null);
        
        // this.setOrgUsrARAgeingBktParameters(p_cld_id, p_org_id, p_usr_id);
        this.setOrgUsrAPAgeingBktParameters(p_cld_id, p_org_id, p_usr_id);
          
        
    }
    
    
    //This is used to excute query at disclosure of ar TAB
    
    public void setBindForAr(String p_org_id, String p_cld_id, Integer p_coa_id,
                                   Integer p_usr_id, String p1) {

          this.setARCurrencyWiseOutstandingParameters(p_cld_id, p_org_id, fyId, p_coa_id, p_usr_id, p1);

          
          this.setARCoaVsOutstandingParameters(p_cld_id, p_org_id, fyId, p_coa_id, p_usr_id, p1);

          this.setARDetailParameters(p_cld_id, p_org_id, fyId, p_coa_id, p_usr_id, p1);


         this.setOrgUsrARAgeingBktParameters(p_cld_id, p_org_id, p_usr_id);
        
    }
    
    public void AgeingTabAR(String p_org_id, String p_cld_id, Integer p_fy_id, Integer p_coa_id,
                                   Integer p_usr_id, String p1) {

        
          this.setArRangeParameters(p_cld_id, p_org_id, null, null);
          
        
    }

    /**
     * @param voNm as viewobject name
     * @param attrNm as attribute name
     * Method to return the value of the named attribute of the current row.
     * **/
    public String getCurrRowAttribute(String voNm, String attrNm) {

        String retVal = null;

        if (((ViewObjectImpl)findObject(voNm)).getCurrentRow().getAttribute(attrNm) != null) {
            retVal = ((ViewObjectImpl)findObject(voNm)).getCurrentRow().getAttribute(attrNm).toString();
        }

        return retVal;
    }

    /**
     @param p_org_id for organization identity
     * @param p_cld_id for cloud identity
     * @param p_usr_id for chart of account identity
     * Method to return the start date for .
     * **/
    public String getTmSeletorStrtDT(String p_cld_id, String p_org_id, Integer p_usr_id,Integer coaId) {


        try {
            callStoredProcedure("FIN.PROC_USR_BUCKET_RANGE(?,?,?,?,?,?)", new Object[] { p_cld_id, p_org_id, p_usr_id,coaId });
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(strtDt!=null)
          return strtDt.toString();
        else
            return "NA";
    }

    /**
     @param p_org_id for organization identity
     * @param p_cld_id for cloud identity
     * @param p_usr_id for chart of account identity
     * Method to return the start date for .
     * **/
    public String getTmSeletorEndDT(String p_cld_id, String p_org_id, Integer p_usr_id,Integer coaId) {


        try {
            callStoredProcedure("FIN.PROC_USR_BUCKET_RANGE(?,?,?,?,?,?)", new Object[] { p_cld_id, p_org_id, p_usr_id ,null});
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(endDt!=null){
          return endDt.toString();
        }else{
            return "NA";
        }
    }

    /**
     * @param p_cld_id as cloud identity
     * @param p_org_id as organization identity
     * This method returns the financial year Id for the oraganization.
     * If the financial id in the parameter is null then max financial year id is returned
     * **/
    public Integer getMaxFyId(String p_org_id, String p_cld_id, Integer p_fy_id) {

        if (p_fy_id == null && maxFyId == null) {

            maxFyId = getFyId(p_cld_id, p_org_id);

        } else {
            maxFyId = p_fy_id;
        }
        return maxFyId;
    }

    private Integer getFyId(String cldId, String orgId) {

        Integer fyId = null;

        try {
            fyId =
Integer.parseInt(callStoredFunction(NUMERIC, "APP.FN_GET_MAX_FY(?,?)", new Object[] { cldId, orgId }).toString());

        } catch (NumberFormatException nfe) {
            // TODO: Add catch code
            // fyId = Integer.parseInt(getLovOrgFyVO().first().getAttribute("OrgFyId").toString());
            nfe.printStackTrace();
        }
        return fyId;
    }

    public void callStoredProcedure(String stmt, Object[] bindVars) throws SQLException {

        CallableStatement st = null;
        try {
            //1.Create a JDBC CallableStatement
            st = getDBTransaction().createCallableStatement("begin " + stmt + "; end ;", 0);
            //2. Register the first bind variable for the return value
            st.setObject(1, bindVars[0]);
            st.setObject(2, bindVars[1]);
            st.setObject(3, bindVars[2]);
            st.setObject(4, bindVars[3] );
            
            st.registerOutParameter(5, Types.DATE);
            st.registerOutParameter(6, Types.DATE);
            // 5. Set the value of user-supplied bind vars in the stmt

            st.executeUpdate();

            try {
                strtDt = (java.sql.Date)st.getObject(5);
                endDt = (java.sql.Date)st.getObject(6);
            } catch (Exception e) {
                // TODO: Add catch code
                e.printStackTrace();
            }

        } catch (SQLException e) {
            int end = e.getMessage().indexOf("\n");
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    // 7. Close the statement
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();

                }
            }
        }

    }

    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {

            st = this.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 2, bindVars[z]);
                }
            }
            st.executeUpdate();
            return st.getObject(1);
        } catch (SQLException e) {
            e.getMessage().indexOf("\n");

            throw new JboException(e.getMessage());
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    throw new JboException(e.getMessage());
                }
            }
        }
    }


    /**
     * Container's getter for apRange1.
     * @return apRange1
     */
    public ViewObjectImpl getapRange1() {
        return (ViewObjectImpl)findViewObject("apRange1");
    }


    /**
     * Container's getter for CoaVsOutstanding1.
     * @return CoaVsOutstanding1
     */
    public ViewObjectImpl getCoaVsOutstanding1() {
        return (ViewObjectImpl)findViewObject("CoaVsOutstanding1");
    }


    /**
     * Container's getter for CurrencyWiseOutstandingVO.
     * @return CurrencyWiseOutstandingVO
     */
    public ViewObjectImpl getCurrencyWiseOutstandingVO() {
        return (ViewObjectImpl)findViewObject("CurrencyWiseOutstandingVO");
    }

    /**
     * Container's getter for MainTable1.
     * @return MainTable1
     */
    public ViewObjectImpl getMainTable1() {
        return (ViewObjectImpl)findViewObject("MainTable1");
    }

    /**
     * Container's getter for ARCoaVsOutstandingVO.
     * @return ARCoaVsOutstandingVO
     */
    public ViewObjectImpl getARCoaVsOutstandingVO() {
        return (ViewObjectImpl)findViewObject("ARCoaVsOutstandingVO");
    }

    /**
     * Container's getter for ARCurrencyWiseOutstandingVO.
     * @return ARCurrencyWiseOutstandingVO
     */
    public ViewObjectImpl getARCurrencyWiseOutstandingVO() {
        return (ViewObjectImpl)findViewObject("ARCurrencyWiseOutstandingVO");
    }

    /**
     * Container's getter for ARDetailsVO.
     * @return ARDetailsVO
     */
    public ViewObjectImpl getARDetailsVO() {
        return (ViewObjectImpl)findViewObject("ARDetailsVO");
    }


    /**
     * Container's getter for OrgUsrARAgeingBktVO.
     * @return OrgUsrARAgeingBktVO
     */
    public ViewObjectImpl getOrgUsrARAgeingBktVO() {
        return (ViewObjectImpl)findViewObject("OrgUsrARAgeingBktVO");
    }

    /**
     * Container's getter for OrgUsrAPAgeingBktVO.
     * @return OrgUsrAPAgeingBktVO
     */
    public ViewObjectImpl getOrgUsrAPAgeingBktVO() {
        return (ViewObjectImpl)findViewObject("OrgUsrAPAgeingBktVO");
    }

    /**
     * Container's getter for arAdhocRangeVO.
     * @return arAdhocRangeVO
     */
    public ViewObjectImpl getarAdhocRangeVO() {
        return (ViewObjectImpl)findViewObject("arAdhocRangeVO");
    }

    /**
     * Container's getter for CoaDetail1.
     * @return CoaDetail1
     */
    public CoaDetailVOImpl getCoaDetail1() {
        return (CoaDetailVOImpl)findViewObject("CoaDetail1");
    }

    /**
     * Container's getter for TimeSelector1.
     * @return TimeSelector1
     */
    public ViewObjectImpl getTimeSelector1() {
        return (ViewObjectImpl)findViewObject("TimeSelector1");
    }
    
    
// Code is written  by MS 
    public void filterCoaDEtailByDate(java.sql.Date start,java.sql.Date end){
        System.out.println("Filtering Coa Detail");
        
        CoaDetailVOImpl coaDetails = this.getCoaDetail1();

        coaDetails.setBindStartDate(new oracle.jbo.domain.Date(start));
        coaDetails.setBindEndDate(new oracle.jbo.domain.Date(end));
        coaDetails.executeQuery();
    }
    
    private void initilizedDatefilter(){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
       // DateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
         Date date = new Date();
         System.out.println("date is" +format.format(date));
         
         String eDate=format.format(date);
         String sDate;
         String month=eDate.substring(5,7);
         Integer year=Integer.parseInt(eDate.substring(0,4));
         Integer inMonth=Integer.parseInt(month);
         String day=eDate.substring(8,10);
         System.out.println("Year :"+year +"inMonth "+inMonth + " day"+day);
         if(inMonth==1){
            year=year-1;
            inMonth=12;
            month="12";
            }else{
                if(inMonth<11)
                    month="0"+(inMonth-1);
                else
                month=(new Integer(inMonth-1)).toString(); 
            }    
        year=year-1;
         sDate=year.toString()+"-"+month+"-"+day;
     
      
      
      this.filterCoaDEtailByDate(java.sql.Date.valueOf(sDate), java.sql.Date.valueOf(eDate));
    }
    
    public Integer getCurrentUsr() {
       Integer user;
        user = Integer.parseInt( resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        return user;
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
}
