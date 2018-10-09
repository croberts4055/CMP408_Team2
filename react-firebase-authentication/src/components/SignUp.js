import React, { Component } from 'react';

import { auth } from '../firebase';
import { Link,
         withRouter,
          } from 'react-router-dom';

import * as routes from '../constants/routes';



//lets declare the constent object SignUpPage remember you reference this object
//in the app.js file ads a component
const SignUpPage =  ({history}) => 
  <div>
    <h1>SignUp </h1>
    <SignUpForm history={history}/>
  </div>

  //lets create the signUp form it is a class 
  //a class also has constructor on react isn;t this crazy lol

  const INITIAL_STATE = {
  	username: '',
  	email: '',
  	passwordOne: '',
  	passwordTwo: '',
  	error: null,
  };

//NOW LETS CREATE A NEW PROPERTY 
const byPropKey = (propertyName, value) => () => ({
  [propertyName]: value,
});


  class SignUpForm extends Component {
  	constructor(props){
  		super(props);
  		this.state =  {...INITIAL_STATE};
  	}


  	onSubmit = (event) => {
  		//now the onSubmit button is going to be calling this function and event is going
  		const {
  			username,
  			email,
  			passwordOne,

  		} =  this.state;

  	//another prop this is the history prop
  	const {
      history,
    } = this.props;

  		auth.doCreateUserWithEmailAndPassword(email,passwordOne)
  		.then(authUser => {
  			this.setState({...INITIAL_STATE});
  			history.push(routes.HOME);
  		})
  		.catch(error => {
  			this.setState(byPropKey('error', error));
  		});
       
        //event prevent default is preventing our page from refreshing 
  		event.preventDefault();

  	}

  	//now our render function
    render() {
        const {
        	username,
        	email,
        	passwordOne,
        	passwordTwo,
        	error,
        } =  this.state;
     
     const isInvalid = 
            passwordOne !== passwordTwo ||
            passwordOne === '' ||
            email === '' ||
            username === '';

    	return (
             <form onSubmit={this.onSubmit}>
             <input
                value={username}
                onChange={event => this.setState(byPropKey('username',event.target.value))}
                type="text"
                placeholder="Full Name"
                />
                <input
                value={email}
                onChange={event => this.setState(byPropKey('email',event.target.value))}
                type="text"
                placeholder="Email"
                />
                <input
                value={passwordOne}
                onChange={event => this.setState(byPropKey('passwordOne',event.target.value))}
                type="password"
                placeholder="Password"
                />
                <input 
                value={passwordTwo}
                onChange={event => this.setState(byPropKey('passwordTwo', event.target.value))}
                type="password"
                placeholder="Confirm password"
                />
                <button disabled={isInvalid} type="submit">Sign Up</button>
                {error && <p>{error.message}</p>}
             </form>
    		);
    }
  };//close class signup form 

  //lets create signUpLink now 
  const  SignUpLink = () =>
      <p>
         Dont have an account {'  '}
           <Link to={routes.SIGN_UP}>Sign Up</Link>
      </p>
       


  export default withRouter(SignUpPage);

  export {
  	 SignUpForm,
  	 SignUpLink,

  };