var http = require('http');
var querystring = require('querystring');
 
var postHTML = 
    '<html><head><meta charset="utf-8"><title>test post</title></head>' +
    '<body>' +
    '<form method="post">' +
    'post_data: <input name="post_data"><br>' +
    '<input type="submit">' +
    '</form>' +
    '</body></html>';
 
http.createServer(function (req, res) {
    var body = "";
    req.on('data', function (chunk) {
        body += chunk;
    });
    req.on('end', function () {
        // parse param
        console.log(body);
        body = querystring.parse(body);
        console.log(body);
        // set header & encode
        res.writeHead(200, {'Content-Type': 'text/html; charset=utf8'});

        if(body.post_data) {     // display post_data
            res.write("post_data: " + body.post_data);
        } else {                        // display table
            res.write(postHTML);
        }
        res.end();
    });
}).listen(3000);

