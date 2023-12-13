import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginaFinalizarComponent } from './pagina-finalizar.component';

describe('PaginaFinalizarComponent', () => {
  let component: PaginaFinalizarComponent;
  let fixture: ComponentFixture<PaginaFinalizarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PaginaFinalizarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PaginaFinalizarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
