insert into public.c_civil_status(id_civil_status, ds_status_civil, ds_last_user_modifier, dt_last_modification, dt_creation_date, fg_active)
values (1, 'soltero/a', 'rhtotal', current_date, current_date, 't');

insert into public.c_civil_status(id_civil_status, ds_status_civil, ds_last_user_modifier, dt_last_modification, dt_creation_date, fg_active)
values (2, 'comprometido/a', 'rhtotal', current_date, current_date, 't');

insert into public.c_civil_status(id_civil_status, ds_status_civil, ds_last_user_modifier, dt_last_modification, dt_creation_date, fg_active)
values (3, 'casado/a', 'rhtotal', current_date, current_date, 't');

insert into public.c_civil_status(id_civil_status, ds_status_civil, ds_last_user_modifier, dt_last_modification, dt_creation_date, fg_active)
values (4, 'divorciado/a', 'rhtotal', current_date, current_date, 't');

insert into public.c_civil_status(id_civil_status, ds_status_civil, ds_last_user_modifier, dt_last_modification, dt_creation_date, fg_active)
values (5, 'viudo/a', 'rhtotal', current_date, current_date, 't');

INSERT INTO public.c_rol(id_rol, ds_name_rol, ds_permission, ds_last_user_modifier, dt_last_modification, fg_active,ds_creation_user, dt_creation_date)
VALUES (1, 'Administrador Master', '{idRol:1,nombreRol:Administrador Master,permisos:[{nameMenu:Usuarios,nameUrl:/usuarios,submenu:[{nameSubmenu:Usuarios,submenuUrl:/crear-usuarios }]},{nameMenu:Descuentos,nameUrl:/descuentos,submenu:[{nameSubmenu:Crear descuento nuevo,submenuUrl:/crear-desuento},{nameSubmenu:Catalogo de categorias,submenuUrl:/catologo-categorias}]},{nameMenu:Banners,nameUrl:/banners,submenu:[{nameSubmenu:Crear banner nuevo,submenuUrl:/crear-banner}]},{nameMenu:Notificaciones,nameUrl:/notificaciones,submenu:[{nameSubmenu:Crear notificacion nueva,submenuUrl:/crear-notificacion}]},{nameMenu:Seguros,nameUrl:/seguros,submenu:[{nameSubmenu:Crear seguro nuevo,submenuUrl:/crear-seguro}]},{nameMenu:Anticipo de Nomina,nameUrl:/anticipoNomina},{nameMenu:Clientes,nameUrl:/clientes,submenu:[{nameSubmenu:Crear cliente/proyecto nuevo,submenuUrl:/crear-cliente}]},{nameMenu:Roles,nameUrl:/roles,submenu:[{nameSubmenu:Crear rol nuevo,submenuUrl:/crear-rol}]}]}', 'rhtotal', CURRENT_DATE, 'true','RHTOTAL', CURRENT_DATE);

INSERT INTO public.c_rol(id_rol, ds_name_rol, ds_permission, ds_last_user_modifier, dt_last_modification, fg_active,ds_creation_user, dt_creation_date)
VALUES (2, 'Administrador Web', '{idRol:2,nombreRol:Administrador Web,permisos:[{nameMenu:Usuarios,nameUrl:/usuarios,submenu:[{nameSubmenu:Usuarios,submenuUrl:/crear-usuarios}]},{nameMenu:Descuentos,nameUrl:/descuentos,submenu:[{nameSubmenu:Crear descuento nuevo,submenuUrl:/crear-desuento},{nameSubmenu:Catalogo de categorias,submenuUrl:/catologo-categorias					}]},{nameMenu:Banners,nameUrl:/banners,submenu:[{nameSubmenu:Crear banner nuevo,submenuUrl:/crear-banner}]},{nameMenu:Notificaciones,nameUrl:/notificaciones,submenu:[{nameSubmenu:Crear notificacion nueva,submenuUrl:/crear-notificacion}]},{nameMenu:Seguros,nameUrl:/seguros,submenu:[{nameSubmenu:Crear seguro nuevo,submenuUrl:/crear-seguro}]},{nameMenu:Anticipo de Nomina,nameUrl:/anticipoNomina},{nameMenu:Clientes,nameUrl:/clientes,submenu:[{nameSubmenu:Crear cliente/proyecto nuevo,submenuUrl:/crear-cliente}]}]}', 'rhtotal', CURRENT_DATE, 'true', 'RHTOTAL',CURRENT_DATE);

INSERT INTO public.c_rol(id_rol, ds_name_rol, ds_permission, ds_last_user_modifier, dt_last_modification, fg_active, ds_creation_user,dt_creation_date)
VALUES (3, 'Administrador Financiero', '{idRol:3,nombreRol:Administrador Financiero,permisos:[{nameMenu:Anticipo de Nomina,nameUrl:/anticipoNomina}]}', 'rhtotal', CURRENT_DATE, 'true','RHTOTAL',CURRENT_DATE);

INSERT INTO public.c_rol(id_rol, ds_name_rol, ds_permission, ds_last_user_modifier, dt_last_modification, fg_active,ds_creation_user, dt_creation_date)
VALUES (4, 'Administrador Servicios', '{idRol:4,nombreRol:Administrador Servicios,permisos:[{nameMenu:Descuentos,nameUrl:/descuentos,submenu:[{nameSubmenu:Crear descuento nuevo,submenuUrl:/crear-desuento},{nameSubmenu:Catalogo de categorias,submenuUrl:/catologo-categorias}]},{nameMenu:Banners,nameUrl:/banners,submenu:[{nameSubmenu:Crear banner nuevo,submenuUrl:/crear-banner}]},{nameMenu:Notificaciones,nameUrl:/notificaciones,submenu:[{nameSubmenu:Crear notificacion nueva,submenuUrl:/crear-notificacion}]},{nameMenu:Seguros,nameUrl:/seguros,submenu:[{nameSubmenu:Crear seguro nuevo,submenuUrl:/crear-seguro}]}]}', 'rhtotal', CURRENT_DATE, 'true','RHTOTAL' ,CURRENT_DATE);

INSERT INTO public.c_rol(id_rol, ds_name_rol, ds_permission, ds_last_user_modifier, dt_last_modification, fg_active,ds_creation_user, dt_creation_date)
VALUES (5, 'Administrador RH', '{idRol:5,nombreRol:Administrador RH,permisos: [{nameMenu:Usuarios,nameUrl:/usuarios,submenu:[{nameSubmenu:Usuarios,submenuUrl:/crear-usuarios}]},{nameMenu:Clientes,nameUrl:/clientes,submenu:[{nameSubmenu:Crear cliente/proyecto nuevo,submenuUrl:/crear-cliente}]}]}', 'rhtotal', CURRENT_DATE, 'true','RHTOTAL',CURRENT_DATE);

INSERT INTO k_banner (ds_internal_comments,ds_title,dt_start_date,dt_time_publicacion,dt_end_date,
ds_status,ds_last_user_modifier,dt_last_modification,ds_creation_user,dt_creation_date,fg_active)
VALUES ('test','test','2015-06-22','15:09:00','2015-06-22','test','test','2015-06-22','test','2015-06-22','false');

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
VALUES('INBURSA',1,'001GNP', 1,0, 'test seguro 1', current_date, current_date+360, 'activo', '0908',null, 'Aseguradora', '555555555', 'correo@orreo.com', 'http://seguros/', 'ninguno','rh_total',current_date, 'rh_total',current_date, 'false');


INSERT INTO c_notification
(ds_title, dt_start_date, ds_notification_text, ds_status, ds_internal_comments, ds_last_user_modifier, dt_last_modification, ds_creation_user, dt_creation_date, fg_active)
VALUES('Taquitos de Canasta', current_date,  'Prueba Notificaciones Taquitos de Canasta','E', 'Prueba Nuggets', 'RH_total',current_date ,'RH_total',current_date, 'true');

INSERT INTO w_parameter(
            id_parameter, ds_name_parameter, ds_value, ds_description_parameter,
            ds_last_user_modifier,dt_last_modification,ds_creation_user,dt_creation_date,
            fg_active)
VALUES (1, 'headersBannersXls','allColumns:Título,Fecha Inicio,Fecha Fín,Hora de publicación,Autor,Estatus' , 'Encabezados Tabla Banners',
            'rhtotal', CURRENT_DATE, 'rhtotal',CURRENT_DATE, 'true');





INSERT INTO w_parameter(
            id_parameter, ds_name_parameter, ds_value, ds_description_parameter,
            ds_last_user_modifier,dt_last_modification,ds_creation_user,dt_creation_date,
            fg_active)
VALUES (2, 'headersCustomerXls','Cliente,Proyecto,Empleadora,Ingreso Total Mensual,Estatus' , 'Encabezados Tabla Customer',
            'rhtotal', CURRENT_DATE, 'rhtotal',CURRENT_DATE, 'true');



INSERT INTO k_notification_repository (id_element, ds_type, ds_status, dt_date_notification, ds_description, ds_title, ds_description_small, ds_subcategory, ds_last_user_modifier, dt_last_modification, ds_creation_user, dt_creation_date, fg_active)
VALUES (1, 'D', 'E', '2018-11-02 10:00:00.000000', 'Descuento en tennis para toda la familia', 'Promo Tennis 50 desc', 'Tennis Nike 50 descuento', 'D', 'info@rhtotal.com', '2018-10-27 19:09:19.794238', 'info@rhtotal.com', '2018-10-27 05:39:31.826000', true);

INSERT INTO k_notification_assignment(id_user, id_notification, id_cliente, id_proyecto, ds_type_notification, ds_last_user_modifier, dt_last_modification, ds_creation_user, dt_creation_date, fg_active)
VALUES (1, 1, 1, 1, 'D', current_date, current_date, 'test', current_date, true);

INSERT INTO k_token_notification(id_user, ds_token, ds_user, ds_last_user_modifier, dt_last_modification, ds_creation_user, dt_creation_date, fg_active)
VALUES (1, 'token', 'prueba', 'test', current_date, 'test', current_date , true);

INSERT INTO w_parameter(id_parameter, ds_name_parameter, ds_value, ds_description_parameter, ds_last_user_modifier, dt_last_modification, ds_creation_user,dt_creation_date, fg_active)
VALUES (9, 'firebaseUrl', 'https://fcm.googleapis.com/fcm/send', 'Se guarda url de firebase', 'rhtotal', CURRENT_DATE, 'rhtotal',CURRENT_DATE, 'true');

INSERT INTO w_parameter(id_parameter, ds_name_parameter, ds_value, ds_description_parameter, ds_last_user_modifier, dt_last_modification, ds_creation_user,dt_creation_date, fg_active)
VALUES (10, 'firebaseToken', 'AAAARgkw6iU:APA91bFPFq65kjw1YivBf-nhyEnWDtZgL9pWj9k7G47Lk2efuj13-CMswFrM6Roe8dKCiqWNmMTcNakkvLXxXSxJKYzS7Mh4e2YKNqqnjvGlu574kf0xD4zFaw4mLQ2X4Sipw0ah7cDNHxAWCkFoNfrEh4t3W3wbvg', 'Se guarda Token de firebase', 'rhtotal', CURRENT_DATE, 'rhtotal',CURRENT_DATE, 'true');

INSERT INTO w_parameter(id_parameter, ds_name_parameter, ds_value, ds_description_parameter,ds_last_user_modifier,dt_last_modification,ds_creation_user,dt_creation_date,fg_active)VALUES (3, 'headersDiscountsXls','Categoría,Subcategoría,Proveedor,Título,Ubicación,Fecha Inicio,Fecha Fin,Estatus,Veces vista' , 'Encabezados Tabla descuentos','rhtotal', CURRENT_DATE,'rhtotal', CURRENT_DATE, 'true');



INSERT INTO c_category
(ds_category, ds_last_user_modifier, dt_last_modification, ds_creation_user, dt_creation_date, fg_active)
VALUES('Comida', 'rh_total', current_date, 'rh_total', current_date, true);

INSERT INTO c_category
(ds_category, ds_last_user_modifier, dt_last_modification, ds_creation_user, dt_creation_date, fg_active)
VALUES('Deportes', 'rh_total', current_date, 'rh_total', current_date, true);

INSERT INTO c_subcategory
(id_category,ds_subcategory, ds_last_user_modifier, dt_last_modification, ds_creation_user, dt_creation_date, fg_active)
VALUES(1,'Rapida', 'rh_total', current_date, 'rh_total', current_date, true);

INSERT INTO c_subcategory
(id_category,ds_subcategory, ds_last_user_modifier, dt_last_modification, ds_creation_user, dt_creation_date, fg_active)
VALUES(2,'Tennis', 'rh_total', current_date, 'rh_total', current_date, true);
INSERT INTO k_discount
(id_category, id_subcategory, ds_supplier, ds_title, dt_start_date, dt_end_date, ds_state, ds_status, ds_description, ds_link_url, ds_terms_conditions, ds_description_preview, ds_last_user_modifier, dt_last_modification, ds_creation_user, dt_creation_date, fg_active, ds_view_count)
VALUES(1, 1, 'Burguer King', ' 2 x 1 Hamburguesas', current_timestamp,current_timestamp , 'Mexico', 'A', 'Descuento en Hamburguesas', 'http://burguer.king.com', 'Sólo Hamburguesas pequeñas','Otras' ,'rhtotal', current_timestamp, 'rhtotal',current_timestamp,true, 0);















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
VALUES (1,  'marialopez_89@hotmail.com', 'EX', 'ACTIVO', 'MALOPEZ', CURRENT_DATE, CURRENT_DATE, 't','12345');

INSERT INTO k_employee(
  id_employee, id_client, id_project, ds_civil_status, id_user, ds_name, ds_last_name, ds_m_last_name, ds_gender, ds_last_user_modifier, dt_last_modification, dt_creation_date,fg_active)
VALUES (1, 1, 1, 'artur', 1, 'Juan', 'Vazquez', 'Lopez', 'M', 'rhtotal', CURRENT_DATE, CURRENT_DATE, 'true');

 INSERT INTO k_user(
            id_user, ds_email, ds_user_type, ds_status_user, ds_last_user_modifier,
            dt_last_modification, dt_creation_date, fg_active,ds_password)
    VALUES (2,  'juanvazquez_87@gmail.com', 'IN', 'ACTIVO', 'JVAZQUEZ',
            CURRENT_DATE, CURRENT_DATE, 'true','12345');
  INSERT INTO k_user(
            id_user, ds_email, ds_user_type, ds_status_user, ds_last_user_modifier,
            dt_last_modification, dt_creation_date, fg_active,ds_password)
    VALUES (3,  'juanrodriguez_88@gmail.com', 'IN', 'INACTIVO', 'JRODRIGUEZ',
            CURRENT_DATE, CURRENT_DATE, 'true','12345');


INSERT INTO c_level_employee
(ds_level, ds_last_user_modifier, dt_last_modification, ds_creation_user, dt_creation_date, fg_active)
VALUES('Oro', 'rhtotal',current_timestamp, 'rhtotal', current_timestamp, false);

INSERT INTO c_level_employee
(ds_level, ds_last_user_modifier, dt_last_modification, ds_creation_user, dt_creation_date, fg_active)
VALUES('Plata', 'rhtotal',current_timestamp, 'rhtotal', current_timestamp, false);

INSERT INTO c_level_employee
(ds_level, ds_last_user_modifier, dt_last_modification, ds_creation_user, dt_creation_date, fg_active)
VALUES('Bronce', 'rhtotal',current_timestamp, 'rhtotal', current_timestamp, false);


INSERT INTO k_contrating_data
(id_user,qt_salary, ds_area, cv, contract, ds_last_user_modifier, dt_last_modification, ds_creation_user, dt_creation_date, fg_active)
VALUES(1,1000, 'Admon', '', '', 'rhtotal', current_timestamp, 'rhtotal',current_timestamp, false);

INSERT INTO k_contrating_data
(id_user,qt_salary, ds_area, cv, contract, ds_last_user_modifier, dt_last_modification, ds_creation_user, dt_creation_date, fg_active)
VALUES(2,15000, 'Admon', '', '', 'rhtotal', current_timestamp, 'rhtotal',current_timestamp, false);

INSERT INTO k_contrating_data
(id_user,qt_salary, ds_area, cv, contract, ds_last_user_modifier, dt_last_modification, ds_creation_user, dt_creation_date, fg_active)
VALUES(3,7000, 'Admon', '', '', 'rhtotal', current_timestamp, 'rhtotal',current_timestamp, false);

