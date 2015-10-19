package batchpayment.view.bundle;

import java.util.ListResourceBundle;

public class Resource extends ListResourceBundle {
    private static final Object[][] contents = { { "batchpayment.model.views.CurrConRateVO_LABEL", "Curr Con Rate Vo" },
        { "batchpayment.model.views.LovProjectVO_LABEL", "Lov Project Vo" },
        { "batchpayment.model.views.LovAdjstMntType_LABEL", "Lov Adjst Mnt Type" }


    };

    public Object[][] getContents() {
        return contents;
    }
}
