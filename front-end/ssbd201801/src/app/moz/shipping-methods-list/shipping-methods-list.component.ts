import {Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {LocationService} from '../../mok/common/location.service';
import {ShippingMethodService} from '../common/shipping-method.service';
import {TranslateService} from '@ngx-translate/core';
import {ShippingMethod} from '../model/shipping-method';
import {NotificationService} from '../../mok/common/notification.service';

@Component({
  selector: 'app-shipping-methods-list',
  templateUrl: './shipping-methods-list.component.html',
  styleUrls: ['./shipping-methods-list.component.css']
})
export class ShippingMethodsListComponent implements OnInit {

  displayedColumns = ['name', 'price', 'active'];
  dataSource;
  shippingMethodsWithChangedActive: Set<ShippingMethod> = new Set<ShippingMethod>();
  changedShippingMethods: Set<ShippingMethod> = new Set<ShippingMethod>();
  changedRows: Set<number> = new Set<number>();
  private successfullySentShippingMethods: Set<string> = new Set<string>();
  private faultlySentShippingMethods: Set<string> = new Set<string>();

  private responseCounter = 0;
  private submitStatusMessage = '';

  constructor(private locationService: LocationService,
              private shippingMethodService: ShippingMethodService,
              private translateService: TranslateService,
              private notificationService: NotificationService) { }

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

  onActiveChange(account: ShippingMethod, rowId: number) {
    this.onShippingMethodChange(account, rowId);
    this.shippingMethodsWithChangedActive.add(account);
  }

  private onShippingMethodChange(account: ShippingMethod, rowId: number) {
    this.changedShippingMethods.add(account);
    this.changedRows.add(rowId);
  }

  submitShippingMethods() {
    this.submitActive();
  }

  private handleSubmit(message: string) {
    this.registerResponse(message);
  }

  private reinitializeStuff() {
    this.responseCounter = 0;
    this.shippingMethodsWithChangedActive.clear();
    this.changedRows.clear();
    this.changedShippingMethods.clear();
    this.successfullySentShippingMethods.clear();
    this.faultlySentShippingMethods.clear();
    this.submitStatusMessage = '';
  }

  private registerResponse(message: string) {
    this.responseCounter++;

    this.submitStatusMessage += message + ' | ';

    const numberOfChangedShippingMethods =
      this.shippingMethodsWithChangedActive.size;

    if (this.responseCounter === numberOfChangedShippingMethods) {
      this.notificationService.displayTranslatedNotification(this.submitStatusMessage);
      this.reinitializeStuff();
    }
  }

  wasAnyShippingMEthodChanged() {
    return (this.changedShippingMethods.size !== 0 || this.changedRows.size !== 0);
  }

  private submitActive() {
    this.shippingMethodsWithChangedActive.forEach((method: ShippingMethod) => {
      const isDeactivated: boolean = !method.active;

      if (isDeactivated) {
        this.shippingMethodService.deactivateShippingMethod(method.id).subscribe(
          () => {
            const succMessage = this.translateService.instant('SUCCESS.METHOD_DEACTIVATED');
            this.handleSubmit(succMessage + ' ' + method.name);
          },
          () => {
            const failMessage = this.translateService.instant('SUCCESS.FAILED_TO_DEACTIVATE_METHOD');
            this.handleSubmit(failMessage + ' ' + method.name);
          });
      } else {
        this.shippingMethodService.activateShippingMethod(method.id).subscribe(
          () => {
            const succMessage = this.translateService.instant('SUCCESS.METHOD_ACTIVATED');
            this.handleSubmit(succMessage + ' ' + method.name);
          },
          () => {
            const failMessage = this.translateService.instant('SUCCESS.FAILED_TO_ACTIVATE_METHOD');
            this.handleSubmit(failMessage + ' ' + method.name);
          });
      }
    });
  }
}
