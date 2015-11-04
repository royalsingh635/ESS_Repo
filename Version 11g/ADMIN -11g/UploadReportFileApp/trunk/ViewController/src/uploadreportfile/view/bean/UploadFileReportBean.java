package uploadreportfile.view.bean;

import com.javamex.arcmexer.ArchiveEntry;
import com.javamex.arcmexer.ArchiveFormatException;
import com.javamex.arcmexer.ArchiveReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.input.RichInputFile;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.util.ResetUtils;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import org.apache.commons.lang3.StringUtils;
import org.apache.myfaces.trinidad.model.UploadedFile;


public class UploadFileReportBean {
    private RichInputText reportNamePgBind;
    private RichSelectOneChoice applicationNamePgBind;
    private RichInputText authenticationPgBind;
    private RichInputFile browseReportPgBind;
    private String path;
    private String description;


    public UploadFileReportBean() {
    }

    public void setReportNamePgBind(RichInputText reportNamePgBind) {
        this.reportNamePgBind = reportNamePgBind;
    }

    public RichInputText getReportNamePgBind() {
        return reportNamePgBind;
    }

    public void setApplicationNamePgBind(RichSelectOneChoice applicationNamePgBind) {
        this.applicationNamePgBind = applicationNamePgBind;
    }

    public RichSelectOneChoice getApplicationNamePgBind() {
        return applicationNamePgBind;
    }

    public void setAuthenticationPgBind(RichInputText authenticationPgBind) {
        this.authenticationPgBind = authenticationPgBind;
    }

    public RichInputText getAuthenticationPgBind() {
        return authenticationPgBind;
    }

    public void setBrowseReportPgBind(RichInputFile browseReportPgBind) {
        this.browseReportPgBind = browseReportPgBind;
    }

    public RichInputFile getBrowseReportPgBind() {
        return browseReportPgBind;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Used to get path where Reports need to be stored
     */

    public void returnRptStorPath() {
        BindingContainer bindings = this.getBindings();
        bindings.getOperationBinding("getReportPathFromDB").execute();
        setPath(bindings.getOperationBinding("getReportPathFromDB").getResult().toString());
        System.out.println("File for DB is " + this.getPath());

        // Will create new folder for the Reports which were downloaded from portal and store reports at that location
        File loc = new File(this.getPath() + "Portal");
        System.out.println("Exists " + loc.isDirectory());
        if (!loc.isDirectory()) {
            loc.mkdirs();
            System.out.println("Folder has been created");
        }
        setPath(loc + "/");
        //setPath(this.getPath() + "Portal/");
        System.out.println("After created is " + this.getPath());
    }

    /**
     * To Unzip zip file
     * which have password
     */
    public Boolean unzipFileWithPassword(UploadedFile up) throws Exception {
        Boolean retVal = false;
        ArchiveReader r = null;
        FileOutputStream fos = null;
        InputStream in = null;

        try {
            r = ArchiveReader.getReader(up.getInputStream());
            r.setDefaultPassword("essindia"); // Password of Zip File which is encrypted with...
            ArchiveEntry entry;
            System.out.println("Outside loop");
            String opFilePath = null;
            File f = null;

            while ((entry = r.nextEntry()) != null) {
                System.out.println("Entry is " + entry.getCompressedLength());
                String filename = entry.getFilename();
                System.out.println("File name is " + filename);
                in = entry.getInputStream();
                System.out.println("Input stream is " + in);
                byte[] tmp = new byte[4 * 1024];

                //opFilePath = "D:/File/" + entry.getFilename();

                opFilePath = this.getPath() + entry.getFilename();

                f = new File(opFilePath);

                if (!f.isDirectory() && !f.exists() &&
                    opFilePath.substring(opFilePath.lastIndexOf("/")).length() == 1) {
                    System.out.println("Creating new folder if not present");
                    f.mkdirs();
                } else {
                    System.out.println("-----------");
                    System.out.println("123 " + opFilePath);
                    System.out.println(f.exists());
                    System.out.println("directory " + f.isDirectory() + " file is " + f.isFile());
                    if (!f.exists()) {
                        fos = new FileOutputStream(opFilePath);
                        System.out.println("234");
                        int size = 0;

                        while ((size = in.read(tmp)) != -1) {
                            fos.write(tmp, 0, size);
                        }
                        fos.flush();
                        if (!opFilePath.substring(opFilePath.lastIndexOf(".")).equalsIgnoreCase(".txt")) {
                            retVal = true;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ArchiveFormatException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                System.out.println("Inside close**********");
                fos.close();
            }
            if (in != null) {
                in.close();
            }
            if (r != null) {
                System.out.println("Inside close++++++++++");
                r.close();
            }
        }

        return retVal;
    }

    /**
     * To Unzip zip file
     * which don't have any password
     */

    public Boolean unzipFileWithoutPassword(UploadedFile filePath) throws Exception {
        Boolean retVal = false;
        InputStream is = null;
        ZipInputStream zipIs = null;
        ZipEntry zEntry = null;
        FileOutputStream fos = null;
        try {
            is = filePath.getInputStream();
            System.out.println("Is is " + is);
            zipIs = new ZipInputStream(is);
            System.out.println("Stream is: " + zipIs);
            String opFilePath = null;
            File f = null;

            while ((zEntry = zipIs.getNextEntry()) != null) {
                try {

                    System.out.println("List is " + zEntry.getName());
                    byte[] tmp = new byte[4 * 1024];

                    System.out.println("Z entry is " + zEntry);

                    chkInternalFile(zEntry.toString());

                    //opFilePath = "D:/File/" + zEntry.getName();

                    opFilePath = this.getPath() + zEntry.getName();

                    System.out.println("Extracting file to " + opFilePath);

                    f = new File(opFilePath);

                    if (!f.isDirectory() && !f.exists() &&
                        opFilePath.substring(opFilePath.lastIndexOf("/")).length() == 1) {
                        System.out.println("Creating new folder if not present");
                        f.mkdirs();
                    } else {
                        System.out.println("-----------");
                        System.out.println("123 " + opFilePath);
                        System.out.println(f.exists());
                        System.out.println("directory " + f.isDirectory() + " file is " + f.isFile());
                        if (!f.exists()) {
                            fos = new FileOutputStream(opFilePath);
                            System.out.println("234");
                            int size = 0;

                            while ((size = zipIs.read(tmp)) != -1) {

                                fos.write(tmp, 0, size);
                            }

                            fos.flush();

                            if (!opFilePath.substring(opFilePath.lastIndexOf(".")).equalsIgnoreCase(".txt")) {
                                retVal = true;
                            }
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("%%%%%%%%");
                    ex.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("&&&&&&&&&&");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("$$$$$$$$$$");
            e.printStackTrace();
        } finally {
            if (fos != null) {
                fos.close();
            }
            if (is != null) {
                is.close();
            }
            if (zipIs != null) {
                zipIs.close();
            }
        }
        return retVal;
    }

    /**
     * Read text files and its data
     */
    public Boolean readTextFile(String fileNme) throws IOException {
        Boolean retVal = false;

        // File f = new File("D:/File/" + fileNme + ".txt");

        File f = new File(this.getPath() + fileNme + ".txt");

        System.out.println("can Read is " + f.exists());
        if (f.exists()) {
            BufferedReader reader = null;
            String text = null;
            StringBuilder line = new StringBuilder();
            try {
                reader = new BufferedReader(new FileReader(f));

                while ((text = reader.readLine()) != null) {
                    line.append(text).append(System.getProperty("line.separator"));
                }
                retVal = true;
            } catch (FileNotFoundException e) {
                System.out.println("PPPPPPPPPPPPPP");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("AAAAAAAA");
                e.printStackTrace();
            } finally {
                reader.close();
            }
            System.out.println("_________________________+++++++");
            System.out.println(line.toString());

            setDescription(line.toString());

            f.delete(); // Deletes the text file
        }

        return retVal;
    }


    /**
     *Upload Button Action Listener
     * @param actionEvent
     */

    public void UploadReportAL(ActionEvent actionEvent) {
        Boolean status = false; // Used to know whether report has been uploaded properly or not
        BindingContainer bindings = this.getBindings();
        UploadedFile up = (UploadedFile)browseReportPgBind.getValue();

        if (chkMandatoryEntered()) {

            String file = up.getFilename();
            if (!file.contains("."))
                System.out.println("File Name=" + up.getFilename());
            else {
                if (file.contains("_") && StringUtils.countMatches(file, "_") > 2) {
                    file = file.substring(file.indexOf("_"));
                    System.out.println("----- "+file);
                    file = file.substring(file.indexOf("_",2));
                    System.out.println("----- "+file);
                    file = file.substring(file.indexOf("_"), file.lastIndexOf("."));
                    file = file.substring(1);
                } else {
                    file = file.substring(0, file.lastIndexOf("."));
                }

                System.out.println("File Name=" + file);
            }

            if (chkFileType()) {

                OperationBinding ob = bindings.getOperationBinding("InsRowIntoRpt");

                ob.getParamsMap().put("name", reportNamePgBind.getValue());
                ob.getParamsMap().put("auth", authenticationPgBind.getValue());
                ob.getParamsMap().put("fileName", file);
                ob.getParamsMap().put("documId", applicationNamePgBind.getValue());


                if (browseReportPgBind.getValue() != null) {
                    try {
                        returnRptStorPath();

                        //status = unzipFileWithoutPassword(up);
                        status = unzipFileWithPassword(up);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("Status is " + status);
                    if (status) {
                        try {
                            readTextFile(file);

                        } catch (IOException e) {
                            System.out.println("AAAAAAAA");
                            e.printStackTrace();
                        }
                    } else {
                        /**
                         * If any problem occured at the time of unzip then text file which is created will be stored in file location
                         * To delete it I used this code
                         */

                        //File f = new File("D:/File/" + file + ".txt");

                        File f = new File(this.getPath() + file + ".txt");
                        if (f != null) {
                            f.delete();
                        }
                    }
                }

                ob.getParamsMap().put("desc", this.getDescription());
                ob.execute();

                if ((Boolean)ob.getResult()) {
                    if (status) {
                        System.out.println("Inside true");
                        bindings.getOperationBinding("Commit").execute();
                        FacesMessage message = new FacesMessage("Record Saved Successfully.");
                        message.setSeverity(FacesMessage.SEVERITY_INFO);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);

                    } else {
                        System.out.println("Inside false");
                        bindings.getOperationBinding("Rollback").execute();
                        FacesMessage message =
                            new FacesMessage("Report has been uploaded and it cannot be inserted more than once.");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }

                } else {

                    /**
                     * Done this to delete the uploaded file because there is some problem occured at the time of inserting data into Database
                     */


                    //File f = new File("D:/File/" + file + ".jasper");

                    File f = new File(this.getPath() + file + ".jasper");

                    f.delete();

                    FacesMessage message = new FacesMessage("Some Problem occured.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }
            } else {
                FacesMessage message = new FacesMessage("Please select Zip file.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
            bindings.getOperationBinding("Execute").execute();
        }
        ResetUtils.reset(browseReportPgBind);
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    /**
     * Used to check whether all mandatory fields were entered or not
     */

    public Boolean chkMandatoryEntered() {
        Boolean val = false;

        System.out.println("Report Name is " + reportNamePgBind.getValue());
        System.out.println("Application id is " + applicationNamePgBind.getValue());
        System.out.println("Authentication code is " + authenticationPgBind.getValue());
        System.out.println("uploaded report is " + browseReportPgBind.getValue());

        if (reportNamePgBind.getValue() != null && reportNamePgBind.getValue().toString().length() > 0) {
            if (applicationNamePgBind.getValue() != null && applicationNamePgBind.getValue().toString().length() > 0) {
                if (authenticationPgBind.getValue() != null &&
                    authenticationPgBind.getValue().toString().length() > 0) {
                    if (browseReportPgBind.getValue() != null &&
                        browseReportPgBind.getValue().toString().length() > 0) {
                        val = true;
                    } else {
                        FacesMessage message = new FacesMessage("Please select the file which you want to upload.");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(browseReportPgBind.getClientId(), message);
                    }
                } else {
                    FacesMessage message = new FacesMessage("Please enter proper authentication code.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(authenticationPgBind.getClientId(), message);
                }
            } else {
                FacesMessage message =
                    new FacesMessage("Please Select Application Name in which you want to open the report.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(applicationNamePgBind.getClientId(), message);
            }
        } else {
            FacesMessage message = new FacesMessage("Please Enter Report Name.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(reportNamePgBind.getClientId(), message);
        }

        return val;
    }

    /**
     * Check wether zip file was selected or any other file was selected
     */
    public Boolean chkFileType() {
        Boolean val = false;
        UploadedFile up = (UploadedFile)browseReportPgBind.getValue();
        // To know whether Zip file was selected or any other file was selected
        String filename = up.getFilename();
        if (!filename.contains("."))
            System.out.println("File Name=" + up.getFilename());
        else {
            filename = filename.substring(filename.lastIndexOf("."));
            System.out.println("File Name=" + filename);
        }
        if (filename.equalsIgnoreCase(".zip")) {
            val = true;
        }
        return val;
    }


    /**
     * Checks whether zip file and jrxml file is same or not
     */

    public Boolean chkInternalFile(String internalFileName) {
        Boolean val = false;
        int count = StringUtils.countMatches(internalFileName, "/");
        System.out.println("no is " + count);

        UploadedFile up = (UploadedFile)browseReportPgBind.getValue();
        String name = up.getFilename();
        String chkfileType = name.substring(name.lastIndexOf("."));
        if (count == 1) {

            name = name.substring(0, name.lastIndexOf("."));
            internalFileName = internalFileName.substring(internalFileName.indexOf("/"));
            if (internalFileName.length() > 1) {
                internalFileName = internalFileName.substring(1, internalFileName.lastIndexOf("."));
                System.out.println(name + " ^^^^^^^^^^^^^^ " + internalFileName);

                System.out.println("<><><><><>< " + name.equals(internalFileName));
            } else {
                val = false;
            }
        } else {
            val = true;
        }
        return val;
    }

    public void ReportNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("chkRptNameExistOrNot");
            operationBinding.getParamsMap().put("rptName", object);
            operationBinding.execute();
            // System.out.println("Return Value is " + operationBinding.getResult());
            if ((Boolean)operationBinding.getResult()) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Report name already exist. Please select another name..!!",
                                                              null));
            }
        }
    }
}
