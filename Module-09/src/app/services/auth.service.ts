import { Injectable } from '@angular/core';

// Hands-On 7, Task 2, Step 75: hardcoded login state backing the AuthGuard.
@Injectable({ providedIn: 'root' })
export class AuthService {
  isLoggedIn = true;
}
