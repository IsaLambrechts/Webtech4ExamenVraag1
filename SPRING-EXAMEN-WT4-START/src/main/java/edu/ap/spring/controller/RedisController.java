package edu.ap.spring.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.QueryParam;

import edu.ap.spring.model.InhaalExamen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.ap.spring.redis.RedisService;

@Controller
public class RedisController {

    private List<String> redisMessages = new ArrayList<String>();
    private RedisService service;
    private int id = 1;

    @Autowired
    public void setRedisService(RedisService service) {
        this.service = service;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> messages(@RequestParam("student") String student, @RequestParam("exam") String exam, @RequestParam("reason") String reason) {
        Boolean exist = service.hasKey("inhaalExamen:000" + id + ":" + student, "student");
        Map<Object, Object> map = service.hgetAll("inhaalExamen:000" + id + ":" + student);

        InhaalExamen examen = new InhaalExamen(student, exam, reason);
        if(exist && map.get(student).equals(examen.getStudent()) && map.get(exam).equals(examen.getExam())) {
            System.out.println("bestaat al");
        } else if(exist) {
            service.hset("inhaalExamen:000" + id + ":" + student, "student", examen.getStudent());
            service.hset("inhaalExamen:000" + id + ":" + student, "exam", examen.getExam());
            service.hset("inhaalExamen:000" + id + ":" + student, "reason", examen.getReason());
            service.hset("inhaalExamen:000" + id + ":" + student, "date", examen.getDate());
            id++;
        }


        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/list")
    @ResponseBody
    public String getAllAanvragenStudent(@QueryParam("student") String student) {
        final String[] html = {"<html><head></head><body><h1>" + student + "</h1>"};
        Set<String> keys = service.getKeys("inhaalExamen:*:" + student);
        keys.forEach((s) -> {
            Map<Object, Object> map = service.hgetAll(s);
            html[0] += "<p><b>Student</b><p>" + map.get("student") + "</p>";
            html[0] += "<p><b>Exam</b><p>" + map.get("exam") + "</p>";
            html[0] += "<p><b>Reason</b><p>" + map.get("reason") + "</p>";
            html[0] += "<p><b>Date</b><p>" + map.get("date") + "</p>";
        });
        return html[0];
    }

    // Messaging
    public void onMessage(String message) {
        this.redisMessages.add(message);
    }
}
