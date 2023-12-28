import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginaFinalizadoComponent } from './pagina-finalizado.component';

describe('PaginaFinalizadoComponent', () => {
  let component: PaginaFinalizadoComponent;
  let fixture: ComponentFixture<PaginaFinalizadoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PaginaFinalizadoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PaginaFinalizadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
