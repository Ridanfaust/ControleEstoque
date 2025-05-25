DO '
BEGIN
IF NOT EXISTS (SELECT FROM information_schema.schemata WHERE schema_name = ''estoque'') THEN
    EXECUTE ''CREATE SCHEMA estoque'';
    END IF;
END;
'
