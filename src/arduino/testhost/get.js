var http = require('http');
var url = require('url');
var util = require('util');
 
http.createServer(function(req, res){
    res.writeHead(200, {'Content-Type': 'text/plain'});
 
    // parse url param
    var params = url.parse(req.url, true).query;
    res.write("get_data: " + params.get_data);
    res.end();
 
}).listen(3000);

