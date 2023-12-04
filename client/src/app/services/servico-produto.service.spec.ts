import { TestBed } from '@angular/core/testing';

import { ServicoProduto } from './servico-produto.service';

describe('ServicoProdutoService', () => {
  let service: ServicoProduto;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServicoProduto);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
