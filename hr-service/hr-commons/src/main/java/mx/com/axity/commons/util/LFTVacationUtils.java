package mx.com.axity.commons.util;

/**
 * Calcula días de vacaciones conforme a la Ley Federal del Trabajo (LFT) mexicana.
 * Reforma 2022: mínimo 12 días el primer año, +2 por cada año adicional hasta el quinto,
 * luego 2 días adicionales cada 5 años.
 */
public class LFTVacationUtils {

    private LFTVacationUtils() {}

    /**
     * Retorna los días de vacaciones a los que tiene derecho un empleado
     * según sus años de servicio completos.
     *
     * @param yearsOfService años completos de antigüedad (≥ 1)
     * @return días de vacaciones mínimos según LFT
     */
    public static int getDaysEntitled(int yearsOfService) {
        if (yearsOfService < 1) return 0;
        if (yearsOfService <= 4) {
            // Años 1-4: 12, 14, 16, 18
            return 10 + (yearsOfService * 2);
        }
        if (yearsOfService <= 9) {
            // Años 5-9: 20 días
            return 20;
        }
        // Años 10+: 20 + 2 días por cada 5 años completos adicionales sobre 5
        int extraPeriods = (yearsOfService - 5) / 5;
        return 20 + (extraPeriods * 2);
    }
}
