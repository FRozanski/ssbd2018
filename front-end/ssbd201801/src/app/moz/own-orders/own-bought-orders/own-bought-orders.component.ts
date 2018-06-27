import { Component, OnInit } from '@angular/core';
import { Order } from '../../model/order';
import { MatTableDataSource } from '@angular/material';
import { OrderService } from '../../common/order.service';

@Component({
  selector: 'app-own-bought-orders',
  templateUrl: './own-bought-orders.component.html',
  styleUrls: ['./own-bought-orders.component.css']
})
export class OwnBoughtOrdersComponent implements OnInit {

  dataSource: MatTableDataSource<Order> = new MatTableDataSource<Order>();
  hiddenColumns: string[] = ['buyerLogin']

  constructor(private orderService: OrderService) { }

  ngOnInit() {
    this.orderService.getOrdersBoughtByUser().subscribe((orders: Order[]) => {
      this.dataSource.data = orders;
    })
  }

}
