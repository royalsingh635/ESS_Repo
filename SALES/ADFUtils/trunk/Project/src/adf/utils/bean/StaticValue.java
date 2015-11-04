package adf.utils.bean;

import adf.utils.ebiz.EbizParams;

import java.sql.SQLException;

import java.util.Calendar;

import oracle.adf.share.logging.ADFLogger;

import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;

/**
 *  This class contains that static values that can be used in Applications.
 *  This contains methods for formatting and truncating Timestamp to proper proper format.
 */
public class StaticValue {
    public final static Number NUMBER_HUNDRED = new Number(100);
    public final static Number NUMBER_ZERO = new Number(0);
    public final static int NUMBER_PRECISION = 26;
    public final static int NUMBER_SCALE= 26;
    private static ADFLogger _log = ADFLogger.createADFLogger(EbizParams.class);

    
    public StaticValue() {
        super();
    }
    
    /**
     * Method to fetch new truncated date with no current Timestamp as hh:mm 00:00:00).
     * @return
     */
    public static Timestamp getTruncatedCurrDt(){
        Timestamp t = new Timestamp(System.currentTimeMillis());
        try {
            t = new Timestamp(t.dateValue() + " 00:00:00");
        } catch (SQLException e) {
            _log.severe("Unable to get Current Date Timestamp !"+e.getMessage());
        }
        return t;
    }
    
    /**
     * Method to fetch new truncated date with proper current Timestamp as hh:mm 00:00:00).
     * @return
     */
    public static Timestamp getCurrDtWidTimestamp(){
        Timestamp t = new Timestamp(System.currentTimeMillis());
        t = new Timestamp(t.toString().substring(0,19));
        return t;
    }
    
    /**
     * Method to fetch new truncated Date with no current Timestamp as hh:mm 00:00:00).
     * @param t
     * @return
     */
    public static Timestamp getTruncatedDt(Timestamp t){
        try {
            t = new Timestamp(t.dateValue() + " 00:00:00");
        } catch (SQLException e) {
            _log.severe("Unable to get Current Date Timestamp !"+e.getMessage());
        }
        return t;
    }
    
    /**
     * Method to fetch new truncated date with proper currrent Timestamp with hh:mm 00:00:00)
     * @param t
     * @return
     */
    public static Timestamp getDtWithTimestamp(Timestamp t){
        return new Timestamp(t.toString().substring(0,19));
    }
    
    /**
     * Method to add or substract days in the given timestap.
     * ts - Base Timestamp i.e. passed timestamp
     * opType - Defines was operation you want to perform
     * A - Add
     * S - Substration
     * noOfDays - No. of days that needs to be added or Substracted.
     *
     * @param ts
     * @param opType
     * @param noOfDays
     * @return
     */
    public static Timestamp addOrSubDaysWithTimestamp(Timestamp ts,String opType, Integer noOfDays) {
        java.sql.Date stDt = null;
        try {
            stDt = ts.dateValue();
        } catch (SQLException e) {
        }
        Calendar calStDate = Calendar.getInstance();
        calStDate.setTime(stDt);
        if("A".equals(opType)){
            calStDate.add(Calendar.DATE, noOfDays);
        }else if("S".equals(opType)){
            calStDate.add(Calendar.DATE, -noOfDays);
        }
        return getDtWithTimestamp(new Timestamp(calStDate.getTime()));

    }

}
