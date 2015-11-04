package mnfPlanningBoardApp.view.dc;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Calendar;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;
import oracle.jbo.domain.Timestamp;


public class Test {
    public Test() {
        super();
    }

    public static void main(String s[]) {
        boolean flag = true;
        int count = 0;
        Map<Integer,Integer> suppLed =new TreeMap<Integer,Integer>();
        int lead=0,pric=0;
        int sum = lead+pric;
        if(flag){
            count = sum;
            flag = false;
        }
        if(count>sum){
            count = sum;
        }
        suppLed.put(1,3);
        suppLed.put(92,6);
        suppLed.put(23,6);
        suppLed.put(4,3);
        suppLed.put(13,4);
        suppLed.put(21,7);
        suppLed.put(25,2);
        suppLed.put(31,2);
        suppLed.put(64,8);
        System.out.println(suppLed);
        Map map = new HashMap();
        int val = 0;
        Set<Entry<Integer, Integer>> set = suppLed.entrySet();
                List<Entry<Integer, Integer>> list = new ArrayList<Entry<Integer, Integer>>(set);
                Collections.sort( list, new Comparator<Map.Entry<Integer, Integer>>()
                {
                    public int compare( Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2 )
                    {
                        return (o1.getValue()).compareTo( o2.getValue() );
                    }
                } );
                for(Map.Entry<Integer, Integer> entry:list){
                    if(val<2){
                        val = val+1;
                        map.put(entry.getKey(), entry.getValue());
                    }
                    System.out.println(entry.getKey()+" ==== "+entry.getValue());
                }
                Set we = map.keySet();
        System.out.println(we);
        /* SortedSet<Integer> supLedTm = new TreeSet<Integer>();
        supLedTm.add(4);
        supLedTm.add(3);
        supLedTm.add(2);
        supLedTm.add(4);
        supLedTm.add(3); 
        if (supLedTm.isEmpty()) {
            System.out.println("empty");
        } else {
            for (Integer item : supLedTm) {
                System.out.print(item + " ");
            }

            System.out.println(supLedTm.last());
        } */

    }
}
