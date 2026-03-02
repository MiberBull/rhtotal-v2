import { DiscountTO } from './discount.model';
import { LocalStorageService } from '../services/local-sotorage/localstorage.service';
import { CreationInformation } from './creation-information.model';
export class ImageTO {
    id:number;
    idBanner:number | any;
    nameImage:string;
    base64:string;
    typeImage:string;
    lastUserModiffier:string;
    lastModification:Date;
    creationUser:string;
    creationDate:Date;
    active:boolean;
}

export class ImageDiscountTO extends CreationInformation{

    idImage:number;
    idDiscount:DiscountTO;
    nameImage:string;
    value:string;
    typeImage:string;
    
    constructor( private _localService:LocalStorageService ) {
        super( _localService );
    }

}
