import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  AbstractControl,
  FormArray,
  FormBuilder,
  FormControl,
  ReactiveFormsModule,
  ValidationErrors,
  Validators
} from '@angular/forms';
import { Observable, of } from 'rxjs';
import { CanComponentDeactivate } from '../../guards/unsaved-changes.guard';

// Hands-On 5: reactive form rebuild of the enrollment request, with a custom sync validator,
// an async validator, and a FormArray for dynamic "additional courses" controls.
// Hands-On 7, Task 2, Step 77: implements CanComponentDeactivate for the unsavedChangesGuard.

// Step 53: rejects course codes that start with the disallowed 'XX' prefix.
export function noCourseCode(control: AbstractControl): ValidationErrors | null {
  const value = String(control.value ?? '');
  return value.startsWith('XX') ? { noCourseCode: true } : null;
}

// Step 55: simulated async validator — flags any email containing 'test@' as already taken.
export function simulateEmailCheck(control: AbstractControl): Observable<ValidationErrors | null> {
  return new Observable(subscriber => {
    const timer = setTimeout(() => {
      const taken = String(control.value ?? '').includes('test@');
      subscriber.next(taken ? { emailTaken: true } : null);
      subscriber.complete();
    }, 800);
    return () => clearTimeout(timer);
  });
}

@Component({
  selector: 'app-reactive-enrollment-form',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './reactive-enrollment-form.component.html',
  styleUrl: './reactive-enrollment-form.component.css'
})
export class ReactiveEnrollmentFormComponent implements OnInit, CanComponentDeactivate {
  private fb = inject(FormBuilder);

  enrollForm!: ReturnType<FormBuilder['group']>;
  submitted = false;

  ngOnInit(): void {
    this.enrollForm = this.fb.group({
      studentName: ['', [Validators.required, Validators.minLength(3)]],
      studentEmail: this.fb.control('', [Validators.required, Validators.email], [simulateEmailCheck]),
      courseId: [null, [Validators.required, noCourseCode]],
      preferredSemester: ['Odd', Validators.required],
      agreeToTerms: [false, Validators.requiredTrue],
      additionalCourses: this.fb.array([])
    });
  }

  // Step 57: typed getter avoids repeated `as FormArray` casts scattered through the template.
  get additionalCourses(): FormArray {
    return this.enrollForm.get('additionalCourses') as FormArray;
  }

  addCourse(): void {
    this.additionalCourses.push(new FormControl('', Validators.required));
  }

  removeCourse(index: number): void {
    this.additionalCourses.removeAt(index);
  }

  onSubmit(): void {
    // .value excludes disabled controls; .getRawValue() includes every control regardless
    // of disabled state — useful when disabled fields still need to be submitted.
    console.log(this.enrollForm.value);
    console.log(this.enrollForm.getRawValue());
    if (this.enrollForm.valid) {
      this.submitted = true;
    }
  }

  hasUnsavedChanges(): boolean {
    return this.enrollForm.dirty && !this.submitted;
  }
}
