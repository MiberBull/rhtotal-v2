import { CreationInformation } from "./creation-information.model";

export class CategoryTO extends CreationInformation {
    idCategory: number;
    category: string;
    lastUserModifier: string;
    lastModification: Date;
    creationUser: string;
    creationDate: Date;
    active:boolean;

    constructor(localService) {
        super(localService);
        this.active = true;
        this.idCategory = null;
    }

}

export class SubcategoryTO extends CreationInformation {
    
    idSubCategory: number;
    category: CategoryTO;
    subcategory: string;
    lastUserModifier: string;
    lastModification: Date;
    creationUser: string;
    creationDate: Date;
    active: boolean;

    constructor(localService) {
        super(localService);
        this.active = true;
        this.idSubCategory = null;
    }

}