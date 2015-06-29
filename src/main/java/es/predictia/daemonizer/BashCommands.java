package es.predictia.daemonizer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BashCommands {

	public static String executeBashAndGetResponse(String bashCommand) throws IOException{
		return executeBashAndGetResponse(bashCommand, new Options());
	}
	
	public static String executeBashAndGetResponse(String bashCommand, Options options) throws IOException{
		return executeAndGetResponse(createBashCommand(bashCommand, options.login), options);
	}
	
	public static class Options{
		
		private boolean login = true;
		private File workingDirectory = null;
		private int[] exitValues = null;
		
		public Options withLogin(boolean login) {
			this.login = login;
			return this;
		}
		
		public Options withWorkingDirectory(File workingDirectory) {
			this.workingDirectory = workingDirectory;
			return this;
		}
		
		public Options withExitValues(int[] exitValues) {
			this.exitValues = exitValues;
			return this;
		}
		
	}
	
	private static CommandLine createBashCommand(String bashCommand, boolean login){
		CommandLine cmdLine = new CommandLine("bash");
		if(login){
			cmdLine.addArgument("--login"); // loads user's .profile
		}
		cmdLine.addArgument("-c");
		cmdLine.addArgument(bashCommand, false);
		return cmdLine;
	}
	
	private static String executeAndGetResponse(CommandLine cmdLine, Options options) throws IOException{
		LOGGER.debug("Executing: " + cmdLine);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try{
			DefaultExecutor executor = new DefaultExecutor();		
		    PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
		    executor.setStreamHandler(streamHandler);
		    if(options.workingDirectory != null){
		    	executor.setWorkingDirectory(options.workingDirectory);
		    }
		    if(options.exitValues != null){
		    	executor.setExitValues(options.exitValues);
		    }
		    int responseCode = executor.execute(cmdLine);
			LOGGER.debug("Response code: " + responseCode);
			String response = outputStream.toString();
			LOGGER.debug("Output: " + response);
			return response;
		}finally{
			outputStream.close();
		}
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(BashCommands.class);
	
}
