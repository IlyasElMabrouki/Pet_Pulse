import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplyFindingComponent } from './apply-finding.component';

describe('ApplyFindingComponent', () => {
  let component: ApplyFindingComponent;
  let fixture: ComponentFixture<ApplyFindingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ApplyFindingComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ApplyFindingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
