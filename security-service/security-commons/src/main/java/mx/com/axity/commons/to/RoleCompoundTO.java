package mx.com.axity.commons.to;

import java.io.Serializable;
import java.util.List;

public class RoleCompoundTO<T> implements Serializable {
    private List<T> roleList;
    private String imageRole;
    private String htmlRole;

    public List<T> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<T> roleList) {
        this.roleList = roleList;
    }

    public String getImageRole() {
        return imageRole;
    }

    public void setImageRole(String imageRole) {
        this.imageRole = imageRole;
    }

    public String getHtmlRole() {
        return htmlRole;
    }

    public void setHtmlRole(String htmlRole) {
        this.htmlRole = htmlRole;
    }
}
