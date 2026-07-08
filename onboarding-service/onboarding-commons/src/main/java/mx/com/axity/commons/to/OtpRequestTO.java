package mx.com.axity.commons.to;

public class OtpRequestTO {

    private Long candidateId;
    private String email;

    public Long getCandidateId() { return candidateId; }
    public void setCandidateId(Long candidateId) { this.candidateId = candidateId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
