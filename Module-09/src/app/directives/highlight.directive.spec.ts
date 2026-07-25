import { HighlightDirective } from './highlight.directive';
import { ElementRef } from '@angular/core';

describe('HighlightDirective', () => {
  it('should create an instance', () => {
    const el = new ElementRef(document.createElement('div'));
    const directive = new HighlightDirective();
    (directive as any).el = el;
    expect(directive).toBeTruthy();
  });
});
