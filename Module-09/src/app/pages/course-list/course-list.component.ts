import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { CourseCardComponent } from '../../components/course-card/course-card.component';
import { Course } from '../../models/course.model';
import { loadCourses } from '../../store/course/course.actions';
import { selectAllCourses, selectCoursesError, selectCoursesLoading } from '../../store/course/course.selectors';

// Hands-On 3 (structural/attribute directives, trackBy), 7 (navigation to detail, query params),
// 9 (NgRx store instead of the raw service subscription).
@Component({
  selector: 'app-course-list',
  imports: [CommonModule, FormsModule, CourseCardComponent],
  templateUrl: './course-list.component.html',
  styleUrl: './course-list.component.css'
})
export class CourseListComponent implements OnInit {
  private store = inject(Store);
  private router = inject(Router);
  private route = inject(ActivatedRoute);

  courses$: Observable<Course[]> = this.store.select(selectAllCourses);
  isLoading$: Observable<boolean> = this.store.select(selectCoursesLoading);
  error$: Observable<string | null> = this.store.select(selectCoursesError);

  searchTerm = '';
  selectedCourseId: number | null = null;

  ngOnInit(): void {
    // Hands-On 9, Task 1, Step 96: dispatch instead of calling the service directly.
    this.store.dispatch(loadCourses());

    this.searchTerm = this.route.snapshot.queryParamMap.get('search') ?? '';
  }

  // Hands-On 3, Task 1, Step 26: trackBy avoids re-rendering every card on array changes —
  // Angular only updates items whose id actually changed.
  trackByCourseId(index: number, course: Course): number {
    return course.id;
  }

  onSearchChange(term: string): void {
    this.searchTerm = term;
    this.router.navigate(['courses'], { queryParams: { search: term || null } });
  }

  // Hands-On 7, Task 1, Step 70: navigate to the detail route on card click.
  goToDetail(courseId: number): void {
    this.router.navigate(['courses', courseId]);
  }

  onEnroll(courseId: number): void {
    console.log('Enrolling in course: ' + courseId);
    this.selectedCourseId = courseId;
  }
}
