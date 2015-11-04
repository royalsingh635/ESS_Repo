package schedule6.bean.utill;

import java.sql.CallableStatement;
import java.sql.SQLException;

import java.util.Iterator;
import java.util.List;

import java.util.Set;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.model.BindingContainer;
import oracle.adf.model.BindingContext;
import oracle.adf.model.OperationBinding;
import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.jbo.JboException;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

import schedule6.model.services.Schedule6AMImpl;
import java.util.*;

import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;

import oracle.jbo.Key;

public class JSFUtill {
    private HashMap<String,HashMap<String,Object>> map=new HashMap<String,HashMap<String,Object>>();
    
    public JSFUtill() {
        super();
    }

    public static void  showPopup(RichPopup pop, boolean visible) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && pop != null) {
                String popupId = pop.getClientId(context);
                if (popupId != null) {
                    StringBuilder script = new StringBuilder();
                    script.append("var popup = AdfPage.PAGE.findComponent('").append(popupId).append("'); ");
                    if (visible) {
                        script.append("if (!popup.isPopupVisible()) { ").append("popup.show();}");
                    } else {
                        script.append("if (popup.isPopupVisible()) { ").append("popup.hide();}");
                    }
                    ExtendedRenderKitService erks =
                        Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);
                    erks.addScript(context, script.toString());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static OperationBinding getOpertion(String operation){
        BindingContext bc=BindingContext.getCurrent();
        return (OperationBinding)bc.getCurrentBindingsEntry().getOperationBinding(operation);
    }
    
    public static Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        Schedule6AMImpl am = (Schedule6AMImpl)resolvElDC("Schedule6AMDataControl");
        try {
            st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 2, bindVars[z]);
                }
            }
            st.executeUpdate();
            System.out.println(st.getObject(1));
            return st.getObject(1);

        } catch (SQLException e) {
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
    
    public static Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }
    
    public static FacesMessage getMessage(FacesMessage.Severity type,String heading,String message){
        return new FacesMessage(type,heading,message);
    }
    
    public  void getCurrentRowForIterator(){
//        this.map.clear();
//       
//        DCBindingContainer container = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
//        DCIteratorBinding dcIter = null;
//        Key key=null;
//        int rangeIndex=0;
//        HashMap<String,Object> rowMap=new HashMap<String,Object>();
//        
//        
//        System.out.println("Controlar control  is "+container.keySet());
//        System.out.println("Iterator  is "+container.get("FinSch1Iterator"));
//        System.out.println("Iterator  is "+((DCBindingContainer)container).findIteratorBinding("FinSch1Iterator"));
//        System.out.println("Controlar Binding  is "+container.getControlBindings());
//        System.out.println("Controlar  is "+container.getIterBindingList());
//        
//        List bindings=(List)container.getControlBindings();
//        Iterator iter=bindings.iterator();
//        while(iter.hasNext()){
//            String attr=(String)iter.next();
//            if(attr.contains("Itertor")){
//               dcIter=(DCIteratorBinding)container.getControlBinding(attr);
//               key=dcIter.getCurrentRow().getKey();
//               rangeIndex=dcIter.getRangeSize();
//               rowMap.put("Key", key);
//               rowMap.put("Range",rangeIndex);
//               this.map.put(attr, rowMap);
//               
//            }
//        }
//        System.out.println("Map is "+map);
    }
    
    public void setCurrentRowForIterator(){
//        oracle.binding.BindingContainer container= BindingContext.getCurrent().getCurrentBindingsEntry();
//        DCIteratorBinding dcIter = null;
//        Key key=null;
//        Integer rangeIndex=0;
//        HashMap<String,Object> rowMap=new HashMap<String,Object>();
//        
//        Iterator iter=this.map.keySet().iterator();
//        while(iter.hasNext()){
//            String dcKey=(String)iter.next();
//            dcIter=(DCIteratorBinding)container.get(dcKey);
//            key=(Key)this.map.get(dcKey).get("Key");
//            rangeIndex=(Integer)this.map.get(dcKey).get("Range");
//            dcIter.setCurrentRowWithKey(key.toStringFormat(true));
//            
//        }
    }
    
    
    public static Object resolvElDCMsg(String data) {
           FacesContext fc = FacesContext.getCurrentInstance();
           Application app = fc.getApplication();
           ExpressionFactory elFactory = app.getExpressionFactory();
           ELContext elContext = fc.getELContext();
           ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
           return valueExp.getValue(elContext);
       } 
}
