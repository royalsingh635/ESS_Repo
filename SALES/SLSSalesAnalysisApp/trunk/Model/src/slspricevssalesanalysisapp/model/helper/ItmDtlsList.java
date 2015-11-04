package slspricevssalesanalysisapp.model.helper;

import java.util.ArrayList;

public class ItmDtlsList {
    private ArrayList<ItmDataDS> data;
    public ItmDtlsList() {
        super();
        data = new ArrayList<ItmDataDS>();
    }
    
    public void addItmDtls(ItmDataDS itmDtls){
        data.add(itmDtls);
    }
    
    public String getQueryString(){
        StringBuilder query = new StringBuilder("");
        if(data.size() > 0){
            query.append("(");
            for(int i = 0;i <= data.size()-1;i++){
                ItmDataDS dS = data.get(i);
                query.append("'");
                query.append(dS.getItmId());
                query.append("'");
                if(data.size()-1 != i){
                    query.append(",");
                }
            }
            query.append(")");
        }
        return query.toString();    
    }
}
