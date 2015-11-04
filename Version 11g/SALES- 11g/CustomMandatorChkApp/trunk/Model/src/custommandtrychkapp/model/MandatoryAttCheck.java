package custommandtrychkapp.model;

import custommandtrychkapp.model.data.A;
import custommandtrychkapp.model.services.CustomMandatoryChkAMImpl;

import java.util.ArrayList;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.share.logging.ADFLogger;
import oracle.jbo.client.Configuration;

import oracle.jbo.AttributeDef;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.server.ViewObjectImpl;

public class MandatoryAttCheck {
    private static ADFLogger _log = ADFLogger.createADFLogger(MandatoryAttCheck.class);
    // To store the application module
    private ApplicationModuleImpl appModule;
    private StringBuffer cldId;
    private Integer slocId;
    private StringBuffer orgId;
    CustomMandatoryChkAMImpl am = null;
    
    public MandatoryAttCheck(ApplicationModuleImpl appModule, StringBuffer cldId,StringBuffer orgId,Integer slocId ) {
        super();
        this.appModule = appModule;
        this.cldId = cldId;
        this.orgId = orgId;
        this.slocId = slocId;
    }

    public void setAppModule(ApplicationModuleImpl appModule) {
        this.appModule = appModule;
    }

    public ApplicationModuleImpl getAppModule() {
        return appModule;
    }
    public void setCldId(StringBuffer cldId) {
        this.cldId = cldId;
    }

    public StringBuffer getCldId() {
        if(cldId == null){
            return new StringBuffer("");
        }
        return cldId;
    }

    public void setSlocId(Integer slocId) {
        this.slocId = slocId;
    }

    public Integer getSlocId() {
        return slocId;
    }

    public void setOrgId(StringBuffer orgId) {
        this.orgId = orgId;
    }

    public StringBuffer getOrgId() {
        if(orgId == null){
            return new StringBuffer("");
        }
        return orgId;
    }
    
    /**
     * @param docId : Denotes the DocId of the Application
     * @param viewObjectNm : Denotes the viewObject in which check Needs to be made
     * @param tableNm : Denotes the table name on basis of which, mandatory check will be made.
     * @param rowItr : Represents the Iterator for rows that will be checked ()
     * @param rowList : Represents the ArrayList of rows that need to be checked
     * @return boolean
     * INFO :   User have the option to pass RowIterator or ArrayList of rows.
     *          If the user want to pass ArrayList of rows then he need to pass the iterator as null.
     *          However if he passed Iterator and ArrayList both, then the rows of the ArrayList will be considered
     *          for the operation.
     **/
    
    public Boolean isMandatoryAttributesEntered(Integer docId, StringBuffer viewObjectNm, StringBuffer tableNm,
                                                RowSetIterator rowItr, ArrayList<Row> rowList) throws Exception {
        //_log.info("Appmodule is : "+appModule);
        _log.info("CldId : "+getCldId()+" SlocId : "+getSlocId()+" OrgId : "+getOrgId());
        Boolean b = false;
        if (appModule == null) {
            _log.info("Application Module Exception Thrown.");
            throw new Exception("Application Module Not Found Exception !");
        } else if (appModule.findViewObject(viewObjectNm.toString()) == null) {
            throw new Exception("ViewObject Not Found Exception !");
        } else {
            _log.info("CustomMandatory AppModule is : "+getApplicationModule());
            
            ViewObjectImpl chkVO = getApplicationModule().getMandatoryChkVO();
            //_log.info("ViewObject MandatoryChk :"+chkVO);
            chkVO.setNamedWhereClauseParam("CldIdBind", getCldId().toString());
            chkVO.setNamedWhereClauseParam("OrgIdBind", getOrgId().toString());
            chkVO.setNamedWhereClauseParam("DocIdBind", docId);
            chkVO.setNamedWhereClauseParam("SlocIdBind", getSlocId());
            chkVO.setNamedWhereClauseParam("TableNmBind", (tableNm == null ? "" : tableNm.toString()));
            chkVO.executeQuery();
            _log.info("No. Of rows in MandatoryChk VO : "+chkVO.getEstimatedRowCount());
            
            // HashMap to hold the values of Mandatory columns
            ArrayList<A> mandAttrs = new ArrayList<A>();
            int i = chkVO.getRangeSize();
            chkVO.setRangeSize(-1);
            Row[] allRows = chkVO.getAllRowsInRange();
            for(Row r : allRows){
                Object req = r.getAttribute("IsMandatory");
                if(req != null){
                    if(req.toString().equals("Y")){
                        Object colNm = r.getAttribute("ColNm");
                        Object colDesc = (r.getAttribute("ColDesc") == null ? colNm : r.getAttribute("ColDesc"));
                        if(colNm != null){
                            mandAttrs.add(new A(new StringBuffer(colNm.toString()),new StringBuffer(colDesc.toString())));
                        }
                    }
                }
            }

            ViewObject viewObject = appModule.findViewObject(viewObjectNm.toString());
            if(viewObject == null){
                throw new Exception("ViewObject with name "+viewObjectNm+" Not found !");
            }else{
                // ArrayList to store actual attribute names.
                ArrayList<A> attrNm = new ArrayList<A>();
                AttributeDef[] attributeDef = viewObject.getAttributeDefs();
                for(AttributeDef def : attributeDef){
                    String columnName = def.getColumnName();
                    if(columnName != null){
                        for(A s : mandAttrs){
                            if(s.getAttNm().toString().equals(columnName)){
                                attrNm.add(new A(new StringBuffer(def.getName()), s.getAttDesc()));
                            }
                        }
                    }
                }
                //  ArryList to store list or required Attributes
                ArrayList<StringBuffer> requiredAttr = new ArrayList<StringBuffer>();
                if(rowItr != null){
                    while(rowItr.hasNext()){
                        Row row = rowItr.next();
                        for(A f : attrNm){
                            Object val = row.getAttribute(f.getAttNm().toString());
                            if(val == null){
                                requiredAttr.add(f.getAttDesc());
                            }
                        }
                    }
                    
                }else if(rowList != null){
                    for(Row r :rowList){
                        Row row = r;
                        for(A f : attrNm){
                            Object val = row.getAttribute(f.getAttNm().toString());
                            if(val == null){
                                requiredAttr.add(f.getAttDesc());
                            }
                        }
                    }
                }
                
                if(requiredAttr.size() > 0){
                    StringBuffer msg = new StringBuffer("");
                    msg.append("<html>");
                    msg.append("  <head></head>");
                    msg.append("  <body style='font-family:Arial;font-size:12px;'>");
                    msg.append("    <span style='color:red;font-weight:bold;'>* Mandatory Fields given below are Blank</span><br />");
                    msg.append("    <span style='color:red;font-weight:bold;'> &nbsp;&nbsp Please enter values in following attributes. </span>");
                    msg.append("    <ul style='color:navy;font-weight:bold;'>");
                    StringBuffer j = new StringBuffer("");
                    for(StringBuffer s : requiredAttr){
                        msg.append("      <li>"+s+"</li>");    
                    }
                    msg.append("    </ul>");
                    msg.append("  </body>");
                    msg.append("</html>");
                    FacesMessage message =
                        new FacesMessage(msg.toString()); //Enter the date
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                    
                }else{
                    b = true;
                }
            }
        }
        if(rowItr != null){
            rowItr.closeRowSetIterator();
        }
        return b;
    }
    
    public CustomMandatoryChkAMImpl getApplicationModule(){
        if(am == null){
            am = (CustomMandatoryChkAMImpl)Configuration.createRootApplicationModule("custommandtrychkapp.model.services.CustomMandatoryChkAM",
                                                                                     "CustomMandatoryChkAMLocal");
            _log.info("Am is : "+am);   
        }
        return am;
    }

}
