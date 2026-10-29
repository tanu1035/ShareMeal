package com.sharemeal.service;

import com.sharemeal.entity.ActivityLog;
import com.sharemeal.entity.User;
import com.sharemeal.repository.ActivityLogRepository;
import org.springframework.stereotype.Service;

@Service
public class ActivityLogService {

    private final ActivityLogRepository repository;

    public ActivityLogService(ActivityLogRepository repository) {
        this.repository = repository;
    }

    public void log(User user, String action) {
        ActivityLog entry = new ActivityLog();
        entry.setUser(user);
        entry.setAction(action);
        repository.save(entry);
    }
}
