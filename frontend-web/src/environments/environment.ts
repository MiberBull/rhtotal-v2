import { RFC_2822 } from 'moment';

export const environment = {
  production: false,
  DEFAULT_TENANT: 'demo-corp',
  PARAMS: 'params',
  INTENTS: 'value',
  ANSWER_LOGIN: 'answerLogin',
  USER_ERROR: 'El usuario que ingreso no es válido',
  LONG_EMAIL: 50,
  EMPTY_INPUT: '',
  EMAIL_REGEX: /^[-\w.%+]{1,64}@(?:[A-Z0-9-]{1,63}\.){1,125}[A-Z]{2,63}$/i,
  VALUE_DECREASE: 1,
  MIN_LENGTH_PASSWORD: 8,
  MAX_LENGTH_PASSWORD: 50,
  MENU: 'menu',
  ERROR_SERVIDOR: 'Error en el servidor',
  POINT: '.',
  SLICE: 1,
  DATOS_CORRECTOS: 'Los datos han sido guardados correctamente',
  EMAIL: 'Correo electrónico invalido',
  ROL_USER_READ: 7,
  DEFAULT_VALUE_EVENTUALIDAD: '0',
  NAME_EXCEL_NOTIFICATION_PROGRAMADAS: 'NotificacionesProgramadas',
  NAME_EXCEL_NOTIFICATION_ENVIADAS: 'NotificacionesEnviadas',
  ROOT_NOTIFICATION: '/home/notificaciones',
  URL_USERS_DATA: '/home/admin-usuario/datos-personales',
};

export const MSG = {
  OK: 'Los datos han sido guardados correctamente',
  INFORMATION_IS_MISSING: 'Favor de validar campos requeridos(*)',
  SERVER_ERROR: 'Ocurrio un error en el servidor',
  INVALID_EMAIL: 'La cuenta de correo electrónico no es válida, intente nuevamente',
  USE_EMAIL:
    'La cuenta de correo electrónico que solicita registrar ya se encuentra en uso, intente con una nueva',
  ERROR_TWO_DATE: 'La fecha inicio tiene que ser menor a la fecha fin',
  IMAGE_EXIST: 'Ingrese mínimo una imagen',
  IMAGE_FORMAT_VALIT: 'Ingrese sólo imágenes con formato .jpg o .png para la imagen',
  NOTIFICATION_START_END: 'Ingrese Fecha inicio y Fecha fin',
  MSG_SAVE_UPDATE: 'Los datos han sido guardados correctamente',
  MSG_CONFIR_STATUS_INSURANCE: 'Se ha modificado el estatus del Seguro',
  MSG_SIZE_FILE: 'Se requiere un archivo menor a 2MB',
  TITLE_EVENTUALIDAD: 'Eventualidades Amparadas',
  TITLE_COBERTURA: 'Cobertura Plan',
  FILE_PDF: 'Selecciona sólo archivos con formato PDF',
  ERROR_DATE_USERS: 'La fecha de ingreso tiene que ser menor a la fecha de salida',
  NOTIFICATION_START_END_USERS: 'Ingrese fecha de ingreso y fecha de salida',
};

export const EXPRESSION = {
  EMAIL: /^[-\w.%+]{1,64}@(?:[A-Z0-9-]{1,63}\.){1,125}[A-Z]{2,63}$/i,
  INPUT_TEXT: /^[A-Za-z\s.]+$/,
  USERNAME_TEXT: /^[A-Za-zÑñÁËÍÓÚáéíóúÜü\s.-]+$/,
  OWN_NAME: /^[\S][A-Za-zÑñÁËÍÓÚáéíóúÜü\s.-]+$/,
  ALPHANUMERIC_CODE: /^\S/,
  NUMBER: /^[0-9]+$/,
  DECIMAL: /^[0-9]{1,9}(\.[0-9]{1,2})?$/,
  DECIMAL_ONE_NINE: /^[0-9]{1,9}(\.[0-9]{1,2})?$/,
  DECIMAL_ONE_TWO: /^[0-9]{1,2}(\.[0-9]{1,2})?$/,
  URL_VALID: /^http(s)?:\/\/[\w-]+(\.[\w-]+)+[/#?]?.*$/,
  CURP: /^([A-Z][AEIOUX][A-Z]{2}\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[12]\d|3[01])[HM](?:AS|B[CS]|C[CLMSH]|D[FG]|G[TR]|HG|JC|M[CNS]|N[ETL]|OC|PL|Q[TR]|S[PLR]|T[CSL]|VZ|YN|ZS)[B-DF-HJ-NP-TV-Z]{3}[A-Z\d])(\d)$/,
  RFC: /^[a-zA-Z]{3,4}(\d{6})((\D|\d){2,3})?$/,
  CURRENCY: /^(\d{1}\.)?(\d+\.?)+(,\d{2})?$/,
  PASSWORD: /^(?=.*\d)(?=.*[A-Z])(?=.*[a-z])(?=.*)\S{8,50}$/,
  PAGE_URL: /^https?:\/\/[\w-]+(\.[\w-]+)+[/#?]?.*$/,
};

export const parameters = {
  name: 'name',
  intents: 'numberIntent',
  TEMPLATE: 'html',
  EMAIL: 'email',
  FORGET_PASS: 'forget_pass',
  CHANGE_PASS: 'change_pass',
  APP: 'mobile',
  WEB: 'web',
};

export const TEMPLATE = {
  FORGET_PASS: 'NOMBRE',
};

export const PATH_CLIENT = {
  //DOMAIN:'http://localhost:8000/api/application',
  DOMAIN: 'http://localhost:8000/api/application',
  CLIENT: 'client/saveOrUpdateClient',
  SEARCH_BY_ID: 'client/getClient',
  PAGINATOR: 'client/getNumberRow',
};

export const PATH_SECURITY = {
  //DOMAIN:'http://localhost:8000/api/security',
  DOMAIN: 'http://localhost:8000/api/security',
  GET_PARAMETERS: 'parameter/getParameter',
  LOGIN: 'login/loginWeb',
  SEND_EMAIL: 'email/sendEmail',
  ROLES_CATALOGUE: 'role/getAllCatalogue',
  USER_ROLE_INFO: 'role/getRole',
  SAVE_USER_ROLE: 'role/saveOrUpdateRole',
  TABLE_ROLE: 'role/getPagedRole',
  COUNT_ROLE: 'role/getNumberRow',
  RESET_REQUEST_ENDPOINT: 'role/reset/request',
  RESET_CONFIRMATION_ENDPOINT: 'role/reset/confirmation',
};

export const PATH_APPLICATION = {
  //DOMAIN: 'http://localhost:8091',
  //DOMAIN:'http://localhost:8000/api/application',
  DOMAIN: 'http://localhost:8000/api/application',
  TABLE_BANNER: 'banner/getPagedBanner',
  TABLE_NOTIFICATION: 'notification/getPagedNotification',
  TABLE_DISCOUNTS: 'discount/getPagedDiscount',
  TABLE_INSURANCE: 'insurance/getAllInsurance',
  TABLE_USERS: '',
  HEADERS: 'generic/getHeader',
  SAVE_NOTIFICATION: 'notification/saveOrUpdateNotification',
  GET_NOTIFCATION: 'notification/getNotification',
  COUNT_NOTIFICATION: 'notification/getNumberRow',
  COUNT_BANNER: 'banner/getNumberRow',
  NEW_BANNER: `banner/saveOrUpdateBanner`,
  GET_BANNER: `banner/getBanner`,
  TREE: `http://localhost:8000/api/application/notificationassignment/getAssignmentBenefitsNotifications`,
  GET_ONE_INSURANCE: 'insurance/getOneInsurance',
  GET_ALL_EVENTUALY: 'insurance/getAllEventualy',
  GET_ALL_COVERAGE: 'insurance/getAllPlanCoverage',
  SAVE_UPDATE_INSURANCE: 'insurance/saveUpdateInsurance',
  GET_ONE_EVENTUALY: 'insurance/getOneEventualy',
  GET_ONE_PLAN_COVERAGE: 'insurance/getOnePlanCoverage',
  COUNT_INSURANCE: 'insurance/getNumberRow',
  COUNT_EVENTUALY: 'insurance/getNumberRowEventualy',
  COUNT_COVERAGE: 'insurance/getNumberRowCoverage',
};

export const routesWeb = {
  LOGIN: 'login',
  NEW_USER: 'nuevo-usuario',
  HOME: '/home',
  EMPTY: '',
  ROOT: '/',
  ADMIN_ROLES: '/adminRoles',
  ROLES: '/roles',
  ADMIN_INSURANCE: 'admin-seguros',
  DISCOUNT: 'descuentos',
  ADMIN_DISCOUNT: 'admin-descuentos',
  BANNERS: 'banners',
  ADMIN_BANNERS: 'admin-banners',
  ADMIN_USER: 'admin-usuario',
  ADMIN_NOTIFICATION: 'admin-notificaciones',
  NOTIFICATION: 'notificaciones',
  RESET_ACCOUNT: 'reset',
  RESET_ACCOUNT_CONFIRMATION: 'reset-confirmation',
  CUSTOMER: 'clientes',
  ADMIN_CUSTOMER: 'crear-cliente',
  INSURANCE: 'seguros',
  INSURANCE_ADMIN: 'admin-seguros',
  VACACIONES: 'vacaciones',
  INCIDENCIAS: 'incidencias',
  TICKETS: 'tickets',
  ENCUESTAS: 'encuestas',
  REPSE_CUMPLIMIENTO: 'repse-cumplimiento',
  REPSE_PERFIL: 'repse-perfil',
  DOCUMENTOS: 'documentos',
  CFDI: 'cfdi',
  TENANTS: 'tenants',
};

export const BUTTON = {
  SAVE: 'Guardar',
  EDIT: 'Editar',
};

export const BREADCRUMB = {
  NEW_ROL: 'Rol nuevo',
  DETAIL_ROLE: 'Rol detalle',
  ROLES: 'Roles',
  NEW_NOTIFICATION: 'Notificación nueva',
  DETAIL_NOTIFICATION: 'Notificación detalle',
  INSURANCE: 'Seguros',
  NEW_INSURANCE: 'Seguro nuevo',
  DETAIL_INSURANCE: 'Seguro detalle',
  USER: 'Usuario',
  BANNER: 'Banners',
  NOTIFICATION: 'Notificaciones',
  DISCOUNT: 'Beneficios y Descuentos',
  DETAIL_DISCOUNT: 'Beneficio o Descuento detalle',
  NEW_DISCOUNT: 'Beneficio o Descuento Nuevo',
  DETAIL_BANNER: 'Banner detalle',
  NEW_BANNER: 'Nuevo banner',
  NEW_CLIENT: 'Cliente nuevo',
  DETAIL_CLIENT: 'Cliente detalle',
  DETAIL_USER: 'Usuario detalle',
  NEW_USER: 'Usuario nuevo',
  VACACIONES: 'Vacaciones',
  INCIDENCIAS: 'Incidencias',
  TICKETS: 'Mesa de Ayuda',
  ENCUESTAS: 'Encuestas',
  REPSE_CUMPLIMIENTO: 'Cumplimiento REPSE',
  REPSE_PERFIL: 'Perfil REPSE',
  DOCUMENTOS: 'Expediente Digital',
  CFDI: 'Recibos de Nómina',
};

export const NUMBERS = {
  CERO: 0,
  ONE: 1,
  TWO: 2,
  THREE: 3,
  FOUR: 4,
  FIVE: 5,
  SIX: 6,
  SEVEN: 7,
  EIGHT: 8,
  NINE: 9,
  TEN: 10,
  EIGHTEEN: 18,
};

export const FAB = {
  ADD: 'add',
};

export const pathclient = {
  DOMAIN: 'http://localhost:8000/api/security',
  LOGIN: 'client/saveOrUpdateClient',
};

export const DECLARATION = {
  EMPTY_INPUT: '',
};

export const STORAGE = {
  ANSWER_LOGIN: 'answerLogin',
  TOKEN: 'token',
};

export const STATUS_ROLES = [
  { id: 'A', status: 'Activo' },
  { id: 'I', status: 'Inactivo' },
];

export const DICTIONARY = {
  ALPHABET: '0123456789ABCDEFGHIJKLMNÑOPQRSTUVWXYZ',
};

export const TABLE_ROUTE = {
  ROLES: `${PATH_SECURITY.DOMAIN}/${PATH_SECURITY.TABLE_ROLE}`,
  BANNERS: `${PATH_APPLICATION.DOMAIN}/${PATH_APPLICATION.TABLE_BANNER}`,
  NOTIFICATION: `${PATH_APPLICATION.DOMAIN}/${PATH_APPLICATION.TABLE_NOTIFICATION}`,
  DISCOUNTS: `${PATH_APPLICATION.DOMAIN}/${PATH_APPLICATION.TABLE_DISCOUNTS}`,
  INSURANCE: `${PATH_APPLICATION.DOMAIN}/${PATH_APPLICATION.TABLE_INSURANCE}`,
  SELECTED_ONE_INSURANCE: `${PATH_APPLICATION.DOMAIN}/${PATH_APPLICATION.GET_ONE_INSURANCE}`,
  SHOW_ALL_EVENTUALY: `${PATH_APPLICATION.DOMAIN}/${PATH_APPLICATION.GET_ALL_EVENTUALY}`,
  SHOW_ALL_COVERAGE: `${PATH_APPLICATION.DOMAIN}/${PATH_APPLICATION.GET_ALL_COVERAGE}`,
  SHOW_COUNT_INSURANCE: `${PATH_APPLICATION.DOMAIN}/${PATH_APPLICATION.COUNT_INSURANCE}`,
  SHOW_COUNT_EVENTUALY: `${PATH_APPLICATION.DOMAIN}/${PATH_APPLICATION.COUNT_EVENTUALY}`,
  SHOW_COUNT_COVERAGE: `${PATH_APPLICATION.DOMAIN}/${PATH_APPLICATION.COUNT_COVERAGE}`,
};

export const HEADERS = {
  ROLES: 'headersRol',
  BANNERS: `headersBanners`,
  NOTIFICATION: 'headersNotifications',
  DISCOUNTS: 'headersDiscounts',
  INSURANCE: 'headersInsurance',
  USERS: 'headersUsers',
  EVENTUALY_HEADER: 'headerEventuality',
  COVERAGE_HEADER: 'headerPlanCoverage',
};

export const EMAIL = {
  IMG_EMAIL: 'imgEmailUnbound',
  NEW_USER: 'layoutNewUser',
};

export const PATH_EXCEL = {
  GENERIC: `${PATH_APPLICATION.DOMAIN}/generic/sendExcel`,
  ROLES: `${PATH_SECURITY.DOMAIN}/role/sendExcel`,
};

export const DIALOG_TITLES = {
  LOGOUT: '¿Deseas cerrar sesión?',
  CREATE_INCIDENT: 'Nueva Incidencia',
  SURVEY_RESULTS: 'Resultados de Encuesta',
  UPLOAD_REPSE_DOC: 'Subir Documento REPSE',
  UPLOAD_EMP_DOC: 'Subir Documento',
};

export const PATH_REPSE = {
  DOMAIN: 'http://localhost:8000/api/application',
  PROFILE: 'repse/profile',
  CLIENT_ALL: 'repse/client/all',
  CLIENT: 'repse/client',
  DOCUMENT: 'repse/document',
  COMPLIANCE_DASHBOARD: 'repse/compliance/dashboard',
  COMPLIANCE_SEMAFORO: 'repse/compliance/semaforo',
  COMPLIANCE_RECALCULATE: 'repse/compliance/recalculate',
  COMPLIANCE_EXPIRING: 'repse/compliance/expiring',
  COMPLIANCE_EXPORT: 'repse/compliance/export',
  COMPLIANCE_REPORT: 'repse/compliance/report',
};

export const PATH_DOCUMENT = {
  DOMAIN: 'http://localhost:8000/api/document',
  CFDI_IMPORT: 'cfdi/import',
  CFDI_BY_EMPLOYEE: 'cfdi/employee',
  CFDI_BY_PERIOD: 'cfdi/period',
  DOC_UPLOAD: 'document/upload',
  DOC_BY_EMPLOYEE: 'document/employee',
  DOC_PENDING: 'document/pending',
  DOC_VALIDATE: 'document',
  DOC_REJECT: 'document',
  DOC_TYPE_ALL: 'document-type',
  DOC_TYPE_REQUIRED: 'document-type/required',
};

export const PATH_HR = {
  DOMAIN: 'http://localhost:8000/api/hr',
  VACATION_PENDING: 'vacation/request/pending',
  VACATION_BY_EMP: 'vacation/request/employee',
  VACATION_BALANCE: 'vacation/balance',
  VACATION_APPROVE: 'vacation/request',
  VACATION_REJECT: 'vacation/request',
  INCIDENT_PERIOD: 'incident/period',
  INCIDENT_SAVE: 'incident',
  INCIDENT_EXCEL: 'incident/export/excel',
  TICKET_BY_STATUS: 'ticket/status',
  TICKET_SAVE: 'ticket',
  TICKET_STATUS: 'ticket',
  TICKET_COMMENTS: 'ticket',
  TICKET_COMMENT_ADD: 'ticket/comment',
  SURVEY_LIST: 'survey',
  SURVEY_PUBLISH: 'survey',
  SURVEY_CLOSE: 'survey',
  SURVEY_RESULTS: 'survey',
  SURVEY_SAVE: 'survey',
};

export const PATH_TREE = {
  NOTIFCATION: `${PATH_APPLICATION.DOMAIN}/${PATH_APPLICATION.TREE}`,
};

// Set AES_SECRET_KEY via sed replacement in CI/CD before build (see .env.example)
export const SECRETS = {
  AES_PASSWORD_SECRET: 'dchdev32charssecretkeyhere12345',
};

export const STATUS = [
  { id: 'A', value: 'Activo' },
  { id: 'I', value: 'Inactivo' },
];

export const GENERO = [
  { id: 'M', value: 'Masculino' },
  { id: 'F', value: 'Femenino' },
];

export const PERMISSION = [
  { id: 'Si', value: 'Si' },
  { id: 'No', value: 'No' },
];

export const PATH_USER = {
  //DOMAIN: 'http://localhost:8092',
  //DOMAIN: 'http://localhost:8000/api/user',
  DOMAIN: 'http://localhost:8000/api/user',
  RESET_REQUEST_ENDPOINT: 'user/reset/request',
  RESET_CONFIRMATION_ENDPOINT: 'user/reset/confirmation',
  USER_CREATION_ENDPOINT: 'user/create',
  USER_CONFIRMATION_ENDPOINT: 'user/confirmation',
  USER_SAVE_UPDTE_EMPLOYEE: 'user/saveOrUpdateEmployeeComplementaryWeb',
  USER_SAVE_UPDATE_EMPLOYEE_ADDRESS: 'user/saveOrUpdateEmployeeAddress',
  USER_SAVE_UPDATE_EMPLOYEE_CONTRATING: 'user/saveOrUpdateContrating',
  USER_SAVE_UPDATE_EMPLOYEE_HISTORY: 'user/saveOrUpdateHistory',
  USER_SAVE_UPDATE_EMPLOYEE_COMPESATION: 'user/saveOrUpdateCompesation',
  USER_SAVE_UPDATE_EMPLOYEE_ASIGNATION: 'user/saveOrUpdateAsignationData',
  USER_SAVE_UPDATE_EMPLOYEE_SOCIAL_NETWORK: 'user/saveOrUpdateSocialNetwork',
  USER_GET_CIVIL_STATUS: 'user/getCivilStatus',
  USER_GET_CITY: 'user/getCity',
  USER_GET_STATE: 'user/getState',
  USER_GET_ALL_EMPLOYEES: 'user/getAllEmployees',
  USER_GET_EMPLOYEE_BY_NAME: 'user/getUserRegisterSico',
  USER_GET_EMPLOYEE_BY_CURP: 'user/getEmployeesByCurpClientProject',
  USER_GET_EMPLOYEE_BY_ID_USER: 'user/getEmployeeByIdUser',
  USER_GET_SOCIAL_NETWORK_BY_ID_USER: 'user/getSocialNetworkByIdUser',
  USER_GET_COMPENSATION_BY_ID_USER: 'user/getEmployeeCompensationByIdUser',
  USER_GET_EMPLOYEE_HISTORY_BY_ID_USER: 'user/getEmployeeHistoryByIdUser',
  USER_GET_EMPLOYEE_COMPLEMENTARY_BY_ID_USER: 'user/getUserRegisterById',
  USER_GET_EMPLOYEE_DOMICILE_BY_ID_USER: 'user/getEmployeeAdressByIdUser',
  USER_GET_EMPLOYEE_CONTRATING_BY_ID_USER: 'user/getContratingDataByIdUser',
  USER_GET_EMPLOYEE_ASIGNATION_BY_ID_USER: 'user/getEmployeeAsignationByIdUser',
  USER_GET_PROPORTION_TABS_BY_ID_USER: 'user/getTabByIdUser',
  GET_COUNT_ROW_USER: 'user/getCountRow',
  USER_GET_ALL_CLIENT: 'user/getClient',
  USER_GET_PROJECT_BY_ID_CLIENT: 'user/getProjectByIdClient',
};

export const TABLE_ROUTE_USERS = {
  GET_COUNT_ROW_USERS: `${PATH_USER.DOMAIN}/${PATH_USER.GET_COUNT_ROW_USER}`,
  EXCEL_USER: `${PATH_USER.DOMAIN}/generic/sendExcel`,
};

export const ROOT_FRONTEND_WEB = {
  USERS: '/home/usuarios',
};
