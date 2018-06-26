import { Component, OnInit } from '@angular/core';
import { LocationService } from '../../mok/common/location.service';

@Component({
  selector: 'app-own-orders',
  templateUrl: './own-orders.component.html',
  styleUrls: ['./own-orders.component.css']
})
export class OwnOrdersComponent implements OnInit {

  constructor(private locationService: LocationService) { }

  ngOnInit() {
    this.locationService.passRouter('LOCATION.MY_ORDERS');
  }

}
