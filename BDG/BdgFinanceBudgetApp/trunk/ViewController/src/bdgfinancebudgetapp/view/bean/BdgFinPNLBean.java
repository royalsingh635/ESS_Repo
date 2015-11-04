package bdgfinancebudgetapp.view.bean;

import adf.utils.model.ADFModelUtils;

import bdgfinancebudgetapp.view.dc.CommonTagDC;
import bdgfinancebudgetapp.view.dc.TagDC;
import bdgfinancebudgetapp.view.utils.ADFUtils;

import java.util.ArrayList;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.PopupFetchEvent;

import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.context.RequestContext;

public class BdgFinPNLBean {
    /**Menu/Entity TypeId ->1(activeMenuId)
     * BreadCrumb Type -> 3 (For Filters)
     * 1 For Region
     * 2 For Employee
     * 3 For Customer
     * for dynamic view doc Struct(Entity)-> 1 FOR ORG, 2 FOR EMPLOYEE, 3 FOR Region, 4 FOR CUST, 5 FRO PRODUCT
     **/
    private String activeEntityId = "1";
    private String activeEntitynm = "Organization";

    public void setActiveEntitynm(String activeEntitynm) {
        this.activeEntitynm = activeEntitynm;
    }

    public String getActiveEntitynm() {
        return activeEntitynm;
    }
    private String hoverEntityId = "1";
    private String hoverEntityNm = "";

    public void setHoverEntityNm(String hoverEntityNm) {
        this.hoverEntityNm = hoverEntityNm;
    }

    public String getHoverEntityNm() {
        return hoverEntityNm;
    }

    public void setHoverEntityId(String hoverEntityId) {
        System.out.println("Setter of Set Hover Entity Id is called");
        /*  System.out.println("HoverEntityId=" + hoverEntityId);
        OperationBinding op = ADFUtils.findOperation("filterCommonVOForDocStruct");
        op.getParamsMap().put("structId", hoverEntityId);
        op.execute(); */
        this.hoverEntityId = hoverEntityId;
    }

    public String getHoverEntityId() {
        return hoverEntityId;
    }

    public void setActiveEntityId(String activeEntityId) {
        this.activeEntityId = activeEntityId;
    }

    public String getActiveEntityId() {
        return activeEntityId;
    }
    private StringBuffer activeMenuId = new StringBuffer("1");

    private String tabExpanded = "region";

    /*** ArrayList to show selected Region **/
    private ArrayList<TagDC> regionList = new ArrayList<TagDC>();
    private String regionName;
    private String filterName;
    private ArrayList<CommonTagDC> filterList = new ArrayList<CommonTagDC>();

    public void setFilterList(ArrayList filterList) {
        this.filterList = filterList;
    }

    public ArrayList getFilterList() {
        OperationBinding binding = ADFUtils.findOperation("fetchCommonList");
        if (binding != null) {
            binding.execute();
            filterList = (ArrayList<CommonTagDC>) binding.getResult();
        }
        return filterList;
    }
    private StringBuffer filterInClause = new StringBuffer("");
    private StringBuffer regionInClause = new StringBuffer("");

    /*** ArrayList to show selected Employee **/
    private ArrayList<TagDC> employeeList = new ArrayList<TagDC>();
    private String employeeName;
    private StringBuffer employeeInClause = new StringBuffer("");

    /*** ArrayList to show selected Customer **/
    private ArrayList<TagDC> customerList = new ArrayList<TagDC>();
    private String customerName;
    private StringBuffer customerInClause = new StringBuffer("");
    private RichPopup regionPopUpPgBind;
    private RichPopup employeePopUpPgBind;
    private RichPopup customerPopUpPgBind;

    public BdgFinPNLBean() {
    }

    public void setTabExpanded(String tabExpanded) {
        this.tabExpanded = tabExpanded;
    }

    public void setActiveMenuId(StringBuffer activeMenuId) {
        this.activeMenuId = activeMenuId;
    }

    public StringBuffer getActiveMenuId() {
        return activeMenuId;
    }

    public String getTabExpanded() {
        return tabExpanded;
    }

    public void setRegionList(ArrayList<TagDC> regionList) {
        this.regionList = regionList;
    }

    public ArrayList<TagDC> getRegionList() {
        OperationBinding binding = ADFUtils.findOperation("fetchRegionList");
        if (binding != null) {
            binding.execute();
            regionList = (ArrayList<TagDC>) binding.getResult();
        }
        return regionList;
    }

    public Integer getRegionListSize() {
        return regionList.size();
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setEmployeeList(ArrayList<TagDC> employeeList) {
        this.employeeList = employeeList;
    }

    public ArrayList<TagDC> getEmployeeList() {
        OperationBinding binding = ADFUtils.findOperation("fetchEmployeeList");
        if (binding != null) {
            binding.execute();
            employeeList = (ArrayList<TagDC>) binding.getResult();
        }
        return employeeList;
    }

    public Integer getEmployeeListSize() {
        return employeeList.size();
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setCustomerList(ArrayList<TagDC> customerList) {
        this.customerList = customerList;
    }

    public ArrayList<TagDC> getCustomerList() {
        OperationBinding binding = ADFUtils.findOperation("fetchCustomerList");
        if (binding != null) {
            binding.execute();
            customerList = (ArrayList<TagDC>) binding.getResult();
        }
        return customerList;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Integer getCustomerListSize() {
        return customerList.size();
    }

    public void addRegionButtonAL(ActionEvent actionEvent) {
        OperationBinding orgNmBind = ADFUtils.findOperation("getRegionIdOnRegionNm");
        orgNmBind.getParamsMap().put("arg", new StringBuffer(regionName));
        orgNmBind.execute();
        Object regionId = orgNmBind.getResult();
        if (regionId != null) {
            if (!regionId.equals("")) {
                OperationBinding binding = ADFUtils.findOperation("addRegion");
                binding.getParamsMap().put("regionId", new StringBuffer(regionId.toString()));
                binding.getParamsMap().put("regionName", new StringBuffer(regionName));
                binding.execute();
                regionName = "";
                System.out.println("Added");
            }
        } else {
            FacesMessage msg = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2317"));
            msg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        regionName = "";
    }

    public void removeRegionButtonAL(ActionEvent actionEvent) {
        RichLink ob = (RichLink) actionEvent.getSource();
        StringBuffer regionId = (StringBuffer) ob.getAttributes().get("regionId");
        OperationBinding binding = ADFUtils.findOperation("removeRegion");
        binding.getParamsMap().put("regionId", regionId);
        binding.execute();
    }

    public void clearRegionListAL(ActionEvent actionEvent) {
        OperationBinding removeBinding = ADFUtils.findOperation("clearRegionList");
        removeBinding.execute();

        regionPopUpPgBind.hide();
    }

    public void applyFilterForSelectedRegionAL(ActionEvent actionEvent) {
        //Start
        OperationBinding getInClause = ADFUtils.findOperation("valuesOfINClauseTagDC");
        getInClause.getParamsMap().put("searchList", regionList);
        getInClause.execute();
        regionInClause = new StringBuffer("");
        if (getInClause.getResult() != null && getInClause.getResult().toString().length() > 0)
            regionInClause = regionInClause.append(getInClause.getResult());
        System.out.println("InClause For Region=" + regionInClause);
        //End

        OperationBinding setInClause = ADFUtils.findOperation("setInClauseAsBindVar");
        setInClause.getParamsMap().put("menuId", "1");
        setInClause.getParamsMap().put("regionInClause", regionInClause);
        setInClause.getParamsMap().put("eoIdInClause", employeeInClause);
        setInClause.getParamsMap().put("custEoIdInClause", customerInClause);

        setInClause.execute();

        regionPopUpPgBind.hide();
    }

    public String regionDtlAction() {
        tabExpanded = "region";
        setActiveMenuId(new StringBuffer("1"));

        return null;
    }

    public String employeeDtlAction() {
        tabExpanded = "employee";
        setActiveMenuId(new StringBuffer("2"));

        return null;
    }

    public String customerDtlAction() {
        tabExpanded = "customer";
        setActiveMenuId(new StringBuffer("3"));

        return null;
    }

    ///// For Employees
    public void addEmployeeButtonAL(ActionEvent actionEvent) {
        OperationBinding orgNmBind = ADFUtils.findOperation("getEmployeeIdOnEmployeeNm");
        orgNmBind.getParamsMap().put("arg", new StringBuffer(employeeName));
        orgNmBind.execute();
        Object employeeId = orgNmBind.getResult();
        if (employeeId != null) {
            if (!employeeId.equals("")) {
                OperationBinding binding = ADFUtils.findOperation("addEmployee");
                binding.getParamsMap().put("employeeId", new StringBuffer(employeeId.toString()));
                binding.getParamsMap().put("employeeName", new StringBuffer(employeeName));
                binding.execute();
                employeeName = "";
                System.out.println("Added");
            }
        } else {
            FacesMessage msg = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2318"));
            msg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        employeeName = "";
    }

    public void removeEmployeeButtonAL(ActionEvent actionEvent) {
        RichLink ob = (RichLink) actionEvent.getSource();
        StringBuffer employeeId = (StringBuffer) ob.getAttributes().get("employeeId");
        OperationBinding binding = ADFUtils.findOperation("removeEmployee");
        binding.getParamsMap().put("employeeId", employeeId);
        binding.execute();
    }

    public void clearEmployeeListAL(ActionEvent actionEvent) {
        OperationBinding removeBinding = ADFUtils.findOperation("clearEmployeeList");
        removeBinding.execute();

        employeePopUpPgBind.hide();
    }

    public void applyFilterForSelectedEmployeeAL(ActionEvent actionEvent) {
        /*  System.out.println("Region is " + regionInClause + " customer " + customerInClause + " employee " +
                           employeeInClause); */
        //Start
        OperationBinding getInClause = ADFUtils.findOperation("valuesOfINClauseTagDC");
        getInClause.getParamsMap().put("searchList", employeeList);
        getInClause.execute();
        employeeInClause = new StringBuffer("");
        if (getInClause.getResult() != null && getInClause.getResult().toString().length() > 0)
            employeeInClause = employeeInClause.append(getInClause.getResult());
        System.out.println("InClause For Employee=" + employeeInClause);
        //End

        OperationBinding setInClause = ADFUtils.findOperation("setInClauseAsBindVar");
        setInClause.getParamsMap().put("menuId", "2");
        setInClause.getParamsMap().put("regionInClause", regionInClause);
        setInClause.getParamsMap().put("eoIdInClause", employeeInClause);
        setInClause.getParamsMap().put("custEoIdInClause", customerInClause);
        setInClause.execute();

        employeePopUpPgBind.hide();
    }

    //////   FOr Customer

    public void addCustomerButtonAL(ActionEvent actionEvent) {
        OperationBinding orgNmBind = ADFUtils.findOperation("getCustomerIdOnCustomerNm");
        orgNmBind.getParamsMap().put("arg", new StringBuffer(customerName));
        orgNmBind.execute();
        Object customerId = orgNmBind.getResult();
        if (customerId != null) {
            if (!customerId.equals("")) {
                OperationBinding binding = ADFUtils.findOperation("addCustomer");
                binding.getParamsMap().put("customerId", new StringBuffer(customerId.toString()));
                binding.getParamsMap().put("customerName", new StringBuffer(customerName));
                binding.execute();
                customerName = "";
                System.out.println("Added");
            }
        } else {
            FacesMessage msg = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.933"));
            msg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        customerName = "";
    }

    public void removeCustomerButtonAL(ActionEvent actionEvent) {
        RichLink ob = (RichLink) actionEvent.getSource();
        StringBuffer customerId = (StringBuffer) ob.getAttributes().get("customerId");
        OperationBinding binding = ADFUtils.findOperation("removeCustomer");
        binding.getParamsMap().put("customerId", customerId);
        binding.execute();
    }

    public void clearCustomerListAL(ActionEvent actionEvent) {
        OperationBinding removeBinding = ADFUtils.findOperation("clearCustomerList");
        removeBinding.execute();

        customerPopUpPgBind.hide();
    }

    public void applyFilterForSelectedCustomerAL(ActionEvent actionEvent) {
        //Start
        OperationBinding getInClause = ADFUtils.findOperation("valuesOfINClauseTagDC");
        getInClause.getParamsMap().put("searchList", customerList);
        getInClause.execute();
        customerInClause = new StringBuffer("");
        if (getInClause.getResult() != null && getInClause.getResult().toString().length() > 0)
            customerInClause = customerInClause.append(getInClause.getResult());
        System.out.println("InClause For Customer=" + customerInClause);
        //End

        OperationBinding setInClause = ADFUtils.findOperation("setInClauseAsBindVar");
        setInClause.getParamsMap().put("menuId", "3");
        setInClause.getParamsMap().put("regionInClause", regionInClause);
        setInClause.getParamsMap().put("eoIdInClause", employeeInClause);
        setInClause.getParamsMap().put("custEoIdInClause", customerInClause);
        setInClause.execute();

        customerPopUpPgBind.hide();
    }

    public void setRegionPopUpPgBind(RichPopup regionPopUpPgBind) {
        this.regionPopUpPgBind = regionPopUpPgBind;
    }

    public RichPopup getRegionPopUpPgBind() {
        return regionPopUpPgBind;
    }

    public void setEmployeePopUpPgBind(RichPopup employeePopUpPgBind) {
        this.employeePopUpPgBind = employeePopUpPgBind;
    }

    public RichPopup getEmployeePopUpPgBind() {
        return employeePopUpPgBind;
    }

    public void setCustomerPopUpPgBind(RichPopup customerPopUpPgBind) {
        this.customerPopUpPgBind = customerPopUpPgBind;
    }

    public RichPopup getCustomerPopUpPgBind() {
        return customerPopUpPgBind;
    }

    public void entityDtlAction(ActionEvent actionEvent) {
        RichLink ob = (RichLink) actionEvent.getSource();
        this.setActiveEntityId(ob.getAttributes().get("docStructId").toString());
        this.setActiveEntitynm(ob.getAttributes().get("docStructNm").toString());
        OperationBinding opFilter = ADFUtils.findOperation("SetHeaderOnStructure");
        opFilter.getParamsMap().put("structId", ob.getAttributes().get("docStructId"));
        opFilter.execute();
    }

    public void addFilterButtonAL(ActionEvent actionEvent) {
        OperationBinding orgNmBind = ADFUtils.findOperation("getFilterIdOnFilterNm");
        orgNmBind.getParamsMap().put("filterNm", new StringBuffer(filterName));
        //orgNmBind.getParamsMap().put("docStructId", this.hoverEntityId);
        orgNmBind.execute();
        Object filterVal = orgNmBind.getResult();
        if (filterVal != null) {
            if (!filterVal.equals("")) {
                OperationBinding binding = ADFUtils.findOperation("addFilterToList");
                binding.getParamsMap().put("filterId", new StringBuffer(this.hoverEntityId));
                binding.getParamsMap().put("filterVal", new StringBuffer(filterVal.toString()));
                binding.getParamsMap().put("filterName", new StringBuffer(filterName));
                binding.execute();
                filterName = "";
                System.out.println("Added");
            }
        } else {
            String dispMsg = ADFModelUtils.resolvRsrc("LBL.4851") + hoverEntityNm + ADFModelUtils.resolvRsrc("LBL.608");
            FacesMessage msg = new FacesMessage(dispMsg);
            msg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        filterName = "";
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public String getFilterName() {
        return filterName;
    }

    public void filterPopupFetchListener(PopupFetchEvent popupFetchEvent) {
        System.out.println("Popup fetch Listener is called");
        RichPopup ob = (RichPopup) popupFetchEvent.getSource();
        System.out.println("Value is =" + ob);
        System.out.println("'Value 2=" + ob.getLauncherVar());
        //this.setHoverEntityId((String) ob.getAttributes().get("docStructId"));
        // this.setHoverEntityNm((String) ob.getAttributes().get("docStructNm"));
        System.out.println("HoverEntityId=" + this.hoverEntityId);
        System.out.println("HoverEntityNm=" + this.hoverEntityNm);
        OperationBinding op = ADFUtils.findOperation("filterCommonVOForDocStruct");
        op.getParamsMap().put("structId", hoverEntityId);
        op.execute();
    }


    public void goToBdgAnalysisAL(ActionEvent actionEvent) {
        System.out.println("In action listener ------1");
        RichLink ob = (RichLink) actionEvent.getSource();
        System.out.println("In action listener ------2");
        //this.setActiveEntityId(ob.getAttributes().get("docStructId").toString());
        System.out.println("In action listener ------3");
        RequestContext.getCurrentInstance().getPageFlowScope().put("P_ANALYSIS_TYPE",
                                                                   ob.getAttributes().get("analysisType"));
        System.out.println("In action listener ------4");
        RequestContext.getCurrentInstance().getPageFlowScope().put("P_DOC_STRUCT", ob.getAttributes().get("docStruct"));
        System.out.println("In action listener ------5");
        RequestContext.getCurrentInstance().getPageFlowScope().put("P_DOC_STRUCT_VAL",
                                                                   ob.getAttributes().get("structVal"));
        System.out.println("In action listener ------6");
        RequestContext.getCurrentInstance().getPageFlowScope().put("P_START_DT", ob.getAttributes().get("prdStartDt"));
        System.out.println("In action listener ------7");
        RequestContext.getCurrentInstance().getPageFlowScope().put("P_END_DT", ob.getAttributes().get("prdEndDt"));
        System.out.println("In action listener ------8");
        RequestContext.getCurrentInstance().getPageFlowScope().put("P_STRUCT_DPND_VAL",
                                                                   ob.getAttributes().get("structDepdVal"));
        //P_DOC_STRUCT -> this.setActiveEntityId(ob.getAttributes().get("docStruct"));
        //P_DOC_STRUCT_VAL -> this.setActiveEntityId(ob.getAttributes().get("structVal"));
        //P_START_DT-> this.setActiveEntityId(ob.getAttributes().get("prdStartDt"));
        //P_END_DT-> this.setActiveEntityId(ob.getAttributes().get("prdEndDt"));
        //P_STRUCT_DPND_VAL-> this.setActiveEntityId(ob.getAttributes().get("structDepdVal"));
        System.out.println("In action listener end");

    }

    public String goToBdgAnalysisAction() {
        System.out.println("In action");
        return "analysis";
    }

    public String collapsePageAction() {
        AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
        Map pageFlowScope = adfFacesContext.getPageFlowScope();
        pageFlowScope.put("P_IS_EXPANDED", "N");
        return "collapse";
    }
}
