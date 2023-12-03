package bg.softuni.aquagatedb.service.scheduler;

import bg.softuni.aquagatedb.service.TopicService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneOffset;

@Component
public class NotApprovedTopicsCleanUpScheduler {
    private final TopicService topicService;

    public NotApprovedTopicsCleanUpScheduler(TopicService topicService) {
        this.topicService = topicService;
    }

    @Scheduled(cron = "0 59 23 * * *")
    private void notApprovedTopicsCleanUp() {
        LocalDate now = LocalDate.now(ZoneOffset.UTC);
        topicService.removeAllNotApprovedTopicsBeforeDate(now);
    }
}
