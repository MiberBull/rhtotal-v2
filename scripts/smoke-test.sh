#!/usr/bin/env bash
# =============================================================================
# DCH Know Who — Smoke Test Script
# Verifica que todos los microservicios respondan correctamente antes de un demo.
#
# Uso:  ./scripts/smoke-test.sh
#       ./scripts/smoke-test.sh --tenant demo-corp   (override del X-Tenant-ID)
#
# Requisitos:
#   - Docker Desktop corriendo
#   - Stack levantado con:  docker-compose up -d
#   - curl disponible en PATH
# =============================================================================

set -euo pipefail

# ---------------------------------------------------------------------------
# Config
# ---------------------------------------------------------------------------
GATEWAY="http://localhost:8000"
EUREKA="http://localhost:8761"
FRONTEND="http://localhost"
TENANT="${2:-demo-corp}"          # default: demo-corp (puede sobreescribirse)
TIMEOUT=10                        # segundos por request
WAIT_SECS=5                       # pausa antes de empezar si se pasa --wait

PASS="✅ PASS"
FAIL="❌ FAIL"
SKIP="⚠️  SKIP"

PASS_COUNT=0
FAIL_COUNT=0
TOTAL=0

# Color helpers (solo si el terminal lo soporta)
RED=""
GREEN=""
YELLOW=""
BOLD=""
RESET=""
if [ -t 1 ]; then
    RED='\033[0;31m'
    GREEN='\033[0;32m'
    YELLOW='\033[1;33m'
    BOLD='\033[1m'
    RESET='\033[0m'
fi

# ---------------------------------------------------------------------------
# Helper functions
# ---------------------------------------------------------------------------
print_header() {
    echo ""
    echo -e "${BOLD}============================================================${RESET}"
    echo -e "${BOLD}  DCH Know Who — Pre-Demo Smoke Tests${RESET}"
    echo -e "${BOLD}  $(date '+%Y-%m-%d %H:%M:%S')   Tenant: ${TENANT}${RESET}"
    echo -e "${BOLD}============================================================${RESET}"
    echo ""
}

print_section() {
    echo ""
    echo -e "${YELLOW}--- $1 ---${RESET}"
}

check() {
    local name="$1"
    local url="$2"
    local expected_code="${3:-200}"
    local extra_header="${4:-}"

    TOTAL=$((TOTAL + 1))

    local header_args=()
    if [ -n "$extra_header" ]; then
        header_args=(-H "$extra_header")
    fi

    local http_code
    http_code=$(curl -s -o /dev/null -w "%{http_code}" \
        --max-time "$TIMEOUT" \
        -H "Content-Type: application/json" \
        "${header_args[@]}" \
        "$url" 2>/dev/null) || http_code="000"

    if [ "$http_code" = "$expected_code" ] || \
       { [ "$expected_code" = "2xx" ] && [[ "$http_code" =~ ^2 ]]; }; then
        echo -e "  ${GREEN}${PASS}${RESET}  [HTTP $http_code]  $name"
        PASS_COUNT=$((PASS_COUNT + 1))
    else
        echo -e "  ${RED}${FAIL}${RESET}  [HTTP $http_code]  $name"
        echo -e "           URL: $url"
        FAIL_COUNT=$((FAIL_COUNT + 1))
    fi
}

check_docker_running() {
    print_section "1. Docker Desktop"
    if docker info > /dev/null 2>&1; then
        echo -e "  ${GREEN}${PASS}${RESET}  Docker Desktop está corriendo"
    else
        echo -e "  ${RED}${FAIL}${RESET}  Docker Desktop NO está corriendo — levanta Docker primero"
        echo ""
        echo "  Ejecuta:  open -a Docker   (macOS)"
        exit 1
    fi
}

check_containers() {
    print_section "2. Contenedores Docker"

    local services=(
        "postgres"
        "eurekaservice"
        "gateway-service"
        "security-service"
        "user-service"
        "application-service"
        "onboarding-service"
        "attendance-service"
        "hr-service"
        "document-service"
        "frontend-web"
    )

    local all_up=true
    for svc in "${services[@]}"; do
        local state
        state=$(docker inspect --format='{{.State.Status}}' "$svc" 2>/dev/null || echo "not_found")
        if [ "$state" = "running" ]; then
            echo -e "  ${GREEN}${PASS}${RESET}  $svc  [running]"
        else
            echo -e "  ${RED}${FAIL}${RESET}  $svc  [$state]"
            all_up=false
        fi
    done

    if ! $all_up; then
        echo ""
        echo -e "  ${YELLOW}Tip:${RESET}  docker-compose up -d --build && sleep 90"
    fi
}

check_ports() {
    print_section "3. Puertos directos (TCP)"

    local ports=(
        "8761:Eureka"
        "8000:Gateway"
        "8090:Security"
        "8091:Application"
        "8092:User"
        "8093:Onboarding"
        "8094:Attendance"
        "8095:HR"
        "8096:Document"
        "80:Frontend-Web"
        "5432:PostgreSQL"
    )

    for entry in "${ports[@]}"; do
        local port="${entry%%:*}"
        local name="${entry##*:}"
        TOTAL=$((TOTAL + 1))
        if nc -z -w 3 localhost "$port" 2>/dev/null; then
            echo -e "  ${GREEN}${PASS}${RESET}  :$port  $name"
            PASS_COUNT=$((PASS_COUNT + 1))
        else
            echo -e "  ${RED}${FAIL}${RESET}  :$port  $name  (puerto no responde)"
            FAIL_COUNT=$((FAIL_COUNT + 1))
        fi
    done
}

check_endpoints() {
    print_section "4. Endpoints vía Gateway (HTTP)"

    local tenant_header="X-Tenant-ID: ${TENANT}"

    # Eureka dashboard
    check "Eureka Dashboard" \
        "${EUREKA}/" "200"

    # Gateway actuator
    check "Gateway Health" \
        "${GATEWAY}/actuator/health" "200"

    # Security Service
    check "Security — tenant/all" \
        "${GATEWAY}/api/security/tenant/all" "200" "$tenant_header"

    # User Service
    check "User — employee/all" \
        "${GATEWAY}/api/user/employee/all" "200" "$tenant_header"

    # Application Service — REPSE
    check "Application — repse/profile/all" \
        "${GATEWAY}/api/application/repse/profile/all" "200" "$tenant_header"

    # Onboarding Service
    check "Onboarding — candidate/all" \
        "${GATEWAY}/api/onboarding/candidate/all" "200" "$tenant_header"

    # Attendance Service
    check "Attendance — shift/all" \
        "${GATEWAY}/api/attendance/shift/all" "200" "$tenant_header"

    # HR Service — Vacaciones
    check "HR — vacation/request/pending" \
        "${GATEWAY}/api/vacation/request/pending" "200" "$tenant_header"

    # HR Service — Tickets
    check "HR — ticket/status/ABIERTO" \
        "${GATEWAY}/api/ticket/status/ABIERTO" "200" "$tenant_header"

    # Document Service
    check "Document — pending" \
        "${GATEWAY}/api/document/pending" "200" "$tenant_header"

    # Frontend Web
    check "Frontend Web (:80)" \
        "${FRONTEND}/" "200"
}

check_eureka_registry() {
    print_section "5. Eureka — servicios registrados"

    local registered
    registered=$(curl -s --max-time "$TIMEOUT" \
        -H "Accept: application/json" \
        "${EUREKA}/eureka/apps" 2>/dev/null | \
        grep -o '"app":"[^"]*"' | wc -l | tr -d ' ') || registered=0

    if [ "$registered" -ge 7 ]; then
        echo -e "  ${GREEN}${PASS}${RESET}  $registered servicios registrados en Eureka (esperado ≥7)"
        PASS_COUNT=$((PASS_COUNT + 1))
    else
        echo -e "  ${YELLOW}${SKIP}${RESET}  $registered servicios registrados en Eureka (esperado ≥7)"
        echo -e "            Eureka puede estar aún inicializando — espera 30s y reintenta"
        FAIL_COUNT=$((FAIL_COUNT + 1))
    fi
    TOTAL=$((TOTAL + 1))
}

print_summary() {
    echo ""
    echo -e "${BOLD}============================================================${RESET}"
    echo -e "${BOLD}  RESUMEN${RESET}"
    echo -e "${BOLD}============================================================${RESET}"
    echo ""
    echo -e "  Tests ejecutados : $TOTAL"
    echo -e "  ${GREEN}Pasaron${RESET}          : $PASS_COUNT"
    echo -e "  ${RED}Fallaron${RESET}         : $FAIL_COUNT"
    echo ""

    if [ "$FAIL_COUNT" -eq 0 ]; then
        echo -e "  ${GREEN}${BOLD}¡Stack listo para demo! ✅${RESET}"
        echo ""
        echo -e "  Web-admin : ${BOLD}http://localhost${RESET}"
        echo -e "  Credenciales demo:"
        echo -e "    admin@dch.mx    / Admin2026    (Administrador Master)"
        echo -e "    rrhh@dch.mx     / Rrhh2026     (RRHH)"
        echo -e "    empleado@dch.mx / Emp2026      (Empleado)"
        echo ""
    else
        echo -e "  ${RED}${BOLD}Stack no listo — revisa los servicios fallidos arriba.${RESET}"
        echo ""
        echo -e "  Comandos útiles:"
        echo -e "    Ver logs:         docker-compose logs -f <servicio>"
        echo -e "    Reiniciar todo:   docker-compose restart"
        echo -e "    Reconstruir:      docker-compose up -d --build"
        echo ""
    fi
    echo -e "${BOLD}============================================================${RESET}"
    echo ""
}

# ---------------------------------------------------------------------------
# Main
# ---------------------------------------------------------------------------
print_header
check_docker_running
check_containers
check_ports
check_endpoints
check_eureka_registry
print_summary

# Exit code: 0 si todos pasaron, 1 si alguno falló
[ "$FAIL_COUNT" -eq 0 ] && exit 0 || exit 1
