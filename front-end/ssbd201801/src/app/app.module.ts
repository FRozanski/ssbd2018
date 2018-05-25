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
import {
  MatTableModule,
  MatCheckboxModule,
  MatButtonModule,
  MatInputModule,
  MatCardModule,
  MatSidenavModule,
  MatSortModule,
  MatPaginatorModule
} from '@angular/material';
import { RegisterComponent } from './register/register.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';
import { LocationStrategy, HashLocationStrategy } from '@angular/common';
import { SidenavComponent } from './sidenav/sidenav.component';
import { AuthUtilService } from './common/auth-util.service';
import { AuthGuard } from './common/user-guard';
import { AccountEditComponent } from './account-edit/account-edit.component';
import { RegistrationConfirmComponent } from './registration-confirm/registration-confirm.component';
import { ChangeOthersPasswordComponent } from './change-others-password/change-others-password.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { OwnAccountEditComponent } from './own-account-edit/own-account-edit.component';
import {SessionService} from './common/session.service';
import { AccountStatisticsComponent } from './account-statistics/account-statistics.component';
import {MessageService} from './common/message.service';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

export function createTranslateLoader(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}

const appRoutes: Routes = [
  {
    path: '',
    redirectTo: 'main',
    pathMatch: 'full'
  },
  {
    path: 'main',
    component: MainPageComponent
  },
  {
    path: 'accounts',
    canActivate: [AuthGuard],
    component: AccountListComponent,
    data:
      {
        expectedRole: 'ADMIN'
      }
  },
  {
    path: 'statistics',
    canActivate: [AuthGuard],
    component: AccountStatisticsComponent,
    data:
      {
        expectedRole: 'ADMIN'
      }
  },
  {
    path: 'register',
    canActivate: [AuthGuard],
    component: RegisterComponent,
    data:
      {
        expectedRole: 'GUEST'
      }
  },
  {
    path: 'accountEdit/:id',
    canActivate: [AuthGuard],
    component: AccountEditComponent,
    data:
    {
      expectedRole: 'ADMIN'
    }
  },
  {
    path: 'changeMyPassword',
    component: ChangePasswordComponent,
    data:
    {
      expectedRole: 'USER'
    }
  },
  {
    path: 'changeOthersPassword',
    component: ChangeOthersPasswordComponent
  },
  {
    path: 'registrationConfirm',
    component: RegistrationConfirmComponent
  },
  {
    path: 'myAccount',
    component: OwnAccountEditComponent,
    data:
    {
      expectedRole: 'USER'
    },
    canActivate: [AuthGuard]
  },
  {
    path: '**',
    redirectTo: 'main'
  }

];

@NgModule({
declarations: [
    AppComponent,
    MainPageComponent,
    AccountListComponent,
    RegisterComponent,
    RegistrationConfirmComponent,
    ChangeOthersPasswordComponent,
    ChangePasswordComponent,
    SidenavComponent,
    AccountEditComponent,
    OwnAccountEditComponent,
    AccountStatisticsComponent
  ],
  imports: [
    MatTableModule,
    MatCheckboxModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatSidenavModule,
    MatSortModule,
    MatPaginatorModule,
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
    AccountService,
    SessionService,
    MessageService,
    RegistrationConfirmComponent,
    {provide: LocationStrategy, useClass: HashLocationStrategy},
    AuthGuard,
    AuthUtilService
  ]
})
export class AppModule { }
