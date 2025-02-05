package com.employee.services;


import com.employee.dtos.NotificationDto;

public interface NotificationsService {
    /**
     *
     * @param notificationDto
     */
    void sendEmail(NotificationDto notificationDto);
}
