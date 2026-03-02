package mx.com.axity.commons.util;


import mx.com.axity.commons.to.CommissionsTO;
import mx.com.axity.commons.to.PaymentPropertiesTO;
import mx.com.axity.commons.to.SicoPaysheetTO;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Operations {


    public static CommissionsTO lendingVeloCahs( double amount,double discountRate ) {

        CommissionsTO commissions = new CommissionsTO();
        DecimalFormat f = new DecimalFormat("##.00");

        var iva = .16;
        var montoDeposito = ( amount - ( (amount * discountRate)/100 ));
        var comisionTotal = amount - montoDeposito;
        var comisionSinIva = comisionTotal / (iva + 1);
        var ivaTotal = (comisionSinIva * iva);

        commissions.setMontoSolicitado(Double.parseDouble(f.format(amount)));
        commissions.setTasaDescuento(Double.parseDouble(f.format(discountRate)));
        commissions.setMontoDeposito(Double.parseDouble(f.format(montoDeposito)));
        commissions.setComisionTotal(Double.parseDouble(f.format(comisionTotal)));
        commissions.setComisionSinIva(Double.parseDouble(f.format(comisionSinIva)));
        commissions.setIva(Double.parseDouble(f.format(ivaTotal)));

        return commissions;
    }

    public static List<PaymentPropertiesTO> lendingMyAdvance(SicoPaysheetTO paysheet,double discountRate, int count ) {

        DecimalFormat f = new DecimalFormat("##.00");
        List<PaymentPropertiesTO> paymentPropertiesList = new ArrayList<>();
        var porcentaje = .20;
        var nomina = Double.parseDouble(paysheet.getNominaPeriodo());
        var iva = .16;

        for (int i = 0; i < count; i++){


            PaymentPropertiesTO paymentProperties = new PaymentPropertiesTO();


            var montoAcumulado = ( nomina - ( porcentaje * nomina ) );
            var montoRestante = ( nomina - montoAcumulado );

            /*** Comisiones ****/

            CommissionsTO commissions = new CommissionsTO();


            var montoDeposito = ( montoRestante - ( (montoRestante * discountRate)/100 ));
            var comisionTotal = montoRestante - montoDeposito;
            var comisionSinIva = comisionTotal / (iva + 1);
            var ivaTotal = (comisionSinIva * iva);

            commissions.setMontoSolicitado(Double.parseDouble(f.format(montoRestante)));
            commissions.setTasaDescuento(Double.parseDouble(f.format(discountRate)));
            commissions.setMontoDeposito(Double.parseDouble(f.format(montoDeposito)));
            commissions.setComisionTotal(Double.parseDouble(f.format(comisionTotal)));
            commissions.setComisionSinIva(Double.parseDouble(f.format(comisionSinIva)));
            commissions.setIva(Double.parseDouble(f.format(ivaTotal)));
            commissions.setPorcentaje(String.valueOf(Math.round( (porcentaje*100) )));

            /*******/


            porcentaje += .2;


            paymentProperties.setCorrespondingAmount(Double.parseDouble(f.format(nomina)));
            paymentProperties.setAcomulatedAmount(Double.parseDouble(f.format(montoRestante)));
            paymentProperties.setNextAmount(Double.parseDouble(f.format(montoAcumulado)));
            paymentProperties.setCommission(discountRate);
            paymentProperties.setFormadePago(paysheet.getPeriodoPago());
            paymentProperties.setSiguientePago(paysheet.getFechaFinPeriodo());
            paymentProperties.setCommisiones( commissions );


            paymentPropertiesList.add(paymentProperties);

        }

        return paymentPropertiesList;
    }


}
