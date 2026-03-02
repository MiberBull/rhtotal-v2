import { LocalStorageService } from '../services/local-sotorage/localstorage.service';
import { Information } from '../util/date';
export class CreationInformation {
    lastUserModifier: string;
    lastModification: Date;
    creationUser: string;
    creationDate: Date;
    active: boolean;

    constructor( private _localStorage: LocalStorageService ) {
        this.lastUserModifier = this._localStorage.getUser();
        this.lastModification = Information.getCurrentDate();
        this.creationDate = Information.getCurrentDate();
        this.creationUser = this._localStorage.getUser();
        this.active = true;
    }
}