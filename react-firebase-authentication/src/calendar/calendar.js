import React, { Component } from 'react';
import  gapi from 'react-google-calendar-api';

import moment from "moment";
import { GOOGLE_API_KEY, CALENDAR_ID } from './config.js';

export default class Calendar extends Component{
      constructor(props){
        super(props)

        this.state = {
          time: moment().format("dd,Do MMMM, h:mm A"),
          events:  [],
          isBusy: false,
          isEmpty: false,
          isLoading: true
        };
      }//close cunstructor class braker

     componentDidMount = () => {
      this.getEvents();
      setInterval(() => {
        this.tick();
      }, 1000);
      setInterval(() =>{
       this.getEvents();
      },6000);
     };



  getEvents() {
    let that = this;
    function start() {
      gapi.client
        .init({
          apiKey: GOOGLE_API_KEY
        })
        .then(function() {
          return gapi.client.request({
            path: `https://www.googleapis.com/calendar/v3/calendars/${CALENDAR_ID}/events?maxResults=11&orderBy=updated&timeMin=${moment().toISOString()}&timeMax=${moment()
              .endOf("day")
              .toISOString()}`
          });
        })
        .then(
          response => {
            let events = response.result.items;
            let sortedEvents = events.sort(function(a, b) {
              return (
                moment(b.start.dateTime).format("YYYYMMDD") -
                moment(a.start.dateTime).format("YYYYMMDD")
              );
            });
            if (events.length > 0) {
              that.setState(
                {
                  events: sortedEvents,
                  isLoading: false,
                  isEmpty: false
                },
                () => {
                  that.setStatus();
                }
              );
            } else {
              that.setState({
                isBusy: false,
                isEmpty: true,
                isLoading: false
              });
            }
          },
          function(reason) {
            console.log(reason);
          }
        );
    }
    gapi.load("client", start);
  }
render() {
    const { time, events } = this.state;

    let eventsList = events.map(function(event) {
      return (
        <a
          className="list-group-item"
          href={event.htmlLink}
          target="_blank"
          key={event.id}
        >
          {event.summary}{" "}
          <span className="badge">
            {moment(event.start.dateTime).format("h:mm a")},{" "}
            {moment(event.end.dateTime).diff(
              moment(event.start.dateTime),
              "minutes"
            )}{" "}
            minutes, {moment(event.start.dateTime).format("MMMM Do")}{" "}
          </span>
        </a>
      );
    });

let emptyState = (
      <div className="empty">
        <img src="" alt="Welcome" />
        <h3>
          No meetings are scheduled for the day. Create one by clicking the
          button below.
        </h3>
      </div>
    );
 let loadingState = (
      <div className='loading'>
        <img src="" alt='Loading...' />
      </div>
      );


       return (
      <div className="container">
        <div
          className={
            this.state.isBusy ? "current-status busy" : "current-status open"
          }
        >
          <h1>{this.state.isBusy ? "BUSY" : "OPEN"}</h1>
        </div>
        <div className="upcoming-meetings">
          <div className="current-time">{time}, 2018</div>
          <h1>Upcoming Meetings</h1>
          <div className="list-group">
            {this.state.isLoading && loadingState}
            {events.length > 0 && eventsList}
            {this.state.isEmpty && emptyState}
          </div>
          <a
            className="primary-cta"
            href="https://calendar.google.com/calendar?cid=c3FtMnVkaTFhZGY2ZHM3Z2o5aDgxdHVldDhAZ3JvdXAuY2FsZW5kYXIuZ29vZ2xlLmNvbQ"
            target="_blank"
          >
            +
          </a>
        </div>
      </div>
    );

  }//close render
    
} 
