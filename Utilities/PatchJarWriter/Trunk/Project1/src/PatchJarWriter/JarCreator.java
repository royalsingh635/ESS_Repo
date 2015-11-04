package PatchJarWriter;

import PatchJarWriter.crypt.Crypto;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.Arrays;
import java.util.Comparator;

public class JarCreator {
    
    private File patch=null;
    private File auth=null;
    private File patchEn=null;
    private File authEn=null;
    private String conFolder=null;
    
    public JarCreator() {
        super();
    }
    
    public void createFile(String pAddress,String jAddress,String lPatch) throws Exception {
        this.conFolder=jAddress+"\\Patch";
        File folder=new File(conFolder);
        folder.mkdir();
        
        folder.delete();
        
         folder=new File(conFolder);
        folder.mkdir();
        
         this.patch=new File(conFolder+"\\Patch.txt");
         this.auth=new File(conFolder+"\\AuthInfo.txt");
         
         this.sortFolder(pAddress);
        
         try(PrintWriter pw1=new PrintWriter(new FileWriter(this.patch));
             PrintWriter pw2=new PrintWriter(new FileWriter(this.auth))){
             
             PatchReader pr=new PatchReader();
             ReqInfoCreator req=new  ReqInfoCreator();
             
             pw1.print(pr.readString(pAddress));
             pw2.print(req.getReqInfo("LastPatch="+lPatch+(pr.hash!=null ?"\n EarNo="+pr.hash:"" )+ "\n Patch_Files="+pr.patches.toString()));
             
             this.patchEn=new File(conFolder+"\\PatchEn.txt");
             this.authEn=new File(conFolder+"\\AuthInfoEn.txt");
             
         }catch(IOException e){
             e.printStackTrace();
         }
        Crypto.encrypt(this.patch, this.patchEn);
        Crypto.encrypt(this.auth, this.authEn);
        
    }
    
    public void createJar(String pAddress,String jAddress,String lPatch) throws Exception {
        
        if(this.patch==null||this.auth==null)
            this.createFile(pAddress, jAddress,lPatch);
        
        try
        {
            
          String params[] = {
            "cmd.exe", "/c","start","/D", conFolder,"jar","cf","Patch.jar" ,"PatchEn.txt" ,"AuthInfoEn.txt"
          };
          ProcessBuilder pb = new ProcessBuilder(params);
          Process pr = pb.start();
          pr.waitFor();
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
        
        
    }
    
    
    public void sortFolder(String conFolder){
        File folder=new File(conFolder);
        folder.mkdir();
        
        Arrays.sort( folder.listFiles(), new FileComparator() );
        
    }
    
    class FileComparator<File> implements Comparator{

        @Override
        public int compare(Object f1, Object f2) {
            if(!(f1 instanceof java.io.File &&  f2 instanceof java.io.File) )
                return 0;
            
            return ((java.io.File )f1).getName().compareTo(((java.io.File )f2).getName());
            
        }
    }
    
}
