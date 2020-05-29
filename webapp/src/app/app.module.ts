import {NgModule} from '@angular/core';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {ServiceWorkerModule} from '@angular/service-worker';
import {AppComponent} from './app.component';
import {AppRoutingModule} from './app-routing.module';
import {Http_interceptor} from './services/http_interceptor';
import {HomeComponent} from './pages/home/home.component';
import {NotificationsComponent} from './pages/notifications/notifications.component';
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

@NgModule({
  imports: [
    CommonModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgbModule,
    ComponentsModule,
    ServiceWorkerModule.register('ngsw-worker.js', {enabled: environment.production}),
    ReactiveFormsModule,
    MatchesModule,
    HobbymoduleModule,
  ],
  declarations: [
    AppComponent,
    HomeComponent,
    NotificationsComponent,
    ProfileComponent,
    AccountComponent,
    MatchesComponent,
    LoginComponent,
    RegisterComponent,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: Http_interceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
