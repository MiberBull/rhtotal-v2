import { Component, OnInit, Input, SimpleChanges, OnChanges } from '@angular/core';
import { TreeService } from '../../../services/tree/tree.service';

@Component({
  selector: 'app-tree-proyect',
  templateUrl: './tree-proyect.component.html',
  styleUrls: ['./tree-proyect.component.css']
})
export class TreeProyectComponent implements OnInit,OnChanges {

  @Input() treeJsonClient:any={};
  @Input('disable') disable:boolean;

  projectsCLientSelected:any=[];

  constructor(private _treeService:TreeService) { }

  ngOnInit() {       

  }
  /**
   *Consulta Unica, obtiene empleados de un proyecto seleccionado
   */
  getProject(idProjecto:any,idClient:any){
      
      let numProject=0;
      let contador=0;
      let countProyecto=0;

      this._treeService.getEmployeesOneProject(idProjecto.id).subscribe( employees => {
          this.treeJsonClient['clients'].forEach( client => {
                if(client.id == idClient){
                    client.projects.forEach( (project,i) => {
                        
                        if(project.id == idProjecto.id && project.check){
                            project.employees = employees;

                            if (project.employees.length > 0){
                                client.count = 1;
                            }

                            
                        }else if(!project.check && (project.id == idProjecto.id)){
                            client.everyBody = false;
                            this.treeJsonClient['checkProject']=false;
                            delete project['employees'];
                            
                            let cont=0;
                            let noProject = client.projects.length;
                            client.allProjects = false;
                            project.everyBody = false;
                            client.projects.forEach( project => {
                                if (!(project.employees)){
                                    cont += 1;                                    
                                } 
                            });
                            client.count = cont === noProject ? 0 : 1;

                        }
                     });
                }
          });
       });

             

       this.treeJsonClient['clients'].forEach( client => {
           if(client.check){
               numProject += client.projects.length;
           }           
       });
      this.treeJsonClient['clients'].forEach( client => {
          client.projects.forEach( project => {
              if(project.check){
                 contador += 1;
              }
              if(numProject == contador){
                 this.treeJsonClient['checkProject']=true;
                 this.treeJsonClient['clients'].forEach( clientSub => {
                      clientSub.everyBody=true;
                 });
              }
          });
      });
      
      numProject=0;
      contador=0;
      this.treeJsonClient['clients'].forEach( client => {
          if(client.id == idClient){
              numProject= client.projects.length;              
          }
      });

      this.treeJsonClient['clients'].forEach(client => {
        if(client.id == idClient){
           client.projects.forEach(project => {
               if(project.check){
                   contador += 1;
               }
               if(contador == numProject){
                   client.everyBody=true;
               }
           });
        }
      });
      
  }

  /**
   *Consulta de todo los empleados
   */
  selectedAllProjectsClients(){
    let clientsProject:any=[];
    this.treeJsonClient['clients'].forEach( client => {
        if(client.projects){
            client.projects.forEach( project => {
                project.check = this.treeJsonClient['checkProject'];
                client.everyBody = this.treeJsonClient['checkProject'];
                clientsProject.push({"idClient":client.id,"idProject":project.id});
            });
        }

    });

    clientsProject.forEach( element => {
        this._treeService.getEmployeesOneProject(element.idProject).subscribe( employees => {
           this.treeJsonClient['clients'].forEach( client => {
                 if(client.id == element.idClient){
                    client.projects.forEach( project => {
                        if((project.id == element.idProject) && project.check){
                            project.employees = employees;
                            if (project.employees.length > 0) {
                                client.count = 1;
                            }
                            project.employees.length > 0 ? project.employees = employees : delete project['employees'];
                        }else if(!project.check && (project.id == element.idProject)){
                             delete project['employees'];
                             this.treeJsonClient['checkEmployee']=false;
                             client.allProjects=false;
                             project.everyBody=false;
                             client.count = 0;
                        }
                    });
                 }
           });
        });
    });
    
    
  }

  /**
  ** Consulta los empleados de un proyecto seleccionado
   **/
  selectedAllProject(clientSelected){

      let numProjects = 0;
      let contados = 0;

      clientSelected.projects.forEach( project => {
            project.check = clientSelected.everyBody;
            this.projectsCLientSelected.push({"id":project.id,"name":project.name,"check":project.check});          
      });


      this.projectsCLientSelected.forEach( selectedClient => {
           this._treeService.getEmployeesOneProject(selectedClient.id).subscribe( employees => {
               this.treeJsonClient['clients'].forEach( client => {
                   if(client.id == clientSelected.id){
                      client.projects.forEach( project => {
                          if(project.id == selectedClient.id && project.check){
                             project.employees = employees;

                             if (project.employees.length > 0) {
                                  client.count = 1;
                             }

                             project.employees.length > 0 && project.employees != null  ? project.employees = employees : delete project['employees'];
                          }else if(!project.check && (project.id == selectedClient.id)){
                              delete project['employees'];
                              this.treeJsonClient['checkProject']=false;
                              client.allProjects = false;
                              project.everyBody = false;
                              client.count = 0;
                          }
                      });
                   }
               });
           });
       });
       console.log("scsdcds ",this.treeJsonClient);
       

      numProjects = this.treeJsonClient['clients'].length;

      this.treeJsonClient['clients'].forEach(client => {
                    
          if(client.projects && (client.everyBody || client.projects.length==0)){
             contados += 1;
          }
          if(contados == numProjects){
              this.treeJsonClient['checkProject']=true;
          }
      });
      console.log("scsdcds ", this.treeJsonClient);
  }

  enableCheck( enable:boolean ) {
    this.disable = enable;
  }


  ngOnChanges(changes: SimpleChanges): void {
    // this.enableCheck( changes.disable.currentValue );
    
  }

}
