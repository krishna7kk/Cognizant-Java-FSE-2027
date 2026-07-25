import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CourseService } from '../../services/course.service';

// Hands-On 1 Task 2 (welcome content/stats), Hands-On 2 Task 1 & 2 (bindings + lifecycle hooks).
@Component({
  selector: 'app-home',
  imports: [FormsModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit, OnDestroy {
  private courseService = inject(CourseService);

  portalName = 'Student Course Portal';
  isPortalActive = true;
  message = '';
  searchTerm = '';
  coursesAvailable = 0;

  onEnrollClick(): void {
    this.message = 'Enrollment opened!';
  }

  // Hands-On 2, Task 2, Step 16: fires once, after inputs are set — the right place for
  // data fetching (unlike the constructor, which runs before inputs are set).
  ngOnInit(): void {
    this.coursesAvailable = this.courseService.getSeedCourses().length;
    console.log('HomeComponent initialised — courses loaded');
  }

  // Hands-On 2, Task 2, Step 17: critical for cleanup (unsubscribing, clearing timers).
  ngOnDestroy(): void {
    console.log('HomeComponent destroyed');
  }
}
