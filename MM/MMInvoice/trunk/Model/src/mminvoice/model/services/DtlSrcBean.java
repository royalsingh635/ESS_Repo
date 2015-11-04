package mminvoice.model.services;

public class DtlSrcBean {
    
    private String rcptNo;
    private String srcNo;
    private Integer dlvScdl;

  

    public DtlSrcBean() {
        super();
    }
    
    
    public DtlSrcBean(String rcptNo,String srcNo,Integer dlvScdl) 
    {
    this.rcptNo=rcptNo;
    this.srcNo=srcNo;
    this.dlvScdl=dlvScdl;
    }

    public void setDlvScdl(Integer dlvScdl) {
        this.dlvScdl = dlvScdl;
    }

    public Integer getDlvScdl() {
        return dlvScdl;
    }
    public void setRcptNo(String rcptNo) {
        this.rcptNo = rcptNo;
    }

    public String getRcptNo() {
        return rcptNo;
    }

    public void setSrcNo(String srcNo) {
        this.srcNo = srcNo;
    }

    public String getSrcNo() {
        return srcNo;
    }

  
  public boolean equals(Object obj)
  {
      if(obj == null){return false;}
      
      else if(!(obj instanceof DtlSrcBean))
      {return false;}
      
      else if(obj.getClass() != this.getClass())
      {return false;}
      
      final DtlSrcBean that = (DtlSrcBean)obj;
      
      if(that.getRcptNo().equals(this.getRcptNo()) && that.getSrcNo().equals(this.getSrcNo()) && that.getDlvScdl().equals(this.dlvScdl))
       {return true;}
      
      return false;
   }
  
  
  public int hashCode()
  {
      int h = 27;
      h=h*this.getRcptNo().hashCode();
      h=h*this.getSrcNo().hashCode();
      h=h*this.getDlvScdl().hashCode();
      return h;
  }

}
