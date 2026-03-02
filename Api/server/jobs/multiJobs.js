const CronJob = require("node-cron");
const express = require("express");
const fs = require("fs");
const dateFormat = require('dateformat');


console.log('Start Process');
const job = CronJob.schedule('*/20 * * * * *', function() {
	const now = new Date();	
	console.log('Check Sico:', dateFormat(now, "dddd, mmmm dS, yyyy, h:MM:ss TT"));
});

const job2 = CronJob.schedule('*/20 * * * * *', function() {
	const now = new Date();
	console.log('Check Data:', dateFormat(now, "HH:MM:ss TT"));
});
console.log('After job instantiation');
job.start();
job2.start();