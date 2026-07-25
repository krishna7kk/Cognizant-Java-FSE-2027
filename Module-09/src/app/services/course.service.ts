import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map, retry, tap } from 'rxjs/operators';
import { Course } from '../models/course.model';

// Hands-On 6, Task 1, Step 58: CourseService — singleton (providedIn: 'root').
// Hands-On 8, Task 1: hardcoded array replaced with real HttpClient calls against JSON Server.
const API_URL = 'http://localhost:3000/courses';

@Injectable({ providedIn: 'root' })
export class CourseService {
  private http = inject(HttpClient);

  // Fallback in-memory seed data, used only if JSON Server is not running / for initial db.json.
  private courses: Course[] = [
    { id: 1, name: 'Data Structures', code: 'CS101', credits: 4, gradeStatus: 'passed' },
    { id: 2, name: 'Operating Systems', code: 'CS102', credits: 3, gradeStatus: 'pending' },
    { id: 3, name: 'Database Systems', code: 'CS103', credits: 4, gradeStatus: 'failed' },
    { id: 4, name: 'Web Development', code: 'CS104', credits: 3, gradeStatus: 'passed' },
    { id: 5, name: 'Computer Networks', code: 'CS105', credits: 2, gradeStatus: 'pending' }
  ];

  // Step 79 + Task 2 Step 83-86: map/catchError/tap/retry pipeline.
  getCourses(): Observable<Course[]> {
    return this.http.get<Course[]>(API_URL).pipe(
      map(courses => courses.filter(c => c.credits > 0)),
      // tap is for side effects only (logging) — never mutate data inside tap; use map for transformations.
      tap(courses => console.log('Courses loaded:', courses.length)),
      retry(2),
      catchError(err => {
        console.error(err);
        return throwError(() => new Error('Failed to load courses. Please try again.'));
      })
    );
  }

  getCourseById(id: number): Observable<Course> {
    return this.http.get<Course>(`${API_URL}/${id}`);
  }

  createCourse(course: Omit<Course, 'id'>): Observable<Course> {
    return this.http.post<Course>(API_URL, course);
  }

  updateCourse(course: Course): Observable<Course> {
    return this.http.put<Course>(`${API_URL}/${course.id}`, course);
  }

  deleteCourse(id: number): Observable<void> {
    return this.http.delete<void>(`${API_URL}/${id}`);
  }

  // Kept for local/demo use without a backend running (used by seed script + tests).
  getSeedCourses(): Course[] {
    return this.courses;
  }

  addCourse(course: Course): void {
    this.courses.push(course);
  }
}
