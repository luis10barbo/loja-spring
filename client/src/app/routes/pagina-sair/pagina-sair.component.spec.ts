import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginaSairComponent } from './pagina-sair.component';

describe('PaginaSairComponent', () => {
  let component: PaginaSairComponent;
  let fixture: ComponentFixture<PaginaSairComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PaginaSairComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PaginaSairComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
