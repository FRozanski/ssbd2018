import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { DataSource } from '@angular/cdk/table';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { Observable } from 'rxjs/Observable';
import { Order } from '../model/order';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-generic-order-list',
  templateUrl: './generic-order-list.component.html',
  styleUrls: ['./generic-order-list.component.css']
})
export class GenericOrderListComponent {

  @Input()
  dataSource: MatTableDataSource<Order>;

  @Input()
  hiddenColumns: string[] = [];

  @ViewChild(MatPaginator) 
  paginator: MatPaginator;


  displayedColumns = [
    'id',
    'orderPlacedDate',
    'totalPrice',
    'buyerLogin',
    'sellerLogin',
    'status',
    'isClosed',
    'shippingMethod'
  ];

  constructor() {}

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
