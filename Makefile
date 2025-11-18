# ==================== MAKEFILE SIMPLE PARA MICROSERVICIOS ====================

# Comando de docker compose (ajusta si usas "docker-compose" clásico)
COMPOSE ?= docker compose

# Servicios
# ==================== CAMBIO 1: Añadir gateway-service ====================
SERVICES       = gateway-service matricula-service auth-service event-dispatcher-service notification-service kafka-service
# ========================================================================
INFRASTRUCTURE = matricula-db auth-db rabbitmq zookeeper kafka kafka-ui

.DEFAULT_GOAL := help

# ==================== HELP ====================

.PHONY: help
help: ## Mostrar esta ayuda
	@echo "========================================="
	@echo "  Microservicios - Comandos Make"
	@echo "========================================="
	@echo ""
	@echo "Comandos:"
	@grep -E '^[a-zA-Z0-9_%-]+:.*##' $(MAKEFILE_LIST) \
	 | sed -e 's/:.*##/:/' \
	 | awk 'BEGIN {FS=":"} {printf "  %-18s %s\n", $$1, $$2}'
	@echo ""
	@echo "Acrónimos de servicios:"
# ==================== CAMBIO 2: Añadir acrónimo gw ====================
	@echo "  gw    - gateway-service"
# ====================================================================
	@echo "  mat   - matricula-service"
	@echo "  auth  - auth-service"
	@echo "  eds   - event-dispatcher-service"
	@echo "  notif - notification-service"
	@echo "  ksvc  - kafka-service"

# ==================== COMANDOS PRINCIPALES ====================

.PHONY: up
up: ## Levantar infraestructura + microservicios
	$(COMPOSE) up -d $(INFRASTRUCTURE)
	@echo "Esperando 30 segundos para que arranque la infraestructura..."
	sleep 30
	$(COMPOSE) up -d $(SERVICES)
	$(COMPOSE) ps

.PHONY: down
down: ## Detener y eliminar todos los contenedores
	$(COMPOSE) down

.PHONY: restart
restart: ## Reiniciar todos los servicios (sin recrear)
	$(COMPOSE) restart

.PHONY: ps
ps: ## Mostrar estado de los contenedores
	$(COMPOSE) ps

.PHONY: build
build: ## Construir todas las imágenes
	$(COMPOSE) build

.PHONY: rebuild
rebuild: ## Reconstruir todas las imágenes sin caché
	$(COMPOSE) build --no-cache

.PHONY: dev
dev: clean build up ## Limpiar, construir y levantar (workflow de desarrollo)

# ==================== LOGS ====================

# ==================== CAMBIO 3: Añadir logs-gw ====================
.PHONY: logs logs-svc logs-gw logs-mat logs-auth logs-eds logs-notif logs-ksvc

logs: ## Ver logs de todos los servicios
	$(COMPOSE) logs -f

# Solo para que aparezca una línea compacta en el help
logs-svc: ## Ver logs de un servicio (logs-gw, logs-mat, logs-auth, logs-eds, logs-notif, logs-ksvc)
	@echo "Usa: make logs-<acrónimo>"
	@echo "Ejemplos: logs-gw, logs-mat, logs-auth, logs-eds, logs-notif, logs-ksvc"

logs-gw:
	$(COMPOSE) logs -f gateway-service
# ==================================================================

logs-mat:
	$(COMPOSE) logs -f matricula-service

logs-auth:
	$(COMPOSE) logs -f auth-service

logs-eds:
	$(COMPOSE) logs -f event-dispatcher-service

logs-notif:
	$(COMPOSE) logs -f notification-service

logs-ksvc:
	$(COMPOSE) logs -f kafka-service

# ==================== RESTART POR SERVICIO ====================

# ==================== CAMBIO 4: Añadir restart-gw ====================
.PHONY: restart-svc restart-gw restart-mat restart-auth restart-eds restart-notif restart-ksvc

# Línea compacta para el help
restart-svc: ## Reiniciar un servicio (restart-gw, restart-mat, restart-auth, restart-eds, restart-notif, restart-ksvc)
	@echo "Usa: make restart-<acrónimo>"

restart-gw:
	$(COMPOSE) restart gateway-service
# ====================================================================

restart-mat:
	$(COMPOSE) restart matricula-service

restart-auth:
	$(COMPOSE) restart auth-service

restart-eds:
	$(COMPOSE) restart event-dispatcher-service

restart-notif:
	$(COMPOSE) restart notification-service

restart-ksvc:
	$(COMPOSE) restart kafka-service

# ==================== REBUILD POR SERVICIO ====================

# ==================== CAMBIO 5: Añadir rebuild-gw ====================
.PHONY: rebuild-svc rebuild-gw rebuild-mat rebuild-auth rebuild-eds rebuild-notif rebuild-ksvc

rebuild-svc: ## Reconstruir y recrear un servicio (rebuild-gw, rebuild-mat, rebuild-auth, rebuild-eds, ... )
	@echo "Usa: make rebuild-<acrónimo>"
	@echo "Ejemplos: rebuild-gw, rebuild-mat, rebuild-auth, rebuild-eds, rebuild-notif, rebuild-ksvc"

rebuild-gw: ## Reconstruir gateway-service (docker compose up -d --build)
	$(COMPOSE) up -d --build gateway-service
# ====================================================================

rebuild-mat: ## Reconstruir matricula-service (docker compose up -d --build)
	$(COMPOSE) up -d --build matricula-service

rebuild-auth: ## Reconstruir auth-service (docker compose up -d --build)
	$(COMPOSE) up -d --build auth-service

rebuild-eds: ## Reconstruir event-dispatcher-service (docker compose up -d --build)
	$(COMPOSE) up -d --build event-dispatcher-service

rebuild-notif: ## Reconstruir notification-service (docker compose up -d --build)
	$(COMPOSE) up -d --build notification-service

rebuild-ksvc: ## Reconstruir kafka-service (docker compose up -d --build)
	$(COMPOSE) up -d --build kafka-service

# ==================== LEVANTAR SERVIDOR POR SERVICIO ====================

# ==================== CAMBIO 6: Añadir up-gw ====================
.PHONY: up-svc up-gw up-mat up-auth up-eds up-notif up-ksvc

up-svc: ## Levantar solo un servicio (up-gw, up-mat, up-auth, up-eds, up-notif, up-ksvc)
	@echo "Usa: make up-<acrónimo>"
	@echo "Ejemplos: up-gw, up-mat, up-auth, up-eds, up-notif, up-ksvc"

up-gw: ## Levantar solo gateway-service
	$(COMPOSE) up -d gateway-service
# ====================================================================

up-mat: ## Levantar solo matricula-service
	$(COMPOSE) up -d matricula-service

up-auth: ## Levantar solo auth-service
	$(COMPOSE) up -d auth-service

up-eds: ## Levantar solo event-dispatcher-service
	$(COMPOSE) up -d event-dispatcher-service

up-notif: ## Levantar solo notification-service
	$(COMPOSE) up -d notification-service

up-ksvc: ## Levantar solo kafka-service
	$(COMPOSE) up -d kafka-service

# ==================== INFRA SOLO (OPCIONAL) ====================

.PHONY: infra-up
infra-up: ## Levantar solo infraestructura
	$(COMPOSE) up -d $(INFRASTRUCTURE)

.PHONY: infra-down
infra-down: ## Detener solo infraestructura
	$(COMPOSE) stop $(INFRASTRUCTURE)

.PHONY: services-up
services-up: ## Levantar solo microservicios
	$(COMPOSE) up -d $(SERVICES)

.PHONY: services-down
services-down: ## Detener solo microservicios
	$(COMPOSE) stop $(SERVICES)

# ==================== LIMPIEZA ====================

.PHONY: clean
clean: ## Detener y eliminar contenedores (mantiene volúmenes)
	$(COMPOSE) down

.PHONY: clean-all
clean-all: ## Detener y eliminar todo (incluye volúmenes)
	$(COMPOSE) down -v

# ==================== UTILIDADES ====================

.PHONY: env
env: ## Mostrar variables de entorno (.env)
	@echo "========================================="
	@echo "  Variables de Entorno"
	@echo "========================================="
	@cat .env 2>/dev/null || echo "❌ Archivo .env no encontrado"

.PHONY: urls
urls: ## Mostrar URLs de los servicios
	@echo "========================================="
	@echo "  URLs de Servicios"
	@echo "========================================="
	@echo ""
	@echo "Punto de Entrada Principal (Gateway):"
# ==================== CAMBIO 7: Actualizar URLs ====================
	@echo "  API Gateway:          http://localhost:8080"
	@echo ""
	@echo "Rutas (ejemplos):"
	@echo "  Auth Service:         http://localhost:8080/auth/..."
	@echo "  Matricula Service:    http://localhost:8080/api/matricula/..."
# =================================================================
	@echo ""
	@echo "Infraestructura:"
	@echo "  RabbitMQ Management:  http://localhost:15672 (rabbituser / rabbitpass)"
	@echo "  Kafka UI:             http://localhost:8090"

.PHONY: health
health: ## Verificar health de los servicios básicos
# =V= CAMBIO 8: Verificar el health a través del Gateway =V=
	@echo "Verificando health a través del API Gateway..."
	@echo ""
	@echo "Gateway (Ruta Auth):"
	@curl -s http://localhost:8080/auth/actuator/health || echo "❌ No disponible"
	@echo ""
	@echo "Gateway (Ruta Matrícula):"
	@curl -s http://localhost:8080/api/matricula/actuator/health || echo "❌ No disponible"
# =================================================================
