package mmappgrpdef.view.bean;

import java.util.Iterator;
import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import mmappgrpdef.model.module.AppGrpdefAMImpl;

import oracle.adf.view.rich.component.rich.data.RichTreeTable;

import oracle.jbo.Row;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.RowKeySet;

public class AppGrpBean {
    private RichTreeTable groupTree;

    public AppGrpBean() {
    }

    public void OnSelection(SelectionEvent selectionEvent) {
        RichTreeTable treeTable = this.getGroupTree();
        RowKeySet rks = treeTable.getSelectedRowKeys();
        Iterator keys = rks.iterator();
        Integer grpId = 0;
        while (keys.hasNext()) {
            List key = (List)keys.next();
            treeTable.setRowKey(key);
            JUCtrlHierNodeBinding node = (JUCtrlHierNodeBinding)treeTable.getRowData();
            Row rw = node.getRow();
            String voName = node.getViewObject().getName();
            grpId = Integer.parseInt(rw.getAttribute("GrpId").toString());
    }
        
    AppGrpdefAMImpl am = (AppGrpdefAMImpl)resolvElDC("AppGrpdefAMDataControl");
    ViewObjectImpl v = am.getFormGrp1();
    v.setWhereClause("Grp_Id ="+grpId);
    v.executeQuery();
}
    
    public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }


    public void setGroupTree(RichTreeTable groupTree) {
        this.groupTree = groupTree;
    }

    public RichTreeTable getGroupTree() {
        return groupTree;
    }
}
