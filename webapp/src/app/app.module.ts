import {NgModule} from '@angular/core';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {AsyncPipe, CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {ServiceWorkerModule} from '@angular/service-worker';
import {AppComponent} from './app.component';
import {AppRoutingModule} from './app-routing.module';
import {HttpRequestInterceptor} from './services/httprequest.interceptor';
import {HomeComponent} from './pages/home/home.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {ComponentsModule} from './components/components.module';
import {ProfileComponent} from './pages/profile/profile.component';
import {AccountComponent} from './pages/account/account.component';
import {MatchesComponent} from './pages/matches/matches.component';
import {LoginComponent} from './pages/login/login.component';
import {RegisterComponent} from './pages/register/register.component';
import {MatchesModule} from './pages/matches/matches.module';
import {HobbymoduleModule} from './pages/profile/hobbymodule/hobbymodule.module';
import {environment} from '../environments/environment';
import { LogoutComponent } from './pages/logout/logout.component';

import {AngularFireMessagingModule} from "@angular/fire/messaging";
import {AngularFireModule} from "@angular/fire";
import {MessagingService} from "./services/messaging.service";



@NgModule({
  imports: [
    CommonModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgbModule,
    ComponentsModule,
    ReactiveFormsModule,
    MatchesModule,
    HobbymoduleModule,
    AngularFireMessagingModule,
    AngularFireModule.initializeApp(environment.firebaseConfig),
    environment.production ? ServiceWorkerModule.register('./ngsw-worker.js', ): []
  ],
  declarations: [
    AppComponent,
    HomeComponent,
    ProfileComponent,
    AccountComponent,
    MatchesComponent,
    LoginComponent,
    RegisterComponent,
    LogoutComponent,
  ],
  providers: [
    MessagingService,
    AsyncPipe,
    /*{
      provide: HTTP_INTERCEPTORS,
      useClass: HttpRequestInterceptor,
      multi: true
    },

     */
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
