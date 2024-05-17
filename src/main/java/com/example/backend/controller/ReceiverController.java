package com.example.backend.controller;

import com.example.backend.dto.ReceiverDTO;
import com.example.backend.dto.ReceiverResponse;
import com.example.backend.service.ReceiverService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/receiver")
@CrossOrigin
public class ReceiverController {
    @Resource
    private ReceiverService receiverService;
    @PostMapping("/details")
    @ResponseBody
    public ReceiverResponse Details(@RequestBody ReceiverDTO receiverDTO){
        return receiverService.Details(receiverDTO);
    }

    @PostMapping("/search")
    @ResponseBody
    public ReceiverResponse Search(@RequestBody ReceiverDTO receiverDTO){
        return receiverService.Search(receiverDTO);
    }

    @PostMapping("/delete")
    @ResponseBody
    public ReceiverResponse Delete(@RequestBody ReceiverDTO receiverDTO){
        return receiverService.Delete(receiverDTO);
    }

    @PostMapping("/add")
    @ResponseBody
    public ReceiverResponse Add(@RequestBody ReceiverDTO receiverDTO){
        return receiverService.Add(receiverDTO);
    }

    @PostMapping("/all")
    @ResponseBody
    public ReceiverResponse All(@RequestBody ReceiverDTO receiverDTO){
        return receiverService.All(receiverDTO);
    }

    @GetMapping("/ma_all")
    @ResponseBody
    public ReceiverResponse All(){
        return receiverService.Ma_All();
    }

    @PostMapping("/update")
    @ResponseBody
    public ReceiverResponse Update(@RequestBody ReceiverDTO receiverDTO){
        return receiverService.Update(receiverDTO);
    }

}
