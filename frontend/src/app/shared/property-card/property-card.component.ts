import {Component, inject, Input, OnInit} from '@angular/core';
import {ImageService} from '../../services/image/image.service';
import {Property} from '../../model/property';
import {Router} from '@angular/router';

@Component({
  selector: 'app-property-card',
  standalone: true,
  imports: [],
  templateUrl: './property-card.component.html',
  styleUrl: './property-card.component.scss'
})
export class PropertyCardComponent implements OnInit{

  private readonly imageService: ImageService = inject(ImageService);
  private readonly router: Router = inject(Router);
  @Input({required: true}) property!: Property;
  imageUrl: string = '';

  ngOnInit(): void {
    this.imageService.getImage(this.property.id).subscribe(image => {
      this.imageUrl = image.imageUrl;
    })
  }

   async onClick(): Promise<void> {
     await this.router.navigate(['property', this.property.id]);
  }
}
