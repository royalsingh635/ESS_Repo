package PatchJarWriter;

import PatchJarWriter.crypt.Crypto;

public class ReqInfoCreator {
    
    
    public ReqInfoCreator() {
        super();
    }
    
    
    public String getReqInfo(String s){
        StringBuilder str=new StringBuilder();
        
        str.append(this.ncrypt("EAR_NO=")+"\n");
        str.append(this.ncrypt("PATCH_NO=")+"\n");
        str.append(this.ncrypt(s)+"\n");
        return str.toString();
    }
    
    
    private String ncrypt(String input){
        String output=this.encrypt(input).toString();
        return output;
    }
    
    public  StringBuilder encrypt(String str) {
        StringBuilder build=new StringBuilder( str); 
//         try {
//             build=new StringBuilder(Crypto.encrypt(str));
//         } catch (Exception e) {
//             build=(new StringBuilder(str)).reverse();
//             e.printStackTrace();
//         }

         return build;
    }
}
