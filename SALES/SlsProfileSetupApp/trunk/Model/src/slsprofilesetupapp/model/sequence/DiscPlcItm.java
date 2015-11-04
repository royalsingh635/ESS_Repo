package slsprofilesetupapp.model.sequence;

public class DiscPlcItm {
    private Integer id;
    private String desc;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public DiscPlcItm(Integer id,String desc) {
        super();
        this.id = id;
        this.desc = desc;
    }
}
