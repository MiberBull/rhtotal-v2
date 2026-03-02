package mx.com.axity.commons.util.enumerators;

public enum EnumMessageSystem {

    CORRECT_USER(0, "USUARIO CORRECTO"),
    WRONG_USER(1, "El usuario que ingresó no es válido "),
    WRONG_PASSWORD(2, "Contraseña incorrecta"),
    USER_BLOCKED(3, "La cuenta ha sido bloqueada por :N minutos"),
    USER_NEW(4, "USUARIO NUEVO");

    private int id;
    private String menssage;

    EnumMessageSystem(int id, String menssage) {
        this.id = id;
        this.menssage = menssage;
    }

    public int getId() {
        return id;
    }

    public String getMenssage() {
        return menssage;
    }
}
