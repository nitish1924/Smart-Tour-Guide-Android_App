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

app.post('/register',(req,res)=>{
	//return res.json("hello")
	const{email,name,password,contact} = req.body; //destructuring
	if(!email||!name||!password||!contact){ // checking blank fields
		return res.send('All fields are Mandatory!');
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
	.then(data=>res.send("Registered Successfuly!"))
	.catch((error) => res.send(error.message));
});

app.post('/signin',(req,res)=>{
	//return res.json("hello")
	console.log("request received");
	console.log(req.body);
	const{email,password} = req.body; //destructuring
	if(!email||!password){ // checking blank fields
		
		return res.send("All fields are Mandatory!");
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
		return res.send(error.message);
	});
});
app.post('/addwishlist',(req,res)=>{
	//return res.json("hello")
	console.log("request wishlist addwishlist");
	console.log(req.body);
	const{user,name,description,rating,price,contact,photoUrl,latitude,longitude,address} = req.body; //destructuring
	db.ref("wishlist").push({ // set can be used instead of push
			user:user,
			name:name,
			description:description,
			rating:rating,
			price:price,
			contact:contact,
			photoUrl:photoUrl,
			latitude:latitude,
			longitude:longitude,
			address:address
	})
	.then(data=>res.send("wishlist saved Successfuly!"))
	.catch((error) => res.send(error.message));
	
});
app.post('/getwishlist',(req,res)=>{
	//return res.json("hello")
	console.log("request getwishlist");
	console.log(req.body);
	const{user} = req.body; //destructuring
	var responseArray=[];
	db.ref('wishlist').once("value")
		.then((snapshot) => {
		    snapshot.forEach((Snapshot) => {
		    	if(Snapshot.child('user').val()===user){
		     	name = Snapshot.child('name').val()
		     	description = Snapshot.child('description').val()
		     	rating = Snapshot.child('rating').val()
		     	price = Snapshot.child('price').val()
		     	contact = Snapshot.child('contact').val()
		     	photoUrl = Snapshot.child('photoUrl').val()
		     	latitude = Snapshot.child('latitude').val()
		     	longitude = Snapshot.child('longitude').val()
		     	address = Snapshot.child('address').val()

		  		var response = {"name":name,"description":description,"rating":rating,"price":price,"contact":contact,"photoUrl":photoUrl,"latitude":latitude,"longitude":longitude,"address":address}
		  		responseArray.push(response)
		     	}
		  	});
		  	return res.json(responseArray);
		})
		.catch((error)=>{return res.send(error.message)});
	
});
//forgot password route
app.post('/forgotpwd',(req,res)=>{
	//return res.json("hello")
	console.log("request forgotpwd");
	console.log(req.body);
	const{email} = req.body; //destructuring
	auth.sendPasswordResetEmail(email)
		.then(() => {
			return res.send("Password reset email sent to employee registered email address");
		})
		.catch((error)=>{
			return res.send(error.message);
		});
	
});
	



//app.get('/testGet',(req,res)=>(res.json("Bingo!!!..working")));

app.listen(3000,(a,b)=>{console.log('running at port 3000')})