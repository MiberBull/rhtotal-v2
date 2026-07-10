# Plan de Pruebas Local — DCH Know Who Platform

**Versión:** 1.0
**Fecha:** Julio 2026
**Preparado por:** Bambu Tech Services
**Tenant de demo:** `demo-corp`

---

## Índice

1. [Setup del Entorno](#1-setup-del-entorno-10-min)
2. [Credenciales de Demo](#2-credenciales-de-demo)
3. [Casos de Prueba por Módulo](#3-casos-de-prueba-por-módulo)
4. [Pruebas de Exportaciones](#4-pruebas-de-exportaciones)
5. [Checklist Go/No-Go para Demo](#5-checklist-gono-go-para-demo)
6. [Solución de Problemas Comunes](#6-solución-de-problemas-comunes)

---

## 1. Setup del Entorno (10 min)

### 1.1 Requisitos previos

| Requisito                                 | Verificación                 |
| ----------------------------------------- | ---------------------------- |
| Docker Desktop instalado y corriendo      | `docker info`                |
| Puerto 80 libre (frontend-web)            | `lsof -i :80` → sin output   |
| Puerto 5432 libre (PostgreSQL)            | `lsof -i :5432` → sin output |
| Puerto 8000 libre (Gateway)               | `lsof -i :8000` → sin output |
| Puertos 8090–8096 libres (microservicios) | `lsof -i :8090` → sin output |
| Puerto 8761 libre (Eureka)                | `lsof -i :8761` → sin output |
| Mínimo 8 GB RAM disponible                | Monitor de Actividad         |
| Al menos 5 GB en disco libre              | `df -h .`                    |

### 1.2 Levantar el stack

```bash
# Desde la raíz del repositorio
cd /ruta/a/dch-knowwho-platform

# Levantar todos los servicios (primera vez: --build)
docker-compose up -d --build

# Esperar 90 segundos a que todos los servicios arranquen
sleep 90
```

### 1.3 Verificar servicios

```bash
# Ejecutar smoke test automático
./scripts/smoke-test.sh

# Resultado esperado: 10/10 PASS (o mejor)
```

**Si el smoke test falla parcialmente**, espera 30 segundos adicionales y vuelve a ejecutarlo. Los servicios Spring Boot pueden tardar hasta 2 minutos en registrarse en Eureka.

### 1.4 Abrir el web-admin

```
http://localhost
```

> **Nota de seguridad:** En entorno local, el backend no requiere TLS. Para producción se configura HTTPS en el load balancer.

---

## 2. Credenciales de Demo

Todos los usuarios pertenecen al tenant `demo-corp`. El web-admin envía el header `X-Tenant-ID: demo-corp` automáticamente al hacer login.

| Usuario           | Password    | Rol                   | Acceso                               |
| ----------------- | ----------- | --------------------- | ------------------------------------ |
| `admin@dch.mx`    | `Admin2026` | Administrador Master  | Todos los módulos + configuración    |
| `rrhh@dch.mx`     | `Rrhh2026`  | RRHH                  | Gestión de empleados + HR + reportes |
| `empleado@dch.mx` | `Emp2026`   | Empleado (Ana García) | Módulos de empleado                  |

> **Cómo funciona el login:**
> El frontend cifra el password con AES-CBC antes de enviarlo. El backend descifra y compara SHA-256. Los hashes en la BD son calculados del password en texto claro.

### Datos de demo cargados

| Módulo      | Datos disponibles                                             |
| ----------- | ------------------------------------------------------------- |
| Empleados   | Ana García (emp. 100) + Carlos Mendoza (emp. 101)             |
| Vacaciones  | 1 solicitud PENDIENTE + 1 APROBADA + balances 15 días         |
| Incidencias | 2 (RETARDO + FALTA_JUSTIFICADA para emp. 100)                 |
| Tickets     | TKT-DEMO-001 ABIERTO + TKT-DEMO-002 EN_PROCESO con comentario |
| Asistencia  | Turno 8am-5pm + 5 días de registros semana pasada             |
| Hora extra  | 1 registro PENDIENTE (165 min — jueves)                       |
| Encuestas   | "Clima Laboral Q3 2026" activa con 3 preguntas                |
| Onboarding  | 3 candidatos: ENTREVISTA / SELECCIONADO / ONBOARDING          |
| REPSE       | Perfil activo + 1 cliente (Bosch) + semáforo VERDE            |
| Documentos  | 2 documentos PENDIENTE para Ana García                        |
| Tenants     | `demo-corp` (activo)                                          |

---

## 3. Casos de Prueba por Módulo

### Convenciones

- **TC-XX** = Test Case número XX
- **Pre:** = Precondición necesaria
- **Paso:** = Acción a realizar
- **Esperado:** = Resultado que debe verse

---

### 3.1 Autenticación

| #     | Módulo | Caso de Prueba      | Resultado Esperado                           |
| ----- | ------ | ------------------- | -------------------------------------------- |
| TC-01 | Auth   | Login como admin    | Dashboard admin visible con menús completos  |
| TC-02 | Auth   | Login como empleado | Dashboard empleado visible (módulos propios) |

**TC-01 — Login Administrador**

```
Pre:    Stack corriendo, smoke test 10/10
Paso:   Ir a http://localhost → ingresar admin@dch.mx / Admin2026 → clic en Iniciar Sesión
Esperado: Redirige a /home/dashboard. Sidebar muestra: Empleados, Vacaciones, Incidencias,
          Tickets, Asistencia, Encuestas, Onboarding, REPSE, Documentos, Tenants.
```

**TC-02 — Login Empleado**

```
Pre:    TC-01 completado (o sesión cerrada)
Paso:   Login con empleado@dch.mx / Emp2026
Esperado: Redirige a dashboard. Sidebar muestra solo módulos de empleado.
          NO debe ver opciones de administración (Tenants, configuración global).
```

---

### 3.2 Onboarding

| #     | Módulo     | Caso de Prueba                      | Resultado Esperado                            |
| ----- | ---------- | ----------------------------------- | --------------------------------------------- |
| TC-03 | Onboarding | Ver pipeline de candidatos          | 3 candidatos visibles en diferentes etapas    |
| TC-04 | Onboarding | Avanzar candidato a siguiente etapa | Estado actualizado + nuevo stage en historial |

**TC-03 — Pipeline de candidatos**

```
Pre:    Login como admin o rrhh
Paso:   Navegar a Onboarding (sidebar o /home/onboarding)
Esperado:
  - Sofía Ramírez  → etapa: ENTREVISTA
  - Miguel Hernández → etapa: SELECCIONADO
  - Lucía Torres    → etapa: ONBOARDING
  - Filtro por etapa funciona (dropdown o tabs)
```

**TC-04 — Avanzar candidato**

```
Pre:    TC-03 completado, Sofía Ramírez visible en ENTREVISTA
Paso:   Clic en Sofía → botón "Avanzar etapa" → confirmar
Esperado:
  - Sofía pasa de ENTREVISTA a SELECCIONADO
  - Lista se actualiza sin recargar página
  - Historial de Sofía muestra nuevo entry SELECCIONADO
```

---

### 3.3 Vacaciones

| #     | Módulo     | Caso de Prueba             | Resultado Esperado            |
| ----- | ---------- | -------------------------- | ----------------------------- |
| TC-05 | Vacaciones | Ver solicitudes pendientes | 1 solicitud PENDIENTE visible |
| TC-06 | Vacaciones | Aprobar solicitud          | Estado cambia a APROBADA      |

**TC-05 — Solicitudes pendientes**

```
Pre:    Login como admin o rrhh
Paso:   Navegar a Vacaciones → tab/sección "Pendientes"
Esperado:
  - Solicitud de Ana García visible
  - Fechas: próxima semana (+7 a +9 días desde hoy)
  - Días solicitados: 3
  - Estado: PENDIENTE
```

**TC-06 — Aprobar solicitud**

```
Pre:    TC-05, solicitud de Ana García visible
Paso:   Clic en solicitud → botón "Aprobar" → confirmar
Esperado:
  - Estado cambia a APROBADA
  - Campo "Aprobado por" muestra el usuario actual
  - Solicitud desaparece de la lista de pendientes
  - Aparece en lista de APROBADAS
```

---

### 3.4 Asistencia

| #     | Módulo     | Caso de Prueba            | Resultado Esperado           |
| ----- | ---------- | ------------------------- | ---------------------------- |
| TC-07 | Asistencia | Ver reportes de la semana | 5 días de registros visibles |
| TC-08 | Asistencia | Aprobar hora extra        | Estado → APROBADO            |

**TC-07 — Reportes de asistencia**

```
Pre:    Login como admin o rrhh
Paso:   Navegar a Asistencia → Reportes → seleccionar última semana
Esperado:
  - Ana García: 5 registros check-in/check-out (lun-vie)
  - Jueves: check-out a las 19:45 (destacado como hora extra)
  - Todos los registros con geofence válido ✓
```

**TC-08 — Aprobar hora extra**

```
Pre:    TC-07, registro del jueves visible
Paso:   Navegar a Horas Extra → solicitud PENDIENTE de Ana García → Aprobar
Esperado:
  - Estado cambia de PENDIENTE a APROBADO
  - 165 minutos extra registrados como aprobados
```

---

### 3.5 Tickets (Mesa de Ayuda)

| #     | Módulo  | Caso de Prueba       | Resultado Esperado                |
| ----- | ------- | -------------------- | --------------------------------- |
| TC-09 | Tickets | Ver tickets abiertos | 2 tickets visibles                |
| TC-10 | Tickets | Agregar comentario   | Comentario guardado y visible     |
| TC-11 | Tickets | Cambiar estado       | Estado actualizado en tiempo real |

**TC-09 — Ver tickets**

```
Pre:    Login como admin o rrhh
Paso:   Navegar a Tickets
Esperado:
  - TKT-DEMO-001: "Acceso VPN" - ABIERTO - ALTA prioridad - Ana García
  - TKT-DEMO-002: "Corrección nómina" - EN_PROCESO - Carlos Mendoza
  - TKT-DEMO-002 tiene 1 comentario visible
```

**TC-10 — Agregar comentario**

```
Pre:    TC-09, TKT-DEMO-001 visible
Paso:   Clic en TKT-DEMO-001 → campo "Comentario" → escribir "Revisando configuración VPN" → Guardar
Esperado:
  - Comentario aparece en el hilo con fecha/hora actual
  - Autor: usuario logueado
```

**TC-11 — Cambiar estado ticket**

```
Pre:    TC-09, TKT-DEMO-001 en estado ABIERTO
Paso:   En TKT-DEMO-001 → dropdown de estado → seleccionar EN_PROCESO → Guardar
Esperado:
  - Estado cambia a EN_PROCESO
  - Ticket ya no aparece en filtro "ABIERTO"
  - Aparece en filtro "EN_PROCESO"
```

---

### 3.6 Incidencias

| #     | Módulo      | Caso de Prueba          | Resultado Esperado          |
| ----- | ----------- | ----------------------- | --------------------------- |
| TC-12 | Incidencias | Ver incidencias del mes | 2 incidencias de Ana García |
| TC-13 | Incidencias | Validar incidencia      | Estado → VALIDADA           |

**TC-12 — Ver incidencias**

```
Pre:    Login como admin o rrhh
Paso:   Navegar a Incidencias → filtrar por empleado Ana García (o ver todas)
Esperado:
  - Incidencia 1: RETARDO (hace 5 días) — estado REGISTRADA
  - Incidencia 2: FALTA_JUSTIFICADA (hace 12 días) — estado REGISTRADA
  - Notas visibles con descripción de cada incidencia
```

**TC-13 — Validar incidencia**

```
Pre:    TC-12, incidencia de RETARDO visible
Paso:   Clic en incidencia de RETARDO → cambiar estado a VALIDADA → Guardar
Esperado:
  - Estado cambia a VALIDADA
  - Incidencia ya no aparece en lista de REGISTRADAS sin filtro
```

---

### 3.7 Encuestas

| #     | Módulo    | Caso de Prueba      | Resultado Esperado              |
| ----- | --------- | ------------------- | ------------------------------- |
| TC-14 | Encuestas | Ver encuesta activa | "Clima Laboral Q3 2026" visible |
| TC-15 | Encuestas | Ver resultados      | Dashboard con 3 preguntas       |

**TC-14 — Ver encuesta activa**

```
Pre:    Login como admin o rrhh
Paso:   Navegar a Encuestas → tab Activas
Esperado:
  - "Clima Laboral Q3 2026" aparece con estado ACTIVO
  - Fechas: inicio hace 7 días, cierre en 23 días
  - Tipo: CLIMA_LABORAL, anónima: sí
  - 3 preguntas configuradas
```

**TC-15 — Ver resultados**

```
Pre:    TC-14
Paso:   Clic en "Clima Laboral Q3 2026" → Ver resultados / Preguntas
Esperado:
  - Pregunta 1: Escala 1-5 (ambiente de trabajo)
  - Pregunta 2: Opción múltiple (retroalimentación)
  - Pregunta 3: Texto libre (sugerencias)
  - 0 respuestas registradas (encuesta nueva)
```

---

### 3.8 REPSE

| #     | Módulo | Caso de Prueba       | Resultado Esperado                             |
| ----- | ------ | -------------------- | ---------------------------------------------- |
| TC-16 | REPSE  | Dashboard semáforo   | Indicadores de cumplimiento con semáforo VERDE |
| TC-17 | REPSE  | Exportar reporte PDF | PDF descargado exitosamente                    |

**TC-16 — Dashboard REPSE**

```
Pre:    Login como admin
Paso:   Navegar a REPSE → Cumplimiento
Esperado:
  - Perfil: DCH Total S.A. de C.V. — Registro REPSE-2024-000001234
  - Vigencia: +90 días desde hoy (estado ACTIVO — sin alerta)
  - Cliente: Corporativo Bosch México
  - Semáforo del mes anterior: VERDE (5/6 documentos validados)
  - 3 documentos aprobados: ISR, IMSS-SUA, INFONAVIT
```

**TC-17 — Exportar PDF**

```
Pre:    TC-16
Paso:   Botón "Exportar Reporte PDF" → seleccionar período del mes anterior → Descargar
Esperado:
  - Navegador descarga archivo PDF
  - PDF abre correctamente con datos del período
  - Incluye tabla de semáforo y estado de documentos
```

---

### 3.9 Documentos del Empleado

| #     | Módulo     | Caso de Prueba            | Resultado Esperado         |
| ----- | ---------- | ------------------------- | -------------------------- |
| TC-18 | Documentos | Ver documentos pendientes | 2 documentos de Ana García |
| TC-19 | Documentos | Validar documento         | Estado → VALIDADO          |

**TC-18 — Documentos pendientes**

```
Pre:    Login como admin o rrhh
Paso:   Navegar a Documentos → tab Pendientes
Esperado:
  - INE_ANA_GARCIA_2026.pdf — empleado: Ana García — PENDIENTE
  - COMPROBANTE_DOMICILIO_ANA_GARCIA_JUL2026.pdf — PENDIENTE
  - Ambos subidos en los últimos 3 días
```

**TC-19 — Validar documento**

```
Pre:    TC-18
Paso:   Clic en INE de Ana García → botón "Validar" → confirmar
Esperado:
  - Estado cambia a VALIDADO
  - Documento desaparece de lista de PENDIENTES
  - Aparece en lista del expediente de Ana García
```

---

### 3.10 Tenants

| #     | Módulo  | Caso de Prueba       | Resultado Esperado    |
| ----- | ------- | -------------------- | --------------------- |
| TC-20 | Tenants | Ver tenant demo-corp | Tenant activo visible |

**TC-20 — Gestión de tenants**

```
Pre:    Login como admin
Paso:   Navegar a Tenants (sidebar, solo visible para Administrador Master)
Esperado:
  - dchkw: "DCH Know Who" — dominio: dchkw.com.mx — Activo ✓
  - demo-corp: "Demo Corporation" — dominio: demo.dchkw.com.mx — Activo ✓
  - Tabla con toggle de activar/desactivar por tenant
```

---

## 4. Pruebas de Exportaciones

| #     | Tipo  | Caso de Prueba              | Resultado Esperado                        |
| ----- | ----- | --------------------------- | ----------------------------------------- |
| TC-E1 | Excel | Asistencia (últimos 7 días) | Archivo .xlsx descargado con 10 registros |
| TC-E2 | Excel | Incidencias (mes actual)    | Archivo .xlsx con 2 incidencias           |
| TC-E3 | PDF   | REPSE cumplimiento          | PDF con semáforo y tabla de documentos    |

**TC-E1 — Excel de Asistencia**

```
Pre:    Login como admin o rrhh, módulo Asistencia visible
Paso:   Asistencia → Reportes → seleccionar "última semana" → botón "Exportar Excel"
Esperado:
  - Descarga inmediata de archivo .xlsx
  - Columnas: empleado_id, nombre, proyecto, fecha, check_in, check_out,
              duracion_horas, horas_extra, en_geofence
  - 5 filas (una por día, lun-vie) para Ana García
  - Horas extra del jueves marcadas
```

**TC-E2 — Excel de Incidencias**

```
Pre:    Login como admin o rrhh, módulo Incidencias visible
Paso:   Incidencias → botón "Exportar Excel" → seleccionar mes actual
Esperado:
  - Archivo .xlsx con 2 filas (RETARDO y FALTA_JUSTIFICADA)
  - Columnas incluyen: empleado, tipo, fecha, notas, estado
```

**TC-E3 — PDF de REPSE**

```
Pre:    Login como admin, módulo REPSE → Cumplimiento visible
Paso:   REPSE → Cumplimiento → "Exportar PDF" → período del mes anterior
Esperado:
  - PDF bien formado (A4, estilo corporativo)
  - Encabezado: DCH Total S.A. de C.V. — período seleccionado
  - Tabla de documentos con semáforo VERDE/AMARILLO/ROJO por tipo
  - Firma/watermark DCH Total si aplica
```

---

## 5. Checklist Go/No-Go para Demo

Completar este checklist antes de la presentación al cliente:

### Infraestructura

```
[ ] smoke-test.sh ejecutado → resultado 10/10 (o mínimo 8/10 con justificación)
[ ] Eureka muestra ≥7 servicios registrados
[ ] Frontend web carga en http://localhost sin errores en consola
[ ] docker stats muestra todos los contenedores con CPU/RAM estables
```

### Autenticación

```
[ ] Login admin@dch.mx / Admin2026 → OK (TC-01)
[ ] Login rrhh@dch.mx / Rrhh2026 → OK (variante)
[ ] Login empleado@dch.mx / Emp2026 → OK (TC-02)
[ ] Logout funciona correctamente (sesión limpiada)
```

### Flujos completos (mínimo 1 por módulo)

```
[ ] Onboarding: pipeline visible con 3 candidatos (TC-03)
[ ] Vacaciones: 1 solicitud PENDIENTE visible + flujo de aprobación (TC-05, TC-06)
[ ] Asistencia: registros semana pasada visibles (TC-07)
[ ] Tickets: 2 tickets visibles + añadir comentario funciona (TC-09, TC-10)
[ ] Incidencias: 2 incidencias de Ana García visibles (TC-12)
[ ] Encuestas: "Clima Laboral Q3 2026" activa con 3 preguntas (TC-14)
[ ] REPSE: semáforo VERDE + perfil activo visible (TC-16)
[ ] Documentos: 2 pendientes de Ana García (TC-18)
[ ] Tenants: demo-corp activo visible (TC-20)
```

### Exportaciones

```
[ ] Excel asistencia generado correctamente (TC-E1)
[ ] PDF REPSE descargado correctamente (TC-E3)
```

### Criterio de aprobación

- **GO** si: ≥ 8/10 smoke tests + los 3 logins + al menos 1 flujo completo por módulo + 1 exportación
- **NO-GO** si: gateway o seguridad caído, login admin no funciona, menos de 5 módulos operativos

---

## 6. Solución de Problemas Comunes

### El smoke test falla en varios servicios

```bash
# Ver qué contenedores no están corriendo
docker-compose ps

# Revisar logs de un servicio específico
docker-compose logs -f hr-service

# Reiniciar servicio con error
docker-compose restart hr-service

# Reconstruir todo desde cero
docker-compose down -v && docker-compose up -d --build
sleep 120
./scripts/smoke-test.sh
```

### Login falla con "Usuario inválido" o "Contraseña incorrecta"

El seed data de demo se aplica vía Liquibase al arrancar el stack. Si los usuarios no existen:

```bash
# Verificar que Liquibase aplicó el changeset 0.0.11
docker-compose logs postgres | grep "0.0.11"

# Verificar usuarios en BD directamente
docker exec -it postgres psql -U postgres -d rhtotal \
  -c "SELECT ds_email, ds_status, tenant_id FROM k_rol_assignment WHERE tenant_id='demo-corp';"
```

Si los usuarios no aparecen, ejecutar Liquibase manualmente:

```bash
# Desde el contenedor de liquibase (si existe) o via Maven
docker-compose run --rm liquibase update
```

### Eureka muestra 0 servicios registrados

Los servicios tardan hasta 3 minutos en registrarse tras el arranque. Esperar y reintentar:

```bash
sleep 60 && ./scripts/smoke-test.sh
```

### Frontend no carga (HTTP 502/504)

El nginx puede arrancar antes que el gateway:

```bash
docker-compose restart frontend-web
# Esperar 10 segundos
sleep 10
curl -I http://localhost
```

### Error de CORS en el navegador

Verificar que el gateway tiene CORS habilitado y que el `X-Tenant-ID` header se envía:

```bash
# Probar endpoint directamente con header
curl -H "X-Tenant-ID: demo-corp" http://localhost:8000/api/user/employee/all
```

### Base de datos no responde

```bash
# Verificar conexión
docker exec -it postgres psql -U postgres -c "SELECT 1;"

# Si el contenedor está caído
docker-compose start postgres
sleep 10
docker-compose restart eureka-service gateway-service
sleep 30
./scripts/smoke-test.sh
```

---

## Flujo de Trabajo Completo

```bash
# ============================
# SETUP COMPLETO DESDE CERO
# ============================

# 1. Levantar stack
docker-compose up -d --build

# 2. Esperar arranque (Eureka + todos los servicios)
echo "Esperando 90 segundos para arranque completo..."
sleep 90

# 3. Verificar servicios
./scripts/smoke-test.sh

# 4. Abrir web-admin en navegador
open http://localhost   # macOS
# xdg-open http://localhost  # Linux

# 5. Ejecutar casos de prueba según secciones 3 y 4

# ============================
# AL FINALIZAR EL DEMO
# ============================

# Bajar el stack (conserva datos)
docker-compose stop

# Bajar el stack y limpiar volúmenes (reset completo)
docker-compose down -v
```

---

_Documento generado para la plataforma DCH Know Who — Sprint 11 | Bambu Tech Services_
