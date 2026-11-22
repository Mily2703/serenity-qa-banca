# Serenity QA Banca

Automatización de pruebas **web, API REST y SOAP** sobre un banco de demostración
([ParaBank](https://parabank.parasoft.com)), usando **Serenity BDD**, el patrón **Screenplay**
y escenarios **Cucumber escritos en español**.

El proyecto cubre los flujos típicos de una banca digital: registro de clientes, apertura de
cuentas, transferencias entre cuentas propias y consultas de saldo, validando tanto la interfaz
web como los servicios que la soportan.

## Stack

| Capa | Herramientas |
|---|---|
| Lenguaje y build | Java 17+, Maven (incluye Maven Wrapper) |
| Framework | Serenity BDD 4.2, Screenplay, Cucumber 7 (Gherkin en español), JUnit 5 |
| Web | Selenium WebDriver (Chrome headless) vía `serenity-screenplay-webdriver` |
| API | REST Assured vía `serenity-screenplay-rest` (REST con JSON y SOAP 1.1 con XML) |
| Aserciones | Serenity Ensure, AssertJ |
| CI | GitHub Actions: ejecuta la suite y publica el reporte de Serenity |

## Cobertura

| Feature | Escenarios |
|---|---|
| `web/banca_por_internet.feature` | Registro de cliente, apertura de cuenta de ahorros, transferencia entre cuentas propias, transferencia sin monto (defecto conocido) |
| `web/inicio_de_sesion.feature` | Inicio de sesión sin credenciales |
| `api/cuentas_rest.feature` | Consulta de cuentas, apertura + transferencia con validación de saldo, cuentas inexistentes (Scenario Outline) |
| `api/cuentas_soap.feature` | `login`, `getAccounts`/`getAccount`, `createAccount` + `transfer`, SOAP Fault por cuenta inexistente |

## Estructura (Screenplay)

```
src/test/java/pe/mtongo/qa
├── CucumberTestSuite.java        # Runner JUnit 5 + Cucumber
├── config/Entorno.java           # URLs y credenciales por ambiente (serenity.conf)
├── hooks/                        # Prepara el "Stage" de Screenplay por escenario
├── web/
│   ├── ui/                       # Targets (localizadores) por pantalla
│   ├── tasks/                    # Tareas de negocio: Registrarse, AbrirCuenta, Transferir...
│   ├── questions/                # Preguntas sobre el estado de la pantalla
│   └── model/                    # Datos de prueba (cliente único por ejecución)
├── api/
│   ├── rest/                     # Operaciones REST del servicio /bank
│   └── soap/                     # Constructor de sobres SOAP y lectura de elementos XML
└── stepdefinitions/              # Glue entre Gherkin y Screenplay
src/test/resources
├── features/{web,api}/*.feature  # Escenarios en español
└── serenity.conf                 # Navegador, timeouts y ambientes
```

## Cómo ejecutar

Requisitos: Java 17 o superior y Google Chrome. No es necesario instalar Maven.

```bash
# Suite completa (web + REST + SOAP) y reporte de Serenity
./mvnw clean verify            # Windows: mvnw.cmd clean verify

# Solo un grupo de pruebas, usando tags
./mvnw clean verify -Dtags="@api"
./mvnw clean verify -Dtags="@soap"
./mvnw clean verify -Dtags="@smoke"
```

El reporte queda en `target/site/serenity/index.html`, con el detalle paso a paso,
las peticiones y respuestas de cada API y las capturas de pantalla de los fallos.

## Decisiones de diseño

- **Datos aislados:** cada escenario web registra un cliente nuevo y los escenarios de API abren
  sus propias cuentas antes de transferir. Así las pruebas no dependen de saldos que otros
  usuarios del sitio demo modifican.
- **Esperas explícitas:** ParaBank carga las cuentas por AJAX y procesa algunos depósitos de forma
  asíncrona. Las pruebas esperan a que los elementos o los saldos estén listos, en lugar de usar
  pausas fijas.
- **Un solo ambiente configurable:** las URLs viven en `serenity.conf`, así que apuntar la suite a
  otro ambiente (QA, UAT) no requiere tocar el código.

## Defecto encontrado

**BUG-001: la transferencia sin monto no muestra la validación al usuario.**

| | |
|---|---|
| Pantalla | Transfer Funds (`/parabank/transfer.htm`) |
| Pasos | 1) Iniciar sesión. 2) Ir a *Transfer Funds*. 3) Dejar *Amount* vacío. 4) Clic en *Transfer*. |
| Resultado esperado | Se muestra *"The amount cannot be empty."* y no se envía la operación. |
| Resultado obtenido | La petición se envía al servidor con `amount=` vacío y la pantalla muestra *"Error! An internal error has occurred and has been logged."* |
| Causa | El mensaje existe en el HTML, pero el JavaScript del formulario nunca valida el monto: solo oculta los errores (`resetErrors`) y envía la petición. |
| Severidad | Media: no hay pérdida de dinero, pero el usuario recibe un error genérico en lugar de una guía. |

El escenario `Transferencia sin ingresar monto` documenta el comportamiento esperado y está
marcado con `@defecto-conocido`. Se excluye de la ejecución normal para que la integración
continua siga reflejando el estado real del resto de la suite. Para reproducirlo:

```bash
./mvnw clean verify -Dtags="@defecto-conocido"
```

## Próximos pasos

- Pruebas de rendimiento de los servicios REST con JMeter.
- Integración de resultados con Jira + Xray.
- Pipeline equivalente en Azure DevOps.
