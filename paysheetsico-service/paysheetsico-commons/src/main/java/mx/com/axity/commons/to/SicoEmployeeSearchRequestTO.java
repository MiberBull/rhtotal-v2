package mx.com.axity.commons.to;

import java.io.Serializable;
import java.util.List;

public class SicoEmployeeSearchRequestTO implements Serializable {

    private List<String> names;

    private String lastName;

    private String LastMotherName;

    private String birthday;

    private int page;

    private int resultPageSize;

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastMotherName() {
        return LastMotherName;
    }

    public void setLastMotherName(String lastMotherName) {
        LastMotherName = lastMotherName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getResultPageSize() {
        return resultPageSize;
    }

    public void setResultPageSize(int resultPageSize) {
        this.resultPageSize = resultPageSize;
    }
}
