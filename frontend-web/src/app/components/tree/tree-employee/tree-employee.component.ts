import { Component, OnInit, Input, SimpleChanges, OnChanges } from '@angular/core';
import { TreeService } from '../../../services/tree/tree.service';

@Component({
  selector: 'app-tree-employee',
  templateUrl: './tree-employee.component.html',
  styleUrls: ['./tree-employee.component.css']
})
export class TreeEmployeeComponent implements OnInit,OnChanges {

  @Input() treeJsonClient:any={};
  @Input('disable') disable:boolean;

  banderaClient:boolean = false;

  countClient:number;

  constructor(private _treeService:TreeService ) {              
      this._treeService.getCountClient().subscribe( (count:number) => {
          this.countClient = count;
      });
  }

  ngOnInit() {
  }

  getEmployee(checkEmployee:any,Project,Client){
    let numEmployees = 0;
    let contador = 0;

    this.treeJsonClient['clients'].forEach( client => {
          if(client.id == Client.id){
              client.projects.forEach( project => {
                 if(project.id == Project.id){
                   project.employees.forEach( employee => {
                      if(employee.id == checkEmployee.id && !employee.check){
                         project.everyBody = employee.check;
                         client.allProjects = employee.check;
                         this.treeJsonClient['checkEmployee'] = employee.check;
                      }
                   });
                 }
              });
          }
      });

      this.treeJsonClient['clients'].forEach( client => {
          if(client.id == Client.id){
            client.projects.forEach( project => {
                if(project.id == Project.id){
                   numEmployees = project.employees.length;
                   console.log('zizw',numEmployees);
                   project.employees.forEach( employee => {
                       if(employee.check){
                          contador += 1;
                       }
                       if(contador == numEmployees){
                          project.everyBody = true;
                       }
                   });
                }
            });
          }
      });

      //
      numEmployees=0;
      contador = 0;

      this.treeJsonClient['clients'].forEach( client => {
        if(client.id == Client.id){
            client.projects.forEach( project => {                
                if(project.employees){
                    numEmployees += project.employees.length;
                    console.log('zizw',numEmployees);               
                }
            });
        }
      });

      this.treeJsonClient['clients'].forEach( client => {
          if(client.id == Client.id){
            client.projects.forEach( project => { 
                if(project.employees){
                    project.employees.forEach( employee => {
                        if(employee.check){
                           contador += 1;
                        }
                        if(contador == numEmployees){
                           project.everyBody = true;
                           client.allProjects = true;
                        }
                    });
                }
            });
          }
      });

      //

      numEmployees=0;
      contador = 0;
      this.treeJsonClient['clients'].forEach( client => {
         client.projects.forEach( project => {
             if(project.employees){
                numEmployees += project.employees.length;
             }
         });

      });
      this.treeJsonClient['clients'].forEach( client => {
            client.projects.forEach( project => {                   
                if(project.employees){
                    project.employees.forEach( employee => {
                        console.log("CONT",contador);
                        console.log("NumEmp ",numEmployees);                        
                        if(employee.check){
                           contador += 1;
                        }
                        if(contador == numEmployees){
                           project.everyBody = true;
                           client.allProjects = true;
                           this.treeJsonClient['checkEmployee']=true;
                        }
                    });
                }


            });
      });


      console.log('Arbol ',this.treeJsonClient);
      //console.log('Arbol Antes ',this._treeService.getJsonTreeInfo());
      ///this._treeService.setJsonTreeInfo(this.treeJsonClient);
      console.log('Arbol Despues ',this._treeService.getJsonTreeInfo());
      //checkEmployee.check ? this._treeService.setCheckTreeInfo(checkEmployee) : console.log(checkEmployee.check);
  }

  getAllEmployee(){
        this.treeJsonClient['clients'].forEach( client => {
               client.projects.forEach( project => {
                  if(project.employees){
                        project.employees.forEach( employee => {
                            employee.check=this.treeJsonClient['checkEmployee'];
                            client.allProjects=this.treeJsonClient['checkEmployee'];
                            project.everyBody=this.treeJsonClient['checkEmployee'];
                        },this);
                  }
               },this);
        },this);
  }

  getAllEmployeeProject(client, data){

      let numClients=0;
      let contador = 0;

      this.treeJsonClient['clients'][client].projects.forEach( (project) => {
          if (project.employees){
              project.employees.forEach((employee) => {
                  employee.check = data.allProjects;
                  project.everyBody = data.allProjects;
              });
          }

      });

     ///////
     //////
     numClients = this.treeJsonClient['clients'].length;

     this.treeJsonClient['clients'].forEach( client => {
          if(client.allProjects){
             contador += 1;
          }
          if(contador == numClients){
             this.treeJsonClient['checkEmployee']= true;
          }else{
            this.treeJsonClient['checkEmployee']= false;
          }
     });

     //////
     //////
      console.log(this._treeService.getJsonTreeInfo());

  }

  getAllEmployeeOneProject(ClientObject,project,projectRef){

      let sizeProjects = 0;
      let contador = 0;

      projectRef.employees.forEach( (employee,i) => {
        employee.check = projectRef.everyBody;
      });

      this.treeJsonClient['clients'].forEach( client => {
          if(client.id == ClientObject.id){
            client.projects.forEach( project => {
               if(!project.everyBody && (project.id == projectRef.id)){
                  client.allProjects = project.everyBody;
                  this.treeJsonClient['checkEmployee']=project.everyBody;
               }
            });
          }
      });

      /////
      this.treeJsonClient['clients'].forEach( client => {
         if( client.id == ClientObject.id){
            sizeProjects = client.projects.length;            
            client.projects.forEach( project => {
                 if(project.everyBody || (project.employees.length == 0)){
                   contador += 1;
                 }
                 if(sizeProjects == contador){
                     client.allProjects=true;
                 }
            });
         }
      });

      /////
      sizeProjects = 0;
      contador = 0;
      
      
      this.treeJsonClient['clients'].forEach( client => {
          if(client.projects){
            client.projects.forEach(project => {
                if(project.employees){
                    sizeProjects += 1;
                }
            });
          }
      });
      
      
      console.log("NUm Proyectos ",sizeProjects);
      this.treeJsonClient['clients'].forEach( client => {
        client.projects.forEach(project => {
            if(project.employees && project.everyBody){
                contador += 1;
            }
            if(sizeProjects == contador){
                client.allProjects=true;
                this.treeJsonClient['checkEmployee']=true;
            }            
        });                

      });
      console.log("CONTADOR ",contador);   
      console.log(this.treeJsonClient);   
  }

  enableCheck( enable:boolean ) {
    this.disable = enable;
  }


  ngOnChanges(changes: SimpleChanges): void {
    // this.enableCheck( changes.disable.currentValue );
    
  }

}
