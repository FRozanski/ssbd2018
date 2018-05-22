import { Component, OnInit } from '@angular/core';
// import 'rxjs/add/operator/filter';

@Component({
  selector: 'app-registration-confirm',
  templateUrl: './registration-confirm.component.html',
  styleUrls: ['./registration-confirm.component.css']
})
export class RegistrationConfirmComponent implements OnInit {
  // token: string;

  constructor (
    // private registrationConfirmService: RegistrationConfirmService, private route: ActivatedRoute
  ) { }

  ngOnInit() {
    // this.route.queryParams
    //   .filter(params => params.token)
    //   .subscribe(params => this.token = params.token);
    // this.registrationConfirmService.confirmAccount(this.token);
  }

}
