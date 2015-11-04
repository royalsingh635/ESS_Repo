package hcmlandingpage.view.beans;

import adf.utils.bean.ADFBeanUtils;

import adf.utils.ebiz.EbizParams;

import adf.utils.model.ADFModelUtils;

import alertupdateservice.view.bean.AlertUpdateServiceBean;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.HashMap;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIOutput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.component.rich.nav.RichCommandLink;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.OperationBinding;

import oracle.iam.management.ovd.config.jaxb.server.ViewType;

import oracle.jbo.domain.Number;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class HcmDashboardBean
{
    private RichSelectOneChoice criteriaValueBinging;
    private RichInputText criteriaValueIdBinding;
    private RichInputListOfValues criteriaValueNameBinding;
    Number million = new Number(1000000);
    Number thousand = new Number(1000);
    Number zero = new Number(0);
    private String firstTkrCurrOTNotation = "M";
    private String firstTkrPrvsOTNotation = "M";
    private String secondTkrCurrentGrossSalPaidNotation = "M";
    private String secondTkrPreviousGrossSalPaidNotation = "M";
    private String thirdTkrCurrAbsentNotation = "M";
    private String dataRangeTkr1 = null;
    private String dataRangeTkr2 = null;
    private RichOutputText totalCurrentOT;
    private RichOutputText totalPreviousOT;
    private RichOutputText grossCurrentSalaryPaid;
    private RichOutputText grossPreviousSalaryPaid;
    private RichOutputText totalCurrentAbsent;
    private RichOutputText totalPreviousAbsent;
    private Number totalCurntOT = new Number(0);
    private Number totalPrvOT = new Number(0);
    private Number grossCurntSalaryPaid = new Number(0);
    private Number grossPrvSalaryPaid = new Number(0);
    private Number totalCurntAbsent = new Number(0);
    private Number totalPrvAbsent = new Number(0);
    private String paneItm = null;

    private RichOutputText grpIdBinding;
    private RichInputListOfValues grpName;
    private RichInputListOfValues grpNameBinding;
    private RichPopup empSearchPopPupBinding;
    private RichInputListOfValues employeeNmBinding;
    private RichInputText empDocIdBinding;
    private RichInputDate fromDateBinding;
    private RichInputDate toDateBinding;
    private RichInputText empDeptIdBinding;
    private RichInputListOfValues empDeptNMBinding;
    private RichInputText empLocationIdBinding;
    private RichInputListOfValues empLocationNmBinding;
    private AlertUpdateServiceBean bean = new AlertUpdateServiceBean();
    private RichPanelFormLayout panelForm;
    private int i = 0;
    private RichPanelGroupLayout panelGroup;
    private RichPopup showBigGraphPopupBinding;
    private StringBuffer desc = new StringBuffer("");
    public String pageName;
    private RichOutputText encashDocIdBinding;
    private RichOutputText salProcTxnDocIdBinding;
    private RichOutputText salIncrDocIdBinding;
    private RichOutputText fulllAndFinalDocIdBinding;

    public void setPageName(String pageName)
    {
        this.pageName = pageName;
    }

    public String getPageName()
    {
        return pageName;
    }

    public void setDesc(StringBuffer desc)
    {
        this.desc = desc;
    }

    public StringBuffer getDesc()
    {
        return desc;
    }


    public String graphId;
    private RichInputText nameStartsWithBinding;

    public void setGraphId(String graphId)
    {
        this.graphId = graphId;
    }

    public String getGraphId()
    {
        return graphId;
    }


    public String getText()
    {
        if (i == 0)
        {
            this.printDetail();
            i++;
        }
        return "Y";
    }

    public void printDetail()
    {

        UIOutput o1 =
            bean.getAlert("340", "500", EbizParams.GLBL_APP_CLD_ID(), EbizParams.GLBL_APP_SERV_LOC(),
                          EbizParams.GLBL_HO_ORG_ID(), EbizParams.GLBL_APP_USR_ORG(), EbizParams.GLBL_APP_USR(),
                          "00504");
        this.panelForm.getChildren().add(o1);
        System.out.println("addind Refresh");
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.panelForm);
        AdfFacesContext.getCurrentInstance().addPartialTarget(o1);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelGroup());


    }


    public void setEmpMap(HashMap empMap)
    {
        this.empMap = empMap;
    }

    public HashMap getEmpMap()
    {
        return empMap;
    }

    HashMap empMap = new HashMap();

    private RichInputListOfValues grpNmOnPopBinding;

    public void setPaneItm(String paneItm)
    {
        this.paneItm = paneItm;
    }

    public String getPaneItm()
    {
        return paneItm;
    }

    public void setTotalCurntAbsent(Number totalCurntAbsent)
    {
        this.totalCurntAbsent = totalCurntAbsent;
    }

    public Number getTotalCurntAbsent()
    {
        return (Number) this.getTotalCurrentAbsent().getValue();
        // return totalCurntAbsent;
    }

    public void setTotalPrvAbsent(Number totalPrvAbsent)
    {
        this.totalPrvAbsent = totalPrvAbsent;
    }

    public Number getTotalPrvAbsent()
    {
        return (Number) this.getTotalPreviousAbsent().getValue();
        //return totalPrvAbsent;
    }

    public void setGrossCurntSalaryPaid(Number grossCurntSalaryPaid)
    {
        this.grossCurntSalaryPaid = grossCurntSalaryPaid;
    }

    public Number getGrossCurntSalaryPaid()
    {
        return (Number) this.getGrossCurrentSalaryPaid().getValue();
        //return grossCurntSalaryPaid;
    }

    public void setGrossPrvSalaryPaid(Number grossPrvSalaryPaid)
    {
        this.grossPrvSalaryPaid = grossPrvSalaryPaid;
    }

    public Number getGrossPrvSalaryPaid()
    {
        return (Number) this.getGrossPreviousSalaryPaid().getValue();
        //return grossPrvSalaryPaid;
    }

    public void setTotalPrvOT(Number totalPrvOT)
    {
        this.totalPrvOT = totalPrvOT;
    }

    public Number getTotalPrvOT()
    {
        return (Number) this.getTotalPreviousOT().getValue();
        //return totalPrvOT;
    }

    public void setTotalCurntOT(Number totalCurntOT)
    {
        this.totalCurntOT = totalCurntOT;
    }

    public Number getTotalCurntOT()
    {
        return (Number) this.getTotalCurrentOT().getValue();
        //return totalCurntOT;
    }

    public void setDataRangeTkr1(String dataRangeTkr1)
    {
        this.dataRangeTkr1 = dataRangeTkr1;
    }

    public String getDataRangeTkr1()
    {
        OperationBinding data = ADFBeanUtils.getOperationBinding("getTkrDataRange");
        data.getParamsMap().put("pos", new Integer(1));
        data.execute();
        String name = "No Data Range Available";
        if (data.getResult() != null)
        {
            name = data.getResult().toString();
        }
        return name;
        // return dataRangeTkr1;
    }

    public void setDataRangeTkr2(String dataRangeTkr2)
    {
        this.dataRangeTkr2 = dataRangeTkr2;
    }

    public String getDataRangeTkr2()
    {
        OperationBinding data = ADFBeanUtils.getOperationBinding("getTkrDataRange");
        data.getParamsMap().put("pos", new Integer(1));
        data.execute();
        String name = "No Data Range Available";
        if (data.getResult() != null)
        {
            name = data.getResult().toString();
        }
        return name;
        //return dataRangeTkr2;
    }

    public void setDataRangeTkr3(String dataRangeTkr3)
    {
        this.dataRangeTkr3 = dataRangeTkr3;
    }

    public String getDataRangeTkr3()
    {
        OperationBinding data = ADFBeanUtils.getOperationBinding("getTkrDataRange");
        data.getParamsMap().put("pos", new Integer(1));
        data.execute();
        String name = "No Data Range Available";
        if (data.getResult() != null)
        {
            name = data.getResult().toString();
        }
        return name;
        // return dataRangeTkr3;
    }
    private String dataRangeTkr3 = null;

    public void setMillion(Number million)
    {
        this.million = million;
    }

    public Number getMillion()
    {
        return million;
    }

    public void setThousand(Number thousand)
    {
        this.thousand = thousand;
    }

    public Number getThousand()
    {
        return thousand;
    }

    public void setZero(Number zero)
    {
        this.zero = zero;
    }

    public void setFirstTkrCurrOTNotation(String firstTkrCurrOTNotation)
    {
        this.firstTkrCurrOTNotation = firstTkrCurrOTNotation;
    }

    public String getFirstTkrCurrOTNotation()
    {
        return firstTkrCurrOTNotation;
    }

    public void setFirstTkrPrvsOTNotation(String firstTkrPrvsOTNotation)
    {
        this.firstTkrPrvsOTNotation = firstTkrPrvsOTNotation;
    }

    public String getFirstTkrPrvsOTNotation()
    {
        return firstTkrPrvsOTNotation;
    }

    public void setSecondTkrCurrentGrossSalPaidNotation(String secondTkrCurrentGrossSalPaidNotation)
    {
        this.secondTkrCurrentGrossSalPaidNotation = secondTkrCurrentGrossSalPaidNotation;
    }

    public String getSecondTkrCurrentGrossSalPaidNotation()
    {
        return secondTkrCurrentGrossSalPaidNotation;
    }

    public void setSecondTkrPreviousGrossSalPaidNotation(String secondTkrPreviousGrossSalPaidNotation)
    {
        this.secondTkrPreviousGrossSalPaidNotation = secondTkrPreviousGrossSalPaidNotation;
    }

    public String getSecondTkrPreviousGrossSalPaidNotation()
    {
        return secondTkrPreviousGrossSalPaidNotation;
    }

    public void setThirdTkrCurrAbsentNotation(String thirdTkrCurrAbsentNotation)
    {
        this.thirdTkrCurrAbsentNotation = thirdTkrCurrAbsentNotation;
    }

    public String getThirdTkrCurrAbsentNotation()
    {
        return thirdTkrCurrAbsentNotation;
    }

    public void setThirdTkrPrvsAbsentNotation(String thirdTkrPrvsAbsentNotation)
    {
        this.thirdTkrPrvsAbsentNotation = thirdTkrPrvsAbsentNotation;
    }

    public String getThirdTkrPrvsAbsentNotation()
    {
        return thirdTkrPrvsAbsentNotation;
    }

    private String thirdTkrPrvsAbsentNotation = "M";

    public Integer size = 0;
    public Integer grpSize = 0;
    public Integer empSize = 0;
    public Integer depSize = 0;
    public Integer locSize = 0;

    public void setGrpSize(Integer grpSize)
    {
        this.grpSize = grpSize;
    }

    public Integer getGrpSize()
    {
        return grpSize;
    }

    public void setEmpSize(Integer empSize)
    {
        this.empSize = empSize;
    }

    public Integer getEmpSize()
    {
        return empSize;
    }
    ArrayList<String> criteriaValuesAL = new ArrayList<String>();
    ArrayList<String> groupNmAL = new ArrayList<String>();
    ArrayList<String> employeeNMAL = new ArrayList<String>();
    ArrayList<String> criteraValuesIdAL = new ArrayList<String>();
    ArrayList<String> groupIdAL = new ArrayList<String>();
    ArrayList dateRangeAL = new ArrayList();
    ArrayList<String> departmentNMAL = new ArrayList<String>();
    ArrayList<String> departIdAL = new ArrayList<String>();
    ArrayList<String> locatiionNMAL = new ArrayList<String>();
    ArrayList<String> locIdAL = new ArrayList<String>();


    public void setCriteriaValuesAL(ArrayList<String> criteriaValuesAL)
    {
        this.criteriaValuesAL = criteriaValuesAL;
    }

    public ArrayList<String> getCriteriaValuesAL()
    {
        return criteriaValuesAL;
    }

    public void setCriteraValuesIdAL(ArrayList<String> criteraValuesIdAL)
    {
        this.criteraValuesIdAL = criteraValuesIdAL;
    }

    public void setGroupNmAL(ArrayList<String> groupNmAL)
    {
        this.groupNmAL = groupNmAL;
    }

    public ArrayList<String> getGroupNmAL()
    {
        return groupNmAL;
    }

    public void setEmployeeNMAL(ArrayList<String> employeeNMAL)
    {
        this.employeeNMAL = employeeNMAL;
    }

    public ArrayList<String> getEmployeeNMAL()
    {
        return employeeNMAL;
    }

    public void setGroupIdAL(ArrayList<String> groupIdAL)
    {
        this.groupIdAL = groupIdAL;
    }

    public ArrayList<String> getGroupIdAL()
    {
        return groupIdAL;
    }

    public void setEmpDocIdAL(ArrayList<String> empDocIdAL)
    {
        this.empDocIdAL = empDocIdAL;
    }

    public ArrayList<String> getEmpDocIdAL()
    {
        return empDocIdAL;
    }

    public void setGroupKeyValueMap(HashMap groupKeyValueMap)
    {
        this.groupKeyValueMap = groupKeyValueMap;
    }

    public HashMap getGroupKeyValueMap()
    {
        return groupKeyValueMap;
    }

    public void setEmployeeKeyValueMap(HashMap employeeKeyValueMap)
    {
        this.employeeKeyValueMap = employeeKeyValueMap;
    }

    public HashMap getEmployeeKeyValueMap()
    {
        return employeeKeyValueMap;
    }

    public ArrayList<String> getCriteraValuesIdAL()
    {
        return criteraValuesIdAL;
    }

    public void setDateRangeAL(ArrayList<String> dateRangeAL)
    {
        this.dateRangeAL = dateRangeAL;
    }

    public ArrayList<String> getDateRangeAL()
    {
        return dateRangeAL;
    }
    ArrayList<String> empDocIdAL = new ArrayList<String>();
    HashMap criteriaKeyValueMap = new HashMap();
    HashMap groupKeyValueMap = new HashMap();
    HashMap employeeKeyValueMap = new HashMap();
    HashMap departmentKeyValueMap = new HashMap();
    HashMap locationKeyValueMap = new HashMap();

    public void setSize(Integer size)
    {
        this.size = size;
    }

    public Integer getSize()
    {
        return size;
    }


    public void setCriteriaKeyValueMap(HashMap criteriaKeyValueMap)
    {
        this.criteriaKeyValueMap = criteriaKeyValueMap;
    }

    public HashMap getCriteriaKeyValueMap()
    {
        return criteriaKeyValueMap;
    }


    public HcmDashboardBean()
    {
    }

    public Number getSalaryProcForMyApprovalCount()
    {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("getDocCountForWorkFlow");
        binding.getParamsMap().put("DocTypeId", 29502);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.execute();
        if (binding.getResult() != null)
        {
            return (Number) binding.getResult();
        }
        else
        {
            return new Number(0);
        }
    }

    public Number getIncrementForMyApprovalCount()
    {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("getDocCountForWorkFlow");
        binding.getParamsMap().put("DocTypeId", 28511);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.execute();
        if (binding.getResult() != null)
        {
            return (Number) binding.getResult();
        }
        else
        {
            return new Number(0);
        }
    }

    public Number getfullNdFinalForMyApprovalCount()
    {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("getDocCountForWorkFlow");
        binding.getParamsMap().put("DocTypeId", 29504);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.execute();
        if (binding.getResult() != null)
        {
            return (Number) binding.getResult();
        }
        else
        {
            return new Number(0);
        }
    }

    public Number getEncahsmentLeaveForMyApprovalCount()
    {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("getDocCountForWorkFlow");
        binding.getParamsMap().put("DocTypeId", 29506);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.execute();
        if (binding.getResult() != null)
        {
            return (Number) binding.getResult();
        }
        else
        {
            return new Number(0);
        }
    }

    public Number getSalaryProcForOtherApprovalCount()
    {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("getDocCountForWorkFlow");
        binding.getParamsMap().put("DocTypeId", 29502);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.execute();
        if (binding.getResult() != null)
        {
            return (Number) binding.getResult();
        }
        else
        {
            return new Number(0);
        }
    }

    public Number getIncrementForOtherApprovalCount()
    {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("getDocCountForWorkFlow");
        binding.getParamsMap().put("DocTypeId", 28511);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.execute();
        if (binding.getResult() != null)
        {
            return (Number) binding.getResult();
        }
        else
        {
            return new Number(0);
        }
    }

    public Number getFullNdFinalForOtherApprovalCount()
    {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("getDocCountForWorkFlow");
        binding.getParamsMap().put("DocTypeId", 29504);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.execute();
        if (binding.getResult() != null)
        {
            return (Number) binding.getResult();
        }
        else
        {
            return new Number(0);
        }
    }

    public Number getEncahsmentLeaveForOtherApprovalCount()
    {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("getDocCountForWorkFlow");
        binding.getParamsMap().put("DocTypeId", 29506);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.execute();
        if (binding.getResult() != null)
        {
            return (Number) binding.getResult();
        }
        else
        {
            return new Number(0);
        }
    }

    public Number getUnpostedSalaryProcCount()
    {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("getDocCountForWorkFlow");
        binding.getParamsMap().put("DocTypeId", 29502);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.execute();
        if (binding.getResult() != null)
        {
            return (Number) binding.getResult();
        }
        else
        {
            return new Number(0);
        }
    }
    

    public Number getUnpostedIncrementCount()
    {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("getDocCountForWorkFlow");
        binding.getParamsMap().put("DocTypeId", 28511);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.execute();
        if (binding.getResult() != null)
        {
            return (Number) binding.getResult();
        }
        else
        {
            return new Number(0);
        }
    }

    public Number getUnpostedFullNdFinalCount()
    {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("getDocCountForWorkFlow");
        binding.getParamsMap().put("DocTypeId", 29504);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.execute();
        if (binding.getResult() != null)
        {
            return (Number) binding.getResult();
        }
        else
        {
            return new Number(0);
        }
    }

    public Number getUnpostedEncahsmentLeaveCount()
    {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("getDocCountForWorkFlow");
        binding.getParamsMap().put("DocTypeId", 29506);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.execute();
        if (binding.getResult() != null)
        {
            return (Number) binding.getResult();
        }
        else
        {
            return new Number(0);
        }
    }

    public String getTickerFirstLableName()
    {
        OperationBinding tkrName = ADFBeanUtils.getOperationBinding("getTkrLableName");
        tkrName.getParamsMap().put("tkrPos", 1);
        tkrName.execute();
        String name = "No Ticker Selected";
        if (tkrName.getResult() != null)
        {
            name = tkrName.getResult().toString();
        }
        return name;
    }

    public String getTickerSecondLableName()
    {
        OperationBinding tkrName = ADFBeanUtils.getOperationBinding("getTkrLableName");
        tkrName.getParamsMap().put("tkrPos", 2);
        tkrName.execute();
        String name = "No Ticker Selected";
        if (tkrName.getResult() != null)
        {
            name = tkrName.getResult().toString();
        }
        return name;
    }

    public String getTickerThirdLableName()
    {
        OperationBinding tkrName = ADFBeanUtils.getOperationBinding("getTkrLableName");
        tkrName.getParamsMap().put("tkrPos", 3);
        tkrName.execute();
        String name = "No Ticker Selected";
        if (tkrName.getResult() != null)
        {
            name = tkrName.getResult().toString();
        }

        return name;
    }

    public void criteriaVCL(ValueChangeEvent valueChangeEvent)
    {
        if (valueChangeEvent.getNewValue() != null)
        {
            criteriaValueIdBinding.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(criteriaValueIdBinding);
        }
    }


    public void addCriteraValues(ActionEvent actionEvent)
    {
        if (criteriaValueNameBinding.getValue() != null && getCriteriaValueIdBinding().getValue() != null)
        {
            String criteriaName = (String) criteriaValueNameBinding.getValue();
            String criteriaId = (String) criteriaValueIdBinding.getValue();
            System.out.println("criteriaId" + criteriaId);
            System.out.println("criteriaNAME" + criteriaName.toString());
            criteriaValuesAL.add(criteriaName);
            criteraValuesIdAL.add(criteriaId);
            criteriaKeyValueMap.put(criteriaId, criteriaName);
            size = criteriaValuesAL.size();
            this.criteriaValueNameBinding.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.criteriaValueNameBinding);

        }
    }

    public void addGrpOnPopAL(ActionEvent actionEvent)
    {
        if (grpNameBinding.getValue() != null)
        {
            String grpNm = (String) grpNameBinding.getValue();
            String grpId = (String) grpIdBinding.getValue();
            groupNmAL.add(grpNm);
            groupIdAL.add(grpId);
            groupKeyValueMap.put(grpId, grpNm);
            grpSize = groupNmAL.size();
            this.grpNameBinding.setValue("");
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.grpNameBinding);

        }
        else
        {
            FacesMessage msg = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2459"));
            //FacesMessage msg = new FacesMessage("Please select a group!");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(grpNameBinding.getClientId(), msg);
        }
    }

    public void addEmployeeOnPopAL(ActionEvent actionEvent)
    {
        if (employeeNmBinding.getValue() != null)
        {
            String empNm = (String) employeeNmBinding.getValue();
            String empDocid = (String) empDocIdBinding.getValue();
            employeeNMAL.add(empNm);
            empDocIdAL.add(empDocid);
            employeeKeyValueMap.put(empDocid, empNm);
            empSize = employeeNMAL.size();
            this.employeeNmBinding.setValue("");
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.employeeNmBinding);

        }
        else
        {
            FacesMessage msg = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1425"));
            //FacesMessage msg = new FacesMessage("Please select employee.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(employeeNmBinding.getClientId(), msg);
        }
    }

    public void addDepartmentPopAL(ActionEvent actionEvent)
    {
        if (empDeptNMBinding.getValue() != null)
        {

            String deptNM = (String) empDeptNMBinding.getValue();
            String empDeptId = (String) empDeptIdBinding.getValue();
            departmentNMAL.add(deptNM);
            departIdAL.add(empDeptId);
            departmentKeyValueMap.put(empDeptId, deptNM);
            depSize = departmentNMAL.size();
            this.empDeptNMBinding.setValue("");
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.empDeptNMBinding);

        }
        else
        {
            FacesMessage msg = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1564"));
            //FacesMessage msg = new FacesMessage("Please select department!");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(empDeptNMBinding.getClientId(), msg);
        }
    }

    public void addLocationPopAL(ActionEvent actionEvent)
    {
        if (empLocationNmBinding.getValue() != null)
        {
            String locNm = (String) empLocationNmBinding.getValue();
            String locId = (String) empLocationIdBinding.getValue();
            locatiionNMAL.add(locNm);
            locIdAL.add(locId);
            locationKeyValueMap.put(locId, locNm);
            locSize = locatiionNMAL.size();
            this.empLocationNmBinding.setValue("");
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.empLocationNmBinding);

        }
        else
        {
            FacesMessage msg = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2374"));
            //FacesMessage msg = new FacesMessage("Please select location!");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(empLocationNmBinding.getClientId(), msg);
        }
        System.out.println(locIdAL);
    }

    public void setDepSize(Integer depSize)
    {
        this.depSize = depSize;
    }

    public Integer getDepSize()
    {
        return depSize;
    }

    public void setLocSize(Integer locSize)
    {
        this.locSize = locSize;
    }

    public Integer getLocSize()
    {
        return locSize;
    }

    public void setDepartmentNMAL(ArrayList<String> departmentNMAL)
    {
        this.departmentNMAL = departmentNMAL;
    }

    public ArrayList<String> getDepartmentNMAL()
    {
        return departmentNMAL;
    }

    public void setDepartIdAL(ArrayList<String> departIdAL)
    {
        this.departIdAL = departIdAL;
    }

    public ArrayList<String> getDepartIdAL()
    {
        return departIdAL;
    }

    public void setLocatiionNMAL(ArrayList<String> locatiionNMAL)
    {
        this.locatiionNMAL = locatiionNMAL;
    }

    public ArrayList<String> getLocatiionNMAL()
    {
        return locatiionNMAL;
    }

    public void setLocIdAL(ArrayList<String> locIdAL)
    {
        this.locIdAL = locIdAL;
    }

    public ArrayList<String> getLocIdAL()
    {
        return locIdAL;
    }

    public void setDepartmentKeyValueMap(HashMap departmentKeyValueMap)
    {
        this.departmentKeyValueMap = departmentKeyValueMap;
    }

    public HashMap getDepartmentKeyValueMap()
    {
        return departmentKeyValueMap;
    }

    public void setLocationKeyValueMap(HashMap locationKeyValueMap)
    {
        this.locationKeyValueMap = locationKeyValueMap;
    }

    public HashMap getLocationKeyValueMap()
    {
        return locationKeyValueMap;
    }

    public void deleteCriteraValues(ActionEvent actionEvent)
    {
        RichCommandLink ob = (RichCommandLink) actionEvent.getSource();
        String CitariaValueNm = (String) ob.getAttributes().get("CitariaValueNm");

        for (int i = 0; i < criteriaKeyValueMap.size(); i++)
        {
            String CriteriaVaalueId = criteraValuesIdAL.get(i).toString();
            String newgrpNM = (String) criteriaKeyValueMap.get(CriteriaVaalueId);
            System.out.println(CriteriaVaalueId + "  a b  " + newgrpNM);
            if (newgrpNM.equals(CitariaValueNm))
            {
                this.criteraValuesIdAL.remove(CriteriaVaalueId);
                this.criteriaKeyValueMap.remove(CriteriaVaalueId);
                this.criteriaValuesAL.remove(CitariaValueNm);
                break;
            }
        }
        this.size = criteriaValuesAL.size();
    }

    public void removeGrpOnPopAL(ActionEvent actionEvent)
    {
        RichCommandLink ob = (RichCommandLink) actionEvent.getSource();
        String groupNm = (String) ob.getAttributes().get("grpNm");
        for (int i = 0; i < groupKeyValueMap.size(); i++)
        {
            String groupId = groupIdAL.get(i).toString();
            String newgrpNM = (String) groupKeyValueMap.get(groupId);
            if (newgrpNM.equals(groupNm))
            {
                this.groupIdAL.remove(groupId);
                this.groupKeyValueMap.remove(groupId);
                this.groupNmAL.remove(groupNm);
                break;
            }
        }
        this.grpSize = groupNmAL.size();
    }

    public void removeEmployeeOnPopAL(ActionEvent actionEvent)
    {
        RichCommandLink ob = (RichCommandLink) actionEvent.getSource();
        String empNM = (String) ob.getAttributes().get("empNm");
        for (int i = 0; i < employeeKeyValueMap.size(); i++)
        {
            String empdocId = getEmpDocIdAL().get(i).toString();
            String newEmpNm = (String) employeeKeyValueMap.get(empdocId);
            if (newEmpNm.equals(empNM))
            {
                this.getEmpDocIdAL().remove(empdocId);
                this.employeeKeyValueMap.remove(empdocId);
                this.employeeNMAL.remove(empNM);
                break;
            }
        }
        this.empSize = employeeNMAL.size();
    }

    public void removeDepartmentOnPopAL(ActionEvent actionEvent)
    {
        RichCommandLink ob = (RichCommandLink) actionEvent.getSource();
        String deptNM = (String) ob.getAttributes().get("deptNm");
        for (int i = 0; i < departmentKeyValueMap.size(); i++)
        {
            System.out.println(departIdAL);
            String deptId = getDepartIdAL().get(i).toString();
            String newdeptNm = (String) departmentKeyValueMap.get(deptId);
            if (newdeptNm.equals(deptNM))
            {
                this.getDepartIdAL().remove(deptId);
                this.departmentKeyValueMap.remove(deptId);
                this.departmentNMAL.remove(deptNM);
                break;
            }
        }
        this.depSize = departmentNMAL.size();
    }

    public void removeLocationOnPopAL(ActionEvent actionEvent)
    {
        RichCommandLink ob = (RichCommandLink) actionEvent.getSource();
        String locNM = (String) ob.getAttributes().get("locNm");
        System.out.println("Location =" + locNM);
        System.out.println(locIdAL);

        for (int i = 0; i < locationKeyValueMap.size(); i++)
        {

            String locId = getLocIdAL().get(i).toString();
            String newLocNm = (String) locationKeyValueMap.get(locId);
            if (newLocNm.equals(locNM))
            {
                this.getLocIdAL().remove(locId);
                this.locationKeyValueMap.remove(locId);
                this.locatiionNMAL.remove(locNM);
                break;
            }
        }
        this.locSize = locatiionNMAL.size();
    }

    public void clearAllCriteria(ActionEvent actionEvent)
    {

        criteriaValuesAL.clear();
        criteraValuesIdAL.clear();
        criteriaKeyValueMap.clear();
        this.size = criteriaValuesAL.size();

    }

    public void setCriteriaValueBinging(RichSelectOneChoice criteriaValueBinging)
    {
        this.criteriaValueBinging = criteriaValueBinging;
    }

    public RichSelectOneChoice getCriteriaValueBinging()
    {
        return criteriaValueBinging;
    }

    public void setCriteriaValueIdBinding(RichInputText criteriaValueIdBinding)
    {
        this.criteriaValueIdBinding = criteriaValueIdBinding;
    }

    public RichInputText getCriteriaValueIdBinding()
    {
        return criteriaValueIdBinding;
    }

    public void setCriteriaValueNameBinding(RichInputListOfValues criteriaValueNameBinding)
    {
        this.criteriaValueNameBinding = criteriaValueNameBinding;
    }

    public RichInputListOfValues getCriteriaValueNameBinding()
    {
        return criteriaValueNameBinding;
    }

    public void searchCriteria(ActionEvent actionEvent)
    {
        if (chkDate())
        {
            HashMap criteriaMap = new HashMap();
            criteriaMap.put("Employee", empDocIdAL);
            criteriaMap.put("Group", groupIdAL);
            criteriaMap.put("Department", departIdAL);
            criteriaMap.put("Location", locIdAL);
            criteriaMap.put("Daterange", dateRangeAL);
            OperationBinding searchCriteriaOb = ADFBeanUtils.getOperationBinding("searchCriteria");
            searchCriteriaOb.getParamsMap().put("criteria", criteriaMap);
            searchCriteriaOb.execute();
        }

    }


    public String showMessage(String message, String cid)
    {

        FacesMessage fm = new FacesMessage(message);
        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(cid, fm);
        return null;
    }

    public void setTotalCurrentOT(RichOutputText totalCurrentOT)
    {
        this.totalCurrentOT = totalCurrentOT;
    }

    public RichOutputText getTotalCurrentOT()
    {
        return totalCurrentOT;
    }

    public void setTotalPreviousOT(RichOutputText totalPreviousOT)
    {
        this.totalPreviousOT = totalPreviousOT;
    }

    public RichOutputText getTotalPreviousOT()
    {
        return totalPreviousOT;
    }

    public void setGrossCurrentSalaryPaid(RichOutputText grossCurrentSalaryPaid)
    {
        this.grossCurrentSalaryPaid = grossCurrentSalaryPaid;
    }

    public RichOutputText getGrossCurrentSalaryPaid()
    {
        return grossCurrentSalaryPaid;
    }

    public void setGrossPreviousSalaryPaid(RichOutputText grossPreviousSalaryPaid)
    {
        this.grossPreviousSalaryPaid = grossPreviousSalaryPaid;
    }

    public RichOutputText getGrossPreviousSalaryPaid()
    {
        return grossPreviousSalaryPaid;
    }

    public void setTotalCurrentAbsent(RichOutputText totalCurrentAbsent)
    {
        this.totalCurrentAbsent = totalCurrentAbsent;
    }

    public RichOutputText getTotalCurrentAbsent()
    {
        return totalCurrentAbsent;
    }

    public void setTotalPreviousAbsent(RichOutputText totalPreviousAbsent)
    {
        this.totalPreviousAbsent = totalPreviousAbsent;
    }

    public RichOutputText getTotalPreviousAbsent()
    {
        return totalPreviousAbsent;
    }

    public void empGroupSrchAL(ActionEvent actionEvent)
    {
        this.setPaneItm("grp");
    }

    public void empSrchAL(ActionEvent actionEvent)
    {
        this.setPaneItm("emp");
    }


    public void empLocSrchAL(ActionEvent actionEvent)
    {
        this.setPaneItm("loc");
    }

    public void empDeptSrchAL(ActionEvent actionEvent)
    {
        this.setPaneItm("dept");
    }


    public void setGrpNmOnPopBinding(RichInputListOfValues grpNmOnPopBinding)
    {
        this.grpNmOnPopBinding = grpNmOnPopBinding;
    }

    public RichInputListOfValues getGrpNmOnPopBinding()
    {
        return grpNmOnPopBinding;
    }

    public void setGrpIdBinding(RichOutputText grpIdBinding)
    {
        this.grpIdBinding = grpIdBinding;
    }

    public RichOutputText getGrpIdBinding()
    {
        return grpIdBinding;
    }


    public void setGrpName(RichInputListOfValues grpName)
    {
        this.grpName = grpName;
    }

    public RichInputListOfValues getGrpName()
    {
        return grpName;
    }

    public void setGrpNameBinding(RichInputListOfValues grpNameBinding)
    {
        this.grpNameBinding = grpNameBinding;
    }

    public RichInputListOfValues getGrpNameBinding()
    {
        return grpNameBinding;
    }

    public void setEmpSearchPopPupBinding(RichPopup empSearchPopPupBinding)
    {
        this.empSearchPopPupBinding = empSearchPopPupBinding;
    }

    public RichPopup getEmpSearchPopPupBinding()
    {
        return empSearchPopPupBinding;
    }

    public void setEmployeeNmBinding(RichInputListOfValues employeeNmBinding)
    {
        this.employeeNmBinding = employeeNmBinding;
    }

    public RichInputListOfValues getEmployeeNmBinding()
    {
        return employeeNmBinding;
    }

    public void setEmpDocIdBinding(RichInputText empDocIdBinding)
    {
        this.empDocIdBinding = empDocIdBinding;
    }

    public RichInputText getEmpDocIdBinding()
    {
        return empDocIdBinding;
    }

    public void clearAllGroupActionListner(ActionEvent actionEvent)
    {
        ClearAllGroupFilter();
    }

    public void clearAllEmployeeActionListner(ActionEvent actionEvent)
    {
        ClearAllEmployeeFilter();
    }

    public void clearAllDepartmentActionListner(ActionEvent actionEvent)
    {
        ClearAllDepartmentFilter();
    }

    public void clearAllLocationtActionListner(ActionEvent actionEvent)
    {
        ClearAllLocationFilter();
    }

    public void clearDateRangeActionListner(ActionEvent actionEvent)
    {
        fromDateBinding.setValue("");
        toDateBinding.setValue("");
    }

    public void ClearAllEmployeeFilter()
    {
        employeeNMAL.clear();
        empDocIdAL.clear();
        employeeKeyValueMap.clear();
        this.empSize = employeeNMAL.size();
    }

    public void ClearAllGroupFilter()
    {
        groupNmAL.clear();
        groupIdAL.clear();
        groupKeyValueMap.clear();
        this.grpSize = groupNmAL.size();
    }

    public void ClearAllDepartmentFilter()
    {
        departmentNMAL.clear();
        departIdAL.clear();
        departmentKeyValueMap.clear();
        this.depSize = departmentNMAL.size();
    }

    public void ClearAllLocationFilter()
    {
        locatiionNMAL.clear();
        locIdAL.clear();
        locationKeyValueMap.clear();
        this.locSize = locatiionNMAL.size();
    }


    public void clearAllFiltersActionListner(ActionEvent actionEvent)
    {
        ClearAllEmployeeFilter();
        ClearAllGroupFilter();
        ClearAllDepartmentFilter();
        ClearAllLocationFilter();

        fromDateBinding.setValue("");
        toDateBinding.setValue("");
    }


    public void setFromDateBinding(RichInputDate fromDateBinding)
    {
        this.fromDateBinding = fromDateBinding;
    }

    public RichInputDate getFromDateBinding()
    {
        return fromDateBinding;
    }

    public void setToDateBinding(RichInputDate toDateBinding)
    {
        this.toDateBinding = toDateBinding;
    }

    public RichInputDate getToDateBinding()
    {
        return toDateBinding;
    }


    public void getUpdate(ClientEvent clientEvent)
    {
        System.out.println("I am in alert bean");
        bean.getUpdate(this.getPanelForm(), EbizParams.GLBL_APP_CLD_ID(), EbizParams.GLBL_APP_SERV_LOC(),
                       EbizParams.GLBL_HO_ORG_ID(), EbizParams.GLBL_APP_USR_ORG(), EbizParams.GLBL_APP_USR(), "00504");
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelForm());

    }

    public void setEmpDeptIdBinding(RichInputText empDeptIdBinding)
    {
        this.empDeptIdBinding = empDeptIdBinding;
    }

    public RichInputText getEmpDeptIdBinding()
    {
        return empDeptIdBinding;
    }

    public void setEmpDeptNMBinding(RichInputListOfValues empDeptNMBinding)
    {
        this.empDeptNMBinding = empDeptNMBinding;
    }

    public RichInputListOfValues getEmpDeptNMBinding()
    {
        return empDeptNMBinding;
    }

    public void setEmpLocationIdBinding(RichInputText empLocationIdBinding)
    {
        this.empLocationIdBinding = empLocationIdBinding;
    }

    public RichInputText getEmpLocationIdBinding()
    {
        return empLocationIdBinding;
    }

    public void setEmpLocationNmBinding(RichInputListOfValues empLocationNmBinding)
    {
        this.empLocationNmBinding = empLocationNmBinding;
    }

    public RichInputListOfValues getEmpLocationNmBinding()
    {
        return empLocationNmBinding;
    }

    public void setPanelForm(RichPanelFormLayout panelForm)
    {
        this.panelForm = panelForm;
    }

    public RichPanelFormLayout getPanelForm()
    {
        return panelForm;
    }

    public void setPanelGroup(RichPanelGroupLayout panelGroup)
    {
        this.panelGroup = panelGroup;
    }

    public RichPanelGroupLayout getPanelGroup()
    {
        return panelGroup;
    }

    public void openBigGraph(ActionEvent actionEvent)
    {
        System.out.println("ID = " + actionEvent.getComponent().getId());
        String id = actionEvent.getComponent().getId();
        setGraphId(id);
        showPopup(showBigGraphPopupBinding, true);
    }

    private void showPopup(RichPopup pop, boolean visible)
    {
        try
        {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && pop != null)
            {
                String popupId = pop.getClientId(context);
                if (popupId != null)
                {
                    StringBuilder script = new StringBuilder();
                    script.append("var popup = AdfPage.PAGE.findComponent('").append(popupId).append("'); ");
                    if (visible)
                    {
                        script.append("if (!popup.isPopupVisible()) { ").append("popup.show();}");
                    }
                    else
                    {
                        script.append("if (popup.isPopupVisible()) { ").append("popup.hide();}");
                    }
                    ExtendedRenderKitService erks =
                        Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);
                    erks.addScript(context, script.toString());
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setShowBigGraphPopupBinding(RichPopup showBigGraphPopupBinding)
    {
        this.showBigGraphPopupBinding = showBigGraphPopupBinding;
    }

    public RichPopup getShowBigGraphPopupBinding()
    {
        return showBigGraphPopupBinding;
    }

    public void MaxLeaveValueVCL(ValueChangeEvent valueChangeEvent)
    {
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue() != "")
        {
            if (chkDate())
            {
                BigDecimal d = new BigDecimal(valueChangeEvent.getNewValue().toString());
                Integer perValue = d.intValue();
                HashMap criteriaMap = new HashMap();
                criteriaMap.put("Group", groupIdAL);
                criteriaMap.put("Employee", empDocIdAL);
                criteriaMap.put("Department", departIdAL);
                criteriaMap.put("Location", locIdAL);
                criteriaMap.put("Daterange", dateRangeAL);

                OperationBinding setPerCriteria = ADFBeanUtils.getOperationBinding("setMaxLeaveValueInCriteria");
                setPerCriteria.getParamsMap().put("criteria", criteriaMap);
                setPerCriteria.getParamsMap().put("perValue", perValue);
                setPerCriteria.execute();

            }

        }
    }

    public void MaxOverTimePaidValueVCL(ValueChangeEvent valueChangeEvent)
    {
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue() != "")
        {
            if (chkDate())
            {
                BigDecimal d = new BigDecimal(valueChangeEvent.getNewValue().toString());
                Integer perValue = d.intValue();
                HashMap criteriaMap = new HashMap();
                criteriaMap.put("Group", groupIdAL);
                criteriaMap.put("Employee", empDocIdAL);
                criteriaMap.put("Department", departIdAL);
                criteriaMap.put("Location", locIdAL);
                criteriaMap.put("Daterange", dateRangeAL);

                OperationBinding setPerCriteria = ADFBeanUtils.getOperationBinding("setOverTimePaidValueInCriteria");
                setPerCriteria.getParamsMap().put("criteria", criteriaMap);
                setPerCriteria.getParamsMap().put("perValue", perValue);
                setPerCriteria.execute();

            }

        }
    }

    public void currentVsSupposedSalPerIncVCL(ValueChangeEvent valueChangeEvent)
    {
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue() != "")
        {
            BigDecimal d = new BigDecimal(valueChangeEvent.getNewValue().toString());
            Integer perValue = d.intValue();
            HashMap criteriaMap = new HashMap();
            criteriaMap.put("Group", groupIdAL);
            criteriaMap.put("Employee", empDocIdAL);
            criteriaMap.put("Department", departIdAL);
            criteriaMap.put("Location", locIdAL);
            criteriaMap.put("Daterange", dateRangeAL);

            OperationBinding setPerCriteria = ADFBeanUtils.getOperationBinding("setMaxSpposedSalaryInCriteria");
            setPerCriteria.getParamsMap().put("criteria", criteriaMap);
            setPerCriteria.getParamsMap().put("perValue", perValue);
            setPerCriteria.execute();
        }

    }


    public void MaxTurnOverRateValueVCL(ValueChangeEvent valueChangeEvent)
    {
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue() != "")
        {
            if (chkDate())
            {
                BigDecimal d = new BigDecimal(valueChangeEvent.getNewValue().toString());
                Integer perValue = d.intValue();
                HashMap criteriaMap = new HashMap();
                criteriaMap.put("Group", groupIdAL);
                criteriaMap.put("Employee", empDocIdAL);
                criteriaMap.put("Department", departIdAL);
                criteriaMap.put("Location", locIdAL);
                criteriaMap.put("Daterange", dateRangeAL);

                OperationBinding setPerCriteria = ADFBeanUtils.getOperationBinding("setMaxTurnOverValueInCriteria");
                setPerCriteria.getParamsMap().put("criteria", criteriaMap);
                setPerCriteria.getParamsMap().put("perValue", perValue);
                setPerCriteria.execute();

            }

        }
    }

    public void MaxLwpTakenVCL(ValueChangeEvent valueChangeEvent)
    {
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue() != "")
        {

            if (chkDate())
            {
                BigDecimal d = new BigDecimal(valueChangeEvent.getNewValue().toString());
                Integer perValue = d.intValue();
                HashMap criteriaMap = new HashMap();
                criteriaMap.put("Group", groupIdAL);
                criteriaMap.put("Employee", empDocIdAL);
                criteriaMap.put("Department", departIdAL);
                criteriaMap.put("Location", locIdAL);
                criteriaMap.put("Daterange", dateRangeAL);

                OperationBinding setPerCriteria = ADFBeanUtils.getOperationBinding("setMaxLwpInCriteria");
                setPerCriteria.getParamsMap().put("criteria", criteriaMap);
                setPerCriteria.getParamsMap().put("perValue", perValue);
                setPerCriteria.execute();

            }

        }
    }

    public void MaxWorkExpTakenVCL(ValueChangeEvent valueChangeEvent)
    {
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue() != "")
        {

            if (chkDate())
            {
                BigDecimal d = new BigDecimal(valueChangeEvent.getNewValue().toString());
                Integer perValue = d.intValue();
                HashMap criteriaMap = new HashMap();
                criteriaMap.put("Group", groupIdAL);
                criteriaMap.put("Employee", empDocIdAL);
                criteriaMap.put("Department", departIdAL);
                criteriaMap.put("Location", locIdAL);
                criteriaMap.put("Daterange", dateRangeAL);

                OperationBinding setPerCriteria = ADFBeanUtils.getOperationBinding("setMaxWorkExpInCriteria");
                setPerCriteria.getParamsMap().put("criteria", criteriaMap);
                setPerCriteria.getParamsMap().put("perValue", perValue);
                setPerCriteria.execute();

            }

        }
    }


    public void serchAlrt(ActionEvent actionEvent)
    {
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("search").execute();
    }

    public void resetAlrt(ActionEvent actionEvent)
    {
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("reset").execute();
    }

    public boolean chkDate()
    {
        boolean chkDtRnge = true;
        if (fromDateBinding.getValue() == null || fromDateBinding.getValue() == "" ||
            toDateBinding.getValue() == null || toDateBinding.getValue() == "")
        {

            chkDtRnge = false;
        }
        else
        {
            dateRangeAL.add(0, fromDateBinding.getValue());
            dateRangeAL.add(1, toDateBinding.getValue());
            System.out.println("date values in arrayList " + dateRangeAL.get(0) + "and" + dateRangeAL.get(1));
        }
        return chkDtRnge;
    }

    public void overTimePaidDiscloserListner(DisclosureEvent disclosureEvent)
    {

        if (chkDate())
        {
            HashMap criteriaMap = new HashMap();
            criteriaMap.put("Employee", empDocIdAL);
            criteriaMap.put("Group", groupIdAL);
            criteriaMap.put("Department", departIdAL);
            criteriaMap.put("Location", locIdAL);
            criteriaMap.put("Daterange", dateRangeAL);
            OperationBinding searchCriteriaOb = ADFBeanUtils.getOperationBinding("loadOverTimePaidGraph");
            searchCriteriaOb.getParamsMap().put("criteria", criteriaMap);
            searchCriteriaOb.execute();
        }
    }

    public void turnOverRateDiscloserListner(DisclosureEvent disclosureEvent)
    {
        if (chkDate())
        {

            HashMap criteriaMap = new HashMap();
            criteriaMap.put("Employee", empDocIdAL);
            criteriaMap.put("Group", groupIdAL);
            criteriaMap.put("Department", departIdAL);
            criteriaMap.put("Location", locIdAL);
            criteriaMap.put("Daterange", dateRangeAL);
            OperationBinding searchCriteriaOb = ADFBeanUtils.getOperationBinding("loadTurnOverGraph");
            searchCriteriaOb.getParamsMap().put("criteria", criteriaMap);
            searchCriteriaOb.execute();
        }
    }

    public void maxLeaveTakenDiscloserListner(DisclosureEvent disclosureEvent)
    {
        if (chkDate())
        {

            HashMap criteriaMap = new HashMap();
            criteriaMap.put("Employee", empDocIdAL);
            criteriaMap.put("Group", groupIdAL);
            criteriaMap.put("Department", departIdAL);
            criteriaMap.put("Location", locIdAL);
            criteriaMap.put("Daterange", dateRangeAL);
            OperationBinding searchCriteriaOb = ADFBeanUtils.getOperationBinding("loadMaxLeaveTakenGraph");
            searchCriteriaOb.getParamsMap().put("criteria", criteriaMap);
            searchCriteriaOb.execute();
        }
    }

    public void currentVsSupposedDiscloserListner(DisclosureEvent disclosureEvent)
    {
        if (chkDate())
        {

            HashMap criteriaMap = new HashMap();
            criteriaMap.put("Employee", empDocIdAL);
            criteriaMap.put("Group", groupIdAL);
            criteriaMap.put("Department", departIdAL);
            criteriaMap.put("Location", locIdAL);
            criteriaMap.put("Daterange", dateRangeAL);
            OperationBinding searchCriteriaOb = ADFBeanUtils.getOperationBinding("loadCurrentVsSupposedGraph");
            searchCriteriaOb.getParamsMap().put("criteria", criteriaMap);
            searchCriteriaOb.execute();
        }
    }

    public void lwpTakenDiscloserListner(DisclosureEvent disclosureEvent)
    {
        if (chkDate())
        {
            HashMap criteriaMap = new HashMap();
            criteriaMap.put("Employee", empDocIdAL);
            criteriaMap.put("Group", groupIdAL);
            criteriaMap.put("Department", departIdAL);
            criteriaMap.put("Location", locIdAL);
            criteriaMap.put("Daterange", dateRangeAL);
            OperationBinding searchCriteriaOb = ADFBeanUtils.getOperationBinding("loadLwpTakenGraph");
            searchCriteriaOb.getParamsMap().put("criteria", criteriaMap);
            searchCriteriaOb.execute();
        }
    }


    public void workExperianceDiscloserListner(DisclosureEvent disclosureEvent)
    {
        if (chkDate())
        {
            HashMap criteriaMap = new HashMap();
            criteriaMap.put("Employee", empDocIdAL);
            criteriaMap.put("Group", groupIdAL);
            criteriaMap.put("Department", departIdAL);
            criteriaMap.put("Location", locIdAL);
            criteriaMap.put("Daterange", dateRangeAL);
            OperationBinding searchCriteriaOb = ADFBeanUtils.getOperationBinding("loadWorkExpGraph");
            searchCriteriaOb.getParamsMap().put("criteria", criteriaMap);
            searchCriteriaOb.execute();
        }
    }


    public void fromDateVCL(ValueChangeEvent valueChangeEvent)
    {
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue() != "")

        {
            dateRangeAL.add(0, valueChangeEvent.getNewValue());
            dateRangeAL.add(1, toDateBinding.getValue());
        }

    }

    public void toDateVCL(ValueChangeEvent valueChangeEvent)
    {
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue() != "")

        {
            dateRangeAL.add(0, fromDateBinding.getValue());
            dateRangeAL.add(1, valueChangeEvent.getNewValue());
        }
    }

    public void filterGrphForNMStrtsWithActionListner(ActionEvent actionEvent)
    {
        OperationBinding searchCriteriaOb = ADFBeanUtils.getOperationBinding("filterGraphForNameStartsWith");
        searchCriteriaOb.getParamsMap().put("graphTypeId", getGraphId());
        searchCriteriaOb.getParamsMap().put("startsWith", nameStartsWithBinding.getValue());
        searchCriteriaOb.execute();

    }

    public void setNameStartsWithBinding(RichInputText nameStartsWithBinding)
    {
        this.nameStartsWithBinding = nameStartsWithBinding;
    }

    public RichInputText getNameStartsWithBinding()
    {
        return nameStartsWithBinding;
    }

    public void filterGraphWithNameInitialVC(ValueChangeEvent valueChangeEvent)
    {
        OperationBinding searchCriteriaOb = ADFBeanUtils.getOperationBinding("filterGraphForNameStartsWith");
        searchCriteriaOb.getParamsMap().put("graphTypeId", getGraphId());
        searchCriteriaOb.getParamsMap().put("startsWith", nameStartsWithBinding.getValue());
        searchCriteriaOb.execute();
    }


    public void chkOvrWhichPageUserWillMoveActionListner(ActionEvent actionEvent)
    {
        System.out.println("Link Id Is  = " + actionEvent.getComponent().getId());
        String id = actionEvent.getComponent().getId();
        if (id != null)
        {
            StringBuffer descValuse = new StringBuffer();
            String caseResult = chkCasesForSalProc(id, descValuse);
            if (caseResult.equals("No"))
            {
                caseResult = chkCasesForSalIncr(id, descValuse);
            }
            if (caseResult.equals("No"))
            {
                caseResult = chkCasesForEncashLeave(id, descValuse);
            }
            if (caseResult.equals("No"))
            {
                caseResult = chkCasesForFullAndFinal(id, descValuse);
            }
            FacesContext fctx = FacesContext.getCurrentInstance();
            String refreshpage = fctx.getViewRoot().getViewId();
            ViewHandler ViewH = fctx.getApplication().getViewHandler();
            UIViewRoot UIV = ViewH.createView(fctx, refreshpage);
            UIV.setViewId(refreshpage);
            fctx.setViewRoot(UIV);
        }

    }
    
    
    public void chkOvrWhchPageUserWillMoveInAlertActionListner(ActionEvent actionEvent)
    {
        System.out.println("Link Id Is  = " + actionEvent.getComponent().getId());
        String id = actionEvent.getComponent().getId();
        if (id != null)
        {
            StringBuffer descValuse = new StringBuffer();
            String caseResult = chkCasesAlerts(id, descValuse);
            FacesContext fctx = FacesContext.getCurrentInstance();
            String refreshpage = fctx.getViewRoot().getViewId();
            ViewHandler ViewH = fctx.getApplication().getViewHandler();
            UIViewRoot UIV = ViewH.createView(fctx, refreshpage);
            UIV.setViewId(refreshpage);
            fctx.setViewRoot(UIV);
        }

    }

    public String chkCasesForSalProc(String id, StringBuffer descValue)
    {
        //---all three cases for salary processing---//salProcMyPending//salProcUnposetd//salProcOthersPending//
        String ViewType = null;
        String iDMatchFound = "No";
        if (id.equals("salProcMyPending"))
        {
            
            descValue.append("Salary Processing Documents For My Approval");
            ViewType = "M";
            iDMatchFound = "Yes";
        }
        else if (id.equals("salProcOthersPending"))
        {
            descValue.append(ADFModelUtils.resolvRsrc("MSG.1075"));
            //descValue.append("Salary Processing Documents For Others Approval");
            ViewType = "O";
            iDMatchFound = "Yes";
        }
        else if (id.equals("salProcUnposetd"))
        {
            descValue.append(ADFModelUtils.resolvRsrc("MSG.2460"));
            //descValue.append("Salary Processing Unposted Documents");
            ViewType = "U";
            iDMatchFound = "Yes";
        }
        if (iDMatchFound.equals("Yes"))
        {
            OperationBinding data = ADFBeanUtils.getOperationBinding("filterAllWFViewPageVO");
            data.getParamsMap().put("type",ViewType);
            data.getParamsMap().put("vOType","SalProc");
            data.execute();
            setDesc(descValue);
            setPageName("goToWFSalProcViewPage");
        }
        return iDMatchFound;
    }

    public String chkCasesForSalIncr(String id, StringBuffer descValue)
    {

        //---all three cases for salary Increment---//salIncrMyPending//salIncrUnposetd//salIncrOthersPending//
        String ViewType = null;
        String iDMatchFound = "No";
        if (id.equals("salIncrMyPending"))
        {
            descValue.append("Salary Increment Documents For My Approval");
            ViewType = "M";
            iDMatchFound = "Yes";
        }
        else if (id.equals("salIncrOthersPending"))
        {
            descValue.append("Salary Increment Documents For Others Approval");
            ViewType = "O";
            iDMatchFound = "Yes";
        }

        else if (id.equals("salIncrUnposetd"))
        {
            descValue.append("Salary Increment Unposted Documents");
            ViewType = "U";
            iDMatchFound = "Yes";
        }
        if (iDMatchFound.equals("Yes"))
        {
            OperationBinding data = ADFBeanUtils.getOperationBinding("filterAllWFViewPageVO");
            data.getParamsMap().put("type",ViewType);
            data.getParamsMap().put("vOType","SalIncr");
            data.execute();
            setDesc(descValue);
            setPageName("goToWFSalIncrViewPage");

        }
        return iDMatchFound;
    }

    public String chkCasesForEncashLeave(String id, StringBuffer descValue)
    {
        //---all three cases for encashleaves------//encashLeaveMyPending//encashLeaveUnposetd//encashLeaveOthersPending//
        String ViewType = null;
        String iDMatchFound = "No";
        if (id.equals("encashLeaveMyPending"))
        {
            descValue.append("Encash Leave Documents For My Approval");
            ViewType = "M";
            iDMatchFound = "Yes";
        }
        else if (id.equals("encashLeaveOthersPending"))
        {
            descValue.append("Encash Leave Documents For Others Approval");
            ViewType = "O";
            iDMatchFound = "Yes";
        }

        else if (id.equals("encashLeaveUnposetd"))
        {
            descValue.append("Encash Leave Unposted Documents");
            ViewType = "U";
            iDMatchFound = "Yes";
        }
        if (iDMatchFound.equals("Yes"))
        {
            OperationBinding data = ADFBeanUtils.getOperationBinding("filterAllWFViewPageVO");
            data.getParamsMap().put("type",ViewType);
            data.getParamsMap().put("vOType","EncashLeave");
            data.execute();
            setDesc(descValue);
            setPageName("goToWFEncasheLeaveViewPage");
        }
        return iDMatchFound;
    }

    public String chkCasesForFullAndFinal(String id, StringBuffer descValue)
    {
        //---all three cases for full and final---//fullAndFinalMyPending//fullAndFinalUnposetd//fullAndFinalOthersPending//
        String ViewType = null;
        String iDMatchFound = "No";
        if (id.equals("fullAndFinalMyPending"))
        {
            ViewType = "M";
            descValue.append("Full And Final  Documents For My Approval");
            iDMatchFound = "Yes";
        }
        else if (id.equals("fullAndFinalOthersPending"))
        {
            ViewType = "O";
            descValue.append("Full And Final Documents For Others Approval");
            iDMatchFound = "Yes";
        }

        else if (id.equals("fullAndFinalUnposetd"))
        {
            ViewType = "U";
            descValue.append("Full And Final Unposted Documents");
            iDMatchFound = "Yes";
        }
        if (iDMatchFound.equals("Yes"))
        {
            OperationBinding data = ADFBeanUtils.getOperationBinding("filterAllWFViewPageVO");
            data.getParamsMap().put("type",ViewType);
            data.getParamsMap().put("vOType","FullAndFinal");
            data.execute();
            setDesc(descValue);
            setPageName("goToWFFullAndFinalViewPage");
        }
        return iDMatchFound;
    }
    
    public String chkCasesAlerts(String id, StringBuffer descValue)
    {
        //---cases for increment not process after one year
        String ViewType = null;
        String iDMatchFound = "No";
        if (id.equals("incrementNotProcessAlert"))
        {
            ViewType = "IncrmntAlert";
            descValue.append("Due Increment Alert");
            iDMatchFound = "Yes";
        }
        if (iDMatchFound.equals("Yes"))
        {
            OperationBinding data = ADFBeanUtils.getOperationBinding("filterAlertsIncrNotProcessPageVO");
            data.getParamsMap().put("vOType","IncrmntNotProcess");
            data.execute();
            setDesc(descValue);
            setPageName("goToIncrmntNotProcessEmpViewPage");
        }
        return iDMatchFound;
    }

    public Object goToWFViewPageAction()
    {
        System.out.println("over which page use will enter "+getPageName());
        return getPageName();
    }
    
    public String viewIncrementAction()
    {
        String doc_id = null;
        if(salIncrDocIdBinding.getValue()!=null)
        {
            doc_id = salIncrDocIdBinding.getValue().toString();
            RequestContext context = RequestContext.getCurrentInstance();
            context.getPageFlowScope().put("GLBL_CHK_MODE", "E");
            context.getPageFlowScope().put("ADD_EDIT_MODE", "D");
            context.getPageFlowScope().put("GLBL_GET_DOC_ID", doc_id);
            System.out.println("values are="+resolvEl("#{pageFlowScope.GLBL_GET_DOC_ID}"));
            return "viewIncrementStatus";
        }
        return null;
    }
    
    public String viewSalaryProcAction()
    {
        String doc_id = null;
        if(salProcTxnDocIdBinding.getValue()!=null)
        {
            doc_id = salProcTxnDocIdBinding.getValue().toString();
            RequestContext.getCurrentInstance().getPageFlowScope().put("PARAM_DOC_TXN_ID", doc_id);
            System.out.println("values set in sal proc doc id ="+resolvEl("#{pageFlowScope.PARAM_DOC_TXN_ID}"));
            return "viewSalaryProcStatus";
        }
        return null;
    }
    
    public String viewSFullAndFinalAction()
    {
        String doc_id = null;
        if(fulllAndFinalDocIdBinding.getValue()!=null)
        {
            doc_id = fulllAndFinalDocIdBinding.getValue().toString();
            RequestContext.getCurrentInstance().getPageFlowScope().put("PARAM_DOC_TXN_ID", doc_id);
            System.out.println("values set In full and final DocId ="+resolvEl("#{pageFlowScope.PARAM_DOC_TXN_ID}"));
            return "viewFullAndFinalStatus";
        }
        return null;
    }
    
    public String viewEncashLeavelAction()
    { 
        String doc_id = null;
        if(encashDocIdBinding.getValue()!=null)
        {
            doc_id = encashDocIdBinding.getValue().toString();
            RequestContext.getCurrentInstance().getPageFlowScope().put("PARAM_DOC_TXN_ID", doc_id);
            System.out.println("values set In Encash DocId="+resolvEl("#{pageFlowScope.PARAM_DOC_TXN_ID}"));
            return "viewEncashLeaveStatus";
        }
       return null;
    }
    
    
    public String resolvEl(String data)
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String msg = valueExp.getValue(elContext).toString();
        return msg;
    }


    public void setEncashDocIdBinding(RichOutputText encashDocIdBinding)
    {
        this.encashDocIdBinding = encashDocIdBinding;
    }

    public RichOutputText getEncashDocIdBinding()
    {
        return encashDocIdBinding;
    }

    public void setSalProcTxnDocIdBinding(RichOutputText salProcTxnDocIdBinding)
    {
        this.salProcTxnDocIdBinding = salProcTxnDocIdBinding;
    }

    public RichOutputText getSalProcTxnDocIdBinding()
    {
        return salProcTxnDocIdBinding;
    }

    public void setSalIncrDocIdBinding(RichOutputText salIncrDocIdBinding)
    {
        this.salIncrDocIdBinding = salIncrDocIdBinding;
    }

    public RichOutputText getSalIncrDocIdBinding()
    {
        return salIncrDocIdBinding;
    }

    public void setFulllAndFinalDocIdBinding(RichOutputText fulllAndFinalDocIdBinding)
    {
        this.fulllAndFinalDocIdBinding = fulllAndFinalDocIdBinding;
    }

    public RichOutputText getFulllAndFinalDocIdBinding()
    {
        return fulllAndFinalDocIdBinding;
    }
    
}
