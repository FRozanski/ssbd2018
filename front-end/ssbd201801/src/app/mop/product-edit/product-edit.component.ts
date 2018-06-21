import {Component, OnInit, ChangeDetectorRef, OnChanges, ElementRef, ViewChild, Output, EventEmitter, Input} from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import {ProductService} from '../common/product.service';
import {EditProductData} from '../model/edit-product-data';
import { HttpResponse } from '@angular/common/http';
import { TranslateService } from '@ngx-translate/core';
import { Location } from '@angular/common';
import { Router, ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import {NotificationService} from '../../mok/common/notification.service';
import {LocationService} from '../../mok/common/location.service';
import {NewProductData} from '../model/new-product-data';
import {ConfirmDialogComponent} from '../../shared/confirm-dialog/confirm-dialog.component';
import {MatDialog, MatDialogRef} from '@angular/material';

@Component({
  selector: 'app-product-edit',
  templateUrl: './product-edit.component.html',
  styleUrls: ['./product-edit.component.css']
})
export class ProductEditComponent implements OnInit {

  form: FormGroup;
  productId: number;
  version: number;
  wasFormSent = false;

  dialogRef: MatDialogRef<ConfirmDialogComponent>;

  @Input()
  formValidationMessage = '';

  @Input()
  productSource: Observable<EditProductData>;

  @Output('onFormSend')
  onFormSendEmitter: EventEmitter<EditProductData> = new EventEmitter<EditProductData>();

  @Output('onCancelClick')
  onCancelClickEmitter: EventEmitter<any> = new EventEmitter<any>();

  constructor(public dialog: MatDialog,
              private productService: ProductService,
              private locationService: LocationService,
              private location: Location,
              private router: Router,
              private activatedRoute: ActivatedRoute,
              private translateService: TranslateService) {
  }

  ngOnInit() {
    this.productId = this.activatedRoute.snapshot.params["id"];
    this.initializeForm();
    this.loadProductData();
  }

  private initializeForm() {
    this.form = new FormGroup({
      name: new FormControl('', [
        Validators.required
      ]),
      category: new FormControl('', [
        Validators.required
      ]),
      description: new FormControl('', [
        Validators.required
      ]),
      price: new FormControl('', [
        Validators.required
      ]),
      qty: new FormControl('', [
        Validators.required
      ]),
      unit: new FormControl('', [
        Validators.required
      ]),
    });
  }

  sendForm() {
    this.wasFormSent = true;
    const product: EditProductData = {};

    if (this.form.valid) {

      this.dialogRef = this.dialog.open(ConfirmDialogComponent, {
        disableClose: false
      });

      this.dialogRef.afterClosed().subscribe(result => {
        if (result) {
          product.id = this.productId;
          product.version = this.version;
          product.name = this.form.value.name;
          product.description = this.form.value.description;
          product.price = this.form.value.price;
          product.qty = this.form.value.qty;
          this.productService.updateProduct(product).subscribe(() => {
            alert(this.translateService.instant('SUCCESS.EDIT_PRODUCT'));
            this.router.navigate(['/main']);
          }, (errorResponse) => {
            this.formValidationMessage = this.translateService.instant(errorResponse.error.message);
          });
        }
        this.dialogRef = null;
      });
    }
  }

  loadProductData() {
    this.productSource = this.productService.getProductToEdit(this.productId);
    this.productSource.subscribe((product) => {

      this.form.setValue({
        'name': product.name,
        'category': product.categoryName,
        'description': product.description,
        'price': product.price,
        'qty': product.qty,
        'unit': product.unitName,
      });

      this.productId = product.id;
      this.version = product.version;
    });
  }

  isRequiredSatisfied(controlName: string) {
    return (
      this.form.get(controlName).errors &&
      this.form.get(controlName).errors.required) &&
      ((
          this.form.get(controlName).dirty ||
          this.form.get(controlName).touched) ||
        this.wasFormSent
      );
  }

  onReturnClick() {
    this.location.back();
  }

}
