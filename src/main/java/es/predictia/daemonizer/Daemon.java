package es.predictia.daemonizer;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;

public class Daemon implements CommandLineRunner {

	@Override
	public void run(String... arg0) throws Exception {
		if(StringUtils.isNotBlank(onInitCommand)){
			executeBashCommand(onInitCommand);
		}
		if(StringUtils.isNotBlank(onfinishCommand)){
			Runtime.getRuntime().addShutdownHook(new Thread(){
	            @Override
	            public void run(){
	            	executeBashCommand(onfinishCommand);
	            }
	        });
		}
		while(true){
            Thread.sleep(sleepIntervalMillis);
            if(StringUtils.isNotBlank(onwaitCommand)){
    			executeBashCommand(onwaitCommand);
    		}
        }
	}
	
	private static void executeBashCommand(String command){
		try {
			LOGGER.info("Running command: " + command);
			String response = BashCommands.executeBashAndGetResponse(command);
			if(StringUtils.isNotBlank(response)){
				LOGGER.info("Command response: " + response);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(Daemon.class);
	
	@Value("${command.oninit}") private String onInitCommand;
	@Value("${command.onwait}") private String onwaitCommand;
	@Value("${command.onfinish}") private String onfinishCommand;
	@Value("${sleep.interval.millis}") private Long sleepIntervalMillis;

}
