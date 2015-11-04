package bdgfinancebudgetapp.view.dc;

import java.io.Serializable;

import java.util.ArrayList;

import oracle.adf.share.logging.ADFLogger;

import oracle.binding.AttributeContext;
import oracle.binding.RowContext;


public class DataObject {
    private static ADFLogger _log = ADFLogger.createADFLogger(DataObject.class);
    private boolean filterChanged = false;

    /*** ArrayList to store selected Region **/
    private ArrayList<TagDC> regionList = new ArrayList<TagDC>();
    private ArrayList<CommonTagDC> commonList = new ArrayList<CommonTagDC>();

    public void setCommonList(ArrayList<CommonTagDC> commonList) {
        this.commonList = commonList;
    }

    public ArrayList<CommonTagDC> getCommonList() {
        return commonList;
    }

    /*** ArrayList to store selected Employee **/
    private ArrayList<TagDC> employeeList = new ArrayList<TagDC>();

    /*** ArrayList to store selected Employee **/
    private ArrayList<TagDC> customerList = new ArrayList<TagDC>();

    public DataObject() {
        super();
    }

    public Serializable createSnapshot() {
        return null;
    }

    public void restoreSnapshot(Serializable p0) {
    }

    public void removeSnapshot(Serializable p0) {
    }

    public boolean isTransactionDirty() {
        return false;
    }

    public void rollbackTransaction() {
    }

    public void commitTransaction() {
    }

    public boolean setAttributeValue(AttributeContext p0, Object p1) {
        return false;
    }

    public Object createRowData(RowContext p0) {
        return null;
    }

    public Object registerDataProvider(RowContext p0) {
        return null;
    }

    public boolean removeRowData(RowContext p0) {
        return false;
    }

    public void validate() {
    }

    /**
     *  Method to clear all the filters
     */
    public void clearAllSearchList() {
        this.filterChanged = true;
        this.regionList.clear();
        this.employeeList.clear();
        this.customerList.clear();
    }

    /*** START *************************************************** Region LIST **********************************************************/

    /**
     * Custom Method to Add Region to the Search List
     */
    public void addRegion(StringBuffer regionId, StringBuffer regionName) {
        _log.info("RegionName : " + regionName + " Region : " + regionId);
        Integer i = 1;
        if (regionId != null || regionName != null) {
            if (!regionName.toString().equals("") || !regionId.toString().equals("")) {
                for (TagDC dc : regionList) {
                    if (dc.getIdSecond().toString().equals("-1")) {
                        _log.info("Second Id is -1");
                        StringBuffer newRegionNm = new StringBuffer("");
                        if (regionName.length() >= 12) {
                            newRegionNm = new StringBuffer(regionName.substring(0, 12) + "..");
                        } else {
                            newRegionNm = new StringBuffer(regionName);
                        }
                        _log.info("Second Id is -1 so " + regionId);
                        dc.setIdSecond(regionId);
                        dc.setDescSecond(regionName);
                        dc.setValueSecond(newRegionNm);
                        i = 2;
                        break;
                    } else if (dc.getIdFirst().toString().equals("-1")) {
                        _log.info("First Id is -1");
                        StringBuffer newRegionNm = new StringBuffer("");
                        if (regionName.length() >= 12) {
                            newRegionNm = new StringBuffer(regionName.substring(0, 12) + "..");
                        } else {
                            newRegionNm = regionName;
                        }
                        _log.info("First Id is -1 so " + regionId);
                        dc.setIdFirst(regionId);
                        dc.setDescFirst(regionName);
                        dc.setValueFirst(newRegionNm);
                        i = 2;
                        break;

                    }
                }
                if (i == 1) {
                    _log.info("New Id Added.");
                    StringBuffer newRegionNm = new StringBuffer("");
                    if (regionName.length() >= 12) {
                        newRegionNm = new StringBuffer(regionName.substring(0, 12) + "..");
                    } else {
                        newRegionNm = new StringBuffer(regionName);
                    }
                    regionList.add(new TagDC(regionId, newRegionNm, regionName, new StringBuffer("-1"),
                                             new StringBuffer(""), new StringBuffer("")));

                }
            }
        }
        _log.info("List is=>" + regionList);
    }

    /**
     * Method to remove Region from Search List
     *
     */
    public void removeRegion(StringBuffer regionId) {
        TagDC removeRegion = null;
        for (TagDC dc : regionList) {
            if (dc.getIdFirst().toString().equals(regionId.toString())) {
                _log.info("First Id removed.");
                dc.setIdFirst(new StringBuffer("-1"));
                dc.setValueFirst(new StringBuffer(""));
                if (dc.getIdFirst().toString().equals("-1") && dc.getIdSecond().toString().equals("-1")) {
                    _log.info("First & Second both Id's removed.");
                    removeRegion = dc;
                    break;
                }
                break;
            }
            if (dc.getIdSecond().toString().equals(regionId.toString())) {
                _log.info("Second Id removed.");
                dc.setIdSecond(new StringBuffer("-1"));
                dc.setValueSecond(new StringBuffer(""));
                if (dc.getIdFirst().toString().equals("-1") && dc.getIdSecond().toString().equals("-1")) {
                    _log.info("First & Second both Id's removed.");
                    removeRegion = dc;
                    break;
                }
                break;
            }
            if (dc.getIdFirst().toString().equals("-1") && dc.getIdSecond().toString().equals("-1")) {
                _log.info("First & Second both Id's removed.");
                removeRegion = dc;
                break;
            }
        }
        if (removeRegion != null) {
            _log.info("Removed Tag is :" + removeRegion.getValueFirst() + " " + removeRegion.getValueSecond());
            regionList.remove(removeRegion);
        }
    }

    public StringBuffer valuesOfINClauseTagDC(ArrayList<TagDC> searchList) {
        StringBuffer inClause = new StringBuffer("");
        if (searchList != null && searchList.size() > 0) {
            Integer count = 1;
            for (TagDC dc : searchList) {
                if (!dc.getIdFirst().toString().equals("-1")) {
                    inClause = inClause.append(" " + dc.getIdFirst());
                }
                if (!dc.getIdFirst().toString().equals("-1") && !dc.getIdSecond().toString().equals("-1")) {
                    inClause = inClause.append(",");
                }
                if (!dc.getIdSecond().toString().equals("-1")) {
                    inClause = inClause.append(" " + dc.getIdSecond());
                }
                if (count < searchList.size()) {
                    inClause = inClause.append(",");
                }
                count = count + 1;
            }

        }
        return inClause;
    }

    /**
     * Method to clear CustomerList
     */
    public void clearRegionList() {
        initFilterChange(true);
        regionList.clear();
    }

    /**
     * Method to Initialize and retrieve the filter change.
     */
    public void initFilterChange(boolean val) {
        filterChanged = val;
    }

    /**
     * Method to return RegionList which is used for Search Field
     * @return
     */
    public ArrayList<TagDC> fetchRegionList() {
        return regionList;
    }

    /*** START *************************************************** Employee LIST **********************************************************/

    /**
     * Custom Method to Add Employee to the Search List
     */
    public void addEmployee(StringBuffer employeeId, StringBuffer employeeName) {
        _log.info("EmployeeName : " + employeeName + " Employee : " + employeeId);
        Integer i = 1;
        if (employeeId != null || employeeName != null) {
            if (!employeeName.toString().equals("") || !employeeId.toString().equals("")) {
                for (TagDC dc : employeeList) {
                    if (dc.getIdSecond().toString().equals("-1")) {
                        _log.info("Second Id is -1");
                        StringBuffer newEmployeeNm = new StringBuffer("");
                        if (employeeName.length() >= 12) {
                            newEmployeeNm = new StringBuffer(employeeName.substring(0, 12) + "..");
                        } else {
                            newEmployeeNm = new StringBuffer(employeeName);
                        }
                        _log.info("Second Id is -1 so " + employeeId);
                        dc.setIdSecond(employeeId);
                        dc.setDescSecond(employeeName);
                        dc.setValueSecond(newEmployeeNm);
                        i = 2;
                        break;
                    } else if (dc.getIdFirst().toString().equals("-1")) {
                        _log.info("First Id is -1");
                        StringBuffer newEmployeeNm = new StringBuffer("");
                        if (employeeName.length() >= 12) {
                            newEmployeeNm = new StringBuffer(employeeName.substring(0, 12) + "..");
                        } else {
                            newEmployeeNm = employeeName;
                        }
                        _log.info("First Id is -1 so " + employeeId);
                        dc.setIdFirst(employeeId);
                        dc.setDescFirst(employeeName);
                        dc.setValueFirst(newEmployeeNm);
                        i = 2;
                        break;

                    }
                }
                if (i == 1) {
                    _log.info("New Id Added.");
                    StringBuffer newEmployeeNm = new StringBuffer("");
                    if (employeeName.length() >= 12) {
                        newEmployeeNm = new StringBuffer(employeeName.substring(0, 12) + "..");
                    } else {
                        newEmployeeNm = new StringBuffer(employeeName);
                    }
                    employeeList.add(new TagDC(employeeId, newEmployeeNm, employeeName, new StringBuffer("-1"),
                                               new StringBuffer(""), new StringBuffer("")));

                }
            }
        }
        _log.info("List is=>" + employeeList);
    }

    /**
     * Method to remove Employee from Search List
     *
     */
    public void removeEmployee(StringBuffer employeeId) {
        TagDC removeEmployee = null;
        for (TagDC dc : employeeList) {
            if (dc.getIdFirst().toString().equals(employeeId.toString())) {
                _log.info("First Id removed.");
                dc.setIdFirst(new StringBuffer("-1"));
                dc.setValueFirst(new StringBuffer(""));
                if (dc.getIdFirst().toString().equals("-1") && dc.getIdSecond().toString().equals("-1")) {
                    _log.info("First & Second both Id's removed.");
                    removeEmployee = dc;
                    break;
                }
                break;
            }
            if (dc.getIdSecond().toString().equals(employeeId.toString())) {
                _log.info("Second Id removed.");
                dc.setIdSecond(new StringBuffer("-1"));
                dc.setValueSecond(new StringBuffer(""));
                if (dc.getIdFirst().toString().equals("-1") && dc.getIdSecond().toString().equals("-1")) {
                    _log.info("First & Second both Id's removed.");
                    removeEmployee = dc;
                    break;
                }
                break;
            }
            if (dc.getIdFirst().toString().equals("-1") && dc.getIdSecond().toString().equals("-1")) {
                _log.info("First & Second both Id's removed.");
                removeEmployee = dc;
                break;
            }
        }
        if (removeEmployee != null) {
            _log.info("Removed Tag is :" + removeEmployee.getValueFirst() + " " + removeEmployee.getValueSecond());
            employeeList.remove(removeEmployee);
        }
    }

    /**
     * Method to clear EmployeeList
     */
    public void clearEmployeeList() {
        initFilterChange(true);
        employeeList.clear();
    }

    /**
     * Method to return EmployeeList which is used for Search Field
     * @return
     */
    public ArrayList<TagDC> fetchEmployeeList() {
        return employeeList;
    }

    /*** START *************************************************** Customer LIST **********************************************************/

    /**
     * Custom Method to Add Customer to the Search List
     */
    public void addCustomer(StringBuffer customerId, StringBuffer customerName) {
        _log.info("CustomerName : " + customerName + " Customer : " + customerId);
        Integer i = 1;
        if (customerId != null || customerName != null) {
            if (!customerName.toString().equals("") || !customerId.toString().equals("")) {
                for (TagDC dc : customerList) {
                    if (dc.getIdSecond().toString().equals("-1")) {
                        _log.info("Second Id is -1");
                        StringBuffer newCustomerNm = new StringBuffer("");
                        if (customerName.length() >= 12) {
                            newCustomerNm = new StringBuffer(customerName.substring(0, 12) + "..");
                        } else {
                            newCustomerNm = new StringBuffer(customerName);
                        }
                        _log.info("Second Id is -1 so " + customerId);
                        dc.setIdSecond(customerId);
                        dc.setDescSecond(customerName);
                        dc.setValueSecond(newCustomerNm);
                        i = 2;
                        break;
                    } else if (dc.getIdFirst().toString().equals("-1")) {
                        _log.info("First Id is -1");
                        StringBuffer newCustomerNm = new StringBuffer("");
                        if (customerName.length() >= 12) {
                            newCustomerNm = new StringBuffer(customerName.substring(0, 12) + "..");
                        } else {
                            newCustomerNm = customerName;
                        }
                        _log.info("First Id is -1 so " + customerId);
                        dc.setIdFirst(customerId);
                        dc.setDescFirst(customerName);
                        dc.setValueFirst(newCustomerNm);
                        i = 2;
                        break;

                    }
                }
                if (i == 1) {
                    _log.info("New Id Added.");
                    StringBuffer newCustomerNm = new StringBuffer("");
                    if (customerName.length() >= 12) {
                        newCustomerNm = new StringBuffer(customerName.substring(0, 12) + "..");
                    } else {
                        newCustomerNm = new StringBuffer(customerName);
                    }
                    customerList.add(new TagDC(customerId, newCustomerNm, customerName, new StringBuffer("-1"),
                                               new StringBuffer(""), new StringBuffer("")));

                }
            }
        }
        _log.info("List is=>" + customerList);
    }

    /**
     * Method to remove Customer from Search List
     *
     */
    public void removeCustomer(StringBuffer customerId) {
        TagDC removeCustomer = null;
        for (TagDC dc : customerList) {
            if (dc.getIdFirst().toString().equals(customerId.toString())) {
                _log.info("First Id removed.");
                dc.setIdFirst(new StringBuffer("-1"));
                dc.setValueFirst(new StringBuffer(""));
                if (dc.getIdFirst().toString().equals("-1") && dc.getIdSecond().toString().equals("-1")) {
                    _log.info("First & Second both Id's removed.");
                    removeCustomer = dc;
                    break;
                }
                break;
            }
            if (dc.getIdSecond().toString().equals(customerId.toString())) {
                _log.info("Second Id removed.");
                dc.setIdSecond(new StringBuffer("-1"));
                dc.setValueSecond(new StringBuffer(""));
                if (dc.getIdFirst().toString().equals("-1") && dc.getIdSecond().toString().equals("-1")) {
                    _log.info("First & Second both Id's removed.");
                    removeCustomer = dc;
                    break;
                }
                break;
            }
            if (dc.getIdFirst().toString().equals("-1") && dc.getIdSecond().toString().equals("-1")) {
                _log.info("First & Second both Id's removed.");
                removeCustomer = dc;
                break;
            }
        }
        if (removeCustomer != null) {
            _log.info("Removed Tag is :" + removeCustomer.getValueFirst() + " " + removeCustomer.getValueSecond());
            customerList.remove(removeCustomer);
        }
    }

    /**
     * Method to clear CustomerList
     */
    public void clearCustomerList() {
        initFilterChange(true);
        customerList.clear();
    }

    /**
     * Method to return CustomerList which is used for Search Field
     * @return
     */
    public ArrayList<TagDC> fetchCustomerList() {
        return customerList;
    }


    /*** START *************************************************** Common LIST **********************************************************/

    /**
     * Custom Method to Add Filter to the Common List
     */
    public void addFilterToList(StringBuffer filterId, StringBuffer filterVal, StringBuffer filterName) {
        _log.info("FilterId : " + filterId + " FilterVal : " + filterVal + " FilterName:" + filterName);
        Integer i = 1;
        if (filterId != null && filterVal != null && filterName != null) {
            if (!filterVal.toString().equals("") || !filterName.toString().equals("") ||
                !filterId.toString().equals("")) {
                for (CommonTagDC dc : commonList) {
                    if (dc.getStructIdSecond().toString().equals(filterId) &&
                        dc.getStructValSecond().toString().equals("-1")) {
                        _log.info("Second Id is -1");
                        StringBuffer newFilterNm = new StringBuffer("");
                        if (filterName.length() >= 12) {
                            newFilterNm = new StringBuffer(filterName.substring(0, 12) + "..");
                        } else {
                            newFilterNm = new StringBuffer(filterName);
                        }
                        _log.info("Second Id is -1 so " + filterVal);
                        dc.setStructIdSecond(filterId);
                        dc.setStructValSecond(filterVal);
                        dc.setStructValNmSecond(filterName);
                        dc.setStructValNmDtlSecond(newFilterNm);
                        i = 2;
                        break;
                    } else if (dc.getStructIdSecond().toString().equals(filterId) &&
                               dc.getStructValFirst().toString().equals("-1")) {
                        _log.info("First Id is -1");
                        StringBuffer newFilterNm = new StringBuffer("");
                        if (filterName.length() >= 12) {
                            newFilterNm = new StringBuffer(filterName.substring(0, 12) + "..");
                        } else {
                            newFilterNm = filterName;
                        }
                        _log.info("First Id is -1 so " + filterVal);
                        dc.setStructIdFirst(filterId);
                        dc.setStructValFirst(filterVal);
                        dc.setStructValNmFirst(filterName);
                        dc.setStructValNmDtlFirst(newFilterNm);
                        i = 2;
                        break;

                    }
                }
                if (i == 1) {
                    _log.info("New Id Added.");
                    StringBuffer newFilterNm = new StringBuffer("");
                    if (filterName.length() >= 12) {
                        newFilterNm = new StringBuffer(filterName.substring(0, 12) + "..");
                    } else {
                        newFilterNm = new StringBuffer(filterName);
                    }
                    commonList.add(new CommonTagDC(filterId, filterVal, filterName, newFilterNm, filterId,
                                                   new StringBuffer("-1"), new StringBuffer(""), new StringBuffer("")));

                }
            }
        }
        _log.info("List is=>" + commonList);
    }

    /**
     * Method to remove Region from Search List
     *
     */
    public void removeFilter(StringBuffer regionId) {
        TagDC removeRegion = null;
        for (TagDC dc : regionList) {
            if (dc.getIdFirst().toString().equals(regionId.toString())) {
                _log.info("First Id removed.");
                dc.setIdFirst(new StringBuffer("-1"));
                dc.setValueFirst(new StringBuffer(""));
                if (dc.getIdFirst().toString().equals("-1") && dc.getIdSecond().toString().equals("-1")) {
                    _log.info("First & Second both Id's removed.");
                    removeRegion = dc;
                    break;
                }
                break;
            }
            if (dc.getIdSecond().toString().equals(regionId.toString())) {
                _log.info("Second Id removed.");
                dc.setIdSecond(new StringBuffer("-1"));
                dc.setValueSecond(new StringBuffer(""));
                if (dc.getIdFirst().toString().equals("-1") && dc.getIdSecond().toString().equals("-1")) {
                    _log.info("First & Second both Id's removed.");
                    removeRegion = dc;
                    break;
                }
                break;
            }
            if (dc.getIdFirst().toString().equals("-1") && dc.getIdSecond().toString().equals("-1")) {
                _log.info("First & Second both Id's removed.");
                removeRegion = dc;
                break;
            }
        }
        if (removeRegion != null) {
            _log.info("Removed Tag is :" + removeRegion.getValueFirst() + " " + removeRegion.getValueSecond());
            regionList.remove(removeRegion);
        }
    }

    public StringBuffer valuesOfINClauseCommonTagDC(ArrayList<TagDC> searchList) {
        StringBuffer inClause = new StringBuffer("");
        if (searchList != null && searchList.size() > 0) {
            Integer count = 1;
            for (TagDC dc : searchList) {
                if (!dc.getIdFirst().toString().equals("-1")) {
                    inClause = inClause.append(" " + dc.getIdFirst());
                }
                if (!dc.getIdFirst().toString().equals("-1") && !dc.getIdSecond().toString().equals("-1")) {
                    inClause = inClause.append(",");
                }
                if (!dc.getIdSecond().toString().equals("-1")) {
                    inClause = inClause.append(" " + dc.getIdSecond());
                }
                if (count < searchList.size()) {
                    inClause = inClause.append(",");
                }
                count = count + 1;
            }

        }
        return inClause;
    }

    /**
     * Method to clear CustomerList
     */
    public void clearFilterList() {
        initFilterChange(true);
        //Add condition of Struct Id
        commonList.clear();
    }


    /**
     * Method to return RegionList which is used for Search Field
     * @return
     */
    public ArrayList<CommonTagDC> fetchCommonList() {
        return commonList;
    }
}
