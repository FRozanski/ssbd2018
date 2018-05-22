import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { AccountListComponent } from './account-list/account-list.component';
import { RouterModule, Routes } from '@angular/router';
import { MainPageComponent } from './main-page/main-page.component';
import { AccountService } from './common/account.service';
import { MatTableModule, MatCheckboxModule, MatButtonModule, MatInputModule, MatCardModule } from '@angular/material';
import { RegisterComponent } from './register/register.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

export function createTranslateLoader(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}

const appRoutes: Routes = [
  { path: 'main', component: MainPageComponent },
  { path: 'accounts', component: AccountListComponent },
  { path: 'register', component: RegisterComponent },
  { path: '', redirectTo: 'main', pathMatch: 'full' }
];

@NgModule({
  declarations: [
    AppComponent,
    MainPageComponent,
    AccountListComponent,
    RegisterComponent
  ],
  imports: [
    MatTableModule,
    MatCheckboxModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    RouterModule.forRoot(appRoutes),
    BrowserModule,
    HttpClientModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: createTranslateLoader,
        deps: [HttpClient]
      }
    })
  ],
  bootstrap: [AppComponent],
  providers: [
    AccountService
  ]
})
export class AppModule { }
