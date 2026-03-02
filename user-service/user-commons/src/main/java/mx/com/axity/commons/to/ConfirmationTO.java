package mx.com.axity.commons.to;

import java.io.Serializable;

public class ConfirmationTO implements Serializable {

    private String title;
    private String description;

    public ConfirmationTO(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
