# Student Course Portal — Angular (v20.0) Hands-On Exercise Book Solutions

Digital Nurture 5.0 — .NET Full Stack Engineer Track
All 10 hands-on exercises solved in a single, incrementally-built Angular application.

## What's implemented

| # | Hands-On | Where to look |
|---|----------|----------------|
| 1 | Project setup, first components | `notes.txt`, `components/header`, `pages/home` |
| 2 | Binding, lifecycle hooks, @Input/@Output | `pages/home`, `components/course-card`, `pages/course-list` |
| 3 | Directives & pipes | `directives/highlight.directive.ts`, `pipes/credit-label.pipe.ts`, `components/course-card` |
| 4 | Template-driven forms | `pages/enrollment-form` |
| 5 | Reactive forms (FormBuilder/FormArray/validators) | `pages/reactive-enrollment-form` |
| 6 | Services & DI | `services/course.service.ts`, `services/enrollment.service.ts`, `components/notification` |
| 7 | Routing, guards, lazy loading | `app.routes.ts`, `layout/courses-layout`, `guards/`, `features/enrollment` |
| 8 | HttpClient, RxJS, interceptors | `services/course.service.ts`, `interceptors/` |
| 9 | NgRx state management | `store/course`, `store/enrollment` |
| 10 | Jasmine/Karma unit tests | every `*.spec.ts` file |

## Running the app

```bash
npm install
npm run mock-api   # starts JSON Server on http://localhost:3000 (serves db.json)
npm start          # ng serve, http://localhost:4200
```

## Running tests

```bash
npm test                # Karma watch mode
npm run test:coverage   # generates coverage/ report
```

> Note: this workspace was built and `ng build` was verified in a sandboxed CI environment
> without a Chrome binary, so Karma could not execute here — but every `*.spec.ts` file
> compiles cleanly (zero TypeScript/template errors) as part of `ng build`/`ng test` bundling.
> Install Chrome/Chromium locally and `npm test` will run all suites normally.

## Notes on architecture decisions

- Angular 20 defaults to **standalone components** — there is no `AppModule`; bootstrapping
  happens via `main.ts` + `app.config.ts`.
- The reactive enrollment form and template-driven enrollment form both live under
  `features/enrollment/` and are **lazy-loaded** via `loadChildren` on the `/enroll` route.
- `/profile` and `/enroll` are protected by `authGuard` (`CanActivateFn`); the reactive form's
  `/enroll/reactive` route is additionally protected by `unsavedChangesGuard` (`CanDeactivateFn`).
- NgRx (`@ngrx/store` + `@ngrx/effects` + `@ngrx/store-devtools`) manages `course` and
  `enrollment` state; `CourseListComponent` and `CourseCardComponent` read/dispatch through it.
- HTTP interceptors are registered in this order: `authInterceptor` → `loadingInterceptor` →
  `errorHandlerInterceptor`.
