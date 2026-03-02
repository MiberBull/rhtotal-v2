import { CategoryTO, SubcategoryTO } from './category_sub.model';
import { ImageDiscountTO } from './image.model';
import { CreationInformation } from './creation-information.model';
import { LocalStorageService } from '../services/local-sotorage/localstorage.service';

export class DiscountTO extends CreationInformation{

    idDiscount: number;
    category: CategoryTO | any;
    subCategory: SubcategoryTO | any;
    supplier: string;
    title: string;
    startDate: Date;
    endDate: Date;
    state: string;
    status: string;
    previewImage: string;
    secondaryImage: string;
    description: string;
    linkUrl: string;
    termsConditions: string;
    descriptionPreview: string; 
    viewCount: number;
    notificationTime:Date;
    notificationDetail:string;
    publicationTime:Date;
    typeDiscount:string;
    cost:boolean;
    levelRh: number;


    constructor(private _localService: LocalStorageService){
        super( _localService );
    }

}

export class BenefitsDiscountTreeTO {
    images:ImageDiscountTO[];
    discount:DiscountTO;
    benefitsNotificationsTO:any;
}




