package mx.com.axity.commons.to;

import java.io.Serializable;

public class EmailContentTO implements Serializable {
   private String email;
   private String template;
   private String nameTemplate;
   private String parameters;

    public EmailContentTO(String email, String template, String nameTemplate, String parameters) {
        this.email = email;
        this.template = template;
        this.nameTemplate = nameTemplate;
        this.parameters = parameters;
    }

    public EmailContentTO() {
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
