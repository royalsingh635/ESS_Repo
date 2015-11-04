package mmbiapp.view.beans;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import mmbiapp.view.dc.TagDC;

import mmbiapp.view.dc.TagForItmDC;

import mmbiapp.view.dc.TickerDC;

import mmbiapp.view.dc.TimeSelectorDC;

import oracle.adf.model.BindingContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.nav.RichCommandLink;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class MMBiBean {
    private static ADFLogger _log = ADFLogger.createADFLogger(MMBiBean.class);
    private boolean refresh;
    /*** ArrayList to show selected customers **/
    private ArrayList<TagDC> custList = new ArrayList<TagDC>();
    private String custName;

    /*** ArrayList to show selected Organisation **/
   private ArrayList<TagDC> orgList = new ArrayList<TagDC>();
    private String OrgName;

    /*** ArrayList to show selected Organisation **/
   private ArrayList<TagForItmDC> itmList = new ArrayList<TagForItmDC>();
    private String itmName;

    /*** ArrayList to show selected Organisation **/
    private ArrayList<TagForItmDC> itmGrp = new ArrayList<TagForItmDC>();
    private String grpNm;
    
    private ArrayList<TickerDC> tickerList = new ArrayList<TickerDC>();
    private ArrayList<TimeSelectorDC> timeSelectorList = new ArrayList<TimeSelectorDC>();
    private StringBuffer  initTfId = new StringBuffer("1");
    private Map<String, Object> paramMap = new HashMap<String, Object>();
    private static ADFLogger adfLog=(ADFLogger)ADFLogger.createADFLogger(MMBiBean.class);
    
    public MMBiBean() {
        timeSelectorList.add(new TimeSelectorDC("1", new StringBuffer("April"), false));
        timeSelectorList.add(new TimeSelectorDC("2", new StringBuffer("Quarter"), true));
        timeSelectorList.add(new TimeSelectorDC("3", new StringBuffer("Half"), false));
        timeSelectorList.add(new TimeSelectorDC("4", new StringBuffer("Year"), false));
        timeSelectorList.add(new TimeSelectorDC("5", new StringBuffer("Time"), false));
        tickerList.add(new TickerDC(118, new StringBuffer("$ 81.09 mn"), new StringBuffer("Stock in Hand"), true));
        tickerList.add(new TickerDC(119, new StringBuffer("$ 59.09 mn"), new StringBuffer("Ordered Stock"), false));
        tickerList.add(new TickerDC(120, new StringBuffer("30.09 mn"), new StringBuffer("Requirements"), false));
        tickerList.add(new TickerDC(121, new StringBuffer("40.03 mn"), new StringBuffer("Purchases"), false));
        tickerList.add(new TickerDC(122, new StringBuffer("52.09 mn"), new StringBuffer("Consumptions"), false));
        tickerList.add(new TickerDC(123, new StringBuffer("13.09 %"), new StringBuffer("Credit"), false));
    }
    /**Method to get Binding Container*/
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    public void addOrgAction(ActionEvent actionEvent) {
        Integer i = 1;
        if (OrgName != "") {
            for (TagDC dc : orgList) {
                if (dc.getIdSecond().toString().equals("-1")) {
                    _log.fine("Second Id is -1");
                    OperationBinding binding = this.getBindings().getOperationBinding("getOrgIdOnOrgNm");
                    binding.getParamsMap().put("arg", new StringBuffer(OrgName));
                    binding.execute();
                    StringBuffer newOrgNm = new StringBuffer("");
                    if (OrgName.length() >= 12) {
                        newOrgNm = new StringBuffer(OrgName.substring(0, 12) + "..");
                    } else {
                        newOrgNm = new StringBuffer(OrgName);
                    }
                    if (binding.getResult() != null) {
                        _log.fine("Second Id is -1 so " + binding.getResult());
                        dc.setIdSecond(new StringBuffer(binding.getResult().toString()));
                        dc.setDescSecond(new StringBuffer(OrgName));
                        dc.setValueSecond(newOrgNm);
                        i = 2;
                        break;
                    }
                } else if (dc.getIdFirst().toString().equals("-1")) {
                    _log.fine("First Id is -1");
                    OperationBinding binding = this.getBindings().getOperationBinding("getOrgIdOnOrgNm");
                    binding.getParamsMap().put("arg", new StringBuffer(OrgName));
                    binding.execute();
                    StringBuffer newOrgNm = new StringBuffer("");
                    if (OrgName.length() >= 12) {
                        newOrgNm = new StringBuffer(OrgName.substring(0, 12) + "..");
                    } else {
                        newOrgNm = new StringBuffer(OrgName);
                    }
                    if (binding.getResult() != null) {
                        _log.fine("First Id is -1 so " + binding.getResult());
                        dc.setIdFirst(new StringBuffer(binding.getResult().toString()));
                        dc.setDescFirst(new StringBuffer(OrgName));
                        dc.setValueFirst(newOrgNm);
                        i = 2;
                        break;
                    }
                }
            }
            if (i == 1 && OrgName!=null) {
                _log.fine("New Id Added.");
                OperationBinding binding = this.getBindings().getOperationBinding("getOrgIdOnOrgNm");
                binding.getParamsMap().put("arg", new StringBuffer(OrgName));
                binding.execute();
                StringBuffer newOrgNm = new StringBuffer("");
                if (OrgName.length() >= 12) {
                    newOrgNm = new StringBuffer(OrgName.substring(0, 12) + "..");
                } else {
                    newOrgNm = new StringBuffer(OrgName);
                }
                if (binding.getResult() != null) {
                    orgList.add(new TagDC(new StringBuffer(binding.getResult().toString()), newOrgNm,
                                          new StringBuffer(OrgName), new StringBuffer("-1"), new StringBuffer(""),
                                          new StringBuffer("")));
                }
            }
            OrgName = "";
        }
    }
    
    
    
    public void removeOrgAction(ActionEvent actionEvent) {
        RichCommandLink ob = (RichCommandLink)actionEvent.getSource();
        StringBuffer custId = (StringBuffer)ob.getAttributes().get("supplierId");
        /* boolean contains = custList.contains(emp);
        if(contains == true){
            custList.remove(emp);
            System.out.println("Emp : "+emp);
        } */
        TagDC removeCust = null;
        for (TagDC dc : orgList) {
            if (dc.getIdFirst().toString().equals(custId.toString())) {
                _log.fine("First Id removed.");
                dc.setIdFirst(new StringBuffer("-1"));
                dc.setValueFirst(new StringBuffer(""));
                if (dc.getIdFirst().toString().equals("-1") && dc.getIdSecond().toString().equals("-1")) {
                    _log.fine("First & Second both Id's removed.");
                    removeCust = dc;
                    break;
                }
                break;
            }
            if (dc.getIdSecond().toString().equals(custId.toString())) {
                _log.fine("Second Id removed.");
                dc.setIdSecond(new StringBuffer("-1"));
                dc.setValueSecond(new StringBuffer(""));
                if (dc.getIdFirst().toString().equals("-1") && dc.getIdSecond().toString().equals("-1")) {
                    _log.fine("First & Second both Id's removed.");
                    removeCust = dc;
                    break;
                }
                break;
            }
            if (dc.getIdFirst().toString().equals("-1") && dc.getIdSecond().toString().equals("-1")) {
                _log.fine("First & Second both Id's removed.");
                removeCust = dc;
                break;
            }
        }
        if (removeCust != null) {
            _log.fine("Removed Tag is :" + removeCust.getValueFirst() + " " + removeCust.getValueSecond());
            orgList.remove(removeCust);
        }
    }
    /**
     * To put the parameters for the dynamic region
     * @return
     */
    public Map<String, Object> getParamMap() {
        paramMap.clear();
        _log.fine("NEW PARAMS ASSIGNED.");
        paramMap.put("GLBL_APP_USR", resolvElO("#{pageFlowScope.GLBL_APP_USR}"));
        paramMap.put("GLBL_APP_USR_ROLE", 1);
        paramMap.put("GLBL_APP_USR_ORG", resolvElO("#{pageFlowScope.GLBL_APP_USR_ORG}"));
        paramMap.put("GLBL_APP_VER", "1");
        paramMap.put("GLBL_APP_REG", 1);
        paramMap.put("GLBL_APP_DB_VER", 1);
        paramMap.put("GLBL_APP_SESSID", "1");
        paramMap.put("GLBL_APP_SERV_LOC", resolvElO("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        paramMap.put("GLBL_APP_CLD_ID", resolvElO("#{pageFlowScope.GLBL_APP_CLD_ID}"));
        paramMap.put("GLBL_APP_INST_ID", 1);
        paramMap.put("GLBL_HO_ORG_ID", resolvElO("#{pageFlowScope.GLBL_HO_ORG_ID}"));
        paramMap.put("GLBL_DT_FORMAT", resolvElO("#{pageFlowScope.GLBL_DT_FORMAT}"));
        paramMap.put("GLBL_NUM_FORMAT", resolvElO("#{pageFlowScope.GLBL_NUM_FORMAT}"));
        paramMap.put("GLBL_AMT_DIGIT", resolvElO("#{pageFlowScope.GLBL_AMT_DIGIT}"));
        paramMap.put("GLBL_RATE_DIGIT", resolvElO("#{pageFlowScope.GLBL_RATE_DIGIT}"));
        paramMap.put("GLBL_CURR_DIGIT", resolvElO("#{pageFlowScope.GLBL_CURR_DIGIT}"));
        paramMap.put("GLBL_QTY_DIGIT", resolvElO("#{pageFlowScope.GLBL_QTY_DIGIT}"));
        paramMap.put("PARAM_PG_ADD_MD", resolvElO("#{pageFlowScope.PARAM_PG_ADD_MD}"));
        paramMap.put("PARAM_PG_EDIT_MD", resolvElO("#{pageFlowScope.PARAM_PG_EDIT_MD}"));
        paramMap.put("PARAM_PG_VIEW_MD", resolvElO("#{pageFlowScope.PARAM_PG_VIEW_MD}"));
        paramMap.put("PARAM_PG_DEL_MD", resolvElO("#{pageFlowScope.PARAM_PG_DEL_MD}"));
        paramMap.put("PARAM_PG_CALLED", resolvElO("#{pageFlowScope.PARAM_PG_CALLED}"));
       // paramMap.put("WHERE_CLAUSE_FOR_ORG_DETAILS", this.getWhereClauseFromSelectedValues());
        return paramMap;
    }
    /**Method to resolve expression- returns String value*/
    public Object resolvElO(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        Object obj = valueExp.getValue(elContext);
        return obj;
        
    }
    
    /**
     * Methdod to return refresh to refresh the regionBinding 
     * @return
     */
    public boolean isRefresh(){
        _log.fine("Refreshhed : "+refresh);
        if(refresh == true){
            refresh = false;
            return true;
        }
        return refresh;
    }
    
    
    /**
     * Method to apply filter on the basis of selected Organisation
     * @param actionEvent
     */
    public void applyFilterForSelectedOrganisationsAction(ActionEvent actionEvent) {
        Integer i = 0;
        OperationBinding operationBinding = this.getBindings().getOperationBinding("fetchCurrentTaskFlowId");
        operationBinding.execute();
        if(operationBinding.getResult() != null){
           i = Integer.valueOf(operationBinding.getResult().toString());
        }
         adfLog.info("fetchCurrentTaskFlowId     "+i);
        getWhereClauseFromSelectedValues();
        OperationBinding binding = this.getBindings().getOperationBinding("filterAllViewObjects");
        binding.getParamsMap().put("id", i);
        binding.getParamsMap().put("clause", getWhereClauseFromSelectedValues());
        binding.execute();
    }
    
    /**
     * method to Clear org list
     * @param actionEvent
     */
    public void clearOrgListAction(ActionEvent actionEvent) {
        orgList.clear();
        Integer i = 0;
        OperationBinding operationBinding = this.getBindings().getOperationBinding("fetchCurrentTaskFlowId");
        operationBinding.execute();
        if(operationBinding.getResult() != null){
           i = Integer.valueOf(operationBinding.getResult().toString());
        }
        getWhereClauseFromSelectedValues();
        OperationBinding binding = this.getBindings().getOperationBinding("filterAllViewObjects");
        binding.getParamsMap().put("id", i);
        binding.getParamsMap().put("clause", getWhereClauseFromSelectedValues());
        binding.execute();
    }
    
    
    
    /**
     * Get final where clause string
     * @return
     */
    public StringBuffer getWhereClauseFromSelectedValues() {
        StringBuffer clause = new StringBuffer("");
        if (!getItmInClause().toString().equals("")) {
            clause = clause.append(" AND " + getItmInClause().toString());
        }
        if (!getOrgInClause().toString().equals("")) {
            clause = clause.append(" AND " + getOrgInClause().toString());
        }
    /*     if (!getCustomerInClause().toString().equals("")) {
            clause = clause.append(" AND " + getCustomerInClause().toString());
        } */
        if (!getItmGrpClause().toString().equals("")) {
            clause = clause.append(" AND " + getItmGrpClause().toString());
        }
        OperationBinding binding = this.getBindings().getOperationBinding("initWhereClause");
        binding.getParamsMap().put("whereClause", clause);
        binding.execute();
           adfLog.info("Final where clause is : " + clause);
        return clause;
    }
    
    /**
     * Method that returns the in clause for selected Items;
     * @return
     */
    public StringBuffer getItmInClause() {
        StringBuffer clause = new StringBuffer("");
        if (this.itmList.size() > 0) {
            Integer count = 1;
            clause = new StringBuffer("ITM_ID IN (");
            for (TagForItmDC dc : itmList) {
                if (!dc.getIdFirst().toString().equals("-1")) {
                    clause = clause.append(" '" + dc.getIdFirst() + "'");
                }
                if (!dc.getIdFirst().toString().equals("-1") && !dc.getIdSecond().toString().equals("-1")) {
                    clause = clause.append(",");
                }
                if (!dc.getIdSecond().toString().equals("-1")) {
                    clause = clause.append(" '" + dc.getIdSecond() + "'");
                }
                if (count < itmList.size()) {
                    clause = clause.append(",");
                }

                count = count + 1;
            }
            clause = clause.append(" )");
        }
        return clause;
    }
    /**
     * Method that returns the in clause for selected organisation;
     * @return
     */
    public StringBuffer getOrgInClause() {
        StringBuffer clause = new StringBuffer("");
        if (this.orgList.size() > 0) {
            Integer count = 1;
            clause = new StringBuffer("ORG_ID IN (");
            for (TagDC dc : orgList) {
                if (!dc.getIdFirst().toString().equals("-1")) {
                    clause = clause.append(" '" + dc.getIdFirst() + "'");
                }
                if (!dc.getIdFirst().toString().equals("-1") && !dc.getIdSecond().toString().equals("-1")) {
                    clause = clause.append(",");
                }
                if (!dc.getIdSecond().toString().equals("-1")) {
                    clause = clause.append(" '" + dc.getIdSecond() + "'");
                }
                if (count < orgList.size()) {
                    clause = clause.append(",");
                }

                count = count + 1;
            }
            clause = clause.append(" )");
        }
        return clause;
    }
    
    
    /**
     * Method that returns the in clause for selected ItemGrp
     * @return
     */
    public StringBuffer getItmGrpClause() {
        StringBuffer clause = new StringBuffer("");
        if (this.itmGrp.size() > 0) {
            Integer count = 1;
            clause = new StringBuffer("GRP_ID IN (");
            for (TagForItmDC dc : itmGrp) {
                if (!dc.getIdFirst().toString().equals("-1")) {
                    clause = clause.append(" '" + dc.getIdFirst() + "'");
                }
                if (!dc.getIdFirst().toString().equals("-1") && !dc.getIdSecond().toString().equals("-1")) {
                    clause = clause.append(",");
                }
                if (!dc.getIdSecond().toString().equals("-1")) {
                    clause = clause.append(" '" + dc.getIdSecond() + "'");
                }
                if (count < itmGrp.size()) {
                    clause = clause.append(",");
                }

                count = count + 1;
            }
            clause = clause.append(" )");
        }
        return clause;
    }
    
    public void setCustList(ArrayList<TagDC> custList) {
        this.custList = custList;
    }

    public ArrayList<TagDC> getCustList() {
        return custList;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustName() {
        return custName;
    }

    public void setOrgList(ArrayList<TagDC> orgList) {
        this.orgList = orgList;
    }

    public ArrayList<TagDC> getOrgList() {
        return orgList;
    }

    public void setOrgName(String OrgName) {
        this.OrgName = OrgName;
    }

    public String getOrgName() {
        return OrgName;
    }

    public void setItmList(ArrayList<TagForItmDC> itmList) {
        this.itmList = itmList;
    }

    public ArrayList<TagForItmDC> getItmList() {
        return itmList;
    }

    public void setItmName(String itmName) {
        this.itmName = itmName;
    }

    public String getItmName() {
        return itmName;
    }

    public void setItmGrp(ArrayList<TagForItmDC> itmGrp) {
        this.itmGrp = itmGrp;
    }

    public ArrayList<TagForItmDC> getItmGrp() {
        return itmGrp;
    }

    public void setGrpNm(String grpNm) {
        this.grpNm = grpNm;
    }

    public String getGrpNm() {
        return grpNm;
    }

    public void setTickerList(ArrayList<TickerDC> tickerList) {
        this.tickerList = tickerList;
    }

    public ArrayList<TickerDC> getTickerList() {
        return tickerList;
    }

    public void setTimeSelectorList(ArrayList<TimeSelectorDC> timeSelectorList) {
        this.timeSelectorList = timeSelectorList;
    }

    public ArrayList<TimeSelectorDC> getTimeSelectorList() {
        return timeSelectorList;
    }

    public void setInitTfId(StringBuffer initTfId) {
        this.initTfId = initTfId;
    }

    public StringBuffer getInitTfId() {
        return initTfId;
    }

    public void topTimeLinkAction(ActionEvent actionEvent) {
        RichCommandLink ob = (RichCommandLink)actionEvent.getSource();
        String linkId = (String)ob.getAttributes().get("linkId");
        _log.fine("LInkID id : " + linkId);
        for (TimeSelectorDC list : timeSelectorList) {
            _log.fine(" matched : " + list.getLinkId().equals(linkId));
            if (list.getLinkId().equals(linkId)) {
                _log.fine("Set to true");
                list.setActive(true);
            } else {
                list.setActive(false);
            }
        }
    }

    /**
     * Method to Changes the Page to Organisation
     * @return
     */

    public String organisationSelectionAction() {
        adfLog.info("Region Link Setting TF to 1");
        this.initTfId = new StringBuffer("1");
        refresh = true;
        return null;
    }
    
    /**
     * Method to Changes the Page to Warehouse
     * @return
     */

    public String warehouseSelectionAction() {
        adfLog.info("Region Link Setting TF to 2");
        this.initTfId = new StringBuffer("2");
        refresh = true;
        return null;
    }
    
    /**
     * Method to Changes the Page to Suppliers
     * @return
     */

    public String supplierSelectionAction() {
        adfLog.info("Region Link Setting TF to 3");
        this.initTfId = new StringBuffer("3");
        refresh = true;
        return null;
    }
    
    /**
     * Method to Changes the Page to Product
     * @return
     */

    public String productSelectionAction() {
        adfLog.info("Region Link Setting TF to 4");
        this.initTfId = new StringBuffer("4");
        refresh = true;
        return null;
    }
    
    /**
     * Method to Changes the Page to Product Group
     * @return
     */

    public String productGroupSelectionAction() {
        adfLog.info("Region Link Setting TF to 5");
        this.initTfId = new StringBuffer("5");
        refresh = true;
        return null;
    }
    
    /**
     * Method to Changes the Page to Consummer
     * @return
     */

    public String consummerSelectionAction() {
        adfLog.info("Region Link Setting TF to 6");
        this.initTfId = new StringBuffer("6");
        refresh = true;
        return null;
    }
    
    /**
     * Method to Changes the Page to Region
     * @return
     */

    public String regionSelectionAction() {
     System.out.println("Region Link Setting TF to 7");
        this.initTfId = new StringBuffer("7");
        refresh = true;
        return null;
    }

    /**
     * Method to set current tickerId
     * @param actionEvent
     */
    public void setTickerIdAction(ActionEvent actionEvent) {
        
      
            RichCommandLink ob = (RichCommandLink)actionEvent.getSource();
            Integer lnkd = (Integer)ob.getAttributes().get("linkId");
            OperationBinding binding = this.getBindings().getOperationBinding("initCurrentTickerId");
            binding.getParamsMap().put("currentTickerId", lnkd);
            binding.execute();
            for (TickerDC dc : this.tickerList) {
                if (dc.getTickerId().equals(lnkd)) {
                    dc.setActive(true);
                } else {
                    dc.setActive(false);
                }
            }
            OperationBinding newBinding = this.getBindings().getOperationBinding("filterMisLov");
            newBinding.getParamsMap().put("typId", lnkd);
            newBinding.execute();
        
    }

}
