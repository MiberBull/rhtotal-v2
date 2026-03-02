package mx.com.axity.commons.to.totree;

import java.io.Serializable;
import java.util.List;

public class TreeClientTO implements Serializable {

    private Boolean everyBody;
    private String name;
    private Long id;
    private Boolean check;
    List<TreeProjectTO> projects;

    public Boolean getEveryBody() {
        return everyBody;
    }

    public void setEveryBody(Boolean everyBody) {
        this.everyBody = everyBody;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public List<TreeProjectTO> getProjects() {
        return projects;
    }

    public void setProjects(List<TreeProjectTO> projects) {
        this.projects = projects;
    }
}
