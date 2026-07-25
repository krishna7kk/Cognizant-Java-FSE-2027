import { CanDeactivateFn } from '@angular/router';

// Hands-On 7, Task 2, Step 77: prevents accidental loss of unsaved reactive-form data.
export interface CanComponentDeactivate {
  hasUnsavedChanges: () => boolean;
}

export const unsavedChangesGuard: CanDeactivateFn<CanComponentDeactivate> = component => {
  if (component.hasUnsavedChanges()) {
    return window.confirm('You have unsaved changes. Leave?');
  }
  return true;
};
