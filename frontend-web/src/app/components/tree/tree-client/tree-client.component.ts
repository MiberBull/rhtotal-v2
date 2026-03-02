import { Component, OnInit,Input } from '@angular/core';
import { TreeService } from '../../../services/tree/tree.service';
import { OnChanges } from '@angular/core';
import { SimpleChanges } from '@angular/core';

@Component({
  selector: 'app-tree-client',
  templateUrl: './tree-client.component.html',
  styleUrls: ['./tree-client.component.css']
})
export class TreeClientComponent implements OnInit,OnChanges {


  @Input() treeJsonClient:any={};
  @Input('disable') disable:boolean;

  banderaClient:boolean=false;
  clientsSelected:any=[];

  constructor(private _treeService:TreeService) {

  }

  ngOnInit() {

  }

  getClientTree(jsonClient:any){
       let numClient = 0;
       let contador = 0;
       console.log(jsonClient);

       console.log("Arbol ",this.treeJsonClient);
       
       this._treeService.getProjectsOneClient(jsonClient.id).subscribe( projects => {
           this.treeJsonClient['clients'].forEach( client => {
              if(client.id == jsonClient.id && client.check){
                 client.projects = projects;
                 this.treeJsonClient['checkProject']=false;
              }else if(!client.check && client.id == jsonClient.id){
                
                 delete client['projects'];
                 this.treeJsonClient['checkClient']=false;                  
                 client.everyBody=false;
                 client.allProjects = false;
                 client.count = 0;
              }
           },this);
       });

       numClient = this.treeJsonClient['clients'].length;

       this.treeJsonClient['clients'].forEach( client => {
          if(client.check){
              contador += 1;
          }
       });
       if(contador == numClient){
           this.treeJsonClient['checkClient']=true;
       }

       console.log(this.treeJsonClient['clients']);

  }

  selectedAllClient(){

     this.treeJsonClient['clients'].forEach( client => {
         client.check= this.treeJsonClient['checkClient'];
         this.clientsSelected.push({"name":client.name, "id":client.id,"check":client.check});
     });

     console.log(this.clientsSelected);
     this.clientsSelected.forEach( clientSelected => {
          this._treeService.getProjectsOneClient(clientSelected.id).subscribe( projects => {
                this.treeJsonClient['clients'].forEach( client => {
                   if((client.id == clientSelected.id) && client.check){
                      client.projects = projects;
                      this.treeJsonClient['checkProject']=false;
                      client.everyBody = false;                      
                   }else if(!client.check && client.id == clientSelected.id){
                      delete client['projects'];
                      this.treeJsonClient['checkProject']=this.treeJsonClient['checkClient'];
                      this.treeJsonClient['checkEmployee']=this.treeJsonClient['checkClient'];
                      client.everyBody=client.check;
                      client.allProjects = false;
                      client.count = 0;
                   }
                },this);
          });
     });

  }

  enableCheck( enable:boolean ) {
    this.disable = enable;
  }


  ngOnChanges(changes: SimpleChanges): void {
    // this.enableCheck( changes.disable.currentValue );
  }

  
}
