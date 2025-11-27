import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TrazabilidadService } from './services/trazabilidad.service';
import { Trazabilidad } from './models/trazabilidad.model';

@Component({
  selector: 'app-trazabilidad',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './trazabilidad.component.html',
  styleUrls: ['./trazabilidad.component.css'],
})
export class TrazabilidadComponent implements OnInit {
  trazabilidad: Trazabilidad[] = [];
  isLoading = true;
  errorMessage = '';

  constructor(private trazabilidadService: TrazabilidadService) {}

  ngOnInit(): void {
    this.cargarTrazabilidad();
  }

  cargarTrazabilidad(): void {
    this.isLoading = true;
    this.errorMessage = '';
    this.trazabilidadService.getTrazabilidad().subscribe({
      next: (data) => {
        this.trazabilidad = data;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error al cargar trazabilidad', error);
        this.errorMessage = 'Error al cargar los datos de trazabilidad.';
        this.isLoading = false;
      },
    });
  }
}
