import { unsavedChangesGuard } from './unsaved-changes.guard';

describe('unsavedChangesGuard', () => {
  it('allows navigation when there are no unsaved changes', () => {
    const component = { hasUnsavedChanges: () => false };
    const result = unsavedChangesGuard(component as any, {} as any, {} as any, {} as any);
    expect(result).toBeTrue();
  });

  it('confirms with the user when there are unsaved changes', () => {
    spyOn(window, 'confirm').and.returnValue(true);
    const component = { hasUnsavedChanges: () => true };
    const result = unsavedChangesGuard(component as any, {} as any, {} as any, {} as any);
    expect(window.confirm).toHaveBeenCalled();
    expect(result).toBeTrue();
  });
});
