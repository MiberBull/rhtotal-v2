package mx.com.axity.commons.to;

import java.io.Serializable;

public class AnswerLoginTO implements Serializable {
    private Boolean block;
    private String message;
    private int flag;
    private RolesUserTO user;

    public Boolean getBlock() {
        return block;
    }

    public void setBlock(Boolean block) {
        this.block = block;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public RolesUserTO getUser() {
        return user;
    }

    public void setUser(RolesUserTO user) {
        this.user = user;
    }
}
