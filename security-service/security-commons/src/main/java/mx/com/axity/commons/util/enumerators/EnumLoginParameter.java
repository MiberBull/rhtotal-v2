package mx.com.axity.commons.util.enumerators;

public enum EnumLoginParameter {
    MOBILE(1),
    WEB_APLICATION(2);



    private int typeEntry;

     EnumLoginParameter(int typeEntry) {
        this.typeEntry = typeEntry;
    }

    public int getTypeEntry() {
        return typeEntry;
    }
}
