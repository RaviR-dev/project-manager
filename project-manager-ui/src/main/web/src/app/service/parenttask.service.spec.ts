import { TestBed } from '@angular/core/testing';
import { ParenttaskService } from './parenttask.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('ParenttaskService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [HttpClientTestingModule], 
    providers: [ParenttaskService]
  }));

  it('should be created', () => {
    const service: ParenttaskService = TestBed.get(ParenttaskService);
    expect(service).toBeTruthy();
  });
});
