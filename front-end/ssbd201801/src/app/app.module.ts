import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {TranslateModule, TranslateLoader} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {HttpClientModule, HttpClient, HTTP_INTERCEPTORS} from '@angular/common/http';
import {RouterModule, Routes} from '@angular/router';
import {MainPageComponent} from './main-page/main-page.component';
import {
  MatTableModule,
  MatCheckboxModule,
  MatButtonModule,
  MatInputModule,
  MatCardModule,
  MatSidenavModule,
  MatSortModule,
  MatPaginatorModule,
  MatPaginatorIntl,
  MatDialogModule,
  MatSelectModule,
  MatDividerModule,
  MatListModule
} from '@angular/material';
import {ErrorHandlerService} from './shared/common/error-handler.service';
import {RequestInterceptorService} from './shared/common/request-interceptor.service';
import {ErrorsComponent} from './shared/errors/errors.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ReactiveFormsModule, FormsModule} from '@angular/forms';
import {LocationStrategy, HashLocationStrategy} from '@angular/common';
import {ConfirmDialogComponent} from './shared/confirm-dialog/confirm-dialog.component';
import {AccountListComponent} from './mok/account-list/account-list.component';
import {AccountService} from './mok/common/account.service';
import {RegisterComponent} from './mok/register/register.component';
import {BaseAccountFormComponent} from './mok/account-forms/_base-account-edit/base-account-form.component';
import {SidenavComponent} from './shared/sidenav/sidenav.component';
import {AuthGuard} from './mok/common/auth-guard';
import {AccountStatisticsComponent} from './mok/account-statistics/account-statistics.component';
import {AccountEditComponent} from './mok/account-forms/account-edit/account-edit.component';
import {ChangePasswordComponent} from './mok/change-password/change-password.component';
import {ChangeOthersPasswordComponent} from './mok/change-others-password/change-others-password.component';
import {RegistrationConfirmComponent} from './mok/registration-confirm/registration-confirm.component';
import {OwnAccountEditComponent} from './mok/account-forms/own-account-edit/own-account-edit.component';
import {LoginComponent} from './mok/login/login.component';
import {NotificationsComponent} from './shared/notifications/notifications.component';
import {SessionService} from './mok/common/session.service';
import {LocationService} from './mok/common/location.service';
import {AuthUtilService} from './mok/common/auth-util.service';
import {AuthService} from './mok/common/auth.service';
import {NotificationService} from './mok/common/notification.service';
import {MatPaginatorIntlPl} from './mok/common/mat-table-utils/mat-paginator-intl-pl';
import {Properties} from './shared/constsants';
import {RECAPTCHA_SETTINGS, RecaptchaModule, RecaptchaSettings} from 'ng-recaptcha';
import {RecaptchaFormsModule} from 'ng-recaptcha/forms';
import {ProductListComponent} from './mop/product-list/product-list.component';
import {ProductService} from './mop/common/product.service';
import {AddProductComponent} from './mop/add-product/add-product.component';
import {UnitService} from './mop/common/unit.service';
import { MyProductListComponent } from './mop/my-product-list/my-product-list.component';
import { GenericOrderListComponent } from './moz/generic-order-list/generic-order-list.component';
import { AllOrdersListComponent } from './moz/all-orders-list/all-orders-list.component';
import { OrderService } from './moz/common/order.service';
import { OwnOrdersComponent } from './moz/own-orders/own-orders.component';
import { OwnBoughtOrdersComponent } from './moz/own-orders/own-bought-orders/own-bought-orders.component';
import { OwnSoldOrdersComponent } from './moz/own-orders/own-sold-orders/own-sold-orders.component';
import { CategoryListComponent } from './mop/category-list/category-list.component';
import { CategoryService } from './mop/common/category.service';
import { ShippingMethodsListComponent } from './moz/shipping-methods-list/shipping-methods-list.component';
import {ShippingMethodService} from './moz/common/shipping-method.service';
import { AddShippingMethodComponent } from './moz/add-shipping-method/add-shipping-method.component';
import {ProductEditComponent} from './mop/product-edit/product-edit.component';

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
    path: 'products',
    canActivate: [AuthGuard],
    component: ProductListComponent,
    data:
      {
        expectedRoles: [Properties.UserRole, Properties.AdminRole, Properties.ManagerRole]
      }
  },
  {
    path: 'myProducts',
    canActivate: [AuthGuard],
    component: MyProductListComponent,
    data:
      {
        expectedRoles: [Properties.UserRole]
      }
  },
  {
    path: 'accounts',
    canActivate: [AuthGuard],
    component: AccountListComponent,
    data:
      {
        expectedRoles: [Properties.AdminRole, Properties.ManagerRole]
      }
  },
  {
    path: 'orders',
    canActivate: [AuthGuard],
    component: AllOrdersListComponent,
    data: {
      expectedRoles: [Properties.AdminRole]
    }
  },
  {
    path: 'shippingMethods',
    canActivate: [AuthGuard],
    component: ShippingMethodsListComponent,
    data: {
      expectedRoles: [Properties.ManagerRole]
    }
  },
  {
    path: 'ownOrders',
    canActivate: [AuthGuard],
    component: OwnOrdersComponent,
    data: {
      expectedRoles: [Properties.UserRole]
    }
  },
  {
    path: 'statistics',
    canActivate: [AuthGuard],
    component: AccountStatisticsComponent,
    data:
      {
        expectedRoles: [Properties.AdminRole, Properties.ManagerRole]
      }
  },
  {
    path: 'register',
    canActivate: [AuthGuard],
    component: RegisterComponent,
    data:
      {
        expectedRoles: [Properties.GuestRole, Properties.AdminRole]
      }
  },
  {
    path: 'accountEdit/:id',
    canActivate: [AuthGuard],
    component: AccountEditComponent,
    data:
      {
        expectedRoles: [Properties.AdminRole]
      }
  },
  {
    path: 'changeMyPassword',
    component: ChangePasswordComponent,
    data:
      {
        expectedRoles: [Properties.UserRole, Properties.AdminRole, Properties.ManagerRole]
      }
  },
  {
    path: 'changeOthersPassword',
    component: ChangeOthersPasswordComponent,
    data:
      {
        expectedRoles: [Properties.AdminRole]
      }
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
        expectedRoles: [Properties.UserRole, Properties.AdminRole, Properties.ManagerRole]
      },
    canActivate: [AuthGuard]
  },
  {
    path: 'login',
    component: LoginComponent,
    data:
      {
        expectedRoles: [Properties.GuestRole]
      },
    canActivate: [AuthGuard]
  },
  {
    path: 'categories',
    component: CategoryListComponent,
    data:
      {
        expectedRoles: [Properties.ManagerRole]
      },
    canActivate: [AuthGuard]
  },
  {
    path: 'addProduct',
    component: AddProductComponent,
    data:
      {
        expectedRoles: [Properties.UserRole]
      }
  },
  {
    path: 'productEdit/:id',
    canActivate: [AuthGuard],
    component: ProductEditComponent,
    data:
      {
        expectedRoles: [Properties.UserRole]
      }
  },
  {
    path: 'shippingMethods',
    component: ShippingMethodsListComponent,
    data: {
      expectedRoles: [Properties.ManagerRole]
    }
  },
  {
    path: 'addShippingMethod',
    component: AddShippingMethodComponent,
    data: {
      expectedRoles: [Properties.ManagerRole]
    },
    canActivate: [AuthGuard]
  },
  {
    path: 'error',
    component: ErrorsComponent
  },
  {
    path: '**',
    component: ErrorsComponent,
    data: {
      error: 404
    }
    // redirectTo: 'main'
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
    ConfirmDialogComponent,
    ErrorsComponent,
    BaseAccountFormComponent,
    ProductListComponent,
    MyProductListComponent,
    GenericOrderListComponent,
    AllOrdersListComponent,
    OwnOrdersComponent,
    OwnBoughtOrdersComponent,
    OwnSoldOrdersComponent,
    AddProductComponent,
    CategoryListComponent,
    ShippingMethodsListComponent,
    AddShippingMethodComponent,
    ProductEditComponent
  ],
  imports: [
    MatDialogModule,
    MatTableModule,
    MatCheckboxModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatSidenavModule,
    MatSortModule,
    MatDividerModule,
    MatListModule,
    MatPaginatorModule,
    MatSelectModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    FormsModule,
    RouterModule.forRoot(appRoutes),
    BrowserModule,
    HttpClientModule,
    RecaptchaModule.forRoot(),
    RecaptchaFormsModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: createTranslateLoader,
        deps: [HttpClient]
      }
    }),
    FormsModule
  ],
  bootstrap: [AppComponent],
  providers: [
    AccountService,
    ProductService,
    SessionService,
    UnitService,
    LocationService,
    OrderService,
    ShippingMethodService,
    RegistrationConfirmComponent,
    CategoryService,
    {provide: LocationStrategy, useClass: HashLocationStrategy},
    AuthGuard,
    AuthUtilService,
    AuthService,
    NotificationService,
    {provide: MatPaginatorIntl, useClass: MatPaginatorIntlPl},
    ErrorHandlerService,
    {provide: HTTP_INTERCEPTORS, useClass: RequestInterceptorService, multi: true},
    {
      provide: RECAPTCHA_SETTINGS,
      useValue: {
        siteKey: '6LedI14UAAAAAAvOKoEEisGXDcVdjXk3utDxQtQA',
      } as RecaptchaSettings,
    },
  ],
  entryComponents: [ConfirmDialogComponent]
})
export class AppModule {
}
