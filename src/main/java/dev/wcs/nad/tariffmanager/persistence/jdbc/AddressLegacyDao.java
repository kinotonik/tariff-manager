package dev.wcs.nad.tariffmanager.persistence.jdbc;


import dev.wcs.nad.tariffmanager.persistence.entity.Address;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
public class AddressLegacyDao {

    private final DataSource dataSource;

    // DataSource is configured by Spring in application.properties and injected during Context setup.
    public AddressLegacyDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Optional<Address> getByIdJava7Syntax(long id) {
        // Challenge: Add the retrieval of the Address ResultSet and the Mapping to an instance of Address here.
        try (Connection connection = dataSource.getConnection();
            /*
             Create the Statement object
             The preparedStatement() method of Connection interface is used to create statement. The object of statement is responsible to execute queries with the database.

             // NOTE
             // For security reasons: Always use PreparedStatements, not Statement
             */
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ADDRESS WHERE ID=?")) {
            stmt.setLong(1, id);
            /*
             Execute the query
             The executeQuery() method of Statement interface is used to execute queries to the database. This method returns the object of ResultSet that can be used to get all the records of a table.
             */
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    /*
                     Read results from ResultSet
                     */

                    Address address = new Address();
                    address.setId(id);
                    address.getCity();
                    address.getPostalcode();
                    address.getStreet();
                    address.getNumber();
                    return Optional.of(address);
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return Optional.empty();
    }

}
