import { inject } from '@angular/core';
import { HttpInterceptorFn } from '@angular/common/http';
import { finalize } from 'rxjs';
import { LoadingService } from '../services/loading.service';

// Hands-On 8, Task 3, Step 91: toggles LoadingService around every HTTP request.
// `finalize` runs whether the Observable completes or errors — equivalent to try/finally —
// which makes it the correct place to hide a loading spinner.
export const loadingInterceptor: HttpInterceptorFn = (req, next) => {
  const loadingService = inject(LoadingService);
  loadingService.setLoading(true);

  return next(req).pipe(finalize(() => loadingService.setLoading(false)));
};
