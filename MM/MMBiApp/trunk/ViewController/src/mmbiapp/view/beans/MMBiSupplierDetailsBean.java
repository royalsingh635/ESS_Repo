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

public class MMBiSupplierDetailsBean {
    private StringBuffer labelTopNSuppler = new StringBuffer("Top 5 Supplier in Amount");
    private StringBuffer labelTopNSupplierRating = new StringBuffer("Top 5 Supplier in Rating");
    private StringBuffer labelTopNPOForOneSupplier = new StringBuffer("Top 5 Purchase Orders");
    private static ADFLogger adfLog=(ADFLogger)ADFLogger.createADFLogger(MMBiSupplierDetailsBean.class);
    public MMBiSupplierDetailsBean() {
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
    public void topNSupplierVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue() != null && (!valueChangeEvent.getNewValue().toString().equals(valueChangeEvent.getOldValue().toString()))) {
            labelTopNSuppler = new StringBuffer("");
            labelTopNSuppler.append("Top ");
            labelTopNSuppler.append(valueChangeEvent.getNewValue());
            labelTopNSuppler.append(" Suppliers in Amount");
            StringBuffer clause = new StringBuffer("");
            OperationBinding clauseBinding = this.getBindings().getOperationBinding("fetchWhereClause");
            clauseBinding.execute();
            if(clauseBinding.getResult() != null){
                clause = (StringBuffer)clauseBinding.getResult();
            }
            
           
            OperationBinding binding = this.getBindings().getOperationBinding("executeTopNSupplierVO");
            binding.getParamsMap().put("val", Integer.parseInt(valueChangeEvent.getNewValue().toString()));
            binding.getParamsMap().put("clause", clause);
            binding.execute();
        }
    }

    public void topNSuppliersRatingVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue() != null && (!valueChangeEvent.getNewValue().toString().equals(valueChangeEvent.getOldValue().toString()))) {
            labelTopNSupplierRating = new StringBuffer("");
            labelTopNSupplierRating.append("Top ");
            labelTopNSupplierRating.append(valueChangeEvent.getNewValue());
            labelTopNSupplierRating.append(" Suppliers In Rating");
            StringBuffer clause = new StringBuffer("");
            OperationBinding clauseBinding = this.getBindings().getOperationBinding("fetchWhereClause");
            clauseBinding.execute();
            if(clauseBinding.getResult() != null){
                clause = (StringBuffer)clauseBinding.getResult();
            }
            OperationBinding binding = this.getBindings().getOperationBinding("executeTopNSupplierRatingVO");
            binding.getParamsMap().put("val", Integer.parseInt(valueChangeEvent.getNewValue().toString()));
            binding.getParamsMap().put("clause", clause);
            binding.execute();
        }
    }

    public StringBuffer getLabelTopNSuppler() {
        return labelTopNSuppler;
    }

    public StringBuffer getLabelTopNSupplierRating() {
        return labelTopNSupplierRating;
    }
}
