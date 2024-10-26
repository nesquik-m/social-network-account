services:
  
  frontend:
    restart: always
#    image: nesquikam1/social-network-frontend
    image: nesquikam1/social-network-frontend-local
    container_name: frontend
    ports:
      - "8080:80"
    networks:
      - social-network

  rabbitmq:
    restart: always
    image: itzg/rabbitmq-stomp
    container_name: rabbitmq
    ports:
      - "5672:5672"  # RabbitMQ port
      - "15672:15672" # RabbitMQ Management UI
      - "61613:61613"
    networks:
      - social-network

  zookeeper:
    restart: always
    image: confluentinc/cp-zookeeper:7.5.0
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000
    ports:
      - "2181:2181"
    networks:
      - social-network

  kafka:
    restart: always
    image: confluentinc/cp-kafka:7.5.0
    depends_on:
      - zookeeper
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_LISTENERS: INSIDE://0.0.0.0:9092,OUTSIDE://0.0.0.0:9093
#      KAFKA_ADVERTISED_LISTENERS: INSIDE://kafka:9092,OUTSIDE://89.111.174.153:9093
      KAFKA_ADVERTISED_LISTENERS: INSIDE://kafka:9092,OUTSIDE://localhost:9093
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: INSIDE:PLAINTEXT,OUTSIDE:PLAINTEXT
      KAFKA_LISTENER_NAME_REPLICATION_FACTOR: 1
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
      KAFKA_AUTO_CREATE_TOPICS_ENABLE: "true"
      KAFKA_INTER_BROKER_LISTENER_NAME: INSIDE
    ports:
      - "9092:9092"  # Для подключения из других контейнеров в Docker сети
      - "9093:9093"  # Для подключения из хост-машины
    networks:
      - social-network

  kafka-ui:
    restart: always
    image: provectuslabs/kafka-ui:latest
    container_name: kafka-ui
    depends_on:
      - kafka
    ports:
      - "8081:8080"  # Доступ к Kafka UI через порт 8081 на хост-машине
    environment:
      KAFKA_CLUSTERS_0_NAME: local
      KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS: kafka:9092  # Используем сетевое имя контейнера
    networks:
      - social-network

  postgres:
    restart: always
    image: postgres:latest
    ports:
      - "5432:5432"
    environment:
      - POSTGRES_USER=postgres
      - POSTGRES_PASSWORD=postgres
    volumes:
      - postgres_data:/var/lib/postgresql/data
    networks:
      - social-network

  redis:
    restart: always
    image: mirror.gcr.io/redis
    ports:
      - "6379:6379"
    networks:
      - social-network

volumes:
  postgres_data:


networks:
  social-network:
    driver: bridge
