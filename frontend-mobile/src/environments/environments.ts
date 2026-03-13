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
    KEY: 'YXNkZmdoMTQ3cmh0b3RhbA=='
} 


export const PATH_SECURITY = {
    DOMAIN:`${DEV_GATEWAY}/api/security`,
    LOGIN:`login/loginMobile`,
    PARAMETER_EMAIL:'email/sendEmail?shouldbeparse=false',
    PARAMETER:`parameter/getParameter`,
    EMAIL_TRUE:'email/sendEmail?shouldbeparse=true' // Api para correos personalizados sin template
} 


export const PATH_USER = {
    DOMAIN:`${DEV_GATEWAY}/api/user`,
    CREATE:`user/create`,
    CODE:`user/confirmation`,
    CIVIL_STATUS:'user/getCivilStatus',
    USER_EXIST_RH_TOTAL:'user/getUserRegisterSico',
    SAVE_EMPLOYE:'user/saveOrUpdateEmployeeComplementary',
    GET_EMPLOYEE_COMPL_BY_ID:'user/getUserRegisterById',
    GET_CITY:'user/getCity',
    GET_STATE:'user/getState',
    SAVE_ADDRESS:'user/saveOrUpdateEmployeeAddress',
    GET_ADDRESS:'user/getEmployeeAdressByIdUser',
    GET_EMPLOYEE:'user/getEmployeeByIdUser',
    SAVE_SOCIAL:'user/saveOrUpdateSocialNetwork',
    GET_SOCIAL:'user/getSocialNetworkByIdUser',
    SAVE_USER:'user/saveOrUpdateUser',
    GET_USER:'login/getUserById',
    SAVE_MY_CV:'user/saveOrUpdateMyCv',
    GET_MY_CV:'user/getCvByEmail',
    SAVE_COMPENSATION:'user/saveOrUpdateCompesation',
    GET_COMPENSATION:'user/getEmployeeCompensationByIdUser',
    SAVE_JOBS:'user/job/add',
    GET_JOBS:'user/job',
    USER_SICO_URL:'https://nominaenlanube.com:8085/api-nomen/v1/',
    USER_SICO_WORK_INFORMATION:'get_informacion_empleado?',
    USER_SAVE_UPDATE_EMPLOYEE_CONTRATING:'user/saveOrUpdateContrating',
    USER_GET_EMPLOYEE_CONTRATING_BY_ID_USER:'user/getContratingDataByIdUser',
    SAVE_UPDATE_ASIGNATION_DATA:'user/saveOrUpdateAsignationData',
    GET_ASIGNATION_DATA:'user/getEmployeeAsignationByIdUser',
    USER_GET_EMPLOYEE_HISTORY_BY_ID_USER:'user/getEmployeeHistoryByIdUser',
    USER_SAVE_UPDATE_EMPLOYEE_HISTORY:'user/saveOrUpdateHistory',
    PONDERATION_DATA:'user/getPonderationSection',
    CREDENTIAL_INFO:'user/credential',
    DELETE_JOBS:'user/job/delete'

}

export const MESES =['Enero','Febrero','Marzo','Abril','Mayo','Junio','Julio','Agosto','Septiembre','Octubre','Noviembre','Diciembre'];
export const VALIDATORS={
    CURP:/^([A-Z][AEIOUX][A-Z]{2}\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[12]\d|3[01])[HM](?:AS|B[CS]|C[CLMSH]|D[FG]|G[TR]|HG|JC|M[CNS]|N[ETL]|OC|PL|Q[TR]|S[PLR]|T[CSL]|VZ|YN|ZS)[B-DF-HJ-NP-TV-Z]{3}[A-Z\d])(\d)$/,
    RFC:/^[a-zA-Z]{3,4}(\d{6})((\D|\d){2,3})?$/,
    RFCHOMO:/^([A-ZÑ\x26]{3,4}([0-9]{2})(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1]))([A-Z\d]{3})?$/,
    DECIMAL: /^[0-9]{1,9}(\.[0-9]{1,2})?$/,
    OWN_NAME:/^[\S][A-Za-zÑñÁËÍÓÚáéíóúÜü\s.-]+$/
}

export const PRMISION_WORK =[] = [{name:'Si',id:'Si'},{name:'No',id:'No'}];
export const GENERO =[] = [{id:'F',name:'Femenino'},{id:'M',name:'Masculino'}];

// Tipos de seguro id 1 - 4
export const SEGURO =[] = [{id:'0',name:'Seleccione un tipo de seguro.'},{id:'1',name:'Auto'},{id:'2',name:'Hogar'},{id:'3',name:'Vida Ahorro'},{id:'4',name:'Mascotas'}];
// Correos para velocash y my advance
export const EMAILS = [] = [{id:'1', email:'rodrigo@rhtotal.com'},{id:'2', email:'ismael@rhtotal.com'}]; //roberto.mdcc
export const EMAILS_SUPPORT = [] = [{id:'1', email:'adrakiel@gmail.com'},{id:'2', email:'ismael@rhtotal.com'}];
export const TEMPLATES_FINTECH = { REQUEST: 'request', SUPPORT: 'support' };
export const EMAIL_LOANS = 'emailLoans';
export const EMAIL_SUPPORT = 'emailSupport';
export const EMAIL_SEGURO = 'emailSeguro';
export const IMG_EMAIL = 'imgEmailUnbound';
export const IMG_MAIL_HEADER = 'emailHeadImg';
export const EMAIL_SOPORTE_APP = 'emailSupportApp';
export const TIPOS_SOPORTES =[] = [{id:'0',name:'¿Cómo podemos ayudarle?'},{id:'1',name:'Anticipos'},{id:'2',name:'Recibos de Nómina'},{id:'3',name:'Beneficios'},{id:'4',name:'Otros Servicios'}];


export const SEGUROS = {
    HOGAR: 'emailHogar',
    AUTO: 'emailAuto',
    MASCOTA: 'emailMascota',
    VIDA: 'emailVida'
};
export const PATH_NOTIFICATION = {

    DOMAIN:`${DEV_GATEWAY}/api/application`,
    SET_USER_TOKEN:`notification/setUserToken`,
    INACTIVE_USER_TOKEN:`notification/inactiveUserToken`,
    LAST_NOTIFICATIONS:`notification/notificationUser`,
}

export const PATH_SICO ={
    DOMAINT:`${DEV_GATEWAY}/api/paysheetsico`,
    GET_EXIST_USER_FOR_URI:'sico/raw',
    URI_CICO:'https://nominaenlanube.com:8085/api-nomen/v1/get_empleado_general?'
}

export const PATH_APLICATION = {
    DOMAIN:`${DEV_GATEWAY}/api/application/`,
    PARAMETER:'companyInformation/getCompanyInformation?nameCompanyInformation=',
    BANNERS_HOME:'banner/showbanners',
    CATEGORY:'discount/getCategory',
    CATEGORY_IDUSER:'discount/getCategoryIdUser',
    SUBCATEGORY:'discount/getSubCategory?idCategory=',
    SUBCATEGORY_IDUSER:'discount/getSubCategoryIdUser',
    DISCOUNT_IMAGE:'discount/getImageDiscountByUser',
    DISCOUNT_IMAGE_SECUNDARY:'discount/getImagesSecundary',
    DATETIME:'parameter/datetime',
    VERIFY_EMAIL:'discount/verifyHourPublication',
    VERIFY_INSURANCE:'insurance/verifyHourPublication',
    INSURANCE_BENEFIS:'insurance/getInsuranseUser',
    INSURANCE_DETAIL_BENEFIS:'insurance/geInsurangeByInsurance',
    GET_COVERAGE_INSURANCE:'insurance/getCoverageInsurance',
    GET_lEVEL_DISCOUNT:'discount/getLevelDiscount'
}

export const TABLE_DB= {
    COMPANY_INFORMATION:'CompanyInformation'
}

export const INSERT_DB_RHTOTAL = {
   INSERT_COMPANY_INFORMATION:'INSERT OR REPLACE INTO CompanyInformation(id,name,value) values(?,?,?)',
   INSERT_USER_PDF:'INSERT OR REPLACE INTO UserPdf(idMycv,idUser,nameCv,value,creationUser,lastUserModifier,lastModification, creationDate,active,email) values(?,?,?,?,?,?,?,?,?,?)',
   INSERT_NOTIFICATION:'INSERT OR REPLACE INTO Notifications(idRepository,idNotification,type,subCategory,description,descriptionSmall,title,unRead,date) values(?,?,?,?,?,?,?,?,?);',
}

export const UPDATE_DB_RHTOTAL = {
    UPDATE_NOTIFICATION:'UPDATE Notifications SET unRead = ? WHERE idRepository = ?',
}

export const DELETE_DB_RHTOTAL = {
    DELETE_ALL_NOTIFICATIONS:'DELETE FROM Notifications',
}

export const VARIABLES_PAGE_DISCOUNT ={
    VAR_INDEX_INIT_PAGE: 0 ,
    VAR_TYPE_NOTIFICATION:'D',
    VAR_TYPE_IMAGE:'P',
    VAR_TYPE_DISCOUNT_BENEFIS:'B',
    VAR_TYPE_DISCOUNT_DISCOUNT:'D',
    VAR_TYPE_IMAGE_SECUNDARY:'S',
    TAMANO:1024000
}

export const QUERY_DB_RHTOTAL = {
    QUERY_COMPANY_INFORMATION:'select * from CompanyInformation where name= ?',
    QUERY_USER_PDF:'select * from UserPdf where email= ?',
    QUERY_NOTIFICATIONS:'select * from Notifications'
}



export const ARRAYPARAM:string[] =['aboutUS','help','privacity'];

export const PARAMETER = {
    INTENTS:'numberIntent',
    CONDITIONS:'conditions'
}

export const EXPRESSION = {
    EMAIL:/^[-\w.%+]{1,64}@(?:[A-Z0-9-]{1,63}\.){1,125}[A-Z]{2,63}$/i,
    PWD:/^(?=.*\d)(?=.*[A-Z])(?=.*[a-z])(?=.*)\S{8,50}$/,
    DECIMAL: /^\d+(\.\d{1,2})?$/,
    OWN_NAME:/^[\S][A-Za-zÑñÁËÍÓÚáéíóúÜü\s.-]+$/,
    ALPHANUMERIC_CODE:/^\S/,
    ONLY_NUMBERS:/^[0-9]*$/,
    NUMBER:/^[0-9]+$/,
}

export const MSG = {
    INVALID_PWD:'Las contraseñas no coinciden',
    INVALID_EXP:'El formato debe contener mínimo 8 caracteres, mayúsculas, minúsculas y números',
    INVALID_EMAIL:'El correo electrónico no es válido, ingrésalo nuevamente',
    TERMINOS_CONDICIONES:'Lorem ipsum dolor sit amet consectetur adipisicing elit. Omnis temporibus, alias est magnam officia doloremque sunt at dolores a, cumque ad delectus. Nesciunt, ipsum necessitatibus eos maxime mollitia placeat aut     Lorem ipsum dolor sit amet consectetur adipisicing elit. Sint quos tenetur dolor quaerat ea nam deleniti modi minus vitae! Deleniti ratione velit aliquam cupiditate nobis debitis nam quam dicta minus.    Dolores rerum quibusdam illum alias, enim architecto doloribus expedita deserunt labore consectetur ipsum rem voluptate facere quo omnis assumenda voluptatum odit culpa esse recusandae ducimus dignissimos. Sapiente rerum sint quisquam.    Consectetur ex impedit fugit quos modi repudiandae? Facere libero eaque ipsum pariatur ipsa iure molestiae numquam, aliquam cupiditate debitis temporibus. Velit quos totam harum officia molestias praesentium a reprehenderit libero!    Quos illo quaerat deserunt, possimus amet esse doloribus, necessitatibus cum molestias cumque debitis atque. Ipsam vel perferendis amet dolore temporibus consequuntur voluptatibus necessitatibus culpa! Voluptas aspernatur facilis praesentium vel quia!    Quisquam animi fuga molestiae sequi distinctio quidem ullam laborum? Dolores consequuntur consectetur impedit totam debitis magnam nobis voluptas, tempore deleniti quas dolor fugit delectus, ipsam ex incidunt repellendus possimus deserunt.',
    TERMINOS_CONDICIONES_TITLE:'Términos y Condiciones',
    TITLE_BENEFIS_ALERT:'¡Próximamente!',
    CONTENT_BENEFIS_ALERT:'Espéralo muy pronto...'
    
    
}

export const MSG_DIALOG = {
    QUOTE_SAFE_TITLE : 'Cotización  de seguro',
    QUOTE_SAFE_CONTENT:'Serás contactado próximamente por un agente de seguros',
    QUOTE_SAFE_EMAIL_BODY:'<html><head><title>Correo electrónico</title></head><body><center style=\"margin-top:50px\"><img src=\"cid:image\"></center><div style=\"align-items:center;display:flex;justify-content:center;margin-top:20px;font-size:14px;font-family:Arial\"><center style=\"width:340px;text-align:justify\">¡Hola!<br>Recibimos la petición para cambiar tu contraseña. Solo haz clic en el siguiente botón y sigue las instrucciones:</center></div><center style=\"margin:50px\"><a href=\":host/#/reset-confirmation?token=:token&user=:user\"><button style=\"background-color:#1099d6;width:250px;height:40px;margin:auto;border-style:none;color:#fff;font-family:Arial;font-size:18px;cursor:pointer\">Restablecer Contraseña</button></a></center><center><div style=\"align-items:center;display:flex;justify-content:center;margin-top:20px;font-size:14px;font-family:Arial\"><center style=\"width:340px;text-align:justify;margin:16px 0\">También puedes copiar y pegar la siguiete url en tu navegador:</center></div><div style=\"align-items:center;display:flex;justify-content:center;margin-top:20px;font-size:14px;font-family:Arial\"><center style=\"width:340px;text-align:justify;margin:16px 0\"><a href=\":host/#/reset-confirmation?token=:token&user=:user\">:host/#/reset-confirmation?token=:token&user=:user</a></center></div></center><div style=\"align-items:center;display:flex;justify-content:center;font-size:14px;font-family:Arial\"><center style=\"width:340px\">Si no haz solicitado el cambio de contraseña,<br>!no te preocupes¡, solo haz caso omiso de este correo electrónico. No es necesario responder a este correco electrónico.</center></div></body></html>',
    ERROR_SERVICE:'Servicio no disponible, intente más tarde.',
    //ERROR_TITLE:'Oops!',
    ERROR_TITLE:'',
    RESPONSE_EMPTY: 'No se encontraron resultados',
    OK:'¡Listo!',
    MSG_SAVE:'Tu información ha sido guardada correctamente',
    MSG_SIZE_PDF:'El archivo a cargar no cumple con el formato requerido, cargar un archivo en PDF y menor a 1 MB',
    MSG_SIN_RESULTADOS:'No se encontraron resultados',
    BUSQUEDA:'Error al realizar la búsqueda',
    REQUIRED:'Favor de validar campos requeridos(*)',
    DATA_ERROR:'Validar información ingresada',
    DELETE_INFO:'La información fue eliminada correctamente'
}

export const TEXT_STATIC_CUOTES = {
    TEXT_CONTENT :'Gracias por tu interés en asegurarte con nosotros.#Recuerda que es mejor estar preparado para cualquier situación. #¿Deseas que un agente te contacte para darte una atención  más personalizada para cotizar el seguro que mejor se adapte a tus necesidades?'
}

export const TEMPLATE_IMAGE ={
    TEMPLATE_SEGURO : 'layoutSeguro',
}

export const BTN = {
    OK:'Ok',
    ACEPT:'Aceptar'
}

export const SECRETS = {
    AES_PASSWORD_SECRET: 'megustanlaschicascongafas'
}

export const KEYS_STORAGE = {
    USER:'user',
    INFO_CRENDENTIAL:'infoCredential',
    JOBS: 'jobs',
    IMAGES_BANNER: 'imagesBanner'
}

export const PATH_FINTECH = {
    DOMAIN:`${DEV_GATEWAY}/api/fintech`,
    VELOCAHS:'nomina/velocahs',
    MY_ADVANCE:'nomina/adelanto',
    ACTUAL_POSITION:'recursos/puesto',
    PAYMENT_DETAILS:'recursos/pagos',
    CFDI:'recursos/cfdi',
    VISIT_SWAP:'fintech/visitSwap'
}

export const CLEAN_LOCAL_STORAGE = ['user','infoCredential','jobs','imagesBanner']

export const LINKS = {
    RECOVERY_PWD:`${DEV_WEB}/#/nuevo-usuario?website=mobile`
}

export const TYPE_USER = {
    IN:'IN',
    EX:'EX'
}

export const SOCIAL_NETWORK= ['Facebook','Twitter','LinkedIn','Google+','Instagram','Snapchat','Spotify'];

export const MSG_LOAN_BLOCK = {
    1:'Esta opción solo está disponible para los empleados que tengan más de 3 meses de antigüedad.',
    2:'Esta opción solo está disponible para los empleados que tengan más de 6 meses de antigüedad.',
    3:'Esta opción solo está disponible para los empleados que tengan más de 9 meses de antigüedad.'
}
