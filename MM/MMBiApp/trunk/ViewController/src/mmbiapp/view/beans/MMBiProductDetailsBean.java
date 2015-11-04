package mmbiapp.view.beans;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.share.logging.ADFLogger;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class MMBiProductDetailsBean {
    private StringBuffer labelTopNProducts = new StringBuffer("Top 5 Products");
    private StringBuffer labelTopNProductGroups = new StringBuffer("Top 5 Product Groups");
    
    private static ADFLogger adfLog = (ADFLogger)ADFLogger.createADFLogger(MMBiProductDetailsBean.class);
    public MMBiProductDetailsBean() {
    }


    /**Method to get Binding Container*/
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    /**Method to resolve expression- returns String value*/
    public Object resolvElO(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        Object obj = valueExp.getValue(elContext);
        return obj;
    }
    
    
    public void topNProductVCL(ValueChangeEvent valueChangeEvent) {
        System.out.println(" new value "+valueChangeEvent.getNewValue());
        
        ///
        if (valueChangeEvent.getNewValue() != null && (!valueChangeEvent.getNewValue().toString().equals(valueChangeEvent.getOldValue().toString()))) {
            labelTopNProducts = new StringBuffer("");
            labelTopNProducts.append("Top ");
            labelTopNProducts.append(valueChangeEvent.getNewValue());
            labelTopNProducts.append(" Products");
            StringBuffer clause = new StringBuffer("");
            OperationBinding clauseBinding = this.getBindings().getOperationBinding("fetchWhereClause");
            clauseBinding.execute();
            if (clauseBinding.getResult() != null) {
                clause = (StringBuffer)clauseBinding.getResult();
            }
            System.out.println("clause    "+clause+"  value -- "+valueChangeEvent.getNewValue());
            OperationBinding binding = this.getBindings().getOperationBinding("executeTopNProductsVO");
            binding.getParamsMap().put("val", Integer.parseInt(valueChangeEvent.getNewValue().toString()));
            binding.getParamsMap().put("clause", clause);
            binding.execute();
        }
    }

    public void topNProductGroupVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null && (!valueChangeEvent.getNewValue().toString().equals(valueChangeEvent.getOldValue().toString()))) {
            labelTopNProductGroups = new StringBuffer("");
            labelTopNProductGroups.append("Top ");
            labelTopNProductGroups.append(valueChangeEvent.getNewValue());
            labelTopNProductGroups.append(" Products Groups");
            StringBuffer clause = new StringBuffer("");
            OperationBinding clauseBinding = this.getBindings().getOperationBinding("fetchWhereClause");
            clauseBinding.execute();
            if (clauseBinding.getResult() != null) {
                clause = (StringBuffer)clauseBinding.getResult();
            }
            OperationBinding binding = this.getBindings().getOperationBinding("executeTopNProductGrpVO");
            binding.getParamsMap().put("val", Integer.parseInt(valueChangeEvent.getNewValue().toString()));
            binding.getParamsMap().put("clause", clause);
            binding.execute();
        } 

    }

    

    public StringBuffer getLabelTopNProducts() {
        return labelTopNProducts;
    }

    public StringBuffer getLabelTopNProductGroups() {
        return labelTopNProductGroups;
    }
}
