package enums;

public enum Command {
    CREATE, DROP, ALTER, TRUNCATE,
    INSERT, UPDATE, DELETE, CALL, EXPLAIN, LOCK, USE,
    COMMIT, SAVEPOINT, ROLLBACK, SET,
    GRANT, REVOKE,
    SELECT, SHOW;

    @Override
    public String toString() {
        return this.name();
    }
}
