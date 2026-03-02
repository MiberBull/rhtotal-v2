package mx.com.axity.commons.util;

import mx.com.axity.commons.to.AnswerLoginMobileTO;
import mx.com.axity.commons.to.AnswerLoginTO;
import  mx.com.axity.commons.util.enumerators.EnumMessageSystem;

public class UtilAnswerLogin {

    public static AnswerLoginTO loginWebResult(EnumMessageSystem messageType, AnswerLoginTO  answerLoginTO ) {
        answerLoginTO.setBlock(Boolean.FALSE);
        switch (messageType) {
            case CORRECT_USER:
                answerLoginTO.setFlag(EnumMessageSystem.CORRECT_USER.getId());
                answerLoginTO.setMessage(EnumMessageSystem.CORRECT_USER.getMenssage());
                break;

            case WRONG_USER:
                answerLoginTO.setMessage(EnumMessageSystem.WRONG_USER.getMenssage());
                answerLoginTO.setFlag(EnumMessageSystem.WRONG_USER.getId());
                answerLoginTO.setUser(null);
            break;
            case WRONG_PASSWORD:
                answerLoginTO.setUser(null);
                answerLoginTO.setMessage(EnumMessageSystem.WRONG_PASSWORD.getMenssage());
                answerLoginTO.setFlag(EnumMessageSystem.WRONG_PASSWORD.getId());
            break;

            case USER_BLOCKED:
                answerLoginTO.setBlock(Boolean.TRUE);
                answerLoginTO.setUser(null);
                answerLoginTO.setFlag(EnumMessageSystem.USER_BLOCKED.getId());
                answerLoginTO.setMessage(EnumMessageSystem.USER_BLOCKED.getMenssage());
            break;

            case USER_NEW:
                answerLoginTO.setUser(null);
                answerLoginTO.setFlag(EnumMessageSystem.USER_NEW.getId());
                answerLoginTO.setMessage(EnumMessageSystem.USER_NEW.getMenssage());
                break;

        }
        return answerLoginTO;
    }


    public static AnswerLoginMobileTO loginMobileResult(EnumMessageSystem messageType, AnswerLoginMobileTO   answerLoginMobileTO ) {
        answerLoginMobileTO.setBlock(Boolean.FALSE);
        switch (messageType) {
            case CORRECT_USER:
                answerLoginMobileTO.setFlag(EnumMessageSystem.CORRECT_USER.getId());
                answerLoginMobileTO.setMessage(EnumMessageSystem.CORRECT_USER.getMenssage());
                break;

            case WRONG_USER:
                answerLoginMobileTO.setMessage(EnumMessageSystem.WRONG_USER.getMenssage());
                answerLoginMobileTO.setFlag(EnumMessageSystem.WRONG_USER.getId());
                answerLoginMobileTO.setUser(null);
                break;
            case WRONG_PASSWORD:
                answerLoginMobileTO.setUser(null);
                answerLoginMobileTO.setMessage(EnumMessageSystem.WRONG_PASSWORD.getMenssage());
                answerLoginMobileTO.setFlag(EnumMessageSystem.WRONG_PASSWORD.getId());
                break;

            case USER_BLOCKED:
                answerLoginMobileTO.setBlock(Boolean.TRUE);
                answerLoginMobileTO.setUser(null);
                answerLoginMobileTO.setFlag(EnumMessageSystem.USER_BLOCKED.getId());
                answerLoginMobileTO.setMessage(EnumMessageSystem.USER_BLOCKED.getMenssage());
                break;

            case USER_NEW:
                answerLoginMobileTO.setUser(null);
                answerLoginMobileTO.setFlag(EnumMessageSystem.USER_NEW.getId());
                answerLoginMobileTO.setMessage(EnumMessageSystem.USER_NEW.getMenssage());
                break;

        }
        return answerLoginMobileTO;
    }
}
