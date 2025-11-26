import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

interface DashboardFlash {
  total_ganancia: number;
  ganancia_socia: number;
}

@Component({
  selector: 'app-dashboard-flash',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="dashboard-container">
      <div class="header">
        <div class="badge">Panel financiero</div>
        <h1 class="title">Resumen de ganancias</h1>
        <p class="subtitle">Bodega Barbacana · Vinos &amp; Singanis</p>
      </div>

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

      <div class="loading-section" *ngIf="loading">
        <div class="spinner"></div>
        <p>Cargando datos financieros...</p>
      </div>

      <div class="metrics-placeholder">
        <div class="placeholder-icon">📊</div>
        <p class="placeholder-text">Próximamente: tablero de inventario</p>
        <div class="placeholder-bar"></div>
      </div>
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

      .metrics-placeholder {
        max-width: 900px;
        margin: 0 auto;
        margin-top: 0.5rem;
        background-color: var(--bg-subtle);
        border-radius: 16px;
        padding: 2.25rem 2rem 2.1rem;
        text-align: center;
        border: 1px dashed var(--border-strong);
      }

      .placeholder-icon {
        font-size: 2.8rem;
        margin: 0 0 0.7rem;
        animation: pulse 2.2s ease-in-out infinite;
      }

      .placeholder-text {
        font-size: 1.1rem;
        font-weight: 500;
        margin: 0 0 1.4rem;
        color: var(--text-muted);
        letter-spacing: 0.08em;
        text-transform: uppercase;
      }

      .placeholder-bar {
        height: 5px;
        background: #e5e7eb;
        border-radius: 999px;
        overflow: hidden;
        position: relative;
      }

      .placeholder-bar::after {
        content: '';
        position: absolute;
        top: 0;
        left: -40%;
        height: 100%;
        width: 40%;
        background: linear-gradient(
          90deg,
          transparent,
          rgba(109, 40, 217, 0.6),
          transparent
        );
        animation: shimmer 2s infinite;
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

      @keyframes pulse {
        0%,
        100% {
          opacity: 1;
          transform: scale(1);
        }
        50% {
          opacity: 0.8;
          transform: scale(1.04);
        }
      }

      @keyframes shimmer {
        0% {
          left: -40%;
        }
        100% {
          left: 100%;
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

        .metrics-placeholder {
          padding: 2rem 1.5rem 1.9rem;
        }
      }
    `,
  ],
})
export class DashboardFlashComponent implements OnInit {
  dashboardData: DashboardFlash | null = null;
  loading = true;

  private apiUrl = 'http://localhost:8080/api/dashboard/flash-ganancias';

  constructor(private http: HttpClient) {
    console.log('DashboardFlashComponent constructor ejecutado');
  }

  ngOnInit(): void {
    console.log('ngOnInit ejecutado - iniciando carga de datos');
    this.loadDashboardData();
  }

  loadDashboardData(): void {
    console.log('Intentando cargar datos desde:', this.apiUrl);
    this.http.get<DashboardFlash>(this.apiUrl).subscribe({
      next: (data) => {
        console.log('Datos recibidos:', data);
        this.dashboardData = data;
        this.loading = false;
      },
      error: (error) => {
        console.error('Error al cargar datos del dashboard:', error);
        this.loading = false;
        // Datos de ejemplo para pruebas
        this.dashboardData = {
          total_ganancia: 15000,
          ganancia_socia: 7500,
        };
        console.log('Usando datos de ejemplo:', this.dashboardData);
      },
    });
  }
}

