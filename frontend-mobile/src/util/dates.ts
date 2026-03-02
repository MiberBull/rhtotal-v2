export class DatesApplication {

    public static formatDate( date:string ):string {
        let dateformat:string;
        var d = new Date( date );
        dateformat = `${d.getDate()}/${d.getMonth()+1}/${d.getFullYear()}`;
        return  dateformat;
    }

}