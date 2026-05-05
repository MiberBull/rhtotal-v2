import { Injectable } from '@angular/core';
import { SQLite, SQLiteObject } from '@ionic-native/sqlite';

/*
  Generated class for the DbStorageRhtotalProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
@Injectable()
export class DbStorageRhtotalProvider {
  private db: SQLiteObject;

  sqlCreateTableScripts: string[] = [
   'create table IF NOT EXISTS CompanyInformation(id INTEGER primary key UNIQUE, name VARCHAR(32),value VARCHAR(250));',
   'create table IF NOT EXISTS UserPdf(idMycv INTEGER primary key UNIQUE,idUser INTEGER,nameCv VARCHAR(50),value VARCHAR,creationUser VARCHAR(50),lastUserModifier VARCHAR(50),lastModification DATETIME, creationDate DATETIME,active BOOLEAN,email VARCHAR(50));',
   'create table IF NOT EXISTS Notifications(idRepository INTEGER primary key UNIQUE, idNotification INTEGER, type VARCHAR(1), subCategory VARCHAR(250), description VARCHAR(250), descriptionSmall VARCHAR(250), title VARCHAR(250), unRead INTEGER, date DATETIME);'
  ];

  constructor(private sqlite: SQLite) {}

  initializeDatabase() {
    return this.sqlite.create({
      name: 'rhtotal.db',
      location: 'default'
    })
    .then((db: SQLiteObject) => {
      this.db = db;


      this.sqlCreateTableScripts.forEach(element => {
        return db.executeSql(element, [])
        .then(() => console.log('tabla exitosa'))
        .catch(e => console.log('error table'));  
      });
      


    })
    .catch(e => console.log('error bd'));
  }

  getAll(sentence: string) {
    if (!this.db) { return Promise.resolve([]); }
    return this.db.executeSql(sentence, []).then(data => {
      let results = [];
      for (let i = 0; i < data.rows.length; i++) {
        results.push(data.rows.item(i));
      }
      return Promise.resolve(results);
    }).catch(error => Promise.reject(error));
  }


  getAllByParameter(sentence: string,param:any[]) {
    if (!this.db) { return Promise.reject(new Error('No database available')); }
    return this.db.executeSql(sentence, param).then(data => {
      let results = [];

      for (let i = 0; i < data.rows.length; i++) {
        results.push(data.rows.item(i));
      }
      if(results.length > 0){
        return Promise.resolve(results);
      }else{
       return Promise.reject(new Error('error'));
      }

    }).catch(error =>{
      return Promise.reject(error);
    } );
  }

  saveOrUpdate(query: string, values: any[]) {
    if (!this.db) { return Promise.resolve(null); }
    return this.db.executeSql(query, values).then(data => {
      if(data.rows.length > 0){
        return data.rows.item(0).value;
      }
    }).catch(e => {
      console.log('error al insertar', e);
    });
  }

  remove(query:string ,key: any[]) {
    if (!this.db) { return Promise.resolve(null); }
    return this.db.executeSql(query, [key]);
  }

  removeAll(query:string) {
    if (!this.db) { return Promise.resolve(null); }
    return this.db.executeSql(query, []);
  }


}
