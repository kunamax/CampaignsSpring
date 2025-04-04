# CampaignsSpring - Aplikacja do zarządzania kampaniami

![Java](https://img.shields.io/badge/Java-17%2B-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green)
![React](https://img.shields.io/badge/React-18.x-%2361DAFB)
![Database](https://img.shields.io/badge/Database-H2-orange)

## 📦 Wymagania wstępne
- Java 17+
- Maven 3.8+ lub Gradle 7.5+
- Node.js 16+ i npm/yarn (dla frontendu)
- H2 Database

## Projekt działa na Javie 21, w razie potrzeby zmiany na Java 17
```bash
cd CampaignsSpring/CampaignsSpring/
```
```java
// build.gradle
java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21) // 17
	}
}
```

## 🚀 Szybki start

Backend (Spring Boot)

# 1. Wejdź do projektu
```bash
cd CampaignsSpring
```

# 2. Uruchom z Maven
```bash
mvn clean install
mvn spring-boot:run
```

# 3. Albo uruchom z Gradle(polecane)
```bash
./gradlew build
./gradlew bootRun
```

# 4. Aplikacja będzie dostępna pod:
http://localhost:8080

Swagger UI: http://localhost:8080/swagger-ui/index.html

Frontend (React)

# 1. Przejdź do folderu frontend
```bash
cd frontend/campaign-manager
```

# 2. Zainstaluj zależności
```bash
npm install
```

# 3. Uruchom aplikację
```bash
npm start
```

# 4. Aplikacja będzie dostępna pod:
 http://localhost:3000

🌐 Główne endpointy API widoczne pod adresem Swaggera

Wersja Live
## Pomiędzy trazakcjami należy odświeżać stronę oraz wybierać ponownie użytkownika dla dynamicznie odświeżanych funduszy

<img width="1639" alt="Zrzut ekranu 2025-04-4 o 21 44 38" src="https://github.com/user-attachments/assets/ce4e9770-4ae6-441c-91e7-80d19dd6f485" />
<img width="1639" alt="Zrzut ekranu 2025-04-4 o 21 45 32" src="https://github.com/user-attachments/assets/a11637bc-a269-4261-a3c8-a4b2fa1721aa" />
<img width="1642" alt="Zrzut ekranu 2025-04-4 o 21 45 45" src="https://github.com/user-attachments/assets/751e477f-85b7-4dc2-b942-d4fec62b0a1f" />
<img width="1640" alt="Zrzut ekranu 2025-04-4 o 21 46 28" src="https://github.com/user-attachments/assets/9b4511e5-aec5-4b42-a8a7-b365e12d1577" />
<img width="1639" alt="Zrzut ekranu 2025-04-4 o 21 47 04" src="https://github.com/user-attachments/assets/49e48e50-77d4-4607-abaf-cc577ab522f5" />
<img width="1641" alt="Zrzut ekranu 2025-04-4 o 21 49 39" src="https://github.com/user-attachments/assets/c221a035-0bd5-419a-9f0a-d80268baba3e" />





