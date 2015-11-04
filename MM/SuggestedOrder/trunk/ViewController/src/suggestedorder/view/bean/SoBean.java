package suggestedorder.view.bean;

public class SoBean{
    
    private Integer slocId;
    private String cldId;
    private String orgId;
    private String itmId;
    private String itmUom;
    private String groupId;
    private Integer userId;
    private oracle.jbo.domain.Number totQty;
    public SoBean() {
        super();
    }

    public SoBean(Integer slocId,String cldId,String orgId,String itmId,String itmUom,String groupId,Integer userId,oracle.jbo.domain.Number totQty)
    {
        this.slocId=slocId;
        this.cldId=cldId;
        this.orgId=orgId;
        this.itmId=itmId;
        this.itmUom=itmUom;
        this.groupId=groupId;
        this.userId=userId;
        this.totQty=totQty;
    }

    public void setSlocId(Integer slocId) {
        this.slocId = slocId;
    }

    public Integer getSlocId() {
        return slocId;
    }

    public void setCldId(String cldId) {
        this.cldId = cldId;
    }

    public String getCldId() {
        return cldId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setItmId(String itmId) {
        this.itmId = itmId;
    }

    public String getItmId() {
        return itmId;
    }

    public void setItmUom(String itmUom) {
        this.itmUom = itmUom;
    }

    public String getItmUom() {
        return itmUom;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setTotQty(oracle.jbo.domain.Number totQty) {
        this.totQty = totQty;
    }

    public oracle.jbo.domain.Number getTotQty() {
        return totQty;
    }
    
    
    public boolean equals(Object object)
    {
        if(object == null)
        {return false;}
        
        if( !(object instanceof SoBean))
        {return false;}
        
        if(object.getClass() != this.getClass())
        {return false;}
        
         SoBean that = (SoBean)object;
         if( (this.getItmId().equals(that.getItmId())) && (this.getGroupId().equals(that.getGroupId())) && (this.getItmUom().equals(that.getItmUom())))
         { 
             that.totQty =this.getTotQty().add(that.getTotQty());
             return true;
        }
        return false;
    }
    
    
    public int hashCode()
    {
      int hash=3;
      hash = 7 * hash + this.itmId.hashCode();
      hash = 7 * hash + this.groupId.hashCode();
      hash = 7 * hash + this.itmUom.hashCode();
      return hash;
    }
    
    
    public String toString()
    {return this.getItmId()+" "+this.getGroupId()+" "+this.getTotQty()+" "+this.getItmUom();}

   /*  @Override
    public int compareTo(SoBean o) {
        if(o == null)
        {return -2;}
        
        return (this.getItmId().compareTo(o.getItmId()));
    } */
}
