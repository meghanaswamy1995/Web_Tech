import { TestBed } from '@angular/core/testing';

import { ArtistSvcService } from './artist-svc.service';

describe('ArtistSvcService', () => {
  let service: ArtistSvcService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ArtistSvcService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
