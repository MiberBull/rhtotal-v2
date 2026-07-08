package mx.com.axity.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_ticket_comment", schema = "public")
public class TicketCommentDO {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comment") private Long idComment;
    @Column(name = "tenant_id", nullable = false) private String tenantId;
    @Column(name = "id_ticket", nullable = false) private Long idTicket;
    @Column(name = "ds_author", nullable = false) private String dsAuthor;
    @Column(name = "ds_content", nullable = false, columnDefinition = "TEXT") private String dsContent;
    @Column(name = "fg_internal", nullable = false) private Boolean fgInternal = false;
    @Column(name = "fg_active", nullable = false) private Boolean fgActive = true;
    @Column(name = "dt_creation_date", nullable = false) private LocalDateTime dtCreationDate;
    @Column(name = "dt_modification_date") private LocalDateTime dtModificationDate;
    @Column(name = "ds_creation_user") private String dsCreationUser;
    @Column(name = "ds_modification_user") private String dsModificationUser;

    @PrePersist protected void onCreate() { dtCreationDate = LocalDateTime.now(); if (fgActive == null) fgActive = true; if (fgInternal == null) fgInternal = false; }
    @PreUpdate protected void onUpdate() { dtModificationDate = LocalDateTime.now(); }

    public Long getIdComment() { return idComment; } public void setIdComment(Long v) { idComment = v; }
    public String getTenantId() { return tenantId; } public void setTenantId(String v) { tenantId = v; }
    public Long getIdTicket() { return idTicket; } public void setIdTicket(Long v) { idTicket = v; }
    public String getDsAuthor() { return dsAuthor; } public void setDsAuthor(String v) { dsAuthor = v; }
    public String getDsContent() { return dsContent; } public void setDsContent(String v) { dsContent = v; }
    public Boolean getFgInternal() { return fgInternal; } public void setFgInternal(Boolean v) { fgInternal = v; }
    public Boolean getFgActive() { return fgActive; } public void setFgActive(Boolean v) { fgActive = v; }
    public LocalDateTime getDtCreationDate() { return dtCreationDate; } public void setDtCreationDate(LocalDateTime v) { dtCreationDate = v; }
    public LocalDateTime getDtModificationDate() { return dtModificationDate; } public void setDtModificationDate(LocalDateTime v) { dtModificationDate = v; }
    public String getDsCreationUser() { return dsCreationUser; } public void setDsCreationUser(String v) { dsCreationUser = v; }
    public String getDsModificationUser() { return dsModificationUser; } public void setDsModificationUser(String v) { dsModificationUser = v; }
}
