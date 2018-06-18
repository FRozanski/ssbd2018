import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Location} from '@angular/common';

@Component({
  selector: 'app-add-shipping-method',
  templateUrl: './add-shipping-method.component.html',
  styleUrls: ['./add-shipping-method.component.css']
})
export class AddShippingMethodComponent implements OnInit {

  form: FormGroup;

  wasFormSent = false;

  formValidationMessage = '';

  constructor(private location: Location) { }

  ngOnInit() {
    this.initializeForm();
  }

  sendForm() {
  }

  onReturnClick() {
    this.location.back();
  }

  isRequiredSatisfied(controlName: string) {
    return (this.form.get(controlName).errors && this.form.get(controlName).errors.required) && ((this.form.get(controlName).dirty || this.form.get(controlName).touched) || this.wasFormSent);
  }

  private initializeForm() {
    this.form = new FormGroup({
      name: new FormControl('', [
        Validators.required
      ]),
      price: new FormControl('', [
        Validators.required
      ])
    });
  }
}
