import { Component, OnInit } from '@angular/core';
import {MessageService} from '../common/message.service';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})
export class MainPageComponent implements OnInit {

  messageShown = '';

  constructor(private messageService: MessageService) { }

  ngOnInit() {
    this.messageService.currentMessage.subscribe(message => this.messageShown = message);
  }

}
