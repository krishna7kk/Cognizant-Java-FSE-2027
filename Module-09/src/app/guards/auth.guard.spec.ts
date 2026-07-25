import { TestBed } from '@angular/core/testing';
import { provideRouter, Router } from '@angular/router';
import { authGuard } from './auth.guard';
import { AuthService } from '../services/auth.service';

describe('authGuard', () => {
  it('allows navigation when logged in', () => {
    TestBed.configureTestingModule({ providers: [provideRouter([])] });
    const result = TestBed.runInInjectionContext(() => authGuard({} as any, {} as any));
    expect(result).toBeTrue();
  });

  it('redirects home when not logged in', () => {
    TestBed.configureTestingModule({ providers: [provideRouter([])] });
    const authService = TestBed.inject(AuthService);
    authService.isLoggedIn = false;
    const router = TestBed.inject(Router);
    spyOn(router, 'navigate');

    const result = TestBed.runInInjectionContext(() => authGuard({} as any, {} as any));

    expect(result).toBeFalse();
    expect(router.navigate).toHaveBeenCalledWith(['/']);
  });
});
