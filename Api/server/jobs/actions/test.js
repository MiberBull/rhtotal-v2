var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;
var xhr = new XMLHttpRequest();

const testApi = module.exports = {}
const url='http://localhost:3000/api/v1/email/destiny/emailSupport/YXNkZmdoMTQ3cmh0b3RhbA==';

/**
* @function testJOB
**/
testApi.testJOB = () => {
	xhr.open("GET", url);
	xhr.send();


	xhr.onreadystatechange = (e) => {
	  console.log(xhr.responseText)
	}
}