import {Component, OnInit} from '@angular/core';
import {ProductService} from '../common/product.service';
import {CategoryService} from '../common/category.service';
import {UnitService} from '../common/unit.service';
import {LocationService} from '../../mok/common/location.service';
import {FormControl, FormGroup, FormGroupDirective, NgForm, Validators} from '@angular/forms';
import {Location} from '@angular/common';
import {CategoryData} from '../model/category-data';
import {UnitData} from '../model/unit-data';
import {Router} from '@angular/router';
import {TranslateService} from '@ngx-translate/core';
import {NewProductData} from '../model/new-product-data';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {

  form: FormGroup;

  wasFormSent = false;

  formValidationMessage = '';

  categories: CategoryData[];
  units: UnitData[];
  selectedCategoryId: number;
  selectedUnitId: number;

  constructor(private productService: ProductService,
              private categoryService: CategoryService,
              private unitService: UnitService,
              private locationService: LocationService,
              private location: Location,
              private router: Router,
              private translateService: TranslateService) {
  }

  ngOnInit() {
    this.locationService.passRouter('LOCATION.ADD_PRODUCT');

    this.initializeForm();

    this.categoryService.getAllCategories().subscribe((categories) => {
      this.categories = categories.map(c => Object.assign({}, c));
    });

    this.unitService.getAllUnits().subscribe((units) => {
      this.units = units.map(u => Object.assign({}, u));
    });
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

  isRequiredSatisfied(controlName: string) {
    return (this.form.get(controlName).errors &&
      this.form.get(controlName).errors.required) &&
      ((this.form.get(controlName).dirty || this.form.get(controlName).touched) || this.wasFormSent);
  }

  onReturnClick() {
    this.location.back();
  }

  sendForm() {
    this.wasFormSent = true;
    const product: NewProductData = {};

    if (this.form.valid) {
      product.name = this.form.value.name;
      product.description = this.form.value.description;
      product.price = this.form.value.price;
      product.qty = this.form.value.qty;
      product.categoryId = this.selectedCategoryId;
      product.unitId = this.selectedUnitId;
      this.productService.addProduct(product).subscribe(() => {
        alert(this.translateService.instant('SUCCESS.ADD_PRODUCT'));
        this.router.navigate(['/main']);
      }, (errorResponse) => {
        console.log(product.name);
        console.log(product.description);
        console.log(product.price);
        console.log(product.qty);
        console.log(product.categoryId);
        console.log(product.unitId);
        this.formValidationMessage = this.translateService.instant(errorResponse.error.message);
      });
    }
  }

}
