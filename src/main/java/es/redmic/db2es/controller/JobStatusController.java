package es.redmic.db2es.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import es.redmic.db2es.dto.EventTask;
import es.redmic.db2es.dto.StatusTask;
import es.redmic.db2es.jobs.job.indexing.JobsNames;

@Controller
public class JobStatusController implements ApplicationListener<EventTask> {

	@Autowired
	JobOperator jobOperator;
	
	@Autowired
	JobExplorer jobExplorer;
	
	@Autowired
    private SimpMessagingTemplate template;	

	
    @Scheduled(fixedDelay=5000)
    public void status() {
		List<StatusTask> tasks = new ArrayList<StatusTask>();
		
		for (String jobName : jobExplorer.getJobNames()) {
			for (JobExecution jobExec : jobExplorer.findRunningJobExecutions(jobName)) {
				tasks.add(new StatusTask(jobExec));
			}
		}
		
		if (!tasks.isEmpty())
			template.convertAndSend("/topic/jobs/status", tasks);
    }
    
    @MessageMapping("/jobs/list")
    public void getJobs() {
		List<String> tasks = new ArrayList<String>();
		
		for (JobsNames jobName : JobsNames.values()) {
			tasks.add("indexing-" + jobName.toString() + "-job");
		}
				
		if (!tasks.isEmpty())
			template.convertAndSend("/topic/jobs/list", tasks);
    }

	public void onApplicationEvent(EventTask event) {
		List<StatusTask> tasks = new ArrayList<StatusTask>();
		tasks.add(event.getTask());
		
		template.convertAndSend("/topic/jobs/status", tasks);	
	} 

}

