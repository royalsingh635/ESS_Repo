package budmis.view.bean;

import adf.utils.bean.ADFBeanUtils;

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.layout.RichPanelDashboard;

import oracle.binding.OperationBinding;

public class bdgMisStartPage {
    private String NavPaneBackGround = null;
    private RichPanelDashboard dashboardBindVar;

    public void setNavPaneBackGround(String NavPaneBackGround) {
        this.NavPaneBackGround = NavPaneBackGround;
    }

    public String getNavPaneBackGround() {
        return NavPaneBackGround;
    }

    public bdgMisStartPage() {
    }

    public void CustNavPaneACL(ActionEvent actionEvent) {
        this.setNavPaneBackGround("cust");
    }

    public void EmpNavPaneACL(ActionEvent actionEvent) {
        this.setNavPaneBackGround("emp");
    }

    public void ProductNavPaneACL(ActionEvent actionEvent) {
        this.setNavPaneBackGround("prd");
    }

    public void ProductGrpNavPaneACL(ActionEvent actionEvent) {
        this.setNavPaneBackGround("prdgrp");
    }

    public void RegionNavPaneACL(ActionEvent actionEvent) {
        this.setNavPaneBackGround("region");
    }

    public void CoaNavPaneACL(ActionEvent actionEvent) {
        this.setNavPaneBackGround("coa");
    }

    public Map getGraphName() {
        return new HashMap<Integer, String>() {
            @Override
            public String get(Object key) {
                String grphName = "";
                if (key != null) {
                    OperationBinding op = ADFBeanUtils.getOperationBinding("retriveGraphName");
                    op.getParamsMap().put("grphId", key);
                    op.execute();
                    if (op.getResult() != null)
                        grphName = op.getResult().toString();
                }
                return grphName;
            }
        };
    }

    public void setDashboardBindVar(RichPanelDashboard dashboardBindVar) {
        this.dashboardBindVar = dashboardBindVar;
    }

    public RichPanelDashboard getDashboardBindVar() {
        return dashboardBindVar;
    }

    public void addCustToListACL(ActionEvent actionEvent) {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("addIDName_Func");
        binding.getParamsMap().put("val", "C");
        binding.execute();
    }

    public void addEmpToListACL(ActionEvent actionEvent) {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("addIDName_Func");
        binding.getParamsMap().put("val", "E");
        binding.execute();
    }

    public void addProductToListACL(ActionEvent actionEvent) {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("addIDName_Func");
        binding.getParamsMap().put("val", "P");
        binding.execute();
    }

    public void addProductGrpToListACL(ActionEvent actionEvent) {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("addIDName_Func");
        binding.getParamsMap().put("val", "PG");
        binding.execute();
    }

    public void addRegionToListACL(ActionEvent actionEvent) {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("addIDName_Func");
        binding.getParamsMap().put("val", "R");
        binding.execute();
    }

    public void addCoaToListACL(ActionEvent actionEvent) {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("addIDName_Func");
        binding.getParamsMap().put("val", "CO");
        binding.execute();
    }

    public void deleteFilterACL(ActionEvent actionEvent) {
        UIComponent componentVal = actionEvent.getComponent();
        oracle.adf.view.rich.util.ResetUtils.reset(componentVal);
        Object objectVal = componentVal.getAttributes().get("delVal");
        String stringVal = objectVal.toString();
        System.out.println("bolll :::::" + stringVal);
        OperationBinding binding = ADFBeanUtils.getOperationBinding("deleteFilter_Func");
        binding.getParamsMap().put("val", stringVal);
        binding.execute();
    }

    public Map getGraphVisiblity() {
        return new HashMap<Integer, Boolean>() {
            @Override
            public Boolean get(Object key) {
                Boolean ret = false;
                if (key != null) {
                    OperationBinding op = ADFBeanUtils.getOperationBinding("chkGraphAvailablity");
                    op.getParamsMap().put("grphId", key);
                    op.execute();
                    if (op.getResult() != null && op.getResult().toString().equals("Y"))
                        ret = true;
                    else
                        ret = false;
                }
                //   dashboardBindVar.prepareOptimizedEncodingOfDeletedChild(FacesContext.getCurrentInstance(), Integer.parseInt(key.toString()) - 1);

                return ret;
            }
        };
    }

    public void searchCustomerACL(ActionEvent actionEvent) {
        OperationBinding op = ADFBeanUtils.getOperationBinding("SearchInGraphs_Func");
        op.getParamsMap().put("LinkId", "C");
        op.execute();
    }

    public void searchEmployeeACL(ActionEvent actionEvent) {
        OperationBinding op = ADFBeanUtils.getOperationBinding("SearchInGraphs_Func");
        op.getParamsMap().put("LinkId", "E");
        op.execute();
    }

    public void searchProductACL(ActionEvent actionEvent) {
        OperationBinding op = ADFBeanUtils.getOperationBinding("SearchInGraphs_Func");
        op.getParamsMap().put("LinkId", "P");
        op.execute();
    }

    public void searchProductGroupACL(ActionEvent actionEvent) {
        OperationBinding op = ADFBeanUtils.getOperationBinding("SearchInGraphs_Func");
        op.getParamsMap().put("LinkId", "PG");
        op.execute();
    }

    public void searchRegionACL(ActionEvent actionEvent) {
        OperationBinding op = ADFBeanUtils.getOperationBinding("SearchInGraphs_Func");
        op.getParamsMap().put("LinkId", "R");
        op.execute();
    }

    public void searchCoaACL(ActionEvent actionEvent) {
        OperationBinding op = ADFBeanUtils.getOperationBinding("SearchInGraphs_Func");
        op.getParamsMap().put("LinkId", "CO");
        op.execute();
    }

    public String exitFullScreenAction() {
        //   AdfFacesContext.getCurrentInstance().getPageFlowScope().put("P_IS_MAXIMIZED", "N");
        return "Back";
    }
}
