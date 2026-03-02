const config = module.exports = {}

const { Pool, Client } = require('pg')

config.pool = new Pool({
	user: 'rhtotal',
	host: 'rhtotal.cb5ktwale9p7.us-east-1.rds.amazonaws.com',
	database: 'rhtotal',
	password: 'UPi0TiKY4vzpdHRntejrh1v40Jp',
	port: 5432,
})

config.db_pool = new Pool({
	user: 'admin',
	host: 'ec2-52-205-42-63.compute-1.amazonaws.com',
	database: 'rhtotal',
	password: 'rhtotal13',
	port: 5432,
})

/*
config.client = new Client({
user: 'rhtotal',
host: 'rhtotal.cb5ktwale9p7.us-east-1.rds.amazonaws.com',
database: 'rhtotal',
password: 'UPi0TiKY4vzpdHRntejrh1v40Jp',
port: 5432,
})
*/
//client.connect()
