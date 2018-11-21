//hey this is our landing page you can accomplis alot here as a front end developer please add some style css
// to this landing page  for example add some page welcome screen and add some animations to it 
import React from 'react';
//lets implements google calendar api
import Calendar from 'react_google_calendar'

//lets declare the constent object LandingPage remember you reference this object
//in the app.js file ads a component
const LandingPage =  () => 
  <div>
    <h1>Landing Page </h1>
    <Calendar></Calendar>
  </div>

  export default LandingPage;