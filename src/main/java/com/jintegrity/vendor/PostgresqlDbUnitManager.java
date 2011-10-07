package com.jintegrity.vendor;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.ext.mysql.MySqlConnection;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;

import com.jintegrity.core.DbUnitManager;
import com.jintegrity.model.PropertiesKey;

public class PostgresqlDbUnitManager extends DbUnitManager {

	private static final Logger LOG = Logger.getLogger(PostgresqlDbUnitManager.class);

	public PostgresqlDbUnitManager() { }

	public PostgresqlDbUnitManager(PropertiesKey propertiesKey) {
		super(propertiesKey);
	}

	@Override
	protected IDatabaseConnection getDbUnitConnection() throws DatabaseUnitException, SQLException {
		LOG.info("Postgree driver selected");

		Connection conn = getConnection();

		DatabaseMetaData databaseMetaData = conn.getMetaData();

		String url = databaseMetaData.getURL();
		int start = url.lastIndexOf("/") + 1;
		int end = url.length();
		String schema = url.substring(start, end);

		IDatabaseConnection iConn = new MySqlConnection(conn, schema);

		iConn.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory());

		return iConn;
	}

}