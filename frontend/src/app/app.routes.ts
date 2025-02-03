import { Routes } from '@angular/router';
import {HomePageComponent} from './pages/home-page/home-page.component';
import {PropertiesPageComponent} from './pages/properties-page/properties-page.component';
import {PropertyDetailsPageComponent} from './pages/property-details-page/property-details-page.component';
import {AboutUsPageComponent} from './pages/about-us-page/about-us-page.component';
import {ServicesPageComponent} from './pages/services-page/services-page.component';

export const routes: Routes = [
  {path: '', component: HomePageComponent},
  {path: 'properties', component: PropertiesPageComponent},
  {path: 'property/:id', component: PropertyDetailsPageComponent},
  {path: 'about-us',component: AboutUsPageComponent},
  {path: 'services', component: ServicesPageComponent}
];
