package appitemprofile.model.services.client;

import appitemprofile.model.services.common.ItemProfileAM;

import oracle.jbo.client.remote.ApplicationModuleImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Jan 06 11:21:48 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class ItemProfileAMClient extends ApplicationModuleImpl implements ItemProfileAM {
    /**
     * This is the default constructor (do not remove).
     */
    public ItemProfileAMClient() {
    }


    public Boolean isItemNameDuplicate(String itemName) {
        Object _ret = this.riInvokeExportedMethod(this,"isItemNameDuplicate",new String [] {"java.lang.String"},new Object[] {itemName});
        return (Boolean)_ret;
    }


    public void addItemAttribute() {
        Object _ret = this.riInvokeExportedMethod(this,"addItemAttribute",null,null);
        return;
    }

    public void addItemAttribute(String itmAttType, String itmAttValue) {
        Object _ret =
            this.riInvokeExportedMethod(this,"addItemAttribute",new String [] {"java.lang.String","java.lang.String"},new Object[] {itmAttType, itmAttValue});
        return;
    }

    public void defaultItmSearchView() {
        Object _ret = this.riInvokeExportedMethod(this,"defaultItmSearchView",null,null);
        return;
    }

    public void doFilterAppGrpViewLov() {
        Object _ret = this.riInvokeExportedMethod(this,"doFilterAppGrpViewLov",null,null);
        return;
    }

    public void executeViewOrg() {
        Object _ret = this.riInvokeExportedMethod(this,"executeViewOrg",null,null);
        return;
    }

    public String getAttributePath(Integer SlocId, String CldId, String hoOrgId, String itmId) {
        Object _ret =
            this.riInvokeExportedMethod(this,"getAttributePath",new String [] {"java.lang.Integer","java.lang.String","java.lang.String","java.lang.String"},new Object[] {SlocId, CldId, hoOrgId, itmId});
        return (String)_ret;
    }

    public Integer getRowCount() {
        Object _ret = this.riInvokeExportedMethod(this,"getRowCount",null,null);
        return (Integer)_ret;
    }

    public String getUserName(Integer usrId) {
        Object _ret = this.riInvokeExportedMethod(this,"getUserName",new String [] {"java.lang.Integer"},new Object[] {usrId});
        return (String)_ret;
    }

    public Integer getUsrLvl(Integer SlocId, String CldId, String OrgId, Integer UsrId, String WfNo, String DocNo) {
        Object _ret =
            this.riInvokeExportedMethod(this,"getUsrLvl",new String [] {"java.lang.Integer","java.lang.String","java.lang.String","java.lang.Integer","java.lang.String","java.lang.String"},new Object[] {SlocId, CldId, OrgId, UsrId, WfNo, DocNo});
        return (Integer)_ret;
    }

    public String getWfNo(Integer SlocId, String CldId, String OrgId, Integer DocNo) {
        Object _ret =
            this.riInvokeExportedMethod(this,"getWfNo",new String [] {"java.lang.Integer","java.lang.String","java.lang.String","java.lang.Integer"},new Object[] {SlocId, CldId, OrgId, DocNo});
        return (String)_ret;
    }

    public Integer insIntoTxn(Integer SlocId, String CldId, String OrgId, Integer DocNo, String WfNo,
                              Integer usr_idFrm, Integer usr_idTo, Integer levelTo, Integer levelFrm, String action,
                              String remark, oracle.jbo.domain.Number amount) {
        Object _ret =
            this.riInvokeExportedMethod(this,"insIntoTxn",new String [] {"java.lang.Integer","java.lang.String","java.lang.String","java.lang.Integer","java.lang.String","java.lang.Integer","java.lang.Integer","java.lang.Integer","java.lang.Integer","java.lang.String","java.lang.String","oracle.jbo.domain.Number"},new Object[] {SlocId, CldId, OrgId, DocNo, WfNo, usr_idFrm, usr_idTo, levelTo, levelFrm, action, remark, amount});
        return (Integer)_ret;
    }

    public String isAttributeValid(String itmAttType, String itmAttValue) {
        Object _ret =
            this.riInvokeExportedMethod(this,"isAttributeValid",new String [] {"java.lang.String","java.lang.String"},new Object[] {itmAttType, itmAttValue});
        return (String)_ret;
    }

    public Boolean isItemNameDuplicate(String itemName, String previousVal) {
        Object _ret =
            this.riInvokeExportedMethod(this,"isItemNameDuplicate",new String [] {"java.lang.String","java.lang.String"},new Object[] {itemName, previousVal});
        return (Boolean)_ret;
    }

    public boolean isPropertyCombinationValid() {
        Object _ret = this.riInvokeExportedMethod(this,"isPropertyCombinationValid",null,null);
        return ((Boolean)_ret).booleanValue();
    }

    public Integer pendingCheck(Integer SlocId, String CldId, String OrgId, Integer DocNo) {
        Object _ret =
            this.riInvokeExportedMethod(this,"pendingCheck",new String [] {"java.lang.Integer","java.lang.String","java.lang.String","java.lang.Integer"},new Object[] {SlocId, CldId, OrgId, DocNo});
        return (Integer)_ret;
    }

    public void updateItemIdAfterApproved(String retValue) {
        Object _ret =
            this.riInvokeExportedMethod(this,"updateItemIdAfterApproved",new String [] {"java.lang.String"},new Object[] {retValue});
        return;
    }
}
