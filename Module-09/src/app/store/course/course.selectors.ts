import { createFeatureSelector, createSelector } from '@ngrx/store';
import { CourseState } from './course.reducer';

// Hands-On 9, Task 1, Step 95: memoised selectors — they only recompute when their
// input selectors' output changes.
export const selectCourseState = createFeatureSelector<CourseState>('course');

export const selectAllCourses = createSelector(selectCourseState, state => state.courses);
export const selectCoursesLoading = createSelector(selectCourseState, state => state.loading);
export const selectCoursesError = createSelector(selectCourseState, state => state.error);
