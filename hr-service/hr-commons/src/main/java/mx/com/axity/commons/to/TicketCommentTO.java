package mx.com.axity.commons.to;

public class TicketCommentTO {
    private Long idComment;
    private String tenantId;
    private Long idTicket;
    private String dsAuthor;
    private String dsContent;
    private Boolean fgInternal;
    private Boolean fgActive;

    public Long getIdComment() { return idComment; }
    public void setIdComment(Long idComment) { this.idComment = idComment; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public Long getIdTicket() { return idTicket; }
    public void setIdTicket(Long idTicket) { this.idTicket = idTicket; }
    public String getDsAuthor() { return dsAuthor; }
    public void setDsAuthor(String dsAuthor) { this.dsAuthor = dsAuthor; }
    public String getDsContent() { return dsContent; }
    public void setDsContent(String dsContent) { this.dsContent = dsContent; }
    public Boolean getFgInternal() { return fgInternal; }
    public void setFgInternal(Boolean fgInternal) { this.fgInternal = fgInternal; }
    public Boolean getFgActive() { return fgActive; }
    public void setFgActive(Boolean fgActive) { this.fgActive = fgActive; }
}
