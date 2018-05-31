import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {LocationService} from '../common/location.service';
import {TranslateService} from '@ngx-translate/core';

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
