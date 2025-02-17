import {Component, inject, OnInit} from '@angular/core';
import {ReactiveFormsModule} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import {ImageService} from '../../services/image/image.service';
import {PropertyService} from '../../services/property/property.service';
import {BookingService} from '../../services/booking/booking.service';
import {Image} from '../../model/image';
import {Property} from '../../model/property';

@Component({
  selector: 'app-property-details-page',
  standalone: true,
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './property-details-page.component.html',
  styleUrl: './property-details-page.component.scss'
})
export class PropertyDetailsPageComponent implements OnInit{

  activatedRoute: ActivatedRoute = inject(ActivatedRoute);
  private propertyId: number = this.activatedRoute.snapshot.params['id'];
  private readonly imageService: ImageService = inject(ImageService);
  private readonly propertyService: PropertyService = inject(PropertyService);
  private readonly bookingService: BookingService = inject(BookingService);
  images: Array<Image> = [];
  property: Property = {} as Property;

  ngOnInit(): void {
    this.propertyService.getProperty(this.propertyId).subscribe(property => {
      this.property = property;
    })
    this.imageService.getPropertyImages(this.propertyId).subscribe(images => {
      this.images = images;
    })
  }
}
