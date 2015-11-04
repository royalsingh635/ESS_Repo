package slspicpackshipapp.model.datatype;

import java.util.ArrayList;
import java.util.HashSet;
import oracle.jbo.domain.Number;

public class LotData {
    private ArrayList<LotRow> data = new ArrayList<LotRow>();

    public void addLotData(LotRow row) {
        data.add(row);
        System.out.println("Row is : "+row.toString());
    }

    public void removeAllData() {
        data.clear();
    }
    
    public ArrayList<LotRow> getData(){
        return data;
    }

    public Number getTotOkQtyForLot(String lotId) {
        Number i = new Number(0);
        for (LotRow row : data) {
            if (row.getLotId().equals(lotId)) {
                i = i.add(row.getOkQty());
            }
        }
        System.out.println("Total Approved Quantity for Lot :" + lotId + " is : " + i);
        return i;
    }

    public Number getTotOkQtyForLotBin(String lotId, String binId) {
        Number i = new Number(0);
        for (LotRow row : data) {
            if (row.getLotId().equals(lotId) && row.getBinId().equals(binId)) {
                i = i.add(row.getOkQty());
            }
        }
        System.out.println("Total Approved Quantity for Lot :" + lotId + " Bin :" + binId + " is : " + i);
        return i;
    }

    public ArrayList<String> getDistinctLotId() {
        ArrayList<String> list = new ArrayList<String>();
        HashSet<String> set = new HashSet<String>();
        for (LotRow row : data) {
            if (set.add(row.getLotId())) {
                list.add(row.getLotId());
            }
        }
        return list;
    }

    public ArrayList<String[]> getDistinctLotBinId() {
        ArrayList<String[]> list = new ArrayList<String[]>();
        HashSet<String> set = new HashSet<String>();
        for (LotRow row : data) {
            String lotId = row.getLotId();
            String binId = row.getBinId();
            boolean add = set.add(lotId + "_" + binId);
            if (add) {
                String a[] = new String[2];
                a[0] = lotId;
                a[1] = binId;
                list.add(a);
            }
        }
        return list;
    }

    public String toString() {
        return data.toString();
    }
}

