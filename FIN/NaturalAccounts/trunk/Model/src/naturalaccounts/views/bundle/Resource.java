package naturalaccounts.views.bundle;

import java.util.ListResourceBundle;

public class Resource extends ListResourceBundle {
    private static final Object[][] contents = { { "naturalaccounts.model.views.FinAccNaVO.AccNameBindVar_LABEL", "#{bundle['NaturalAccount.Name']}" }, { "naturalaccounts.model.views.FinAccNaVO.AccAliasBindVar_LABEL", "#{bundle['NaturalAccount.Alias']}" }, { "naturalaccounts.model.views.FinAccNaVO.AccountTypeBindVar_LABEL", "#{bundle['NaturalAccount.AccountType']}" }, { "naturalaccounts.model.views.FinAccNaVO.AccNm_LABEL", "#{bundle['NaturalAccount.Name']}" }, { "naturalaccounts.model.views.FinAccNaVO.AccAlias_LABEL", "#{bundle['NaturalAccount.Alias']}" }, { "naturalaccounts.model.views.FinAccNaVO.AccType_LABEL", "#{bundle['NaturalAccount.AccountType']}" } };

    public Object[][] getContents() {
        return contents;
    }
}
