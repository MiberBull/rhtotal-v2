package mx.com.axity.commons.to;

import java.io.Serializable;

public class EmailTO implements Serializable {

    private String email;
    private String template;
    private String nameTemplate;
    private String parameters;

    public EmailTO(String email, String template) {
        this.email = email;
        this.template = template;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getNameTemplate() {
        return nameTemplate;
    }

    public void setNameTemplate(String nameTemplate) {
        this.nameTemplate = nameTemplate;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }
}
