// Para browser/emulador usar 'localhost'. Para dispositivo fisico cambiar a la IP de tu maquina (ej: '192.168.68.50')
const DEV_HOST = 'localhost';
const DEV_GATEWAY = `http://${DEV_HOST}:8000`;
const DEV_NODE_API = `http://${DEV_HOST}`;
const DEV_WEB = `http://${DEV_HOST}:4200`;

export const API_RHTOTAL = {
  DOMAIN: DEV_NODE_API,
  PORT: '3000',
  GET_BANK_INFO_BY_ID_EMPLOYEE: '/api/v1/bank/:employee_id/:apikey',
  GET_EMAIL_SEND: '/api/v1/email/destiny/:sendto/:apikey',
  GET_PROPERTY: '/api/v1/settings/:property',
  SEND_EMAIL: '/api/v1/email/send/:apikey',
  EMPLOYEE_BY_PROJECT: '/api/v1/employee/:project/:apikey',
  KEY: 'YXNkZmdoMTQ3cmh0b3RhbA==',
};

export const PATH_SECURITY = {
  DOMAIN: `${DEV_GATEWAY}/api/security`,
  LOGIN: 'login/loginMobile',
  PARAMETER_EMAIL: 'email/sendEmail?shouldbeparse=false',
  PARAMETER: 'parameter/getParameter',
  EMAIL_TRUE: 'email/sendEmail?shouldbeparse=true',
};

export const PATH_USER = {
  DOMAIN: `${DEV_GATEWAY}/api/user`,
  CREATE: 'user/create',
  CODE: 'user/confirmation',
  CIVIL_STATUS: 'user/getCivilStatus',
  USER_EXIST_RH_TOTAL: 'user/getUserRegisterSico',
  SAVE_EMPLOYE: 'user/saveOrUpdateEmployeeComplementary',
  GET_EMPLOYEE_COMPL_BY_ID: 'user/getUserRegisterById',
  GET_CITY: 'user/getCity',
  GET_STATE: 'user/getState',
  SAVE_ADDRESS: 'user/saveOrUpdateEmployeeAddress',
  GET_ADDRESS: 'user/getEmployeeAdressByIdUser',
  GET_EMPLOYEE: 'user/getEmployeeByIdUser',
  SAVE_SOCIAL: 'user/saveOrUpdateSocialNetwork',
  GET_SOCIAL: 'user/getSocialNetworkByIdUser',
  SAVE_USER: 'user/saveOrUpdateUser',
  GET_USER: 'login/getUserById',
  SAVE_MY_CV: 'user/saveOrUpdateMyCv',
  GET_MY_CV: 'user/getCvByEmail',
  SAVE_COMPENSATION: 'user/saveOrUpdateCompesation',
  GET_COMPENSATION: 'user/getEmployeeCompensationByIdUser',
  SAVE_JOBS: 'user/job/add',
  GET_JOBS: 'user/job',
  USER_SAVE_UPDATE_EMPLOYEE_CONTRATING: 'user/saveOrUpdateContrating',
  USER_GET_EMPLOYEE_CONTRATING_BY_ID_USER: 'user/getContratingDataByIdUser',
  SAVE_UPDATE_ASIGNATION_DATA: 'user/saveOrUpdateAsignationData',
  GET_ASIGNATION_DATA: 'user/getEmployeeAsignationByIdUser',
  USER_GET_EMPLOYEE_HISTORY_BY_ID_USER: 'user/getEmployeeHistoryByIdUser',
  USER_SAVE_UPDATE_EMPLOYEE_HISTORY: 'user/saveOrUpdateHistory',
  PONDERATION_DATA: 'user/getPonderationSection',
  CREDENTIAL_INFO: 'user/credential',
  DELETE_JOBS: 'user/job/delete',
  ACTUAL_POSITION: 'user/getActualPosition',
  CFDI: 'user/getCfdiByUser',
};

export const PATH_HR = {
  DOMAIN: `${DEV_GATEWAY}/api/hr`,
  VACATION_BALANCE: 'vacation/balance',
  VACATION_REQUEST: 'vacation/request',
  VACATION_MY_REQUESTS: 'vacation/request/employee',
  TICKET_SAVE: 'ticket',
  TICKET_MY_TICKETS: 'ticket/employee',
  TICKET_COMMENTS: 'ticket',
  TICKET_COMMENT_ADD: 'ticket/comment',
  SURVEY_LIST: 'survey',
  SURVEY_QUESTIONS: 'survey',
  SURVEY_RESPONSE: 'survey/response',
};

export const PATH_ATTENDANCE = {
  DOMAIN: `${DEV_GATEWAY}/api/attendance`,
  CHECK_IN: 'attendance/check-in',
  CHECK_OUT: 'attendance/check-out',
  TODAY: 'attendance/today',
};

export const PATH_NOTIFICATION = {
  DOMAIN: `${DEV_GATEWAY}/api/application`,
  SET_USER_TOKEN: 'notification/setUserToken',
  INACTIVE_USER_TOKEN: 'notification/inactiveUserToken',
  LAST_NOTIFICATIONS: 'notification/notificationUser',
};

export const PATH_BIBLIOTECA = {
  DOMAIN: `${DEV_GATEWAY}/api/application`,
  CATEGORIES: 'library/categories',
  DOCUMENTS_VISIBLE: 'library/documents/visible',
  DOCUMENT_DETAIL: 'library/document',
  DOCUMENT_ACK: 'library/document',
};

export const PATH_APLICATION = {
  DOMAIN: `${DEV_GATEWAY}/api/application/`,
  PARAMETER: 'companyInformation/getCompanyInformation?nameCompanyInformation=',
  BANNERS_HOME: 'banner/showbanners',
  CATEGORY: 'discount/getCategory',
  CATEGORY_IDUSER: 'discount/getCategoryIdUser',
  SUBCATEGORY: 'discount/getSubCategory?idCategory=',
  SUBCATEGORY_IDUSER: 'discount/getSubCategoryIdUser',
  DISCOUNT_IMAGE: 'discount/getImageDiscountByUser',
  DISCOUNT_IMAGE_SECUNDARY: 'discount/getImagesSecundary',
  DATETIME: 'parameter/datetime',
  VERIFY_EMAIL: 'discount/verifyHourPublication',
  VERIFY_INSURANCE: 'insurance/verifyHourPublication',
  INSURANCE_BENEFIS: 'insurance/getInsuranseUser',
  INSURANCE_DETAIL_BENEFIS: 'insurance/geInsurangeByInsurance',
  GET_COVERAGE_INSURANCE: 'insurance/getCoverageInsurance',
  GET_lEVEL_DISCOUNT: 'discount/getLevelDiscount',
};

export const TABLE_DB = {
  COMPANY_INFORMATION: 'CompanyInformation',
};

export const INSERT_DB_RHTOTAL = {
  INSERT_COMPANY_INFORMATION:
    'INSERT OR REPLACE INTO CompanyInformation(id,name,value) values(?,?,?)',
  INSERT_USER_PDF:
    'INSERT OR REPLACE INTO UserPdf(idMycv,idUser,nameCv,value,creationUser,lastUserModifier,lastModification, creationDate,active,email) values(?,?,?,?,?,?,?,?,?,?)',
  INSERT_NOTIFICATION:
    'INSERT OR REPLACE INTO Notifications(idRepository,idNotification,type,subCategory,description,descriptionSmall,title,unRead,date) values(?,?,?,?,?,?,?,?,?);',
};

export const UPDATE_DB_RHTOTAL = {
  UPDATE_NOTIFICATION: 'UPDATE Notifications SET unRead = ? WHERE idRepository = ?',
};

export const DELETE_DB_RHTOTAL = {
  DELETE_ALL_NOTIFICATIONS: 'DELETE FROM Notifications',
};

export const VARIABLES_PAGE_DISCOUNT = {
  VAR_INDEX_INIT_PAGE: 0,
  VAR_TYPE_NOTIFICATION: 'D',
  VAR_TYPE_IMAGE: 'P',
  VAR_TYPE_DISCOUNT_BENEFIS: 'B',
  VAR_TYPE_DISCOUNT_DISCOUNT: 'D',
  VAR_TYPE_IMAGE_SECUNDARY: 'S',
  TAMANO: 1024000,
};

export const QUERY_DB_RHTOTAL = {
  QUERY_COMPANY_INFORMATION: 'select * from CompanyInformation where name= ?',
  QUERY_USER_PDF: 'select * from UserPdf where email= ?',
  QUERY_NOTIFICATIONS: 'select * from Notifications',
};

export const MESES = [
  'Enero',
  'Febrero',
  'Marzo',
  'Abril',
  'Mayo',
  'Junio',
  'Julio',
  'Agosto',
  'Septiembre',
  'Octubre',
  'Noviembre',
  'Diciembre',
];

export const VALIDATORS = {
  CURP: /^([A-Z][AEIOUX][A-Z]{2}\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[12]\d|3[01])[HM](?:AS|B[CS]|C[CLMSH]|D[FG]|G[TR]|HG|JC|M[CNS]|N[ETL]|OC|PL|Q[TR]|S[PLR]|T[CSL]|VZ|YN|ZS)[B-DF-HJ-NP-TV-Z]{3}[A-Z\d])(\d)$/,
  RFC: /^[a-zA-Z]{3,4}(\d{6})((\D|\d){2,3})?$/,
  RFCHOMO: /^([A-ZÑ\x26]{3,4}([0-9]{2})(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1]))([A-Z\d]{3})?$/,
  DECIMAL: /^[0-9]{1,9}(\.[0-9]{1,2})?$/,
  OWN_NAME: /^[\S][A-Za-zÑñÁËÍÓÚáéíóúÜü\s.-]+$/,
};

export const PRMISION_WORK = [
  { name: 'Si', id: 'Si' },
  { name: 'No', id: 'No' },
];
export const GENERO = [
  { id: 'F', name: 'Femenino' },
  { id: 'M', name: 'Masculino' },
];
export const SEGURO = [
  { id: '0', name: 'Seleccione un tipo de seguro.' },
  { id: '1', name: 'Auto' },
  { id: '2', name: 'Hogar' },
  { id: '3', name: 'Vida Ahorro' },
  { id: '4', name: 'Mascotas' },
];

export const SEGUROS = {
  HOGAR: 'emailHogar',
  AUTO: 'emailAuto',
  MASCOTA: 'emailMascota',
  VIDA: 'emailVida',
};

export const ARRAYPARAM: string[] = ['aboutUS', 'help', 'privacity'];

export const PARAMETER = {
  INTENTS: 'numberIntent',
  CONDITIONS: 'conditions',
};

export const EXPRESSION = {
  EMAIL: /^[-\w.%+]{1,64}@(?:[A-Z0-9-]{1,63}\.){1,125}[A-Z]{2,63}$/i,
  PWD: /^(?=.*\d)(?=.*[A-Z])(?=.*[a-z])(?=.*)\S{8,50}$/,
  DECIMAL: /^\d+(\.\d{1,2})?$/,
  OWN_NAME: /^[\S][A-Za-zÑñÁËÍÓÚáéíóúÜü\s.-]+$/,
  ALPHANUMERIC_CODE: /^\S/,
  ONLY_NUMBERS: /^[0-9]*$/,
  NUMBER: /^[0-9]+$/,
};

export const MSG = {
  INVALID_PWD: 'Las contraseñas no coinciden',
  INVALID_EXP: 'El formato debe contener mínimo 8 caracteres, mayúsculas, minúsculas y números',
  INVALID_EMAIL: 'El correo electrónico no es válido, ingrésalo nuevamente',
  TERMINOS_CONDICIONES_TITLE: 'Términos y Condiciones',
  TITLE_BENEFIS_ALERT: '¡Próximamente!',
  CONTENT_BENEFIS_ALERT: 'Espéralo muy pronto...',
};

export const MSG_DIALOG = {
  QUOTE_SAFE_TITLE: 'Cotización  de seguro',
  QUOTE_SAFE_CONTENT: 'Serás contactado próximamente por un agente de seguros',
  ERROR_SERVICE: 'Servicio no disponible, intente más tarde.',
  ERROR_TITLE: '',
  RESPONSE_EMPTY: 'No se encontraron resultados',
  OK: '¡Listo!',
  MSG_SAVE: 'Tu información ha sido guardada correctamente',
  MSG_SIZE_PDF:
    'El archivo a cargar no cumple con el formato requerido, cargar un archivo en PDF y menor a 1 MB',
  MSG_SIN_RESULTADOS: 'No se encontraron resultados',
  BUSQUEDA: 'Error al realizar la búsqueda',
  REQUIRED: 'Favor de validar campos requeridos(*)',
  DATA_ERROR: 'Validar información ingresada',
  DELETE_INFO: 'La información fue eliminada correctamente',
};

export const BTN = {
  OK: 'Ok',
  ACEPT: 'Aceptar',
};

// Set AES_SECRET_KEY via environment variable at build time (see .env.example)
export const SECRETS = {
  AES_PASSWORD_SECRET: process.env['AES_SECRET_KEY'] || 'megustanlaschicascongafas',
};

export const KEYS_STORAGE = {
  USER: 'user',
  INFO_CRENDENTIAL: 'infoCredential',
  JOBS: 'jobs',
  IMAGES_BANNER: 'imagesBanner',
};

export const CLEAN_LOCAL_STORAGE = ['user', 'infoCredential', 'jobs', 'imagesBanner'];

export const LINKS = {
  RECOVERY_PWD: `${DEV_WEB}/#/nuevo-usuario?website=mobile`,
};

export const TYPE_USER = {
  IN: 'IN',
  EX: 'EX',
};

export const SOCIAL_NETWORK = [
  'Facebook',
  'Twitter',
  'LinkedIn',
  'Google+',
  'Instagram',
  'Snapchat',
  'Spotify',
];

export const TEMPLATES_FINTECH = { REQUEST: 'request', SUPPORT: 'support' };
export const EMAIL_LOANS = 'emailLoans';
export const EMAIL_SUPPORT = 'emailSupport';
export const EMAIL_SEGURO = 'emailSeguro';
export const IMG_EMAIL = 'imgEmailUnbound';
export const IMG_MAIL_HEADER = 'emailHeadImg';
export const EMAIL_SOPORTE_APP = 'emailSupportApp';
export const TIPOS_SOPORTES = [
  { id: '0', name: '¿Cómo podemos ayudarle?' },
  { id: '1', name: 'Anticipos' },
  { id: '2', name: 'Recibos de Nómina' },
  { id: '3', name: 'Beneficios' },
  { id: '4', name: 'Otros Servicios' },
];
export const TEMPLATE_IMAGE = { TEMPLATE_SEGURO: 'layoutSeguro' };
export const TEXT_STATIC_CUOTES = {
  TEXT_CONTENT:
    'Gracias por tu interés en asegurarte con nosotros.#Recuerda que es mejor estar preparado para cualquier situación. #¿Deseas que un agente te contacte para darte una atención  más personalizada para cotizar el seguro que mejor se adapte a tus necesidades?',
};

export const MSG_LOAN_BLOCK = {
  1: 'Esta opción solo está disponible para los empleados que tengan más de 3 meses de antigüedad.',
  2: 'Esta opción solo está disponible para los empleados que tengan más de 6 meses de antigüedad.',
  3: 'Esta opción solo está disponible para los empleados que tengan más de 9 meses de antigüedad.',
};
