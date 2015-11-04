package fincashflowapp.view.bean;

import java.awt.Color;

import java.awt.Dimension;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.GregorianCalendar;

import javax.faces.component.ContextCallback;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import oracle.adf.view.faces.bi.component.graph.Background;
import oracle.adf.view.faces.bi.component.graph.UIGraph;

import oracle.dss.dataView.ImageView;


public class DvtContextCallBack implements ContextCallback {


    public DvtContextCallBack() {
        super();
    }


    public void invokeContextCallback(FacesContext facesContext,
                                      UIComponent uiComponent) {
        //PRINT
        if (uiComponent instanceof UIGraph) {
            
            UIGraph dvtgraph = (UIGraph)uiComponent;            

            //We can set a different background color. However, this changes the
            //graph instance and thus best is to copy the current values to set 
            //them back after the image is processed
            
            Background orgBackground = dvtgraph.getBackground();
            Background bg = new Background();                   
            bg.setFillColor(Color.WHITE);
            bg.setFillTransparent(false);
            dvtgraph.setBackground(bg);
            dvtgraph.transferProperties();
            
            ImageView imgView = dvtgraph.getImageView(); 
            
            //We can set a different image size. However, this changes the 
            //graph instance and thus best is to copy the current values to set 
            //them back after the image is processed
            Dimension orgDimension = imgView.getImageSize();
                                          //width, height
            imgView.setImageSize(new Dimension(400,400));
            String slash = File.separator;
            String dSlash = slash + slash;
            
            //create a unique file name (you may want to change generating the
            //file name using a real random so that concurrent access to the 
            //application don't conflic if they are processed just in the same
            //faction of a second
            String filename = GregorianCalendar.getInstance().getTimeInMillis() + "dvt";
            String drive = "c:";
            String tmpFolder = "temp";
            File file = null;
            FileOutputStream fos;
            try {                
                file = new File(drive + dSlash + tmpFolder + slash + filename + ".png");
                fos = new FileOutputStream(file);
                imgView.exportToPNG(fos);
                fos.close();
            }
            
            catch (FileNotFoundException e) {
                //For
                //sample - just show stack trace
                e.printStackTrace();
            }
            catch (IOException e) {
                  e.printStackTrace();
            }
            finally{
              //reset the graph default
              dvtgraph.setBackground(orgBackground);              
              dvtgraph.transferProperties();
              imgView.setImageSize(orgDimension);
            }          
        }
    }

}