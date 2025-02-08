import {Component, inject, Input} from '@angular/core';
import {Property} from '../../model/property';
import {Router} from '@angular/router';

@Component({
  selector: 'app-property-card',
  standalone: true,
  imports: [],
  templateUrl: './property-card.component.html',
  styleUrl: './property-card.component.scss'
})
export class PropertyCardComponent {

  private readonly router: Router = inject(Router);
  @Input({required: true}) property!: Property;

   async onClick(): Promise<void> {
     await this.router.navigate(['property', this.property.id]);
  }
}
