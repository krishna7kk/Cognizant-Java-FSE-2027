import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EnrollmentService } from '../../services/enrollment.service';
import { Course } from '../../models/course.model';

// Hands-On 6, Task 2, Step 66: displays enrolled courses via EnrollmentService.
@Component({
  selector: 'app-student-profile',
  imports: [CommonModule],
  templateUrl: './student-profile.component.html',
  styleUrl: './student-profile.component.css'
})
export class StudentProfileComponent implements OnInit {
  private enrollmentService = inject(EnrollmentService);

  enrolledCourses: Course[] = [];

  ngOnInit(): void {
    this.enrolledCourses = this.enrollmentService.getEnrolledCourses();
  }
}
