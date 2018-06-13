import {Component, OnInit, ViewChild} from '@angular/core';
import {ProductData} from '../model/product-data';
import {MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {LocationService} from '../../mok/common/location.service';
import {ProductService} from '../common/product.service';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-my-product-list',
  templateUrl: './my-product-list.component.html',
  styleUrls: ['./my-product-list.component.css']
})
export class MyProductListComponent implements OnInit {

  displayedColumns = ['name', 'description', 'price', 'qty', 'unit', 'active', 'category', 'owner'];
  dataSource;

  constructor(private locationService: LocationService,
              private productService: ProductService,
              private translateService: TranslateService) { }

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngOnInit() {
    this.locationService.passRouter('LOCATION.MY_PRODUCT_LIST_PAGE');
    this.productService.getMyProducts().subscribe((data) => {
      this.dataSource = new MatTableDataSource<ProductData>(data);
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
