import {Component} from '@angular/core';
import {BurgerMenuComponent} from '../burger-menu/burger-menu.component';
import {NgClass} from '@angular/common';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [
    BurgerMenuComponent,
    NgClass,
    RouterLink
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
})
export class HeaderComponent {
  isActive: boolean = false;

  onClick(): void {
    this.isActive = !this.isActive;
  }
}
