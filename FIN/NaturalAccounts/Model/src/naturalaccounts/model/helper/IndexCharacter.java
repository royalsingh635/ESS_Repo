package naturalaccounts.model.helper;

import oracle.jbo.Key;

public class IndexCharacter {
    public IndexCharacter() {
        super();
    }
    String character;
    Boolean found;
    Key rowIndex;
    
    

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getCharacter() {
        return character;
    }

    public void setFound(Boolean found) {
        this.found = found;
    }

    public Boolean getFound() {
        return found;
    }

    public void setRowIndex(Key rowIndex) {
        this.rowIndex = rowIndex;
    }

    public Key getRowIndex() {
        return rowIndex;
    }
    

}
