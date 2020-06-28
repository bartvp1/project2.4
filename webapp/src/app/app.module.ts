import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import {AsyncPipe, CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {ServiceWorkerModule} from '@angular/service-worker';
import {AppComponent} from './app.component';
import {AppRoutingModule} from './app-routing.module';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {ComponentsModule} from './components/components.module';
import {environment} from '../environments/environment';
import {AngularFireMessagingModule} from "@angular/fire/messaging";
import {AngularFireModule} from "@angular/fire";
import {MessagingService} from "./services/messaging.service";
import {PagesModule} from "./pages/pages.module";



@NgModule({
  imports: [
    CommonModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgbModule,
    ComponentsModule,
    PagesModule,
    ReactiveFormsModule,
    AngularFireMessagingModule,
    AngularFireModule.initializeApp(environment.firebaseConfig),
    environment.production ? ServiceWorkerModule.register('./ngsw-worker.js', ): []
  ],
  declarations: [
    AppComponent
  ],
  providers: [
    MessagingService,
    AsyncPipe,
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
