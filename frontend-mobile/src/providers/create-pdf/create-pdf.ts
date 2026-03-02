import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { File } from '@ionic-native/file';
import { Platform } from 'ionic-angular';
import { INSERT_DB_RHTOTAL,QUERY_DB_RHTOTAL } from '../../environments/environments';
import { DbStorageRhtotalProvider } from '../db-storage-rhtotal/db-storage-rhtotal';
//import { getAllDebugNodes } from '../../../node_modules/@angular/core/src/debug/debug_node';
/*
  Generated class for the CreatePdfProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
@Injectable()
export class CreatePdfProvider {
  directory:string ; 
  constructor(public http: HttpClient, private db: DbStorageRhtotalProvider, public file: File,public plt: Platform) {
    console.log('Hello CreatePdfProvider Provider');
     this.directory = this.plt.is('ios') ? this.file.dataDirectory : this.file.externalDataDirectory; 
  }

  writePdf(pdfBase64:string ,filename:string){
     return this.file.writeFile(this.directory,filename,
      this.convertBaseb64ToBlob(pdfBase64, 'data:application/pdf;base64'),
        {replace:true});
  }
  
  readPdf(filename:string):string{
    return this.directory + filename;
  }


   convertBaseb64ToBlob(b64Data, contentType): Blob {
    contentType = contentType || '';
    const sliceSize = 512;
    b64Data = b64Data.replace(/^[^,]+,/, '');
    b64Data = b64Data.replace(/\s/g, '');
    const byteCharacters = window.atob(b64Data);
    const byteArrays = [];
    for (let offset = 0; offset < byteCharacters.length; offset += sliceSize) {
         const slice = byteCharacters.slice(offset, offset + sliceSize);
         const byteNumbers = new Array(slice.length);
         for (let i = 0; i < slice.length; i++) {
             byteNumbers[i] = slice.charCodeAt(i);
         }
         const byteArray = new Uint8Array(byteNumbers);
         byteArrays.push(byteArray);
    }
   return new Blob(byteArrays, {type: contentType});
}




savePdfDb(idMycv:number,idUser:number,nameCv:string,value:string, creationUser:string,lastUserModifier:string,lastModification:Date, creationDate:Date,active:boolean,email:string){
  let arr:any = [idMycv,idUser,nameCv,value,creationUser,lastUserModifier,lastModification, creationDate,active,email];
  return this.db.saveOrUpdate(INSERT_DB_RHTOTAL.INSERT_USER_PDF,arr);
}

findUserPdf(email:string){
 return this.db.getAllByParameter(QUERY_DB_RHTOTAL.QUERY_USER_PDF,[email]);   
}
 getAll(){
   return this.db.getAll('select * from UserPdf');
 }
}