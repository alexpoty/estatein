import {Component, inject, OnInit} from '@angular/core';
import {PropertyCardComponent} from '../../shared/property-card/property-card.component';
import {PropertyService} from '../../services/property/property.service';
import {Property} from '../../model/property';

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
  private readonly propertyService: PropertyService = inject(PropertyService);
  properties: Array<Property> = [];

  ngOnInit(): void {
    this.propertyService.getProperties().subscribe(property => {
      this.properties = property;
    })
  }
}
