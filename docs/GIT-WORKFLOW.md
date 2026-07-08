# Git Workflow — DCH Know Who Platform

## Estrategia GitFlow

```
main          ← PROD  — solo PRs de release/* o hotfix/*
  └── develop ← DEV   — integración diaria; auto-deploy a DEV
        └── feature/sprint-10-xxx
        └── feature/sprint-10-yyy
  └── release/v1.1.0  ← QA  — cuando develop está listo; PR a main
  └── hotfix/fix-xxx  ← desde main, PR a main + merge a develop
```

## Tabla Ambiente ↔ Rama ↔ Pipeline

| Ambiente | Rama        | Jenkinsfile      | Puertos   | BD           |
| -------- | ----------- | ---------------- | --------- | ------------ |
| DEV      | `develop`   | `Jenkinsfile`    | 8090–8096 | Docker local |
| QA       | `release/*` | `JenkinsfileQA`  | 9090–9096 | PostgresQA   |
| PROD     | `main`      | `JenkinsfileAWS` | 8090–8096 | AWS RDS      |

## Convención de Nombres de Ramas

| Tipo    | Patrón                          | Ejemplo                        |
| ------- | ------------------------------- | ------------------------------ |
| Feature | `feature/sprint-XX-descripcion` | `feature/sprint-10-vacaciones` |
| Release | `release/vX.Y.Z`                | `release/v1.1.0`               |
| Hotfix  | `hotfix/descripcion`            | `hotfix/login-timeout`         |

## Flujo por Tipo de Cambio

### Feature / Sprint

```bash
# 1. Crear desde develop
git checkout develop
git pull origin develop
git checkout -b feature/sprint-10-vacaciones

# 2. Desarrollar, commits frecuentes
git add <archivos>
git commit -m "feat(vacaciones): agregar endpoint de solicitud"

# 3. PR a develop cuando esté listo
git push -u origin feature/sprint-10-vacaciones
# → Abrir PR en GitHub: feature/sprint-10-vacaciones → develop
# → Template de PR se auto-completa
# → Requiere 1 aprobación de @robertoesparza
```

### Release (pasar a QA)

```bash
# 1. Crear release desde develop cuando el sprint esté completo
git checkout develop
git pull origin develop
git checkout -b release/v1.1.0

# 2. Ajustes finales de QA (versiones, notas de release, etc.)
git commit -m "chore(release): bump version to 1.1.0"
git push -u origin release/v1.1.0

# → JenkinsfileQA se activa automáticamente
# → Probar en QA

# 3. Cuando QA está aprobado → PR a main
# → Abrir PR: release/v1.1.0 → main
# → Requiere 1 aprobación de @robertoesparza

# 4. Tras merge a main, crear tag
git checkout main
git pull origin main
git tag -a v1.1.0 -m "Release v1.1.0"
git push origin v1.1.0

# 5. Merge de vuelta a develop para incorporar ajustes de QA
git checkout develop
git merge release/v1.1.0
git push origin develop
```

### Hotfix (bug crítico en producción)

```bash
# 1. Crear desde main
git checkout main
git pull origin main
git checkout -b hotfix/login-timeout

# 2. Fix, commit
git commit -m "fix(security): aumentar timeout de sesión a 30min"
git push -u origin hotfix/login-timeout

# 3. PR a main (deploy rápido a PROD)
# → Abrir PR: hotfix/login-timeout → main
# → Requiere 1 aprobación

# 4. Tras merge, también merge a develop
git checkout develop
git merge hotfix/login-timeout
git push origin develop
```

## Branch Protection — Activar Reglas

Ejecutar una sola vez con permisos de admin del repo:

```bash
bash .github/branch-protection-setup.sh
```

**Reglas configuradas:**

- **`main`:** PR obligatorio (1 aprobación), dismiss stale reviews, no push directo (incluido admin), no force push
- **`develop`:** PR obligatorio (1 aprobación), no push directo

## Jenkins — Configuración Recomendada

Para que los `when { branch '...' }` funcionen, el job en Jenkins debe ser de tipo **Multibranch Pipeline**:

1. Jenkins → New Item → Multibranch Pipeline → nombre: `dch-total`
2. Branch Sources → GitHub → URL del repo
3. Build Configuration → by Jenkinsfile → Script Path: `Jenkinsfile`
4. Crear 3 jobs: uno para cada Jenkinsfile

| Job Jenkins      | Jenkinsfile      | Branch Filter |
| ---------------- | ---------------- | ------------- |
| `dch-total-dev`  | `Jenkinsfile`    | `develop`     |
| `dch-total-qa`   | `JenkinsfileQA`  | `release/*`   |
| `dch-total-prod` | `JenkinsfileAWS` | `main`        |

## Variables de Entorno Jenkins

Agregar en Jenkins → Manage Jenkins → Configure System → Global Properties:

| Variable         | Descripción                   |
| ---------------- | ----------------------------- |
| `SONAR_HOST_URL` | URL de la instancia SonarQube |

Agregar en Jenkins → Credentials:

| ID                | Tipo   | Descripción                |
| ----------------- | ------ | -------------------------- |
| `sonarqube-token` | Secret | Token de SonarQube         |
| `db-pass-prod`    | Secret | Password BD RDS producción |
| `aws-account-id`  | Secret | Account ID de AWS          |
| `ec2-hostname`    | Secret | Hostname del EC2           |
| `rds-endpoint`    | Secret | Endpoint del RDS           |
