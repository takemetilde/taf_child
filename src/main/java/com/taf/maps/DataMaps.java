package com.taf.maps;

import com.taf.enums.Environment;

import java.util.HashMap;
import java.util.Map;

public class DataMaps {

    private static final Map<String, String> dataMaps = new HashMap<String, String>();
    private static final Map<String, String> sit4DataMaps = new HashMap<String, String>();
    private static final Map<String, String> sit5DataMaps = new HashMap<String, String>();
    private static final HashMap<String, HashMap<String, String>> testDataMaps = new HashMap<String, HashMap<String, String>>();

    //sit4DataMaps
    static {
        sit4DataMaps.put("Castlight User", "SIT4SB073T95204");      // Find a doctor
        sit4DataMaps.put("Castlight User1", "s4wgssb730T90349");    // castlight member with only medical coverage
        sit4DataMaps.put("Castlight User2", "s4nasbAN0155137");     // castlight member with medical and pharmacy coverage
        sit4DataMaps.put("Castlight User3", "s4nsbAN0155134");      // castlight member with medical and pharmacy coverage - but SSO issue
        sit4DataMaps.put("Castlight User5", "s4nsbAN4351148");      // castlight member with only medical coverage
        sit4DataMaps.put("Castlight User6", "s4nsbAN0744518");      // castlight member with medical,dental coverage
        sit4DataMaps.put("Castlight User7", "Sit4AN4766855");      // castlight member with medical,vision coverage
        sit4DataMaps.put("Castlight User8", "sit4_754T92080");      // castlight member without Core Indicator=9
        sit4DataMaps.put("Castlight User9", "sit4_688T92080");      // castlight member with Non-ESI RX contract
        sit4DataMaps.put("Castlight User10", "sit4sb127T91199");      // member with medical,dental,vision,pharmacy coverages
        //sit4sb127T91199  - member with medical,dental,vision,pharmacy coverages
        
    }

    //sit5DataMaps
    static {
        sit5DataMaps.put("Castlight User", "SIT5SB127T91199");
        sit5DataMaps.put("Castlight User1", "sit5m730T90349");
        sit5DataMaps.put("Castlight User2", "s5nassbAN0155137");
        sit5DataMaps.put("Castlight User3", "sit5nassbAN0155134");
        sit5DataMaps.put("Castlight User4", "sit5m730T90349");
        sit5DataMaps.put("Castlight User5", "s5nassbAN4351148");
    }

    //Generic Maps
    static {
        dataMaps.put("Generic Password", "support1");
    }

    public static String getDataValue(String key) {
        Integer envIndex = Environment.getIndex();

        switch(Environment.getEnv()) {
            case LOCALHOST:
            case SIT:
                if (envIndex == 4)
                    return sit4DataMaps.get(key);
                else if (envIndex == 5)
                    return sit5DataMaps.get(key);
            default:
                throw new IllegalArgumentException("Parameter 'key' <" + key + "> is undefined. Must be one of 'dev', 'ci' or 'sit'");
        }
    }

    public static String getSharedDataValue(String key) {
        return dataMaps.get(key);
    }
}
