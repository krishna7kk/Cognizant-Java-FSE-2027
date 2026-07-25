import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { CourseListComponent } from './pages/course-list/course-list.component';
import { CourseDetailComponent } from './pages/course-detail/course-detail.component';
import { StudentProfileComponent } from './pages/student-profile/student-profile.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { CoursesLayoutComponent } from './layout/courses-layout/courses-layout.component';
import { authGuard } from './guards/auth.guard';

// Hands-On 7, Task 1 & 2: routes, nested routes under /courses, guards, and lazy loading.
// The ** wildcard route (NotFoundComponent) MUST stay last — Angular matches routes in order.
export const routes: Routes = [
  { path: '', component: HomeComponent },
  {
    path: 'courses',
    component: CoursesLayoutComponent,
    children: [
      { path: '', component: CourseListComponent },
      { path: ':id', component: CourseDetailComponent }
    ]
  },
  { path: 'profile', component: StudentProfileComponent, canActivate: [authGuard] },
  {
    path: 'enroll',
    canActivate: [authGuard],
    loadChildren: () => import('./features/enrollment/enrollment.routes').then(m => m.ENROLLMENT_ROUTES)
  },
  { path: '**', component: NotFoundComponent }
];
