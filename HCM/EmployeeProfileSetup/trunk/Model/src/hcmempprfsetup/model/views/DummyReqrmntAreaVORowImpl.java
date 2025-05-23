package hcmempprfsetup.model.views;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import oracle.jbo.RowSet;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sat Jan 17 16:42:49 IST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class DummyReqrmntAreaVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum
    {
        Selectobjects0,
        TransHoOrgId,
        TransOrgId,
        TransCldId,
        TransSlocId,
        TransRequirementArearName,
        TransAddress,
        TransDefltWreHouse,
        TransIsProduction,
        TransIsServcCentre,
        TransIsCostCentre,
        TransIsTempIssueOnly,
        TransAddressId,
        TransWreHouseId,
        LovWareHouseVO,
        LovAppAddressVo1;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public int index()
        {
            return AttributesEnum.firstIndex() + ordinal();
        }

        public static final int firstIndex()
        {
            return firstIndex;
        }

        public static int count()
        {
            return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
        }

        public static final AttributesEnum[] staticValues()
        {
            if (vals == null)
            {
                vals = AttributesEnum.values();
            }
            return vals;
        }
    }


    public static final int SELECTOBJECTS0 = AttributesEnum.Selectobjects0.index();
    public static final int TRANSHOORGID = AttributesEnum.TransHoOrgId.index();
    public static final int TRANSORGID = AttributesEnum.TransOrgId.index();
    public static final int TRANSCLDID = AttributesEnum.TransCldId.index();
    public static final int TRANSSLOCID = AttributesEnum.TransSlocId.index();
    public static final int TRANSREQUIREMENTAREARNAME = AttributesEnum.TransRequirementArearName.index();
    public static final int TRANSADDRESS = AttributesEnum.TransAddress.index();
    public static final int TRANSDEFLTWREHOUSE = AttributesEnum.TransDefltWreHouse.index();
    public static final int TRANSISPRODUCTION = AttributesEnum.TransIsProduction.index();
    public static final int TRANSISSERVCCENTRE = AttributesEnum.TransIsServcCentre.index();
    public static final int TRANSISCOSTCENTRE = AttributesEnum.TransIsCostCentre.index();
    public static final int TRANSISTEMPISSUEONLY = AttributesEnum.TransIsTempIssueOnly.index();
    public static final int TRANSADDRESSID = AttributesEnum.TransAddressId.index();
    public static final int TRANSWREHOUSEID = AttributesEnum.TransWreHouseId.index();
    public static final int LOVWAREHOUSEVO = AttributesEnum.LovWareHouseVO.index();
    public static final int LOVAPPADDRESSVO1 = AttributesEnum.LovAppAddressVo1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public DummyReqrmntAreaVORowImpl()
    {
    }

    /**
     * Gets the attribute value for the calculated attribute Selectobjects0.
     * @return the Selectobjects0
     */
    public String getSelectobjects0()
    {
        return (String) getAttributeInternal(SELECTOBJECTS0);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Selectobjects0.
     * @param value value to set the  Selectobjects0
     */
    public void setSelectobjects0(String value)
    {
        setAttributeInternal(SELECTOBJECTS0, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransHoOrgId.
     * @return the TransHoOrgId
     */
    public String getTransHoOrgId()
    {
        return (String) resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        //return (String) getAttributeInternal(TRANSHOORGID);
    }


    /**
     * Gets the attribute value for the calculated attribute TransOrgId.
     * @return the TransOrgId
     */
    public String getTransOrgId()
    {
        return (String) resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        //return (String) getAttributeInternal(TRANSORGID);
    }


    /**
     * Gets the attribute value for the calculated attribute TransCldId.
     * @return the TransCldId
     */
    public String getTransCldId()
    {
        return (String) resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        //return (String) getAttributeInternal(TRANSCLDID);
    }


    /**
     * Gets the attribute value for the calculated attribute TransSlocId.
     * @return the TransSlocId
     */
    public Integer getTransSlocId()
    {
        return  Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
       // return (Integer) getAttributeInternal(TRANSSLOCID);
    }


    /**
     * Gets the attribute value for the calculated attribute TransRequirementArearName.
     * @return the TransRequirementArearName
     */
    public String getTransRequirementArearName()
    {
        return (String) getAttributeInternal(TRANSREQUIREMENTAREARNAME);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransRequirementArearName.
     * @param value value to set the  TransRequirementArearName
     */
    public void setTransRequirementArearName(String value)
    {
        setAttributeInternal(TRANSREQUIREMENTAREARNAME, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransAddress.
     * @return the TransAddress
     */
    public String getTransAddress()
    {
        return (String) getAttributeInternal(TRANSADDRESS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransAddress.
     * @param value value to set the  TransAddress
     */
    public void setTransAddress(String value)
    {
        setAttributeInternal(TRANSADDRESS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransDefltWreHouse.
     * @return the TransDefltWreHouse
     */
    public String getTransDefltWreHouse()
    {
        return (String) getAttributeInternal(TRANSDEFLTWREHOUSE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransDefltWreHouse.
     * @param value value to set the  TransDefltWreHouse
     */
    public void setTransDefltWreHouse(String value)
    {
        setAttributeInternal(TRANSDEFLTWREHOUSE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransIsProduction.
     * @return the TransIsProduction
     */
    public String getTransIsProduction()
    {
        return (String) getAttributeInternal(TRANSISPRODUCTION);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransIsProduction.
     * @param value value to set the  TransIsProduction
     */
    public void setTransIsProduction(String value)
    {
        setAttributeInternal(TRANSISPRODUCTION, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransIsServcCentre.
     * @return the TransIsServcCentre
     */
    public String getTransIsServcCentre()
    {
        return (String) getAttributeInternal(TRANSISSERVCCENTRE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransIsServcCentre.
     * @param value value to set the  TransIsServcCentre
     */
    public void setTransIsServcCentre(String value)
    {
        setAttributeInternal(TRANSISSERVCCENTRE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransIsCostCentre.
     * @return the TransIsCostCentre
     */
    public String getTransIsCostCentre()
    {
        return (String) getAttributeInternal(TRANSISCOSTCENTRE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransIsCostCentre.
     * @param value value to set the  TransIsCostCentre
     */
    public void setTransIsCostCentre(String value)
    {
        setAttributeInternal(TRANSISCOSTCENTRE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransIsTempIssueOnly.
     * @return the TransIsTempIssueOnly
     */
    public String getTransIsTempIssueOnly()
    {
        return (String) getAttributeInternal(TRANSISTEMPISSUEONLY);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransIsTempIssueOnly.
     * @param value value to set the  TransIsTempIssueOnly
     */
    public void setTransIsTempIssueOnly(String value)
    {
        setAttributeInternal(TRANSISTEMPISSUEONLY, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransAddressId.
     * @return the TransAddressId
     */
    public String getTransAddressId()
    {
        return (String) getAttributeInternal(TRANSADDRESSID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransAddressId.
     * @param value value to set the  TransAddressId
     */
    public void setTransAddressId(String value)
    {
        setAttributeInternal(TRANSADDRESSID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransWreHouseId.
     * @return the TransWreHouseId
     */
    public String getTransWreHouseId()
    {
        return (String) getAttributeInternal(TRANSWREHOUSEID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransWreHouseId.
     * @param value value to set the  TransWreHouseId
     */
    public void setTransWreHouseId(String value)
    {
        setAttributeInternal(TRANSWREHOUSEID, value);
    }


    /**
     * Gets the view accessor <code>RowSet</code> LovWareHouseVO.
     */
    public RowSet getLovWareHouseVO()
    {
        return (RowSet) getAttributeInternal(LOVWAREHOUSEVO);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovAppAddressVo1.
     */
    public RowSet getLovAppAddressVo1()
    {
        return (RowSet) getAttributeInternal(LOVAPPADDRESSVO1);
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

