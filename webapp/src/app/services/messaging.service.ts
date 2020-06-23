import {Injectable} from '@angular/core';
import {AngularFireMessaging} from '@angular/fire/messaging';
import {BehaviorSubject, Observable} from 'rxjs'

@Injectable()
export class MessagingService {
  currentMessage = new BehaviorSubject(null);
  constructor(private angularFireMessaging: AngularFireMessaging) {
    this.angularFireMessaging.messages.subscribe((e) => {
        e.constructor().onMessage = e.constructor().onMessage.bind(e);
        e.constructor().onTokenRefresh = e.constructor().onTokenRefresh.bind(e);
      }
    )
  }
  requestPermission() {
    this.angularFireMessaging.requestToken.subscribe(
      (token) => {
        console.log(token);
      },
      (err) => {
        console.error('Unable to get permission to notify.', err);
      }
    );
  }

  receiveMessage() {
    this.angularFireMessaging.messages.subscribe(
      (payload) => {
        console.log("new message received. ", payload);
        this.currentMessage.next(payload);
      })
  }
}