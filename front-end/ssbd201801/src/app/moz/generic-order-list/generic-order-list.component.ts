import { Component, OnInit, Input, ViewChild, EventEmitter, Output } from '@angular/core';
import { DataSource } from '@angular/cdk/table';
import { MatPaginator, MatTableDataSource, MatSort } from '@angular/material';
import { Observable } from 'rxjs/Observable';
import { Order } from '../model/order';
import { TranslateService } from '@ngx-translate/core';
import { OrderStatus } from '../model/order-status';
import { OrderService } from '../common/order.service';
import { OrderAndStatus } from '../model/order-and-status';

@Component({
  selector: 'app-generic-order-list',
  templateUrl: './generic-order-list.component.html',
  styleUrls: ['./generic-order-list.component.css']
})
export class GenericOrderListComponent implements OnInit{

  @Input()
  dataSource: MatTableDataSource<Order>;

  @Input()
  statusSelectOptionsSource: Observable<any>;

  @Input()
  hiddenColumns: string[] = [];

  @Input()
  isStatusEditable: boolean = false;

  @Input()
  udpateData: EventEmitter<any> = new EventEmitter<any>();

  @Output()
  statusChange: EventEmitter<OrderAndStatus> = new EventEmitter<OrderAndStatus>();

  @ViewChild(MatPaginator)
  paginator: MatPaginator;

  @ViewChild(MatSort)
  sort: MatSort;

  displayedColumns = [
    'id',
    'orderPlacedDate',
    'totalPrice',
    'buyerLogin',
    'sellerLogin',
    'status',
    'shippingMethod'
  ];

  statusSelectOptions: OrderStatus[];

  constructor() {}

  ngOnInit() {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;

    if (this.isStatusEditable) {
      if (this.statusSelectOptionsSource) {
        this.statusSelectOptionsSource.subscribe((data: OrderStatus[]) => {
          this.statusSelectOptions = data;
        });
      } else {
        throw new Error("You have to provide statusSelectOptionsSource via @Input()!");
      }
    }

  }

  onStatusChange(element, event) {
    if(this.isStatusEditable) {

      let orderData: OrderAndStatus = {
        orderId: element.id,
        statusId: event.value
      }

      this.statusChange.emit(orderData);
    } else {
      throw new Error("statusChange event is accessible only when @Input() isStatusEditable == true.");
    }

  }

  getDisplayedColumns() {
    return this.displayedColumns
      .filter(columnName => this.hiddenColumns.indexOf(columnName) === -1);
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim();
    filterValue = filterValue.toLowerCase();
    this.dataSource.filter = filterValue;
  }

}
