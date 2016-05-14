package de.wenzlaff.rest.client;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.wenzlaff.timer.AbfrageJob;
import de.wenzlaff.timer.AbfrageListener;

/**
 * Beispiel REST Abfrage der Flugzeugdaten aus Hannover.
 * 
 * @author Thomas Wenzlaff
 * @version 0.1
 */
public class Abfrage {

	private static final Logger LOG = LoggerFactory.getLogger(Abfrage.class);

	/**
	 * Start Methode.
	 * 
	 * @param args
	 *            keine.
	 * @throws Exception
	 *             bei Fehlern
	 */
	public static void main(String[] args) throws Exception {

		LOG.info("Starte Flugzeug Abfrage");

		// nun ein Beispiel zum periodischen Abfragen mit Quarz
		final JobKey abfrageKey = new JobKey("AbfrageNamen", "AbfrageGruppe");
		final JobDetail job = JobBuilder.newJob(AbfrageJob.class).withIdentity(abfrageKey).build();

		// alle 5 Sekunden
		final Trigger trigger = TriggerBuilder.newTrigger().withIdentity("AbfrageNamen", "AbfrageGruppe").withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?")).build();

		final Scheduler scheduler = new StdSchedulerFactory().getScheduler();

		// Listener mit den abfrageKey verbinden
		scheduler.getListenerManager().addJobListener(new AbfrageListener(), KeyMatcher.keyEquals(abfrageKey));

		scheduler.start();
		scheduler.scheduleJob(job, trigger);
	}

}
