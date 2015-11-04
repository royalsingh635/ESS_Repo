package trnpstnfueldtlapp.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.model.ADFModelUtils;

import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;

public class TrnpStationFuelDtlBean {
    private String mode = "V";
    private static ADFLogger _log = ADFLogger.createADFLogger(TrnpStationFuelDtlBean.class);
    private RichInputListOfValues stnNmBinding;
    private RichSelectOneChoice fuelTypeBinding;
    private RichInputText fuelQtyBinding;
    private RichInputListOfValues unitBinding;
    private RichInputText fuelRateBinding;
    private RichInputText invcNoBinding;
    private RichInputDate invcDtBinding;
    private RichInputListOfValues currencyNmBinding;
    private RichInputText vhclRreadingBinding;
    private RichInputListOfValues driverNmBinding;
    private RichInputText minAmtBinding;
    private RichInputListOfValues vhclNoBinding;

    public TrnpStationFuelDtlBean() {
    }

    public String addFuelDtlACTION() {
        OperationBinding createOp = ADFBeanUtils.getOperationBinding("CreateInsert");
        createOp.execute();
        mode = "A";
        return null;
    }

    public String editFuelDtlACTION() {
        mode = "A";
        return null;
    }

    public String saveFuelDtlACTION() {
        if (stnNmBinding.getValue() == null || stnNmBinding.getValue().toString().length() == 0) {
            ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.1730"), " ", FacesMessage.SEVERITY_ERROR,
                                           stnNmBinding.getClientId());
            return null;
        }
        if (vhclNoBinding.getValue() == null || vhclNoBinding.getValue().toString().length() == 0) {
            ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.1730"), " ", FacesMessage.SEVERITY_ERROR,
                                           vhclNoBinding.getClientId());
            return null;
        }
        if (fuelTypeBinding.getValue() == null || fuelTypeBinding.getValue().toString().length() == 0) {
            ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.1730"), " ", FacesMessage.SEVERITY_ERROR,
                                           fuelTypeBinding.getClientId());
            return null;
        }
        if (fuelQtyBinding.getValue() == null || fuelQtyBinding.getValue().toString().length() == 0) {
            ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.1730"), " ", FacesMessage.SEVERITY_ERROR,
                                           fuelQtyBinding.getClientId());
            return null;
        }
        if (unitBinding.getValue() == null || unitBinding.getValue().toString().length() == 0) {
            ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.1730"), " ", FacesMessage.SEVERITY_ERROR,
                                           unitBinding.getClientId());
            return null;
        }
        if (fuelRateBinding.getValue() == null || fuelRateBinding.getValue().toString().length() == 0) {
            ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.959"), " ", FacesMessage.SEVERITY_ERROR,
                                           fuelRateBinding.getClientId());
            return null;
        }
        if (invcNoBinding.getValue() == null || invcNoBinding.getValue().toString().length() == 0) {
            ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.1730"), " ", FacesMessage.SEVERITY_ERROR,
                                           invcNoBinding.getClientId());
            return null;
        }
        if (invcDtBinding.getValue() == null || invcDtBinding.getValue().toString().length() == 0) {
            ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.1730"), " ", FacesMessage.SEVERITY_ERROR,
                                           invcDtBinding.getClientId());
            return null;
        }
        if (currencyNmBinding.getValue() == null || currencyNmBinding.getValue().toString().length() == 0) {
            ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.1730"), " ", FacesMessage.SEVERITY_ERROR,
                                           currencyNmBinding.getClientId());
            return null;
        }
        if (vhclRreadingBinding.getValue() == null || vhclRreadingBinding.getValue().toString().length() == 0) {
            ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.959"), " ", FacesMessage.SEVERITY_ERROR,
                                           vhclRreadingBinding.getClientId());
            return null;
        }
        if (driverNmBinding.getValue() == null || driverNmBinding.getValue().toString().length() == 0) {
            ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.1730"), " ", FacesMessage.SEVERITY_ERROR,
                                           driverNmBinding.getClientId());
            return null;
        }

        OperationBinding saveOp = ADFBeanUtils.getOperationBinding("Commit");
        saveOp.execute();
        mode = "V";
        ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.91"), " ", FacesMessage.SEVERITY_INFO);
        return null;

    }

    public String cancelFuelACTION() {
        OperationBinding createOp = ADFBeanUtils.getOperationBinding("Rollback");
        createOp.execute();
        mode = "V";
        return null;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setStnNmBinding(RichInputListOfValues stnNmBinding) {
        this.stnNmBinding = stnNmBinding;
    }

    public RichInputListOfValues getStnNmBinding() {
        return stnNmBinding;
    }


    public void setFuelTypeBinding(RichSelectOneChoice fuelTypeBinding) {
        this.fuelTypeBinding = fuelTypeBinding;
    }

    public RichSelectOneChoice getFuelTypeBinding() {
        return fuelTypeBinding;
    }

    public void setFuelQtyBinding(RichInputText fuelQtyBinding) {
        this.fuelQtyBinding = fuelQtyBinding;
    }

    public RichInputText getFuelQtyBinding() {
        return fuelQtyBinding;
    }

    public void setUnitBinding(RichInputListOfValues unitBinding) {
        this.unitBinding = unitBinding;
    }

    public RichInputListOfValues getUnitBinding() {
        return unitBinding;
    }

    public void setFuelRateBinding(RichInputText fuelRateBinding) {
        this.fuelRateBinding = fuelRateBinding;
    }

    public RichInputText getFuelRateBinding() {
        return fuelRateBinding;
    }

    public void setInvcNoBinding(RichInputText invcNoBinding) {
        this.invcNoBinding = invcNoBinding;
    }

    public RichInputText getInvcNoBinding() {
        return invcNoBinding;
    }

    public void setInvcDtBinding(RichInputDate invcDtBinding) {
        this.invcDtBinding = invcDtBinding;
    }

    public RichInputDate getInvcDtBinding() {
        return invcDtBinding;
    }

    public void setCurrencyNmBinding(RichInputListOfValues currencyNmBinding) {
        this.currencyNmBinding = currencyNmBinding;
    }

    public RichInputListOfValues getCurrencyNmBinding() {
        return currencyNmBinding;
    }

    public void setVhclRreadingBinding(RichInputText vhclRreadingBinding) {
        this.vhclRreadingBinding = vhclRreadingBinding;
    }

    public RichInputText getVhclRreadingBinding() {
        return vhclRreadingBinding;
    }

    public void setDriverNmBinding(RichInputListOfValues driverNmBinding) {
        this.driverNmBinding = driverNmBinding;
    }

    public RichInputListOfValues getDriverNmBinding() {
        return driverNmBinding;
    }

    public void vhclReadingValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Integer read = (Integer) (object);
            if (read <= 0)
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.198"), null));

        }
    }

    public void invcDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            java.sql.Date invcDt = null;
            java.sql.Date sysDt = null;
            try {
                invcDt = ((Timestamp) object).dateValue();
                sysDt = (new Timestamp(System.currentTimeMillis())).dateValue();
                if (invcDt.compareTo(sysDt) > 0) {
                    if (sysDt.toString().equals(invcDt.toString())) {
                        //ok
                    } else {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                       ADFModelUtils.resolvRsrc("MSG.521"), null));
                    }
                }
            } catch (SQLException e) {
                System.out.println("Exception in cast");
            }
        }
    }


    public void fuelRateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number rate = (Number) (object);
            if (rate.compareTo(new Number(0)) <= 0)
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.198"), null));

            if (!ADFBeanUtils.isPrecisionValid(26, 6, rate))
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.1107"),
                                                              null));


        }

    }

    public void invcNoValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0 && stnNmBinding.getValue() != null) {
            OperationBinding opchkDupli = ADFBeanUtils.getOperationBinding("chkDuplicateInvoice");
            opchkDupli.getParamsMap().put("invcNoParam", object.toString());
            opchkDupli.execute();
            if (opchkDupli.getResult() != null && opchkDupli.getResult().toString().equals("Y")) {
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.375"),
                                                              null));
            }
        }
    }

    public void maxAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) throws SQLException {
        if (object != null) {

            Number maxAmt = (Number) (object);

            if (maxAmt.compareTo(new Number(0)) < 0)
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                             ADFModelUtils.resolvRsrc("MSG.198"), null));
            System.out.println("Pass2");
            if (!ADFBeanUtils.isPrecisionValid(26, 6, maxAmt))
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.1107"),
                                                              null));
            System.out.println("Pass3");

            if (minAmtBinding.getValue() != null) {
                Number minAmt = (Number) (minAmtBinding.getValue());
                if (minAmt.compareTo(maxAmt) > 0)
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  ADFModelUtils.resolvRsrc("MSG.2062"),
                                                                  null));
                System.out.println("Pass4");

            }

        }

    }

    public void minAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {

            Number rate = (Number) (object);
            if (rate.compareTo(new Number(0)) < 0)
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.198"), null));

            if (!ADFBeanUtils.isPrecisionValid(26, 6, rate))
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.1107"),
                                                              null));


        }

    }

    public void setMinAmtBinding(RichInputText minAmtBinding) {
        this.minAmtBinding = minAmtBinding;
    }

    public RichInputText getMinAmtBinding() {
        return minAmtBinding;
    }

    public void setVhclNoBinding(RichInputListOfValues vhclNoBinding) {
        this.vhclNoBinding = vhclNoBinding;
    }

    public RichInputListOfValues getVhclNoBinding() {
        return vhclNoBinding;
    }

    public void fuelQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null) {
            Number rate = (Number) (object);
            if (rate.compareTo(new Number(0)) <= 0)
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.198"), null));

            if (!ADFBeanUtils.isPrecisionValid(26, 6, rate))
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.1107"),
                                                              null));


        }


    }
}
