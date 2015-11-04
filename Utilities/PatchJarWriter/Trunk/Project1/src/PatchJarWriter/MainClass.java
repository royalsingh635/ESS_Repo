package PatchJarWriter;

public class MainClass {
    public MainClass() {
        super();
    }

    public static void main(String[] args) throws Exception {
       SysVarReader var=new SysVarReader();
       var.readVariable();
       
       JarCreator jar=new JarCreator();
       jar.createJar(var.patchFolder, var.jarFolder,var.patch);
       
       System.exit(0);
    }
}
