import {Component, OnInit, ViewChild} from '@angular/core';
import {MatDialog, MatDialogRef, MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {LocationService} from '../../mok/common/location.service';
import {ProductService} from '../common/product.service';
import {ProductData} from '../model/product-data';
import {TranslateService} from '@ngx-translate/core';
import {ShippingMethod} from '../../moz/model/shipping-method';
import {ShippingMethodService} from '../../moz/common/shipping-method.service';
import {OrderData} from '../model/order-data';
import {ConfirmDialogComponent} from '../../shared/confirm-dialog/confirm-dialog.component';
import {NotificationService} from '../../mok/common/notification.service';
import {OrderService} from '../../moz/common/order.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  displayedColumns = ['name', 'price', 'qty', 'unit', 'category', 'shippment', 'qtyToBuy', 'buy'];
  dataSource;

  selectedMethod: ShippingMethod = {};
  orderData: OrderData = {};
  availableShipments: ShippingMethod[] = [];
  dialogRef: MatDialogRef<ConfirmDialogComponent>;

  constructor(private locationService: LocationService,
              private productService: ProductService,
              private orderService: OrderService,
              private shippingMethodService: ShippingMethodService,
              public dialog: MatDialog,
              private translateService: TranslateService,
              private notificationService: NotificationService) {
  }

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngOnInit() {
    this.locationService.passRouter('LOCATION.PRODUCT_LIST_PAGE');
    this.productService.getAllActiveProductWithActiveCategory().subscribe((products) => {
      this.dataSource = new MatTableDataSource<ProductData>(products);
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

    this.shippingMethodService.getAllShippingMethods().subscribe((data: ShippingMethod[]) => {
      this.availableShipments = data;
    });
    this.selectedMethod = this.availableShipments[0];
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim();
    filterValue = filterValue.toLowerCase();
    this.dataSource.filter = filterValue;
  }

  onShippingMethodChange(event) {
    this.orderData.shippingId = event.value;
  }

  onQtyToBuyChange(product: ProductData, event) {
    this.orderData.productId = product.id;
    this.orderData.qty = event.target.value;
  }

  onBuyProductClick(product: ProductData) {
    this.dialogRef = this.dialog.open(ConfirmDialogComponent, {disableClose: true});
    this.dialogRef.componentInstance.text = this.translateService.instant('PRODUCT.BUY_CONFIRM');

    this.dialogRef.afterClosed().subscribe((userAccepted: boolean) => {
      if (userAccepted) {
        this.orderData.productId = product.id;
        this.orderService.makeOrder(this.orderData).subscribe((response) => {
          this.notificationService.displayTranslatedNotification('PRODUCT.BUY_NOTE');
        }, (error) => {
          this.notificationService.displayTranslatedNotification(error.error.message);
        });
      }
    });
  }
}
