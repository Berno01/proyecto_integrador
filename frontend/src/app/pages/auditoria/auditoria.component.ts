import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RepuestoService } from '../repuesto/services/repuesto.service';
import { RepuestoResponse } from '../repuesto/models/repuesto.model';

interface ResultadoAuditoria {
  diferencia: number;
  tipo: 'faltante' | 'sobrante' | 'correcto';
  valorDiferencia: number;
}

@Component({
  selector: 'app-auditoria',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './auditoria.component.html',
  styleUrl: './auditoria.component.css'
})
export class AuditoriaComponent implements OnInit {
  private repuestoService = inject(RepuestoService);

  // Lista de productos
  repuestos: RepuestoResponse[] = [];
  repuestoSeleccionado: RepuestoResponse | null = null;

  // Datos de auditoría
  conteoFisico: number = 0;
  observaciones: string = '';

  // Resultado
  resultado: ResultadoAuditoria | null = null;

  // Historial de auditorías (en memoria, no persistido)
  historialAuditorias: {
    fecha: Date;
    producto: string;
    stockSistema: number;
    conteoFisico: number;
    diferencia: number;
    tipo: string;
    valorDiferencia: number;
  }[] = [];

  ngOnInit(): void {
    this.cargarRepuestos();
  }

  cargarRepuestos(): void {
    this.repuestoService.getAllRepuestos().subscribe({
      next: (data) => {
        this.repuestos = data.filter(r => r.estadoRepuesto);
      },
      error: (err) => console.error('Error cargando productos:', err)
    });
  }

  onRepuestoChange(): void {
    this.resultado = null;
    this.conteoFisico = 0;
    this.observaciones = '';
  }

  realizarAuditoria(): void {
    if (!this.repuestoSeleccionado) {
      alert('Por favor seleccione un producto');
      return;
    }

    if (this.conteoFisico < 0) {
      alert('El conteo físico no puede ser negativo');
      return;
    }

    const stockSistema = this.repuestoSeleccionado.stockActual;
    const diferencia = this.conteoFisico - stockSistema;
    
    let tipo: 'faltante' | 'sobrante' | 'correcto';
    if (diferencia < 0) {
      tipo = 'faltante';
    } else if (diferencia > 0) {
      tipo = 'sobrante';
    } else {
      tipo = 'correcto';
    }

    const valorDiferencia = Math.abs(diferencia) * this.repuestoSeleccionado.costoRepuesto;

    this.resultado = {
      diferencia,
      tipo,
      valorDiferencia
    };

    // Agregar al historial
    this.historialAuditorias.unshift({
      fecha: new Date(),
      producto: this.repuestoSeleccionado.nombreRepuesto,
      stockSistema,
      conteoFisico: this.conteoFisico,
      diferencia,
      tipo,
      valorDiferencia
    });
  }

  limpiar(): void {
    this.repuestoSeleccionado = null;
    this.conteoFisico = 0;
    this.observaciones = '';
    this.resultado = null;
  }

  getIconoTipo(tipo: string): string {
    switch (tipo) {
      case 'faltante': return '📉';
      case 'sobrante': return '📈';
      case 'correcto': return '✅';
      default: return '❓';
    }
  }

  getColorTipo(tipo: string): string {
    switch (tipo) {
      case 'faltante': return '#e74c3c';
      case 'sobrante': return '#f39c12';
      case 'correcto': return '#27ae60';
      default: return '#7f8c8d';
    }
  }

  contarFaltantes(): number {
    return this.historialAuditorias.filter(h => h.tipo === 'faltante').length;
  }

  contarSobrantes(): number {
    return this.historialAuditorias.filter(h => h.tipo === 'sobrante').length;
  }

  contarCorrectos(): number {
    return this.historialAuditorias.filter(h => h.tipo === 'correcto').length;
  }
}
