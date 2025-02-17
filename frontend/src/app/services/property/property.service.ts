import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Property} from '../../model/property';
import {environment} from '../../env/environment';

@Injectable({
  providedIn: "root"
})
export class PropertyService {

  constructor(private http: HttpClient) {
  }

  getProperties(): Observable<Array<Property>> {
    return this.http.get<Array<Property>>(environment.propertyServiceUrl);
  }

  getProperty(propertyId: number): Observable<Property> {
    return this.http.get<Property>(environment.propertyServiceUrl + '/' + propertyId);
  }
}
