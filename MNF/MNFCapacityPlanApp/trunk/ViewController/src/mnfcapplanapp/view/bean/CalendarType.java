package mnfcapplanapp.view.bean;

import java.awt.Color;

public class CalendarType 
{
    public CalendarType()
    {
        super();
    }
    
    String id;
    String name;
    Boolean enabled;
    Color colour;
    
    public CalendarType(String CId, String CName, Boolean CEnabled, Color CColour) {
        super();
        this.id = CId;
        this.name = CName;
        this.enabled = CEnabled;
        this.colour = CColour;        
    } 
    
    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setColour(Color colour) {
        this.colour = colour;
    }

    public Color getColour() {
        return colour;
    }
    
}
