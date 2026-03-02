const express = require('express')
const ConfigDB = require("../config")
const CryptoJS = require("crypto-js") 
const ErrorModel = require('../../models/errorModel')
const SuccessModel = require('../../models/successModel')
const ws = express()

const errorUnauthorized = new ErrorModel(401,'Unauthorized access.')
const errorUndefined = new ErrorModel(400,'Requested is undefined.')
const successNoInfo = new SuccessModel(200, 'Information not found.')
const errorFormat = new ErrorModel(400,'Bad Format Request.')
const errorNonAuthoritative = new ErrorModel(203, 'Non-Authoritative Information')

const authApi = module.exports = {}
/**
 * Global Variables
 **/

 authApi.valueSet = {}

/**
* @function authApi
* @param apikey
* @return boolean
**/
authApi.check = (apikey) => {
  // Here put db quere yo check apikey phrase and apikey comes from db

  const phrase = 'rhtotal'
  const dbKey = 'asdfgh147'

  const dbApiKey = CryptoJS.enc.Base64.stringify(CryptoJS.enc.Utf8.parse(dbKey+phrase))
  //YXNkZmdoMTQ3cmh0b3RhbA==<
  //console.log(dbApiKey)

  if(apikey === undefined){
  	return false;
  } else {
  	return dbApiKey.toString() === apikey.toString() ? true : false
  }
}

