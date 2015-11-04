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

public class MMBiOneSupplierDetailsBean {
    private StringBuffer labelTopNProductsForOneSuppler = new StringBuffer("Top 5 Products");
    private StringBuffer labelTopNProductGroupsForOneSupplier = new StringBuffer("Top 5 Product Groups");
    private StringBuffer labelTopNPOForOneSupplier = new StringBuffer("Top 5 Purchase Orders");
    private static ADFLogger adfLog=(ADFLogger)ADFLogger.createADFLogger(MMBiOneSupplierDetailsBean.class);
    public MMBiOneSupplierDetailsBean() {
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
    public Integer getEoId(){
        Integer eoId = Integer.parseInt(resolvElO("#{pageFlowScope.EO_ID}").toString());
        return eoId;
    }
    public void topNProductForOneSupplierVCL(ValueChangeEvent valueChangeEvent) {
        System.out.println(" new value "+valueChangeEvent.getNewValue());
        
        ///
        if (valueChangeEvent.getNewValue() != null && (!valueChangeEvent.getNewValue().toString().equals(valueChangeEvent.getOldValue().toString()))) {
            labelTopNProductsForOneSuppler = new StringBuffer("");
            labelTopNProductsForOneSuppler.append("Top ");
            labelTopNProductsForOneSuppler.append(valueChangeEvent.getNewValue());
            labelTopNProductsForOneSuppler.append(" Products");
            StringBuffer clause = new StringBuffer("");
            OperationBinding clauseBinding = this.getBindings().getOperationBinding("fetchWhereClause");
            clauseBinding.execute();
            if (clauseBinding.getResult() != null) {
                clause = (StringBuffer)clauseBinding.getResult();
            }
            StringBuffer clauseNew = new StringBuffer("");
            if(getEoId()!=null){
                clauseNew.append(" AND EO_ID_SRC = "+getEoId());
            }
            System.out.println("clause    "+clause+"  value -- "+valueChangeEvent.getNewValue());
            OperationBinding binding = this.getBindings().getOperationBinding("executeTopNProductsForOneSupplierVO");
            binding.getParamsMap().put("val", Integer.parseInt(valueChangeEvent.getNewValue().toString()));
            binding.getParamsMap().put("clause", clauseNew);
            binding.execute();
        }
    }
    public void topNProductGroupForOneSupplierVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null && (!valueChangeEvent.getNewValue().toString().equals(valueChangeEvent.getOldValue().toString()))) {
            labelTopNProductGroupsForOneSupplier = new StringBuffer("");
            labelTopNProductGroupsForOneSupplier.append("Top ");
            labelTopNProductGroupsForOneSupplier.append(valueChangeEvent.getNewValue());
            labelTopNProductGroupsForOneSupplier.append(" Products Groups");
            StringBuffer clause = new StringBuffer("");
            OperationBinding clauseBinding = this.getBindings().getOperationBinding("fetchWhereClause");
            clauseBinding.execute();
            if (clauseBinding.getResult() != null) {
                clause = (StringBuffer)clauseBinding.getResult();
            }
            StringBuffer clauseNew = new StringBuffer("");
            if(getEoId()!=null){
                clauseNew.append(" AND EO_ID_SRC = "+getEoId());
            }
            OperationBinding binding = this.getBindings().getOperationBinding("executeTopNProductGrpForOneSupplierVO");
            binding.getParamsMap().put("val", Integer.parseInt(valueChangeEvent.getNewValue().toString()));
            binding.getParamsMap().put("clause", clauseNew);
            binding.execute();
        } 
    }

    public void topNPOForOneSupplierVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null && (!valueChangeEvent.getNewValue().toString().equals(valueChangeEvent.getOldValue().toString()))) {
            labelTopNPOForOneSupplier = new StringBuffer("");
            labelTopNPOForOneSupplier.append("Top ");
            labelTopNPOForOneSupplier.append(valueChangeEvent.getNewValue());
            labelTopNPOForOneSupplier.append(" Purchase Orders");
            StringBuffer clause = new StringBuffer("");
            OperationBinding clauseBinding = this.getBindings().getOperationBinding("fetchWhereClause");
            clauseBinding.execute();
            if (clauseBinding.getResult() != null) {
                clause = (StringBuffer)clauseBinding.getResult();
            }
            StringBuffer clauseNew = new StringBuffer("");
           /*  if(getEoId()!=null){
                clauseNew.append(" AND EO_ID_SRC = "+getEoId());
            } */
            OperationBinding binding = this.getBindings().getOperationBinding("executeTopNPOForOneSupplierVO");
            binding.getParamsMap().put("val", Integer.parseInt(valueChangeEvent.getNewValue().toString()));
            binding.getParamsMap().put("clause", clauseNew);
            binding.getParamsMap().put("eoId", getEoId());
            binding.execute();
        } 
    }

    

    public StringBuffer getLabelTopNProductsForOneSuppler() {
        return labelTopNProductsForOneSuppler;
    }

    public StringBuffer getLabelTopNProductGroupsForOneSupplier() {
        return labelTopNProductGroupsForOneSupplier;
    }

    public StringBuffer getLabelTopNPOForOneSupplier() {
        return labelTopNPOForOneSupplier;
    }
}
