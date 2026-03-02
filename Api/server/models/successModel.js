'use strict'
/**
 * Class represent a success model
 * 
 **/
class successModel  {
  constructor(code, msg){
    this.Success = {
      'code': code,
      'msg': msg
    }
  }

  /**
   * Getters and setters for success Model
   **/
  get code() {
  	return this.Success.code
  }

  get msg() {
  	return this.Success.msg
  }

  set code(newCode){
  	this.Success.code = newCode
  }

  set msg(newMsg){
  	this.Success.msg = newMsg
  }
}

module.exports = successModel
