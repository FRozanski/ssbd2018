import { Component, OnInit } from '@angular/core';
import { Order } from '../../model/order';
import { MatTableDataSource } from '@angular/material';
import { OrderService } from '../../common/order.service';

@Component({
  selector: 'app-own-sold-orders',
  templateUrl: './own-sold-orders.component.html',
  styleUrls: ['./own-sold-orders.component.css']
})
export class OwnSoldOrdersComponent implements OnInit {
  
  dataSource: MatTableDataSource<Order> = new MatTableDataSource<Order>();
  hiddenColumns: string[] = ['sellerLogin']


  constructor(private orderService: OrderService) { }

  ngOnInit() {
    this.orderService.getOrdersSoldByUser().subscribe((orders: Order[]) => {
      this.dataSource.data = orders;
    })
  }
}
