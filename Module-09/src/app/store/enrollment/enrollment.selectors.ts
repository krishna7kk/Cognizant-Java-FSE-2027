import { createFeatureSelector, createSelector } from '@ngrx/store';
import { EnrollmentState } from './enrollment.reducer';
import { selectAllCourses } from '../course/course.selectors';

// Hands-On 9, Task 2, Step 99-100.
export const selectEnrollmentState = createFeatureSelector<EnrollmentState>('enrollment');
export const selectEnrolledIds = createSelector(selectEnrollmentState, state => state.enrolledCourseIds);

// Cross-slice selector combining course + enrollment state — a powerful NgRx pattern for
// deriving joined data without duplicating state.
export const selectEnrolledCourses = createSelector(
  selectAllCourses,
  selectEnrolledIds,
  (courses, ids) => courses.filter(c => ids.includes(c.id))
);
