package appwfservdisp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.binding.OperationBinding;

public class AppWfServDispBean {
    
    private RichInputText fileNamepath;

    public void setFileNamepath(RichInputText fileNamepath) {
        this.fileNamepath = fileNamepath;
    }

    public RichInputText getFileNamepath() {
        return fileNamepath;
    }

    public AppWfServDispBean() {
        
       
        super();
    }
    
    
    Integer j;


    ArrayList<HeaderImage> arrWf = new ArrayList<HeaderImage>(); //change to null
    ArrayList remarks = new ArrayList();


    public Integer getJ() {
        j = arrWf.size();
        System.out.println("size of array lsit is" + j);
        return j;
    }

    public void viewFileAction(FacesContext facesContext, OutputStream outputStream) {
        RichInputText bind = this.getFileNamepath();
        if (bind != null) {
            System.out.println("come ----- 1");
            String path = bind.getValue().toString();

            System.out.println("path is:  " + path);
            try {
                FileInputStream in = new FileInputStream(path);
                System.out.println("Available bytes are:  " + in.available());
                if (in.available() <= 0) {
                    System.out.println("came in unavailable!");
                    FacesMessage msg = new FacesMessage("There is no data in the File !!");
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return;
                }
                byte b[] = new byte[in.available()];
                int i = in.read(b);
                outputStream.write(b);

                outputStream.flush();
                outputStream.close();
                in.close();
                facesContext.getCurrentInstance().responseComplete();
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
                FacesMessage msg = new FacesMessage("File Not Found in the Directory!!");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (IOException e) {
                System.out.println("IO Exception occur------");
                FacesMessage msg = new FacesMessage("The File is corrupted!!");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }


    public ArrayList<HeaderImage> getArrWf() {
        Integer lvl = 0;
        OperationBinding ob = ADFBeanUtils.findOperation("getLevel");
        System.out.println("getLevel execution");
        ob.execute();
        if (ob.getResult() != null) {
            lvl = (Integer) ob.getResult();
            System.out.println("level is::::::::::::::" + lvl);

            if (lvl >= 0) {

                System.out.println("Level is length si " + lvl);

                arrWf.clear();


                for (int i = 1; i <= lvl; i++) {
                    
                    OperationBinding obCurrLvl = ADFBeanUtils.findOperation("getWfCurrentLevel");
                    obCurrLvl.execute();
                    //if(obCurrLvl.getResult()!=null && ((((Integer)obCurrLvl.getResult()).compareTo(new Integer(0))) == 0 || (((Integer)obCurrLvl.getResult()).compareTo(i)) >= 0 )){
                        
                    if(obCurrLvl.getResult()!=null &&  (((Integer)obCurrLvl.getResult()).compareTo(i)) >= 0 ){
                    
                    String st = null;
                    OperationBinding obA = ADFBeanUtils.findOperation("getWfAction");
                    obA.getParamsMap().put("index", i);
                    obA.execute();
                    if (obA != null) {
                        System.out.println("obA is not null");
                        //                    System.out.println("obAresult  is not null" + obA.getResult());
                        if (obA.getResult() != null) {
                            System.out.println("obA.getReult() is not null");
                            System.out.println("STRING IS vv ::::::::::" + obA.getResult());
                            //System.out.println("iffffffffffffffffff");
                            st = obA.getResult().toString();
                            System.out.println("STRING IS ::::::::::" + st);

                            //                            remarks = null;
                            OperationBinding obe = ADFBeanUtils.findOperation("wfHistory");
                            System.out.println("obe::::::::::::::::::::");
                            obe.execute();
                            if (obe != null) {
                                System.out.println("obe is not null---------------------------");

                                if (obe.getResult() != null) {

                                    System.out.println("obe.getResult() is not nulll=============");
                                    remarks = (ArrayList) obe.getResult();
                                    System.out.println("value of the remark is::::::::::::::::::::: " + remarks);


                                }
                            }

                        }
                    } else {
                        System.out.println("elsseeeee");
                    }
                    arrWf.add(new HeaderImage(st, i));
                    System.out.println("arrWf value " + i);
                    }else{
                        System.out.println("elsseeeee part PPPPPPPPPPPPPPPPPP");
                        String stPnd="P";
                        arrWf.add(new HeaderImage(stPnd, i));
                    }
            }

            }
        }
        System.out.println("arrWf value ---- > " + arrWf.size());
        return arrWf;

    }

    public String getDocumentStatus(){
        try{
            OperationBinding obCurrLvl = ADFBeanUtils.findOperation("getWfCurrentLevel");
            obCurrLvl.execute();
            if(obCurrLvl.getResult()!=null &&  (((Integer)obCurrLvl.getResult()).compareTo(new Integer(0))) == 0 ){
                return "THIS DOCUMENT IS APPROVED";
            }
                }catch(Exception e){
                    e.printStackTrace();
                    return null;
                }
        return null;
    }


}
