# java-filmorate
## ER - диаграмма базы данных проекта Filmorate:
![This is an image](https://i.ibb.co/DkJS1hN/filmorate-dbd.png)

*Пример решения задачи поиска самого высокооцененного фильма путем составления SQL запроса*
```sql
SELECT name
FROM film
WHERE film_id = (  SELECT film_id 
                   FROM film_likes
                   GROUP BY film_id
                   ORDER BY COUNT(user_id) desc
                   LIMIT 1);
```


