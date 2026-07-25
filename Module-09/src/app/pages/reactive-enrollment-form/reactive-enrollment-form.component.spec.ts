import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveEnrollmentFormComponent, noCourseCode } from './reactive-enrollment-form.component';
import { FormControl } from '@angular/forms';

describe('ReactiveEnrollmentFormComponent', () => {
  let component: ReactiveEnrollmentFormComponent;
  let fixture: ComponentFixture<ReactiveEnrollmentFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReactiveEnrollmentFormComponent]
    }).compileComponents();

    fixture = TestBed.createComponent(ReactiveEnrollmentFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('noCourseCode validator flags XX-prefixed values', () => {
    expect(noCourseCode(new FormControl('XX101'))).toEqual({ noCourseCode: true });
    expect(noCourseCode(new FormControl('CS101'))).toBeNull();
  });

  it('addCourse/removeCourse manage the FormArray', () => {
    component.addCourse();
    expect(component.additionalCourses.length).toBe(1);
    component.removeCourse(0);
    expect(component.additionalCourses.length).toBe(0);
  });

  it('hasUnsavedChanges reflects the dirty state', () => {
    expect(component.hasUnsavedChanges()).toBeFalse();
    component.enrollForm.markAsDirty();
    expect(component.hasUnsavedChanges()).toBeTrue();
  });
});
