package hcmattsalperiodapp.model.service.common;

import oracle.jbo.ApplicationModule;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Sep 22 18:27:16 IST 2015
// ---------------------------------------------------------------------
public interface HCMAttSalPeriodAppAM extends ApplicationModule {
    Integer addSalPeriod();

    Integer saveRecord();

    void initalFilter();


    String chkDuplictSlyPrcsFrmDt(String slyPrcsFrmDt);

    String chkDuplictAttenFrmDt(String attenFrmDt, String slyPrcsFrmDt1);
}

