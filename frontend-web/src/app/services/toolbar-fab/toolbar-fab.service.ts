import { Injectable } from "@angular/core";
import { Subject } from "rxjs";

@Injectable({
    providedIn:'root'
})

export class ToolbarFabService{

    add = new Subject<string>();
    visible = new Subject<any[]>();
    visibleFilter:boolean = true;
    search = new Subject<string>();

    idRolUserRead = new Subject<boolean>();
    showEditSave = new Subject<number>();


    constructor(){}

    /**
     * Coloca el string del button que se oprimio
     * en la toolbar
     * @param button
     */
    setAddEvent( button: string ){
        this.add.next(button);
    }

    /**
     * Retorna el string del button que se opimio
     * en la toolbar
     */
    getAddEvent(){
        return this.add.asObservable();
    }

    setClickSearch( button:string ){
        this.search.next(button)
    }

    getClickSearch(){
        return this.search.asObservable();
    }


    /**
     * Coloca la visibilidad de la toolbar
     * @param visible
     */
    setVisible( url:string ){
        let arrayToolbar:any[]=[];
        let defaulToolbar:boolean = true;

        toolbarVisible.forEach( element => {
                if(url == element.url){
                   arrayToolbar=element.toolbar;
                   defaulToolbar=false;
                }              
        });   
        if(defaulToolbar){
            arrayToolbar = [
                                {"item":"search", "visible":false},
                                {"item":"add", "visible":false},
                                {"item":"excel", "visible":false},
                                {"item":"settings", "visible":false},
                           ]   
        }
        this.visible.next(arrayToolbar);        
    }

    /**
     * Retorna si es visible la toolbar
     */
    getVisible(){
        return this.visible.asObservable();
    }

    setVisibleFilter( visible:boolean ) {
        this.visibleFilter = visible;
    }

    getVisibleFilter(){
        return this.visibleFilter;
    }


    setRolUserRead(idRol: boolean) {
        this.idRolUserRead.next(idRol);
    }

    getRolUserRead() {
        return this.idRolUserRead.asObservable();
    }    

    setShowSaveUpdate(idRol: number) {
        this.showEditSave.next(idRol)
    }

    getShowSaveUpdate() {
        return this.showEditSave.asObservable();
    }
	
}

const toolbarVisible = [
    {"url":"/home/roles","toolbar": [
                                      {"item":"search", "visible":false},
                                      {"item":"add", "visible":true},
                                      {"item":"excel", "visible":true},
                                      {"item":"settings", "visible":true},
                                    ]},
    {"url":"/home/descuentos","toolbar": [
                                      {"item":"search", "visible":true},
                                      {"item":"add", "visible":true},
                                      {"item":"excel", "visible":true},
                                      {"item":"settings", "visible":true},
                                    ]},                                    
    {"url":"/home/banners","toolbar": [
                                      {"item":"search", "visible":true},
                                      {"item":"add", "visible":true},
                                      {"item":"excel", "visible":true},
                                      {"item":"settings", "visible":true},
                                    ]},                                    
    {"url":"/home/notificaciones","toolbar": [
                                      {"item":"search", "visible":true},
                                      {"item":"add", "visible":true},
                                      {"item":"excel", "visible":true},
                                      {"item":"settings", "visible":true},
                                    ]},                                    
    {"url":"/home/seguros","toolbar": [
                                      {"item":"search", "visible":true},
                                      {"item":"add", "visible":true},
                                      {"item":"excel", "visible":true},
                                      {"item":"settings", "visible":true},
                                    ]},                                    
    {"url":"/home/clientes","toolbar": [
                                      {"item":"search", "visible":false},
                                      {"item":"add", "visible":true},
                                      {"item":"excel", "visible":true},
                                      {"item":"settings", "visible":false},
                                    ]},                                    
    {"url":"/home/usuarios","toolbar": [
                                      {"item":"search", "visible":true},
                                      {"item":"add", "visible":true},
                                      {"item":"excel", "visible":true},
                                      {"item":"settings", "visible":true},
                                    ] }                                   
 ];
