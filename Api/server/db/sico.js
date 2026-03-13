var http = require("http");

var options = {
  "method": "GET",
  "hostname": "nominaenlanube.com",
  "port": "8085",
  "path": [
  "api-nomen",
  ":identificador",
  "auth"
  ],
  "headers": {}
};

var req = http.request(options, function (res) {
  var chunks = [];

  res.on("data", function (chunk) {
    chunks.push(chunk);
  });

  res.on("end", function () {
    var body = Buffer.concat(chunks);
    console.log(body.toString());
  });
});

req.end();