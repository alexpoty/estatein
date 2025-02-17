import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Image} from '../../model/image';
import {environment} from '../../env/environment';

@Injectable({
  providedIn: 'root'
})
export class ImageService {

  constructor(private http: HttpClient) {
  }

  getImage(propertyId: number): Observable<Image> {
    return this.http.get<Image>(environment.imageServiceUrl + '?propertyId=' + propertyId);
  }

  getPropertyImages(propertyId: number): Observable<Array<Image>> {
    return this.http.get<Array<Image>>(environment.imageServiceUrl + '/' + propertyId);
  }
}
