const ConfigDB = require("./config")
const CryptoJS = require("crypto-js")
const nodeMailer = require('nodemailer')
const ErrorModel = require('../models/errorModel')
const SuccessModel = require('../models/successModel')

const errorUnauthorized = new ErrorModel(401,'Unauthorized access.')
const errorFormat = new ErrorModel(400,'Bad Format Request.')
const successNoInfo = new SuccessModel(200, 'Information not found.')
const successResponse = new SuccessModel(200, 'msg')
const successNoContent = new SuccessModel(200,'No content to show.')

const dbApi = module.exports = {}
//const apiKey = 'U2FsdGVkX1+gu/QNS2WbqH54n0Ifug5VRzGVM22FpBM='

/**
* @function authApi
* @param apikey
* @return boolean
**/
function authApi(apikey){
  // Here put db quere yo check apikey phrase and apikey comes from db
  const phrase = 'rhtotal'
  const dbKey = 'asdfgh147'

  const dbApiKey = CryptoJS.enc.Base64.stringify(CryptoJS.enc.Utf8.parse(dbKey+phrase))
  //YXNkZmdoMTQ3cmh0b3RhbA==<
  //console.log(dbApiKey)

  
  return dbApiKey.toString() === apikey.toString() ? true : false
}

/**
* @function getPendingVelocash
* @oaram request
* @param response
**/
dbApi.getPendingVechocash = (request, response) => {
  let status = request.params.status
  let page = (request.params.page === undefined) ? 1 : parseInt(request.params.page)
  let apikey = request.params.apikey

  let isDataOK = (status === 'ES' || status === 'AP' || status === 'R') ? true : false

  if(authApi(apikey)) {
    if(isDataOK){
      const queryVelocash = {
        name: 'pending-velocash',
        text: 'SELECT * FROM k_paysheet_now WHERE ds_status_requisition = $1 ORDER BY dt_requisition_date DESC',
        values: [status],
        rowMode: 'array'
      }

      ConfigDB.pool.query(queryVelocash, (error, results) => {
        if (error) {
          throw error
        }
        if(results.rows[0]== undefined) {
          response.status(successNoInfo.Success.code).send(successNoInfo).end()
        } else {
          let info = {}
          let data = []
          let i = 0
          let totalPage = Math.ceil(results.rowCount / 10)
          let isContent = (page <= totalPage && page > 0) ? true : false

          //console.log(isContent, page, totalPage)

          if(isContent){
             results.rows.forEach(function(row, index){           
              if( i < (page * 10) && i >= ((page * 10) - 10)){
                data.push(row)
              }
              i = i + 1
            })

            info = {
              'page': page,
              'totalPage': totalPage,
              'velocash': data
            }

            response.status(200).json(info).end() 
          } else {
            response.status(successNoContent.Success.code).send(successNoContent).end()
          }
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
* @function getPendingMyAdvance
* @oaram request
* @param response
**/
dbApi.getPendingMyAdvance = (request, response) => {
  let status = request.params.status
  let page = (request.params.page === undefined) ? 1 : parseInt(request.params.page)
  let apikey = request.params.apikey

  let isDataOK = (status === 'ES' || status === 'AP' || status === 'R') ? true : false

  if(authApi(apikey)) {
    if(isDataOK){
      const queryMyAdvance = {
        name: 'pending-myadvance',
        text: 'SELECT * FROM k_advance_paysheet WHERE ds_status_requisition = $1 ORDER BY dt_requisition_time DESC',
        values: [status],
        rowMode: 'array'
      }

      ConfigDB.pool.query(queryMyAdvance, (error, results) => {
        if (error) {
          throw error
        }
        if(results.rows[0]== undefined) {
          response.status(successNoInfo.Success.code).send(successNoInfo).end()
        } else {
          let info = {}
          let data = []
          let i = 0
          let totalPage = Math.ceil(results.rowCount / 10)
          let isContent = (page <= totalPage && page > 0) ? true : false

          //console.log(isContent, page, totalPage)

          if(isContent){
             results.rows.forEach(function(row, index){           
              if( i < (page * 10) && i >= ((page * 10) - 10)){
                data.push(row)
              }
              i = i + 1
            })

            info = {
              'page': page,
              'totalPage': totalPage,
              'myadvance': data
            }

            response.status(200).json(info).end() 
          } else {
            response.status(successNoContent.Success.code).send(successNoContent).end()
          }
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
* @function updateVelocash
* @oaram request
* @param response
**/
dbApi.updateVelocash = (request, response) => {
  let apikey = request.params.apikey
  let { status, lastUserModifier, folio, reasonReject } = request.body
  let statusText = 'Waiting'

  let isDataOK = (status === undefined || lastUserModifier === undefined || folio === undefined) ? false : true

  reasonReject = (reasonReject === undefined || reasonReject === '') ? 'N/A' : reasonReject

  if(authApi(apikey)) {
    if(isDataOK){

      switch(status){
        case 'AP':
        statusText = 'Approved'
        break
        case'ES':
        statusText = 'Waiting'
        break
        case 'R':
        statusText = 'Rejected'
        break
        default:
        status = 'R'
        statusText = 'Rejected'
        break
      }

      const queryUpdateVelocash = {
        name: 'update-velocash',
        text: 'UPDATE k_paysheet_now SET ds_status_requisition = $1, ds_reason_reject = $2, ds_entity = $3, dt_date_response_swap = current_date, dt_time_response_swap = current_timestamp, ds_folio_swap = null, ds_description_approbation = null, ds_last_user_modifier = $4, dt_date_approbation = current_timestamp, dt_time_approbation = current_timestamp, ds_result_approbation = $5 where id_paysheet_now = $6;',
        values: [status, reasonReject, 'RHTotal', lastUserModifier, status, folio],
        rowMode: 'array'
      }

      ConfigDB.pool.query(queryUpdateVelocash, (error, results) => {
        if (error) {
          throw error
        }
        response.status(201).send(`Velocash folio: ${folio} was ${statusText}`).end()
        //response.status(201).send(`Velocash folio: ${folio} was ${statusText}`)
      })
    } else {
      response.status(errorFormat.Error.code).send(errorFormat).end()
    }
  } else {
    response.status(errorUnauthorized.Error.code).send(errorUnauthorized).end()
  }

}

/**
* @function getEmployeesByProyect
* @oaram request
* @param response
**/
dbApi.getEmployeesByProject = (request, response) => {
  let project_name = request.params.project_name
  let apikey = request.params.apikey

  let isDataOK = (project_name === undefined) ? false : true

  if(authApi(apikey)) {
    if(isDataOK){
      const queryEmployeesByProject = {
        name: 'employees-project',
        text: 'SELECT ds_email FROM k_user WHERE id_user IN (SELECT id_user AS user FROM k_employee WHERE id_project = (SELECT id_project AS client FROM k_project WHERE ds_name = $1))',
        values: [project_name],
        rowMode: 'array'
      }

      ConfigDB.pool.query(queryEmployeesByProject, (error, results) => {
        if (error) {
          throw error
        }
        if(results.rows[0]== undefined) {
          response.status(successNoInfo.Success.code).send(successNoInfo).end()
        } else {
          let employee_list = results.rows
          let info = {}
          let data = []
          let total = 0;

          results.rows.forEach(function(row, index){           
            data.push(row)
            total = total + 1
          })

          info = {
            total : total,
            employee : data
            
          }

          response.status(200).json(info).end()
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
* @function getPendingVelocash
* @oaram request
* @param response
**/
dbApi.getBankData = (request, response) => {
  let employee_id = request.params.employee_id
  let apikey = request.params.apikey

  let isDataOK = (employee_id === undefined) ? false : true

  if(authApi(apikey)) {
    if(isDataOK){
      const queryBankData = {
        name: 'bank-data',
        text: 'SELECT ds_bank, ds_bank_account, ds_clabe FROM k_employee WHERE id_user = $1 LIMIT 1',
        values: [employee_id]
        //rowMode: 'array'
      }

      ConfigDB.pool.query(queryBankData, (error, results) => {
        if (error) {
          throw error
        }
        if(results.rows[0]== undefined) {
          response.status(successNoInfo.Success.code).send(successNoInfo).end()
        } else {
          let employee = results.rows[0]
          response.status(200).json(employee).end()
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
* @function insertLog
* @oaram request
* @param response
**/
dbApi.insertLog = (request, response) => {
  let alog = ConfigDB.log_action
  let { action_type, user_id, description } = request.body
  // navigate, get_info, update_info, delete_info, download_file, send_email
  // web, mobile

  if(authApi(apikey)){

  } else {
    response.status(errorUnauthorized.Error.code).send(errorUnauthorized).end()
  }
}

/**
* @function getDestinyMails
* @oaram request
* @param response
**/
dbApi.getDestinyMails = (request, response) => {
  let sendto = request.params.sendto
  let apikey = request.params.apikey

  let isDataOK = (sendto === undefined) ? false : true

  if(authApi(apikey)) {
    if(isDataOK){
      const queryDestinyMails = {
        name: 'destiny-mails',
        text: 'SELECT ds_value FROM w_config_email WHERE ds_name_parameter = $1 LIMIT 1',
        values: [sendto],
        rowMode: 'array'
      }

      ConfigDB.pool.query(queryDestinyMails, (error, results) => {
        if (error) {
          throw error
        }
        if(results.rows[0]== undefined) {
          response.status(successNoInfo.Success.code).send(successNoInfo).end()
        } else {
          let destinyMails = results.rows
          response.status(200).json(destinyMails).end()
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
* @function sendEmail
* @oaram request
* @param response
**/
dbApi.sendEmail = (request, response) => {
  let apikey = request.params.apikey
  let password = 'VG90YWxBcHAxIw=='
  let usermail = 'aW5mb0ByaHRvdGFsLmNvbQ=='


  if(authApi(apikey)){
    //console.log(request.body)

    let transporter = nodeMailer.createTransport({
      host: 'SMTP.office365.com',
      port: 25,
      secure: false,
      auth: {
        user: 'info@rhtotal.com',
        pass: 'TotalApp1#'
      }
    })

    let mailOptions = {
      from: '"Informacion RH Total" <info@rhtotal.com>', // sender address
      to: request.body.to, // list of receivers
      subject: request.body.subject, // Subject line
      text: request.body.body, // plain text body
      html: request.body.html // html body
    }

    transporter.sendMail(mailOptions, (error, info) => {
      if (error) {
        return console.log(error)
      }
      //console.log('Message %s sent: %s', info.messageId, info.response)
      //res.render('index')
      successResponse.msg = "Envio de correo exitoso a " + request.body.to
      response.status(successResponse.Success.code).send(successResponse).end()

    })

  } else {
    response.status(errorUnauthorized.Error.code).send(errorUnauthorized).end()
  }
}


