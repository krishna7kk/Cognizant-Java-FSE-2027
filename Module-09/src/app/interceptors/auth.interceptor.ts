import { HttpInterceptorFn } from '@angular/common/http';

// Hands-On 8, Task 3, Step 88: adds a mock bearer token to every outgoing request.
export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const cloned = req.clone({
    setHeaders: { Authorization: 'Bearer mock-token-12345' }
  });
  return next(cloned);
};
