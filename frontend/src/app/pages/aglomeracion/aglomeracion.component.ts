import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

interface PedidoSugerido {
  producto: string;
  cantidadSugerida: number;
  unidad: string;
  prioridad: 'alta' | 'media' | 'baja';
  stockActual: number;
  rop: number;
  costoUnitario: number;
}

interface GrupoProveedor {
  proveedor: string;
  contacto: string;
  telefono: string;
  email: string;
  pedidos: PedidoSugerido[];
  valorTotal: number;
}

@Component({
  selector: 'app-aglomeracion',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './aglomeracion.component.html',
  styleUrl: './aglomeracion.component.css'
})
export class AglomeracionComponent {
  
  // Modal state
  mostrarModal = false;
  modalTipo: 'orden' | 'detalle' = 'orden';
  proveedorSeleccionado: GrupoProveedor | null = null;
  ordenGenerada = false;
  
  // Datos hardcoded basados en el sistema real de vinos y singanis
  gruposProveedores: GrupoProveedor[] = [
    {
      proveedor: 'El Portillo - Viñedos',
      contacto: 'Roberto Mendoza',
      telefono: '+591 4 6642345',
      email: 'ventas@elportillo.bo',
      valorTotal: 5075,
      pedidos: [
        { producto: 'Uva Moscatel Alejandría', cantidadSugerida: 230, unidad: 'kg', prioridad: 'alta', stockActual: 50, rop: 100, costoUnitario: 5 },
        { producto: 'Uva Tannat', cantidadSugerida: 180, unidad: 'kg', prioridad: 'alta', stockActual: 30, rop: 80, costoUnitario: 5 },
        { producto: 'Uva Malbec', cantidadSugerida: 200, unidad: 'kg', prioridad: 'media', stockActual: 60, rop: 90, costoUnitario: 5 },
        { producto: 'Uva Cabernet Sauvignon', cantidadSugerida: 215, unidad: 'kg', prioridad: 'media', stockActual: 45, rop: 85, costoUnitario: 5 },
        { producto: 'Uva Syrah', cantidadSugerida: 190, unidad: 'kg', prioridad: 'alta', stockActual: 25, rop: 75, costoUnitario: 5 }
      ]
    },
    {
      proveedor: 'Edprint - Etiquetas',
      contacto: 'Carolina Vásquez',
      telefono: '+591 4 6123456',
      email: 'pedidos@edprint.com.bo',
      valorTotal: 1125,
      pedidos: [
        { producto: 'Etiquetas Tannat Reserva', cantidadSugerida: 500, unidad: 'unidades', prioridad: 'media', stockActual: 75, rop: 200, costoUnitario: 0.75 },
        { producto: 'Etiquetas Syrah Reserva', cantidadSugerida: 500, unidad: 'unidades', prioridad: 'media', stockActual: 80, rop: 200, costoUnitario: 0.75 },
        { producto: 'Etiquetas Cofradía', cantidadSugerida: 1000, unidad: 'unidades', prioridad: 'baja', stockActual: 150, rop: 300, costoUnitario: 0.50 }
      ]
    },
    {
      proveedor: 'Vidrios Bolivia',
      contacto: 'Mario Fernández',
      telefono: '+591 4 6654321',
      email: 'ventas@vidriosbolivia.com',
      valorTotal: 1280,
      pedidos: [
        { producto: 'Botellas 750ml Verde', cantidadSugerida: 200, unidad: 'unidades', prioridad: 'alta', stockActual: 120, rop: 150, costoUnitario: 4 },
        { producto: 'Botellas 750ml Transparente', cantidadSugerida: 120, unidad: 'unidades', prioridad: 'media', stockActual: 80, rop: 100, costoUnitario: 4 }
      ]
    },
    {
      proveedor: 'Corchos Portugal Import',
      contacto: 'Luis Almeida',
      telefono: '+591 2 2889900',
      email: 'importaciones@corchospt.bo',
      valorTotal: 850,
      pedidos: [
        { producto: 'Corchos Naturales Premium', cantidadSugerida: 500, unidad: 'unidades', prioridad: 'alta', stockActual: 170, rop: 300, costoUnitario: 1 },
        { producto: 'Corchos Sintéticos', cantidadSugerida: 350, unidad: 'unidades', prioridad: 'baja', stockActual: 200, rop: 150, costoUnitario: 1 }
      ]
    },
    {
      proveedor: 'Empaques Tarija',
      contacto: 'Sandra Quiroga',
      telefono: '+591 4 6112233',
      email: 'ventas@empaquesta.bo',
      valorTotal: 485,
      pedidos: [
        { producto: 'Cajas de Vino x6', cantidadSugerida: 50, unidad: 'unidades', prioridad: 'media', stockActual: 47, rop: 50, costoUnitario: 5 },
        { producto: 'Cajas de Vino x12', cantidadSugerida: 47, unidad: 'unidades', prioridad: 'alta', stockActual: 20, rop: 40, costoUnitario: 5 }
      ]
    }
  ];

  getIconoPrioridad(prioridad: string): string {
    switch (prioridad) {
      case 'alta': return '🔴';
      case 'media': return '🟡';
      case 'baja': return '🟢';
      default: return '⚪';
    }
  }

  getTotalProductos(): number {
    return this.gruposProveedores.reduce((acc, g) => acc + g.pedidos.length, 0);
  }

  getTotalValor(): number {
    return this.gruposProveedores.reduce((acc, g) => acc + g.valorTotal, 0);
  }

  getPedidosAlta(): number {
    return this.gruposProveedores.reduce((acc, g) => 
      acc + g.pedidos.filter(p => p.prioridad === 'alta').length, 0);
  }

  // Métodos para el modal
  abrirModalOrden(grupo: GrupoProveedor): void {
    this.proveedorSeleccionado = grupo;
    this.modalTipo = 'orden';
    this.ordenGenerada = false;
    this.mostrarModal = true;
  }

  abrirModalDetalle(grupo: GrupoProveedor): void {
    this.proveedorSeleccionado = grupo;
    this.modalTipo = 'detalle';
    this.mostrarModal = true;
  }

  cerrarModal(): void {
    this.mostrarModal = false;
    this.proveedorSeleccionado = null;
    this.ordenGenerada = false;
  }

  generarOrden(): void {
    this.ordenGenerada = true;
    // Simular envío de orden
    setTimeout(() => {
      // Se podría agregar lógica real aquí
    }, 500);
  }

  getFechaActual(): string {
    return new Date().toLocaleDateString('es-BO', { 
      year: 'numeric', 
      month: 'long', 
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  }

  getNumeroOrden(): string {
    return 'OC-' + new Date().getFullYear() + '-' + String(Math.floor(Math.random() * 9000) + 1000);
  }

  calcularSubtotal(pedido: PedidoSugerido): number {
    return pedido.cantidadSugerida * pedido.costoUnitario;
  }

  contarPrioridadAlta(grupo: GrupoProveedor): number {
    return grupo.pedidos.filter(p => p.prioridad === 'alta').length;
  }
}
