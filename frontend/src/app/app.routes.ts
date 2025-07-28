import { Routes } from '@angular/router';

import { App } from './app';
import { UserProfileComponent } from './components/user-profile/user-profile.component';
// import { BooksComponent } from './components/books/books.component';
// import { NotFoundComponent } from './components/not-found/not-found.component';
import { ForbiddenComponent } from './components/forbidden/forbidden.component';
import { canActivateAuthRole } from './guards/auth-role.guard';

export const routes: Routes = [
  { path: '', component: App },
  {
    path: 'profile',
    component: UserProfileComponent,
    canActivate: [canActivateAuthRole],
    data: { role: 'admin' },
  },
  { path: 'forbidden', component: ForbiddenComponent },
  // { path: '**', component: NotFoundComponent },
];
