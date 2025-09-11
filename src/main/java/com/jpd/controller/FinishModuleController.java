package com.jpd.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jpd.repository.CustomerFinishedModuleRepository;
import com.jpd.service.EnrollService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/finish")
@RequiredArgsConstructor
public class FinishModuleController {
private final EnrollService enSer;
@PostMapping
public ResponseEntity<?>saveProcess(@RequestParam("id_module") long id_module,@RequestParam("enrollId") long enrollId) {

    this.enSer.saveProcess(enrollId, id_module);
    return ResponseEntity.ok("ok bro");
}

}
