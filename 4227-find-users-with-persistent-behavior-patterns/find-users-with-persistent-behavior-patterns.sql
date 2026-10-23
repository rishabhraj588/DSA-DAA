WITH daily_activity AS (
    SELECT
        user_id,
        action_date,
        MAX(action) AS action
    FROM activity
    GROUP BY user_id, action_date
    HAVING COUNT(*) = 1
),
numbered AS (
    SELECT
        user_id,
        action_date,
        action,
        ROW_NUMBER() OVER (
            PARTITION BY user_id, action
            ORDER BY action_date
        ) AS rn
    FROM daily_activity
),
streak_groups AS (
    SELECT
        user_id,
        action_date,
        action,
        DATE_SUB(action_date, INTERVAL rn DAY) AS grp
    FROM numbered
),
streaks AS (
    SELECT
        user_id,
        action,
        COUNT(*) AS streak_length,
        MIN(action_date) AS start_date,
        MAX(action_date) AS end_date
    FROM streak_groups
    GROUP BY user_id, action, grp
    HAVING COUNT(*) >= 5
),
ranked AS (
    SELECT
        *,
        ROW_NUMBER() OVER (
            PARTITION BY user_id
            ORDER BY
                streak_length DESC,
                action ASC,
                start_date DESC
        ) AS rnk
    FROM streaks
)
SELECT
    user_id,
    action,
    streak_length,
    start_date,
    end_date
FROM ranked
WHERE rnk = 1
ORDER BY streak_length DESC, user_id ASC;