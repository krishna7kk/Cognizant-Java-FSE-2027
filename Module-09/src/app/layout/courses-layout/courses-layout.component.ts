import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

// Hands-On 7, Task 1, Step 72: parent layout for the nested /courses routes.
@Component({
  selector: 'app-courses-layout',
  imports: [RouterOutlet],
  template: `<router-outlet></router-outlet>`
})
export class CoursesLayoutComponent {}
