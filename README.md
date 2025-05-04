# 🧠 MindMate – Mental Health Chatbot with Sentiment Analysis

MindMate is a Java-based chatbot designed to support mental well-being through intelligent and empathetic conversations. It uses sentiment analysis to detect the user’s emotional state and respond with relevant, compassionate replies.

## 🚀 Features

- 💬 Chat with the bot about your feelings or issues
- 🔍 Sentiment analysis on every user message
- 🧠 Smart, mood-sensitive responses
- 🌐 Backend built with **Spring Boot**
- 🧪 Tested via **Postman**
- 🔐 Hugging Face API integration for NLP

---

## 🛠️ Tech Stack

| Layer     | Tech                        |
|-----------|-----------------------------|
| Language  | Java 17                     |
| Framework | Spring Boot                 |
| API Test  | Postman                     |
| AI/NLP    | Hugging Face Sentiment Model |
| Build Tool| Maven                       |
| IDE       | VS Code                     |

---

## 📁 Project Structure

MindMate/
├── backend/
│ ├── src/main/java/com/mindmate/backend/
│ │ ├── ChatController.java
│ │ └── MindMateApplication.java
│ ├── resources/
│ │ └── application.properties
│ └── pom.xml

## Installation

```bash
git clone https://github.com/Sanjana11092004/MindMate.git
cd MindMate/backend

### ✅ Example API Test – Positive Sentiment

![Positive Test](screenshots/postman-positive.png)

---

### ❌ Example API Test – Negative Sentiment

![Negative Test](screenshots/postman-negative.png)
