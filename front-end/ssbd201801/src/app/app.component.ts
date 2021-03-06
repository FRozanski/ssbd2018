import { Component } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

const Languages = ['pl','en'];

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {

  constructor(private translateService: TranslateService) {
    this.setupTranslationService();
  }

  private setupTranslationService() {
    this.translateService.addLangs(Languages);   
    this.translateService.setDefaultLang(this.translateService.getBrowserLang());
  }
}
