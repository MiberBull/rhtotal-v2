# Arquitectura del Sistema — DCH Know Who (Plataforma HR)

## Vision General

DCH Know Who es una plataforma de **Recursos Humanos** construida con una **arquitectura de microservicios** en Java/Spring Boot, desplegada en **AWS** con Docker. Originalmente desarrollada por **Axity** como RHTotal.

---

## Diagrama de Arquitectura

```
                        ┌─────────────────┐    ┌─────────────────┐
                        │  Frontend Web   │    │ Frontend Mobile │
                        │  Angular 6      │    │  Ionic 3        │
                        │  Puerto: 80     │    │  iOS / Android  │
                        └────────┬────────┘    └────────┬────────┘
                                 │                      │
                                 └──────────┬───────────┘
                                            │
                                 ┌──────────▼──────────┐
                                 │   Gateway Service   │
                                 │ Spring Cloud Gateway│
                                 │   Puerto: 8000      │
                                 │   Prefijo: /api     │
                                 └──────────┬──────────┘
                                            │
                                 ┌──────────▼──────────┐
                                 │   Eureka Service    │
                                 │  Service Discovery  │
                                 │   Puerto: 8761      │
                                 └──────────┬──────────┘
                                            │
               ┌────────────────────────────┼────────────────┐
               │              │             │                │
    ┌──────────▼───┐ ┌───────▼──────┐ ┌────▼─────┐         │
    │  Security    │ │ Application  │ │  User    │         │
    │  Service     │ │ Service      │ │ Service  │         │
    │  :8090       │ │ :8091        │ │ :8092    │         │
    │  /security/* │ │ /application/│ │ /user/*  │         │
    └──────┬───────┘ └──────┬───────┘ └────┬─────┘         │
           │                │              │                │
           └────────────────┴──────────────┴────────────────┘
                                                  │
                                    ┌─────────────▼──────┐
                                    │   PostgreSQL (RDS) │
                                    │   AWS us-east-1    │
                                    │   DB: rhtotal      │
                                    └────────────────────┘
```

---

## Microservicios - Detalle

### 0. Eureka Service (Puerto 8761)
- **Tecnologia:** Spring Cloud Netflix Eureka Server (standalone)
- **Rol:** Service Discovery — registro y descubrimiento de microservicios
- **Codigo:** `eureka-service/`

### 1. Gateway Service (Puerto 8000)
- **Tecnologia:** Spring Cloud Gateway + Eureka Client
- **Rol:** Punto unico de entrada. Enruta todas las peticiones `/api/*` a los microservicios
- **Service Discovery:** Eureka para registro y descubrimiento
- **CORS:** Habilitado para todos los origenes
- **Timeouts:** Read 60s, Connection 3s
- **Codigo:** `gateway-service/src/main/java/mx/com/axity/`

### 2. Security Service (Puerto 8090) — `/api/security/*`
- **Rol:** Autenticacion y autorizacion
- **Auth:** Login custom con password encriptado AES → hash SHA-256 en BD
- **Endpoints clave:**
  - `POST /login/loginWeb` y `/login/loginMobile` — Login con flags (0=ok, 1=usuario invalido, 2=password incorrecto, 3=bloqueado, 4=nuevo)
  - Gestion de roles con permisos JSON (menus dinamicos)
  - Reset de password con token UUID por email
- **Bloqueo:** 5 intentos fallidos → cuenta bloqueada 10 min
- **Email:** SMTP via Gmail
- **Modulos Maven:** security-web, security-services, security-persistence, security-model, security-commons
- **Encriptacion:** AES-CBC (key: `megustanlaschicascongafas`), SHA-256 para almacenamiento

### 3. User Service (Puerto 8092) — `/api/user/*`
- **Rol:** Gestion completa del empleado
- **Entidades:** User, Employee, EmployeeAddress, EmployeeComplementary, CompensationPackage, ContratingData, SocialNetwork, MyCv, HistoryEmployee
- **Endpoints clave:** CRUD de empleados, datos de contratacion, compensacion, domicilio, redes sociales, historial laboral
- **Datos MX:** RFC, CURP, NSS, pasaporte
- **Modulos Maven:** user-web, user-services, user-persistence, user-model, user-commons

### 4. Application Service (Puerto 8091) — `/api/application/*`
- **Rol:** Gestion de contenido y beneficios de la plataforma
- **Modulos funcionales:**
  - **Descuentos/Beneficios** — Categorias, subcategorias, imagenes, proveedores
  - **Seguros** — Polizas, coberturas, tipos (auto, vida, gastos medicos)
  - **Notificaciones** — Push notifications, tokens FCM, banners
  - **Clientes/Proyectos** — Gestion de empresas y proyectos
  - **Parametros del sistema** — Configuracion global
- **48+ entidades JPA**
- **Modulos Maven:** application-web, application-services, application-persistence, application-model, application-commons

---

## Frontends

### Frontend Web (Angular 6) — Puerto 80
- **UI:** Angular Material
- **Modulos principales:** Dashboard, Empleados (7 tabs de admin), Descuentos, Seguros, Notificaciones, Banners, Roles, Clientes
- **Auth:** AES encrypt del password, token en localStorage, Route Guards (LoginGuard)
- **Visualizacion:** Chart.js (ng2-charts) para graficas
- **Estado global:** RxJS Subjects via DataService
- **Codigo:** `frontend-web/src/app/`
- **Rutas:** Definidas en `app.routes.ts`, protegidas bajo `/home/*`

### Frontend Mobile (Ionic 3 + Angular 5)
- **Plataformas:** iOS y Android via Cordova
- **Modulos:** Login, Beneficios, Mi Cuenta, Credencial, Nomina, Seguros, RH
- **Push Notifications:** Firebase Cloud Messaging (FCM)
- **Almacenamiento local:** SQLite + localStorage
- **Tipos de usuario:** `IN` (interno/empleado, acceso completo) vs `EX` (externo, acceso limitado)
- **Codigo:** `frontend-mobile/src/`

---

## Infraestructura y DevOps

| Componente | Tecnologia |
|---|---|
| **Cloud** | AWS (EC2, RDS, ECR) |
| **Base de datos** | PostgreSQL 9.x en AWS RDS |
| **Migraciones** | Liquibase (`database/liquibase/`) |
| **Contenedores** | Docker + Docker Compose |
| **CI/CD** | Jenkins (3 pipelines: DEV, QA, AWS Prod) |
| **Calidad** | SonarQube |
| **Registry** | AWS ECR (produccion), Nexus 3 (paquetes npm) |
| **Java Runtime** | Eclipse Temurin 21 |
| **Web Server** | NGINX (frontend) |

### Ambientes
| Ambiente | Gateway | BD | Puertos |
|---|---|---|---|
| **DEV** | localhost:8000 | Docker local (`unbound`) | 8090-8092 |
| **QA** | localhost:9000 | PostgresQA | 9090-9092 |
| **PROD** | EC2 AWS:8000 | AWS RDS (`rhtotal`) | 8090-8092 |

### CI/CD Pipelines (Jenkins)
- **Jenkinsfile** (DEV): Build paralelo → SonarQube → Docker Compose → Liquibase
- **JenkinsfileQA** (QA): Igual a DEV con puertos +1000 y BD QA
- **JenkinsfileAWS** (Prod): Build → Docker Build → Tag ECR → Push ECR

---

## Stack Tecnologico Completo

- **Backend:** Java 21, Spring Boot 3.2.5, Spring Cloud (2023.0.1), Spring Cloud Gateway + Eureka
- **Frontend Web:** Angular 6, Angular Material, TypeScript, Chart.js
- **Frontend Mobile:** Ionic 3, Angular 5, Cordova, Firebase
- **Base de Datos:** PostgreSQL 9.x
- **Seguridad:** AES-CBC + SHA-256, tokens custom, roles con permisos JSON
- **Infra:** Docker, Jenkins, AWS (EC2/RDS/ECR), SonarQube, Liquibase

## Patron de Arquitectura por Servicio (Todos los microservicios)

Cada microservicio sigue la misma estructura Maven multi-modulo:
```
{service}-web        → REST Controllers (Spring MVC)
{service}-services   → Facades + Services (logica de negocio)
{service}-persistence → DAOs/Repositories (Spring Data JPA)
{service}-model      → Entidades JPA (DOs)
{service}-commons    → TOs, Constantes, Excepciones, Utilidades
```

Patron de flujo: **Controller → Facade → Service → DAO → PostgreSQL**
