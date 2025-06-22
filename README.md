# Kafka-Payment-Processing-Problem
Spring Boot service to Ensure a payment is processed exactly once — never duplicated — using Kafka's idempotent producer + transaction support.


STEPS TO TEST THE TASK
========================

# 1. Docker containers ko build aur start karein
docker compose up -d --build

# 2. Check karein ki saare containers chal rahe hain
docker compose ps

# Postgres container ke andar login karein
docker exec -it <postgres-container-name> psql -U postgres -d payments

Database Check:
# Jab psql prompt aaye (payments=#), yeh command chalayein
SELECT * FROM payment;

Kafka payments.processed Topic Check:
docker exec -it <kafka-container-name> kafka-console-consumer --bootstrap-server localhost:9092 --topic payments.processed --from-beginning

Action 2: Payment Initiate Karna
curl -X POST http://localhost:8080/payments \
-H "Content-Type: application/json" \
-d '{"userId": "user-123", "amount": 99.99}'

Application Logs Check karein:
docker-compose logs -f app

Database Check karein:
selcet * from payment;
