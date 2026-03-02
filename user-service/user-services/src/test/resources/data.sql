INSERT INTO public.k_user
(ds_email, ds_user_type, ds_status_user, ds_level_rh, ds_last_user_modifier, dt_last_modification, ds_creation_user, dt_creation_date, fg_active, ds_password)
VALUES('uncorreox@correo.com', 'inci', 'o cons', 0, 'sint occaecat cupidatat non proident, sunt in culpa qui offic', '2027-07-10 08:22:44.270', 'dunt ut labore et dolore magna ', '1964-04-03 11:18:38.466', false, ' in voluptate velit esse cillum ');

INSERT INTO public.w_code_usernew
(ds_code, ds_user, ds_password_temp, ds_status_code, ds_last_user_modifier, dt_last_modification, ds_creation_user, dt_creation_date, fg_active)
VALUES( '2345', 'uncorreox@correo.com', 'unpassx', 'A', 'rhtotal', '2033-03-28 02:37:14.945', 'rhtotal', '2032-08-16 12:15:53.848', true);

INSERT INTO public.w_code_reset_token_mobile
(ds_email, ds_token)
VALUES('uncorreox@correo.com', '7dae23ff-e3c7-4622-873e-aa32e2bdfcd5');


INSERT INTO public.w_parameter
(ds_name_parameter, ds_value, ds_description_parameter, ds_last_user_modifier, dt_last_modification, ds_creation_user, dt_creation_date, fg_active)
VALUES('domainNameM', 'http://localhost:4200', ' amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labor', 'd est laborum.Lorem ipsu', '1998-05-20 01:41:41.212', 'llum dolore eu fugiat nulla p', '1945-09-23 22:44:41.746', true);

INSERT INTO public.w_parameter
(ds_name_parameter, ds_value, ds_description_parameter, ds_last_user_modifier, dt_last_modification, ds_creation_user, dt_creation_date, fg_active)
VALUES('telEmpHelpM', '(55) 4356 2345', 'ed do eiusmod te', 'equat. Duis aute irure dolor in reprehender', '1960-12-01 14:31:09.455', 'ud exercitation ullamco laboris nisi ut aliquip ex ea commodo conse', '2026-08-09 05:25:19.734', false);

INSERT INTO public.k_employee
(id_user, ds_name, ds_civil_status, ds_last_name, ds_m_last_name, ds_gender, ds_last_user_modifier, dt_last_modification, ds_creation_user, dt_creation_date, fg_active)
VALUES(1, 'alessandro', '', 'auilar', 'chombo', '', '', '2018-10-01 15:37:48.068', '', '2018-10-01 15:37:48.068', true);