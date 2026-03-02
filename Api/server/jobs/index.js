    // index.js
    const cron = require("node-cron");
    const express = require("express");
    const fs = require("fs");
    const testApi = require("./actions/test")

    app = express();

    // schedule tasks to be run on the server   
    cron.schedule("*/2 * * * * *", function() {
      console.log("running a task every two seconds");
      testApi.testJOB();

    });

    app.listen(3128);