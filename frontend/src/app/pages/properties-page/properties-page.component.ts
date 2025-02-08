import {Component, inject, OnInit} from '@angular/core';
import {PropertyService} from '../../services/property/property.service';
import {Property} from '../../model/property';
import {Router} from '@angular/router';
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
export class PropertiesPageComponent implements OnInit{

  private readonly propertyService = inject(PropertyService);
  private readonly router = inject(Router);
  properties: Array<Property> = [];

  ngOnInit(): void {
    this.propertyService.getProperties()
      .pipe()
      .subscribe(property => {
        this.properties = property;
      })
  }

   async navigateToPropertyDetailPage(id: number | undefined): Promise<void> {
      await this.router.navigate(['/property', id])
  }
}
