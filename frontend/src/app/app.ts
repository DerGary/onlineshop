import { Component, signal } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Profile } from './profile/profile';
import { MenuComponent } from './components/menu/menu.component';

@Component({
  selector: 'app-root',
  imports: [MenuComponent, RouterModule],
  template: `
    <app-menu></app-menu>
    <main>
      <router-outlet></router-outlet>
    </main>
  `,
  styles: [``],
})
export class App {
  protected readonly title = signal('onlineshop');
}
