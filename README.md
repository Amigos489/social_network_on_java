# social\_network\_on\_java



## Что нужно установить



\- JDK 17+

\- Maven 3+

\- Docker Desktop

\-PostgreSQL



Основной Maven-проект находится в папке:

```text

social_network

```

SQL-скрипты для создания БД находятся в папке:

```text

init_db

```




## Запуск 



создать в Postgres базу данных и выполнить скрипты из папки init\_db



запустить Docker и выполнить  docker compose up --build



также можно запустить с помощью контейнера сервлетов Tomcat версии 10.1.52 



предварительно собрав приложение в WAR выполнив команду mvn clean package



тесты можно запустить выполнив команду mvn test







