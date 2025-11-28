import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RepuestoService } from '../repuesto/services/repuesto.service';
import { RepuestoResponse } from '../repuesto/models/repuesto.model';

interface ResultadoEOQ {
  eoq: number;           // Cantidad Económica de Pedido (Q*)
  numeroPedidos: number; // Número de pedidos por año (N)
  tiempoEntrePedidos: number; // Tiempo entre pedidos en días (T)
  costoTotal: number;    // Costo Total anual
  rop: number;           // Punto de Reorden
  stockSeguridad: number; // Stock de seguridad
  demandaLeadTime: number; // Demanda durante lead time
}

@Component({
  selector: 'app-calculadora-eoq',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './calculadora-eoq.component.html',
  styleUrl: './calculadora-eoq.component.css'
})
export class CalculadoraEoqComponent implements OnInit {
  private repuestoService = inject(RepuestoService);

  // Lista de productos
  repuestos: RepuestoResponse[] = [];
  repuestoSeleccionado: RepuestoResponse | null = null;

  // Inputs del formulario
  demandaAnual: number = 0;      // D - Demanda anual en unidades
  costoPedido: number = 0;       // S - Costo por realizar cada pedido
  costoMantener: number = 0;     // H - Costo de mantener una unidad por año
  leadTime: number = 0;          // L - Tiempo de entrega en días
  nivelServicio: number = 95;    // Nivel de servicio (%)
  desviacionDemanda: number = 0; // Desviación estándar de demanda diaria

  // Resultados
  resultado: ResultadoEOQ | null = null;
  mostrarResultados: boolean = false;

  // Valores Z para nivel de servicio
  valoresZ: { [key: number]: number } = {
    90: 1.28,
    95: 1.65,
    99: 2.33
  };

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
    if (this.repuestoSeleccionado) {
      // Pre-llenar el costo de mantener con un % del costo del producto
      this.costoMantener = this.repuestoSeleccionado.costoRepuesto * 0.25; // 25% del costo
    }
  }

  calcularEOQ(): void {
    if (!this.validarInputs()) {
      return;
    }

    const D = this.demandaAnual;
    const S = this.costoPedido;
    const H = this.costoMantener;
    const L = this.leadTime;
    const Z = this.valoresZ[this.nivelServicio] || 1.65;

    // 1. EOQ (Q*) = √(2DS/H)
    const eoq = Math.sqrt((2 * D * S) / H);

    // 2. Número de pedidos por año (N) = D/Q*
    const numeroPedidos = D / eoq;

    // 3. Tiempo entre pedidos (T) = (Q*/D) * 365 días
    const tiempoEntrePedidos = (eoq / D) * 365;

    // 4. Demanda diaria promedio
    const demandaDiaria = D / 365;

    // 5. Demanda durante Lead Time (DL)
    const demandaLeadTime = demandaDiaria * L;

    // 6. Desviación estándar durante Lead Time
    // σDL = σd * √L
    const sigmaLeadTime = this.desviacionDemanda * Math.sqrt(L);

    // 7. Stock de Seguridad (SS) = Z * σDL
    const stockSeguridad = Z * sigmaLeadTime;

    // 8. Punto de Reorden (ROP) = DL + SS
    const rop = demandaLeadTime + stockSeguridad;

    // 9. Costo Total anual = (D/Q)S + (Q/2)H + PD
    const costoOrdenar = (D / eoq) * S;
    const costoMantenerTotal = (eoq / 2) * H;
    const costoProducto = this.repuestoSeleccionado ? 
      this.repuestoSeleccionado.costoRepuesto * D : 0;
    const costoTotal = costoOrdenar + costoMantenerTotal + costoProducto;

    this.resultado = {
      eoq: Math.round(eoq),
      numeroPedidos: Math.round(numeroPedidos * 10) / 10,
      tiempoEntrePedidos: Math.round(tiempoEntrePedidos * 10) / 10,
      costoTotal: Math.round(costoTotal * 100) / 100,
      rop: Math.round(rop),
      stockSeguridad: Math.round(stockSeguridad),
      demandaLeadTime: Math.round(demandaLeadTime)
    };

    this.mostrarResultados = true;
  }

  validarInputs(): boolean {
    if (this.demandaAnual <= 0) {
      alert('La demanda anual debe ser mayor a 0');
      return false;
    }
    if (this.costoPedido <= 0) {
      alert('El costo por pedido debe ser mayor a 0');
      return false;
    }
    if (this.costoMantener <= 0) {
      alert('El costo de mantener debe ser mayor a 0');
      return false;
    }
    if (this.leadTime <= 0) {
      alert('El tiempo de entrega debe ser mayor a 0');
      return false;
    }
    return true;
  }

  limpiar(): void {
    this.repuestoSeleccionado = null;
    this.demandaAnual = 0;
    this.costoPedido = 0;
    this.costoMantener = 0;
    this.leadTime = 0;
    this.nivelServicio = 95;
    this.desviacionDemanda = 0;
    this.resultado = null;
    this.mostrarResultados = false;
  }
}
