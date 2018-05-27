import {MatPaginatorIntl} from '@angular/material';
import {TranslateService} from '@ngx-translate/core';

export class MatPaginatorIntlPl extends MatPaginatorIntl {

  constructor (
    private translateService: TranslateService
  ) {
    super();
  }

  itemsPerPageLabel = this.translateService.instant('MAT_TABLE.ITEMS_PER_PAGE');
  nextPageLabel     = this.translateService.instant('MAT_TABLE.NEXT_PAGE');
  previousPageLabel = this.translateService.instant('MAT_TABLE.PREVIOUS_PAGE');
}
