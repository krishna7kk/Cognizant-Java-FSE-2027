import { Pipe, PipeTransform } from '@angular/core';

// Hands-On 3, Task 3, Step 35: transforms a numeric credit count into a readable label.
// Pure by default — only re-evaluates when the input reference changes, which is fine here
// since `credits` is a primitive number.
@Pipe({
  name: 'creditLabel'
})
export class CreditLabelPipe implements PipeTransform {
  transform(credits: number | null | undefined): string {
    if (credits === null || credits === undefined || credits === 0) {
      return 'No Credits';
    }
    return credits === 1 ? '1 Credit' : `${credits} Credits`;
  }
}
