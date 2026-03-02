import { Injectable } from '@angular/core';
import { Subject } from "rxjs";
import { HttpClient, HttpParams } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { PATH_APPLICATION, parameters } from '../../../environments/environment';
import { JSONTREE } from '../../../assets/data/tree';


@Injectable({
  providedIn: 'root'
})
export class TreeService {

  infoTree = new Subject<any>();
  infoProyectTree = new Subject<any>();
  infoClientTree = new Subject<any>();

  private jsonTreeInfo:any=[];
  
  private urlProjectsOneClient:string=`${PATH_APPLICATION.DOMAIN}/notificationassignment/getProjects`;
  private urlEmployeesOneProject:string=`${PATH_APPLICATION.DOMAIN}/notificationassignment/getEmployee`;

    countClient = new Subject<number>();

  constructor( private http: HttpClient ) { }


  /**
   * 
   * @param url 
   * @param parameters 
   */
  getAllTree(idNotification='0',typeNotification){
    let params =  new HttpParams()
                               .set('idNotification',idNotification)
                               .set('typeNotification',typeNotification);
    
    return this.http.get( PATH_APPLICATION.TREE,{params} ).pipe(
                   map( infoTree => {                                 
                    let contador = 0;
                    console.log('ARBOL DESDE EL SERVICE ', infoTree);
                    infoTree['clients'].forEach( client => {
                        client.everyBody=false;
                        client.count = 0;
                                                
                    });

                    infoTree['clients'].forEach( client => {
                        if (client.projects){
                            client.projects.forEach(project => {

                                if (project.employees && project.employees.length > 0) {
                                    project.employees.forEach( employee => {
                                        if (employee.check == true){
                                            client.count = 1;
                                        }
                                    });                                      

                                }
                            });
                        }

                    });
                    

                  
                               
                    return infoTree;
                     
                    })
                );
  }

  getAllEmployeesAllProject(){

  }

  getProjectsOneClient(idClient){

        let params = new HttpParams()
                        .set('id',idClient);
    
        return this.http.get(this.urlProjectsOneClient,{params})
                          .pipe(
                              map( resp => {                                                                
                                  return resp;
                              })
                          );       
  }

  getEmployeesOneProject(idProject){
        let params = new HttpParams()
                          .set('id',idProject);

        return this.http.get(this.urlEmployeesOneProject,{params})
                            .pipe(
                                map( resp => {
                                    return resp;
                                })
                            );
  }

  getAllClients(clientsSelected){

  }


  /************/
  /************/


  getCheckTreeInfo(){
      return this.infoTree.asObservable();
  }

  setCheckTreeInfo(jsonTreeInfo:any){
      this.infoTree.next(jsonTreeInfo);
  }

  getCheckTreeProyect(){
      return this.infoProyectTree.asObservable();
  }

  setCheckTreeProyect(jsonProyect:any){
      this.infoProyectTree.next(jsonProyect);
  }


  getCheckTreeClient(){
      return this.infoClientTree.asObservable();
  }

  setCheckTreeClient(jsonClient:any){
      this.infoClientTree.next(jsonClient);
  }

  /**Informacion del Arbol */

  getJsonTreeInfo(){
    return this.jsonTreeInfo;
  }

  setJsonTreeInfo(jsonTree:any){
    this.jsonTreeInfo = jsonTree;
  }

  setCountClient(count:number){
      this.countClient.next(count);
  }
    
  getCountClient(){      
      return this.countClient.asObservable();
  }

}