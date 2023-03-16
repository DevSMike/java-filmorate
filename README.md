# java-filmorate
## ER - диаграмма базы данных проекта Filmorate:
![This is an image](https://i.ibb.co/MDMxxHY/filmorate-dbd.png)

*Пример решения задачи поиска самого высокооцененного фильма путем составления SQL запроса*
```sql
SELECT name
FROM film
WHERE id = (SELECT  film_id
            FROM film_likes
            GROUP by film_id
            ORDER BY MAX(user_id) DESC);
```


