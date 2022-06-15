import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoriesmodelComponent } from './categoriesmodel.component';

describe('CategoriesmodelComponent', () => {
  let component: CategoriesmodelComponent;
  let fixture: ComponentFixture<CategoriesmodelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CategoriesmodelComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CategoriesmodelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
