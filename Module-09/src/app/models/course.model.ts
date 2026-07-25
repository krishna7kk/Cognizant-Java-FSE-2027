// Hands-On 6, Task 1, Step 59: Course data model shared across the whole portal.
export interface Course {
  id: number;
  name: string;
  code: string;
  credits: number;
  gradeStatus: 'passed' | 'failed' | 'pending';
  enrolled?: boolean;
}
