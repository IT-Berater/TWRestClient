package de.wenzlaff.timer;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Der Listener für die Abfrage.
 *
 * @author Thomas Wenzlaff
 * @version 0.1
 */
public class AbfrageListener implements JobListener {

	private static final Logger LOG = LoggerFactory.getLogger(AbfrageListener.class);

	public String getName() {
		return "Abfrage Listener";
	}

	public void jobExecutionVetoed(JobExecutionContext context) {
		// Quartz will invoke this method when the job execution was banned from the trigger.
		LOG.debug("jobExecutionVetoed");
	}

	public void jobToBeExecuted(JobExecutionContext context) {
		// Quartz will invoke this method when the job is going to be executed.
		final String jobName = context.getJobDetail().getKey().toString();
		LOG.debug("jobToBeExecuted: {} Job startet ...", jobName);
	}

	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		// Quartz will invoke this method when the job was executed.
		LOG.debug("jobWasExecuted");

		final String jobName = context.getJobDetail().getKey().toString();

		LOG.debug("Job : {} ist beendet", jobName);

		if (jobException != null && jobException.getMessage().equals("")) { // bei Fehler
			LOG.error("Exception thrown by: {}", jobName, jobException);
		}
	}

}
