import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Booking} from '../../model/booking';
import {environment} from '../../env/environment';

@Injectable({
  providedIn: 'root'
})
export class BookingService {

  constructor(private  http: HttpClient) {
  }

  createBooking(booking: Booking): void {
    this.http.post<Booking>(environment.bookingServiceUrl, booking);
  }
}
