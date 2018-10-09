 //this is the firebase configuration file and it points towards my own data base 
 // also please note that firebase is nonsql data base 
 //back-end engineer luis manon
 import firebase from "firebase/app";
 import 'firebase/auth';

 // Initialize Firebase
  var config = {
    apiKey: "AIzaSyDtFUSjpYV45BXWJB7eWF7H0SEQw1L0YXI",
    authDomain: "makeacase-b9edd.firebaseapp.com",
    databaseURL: "https://makeacase-b9edd.firebaseio.com",
    projectId: "makeacase-b9edd",
    storageBucket: "",
    messagingSenderId: "119853576174"
  };

  if(!firebase.apps.length){
  firebase.initializeApp(config);
}

//lets instantiate the authentication
const auth =  firebase.auth();
export {
	auth,
};