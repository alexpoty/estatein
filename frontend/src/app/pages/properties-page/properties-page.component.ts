import {Component} from '@angular/core';
import {PropertyCardComponent} from '../../shared/property-card/property-card.component';

@Component({
  selector: 'app-properties-page',
  standalone: true,
  imports: [
    PropertyCardComponent
  ],
  templateUrl: './properties-page.component.html',
  styleUrl: './properties-page.component.scss'
})
export class PropertiesPageComponent {

}
