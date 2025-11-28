import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

/* ----------------- Interfaces de datos ------------------ */

interface DashboardFlash {
  total_ganancia: number;
  ganancia_socia: number;
}

/**
 * Forma cruda que puede devolver el backend.
 * Aceptamos camelCase y snake_case para no depender
 * de cómo se llamen exactamente las propiedades.
 */
interface RopAlertApi {
  idRepuesto?: number;
  id_repuesto?: number;

  nombreRepuesto?: string;
  nombre_repuesto?: string;

  stockActual?: number;
  stock_actual?: number;

  rop?: number;
  ropCalculado?: number;
  rop_calculado?: number;

  stockSeguridad?: number;
  stock_seguridad?: number;

  tiempoEntrega?: number;
  tiempo_entrega?: number;
}

/**
 * Modelo ya normalizado para la vista.
 */
interface RopAlertView {
  id: number;
  producto: string;
  stockActual: number;
  rop: number;
  stockSeguridad: number;
  tiempoEntrega: number;
}

@Component({
  selector: 'app-dashboard-flash',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="dashboard-container">
      <!-- Migas de pan / contexto -->
      <div class="breadcrumb">Panel financiero · inventarios</div>

      <!-- Encabezado principal -->
      <header class="header">
        <div class="badge">Panel financiero</div>
        <h1 class="title">Resumen de desempeño</h1>
        <p class="subtitle">Bodega Barbacana · Ventas, stock y alertas</p>
      </header>

      <!-- Tarjetas de ganancias -->
      <section class="cards-container" *ngIf="!loading && dashboardData; else loadingBlock">
        <article class="card card-main">
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
        </article>

        <article class="card card-secondary">
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
        </article>
      </section>

      <ng-template #loadingBlock>
        <section class="loading-section">
          <div class="spinner"></div>
          <p>Cargando datos financieros...</p>
        </section>
      </ng-template>

      <!-- Sección de inventario / ROP -->
      <section class="inventory-section">
        <header class="inventory-header">
          <h2 class="inventory-title">Inventario analítico</h2>
          <p class="inventory-subtitle">
            Alertas de punto de reorden basadas en stock actual, demanda estimada y lead time.
          </p>
        </header>

        <div class="rop-panel">
          <div class="rop-panel-header">
            <div class="rop-header-left">
              <div class="rop-icon-circle">📦</div>
              <div>
                <h3 class="rop-title">Alertas de punto de reorden (ROP)</h3>
                <p class="rop-subtitle">
                  Cuando el stock actual cae por debajo del ROP, se marca como
                  <strong>"Hacer pedido"</strong>.
                </p>
              </div>
            </div>
          </div>

          <!-- Loading ROP -->
          <div class="rop-loading" *ngIf="loadingRop">
            <div class="spinner small"></div>
            <span>Cargando alertas de reorden...</span>
          </div>

          <!-- Error ROP -->
          <div class="rop-error-banner" *ngIf="ropError">
            {{ ropError }}
          </div>

          <!-- Contenido de alertas -->
          <ng-container *ngIf="!loadingRop && !ropError">
            <ng-container *ngIf="ropAlerts.length > 0; else ropEmpty">
              <article class="rop-alert-card" *ngFor="let alerta of ropAlerts">
                <div class="rop-alert-header">
                  <div>
                    <h4 class="rop-product-name">{{ alerta.producto }}</h4>
                    <p class="rop-product-sub">Repuesto con stock por debajo del ROP.</p>
                  </div>
                  <button class="btn-danger">Hacer pedido</button>
                </div>

                <div class="rop-metrics">
                  <div class="metric">
                    <span class="metric-label">Stock actual</span>
                    <span class="metric-value">{{ alerta.stockActual }}</span>
                  </div>

                  <div class="metric">
                    <span class="metric-label">ROP</span>
                    <span class="metric-value">
                      {{ alerta.rop | number : '1.0-2' }}
                    </span>
                  </div>

                  <div class="metric">
                    <span class="metric-label">Stock seg.</span>
                    <span class="metric-value">{{ alerta.stockSeguridad }}</span>
                  </div>

                  <div class="metric">
                    <span class="metric-label">Lead time</span>
                    <span class="metric-value">{{ alerta.tiempoEntrega }} días</span>
                  </div>
                </div>
              </article>
            </ng-container>

            <!-- Sin alertas -->
            <ng-template #ropEmpty>
              <div class="rop-empty-banner">
                No hay alertas activas de reorden. El stock está por encima del ROP para
                todos los productos configurados.
              </div>
            </ng-template>
          </ng-container>
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
        --accent: #140611; /* morado profundo */
        --accent-soft: rgba(109, 40, 217, 0.08);
        --text-main: #111827;
        --text-muted: #6b7280;
        --danger: #b91c1c;
        --danger-soft: #fee2e2;
      }

      .dashboard-container {
        min-height: 100vh;
        padding: 2.5rem 1.5rem 3rem;
        background-color: var(--bg-page);
        font-family: system-ui, -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
        color: var(--text-main);
        display: flex;
        flex-direction: column;
        gap: 2.5rem;
        max-width: 1200px;
        margin: 0 auto;
      }

      .breadcrumb {
        font-size: 0.8rem;
        text-transform: uppercase;
        letter-spacing: 0.2em;
        color: var(--text-muted);
        margin-bottom: -0.8rem;
      }

      .header {
        text-align: center;
        margin-bottom: 1rem;
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
        flex-wrap: wrap;
      }

      .card {
        flex: 1;
        min-width: 260px;
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

      .spinner.small {
        width: 26px;
        height: 26px;
        border-width: 3px;
        margin-right: 0.75rem;
        margin-bottom: 0;
      }

      /* -------- Inventario / ROP -------- */

      .inventory-section {
        margin-top: 1rem;
        display: flex;
        flex-direction: column;
        gap: 1.5rem;
      }

      .inventory-header {
        text-align: left;
      }

      .inventory-title {
        font-size: 1.4rem;
        letter-spacing: 0.18em;
        text-transform: uppercase;
        margin: 0 0 0.3rem;
      }

      .inventory-subtitle {
        margin: 0;
        font-size: 0.9rem;
        color: var(--text-muted);
      }

      .rop-panel {
        background-color: var(--bg-subtle);
        border-radius: 16px;
        padding: 1.9rem 1.8rem 1.9rem;
        border: 1px solid var(--border-soft);
      }

      .rop-panel-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 1.4rem;
      }

      .rop-header-left {
        display: flex;
        align-items: center;
        gap: 0.9rem;
      }

      .rop-icon-circle {
        width: 44px;
        height: 44px;
        border-radius: 999px;
        background-color: #fefce8;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 1.5rem;
        border: 1px solid #facc15;
      }

      .rop-title {
        margin: 0;
        font-size: 1rem;
        letter-spacing: 0.12em;
        text-transform: uppercase;
      }

      .rop-subtitle {
        margin: 0.2rem 0 0;
        font-size: 0.85rem;
        color: var(--text-muted);
      }

      .rop-loading {
        display: inline-flex;
        align-items: center;
        font-size: 0.9rem;
        color: var(--text-muted);
        padding: 0.9rem 1.1rem;
        border-radius: 999px;
        background-color: #eef2ff;
        border: 1px solid #e0e7ff;
      }

      .rop-error-banner {
        padding: 0.9rem 1.1rem;
        border-radius: 12px;
        background-color: var(--danger-soft);
        color: var(--danger);
        font-size: 0.9rem;
        border: 1px solid rgba(185, 28, 28, 0.4);
      }

      .rop-empty-banner {
        padding: 1rem 1.3rem;
        border-radius: 12px;
        background-color: #ecfdf5;
        border: 1px solid #a7f3d0;
        color: #047857;
        font-size: 0.9rem;
      }

      .rop-alert-card {
        background-color: #ffffff;
        border-radius: 14px;
        padding: 1.4rem 1.5rem 1.3rem;
        border: 1px solid var(--border-soft);
        box-shadow: 0 12px 30px rgba(15, 23, 42, 0.06);
        display: flex;
        flex-direction: column;
        gap: 1rem;
      }

      .rop-alert-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        gap: 1rem;
      }

      .rop-product-name {
        margin: 0;
        font-weight: 600;
        letter-spacing: 0.1em;
        text-transform: uppercase;
        font-size: 0.95rem;
      }

      .rop-product-sub {
        margin: 0.2rem 0 0;
        font-size: 0.85rem;
        color: var(--text-muted);
      }

      .btn-danger {
        padding: 0.55rem 1.1rem;
        border-radius: 999px;
        border: none;
        background-color: var(--danger);
        color: #fef2f2;
        font-size: 0.8rem;
        letter-spacing: 0.12em;
        text-transform: uppercase;
        font-weight: 600;
        cursor: pointer;
        box-shadow: 0 10px 25px rgba(185, 28, 28, 0.45);
        transition: transform 0.15s ease, box-shadow 0.15s ease, background-color 0.15s ease;
      }

      .btn-danger:hover {
        transform: translateY(-1px);
        box-shadow: 0 16px 40px rgba(185, 28, 28, 0.55);
        background-color: #991b1b;
      }

      .rop-metrics {
        display: grid;
        grid-template-columns: repeat(4, minmax(0, 1fr));
        gap: 0.8rem 1.2rem;
        font-size: 0.85rem;
      }

      .metric {
        display: flex;
        flex-direction: column;
        gap: 0.12rem;
      }

      .metric-label {
        text-transform: uppercase;
        letter-spacing: 0.12em;
        color: var(--text-muted);
      }

      .metric-value {
        font-weight: 600;
      }

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

        .rop-metrics {
          grid-template-columns: repeat(2, minmax(0, 1fr));
        }

        .inventory-title {
          font-size: 1.1rem;
        }
      }
    `,
  ],
})
export class DashboardFlashComponent implements OnInit {
  dashboardData: DashboardFlash | null = null;
  loading = true;

  ropAlerts: RopAlertView[] = [];
  loadingRop = true;
  ropError: string | null = null;

  private apiUrl = 'http://localhost:8080/api/dashboard/flash-ganancias';
  private apiRopUrl = 'http://localhost:8080/api/dashboard/rop-alertas';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadDashboardData();
    this.loadRopAlerts();
  }

  /* ------------ Ganancias -------------- */

  loadDashboardData(): void {
    this.http.get<DashboardFlash>(this.apiUrl).subscribe({
      next: (data) => {
        console.log('Datos dashboard:', data);
        this.dashboardData = data;
        this.loading = false;
      },
      error: (error) => {
        console.error('Error al cargar datos del dashboard:', error);
        this.loading = false;
        // Datos de ejemplo por si algo falla
        this.dashboardData = {
          total_ganancia: 0,
          ganancia_socia: 0,
        };
      },
    });
  }

  /* -------------- ROP ------------------- */

  /** Normaliza nombres de propiedades desde el backend */
  private normalizeRop(item: RopAlertApi): RopAlertView {
    return {
      id: item.idRepuesto ?? item.id_repuesto ?? 0,
      producto: item.nombreRepuesto ?? item.nombre_repuesto ?? 'Producto sin nombre',
      stockActual: item.stockActual ?? item.stock_actual ?? 0,
      rop:
        item.ropCalculado ??
        item.rop_calculado ??
        item.rop ??
        0,
      stockSeguridad: item.stockSeguridad ?? item.stock_seguridad ?? 0,
      tiempoEntrega: item.tiempoEntrega ?? item.tiempo_entrega ?? 0,
    };
  }

  loadRopAlerts(): void {
    this.loadingRop = true;
    this.ropError = null;

    this.http.get<RopAlertApi[]>(this.apiRopUrl).subscribe({
      next: (data) => {
        console.log('Datos crudos ROP:', data);
        this.ropAlerts = (data || []).map((item) => this.normalizeRop(item));
        this.loadingRop = false;
      },
      error: (error) => {
        console.error('Error al cargar alertas ROP:', error);
        this.loadingRop = false;
        this.ropError = 'No se pudieron cargar las alertas de reorden.';
        this.ropAlerts = [];
      },
    });
  }
}
