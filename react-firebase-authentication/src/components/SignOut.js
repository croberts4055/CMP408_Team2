	//back end engineer luis manon
	import React, { Component } from 'react';
	import { withRouter } from 'react-router-dom';

	import { SignUpLink } from './SignUp';
	import { auth } from '../firebase';
	import * as routes from '../constants/routes';


	const SignOutPage = ({ history }) =>
	  <div>
	    <h1>SignOut</h1>
	    <SignOut history={history} />
	  </div>
	//lets declare the constent object SignOutPage remember you reference this object
	//in the app.js file ads a component
	class SignOut extends Component {
		constructor(props) {
	    super(props);
	    }
	   
        //this is a function event which is going to be call on button click 
	    onSignOut = (event) => {

	    const {
	      history,
	    } = this.props;


	    auth.doSignOut()
	      .then(() => {
	      	history.push(routes.HOME);
	      })
	      .catch(error => {
	        console.log(error);
	      });

	    event.preventDefault();
	  }//close the sign out function


		render()
		{
	       return(
	       	 <button
	    	type="button"
	    	onClick={this.onSignOut}
	   			 > 
	   		 Sign Out
	   			</button>
	       	);
		}
	};
	
	export default withRouter(SignOutPage);

	export {
	  SignOut,
	};
