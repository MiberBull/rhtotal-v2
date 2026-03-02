package mx.com.axity.commons.to.totree;

import java.io.Serializable;
import java.util.List;

public class TreeProjectTO implements Serializable {

    private Boolean everyBody;
    private String name;
    private Long id;
    private Boolean check;
    private List<TreeEmployeeTO> employees;

    public TreeProjectTO() {
    }

    public TreeProjectTO(Boolean everyBody, String name, Long id, Boolean check, List<TreeEmployeeTO> employees) {
        this.everyBody = everyBody;
        this.name = name;
        this.id = id;
        this.check = check;
        this.employees = employees;
    }

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

    public List<TreeEmployeeTO> getEmployees() {
        return employees;
    }

    public void setEmployees(List<TreeEmployeeTO> employees) {
        this.employees = employees;
    }
}
