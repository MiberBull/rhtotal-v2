const ConfigDB = require("../config")
const CryptoJS = require("crypto-js") 
const ErrorModel = require('../../models/errorModel')
const SuccessModel = require('../../models/successModel')
const authApi = require('../auth/auth')


const errorUnauthorized = new ErrorModel(401,'Unauthorized access.')
const errorUndefined = new ErrorModel(400,'Requested is undefined.')
const successNoInfo = new SuccessModel(200, 'Information not found.')
const errorFormat = new ErrorModel(400,'Bad Format Request.')
const errorNonAuthoritative = new ErrorModel(203, 'Non-Authoritative Information')


const setApi = module.exports = {}

/**
* @function getSettings
* @oaram request
* @param response
**/
setApi.getSettings = (request, response) => {
  let property = request.params.property
  let apikey = request.headers.apikey//request.params.apikey

  let isDataOK = (property === undefined) ? false : true

  if(authApi.check(apikey)) {
    if(isDataOK){
      const queryGetSettings = {
        name: 'get-settings',
        text: 'SELECT ds_value FROM w_parameter WHERE ds_name_parameter = $1 LIMIT 1',
        values: [property],
        rowMode: 'array'
      }

      ConfigDB.pool.query(queryGetSettings, (error, results) => {
        if (error) {
          response.status(errorNonAuthoritative.Error.code).send(errorNonAuthoritative).end()
          //throw error
        }
        if(results.rows[0]== undefined) {
          response.status(successNoInfo.Success.code).send(successNoInfo).end()
        } else {
          let settings = results.rows
          response.status(200).json(settings).end()
        }
      })
    }else{
      response.status(errorFormat.Error.code).send(errorFormat).end()
    }
  } else {
    response.status(errorUnauthorized.Error.code).send(errorUnauthorized).end()
  }
}

/**
* @function setService
* @oaram request
* @param response
**/
setApi.setService = (request, response) => {
  let apikey = request.headers.apikey//request.params.apikey
  let { status, service } = request.body
  let successUpdateService = new SuccessModel(201, `Service ${service} was successfully updated.`)

  let isDataOK = (service === undefined) ? false : true

  if(authApi.check(apikey)) {
    if(isDataOK){
      const queryUpdateService = {
        name: 'set-service',
        text: 'UPDATE w_config_email SET ds_value = $1 where ds_name_parameter = $2;',
        values: [status, service],
        rowMode: 'array'
      }

      ConfigDB.pool.query(queryUpdateService, (error, results) => {
        if (error) {
          response.status(errorNonAuthoritative.Error.code).send(errorNonAuthoritative).end()
          //throw error
        }
        response.status(successUpdateService.Success.code).send(successUpdateService).end()
      })
    }else{
      response.status(errorFormat.Error.code).send(errorFormat).end()
    }
  } else {
    response.status(errorUnauthorized.Error.code).send(errorUnauthorized).end()
  }
}




