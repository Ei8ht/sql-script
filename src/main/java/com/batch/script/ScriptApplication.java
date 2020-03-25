package com.batch.script;

import com.batch.script.processes.BatchScriptProcess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@Slf4j
@SpringBootApplication
public class ScriptApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(ScriptApplication.class, args);
		try {
			BatchScriptProcess batchProcess = (BatchScriptProcess) context.getBean(args[0]);
			batchProcess.process();
		} catch (Exception e) {
			log.error("Exception",e);
		}
	}

}
