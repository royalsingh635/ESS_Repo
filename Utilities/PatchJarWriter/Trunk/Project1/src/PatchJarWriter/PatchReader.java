package PatchJarWriter;

import PatchJarWriter.crypt.Crypto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.security.NoSuchAlgorithmException;

public class PatchReader {
    
    public String script;
    public String hash;
    public StringBuilder patches= new StringBuilder();
    
    
    public PatchReader() {
        super();
    }
    
    public StringBuilder readString(String address){
        StringBuilder str=new StringBuilder();
        
        if(address==null){
            System.out.println("Please define an address ");
            return null;
        }
             
           String line=null;
           File patch=null;
           File folder=new File(address);
           folder.mkdir();
           
           String[] list=folder.list();
           
           if(list==null||list.length==0){
                System.out.println("Please define some Patch File");
           }else{
               
               for(String name:list){
                   patches=patches.append(name.substring(0, name.indexOf("."))+",");
                   patch=new File(folder.getAbsolutePath()+"//"+name);
                
                   try(BufferedReader br=new BufferedReader(new FileReader(patch))){
                       
                       line=br.readLine();
                       while(line!=null){
                           //System.out.println("Line is "+line);
                           if(line.trim().length()==0||line.toLowerCase().contains("spool")){
                             // System.out.println("Blank");
                           } else{
                               str.append(this.ncrypt(line)+"\n");
                           }
                           line=br.readLine();
                       }
                       
                       
                   }catch(IOException e){
                       e.printStackTrace();
                   }
               }
           
           }


        try {
            this.hash=Crypto.getHash(str.toString());
//            System.out.println("hash is "+this.hash);
//            System.out.println("String is "+str);
        } catch (NoSuchAlgorithmException e) {
            this.hash=null;
            e.printStackTrace();
        }

        return str;
    }
    
    private String ncrypt(String input){
        String output=this.encrypt(input).toString();
        return output;
    }
    
    public  StringBuilder encrypt(String str) {
       StringBuilder build=null; 
            build=new StringBuilder(str);
        

        return build;
    }

    
}
