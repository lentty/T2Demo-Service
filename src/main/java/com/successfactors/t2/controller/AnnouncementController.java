package com.successfactors.t2.controller;

import com.successfactors.t2.domain.Announcement;
import com.successfactors.t2.domain.Result;
import com.successfactors.t2.service.AnnouncementService;
import com.successfactors.t2.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/announcement")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public Result editAnnouncement(@RequestBody Announcement announcement) {
        if(announcement == null || (announcement.getCreatedBy() == null && announcement.getLastModifiedBy() == null)){
            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
        }
        int status = announcementService.editAnnouncement(announcement);
        return new Result(status, Constants.SUCCESS);
    }

}
