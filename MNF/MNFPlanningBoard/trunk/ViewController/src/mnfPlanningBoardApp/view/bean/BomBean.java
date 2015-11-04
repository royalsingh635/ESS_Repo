package mnfPlanningBoardApp.view.bean;

import javax.faces.event.ActionEvent;

import mnfPlanningBoardApp.view.utils.ADFUtils;

import oracle.binding.OperationBinding;

public class BomBean {
    public BomBean() {
    }

    public void selectBomACE(ActionEvent actionEvent) {
        OperationBinding binding = ADFUtils.findOperation("selectBom");
        binding.getParamsMap().put("bomId", actionEvent.getComponent().getAttributes().get("bomId"));

        binding.execute();
    }

    public void selectBomOuputACE(ActionEvent actionEvent) {
        OperationBinding binding = ADFUtils.findOperation("selectproduct");
        binding.getParamsMap().put("prdId", actionEvent.getComponent().getAttributes().get("produId"));

        binding.execute();
        //selectproduct(String prdId){
    }
}
