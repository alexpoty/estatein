import {Component, inject, OnInit} from '@angular/core';
import {PropertyService} from '../../services/property/property.service';
import {BookingService} from '../../services/booking/booking.service';
import {Property} from '../../model/property';
import {ActivatedRoute} from '@angular/router';
import {FormBuilder, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {Booking} from '../../model/booking';

@Component({
  selector: 'app-property-details-page',
  standalone: true,
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './property-details-page.component.html',
  styleUrl: './property-details-page.component.scss'
})
export class PropertyDetailsPageComponent implements OnInit {

  private readonly propertyService = inject(PropertyService);
  private readonly bookingService = inject(BookingService);
  createBookingForm: FormGroup;
  id: number = 0;
  property: Property = {} as Property;

  constructor(private route: ActivatedRoute, private fb: FormBuilder) {
    this.createBookingForm = this.fb.group({
      date: [new Date().toISOString().split('T')[0]],
      phone: [''],
      name: [''],
      surname: ['']
    });
  }

  ngOnInit() {
    this.id = Number(this.route.snapshot.paramMap.get('id'));
    this.propertyService.getPropertyById(this.id)
      .pipe()
      .subscribe({
        next: property => {
          this.property = property;
        },
        error: err => {
          console.error("Error Fetching Data ", err)
        }
      })
  }

  onSubmit(): void {
    if (this.createBookingForm.valid) {
      const booking: Booking = {
        date: this.getDate()?.value,
        phone: this.getPhone()?.value,
        name: this.getName()?.value,
        surname: this.getSurname()?.value,
        propertyId: this.property.id
      };
      this.bookingService.bookProperty(booking).subscribe(() => {
        this.createBookingForm.reset();
      })
    } else {
      console.log("This Form is not Valid")
      this.createBookingForm.reset();
    }
  }

  getDate() {
    return this.createBookingForm.get('date');
  }

  getPhone() {
    return this.createBookingForm.get('phone');
  }

  getName() {
    return this.createBookingForm.get('name');
  }

  getSurname() {
    return this.createBookingForm.get('surname');
  }
}
