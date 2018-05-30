import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {LocationService} from '../common/location.service';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})
export class MainPageComponent implements OnInit {

  messageShown = '';

  constructor(private router: Router,
              private locationService: LocationService) { }

  ngOnInit() {
    this.locationService.passRouter(this.router.url);
  }

}
