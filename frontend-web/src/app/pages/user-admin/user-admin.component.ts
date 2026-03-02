import { Component, OnInit ,OnDestroy,Renderer2,ViewChild,ElementRef} from '@angular/core';
import { BreadcrumbService } from '../../services/breadcrumbs/breadcrumbs.service';
import { ToolbarFabService } from '../../services/toolbar-fab/toolbar-fab.service';
import { Router, ActivatedRoute } from '@angular/router';
import { UserService } from '../../services/user/user.service';
import { DataService } from '../../services/data.service';
import { EmployeeComplementaryTO, EmployeeTO } from '../../models/employee.model';
import {  MSG } from '../../../environments/environment';


@Component({
  selector: 'app-user-admin',
  templateUrl: './user-admin.component.html',
  styleUrls: ['./user-admin.component.css']
})
export class UserAdminComponent implements OnInit {
  @ViewChild('inputFile_1') inputFile_1: ElementRef<any>;
  employeeData:EmployeeComplementaryTO[];
  ruta:string;
  visiblePhoto:boolean;
  constructor(
  
    private render:Renderer2,
    private _breadcrumb: BreadcrumbService,
    private _toolbar: ToolbarFabService,
    private router: Router,
    private _dataService: DataService,
    private _userService: UserService,
    private routerActive: ActivatedRoute) {
      this.visiblePhoto=true;
      this.ruta='../../../assets/img/user.svg'
    this._breadcrumb.setRouteText( {title:'Usuario nuevo',arrow:true} );
    this._toolbar.setVisible( this.router.url.toString() );
    this._userService
    .getEmployeeAll()
    .subscribe( (resp:EmployeeComplementaryTO[])=> {
        this.employeeData = resp;
    });

    this._userService.getFoto()
    .subscribe(res=>{
      if(res)
      {
        this.ruta=res;
      }

    });

this._userService.getActivePhotography()
.subscribe( res=> this.visiblePhoto=res);

  }

  ngOnInit() {
    this.router.navigate(['home/admin-usuario/datos-personales']);

  }

  showFileChosser() {
    this.render.selectRootElement( this.inputFile_1.nativeElement ).click();
  }
  
  getImage(file:File) {
    if( !file ) {
      return;
    }

    if(!(/\.(jpg|png)$/i).test(file.name)){
      this._dataService.setGeneralNotificationMessage(MSG.IMAGE_FORMAT_VALIT);
      return;
    }
    else
    {
      if (file) {
        const reader = new FileReader();
        reader.onload = this.handleReaderLoaded.bind(this);
        reader.readAsBinaryString(file);
      } 
    }
  
   
  }
  handleReaderLoaded(e) {
  
    this._userService.setFoto('data:image/png;base64,' + btoa(e.target.result));
  }

}
