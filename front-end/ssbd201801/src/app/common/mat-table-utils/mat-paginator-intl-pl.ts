import {MatPaginatorIntl} from '@angular/material';
import {TranslateService} from '@ngx-translate/core';

export class MatPaginatorIntlPl extends MatPaginatorIntl {
  itemsPerPageLabel = '';
  nextPageLabel = '';
  previousPageLabel = '';

  constructor (
    private translateService: TranslateService
  ) {
    super();
    this.itemsPerPageLabel = this.translateService.instant('MAT_TABLE.ITEMS_PER_PAGE');
    this.nextPageLabel     = this.translateService.instant('MAT_TABLE.NEXT_PAGE');
    this.previousPageLabel = this.translateService.instant('MAT_TABLE.PREVIOUS_PAGE');
  }
}
