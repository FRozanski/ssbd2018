import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { RouterModule, Routes } from '@angular/router';
import { MainPageComponent } from './main-page/main-page.component';
import {
  MatTableModule,
  MatCheckboxModule,
  MatButtonModule,
  MatInputModule,
  MatCardModule,
  MatSidenavModule,
  MatSortModule,
  MatPaginatorModule, MatPaginatorIntl
} from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';
import { LocationStrategy, HashLocationStrategy } from '@angular/common';
import { AccountListComponent } from './mok/account-list/account-list.component';
import { AccountService } from './mok/common/account.service';
import { RegisterComponent } from './mok/register/register.component';
import { BaseAccountFormComponent } from './mok/account-forms/_base-account-edit/base-account-form.component';
import { SidenavComponent } from './shared/sidenav/sidenav.component';
import { AuthGuard } from './mok/common/auth-guard';
import { AccountStatisticsComponent } from './mok/account-statistics/account-statistics.component';
import { AccountEditComponent } from './mok/account-forms/account-edit/account-edit.component';
import { ChangePasswordComponent } from './mok/change-password/change-password.component';
import { ChangeOthersPasswordComponent } from './mok/change-others-password/change-others-password.component';
import { RegistrationConfirmComponent } from './mok/registration-confirm/registration-confirm.component';
import { OwnAccountEditComponent } from './mok/account-forms/own-account-edit/own-account-edit.component';
import { LoginComponent } from './mok/login/login.component';
import { NotificationsComponent } from './shared/notifications/notifications.component';
import { SessionService } from './mok/common/session.service';
import { LocationService } from './mok/common/location.service';
import { AuthUtilService } from './mok/common/auth-util.service';
import { AuthService } from './mok/common/auth.service';
import { NotificationService } from './mok/common/notification.service';
import { MatPaginatorIntlPl } from './mok/common/mat-table-utils/mat-paginator-intl-pl';


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
    path: 'login',
    component: LoginComponent,
    data:
    {
      expectedRole: 'GUEST'
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
    AccountStatisticsComponent,
    LoginComponent,
    NotificationsComponent,
    BaseAccountFormComponent
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
    LocationService,
    RegistrationConfirmComponent,
    {provide: LocationStrategy, useClass: HashLocationStrategy},
    AuthGuard,
    AuthUtilService,
    AuthService,
    NotificationService,
    {provide: MatPaginatorIntl, useClass: MatPaginatorIntlPl}
  ]
})
export class AppModule { }
