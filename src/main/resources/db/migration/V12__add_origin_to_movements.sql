ALTER TABLE movements
ADD COLUMN origin VARCHAR(10);

UPDATE movements
SET origin = 'GOAL'
WHERE description LIKE '%meta%'
   OR goal_id IS NOT NULL;

UPDATE movements
SET origin = 'NORMAL'
WHERE origin IS NULL;

ALTER TABLE movements
MODIFY origin VARCHAR(10) NOT NULL;
