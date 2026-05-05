import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Mexican CURP validator
 * Format: 4 letters + 6 digits + 1 letter (gender) + 2 letters (state) + 3 consonants + 1 digit
 */
export const CURP_REGEX =
  /^([A-Z][AEIOUX][A-Z]{2}\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[12]\d|3[01])[HM](?:AS|B[CS]|C[CLMSH]|D[FG]|G[TR]|HG|JC|M[CNS]|N[ETL]|OC|PL|Q[TR]|S[PLR]|T[CSL]|VZ|YN|ZS)[B-DF-HJ-NP-TV-Z]{3}[A-Z\d])(\d)$/;

/**
 * Mexican RFC validator
 * Format: 3-4 letters + 6 digits + 2-3 alphanumeric characters
 */
export const RFC_REGEX = /^[A-ZÑ&]{3,4}(\d{6})((\D|\d){2,3})?$/i;

/**
 * Mexican NSS (Social Security Number) validator — 11 digits
 */
export const NSS_REGEX = /^\d{11}$/;

/**
 * Email validator
 */
export const EMAIL_REGEX = /^[-\w.%+]{1,64}@(?:[A-Z0-9-]{1,63}\.){1,125}[A-Z]{2,63}$/i;

/**
 * Password: min 8 chars, max 50, at least 1 digit, 1 uppercase, 1 lowercase
 */
export const PASSWORD_REGEX = /^(?=.*\d)(?=.*[A-Z])(?=.*[a-z])\S{8,50}$/;

/**
 * Only letters and spaces (Spanish characters included)
 */
export const NAME_REGEX = /^[A-Za-zÑñÁÉÍÓÚáéíóúÜü\s.-]+$/;

/**
 * URL validator
 */
export const URL_REGEX = /^https?:\/\/[\w\-]+(\.[\w\-]+)+[/#?]?.*$/;

/**
 * Decimal with up to 9 integer digits and 2 decimal places
 */
export const DECIMAL_REGEX = /^[0-9]{1,9}(\.[0-9]{1,2})?$/;

// Angular Reactive Form Validators

export function curpValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    if (!control.value) return null;
    return CURP_REGEX.test(control.value.toUpperCase()) ? null : { curp: true };
  };
}

export function rfcValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    if (!control.value) return null;
    return RFC_REGEX.test(control.value.toUpperCase()) ? null : { rfc: true };
  };
}

export function nssValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    if (!control.value) return null;
    return NSS_REGEX.test(control.value) ? null : { nss: true };
  };
}

export function passwordValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    if (!control.value) return null;
    return PASSWORD_REGEX.test(control.value) ? null : { passwordStrength: true };
  };
}

export function emailValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    if (!control.value) return null;
    return EMAIL_REGEX.test(control.value) ? null : { emailFormat: true };
  };
}
