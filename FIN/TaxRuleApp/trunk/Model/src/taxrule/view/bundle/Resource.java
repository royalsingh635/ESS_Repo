package taxrule.view.bundle;

import java.util.ListResourceBundle;

public class Resource extends ListResourceBundle {
    private static final Object[][] contents = { { "taxrule.model.views.AppTaxVO_LABEL", "App Tax Vo" }, { "APP.Duplicaterecord", "LBL.2979" },
        { "taxrule.model.views.AppTaxRulePackgVO_LABEL", "App Tax Rule Packg Vo" },
        { "taxrule.model.views.links.TaxRuleToTaxRulePckgVL_LABEL", "Tax Rule To Tax Rule Pckg Vl" },
        { "taxrule.model.views.LovPckgVO_LABEL", "Lov Pckg Vo" }, { "taxrule.model.views.LovFormVO_LABEL", "Lov Form Vo" }




    };

    public Object[][] getContents() {
        return contents;
    }
}
