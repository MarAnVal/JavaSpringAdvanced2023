package bg.softuni.aquagatedb.service.scheduler;

import bg.softuni.aquagatedb.service.TopicService;
import bg.softuni.aquagatedb.service.util.DateProvider;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotApprovedTopicsCleanUpScheduler {
    private final TopicService topicService;
    public final DateProvider dateProvider;

    public NotApprovedTopicsCleanUpScheduler(TopicService topicService, DateProvider dateProvider) {
        this.topicService = topicService;
        this.dateProvider = dateProvider;
    }

    @Scheduled(cron = "0 59 23 * * *")
    private void notApprovedTopicsCleanUp() {
        topicService.removeAllNotApprovedTopicsBeforeDate(dateProvider.getDate());
    }
}
