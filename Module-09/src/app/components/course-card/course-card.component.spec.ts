import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { provideMockStore, MockStore } from '@ngrx/store/testing';
import { CourseCardComponent } from './course-card.component';
import { Course } from '../../models/course.model';

// Hands-On 10, Task 1: component tests for rendering, @Input, @Output and ngOnChanges.
describe('CourseCardComponent', () => {
  let component: CourseCardComponent;
  let fixture: ComponentFixture<CourseCardComponent>;

  const mockCourse: Course = {
    id: 1,
    name: 'Data Structures',
    code: 'CS101',
    credits: 4,
    gradeStatus: 'passed'
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CourseCardComponent],
      providers: [
        provideMockStore({
          initialState: {
            course: { courses: [], loading: false, error: null },
            enrollment: { enrolledCourseIds: [] }
          }
        })
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(CourseCardComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should render the course name from @Input', () => {
    component.course = mockCourse;
    fixture.detectChanges();
    const h3 = fixture.debugElement.query(By.css('h3'));
    expect(h3.nativeElement.textContent).toContain('Data Structures');
  });

  it('should emit enrollRequested with the course id on click', () => {
    component.course = mockCourse;
    fixture.detectChanges();
    spyOn(component.enrollRequested, 'emit');

    const button = fixture.debugElement.query(By.css('.actions button'));
    button.nativeElement.click();
    fixture.detectChanges();

    expect(component.enrollRequested.emit).toHaveBeenCalledWith(1);
  });

  it('should log previous/current values in ngOnChanges', () => {
    spyOn(console, 'log');
    component.ngOnChanges({
      course: {
        previousValue: null,
        currentValue: mockCourse,
        firstChange: true,
        isFirstChange: () => true
      }
    });
    expect(console.log).toHaveBeenCalled();
  });

  it('should compute cardClasses based on state', () => {
    component.course = mockCourse;
    expect(component.cardClasses['card--full']).toBeTrue();
  });

  it('should toggle isExpanded', () => {
    expect(component.isExpanded).toBeFalse();
    component.toggleExpanded();
    expect(component.isExpanded).toBeTrue();
  });
});
