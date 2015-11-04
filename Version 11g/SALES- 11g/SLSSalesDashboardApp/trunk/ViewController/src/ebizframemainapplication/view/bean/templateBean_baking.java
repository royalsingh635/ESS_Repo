package ebizframemainapplication.view.bean;

import ebizframemainapplication.model.EbizMainAppAMImpl;


import java.io.IOException;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Locale;

import java.util.Map;
import java.util.ResourceBundle;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mmrmda.model.service.MMrmdaAMImpl;

import oracle.adf.model.BindingContext;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.nav.RichCommandMenuItem;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.javatools.resourcebundle.ResourceBundleManagerRT;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.client.Configuration;
import oracle.jbo.server.DBTransaction;
import oracle.jbo.server.RowQualifier;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


public class templateBean_baking {

    private RichPopup settingsPopUp;

    public templateBean_baking() {
        //super();
        // System.out.println("inside template bean");
        getGlobalParameter();
    }

    private RichSelectBooleanCheckbox dtSetCB;
    private RichSelectBooleanCheckbox lanSetCB;
    private RichSelectBooleanCheckbox dsplySetCB;
    private RichSelectBooleanCheckbox frmtSetCB;

    private String _skinFamily = _RICH_DEMO_SKIN;
    static private final String _SKIN_TEST_DIRECTORY_INPUT = "/skinningKeys/input";
    static private final String _SKIN_TEST_DIRECTORY_SELECT = "/skinningKeys/select";
    static private final String _SKIN_TEST_DIRECTORY = "/skinningKeys";
    static private final String _TEST_SKIN_INPUT_SELECT = "demoComponentsInputSelect";
    static private final String _TEST_SKIN_INPUT_OTHER = "demoComponentsOther";
    static private final String _RICH_DEMO_SKIN = "Main";

    private static final long serialVersionUID = 1L;

    Locale preferredLocale;
    private String registeredNM;
    private Integer userId;
    private String date_format;
    private String num_format;
    private String lang_path;
    private String lang_file;
    private String cloud_id = "0000";
    private Integer lang_id = (Integer)resolvEl("#{sessionScope.lang_id}");
    private String lang;
    private Integer amt_digit;
    private String amt_format;
    private Integer curr_digit;
    private String curr_format;
    private Integer rate_digit;
    private String rate_format;
    private Integer qty_digit;
    private String qty_format;
    private String disc_format;
    private Integer disc_digit;
    FacesContext ctx = FacesContext.getCurrentInstance();
    private ArrayList<SelectItem> dateFormat;
    private ArrayList<SelectItem> appLang;
    private static int NUMERIC = Types.NUMERIC;
    private static int STRING = Types.VARCHAR;
    FacesContext context = FacesContext.getCurrentInstance();
    HttpSession session = (HttpSession)context.getExternalContext().getSession(true);
    EbizMainAppAMImpl loginAm;
    HttpSession userSession;

    public void getGlobalParameter() {


        if (session.getAttribute("cloud_Id") != null)
            cloud_id = session.getAttribute("cloud_Id").toString();

        try {
            // lang = callStoredFunction(STRING, "APP.FN_GET_APP_DEF_LANG (?)", new Object[] { cloud_id }).toString();
            //System.out.println("lang from funtion------>" + lang);
            String driver=getdrivername();
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                num_format =
                        callStoredFunction(STRING, "call FN_GET_APP_NUM_STYLE (?)", new Object[] { cloud_id }).toString();   
            }
            else
            {
            num_format =
                    callStoredFunction(STRING, "APP.FN_GET_APP_NUM_STYLE (?)", new Object[] { cloud_id }).toString();
            }
            //   System.out.println("num_format in funtion------>" + num_format);
           
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                amt_format =
                        callStoredFunction(STRING, "call FN_GET_APP_AMT_FORMAT (?)", new Object[] { cloud_id }).toString();   
            }
            else
            {
            amt_format =
                    callStoredFunction(STRING, "APP.FN_GET_APP_AMT_FORMAT (?)", new Object[] { cloud_id }).toString();
            }
            //   System.out.println("amt_format in funtion------>" + amt_format);
           
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                qty_format =
                        callStoredFunction(STRING, "call FN_GET_APP_QTY_FORMAT (?)", new Object[] { cloud_id }).toString();   
            }
            else
            {
            qty_format =
                    callStoredFunction(STRING, "APP.FN_GET_APP_QTY_FORMAT (?)", new Object[] { cloud_id }).toString();
            }
            //  System.out.println("qty_format in funtion------>" + qty_format);
           
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                curr_format =
                        callStoredFunction(STRING, "call FN_GET_APP_CC_FORMAT  (?)", new Object[] { cloud_id }).toString();   
            }
            else
            {
            curr_format =
                    callStoredFunction(STRING, "APP.FN_GET_APP_CC_FORMAT  (?)", new Object[] { cloud_id }).toString();
            }
            //   System.out.println("curr_format in funtion------>" + curr_format);
           
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                rate_format =
                        callStoredFunction(STRING, "call FN_GET_APP_RATE_FORMAT (?)", new Object[] { cloud_id }).toString();   
            }
            else
            {
            rate_format =
                    callStoredFunction(STRING, "APP.FN_GET_APP_RATE_FORMAT (?)", new Object[] { cloud_id }).toString();
            }
            //   System.out.println("rate_format in funtion------>" + rate_format);
           
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                registeredNM =
                        callStoredFunction(STRING, "call FN_GET_PRF_REGD_TO_NM (?)", new Object[] { cloud_id }).toString();   
            }
            else
            {
            registeredNM =
                    callStoredFunction(STRING, "APP.FN_GET_PRF_REGD_TO_NM (?)", new Object[] { cloud_id }).toString();
            }
            //   System.out.println("registeredNM in funtion------>" + registeredNM);
           
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                disc_format =
                        (callStoredFunction(STRING, "call FN_GET_APP_DISC_FORMAT (?)", new Object[] { cloud_id }).toString());   
            }
            else
            {
            disc_format =
                    (callStoredFunction(STRING, "APP.FN_GET_APP_DISC_FORMAT (?)", new Object[] { cloud_id }).toString());
            }
            System.out.println("Template :disc_format in funtion------>" + disc_format);


        } catch (Exception e) {
            // TODO: Add catch code
            System.out.println("error @ templateBean getGlobalparameter() ");
            e.printStackTrace();
        }
    }

    public void setGlobalParameter() {
        Object funRetVal;
        try {
            String driver=getdrivername();
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                date_format =
                        (String)callStoredFunction(STRING, "call FN_SET_APP_DT_FORMAT(?,?)", new Object[] { cloud_id, getDate_format() });   
            }
            else
            {
            date_format =
                    (String)callStoredFunction(STRING, "APP.FN_SET_APP_DT_FORMAT(?,?)", new Object[] { cloud_id, getDate_format() });
            }
            //   System.out.println("date format in funtion------>" + getDate_format());
           
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                funRetVal =
                        callStoredFunction(STRING, "call FN_SET_APP_AMT_DIGIT (?,?)", new Object[] { cloud_id, getAmt_digit() });   
            }
            else
            {
            funRetVal =
                    callStoredFunction(STRING, "APP.FN_SET_APP_AMT_DIGIT (?,?)", new Object[] { cloud_id, getAmt_digit() });
            }
            //   System.out.println("amt digit in funtion------>" + getAmt_digit());
            
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                funRetVal =
                        callStoredFunction(STRING, "call FN_SET_APP_CURR_DIGIT (?,?)", new Object[] { cloud_id, getCurr_digit() });   
            }
            else
            {
            funRetVal =
                    callStoredFunction(STRING, "APP.FN_SET_APP_CURR_DIGIT (?,?)", new Object[] { cloud_id, getCurr_digit() });
            }
            //   System.out.println("curr digit in funtion------>" + getCurr_digit());
           
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                funRetVal =
                        callStoredFunction(STRING, "call FN_SET_APP_RATE_DIGIT (?,?)", new Object[] { cloud_id, getRate_digit() });   
            }
            else
            {
            funRetVal =
                    callStoredFunction(STRING, "APP.FN_SET_APP_RATE_DIGIT (?,?)", new Object[] { cloud_id, getRate_digit() });
            }
            //   System.out.println("rate digit in funtion------>" + getRate_digit());
           
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                funRetVal =
                        callStoredFunction(STRING, "call FN_SET_APP_QTY_DIGIT  (?,?)", new Object[] { cloud_id, getQty_digit() });   
            }
            else
            {
            funRetVal =
                    callStoredFunction(STRING, "APP.FN_SET_APP_QTY_DIGIT  (?,?)", new Object[] { cloud_id, getQty_digit() });
            }
            //   System.out.println("qty digit in funtion------>" + getQty_digit());
            //funRetVal =
            //
            // call to function  RESET  language id
           
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                funRetVal =
                        callStoredFunction(STRING, "call FN_SET_USR_DEF_LANG_ID (?,?,?)", new Object[] { this.getSloc_id(),
                                                                                                        this.getUser_id(),
                                                                                                        this.lang_id });   
            }
            else
            {
            funRetVal =
                    callStoredFunction(STRING, "APP.FN_SET_USR_DEF_LANG_ID (?,?,?)", new Object[] { this.getSloc_id(),
                                                                                                    this.getUser_id(),
                                                                                                    this.lang_id });
            }

            // call to function  RESET  Discount Digit
           
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                funRetVal =
                        callStoredFunction(STRING, "call FN_SET_APP_DISC_DIGIT (?,?)", new Object[] { cloud_id, this.getDisc_digit() });   
            }
            else
            {
            funRetVal =
                    callStoredFunction(STRING, "APP.FN_SET_APP_DISC_DIGIT (?,?)", new Object[] { cloud_id, this.getDisc_digit() });
            }


            OperationBinding createOpB = getBindings().getOperationBinding("Commit");
            createOpB.execute();


            //MSG.566- User preferances successfully saved, preferances will be effective from next login
            FacesMessage msg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.566']}").toString());
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            //MSG.567- Unable to Save User preferances, Please Contact ESS !
            System.out.println("inside catch block for setGlobalParamet()");
            FacesMessage msg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.567']}").toString());
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            e.printStackTrace();
        }

    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String getSkinFamily() {

        // If we're in the skin component tests, return the skinComponentTest skin.
        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot viewRoot = context.getViewRoot();
        if ((viewRoot != null) && (viewRoot.getViewId().indexOf(_SKIN_TEST_DIRECTORY) >= 0)) {
            String viewId = viewRoot.getViewId();

            if (viewId != null && viewId.indexOf(_SKIN_TEST_DIRECTORY) >= 0) {
                if ((viewId.indexOf(_SKIN_TEST_DIRECTORY_INPUT) >= 0) ||
                    (viewId.indexOf(_SKIN_TEST_DIRECTORY_SELECT) >= 0))
                    return _TEST_SKIN_INPUT_SELECT;
                else
                    return _TEST_SKIN_INPUT_OTHER;
            }
        }

        return _skinFamily;
    }

    public void change(String language) {

        ResourceBundleManagerRT rt = (ResourceBundleManagerRT)ResourceBundleManagerRT.getResourceBundleManager();
        rt.flush();
        //  System.out.println(rt.getClass());
        Locale newLocale = new Locale(language);
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.getViewRoot().setLocale(newLocale);

        UIViewRoot uiViewRoot = FacesContext.getCurrentInstance().getViewRoot();
        setPreferredLocale(uiViewRoot.getLocale());

    }

    public void changeLocale(ValueChangeEvent valueChangeEvent) throws NoSuchFieldException {

        change(valueChangeEvent.getNewValue().toString());
        lanSetCB.setValue(Boolean.TRUE);
        AdfFacesContext.getCurrentInstance().addPartialTarget(lanSetCB);
    }

    public void setSkinFamily(String family) {
        _skinFamily = family;
    }

    public void skinMenuAction(ActionEvent ae) {
        RichCommandMenuItem menuItem = (RichCommandMenuItem)ae.getComponent();

        setSkinFamily(menuItem.getText());

        reloadThePage();
    }

    public void afterload(PhaseEvent phaseEvent) {

        if (ADFContext.getCurrent().getSecurityContext().getUserPrincipal().getName().equals("super1")) {
            setSkinFamily("debug");
        }
    }

    public static void reloadThePage() {
        FacesContext fContext = FacesContext.getCurrentInstance();
        String viewId = fContext.getViewRoot().getViewId();
        String actionUrl = fContext.getApplication().getViewHandler().getActionURL(fContext, viewId);
        try {
            ExternalContext eContext = fContext.getExternalContext();
            String resourceUrl = actionUrl; //eContext.encodeResourceURL(actionUrl);
            // Use the action URL directly since the encoding a resource URL will NPE in isEmailablePage()
            eContext.redirect(resourceUrl);
        } catch (IOException ioe) {

            ioe.printStackTrace();
        }
    }

    public String logout() {

        System.out.println("In logOut");
        ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletResponse response = (HttpServletResponse)ectx.getResponse();
        String url = ectx.getRequestContextPath() + "/faces/login.jspx";

        ResourceBundleManagerRT rt = (ResourceBundleManagerRT)ResourceBundleManagerRT.getResourceBundleManager();
        rt.flush();


        ResourceBundle.clearCache(Thread.currentThread().getContextClassLoader());


        HttpSession session = (HttpSession)ectx.getSession(false);
        session.invalidate();


        try {
            response.sendRedirect(url);
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void refreshpage(ActionEvent actionEvent) {

        FacesContext fc = FacesContext.getCurrentInstance();
        String refreshpage = fc.getViewRoot().getViewId();
        ViewHandler ViewH = fc.getApplication().getViewHandler();
        UIViewRoot UIV = ViewH.createView(fc, refreshpage);
        UIV.setViewId(refreshpage);
        fc.setViewRoot(UIV);
    }

    public void setPreferredLocale(Locale preferredLocale) {
        this.preferredLocale = preferredLocale;
    }

    public Locale getPreferredLocale() {
        return preferredLocale;
    }

    public void setDateFormat(ArrayList<SelectItem> dateFormat) {
        this.dateFormat = dateFormat;
    }

    public ArrayList<SelectItem> getDateFormat() {

        ViewObject vo = getAm().getDateFormatLOV();
        vo.setRangeSize(-1);
        Row[] r = vo.getAllRowsInRange();
        ArrayList<SelectItem> a = new ArrayList<SelectItem>(10);

        for (Row rw : r) {
            a.add(new SelectItem(rw.getAttribute("AttNm").toString()));
        }

        return a;
    }

    public void settingsDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            setGlobalParameter();
            //System.out.println("setting global parameter");


            if (this.lanSetCB.isSelected()) {
                resetLanguage();

                Enumeration en = session.getAttributeNames();
                while (en.hasMoreElements()) {
                    System.out.println(en.nextElement().getClass());
                }

            }
            //ResourceBundle.clearCache();
            reset();
            reloadThePage();
            settingsPopUp.hide();
        } else if (dialogEvent.getOutcome().name().equals("cancel")) {
            //      System.out.println("cancel event");
            reset();
        }
    }

    public void reset() {
        //   System.out.println("inside reset ");
        lanSetCB.setValue(Boolean.FALSE);
        dsplySetCB.setValue(Boolean.FALSE);
        dtSetCB.setValue(Boolean.FALSE);
        frmtSetCB.setValue(Boolean.FALSE);
        AdfFacesContext.getCurrentInstance().addPartialTarget(lanSetCB);
        AdfFacesContext.getCurrentInstance().addPartialTarget(dsplySetCB);
        AdfFacesContext.getCurrentInstance().addPartialTarget(dtSetCB);
        AdfFacesContext.getCurrentInstance().addPartialTarget(frmtSetCB);
    }
    

    public String getdrivername()
    {         String   drivernme ="";
            EbizMainAppAMImpl am = (EbizMainAppAMImpl)resolvElDC("EbizMainAppAMDataControl");
             DBTransaction dbTransaction = (DBTransaction) am.getTransaction();
             PreparedStatement prepStatement =
             dbTransaction.createPreparedStatement("select * from dual", 0);
             try
             {
               System.out.println("Driver name--"+prepStatement.getConnection().getMetaData().getDriverName());
               drivernme = prepStatement.getConnection().getMetaData().getDriverName();
             }
             catch (SQLException e)
             {
                 e.printStackTrace();
             }
             return drivernme;

             }


    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {
            String driver=getdrivername();
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                st = getAm().getDBTransaction().createCallableStatement("{? = " + stmt + "};", 0);   
            }
            else
            {
            st = getAm().getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            }
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 2, bindVars[z]);
                }
            }
            st.executeUpdate();

            return st.getObject(1);
        } catch (SQLException e) {
            int end = e.getMessage().indexOf("\n");

            throw new JboException(e.getMessage());
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    throw new JboException(e.getMessage());
                }
            }
        }
    }


    public void setDate_format(String date_format) {
        this.date_format = date_format;
    }

    public String getDate_format() {
        if (date_format == null && session.getAttribute("date_format") != null)
            return session.getAttribute("date_format").toString();
        else
            return date_format;
    }

    public void setNum_format(String num_format) {
        this.num_format = num_format;
    }

    public String getNum_format() {
        return num_format;
    }

    public void setLang_path(String lang_path) {
        this.lang_path = lang_path;
    }

    public String getLang_path() {
        return lang_path;
    }

    public void setLang_file(String lang_file) {
        this.lang_file = lang_file;
    }

    public String getLang_file() {
        return lang_file;
    }

    public void setAmt_digit(Integer amt_digit) {
        this.amt_digit = amt_digit;
    }

    public Integer getAmt_digit() {
        if (amt_digit == null && session.getAttribute("amount_digit") != null)
            return Integer.parseInt(session.getAttribute("amount_digit").toString());
        else
            return amt_digit;
    }

    public void setCurr_digit(Integer curr_digit) {
        this.curr_digit = curr_digit;
    }

    public Integer getCurr_digit() {
        //   System.out.println("Session--" + session);
        if (curr_digit == null && session.getAttribute("currency_digit") != null)
            return Integer.parseInt(session.getAttribute("currency_digit").toString());
        else
            return curr_digit;
    }

    public void setRate_digit(Integer rate_digit) {
        this.rate_digit = rate_digit;
    }

    public Integer getRate_digit() {
        if (rate_digit == null && (session.getAttribute("rate_digit") != null))
            return Integer.parseInt(session.getAttribute("rate_digit").toString());
        else
            return rate_digit;
    }

    public void setQty_digit(Integer qty_digit) {
        this.qty_digit = qty_digit;
    }

    public Integer getQty_digit() {
        if (qty_digit == null && session.getAttribute("qty_digit") != null)
            return Integer.parseInt(session.getAttribute("qty_digit").toString());
        else
            return qty_digit;
    }

    public Integer getDisc_digit() {
        if (disc_digit == null && session.getAttribute("disc_digit") != null)
            return Integer.parseInt(session.getAttribute("disc_digit").toString());
        else
            return disc_digit;
    }

    public void amtDigitChange(ValueChangeEvent valueChangeEvent) {

        if (valueChangeEvent.getNewValue() != null) {
            setAmt_digit(Integer.parseInt(valueChangeEvent.getNewValue().toString()));
            frmtSetCB.setValue(Boolean.TRUE);
            AdfFacesContext.getCurrentInstance().addPartialTarget(frmtSetCB);
        }
    }

    public void currDigitChange(ValueChangeEvent valueChangeEvent) {

        if (valueChangeEvent.getNewValue() != null) {
            setCurr_digit(Integer.parseInt(valueChangeEvent.getNewValue().toString()));
            frmtSetCB.setValue(Boolean.TRUE);
            AdfFacesContext.getCurrentInstance().addPartialTarget(frmtSetCB);
        }
    }

    public void rateDigitChange(ValueChangeEvent valueChangeEvent) {

        if (valueChangeEvent.getNewValue() != null) {
            setRate_digit(Integer.parseInt(valueChangeEvent.getNewValue().toString()));
            frmtSetCB.setValue(Boolean.TRUE);
            AdfFacesContext.getCurrentInstance().addPartialTarget(frmtSetCB);
        }
    }

    public void qtyDigitChange(ValueChangeEvent valueChangeEvent) {

        if (valueChangeEvent.getNewValue() != null) {
            setQty_digit(Integer.parseInt(valueChangeEvent.getNewValue().toString()));
            frmtSetCB.setValue(Boolean.TRUE);
            AdfFacesContext.getCurrentInstance().addPartialTarget(frmtSetCB);
        }
    }


    public void dateFormatChange(ValueChangeEvent valueChangeEvent) {

        if (valueChangeEvent.getNewValue() != null) {
            setDate_format(valueChangeEvent.getNewValue().toString());
            dtSetCB.setValue(Boolean.TRUE);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dtSetCB);
        }
    }

    public void displayStyChange(ValueChangeEvent valueChangeEvent) {

        if (valueChangeEvent.getNewValue() != null) {
            dsplySetCB.setValue(Boolean.TRUE);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dsplySetCB);
        }
    }

    public void setCloud_id(String cloud_id) {
        this.cloud_id = cloud_id;
    }

    public String getCloud_id() {
        return cloud_id;
    }

    public void skinVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            setSkinFamily(valueChangeEvent.getNewValue().toString());
            reloadThePage();
        }
    }

    public void setDtSetCB(RichSelectBooleanCheckbox dtSetCB) {
        this.dtSetCB = dtSetCB;
    }

    public RichSelectBooleanCheckbox getDtSetCB() {
        return dtSetCB;
    }

    public void setLanSetCB(RichSelectBooleanCheckbox lanSetCB) {
        this.lanSetCB = lanSetCB;
    }

    public RichSelectBooleanCheckbox getLanSetCB() {
        return lanSetCB;
    }

    public void setDsplySetCB(RichSelectBooleanCheckbox dsplySetCB) {
        this.dsplySetCB = dsplySetCB;
    }

    public RichSelectBooleanCheckbox getDsplySetCB() {
        return dsplySetCB;
    }

    public void setFrmtSetCB(RichSelectBooleanCheckbox frmtSetCB) {
        this.frmtSetCB = frmtSetCB;
    }

    public RichSelectBooleanCheckbox getFrmtSetCB() {
        return frmtSetCB;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        if (ADFContext.getCurrent().getSecurityContext().getUserName() != null)
            return Integer.parseInt(ADFContext.getCurrent().getSecurityContext().getUserName());
        else
            return userId;
    }

    public void setAmt_format(String amt_format) {
        this.amt_format = amt_format;
    }

    public String getAmt_format() {
        return amt_format;
    }

    public void setCurr_format(String curr_format) {
        this.curr_format = curr_format;
    }

    public String getCurr_format() {
        return curr_format;
    }

    public void setRate_format(String rate_format) {
        this.rate_format = rate_format;
    }

    public String getRate_format() {
        return rate_format;
    }

    public void setQty_format(String qty_format) {
        this.qty_format = qty_format;
    }

    public String getQty_format() {
        return qty_format;
    }

    public void setRegisteredNM(String registeredNM) {
        this.registeredNM = registeredNM;
    }

    public String getRegisteredNM() {
        return registeredNM;
    }

    public ArrayList<SelectItem> getAppLang() {

        ViewObject vo = getAm().getAppLangLOV();
        vo.setRangeSize(-1);
        Row[] r = vo.getAllRowsInRange();
        ArrayList<SelectItem> appLang = new ArrayList<SelectItem>();

        for (Row rw : r) {
            appLang.add(new SelectItem(rw.getAttribute("LangDesc").toString()));

            if (((Integer)(rw.getAttribute("LangId"))).equals(this.lang_id))
                this.lang = rw.getAttribute("LangDesc").toString();
        }
        return appLang;
    }

    public void langVCE(ValueChangeEvent valueChangeEvent) {

        String langDesc;

        if (valueChangeEvent.getNewValue() != null) {
            lanSetCB.setValue(Boolean.TRUE);
            AdfFacesContext.getCurrentInstance().addPartialTarget(lanSetCB);
            langDesc = valueChangeEvent.getNewValue().toString();
            //     System.out.println("lang desc in VCE--->"+langDesc);
            RowQualifier rq = new RowQualifier(getAm().getAppLangLOV());
            rq.setWhereClause("LangDesc= '" + langDesc + "' and LangSlocId = " + 1 + " and LangCldId = '" + cloud_id +
                              "'");
            Row[] xx = getAm().getAppLangLOV().getFilteredRows(rq);
            if (xx.length > 0) {
                lang_id = Integer.parseInt(xx[0].getAttribute("LangId").toString());
                //       System.out.println("lang id in VCE---->"+lang_id);
                setLang_id(lang_id);
            }
            setLang(lang);

        }
    }

    public Integer getLang_id() {


        return lang_id;
    }

    public void setLang_id(Integer lang_id) {
        this.lang_id = lang_id;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLang() {
        return lang;
    }

    public EbizMainAppAMImpl getAm() {
        //System.out.println("EbizMainAppAMImpl_dashBoard");
        return (EbizMainAppAMImpl)resolvElDC("");
    }

    public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data.EbizMainAppAMDataControl.dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }

    public void setSettingsPopUp(RichPopup settingsPopUp) {
        this.settingsPopUp = settingsPopUp;
    }

    public RichPopup getSettingsPopUp() {
        return settingsPopUp;
    }

    public void showSettingPopUpACE(ActionEvent actionEvent) {
        // Add event code here...
        //System.out.println("inside showSettingPopUpACE ");
        //getGlobalParameter();

        showPopup(settingsPopUp, true);
    }


    public void showPopup(RichPopup popup, boolean visible) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && popup != null) {
                String popupId = popup.getClientId(context);
                if (popupId != null) {
                    StringBuilder script = new StringBuilder();
                    script.append("var popup = AdfPage.PAGE.findComponent('").append(popupId).append("'); ");
                    if (visible) {
                        script.append("if (!popup.isPopupVisible()) { ").append("popup.show();}");

                    } else {
                        script.append("if (popup.isPopupVisible()) { ").append("popup.hide();}");
                    }
                    ExtendedRenderKitService erks =
                        Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);
                    erks.addScript(context, script.toString());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public Object resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        Object Message = valueExp.getValue(elContext);
        return Message;
    }

    public void resetLanguage() {
        System.out.println("Resetting Language");
        session.setAttribute("lang_id", this.lang_id);

        try {
            String driver=getdrivername();
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                lang_file =
                        (callStoredFunction(STRING, "call FN_GET_USR_DEF_LANG_RES_FILE(?,?,?)", new Object[] { getUsrCldId(),
                                                                                                              getSloc_id(),
                                                                                                              getUser_id() }).toString());   
            }
            else
            {
            lang_file =
                    (callStoredFunction(STRING, "APP.FN_GET_USR_DEF_LANG_RES_FILE(?,?,?)", new Object[] { getUsrCldId(),
                                                                                                          getSloc_id(),
                                                                                                          getUser_id() }).toString());
            }
            System.out.println("lang_file from funtion------>" + lang_file);

            // setLang_file(lang_file);
            session.setAttribute("lang_file", lang_file);

        } catch (Exception e) {
            System.out.println("Error @ EbizMainApplication-->DashBoardBean-->getGlobalParameter()-->FN_GET_USR_DEF_LANG_RES_FILE(?,?,?) ");
            System.out.println("parameter values-->" + " cloud id : " + getUsrCldId() + " sloc id : " + getSloc_id() +
                               " user id " + getUser_id());
            String driver=getdrivername();
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                lang_file =
                        (callStoredFunction(STRING, "call FN_GET_GLBL_LANG_RES_FILE(?)", new Object[] { getLang_id() }).toString());   
            }
            else
            {
            lang_file =
                    (callStoredFunction(STRING, "APP.FN_GET_GLBL_LANG_RES_FILE(?)", new Object[] { getLang_id() }).toString());
            }
            System.out.println("Using Global lang_file from funtion------>" + lang_file);

            session.setAttribute("lang_file", lang_file);
            //setLang_file(lang_file);
            e.printStackTrace();
        }

        System.out.println((resolvEl("#{sessionScope.lang_file}")).toString());

        try {
            String driver=getdrivername();
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                lang_path =
                        (callStoredFunction(STRING, "call FN_GET_APP_RESOURCE_PATH(?)", new Object[] { getSloc_id() }).toString());   
            }
            else
            {
            lang_path =
                    (callStoredFunction(STRING, "APP.FN_GET_APP_RESOURCE_PATH(?)", new Object[] { getSloc_id() }).toString());
            }

            System.out.println("lang_path from funtion------>" + lang_path);
            setLang_path(lang_path);
            //userSession.setAttribute("lang_path", lang_path);

        } catch (Exception e) {
            System.out.println("Error @ EbizMainApplication-->DashBoardBean-->getGlobalParameter()-->FN_GET_APP_RESOURCE_PATH(?) ");
            System.out.println("parameter values-->" + " sloc id :" + getSloc_id());
            setLang_path("D:\\Resource\\");
            //userSession.setAttribute("lang_path", lang_path);
            e.printStackTrace();
        }

    }

    public String getUsrCldId() {
        return (String)resolvEl("#{sessionScope.usrCldId}");
    }

    public Integer getSloc_id() {
        Integer sloc_id = 1;
        if (session.getAttribute("sloc_Id") != null)
            return Integer.parseInt(session.getAttribute("sloc_Id").toString());
        else
            return sloc_id;
    }


    public Integer getUser_id() {
        Integer user_id = Integer.parseInt(ADFContext.getCurrent().getSecurityContext().getUserName());
        return user_id;
    }

    public HttpSession getSession() {
        ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
        ;
        HttpSession userSession = (HttpSession)ectx.getSession(false);
        return userSession;
    }

    public void setDisc_digit(Integer disc_digit) {
        this.disc_digit = disc_digit;
    }

    public void setDisc_format(String disc_format) {
        this.disc_format = disc_format;
    }

    public String getDisc_format() {
        return disc_format;
    }

    public void discDigitChange(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            //setDisc_digit(Integer.parseInt(valueChangeEvent.getNewValue().toString()));
            frmtSetCB.setValue(Boolean.TRUE);
            AdfFacesContext.getCurrentInstance().addPartialTarget(frmtSetCB);
        }
    }
}
