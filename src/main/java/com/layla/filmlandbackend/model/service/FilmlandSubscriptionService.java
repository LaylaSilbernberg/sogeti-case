package com.layla.filmlandbackend.model.service;

import com.layla.filmlandbackend.exception.EmailSchedulingException;
import com.layla.filmlandbackend.exception.InvalidSubscriptionException;
import com.layla.filmlandbackend.controller.dto.CategoriesDTO;
import com.layla.filmlandbackend.controller.dto.CategoryDTO;
import com.layla.filmlandbackend.enums.SubscriptionCategory;
import com.layla.filmlandbackend.exception.InvalidCategoryException;
import com.layla.filmlandbackend.interfaces.SubscriptionService;
import com.layla.filmlandbackend.model.entity.FilmlandUser;
import com.layla.filmlandbackend.model.entity.Subscription;
import com.layla.filmlandbackend.model.repository.FilmlandUserRepository;
import com.layla.filmlandbackend.model.repository.SubscriptionRepository;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.quartz.CalendarIntervalScheduleBuilder.*;
import static org.quartz.DateBuilder.*;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;

@Service
public class FilmlandSubscriptionService implements SubscriptionService {

    private static final Logger LOG = LoggerFactory.getLogger(FilmlandSubscriptionService.class);

    private final FilmlandUserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final Scheduler scheduler;

    public FilmlandSubscriptionService(FilmlandUserRepository userRepository,
                                       SubscriptionRepository subscriptionRepository,
                                       Scheduler scheduler) {
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;

        this.scheduler = scheduler;
    }

    @Override
    public CategoriesDTO getAvailableCategories(String username) {
        FilmlandUser user = getUser(username);

        Set<Subscription> subscriptions = user.getSubscriptions();

        if (subscriptions.isEmpty()) {
            return new CategoriesDTO(Arrays
                    .stream(SubscriptionCategory
                            .values())
                    .map(SubscriptionCategory::makeDTO)
                    .collect(Collectors.toCollection(LinkedHashSet::new)),
                    Collections.emptySet());
        }

        Set<CategoryDTO> filteredCategories = filterSubscriptionCategories(user);

        return new CategoriesDTO(filteredCategories, subscriptions
                .stream()
                .map(Subscription::makeDto)
                .collect(Collectors.toCollection(LinkedHashSet::new)));
    }

    private Set<CategoryDTO> filterSubscriptionCategories(FilmlandUser user) {
        Set<CategoryDTO> subscriptionCategories = new LinkedHashSet<>();
        CATEGORIES:
        for (SubscriptionCategory category : SubscriptionCategory.values()) {
            SUBSCRIPTIONS:
            for (Subscription subscription : user.getSubscriptions()) {
                if (category.getName().equals(subscription.getCategory().getName())) {
                    continue;
                }
                CategoryDTO dto = category.makeDTO();
                subscriptionCategories.add(dto);
            }
        }
        return subscriptionCategories;
    }

    @Override
    public Set<Subscription> subscribe(String username, SubscriptionCategory category) {
        FilmlandUser user = getUser(username);

        for (Subscription subscription : user.getSubscriptions()) {
            if (subscription.getCategory().equals(category)) {
                throw new InvalidSubscriptionException("Subscription already exists");
            }
        }

        SubscriptionCategory subscriptionCategory = Arrays
                .stream(SubscriptionCategory.values())
                .filter(element -> element.equals(category))
                .findFirst()
                .orElseThrow(() ->
                        new InvalidCategoryException("%s does not match any available categories"
                                .formatted(category)));

        Subscription newSubscription = new Subscription(
                subscriptionCategory,
                user);
        user.addSubscriptions(newSubscription);
        subscriptionRepository.save(newSubscription);

        try {
            scheduleEmail(username, subscriptionCategory);
        } catch (SchedulerException e) {
            throw new EmailSchedulingException(e.getMessage());
        }

        return userRepository.save(user).getSubscriptions();
    }

    @Override
    public Set<FilmlandUser> addSubscriber(String username, String clientName, SubscriptionCategory category) {
        Subscription subscription = getUser(username).getSubscription(category);
        FilmlandUser userToAdd = getUser(clientName);
        userToAdd.addSubscriptions(subscription);
        subscription.addUser(userToAdd);
        userRepository.save(userToAdd);

        try {
            updateScheduledEmail(username, clientName);
        } catch (SchedulerException e) {
            throw new EmailSchedulingException(e.getMessage());
        }


        return subscriptionRepository.save(subscription).getUsers();
    }

    private void updateScheduledEmail(String username, String clientName) throws SchedulerException {
        JobDetail detail = scheduler.getJobDetail(JobKey.jobKey("%s email".formatted(username), "group1"));
        Object possibleUsernames = detail.getJobDataMap().get("username");
        ArrayList<String> usernames;

        if (possibleUsernames instanceof String email) {
            usernames = new ArrayList<>(List.of(email));
        } else if (possibleUsernames instanceof String[] emails) {
            usernames = new ArrayList<>(Arrays.stream(emails).toList());
        } else {
            throw new IllegalArgumentException("Unexpected username object");
        }

        usernames.add(clientName);
        detail.getJobDataMap().put("username", usernames.toArray(String[]::new));
        scheduler.addJob(detail, true);
    }

    private Date scheduleEmail(String username, SubscriptionCategory subscriptionCategory) throws SchedulerException {
        Trigger monthlyEmailtrigger = newTrigger()
                .withIdentity("Email Trigger %s".formatted(username), "group1")
                .withSchedule(calendarIntervalSchedule().withIntervalInMonths(1))
                .startAt(futureDate(1, IntervalUnit.MONTH))
                .build();

        JobDetail emailJob = newJob(EmailJob.class)
                .storeDurably()
                .withIdentity("%s email".formatted(username), "group1")
                .usingJobData("invoice", """
                        To whom it may concern,
                        We would like to inform you that your payment for the
                        %s Filmland subscription is due. Please pay the agreed upon
                        sum within 30 days. On behalf of Filmland, we are thrilled
                        to have you with us and we hope you continue enjoy quality
                        cinema and series.""".formatted(subscriptionCategory.getName()))
                .usingJobData("username", username)
                .build();

        return scheduler.scheduleJob(emailJob, monthlyEmailtrigger);
    }

    private FilmlandUser getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("%s not found".formatted(username)));
    }

}
