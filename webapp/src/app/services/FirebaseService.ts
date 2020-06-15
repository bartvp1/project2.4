import * as firebase from "firebase";
import {Injectable} from "@angular/core";
import {environment} from "../../environments/environment";


@Injectable({
  providedIn: 'root'
})
export class FirebaseService {
  constructor() {
    firebase.initializeApp(environment.firebaseConfig)
    firebase.analytics()
  }
}
