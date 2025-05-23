package hcmsalaryincr.model.views;

import hcmsalaryincr.model.entities.HCMIncrmntGrpSumEOImpl;

import hcmsalaryincr.model.services.HCMSalaryIncrmtAMImpl;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import oracle.jbo.Row;
import oracle.jbo.RowIterator;
import oracle.jbo.RowSet;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Jun 16 12:57:05 IST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class HCMIncrementGrpSumVORowImpl extends ViewRowImpl
{


    public static final int ENTITY_HCMINCRMNTGRPSUMEO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum
    {
        CldId,
        DocId,
        GrpId,
        HoOrgId,
        OrgId,
        SalAmt,
        SalId,
        SlocId,
        UsrIdCreate,
        UsrIdCreateDt,
        UsrIdMod,
        UsrIdModDt,
        ValidEndDt,
        ValidStrtDt,
        TransSalNm,
        DeletChk,
        transGrpNm,
        TransSalName,
        SwitchLov,
        SalType,
        transSalType,
        transIncrStatus,
        HcmIncrGrpSummReff,
        HcmGrpComponentForAddVO1,
        HCMGrpCompForDeleteVO1,
        DummyIncrmntTypeVO1,
        AppSecUserVO1;
        static AttributesEnum[] vals = null;
        ;
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


    public static final int CLDID = AttributesEnum.CldId.index();
    public static final int DOCID = AttributesEnum.DocId.index();
    public static final int GRPID = AttributesEnum.GrpId.index();
    public static final int HOORGID = AttributesEnum.HoOrgId.index();
    public static final int ORGID = AttributesEnum.OrgId.index();
    public static final int SALAMT = AttributesEnum.SalAmt.index();
    public static final int SALID = AttributesEnum.SalId.index();
    public static final int SLOCID = AttributesEnum.SlocId.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();
    public static final int USRIDCREATEDT = AttributesEnum.UsrIdCreateDt.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int USRIDMODDT = AttributesEnum.UsrIdModDt.index();
    public static final int VALIDENDDT = AttributesEnum.ValidEndDt.index();
    public static final int VALIDSTRTDT = AttributesEnum.ValidStrtDt.index();
    public static final int TRANSSALNM = AttributesEnum.TransSalNm.index();
    public static final int DELETCHK = AttributesEnum.DeletChk.index();
    public static final int TRANSGRPNM = AttributesEnum.transGrpNm.index();
    public static final int TRANSSALNAME = AttributesEnum.TransSalName.index();
    public static final int SWITCHLOV = AttributesEnum.SwitchLov.index();
    public static final int SALTYPE = AttributesEnum.SalType.index();
    public static final int TRANSSALTYPE = AttributesEnum.transSalType.index();
    public static final int TRANSINCRSTATUS = AttributesEnum.transIncrStatus.index();
    public static final int HCMINCRGRPSUMMREFF = AttributesEnum.HcmIncrGrpSummReff.index();
    public static final int HCMGRPCOMPONENTFORADDVO1 = AttributesEnum.HcmGrpComponentForAddVO1.index();
    public static final int HCMGRPCOMPFORDELETEVO1 = AttributesEnum.HCMGrpCompForDeleteVO1.index();
    public static final int DUMMYINCRMNTTYPEVO1 = AttributesEnum.DummyIncrmntTypeVO1.index();
    public static final int APPSECUSERVO1 = AttributesEnum.AppSecUserVO1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public HCMIncrementGrpSumVORowImpl()
    {
    }

    /**
     * Gets HCMIncrmntGrpSumEO entity object.
     * @return the HCMIncrmntGrpSumEO
     */
    public HCMIncrmntGrpSumEOImpl getHCMIncrmntGrpSumEO()
    {
        return (HCMIncrmntGrpSumEOImpl) getEntity(ENTITY_HCMINCRMNTGRPSUMEO);
    }

    /**
     * Gets the attribute value for CLD_ID using the alias name CldId.
     * @return the CLD_ID
     */
    public String getCldId()
    {
        return (String) getAttributeInternal(CLDID);
    }

    /**
     * Sets <code>value</code> as attribute value for CLD_ID using the alias name CldId.
     * @param value value to set the CLD_ID
     */
    public void setCldId(String value)
    {
        setAttributeInternal(CLDID, value);
    }

    /**
     * Gets the attribute value for DOC_ID using the alias name DocId.
     * @return the DOC_ID
     */
    public String getDocId()
    {
        return (String) getAttributeInternal(DOCID);
    }

    /**
     * Sets <code>value</code> as attribute value for DOC_ID using the alias name DocId.
     * @param value value to set the DOC_ID
     */
    public void setDocId(String value)
    {
        setAttributeInternal(DOCID, value);
    }

    /**
     * Gets the attribute value for GRP_ID using the alias name GrpId.
     * @return the GRP_ID
     */
    public String getGrpId()
    {
        return (String) getAttributeInternal(GRPID);
    }

    /**
     * Sets <code>value</code> as attribute value for GRP_ID using the alias name GrpId.
     * @param value value to set the GRP_ID
     */
    public void setGrpId(String value)
    {
        setAttributeInternal(GRPID, value);
    }

    /**
     * Gets the attribute value for HO_ORG_ID using the alias name HoOrgId.
     * @return the HO_ORG_ID
     */
    public String getHoOrgId()
    {
        return (String) getAttributeInternal(HOORGID);
    }

    /**
     * Sets <code>value</code> as attribute value for HO_ORG_ID using the alias name HoOrgId.
     * @param value value to set the HO_ORG_ID
     */
    public void setHoOrgId(String value)
    {
        setAttributeInternal(HOORGID, value);
    }

    /**
     * Gets the attribute value for ORG_ID using the alias name OrgId.
     * @return the ORG_ID
     */
    public String getOrgId()
    {
        return (String) getAttributeInternal(ORGID);
    }

    /**
     * Sets <code>value</code> as attribute value for ORG_ID using the alias name OrgId.
     * @param value value to set the ORG_ID
     */
    public void setOrgId(String value)
    {
        setAttributeInternal(ORGID, value);
    }

    /**
     * Gets the attribute value for SAL_AMT using the alias name SalAmt.
     * @return the SAL_AMT
     */
    public Number getSalAmt()
    {
        return (Number) getAttributeInternal(SALAMT);
    }

    /**
     * Sets <code>value</code> as attribute value for SAL_AMT using the alias name SalAmt.
     * @param value value to set the SAL_AMT
     */
    public void setSalAmt(Number value)
    {
        setAttributeInternal(SALAMT, value);
    }

    /**
     * Gets the attribute value for SAL_ID using the alias name SalId.
     * @return the SAL_ID
     */
    public String getSalId()
    {
        return (String) getAttributeInternal(SALID);
    }

    /**
     * Sets <code>value</code> as attribute value for SAL_ID using the alias name SalId.
     * @param value value to set the SAL_ID
     */
    public void setSalId(String value)
    {
        HCMSalaryIncrmtAMImpl am = (HCMSalaryIncrmtAMImpl) this.getApplicationModule();
        if (value != null || value != "")
        {
            if (am.chkDuplicateCompFrGroup(value).equals("N"))
            {
                setAttributeInternal(SALID, value);
            }
            else
            {
                showFacesMessage("Duplicate salary component !!", "E", false, "V");
            }

        }

    }

    /**
     * Gets the attribute value for SLOC_ID using the alias name SlocId.
     * @return the SLOC_ID
     */
    public Integer getSlocId()
    {
        return (Integer) getAttributeInternal(SLOCID);
    }

    /**
     * Sets <code>value</code> as attribute value for SLOC_ID using the alias name SlocId.
     * @param value value to set the SLOC_ID
     */
    public void setSlocId(Integer value)
    {
        setAttributeInternal(SLOCID, value);
    }

    /**
     * Gets the attribute value for USR_ID_CREATE using the alias name UsrIdCreate.
     * @return the USR_ID_CREATE
     */
    public Integer getUsrIdCreate()
    {
        return (Integer) getAttributeInternal(USRIDCREATE);
    }

    /**
     * Sets <code>value</code> as attribute value for USR_ID_CREATE using the alias name UsrIdCreate.
     * @param value value to set the USR_ID_CREATE
     */
    public void setUsrIdCreate(Integer value)
    {
        setAttributeInternal(USRIDCREATE, value);
    }

    /**
     * Gets the attribute value for USR_ID_CREATE_DT using the alias name UsrIdCreateDt.
     * @return the USR_ID_CREATE_DT
     */
    public Timestamp getUsrIdCreateDt()
    {
        return (Timestamp) getAttributeInternal(USRIDCREATEDT);
    }

    /**
     * Sets <code>value</code> as attribute value for USR_ID_CREATE_DT using the alias name UsrIdCreateDt.
     * @param value value to set the USR_ID_CREATE_DT
     */
    public void setUsrIdCreateDt(Timestamp value)
    {
        setAttributeInternal(USRIDCREATEDT, value);
    }

    /**
     * Gets the attribute value for USR_ID_MOD using the alias name UsrIdMod.
     * @return the USR_ID_MOD
     */
    public Integer getUsrIdMod()
    {
        return (Integer) getAttributeInternal(USRIDMOD);
    }

    /**
     * Sets <code>value</code> as attribute value for USR_ID_MOD using the alias name UsrIdMod.
     * @param value value to set the USR_ID_MOD
     */
    public void setUsrIdMod(Integer value)
    {
        setAttributeInternal(USRIDMOD, value);
    }

    /**
     * Gets the attribute value for USR_ID_MOD_DT using the alias name UsrIdModDt.
     * @return the USR_ID_MOD_DT
     */
    public Timestamp getUsrIdModDt()
    {
        return (Timestamp) getAttributeInternal(USRIDMODDT);
    }

    /**
     * Sets <code>value</code> as attribute value for USR_ID_MOD_DT using the alias name UsrIdModDt.
     * @param value value to set the USR_ID_MOD_DT
     */
    public void setUsrIdModDt(Timestamp value)
    {
        setAttributeInternal(USRIDMODDT, value);
    }

    /**
     * Gets the attribute value for VALID_END_DT using the alias name ValidEndDt.
     * @return the VALID_END_DT
     */
    public Timestamp getValidEndDt()
    {
        return (Timestamp) getAttributeInternal(VALIDENDDT);
    }

    /**
     * Sets <code>value</code> as attribute value for VALID_END_DT using the alias name ValidEndDt.
     * @param value value to set the VALID_END_DT
     */
    public void setValidEndDt(Timestamp value)
    {
        setAttributeInternal(VALIDENDDT, value);
    }

    /**
     * Gets the attribute value for VALID_STRT_DT using the alias name ValidStrtDt.
     * @return the VALID_STRT_DT
     */
    public Timestamp getValidStrtDt()
    {
        return (Timestamp) getAttributeInternal(VALIDSTRTDT);
    }

    /**
     * Sets <code>value</code> as attribute value for VALID_STRT_DT using the alias name ValidStrtDt.
     * @param value value to set the VALID_STRT_DT
     */
    public void setValidStrtDt(Timestamp value)
    {
        setAttributeInternal(VALIDSTRTDT, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransSalNm.
     * @return the TransSalNm
     */
    public String getTransSalNm()
    {
        String sal_desc = "";
        HCMSalaryIncrmtAMImpl am = (HCMSalaryIncrmtAMImpl) this.getApplicationModule();
        ViewObjectImpl vo = am.getLovOrgHcmSalary1();
        vo.setNamedWhereClauseParam("bindCldId", this.getCldId());
        vo.setNamedWhereClauseParam("bindHoOrgId", this.getHoOrgId());
        vo.setNamedWhereClauseParam("BindOrgId", this.getOrgId());
        vo.setNamedWhereClauseParam("bindSlocId", this.getSlocId());
        vo.executeQuery();
        Row[] r = vo.getFilteredRows("SalId", this.getSalId());
        if (r != null)
            if (r.length > 0)
            {
                sal_desc = (String) r[0].getAttribute("ParamDesc");
                return sal_desc;
            }
        return (String) getAttributeInternal(TRANSSALNM);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransSalNm.
     * @param value value to set the  TransSalNm
     */
    public void setTransSalNm(String value)
    {
        setAttributeInternal(TRANSSALNM, value);
    }

    /**
     * Gets the attribute value for DELET_CHK using the alias name DeletChk.
     * @return the DELET_CHK
     */
    public String getDeletChk()
    {
        return (String) getAttributeInternal(DELETCHK);
    }

    /**
     * Sets <code>value</code> as attribute value for DELET_CHK using the alias name DeletChk.
     * @param value value to set the DELET_CHK
     */
    public void setDeletChk(String value)
    {
        setAttributeInternal(DELETCHK, value);
    }

    /**
     * Gets the attribute value for the calculated attribute transGrpNm.
     * @return the transGrpNm
     */
    public String gettransGrpNm()
    {
        String grpNm = "";
        HCMSalaryIncrmtAMImpl am = (HCMSalaryIncrmtAMImpl) this.getApplicationModule();
        ViewObjectImpl hcmSetup = am.getLovHcmSetupVO1();
        hcmSetup.setNamedWhereClauseParam("bindCldId", this.getCldId());
        hcmSetup.setNamedWhereClauseParam("bindHoOrgId", this.getHoOrgId());
        hcmSetup.setNamedWhereClauseParam("bindOrgId", this.getOrgId());
        hcmSetup.setNamedWhereClauseParam("bindSlocId", this.getSlocId());
        hcmSetup.executeQuery();
        Row[] r = hcmSetup.getFilteredRows("ParamId", this.getGrpId());
        if (r.length > 0)
        {
            grpNm = (String) r[0].getAttribute("ParamDesc");
            return grpNm;
        }

        return (String) getAttributeInternal(TRANSGRPNM);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute transGrpNm.
     * @param value value to set the  transGrpNm
     */
    public void settransGrpNm(String value)
    {
        setAttributeInternal(TRANSGRPNM, value);
    }


    /**
     * Gets the attribute value for the calculated attribute TransSalName.
     * @return the TransSalName
     */
    public String getTransSalName()
    {
        String sal_desc = "";
        HCMSalaryIncrmtAMImpl am = (HCMSalaryIncrmtAMImpl) this.getApplicationModule();
        ViewObjectImpl vo = am.getLovOrgHcmSalary1();
        vo.setNamedWhereClauseParam("bindCldId", this.getCldId());
        vo.setNamedWhereClauseParam("bindHoOrgId", this.getHoOrgId());
        vo.setNamedWhereClauseParam("BindOrgId", this.getOrgId());
        vo.setNamedWhereClauseParam("bindSlocId", this.getSlocId());
        vo.executeQuery();
        Row[] r = vo.getFilteredRows("SalId", this.getSalId());
        if (r != null)
            if (r.length > 0)
            {
                sal_desc = (String) r[0].getAttribute("ParamDesc");
                return sal_desc;
            }
        return (String) getAttributeInternal(TRANSSALNAME);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransSalName.
     * @param value value to set the  TransSalName
     */
    public void setTransSalName(String value)
    {
        setAttributeInternal(TRANSSALNAME, value);
    }

    /**
     * Gets the attribute value for the calculated attribute SwitchLov.
     * @return the SwitchLov
     */
    public String getSwitchLov()
    {
        return (String) getAttributeInternal(SWITCHLOV);
    }

    /**
     * Gets the attribute value for SAL_TYPE using the alias name SalType.
     * @return the SAL_TYPE
     */
    public String getSalType()
    {
        return (String) getAttributeInternal(SALTYPE);
    }

    /**
     * Sets <code>value</code> as attribute value for SAL_TYPE using the alias name SalType.
     * @param value value to set the SAL_TYPE
     */
    public void setSalType(String value)
    {
        setAttributeInternal(SALTYPE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute transSalType.
     * @return the transSalType
     */
    public String gettransSalType()
    {
        if (getSalType() != null)
        {
            if (getSalType().equals("P"))
            {
                return "Percent";
            }
            else if (getSalType().equals("A"))
            {
                return "Amount";
            }
        }
        return (String) getAttributeInternal(TRANSSALTYPE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute transSalType.
     * @param value value to set the  transSalType
     */
    public void settransSalType(String value)
    {
        setAttributeInternal(TRANSSALTYPE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute transIncrStatus.
     * @return the transIncrStatus
     */
    public String gettransIncrStatus()
    {
        Integer incrStat = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_GET_INCR_STAT}"));
        if (incrStat != null)
        {
            if (incrStat.equals(45))
            {
                return "Pending";
            }
            if (incrStat.equals(46))
            {
                return "Forwarded";
            }
            if (incrStat.equals(47))
            {
                return "Approved";
            }
        }
        return (String) getAttributeInternal(TRANSINCRSTATUS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute transIncrStatus.
     * @param value value to set the  transIncrStatus
     */
    public void settransIncrStatus(String value)
    {
        setAttributeInternal(TRANSINCRSTATUS, value);
    }

    /**
     * Gets the associated <code>RowIterator</code> using master-detail link HcmIncrGrpSummReff.
     */
    public RowIterator getHcmIncrGrpSummReff()
    {
        return (RowIterator) getAttributeInternal(HCMINCRGRPSUMMREFF);
    }

    /**
     * Gets the view accessor <code>RowSet</code> HcmGrpComponentForAddVO1.
     */
    public RowSet getHcmGrpComponentForAddVO1()
    {
        return (RowSet) getAttributeInternal(HCMGRPCOMPONENTFORADDVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> HCMGrpCompForDeleteVO1.
     */
    public RowSet getHCMGrpCompForDeleteVO1()
    {
        return (RowSet) getAttributeInternal(HCMGRPCOMPFORDELETEVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> DummyIncrmntTypeVO1.
     */
    public RowSet getDummyIncrmntTypeVO1()
    {
        return (RowSet) getAttributeInternal(DUMMYINCRMNTTYPEVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> AppSecUserVO1.
     */
    public RowSet getAppSecUserVO1()
    {
        return (RowSet) getAttributeInternal(APPSECUSERVO1);
    }

    public void showFacesMessage(String mesg, String sev, Boolean chk, String typFlg)
    {
        FacesMessage message = new FacesMessage(mesg);
        if (chk == true)
        {
            String msg = (String) resolvEl("#{bundle['" + mesg + "']}");
            message = new FacesMessage(msg);
        }
        if (sev.equalsIgnoreCase("E"))
        {
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
        }
        else if (sev.equalsIgnoreCase("W"))
        {
            message.setSeverity(FacesMessage.SEVERITY_WARN);
        }
        else if (sev.equalsIgnoreCase("I"))
        {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
        }
        else
        {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
        }
        if (typFlg.equals("F"))
        {
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        else if (typFlg.equals("V"))
        {
            throw new ValidatorException(message);
        }
    }

    public String resolvEl(String data)
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String msg = null;
        if (valueExp.getValue(elContext) != null)
            msg = valueExp.getValue(elContext).toString();
        return msg;
    }
}

