import { Time } from "@angular/common";


export class Information {

    static getCurrentDate(){
        let date = new Date();
        date.setDate( date.getDate() );
        date.setFullYear( date.getFullYear() );
        date.setMonth( date.getMonth() );
        return date;
    }

    static getDateString( date: Date ): string {
        var day =(date.getDate()<10?'0'+date.getDate():date.getDate());
        var month = (date.getMonth()<9?'0'+(date.getMonth()+1):date.getMonth()+1);
        return `${day}-${month}-${date.getFullYear()}`;
    }

    
    static getTimeString( time: Time ): string {
        var timeString =time.toString();
        var H = +timeString.substr(0, 2);
        var h = H % 12 || 12;
        var ampm = (H < 12 || H === 24) ? " AM" : " PM";
        var x=(h<10)?"0":"";        
        timeString = x + h + timeString.substr(2, 3) + ampm;
        
        return timeString;
    }

    static getDateForDateTimePicker( date:Date ) {
        let convertDate = new Date(date);
        return new Date(convertDate.getUTCFullYear(), convertDate.getUTCMonth(), convertDate.getUTCDate());
    }

    static getTimeForInputTime( time:string ) {
        let arrayTime = time.split(':');
        return new Date(null,null,null,Number(arrayTime[0]),Number(arrayTime[1]),Number(arrayTime[2]));
    }

    static validateCurrentHour(date:string,time:string) {
        let a = new Date(date);
        let dateConvert = new Date(`${(a.getMonth()+1)}-${a.getDate()}-${a.getFullYear()} ${time}:00`);
        return dateConvert < Information.getCurrentDate();
    }

    static validateTwoDate( dateOne,dateTwo ) {
        let date_one = new Date( dateOne );
        let date_two = new Date( dateTwo );
        return date_one <= date_two;
    }
    static convertTime(time){      
        time = time.toString ().match (/^([01]\d|2[0-3])(:)([0-5]\d)(:[0-5]\d)?$/) || [time];
  
        if (time.length > 1) { 
          time = time.slice (1);
          time[5] = +time[0] < 12 ? 'AM' : 'PM'; 
          time[0] = +time[0] % 12 || 12; 
          time[0]= ('0' + time[0]).slice (-2);
          time.splice(3, 1);
        }
        return time.join (' ');    
    }
    static formatTime(timeArray){
      let time1 = timeArray.slice(0,4);
      let hora = timeArray.slice(4);
      return `${time1.join('')} ${hora.join('')}`;
    }

    static MaysFirst(titles:string[]){
        let arrayTitles:string[]=[];
        
        titles.forEach( title => {
            console.log(title.slice(1).toString().toLowerCase());
            let cadenaTitle = title.slice(1).toString().toLowerCase();
            
            arrayTitles.push(title.charAt(0).toUpperCase()+cadenaTitle+'');
        });  
                
        return arrayTitles;
    }

    static dateUsers(startDate:string,endDate:string):Boolean{
        let start = new Date(startDate);
        let end = new Date(endDate);
    
        return end < start;
    }

}

