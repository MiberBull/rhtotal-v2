package mx.com.axity.commons.to;

public class OtpValidateTO {

    private Long candidateId;
    private String otp;
    private String ip;

    public Long getCandidateId() { return candidateId; }
    public void setCandidateId(Long candidateId) { this.candidateId = candidateId; }

    public String getOtp() { return otp; }
    public void setOtp(String otp) { this.otp = otp; }

    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }
}
