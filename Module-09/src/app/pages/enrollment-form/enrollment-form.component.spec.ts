import { ComponentFixture, TestBed } from '@angular/core/testing';
import { EnrollmentFormComponent } from './enrollment-form.component';

describe('EnrollmentFormComponent', () => {
  let component: EnrollmentFormComponent;
  let fixture: ComponentFixture<EnrollmentFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EnrollmentFormComponent]
    }).compileComponents();

    fixture = TestBed.createComponent(EnrollmentFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should not mark submitted for an invalid form', () => {
    component.onSubmit({ value: {}, valid: false } as any);
    expect(component.submitted).toBeFalse();
  });

  it('should mark submitted for a valid form', () => {
    component.onSubmit({ value: {}, valid: true } as any);
    expect(component.submitted).toBeTrue();
  });
});
