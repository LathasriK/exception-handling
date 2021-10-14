package com.latha.creditservice.controller;

import com.latha.creditservice.dao.CreditRepository;
import com.latha.creditservice.exception.EmptyInputException;
import com.latha.creditservice.exception.ResourceNotFoundException;
import com.latha.creditservice.exception.SuccessException;
import com.latha.creditservice.model.CreditRecord;
import com.latha.creditservice.model.CreditRecordResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Random;


@RestController
@RequestMapping("/credit")
public class CreditController {

    private static final int MIN_CREDIT_SCORE = 500;
    private static final int MAX_CREDIT_SCORE = 900;

    @Autowired
    public CreditRepository creditService;

    @PostMapping("/addRecord")
    public CreditRecordResponse addRecord(@RequestBody CreditRecord user) {
        CreditRecordResponse response = new CreditRecordResponse();

        if (user.getPanNumber().isEmpty()) {
            throw new EmptyInputException("Cannot add record. Pan number is empty");
        }
        creditService.save(user);
        response.setCreditRecord(user);
        return response;
    }

    @GetMapping("/getAllRecords")
    public List<CreditRecord> getAll() throws InterruptedException {
        List<CreditRecord> creditRecordList = (List<CreditRecord>) creditService.findAll();
        try {
            Thread.sleep(1);
            throw new InterruptedException("error is thrown");
        } catch (InterruptedException ie) {
            throw ie;
        }
    }


    @GetMapping("/getCreditScore/{panNumber}")
    public String getCreditSore(@PathVariable String panNumber) throws JSONException {
        JSONObject creditScore = new JSONObject();
        Optional<CreditRecord> record = Optional.ofNullable(creditService.findById(panNumber).orElseThrow(() -> new ResourceNotFoundException("Credit score not found with Pan number: " + panNumber)));
        if (record.isPresent()) {
            creditScore.put("creditScore", record.get().getCreditScore());
            System.out.println(creditScore);
            return creditScore.toString();

        }

        return creditScore.toString();
    }
    
}




