import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HobbylistComponent } from './hobbylist.component';

describe('HobbylistComponent', () => {
  let component: HobbylistComponent;
  let fixture: ComponentFixture<HobbylistComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HobbylistComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HobbylistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
