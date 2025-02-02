import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Property} from '../../model/property';

@Injectable({
  providedIn: "root"
})
export class PropertyService {

  constructor(private http: HttpClient) {
  }

  getProperties(): Observable<Array<Property>> {
    return this.http.get<Array<Property>>('http://localhost:9000/api/');
  }

  getPropertyById(id: number): Observable<Property> {
    return this.http.get<Property>('http://localhost:9000/api/' + id);
  }
}
