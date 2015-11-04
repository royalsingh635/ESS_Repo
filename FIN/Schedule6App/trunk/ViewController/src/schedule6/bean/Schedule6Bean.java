package schedule6.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectManyShuttle;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelHeader;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.OperationBinding;

import schedule6.bean.utill.JSFUtill;

public class Schedule6Bean {
    private RichPopup cogPopUp;
    private RichPopup coaPopUp;
    private RichPanelGroupLayout coaShuttleBind;
    private String modeSchedule="S";
    private String modeLine="S";
    private RichInputText searchNameBind;
    private RichTable coaTableBind;
    private RichSelectManyShuttle coaShuttle;
    private List shuttleValue=null;
    private RichTable scheduleTable;
    private RichTable lineTable;
    private RichTable cogTable;
   // private JSFUtill utill=new JSFUtill();
    private RichInputText dispName;
    private RichSelectBooleanCheckbox isFinal;
    private RichPopup deleteSchedulePop;
    private RichPopup deleteLinePop;
    private RichPopup deleteCogPop;
    private RichPopup schDetailPop;
    private RichSelectOneChoice scheduleId;
    private RichSelectOneChoice hoOrgId;
    private RichSelectOneChoice orgId;
    private RichPopup replicatePop;
    private RichPopup unmappedCoaPop;
    private RichPopup genDataPop;
    private RichSelectOneChoice scheduleForOrg;
    private RichSelectOneChoice schIdGenData;
    private RichSelectOneChoice orgIdGanData;
    private RichTable schOrgTab;
    private RichInputListOfValues orgDesc;
    private RichPanelFormLayout schLineForm;
    private RichPanelGroupLayout schGroup;
    private RichPanelHeader schLineHeader;
    private RichPanelHeader schHead;
    private RichSelectOneChoice genDataSch;
    private RichSelectOneChoice ifrsFyId;
    private RichSelectOneChoice getDataFyId;
    private RichSelectOneChoice fyIdGenData;
    
    private String cogName=null;


    public Schedule6Bean() {
       // JSFUtill.getCurrentRowForIteratorAndChild("Hello");
    }

    public void setCogPopUp(RichPopup cogPopUp) {
        this.cogPopUp = cogPopUp;
    }

    public RichPopup getCogPopUp() {
        return cogPopUp;
    }

    public void setCoaPopUp(RichPopup coaPopUp) {
        this.coaPopUp = coaPopUp;
    }

    public RichPopup getCoaPopUp() {
        return coaPopUp;
    }
    public RichPanelGroupLayout getCoaShuttleBind() {
        return coaShuttleBind;
    }
    
    public void setCoaShuttleBind(RichPanelGroupLayout coaShuttleBind) {
        this.coaShuttleBind = coaShuttleBind;
    }
    
    public void setModeSchedule(String modeSchedule) {
        this.modeSchedule = modeSchedule;
    }

    public String getModeSchedule() {
        return modeSchedule;
    }

    public void setModeLine(String modeLine) {
        this.modeLine = modeLine;
    }

    public String getModeLine() {
        return modeLine;
    }
    
    public void setSearchNameBind(RichInputText searchNameBind) {
        this.searchNameBind = searchNameBind;
    }

    public RichInputText getSearchNameBind() {
        return searchNameBind;
    }
    
    public void setShuttleValue(List shuttleValue) {
        this.shuttleValue = shuttleValue;
    }

    public List getShuttleValue() {
        return shuttleValue;
    }
    
    
    
    
    

    public void showCoaPopUp(ActionEvent actionEvent) {
        JSFUtill.getOpertion("filterCoa").execute();
        OperationBinding op=JSFUtill.getOpertion("getCoaList");
        op.execute();
        List list=(List)op.getResult();
        this.setShuttleValue(list);
        //utill.getCurrentRowForIterator();
        JSFUtill.getOpertion("getCurrentRow").execute();
        JSFUtill.showPopup(this.getCoaPopUp(), true);
        
        
    }

    public void addCogPopUp(ActionEvent actionEvent) {
        System.out.println("Add Group");
        JSFUtill.getOpertion("getCurrentRow").execute();
        JSFUtill.getOpertion("CreateInsert1").execute();
        JSFUtill.showPopup(this.getCogPopUp(), true);
    }

    public void editCogPopUp(ActionEvent actionEvent) {
        System.out.println("edit Group");
       JSFUtill.getOpertion("getCurrentRow").execute();
       cogName=JSFUtill.getOpertion("getDisplayName").execute().toString();
        JSFUtill.showPopup(this.getCogPopUp(), true);
    }

    public void listenCogDialogue(DialogEvent dialogEvent) {
        if(dialogEvent.getOutcome().name().toString().equalsIgnoreCase("OK")){

                    JSFUtill.getOpertion("Commit").execute();
                    cogName=null;
                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.cogTable);

  
        }
    }

    public void listenCogPop(PopupCanceledEvent popupCanceledEvent) {
        JSFUtill.getOpertion("Rollback").execute();
        cogName=null;
        JSFUtill.getOpertion("setCurrentRow").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.cogTable);
    }

    public void save(ActionEvent actionEvent) {
       
       String isValidated="Y"; // validatePage();
        if (isValidated.equalsIgnoreCase("Y")) {
            OperationBinding op = null;

            int error = 0;
            if (this.getModeLine().equalsIgnoreCase("A")) {
                op = JSFUtill.getOpertion("setScheduleLineId");

                op.execute();

                error = op.getErrors().size();

            }

            if (error == 0) {
                try{
                             JSFUtill.getOpertion("Commit").execute();
                }catch(Exception e){
                    e.printStackTrace();
                }
            } else {
                System.out.println("Error in Function");
            }

           JSFUtill.getOpertion("getCurrentRow").execute();
            JSFUtill.getOpertion("Execute").execute();
           JSFUtill.getOpertion("setCurrentRow").execute();


            this.setModeLine("S");
            this.setModeSchedule("S");

            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getScheduleTable());


        }
    }

    public void cancel(ActionEvent actionEvent) {
        JSFUtill.getOpertion("Rollback").execute();
       JSFUtill.getOpertion("setCurrentRow").execute();
        this.setModeLine("S");
        this.setModeSchedule("S");
    }

    public void showReportFormat(ActionEvent actionEvent) {
        JSFUtill.showPopup(this.schDetailPop, true);
    }

    public void createSchedule(ActionEvent actionEvent) {
       JSFUtill.getOpertion("getCurrentRow").execute();
      // System.out.println("after getting current row");
        JSFUtill.getOpertion("CreateWithParams").execute();
      //  System.out.println("After executing createwith params");
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(scheduleTable);
        
        this.setModeSchedule("A");
        
    }

    public void editSchedule(ActionEvent actionEvent) {
     JSFUtill.getOpertion("getCurrentRow").execute();
        this.setModeSchedule("A");
        
    }

    public void createLine(ActionEvent actionEvent) {
       JSFUtill.getOpertion("getCurrentRow").execute();
        JSFUtill.getOpertion("CreateInsert").execute();
        this.setModeLine("A");
    }

    public void editLine(ActionEvent actionEvent) {
       JSFUtill.getOpertion("getCurrentRow").execute();
        this.setModeLine("E");
    }

    public void searchSchedule(ActionEvent actionEvent) {
        OperationBinding op=JSFUtill.getOpertion("searchSchedule");
        op.getParamsMap().put("name", this.getSearchNameBind().getValue());
        op.execute();   
        
        if(op.getErrors().size()!=0){
            System.out.println("Error Occured in searching Function");
        }
    
    }

    public void resetSchedule(ActionEvent actionEvent) {
        OperationBinding op=JSFUtill.getOpertion("resetSchedule");
        op.execute();   
        
        if(op.getErrors().size()!=0){
            System.out.println("Error Occured in reseting Function");
        }else{
            this.searchNameBind.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(searchNameBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getScheduleTable());
        }
    }


    public void addCoa(ActionEvent actionEvent) {
       
//        
//        System.out.println("Class of valies in shuttle"+this.coaShuttle.getValue().getClass().getName());
//        
//        if(this.coaShuttle.getValue().getClass().getName().contains("List")){
//            System.out.println("Shuttl value "+(List)this.coaShuttle.getValue());
//        }
//        
//        System.out.println("Shuttl value "+ this.coaShuttle.getValue());
        
       JSFUtill.getOpertion("getCurrentRow").execute();
        List list =(List) this.coaShuttle.getValue();
        OperationBinding op=JSFUtill.getOpertion("addCoaToSubDtl");
        op.getParamsMap().put("list", list);
        op.execute();
        
        if(op.getErrors().size()==0){
           System.out.println("Record Inserted"); 
            
        }else{
            JSFUtill.getOpertion("Rollback").execute();
            System.out.println("Error in intering Coa " +op.getErrors());
        }
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.coaTableBind);
    }

    public void setCoaTableBind(RichTable coaTableBind) {
        this.coaTableBind = coaTableBind;
    }

    public RichTable getCoaTableBind() {
        return coaTableBind;
    }

    public void listenCoaDetailPop(DialogEvent dialogEvent) {
        if(dialogEvent.getOutcome().name().toString().equalsIgnoreCase("OK")){
            JSFUtill.getOpertion("Commit").execute();
        }
    }

    public void listenCoaDetailPop(PopupCanceledEvent popupCanceledEvent) {
        JSFUtill.getOpertion("Rollback").execute();
      JSFUtill.getOpertion("setCurrentRow").execute();
    }

    public void setCoaShuttle(RichSelectManyShuttle coaShuttle) {
        this.coaShuttle = coaShuttle;
    }

    public RichSelectManyShuttle getCoaShuttle() {
        return coaShuttle;
    }

    public void setScheduleTable(RichTable scheduleTable) {
        this.scheduleTable = scheduleTable;
    }

    public RichTable getScheduleTable() {
        return scheduleTable;
    }

    public void setLineTable(RichTable lineTable) {
        this.lineTable = lineTable;
    }

    public RichTable getLineTable() {
        return lineTable;
    }

    public void setCogTable(RichTable cogTable) {
        this.cogTable = cogTable;
    }

    public RichTable getCogTable() {
        return cogTable;
    }


    public void setDispName(RichInputText dispName) {
        this.dispName = dispName;
    }

    public RichInputText getDispName() {
        return dispName;
    }

    public void listenCogChangeVCE(ValueChangeEvent valueChangeEvent) {
        
        Object o =valueChangeEvent.getNewValue();
        
        if(o!= null){
            this.getDispName().setValue((String)o);
        }else{
            this.getDispName().setValue(null);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDispName());
    }

    public void validateParent(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null &&(!(object.toString()).equals("00"))) {
            uIComponent.processUpdates(FacesContext.getCurrentInstance());
            OperationBinding op = JSFUtill.getOpertion("validateParent");
            op.execute();
            if (op.getErrors().size() == 0) {
                String result = (String) op.getResult();
                System.out.println("Result is " + result);
                if (result.equals("N")) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, JSFUtill.resolvElDCMsg("#{bundle['MSG.1620']}").toString(),
                                                                 JSFUtill.resolvElDCMsg("#{bundle['MSG.1621']}").toString()));
                }

            } else {
                System.out.println("Error in executing function");
            }

        }
    }
    
    public String validatePage(){
        
        String isValid="N";
        OperationBinding op=JSFUtill.getOpertion("validateParent");
        op.execute();
        if(op.getErrors().size()==0){
            String result= (String) op.getResult();
            if(result.equals("N")){
                isValid= "N";
                
                FacesMessage fc=new FacesMessage(FacesMessage.SEVERITY_ERROR,"Invalid Value","You cant add a child for this parent");
                FacesContext.getCurrentInstance().addMessage(null, fc);
            }else{
                    isValid= "Y";
                }
            
        }else{
            System.out.println("Errro in executing function");
        }
        
        
        
        return isValid;
    }
    
    public String getIsFinalized(){
        Object o=this.getIsFinal().getValue();
     //   System.out.println("Object is "+o);
        if(o instanceof String  ){
            if(o.toString().equalsIgnoreCase("Y")){
                return "Y";
            }else{
                return "N";
            }
        }
        if(o instanceof Boolean){
            if((Boolean)o){
                return "Y";
            }else{
                return "N";
            }
        }
        return "N";
    }

    public void setIsFinal(RichSelectBooleanCheckbox isFinal) {
        this.isFinal = isFinal;
    }

    public RichSelectBooleanCheckbox getIsFinal() {
        return isFinal;
    }

    public void validMaxLevel(FacesContext facesContext, UIComponent uIComponent, Object object) {
        
        if(object !=null  && object.toString().trim().length() > 0){
            if((Integer)object < 1){
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,JSFUtill.resolvElDCMsg("#{bundle['MSG.1620']}").toString(),JSFUtill.resolvElDCMsg("#{bundle['MSG.1622']}").toString()));
            }
            if((Integer)object >10){
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,JSFUtill.resolvElDCMsg("#{bundle['MSG.1620']}").toString(),JSFUtill.resolvElDCMsg("#{bundle['MSG.1623']}").toString()));
            }
        }

    }

    public void validateSchName(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().trim().length()>0)
        {
                  OperationBinding op=JSFUtill.getOpertion("validateNameSch");
                op.getParamsMap().put("name", object);
               // op.getParamsMap().put("mode", this.getModeSchedule());
                op.execute();
                
                if(op.getErrors().size()==0){
                    
                    String result=op.getResult().toString();
                    
                    if(result.equalsIgnoreCase("N")){
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,JSFUtill.resolvElDCMsg("#{bundle['LBL.2979']}").toString(),
                                                                      JSFUtill.resolvElDCMsg("#{bundle['MSG.1624']}").toString()));
                    }
                    
                }else{
                    System.out.println("Error in function "+op.getErrors());
                }
        }else{
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,JSFUtill.resolvElDCMsg("#{bundle['MSG.1625']}").toString(),JSFUtill.resolvElDCMsg("#{bundle['MSG.1626']}").toString()));
        }
    
    }

    public void validateLineName(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().trim().length()>0)
        {
                  OperationBinding op=JSFUtill.getOpertion("validateNameLine");
                op.getParamsMap().put("name", object);
               // op.getParamsMap().put("mode", this.getModeLine());
                op.execute();
                
                if(op.getErrors().size()==0){
                    
                    String result=op.getResult().toString();
                    
                    if(result.equalsIgnoreCase("N")){
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,JSFUtill.resolvElDCMsg("#{bundle['LBL.2979']}").toString(),
                                                                      JSFUtill.resolvElDCMsg("#{bundle['MSG.1627']}").toString()));
                    }
                    
                }else{
                    System.out.println("Error in function "+op.getErrors());
                }
        }else{
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,JSFUtill.resolvElDCMsg("#{bundle['MSG.1625']}").toString(),
                                                          JSFUtill.resolvElDCMsg("#{bundle['MSG.1626']}").toString()));
        }
    }

    public void valiadteCog(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().trim().length()>0)
        {
                  OperationBinding op=JSFUtill.getOpertion("validateCogIdDtl");
                op.getParamsMap().put("cog", object);
               // op.getParamsMap().put("mode", this.getModeCog());
                op.execute();
                
                if(op.getErrors().size()==0){
                    
                    String result=op.getResult().toString();
                    
                    if(result.equalsIgnoreCase("N")){
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,JSFUtill.resolvElDCMsg("#{bundle['LBL.2979']}").toString(),
                                                                      JSFUtill.resolvElDCMsg("#{bundle['MSG.1628']}").toString()));
                    }else{
                        //isInUnMapped
                    if (cogName == null || (!this.cogName.equalsIgnoreCase(object.toString()))) {
                        op = JSFUtill.getOpertion("isInUnMapped");
                        op.getParamsMap().put("cog", object);
                        op.execute();
                        result = op.getResult().toString();

                        if (op.getErrors().size() > 0) {
                            System.out.println("Error in function " + op.getErrors());
                        }

                        if (result.equalsIgnoreCase("N")) {
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                          JSFUtill.resolvElDCMsg("#{bundle['LBL.2979']}").toString(),
                                                                          JSFUtill.resolvElDCMsg("Coa of the Cog has been added to another line").toString()));
                        }
                    }
                    }
                    
                }else{
                    System.out.println("Error in function "+op.getErrors());
                }
        }else{
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,JSFUtill.resolvElDCMsg("#{bundle['MSG.1625']}").toString(),
                                                          JSFUtill.resolvElDCMsg("#{bundle['MSG.1626']}").toString()));
        }
    }

    public void schCogDispName(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().trim().length()>0)
        {
                  OperationBinding op=JSFUtill.getOpertion("validateNameDtl");
                op.getParamsMap().put("name", object);
               // op.getParamsMap().put("mode", this.getModeCog());
                op.execute();
                
                if(op.getErrors().size()==0){
                    
                    String result=op.getResult().toString();
                    
                    if(result.equalsIgnoreCase("N")){
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,JSFUtill.resolvElDCMsg("#{bundle['LBL.2979']}").toString(),
                                                                      JSFUtill.resolvElDCMsg("#{bundle['MSG.1629']}").toString()));
                    }
                    
                }else{
                    System.out.println("Error in function "+op.getErrors());
                }
        }else{
           // throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Required","This is mandatory Field"));
        }

    }

    public void deleteCogDiaLogueListen(DialogEvent dialogEvent) {
        if(dialogEvent.getOutcome().name().toString().equalsIgnoreCase("OK")){ 
            OperationBinding op=JSFUtill.getOpertion("deleteCog");
            op.getParamsMap().put("cog",null);
            op.execute();
            this.commitData(op);
        }  
    }

    public void deleteLineDiaListener(DialogEvent dialogEvent) {
        if(dialogEvent.getOutcome().name().toString().equalsIgnoreCase("OK")){ 
                OperationBinding op=JSFUtill.getOpertion("deleteLine");
                op.getParamsMap().put("line",null);
                op.execute();
            this.commitData(op);
            refershOnDelete();
        }
                
                
       
    }

    public void deleteScheduleDialogueListener(DialogEvent dialogEvent) {
        if(dialogEvent.getOutcome().name().equalsIgnoreCase("OK")){
             OperationBinding op=JSFUtill.getOpertion("deleteSchedule");
            op.execute();
            this.commitData(op);
            refershOnDelete();
        }
       
    }

    public void deleteSchdulePopList(PopupCanceledEvent popupCanceledEvent) {
        JSFUtill.getOpertion("Rollback").execute();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,JSFUtill.resolvElDCMsg("#{bundle['MSG.1630']}").toString()
                                                                            ,JSFUtill.resolvElDCMsg("#{bundle['MSG.1631']}").toString()));
        refereshTable();
    }

    public void deleteLinePopList(PopupCanceledEvent popupCanceledEvent) {
        JSFUtill.getOpertion("Rollback").execute();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,JSFUtill.resolvElDCMsg("#{bundle['MSG.1630']}").toString(),
                                                                           JSFUtill.resolvElDCMsg("#{bundle['MSG.1631']}").toString()));
        refereshTable();
    }

    public void deleteCogPopList(PopupCanceledEvent popupCanceledEvent) {
        JSFUtill.getOpertion("Rollback").execute();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,JSFUtill.resolvElDCMsg("#{bundle['MSG.1630']}").toString(),
                                                                            JSFUtill.resolvElDCMsg("#{bundle['MSG.1631']}").toString()));
        refereshTable();
    }
    
    
    public void refereshTable(){
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getScheduleTable());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getLineTable());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCogTable());
    }
    
    public void commitData(OperationBinding op){
        if(op.getErrors().size()==0) {
            JSFUtill.getOpertion("Commit").execute();
             refereshTable();
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,JSFUtill.resolvElDCMsg("#{bundle['MSG.1630']}").toString(),
                                                                                 JSFUtill.resolvElDCMsg("#{bundle['MSG.528']}").toString()));
        }else{
            System.out.println("Error in function "+op.getErrors());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","Errors are "+op.getErrors()));
            
        }
    }

    public void deleteSchedule(ActionEvent actionEvent) {
        JSFUtill.showPopup(this.getDeleteSchedulePop(), true);
    }

    public void setDeleteSchedulePop(RichPopup deleteSchedulePop) {
        this.deleteSchedulePop = deleteSchedulePop;
    }

    public RichPopup getDeleteSchedulePop() {
        return deleteSchedulePop;
    }

    public void deleteLink(ActionEvent actionEvent) {
        OperationBinding op=JSFUtill.getOpertion("isParent");
        op.execute();
        String result=op.getResult().toString();
        if(result.equalsIgnoreCase("N")){  
            JSFUtill.showPopup(this.getDeleteLinePop(), true);
        }else{
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Stop",JSFUtill.resolvElDCMsg("#{bundle['MSG.1634']}").toString() ));
        }
    }

    public void setDeleteLinePop(RichPopup deleteLinePop) {
        this.deleteLinePop = deleteLinePop;
    }

    public RichPopup getDeleteLinePop() {
        return deleteLinePop;
    }

    public void setDeleteCogPop(RichPopup deleteCogPop) {
        this.deleteCogPop = deleteCogPop;
    }

    public RichPopup getDeleteCogPop() {
        return deleteCogPop;
    }

    public void deleteCogLink(ActionEvent actionEvent) {
        JSFUtill.showPopup(this.getDeleteCogPop(), true);
    }

    public void validateModeLine(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if(object != null && object.toString().trim().length()>0&& object.toString().equalsIgnoreCase("S")){
            OperationBinding op=JSFUtill.getOpertion("isValidMode");
            op.getParamsMap().put("vo","Group");
            op.execute();
            String result = (String) op.getResult();
            if(result.equalsIgnoreCase("Y")){
                
            }else{
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,JSFUtill.resolvElDCMsg("#{bundle['MSG.1632']}").toString(),
                                                                JSFUtill.resolvElDCMsg("#{bundle['MSG.1633']}").toString()));
            }
                
        }

    }

    public void validateModeCoa(FacesContext facesContext, UIComponent uIComponent, Object object) {
      //  System.out.println("Ui compoenent is "+uIComponent.getClass().getName());
        System.out.println("You are in validator "+object);
        if(object != null && object.toString().trim().length()>0&& object.toString().equalsIgnoreCase("1")){
            System.out.println("Calling method ");
            OperationBinding op=JSFUtill.getOpertion("isValidMode");
            op.getParamsMap().put("vo","Coa");
            op.execute();
            String result = (String) op.getResult();
            if(result.equalsIgnoreCase("Y")){
                
            }else{
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,JSFUtill.resolvElDCMsg("#{bundle['MSG.1632']}").toString(),
                                                                JSFUtill.resolvElDCMsg("#{bundle['MSG.1633']}").toString()));
            }
                
        }
    }

    public void replicatScheule(ActionEvent actionEvent) {
        Object schId=this.getScheduleId().getValue();
        Object hoOrgId= this.getHoOrgId().getValue();
    
        
        System.out.println("Sch id "+schId +" ho id  "+hoOrgId);
        if(schId == null || schId.toString().trim().length()== 0){
            FacesContext.getCurrentInstance().addMessage(this.getScheduleId().getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR,"Required","Please make a selection"));
        }else if( hoOrgId == null || hoOrgId.toString().trim().length() == 0){
            FacesContext.getCurrentInstance().addMessage(this.getHoOrgId().getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR,"Required","Please make a selection"));
        }else{

            JSFUtill.showPopup(this.getReplicatePop(), true);
        }
        
    }

    public void ListenOrgSchDialogue(DialogEvent dialogEvent) {
        JSFUtill.getOpertion("updateOrgSch").execute();
        JSFUtill.getOpertion("Commit").execute();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Suuccessfull","Schedule has been saved successfully")); 
        
    }

    public void setSchDetailPop(RichPopup schDetailPop) {
        this.schDetailPop = schDetailPop;
    }

    public RichPopup getSchDetailPop() {
        return schDetailPop;
    }

    public void setScheduleId(RichSelectOneChoice scheduleId) {
        this.scheduleId = scheduleId;
    }

    public RichSelectOneChoice getScheduleId() {
        return scheduleId;
    }

    public void setHoOrgId(RichSelectOneChoice hoOrgId) {
        this.hoOrgId = hoOrgId;
    }

    public RichSelectOneChoice getHoOrgId() {
        return hoOrgId;
    }

    public void setOrgId(RichSelectOneChoice orgId) {
        this.orgId = orgId;
    }

    public RichSelectOneChoice getOrgId() {
        return orgId;
    }

    public void orgSchPopCancel(PopupCanceledEvent popupCanceledEvent) {
        JSFUtill.getOpertion("Rollback").execute();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Suuccessfull","Schedule has been reverted successfully")); 
    }



    public void setReplicatePop(RichPopup replicatePop) {
        this.replicatePop = replicatePop;
    }

    public RichPopup getReplicatePop() {
        return replicatePop;
    }
    
    public void replicateDiaLogLis(DialogEvent dialogEvent) {
        // Add event code here...
        
        if(dialogEvent.getOutcome().name().equalsIgnoreCase("OK")){
            OperationBinding op=JSFUtill.getOpertion("replicateSchedule");
            op.execute();
             if(op.getResult().toString().equalsIgnoreCase("Y")){
                // this.getSchDetailPop().hide();
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Suuccessfull","Schedule has been replicated successfully")); 
                 
             }else{
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"","This Schedule already exist in this ho org.")); 
             }
        }
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getSchOrgTab());
    }

    public void replicatePopList(PopupCanceledEvent popupCanceledEvent) {
        
    }

    public void setUnmappedCoaPop(RichPopup unmappedCoaPop) {
        this.unmappedCoaPop = unmappedCoaPop;
    }

    public RichPopup getUnmappedCoaPop() {
        return unmappedCoaPop;
    }

    public void showUnMappedCoa(ActionEvent actionEvent) {
        // Add event code here...
        JSFUtill.getOpertion("showCoa").execute();
        JSFUtill.showPopup(this.unmappedCoaPop, true);
    }

    public void generateData(ActionEvent actionEvent) {
        JSFUtill.showPopup(genDataPop, true);
    }

    public void setGenDataPop(RichPopup genDataPop) {
        this.genDataPop = genDataPop;
    }

    public RichPopup getGenDataPop() {
        return genDataPop;
    }

    public void genDataDiaList(DialogEvent dialogEvent) {
        if(dialogEvent.getOutcome().name().toString().equalsIgnoreCase("OK")){
            OperationBinding op=JSFUtill.getOpertion("genSchDate");
            op.execute();
            if(op.getErrors().size() == 0){
                if(op.getResult().toString().equalsIgnoreCase("Y")){
                   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Succcessfull","Data generated successfully"));
                }else{
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Sorry","Error Occured in function"));  
                }
                
            }else{
                System.out.println("Error is "+op.getErrors());
            }
        }
    }

    public void replicateToOrg(ActionEvent actionEvent) {
        
        Object schId=this.getScheduleForOrg().getValue();
        Object orgId= this.getOrgId().getValue();
       
        if(schId == null || schId.toString().trim().length()==0){
            FacesContext.getCurrentInstance().addMessage(this.getScheduleForOrg().getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR,"Required","Please make a selection"));  
        }else if(orgId == null || orgId.toString().trim().length() == 0){
                 FacesContext.getCurrentInstance().addMessage(this.getOrgId().getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR,"Required","Please make a selection"));
             }else{       
                 JSFUtill.getOpertion("updateOrgSch").execute();
                 JSFUtill.getOpertion("Commit").execute();
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Suuccessfull","Schedule has been saved successfully")); 
                 
             }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getSchOrgTab());
    }

    public void setScheduleForOrg(RichSelectOneChoice scheduleForOrg) {
        this.scheduleForOrg = scheduleForOrg;
    }

    public RichSelectOneChoice getScheduleForOrg() {
        return scheduleForOrg;
    }

    public void setSchIdGenData(RichSelectOneChoice schIdGenData) {
        this.schIdGenData = schIdGenData;
    }

    public RichSelectOneChoice getSchIdGenData() {
        return schIdGenData;
    }

    public void genDataListen(ActionEvent actionEvent) {
        
        Object schId= this.getSchIdGenData().getValue(); 
        Object orgId= this.getOrgIdGanData().getValue();
        Object fyId = this.getFyIdGenData().getValue();
        
       // System.out.println("Fy d is "+fyId);
        if(schId == null || schId.toString().trim().length() == 0){
            FacesContext.getCurrentInstance().addMessage(this.getSchIdGenData().getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR,"Mandatory","Please make a selection"));
        }else if(orgId == null || orgId.toString().trim().length() == 0){
            FacesContext.getCurrentInstance().addMessage(this.getOrgIdGanData().getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR,"Mandatory","Please make a selection"));
        } else if(fyId == null || fyId.toString().trim().length() == 0){
            FacesContext.getCurrentInstance().addMessage(this.getFyIdGenData().getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR,"Mandatory","Please make a selection"));
        }else{
            OperationBinding op=JSFUtill.getOpertion("genSchData");
            op.execute();
            if(op.getErrors().size() == 0){
                if(op.getResult().toString().equalsIgnoreCase("Y")){
                   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Succcessfull","Data generated successfully"));
                }else{
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Sorry","Error Occured in function"));  
                }
                
            }else{
                System.out.println("Error is "+op.getErrors());
            }
        }   
    }

    public void setOrgIdGanData(RichSelectOneChoice orgIdGanData) {
        this.orgIdGanData = orgIdGanData;
    }

    public RichSelectOneChoice getOrgIdGanData() {
        return orgIdGanData;
    }

    public void searchForOrgSch(ActionEvent actionEvent) {
        
        if(this.orgDesc.getValue() == null || this.orgDesc.getValue().toString().trim().length()==0){
            FacesContext.getCurrentInstance().addMessage(this.getOrgDesc().getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR,"Required ","To Search Select An Org"));
        }else{
            JSFUtill.getOpertion("searchOrgSch").execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getSchOrgTab());
           
            
        }
        
    }

    public void resetOrgSch(ActionEvent actionEvent) {
        JSFUtill.getOpertion("resetOrgSch").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getSchOrgTab());
    }

    public void setSchOrgTab(RichTable schOrgTab) {
        this.schOrgTab = schOrgTab;
    }

    public RichTable getSchOrgTab() {
        return schOrgTab;
    }

    public void setOrgDesc(RichInputListOfValues orgDesc) {
        this.orgDesc = orgDesc;
    }

    public RichInputListOfValues getOrgDesc() {
        return orgDesc;
    }

    public void setSchLineForm(RichPanelFormLayout schLineForm) {
        this.schLineForm = schLineForm;
    }

    public RichPanelFormLayout getSchLineForm() {
        return schLineForm;
    }

    public void setSchGroup(RichPanelGroupLayout schGroup) {
        this.schGroup = schGroup;
    }

    public RichPanelGroupLayout getSchGroup() {
        return schGroup;
    }

    public void setSchLineHeader(RichPanelHeader schLineHeader) {
        this.schLineHeader = schLineHeader;
    }

    public RichPanelHeader getSchLineHeader() {
        return schLineHeader;
    }

    public void setSchHead(RichPanelHeader schHead) {
        this.schHead = schHead;
    }

    public RichPanelHeader getSchHead() {
        return schHead;
    }
    
    
    public void refershOnDelete(){
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getSchHead());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getSchGroup());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getSchLineForm());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getSchLineHeader());
    }

    public void setIfrsFyId(RichSelectOneChoice ifrsFyId) {
        this.ifrsFyId = ifrsFyId;
    }

    public RichSelectOneChoice getIfrsFyId() {
        return ifrsFyId;
    }

    public void setGetDataFyId(RichSelectOneChoice getDataFyId) {
        this.getDataFyId = getDataFyId;
    }

    public RichSelectOneChoice getGetDataFyId() {
        return getDataFyId;
    }

    public void setFyIdGenData(RichSelectOneChoice fyIdGenData) {
        this.fyIdGenData = fyIdGenData;
    }

    public RichSelectOneChoice getFyIdGenData() {
        return fyIdGenData;
    }

    public void validateGroupType(FacesContext facesContext, UIComponent uIComponent, Object object) {
        
        if(object !=null && object.toString().equalsIgnoreCase("Y")){
           OperationBinding op =JSFUtill.getOpertion("isTypeValid");
           op.execute();
           String result=(String)op.getResult();
           if(result.equalsIgnoreCase("N")){
               throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Group Exist","You can add  this group only as account Type"));
           }
        }

    }
    
    public Integer[] getIterVal(){
        OperationBinding op=JSFUtill.getOpertion("getSpacerList");
        op.execute();
        return (Integer[])op.getResult();
    }

    public void finalizeListen(ValueChangeEvent vce) {
        String value=null;
        if(vce.getNewValue().getClass().getName().equals("java.lang.Boolean")){
            value=((Boolean)vce.getNewValue()) ?  "Y" :"N";
        }else
            value=vce.getNewValue().toString();
        
        if(value.equals("Y")){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Warning","After Saving , you can't edit this schedule"));
        }
    }
}
