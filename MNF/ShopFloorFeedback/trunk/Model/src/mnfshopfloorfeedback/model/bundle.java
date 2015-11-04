package mnfshopfloorfeedback.model;

import java.util.ListResourceBundle;

public class bundle extends ListResourceBundle {
    private static final Object[][] contents = { { "mnfshopfloorfeedback.model.views.LOVFeedbackIdVO.FdbkId_LABEL", "Id" },
        { "mnfshopfloorfeedback.model.views.LOVCopyPreviousFdbkVO.FdbkId_LABEL", "Id" },
        { "mnfshopfloorfeedback.model.views.LOVJcRcInfoViewVO.DispDocId_LABEL", "Id" },
        { "mnfshopfloorfeedback.model.views.LOVParamSetVO.ParamSetId_LABEL", "Id" },
        { "mnfshopfloorfeedback.model.views.LOVParamSetVO.ParamSetDesc_LABEL", "Description" },
        { "mnfshopfloorfeedback.model.views.LOVParamVO.ParamId_LABEL", "Id" },
        { "mnfshopfloorfeedback.model.views.LOVParamVO.ParamNme_LABEL", "Description" },
        { "mnfshopfloorfeedback.model.views.LOVParamValTypeVO_LABEL", "Lov Param Val Type Vo" },
        { "mnfshopfloorfeedback.model.views.LOVParamUOMVO_LABEL", "Lov Param Uomvo" },
        { "mnfshopfloorfeedback.model.views.LOVAllParameterForViewModeVO_LABEL", "Lov All Parameter For View Mode Vo" },
        { "mnfshopfloorfeedback.model.views.LOVAllParameterSetForViewModeVO_LABEL",
          "Lov All Parameter Set For View Mode Vo" }, { "mnfshopfloorfeedback.model.entities.MnfFdbkParamEO_Rule_0", "Duplicate Parameter Exists." }




    };

    public Object[][] getContents() {
        return contents;
    }
}
