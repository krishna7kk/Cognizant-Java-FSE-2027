import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HomeComponent } from './home.component';

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HomeComponent]
    }).compileComponents();

    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should set a message on enroll click', () => {
    component.onEnrollClick();
    expect(component.message).toBe('Enrollment opened!');
  });

  it('should log on init and destroy', () => {
    spyOn(console, 'log');
    component.ngOnInit();
    component.ngOnDestroy();
    expect(console.log).toHaveBeenCalledWith('HomeComponent initialised — courses loaded');
    expect(console.log).toHaveBeenCalledWith('HomeComponent destroyed');
  });
});
