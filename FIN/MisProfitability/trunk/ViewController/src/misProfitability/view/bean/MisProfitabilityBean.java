package misProfitability.view.bean;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import oracle.adf.model.BindingContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class MisProfitabilityBean {

    public MisProfitabilityBean() {
        setStatusGaugeVarVal();
    }
    private Integer i;
    private Number[] valArry = new Number[11];
    private Map<String, Object> amtLabelMap = new HashMap<String, Object>();


    private void setStatusGaugeVarVal() {

        for (i = 40; i <= 50; i++) {

            OperationBinding opLbl = getBindings().getOperationBinding("getAmountLabel");
            opLbl.getParamsMap().put("p_graph_id", i);
            opLbl.execute();

            amtLabelMap.put(i.toString(), opLbl.getResult());

       
            OperationBinding opVal = getBindings().getOperationBinding("getAmountVal");
            opVal.getParamsMap().put("p_graph_id", i);
            opVal.execute();
            
            valArry[i-40] = (Number)opVal.getResult();
       
        }
        
        OperationBinding opLbl = getBindings().getOperationBinding("getAmountLabel");
        opLbl.getParamsMap().put("p_graph_id", null);
        opLbl.execute();      

        
        OperationBinding opVal = getBindings().getOperationBinding("getAmountVal");
        opVal.getParamsMap().put("p_graph_id", null);
        opVal.execute();
       
    }


    public Map getVisibleVal() {

        return new HashMap<Integer, String>() {

            @Override
            public String get(Object key) {

                if (key != null) {
                    Object c = key;
                    String retVal = "Y";

                    retVal = (String)amtLabelMap.get(c.toString());
                    //System.out.println("inside getVisible() c :" + c + " retVal :" + retVal);

                    return retVal;
                } else
                    return null;
            }
        };
    }

    public BindingContainer getBindings() {

        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void setValArry(Number[] valArry) {
        this.valArry = valArry;
    }

    public Number[] getValArry() {
        
        return valArry;
    }
}
