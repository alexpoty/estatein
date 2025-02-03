import {Component, inject, OnInit} from '@angular/core';
import {PropertyService} from '../../services/property/property.service';
import {BookingService} from '../../services/booking/booking.service';
import {Property} from '../../model/property';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-property-details-page',
  standalone: true,
  imports: [],
  templateUrl: './property-details-page.component.html',
  styleUrl: './property-details-page.component.scss'
})
export class PropertyDetailsPageComponent implements OnInit{

  private readonly propertyService = inject(PropertyService);
  id: number = 0;
  property: Property = {} as Property;
  constructor(private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.id = Number(this.route.snapshot.paramMap.get('id'));
    this.propertyService.getPropertyById(this.id)
      .pipe()
      .subscribe({
        next: property => {this.property = property; },
        error: err => {console.error("Error Fetching Data ", err)}
      })
  }
}
