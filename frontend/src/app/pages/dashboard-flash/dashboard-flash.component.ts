import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

interface DashboardFlash {
  total_ganancia: number;
  ganancia_socia: number;
}

interface AbcRow {
  idRepuesto: number;
  nombreProducto: string;
  cantidadVendida: number;
  vecesVendido: number;
  precioSugerido: number;
  valorTotal: number;
  abcValor: 'A' | 'B' | 'C' | '-';
  abcRotacion: 'A' | 'B' | 'C' | '-';
}

interface RopAlertRow {
  idRepuesto: number;
  nombreProducto: string;
  stockActual: number;
  tiempoEntrega: number;
  stockSeguridad: number;
  demandaAnual: number;
  demandaDiaria: number;
  ropCalculado: number;
  diferencia: number;
  estado: 'HACER_PEDIDO' | 'OK';
}

@Component({
  selector: 'app-dashboard-flash',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="dashboard-container">
      <!-- ENCABEZADO -->
      <div class="header">
        <div class="badge">Panel financiero · Inventarios</div>
        <h1 class="title">Resumen de desempeño</h1>
        <p class="subtitle">Bodega Barbacana · Ventas, stock y alertas</p>
      </div>

      <!-- TARJETAS DE GANANCIAS -->
      <div class="cards-container" *ngIf="!loading && dashboardData">
        <div class="card card-main">
          <div class="card-header">
            <div class="icon-pill">
              <svg class="icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z"
                ></path>
              </svg>
            </div>
            <div>
              <h2 class="card-title">Ganancia neta total</h2>
              <p class="card-caption">Ingresos acumulados de la bodega</p>
            </div>
          </div>
          <p class="card-value">
            {{ dashboardData.total_ganancia | currency : 'BOB' : 'symbol-narrow' : '1.2-2' }}
          </p>
        </div>

        <div class="card card-secondary">
          <div class="card-header">
            <div class="icon-pill">
              <svg class="icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M17 9V7a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2m2 4h10a2 2 0 002-2v-6a2 2 0 00-2-2H9a2 2 0 00-2 2v6a2 2 0 002 2zm7-5a2 2 0 11-4 0 2 2 0 014 0z"
                ></path>
              </svg>
            </div>
            <div>
              <h2 class="card-title">Ganancia por socia (50%)</h2>
              <p class="card-caption">Distribución equitativa de resultados</p>
            </div>
          </div>
          <p class="card-value">
            {{ dashboardData.ganancia_socia | currency : 'BOB' : 'symbol-narrow' : '1.2-2' }}
          </p>
        </div>
      </div>

      <!-- LOADING GENERAL -->
      <div class="loading-section" *ngIf="loading">
        <div class="spinner"></div>
        <p>Cargando datos financieros...</p>
      </div>

      <!-- INVENTARIO ANALÍTICO -->
      <section class="inventory-section">
        <div class="inventory-header">
          <h2>Inventario analítico</h2>
          <p>Clasificación ABC por valor y rotación, y alertas de punto de reorden (ROP).</p>
        </div>

        <div class="inventory-grid">
          <!-- ABC -->
          <div class="inventory-card">
            <div class="inventory-card-header">
              <h3>Clasificación ABC del inventario</h3>
              <p>Basado en valor económico y veces que se vendió cada producto.</p>
            </div>

            <div *ngIf="abcLoading" class="mini-loading">
              <div class="mini-spinner"></div>
              <span>Cargando clasificación ABC...</span>
            </div>

            <div *ngIf="!abcLoading && abcError" class="mini-error">
              {{ abcError }}
            </div>

            <div class="table-wrapper" *ngIf="!abcLoading && !abcError">
              <table class="abc-table">
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Producto</th>
                    <th class="align-right">Cant. vendida</th>
                    <th class="align-right">Veces vendido</th>
                    <th class="align-right">Precio sugerido</th>
                    <th class="align-right">Valor total</th>
                    <th class="align-center">ABC por valor</th>
                    <th class="align-center">ABC por rotación</th>
                  </tr>
                </thead>
                <tbody>
                  <tr *ngFor="let row of abcData">
                    <td>{{ row.idRepuesto }}</td>
                    <td class="product-name">{{ row.nombreProducto }}</td>
                    <td class="align-right">{{ row.cantidadVendida }}</td>
                    <td class="align-right">{{ row.vecesVendido }}</td>
                    <td class="align-right">
                      {{ row.precioSugerido | currency : 'BOB' : 'symbol-narrow' : '1.2-2' }}
                    </td>
                    <td class="align-right">
                      {{ row.valorTotal | currency : 'BOB' : 'symbol-narrow' : '1.2-2' }}
                    </td>
                    <td class="align-center">
                      <span [ngClass]="badgeClass(row.abcValor)">
                        {{ row.abcValor }}
                      </span>
                    </td>
                    <td class="align-center">
                      <span [ngClass]="badgeClass(row.abcRotacion)">
                        {{ row.abcRotacion }}
                      </span>
                    </td>
                  </tr>
                  <tr *ngIf="abcData.length === 0">
                    <td colspan="8" class="empty-row">
                      No hay datos suficientes de ventas para calcular el ABC.
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>

          <!-- ROP -->
          <div class="inventory-card rop-card">
            <div class="inventory-card-header">
              <h3>Alertas de punto de reorden (ROP)</h3>
              <p>
                Cuando el stock actual cae por debajo del ROP, se marca como
                <strong>"Hacer pedido"</strong>.
              </p>
            </div>

            <div *ngIf="ropLoading" class="mini-loading">
              <div class="mini-spinner"></div>
              <span>Cargando alertas de reorden...</span>
            </div>

            <div *ngIf="!ropLoading && ropError" class="mini-error">
              {{ ropError }}
            </div>

            <div class="alert-list" *ngIf="!ropLoading && !ropError">
              <div *ngIf="ropAlerts.length === 0" class="empty-alerts">
                <p>No hay productos con stock por debajo del punto de reorden.</p>
              </div>

              <div *ngFor="let alert of ropAlerts" class="alert-item">
                <div class="alert-main">
                  <h4>{{ alert.nombreProducto }}</h4>
                  <p>
                    Stock actual:
                    <strong>{{ alert.stockActual }}</strong>
                    · ROP:
                    <strong>{{ alert.ropCalculado | number : '1.0-0' }}</strong>
                  </p>
                  <p class="alert-secondary">
                    Demanda diaria aprox.:
                    <strong>{{ alert.demandaDiaria | number : '1.1-1' }}</strong>
                    · Seguridad:
                    <strong>{{ alert.stockSeguridad }}</strong>
                  </p>
                </div>
                <div class="alert-badge">
                  Hacer pedido
                </div>
              </div>
            </div>
          </div>
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
        --accent-soft: rgba(20, 6, 17, 0.08);
        --text-main: #111827;
        --text-muted: #6b7280;
      }

      .dashboard-container {
        min-height: 100vh;
        padding: 2.5rem 2.5rem 3rem;
        background-color: var(--bg-page);
        font-family: system-ui, -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
        color: var(--text-main);
        display: flex;
        flex-direction: column;
        gap: 2.5rem;
      }

      .header {
        text-align: center;
        margin-bottom: 0.5rem;
      }

      .badge {
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

      .title {
        font-size: 2.3rem;
        font-weight: 600;
        letter-spacing: 0.06em;
        text-transform: uppercase;
        margin: 0;
        animation: fadeInDown 0.5s ease-out;
      }

      .subtitle {
        margin-top: 0.35rem;
        font-size: 0.9rem;
        letter-spacing: 0.18em;
        text-transform: uppercase;
        color: var(--text-muted);
      }

      .cards-container {
        display: flex;
        gap: 1.75rem;
        max-width: 1200px;
        margin: 0 auto;
        flex-wrap: wrap;
      }

      .card {
        flex: 1;
        min-width: 320px;
        background-color: var(--card-bg);
        border-radius: 16px;
        padding: 2rem 2.25rem;
        box-shadow: 0 18px 40px rgba(15, 23, 42, 0.08);
        border: 1px solid var(--border-soft);
        transition: transform 0.18s ease, box-shadow 0.18s ease, border-color 0.18s ease;
        animation: fadeInUp 0.5s ease-out;
      }

      .card:hover {
        transform: translateY(-3px);
        box-shadow: 0 24px 60px rgba(15, 23, 42, 0.14);
        border-color: var(--border-strong);
      }

      .card-main {
        border-top: 3px solid var(--accent);
      }

      .card-secondary {
        border-top: 3px solid #4b5563;
      }

      .card-header {
        display: flex;
        align-items: center;
        gap: 1rem;
        margin-bottom: 1.5rem;
      }

      .icon-pill {
        width: 44px;
        height: 44px;
        border-radius: 999px;
        background-color: var(--bg-subtle);
        display: flex;
        align-items: center;
        justify-content: center;
        border: 1px solid var(--border-soft);
      }

      .icon {
        width: 24px;
        height: 24px;
        color: var(--accent);
      }

      .card-title {
        font-size: 1.1rem;
        font-weight: 600;
        margin: 0;
        letter-spacing: 0.08em;
        text-transform: uppercase;
      }

      .card-caption {
        margin: 0.2rem 0 0;
        font-size: 0.85rem;
        color: var(--text-muted);
      }

      .card-value {
        font-size: 2.7rem;
        font-weight: 700;
        margin: 0.75rem 0 0.25rem;
        letter-spacing: 0.08em;
        color: var(--accent);
      }

      .loading-section {
        text-align: center;
        color: var(--text-muted);
        font-size: 1.05rem;
        padding: 2.5rem 1rem 1.5rem;
      }

      .spinner {
        width: 50px;
        height: 50px;
        border: 4px solid rgba(209, 213, 219, 0.6);
        border-top-color: var(--accent);
        border-radius: 50%;
        animation: spin 0.9s linear infinite;
        margin: 0 auto 1rem;
      }

      /* INVENTARIO ANALÍTICO */

      .inventory-section {
        max-width: 1200px;
        margin: 0 auto;
      }

      .inventory-header h2 {
        text-transform: uppercase;
        letter-spacing: 0.18em;
        font-size: 1.1rem;
        margin: 0 0 0.25rem;
      }

      .inventory-header p {
        margin: 0 0 1.5rem;
        color: var(--text-muted);
        font-size: 0.9rem;
      }

      .inventory-grid {
        display: grid;
        grid-template-columns: 2fr 1.4fr;
        gap: 1.5rem;
      }

      .inventory-card {
        background-color: var(--bg-subtle);
        border-radius: 18px;
        padding: 1.75rem 1.75rem 1.5rem;
        border: 1px solid var(--border-soft);
      }

      .inventory-card-header h3 {
        margin: 0 0 0.35rem;
        font-size: 1rem;
        text-transform: uppercase;
        letter-spacing: 0.12em;
      }

      .inventory-card-header p {
        margin: 0 0 1rem;
        color: var(--text-muted);
        font-size: 0.85rem;
      }

      .table-wrapper {
        margin-top: 0.75rem;
        border-radius: 12px;
        overflow: hidden;
        background-color: #ffffff;
        border: 1px solid var(--border-soft);
      }

      .abc-table {
        width: 100%;
        border-collapse: collapse;
        font-size: 0.85rem;
      }

      .abc-table thead {
        background-color: #f9fafb;
      }

      .abc-table th,
      .abc-table td {
        padding: 0.65rem 0.9rem;
        border-bottom: 1px solid #f1f5f9;
      }

      .abc-table th {
        font-weight: 600;
        color: #4b5563;
        text-transform: uppercase;
        letter-spacing: 0.08em;
        font-size: 0.7rem;
      }

      .abc-table tbody tr:last-child td {
        border-bottom: none;
      }

      .product-name {
        font-weight: 500;
      }

      .align-right {
        text-align: right;
      }

      .align-center {
        text-align: center;
      }

      .empty-row {
        text-align: center;
        padding: 1rem 0.5rem;
        color: var(--text-muted);
      }

      .badge-abc {
        display: inline-flex;
        align-items: center;
        justify-content: center;
        min-width: 32px;
        padding: 0.15rem 0.55rem;
        border-radius: 999px;
        font-size: 0.7rem;
        font-weight: 600;
        text-transform: uppercase;
      }

      .badge-a {
        background-color: #dcfce7;
        color: #15803d;
      }

      .badge-b {
        background-color: #fef9c3;
        color: #a16207;
      }

      .badge-c {
        background-color: #fee2e2;
        color: #b91c1c;
      }

      .badge-empty {
        background-color: #e5e7eb;
        color: #6b7280;
      }

      /* MINI LOADING / ERROR */

      .mini-loading,
      .mini-error {
        display: flex;
        align-items: center;
        gap: 0.5rem;
        font-size: 0.85rem;
        margin-top: 0.75rem;
      }

      .mini-loading {
        color: var(--text-muted);
      }

      .mini-error {
        color: #b91c1c;
        background-color: #fee2e2;
        border-radius: 8px;
        padding: 0.6rem 0.75rem;
      }

      .mini-spinner {
        width: 16px;
        height: 16px;
        border-radius: 999px;
        border: 2px solid #e5e7eb;
        border-top-color: var(--accent);
        animation: spin 0.8s linear infinite;
      }

      /* ROP */

      .rop-card {
        background-color: #f9fafb;
      }

      .alert-list {
        margin-top: 0.75rem;
        display: flex;
        flex-direction: column;
        gap: 0.75rem;
      }

      .empty-alerts {
        font-size: 0.9rem;
        color: var(--text-muted);
        background-color: #ffffff;
        border-radius: 12px;
        border: 1px dashed var(--border-soft);
        padding: 1rem;
      }

      .alert-item {
        display: flex;
        align-items: center;
        justify-content: space-between;
        gap: 0.75rem;
        background-color: #ffffff;
        border-radius: 12px;
        padding: 0.9rem 1rem;
        border: 1px solid #fee2e2;
      }

      .alert-main h4 {
        margin: 0 0 0.2rem;
        font-size: 0.95rem;
      }

      .alert-main p {
        margin: 0;
        font-size: 0.8rem;
        color: var(--text-muted);
      }

      .alert-secondary {
        margin-top: 0.2rem;
      }

      .alert-badge {
        padding: 0.35rem 0.8rem;
        border-radius: 999px;
        background-color: #fee2e2;
        color: #b91c1c;
        font-size: 0.8rem;
        font-weight: 600;
        text-transform: uppercase;
        letter-spacing: 0.08em;
        white-space: nowrap;
      }

      /* ANIMACIONES Y RESPONSIVE */

      @keyframes fadeInDown {
        from {
          opacity: 0;
          transform: translateY(-18px);
        }
        to {
          opacity: 1;
          transform: translateY(0);
        }
      }

      @keyframes fadeInUp {
        from {
          opacity: 0;
          transform: translateY(18px);
        }
        to {
          opacity: 1;
          transform: translateY(0);
        }
      }

      @keyframes spin {
        to {
          transform: rotate(360deg);
        }
      }

      @media (max-width: 1024px) {
        .dashboard-container {
          padding: 2rem 1.5rem 2.5rem;
        }

        .inventory-grid {
          grid-template-columns: 1fr;
        }
      }

      @media (max-width: 768px) {
        .dashboard-container {
          padding: 1.75rem 1.25rem 2.25rem;
        }

        .title {
          font-size: 1.9rem;
        }

        .subtitle {
          font-size: 0.8rem;
          letter-spacing: 0.16em;
        }

        .cards-container {
          flex-direction: column;
        }

        .card {
          min-width: 100%;
          padding: 1.75rem 1.6rem;
        }

        .card-value {
          font-size: 2.3rem;
        }
      }
    `,
  ],
})
export class DashboardFlashComponent implements OnInit {
  dashboardData: DashboardFlash | null = null;
  loading = true;

  abcData: AbcRow[] = [];
  abcLoading = true;
  abcError: string | null = null;

  ropAlerts: RopAlertRow[] = [];
  ropLoading = true;
  ropError: string | null = null;

  private apiFlash = 'http://localhost:8080/api/dashboard/flash-ganancias';
  private apiAbc = 'http://localhost:8080/api/dashboard/abc1';
  private apiRop = 'http://localhost:8080/api/dashboard/rop-alertas';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadDashboardData();
    this.loadAbcData();
    this.loadRopAlerts();
  }

  // --------- FLASH FINANCIERO ----------
  loadDashboardData(): void {
    this.loading = true;
    this.http.get<DashboardFlash>(this.apiFlash).subscribe({
      next: (data) => {
        this.dashboardData = data;
        this.loading = false;
      },
      error: (error) => {
        console.error('Error al cargar datos del dashboard:', error);
        this.loading = false;
        // Datos de ejemplo para que no quede vacío
        this.dashboardData = {
          total_ganancia: 0,
          ganancia_socia: 0,
        };
      },
    });
  }

  // --------- ABC ----------
  loadAbcData(): void {
    this.abcLoading = true;
    this.abcError = null;

    this.http.get<AbcRow[]>(this.apiAbc).subscribe({
      next: (data) => {
        this.abcData = data || [];
        this.abcLoading = false;
      },
      error: (error) => {
        console.error('Error al cargar ABC:', error);
        this.abcError = 'No se pudo cargar la clasificación ABC.';
        this.abcLoading = false;
      },
    });
  }

  // --------- ROP ----------
  loadRopAlerts(): void {
    this.ropLoading = true;
    this.ropError = null;

    this.http.get<RopAlertRow[]>(this.apiRop).subscribe({
      next: (data) => {
        this.ropAlerts = data || [];
        this.ropLoading = false;
      },
      error: (error) => {
        console.error('Error al cargar alertas ROP:', error);
        this.ropError = 'No se pudieron cargar las alertas de reorden.';
        this.ropLoading = false;
      },
    });
  }

  // --------- Helpers UI ----------
  badgeClass(categoria: string | null | undefined): string {
    if (!categoria || categoria === '-') {
      return 'badge-abc badge-empty';
    }
    const cat = categoria.toUpperCase();
    if (cat === 'A') return 'badge-abc badge-a';
    if (cat === 'B') return 'badge-abc badge-b';
    if (cat === 'C') return 'badge-abc badge-c';
    return 'badge-abc badge-empty';
  }
}
