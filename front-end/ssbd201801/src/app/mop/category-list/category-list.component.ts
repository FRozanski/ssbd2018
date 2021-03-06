import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort } from '@angular/material';
import { CategoryData } from '../model/category-data';
import { CategoryService } from '../common/category.service';
import { LocationService } from '../../mok/common/location.service';
import { NotificationService } from '../../mok/common/notification.service';
import { TranslateService } from '@ngx-translate/core';
import {Location} from '@angular/common';

@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.css']
})
export class CategoryListComponent implements OnInit {

  @ViewChild(MatPaginator)
  paginator: MatPaginator;

  @ViewChild(MatSort)
  sort: MatSort;

  newCategoryName: string = '';

  readonly displayedColumns = [
    'categoryName', 'active'
  ];

  dataSource: MatTableDataSource<CategoryData> = new MatTableDataSource<CategoryData>();
  categoriesWithChangedActive: Set<CategoryData> = new Set<CategoryData>();
  changedCategories: Set<CategoryData> = new Set<CategoryData>();
  changedRows: Set<number> = new Set<number>();
  private successfullySentCategories: Set<string> = new Set<string>();
  private faultySentCategories: Set<string> = new Set<string>();
  private responseCounter = 0;
  private submitStatusMessage = '';

  constructor(private locationService: LocationService,
    private categoryService: CategoryService,
    private notificationService: NotificationService,
    private translateService: TranslateService,
    private location: Location
  ) { }

  ngOnInit() {
    this.locationService.passRouter('LOCATION.CATEGORY_LIST_PAGE');
    this.reloadData();

    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  reloadData() {
    this.categoryService.getAllCategories().subscribe((categories) => {
      this.dataSource.data = categories;
    });
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim();
    filterValue = filterValue.toLowerCase();
    this.dataSource.filter = filterValue;
  }

  onActiveChange(category: CategoryData, rowId: number) {
    this.onCategoryChange(category, rowId);
    this.categoriesWithChangedActive.add(category);
  }

  sumbitCategories() {
    if (this.wasAnyCategoryChanged()) {
      this.submitActiveStateOfCategories();
    }
    if (this.wasCategoryNameChanged()) {
      this.submitNewCategory();
    }

    this.reloadData();
  }

  private onCategoryChange(category: CategoryData, rowId: number) {
    this.changedCategories.add(category);
    this.changedRows.add(rowId);
  }

  private handleSubmit(message: string) {
    this.response(message);
  }

  private reinitializeStuff() {
    this.responseCounter = 0;
    this.categoriesWithChangedActive.clear();
    this.changedRows.clear();
    this.changedCategories.clear();
    this.successfullySentCategories.clear();
    this.faultySentCategories.clear();
    this.submitStatusMessage = '';
    this.newCategoryName = '';
  }

  private response(message: string) {
    this.responseCounter++;
    this.submitStatusMessage += message + ' | ';
    const numberOfChangedCategories = this.categoriesWithChangedActive.size;
    if (this.responseCounter === numberOfChangedCategories) {
      this.notificationService.displayTranslatedNotification(this.submitStatusMessage);
      this.reinitializeStuff();
    }
  }

  private wasAnyCategoryChanged() {
    return (this.changedCategories.size !== 0 || this.changedRows.size != 0);
  }

  private wasCategoryNameChanged() {
    return this.newCategoryName.length !== 0;
  }

  private submitActiveStateOfCategories() {
    this.categoriesWithChangedActive.forEach((category: CategoryData) => {
      const isDeactivated: boolean = !category.active;

      if (isDeactivated) {
        this.categoryService.deactivateCategory(category.id).subscribe(
          () => {
            const succMessage = this.translateService.instant('SUCCESS.CATEGORY_DEACTIVATED');
            this.handleSubmit(succMessage + ' ' + category.categoryName);
          },
          () => {
            const failMessage = this.translateService.instant('SUCCESS.FAILED_TO_DEACTIVATE_CATEGORY');
            this.handleSubmit(failMessage + ' ' + category.categoryName);
          });
      } else {
        this.categoryService.activateCategory(category.id).subscribe(
          () => {
            const succMessage = this.translateService.instant('SUCCESS.CATEGORY_ACTIVATED');
            this.handleSubmit(succMessage + ' ' + category.categoryName);
          },
          () => {
            const failMessage = this.translateService.instant('SUCCESS.FAILED_TO_ACTIVATE_CATEGORY');
            this.handleSubmit(failMessage + ' ' + category.categoryName);
          });
      }
    });
  }

  private submitNewCategory() {
    this.categoryService.addCategory({ categoryName: this.newCategoryName }).subscribe(() => {
      this.notificationService.displayTranslatedNotification('CATEGORY.ADD_SUCCESS');
      this.reloadData();
      this.reinitializeStuff();
    }, (errorResponse) => {
      this.notificationService.displayTranslatedNotification(errorResponse.error.message);
      this.reloadData();
      this.reinitializeStuff();
    });
  }

  onReturnClick() {
    this.location.back();
  }

  wasFormChanged(): boolean {
    return this.wasAnyCategoryChanged() || this.wasCategoryNameChanged();
  }

}
