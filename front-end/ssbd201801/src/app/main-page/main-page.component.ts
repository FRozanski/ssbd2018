import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { LocationService } from '../mok/common/location.service';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})
export class MainPageComponent implements OnInit {

  constructor(private router: Router,
    private locationService: LocationService,
    private translateService: TranslateService) { }

  ngOnInit() {
    this.locationService.passRouter('');
  }

}
