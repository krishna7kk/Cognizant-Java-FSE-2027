import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute, convertToParamMap } from '@angular/router';
import { CourseDetailComponent } from './course-detail.component';

describe('CourseDetailComponent', () => {
  let fixture: ComponentFixture<CourseDetailComponent>;
  let component: CourseDetailComponent;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CourseDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { snapshot: { paramMap: convertToParamMap({ id: '1' }) } }
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(CourseDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create and load the matching course', () => {
    expect(component).toBeTruthy();
    expect(component.course?.id).toBe(1);
  });
});
