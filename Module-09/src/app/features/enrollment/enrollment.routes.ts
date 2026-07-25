import { Routes } from '@angular/router';
import { EnrollmentFormComponent } from '../../pages/enrollment-form/enrollment-form.component';
import { ReactiveEnrollmentFormComponent } from '../../pages/reactive-enrollment-form/reactive-enrollment-form.component';
import { unsavedChangesGuard } from '../../guards/unsaved-changes.guard';

// Hands-On 7, Task 2, Step 73: lazy-loaded feature routes for the enrollment module.
// Referenced from app.routes.ts via loadChildren, so this whole chunk (and both form
// components) is only downloaded the first time the user visits /enroll.
export const ENROLLMENT_ROUTES: Routes = [
  { path: '', component: EnrollmentFormComponent },
  {
    path: 'reactive',
    component: ReactiveEnrollmentFormComponent,
    canDeactivate: [unsavedChangesGuard]
  }
];
