import { Component, OnInit } from '@angular/core';
import { Order } from '../../model/order';
import { MatTableDataSource } from '@angular/material';
import { OrderService } from '../../common/order.service';
import { Observable } from 'rxjs/Observable';
import { OrderStatus } from '../../model/order-status';
import { OrderAndStatus } from '../../model/order-and-status';
import { NotificationService } from '../../../mok/common/notification.service';

@Component({
  selector: 'app-own-sold-orders',
  templateUrl: './own-sold-orders.component.html',
  styleUrls: ['./own-sold-orders.component.css']
})
export class OwnSoldOrdersComponent implements OnInit {
  
  dataSource: MatTableDataSource<Order> = new MatTableDataSource<Order>();
  statusSelectOptionsSource: Observable<OrderStatus[]>;
  hiddenColumns: string[] = ['sellerLogin']

  constructor(
    private orderService: OrderService,
    private notificationService: NotificationService
  ) { }

  ngOnInit() {
    this.orderService.getOrdersSoldByUser().subscribe((orders: Order[]) => {
      this.dataSource.data = orders;
    })

    this.statusSelectOptionsSource = this.orderService.getStatusDictionary();
  }

  onStatusChange(oas: OrderAndStatus) {
    this.orderService.updateOrderStatus(oas).subscribe(() => {
      this.notificationService.displayTranslatedNotification("ORDER.STATUS_UPDATE_SUCCESS")
    }, (errorResponse) => {
      this.notificationService.displayTranslatedNotification(errorResponse.error.message)

    });
  }


}
