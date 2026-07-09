export const environment = {
  production: false,
  gatewayUrl: 'http://localhost:8000',
  aesSecret: process.env['AES_SECRET_KEY'] || 'REPLACE_WITH_AES_SECRET',
  appName: 'DCH Know Who',
};
