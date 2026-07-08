package mx.com.axity.commons.to;

public class CheckOutRequestTO {

    private Long employeeId;
    private Long projectId;
    private Double latitude;
    private Double longitude;
    private String selfieContent;
    private String selfieMimeType;
    private String notes;

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }

    public Long getProjectId() { return projectId; }
    public void setProjectId(Long projectId) { this.projectId = projectId; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public String getSelfieContent() { return selfieContent; }
    public void setSelfieContent(String selfieContent) { this.selfieContent = selfieContent; }

    public String getSelfieMimeType() { return selfieMimeType; }
    public void setSelfieMimeType(String selfieMimeType) { this.selfieMimeType = selfieMimeType; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
