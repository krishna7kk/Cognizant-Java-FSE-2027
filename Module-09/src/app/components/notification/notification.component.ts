import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AsyncPipe } from '@angular/common';
import { LoadingService } from '../../services/loading.service';
import { NotificationService } from '../../services/notification.service';

// Hands-On 6, Task 2, Step 67: NotificationService provided at COMPONENT level (see
// `providers` below), giving this component (and its children) an isolated instance
// separate from any other component that might also list NotificationService.
// Hands-On 8, Task 3, Step 91: also renders the global HTTP loading spinner.
@Component({
  selector: 'app-notification',
  imports: [CommonModule, AsyncPipe],
  providers: [NotificationService],
  template: `
    @if (loadingService.isLoading$ | async) {
      <div class="spinner">Loading…</div>
    }
  `,
  styles: [`.spinner { position: fixed; top: 0.5rem; right: 0.5rem; background: #1e293b; color: #fff; padding: 0.4rem 0.8rem; border-radius: 6px; font-size: 0.8rem; }`]
})
export class NotificationComponent {
  loadingService = inject(LoadingService);
  private notificationService = inject(NotificationService);
}
