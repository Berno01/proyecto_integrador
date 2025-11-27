import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Trazabilidad } from '../models/trazabilidad.model';

@Injectable({
  providedIn: 'root',
})
export class TrazabilidadService {
  private apiUrl = 'http://localhost:8080/api/trazabilidad';

  constructor(private http: HttpClient) {}

  getTrazabilidad(): Observable<Trazabilidad[]> {
    return this.http.get<Trazabilidad[]>(this.apiUrl);
  }
}
