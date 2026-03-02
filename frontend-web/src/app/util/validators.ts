import { EXPRESSION, NUMBERS, DICTIONARY } from '../../environments/environment';


export class Validators {

    validateCurp( curp ) {
        let validate = curp.match( EXPRESSION.CURP );
        if (!validate) return false;
        let checkDigit = ( curp_2 ) => {
            let lngSum = NUMBERS.CERO,
                lngDigit = NUMBERS.CERO;
            for(var i = NUMBERS.CERO; i<17; i++)
                lngSum = lngSum + DICTIONARY.ALPHABET.indexOf( curp_2.charAt(i) ) * ( NUMBERS.EIGHTEEN - i );
            lngDigit = NUMBERS.TEN - lngSum % NUMBERS.TEN;
            if (lngDigit == 10) return NUMBERS.CERO;
            return lngDigit;
        }
        if (validate[2] != checkDigit(validate[1])) return false;
        return true;
    }
    

}