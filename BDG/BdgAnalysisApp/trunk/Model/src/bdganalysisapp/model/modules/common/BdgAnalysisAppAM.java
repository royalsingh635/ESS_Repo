package bdganalysisapp.model.modules.common;

import oracle.jbo.ApplicationModule;
import oracle.jbo.domain.Timestamp;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sat Jan 17 11:04:44 IST 2015
// ---------------------------------------------------------------------
public interface BdgAnalysisAppAM extends ApplicationModule {


    void processForDataAnalysis(Integer structId, String structVal);


    void searchStructValNm(String structValNm);

    void filterOrgSummFromStructVal(String val);

    void executeVoOnLoad(String orgId, String cldId, Integer slocId, String hoOrgId, Integer usrId);

    void executeVoOnSearch(Timestamp stDt, Timestamp endDt, Integer eoId);

    void filterTreeHeader(Integer structId, String structVal);

    void fetchParamAndCallFilterMethod();

    void filterEoBdgProdDtl(Integer headEoId, Timestamp strtDt, Timestamp endDt, String regionId, Integer dtlEoId,
                            String cldId, String hoOrgId, String orgId, Integer slocId);


    String getFacetNameToDisclose();

    void updtBdgEoProdDtl(oracle.jbo.domain.Number value, String key, oracle.jbo.domain.Number notation);

    void updtBudgetEoProdPrdAmt(oracle.jbo.domain.Number value, String key, oracle.jbo.domain.Number notation);

    String chkUsrDocFreeze();

    void resetValues();

    void executeVoOnTabDiscloure(Integer structId, String structValDepd, String structVal);

    void filterEmpHierarchyVw();

    void filterMtlBdgProdDtl(Integer headEoId, Timestamp strtDt, Timestamp endDt, String regionId, Integer dtlEoId,
                             String cldId, String hoOrgId, String orgId, Integer slocId);

    void updateMtlExpDtl(oracle.jbo.domain.Number val);

    void filterFinBdgProdDtl(Integer headEoId, Timestamp strtDt, Timestamp endDt, String regionId, Integer dtlEoId,
                             String cldId, String hoOrgId, String orgId, Integer slocId);
}

