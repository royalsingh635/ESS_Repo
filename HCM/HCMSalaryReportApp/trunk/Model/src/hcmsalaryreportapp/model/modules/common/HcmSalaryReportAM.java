package hcmsalaryreportapp.model.modules.common;

import java.util.ArrayList;
import java.util.HashMap;

import oracle.jbo.ApplicationModule;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon May 04 18:15:53 IST 2015
// ---------------------------------------------------------------------
public interface HcmSalaryReportAM extends ApplicationModule {
    String clearEmployeMailList();

    String setCriteraValuesInLov();

    String chkForSalaryProcAndApprov(String Emp_docId);

    String generateReport(String empDocId, Integer empCode, String emNm);

    String getSenderInfo();

    void sendEmail(String toAddress, Integer empCode, String emNm);


    Boolean[] checkBox();

    String insertDataByDailyHoursLink();
}

