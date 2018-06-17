import { Component, OnInit } from '@angular/core';
import {ProductService} from '../common/product.service';
import {CategoryService} from '../common/category.service';
import {UnitService} from '../common/unit.service';
import {LocationService} from '../../mok/common/location.service';
import {FormControl, FormGroup, FormGroupDirective, NgForm, Validators} from '@angular/forms';
import {Location} from '@angular/common';
import {ErrorStateMatcher} from '@angular/material';
import {CategoryData} from '../model/category-data';
import {UnitData} from '../model/unit-data';

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

  constructor(private productService: ProductService,
              private categoryService: CategoryService,
              private unitService: UnitService,
              private locationService: LocationService,
              private location: Location) { }

  ngOnInit() {
    this.locationService.passRouter('LOCATION.ADD_PRODUCT');

    this.initializeForm();

    this.categoryService.getAllCategories().subscribe((categories) => {
      this.categories = categories.map(c => Object.assign({}, c));
      console.log('categories[0] = ' + categories[0].categoryName);
    });

    this.unitService.getAllUnits().subscribe((units) => {
      this.units = units.map(u => Object.assign({}, u));
      console.log('units[0] = ' + units[0].unitName);
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

  }

}
