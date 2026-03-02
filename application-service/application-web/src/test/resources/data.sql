INSERT INTO c_customer(
             ds_name, ds_address, ds_phone,
            ds_extension, ds_email, ds_status,
            ds_last_user_modifier, dt_last_modification, dt_creation_date,
            fg_active)
    VALUES ('Bosch', 'Guillermo González Camarena 333, Santa Fe, Panteón Sta Fé, 01210 Ciudad de México, CDMX',  '5552843000',
            '2532', 'tutienda-Bosch@bshg.com', 'Activo',
            'rhtotal', CURRENT_DATE, CURRENT_DATE, 'true');


INSERT INTO k_banner (ds_internal_comments,ds_title,dt_start_date,dt_time_publicacion,dt_end_date,
ds_status,ds_last_user_modifier,dt_last_modification,ds_creation_user,dt_creation_date,fg_active)
VALUES ('test','test','2015-06-22','15:50:00','2015-06-22','test','test','2015-06-22','test','2015-06-22','false');



INSERT INTO c_customer(
            ds_name, ds_address, ds_phone,
            ds_extension, ds_email,  ds_status,
            ds_last_user_modifier, dt_last_modification, dt_creation_date,
            fg_active)
    VALUES ('Bosch', 'Guillermo González Camarena 333, Santa Fe, Panteón Sta Fé, 01210 Ciudad de México, CDMX',  '5552843000',
            '2532', 'tutienda-Bosch@bshg.com', 'Activo',
            'rhtotal', '2015-06-22','2015-06-22', 'true');

INSERT INTO c_header_display(ds_name_header,value_header,ds_last_user_modifier,dt_last_modification,ds_creation_user,dt_creation_date,fg_active)
VALUES ('headersBanners','"{"allColumns":[{"title:Título","title:Fecha Inicio","title:Fecha Fín","title:Hora de publicación","title:Autor","title:Estatus"}]}"' ,
            'rhtotal',  '2015-06-22','2015-06-22','2015-06-22', 'true');

INSERT INTO c_insurance_carrier
(ds_insurance_carrier, ds_last_user_modifier, dt_last_modification, ds_creation_user, dt_creation_date, fg_active)
VALUES('GNP Seguros','rh_total', current_date,'rh_total', current_date, 'true');

INSERT INTO c_insurance_type
(ds_insurance_type, ds_last_user_modifier, dt_last_modification, ds_creation_user, dt_creation_date, fg_active)
VALUES('GMM', 'rh_total', current_date, 'rh_total', current_date, 'true');


INSERT INTO k_insurance
(ds_insurance_carrier,id_insurance_type,ds_policy, ds_scope, qt_sum, ds_coverage, dt_start_date, dt_end_date, ds_status, ds_individual_certificate, contract_pdf, ds_contact, ds_phones, ds_email, ds_url, ds_comments, ds_last_user_modifier, dt_last_modification, ds_creation_user, dt_creation_date, fg_active)
VALUES('marco',1,'001GNP', 1,0, 'test seguro 1', current_date, current_date+360, 'activo', '0908',null, 'Aseguradora', '555555555', 'correo@orreo.com', 'http://seguros/', 'ninguno','rh_total',current_date, 'rh_total',current_date, 'false');


INSERT INTO c_notification
(ds_title, dt_start_date, ds_notification_text, ds_status, ds_internal_comments, ds_last_user_modifier, dt_last_modification, ds_creation_user, dt_creation_date, fg_active)
VALUES('Taquitos de Canasta',current_date, 'Prueba Notificaciones Taquitos de Canasta','enviado', 'Prueba Nuggets', 'RH_total',current_date ,'RH_total',current_date, 'true');

INSERT INTO w_parameter(
            id_parameter, ds_name_parameter, ds_value, ds_description_parameter,
            ds_last_user_modifier,dt_last_modification,ds_creation_user,dt_creation_date,
            fg_active)
VALUES (1, 'headersBannersXls','allColumns:Título,Fecha Inicio,Fecha Fín,Hora de publicación,Autor,Estatus' , 'Encabezados Tabla Banners',
            'rhtotal', CURRENT_DATE, 'rhtotal',CURRENT_DATE, 'true');


INSERT INTO c_category(ds_category, ds_last_user_modifier, dt_last_modification, ds_creation_user, dt_creation_date, fg_active)VALUES('Comida', 'rh_total', current_date, 'rh_total', current_date, 'true');

INSERT INTO c_subcategory(id_category,ds_subcategory, ds_last_user_modifier, dt_last_modification, ds_creation_user, dt_creation_date, fg_active)
VALUES(1,'Rapida', 'rh_total', current_date, 'rh_total', current_date, 'true');


INSERT INTO k_discount(id_category, id_subcategory, ds_supplier, ds_title, dt_start_date, dt_end_date, ds_state, ds_status, ds_description, ds_link_url, ds_terms_conditions, ds_description_preview, ds_last_user_modifier, dt_last_modification, ds_creation_user, dt_creation_date, fg_active, ds_view_count)
VALUES(1,1, 'Panda', '2x1 en sushi', current_date+1, current_date+10, 'activo', 'activo',  'Promo panda 2x1', 'http://www.panda.com', 'test', '2x1', 'rhtotal', current_date, 'rhtotal', current_date,'true', 0);
