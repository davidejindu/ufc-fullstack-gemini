# 🥊 UFC AI Predictor

A full-stack web application that compares UFC fighters and predicts fight outcomes using Google's Gemini API. Built with a modern React + Spring Boot stack and deployed using cloud-native services.

---

## ⚙️ Tech Stack

| Layer         | Technology/Service                                      |
|---------------|----------------------------------------------------------|
| **Frontend**  | React (Vite), Tailwind CSS, Vercel                       |
| **Backend**   | Java 17, Spring Boot, Spring WebFlux, Render            |
| **Database**  | PostgreSQL (Render-managed)                             |
| **Storage**   | AWS S3 (for fighter images)                             |
| **AI/ML**     | Google Gemini API (fight outcome predictions)           |
| **Migration** | DBeaver, CSV, `psql` (MySQL → PostgreSQL)               |
| **CI/CD**     | GitHub → Vercel (frontend), GitHub → Render (backend)   |

---

## 🚀 Features

- 🔍 **Compare Fighters** – Search and view fighter stats side-by-side
- 🧠 **AI Predictions** – Generate real-time fight outcomes using Google Gemini API
- 🖼️ **Image Integration** – Fighter photos stored in AWS S3 and served dynamically
- 🌐 **Cloud Deployment** – Full deployment across Vercel and Render with CI/CD
- 🔒 **Secure Config** – API keys and DB credentials managed via environment variables

---
## 🚀 Live Demo

- 🌐 **Frontend**: [https://ufc-fullstack-gemini.vercel.app](https://ufc-fullstack-gemini.vercel.app)  
- 🔗 **Backend Base API**: [https://ufc-fullstack-gemini.onrender.com/fighters](https://ufc-fullstack-gemini.onrender.com/fighters)

> ⚠️ **Note**: The backend is hosted on Render’s free tier. It may take **20–30 seconds** to respond if it has been idle (cold start). Please be patient on first load.

