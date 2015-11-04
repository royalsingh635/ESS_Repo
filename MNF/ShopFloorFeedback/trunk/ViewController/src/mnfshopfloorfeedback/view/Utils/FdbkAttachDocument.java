package mnfshopfloorfeedback.view.Utils;

import java.io.InputStream;

import org.apache.myfaces.trinidad.model.UploadedFile;


public class FdbkAttachDocument {

    private String attchFileName;
    private String attchedFileExtension;
    private String attchedFilePath;
    private String attchedFileDispName;
    private String fileExt;
    private UploadedFile uploadedFile;
    private InputStream fileInputStream;

    public FdbkAttachDocument() {
        super();
    }

    public FdbkAttachDocument(String filename, String attchedFileExtension, String attchedFilePath,
                              String attchedFileDispName, InputStream file, String ext) {
        super();
        System.out.println("Inside FdbkAttachDocument java class");
        this.attchedFileExtension = attchedFileExtension;
        this.attchedFilePath = attchedFilePath;
        this.attchedFileDispName = attchedFileDispName;
        this.attchFileName = filename;
        this.fileExt = ext;
        this.fileInputStream = file;


    }


    public void setAttchFileName(String attchFileName) {
        this.attchFileName = attchFileName;
    }

    public String getAttchFileName() {
        return attchFileName;
    }

    public void setAttchedFileExtension(String attchedFileExtension) {
        this.attchedFileExtension = attchedFileExtension;
    }

    public String getAttchedFileExtension() {
        return attchedFileExtension;
    }

    public void setAttchedFilePath(String attchedFilePath) {
        this.attchedFilePath = attchedFilePath;
    }

    public String getAttchedFilePath() {
        return attchedFilePath;
    }

    public void setAttchedFileDispName(String attchedFileDispName) {
        this.attchedFileDispName = attchedFileDispName;
    }

    public String getAttchedFileDispName() {
        return attchedFileDispName;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    public String getFileExt() {
        return fileExt;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setFileInputStream(InputStream fileInputStream) {
        System.out.println("Fileinputstream at FdbkAttachDocument  " + fileInputStream);
        this.fileInputStream = fileInputStream;
    }

    public InputStream getFileInputStream() {
        return fileInputStream;
    }
}
