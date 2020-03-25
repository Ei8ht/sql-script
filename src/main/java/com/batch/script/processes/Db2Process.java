package com.batch.script.processes;

import COM.ibm.db2os390.sqlj.jdbc.DB2SQLJDriver;
import com.ibm.db2.jcc.am.SqlException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component("Db2Process")
public class Db2Process implements BatchScriptProcess {
	@Value("${app.db2.datasource.url}")
	private String url;
	@Value("${app.db2.datasource.username}")
	private String username;
	@Value("${app.db2.datasource.password}")
	private String password;
	@Value("${app.db2.datasource.driver-class-name}")
	private String driverName;

	@Override
	public void process() {
		log.debug("--------------- Start Db2 Process ---------------");
		Connection con = null;
		try {
			DriverManager.registerDriver((Driver) new DB2SQLJDriver());

			con = DriverManager.getConnection(url, username, password);
			log.debug("Connection established......");
			//Initialize the script runner
			ScriptRunner sr = new ScriptRunner(con);

			Stream<Path> walk = Files.walk(Paths.get("F:\\BackUp\\DB2_DUMP\\sql developer\\SCRIPT_RUN"));
			List<String> fileNameList = walk.filter(Files::isRegularFile) .map(x -> x.toString()).collect(Collectors.toList());
			fileNameList.forEach(fileName -> {
				try {
					Reader reader = new BufferedReader(new FileReader(fileName));
					sr.runScript(reader);
				} catch (FileNotFoundException ef){
					log.error("FileNotFoundException",ef);
				}
			});

		} catch (Exception e){
			log.error("Exception", e);
		} finally {
			try {
				if(con != null && !con.isClosed()){
					con.close();
				}
			} catch (SQLException se){
				log.error("SQLException", se);
			}
		}
		log.debug("--------------- End Db2 Process -----------------");
	}
}
