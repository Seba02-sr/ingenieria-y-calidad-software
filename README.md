# Trabajo Práctico Integrador — Sistemas de Gestión de la Configuración

Proyecto base: API de Contactos (Spring Boot Java 21) + Front estático mínimo.
Este repo sirve para realizar todo el TP usando GitHub y Gitflow entre 4 personas.

## Objetivos del TP
- Configurar control de versiones en GitHub con Gitflow.
- Subir el código, ignorar archivos locales, hacer cambios y releases.
- Manejar bugfixes en producción (hotfix) y nuevas features (feature branches).
- Documentar con README y Pull Requests.

## Cómo correr el proyecto
- Backend + front integrados: `mvn -f demo/pom.xml spring-boot:run` y abrir `http://localhost:8080/`.
- La UI consume la API en `/api/contacts`.

## Gitflow (ramas)
- `main` (producción)
- `develop` (integración)
- `feature/<nombre>` (nueva funcionalidad)
- `release/<versión>` (preparación de release)
- `hotfix/<versión>` (corrección urgente en prod)

## Pasos mínimos sugeridos (con comandos)
1) Inicializar y primer push
- `git checkout -b develop`
- `git add . && git commit -m "chore: bootstrap contacts app" && git push -u origin develop`

2) Asegurar ignores (no subir configs locales)
- Confirmar `.gitignore` en raíz y `demo/.gitignore` (IDE, `target/`, etc.).

3) Preparar Release 1
- `git checkout -b release/1.0.0`
- Ajustes mínimos (nombres, README, front usando `/api/contacts`).
- `git commit -m "release: prep 1.0.0"`

4) Subir a producción Release 1
- `git checkout main && git merge --no-ff release/1.0.0 -m "release: 1.0.0"`
- `git tag -a v1.0.0 -m "Release 1.0.0" && git push origin main --tags`
- `git checkout develop && git merge --no-ff release/1.0.0 -m "chore: merge back 1.0.0" && git push`

5) Corregir un error en producción (Hotfix)
- `git checkout -b hotfix/1.0.1 main`
- Arreglar el problema, `git commit -m "hotfix: <resumen>"`
- `git checkout main && git merge --no-ff hotfix/1.0.1 -m "hotfix: 1.0.1" && git tag -a v1.0.1 -m "Hotfix 1.0.1" && git push origin main --tags`
- `git checkout develop && git merge --no-ff hotfix/1.0.1 -m "chore: merge hotfix 1.0.1" && git push`

6) Nueva funcionalidad (Feature)
- `git checkout -b feature/<nombre> develop`
- Implementar, `git commit -m "feat: <resumen>" && git push`
- Pull Request → merge a `develop`.

## Distribución de tareas (4 personas — ejemplo)
- Persona A (Repo/Release):
  - Crear repo, configurar `.gitignore`, armar `develop`, cortar `release/1.0.0`, merges y tags.
- Persona B (Frontend):
  - Ajustar front a `const API = '/api/contacts'`, mejoras de UI (búsqueda, mensajes), README de ejecución.
- Persona C (Backend API):
  - Controlador y servicio (CRUD en memoria), validación de entrada, endpoints extra (por ejemplo export CSV).
- Persona D (Flujo PR/Docs):
  - Plantilla de PR, completar README (Gitflow, cómo contribuir), revisar PRs y checklist.

Nota: El modelo `Contact` puede iniciarse vacío y desarrollarse como una feature (`feature/contact-model`) para repartir mejor el trabajo.

## Ideas de features pequeñas (para dividir)
- Exportar contactos en CSV: `GET /api/contacts/export` (`text/csv`).
- Búsqueda por query param `q` (ya existe; se puede mejorar por campos específicos).
- Ordenamiento opcional `?sort=desc`.
- Validaciones más estrictas (name obligatorio, email válido).
- Persistencia en memoria → (opcional) migrar a BD real en un branch aparte.

## Pull Requests (qué pedimos y cómo usamos GitHub)
- Incluir: contexto del cambio, issue/enlace, tipo (feat/fix/chore/docs), pruebas realizadas, pasos de verificación, riesgos y plan de rollback.
- Usar: PR template, reviewers, status checks (build), branch protection en `main`, tags/releases.

## Qué se versiona y qué no
- Sí: código (`demo/**`), `pom.xml`, front estático, scripts útiles, README.
- No: `**/target/`, `**/build/`, archivos de IDE (`.idea/`, `.vscode/`, `*.iml`), archivos de SO (`.DS_Store`), logs/temporales, secretos (`.env*`).

---
Cualquier duda sobre los pasos o si quieren que prearmemos las ramas y el PR template, avisen.
