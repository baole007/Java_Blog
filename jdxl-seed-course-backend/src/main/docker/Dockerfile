FROM  apache/skywalking-base:8.1.0-es7
VOLUME /tmp

ENV SERVICE_NAME="jdxl-seed-course-backend-1.0.0-SNAPSHOT"

ADD ${SERVICE_NAME}.jar app.jar

ENTRYPOINT java -javaagent:/skywalking/agent/skywalking-agent.jar=agent.service_name=${SERVICE_NAME},collector.backend_service=${AGENT_COLLECTOR_ADDRESS} -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} -jar /app.jar