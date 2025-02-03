import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'app-burger-menu',
  standalone: true,
  imports: [],
  templateUrl: './burger-menu.component.html',
  styleUrl: './burger-menu.component.scss'
})
export class BurgerMenuComponent {
  @Output() click = new EventEmitter<void>();
  @Input({required:true})isActive: boolean = false;

  handleClick(): void {
    this.click.emit();
    this.isActive = !this.isActive;
  }
}
