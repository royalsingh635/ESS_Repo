package mnfRoutingApp.view.utils;

import java.io.InputStream;

import org.apache.myfaces.trinidad.model.UploadedFile;

public class RTAttchmentDC {

    private String _attchFileName;
    private String _attchedFileExtension;
    private String _attchedFilePath;
    private String _attchedFileDispName;
    private String _fileExt;
    private UploadedFile _uploadedFile;
    private InputStream _fileInputStream;

    public RTAttchmentDC() {

    }

    public RTAttchmentDC(String filename, String attchedFileExtension, String attchedFilePath,
                         String attchedFileDispName, InputStream file, String ext) {
        super();
        this._attchedFileExtension = attchedFileExtension;
        this._attchedFilePath = attchedFilePath;
        this._attchedFileDispName = attchedFileDispName;
        this._attchFileName = filename;
        this._fileExt = ext;
        this._fileInputStream = file;
    }

    public void setAttchFileName(String _attchFileName) {
        this._attchFileName = _attchFileName;
    }

    public String getAttchFileName() {
        return _attchFileName;
    }

    public void setAttchedFileExtension(String _attchedFileExtension) {
        this._attchedFileExtension = _attchedFileExtension;
    }

    public String getAttchedFileExtension() {
        return _attchedFileExtension;
    }

    public void setAttchedFilePath(String _attchedFilePath) {
        this._attchedFilePath = _attchedFilePath;
    }

    public String getAttchedFilePath() {
        return _attchedFilePath;
    }

    public void setAttchedFileDispName(String _attchedFileDispName) {
        this._attchedFileDispName = _attchedFileDispName;
    }

    public String getAttchedFileDispName() {
        return _attchedFileDispName;
    }


    public void setUploadedFile(UploadedFile _uploadedFile) {
        this._uploadedFile = _uploadedFile;
    }

    public UploadedFile getUploadedFile() {
        return _uploadedFile;
    }

    public void setFileExt(String _fileExt) {
        this._fileExt = _fileExt;
    }

    public String getFileExt() {
        return _fileExt;
    }

    public void setFileInputStream(InputStream _fileInputStream) {
        this._fileInputStream = _fileInputStream;
    }

    public InputStream getFileInputStream() {
        return _fileInputStream;
    }
}
