package hcmempprfsetup.model.modules.common;

import java.math.BigDecimal;

import java.sql.Date;

import java.util.List;

import oracle.jbo.ApplicationModule;
import oracle.jbo.domain.Timestamp;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Sep 08 12:48:47 IST 2014
// ---------------------------------------------------------------------
public interface HcmEmpPrfAM extends ApplicationModule
{

    String pathGetFrmDb();


    void addEditOfficialDetail();

    void applyCriteriaOnPageLoad(String cldId, Integer slocId, String hoOrgId, String orgId);

    String imageIdGenerate();

    void imgAttSetter(String fileType, String imgPath, String imgId);


    String chkForNullField();

    void bindVarSetForTimeCal(String cldId, String orgId, String hoOrgId, Integer slocId, String docId,
                              Integer empCode);


    Integer nextSeqNoGen();

    void updateShiftSeq(String act);
    
    void changeSalaryAmt(oracle.jbo.domain.Number newAmt);

    void colorSetInTable(String red, String green, String blue);

    String clrGetFromAtt();

    void searchFromPopup();

    void updtSalAmt();

    String chkDupliSalaryId(String salId);

    String chkDupliLeaveId(String leaveId);

    String chkDupliRefSalaryId(String refSalId);


    String chkDupliEmpId(String empId);

    String chkDupliEmpCard(String empCardNo);


    String chkDupliAccNo(String accNo);

    String chkDupliMailId(String mailId);

    String chkDupliPanNo(String empPanNo);

    String deleteSalComp();

    void searchEmployee(String cldId, Integer slocId, String hoOrgId, String empNm, String empId, String orgId);

    String chkNoOfPositions(String desgId);

    String chkDupliSkilId(String skilId);


    String chkDupliShiftId(String shiftId);

    String validateShiftStartDate(Date shiftDt);

    String chkDupliWeekoffId(String weekoffId);

    String chkSalIdItSelf(String chkSalIdItSelf);

    void chkBlankRowAndRemove();

    String isSalaryProcPending();

    String chkLevDtValid(Date leaveDt);

    String chkSalDtValid(Date salDt);

    String chkSkilDtValid(Date skilDt);

    String chkPrevStDtValid(Date stDt);

    void executeLovVoInOffcialDtl();

    String updateEoNmFrmEmpNm(String empNm);

    String genEmpCodeForEmp();

    void insertIntoEmpPrf(List list);

    void resetValues();

    void deleteView();

    void callFuncForCalendar(Boolean flg);

    void genCalendarForCurrMonth(Boolean flg);

    String insrtReqmntAreaInfo();

    BigDecimal chkReqAreaForDeletion();

    void refreshAftDeletion();

    void refreshDummyRqmtVo();

    void refreshEmployeDed();

    String isAnySalaryProcess();

    String chkWeekOffType();

    String chkSubSalCompAction(String subSalId);

    String calTotalSubDedAmnt();

    String chkIsOtherDeduction(String refSalId);

    String chkDuplicateDocumentNo(String docNo, Integer dtlType);

    String createAttchmntRow(String cldId, Integer slcId, String hoOrgId, String orgId, String contentTyp,
                             String fileNm, String extn);

    void deleteAttachFileRow(String path);

    String chkDuplicateDependentName(String nm);

    String chDockIssuDt(Timestamp newIssueDate, Integer dtlType);

    String checkDuplicateUser(Integer usrId);

    String chkDuplicateMedicalIssue(String issuName);

    Timestamp calcRelvngDate(Integer days, java.util.Date resDt);

    String chkResignitionDate(Timestamp resgDt);

    String chkIsDesigLinkWithGradeInOrg(String desgId);

    String setEmployeeGrade(String desgId);

    Boolean chkCcApplicableOrNot();

    void declareGlblValuesForCC();

    void generateCostCenterHeaderWise();

    void sendDateFromTempCcToSlsRmaCc();

    String chkDupliaceCompanyName(String empCompName);

    String chkDupliaceCource(String empCrseId);

    String chkDupliaceLanguage(String empLngId);

    String setEmpNoticePrdNDProbPrd(String empType);

    void bindVarSetForCtcDetails(String cldId, String orgId, String hoOrgId, Integer slocId, String docId,
                                 Integer empCode);

    void calculateNewCTCAmnt(BigDecimal amount);

    void setValidStartNdEndDtFrCTC();
}

