package slsreportapp.view.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.List;

import java.util.ListIterator;

import java.util.Map;

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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import oracle.adf.model.BindingContext;
//import oracle.adf.model.OperationBinding;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.data.RichColumn;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.nav.RichGoLink;

import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.QueryEvent;
import oracle.adf.view.rich.model.FilterableQueryDescriptor;
import oracle.adf.view.rich.util.FacesMessageUtils;

import oracle.adfinternal.view.faces.model.binding.FacesCtrlHierNodeBinding;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.dss.graph.Visible;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;

import slsreportapp.model.views.SlsRptMdlVORowImpl;


public class SlsReportBean {
    private RichInputText mdlIdBVar;
    private RichInputText searchMdlIdBVar;
    private String queryVar = "";
    private String mode = "V";
    private String linkMode = "D";
    private String fileType="";
    private RichGoLink golink;
    private int rowcount=0;
   
    private RichSelectOneRadio pageTypeRadioBind; //v view, E edit, A add
    private String pageType = "p";
    private RichSelectOneRadio fileTypeRadioBind;
    private RichInputText colDescPgBind;
    private RichSelectBooleanCheckbox visiblePgCBBind;
    private RichSelectBooleanCheckbox groupByCBBind;
    private RichSelectOneRadio createTypeRadioBind;
    private RichSelectOneChoice schemaNmLOVBind;
    private RichSelectOneChoice viewNmLOVBind;
    private RichInputText colWidthTableBind;
    private RichOutputText mdlDescOutBind;
    private RichTable tabBind;
    private RichColumn mdldescColumnBind;
    private RichInputText aliasTBBind;
    private RichInputText orderNoTBBind;
    private RichSelectOneChoice tempLOVBind;
    private RichSelectBooleanCheckbox crossCBBind;

    public SlsReportBean() {
       
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    public Object resolvEl(String data) {
        ADFContext adfCtx = ADFContext.getCurrent();
                 ELContext elContext = adfCtx.getELContext();
                 ValueExpression valueExp = 
        adfCtx.getExpressionFactory().createValueExpression(elContext, data, 
        Object.class);
                 Object Message = valueExp.getValue(elContext).toString();
                 return Message.toString();
    }
    public void showFacesMessage(String mesg, String sev, Boolean chk, String typFlg, String clientId) {

        FacesMessage message = new FacesMessage(mesg);
        if (chk == true) {
            String msg = resolvEl("#{bundle['" + mesg + "']}").toString();
            message = new FacesMessage(msg);
        }
        if (sev.equalsIgnoreCase("E")) {
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
        } else if (sev.equalsIgnoreCase("W")) {
            message.setSeverity(FacesMessage.SEVERITY_WARN);
        } else if (sev.equalsIgnoreCase("I")) {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
        } else {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
        }
        if (typFlg.equals("F")) {
            FacesContext.getCurrentInstance().addMessage(clientId, message);
        } else if (typFlg.equals("V")) {
            throw new ValidatorException(message);
        }
    }


    public void populateListener(ActionEvent actionEvent) {
        //Integer mdlId = (Integer)mdlIdBVar.getValue();
        //System.out.println("MdlIs in beam " + mdlId);
        OperationBinding opr = (OperationBinding)getBindings().getOperationBinding("populate");
        //opr.getParamsMap().put("modId", mdlId);
        opr.execute();
    }

    public void setMdlIdBVar(RichInputText mdlIdBVar) {
        this.mdlIdBVar = mdlIdBVar;
    }

    public RichInputText getMdlIdBVar() {
        return mdlIdBVar;
    }

    List<String> grppNm = new ArrayList<String>();
    ArrayList<String> ClmNmArr = new ArrayList<String>();
    ArrayList<String> clmDesArrc = new ArrayList<String>();
    List<String> sumColumnName = new ArrayList<String>();
    //Map<Integer,Integer> width = new HashMap<Integer,Integer>();
    List<Integer> width = new ArrayList<Integer>();
    List<String> colSumNamewithoutGrp = new ArrayList<String>();

    public void validateForm(String call){
        OperationBinding binding = (OperationBinding)getBindings().getOperationBinding("validateForm");
        binding.execute();
        Integer flag = -1;
        if (binding.getResult() != null) {
            flag = Integer.parseInt(binding.getResult().toString());
        }
        if (flag == 0) {
            OperationBinding opb = (OperationBinding)getBindings().getOperationBinding("validateDuplicateValue");
            opb.execute();
            int check = -1;

            if (opb.getResult() != null) {
                String msg = "";
                check = Integer.parseInt(opb.getResult().toString());

                if (check == 1) {
                    msg = "Visible Column order can not be duplicate";
                } else if (check == 2) {
                    msg = "Visible Column Group number can not be duplicate";
                } else if (check != -1) {
                    setMode("V");
                    setLinkMode("D");
                    OperationBinding saveBind = (OperationBinding)getBindings().getOperationBinding("Commit");
                    saveBind.execute();
                    msg = "Record Saved Successfully.";
                }
                if(!msg.equals("Record Saved Successfully.") && !call.equals("gen")){
                FacesMessage message = new FacesMessage(msg);
                FacesContext context = FacesContext.getCurrentInstance();
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                context.addMessage(null, message);
                }
            }

        } else {
            String msg = "";


            /* if (flag == 1) {
                msg = "Please Select the Order no. corresponding to the visible column";
            } else if (flag == 2) {
                msg = "Please Select the Width corresponding to the visible column";
            } else if (flag == 3) {
                msg = "Please Select the group no. corresponding to the group column";
            } else if (flag == 4) {
                msg = "Column Description can not be empty corresponding to the visible column";
            } else if (flag == 5) {
                msg = "Can not take sum of Group Column!!";
            } else if (flag == 6) {
                msg = "please select the visible check box corresponding to the group check box!!";
            } else if (flag == 7) {
                msg = "Please select the visible check box corresponding to the Sum Check Box!!";
            } else if (flag == 8) {
                msg = "you can not select sum of the first column expect group!! ";
            } else if (flag == 9) {
                msg = "Please select visible check box for atleast one column";
            } else {
                msg = "There is something wrong with the application.";
            } */
            
            switch (flag) {
            case 1:
                msg = "Please Select the Order no. corresponding to the visible column";
                break;
            case 2:
                msg = "Please Select the Width corresponding to the visible column";
                break;
            case 3:
                msg = "Please Select the group no. corresponding to the group column";
                break;
            case 4:
                msg = "Column Description can not be empty corresponding to the visible column";
                break;
            case 5:
                msg = "Can not take sum of Group Column!!";
                break;
            case 6:
                msg = "please select the visible check box corresponding to the group check box!!";
                break;
            case 7:
                msg = "Please select the visible check box corresponding to the Sum Check Box!!";
                break;
            case 8:
                msg = "you can not select sum of the first column expect group!! ";
                break;
            case 9:
                msg = "Please select visible check box for atleast one column";
                break;
            default:
                msg = "There is something wrong with the application.";
            }
          if(flag!=3){
            FacesMessage message = new FacesMessage(msg);
            FacesContext context = FacesContext.getCurrentInstance();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(null, message);
            }
        } 
    }

    public void saveAction(ActionEvent actionEvent) {
        OperationBinding modbind = (OperationBinding)getBindings().getOperationBinding("getModule");
        modbind.execute();
        String alias=(modbind.getResult().toString() + aliasTBBind.getValue());
        aliasTBBind.setValue(alias);
        String call="save";
        validateForm(call);
    }

    public void setRowcount(int rowcount) {
        this.rowcount = rowcount;
    }

    public int getRowcount() {
        return rowcount;
    }

    public void searchTemplateActionListener(ActionEvent actionEvent) {
        Integer mdlId = (Integer)searchMdlIdBVar.getValue();
        System.out.println("MdlIs in searchTemplateActionListener " + mdlId);
        OperationBinding opr = (OperationBinding)getBindings().getOperationBinding("search");
        opr.getParamsMap().put("modId", mdlId);
        opr.execute();
        //OperationBinding appNm = (OperationBinding)getBindings().getOperationBinding("setAppName");
        //appNm.execute();
    }

    public void setSearchMdlIdBVar(RichInputText searchMdlIdBVar) {
        this.searchMdlIdBVar = searchMdlIdBVar;
    }

    public RichInputText getSearchMdlIdBVar() {
        return searchMdlIdBVar;
    }

    public void visibleVCL(ValueChangeEvent valueChangeEvent) {
        System.out.println(" visibleVCL valueChangeEvent " + valueChangeEvent.getNewValue());
    }

    public void grpByVCL(ValueChangeEvent valueChangeEvent) {
        System.out.println(" grpByVCL valueChangeEvent " + valueChangeEvent.getNewValue());
    }

    public void setQueryVar(String queryVar) {
        this.queryVar = queryVar;
    }

    public String getQueryVar() {
        return queryVar;
    }


    public void setClmNmArr(ArrayList<String> ClmNmArr) {
        this.ClmNmArr = ClmNmArr;
    }

    public ArrayList<String> getClmNmArr() {
        return ClmNmArr;
    }


    public void setClmDesArrc(ArrayList<String> clmDesArrc) {
        this.clmDesArrc = clmDesArrc;
    }

    public ArrayList<String> getClmDesArrc() {
        return clmDesArrc;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setGrppNm(List<String> grppNm) {
        this.grppNm = grppNm;
    }

    public List<String> getGrppNm() {
        return grppNm;
    }

    public void setSumColumnName(List<String> sumColumnName) {
        this.sumColumnName = sumColumnName;
    }

    public List<String> getSumColumnName() {
        return sumColumnName;
    }

    public void setWidth(List<Integer> width) {
        this.width = width;
    }

    public List<Integer> getWidth() {
        return width;
    }

    public void editAction(ActionEvent actionEvent) {
        setMode("E");
        setLinkMode("D");
        // Add event code here...
    }

    public void cancelAction(ActionEvent actionEvent) {
        // Add event code here...
        setMode("V");
        setLinkMode("D");
    
        OperationBinding saveBind = (OperationBinding)getBindings().getOperationBinding("Rollback");
        saveBind.execute();
        getBindings().getOperationBinding("Execute").execute();
    }

    public void genearteAction(ActionEvent actionEvent) {
//        if(pageTypeRadioBind.getValue().equals("p")){
//            showFacesMsg("Please select at least 4 columns",FacesMessage.SEVERITY_WARN);
//        }else{
//            showFacesMsg("Please select at least 8 columns",FacesMessage.SEVERITY_WARN);
//        }
        String call="gen";
        validateForm(call);
        OperationBinding saveBind = (OperationBinding)getBindings().getOperationBinding("Commit");
        saveBind.execute();
        OperationBinding genQuery = (OperationBinding)getBindings().getOperationBinding("geenerateQuery");
        genQuery.execute();
        setLinkMode("E");
        if (genQuery.getResult() != null) {
            System.out.println("in if ");
            queryVar = (String)genQuery.getResult();
            System.out.println(" query in bean " + queryVar);
        }
        /* OperationBinding grpWise = (OperationBinding)getBindings().getOperationBinding("getGroupWiseName");
         grpWise.execute();
         if(grpWise.getResult() !=null){
              grppNm = (String)grpWise.getResult();
              System.out.println("group wise:  "+grppNm+"-------");
         } */
        
            
        
        OperationBinding grpWise = (OperationBinding)getBindings().getOperationBinding("getGroupWiseName");
        grpWise.execute();
        if (grpWise.getResult() != null) {
            grppNm = (List<String>)grpWise.getResult();
            
            System.out.println("Value of group in the bean :");
            ListIterator<String> itr = grppNm.listIterator();
            while (itr.hasNext()) {
                String next = itr.next();
                
                System.out.println("value of grp by is: " + next);
            }
        }
        
        fileType=String.valueOf(fileTypeRadioBind.getValue());
       
        OperationBinding clmNm = (OperationBinding)getBindings().getOperationBinding("getClmNm");
        clmNm.execute();
        System.out.println("clmNm " + clmNm.getResult());
        if (clmNm.getResult() != null) {
            ClmNmArr = (ArrayList<String>)clmNm.getResult();
            
            System.out.println("ClmNmArr in bean " + ClmNmArr);
        }

        OperationBinding clmDesc = (OperationBinding)getBindings().getOperationBinding("getClmDesc");
        clmDesc.execute();
        System.out.println("clmNm des  in bean rtn " + clmDesc.getResult());
        if (clmDesc.getResult() != null) {
            clmDesArrc = (ArrayList<String>)clmDesc.getResult();
           
            System.out.println("clmNm desc in bean " + clmDesArrc);
        }
        clmDesArrc = (ArrayList<String>)clmDesc.getResult();


        OperationBinding sumColumn = (OperationBinding)getBindings().getOperationBinding("getSumColumnName");
        sumColumn.execute();
        //List<String> sumColumnName = (List<String>)sumColumn.getResult();
        if (sumColumn.getResult() != null) {
            sumColumnName = (List<String>)sumColumn.getResult();
        }
        System.out.println("The name of column which need to sum:  ");
        ListIterator<String> iterator = sumColumnName.listIterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            System.out.println("Sum Name is: " + next);
        }

        OperationBinding columnWidth = (OperationBinding)getBindings().getOperationBinding("getColumnWidth");
        columnWidth.execute();
        if (columnWidth.getResult() != null) {
            width = (List<Integer>)columnWidth.getResult();
        }
        getPageType();

        OperationBinding colsumwithoutgrp =
            (OperationBinding)getBindings().getOperationBinding("getColumSumWihoutGrp");
        colsumwithoutgrp.execute();
        if (colsumwithoutgrp.getResult() != null) {
            colSumNamewithoutGrp = (ArrayList<String>)colsumwithoutgrp.getResult();
        }

        /*  FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext session = (HttpSession)context.getExternalContext(); */
        /* HttpServletResponse res =
            (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
        HttpServletRequest req =
            (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        //HttpServletResponse  res = (HttpServletResponse)session.getServletContext();
        try {
            res.sendRedirect("http://www.google.com");
            RequestDispatcher dispatcher = req.getRequestDispatcher("http://www.google.com");
            dispatcher.forward(req, res);
           /// dispatcher.include(req, res);
        } catch (Exception e) {
            e.printStackTrace();
        } */

    }

    public void setGolink(RichGoLink golink) {
        this.golink = golink;
    }

    public RichGoLink getGolink() {
        return golink;
    }

    public void setPageTypeRadioBind(RichSelectOneRadio pageTypeRadioBind) {
        this.pageTypeRadioBind = pageTypeRadioBind;
    }

    public RichSelectOneRadio getPageTypeRadioBind() {
        return pageTypeRadioBind;
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }

    public String getPageType() {
        if (pageTypeRadioBind.getValue() != null) {
            return (pageTypeRadioBind.getValue().toString());
        }
        return "p";
        //return pageType;
    }

    public void templateAddAction(ActionEvent actionEvent) {
        OperationBinding binding = (OperationBinding)getBindings().getOperationBinding("setBindValue");
        binding.execute();
        setMode("A");
        setLinkMode("D");
       // AdfFacesContext.getCurrentInstance().addPartialTarget(tempLOVBind);
        getBindings().getOperationBinding("CreateInsert").execute();

    }

    public void resetAction(ActionEvent actionEvent) {
        OperationBinding binding = (OperationBinding)getBindings().getOperationBinding("reset");
        binding.execute();
    }

    public void validateReportFormat(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            OperationBinding rpfrmt = (OperationBinding)getBindings().getOperationBinding("checkReportName");
            rpfrmt.getParamsMap().put("name", object.toString());
            rpfrmt.execute();
            boolean flag = (Boolean)rpfrmt.getResult();

            if (flag == false) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate Name", null));
            }
        }
    }

    public void validateAlias(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            OperationBinding rpfrmt = (OperationBinding)getBindings().getOperationBinding("checkaliasName");
            rpfrmt.getParamsMap().put("name", object.toString());
            rpfrmt.execute();
            boolean flag = (Boolean)rpfrmt.getResult();

            if (flag == false) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate Name", null));
            }
        }
    }

    public void checkColumnOrdering(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            int val = Integer.parseInt(object.toString());

            if (val <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Number Should be greater than Zero", null));
            }
        }
    }

    public void validateColumnWidth(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            int val = Integer.parseInt(object.toString());

            if (val <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              " Column Width Should be greater than Zero", null));
            }
        }
    }

    public void validateGroupSerial(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            int val = Integer.parseInt(object.toString());

            if (val <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Serial Number Should be greater than Zero", null));
            }
        }
    }

    public void setColSumNamewithoutGrp(List<String> colSumNamewithoutGrp) {
        this.colSumNamewithoutGrp = colSumNamewithoutGrp;
    }

    public List<String> getColSumNamewithoutGrp() {
        return colSumNamewithoutGrp;
    }

    public void tableDeleteAction(ActionEvent actionEvent) {
        setLinkMode("D");
        
    }

    public void showFacesMsg(String msg, FacesMessage.Severity type) {
        FacesMessage message = new FacesMessage(msg);
        FacesContext context = FacesContext.getCurrentInstance();
        message.setSeverity(type);
        context.addMessage(null, message);
    }

    public void dialogAction(DialogEvent dialogEvent) {
        System.out.println("name is: " + dialogEvent.getOutcome().name());
        if (dialogEvent.getOutcome().name().equalsIgnoreCase("OK")) {
            OperationBinding binding = (OperationBinding)getBindings().getOperationBinding("deleteTable");
            Object execute = binding.execute();
            if (binding.getResult() != null) {
                Boolean object = (Boolean)binding.getResult();
                String msg = "";
                if (object == true) {
                    msg = "Record deleted Successfully!";
                    showFacesMsg(msg, FacesMessage.SEVERITY_INFO);
                } else {
                    msg = "Error Occur During the deletion of Record";
                    showFacesMsg(msg, FacesMessage.SEVERITY_ERROR);
                }
            }
        }
    }

    public void setLinkMode(String linkMode) {
        this.linkMode = linkMode;
    }

    public String getLinkMode() {
        return linkMode;
    }

    public void setFileTypeRadioBind(RichSelectOneRadio fileTypeRadioBind) {
        this.fileTypeRadioBind = fileTypeRadioBind;
    }

    public RichSelectOneRadio getFileTypeRadioBind() {
        return fileTypeRadioBind;
    }

    public void setColDescPgBind(RichInputText colDescPgBind) {
        this.colDescPgBind = colDescPgBind;
    }

    public RichInputText getColDescPgBind() {
        return colDescPgBind;
    }

    public void VisiblePgValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if(fileTypeRadioBind.getValue().toString().equalsIgnoreCase("XLS")){
            System.out.println(groupByCBBind.getValue().toString().equalsIgnoreCase("true"));
        if(groupByCBBind.getValue().toString().equalsIgnoreCase("true")){
            String VisCBClientId=visiblePgCBBind.getClientId();
            showFacesMessage("Grouping is not available with CSV File Type ", "E", false, "F", VisCBClientId);
            object=false;
        }
        }
    }

    public void setVisiblePgCBBind(RichSelectBooleanCheckbox visiblePgCBBind) {
        this.visiblePgCBBind = visiblePgCBBind;
    }

    public RichSelectBooleanCheckbox getVisiblePgCBBind() {
        return visiblePgCBBind;
    }

    public void setGroupByCBBind(RichSelectBooleanCheckbox groupByCBBind) {
        this.groupByCBBind = groupByCBBind;
    }

    public RichSelectBooleanCheckbox getGroupByCBBind() {
        return groupByCBBind;
    }

    public void setCreateTypeRadioBind(RichSelectOneRadio createTypeRadioBind) {
        this.createTypeRadioBind = createTypeRadioBind;
    }

    public RichSelectOneRadio getCreateTypeRadioBind() {
        return createTypeRadioBind;
    }

    public void CreateTempActionListener(ActionEvent actionEvent) {
        Boolean create=false;
        System.out.println("schema selected " +schemaNmLOVBind.getValue());
        System.out.println("view selected "+viewNmLOVBind.getValue());
        OperationBinding createTemplate = (OperationBinding)getBindings().getOperationBinding("createTemp");
       
        createTemplate.execute();
    }

    public void CreateTempBackActionListener(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void setSchemaNmLOVBind(RichSelectOneChoice schemaNmLOVBind) {
        this.schemaNmLOVBind = schemaNmLOVBind;
    }

    public RichSelectOneChoice getSchemaNmLOVBind() {
        return schemaNmLOVBind;
    }

    public void setViewNmLOVBind(RichSelectOneChoice viewNmLOVBind) {
        this.viewNmLOVBind = viewNmLOVBind;
    }

    public RichSelectOneChoice getViewNmLOVBind() {
        return viewNmLOVBind;
    }
    public void filterTable(){
 
    }
    public void refreshActionListener(ActionEvent actionEvent) {
        System.out.println("table displayed rows"+tabBind.getDisplayRow());
        OperationBinding appNm = (OperationBinding)getBindings().getOperationBinding("setAppName");
        appNm.execute();
        OperationBinding refreshBind = (OperationBinding)getBindings().getOperationBinding("Commit");
        refreshBind.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tabBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchMdlIdBVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(mdlIdBVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(visiblePgCBBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(groupByCBBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(colWidthTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(mdlDescOutBind);
    }

    public void setColWidthTableBind(RichInputText colWidthTableBind) {
        this.colWidthTableBind = colWidthTableBind;
    }

    public RichInputText getColWidthTableBind() {
        return colWidthTableBind;
    }

    public void setMdlDescOutBind(RichOutputText mdlDescOutBind) {
        this.mdlDescOutBind = mdlDescOutBind;
    }

    public RichOutputText getMdlDescOutBind() {
        return mdlDescOutBind;
    }

    public void setTabBind(RichTable tabBind) {
        this.tabBind = tabBind;
    }

    public RichTable getTabBind() {
        return tabBind;
    }

    public void setMdldescColumnBind(RichColumn mdldescColumnBind) {
        this.mdldescColumnBind = mdldescColumnBind;
    }

    public RichColumn getMdldescColumnBind() {
        return mdldescColumnBind;
    }

    public void tabQueryListener(QueryEvent queryEvent) {
        // Add event code here...
    }

    public void setAliasTBBind(RichInputText aliasTBBind) {
        this.aliasTBBind = aliasTBBind;
    }

    public RichInputText getAliasTBBind() {
        return aliasTBBind;
    }

    public void deleteRowAL(ActionEvent actionEvent) {
        //Fetch current row data as per index
                FacesCtrlHierNodeBinding rowdata =            (FacesCtrlHierNodeBinding)tabBind.getRowData(tabBind.getRowIndex());
         //getting row and typecasting in RowImpl
                SlsRptMdlVORowImpl rowImpl = (SlsRptMdlVORowImpl)rowdata.getRow();
        //Getting primary key of the record
                Number keyVal = rowImpl.getMdlId();
                DCBindingContainer dcBindings =
                    (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        
        //Iterator binding on the page          
         DCIteratorBinding iteratorBinding = (DCIteratorBinding)dcBindings.get("SlsRptMdlVOIterator");
                RowSetIterator rowSetIterator = iteratorBinding.getRowSetIterator();
                Key key = new Key(new Object[] { keyVal });
                Row row = rowSetIterator.findByKey(key, 1)[0];
                rowSetIterator.setCurrentRow(row);
        //Removing the current record
        rowSetIterator.removeCurrentRow(); 
    }

    public String linkCommit() {
        OperationBinding commBind = (OperationBinding)getBindings().getOperationBinding("Commit");
        commBind.execute();
        return "true";
    }

    public void setOrderNoTBBind(RichInputText orderNoTBBind) {
        this.orderNoTBBind = orderNoTBBind;
    }

    public RichInputText getOrderNoTBBind() {
        return orderNoTBBind;
    }

    public void delLinkAL(ActionEvent actionEvent) {
        OperationBinding delBind = (OperationBinding)getBindings().getOperationBinding("Delete");
        delBind.execute();
        OperationBinding ex = (OperationBinding)getBindings().getOperationBinding("Execute");
        ex.execute();
        OperationBinding commBind = (OperationBinding)getBindings().getOperationBinding("Commit");
        commBind.execute();
    }

    public void setTempLOVBind(RichSelectOneChoice tempLOVBind) {
        this.tempLOVBind = tempLOVBind;
    }

    public RichSelectOneChoice getTempLOVBind() {
        return tempLOVBind;
    }

    public void setCrossCBBind(RichSelectBooleanCheckbox crossCBBind) {
        this.crossCBBind = crossCBBind;
    }

    public RichSelectBooleanCheckbox getCrossCBBind() {
        return crossCBBind;
    }
}
