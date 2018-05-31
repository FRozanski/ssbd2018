import {MatPaginatorIntl} from '@angular/material';
import {TranslateService} from '@ngx-translate/core';

export class MatPaginatorIntlPl extends MatPaginatorIntl {
  itemsPerPageLabel = '';
  nextPageLabel = '';
  previousPageLabel = '';
  firstPageLabel = '';
  lastPageLabel = '';

  constructor (
    public translateService: TranslateService
  ) {
    super();
    this.itemsPerPageLabel = this.translateService.instant('MAT_TABLE.ITEMS_PER_PAGE');
    this.nextPageLabel     = this.translateService.instant('MAT_TABLE.NEXT_PAGE');
    this.previousPageLabel = this.translateService.instant('MAT_TABLE.PREVIOUS_PAGE');
    this.firstPageLabel = this.translateService.instant('MAT_TABLE.FIRST_PAGE');
    this.lastPageLabel = this.translateService.instant('MAT_TABLE.LAST_PAGE');
  }

  getRangeLabel = function (page, pageSize, length) {
    if (length === 0 || pageSize === 0) {
      return '0 / ' + length;
    }
    length = Math.max(length, 0);
    const startIndex = page * pageSize;
    // If the start index exceeds the list length, do not try and fix the end index to the end.
    const endIndex = startIndex < length ?
      Math.min(startIndex + pageSize, length) :
      startIndex + pageSize;
    return startIndex + 1 + ' - ' + endIndex + ' / ' + length;
  };
}
