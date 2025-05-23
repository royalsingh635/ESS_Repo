package mnfshopfloorfeedback.model.services.common;

import oracle.jbo.ApplicationModule;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Timestamp;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sat Sep 06 17:50:32 IST 2014
// ---------------------------------------------------------------------
public interface MNFShopFloorFeedbackAM extends ApplicationModule {
    String getServerPathToAttachFile(int slocId);

    String insertInMnfFdbkAttch(String AttchFileExtn, String AttchFilepath, String AttchExtn, String DispFlNm);


    void setCreateViewFdbkParams(Integer slocId, String hoOrgId, String orgId, Integer userId, String fdbkId);

    void setFdbkSearchParams(Timestamp createdto, String id, Integer createdBy, Integer basis, Timestamp createdOn,
                             String jcid, String rcid);

    void setGlobalParameters();

    void getReferenceFeedback();

    void setResetFdbkSearchParams();

    String chkDuplicate(String tab, String val);

    String createAttchmntRow(String contentTyp, String fileNm, String extn);

    void enableReservedMode();

    int chkNmDuplicate(String val);

    void deleteAttachFileRow(String path);

    oracle.jbo.domain.Number checkYearFyId();
}

