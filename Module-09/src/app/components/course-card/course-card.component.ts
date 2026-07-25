import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Store } from '@ngrx/store';
import { Course } from '../../models/course.model';
import { EnrollmentService } from '../../services/enrollment.service';
import { CreditLabelPipe } from '../../pipes/credit-label.pipe';
import { HighlightDirective } from '../../directives/highlight.directive';
import { enrollInCourse, unenrollFromCourse } from '../../store/enrollment/enrollment.actions';
import { selectEnrolledIds } from '../../store/enrollment/enrollment.selectors';

// Hands-On 2 (Input/Output/lifecycle), 3 (directives/pipes/ngClass/ngStyle),
// 6 (EnrollmentService), 9 (NgRx enroll dispatch) all converge on this single component.
@Component({
  selector: 'app-course-card',
  imports: [CommonModule, CreditLabelPipe, HighlightDirective],
  templateUrl: './course-card.component.html',
  styleUrl: './course-card.component.css'
})
export class CourseCardComponent implements OnChanges {
  private enrollmentService = inject(EnrollmentService);
  private store = inject(Store);

  @Input() course: Course | null = null;
  @Output() enrollRequested = new EventEmitter<number>();

  isExpanded = false;
  enrolledIds$ = this.store.select(selectEnrolledIds);

  // Hands-On 2, Task 2, Step 18: log previous/current course value whenever the input changes.
  ngOnChanges(changes: SimpleChanges): void {
    if (changes['course']) {
      console.log(
        'course changed — previous:',
        changes['course'].previousValue,
        'current:',
        changes['course'].currentValue
      );
    }
  }

  onEnrollClick(): void {
    if (!this.course) return;
    this.enrollRequested.emit(this.course.id);
    this.enrollmentService.enroll(this.course.id);
    this.store.dispatch(enrollInCourse({ courseId: this.course.id }));
  }

  onUnenrollClick(): void {
    if (!this.course) return;
    this.enrollmentService.unenroll(this.course.id);
    this.store.dispatch(unenrollFromCourse({ courseId: this.course.id }));
  }

  isEnrolledLocally(): boolean {
    return this.course ? this.enrollmentService.isEnrolled(this.course.id) : false;
  }

  toggleExpanded(): void {
    this.isExpanded = !this.isExpanded;
  }

  // Hands-On 3, Task 2, Step 32: getter keeps the template free of inline object literals,
  // making the ngClass binding easier to read and reuse.
  get cardClasses() {
    return {
      'card--enrolled': this.isEnrolledLocally(),
      'card--full': (this.course?.credits ?? 0) >= 4,
      expanded: this.isExpanded
    };
  }

  get borderColor(): string {
    switch (this.course?.gradeStatus) {
      case 'passed':
        return 'green';
      case 'failed':
        return 'red';
      default:
        return 'grey';
    }
  }
}
