
INSERT INTO c_customer(
            ds_name, ds_address, ds_phone,
            ds_extension, ds_email,  ds_status,
            ds_last_user_modifier, dt_last_modification, dt_creation_date,
            fg_active)
    VALUES ( 'Bosch', 'Guillermo González Camarena 333, Santa Fe, Panteón Sta Fé, 01210 Ciudad de México, CDMX',  '5552843000',
            '2532', 'tutienda-Bosch@bshg.com', 'Activo',
            'rhtotal', CURRENT_DATE, CURRENT_DATE, 'true');
   INSERT INTO c_customer(
            ds_name, ds_address, ds_phone,
            ds_extension, ds_email,  ds_status,
            ds_last_user_modifier, dt_last_modification, dt_creation_date,
            fg_active)
    VALUES ('Fandeli', 'Av Presidente Juárez 225, San Jerónimo Tepetlacalco, 54090 Tlalnepantla, MEX, 01210 Ciudad de México, CDMX',  '017139738993',
            '3345', 'servicio@fandeli.com.mx', 'Activo',
            'rhtotal', CURRENT_DATE, CURRENT_DATE,'true');
    INSERT INTO c_customer(
             ds_name, ds_address, ds_phone,
            ds_extension, ds_email,  ds_status,
            ds_last_user_modifier, dt_last_modification, dt_creation_date,
            fg_active)
    VALUES ( 'Herdez', 'San Bartolo-Naucalpan 36, Av. Ingenieros Militares, Argentina Poniente, 11230 Miguel Hidalgo, CDMX',  '015555763100',
            '1973', 'protecciondedatos@herdez.com', 'Activo',
            'rhtotal', CURRENT_DATE, CURRENT_DATE,'true');

     INSERT INTO k_project(
             id_client, ds_name,  ds_business_name,
            ds_address, ds_phone, ds_extension, ds_email,
             ds_status, ds_last_user_modifier,
            dt_last_modification, dt_creation_date, fg_active)
    VALUES ( 1, 'Razón social Bosh Dos',  'Bosch',
            'Guillermo González Camarena 333, Santa Fe, Panteón Sta Fé, 01210 Ciudad de México, CDMX', '5552843000',
            '2532', 'tutienda-Bosch@bshg.com',
             'Activo', 'rhtotal',
            CURRENT_DATE, CURRENT_DATE, 'true');
    INSERT INTO k_project(
             id_client, ds_name,  ds_business_name,
            ds_address, ds_phone, ds_extension, ds_email,
             ds_status, ds_last_user_modifier,
            dt_last_modification, dt_creation_date, fg_active)
    VALUES ( 2, 'Razón social Bosh Tres',  'Bosch',
            'Guillermo González Camarena 333, Santa Fe, Panteón Sta Fé, 01210 Ciudad de México, CDMX', '5552843000',
            '2532', 'tutienda-Bosch@bshg.com',
             'Activo', 'rhtotal',
            CURRENT_DATE, CURRENT_DATE, 'true');


INSERT INTO k_user(id_user, ds_email, ds_user_type, ds_status_user, ds_last_user_modifier, dt_last_modification, dt_creation_date, fg_active,ds_password)
VALUES (1,  'marialopez_89@hotmail.com', 'EXTERNO', 'ACTIVO', 'MALOPEZ', CURRENT_DATE, CURRENT_DATE, 't','12345');

INSERT INTO k_employee
(id_employee,id_client,id_project,ds_civil_status,id_user,ds_name,ds_last_name,ds_m_last_name,ds_gender,ds_last_user_modifier, dt_last_modification,dt_creation_date,fg_active)
VALUES ( 1,1, 1, 'SOLTERO', 1, 'Juan', 'Vazquez', 'Lopez', 'M','rhtotal', CURRENT_DATE, CURRENT_DATE, 'true');

INSERT INTO k_employee_complementary
(id_employee,ds_rfc, ds_curp, ds_nss, ds_email_client, ds_email, ds_phone, ds_work_permit, photography, ds_passport_number, ds_birthdate,ds_last_user_modifier, dt_last_modification, ds_creation_user, dt_creation_date, fg_active)
VALUES(1,'HOLA09090918', 'tttt1234567890121212', '9999999999', 'correo@correo.com', 'correo@correo.com','5555555555', 'SI', '', '123456789',CURRENT_DATE, 'rhtotal', current_date, 'rhtotal', current_date, false);

INSERT INTO w_parameter
(id_parameter,ds_name_parameter,ds_value,ds_description_parameter,ds_last_user_modifier,dt_last_modification,ds_creation_user,dt_creation_date,fg_active) values
(1,'test','1','desc','rh_test',CURRENT_DATE,'rh_test',CURRENT_DATE,TRUE);

INSERT INTO public.k_paysheet_now
(id_paysheet_now, id_employee, ds_requisition_folio, qt_requisition_amount, qt_loan_comission, qt_deposit_mount, dt_requisition_date, dt_requisition_time, qt_paysheet_next, ds_status_requisition, ds_last_user_modifier, dt_last_modification, ds_creation_user, dt_creation_date, fg_active, ds_payment_period, qt_porc_comission, qt_commission, dt_start_date_pp, dt_date_approbation, dt_time_approbation, dt_date_response_swap, dt_time_response_swap, ds_folio_swap, ds_folio_confirmation_sico, ds_result_approbation, ds_description_approbation, ds_entity, ds_reason_reject, id_employee_sico)
VALUES(20, 1, 'AAA', 2012.93, 5.30, 3757.47, '2018-10-29 00:36:12.532', '2018-10-29 00:36:12.532', -1744.54, NULL, 'RH_TOTAL', '2018-10-29 00:36:12.532', 'RH_TOTAL', '2018-10-29 00:36:12.532', true, 'QUINCENAL', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

CREATE SEQUENCE seq_velocahs START 1;

