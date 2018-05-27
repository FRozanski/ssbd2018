import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AccountService } from '../common/account.service';
import { Credentials } from '../model/credentials';
import { AuthService } from '../common/auth.service';
import { NotificationService } from '../common/notification.service';
import { SessionService } from '../common/session.service';
import { Router } from '@angular/router';

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
    private router: Router
  ) { }

  ngOnInit() {
    this.form = new FormGroup(
      {
        username: new FormControl("", [Validators.required]),
        password: new FormControl("", [Validators.required])
      }
    )
  }

  onSubmit() {
    let creds: Credentials = <Credentials>this.form.value;  
    console.log("creds: ", creds);
     
    this.authService.login(creds).subscribe((data) => {
      this.notificationService.displayNotification("Udało się zalogować!");
      this.router.navigate['/main'];
    }, (data) => {
      this.notificationService.displayNotification("Nie udało się zalogować!");
    });

  }

  isRequiredSatisfied(controlName: string) {
    return (this.form.get(controlName).errors && this.form.get(controlName).errors.required) && ((this.form.get(controlName).dirty || this.form.get(controlName).touched) || this.wasFormSent);
  }


}