import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LostReportsComponent } from './lost-reports.component';

describe('LostReportsComponent', () => {
  let component: LostReportsComponent;
  let fixture: ComponentFixture<LostReportsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LostReportsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LostReportsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
