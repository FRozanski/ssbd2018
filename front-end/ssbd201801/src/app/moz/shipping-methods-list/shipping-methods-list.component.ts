import {Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {LocationService} from '../../mok/common/location.service';
import {ShippingMethodService} from '../common/shipping-method.service';
import {TranslateService} from '@ngx-translate/core';
import {ShippingMethod} from '../model/shipping-method';

@Component({
  selector: 'app-shipping-methods-list',
  templateUrl: './shipping-methods-list.component.html',
  styleUrls: ['./shipping-methods-list.component.css']
})
export class ShippingMethodsListComponent implements OnInit {

  displayedColumns = ['name', 'price', 'active'];
  dataSource;

  constructor(private locationService: LocationService,
              private shippingMethodService: ShippingMethodService,
              private translateService: TranslateService) { }

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngOnInit() {
    this.locationService.passRouter('LOCATION.SHIPPING_METHODS');
    this.shippingMethodService.getAllShippingMethods().subscribe((data) => {
      this.dataSource = new MatTableDataSource<ShippingMethod>(data);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;

      this.translateService.get('app.pagination.itemsPerPageLabel').subscribe(translation => {
        this.paginator._intl.itemsPerPageLabel = translation;
      });
      this.translateService.get('app.pagination.firstPageLabel').subscribe(translation => {
        this.paginator._intl.firstPageLabel = translation;
      });
      this.translateService.get('app.pagination.previousPageLabel').subscribe(translation => {
        this.paginator._intl.previousPageLabel = translation;
      });
      this.translateService.get('app.pagination.nextPageLabel').subscribe(translation => {
        this.paginator._intl.nextPageLabel = translation;
      });
      this.translateService.get('app.pagination.lastPageLabel').subscribe(translation => {
        this.paginator._intl.lastPageLabel = translation;
      });
    });
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim();
    filterValue = filterValue.toLowerCase();
    this.dataSource.filter = filterValue;
  }

}
