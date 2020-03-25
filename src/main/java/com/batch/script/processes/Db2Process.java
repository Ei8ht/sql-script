package com.batch.script.processes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("Db2Process")
public class Db2Process implements BatchScriptProcess {

	@Override
	public void process() {
		log.debug("--------------- Start Db2 Process ---------------");
		log.debug("--------------- End Db2 Process -----------------");
	}
}
