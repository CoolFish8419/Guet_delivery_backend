package com.example.backend.controller;


import com.example.backend.dto.LoginDTO;
import com.example.backend.dto.Response;
import com.example.backend.dto.UserDTO;
import com.example.backend.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Resource
    private UserService userService;

    @ResponseBody
    @PostMapping("/login")
    public Response Login(@RequestBody LoginDTO loginDTO){
        Response response=userService.Login(loginDTO);
        return response;
    }
    @ResponseBody
    @PostMapping("/delete")
    public Response Delete(@RequestBody UserDTO userDTO){
        Response response=userService.Delete(userDTO);
        return response;
    }

    @ResponseBody
    @PostMapping("/update")
    public Response Update(@RequestBody UserDTO userDTO){
        Response response=userService.Update(userDTO);
        return response;
    }

    @ResponseBody
    @GetMapping("/list")
    public Response List(){
        return userService.List();
    }

    @ResponseBody
    @PostMapping("/get")
    public Response Get(@RequestBody UserDTO userDTO){
        Response response=userService.Get(userDTO);
        return response;
    }
}
