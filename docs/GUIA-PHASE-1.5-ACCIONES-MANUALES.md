# Guia de Acciones Manuales — Phase 1.5 y Preparacion para Phase 2+

## Audiencia

Equipo tecnico de RHTotal. Esta guia asume conocimiento basico de AWS IAM, Jenkins, Docker y Spring Boot.

---

## Tabla de Contenido

1. [Panorama General del Problema](#1-panorama-general-del-problema)
2. [Paso A: Crear cuenta AWS y configurar IAM](#2-paso-a-crear-cuenta-aws-y-configurar-iam)
3. [Paso B: Configurar Jenkins desde cero](#3-paso-b-configurar-jenkins-desde-cero)
4. [Paso C: Configurar SonarQube](#4-paso-c-configurar-sonarqube)
5. [Paso D: Limpiar historial de git](#5-paso-d-limpiar-historial-de-git)
6. [Paso E: Implementar Spring Profiles por ambiente](#6-paso-e-implementar-spring-profiles-por-ambiente)
7. [Paso F: Configurar secretos en Docker Compose](#7-paso-f-configurar-secretos-en-docker-compose)
8. [Paso G: Validacion completa antes de mergear](#8-paso-g-validacion-completa-antes-de-mergear)
9. [Roadmap hacia Phase 2 y 3](#9-roadmap-hacia-phase-2-y-3)
10. [Checklist Final](#10-checklist-final)

---

## 1. Panorama General del Problema

### Que tenemos hoy

El proyecto RHTotal tiene **cero gestion de secretos**. Todo esta hardcoded:

| Secreto | Donde esta expuesto | Riesgo |
|---|---|---|
| AWS Access Key + Secret | Archivo `secret` en git (ya limpiado, pero en historial) | Critico |
| Password BD produccion (`UPi0TiKY4vzpdHRntejrh1v40Jp`) | Todos los `application.yml` como valor default + `JenkinsfileAWS` | Critico |
| Token SonarQube | `Jenkinsfile` (7 veces en texto plano) | Alto |
| AWS RDS endpoint, EC2 DNS, Account ID | `JenkinsfileAWS` | Medio |
| Credenciales BD local (`project123`) | `Dockerfile-postgresql` | Bajo (solo local) |

### Que necesitamos

```
ANTES (todo hardcoded):
  Jenkinsfile → sed reemplaza passwords → compila JAR → Docker

DESPUES (secretos externalizados):
  Jenkins Credentials Store → inyecta como env vars → Spring Profiles → Docker env_file
```

### Servicios que necesitan crearse/registrarse

Si el equipo NO tiene acceso actual a estos servicios, aqui estan los pasos para crearlos desde cero:

| Servicio | Se necesita para | Prioridad |
|---|---|---|
| AWS IAM (cuenta nueva o existente) | ECR, RDS, EC2 en produccion | Alta (solo si van a produccion) |
| Jenkins Server | CI/CD pipelines | Alta |
| SonarQube Server | Analisis de calidad de codigo | Media (no bloquea despliegue) |
| PostgreSQL local/Docker | Desarrollo y QA | Ya existe (Docker) |

---

## 2. Paso A: Crear cuenta AWS y configurar IAM

> **Omitir si:** No van a desplegar a AWS por ahora. Pueden trabajar 100% local con Docker Compose.

### A.1 — Crear cuenta AWS (si no existe)

1. Ir a https://aws.amazon.com/ → "Create an AWS Account"
2. Registrar con email corporativo del equipo
3. Configurar MFA (autenticacion multifactor) en la cuenta root — **obligatorio**
4. Crear un usuario IAM para el equipo (nunca usar la cuenta root para operaciones diarias)

### A.2 — Crear usuario IAM para CI/CD (Jenkins)

```
AWS Console → IAM → Users → Create User
```

**Configuracion:**
- Nombre: `rhtotal-jenkins-ci`
- Tipo de acceso: Programmatic access (Access Key + Secret Key)
- NO dar acceso a la consola web

**Politicas (permisos minimos necesarios):**

```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Sid": "ECRPushPull",
      "Effect": "Allow",
      "Action": [
        "ecr:GetAuthorizationToken",
        "ecr:BatchCheckLayerAvailability",
        "ecr:GetDownloadUrlForLayer",
        "ecr:PutImage",
        "ecr:InitiateLayerUpload",
        "ecr:UploadLayerPart",
        "ecr:CompleteLayerUpload",
        "ecr:BatchGetImage"
      ],
      "Resource": "arn:aws:ecr:us-east-1:*:repository/rhtotal-*"
    },
    {
      "Sid": "ECRAuth",
      "Effect": "Allow",
      "Action": "ecr:GetAuthorizationToken",
      "Resource": "*"
    }
  ]
}
```

### A.3 — Guardar las credenciales de forma segura

Al crear el usuario, AWS muestra el Access Key ID y Secret Access Key **una sola vez**.

**Reglas:**
- Guardar en un gestor de passwords (1Password, Bitwarden, etc.)
- **NUNCA** guardar en un archivo dentro del repositorio
- **NUNCA** enviarlo por Slack/email sin cifrar
- Estas credenciales se guardaran en Jenkins Credentials Store (Paso B)

### A.4 — Crear repositorios ECR

```bash
# Instalar AWS CLI si no esta instalado
# macOS:
brew install awscli

# Configurar credenciales locales (solo para administracion)
aws configure
# AWS Access Key ID: [tu key]
# AWS Secret Access Key: [tu secret]
# Default region: us-east-1
# Default output: json

# Crear repositorios ECR para cada servicio
aws ecr create-repository --repository-name rhtotal-eureka-service --region us-east-1
aws ecr create-repository --repository-name rhtotal-gateway-service --region us-east-1
aws ecr create-repository --repository-name rhtotal-security-repository --region us-east-1
aws ecr create-repository --repository-name rhtotal-application-repository --region us-east-1
aws ecr create-repository --repository-name rhtotal-user-repository --region us-east-1
aws ecr create-repository --repository-name rhtotal-fintech-repository --region us-east-1
aws ecr create-repository --repository-name rhtotal-paysheetsico-repository --region us-east-1
aws ecr create-repository --repository-name rhtotal-frontend-repository --region us-east-1
```

### A.5 — Crear RDS PostgreSQL (si se necesita BD en la nube)

```
AWS Console → RDS → Create database
```

- Engine: PostgreSQL 16.x
- Template: Free Tier (para desarrollo) o Production
- DB Instance: `rhtotal-db`
- Master username: `rhtotal_admin` (no usar "postgres")
- Master password: **generar uno fuerte** (minimo 20 caracteres, alfanumerico + simbolos)
- VPC: La misma donde estara EC2
- Public access: **No** (acceso solo desde EC2 en la misma VPC)

**Guardar la password en el gestor de passwords y en Jenkins Credentials.**

---

## 3. Paso B: Configurar Jenkins desde cero

### B.1 — Instalar Jenkins

**Opcion 1 — Docker (recomendada para equipos pequeños):**

```bash
docker run -d \
  --name jenkins \
  -p 8080:8080 \
  -p 50000:50000 \
  -v jenkins_home:/var/jenkins_home \
  jenkins/jenkins:lts-jdk21
```

Notar que usamos `lts-jdk21` para que Jenkins corra con Java 21 nativo.

**Opcion 2 — Instalacion nativa (macOS):**

```bash
brew install jenkins-lts
brew services start jenkins-lts
```

**Opcion 3 — EC2 en AWS:**

```bash
# En una instancia Amazon Linux 2023 o Ubuntu 22.04:
sudo yum install java-21-amazon-corretto -y  # Amazon Linux
# o
sudo apt install openjdk-21-jdk -y            # Ubuntu

# Instalar Jenkins
sudo wget -O /etc/yum.repos.d/jenkins.repo https://pkg.jenkins.io/redhat-stable/jenkins.repo
sudo rpm --import https://pkg.jenkins.io/redhat-stable/jenkins.io-2023.key
sudo yum install jenkins -y
sudo systemctl start jenkins
sudo systemctl enable jenkins
```

### B.2 — Configuracion inicial de Jenkins

1. Acceder a `http://localhost:8080` (o la IP del servidor)
2. Obtener password inicial: `docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword`
3. Instalar plugins sugeridos + estos adicionales:
   - **Pipeline** (ya incluido)
   - **Docker Pipeline**
   - **Amazon ECR** (si usan AWS)
   - **SonarQube Scanner**
   - **Credentials Binding**

### B.3 — Registrar JDK 21 en Jenkins

```
Jenkins → Manage Jenkins → Tools → JDK installations
```

- Name: `jdk21` ← **este nombre debe coincidir con el Jenkinsfile**
- Install automatically: Si
- Installer: "Install from adoptium.net" → Version: `jdk-21+latest`

Alternativamente, si Jenkins ya corre en JDK 21:
- JAVA_HOME: `/opt/java/openjdk` (en Docker) o la ruta de instalacion

### B.4 — Registrar Maven en Jenkins

```
Jenkins → Manage Jenkins → Tools → Maven installations
```

- Name: `M3` ← **debe coincidir con el Jenkinsfile**
- Install automatically: Si
- Version: 3.9.6 o superior

### B.5 — Configurar Jenkins Credentials Store (CRITICO)

Aqui es donde se guardan TODOS los secretos. **Nunca mas hardcoded en Jenkinsfiles.**

```
Jenkins → Manage Jenkins → Credentials → System → Global credentials → Add Credentials
```

**Credencial 1 — AWS Credentials:**
- Kind: "AWS Credentials"
- ID: `aws-ecr-credentials`
- Access Key ID: [el de IAM Paso A.2]
- Secret Access Key: [el de IAM Paso A.2]

**Credencial 2 — SonarQube Token:**
- Kind: "Secret text"
- ID: `sonarqube-token`
- Secret: [el token que generes en SonarQube, ver Paso C]

**Credencial 3 — Password BD Produccion:**
- Kind: "Secret text"
- ID: `rhtotal-db-password-prod`
- Secret: [la password del RDS]

**Credencial 4 — Password BD QA:**
- Kind: "Secret text"
- ID: `rhtotal-db-password-qa`
- Secret: [la password de la BD QA]

### B.6 — Como usar credenciales en Jenkinsfiles

Despues de configurar las credenciales, los Jenkinsfiles se modifican asi:

**ANTES (inseguro):**
```groovy
sh 'mvn sonar:sonar -Dsonar.login=e7a8085ab0f60254c49a1a9296e1cc4be0d99c73'
```

**DESPUES (seguro):**
```groovy
withCredentials([string(credentialsId: 'sonarqube-token', variable: 'SONAR_TOKEN')]) {
    sh 'mvn sonar:sonar -Dsonar.login=$SONAR_TOKEN'
}
```

**Para AWS ECR login:**
```groovy
withCredentials([[$class: 'AmazonWebServicesCredentialsBinding',
                  credentialsId: 'aws-ecr-credentials',
                  accessKeyVariable: 'AWS_ACCESS_KEY_ID',
                  secretKeyVariable: 'AWS_SECRET_ACCESS_KEY']]) {
    sh 'aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 272762519227.dkr.ecr.us-east-1.amazonaws.com'
}
```

**Para passwords de BD:**
```groovy
withCredentials([string(credentialsId: 'rhtotal-db-password-prod', variable: 'DB_PASSWORD')]) {
    sh "mvn clean package -DDB_PASS=${DB_PASSWORD}"
}
```

> **Nota:** Esto es Phase 3 (refactorizar Jenkinsfiles para usar `withCredentials`). Por ahora, el pipeline funciona con los tokens hardcoded. La prioridad es que el pipeline compile con Java 21.

### B.7 — Crear el pipeline en Jenkins

```
Jenkins → New Item → Pipeline
```

- Name: `rhtotal-dev`
- Pipeline → Definition: "Pipeline script from SCM"
- SCM: Git
- Repository URL: [URL de tu repo GitHub]
- Script Path: `Jenkinsfile`
- Branch: `*/main` (o la branch que uses)

Repetir para QA (`JenkinsfileQA`) y AWS (`JenkinsfileAWS`).

---

## 4. Paso C: Configurar SonarQube

> **Omitir si:** No necesitan analisis de calidad de codigo por ahora. SonarQube no bloquea compilacion ni despliegue.

### C.1 — Instalar SonarQube con Docker

```bash
docker run -d \
  --name sonarqube \
  -p 9000:9000 \
  -v sonarqube_data:/opt/sonarqube/data \
  -v sonarqube_logs:/opt/sonarqube/logs \
  sonarqube:lts-community
```

### C.2 — Configuracion inicial

1. Acceder a `http://localhost:9000`
2. Login: `admin` / `admin` → cambiar password inmediatamente
3. Crear proyecto:
   - Project key: `18_WorkPoint_RHTotal_Security` (mantener los mismos keys)
   - Repetir para cada servicio

### C.3 — Generar token de autenticacion

```
SonarQube → My Account → Security → Generate Token
```

- Name: `rhtotal-jenkins`
- Type: Global Analysis Token
- Expires in: 365 dias (o "No expiration" para desarrollo)

**Copiar el token generado** → guardarlo en Jenkins Credentials Store como `sonarqube-token` (Paso B.5).

### C.4 — Configurar SonarQube en Jenkins

```
Jenkins → Manage Jenkins → Configure System → SonarQube servers
```

- Name: `SonarQube`
- Server URL: `http://localhost:9000` (o la URL de tu servidor)
- Server authentication token: seleccionar la credencial `sonarqube-token`

---

## 5. Paso D: Limpiar historial de git

### Porque es necesario

Aunque ya reemplazamos el contenido del archivo `secret` con un placeholder, **las credenciales originales siguen en el historial de git**. Cualquier persona con acceso al repo puede hacer:

```bash
git log --all --full-history -- secret
git show <commit>:secret
# → Ve las credenciales originales
```

### D.1 — Opcion 1: BFG Repo-Cleaner (recomendada, mas rapida)

```bash
# Instalar BFG
brew install bfg  # macOS

# Clonar el repo como mirror
git clone --mirror https://github.com/TU_ORG/rhtotal-v2.git rhtotal-v2-mirror
cd rhtotal-v2-mirror

# Eliminar el archivo 'secret' de todo el historial
bfg --delete-files secret

# Limpiar los objetos huerfanos
git reflog expire --expire=now --all
git gc --prune=now --aggressive

# Push forzado (REQUIERE que todos los miembros del equipo re-clonen despues)
git push --force
```

### D.2 — Opcion 2: git filter-repo (alternativa moderna)

```bash
# Instalar
pip install git-filter-repo

# Desde el directorio del repo (NO mirror)
git filter-repo --path secret --invert-paths

# Esto reescribe el historial eliminando 'secret' de todos los commits
# Luego hacer push --force
```

### D.3 — Despues de limpiar el historial

**Todos los miembros del equipo deben:**

```bash
# Borrar su copia local
rm -rf rhtotal-v2

# Clonar de nuevo
git clone https://github.com/TU_ORG/rhtotal-v2.git
```

**No** hacer `git pull` — el historial cambio y causara conflictos.

---

## 6. Paso E: Implementar Spring Profiles por ambiente

### Problema actual

Hoy, los `application.yml` tienen UN solo set de configuracion con valores de produccion como defaults. Para cambiar ambiente, Jenkins hace `sed -i` sobre los archivos fuente — esto es fragil y peligroso.

### Solucion: Un archivo por ambiente

Para cada servicio (ejemplo: `security-service`), crear:

```
security-service/security-web/src/main/resources/
├── application.yml           ← configuracion comun (no secrets)
├── application-dev.yml       ← BD local Docker, puertos DEV
├── application-qa.yml        ← BD QA, puertos QA
└── application-prod.yml      ← BD produccion (sin passwords hardcoded)
```

### Ejemplo para security-service

**application.yml** (comun, sin secrets):
```yaml
server:
  port: ${SERVER_PORT:8090}

spring:
  application:
    name: security-service

eureka:
  client:
    serviceUrl:
      defaultZone: ${EUREKA_URL:http://eurekaservice:8761/eureka}
```

**application-dev.yml:**
```yaml
spring:
  datasource:
    url: jdbc:postgresql://postgres:5432/unbound
    username: postgres
    password: project123
```

**application-qa.yml:**
```yaml
spring:
  datasource:
    url: jdbc:postgresql://postgresqa:5432/unbound
    username: postgres
    password: ${DB_PASSWORD}

server:
  port: 9090
```

**application-prod.yml:**
```yaml
spring:
  datasource:
    url: jdbc:postgresql://${DB_HOST}:${DB_PORT:5432}/${DB_NAME:rhtotal}
    username: ${DB_USER}
    password: ${DB_PASSWORD}
```

### Como activar el profile

**En Docker Compose (dev):**
```yaml
securityservice:
  environment:
    - SPRING_PROFILES_ACTIVE=dev
```

**En Jenkinsfile (QA), ya no se necesita sed:**
```groovy
sh 'mvn clean package -Dspring.profiles.active=qa'
```

**En produccion (EC2), via variable de entorno:**
```bash
export SPRING_PROFILES_ACTIVE=prod
export DB_HOST=rhtotal.xxxxx.us-east-1.rds.amazonaws.com
export DB_PASSWORD=xxxxx  # inyectado por el sistema, no hardcoded
java -jar app.jar
```

> **Nota:** Implementar Spring Profiles es un paso de Phase 3. No es bloqueante para Phase 1.5 — el sistema funciona ahora con la configuracion actual. Pero es el siguiente paso logico para eliminar los `sed` de los Jenkinsfiles.

---

## 7. Paso F: Configurar secretos en Docker Compose

### Para desarrollo local

Crear un archivo `.env` en la raiz (ya esta en `.gitignore` via `docker-compose.override.yml`):

```bash
# Agregar a .gitignore si no esta:
.env
```

**Archivo `.env`:**
```ini
# Desarrollo local — NO commitear este archivo
POSTGRES_PASSWORD=project123
TZ=America/Mexico_City
```

**En docker-compose.yml, usar variables:**
```yaml
services:
  securityservice:
    environment:
      - SPRING_PROFILES_ACTIVE=dev
      - TZ=${TZ:-America/Mexico_City}
```

### Para produccion (Docker en EC2)

Usar `docker-compose.override.yml` (ya esta en `.gitignore`):

```yaml
# docker-compose.override.yml — SOLO en el servidor, nunca en git
version: '3'
services:
  securityservice:
    environment:
      - SPRING_PROFILES_ACTIVE=prod
      - DB_HOST=rhtotal.xxxxx.us-east-1.rds.amazonaws.com
      - DB_PASSWORD=xxxxx
```

---

## 8. Paso G: Validacion completa antes de mergear

### Checklist de validacion local

Ejecutar estos comandos en orden:

```bash
cd /ruta/a/rhtotal-v2

# 1. Compilar todos los servicios (requiere Java 21 + Maven)
# Verificar version de Java primero:
java -version  # Debe decir "openjdk version 21.x.x"

# Compilar eureka-service
cd eureka-service && mvn clean package && cd ..

# Compilar gateway
cd gateway-service && mvn clean package && cd ..

# Compilar los 5 servicios de negocio
cd security-service && mvn clean package && cd ..
cd application-service && mvn clean package && cd ..
cd user-service && mvn clean package && cd ..
cd fintech-service && mvn clean package && cd ..
cd paysheetsico-service && mvn clean package && cd ..

# 2. Levantar con Docker Compose
docker-compose down --rmi all  # Limpiar imagenes anteriores
docker-compose up -d

# 3. Verificar que todos los contenedores estan corriendo
docker-compose ps
# Todos deben mostrar "Up"

# 4. Verificar Eureka Dashboard
# Abrir en navegador: http://localhost:8761
# Deben aparecer registrados:
#   - GATEWAY-SERVICE
#   - SECURITY-SERVICE
#   - APPLICATION-SERVICE
#   - USER-SERVICE
#   - FINTECH-SERVICE
#   - PAYSHEETSICO-SERVICE

# 5. Verificar gateway
curl http://localhost:8000/actuator/health
# Debe responder: {"status":"UP"}

# 6. Verificar un servicio downstream a traves del gateway
curl http://localhost:8000/api/security/actuator/health

# 7. Ver logs si algo falla
docker-compose logs eurekaservice
docker-compose logs gatewayservice
docker-compose logs securityservice
```

### Problemas comunes

| Sintoma | Causa probable | Solucion |
|---|---|---|
| `Unsupported class file major version 65` | JAR compilado con Java 21 pero Docker usa Java 10 | Verificar que los Dockerfiles usan `eclipse-temurin:21-jre-alpine` |
| `Module java.xml.bind not found` | Flag `--add-modules java.xml.bind` presente | Verificar que los Dockerfiles NO tienen este flag |
| Servicio no aparece en Eureka | Servicio arranca antes que Eureka | Verificar `depends_on: eurekaservice` en docker-compose |
| `Connection refused` al conectar a BD | PostgreSQL no esta en la red Docker | Verificar `docker network connect rhtotal_rhtotalnet postgres` |
| `port is already allocated` | Puerto ocupado por otra instancia | `docker-compose down` primero, o cambiar puertos |

---

## 9. Roadmap hacia Phase 2 y 3

### Phase 2: Migracion Frontend

| Tarea | Complejidad | Dependencia |
|---|---|---|
| Angular 6 → 17 (frontend-web) | Alta | Phase 1.5 completa |
| Ionic 3 → 7 (frontend-mobile) | Muy Alta (casi rewrite) | Phase 1.5 completa |
| Angular Material actualizado | Media | Angular 17 primero |
| Cordova → Capacitor (mobile) | Media | Ionic 7 primero |

**Estrategia recomendada para Angular:**
```
Angular 6 → 7 → 8 → 9 → ... → 17 (incremental con ng update)
```

Cada salto mayor requiere:
```bash
npx @angular/cli@X update @angular/core@X @angular/cli@X
```

Donde X es la version destino. Ir una version a la vez.

### Phase 3: Infraestructura y Seguridad

| Tarea | Prioridad | Esfuerzo |
|---|---|---|
| Refactorizar Jenkinsfiles para usar `withCredentials` | Alta | 1-2 dias |
| Implementar Spring Profiles (dev/qa/prod) | Alta | 2-3 dias |
| Eliminar `sed -i` de Jenkinsfiles | Alta | Incluido en Spring Profiles |
| Agregar stage `mvn test` a pipelines | Media | 1 dia |
| PostgreSQL 9.x → 16 en RDS | Media | 1 dia (con downtime) |
| Health checks en docker-compose | Baja | Medio dia |
| Migrar de Docker Compose a ECS/EKS (produccion) | Baja | 1-2 semanas |

### Orden recomendado

```
1. [AHORA]    Phase 1.5 validacion + merge PR
2. [Semana 1] Phase 3a — Jenkins Credentials + limpiar historial git
3. [Semana 2] Phase 3b — Spring Profiles (eliminar sed)
4. [Semana 3+] Phase 2 — Frontend migration (puede ser en paralelo con 3b)
```

---

## 10. Checklist Final

### Antes de mergear PR #1

- [ ] 6 Dockerfiles de servicios actualizados a `eclipse-temurin:21-jre-alpine`
- [ ] `Dockerfile-postgresql` pinned a `postgres:16-alpine`
- [ ] `Dockerfile-frontend` pinned a `nginx:1.25-alpine`
- [ ] `Dockerfile-eureka-service` con `EXPOSE 8761`
- [ ] `eureka-service` agregado a `docker-compose.yml` y `docker-composeQA.yml`
- [ ] `depends_on: eurekaservice` en todos los servicios
- [ ] Bug de `depends_on` duplicado corregido en `docker-composeQA.yml`
- [ ] Jenkinsfiles actualizados a `jdk 'jdk21'`
- [ ] Eureka build stage agregado a los 3 Jenkinsfiles
- [ ] JenkinsfileAWS tiene Docker build/tag/push para eureka-service
- [ ] Archivo `secret` limpiado (solo placeholder)
- [ ] `secret` agregado a `.gitignore`
- [ ] `CLAUDE.md` actualizado con stack Java 21 + Spring Cloud Gateway

### Acciones manuales (equipo)

- [ ] Instalar Java 21 (Temurin) en maquinas de desarrollo
- [ ] Instalar/configurar Jenkins con JDK 21 registrado como `jdk21`
- [ ] Registrar Maven como `M3` en Jenkins Tools
- [ ] (Opcional) Instalar SonarQube y generar nuevo token
- [ ] (Opcional) Crear usuario IAM en AWS para CI/CD
- [ ] (Opcional) Crear repositorio ECR para `rhtotal-eureka-service`
- [ ] Limpiar historial de git (BFG o filter-repo)
- [ ] Notificar al equipo que re-clone el repo despues de limpiar historial
- [ ] Validar `docker-compose up -d` localmente
- [ ] Verificar Eureka dashboard en `http://localhost:8761`

---

## Contacto y Recursos

- **Jenkins docs:** https://www.jenkins.io/doc/
- **AWS IAM best practices:** https://docs.aws.amazon.com/IAM/latest/UserGuide/best-practices.html
- **BFG Repo-Cleaner:** https://rtyley.github.io/bfg-repo-cleaner/
- **Spring Profiles:** https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.profiles
- **Eclipse Temurin JDK 21:** https://adoptium.net/temurin/releases/?version=21
