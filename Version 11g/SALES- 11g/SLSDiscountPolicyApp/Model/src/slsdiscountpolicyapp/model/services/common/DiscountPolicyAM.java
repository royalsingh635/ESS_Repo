package slsdiscountpolicyapp.model.services.common;

import oracle.jbo.ApplicationModule;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Sep 03 11:50:07 IST 2014
// ---------------------------------------------------------------------
public interface DiscountPolicyAM extends ApplicationModule {
    void setInitialVariables();

    Integer addDiscountRows();

    void resetSearchEditRows();

    void reset();

    void search();

    Boolean editDiscRows();

    void searchForEdit();
}
