package mx.com.axity.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_resource_document", schema = "public")
public class ResourceDocumentDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_document")
    private Long idDocument;

    @Column(name = "tenant_id")
    private String tenantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_category")
    private ResourceCategoryDO category;

    @Column(name = "ds_title")
    private String title;

    @Column(name = "ds_description")
    private String description;

    @Column(name = "ds_file_name")
    private String fileName;

    @Column(name = "ds_mime_type")
    private String mimeType;

    @Column(name = "ds_file_content", columnDefinition = "TEXT")
    private String fileContent;

    @Column(name = "ds_version")
    private String version;

    @Column(name = "ds_visibility")
    private String visibility;

    @Column(name = "id_client")
    private Long idClient;

    @Column(name = "fg_requires_ack")
    private Boolean requiresAck;

    @Column(name = "fg_active")
    private Boolean active;

    @Column(name = "dt_publication_date")
    private LocalDateTime publicationDate;

    @Column(name = "dt_expiry_date")
    private LocalDateTime expiryDate;

    @Column(name = "ds_published_by")
    private String publishedBy;

    @Column(name = "dt_creation_date")
    private LocalDateTime creationDate;

    @Column(name = "dt_modification_date")
    private LocalDateTime modificationDate;

    public Long getIdDocument() { return idDocument; }
    public void setIdDocument(Long idDocument) { this.idDocument = idDocument; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public ResourceCategoryDO getCategory() { return category; }
    public void setCategory(ResourceCategoryDO category) { this.category = category; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getMimeType() { return mimeType; }
    public void setMimeType(String mimeType) { this.mimeType = mimeType; }

    public String getFileContent() { return fileContent; }
    public void setFileContent(String fileContent) { this.fileContent = fileContent; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    public String getVisibility() { return visibility; }
    public void setVisibility(String visibility) { this.visibility = visibility; }

    public Long getIdClient() { return idClient; }
    public void setIdClient(Long idClient) { this.idClient = idClient; }

    public Boolean getRequiresAck() { return requiresAck; }
    public void setRequiresAck(Boolean requiresAck) { this.requiresAck = requiresAck; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public LocalDateTime getPublicationDate() { return publicationDate; }
    public void setPublicationDate(LocalDateTime publicationDate) { this.publicationDate = publicationDate; }

    public LocalDateTime getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDateTime expiryDate) { this.expiryDate = expiryDate; }

    public String getPublishedBy() { return publishedBy; }
    public void setPublishedBy(String publishedBy) { this.publishedBy = publishedBy; }

    public LocalDateTime getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }

    public LocalDateTime getModificationDate() { return modificationDate; }
    public void setModificationDate(LocalDateTime modificationDate) { this.modificationDate = modificationDate; }
}
