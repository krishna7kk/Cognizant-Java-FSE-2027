import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { CourseService } from '../../services/course.service';
import { Course } from '../../models/course.model';

// Hands-On 7, Task 1, Step 68-69: reads the :id route parameter and loads the matching course.
@Component({
  selector: 'app-course-detail',
  imports: [CommonModule],
  templateUrl: './course-detail.component.html',
  styleUrl: './course-detail.component.css'
})
export class CourseDetailComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private courseService = inject(CourseService);

  course: Course | undefined;
  courseId: string | null = null;

  ngOnInit(): void {
    // snapshot.paramMap is fine here since this component does not navigate to itself
    // with a different id while already active.
    this.courseId = this.route.snapshot.paramMap.get('id');
    const id = Number(this.courseId);
    this.course = this.courseService.getSeedCourses().find(c => c.id === id);
  }
}
