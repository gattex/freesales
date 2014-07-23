package ar.com.freesalesview.server.utils;

import org.apache.log4j.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class LogRemoteServiceServlet extends RemoteServiceServlet {
	
	private Logger log = Logger.getLogger(LogRemoteServiceServlet.class);
	
	@Override
	protected void doUnexpectedFailure(Throwable arg0) {
		log.error(arg0,arg0);
		super.doUnexpectedFailure(arg0);
	}

}
