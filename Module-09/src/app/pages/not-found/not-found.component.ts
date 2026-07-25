import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

// Hands-On 7, Task 1, Step 68: wildcard ** route target. Must always be the last route
// in app.routes.ts since Angular matches routes in order.
@Component({
  selector: 'app-not-found',
  imports: [RouterLink],
  templateUrl: './not-found.component.html',
  styleUrl: './not-found.component.css'
})
export class NotFoundComponent {}
