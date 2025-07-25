import { Component, input } from '@angular/core';

@Component({
  selector: 'profile',
  imports: [],
  template: ` <p>profile works! {{ name() }}</p> `,
  styles: ``,
})
export class Profile {
  name = input.required<string>();
}
