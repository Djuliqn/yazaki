import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogDeleteDishComponent } from './dialog-delete-dish.component';

describe('DialogDeleteDishComponent', () => {
  let component: DialogDeleteDishComponent;
  let fixture: ComponentFixture<DialogDeleteDishComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DialogDeleteDishComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogDeleteDishComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
