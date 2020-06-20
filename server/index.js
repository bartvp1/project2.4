const express = require('express');
const cors = require('cors')
const fs = require('fs'); 
const _ = require("lodash");
const bodyParser = require("body-parser");
const jwt = require("jsonwebtoken");
const webpush = require('web-push');
const expressJwt = require('express-jwt');

const users = [
  { id: 1, name: 'bart', password: 'henker' },
  { id: 2, name: 'test', password: 'test' }
];
const PUBLIC_VAPID='BHZX9cdrYz3nQpd-teqYtlvkQWngasxcX5UTSsTGdFIjSBLulClF7NkE0kiQgW4LtJkyvwlgzMJOzHj12OrntDA';
const PRIVATE_VAPID='KCfnMDVddubKOrDIm0pUr46g5Yr_Ouiz99KXQxbHVf8';

let highscore = 0;


webpush.setVapidDetails('mailto:localhost:8080', PUBLIC_VAPID, PRIVATE_VAPID);
const privateKey = fs.readFileSync('./private.pem', 'utf8');
const publicKey = fs.readFileSync('./public.pem', 'utf8');

const checkIfAuthenticated = expressJwt({
  secret: publicKey
});

const signOptions = {
  expiresIn: "30d",
  algorithm: 'ES256'
};

// Express

const app = express();
app.use(cors())

//parse usual forms
app.use(bodyParser.urlencoded({
  extended: true
}));

//parse json for APIs
app.use(bodyParser.json());

app.get('/api', (req, res) => {
  res.json( {message: 'hallo allemaal...'} )
});

app.get('/api/updatescore', (req, res) => {
	if(req.body.score > highscore) {
	highscore = req.body.score; }
});

app.get('/api/getscore', (req, res) => {
	res.json( {highscore: highscore} )
	});


app.post('/api/login', function (req, res) {
	console.log(req.headers);
  if (req.body.name && req.body.password) {
    var name = req.body.name;
  }

  var user = users[_.findIndex(users, { name: name })];

  if (!user) {
    res.status(404).json({ message: 'no such user found' });
  }

  if (user.password === req.body.password) {
    let payload = { name, id: user.id };
    let token = jwt.sign(payload, privateKey, signOptions);
    res.json({
      message: 'ok',
      token: token,
      expiresIn: jwt.decode(token).exp
    });
  } else {
    res.status(401).json({ message: 'password did not match' });
  }
});
app.post('/api/subscription', (req, res) => {
	console.log("subscibe");
       const subscription = req.body;
		console.log(subscription);
		
		const notificationPayload={"notification":{
			
			
			"title":"Welcome",
			"body": "welcome"
			
		}
		
		
		
		
		};
		
		
		
		webpush.sendNotification(subscription, 
JSON.stringify(notificationPayload));
    });

app.route('/api/secret')
  .get(checkIfAuthenticated, function (req, res) {
    res.json({ message: "Success! You can not see this without a token" });
  })

const PORT = process.env.PORT || 5000;

app.listen(PORT, function () {
  console.log("Express starting listening on port "+PORT)
  console.log("Express running")
});
