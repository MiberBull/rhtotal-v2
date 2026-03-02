const express = require('express')
, http = require('http')
, setApi = require('./db/get-settings/get-settings')
, dbApi = require('./db/actions')
, bodyParser = require('body-parser')

const app = express()
//app.set('view engine', 'ejs');

app.use(function(req, res, next) {
	res.header("Access-Control-Allow-Origin", "*")
	res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept")
	res.header('Access-Control-Allow-Methods', 'PUT, POST, GET, DELETE, OPTIONS')
	next()
})

app.use(bodyParser.json()) // for parsing application/json

app.set('port', process.env.PORT || 3000)

/**
 * Settings
 **/
 app.get('/api/v1/settings/:property', (req, res) => { setApi.getSettings(req, res) })


/**
 * Services
 **/
 app.patch('/api/v1/update/service', (req, res) => { setApi.setService(req, res) })

/**
 * Fintech Apis
 */
 app.get('/api/v1/velocash/:status/:page/:apikey', (req, res) => { dbApi.getPendingVechocash(req, res) })
 app.put('/api/v1/update/veloash/:apikey', (req, res) =>{ dbApi.updateVelocash(req, res) }) 
 app.get('/api/v1/myadvance/:status/:page/:apikey', (req, res) => { dbApi.getPendingMyAdvance(req, res) })

/**
 * Employee Apis
 */
 app.get('/api/v1/bank/:employee_id/:apikey', (req, res) => { dbApi.getBankData(req, res) })
 app.get('/api/v1/employee/:project_name/:apikey', (req, res) => { dbApi.getEmployeesByProject(req, res) })



//app.listen(app.get('port'), () => console.log('Server running on port ' + app.get('port')))

/**
 * Email Sender
 **/
 app.get('/api/v1/email/destiny/:sendto/:apikey', (req, res) => { dbApi.getDestinyMails(req, res) })
 app.post('/api/v1/email/send/:apikey', function (req, res) { dbApi.sendEmail(req, res) })


/**
 * Server
 **/
 var server = app.listen(app.get('port'), function () {
 	var host = server.address().address
 	var port = server.address().port
 	console.log('RHTotal app listening at http://%s:%s', host, port)
 })