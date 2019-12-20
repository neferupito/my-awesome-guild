package io.neferupito.myawesomeguild.data.domain.wow.server;

public enum Region {

    EU,
    US;

    public static Region findValue(String r) {
        return valueOf(r.toUpperCase());
    }

}
