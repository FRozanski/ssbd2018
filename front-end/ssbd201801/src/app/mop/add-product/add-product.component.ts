import { Component, OnInit } from '@angular/core';
import {ProductService} from '../common/product.service';
import {CategoryService} from '../common/category.service';
import {UnitService} from '../common/unit.service';
import {LocationService} from '../../mok/common/location.service';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {

  constructor(private productService: ProductService,
              private categoryService: CategoryService,
              private unitService: UnitService,
              private locationService: LocationService) { }

  ngOnInit() {
    this.locationService.passRouter('LOCATION.ADD_PRODUCT');
  }

}
