import {Component} from '@angular/core';
import {ReactiveFormsModule} from '@angular/forms';

@Component({
  selector: 'app-property-details-page',
  standalone: true,
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './property-details-page.component.html',
  styleUrl: './property-details-page.component.scss'
})
export class PropertyDetailsPageComponent {
}
