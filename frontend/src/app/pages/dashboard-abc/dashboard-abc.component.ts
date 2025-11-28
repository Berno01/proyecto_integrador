import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';

interface AbcItem {
  idRepuesto: number;
  nombreRepuesto: string;
  cantidadVendida: number;
  vecesVendido: number;
  precioSugerido: number;
  valorTotal: number;
  clasificacionPorValor: string | null;
  clasificacionPorRotacion: string | null;
}

@Component({
  selector: 'app-dashboard-abc',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="abc-page">
      <header class="abc-header">
        <div class="abc-badge">Inventario analítico</div>
        <h1 class="abc-title">Clasificación ABC del inventario</h1>
        <p class="abc-subtitle">
          Basado en valor económico de las ventas y la rotación de cada producto.
        </p>
      </header>

      <section class="abc-card">
        <div class="abc-card-header">
          <div>
            <h2>Tabla de clasificación ABC</h2>
            <p>
              Los productos se ordenan por valor total vendido y se clasifican como
              <strong>A</strong>, <strong>B</strong> o <strong>C</strong> tanto por valor como por rotación.
            </p>
          </div>
        </div>

        <!-- Loading -->
        <div *ngIf="loadingAbc" class="abc-loading">
          <div class="spinner"></div>
          <p>Cargando clasificación ABC...</p>
        </div>

        <!-- Error -->
        <div *ngIf="errorAbc" class="alert alert-error">
          {{ errorAbc }}
        </div>

        <!-- Tabla -->
        <div *ngIf="!loadingAbc && !errorAbc" class="abc-table-wrapper">
          <table class="abc-table">
            <thead>
              <tr>
                <th class="col-id">ID</th>
                <th class="col-name">Producto</th>
                <th class="col-num">Cant. vendida</th>
                <th class="col-num">Veces vendido</th>
                <th class="col-num">Precio sugerido</th>
                <th class="col-num">Valor total</th>
                <th class="col-badge">ABC por valor</th>
                <th class="col-badge">ABC por rotación</th>
              </tr>
            </thead>
            <tbody>
              <tr *ngFor="let item of abcItems">
                <td class="col-id">{{ item.idRepuesto }}</td>
                <td class="col-name">{{ item.nombreRepuesto }}</td>
                <td class="col-num">{{ item.cantidadVendida }}</td>
                <td class="col-num">{{ item.vecesVendido }}</td>
                <td class="col-num">
                  {{ item.precioSugerido | currency : 'BOB' : 'symbol-narrow' : '1.2-2' }}
                </td>
                <td class="col-num">
                  {{ item.valorTotal | currency : 'BOB' : 'symbol-narrow' : '1.2-2' }}
                </td>
                <td class="col-badge">
                  <span class="tag" [ngClass]="getClaseAbc(item.clasificacionPorValor)">
                    {{ item.clasificacionPorValor || '-' }}
                  </span>
                </td>
                <td class="col-badge">
                  <span class="tag" [ngClass]="getClaseAbc(item.clasificacionPorRotacion)">
                    {{ item.clasificacionPorRotacion || '-' }}
                  </span>
                </td>
              </tr>
            </tbody>
          </table>

          <p class="abc-footnote">
            * A: pocos productos, alto impacto. · B: impacto medio. · C: muchos productos, bajo impacto.
          </p>
        </div>
      </section>
    </div>
  `,
  styles: [
    `
      :host {
        --bg-page: #ffffff;
        --bg-subtle: #f3f4f6;
        --card-bg: #ffffff;
        --border-soft: #e5e7eb;
        --border-strong: #d1d5db;
        --accent: #140611;
        --text-main: #111827;
        --text-muted: #6b7280;
      }

      .abc-page {
        min-height: 100vh;
        padding: 2.5rem 1.5rem 3rem;
        background-color: var(--bg-page);
        font-family: system-ui, -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
        color: var(--text-main);
      }

      .abc-header {
        max-width: 1100px;
        margin: 0 auto 2rem;
        text-align: center;
      }

      .abc-badge {
        display: inline-flex;
        align-items: center;
        justify-content: center;
        padding: 0.35rem 0.9rem;
        border-radius: 999px;
        font-size: 0.75rem;
        letter-spacing: 0.14em;
        text-transform: uppercase;
        background-color: var(--bg-subtle);
        color: var(--text-muted);
        border: 1px solid var(--border-soft);
        margin-bottom: 0.75rem;
      }

      .abc-title {
        font-size: 2.1rem;
        font-weight: 600;
        letter-spacing: 0.06em;
        text-transform: uppercase;
        margin: 0;
      }

      .abc-subtitle {
        margin-top: 0.5rem;
        font-size: 0.95rem;
        color: var(--text-muted);
      }

      .abc-card {
        max-width: 1100px;
        margin: 0 auto;
        background-color: var(--card-bg);
        border-radius: 18px;
        padding: 1.75rem 1.75rem 1.5rem;
        box-shadow: 0 18px 40px rgba(15, 23, 42, 0.09);
        border: 1px solid var(--border-soft);
      }

      .abc-card-header h2 {
        margin: 0 0 0.25rem;
        font-size: 1.25rem;
      }

      .abc-card-header p {
        margin: 0;
        font-size: 0.9rem;
        color: var(--text-muted);
      }

      .abc-loading {
        text-align: center;
        padding: 2rem 1rem;
        color: var(--text-muted);
      }

      .spinner {
        width: 40px;
        height: 40px;
        border: 4px solid rgba(209, 213, 219, 0.7);
        border-top-color: var(--accent);
        border-radius: 50%;
        animation: spin 0.9s linear infinite;
        margin: 0 auto 1rem;
      }

      .abc-table-wrapper {
        margin-top: 1.5rem;
        overflow-x: auto;
      }

      .abc-table {
        width: 100%;
        border-collapse: collapse;
        font-size: 0.9rem;
      }

      .abc-table thead {
        background-color: var(--bg-subtle);
      }

      .abc-table th,
      .abc-table td {
        padding: 0.55rem 0.75rem;
        border-bottom: 1px solid var(--border-soft);
        text-align: left;
        white-space: nowrap;
      }

      .abc-table th {
        font-size: 0.75rem;
        text-transform: uppercase;
        letter-spacing: 0.11em;
        color: var(--text-muted);
      }

      .col-id {
        width: 60px;
      }

      .col-name {
        min-width: 200px;
      }

      .col-num {
        text-align: right;
      }

      .col-badge {
        text-align: center;
      }

      .tag {
        display: inline-flex;
        align-items: center;
        justify-content: center;
        min-width: 26px;
        height: 26px;
        border-radius: 999px;
        font-size: 0.78rem;
        font-weight: 600;
        border: 1px solid var(--border-soft);
        background-color: #f9fafb;
        color: var(--text-muted);
      }

      .tag-a {
        background-color: rgba(16, 185, 129, 0.12);
        color: #047857;
        border-color: rgba(16, 185, 129, 0.3);
      }

      .tag-b {
        background-color: rgba(245, 158, 11, 0.12);
        color: #92400e;
        border-color: rgba(245, 158, 11, 0.3);
      }

      .tag-c {
        background-color: rgba(239, 68, 68, 0.1);
        color: #b91c1c;
        border-color: rgba(239, 68, 68, 0.28);
      }

      .tag-empty {
        opacity: 0.6;
      }

      .abc-footnote {
        margin-top: 0.75rem;
        font-size: 0.8rem;
        color: var(--text-muted);
      }

      .alert {
        padding: 0.9rem 1rem;
        border-radius: 10px;
        margin-top: 1rem;
        font-size: 0.9rem;
      }

      .alert-error {
        background-color: #fee2e2;
        color: #b91c1c;
        border: 1px solid #fecaca;
      }

      @keyframes spin {
        to {
          transform: rotate(360deg);
        }
      }

      @media (max-width: 768px) {
        .abc-page {
          padding: 1.75rem 1rem 2.25rem;
        }

        .abc-title {
          font-size: 1.7rem;
        }

        .abc-card {
          padding: 1.5rem 1.25rem 1.3rem;
        }
      }
    `,
  ],
})
export class DashboardAbcComponent implements OnInit {
  abcItems: AbcItem[] = [];
  loadingAbc = true;
  errorAbc: string | null = null;

  private apiBase = 'http://localhost:8080/api/dashboard';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.cargarAbc();
  }

  private cargarAbc(): void {
    this.loadingAbc = true;
    this.errorAbc = null;

    this.http.get<AbcItem[]>(`${this.apiBase}/abc`).subscribe({
      next: (data) => {
        this.abcItems = data;
        this.loadingAbc = false;
      },
      error: (error) => {
        console.error('Error al cargar ABC:', error);
        this.errorAbc = 'No se pudo cargar la clasificación ABC.';
        this.loadingAbc = false;
      },
    });
  }

  getClaseAbc(letra: string | null | undefined): string {
    const value = (letra || '').toUpperCase();
    switch (value) {
      case 'A':
        return 'tag-a';
      case 'B':
        return 'tag-b';
      case 'C':
        return 'tag-c';
      default:
        return 'tag-empty';
    }
  }
}
