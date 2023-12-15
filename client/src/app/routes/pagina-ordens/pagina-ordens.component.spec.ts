import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginaOrdensComponent } from './pagina-ordens.component';

describe('PaginaOrdensComponent', () => {
  let component: PaginaOrdensComponent;
  let fixture: ComponentFixture<PaginaOrdensComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PaginaOrdensComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PaginaOrdensComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
