import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AccountService } from '../common/account.service';
import { Credentials } from '../model/credentials';
import { AuthService } from '../common/auth.service';
import { NotificationService } from '../common/notification.service';
import { SessionService } from '../common/session.service';
import { Router } from '@angular/router';
import {TranslateService} from '@ngx-translate/core';
import {LocationService} from '../common/location.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form: FormGroup;
  wasFormSent: boolean;

  constructor(
    private authService: AuthService,
    private notificationService: NotificationService,
    private sessionService: SessionService,
    private router: Router,
    private translateService: TranslateService,
    private locationService: LocationService
  ) { }

  ngOnInit() {
    this.locationService.passRouter(
      this.translateService.instant('LOCATION.YOUR_LOCATION') + ': ' +
      this.translateService.instant('LOCATION.LOGIN_PAGE'));
    this.form = new FormGroup(
      {
        username: new FormControl("", [Validators.required]),
        password: new FormControl("", [Validators.required])
      }
    )
  }

  onSubmit() {
    let creds: Credentials = <Credentials>this.form.value;
    this.authService.login(creds).subscribe((data) => {
      this.notificationService.displayNotification(this.translateService.instant('LOGIN.SUCCESS'));
      this.router.navigate['/main'];
    }, (data) => {
      this.notificationService.displayNotification(this.translateService.instant('LOGIN.FAILURE'));
    });
  }

  isRequiredSatisfied(controlName: string) {
    return (this.form.get(controlName).errors && this.form.get(controlName).errors.required) && ((this.form.get(controlName).dirty || this.form.get(controlName).touched) || this.wasFormSent);
  }


}
