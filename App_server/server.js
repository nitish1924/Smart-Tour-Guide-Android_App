const express = require('express');
const bodyParser = require('body-parser');
const firebase = require('firebase');

const app = express();
app.use(bodyParser.json());

//Firebase connection

const config = {
  apiKey: "AIzaSyCZTk68dRoH5hyBztWxkbqRlLrIfYdg3o0",
  authDomain: "cis600-tour-guide.firebaseapp.com",
  databaseURL: "https://cis600-tour-guide.firebaseio.com/",
  storageBucket: "gs://cis600-tour-guide.appspot.com ",
};


firebase.initializeApp(config);
const db = firebase.database();
const auth =firebase.auth();

//end------firebase connect

const insert = ()=>{
	db.ref('demo').push({ // set can be used instead of push
    username: "name",
    email: "email",
    profile_picture : "imageUrl"
  });
}

//insert();


app.post('/register',(req,res)=>{
	//return res.json("hello")
	const{email,name,password,contact} = req.body; //destructuring
	if(!email||!name||!password||!contact){ // checking blank fields
		return res.status(400).json('All fields are Mandatory!');
	}
	auth.createUserWithEmailAndPassword(email, password)
	.then(user=>user.user.uid)
	.then((uid)=>{
		db.ref("users").push({ // set can be used instead of push
			Email:email,
		    UserId: uid,
		    Name: name,
		    contact:contact
  		})
	})
	.then(data=>res.json("Registered Successfuly!"))
	.catch((error) => res.status(400).json(error.message));
});

app.post('/signin',(req,res)=>{
	//return res.json("hello")
	console.log("request received");
	console.log(req.body);
	const{email,password} = req.body; //destructuring
	if(!email||!password){ // checking blank fields
		return res.status(400).json('All fields are Mandatory!');
	}
	var name =  "";
	var email1 ="";
	var contact="";
	auth.signInWithEmailAndPassword(email, password)
	.then((user)=>{
		db.ref('users').once("value")
		.then((snapshot) => {
		    snapshot.forEach((Snapshot) => {
		    	if(Snapshot.child('UserId').val()===user.user.uid){
		     	name = Snapshot.child('Name').val()
		     	email1 = Snapshot.child('Email').val()
		     	contact = Snapshot.child('contact').val()
		     	}
		  	});
		  	var response = {"email":email1, "name":name,"contact":contact}
		  	return res.json(response);
		});
	})
	.catch((error)=>{
		return res.status(400).json(error.message);
	});
});



app.get('/testGet',(req,res)=>(res.json("Bingo!!!..working")));

app.listen(3000,(a,b)=>{console.log('running at port 3000')})