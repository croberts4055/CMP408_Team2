//well well now this is our navigation component here we can say 
//that this is just like a wrapper nav tab that contains links towards
//our website pages   sweet :-)
//please note am just stating with React is not like am a proo 
//things can be done different but here am just folowing a tutorial

import React from 'react';
import { Link } from  'react-router-dom';

//in this import we are navigating back 2 directories to allocate
//the route.js folder remember here we have all the links
//needed for the app pages and css files 
//her also you can implement boostrap if you like is not as difficult as it looks 
//remember if you are trying to add some nice mobile compatible css you can easy do it in react is not a big deal 
import * as routes from '../constants/routes';

const Navigation = () => 
  <div className="App-header">
    <ul className="link-header">
      <li><Link className="Links"  to={routes.SIGN_IN}>Sign In</Link></li>
      <li><Link className="Links"  to={routes.LANDING}>Landing</Link></li>
      <li><Link className="Links"  to={routes.HOME}>Home</Link></li>
      <li><Link className="Links"  to={routes.ACCOUNT}>Account</Link></li>

    </ul>
  </div>


 export default Navigation;