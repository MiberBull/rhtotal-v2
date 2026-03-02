package mx.com.axity.commons.util;

public class Convertions {

    static public String stringToHtml( String chain ) {
        return chain
                .replace("Á","&#193;")
                .replace("á","&#225;")
                .replace("É","&#201;")
                .replace("é","&#233;")
                .replace("Í","&#205;")
                .replace("í","&#237;")
                .replace("Ó","&#211;")
                .replace("ó","&#243;")
                .replace("Ú","&#218;")
                .replace("ú","&#250;")
                .replace("Ñ","&#209;")
                .replace("ñ","&#241;");
    }

}
