import { Component, OnInit } from '@angular/core';
import { OrderService } from '../common/order.service';
import { MatTableDataSource } from '@angular/material';
import { Order } from '../model/order';
import { LocationService } from '../../mok/common/location.service';

@Component({
  selector: 'app-all-orders-list',
  templateUrl: './all-orders-list.component.html',
  styleUrls: ['./all-orders-list.component.css']
})
export class AllOrdersListComponent implements OnInit {

  dataSource: MatTableDataSource<Order> = new MatTableDataSource<Order>();

  constructor(private orderService: OrderService, private locationService: LocationService) { }

  ngOnInit() {
    this.locationService.passRouter('LOCATION.ALL_ORDERS');
    this.orderService.getAllOrders().subscribe((orders: Order[]) => {
      this.dataSource.data = orders;
    })
  }

}
