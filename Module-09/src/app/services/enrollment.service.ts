import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { CourseService } from './course.service';
import { Course } from '../models/course.model';

// Hands-On 6, Task 2, Step 63-64: EnrollmentService injects CourseService (service-to-service DI).
@Injectable({ providedIn: 'root' })
export class EnrollmentService {
  private courseService = inject(CourseService);
  private http = inject(HttpClient);

  private enrolledCourseIds: number[] = [];

  enroll(courseId: number): void {
    if (!this.enrolledCourseIds.includes(courseId)) {
      this.enrolledCourseIds.push(courseId);
    }
  }

  unenroll(courseId: number): void {
    this.enrolledCourseIds = this.enrolledCourseIds.filter(id => id !== courseId);
  }

  isEnrolled(courseId: number): boolean {
    return this.enrolledCourseIds.includes(courseId);
  }

  getEnrolledCourseIds(): number[] {
    return this.enrolledCourseIds;
  }

  getEnrolledCourses(): Course[] {
    return this.courseService
      .getSeedCourses()
      .filter(c => this.enrolledCourseIds.includes(c.id));
  }

  // Hands-On 8, Task 2, Step 87: switchMap chains a dependent HTTP call.
  // switchMap cancels the previous inner Observable when a new courseId arrives, which prevents
  // out-of-order responses if the user rapidly selects different courses.
  getStudentsByCourse(courseId: number): Observable<any[]> {
    return this.http
      .get<number>(`http://localhost:3000/courses/${courseId}`)
      .pipe(switchMap(() => this.http.get<any[]>(`http://localhost:3000/enrollments?courseId=${courseId}`)));
  }
}
