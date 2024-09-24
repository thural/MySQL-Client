package enums;

import java.util.Arrays;
import java.util.Set;

import static enums.Command.*;

public enum CommandType {
    DDL(Set.of(CREATE, DROP, ALTER, TRUNCATE)),
    DML(Set.of(INSERT, UPDATE, DELETE, CALL, EXPLAIN, LOCK, USE)),
    TCL(Set.of(COMMIT, SAVEPOINT, ROLLBACK, SET)),
    DQL(Set.of(SELECT, SHOW)),
    DCL(Set.of(GRANT, REVOKE));

    private final Set<Command> commands;


    CommandType(Set<Command> commands) {
        this.commands = commands;
    }

    public Set<Command> getCommands() {
        return commands;
    }

    public boolean isType(String command) {
        return getCommands().contains(Command.valueOf(command.toUpperCase()));
    }

    public CommandType detectType(String command) {
        return Arrays.stream(values()).filter(type -> type.isType(command))
                .findFirst().orElseThrow();
    }

    @Override
    public String toString() {
        return this.name();
    }
}
