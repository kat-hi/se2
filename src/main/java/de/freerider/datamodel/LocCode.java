package de.freerider.datamodel;

public enum LocCode {
    //
    BER, MUC, FRA, LEJ, HAM, STG,                // airports
    //
    BENT02("Enterprise, Budapester Str. 39"),    // Berlin city pickup/drop locations
    BTH11("52.4931° N, 13.5258° E"),            // GPS location
    ;
    public final String str;

    LocCode() {
        this.str = this.name();
    }

    LocCode(String s) {
        this.str = s;
    }

    public String toString() {
        return str;
    }
};
