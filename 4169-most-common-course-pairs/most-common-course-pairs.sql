WITH top_performers AS (
    SELECT user_id
    FROM course_completions
    GROUP BY user_id
    HAVING COUNT(*) >= 5
       AND AVG(course_rating) >= 4
),
course_sequence AS (
    SELECT
        cc.user_id,
        cc.course_name AS first_course,
        LEAD(cc.course_name) OVER (
            PARTITION BY cc.user_id
            ORDER BY cc.completion_date, cc.course_id
        ) AS second_course
    FROM course_completions cc
    JOIN top_performers tp
        ON cc.user_id = tp.user_id
)
SELECT
    first_course,
    second_course,
    COUNT(*) AS transition_count
FROM course_sequence
WHERE second_course IS NOT NULL
GROUP BY first_course, second_course
ORDER BY
    transition_count DESC,
    first_course ASC,
    second_course ASC;