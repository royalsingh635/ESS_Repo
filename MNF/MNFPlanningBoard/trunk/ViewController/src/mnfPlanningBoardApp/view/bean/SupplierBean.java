package mnfPlanningBoardApp.view.bean;

import javax.faces.event.ActionEvent;

import mnfPlanningBoardApp.view.utils.ADFUtils;

import oracle.binding.OperationBinding;

public class SupplierBean {
    public SupplierBean() {
    }

    public void selectSupplierACE(ActionEvent actionEvent) {
        OperationBinding binding = ADFUtils.findOperation("filterSupplierRawMate");
        
        binding.getParamsMap().put("input_itm_id", null);
        binding.getParamsMap().put("pln_doc_Id", null);
        binding.getParamsMap().put("src_doc_id", null);
        binding.getParamsMap().put("output_id", null);
        binding.getParamsMap().put("dlv_dt", null);
        binding.getParamsMap().put("supp_id", actionEvent.getComponent().getAttributes().get("SuppId"));

        binding.execute();
    }

    public void rmSelectACE(ActionEvent actionEvent) {
        //filterSupplierRawForPrd
        OperationBinding binding = ADFUtils.findOperation("filterSupplierRawForPrd");
        
        binding.getParamsMap().put("input_itm_id", actionEvent.getComponent().getAttributes().get("ItemId"));
        binding.getParamsMap().put("pln_doc_Id", null);
        binding.getParamsMap().put("src_doc_id", null);
        binding.getParamsMap().put("output_id", null);
        binding.getParamsMap().put("dlv_dt", null);
        binding.getParamsMap().put("supp_id", actionEvent.getComponent().getAttributes().get("suppIdForItm"));

        binding.execute();
    }
}
