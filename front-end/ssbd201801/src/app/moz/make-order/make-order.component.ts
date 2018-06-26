import {Component, OnInit, ViewChild} from '@angular/core';
import {MatDialog, MatDialogRef, MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {LocationService} from '../../mok/common/location.service';
import {ProductService} from '../common/product.service';
import {ProductData} from '../model/product-data';
import {TranslateService} from '@ngx-translate/core';
import {ShippingMethod} from '../model/shipping-method';
import {ShippingMethodService} from '../common/shipping-method.service';
import {OrderData} from '../model/order-data';
import {ConfirmDialogComponent} from '../../shared/confirm-dialog/confirm-dialog.component';
import {NotificationService} from '../../mok/common/notification.service';
import {OrderService} from '../common/order.service';

@Component({
  selector: 'app-make-order',
  templateUrl: './make-order.component.html',
  styleUrls: ['./make-order.component.css'],
  providers: [ProductService]
})
export class MakeOrderComponent implements OnInit {

  displayedColumns = ['name', 'price', 'qty', 'unit', 'category', 'shippment', 'qtyToBuy', 'buy'];
  dataSource;

  orderData: Map<number, OrderData> = new Map<number, OrderData>();
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
    this.locationService.passRouter('LOCATION.MAKE_ORDER');
    this.updateProducts();
  }

  updateProducts() {
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
      this.availableShipments = data.filter( el => el.active.valueOf() === true);
    });
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim();
    filterValue = filterValue.toLowerCase();
    this.dataSource.filter = filterValue;
  }

  onShippingMethodChange(event, product: ProductData) {
    if (this.orderData.has(product.id)) {
      const od: OrderData = this.orderData.get(product.id);
      od.shippingId = event.value;
      this.orderData.set(product.id, od);
      console.log('zmiana liczby', od, this.orderData);
    } else {
      this.orderData.set(product.id, {
        productId: product.id,
        shippingId: event.value
      });
    }
  }

  onQtyToBuyChange(product: ProductData, event) {
    if (this.orderData.has(product.id)) {
      const od: OrderData = this.orderData.get(product.id);
      od.qty = event.target.value;
      this.orderData.set(product.id, od);
      console.log('zmiana liczby', od, this.orderData);
    } else {
      this.orderData.set(product.id, {
        productId: product.id,
        qty: event.target.value
      });
    }
  }

  isBuyDisabledForProduct(productId: number) {
    console.log('przycisk aktywacja', productId);
    if (this.orderData.has(productId)) {
      console.log('przycisk aktywacja=?', !(this.orderData.get(productId).qty && this.orderData.get(productId).shippingId));
      return !(this.orderData.get(productId).qty && this.orderData.get(productId).shippingId);
    } else {
      console.log('przycisk aktywacja = prawda');
      return true;
    }
  }

  onBuyProductClick(product: ProductData) {
    this.dialogRef = this.dialog.open(ConfirmDialogComponent, {disableClose: true});
    this.dialogRef.componentInstance.text = this.translateService.instant('PRODUCT.BUY_CONFIRM');

    this.dialogRef.afterClosed().subscribe((userAccepted: boolean) => {
      if (userAccepted) {
        const orderData: OrderData = this.orderData.get(product.id);
        this.orderService.makeOrder(orderData).subscribe((response) => {
          this.notificationService.displayTranslatedNotification('PRODUCT.BUY_NOTE');
          this.orderData.clear();
          this.updateProducts();
        }, (error) => {
          this.notificationService.displayTranslatedNotification(error.error.message);
        });
      }
    });
  }
}
