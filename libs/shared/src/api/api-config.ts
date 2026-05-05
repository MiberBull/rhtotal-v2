export interface ApiConfig {
  gatewayUrl: string;
}

export const API_PATHS = {
  SECURITY: '/api/security',
  USER: '/api/user',
  APPLICATION: '/api/application',
} as const;

export const SECURITY_ENDPOINTS = {
  LOGIN_WEB: 'login/loginWeb',
  LOGIN_MOBILE: 'login/loginMobile',
  SEND_EMAIL: 'email/sendEmail',
  GET_PARAMETERS: 'parameter/getParameter',
  ROLES_CATALOGUE: 'role/getAllCatalogue',
  GET_ROLE: 'role/getRole',
  SAVE_ROLE: 'role/saveOrUpdateRole',
  TABLE_ROLE: 'role/getPagedRole',
  COUNT_ROLE: 'role/getNumberRow',
  RESET_REQUEST: 'role/reset/request',
  RESET_CONFIRMATION: 'role/reset/confirmation',
  EXCEL_ROLES: 'role/sendExcel',
} as const;

export const USER_ENDPOINTS = {
  CREATE: 'user/create',
  CONFIRMATION: 'user/confirmation',
  RESET_REQUEST: 'user/reset/request',
  RESET_CONFIRMATION: 'user/reset/confirmation',
  SAVE_COMPLEMENTARY: 'user/saveOrUpdateEmployeeComplementaryWeb',
  SAVE_ADDRESS: 'user/saveOrUpdateEmployeeAddress',
  SAVE_CONTRATING: 'user/saveOrUpdateContrating',
  SAVE_HISTORY: 'user/saveOrUpdateHistory',
  SAVE_COMPENSATION: 'user/saveOrUpdateCompesation',
  SAVE_ASIGNATION: 'user/saveOrUpdateAsignationData',
  SAVE_SOCIAL_NETWORK: 'user/saveOrUpdateSocialNetwork',
  GET_ALL_EMPLOYEES: 'user/getAllEmployees',
  GET_EMPLOYEE_BY_ID: 'user/getEmployeeByIdUser',
  GET_SOCIAL_NETWORK: 'user/getSocialNetworkByIdUser',
  GET_COMPENSATION: 'user/getEmployeeCompensationByIdUser',
  GET_HISTORY: 'user/getEmployeeHistoryByIdUser',
  GET_COMPLEMENTARY: 'user/getUserRegisterById',
  GET_ADDRESS: 'user/getEmployeeAdressByIdUser',
  GET_CONTRATING: 'user/getContratingDataByIdUser',
  GET_ASIGNATION: 'user/getEmployeeAsignationByIdUser',
  GET_TABS: 'user/getTabByIdUser',
  GET_COUNT_ROW: 'user/getCountRow',
  GET_CIVIL_STATUS: 'user/getCivilStatus',
  GET_CITY: 'user/getCity',
  GET_STATE: 'user/getState',
  GET_CLIENT: 'user/getClient',
  GET_PROJECT_BY_CLIENT: 'user/getProjectByIdClient',
  EXCEL_USERS: 'generic/sendExcel',
} as const;

export const APPLICATION_ENDPOINTS = {
  // Banners
  TABLE_BANNER: 'banner/getPagedBanner',
  SAVE_BANNER: 'banner/saveOrUpdateBanner',
  GET_BANNER: 'banner/getBanner',
  COUNT_BANNER: 'banner/getNumberRow',
  SHOW_BANNERS: 'banner/showbanners',
  // Notifications
  TABLE_NOTIFICATION: 'notification/getPagedNotification',
  SAVE_NOTIFICATION: 'notification/saveOrUpdateNotification',
  GET_NOTIFICATION: 'notification/getNotification',
  COUNT_NOTIFICATION: 'notification/getNumberRow',
  SET_USER_TOKEN: 'notification/setUserToken',
  INACTIVE_USER_TOKEN: 'notification/inactiveUserToken',
  NOTIFICATION_USER: 'notification/notificationUser',
  // Notification assignments
  GET_ASSIGNMENT_TREE: 'notificationassignment/getAssignmentBenefitsNotifications',
  SAVE_ASSIGNMENT: 'notificationassignment/saveAssignmentBenefitsNotifications',
  // Discounts
  TABLE_DISCOUNT: 'discount/getPagedDiscount',
  SAVE_DISCOUNT: 'discount/saveOrUpdateDiscount',
  GET_CATEGORY_BY_USER: 'discount/getCategoryIdUser',
  GET_DISCOUNT_IMAGES: 'discount/getImageDiscountByUser',
  // Insurance
  TABLE_INSURANCE: 'insurance/getAllInsurance',
  GET_ONE_INSURANCE: 'insurance/getOneInsurance',
  SAVE_INSURANCE: 'insurance/saveUpdateInsurance',
  GET_ALL_EVENTUALY: 'insurance/getAllEventualy',
  GET_ONE_EVENTUALY: 'insurance/getOneEventualy',
  GET_ALL_COVERAGE: 'insurance/getAllPlanCoverage',
  GET_ONE_COVERAGE: 'insurance/getOnePlanCoverage',
  COUNT_INSURANCE: 'insurance/getNumberRow',
  COUNT_EVENTUALY: 'insurance/getNumberRowEventualy',
  COUNT_COVERAGE: 'insurance/getNumberRowCoverage',
  GET_INSURANCE_USER: 'insurance/getInsuranseUser',
  // Clients
  GET_ALL_CLIENTS: 'client/getAllClients',
  SAVE_CLIENT: 'client/saveOrUpdateClient',
  GET_CLIENT: 'client/getClient',
  COUNT_CLIENT: 'client/getNumberRow',
  // Company
  GET_COMPANY_INFO: 'companyInformation/getCompanyInformation',
  // Generic
  SEND_EXCEL: 'generic/sendExcel',
  GET_HEADERS: 'generic/getHeader',
} as const;
