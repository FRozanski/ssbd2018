import {Component, OnInit, ViewChild} from '@angular/core';
import {ProductData} from '../model/product-data';
import {MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {LocationService} from '../../mok/common/location.service';
import {ProductService} from '../common/product.service';
import {TranslateService} from '@ngx-translate/core';
import { Location } from '@angular/common';
import {NotificationService} from '../../mok/common/notification.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-my-product-list',
  templateUrl: './my-product-list.component.html',
  styleUrls: ['./my-product-list.component.css']
})
export class MyProductListComponent implements OnInit {

  displayedColumns = ['name', 'price', 'qty', 'unit', 'active', 'category', 'edit', 'deleteProduct'];

  dataSource;
  myProductListWithChangedActive: Set<ProductData> = new Set<ProductData>();
  changedProductMethod: Set<ProductData> = new Set<ProductData>();
  changedRows: Set<number> = new Set<number>();
  private successfullySentProductMethod: Set<string> = new Set<string>();
  private faultySentProductMethod: Set<string> = new Set<string>();

  private responseCounter = 0;
  private submitStatusMessage = '';

  constructor(private locationService: LocationService,
              private productService: ProductService,
              private translateService: TranslateService,
              private location: Location,
              private router: Router,
              private notificationService: NotificationService) { }

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngOnInit() {
    this.locationService.passRouter('LOCATION.MY_PRODUCT_LIST_PAGE');
    this.updateProducts();
  }

  onDeleteProductClick(element: ProductData) {
    console.log(element.id);
    this.productService.deleteProduct(element.id).subscribe((response) => {
      this.updateProducts();
      this.notificationService.displayTranslatedNotification('PRODUCT.DEL_RESPONSE');
    }, (error) => {
      this.notificationService.displayTranslatedNotification(error.response.message);
    });
  }

  updateProducts() {
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

  onActiveChange(product: ProductData, rowId: number) {
    this.onProductMethodChange(product, rowId);
    this.myProductListWithChangedActive.add(product);
  }

  onEditClick(product: ProductData) {
    this.router.navigate(['/productEdit/' + product.id]);
  }

  onReturnClick() {
    this.location.back();
  }

  private onProductMethodChange(product: ProductData, rowId: number) {
    this.changedProductMethod.add(product);
    this.changedRows.add(rowId);
  }

  submitProductMethod() {
    this.submitActive();
  }

  private handleSubmit(message: string) {
    this.registerResponse(message);
  }

  private reinitializeStuff() {
    this.responseCounter = 0;
    this.myProductListWithChangedActive.clear();
    this.changedRows.clear();
    this.changedProductMethod.clear();
    this.successfullySentProductMethod.clear();
    this.faultySentProductMethod.clear();
    this.submitStatusMessage = '';
  }

  private registerResponse(message: string) {
    this.responseCounter++;

    this.submitStatusMessage += message + ' | ';

    const numberOfChangedShippingMethods =
      this.myProductListWithChangedActive.size;

    if (this.responseCounter === numberOfChangedShippingMethods) {
      this.notificationService.displayTranslatedNotification(this.submitStatusMessage);
      this.reinitializeStuff();
    }
  }

  wasAnyProductMethodChanged() {
    return (this.changedProductMethod.size !== 0 || this.changedRows.size !== 0);
  }

  private submitActive() {
    this.myProductListWithChangedActive.forEach((product: ProductData) => {
      const isDeactivated: boolean = !product.active;

      if (isDeactivated) {
        this.productService.deactivateProduct(product.id).subscribe(
          () => {
            const succMessage = this.translateService.instant('SUCCESS.PRODUCT_DEACTIVATED');
            this.handleSubmit(succMessage + ' ' + product.name);
          },
          () => {
            const failMessage = this.translateService.instant('SUCCESS.FAILED_TO_DEACTIVATE_PRODUCT');
            this.handleSubmit(failMessage + ' ' + product.name);
          });
      } else {
        this.productService.activateProduct(product.id).subscribe(
          () => {
            const succMessage = this.translateService.instant('SUCCESS.PRODUCT_ACTIVATED');
            this.handleSubmit(succMessage + ' ' + product.name);
          },
          () => {
            const failMessage = this.translateService.instant('SUCCESS.FAILED_TO_ACTIVATE_PRODUCT');
            this.handleSubmit(failMessage + ' ' + product.name);
          });
      }
    });
  }
}
