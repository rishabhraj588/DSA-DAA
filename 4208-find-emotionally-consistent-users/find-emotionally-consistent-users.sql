WITH total AS (
    SELECT
        user_id,
        COUNT(*) AS total_reactions
    FROM reactions
    GROUP BY user_id
    HAVING COUNT(*) >= 5
),

reaction_count AS (
    SELECT
        user_id,
        reaction,
        COUNT(*) AS cnt
    FROM reactions
    GROUP BY user_id, reaction
)

SELECT
    r.user_id,
    r.reaction AS dominant_reaction,
    ROUND(r.cnt / t.total_reactions, 2) AS reaction_ratio
FROM reaction_count AS r
INNER JOIN total AS t
    ON r.user_id = t.user_id
WHERE r.cnt / t.total_reactions >= 0.60
ORDER BY
    reaction_ratio DESC,
    r.user_id ASC;