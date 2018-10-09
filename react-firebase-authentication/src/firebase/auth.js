import { auth } from './firebase';

//lets define the method for sign up 
export const doCreateUserWithEmailAndPassword = (email, password) =>
auth.createUserWithEmailAndPassword(email, password);


//sign  In 
export const doSignInWithEmailAndPassword = (email, password) =>
auth.signInWithEmailAndPassword(email, password);

//sign out 
export const doSignOut = () =>
auth.signOut();


//password reset 
export const doPasswordReset = (email) =>
auth.sendPasswordResetEmail(email);

//password change
export const doPasswordUpdate = (password) =>
auth.currentUser.updatePassword(password);