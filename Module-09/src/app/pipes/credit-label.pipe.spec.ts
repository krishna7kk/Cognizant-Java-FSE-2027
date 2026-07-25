import { CreditLabelPipe } from './credit-label.pipe';

describe('CreditLabelPipe', () => {
  const pipe = new CreditLabelPipe();

  it('creates an instance', () => {
    expect(pipe).toBeTruthy();
  });

  it('returns "No Credits" for null/0', () => {
    expect(pipe.transform(null)).toBe('No Credits');
    expect(pipe.transform(0)).toBe('No Credits');
  });

  it('returns singular label for 1', () => {
    expect(pipe.transform(1)).toBe('1 Credit');
  });

  it('returns plural label for 2+', () => {
    expect(pipe.transform(3)).toBe('3 Credits');
  });
});
