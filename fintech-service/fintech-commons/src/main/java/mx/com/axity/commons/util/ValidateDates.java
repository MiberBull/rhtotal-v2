package mx.com.axity.commons.util;

import mx.com.axity.commons.to.SicoPaysheetTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Date;

public class ValidateDates {

    public static boolean validateSeniority( Date date ) {
        LocalDate localDate = LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return ChronoUnit.MONTHS.between(localDate, LocalDate.now() ) >= 1;
    }

    public static boolean validateWorkedDays( Date date ) {
        LocalDate localDate = LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
        //Descomentar esta linea, esto se hace para la validacion
        return ChronoUnit.DAYS.between( localDate, LocalDate.now() ) >= 1;
    }

    public static LocalDate formatterDateSico( String date ) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return  LocalDate.parse(date,formatter);

    }

    public static boolean range( String dateStart,String dateEnd ) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate localDateStart = LocalDate.parse(dateStart, formatter);
        LocalDate localDateEnd = LocalDate.parse(dateEnd, formatter);

        return (localDateStart.isBefore( LocalDate.now() ) && localDateEnd.isAfter(LocalDate.now()));

    }

    public static LocalDate dateEndPeriod( String wayToPay,LocalDate date ) {
        var endDate = date;
        if( Constants.QUINCENAL.equals(wayToPay.toLowerCase()) ) {
            endDate = date.plusDays(15);
            if( endDate.getDayOfMonth() == 31 ) {
                endDate = endDate.plusDays(1);
            }
        }

        if( Constants.SEMANAL.equals(wayToPay.toLowerCase()) ) {
            endDate = date.plusDays(7);
        }

        if( Constants.CATORCENAL.equals(wayToPay.toLowerCase()) ) {
            endDate = date.plusDays(14);
        }

        if( Constants.MENSUAL.equals(wayToPay.toLowerCase()) ) {
            endDate = date.plusMonths(1);
        }

        return endDate;

    }

}
