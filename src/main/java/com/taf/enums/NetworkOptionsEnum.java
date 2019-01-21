package com.taf.enums;

/**
 *
 */
public enum NetworkOptionsEnum {

        BLUE_PREFERRED_NETWORK(			"BLPD",	"Blue Preferred Network"),
        CENTERS_OF_EXCELLENCE_NETWORK(	"CCOE",	"Centers of Excellence Network"),
        PREFERRED_HOSPITAL_NETWORK(		"PREF",	"Preferred Hospital Network"),
        PREFERRED_SPECIALIST_NETWORK(	"PSPC",	"Preferred Specialist Network"),
        EPO(							"CEPO",	"EPO Network"),
        CHMO_IN_NETWORK(				"CHMO",	"In Network"),
        BLUE_ACCESS_NETWORK(			"BLAC",	"Blue Access Network"),
        PATHWAY_PPO_NETWORK(			"PPN",	"Pathway PPO Network"),
        PPO_NETWORK(					"GPP",	"PPO Network"),
        TIER_1(							"NHT1",	"Tier 1"),
        TIER_2(							"NHT2",	"Tier 2"),
        VALUE_TIER_1(					"VPAR",	"Value Tier 1"),
        PARTICIPATING_TIER_2(			"BPAR",	"Participating Tier 2"),
        IN_NETWORK(						"C",	"In Network"),
        OUT_OF_NETWORK(					"N",	"Out of Network"),
        OPT_OUT_OF_NETWORK(				"OPT",	"Out of Network"),

        ALL_NETWORKS(					"ALL_NETWORKS","All networks");

        private String code;
        private String displayValue;

        NetworkOptionsEnum(String code,String displayValue){
            this.code = code;
            this.displayValue = displayValue;
        }
        public String value() {
            return name();
        }

        public static NetworkOptionsEnum fromValue(String v) {
            return valueOf(v);
        }

        public static NetworkOptionsEnum fromDisplayValue(String d) {
            for (NetworkOptionsEnum e : NetworkOptionsEnum.values()) {
                if (e.displayValue.equals(d))
                    return e;
            }
            throw new IllegalArgumentException("No " + NetworkOptionsEnum.class.getSimpleName()
                    + " with display value: " + d);
        }

        public static String getDisplayValue(String key) {
            for (NetworkOptionsEnum value : values()) {
                if (value.getCode().equals(key)) {
                    return value.getDisplayValue();
                }

            }
            throw new IllegalArgumentException("Incorrect value: " + key);
        }


        public String getDisplayValue() {
            return displayValue;
        }


        public String getCode() {
            return code;
        }
}

