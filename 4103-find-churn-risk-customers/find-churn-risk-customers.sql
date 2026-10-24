# Write your MySQL query statement below
WITH ranked_events AS (
    SELECT
        user_id,
        event_date,
        event_type,
        plan_name,
        monthly_amount,
        ROW_NUMBER() OVER (
            PARTITION BY user_id
            ORDER BY event_date DESC, event_id DESC
        ) AS rn
    FROM subscription_events
),
user_summary AS (
    SELECT
        user_id,
        MIN(event_date) AS first_date,
        MAX(event_date) AS last_date,
        MAX(monthly_amount) AS max_historical_amount,
        MAX(CASE
            WHEN event_type = 'downgrade' THEN 1
            ELSE 0
        END) AS has_downgrade
    FROM subscription_events
    GROUP BY user_id
)
SELECT
    r.user_id,
    r.plan_name AS current_plan,
    r.monthly_amount AS current_monthly_amount,
    s.max_historical_amount,
    DATEDIFF(s.last_date, s.first_date) AS days_as_subscriber
FROM ranked_events r
JOIN user_summary s
    ON r.user_id = s.user_id
WHERE r.rn = 1
  AND r.event_type <> 'cancel'
  AND s.has_downgrade = 1
  AND r.monthly_amount < 0.5 * s.max_historical_amount
  AND DATEDIFF(s.last_date, s.first_date) >= 60
ORDER BY
    days_as_subscriber DESC,
    r.user_id ASC;