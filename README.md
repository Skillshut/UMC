#  Итог

* Создана сущность EventPgEntity с полями id, name, startTime, endTime.
* Написаны скрипты создания таблиц event с типом ID BIGSERIAL и venue_event, реализующей связь между таблицами venue и event.
* Mutation: добавление нового события с указанием venueReferenceId, name, startTime, endTime.
* Query: получение списка площадок (Venue) с последними событиями (Event) c указанием лимита;
* Query: получение списка событий с указанием площадки
* Реализованы тесты проверки запросов GraphQl с помощью DgsQueryExecutor