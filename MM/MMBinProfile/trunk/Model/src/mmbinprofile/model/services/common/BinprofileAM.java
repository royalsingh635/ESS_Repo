package mmbinprofile.model.services.common;

import oracle.jbo.ApplicationModule;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Oct 28 14:14:30 IST 2013
// ---------------------------------------------------------------------
public interface BinprofileAM extends ApplicationModule {
    Integer on_load_page();

    void setBindVarForWhId(String hoOrg, String cld, String org, Integer sloc);

    void refreshLov();
}
