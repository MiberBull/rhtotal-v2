package mx.com.axity.services;

import mx.com.axity.commons.to.OtpRequestTO;
import mx.com.axity.commons.to.OtpValidateTO;
import mx.com.axity.model.DigitalSignatureDO;

public interface ISignatureService {

    DigitalSignatureDO generateOtp(OtpRequestTO request);

    DigitalSignatureDO validateAndSign(OtpValidateTO validate);
}
