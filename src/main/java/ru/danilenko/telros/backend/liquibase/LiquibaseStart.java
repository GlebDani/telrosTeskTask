package ru.danilenko.telros.backend.liquibase;

//import liquibase.Liquibase;
//import liquibase.command.CommandScope;
//import liquibase.command.core.UpdateCommandStep;
//import liquibase.command.core.helpers.DbUrlConnectionCommandStep;
//import liquibase.database.Database;
//import liquibase.database.DatabaseFactory;
//import liquibase.database.jvm.JdbcConnection;
//import liquibase.resource.ClassLoaderResourceAccessor;
//import ru.danilenko.util.ConnectionToDB;

import java.sql.Connection;

/**
 * class for implementation Liquibase migration
 */
//public class LiquibaseStart {
//
//
//    /**
//     * method migrates Liquibase xml commands
//     */
//    public static void migration() {
//
//        try {
//
//                Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
//                database.setDefaultSchemaName("support");
//                Liquibase liquibase = new liquibase.Liquibase("db/changelog/changelog.xml", new ClassLoaderResourceAccessor(), database);
//                CommandScope updateCommand = new CommandScope(UpdateCommandStep.COMMAND_NAME)
//                        .addArgumentValue(DbUrlConnectionCommandStep.DATABASE_ARG, liquibase.getDatabase())
//                        .addArgumentValue(UpdateCommandStep.CHANGELOG_FILE_ARG, "changelog/changelog.xml");
//                updateCommand.execute();
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
