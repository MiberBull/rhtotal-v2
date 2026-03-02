
export class Conversions {

    static UpperFirstLetter( chain: string ) {
        if( chain != null && chain != '' ){
            let str = chain.toLocaleLowerCase();
            return str.charAt(0).toUpperCase() + str.slice(1);
        } else {
            return '';
        }
    }

    static UpperAllLetter( chain: string ) {
        if( chain != null && chain != '' ){
            let str = chain.toLocaleLowerCase();
            return str.toUpperCase();
        } else {
            return '';
        }
    }
}