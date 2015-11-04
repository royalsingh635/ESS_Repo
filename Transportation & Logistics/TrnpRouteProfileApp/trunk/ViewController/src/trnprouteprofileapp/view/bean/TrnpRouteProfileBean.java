package trnprouteprofileapp.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.model.ADFModelUtils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.domain.Number;

import org.apache.myfaces.trinidad.context.RequestContext;

public class TrnpRouteProfileBean {


    private RichPopup addLocationPopUp;
    private RichOutputText bindDeparturePoint;
    private RichOutputText bindArrivalPoint;
    private RichPopup addOtherChargesPopUp;
    private RichInputListOfValues bindDeparturePnt;
    private RichInputListOfValues arrivalPntBind;
    private RichInputListOfValues bindArrivalAlias;
    private RichInputListOfValues bindDepartureAlias;
    private RichTable ocTableBind;

    public TrnpRouteProfileBean() {

        RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "V");
    }

    public void addRow(ActionEvent actionEvent) {

        RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "E");
        ADFBeanUtils.findOperation("CreateInsert").execute();
        //BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getTxnId").execute();

        ADFBeanUtils.findOperation("setParams").execute();
    }

    public void saveRow(ActionEvent actionEvent) {
        System.out.println("Reached saveRow");
        RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "V");

        ADFBeanUtils.findOperation("Commit").execute();
    }

    public void deleteRow(ActionEvent actionEvent) {

        ADFBeanUtils.findOperation("Delete3").execute();

        //RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "E");

    }

    public void cancelRow(ActionEvent actionEvent) {

        RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "V");
        ADFBeanUtils.findOperation("Rollback").execute();
    }

    public void editRow(ActionEvent actionEvent) {

        OperationBinding rtExists = ADFBeanUtils.findOperation("chkRouteExists");
        rtExists.execute();
        Boolean result = (Boolean) rtExists.getResult();

        if (result != null) {

            if (result) {

                System.out.println("Cannot edit ! Route is in use in Loading Order !!");
                ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.1980"), ADFModelUtils.resolvRsrc("MSG.1981"),
                                               FacesMessage.SEVERITY_INFO, null);

            } else {
                RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "E");
            }

        }

    }


    public void addChildRow(ActionEvent actionEvent) {

        RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "E");
        System.out.println("Hi i am in Create:::::");
        //ADFBeanUtils.findOperation("CreateInsert1").execute();
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("SlsTrnpRoutePrfDtl4Iterator");
        RowSetIterator rsi = dciter.getRowSetIterator();
        Row lastRow = rsi.last();
        int lastRowIndex = rsi.getRangeIndexOf(lastRow);
        Row newRow = rsi.createRow();
        newRow.setNewRowState(Row.STATUS_INITIALIZED);
        rsi.insertRowAtRangeIndex(lastRowIndex + 1, newRow);
                rsi.setCurrentRow(newRow);
        ADFBeanUtils.findOperation("createSlNo").execute();
    }

    public void deleteRouteOC(ActionEvent actionEvent) {

        ADFBeanUtils.findOperation("DeleteOC").execute();

    }

    public String saveButtonAction() {
        ADFBeanUtils.findOperation("Commit").execute();
        return null;
    }

    public void nameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {


        if (object != null) {
            String name = (String) object;
            System.out.println("Route name in Bean is : " + name);

            System.out.println("----------------");
            OperationBinding operationBinding = ADFBeanUtils.findOperation("validateName");
            operationBinding.getParamsMap().put("routenm", name);
            operationBinding.execute();
            Boolean result = (Boolean) operationBinding.getResult();

            System.out.println(" Result in Bean : " + result);

            if (result != null) {
                if (result) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  ADFModelUtils.resolvRsrc("MSG.375"), null));

                }
            }
        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.508"),
                                                          null));
        }

    }

    public void distanceValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null) {
            OperationBinding operationBinding = ADFBeanUtils.findOperation("preValidateDistance");
            operationBinding.execute();
            Boolean result = (Boolean) operationBinding.getResult();

            System.out.println(" Result in Bean : " + result);

            if (result != null) {

                if (result) {

                    if (object != null) {

                        Integer dist = (Integer) object;

                        if (dist <= 0) {
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                          ADFModelUtils.resolvRsrc("MSG.198"), null));
                        }


                    } else {
                        /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      ADFModelUtils.resolvRsrc("MSG.508"), null)); */
                    }

                } else {
                }
            }

        } else {
            System.out.println("MSG.508 Distance !!");
            /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          ADFModelUtils.resolvRsrc("MSG.508"), null)); */
        }
    }

    public void fuelValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {

            OperationBinding operationBinding = ADFBeanUtils.findOperation("preValidateDistance");
            operationBinding.execute();
            Boolean result = (Boolean) operationBinding.getResult();

            System.out.println(" Result in Bean : " + result);

            if (result != null) {

                if (result) {


                    Integer fuel = (Integer) object;

                    if (fuel <= 0) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      ADFModelUtils.resolvRsrc("MSG.198"), null));
                    }
                }
            }
        } else {
            System.out.println("MSG.508 Fuel !!");
            /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.508"),
                                                          null)); */
        }

    }

    public void searchRoutePrf(ActionEvent actionEvent) {

        ADFBeanUtils.findOperation("searchRoute").execute();
    }

    public void resetSearchAL(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("resetSearch").execute();
    }

    public void deleteCurrentRow(ActionEvent actionEvent) {

        OperationBinding binding = ADFBeanUtils.findOperation("deleteChildRow");
        binding.execute();
        //ADFBeanUtils.findOperation("Delete").execute();
    }

    public void addLocationAL(ActionEvent actionEvent) {

        ADFBeanUtils.findOperation("CreateInsert3").execute();
        //ADFBeanUtils.findOperation("setLocationParams").execute();
        ADFBeanUtils.showPopup(addLocationPopUp, true);
    }

    public void setAddLocationPopUp(RichPopup addLocationPopUp) {
        this.addLocationPopUp = addLocationPopUp;
    }

    public RichPopup getAddLocationPopUp() {
        return addLocationPopUp;
    }

    public void arrivalValiator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        AdfFacesContext.getCurrentInstance().addPartialTarget(bindDeparturePnt);
        uIComponent.processUpdates(facesContext);
        
        if (object != null) {

            System.out.println("Bind Departure Point :" + bindDeparturePoint.getValue());

            if (bindDeparturePoint.getValue() != null) {

                String arrivalpnt = (String) object;
                System.out.println("Station name in Bean is : " + arrivalpnt);

                System.out.println("----------------");

                OperationBinding operation = ADFBeanUtils.findOperation("getLocationId");
                operation.getParamsMap().put("locNm", arrivalpnt);
                operation.execute();
                String arrId = (String) operation.getResult();
                System.out.println("Arrival Point Id :" + arrId);

                OperationBinding binding = ADFBeanUtils.findOperation("validateDuplicateRoute");
                binding.getParamsMap().put("arrivalPnt", arrId);
                binding.getParamsMap().put("departurePnt", bindDeparturePoint.getValue());
                binding.execute();
                Boolean duplicate = (Boolean) binding.getResult();

                System.out.println(" Result in Bean : " + duplicate);

                if (duplicate != null) {
                    if (duplicate) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      ADFModelUtils.resolvRsrc("MSG.1986"), null));

                    }
                }

                OperationBinding operationBinding = ADFBeanUtils.findOperation("validateArrivalPoint");
                operationBinding.getParamsMap().put("arrival", arrId);
                operationBinding.execute();
                Boolean result = (Boolean) operationBinding.getResult();

                System.out.println(" Result in Bean : " + result);

                if (result != null) {
                    if (result) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      ADFModelUtils.resolvRsrc("MSG.1985"),
                                                                      null));

                    }
                }

            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.1987"), null));
            }


        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          ADFModelUtils.resolvRsrc("MSG.1982"), null));
        }

    }

    public void departureValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        AdfFacesContext.getCurrentInstance().addPartialTarget(arrivalPntBind);
        uIComponent.processUpdates(facesContext);
        
        if (object != null) {

            System.out.println("Bind Arrival Point :" + bindArrivalPoint.getValue());

            if (bindArrivalPoint.getValue() != null) {

                String departurepnt = (String) object;
                System.out.println("Station name in Bean is : " + departurepnt);

                System.out.println("----------------");

                OperationBinding operation = ADFBeanUtils.findOperation("getLocationId");
                operation.getParamsMap().put("locNm", departurepnt);
                operation.execute();
                String deprtId = (String) operation.getResult();
                System.out.println("Departure Point Id :" + deprtId);

                OperationBinding binding = ADFBeanUtils.findOperation("validateDuplicateRoute");
                binding.getParamsMap().put("arrivalPnt", bindArrivalPoint.getValue());
                binding.getParamsMap().put("departurePnt", deprtId);
                binding.execute();
                Boolean duplicate = (Boolean) binding.getResult();

                System.out.println(" Result in Bean : " + duplicate);

                if (duplicate != null) {
                    if (duplicate) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      ADFModelUtils.resolvRsrc("MSG.1986"), null));

                    } else {
                        //throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Name aleady exists !!",null));
                    }
                }

                OperationBinding operationBinding = ADFBeanUtils.findOperation("validateDeparturePoint");
                operationBinding.getParamsMap().put("departure", departurepnt);
                operationBinding.execute();
                Boolean result = (Boolean) operationBinding.getResult();

                System.out.println(" Result in Bean : " + result);

                if (result != null) {
                    if (result) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      ADFModelUtils.resolvRsrc("MSG.1985"),
                                                                      null));
                    }
                }
            }
        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          ADFModelUtils.resolvRsrc("MSG.1983"), null));
        }

    }


    public void arrivalAliasValiator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        AdfFacesContext.getCurrentInstance().addPartialTarget(bindDepartureAlias);
        uIComponent.processUpdates(facesContext);
        
        if (object != null) {
            String arrivalalias = object.toString();
            System.out.println("Station name in Bean is : " + arrivalalias);

            System.out.println("----------------");
            OperationBinding operationBinding = ADFBeanUtils.findOperation("validateArrivalAlias");
            operationBinding.getParamsMap().put("arrivalals", arrivalalias);
            operationBinding.execute();
            Boolean result = (Boolean) operationBinding.getResult();

            System.out.println(" Result in Bean : " + result);

            if (result != null) {
                if (result) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  ADFModelUtils.resolvRsrc("MSG.1984"),
                                                                  null));
                }
            }
        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          ADFModelUtils.resolvRsrc("MSG.508"), null));
        }
    }

    public void addLocationDL(DialogEvent dialogEvent) {
        System.out.println(" dialogEvent.getOutcome().name() " + dialogEvent.getOutcome().name());

        if (dialogEvent.getOutcome().name().equalsIgnoreCase("yes")) {
        ADFBeanUtils.findOperation("createLocSlNo").execute();
            System.out.println("popup Ok");

        } else if (dialogEvent.getOutcome().name().equalsIgnoreCase("no")) {
            System.out.println("popup cancel");
            OperationBinding opDelete = ADFBeanUtils.findOperation("Delete2");
            opDelete.execute();
        }
    }

    public void addOtherChargesAL(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("CreateInsertOC").execute();

        ADFBeanUtils.findOperation("addOcNewRow").execute();
    }

    public void popupCancelListener(PopupCanceledEvent popupCanceledEvent) {
        System.out.println(" pou main cancel");
        System.out.println("inside the Dl " + popupCanceledEvent);
        ADFBeanUtils.findOperation("Delete2").execute();
    }

    public void locNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null) {
            String locname = (String) object;
            System.out.println("Location name in Bean is : " + locname);

            System.out.println("----------------");
            OperationBinding operationBinding = ADFBeanUtils.findOperation("validateLocName");
            operationBinding.getParamsMap().put("locname", locname);
            operationBinding.execute();
            Boolean result = (Boolean) operationBinding.getResult();

            System.out.println(" Result in Bean : " + result);

            if (result != null) {
                if (result) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  ADFModelUtils.resolvRsrc("MSG.375"), null));

                }
            }
        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.508"),
                                                          null));
        }
    }

    public void setBindDeparturePoint(RichOutputText bindDeparturePoint) {
        this.bindDeparturePoint = bindDeparturePoint;
    }

    public RichOutputText getBindDeparturePoint() {
        return bindDeparturePoint;
    }

    public void setBindArrivalPoint(RichOutputText bindArrivalPoint) {
        this.bindArrivalPoint = bindArrivalPoint;
    }

    public RichOutputText getBindArrivalPoint() {
        return bindArrivalPoint;
    }

    public void addOtherCharges(ActionEvent actionEvent) {

        ADFBeanUtils.findOperation("setCoaBindings").execute();

        ADFBeanUtils.showPopup(addOtherChargesPopUp, true);

    }

    public void setAddOtherChargesPopUp(RichPopup addOtherChargesPopUp) {
        this.addOtherChargesPopUp = addOtherChargesPopUp;
    }

    public RichPopup getAddOtherChargesPopUp() {
        return addOtherChargesPopUp;
    }

    public void amountValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        
        uIComponent.processUpdates(facesContext);
        AdfFacesContext.getCurrentInstance().addPartialTarget(ocTableBind);
        
        if (object != null) {
                System.out.println("OC Amount Inside not null condition..");
                
            Number val = new Number(0);
            val = (Number) object;
            if (!(ADFBeanUtils.isPrecisionValid(26, 6, val))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.1107"),
                                                              null));
            }
            if (val.compareTo(0) <= 0) {
                System.out.println("OC Amount Inside less than zero condition..");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.1063"), null));
            }
        }
        else
        {
            System.out.println("OC Amount Inside null condition .. ");    
        }
    }

    public void departureAliasVCL(ValueChangeEvent valueChangeEvent) {

        AdfFacesContext.getCurrentInstance().addPartialTarget(bindDepartureAlias);
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
    }
    
    public void changeArrivalPointVCL(ValueChangeEvent vce) {
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindDeparturePnt);
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
        
        if (vce.getNewValue() != null) {
            OperationBinding ob = ADFBeanUtils.findOperation("changeArrivalPoint");
            ob.getParamsMap().put("locName", vce.getNewValue());
            ob.execute();
        }
    }

    public void distanceUnitValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        
        System.out.println("Distance unit value in validator :: "+object);
        
        String distUnit = (String) object;
        
        if(distUnit != null && distUnit.length() > 0)
        {
        }
        else
        {
            //throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.508"),null));
        }

    }

    public void fuelUnitValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        
        System.out.println("Fuel unit value in validator :: "+object);
        
        String fuelUnit = (String) object;
        
        if(fuelUnit != null && fuelUnit.length() > 0)
        {
        }
        else
        {
            //throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.508"),null));
        }

    }

    public void setBindDeparturePnt(RichInputListOfValues bindDeparturePnt) {
        this.bindDeparturePnt = bindDeparturePnt;
    }

    public RichInputListOfValues getBindDeparturePnt() {
        return bindDeparturePnt;
    }

    public void setArrivalPntBind(RichInputListOfValues arrivalPntBind) {
        this.arrivalPntBind = arrivalPntBind;
    }

    public RichInputListOfValues getArrivalPntBind() {
        return arrivalPntBind;
    }

    public void setBindArrivalAlias(RichInputListOfValues bindArrivalAlias) {
        this.bindArrivalAlias = bindArrivalAlias;
    }

    public RichInputListOfValues getBindArrivalAlias() {
        return bindArrivalAlias;
    }

    public void setBindDepartureAlias(RichInputListOfValues bindDepartureAlias) {
        this.bindDepartureAlias = bindDepartureAlias;
    }

    public RichInputListOfValues getBindDepartureAlias() {
        return bindDepartureAlias;
    }

    public void setOcTableBind(RichTable ocTableBind) {
        this.ocTableBind = ocTableBind;
    }

    public RichTable getOcTableBind() {
        return ocTableBind;
    }
}
