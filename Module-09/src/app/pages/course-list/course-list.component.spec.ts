import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { MockStore, provideMockStore } from '@ngrx/store/testing';
import { CourseListComponent } from './course-list.component';

describe('CourseListComponent', () => {
  let component: CourseListComponent;
  let fixture: ComponentFixture<CourseListComponent>;
  let store: MockStore;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CourseListComponent],
      providers: [
        provideRouter([]),
        provideMockStore({
          initialState: {
            course: { courses: [], loading: false, error: null },
            enrollment: { enrolledCourseIds: [] }
          }
        })
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(CourseListComponent);
    component = fixture.componentInstance;
    store = TestBed.inject(MockStore);
    fixture.detectChanges();
  });

  // Hands-On 10, Task 2, Step 109-110: NgRx-connected component test using MockStore.
  it('should show the loading indicator when the store state is loading', () => {
    store.setState({
      course: { courses: [], loading: true, error: null },
      enrollment: { enrolledCourseIds: [] }
    });
    fixture.detectChanges();

    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.textContent).toContain('Loading courses...');
  });

  it('should render course cards from the initial mock state', () => {
    store.setState({
      course: {
        courses: [{ id: 1, name: 'Data Structures', code: 'CS101', credits: 4, gradeStatus: 'passed' }],
        loading: false,
        error: null
      },
      enrollment: { enrolledCourseIds: [] }
    });
    fixture.detectChanges();

    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('app-course-card')).toBeTruthy();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('trackByCourseId should return the course id', () => {
    const id = component.trackByCourseId(0, { id: 7 } as any);
    expect(id).toBe(7);
  });

  it('onEnroll should set selectedCourseId', () => {
    component.onEnroll(4);
    expect(component.selectedCourseId).toBe(4);
  });
});
