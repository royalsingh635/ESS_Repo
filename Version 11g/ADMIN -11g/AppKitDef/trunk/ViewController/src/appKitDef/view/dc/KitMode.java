package appKitDef.view.dc;

import appKitDef.model.views.AppKitNewVOImpl;

import appKitDef.view.bean.AppKitDefBean;

import java.util.HashMap;
import java.util.Map;

import oracle.binding.AttributeContext;
import oracle.binding.DataControl;
import oracle.binding.ManagedDataControl;
import oracle.binding.OperationBinding;
import oracle.binding.RowContext;
import oracle.binding.TransactionalDataControl;
import oracle.binding.UpdateableDataControl;

public class KitMode implements TransactionalDataControl, UpdateableDataControl, ManagedDataControl {
    public KitMode() {
        super();
    }
    public void outMode(){
        AppKitDefBean kitDef=new AppKitDefBean();
        kitDef.setOutput_itm(false);
        kitDef.setOutputitm_id(true);
        kitDef.setButton_disable("A");
    }

    public String getName() {
        return null;
    }

    public void release() {
    }

    public Object getDataProvider() {
        return null;
    }

    public boolean invokeOperation(Map p0, OperationBinding p1) {
        return false;
    }

    public boolean isTransactionDirty() {
        return false;
    }

    public void rollbackTransaction() {
    }

    public void commitTransaction() {
    }

    public boolean setAttributeValue(AttributeContext p0, Object p1) {
        return false;
    }

    public Object createRowData(RowContext p0) {
        return null;
    }

    public Object registerDataProvider(RowContext p0) {
        return null;
    }

    public boolean removeRowData(RowContext p0) {
        return false;
    }

    public void validate() {
    }

    public void beginRequest(HashMap p0) {
    }

    public void endRequest(HashMap p0) {
    }

    public boolean resetState() {
        return false;
    }
}
