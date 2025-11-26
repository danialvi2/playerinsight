# PlayerInsight

PlayerInsight er et lokalt kjørende AI-basert analysesystem som simulerer spilltelemetri og analyserer spilleradferd.

Systemet klassifiserer hendelser (som *player jumped*, *player missed target*, *player paused*) til emosjonelle tilstander:  
**focused**, **frustrated**, og **idle**.

---

## Teknologi
- **Python (Flask)** – AI-modul for tekstklassifisering
- **Java (Spring Boot)** – Backend for dataflyt, lagring og rapportering
- **PostgreSQL** – Lagring av analyseresultater
- **Gradle** – Byggverktøy

---

## Kjør prosjektet lokalt

### 1️. Flask AI-modul
```bash
cd ai
py app.py
```
### 2. Spring Boot-backend
```bash
./gradlew bootRun
```
---

## API-endepunkter
| Endpoint                            | Metode | Beskrivelse                              |
| ----------------------------------- |--------| ---------------------------------------- |
| `/api/ai/analyze/file`                | GET    | Kjører analyse på datasettet       |
| `/api/report/player	`                   | GET    | Viser rapport per spiller             |
| `/api/report/session `                       | GET    | Viser rapport per økt |

---

## EksempelResultat

```
{
  "total_players": 10,
  "players": [
    {
      "player_id": "player_1",
      "label_distribution": { "focused": 28, "frustrated": 15, "idle": 7 },
      "overall_average_score": 0.81
    }
  ]
}
```
---

## Formål
Prosjektet demonstrerer hvordan spill- og simulatorlogger kan brukes til å oppdage mønstre i spilleradferd.
Dette kan hjelpe med å balansere vanskelighetsgrad, forbedre brukeropplevelse, og forutsi frustrasjon under testing.

---
 Danial Alvi

---
