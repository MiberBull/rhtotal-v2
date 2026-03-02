'use strict'

class errorModel  {
  constructor(code, msg){
    this.Error = {
      'code': code,
      'msg': msg
    }
  }
}

module.exports = errorModel
