import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogEditDishComponent } from './dialog-edit-dish.component';

describe('DialogEditDishComponent', () => {
  let component: DialogEditDishComponent;
  let fixture: ComponentFixture<DialogEditDishComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DialogEditDishComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogEditDishComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
