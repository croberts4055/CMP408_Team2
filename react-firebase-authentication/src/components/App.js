import React, { Component } from 'react';
import { firebase } from '../firebase';
//we wont need to import the logo since we will not need it in this case
//import the css so you keep updating the style of all html component elements
import './App.css';



import {
  BrowserRouter as Router,
  Route,
} from 'react-router-dom';




//lets import the routes lol
import * as routes from '../constants/routes';

//take a look for the front end developers you can use app.css file
//to add any css style to our html elements 
//now in this App.js file here is our main application component
// here you need to specify the navigation component for our main app
/* hey react is easy when you have previous js skill */
//just need to follow coding structural patterns

import Navigation from './Navigation';
//include all our pages 
import AccountPage from'./Account';
import HomePage from './Home';
import LandingPage from './Landing';
import PasswordChangePage from './PasswordChange';
import PasswordForgetPage from './PasswordForget';
import SignInPage from './SignIn';
import SignOutPage from './SignOut';
import SignUpPage from './SignUp';
//see the div below the router it coild be threated as an wrapper
//you can add css to it if you like 
class App extends Component { 
  constructor(props)
  {
    super(props)

    this.state = {
      authUser: null,
    };
  }


//this function is offered by firebase to handle authentication
componentDidMount(){
  firebase.auth.onAuthStateChanged(authUser => {
     authUser
       ? this.setState({authUser})
       : this.setState({authUser: null});
  });
}





  //render method
  render(){
       return (
<Router>
   <div>
  <Navigation authUser={this.state.authUser}/>
  <hr/>

  <Route exact path={routes.LANDING}
          component={LandingPage}
          />
   <Route exact path={routes.SIGN_OUT}
          component={SignOutPage}
          />
  <Route exact path={routes.SIGN_UP}
          component={SignUpPage}
          />
  <Route exact path={routes.SIGN_IN}
          component={SignInPage}
          />
  <Route exact path={routes.PASSWORD_FORGET}
          component={PasswordForgetPage}
          />
  <Route exact path={routes.PASSWORD_CHANGE}
          component={PasswordChangePage}
          />
  <Route exact path={routes.HOME}
          component={HomePage}
          />
  <Route exact path={routes.ACCOUNT}
          component={AccountPage}
          />
  </div>
</Router>
);
}
}

export default App;


