import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogAddDishComponent } from './dialog-add-dish.component';

describe('DialogAddDishComponent', () => {
  let component: DialogAddDishComponent;
  let fixture: ComponentFixture<DialogAddDishComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DialogAddDishComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogAddDishComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
