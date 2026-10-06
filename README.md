# Building AI Agents on the JVM with Embabel

Code repository for the **EngineerInAI YouTube series** — building a loan approval AI agent from scratch using [Embabel](https://github.com/embabel/embabel-agent), Kotlin, and Spring Boot.

## 📺 The Series

| Episode | Branch | What we build |
|---|---|---|
| EP01 — What is Embabel | `ep01-intro` | Framework intro (slides only) |
| EP02 — Project Setup | `ep02-setup` | Spring Boot + Embabel running |
| EP03 — Domain Model | `ep03-domain-model` | `data class` LoanApplication, CreditScore |
| EP04 — First @Action | `ep04-first-action` | evaluateCredit action |
| EP05 — Blackboard | `ep05-blackboard` | Debug the blackboard live |
| EP06 — Chaining Actions | `ep06-chaining` | assessRisk, planner sequences automatically |
| EP07 — @AchievesGoal | `ep07-goal` | Complete agent end to end |
| EP08 — Testing | `ep08-testing` | Unit tests without calling a real LLM |

## 🎞️ Slide Decks

All episode slide decks are in the [`slides/`](slides/) folder.
Open [`slides/index.html`](slides/index.html) locally to browse all episodes.

## 🚀 Quick Start

```bash
# Clone the repo
git clone https://github.com/sumitvairagar/embabel-loan-agent

# Checkout a specific episode
git checkout ep04-first-action

# Set your OpenAI key
export OPENAI_API_KEY=your-key-here

# Run
./mvnw spring-boot:run
```

## 📋 Prerequisites

- Java 21+
- Maven 3.9+
- OpenAI API key

## 🔗 Links

- [YouTube Channel — EngineerInAI](https://youtube.com/@EngineerInAI)
- [Embabel Framework](https://github.com/embabel/embabel-agent)
- [Embabel Docs](https://docs.embabel.com)
