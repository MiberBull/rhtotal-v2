package mx.com.axity.services.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.OtpRequestTO;
import mx.com.axity.commons.to.OtpValidateTO;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.commons.util.SHA;
import mx.com.axity.model.DigitalSignatureDO;
import mx.com.axity.persistence.DigitalSignatureDAO;
import mx.com.axity.services.ISignatureService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class SignatureServiceImpl implements ISignatureService {

    private static final Logger LOG = LogManager.getLogger(SignatureServiceImpl.class);

    @Autowired
    private DigitalSignatureDAO digitalSignatureDAO;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    @Transactional
    public DigitalSignatureDO generateOtp(OtpRequestTO request) {
        String otp = String.format("%06d", new Random().nextInt(999999));
        String otpHash;
        try {
            otpHash = SHA.encrypt(otp);
        } catch (NoSuchAlgorithmException e) {
            throw new BusinessException(500, "Error al generar OTP: " + e.getMessage());
        }

        DigitalSignatureDO signature = new DigitalSignatureDO();
        signature.setIdCandidate(request.getCandidateId());
        signature.setTenantId("dchkw");
        signature.setDsOtpHash(otpHash);
        signature.setFgUsed(false);
        signature.setFgSigned(false);
        signature.setDtOtpExpiry(LocalDateTime.now().plusMinutes(Constants.OTP_EXPIRY_MINUTES));
        digitalSignatureDAO.save(signature);

        sendOtpEmail(request.getEmail(), otp);

        LOG.info("OTP generado y enviado a {} para candidato {}", request.getEmail(), request.getCandidateId());
        return signature;
    }

    @Override
    @Transactional
    public DigitalSignatureDO validateAndSign(OtpValidateTO validate) {
        String tenantId = "dchkw";
        List<DigitalSignatureDO> signatures = digitalSignatureDAO
            .findByIdCandidateAndTenantIdAndFgUsedFalse(validate.getCandidateId(), tenantId);

        if (signatures.isEmpty()) {
            throw new BusinessException(404, "No se encontró OTP válido para el candidato");
        }

        String otpHash;
        try {
            otpHash = SHA.encrypt(validate.getOtp());
        } catch (NoSuchAlgorithmException e) {
            throw new BusinessException(500, "Error al validar OTP: " + e.getMessage());
        }

        DigitalSignatureDO signature = signatures.stream()
            .filter(s -> s.getDsOtpHash().equals(otpHash))
            .filter(s -> LocalDateTime.now().isBefore(s.getDtOtpExpiry()))
            .findFirst()
            .orElseThrow(() -> new BusinessException(400, "OTP inválido o expirado"));

        signature.setFgUsed(true);
        signature.setFgSigned(true);
        signature.setDtSignedDate(LocalDateTime.now());
        signature.setDsIpAddress(validate.getIp());
        return digitalSignatureDAO.save(signature);
    }

    private void sendOtpEmail(String email, String otp) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Firma tu contrato DCH Know Who | Código: " + otp);
            message.setText(
                "Hola,\n\n" +
                "Tu código de firma electrónica es: " + otp + "\n\n" +
                "Este código es válido por " + Constants.OTP_EXPIRY_MINUTES + " minutos.\n\n" +
                "DCH Know Who"
            );
            mailSender.send(message);
        } catch (Exception e) {
            LOG.warn("No se pudo enviar email OTP a {}: {}", email, e.getMessage());
        }
    }
}
