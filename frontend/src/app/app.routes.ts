import { Routes } from '@angular/router';
import {HomePageComponent} from './pages/home-page/home-page.component';
import {PropertiesPageComponent} from './pages/properties-page/properties-page.component';
import {PropertyDetailsPageComponent} from './pages/property-details-page/property-details-page.component';

export const routes: Routes = [
  {path: '', component: HomePageComponent},
  {path: 'properties', component: PropertiesPageComponent},
  {path: 'property/:id', component: PropertyDetailsPageComponent}
];
