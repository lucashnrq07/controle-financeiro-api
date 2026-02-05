-- Migration para adicionar e corrigir a coluna 'origin' na tabela movements

-- 1️⃣ Adiciona a coluna caso não exista
DO $$
BEGIN
   IF NOT EXISTS (
      SELECT 1
      FROM information_schema.columns
      WHERE table_name='movements'
      AND column_name='origin'
   ) THEN
      ALTER TABLE movements ADD COLUMN origin VARCHAR(10);
   END IF;
END$$;

-- 2️⃣ Preenche registros antigos
-- Movimentos de meta
UPDATE movements
SET origin = 'GOAL'
WHERE description ILIKE '%meta%'
   OR goal_id IS NOT NULL;

-- Movimentos normais
UPDATE movements
SET origin = 'NORMAL'
WHERE origin IS NULL;

-- 3️⃣ Define valor padrão para novos registros
ALTER TABLE movements
ALTER COLUMN origin SET DEFAULT 'NORMAL';

-- 4️⃣ Só agora marca a coluna como NOT NULL
ALTER TABLE movements
ALTER COLUMN origin SET NOT NULL;
